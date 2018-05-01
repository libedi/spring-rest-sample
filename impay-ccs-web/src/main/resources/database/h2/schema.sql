CREATE TABLE USERS (
  username varchar(256),
  password varchar(256),
  enabled boolean
);

CREATE TABLE AUTHORITIES (
  username varchar(256),
  authority varchar(256)
);

create table persistent_logins (
  username varchar(64) not null,
  series varchar(64) primary key,
  token varchar(64) not null,
  last_used timestamp not null
);

CREATE TABLE IMPAY_SAMPLE
(
    key VARCHAR(255) PRIMARY KEY NOT NULL,
    value VARCHAR(255)
);