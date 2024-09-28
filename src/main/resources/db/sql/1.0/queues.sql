-- Liquibase changeset for queues table
CREATE TABLE queues (
    id UUID PRIMARY KEY,
    franchise_id UUID NOT NULL,
    queue_number INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version INT
);

-- Foreign key for queues table
ALTER TABLE queues ADD CONSTRAINT fk_queues_franchise FOREIGN KEY (franchise_id) REFERENCES franchises (id);
