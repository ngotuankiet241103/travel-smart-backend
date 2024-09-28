create table permission (
    name varchar(50) not null,
    description varchar(255),
    primary key (name)
);
create table role (
    name varchar(50) not null,
    description varchar(255),
    primary key (name)
);
create table role_permissions (
    role_name varchar(50) not null,
    permission_name varchar(50) not null,
    primary key (role_name,permission_name)
);
create table users(
    id varchar(255) not null,
    email varchar(50),
    password varchar(255),
    is_block bit,
    is_enable bit,
    primary key(id)
);
create table user_roles (
    user_id varchar(255),
    role_name varchar(255),
    primary key (user_id,role_name)
);
create table confirm_token (
    token varchar(255) not null,
    is_confirm bit,
    is_expired bit,
    type tinyint,
    time_expired DATETIME,
    user_id varchar(255) not null,
    primary key (token)
);
create table token (
    token varchar(255) not null,
    is_expired bit,
    user_id varchar(255) not null,
    primary key (token)
);
ALTER  TABLE  role_permissions  ADD  CONSTRAINT  fk_rolePermission_role FOREIGN  KEY  (role_name)  REFERENCES  role(name);
ALTER  TABLE  role_permissions  ADD  CONSTRAINT  fk_rolePermission_permission FOREIGN  KEY  (permission_name)  REFERENCES  permission(name);
ALTER  TABLE  user_roles  ADD  CONSTRAINT  fk_userRole_role FOREIGN  KEY  (role_name)  REFERENCES  role(name);
ALTER  TABLE  user_roles  ADD  CONSTRAINT  fk_userRole_user FOREIGN  KEY  (user_id)  REFERENCES  users(id);
ALTER  TABLE  confirm_token  ADD  CONSTRAINT  fk_confirmToken_user FOREIGN  KEY  (user_id)  REFERENCES  users(id);
ALTER  TABLE  token  ADD  CONSTRAINT  fk_token_user FOREIGN  KEY  (user_id)  REFERENCES  users(id);



