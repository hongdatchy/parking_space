CREATE TABLE field(
	id int NOT NULL AUTO_INCREMENT,
    position varchar(120) not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE gateway(
	id int NOT NULL AUTO_INCREMENT,
    address_gateway varchar(20) not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE field_gateway(
	id int NOT NULL AUTO_INCREMENT,
    field_id int not null,
    gateway_id int not null,
    last_time_setup datetime not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`),
    CONSTRAINT FOREIGN KEY (`field_id`) REFERENCES `field` (`id`),
    CONSTRAINT FOREIGN KEY (`gateway_id`) REFERENCES `gateway` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE slot(
	id int NOT NULL AUTO_INCREMENT,
    field_id int not null,
    status bit(1) not null,
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
    loracom_level varchar(20) not null,
    operating_mode varchar(20) not null,
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
    tag int not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`)
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

CREATE TABLE invoice(
	id int NOT NULL AUTO_INCREMENT,
    user_id int not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`),
    CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE contract(
	id int NOT NULL AUTO_INCREMENT,
    slot_id int not null,
    invoice_id int not null,
    time_car_in datetime,
    time_car_out datetime,
    time_in_book datetime not null,
    time_out_book datetime not null,
    primary key (id),
    UNIQUE KEY `id_UNIQUE` (`id`),
    CONSTRAINT FOREIGN KEY (`slot_id`) REFERENCES `slot` (`id`),
    CONSTRAINT FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`)
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

CREATE TABLE verify_table(
	mail int NOT NULL,
    pass varchar(255) not null,
    id_number int not null,
    code varchar(20) not null,
    last_time_access datetime,
    equipment varchar(20) not null,
    address varchar(120) not null,
    tag int not null,
    primary key (mail),
    UNIQUE KEY `id_UNIQUE` (`mail`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
