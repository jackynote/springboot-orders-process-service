-- Liquibase changeset for order_items table
CREATE TABLE order_items (
     id UUID PRIMARY KEY,
     order_id UUID NOT NULL,
     menu_item_id UUID NOT NULL,
     quantity INT NOT NULL,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     version INT
);

-- Foreign keys for order_items table
ALTER TABLE order_items ADD CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders (id);
ALTER TABLE order_items ADD CONSTRAINT fk_order_items_menu_item FOREIGN KEY (menu_item_id) REFERENCES menu_items (id);
