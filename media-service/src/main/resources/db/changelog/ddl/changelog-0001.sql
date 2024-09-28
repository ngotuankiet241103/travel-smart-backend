create table media (
    id bigint not null auto_increment,
    url text,
    caption varchar(255),
    public_id varchar(255),
    media_type varchar(255),
    primary key (id)

);