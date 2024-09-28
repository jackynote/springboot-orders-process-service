-- Liquibase changeset for merchants table
CREATE TABLE merchants (
   id UUID PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   version INT
);
