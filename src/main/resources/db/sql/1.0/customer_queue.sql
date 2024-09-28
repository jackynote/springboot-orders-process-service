CREATE TABLE customer_queue (
    id UUID PRIMARY KEY,
    queue_id UUID NOT NULL,
    customer_id UUID NOT NULL,
    order_id UUID NOT NULL,
    waiting_since TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version INT
);

ALTER TABLE customer_queue ADD CONSTRAINT fk_customer_queue_queue FOREIGN KEY (queue_id) REFERENCES queues (id);
ALTER TABLE customer_queue ADD CONSTRAINT fk_customer_queue_customer FOREIGN KEY (customer_id) REFERENCES c_users (id);
ALTER TABLE customer_queue ADD CONSTRAINT fk_customer_queue_order FOREIGN KEY (order_id) REFERENCES orders (id);
