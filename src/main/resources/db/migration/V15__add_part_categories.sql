CREATE TABLE part_category (
    part_category_id BIGINT NOT NULL AUTO_INCREMENT,
    category_code VARCHAR(40) NOT NULL,
    category_name VARCHAR(150) NOT NULL,
    description VARCHAR(255) NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NULL,
    created_by BIGINT NULL,
    PRIMARY KEY (part_category_id),
    CONSTRAINT uk_part_category_code UNIQUE (category_code),
    CONSTRAINT fk_part_category_created_by FOREIGN KEY (created_by) REFERENCES users (user_id)
);

CREATE TABLE part_category_mapping (
    part_id BIGINT NOT NULL,
    part_category_id BIGINT NOT NULL,
    PRIMARY KEY (part_id, part_category_id),
    CONSTRAINT fk_part_category_mapping_part FOREIGN KEY (part_id)
        REFERENCES part (part_id) ON DELETE CASCADE,
    CONSTRAINT fk_part_category_mapping_category FOREIGN KEY (part_category_id)
        REFERENCES part_category (part_category_id)
);
