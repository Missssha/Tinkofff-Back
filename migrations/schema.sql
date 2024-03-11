CREATE TABLE chat
(
    id              bigint                  NOT NULL PRIMARY KEY,
    chat_id         bigint                  NOT NULL,
    link_id         bigint                  NOT NULL REFERENCES link(id)
);
/**/
CREATE TABLE link
(
    id              bigint generated always as identity,
    url             text                     not null,
    link_id         bigint                   not null,
    chat_id         bigint                   NOT NULL REFERENCES chat (id),

    last_check_time timestamp with time zone not null,

    created_at      timestamp with time zone not null,
    created_by      text                     not null,


    primary key (id),
    unique (url)
);
