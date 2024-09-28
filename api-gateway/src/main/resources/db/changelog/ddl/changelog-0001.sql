
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
    type varchar(255),
    status TINYINT,
    housenumber varchar(255),
    thumbnail_id bigint,
    primary key (place_id)
 );
 create table location_collections (
    location_place_id bigint,
    collections_id bigint,
    primary key (location_place_id,collections_id)

 );
 ALTER  TABLE  location  ADD  CONSTRAINT  fk_location_locationImage FOREIGN  KEY  (thumbnail_id)  REFERENCES  location_image(id);
alter table location_collections add constraint FKs6cydsualtsrprvlf2bb3lcam foreign key (location_place_id) references location(place_id);
alter table location_collections add constraint FKgmb19wbjvpu06559t7w33wqoc foreign key (collections_id) references location_image(id);

