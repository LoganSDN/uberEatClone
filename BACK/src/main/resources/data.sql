INSERT INTO roles (name)
SELECT 'ROLE_ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_ADMIN');

INSERT INTO roles (name)
SELECT 'ROLE_CUSTOMER'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_CUSTOMER');

INSERT INTO roles (name)
SELECT 'ROLE_DRIVER'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_DRIVER');

INSERT INTO roles (name)
SELECT 'ROLE_OWNER'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_OWNER');

DROP TABLE IF EXISTS driver_view;

CREATE OR REPLACE VIEW driver_view AS
SELECT u.id,
       u.first_name,
       u.last_name,
       u.status,
       u.phone_number,
       u.socket_id,
       a.street,
       a.city,
       a.zip,
       a.lat,
       a.lng
FROM users u
         JOIN users_roles ur ON u.id = ur.user_id
         JOIN roles r ON ur.role_id = r.id
         JOIN address a ON u.id = a.address_user_id
WHERE r.name::text = 'ROLE_DRIVER'::text
  AND u.status::text = 'WORKING'::text;