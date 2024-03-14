CREATE TABLE chat
(
    id              BIGINT                  NOT NULL PRIMARY KEY
);
/**/
CREATE TABLE link
(
    id              bigint generated always as identity,
    url             text                     not null,
    last_check_time timestamp with time zone not null,
    created_at      timestamp with time zone not null,
    created_by      text                     not null,


    PRIMARY KEY (id),
    UNIQUE (url)
);
/**/
CREATE TABLE chat_link
(
    id              BIGINT                  NOT NULL PRIMARY KEY,
    chat_id         BIGINT                  NOT NULL ,
    link_id         BIGINT                  NOT NULL ,

    FOREIGN KEY (chat_id)                   REFERENCES chat(id),
    FOREIGN KEY (link_id)                   REFERENCES link(id),

    UNIQUE (chat_id, link_id)
);
