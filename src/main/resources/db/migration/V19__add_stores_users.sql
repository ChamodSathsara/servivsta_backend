CREATE TABLE stores_user (
    stores_user_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    stores_user_name VARCHAR(150) NOT NULL,
    mobile_number VARCHAR(20),
    email VARCHAR(150) NOT NULL,
    PRIMARY KEY (stores_user_id),
    CONSTRAINT uk_stores_user_user UNIQUE (user_id),
    CONSTRAINT uk_stores_user_email UNIQUE (email),
    CONSTRAINT fk_stores_user_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);
