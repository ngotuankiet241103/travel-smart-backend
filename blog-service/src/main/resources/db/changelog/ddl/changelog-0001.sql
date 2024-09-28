create table blog (
    id bigint not null auto_increment,
    title varchar(100),
    code varchar(255),
    content text,
    tags varchar(255),
    user_id varchar(255) not null,
    userName varchar(255) not null,
    create_date datetime,
    status tinyint,
    image_id bigint,
    primary key (id)
);
create table blog_image (
    id bigint not null,
    url varchar(255),
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
create table comment (
    id bigint not null auto_increment,
    content varchar(255),
    node_left int,
    node_right int,
    tree_id varchar(255),
    parent_id bigint,
    blog_id bigint,
    user_id varchar(255),
    create_date datetime,
    primary key (id)

);
ALTER TABLE   blog ADD  CONSTRAINT  fk_blogCategory_blog FOREIGN  KEY  (image_id)  REFERENCES  blog_image(id);
ALTER  TABLE  blog_category  ADD  CONSTRAINT  fk_blogCategory_blog FOREIGN  KEY  (blog_id)  REFERENCES  blog(id);
ALTER  TABLE  blog_category  ADD  CONSTRAINT  fk_blogCategory_category FOREIGN  KEY  (category_name)  REFERENCES  category(name);
ALTER  TABLE  blog_favorite  ADD  CONSTRAINT  fk_blogFavorite_blog FOREIGN  KEY  (blog_id)  REFERENCES  blog(id);
ALTER TABLE   comment ADD  CONSTRAINT  fk_comment_blog FOREIGN  KEY  (blog_id)  REFERENCES  blog(id);
ALTER TABLE   comment ADD  CONSTRAINT  fk_blogParent_blog FOREIGN  KEY  (parent_id)  REFERENCES  blog(id);



