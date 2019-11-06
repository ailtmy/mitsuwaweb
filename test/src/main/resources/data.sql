INSERT INTO users(name, password, role)
VALUES
('山田太郎', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_ADMIN'),
('鈴木一郎', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_GENERAL'),
('田中花子', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_GENERAL'),
('大空翼', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_GENERAL'),
('孫悟空', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_GENERAL'),
('石川五右衛門', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_GENERAL'),
('次元大助', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_GENERAL');

INSERT INTO telephone(phone_kind, phone_number)
VALUES
('自宅', '0123456789'),
('会社', '0987654321'),
('携帯', '09012345678'),
('会社', '0123456789'),
('携帯', '08088887777'),
('自宅', '0234560987'),
('携帯', '07077772222'),
('会社', '0333334444');

INSERT INTO users_telephone_list
VALUES
(1,1),
(1,2),
(1,3),
(2,4),
(3,5),
(5,6),
(6,7),
(7,8);
