create table profile (
    id varchar(255) not null,
    email varchar(50),
    first_name varchar(50),
    last_name varchar(50),
    primary key (id)

);
create table avatar (
    id bigint not null,
    url text,
    profile_id varchar(255),
    primary key (id)
);
 ALTER  TABLE  avatar  ADD  CONSTRAINT  fk_avatar_profile FOREIGN  KEY  (profile_id)  REFERENCES  profile(id);
