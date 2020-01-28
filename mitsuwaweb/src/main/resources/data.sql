INSERT INTO users(name, password, role)
VALUES
('山田太郎', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_ADMIN'),
('鈴木一郎', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_GENERAL'),
('田中花子', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_GENERAL'),
('大空翼', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_GENERAL'),
('孫悟空', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_GENERAL'),
('石川五右衛門', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_GENERAL'),
('次元大助', '$2a$10$ydBm8Ee3ns9XRRdgQ3Z/CO8vUYw6dj1IGh4S7iMLcqSU/I21vmjtq', 'ROLE_GENERAL');

INSERT INTO mail_audit(mail_kind, mail_addr)
VALUES
('会社ＰＣ', 'mail@office.jp'),
('自宅ＰＣ', 'mail@jitaku.jp'),
('会社', 'mail@company.co.jp'),
('自宅', 'mail@jitaku.jp'),
('携帯', 'mobile@example.ne.jp'),
('勤務先', 'kunmu@office.co..jp'),
('会社', 'officemail@mail.co.jp'),
('自宅', 'private@jitaku.ne.jp'),
('自宅', 'mail@freemail.ne.jp'),
('スマホ', 'smartphone@example.ne.jp');

INSERT INTO tel_Audit(phone_kind, phone_number)
VALUES
('会社', '0611112222'),
('自宅', '0622223333'),
('個人携帯', '09012345678'),
('会社携帯', '08098765432'),
('会社携帯', '07014785236'),
('会社代表', '0344445555'),
('会社直通', '0699998888'),
('自宅', '0696325874'),
('個人携帯', '09074125896'),
('会社携帯', '07012365478');

INSERT INTO customer(dtype, name, kana, birthday, houjinbango, next_syogyo_toki,  memo)
VALUES
('Person', '個人太郎', 'コジンタロウ', '1970-01-30', null, null, 'テスト備考'),
('Company', '株式会社法人', 'ホウジン', '1955-02-10', '1200010987655', null, '法人テスト備考'),
('Person', '個人次郎', 'コジン次郎', '1981-02-20', null, null, '個人備考'),
('Company', '株式会社ＪＡＶＡ', 'ジャバ', '1955-02-10', '1200010234655', null, 'メモ'),
('Person', '個人三郎', 'コジンサブロウ', '1990-06-30', null, null, 'テスト備考'),
('Company', 'アムロ合同会社', 'アムロ', '2005-12-10', '120003065522', null, '法人テスト備考'),
('Person', '個人司朗', 'コジンシロウ', '1930-08-30', null, null, 'テスト備考'),
('Company', '有限会社エステート', 'エステート', '2010-02-22', '120002023444', null, '法人テスト備考'),
('Person', '個人五郎丸', 'コジンゴロウマル', '1990-11-30', null, null, 'テスト備考'),
('Company', '不動産株式会社', 'フドウサン', '1995-09-10', '1200010237645', null, '法人テスト備考'),
('Person', '個人隼人', 'コジンハヤト', '1987-05-21', null, null, 'テスト備考'),
('Company', 'ＧＤＡＭ株式会社', 'ジーダム', '1975-07-22', '12000107434655', '2022-03-31', '法人テスト備考'),
('Person', '個人花子', 'コジンハナコ', '2010-03-30', null, null, 'テスト備考'),
('Company', '株式会社商業', 'ショウギョウ', '2017-04-01', '1200010987999', '2021-08-01', '法人テスト備考');

INSERT INTO daihyo(katagaki, daitori_id)
VALUES
('代表取締役', 1),
('代表取締役', 5),
('代表社員', 9),
('取締役', 11),
('代表取締役', 11),
('代表取締役', 11),
('代表取締役', 11);

INSERT INTO customer_daihyosya(company_id, daihyosya_id)
VALUES
(2, 1),
(4, 2),
(6, 3),
(8, 4),
(10, 5),
(12, 6),
(14, 7);

INSERT INTO customer_mail_list(customer_id, mail_list_id)
VALUES
(1,1),
(1,2),
(2,3),
(3,4),
(4,5),
(5,6),
(6,7),
(7,8),
(8,9),
(9,10);

INSERT INTO customer_telephone_list(customer_id, telephone_list_id)
VALUES
(1,1),
(1,2),
(2,3),
(3,4),
(4,5),
(5,6),
(6,7),
(7,8),
(8,9),
(9,10);

INSERT INTO honnin_kakunin(customer_id, user_id, memo)
VALUES
(1, 1, '備考'),
(2, 2, '備考'),
(3, 3, '備考'),
(4, 4, '備考'),
(5, 5, '備考'),
(6, 6, '備考'),
(7, 7, '備考'),
(8, 1, '備考'),
(9, 2, '備考'),
(10, 3, '備考');

INSERT INTO hitaimen_torihiki(henshin, ishikakunin, ishikakunin_date, jyuryo, registered_number, sofu, honnin_kakunin_id)
VALUES
('2019-12-01', '電話', '2019-12-02', '2019-12-02', '1111-1111-1111-1111', '2019-11-20', 8),
('2019-12-21', '電話', '2019-12-22', '2019-12-22', '2211-2211-1221-1121', '2019-11-30', 10);

INSERT INTO taimen_torihiki(kakunin_date, kakunin_place, honnin_kakunin_id)
VALUES
('2018-01-10', '事務所', 1),
('2018-03-10', '大阪銀行大阪支店', 2),
('2018-04-11', '奈良銀行奈良支店', 3),
('2018-05-12', '兵庫銀行兵庫支店', 4),
('2018-06-22', '京都銀行京都支店', 5),
('2019-07-01', '和歌山銀行和歌山支店', 6),
('2019-08-11', '滋賀銀行滋賀支店', 7),
('2019-12-12', '架空不動産本店', 9);

INSERT INTO kakunin_syorui(syorui_hakkosya, syorui_kigou, syorui_koufubi, syorui_name, syorui_yukokigen, honnin_kakunin_id)
VALUES
('大阪府公安委員会', '620123948760', '2017-07-30', '運転免許証', '2023-8-31', 1),
('外務大臣', 'TK0123948760', '2012-01-30', 'パスポート', '2022-1-31', 2),
('大阪市', null, '2019-05-31', '個人番号カード', null, 3),
('兵庫県公安委員会', '633301291031', '2019-04-30', '運転免許証', '2024-5-31', 4),
('京都府公安委員会', '6123490129110', '2018-09-30', '運転免許証', '2023-9-30', 5),
('大阪法務局', '整理番号0918726', '2020-01-10', '履歴事項全部証明書', null, 6),
('神戸市', null, '2019-11-22', '個人番号カード', null, 7),
('京都地方法務局嵯峨出張所', '整理番号123456', '2020-01-19', '印鑑証明書', null, 8),
('大阪府公安委員会', '621234567890', '2018-03-20', '運転免許証', '2021-3-31', 9),
('大阪府公安委員会', '629876543211', '2017-06-23', '運転免許証', '2020-7-31', 10);

INSERT INTO customer_address(addr, addr_date, addr_kind, zip_code, honnin_kakunin_id)
VALUES
('大阪市大阪区大阪町一丁目１番１号', null, '住所', '540-0000', 1),
('大阪市中央区中央町二丁目２番２号', null, '住所', '540-0001', 2),
('大阪市北区北町三丁目３番３号', '2019-12-20', '住所', '540-0003', 3),
('大阪市東区東町四丁目４番４号', '2020-01-01', '本店', '540-0004', 4),
('大阪市西区西町五丁目５番５号', null, '居所', '540-0005', 5),
('大阪市南区南町六丁目６番６号', null, '本店', '540-0006', 6),
('東京都東京区東京町一丁目１番１号', null, '住所', '540-0007', 7),
('東京都江戸区江戸町二丁目２番２号', '1878-02-01', '住所', '540-0008', 5),
('東京都千代田区千代田町三丁目３番３号', '2019-10-01', '本店', '540-0009', 8),
('奈良県奈良市なら町1番地の１', null, '住所', '540-0010', 9),
('京都市京都区京都町一丁目１番地', null, '本店', '540-0011', 10);

INSERT INTO shinsei_bukken(dtype, fudosan_bango, biko, kozo, ooaza, shikichiban, shikucyoson, tatemono_bango,
yukamenseki, chiban, chimoku, chiseki, syozai, kaoku_bango, syurui)
VALUES
('Tochi', '1234567890321', null, null, null, null, null, null, null, '１番１', '宅地', '１２３・４５', '大阪市中央区備後町四丁目', null, null),
('Tochi', '2234567890111', null, null, null, null, null, null, null, '２番２', '宅地', '２３４・５６', '大阪市中央区備後町二丁目', null, null),
('Tochi', '3233567890222', null, null, null, null, null, null, null, '３番３', '雑種地', '３４５', '大阪市中央区備後町一丁目', null, null),
('Tochi', '1237897890999', null, null, null, null, null, null, null, '４番４', '公衆用道路', '８・７１', '大阪市中央区備後町三丁目', null, null),
('Tochi', '1234567880888', null, null, null, null, null, null, null, '５番６', '田', '４５６', '大阪市中央区備後町五丁目', null, null),
('Tatemono', '1234567880322', null, '木造スレート葺２階建', null, '１番地１', null, null, '１階　５０．００２階　４０．００', null, null, null, '大阪市中央区備後町四丁目', '１番１', '居宅'),
('Tatemono', '1114567880112', null, '軽量鉄骨造瓦葺２階建', null, '２番地２', null, null, '１階　４０．１０２階　３０．９０', null, null, null, '大阪市中央区備後町二丁目', '２番２の２', '居宅'),
('Tatemono', '3234567880323', null, '木造瓦葺平家建', null, '３番地３', null, null, '１２０．０１', null, null, null, '大阪市中央区備後町三丁目', '３番３', '店舗'),
('IttouTatemono', null, null, null, '四丁目', '４番地４', '大阪市中央区備後町', '備後町ビル', null, null, null, null, null, null, null),
('IttouTatemono', null, null, null, '五丁目', '５番地５', '大阪市中央区備後町', '五番地マンション', null, null, null, null, null, null, null),
('SenyuTatemono', '987654321098', null, '鉄筋コンクリート造１階建', '備後町四丁目', null, '大阪市中央区', '１０１', '１階部分１００・００', null, null, null, null, '４番４の１０１', '事務所'),
('SenyuTatemono', '987654321097', null, '鉄筋コンクリート造１階建', '備後町五丁目', null, '大阪市中央区', '２０１', '２階部分６０・００', null, null, null, null, '２番２の２０１', '居宅'),
('SenyuTatemono', '987654321096', null, '鉄筋コンクリート造１階建', '備後町五丁目', null, '大阪市中央区', '３０１', '３階部分５０・００', null, null, null, null, '２番２の３０１', '居宅');


INSERT INTO tokisyo(tokisyo_code, tokisyo_name)
VALUES
(1200, '大阪法務局'),
(1203, '大阪法務局北出張所'),
(1310, '京都地方法務局伏見出張所');
