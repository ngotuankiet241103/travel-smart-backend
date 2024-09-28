create table blog (
    id bigint not null auto_increment;
    title varchar(100),
    code varchar(255),
    content text,
    tags varchar(255),
    user_id varchar(255) not null,
    userName varchar(255) not null,
    create_date datetime,
    primary key (id)
);
create table category (
    name varchar(255) not null,
    code varchar(255),
    primary key (name)
);
create table blog_category (
    blog_id bigint,
    category_name varchar(255),
    primary key (blog_id,category_name)
);
create table blog_favorite (
    user_id varchar(255) not null,
    blog_id bigint not null,
    primary key (user_id,blog_id)
);
ALTER  TABLE  blog_category  ADD  CONSTRAINT  fk_blogCategory_blog FOREIGN  KEY  (blog_id)  REFERENCES  blog(id);
ALTER  TABLE  blog_category  ADD  CONSTRAINT  fk_blogCategory_category FOREIGN  KEY  (category_name)  REFERENCES  category(name);
ALTER  TABLE  blog_favorite  ADD  CONSTRAINT  fk_blogFavorite_blog FOREIGN  KEY  (blog_id)  REFERENCES  blog(id);


