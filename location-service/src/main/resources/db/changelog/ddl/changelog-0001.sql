
create table location_image(
    id bigint not null,
    url text,
    caption varchar(255),
    primary key (id)
);
create table location (
    place_id bigint not null auto_increment,
    city varchar(255),
    country varchar(255),
    country_code varchar(255),
    postcode varchar(255),
    quarter varchar(255),
    road varchar(255),
    state varchar(255),
    suburb varchar(255),
    address_type varchar(255),
    boundingbox varchar(255),
    category varchar(255),
    display_name varchar(255),
    lat varchar(255) unique,
    lon varchar(255) unique,
    name varchar(255),
    type tinyint,
    status TINYINT,
    housenumber varchar(255),
    thumbnail_id bigint,
    time_visit int,
    primary key (place_id)
 );
 create table introduce_location(
    id bigint not null auto_increment,
    title varchar(255),
    content text,
    location_place_id bigint not null,
    primary key (id)
 );
 create table introduce_collections (
    introduce_id bigint,
    collections_id bigint,
    primary key (introduce_id,collections_id)

 );

 ALTER  TABLE  location  ADD  CONSTRAINT  fk_location_locationImage FOREIGN  KEY  (thumbnail_id)  REFERENCES  location_image(id);
 ALTER  TABLE  introduce_location  ADD  CONSTRAINT  fk_introduce_location FOREIGN  KEY  (location_place_id)  REFERENCES  location(place_id);
alter table introduce_collections add constraint FKs6cydsualtsrprvlf2bb3lcam foreign key (introduce_id) references introduce_location(id);
alter table introduce_collections add constraint FKgmb19wbjvpu06559t7w33wqoc foreign key (collections_id) references location_image(id);

