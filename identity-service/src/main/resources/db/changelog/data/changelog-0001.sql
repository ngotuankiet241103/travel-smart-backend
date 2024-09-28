INSERT INTO permission (name,description)
VALUES  ('BLOG_CREATE','Permission to create a blog'),
        ('BLOG_DELETE','Permission to delete a blog'),
        ('BLOG_READ','Permission to read a blog'),
        ('BLOG_UPDATE','Permission to update a blog'),
        ('LOCATION_CREATE','Permission to create a new location'),
        ('LOCATION_DELETE','Permission to delete a location'),
        ('LOCATION_READ','Permission to create a new location'),
        ('LOCATION_UPDATE','Permission to create a new location');

INSERT INTO role (name,description)
VALUES  ('ADMIN','Role admin'),
        ('Manager','Role MANAGER'),
        ('USER','Role user');

INSERT INTO role_permissions (role_name,permission_name)
VALUES  ('ADMIN','BLOG_CREATE'),
        ('USER','BLOG_CREATE'),
        ('ADMIN','BLOG_DELETE'),
        ('USER','BLOG_DELETE'),
        ('ADMIN','BLOG_READ'),
        ('USER','BLOG_READ'),
        ('ADMIN','BLOG_UPDATE'),
        ('USER','BLOG_UPDATE'),
        ('ADMIN','LOCATION_CREATE'),
        ('Manager','LOCATION_CREATE'),
        ('USER','LOCATION_CREATE'),
        ('ADMIN','LOCATION_DELETE'),
        ('ADMIN','LOCATION_READ'),
        ('USER','LOCATION_READ'),
        ('ADMIN','LOCATION_UPDATE'),
        ('Manager','LOCATION_UPDATE');

INSERT INTO users (id,email,password,is_block,is_enable)
VALUES  ('ab1e5b37-f155-40fc-a90f-bcf22b3075e5','duy@yopmail.com','$2a$10$nEziedPl199RVnrL5FVsF.v0JqqWWaIy8bFIJAM9KusEh/O/11bkm',0,1),
        ('babbf18a-6902-416f-be6a-6dba331f7ffe','khoa@yopmail.com','$2a$10$YjDRnGv.o4nIPtaIMO/H9.LU/cUZmj4ucnNYEHiGPtEILQEECXTn2',0,1),
        ('d597e502-6ca5-4512-9276-82b86c0da080','tien@yopmail.com','$2a$10$co9YUSdcbIFpQYv01YepcOJnn3OYkps.S1jamoCM1PHqNOW7/qWum',0,1),
        ('f7e68997-962d-4362-9319-ce6c98e5add7','admin@gmail.com','$2a$10$zyTh1nrQvMfN122fzcq/le9eK23rEs7OaYWBrWG/2Jnq9B8k5v.cm',0,1);

INSERT INTO user_roles (user_id, role_name)
VALUES  ('f7e68997-962d-4362-9319-ce6c98e5add7','ADMIN'),
        ('ab1e5b37-f155-40fc-a90f-bcf22b3075e5','USER'),
        ('babbf18a-6902-416f-be6a-6dba331f7ffe','USER'),
        ('d597e502-6ca5-4512-9276-82b86c0da080','USER');


