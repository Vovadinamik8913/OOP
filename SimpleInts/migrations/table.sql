create table if not exists chats (
    id bigint generated always as identity,
    chat_id bigint not null,
    primary key(id),
    unique (chat_id)
);


create table if not exists links (
    id bigint generated always as identity,
    url text not null,
    updated_at timestamp with time zone not null,
    chat_id bigint not null,
    primary key (id),
    foreign key (chat_id) references chats(chat_id)
);
