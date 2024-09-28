-- Liquibase changeset for orders table
CREATE TABLE orders (
        id UUID PRIMARY KEY,
        order_number VARCHAR(255) NOT NULL UNIQUE,
        customer_id UUID NOT NULL,
        status VARCHAR(50),
        franchise_id UUID NOT NULL,
        queue_id UUID,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        version INT
);

-- Foreign keys for orders table
ALTER TABLE orders ADD CONSTRAINT fk_orders_customer FOREIGN KEY (customer_id) REFERENCES c_users (id);
ALTER TABLE orders ADD CONSTRAINT fk_orders_franchise FOREIGN KEY (franchise_id) REFERENCES franchises (id);
ALTER TABLE orders ADD CONSTRAINT fk_orders_queue FOREIGN KEY (queue_id) REFERENCES queues (id);
