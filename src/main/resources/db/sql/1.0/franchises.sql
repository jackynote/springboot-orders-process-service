-- Liquibase changeset for franchises table
CREATE TABLE franchises (
        id UUID PRIMARY KEY,
        location VARCHAR(255) NOT NULL,
        name VARCHAR(255),
        contact_details VARCHAR(255) NOT NULL,
        opening_time TIME NOT NULL,
        closing_time TIME NOT NULL,
        number_of_queues INT NOT NULL,
        max_queue_size INT NOT NULL,
        merchant_id UUID NOT NULL,
        latitude DOUBLE PRECISION,
        longitude DOUBLE PRECISION,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        version INT
);

-- Foreign key for franchises table
ALTER TABLE franchises ADD CONSTRAINT fk_franchise_merchant FOREIGN KEY (merchant_id) REFERENCES merchants (id);
