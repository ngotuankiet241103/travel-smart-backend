create table review (
    id bigint not null auto_increment,
    content varchar(255),
    star_rate int,
    user_id varchar(255) not null,
    location_id bigint not null,
    primary key (id)

);
create table review_image (
    id bigint not null,
    url text,
    review_id bigint,
    primary key (id)
);
 ALTER  TABLE  review_image  ADD  CONSTRAINT  fk_reviewImage_review FOREIGN  KEY  (review_id)  REFERENCES  review(id);
