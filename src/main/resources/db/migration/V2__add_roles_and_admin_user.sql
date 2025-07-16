INSERT INTO role (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

INSERT INTO users (first_name, last_name, phone_number, email, password_hash)
VALUES ('Damon',
        'Salvatore',
        '(970)666-6969',
        'damon@admin.com',
        '$2a$12$QKvNGLE154DwA1qI0EVcleJw3CTu52hLXqVUJMIBkYCT2Ik3iwBC6'); -- password: notdevil

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, role r
WHERE u.email = 'damon@admin.com'
  AND r.name = 'ROLE_ADMIN';