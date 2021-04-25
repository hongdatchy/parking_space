CREATE TABLE field(
	id int NOT NULL AUTO_INCREMENT,
    name varchar(120) not null,
    latitude varchar(120) not null,
    longitude varchar(120) not null,
    address varchar(120) not null,
    image varchar(200) default null,
    price varchar(45) not null,
    openstatus varchar(1) not null,
    space decimal(10,1) not null,
    details longtext not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE gateway(
	id int NOT NULL AUTO_INCREMENT,
	field_id int not null,
    address_gateway varchar(20) not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`),
    CONSTRAINT FOREIGN KEY (`field_id`) REFERENCES `field` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE slot(
	id int NOT NULL AUTO_INCREMENT,
    field_id int not null,
    status_detector bit(1),
    status_cam bit(1),
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`),
    CONSTRAINT FOREIGN KEY (`field_id`) REFERENCES `field` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE detector(
	id int NOT NULL,
    address_detector varchar(20) not null,
    slot_id int not null,
    gateway_id int not null,
    battery_level varchar(20) not null,
    communication_level varchar(20) not null,
    last_time_update date not null,
    last_time_setup date not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`),
    CONSTRAINT FOREIGN KEY (`slot_id`) REFERENCES `slot` (`id`),
    CONSTRAINT FOREIGN KEY (`gateway_id`) REFERENCES `gateway` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE user(
	id int NOT NULL AUTO_INCREMENT,
    pass varchar(256) not null,
    id_number int not null,
    email varchar(20) not null,
	equipment varchar(20) not null,
    address varchar(120) not null,
    phone varchar(45) not null,
    last_time_access date not null,
	image varchar(200) default null,
    sex varchar(1) not null,
    birth date not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE tag(
	id int NOT NULL AUTO_INCREMENT,
    user_id int not null,
	time_car_in date,
    time_car_out date,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`),
    CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE manager(
	id int NOT NULL AUTO_INCREMENT,
    email varchar(20) not null,
    pass varchar(256) not null,
    last_time_access date,
    acp bit(1) not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE contract(
	id int NOT NULL AUTO_INCREMENT,
    field_id int not null,
    user_id int not null,
    time_car_in date,
    time_car_out date,
    time_in_book date not null,
    time_out_book date not null,
    car_number varchar(45) not null,
    dt_create date not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`),
    CONSTRAINT FOREIGN KEY (`field_id`) REFERENCES `field` (`id`),
    CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE manager_field(
	id int NOT NULL AUTO_INCREMENT,
    field_id int not null,
    manager_id int not null,
    last_time_setup date not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`),
	CONSTRAINT FOREIGN KEY (`field_id`) REFERENCES `field` (`id`),
    CONSTRAINT FOREIGN KEY (`manager_id`) REFERENCES `manager` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE admin(
	id int NOT NULL AUTO_INCREMENT,
    email varchar(20) not null,
    pass varchar(256) not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE black_list(
	id int NOT NULL AUTO_INCREMENT,
    token varchar(256) not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE package(
	id int not null AUTO_INCREMENT,
	packet_number varchar(20) not null,
    id_node varchar(20) not null,
	battery_level varchar(20) not null,
    node_address varchar(20) not null,
    state bit(1) not null,
    communication_level varchar(20) not null,
    time varchar(20) not null,
	location varchar(20) not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE data_cam_and_detector(
	id int not null AUTO_INCREMENT,
	slot_id int not null,
    status_detector bit(1),
    status_cam bit(1),
    time date not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE verify_table(
	code varchar(20) not null,
	id int NOT NULL AUTO_INCREMENT,
    pass varchar(256) not null,
    id_number int not null,
    email varchar(20) not null,
	equipment varchar(20) not null,
    address varchar(120) not null,
    phone varchar(45) not null,
    last_time_access date not null,
	image varchar(200) default null,
    sex varchar(1) not null,
    birth date not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `parking_space_2021`.`admin` (`id`, `email`, `pass`) VALUES ('1', 'admin@gmail.com', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918');

INSERT INTO `field` VALUES ('3', '17 Phùng Hưng, Hà Đông - Hà Nội', 'Hà Nội', '', '20.960377427559497', '105.79658800934396', '50000', 'O', 15, 'id, image, address, lat, lon, location, price, distance, openstatus, space, details');
INSERT INTO `field` VALUES ('4', 'Điểm đỗ xe sau toà nhà Viglacera 671 Hoàng Hoa Thám', 'Hà Nội', '', '21.045006923839967', '105.80782829707834', '50000', 'O', 15, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Bibendum est ultricies integer quis. Iaculis urna id volutpat lacus laoreet. Mauris vitae ultricies leo integer malesuada. Ac odio tempor orci dapibus ultrices in. Egestas diam in arcu cursus euismod. Dictum fusce ut');
INSERT INTO `field` VALUES ('5', 'Điểm đỗ xe Nguyễn Đình Chiểu', 'Hà Nội', '', '21.01916918502266', '105.84623814002637', '50000', 'O', 16, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Bibendum est ultricies integer quis. Iaculis urna id volutpat lacus laoreet. Mauris vitae ultricies leo integer malesuada. Ac odio tempor orci dapibus ultrices in. Egestas diam in arcu cursus euismod. Dictum fusce ut');
INSERT INTO `field` VALUES ('6', 'Điểm đỗ xe Hà Thành plaza', 'Hà Nội', '', '21.024340036520023', '105.82015308050798', '50000', 'O', 2, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Bibendum est ultricies integer quis. Iaculis urna id volutpat lacus laoreet. Mauris vitae ultricies leo integer malesuada. Ac odio tempor orci dapibus ultrices in. Egestas diam in arcu cursus euismod. Dictum fusce ut');
INSERT INTO `field` VALUES ('7', 'Điểm đỗ xe Đại học Y Hà Nội, số 1 Tôn Thất Tùng', 'Hà Nội', '', '21.03347234978806', '105.83061729584946', '50000', 'O', 45, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Bibendum est ultricies integer quis. Iaculis urna id volutpat lacus laoreet. Mauris vitae ultricies leo integer malesuada. Ac odio tempor orci dapibus ultrices in. Egestas diam in arcu cursus euismod. Dictum fusce ut');
INSERT INTO `field` VALUES ('8', 'Điểm đỗ xe Khách sạn Phụ nữ – Số 10 Thụy Khuê', 'Hà Nội', '', '21.048472303387822', '105.81253402653292', '50000', 'O', 15, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Bibendum est ultricies integer quis. Iaculis urna id volutpat lacus laoreet. Mauris vitae ultricies leo integer malesuada. Ac odio tempor orci dapibus ultrices in. Egestas diam in arcu cursus euismod. Dictum fusce ut');
INSERT INTO `field` VALUES ('9', 'Điểm đô xe Tòa nhà Syrena', 'Hà Nội', '', '21.012966931751308', '105.82812847071014', '50000', 'O', 35, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Bibendum est ultricies integer quis. Iaculis urna id volutpat lacus laoreet. Mauris vitae ultricies leo integer malesuada. Ac odio tempor orci dapibus ultrices in. Egestas diam in arcu cursus euismod. Dictum fusce ut');
INSERT INTO `field` VALUES ('10', 'Điểm đỗ xe Khách sạn Sheraton', 'Hà Nội', '', '21.00897946128564', '105.83171034187457', '50000', 'O', 34, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Bibendum est ultricies integer quis. Iaculis urna id volutpat lacus laoreet. Mauris vitae ultricies leo integer malesuada. Ac odio tempor orci dapibus ultrices in. Egestas diam in arcu cursus euismod. Dictum fusce ut');
INSERT INTO `field` VALUES ('11', 'Điểm đỗ xe Văn Phú', 'Hà Nội', '', '21.003471770161585', '105.7658958839578', '50000', 'O', 12, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Bibendum est ultricies integer quis. Iaculis urna id volutpat lacus laoreet. Mauris vitae ultricies leo integer malesuada. Ac odio tempor orci dapibus ultrices in. Egestas diam in arcu cursus euismod. Dictum fusce ut');
INSERT INTO `field` VALUES ('12', 'Điểm đỗ xe Quang Trung, Ngã 4 Trần hưng đạo đến Tràng thi, 24/24h', 'Hà Nội', '', '21.046715461089097', '105.85934545536792', '50000', 'O', 20, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Bibendum est ultricies integer quis. Iaculis urna id volutpat lacus laoreet. Mauris vitae ultricies leo integer malesuada. Ac odio tempor orci dapibus ultrices in. Egestas diam in arcu cursus euismod. Dictum fusce ut');
INSERT INTO `field` VALUES ('13', 'Điểm đỗ xe nhà hát lớn, Phố Cổ Tân', 'Hà Nội', '', '21.064494902781068', '105.85787149584964', '50000', 'O', 21, '. Chỉ trông xe đến 10h đêm.');
INSERT INTO `field` VALUES ('14', 'Điểm đỗ xe Vạn Phúc, Ba Đình', 'Hà Nội', '', '21.060184939684095', '105.81693711119136', '50000', 'O', 42, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Bibendum est ultricies integer quis. Iaculis urna id volutpat lacus laoreet. Mauris vitae ultricies leo integer malesuada. Ac odio tempor orci dapibus ultrices in. Egestas diam in arcu cursus euismod. Dictum fusce ut');

-- 2 cai ben duoi sai

-- INSERT INTO `user` VALUES ('1', '0368776688', '36f583dd16f4e1e201eb1e6f6d8e35a2ccb3bbe2658de46b4ffae7b0e9ed872e', '2021-04-05 11:30:05', 'Hà Nội', 'thanh.lv@techplus.com.vn', NULL, 'F', '1997-10-10');
-- INSERT INTO `user` VALUES ('2', '0367887788', '36f583dd16f4e1e201eb1e6f6d8e35a2ccb3bbe2658de46b4ffae7b0e9ed872e', '2021-05-05 12:00:00', 'Hà Nội', 'thanh.lv@techplus.com.vn', NULL, 'F', '1997-10-10');

-- INSERT INTO `tbl_dsdatcho` VALUES ('1', 'Điểm đỗ xe Hà Đông', '17 Phùng Hưng, Hà Đông - Hà Nội', 'V', '29D-99999', '2021-04-04 11:30:00', '2021-04-03 11:30:00', '2021-04-05 11:30:00');
-- INSERT INTO `tbl_dsdatcho` VALUES ('2', 'Điểm đỗ xe giáp bát', '17 Phùng Hưng, Hà Đông - Hà Nội', 'Y', '29D-99999', '2021-04-04 11:30:00', '2021-04-04 11:30:00', '2021-04-06 11:30:00');
-- INSERT INTO `tbl_dsdatcho` VALUES ('3', 'Điểm đỗ xe Hoàn Kiếm 2', 'Cửa Đông, Hoàn Kiếm - Hà Nội', 'C', '29D-99999', '2021-04-04 11:30:00', '2021-04-05 11:30:00', '2021-04-07 11:30:00');
-- INSERT INTO `tbl_dsdatcho` VALUES ('4', 'Điểm đỗ xe Hoàn Kiếm', 'Trần Hưng Đạo, Hoàn Kiếm - Hà Nội', 'R', '29D-99999', '2021-04-04 11:30:00', '2021-04-06 11:30:00', '2021-04-08 11:30:00');
