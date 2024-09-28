-- Liquibase changeset for menu_items table
CREATE TABLE menu_items (
        id UUID PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        price DOUBLE PRECISION NOT NULL,
        currency VARCHAR(10) NOT NULL,
        is_available BOOLEAN NOT NULL,
        franchise_id UUID NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        version INT
);

-- Foreign key for menu_items table
ALTER TABLE menu_items ADD CONSTRAINT fk_menu_items_franchise FOREIGN KEY (franchise_id) REFERENCES franchises (id);
