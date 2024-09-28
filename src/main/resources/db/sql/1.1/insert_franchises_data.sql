-- First, retrieve the merchant_id from the first row
WITH first_merchant AS (
    SELECT id
    FROM merchants
    ORDER BY id
    LIMIT 1
)


INSERT INTO franchises (id, location, name, contact_details, opening_time, closing_time, number_of_queues, max_queue_size, merchant_id, latitude, longitude)
SELECT
    uuid_generate_v4(), 'New York City, USA', 'Empire Eats', 'contact@empireeats.com', '09:00:00'::time, '21:00:00'::time, 5, 50, (SELECT id FROM first_merchant), 40.712776, -74.005974
UNION ALL
SELECT
    uuid_generate_v4(), 'Los Angeles, USA', 'Sunset Bites', 'contact@sunsetbites.com', '10:00:00'::time, '22:00:00'::time, 4, 40, (SELECT id FROM first_merchant), 34.052235, -118.243683
UNION ALL
SELECT
    uuid_generate_v4(), 'London, UK', 'The British Feast', 'contact@britishfeast.co.uk', '08:00:00'::time, '20:00:00'::time, 3, 30, (SELECT id FROM first_merchant), 51.507351, -0.127758
UNION ALL
SELECT
    uuid_generate_v4(), 'Paris, France', 'Parisian Delights', 'contact@parisiandelights.fr', '09:00:00'::time, '21:00:00'::time, 5, 50, (SELECT id FROM first_merchant), 48.856613, 2.352222
UNION ALL
SELECT
    uuid_generate_v4(), 'Tokyo, Japan', 'Tokyo Treats', 'contact@tokyotreats.jp', '10:00:00'::time, '22:00:00'::time, 4, 40, (SELECT id FROM first_merchant), 35.689487, 139.691711
UNION ALL
SELECT
    uuid_generate_v4(), 'Moscow, Russia', 'Moscow Munchies', 'contact@moscowmunchies.ru', '08:00:00'::time, '20:00:00'::time, 6, 60, (SELECT id FROM first_merchant), 55.755825, 37.617298
UNION ALL
SELECT
    uuid_generate_v4(), 'Madrid, Spain', 'Madrid Morsels', 'contact@madridmorsels.es', '09:00:00'::time, '21:00:00'::time, 5, 50, (SELECT id FROM first_merchant), 40.416775, -3.703790
UNION ALL
SELECT
    uuid_generate_v4(), 'Berlin, Germany', 'Berlin Bites', 'contact@berlinbites.de', '10:00:00'::time, '22:00:00'::time, 4, 40, (SELECT id FROM first_merchant), 52.520008, 13.404954
UNION ALL
SELECT
    uuid_generate_v4(), 'Sydney, Australia', 'Sydney Savories', 'contact@sydneysavories.au', '08:00:00'::time, '20:00:00'::time, 3, 30, (SELECT id FROM first_merchant), -33.868820, 151.209290
UNION ALL
SELECT
    uuid_generate_v4(), 'Mexico City, Mexico', 'Mexico Munchies', 'contact@mexicomunchies.mx', '09:00:00'::time, '21:00:00'::time, 5, 50, (SELECT id FROM first_merchant), 19.432608, -99.133209
UNION ALL
SELECT
    uuid_generate_v4(), 'San Francisco, USA', 'Bay Area Bites', 'contact@bayareabites.com', '10:00:00'::time, '22:00:00'::time, 6, 60, (SELECT id FROM first_merchant), 37.774929, -122.419418
UNION ALL
SELECT
    uuid_generate_v4(), 'Delhi, India', 'Delhi Delights', 'contact@delhidelights.in', '08:00:00'::time, '20:00:00'::time, 4, 40, (SELECT id FROM first_merchant), 28.704060, 77.102493
UNION ALL
SELECT
    uuid_generate_v4(), 'Shanghai, China', 'Shanghai Eats', 'contact@shanghaieats.cn', '09:00:00'::time, '21:00:00'::time, 5, 50, (SELECT id FROM first_merchant), 31.230391, 121.473701
UNION ALL
SELECT
    uuid_generate_v4(), 'Rome, Italy', 'Roman Feast', 'contact@romanfeast.it', '08:00:00'::time, '20:00:00'::time, 4, 40, (SELECT id FROM first_merchant), 41.902782, 12.496365
UNION ALL
SELECT
    uuid_generate_v4(), 'Singapore', 'Singapore Savory', 'contact@singapore-savory.sg', '10:00:00'::time, '22:00:00'::time, 6, 60, (SELECT id FROM first_merchant), 1.352083, 103.819839
UNION ALL
SELECT
    uuid_generate_v4(), 'São Paulo, Brazil', 'Paulista Plates', 'contact@paulistaplates.br', '09:00:00'::time, '21:00:00'::time, 5, 50, (SELECT id FROM first_merchant), -23.550520, -46.633308
UNION ALL
SELECT
    uuid_generate_v4(), 'Buenos Aires, Argentina', 'Buenos Bites', 'contact@buenosbites.ar', '08:00:00'::time, '20:00:00'::time, 4, 40, (SELECT id FROM first_merchant), -34.603722, -58.381592
UNION ALL
SELECT
    uuid_generate_v4(), 'Toronto, Canada', 'Toronto Tastes', 'contact@torontotastes.ca', '09:00:00'::time, '21:00:00'::time, 5, 50, (SELECT id FROM first_merchant), 43.651070, -79.347015;

