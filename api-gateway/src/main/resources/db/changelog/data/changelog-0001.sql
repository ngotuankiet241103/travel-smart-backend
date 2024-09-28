INSERT INTO location_image (id,caption,url)
VALUES  (1,"Cầu rồng","http://res.cloudinary.com/dzumda5fa/image/upload/v1727321979/b65a16c7-a4dc-4e55-bba6-e845a12910c5.jpg"),
        (2,"Cầu rồng","http://res.cloudinary.com/dzumda5fa/image/upload/v1727321979/b65a16c7-a4dc-4e55-bba6-e845a12910c5.jpg"),
        (3,"Vincom","http://res.cloudinary.com/dzumda5fa/image/upload/v1727318446/48876497-723a-4341-bd59-15d0050ec6d1.jpg"),
        (4,"Vincom","http://res.cloudinary.com/dzumda5fa/image/upload/v1727318446/48876497-723a-4341-bd59-15d0050ec6d1.jpg"),
        (5,"Vincom","http://res.cloudinary.com/dzumda5fa/image/upload/v1727318446/48876497-723a-4341-bd59-15d0050ec6d1.jpg"),
        (6,"Vincom","http://res.cloudinary.com/dzumda5fa/image/upload/v1727318446/48876497-723a-4341-bd59-15d0050ec6d1.jpg");

INSERT INTO  location (city,country,country_code,postcode,quarter,road,state,suburb,address_type,boundingbox,category,display_name,lat,lon,name,type,status,housenumber,thumbnail_id)
VALUES ('Thành phố Đà Nẵng','Việt Nam','vn','02363','Phường An Hải Tây','Đường Võ Văn Kiệt',NULL,'Quận Sơn Trà',NULL,'16.0614250;16.0615250;108.2328275;108.2329275','tourism','Cầu Rồng, Đường Võ Văn Kiệt, Phường An Hải Tây, Quận Sơn Trà, Thành phố Đà Nẵng, 02363, Việt Nam','16.061475','108.2328775','Cầu Rồng','attraction',0,NULL,1),
       ('Thành phố Đà Nẵng','Việt Nam','vn',NULL,NULL,NULL,NULL,NULL,NULL,'15.9179902;16.3340091;107.8185394;108.6563747','boundary','Thành phố Đà Nẵng, Thành phố Đà Nẵng, Việt Nam.','16.068','108.212','Thành phố Đà Nẵng','administrative',1,NULL,2),
       (NULL,'Việt Nam','vn',NULL,NULL,NULL,'Tỉnh Hưng Yên',NULL,NULL,'20.9499854;20.9525216;105.9732981;105.9762071','landuse','Vincom, The Empire Vinhomes Ocean Park 2, Huyện Văn Giang, Tỉnh Hưng Yên, Việt Nam','20.9514192','105.97475387053049','Vincom','construction',0,NULL,3),
       ('Thành phố Hà Nội','Việt Nam','vn','10074','Phường Mỹ Đình 1','Đường Phạm Hùng',NULL,'Quận Nam Từ Liêm',NULL,'21.0201508;21.0202508;105.7812018;105.7813018','shop','Vincom Plaza Skylake, Đường Phạm Hùng, Phường Mỹ Đình 1, Quận Nam Từ Liêm, Thành phố Hà Nội, 10074, Việt Nam','21.0202008','105.7812518','Vincom Plaza Skylake','mall',1,NULL,4),
       ('Thành phố Hà Nội','Việt Nam','vn','10306','Phường Trung Tự','Phố Phạm Ngọc Thạch',NULL,'Quận Đống Đa',NULL,'21.0059610;21.0066083;105.8316234;105.8323411','shop','Vincom Phạm Ngọc Thạch, 02, Phố Phạm Ngọc Thạch, Phường Trung Tự, Quận Đống Đa, Thành phố Hà Nội, 10306, Việt Nam','21.0064031','105.83201014484082','Vincom Phạm Ngọc Thạch','mall',1,NULL,5),
       ('Thành phố Hà Nội','Việt Nam','vn','10055','Phường Trung Hoà','Đường Trần Duy Hưng',NULL,'Quận Cầu Giấy',NULL,'21.0059806;21.0072784;105.7947252;105.7962853','shop','Vincom Center Trần Duy Hưng, 119, Đường Trần Duy Hưng, Phường Trung Hoà, Quận Cầu Giấy, Thành phố Hà Nội, 10055, Việt Nam','21.006624199999997','105.7955029691508','Vincom Center Trần Duy Hưng','mall',1,NULL,6);


CREATE FUNCTION remove_accents(str VARCHAR(255))
RETURNS VARCHAR(255)
DETERMINISTIC
BEGIN
    SET str = REPLACE(str, 'á', 'a');
    SET str = REPLACE(str, 'à', 'a');
    SET str = REPLACE(str, 'ả', 'a');
    SET str = REPLACE(str, 'ã', 'a');
    SET str = REPLACE(str, 'ạ', 'a');
    SET str = REPLACE(str, 'ấ', 'a');
    SET str = REPLACE(str, 'ầ', 'a');
    SET str = REPLACE(str, 'ẩ', 'a');
    SET str = REPLACE(str, 'ẫ', 'a');
    SET str = REPLACE(str, 'ậ', 'a');
    SET str = REPLACE(str, 'ắ', 'a');
    SET str = REPLACE(str, 'ằ', 'a');
    SET str = REPLACE(str, 'ẳ', 'a');
    SET str = REPLACE(str, 'ẵ', 'a');
    SET str = REPLACE(str, 'ặ', 'a');
    SET str = REPLACE(str, 'é', 'e');
    SET str = REPLACE(str, 'è', 'e');
    SET str = REPLACE(str, 'ẻ', 'e');
    SET str = REPLACE(str, 'ẽ', 'e');
    SET str = REPLACE(str, 'ẹ', 'e');
    SET str = REPLACE(str, 'ê', 'e');
    SET str = REPLACE(str, 'í', 'i');
    SET str = REPLACE(str, 'ì', 'i');
    SET str = REPLACE(str, 'ỉ', 'i');
    SET str = REPLACE(str, 'ĩ', 'i');
    SET str = REPLACE(str, 'ị', 'i');
    SET str = REPLACE(str, 'ó', 'o');
    SET str = REPLACE(str, 'ò', 'o');
    SET str = REPLACE(str, 'ỏ', 'o');
    SET str = REPLACE(str, 'õ', 'o');
    SET str = REPLACE(str, 'ọ', 'o');
    SET str = REPLACE(str, 'ô', 'o');
    SET str = REPLACE(str, 'ơ', 'o');
    SET str = REPLACE(str, 'ú', 'u');
    SET str = REPLACE(str, 'ù', 'u');
    SET str = REPLACE(str, 'ủ', 'u');
    SET str = REPLACE(str, 'ũ', 'u');
    SET str = REPLACE(str, 'ụ', 'u');
    SET str = REPLACE(str, 'ư', 'u');
    SET str = REPLACE(str, 'Đ', 'D');
    SET str = REPLACE(str, 'đ', 'd');

    RETURN str;
END;
