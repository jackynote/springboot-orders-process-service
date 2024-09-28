-- Liquibase changeset for customer_scores table
CREATE TABLE customer_scores (
     id UUID PRIMARY KEY,
     customer_id UUID NOT NULL,
     franchise_id UUID NOT NULL,
     score INT NOT NULL,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     version INT
);

-- Foreign keys for customer_scores table
ALTER TABLE customer_scores ADD CONSTRAINT fk_customer_scores_customer FOREIGN KEY (customer_id) REFERENCES c_users (id);
ALTER TABLE customer_scores ADD CONSTRAINT fk_customer_scores_franchise FOREIGN KEY (franchise_id) REFERENCES franchises (id);