-- Insert franchises around New York City
INSERT INTO franchises (id, location, name, contact_details, opening_time, closing_time, number_of_queues, max_queue_size, merchant_id, latitude, longitude)
SELECT
    uuid_generate_v4(), 'Jersey City, NJ', 'Hudson Diner', 'contact@hudsondiner.com', '07:00:00'::time, '22:00:00'::time, 4, 40, (SELECT id FROM merchants LIMIT 1), 40.717754, -74.043476
UNION ALL
SELECT
    uuid_generate_v4(), 'Hoboken, NJ', 'Hoboken Eats', 'contact@hobokeneats.com', '08:00:00'::time, '23:00:00'::time, 3, 30, (SELECT id FROM merchants LIMIT 1), 40.745195, -74.032362
UNION ALL
SELECT
    uuid_generate_v4(), 'Brooklyn, NY', 'Brooklyn Bites', 'contact@brooklynbites.com', '09:00:00'::time, '21:00:00'::time, 5, 50, (SELECT id FROM merchants LIMIT 1), 40.678178, -73.944158
UNION ALL
SELECT
    uuid_generate_v4(), 'Queens, NY', 'Queens Delights', 'contact@queensdelights.com', '10:00:00'::time, '22:00:00'::time, 4, 40, (SELECT id FROM merchants LIMIT 1), 40.728224, -73.794852
UNION ALL
SELECT
    uuid_generate_v4(), 'The Bronx, NY', 'Bronx Grub', 'contact@bronxgrub.com', '08:00:00'::time, '20:00:00'::time, 6, 60, (SELECT id FROM merchants LIMIT 1), 40.844782, -73.865433
UNION ALL
SELECT
    uuid_generate_v4(), 'Newark, NJ', 'Newark Nibbles', 'contact@newarknibbles.com', '09:00:00'::time, '21:00:00'::time, 5, 50, (SELECT id FROM merchants LIMIT 1), 40.735657, -74.172367
UNION ALL
SELECT
    uuid_generate_v4(), 'White Plains, NY', 'White Plains Plates', 'contact@whiteplainsplates.com', '10:00:00'::time, '22:00:00'::time, 4, 40, (SELECT id FROM merchants LIMIT 1), 41.033986, -73.762909
UNION ALL
SELECT
    uuid_generate_v4(), 'Yonkers, NY', 'Yonkers Yum', 'contact@yonkersyum.com', '08:00:00'::time, '20:00:00'::time, 3, 30, (SELECT id FROM merchants LIMIT 1), 40.931209, -73.898746
UNION ALL
SELECT
    uuid_generate_v4(), 'Stamford, CT', 'Stamford Savory', 'contact@stamfordsavory.com', '09:00:00'::time, '21:00:00'::time, 5, 50, (SELECT id FROM merchants LIMIT 1), 41.053430, -73.538734
UNION ALL
SELECT
    uuid_generate_v4(), 'Paramus, NJ', 'Paramus Platter', 'contact@paramusplatter.com', '07:00:00'::time, '22:00:00'::time, 4, 40, (SELECT id FROM merchants LIMIT 1), 40.925000, -74.070220
UNION ALL
SELECT
    uuid_generate_v4(), 'Hackensack, NJ', 'Hackensack Haven', 'contact@hackensackhaven.com', '08:00:00'::time, '23:00:00'::time, 5, 50, (SELECT id FROM merchants LIMIT 1), 40.885931, -74.043783
UNION ALL
SELECT
    uuid_generate_v4(), 'Scarsdale, NY', 'Scarsdale Snacks', 'contact@scarsdalesnacks.com', '10:00:00'::time, '22:00:00'::time, 3, 30, (SELECT id FROM merchants LIMIT 1), 41.004570, -73.792500
UNION ALL
SELECT
    uuid_generate_v4(), 'Ridgewood, NJ', 'Ridgewood Rations', 'contact@ridgewoodrations.com', '09:00:00'::time, '21:00:00'::time, 4, 40, (SELECT id FROM merchants LIMIT 1), 40.984473, -74.109733
UNION ALL
SELECT
    uuid_generate_v4(), 'Elmwood Park, NJ', 'Elmwood Eats', 'contact@elmwoodeats.com', '08:00:00'::time, '20:00:00'::time, 6, 60, (SELECT id FROM merchants LIMIT 1), 40.905733, -74.104637
UNION ALL
SELECT
    uuid_generate_v4(), 'East Rutherford, NJ', 'Rutherford Rations', 'contact@rutherfordrations.com', '07:00:00'::time, '21:00:00'::time, 5, 50, (SELECT id FROM merchants LIMIT 1), 40.847829, -74.082545
UNION ALL
SELECT
    uuid_generate_v4(), 'Lodi, NJ', 'Lodi Larder', 'contact@lodilarder.com', '09:00:00'::time, '22:00:00'::time, 4, 40, (SELECT id FROM merchants LIMIT 1), 40.869629, -74.107788
UNION ALL
SELECT
    uuid_generate_v4(), 'Clifton, NJ', 'Clifton Cravings', 'contact@cliftoncravings.com', '10:00:00'::time, '23:00:00'::time, 6, 60, (SELECT id FROM merchants LIMIT 1), 40.858430, -74.163759
UNION ALL
SELECT
    uuid_generate_v4(), 'Teaneck, NJ', 'Teaneck Treats', 'contact@teanecktreats.com', '08:00:00'::time, '20:00:00'::time, 3, 30, (SELECT id FROM merchants LIMIT 1), 40.889069, -74.017395
UNION ALL
SELECT
    uuid_generate_v4(), 'Fort Lee, NJ', 'Fort Lee Fare', 'contact@fortleefare.com', '07:00:00'::time, '22:00:00'::time, 5, 50, (SELECT id FROM merchants LIMIT 1), 40.851111, -73.981666;