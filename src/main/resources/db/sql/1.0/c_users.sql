-- Liquibase changeset for c_users table
CREATE TABLE c_users (
     id UUID PRIMARY KEY,
     username VARCHAR(255) NOT NULL UNIQUE,
     password VARCHAR(255) NOT NULL,
     first_name VARCHAR(255),
     last_name VARCHAR(255),
     email VARCHAR(255),
     phone VARCHAR(50),
     role VARCHAR(50),
     mobile_number VARCHAR(50),
     address VARCHAR(255),
     merchant_id UUID,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     version INT
);

-- Foreign key for c_users table
ALTER TABLE c_users ADD CONSTRAINT fk_users_merchant FOREIGN KEY (merchant_id) REFERENCES merchants (id);
