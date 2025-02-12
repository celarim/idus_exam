DROP TABLE IF EXISTS orders, user;

CREATE TABLE IF NOT EXISTS user(
    idx             BIGINT          NOT NULL    AUTO_INCREMENT  PRIMARY KEY,
    name            VARCHAR(30)     NOT NULL,
    email           VARCHAR(100)    NOT NULL,
    password        VARCHAR(256)    NOT NULL,
    phone_number    VARCHAR(20)     NOT NULL,
    nickname        VARCHAR(30)     NOT NULL,
    gender          VARCHAR(30)     NULL,
    role            VARCHAR(50)     NOT NULL
);

CREATE TABLE IF NOT EXISTS orders(
    idx             BIGINT      NOT NULL    AUTO_INCREMENT  PRIMARY KEY,
    order_number    VARCHAR(20) NOT NULL    UNIQUE,
    product_name    VARCHAR(50) NOT NULL,
    order_date      DATETIME    NOT NULL,
    user_idx        BIGINT      NOT NULL,
    FOREIGN KEY (user_idx) REFERENCES user(idx)
);


INSERT INTO user(name, email, password, phone_number, nickname, gender, role) VALUE('test01','test01@test.com','$2a$10$ROOCNOLUPlKMeM4gzRKERurtDgEwtF0qOplWucU2M043sXfNHkfm2','01012345678','test','MALE','ROLE_USER');
INSERT INTO user(name, email, password, phone_number, nickname, gender, role) VALUE('test02','test02@test.com','$2a$10$ROOCNOLUPlKMeM4gzRKERurtDgEwtF0qOplWucU2M043sXfNHkfm2','01012345678','test','MALE','ROLE_USER');
INSERT INTO user(name, email, password, phone_number, nickname, gender, role) VALUE('test03','test03@test.com','$2a$10$ROOCNOLUPlKMeM4gzRKERurtDgEwtF0qOplWucU2M043sXfNHkfm2','01012345678','test','MALE','ROLE_USER');
INSERT INTO user(name, email, password, phone_number, nickname, gender, role) VALUE('test04','test04@test.com','$2a$10$ROOCNOLUPlKMeM4gzRKERurtDgEwtF0qOplWucU2M043sXfNHkfm2','01012345678','test','MALE','ROLE_USER');
INSERT INTO user(name, email, password, phone_number, nickname, gender, role) VALUE('test05','test05@test.com','$2a$10$ROOCNOLUPlKMeM4gzRKERurtDgEwtF0qOplWucU2M043sXfNHkfm2','01012345678','test','MALE','ROLE_USER');
INSERT INTO user(name, email, password, phone_number, nickname, gender, role) VALUE('test06','test06@test.com','$2a$10$ROOCNOLUPlKMeM4gzRKERurtDgEwtF0qOplWucU2M043sXfNHkfm2','01012345678','test','MALE','ROLE_USER');


INSERT orders(order_date, order_number, product_name, user_idx) VALUE ('2025-02-10 00:00:00', 'ABCDEFGHIJ11', 'product1', 1);
INSERT orders(order_date, order_number, product_name, user_idx) VALUE ('2025-02-11 00:00:00', 'ABCDEFGHIJ22', 'product2', 1);
INSERT orders(order_date, order_number, product_name, user_idx) VALUE ('2025-02-12 00:00:00', 'ABCDEFGHIJ33', 'product3', 1);
INSERT orders(order_date, order_number, product_name, user_idx) VALUE ('2025-02-13 00:00:00', 'ABCDEFGHIJ44', 'product4', 2);
INSERT orders(order_date, order_number, product_name, user_idx) VALUE ('2025-02-14 00:00:00', 'ABCDEFGHIJ55', 'product5', 2);
INSERT orders(order_date, order_number, product_name, user_idx) VALUE ('2025-02-15 00:00:00', 'ABCDEFGHIJ66', 'product6', 3);
INSERT orders(order_date, order_number, product_name, user_idx) VALUE ('2025-02-16 00:00:00', 'ABCDEFGHIJ77', 'product7', 3);
INSERT orders(order_date, order_number, product_name, user_idx) VALUE ('2025-02-17 00:00:00', 'ABCDEFGHIJ88', 'product8', 3);