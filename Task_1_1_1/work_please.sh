javadoc -d build/docs/javadoc -sourcepath src/main/java -subpackages ru.nsu.abramenko
javac src/main/java/ru/nsu/abramenko/Heapsort.java -d ./build
java -cp ./build ru.nsu.abramenko.Heapsort

echo "IT IS WORKING"