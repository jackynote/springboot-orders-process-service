CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO public.merchants
(id, "name", created_at, updated_at, "version")
VALUES(uuid_generate_v4(), 'The Coffee House', now(), now(), 0);

WITH first_merchant AS (
    SELECT id
    FROM merchants
    ORDER BY id
    LIMIT 1
)
INSERT INTO public.c_users
(id, username, "password", first_name, last_name, email, phone, "role", mobile_number, address, merchant_id, created_at, updated_at, "version")
VALUES(uuid_generate_v4(), 'admin', '$2a$10$.CsDN.H/.kCTm5.MA80LqOpB3vnXCK.km5wG1DWfmodai9/qbDsHm', NULL, NULL, NULL, NULL, 'ADMIN', NULL, NULL, (SELECT id FROM first_merchant), now(), now(), 0);
