package ru.nsu.abramenko.dsl.config;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyObjectSupport;
import groovy.lang.GroovyShell;
import groovy.lang.MetaProperty;
import groovy.util.DelegatingScript;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import lombok.SneakyThrows;
import org.codehaus.groovy.control.CompilerConfiguration;

public class Configuration extends GroovyObjectSupport {
    public URI scriptPath;
    public List<String> essentials = List.of("tasks", "allStudents");

    @SneakyThrows
    public void runFrom(URI uri) {
        this.scriptPath = uri;
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());
        GroovyShell sh = new GroovyShell(getClass().getClassLoader(), new Binding(), cc);
        DelegatingScript script = (DelegatingScript) sh.parse(uri);
        script.setDelegate(this);
        script.run();
    }

    @SneakyThrows
    public void methodMissing(String name, Object args) {
        MetaProperty metaProperty = getMetaClass().getMetaProperty(name);
        if (metaProperty != null) {
            Closure<?> closure = (Closure<?>) ((Object[]) args)[0];
            Class<?> type = metaProperty.getType();
            Constructor<?> constructor = type.getConstructor();
            Object value = getProperty(name) == null
                    ? constructor.newInstance() :
                    getProperty(name);
            closure.setDelegate(value);
            closure.setResolveStrategy(Closure.DELEGATE_FIRST);
            closure.call();
            setProperty(name, value);
        } else {
            throw new IllegalArgumentException("No such field: " + name);
        }
    }

    public void postProcess() {
        for (String propName : essentials) {
            postProcessSpecific(propName);
        }
        for (MetaProperty metaProperty : getMetaClass().getProperties()) {
            postProcessSpecific(metaProperty.getName(), metaProperty);
        }
    }

    public void postProcessSpecific(String propName) {
        MetaProperty metaProperty = getMetaClass().getMetaProperty(propName);
        if (metaProperty == null) {
            return;
        }
        postProcessSpecific(propName, metaProperty);
    }

    @SneakyThrows
    public void postProcessSpecific(String propName, MetaProperty metaProperty) {
        Object value = getProperty(propName);
        if (Collection.class.isAssignableFrom(metaProperty.getType())
                && value instanceof Collection) {
            java.lang.reflect.Field field;
            try {
                field = getClass().getDeclaredField(metaProperty.getName());
            } catch (NoSuchFieldException e) {
                field = getClass().getSuperclass().getDeclaredField(metaProperty.getName());
            }
            ParameterizedType collectionType = (ParameterizedType) field.getGenericType();
            Class<?> itemClass = (Class<?>) collectionType.getActualTypeArguments()[0];
            if (Configuration.class.isAssignableFrom(itemClass)) {
                Collection<?> collection = (Collection<?>) value;
                Collection<Object> newValue = collection
                        .getClass().getDeclaredConstructor().newInstance();
                for (Object o : collection) {
                    if (o instanceof Closure<?>) {
                        Object item = itemClass.getDeclaredConstructor().newInstance();
                        ((Configuration) item).setProperty("scriptPath", scriptPath);
                        ((Closure<?>) o).setDelegate(item);
                        ((Closure<?>) o).setResolveStrategy(Closure.DELEGATE_FIRST);
                        ((Closure<?>) o).call();
                        ((Configuration) item).postProcess();
                        newValue.add(item);
                    } else {
                        newValue.add(o);
                    }
                }
                setProperty(metaProperty.getName(), newValue);
            }
        }
    }
}
