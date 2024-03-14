CREATE TABLE chat
(
    id              bigint                  NOT NULL PRIMARY KEY
);
/**/
CREATE TABLE link
(
    id              bigint generated always as identity,
    url             text                     not null,
    last_check_time timestamp with time zone not null,
    created_at      timestamp with time zone not null,
    created_by      text                     not null,


    primary key (id),
    unique (url)
);
/**/
CREATE TABLE chat_link
(
    id              bigint                  NOT NULL PRIMARY KEY,
    chat_id         bigint                  UNIQUE,
    link_id         bigint                  UNIQUE,

    FOREIGN KEY (chat_id)                   REFERENCES chat(id),
    FOREIGN KEY (link_id)                   REFERENCES link(id),


);
