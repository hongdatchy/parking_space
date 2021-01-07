CREATE TABLE field(
	id int NOT NULL AUTO_INCREMENT,
    position varchar(120) not null,
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
    last_time_update datetime not null,
    last_time_setup datetime not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`),
    CONSTRAINT FOREIGN KEY (`slot_id`) REFERENCES `slot` (`id`),
    CONSTRAINT FOREIGN KEY (`gateway_id`) REFERENCES `gateway` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE user(
	id int NOT NULL AUTO_INCREMENT,
    pass varchar(255) not null,
    id_number int not null,
    phone varchar(20) not null,
    last_time_access datetime,
    equipment varchar(20) not null,
    address varchar(120) not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE tag(
	id int NOT NULL AUTO_INCREMENT,
    user_id int not null,
	time_car_in datetime,
    time_car_out datetime,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`),
    CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE manager(
	id int NOT NULL AUTO_INCREMENT,
    phone varchar(20) not null,
    pass varchar(256) not null,
    last_time_access datetime,
    acp bit(1) not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE contract(
	id int NOT NULL AUTO_INCREMENT,
    field_id int not null,
    user_id int not null,
    time_car_in datetime,
    time_car_out datetime,
    time_in_book datetime not null,
    time_out_book datetime not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`),
    CONSTRAINT FOREIGN KEY (`field_id`) REFERENCES `field` (`id`),
    CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE manager_field(
	id int NOT NULL AUTO_INCREMENT,
    field_id int not null,
    manager_id int not null,
    last_time_setup datetime not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`),
	CONSTRAINT FOREIGN KEY (`field_id`) REFERENCES `field` (`id`),
    CONSTRAINT FOREIGN KEY (`manager_id`) REFERENCES `manager` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE admin(
	id int NOT NULL AUTO_INCREMENT,
    phone varchar(20) not null,
    pass varchar(256) not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE black_list(
	id int NOT NULL AUTO_INCREMENT,
    token varchar(255) not null,
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
    time datetime not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
