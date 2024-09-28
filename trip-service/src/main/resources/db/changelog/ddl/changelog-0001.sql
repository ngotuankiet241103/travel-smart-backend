create table trip (
    id bigint not null auto_increment,
    title varchar(255),
    code varchar(255),
    start_date DATETIME,
    end_date DATETIME,
    location_id bigint not null,
    image varchar(255),
    primary key (id)
);

create table itinerary (
    id bigint not null auto_increment,
    day DATETIME,
    trip_id bigint not null,
    note varchar(255),
    primary key (id)
);
create table destination (
    id bigint not null auto_increment,
    position int,
    location_id bigint not null,
    itinerary_id bigint not null,
    primary key (id)
);
create table user_trip (
    id bigint not null auto_increment,
    permission tinyint,
    user_id varchar(255) not null,
    trip_id bigint not null,
    primary key (id)
);

ALTER  TABLE  itinerary  ADD  CONSTRAINT  fk_itinerary_trip FOREIGN  KEY  (trip_id)  REFERENCES  trip(id);
ALTER  TABLE  destination  ADD  CONSTRAINT  fk_destination_itinerary FOREIGN  KEY  (itinerary_id)  REFERENCES  itinerary(id);
ALTER  TABLE  user_trip  ADD  CONSTRAINT  fk_userTrip_trip FOREIGN  KEY  (trip_id)  REFERENCES  trip(id);