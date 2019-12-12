INSERT INTO users(name, password, role)
VALUES
('山田太郎', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_ADMIN'),
('鈴木一郎', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_GENERAL'),
('田中花子', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_GENERAL'),
('大空翼', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_GENERAL'),
('孫悟空', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_GENERAL'),
('石川五右衛門', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_GENERAL'),
('次元大助', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_GENERAL');

INSERT INTO mailaddress(mail_addr, mail_kind, user_id)
VALUES
('test@jitaku.com', '自宅PC', 1),
('mail@office.com', '会社PC', 1),
('office@office.com', '会社PC', 2),
('jitaku@office.com', '自宅PC', 2),
('home@jitaku.com', '自宅PC', 3),
('smart@smart.co.jp', '携帯', 4),
('mobile@mobile.ne.jp', '携帯', 5),
('kaisya@kaisya.com', '会社PC', 6),
('exam@example.co.jp', '会社PC', 7);

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

INSERT INTO customer(name, kana, birthday, person)
VALUES
('田中一郎', 'タナカイチロウ', '1970-01-01', '個人'),
('株式会社法人', 'カ）ホウジン', '1964-11-11', '法人'),
('佐藤二郎', 'サトウジロウ', '1953-06-03', '個人'),
('合同会社あいうえ', 'ドウ）アイウエ', '2014-05-12', '法人'),
('山田三郎', 'ヤマダサブロウ', '1992-05-01', '個人'),
('有限会社ゆうげん', 'ユ）ユウゲン', '1934-09-21', '法人');

INSERT INTO customer_mail(mail_addr, mail_kind, customer_id)
VALUES
('test@mail.com', '自宅PC', 1),
('office@mail.com', '会社PC', 1),
('test@office.com', '会社PC', 2),
('info@office.com', 'info', 2),
('home@jitaku.com', '自宅PC', 3),
('company@company.co.jp', '会社代表', 4),
('yamada@kinmu.co.jp', '会社PC', 5),
('yamada@jitaku.com', '自宅PC', 5),
('sample@example.co.jp', '会社PC', 6);


INSERT INTO customer_tel(phone_kind, phone_number)
VALUES
('個人携帯', '07012345678'),
('会社携帯', '08098765432'),
('携帯', '09012345678'),
('会社', '0123456789'),
('携帯', '08088887777'),
('自宅', '0234560987'),
('携帯', '07077772222'),
('会社', '0333334444');


INSERT INTO customer_telephone_list
VALUES
(1,1),
(1,2),
(1,3),
(2,4),
(3,5),
(5,6),
(6,7),
(6,8);

INSERT INTO tokisyo(tokisyo_code, tokisyo_name)
VALUES
(1200, '大阪法務局'),
(1310, '京都地方法務局伏見出張所');
