ALTER TABLE usuarios
ADD COLUMN is_admin BOOLEAN;

ALTER TABLE usuarios
ADD CONSTRAINT uk_email UNIQUE (email);