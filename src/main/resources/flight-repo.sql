DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS flight;
DROP TABLE IF EXISTS seat;
DROP TABLE IF EXISTS aircraft;
DROP TABLE IF EXISTS airport;



-- create
-- table airport(аэропорт)
--        code(уникальный код аэропорта)
--        country(страна)
--        city(город)
CREATE TABLE airport
(
    code    char(3) PRIMARY KEY,
    country varchar(255) not null,
    city    varchar(128) not null
);

-- table aircraft(самолет)
--        id(уникальный идентификатор)
--        model(модель самолета unique)
CREATE TABLE aircraft
(
    id    serial primary key,
    model varchar(128) UNIQUE not null
);

-- table seat(место в самолете)
--        aircraft_id(самолет)
--        seat_no(номер места в самолете)
CREATE TABLE seat
(
    aircraft_id serial references aircraft (id),
    seat_no     char(2) not null,
    PRIMARY KEY (aircraft_id, seat_no)
);

-- table fligt(рейс)
--        id(номер рейса не уникальный поэтому нужен id)
--        flight_no(номер рейса)
--        departure_date(дата вылета)
--        departure_airport_code(аэропорт вылета)
--        arrival_date(дата прибытия)
--        arrival_airport_code(аэропорт прибытия)
--        aircraft_id(самолет)
--        status(статус рейса: cancelled, arrived, departed, sheduled)
CREATE TABLE flight
(
    id                     serial primary key,
    flight_no              varchar(16)                       not null,
    departure_date         date                              not null,
    departure_airport_code char(3) references airport (code) not null,
    arrival_date           timestamp                         not null,
    arrival_airport_code   char(3) references airport (code) not null,
    aircraft_id            int references aircraft (id)      not null,
    status                 varchar(64)                       not null
);

-- table ticket(билет на сомолет)
--        id
--        passenger_no(номер паспорта пасажира)
--        passenger_name(имя и фамилия пассажира)
--        flight_id(рейс)
--        seat_no(номер места в самолете flight_id + seat_no - unique)
--        cost(стоимость билета)
CREATE TABLE ticket
(
    id             bigserial primary key,
    passport_no    varchar(32) UNIQUE                 not null,
    passenger_name varchar(128)               not null,
    flight_id      int references flight (id) not null,
    seat_no        varchar(10)                not null,
    cost           numeric(8, 2)              not null
);

CREATE UNIQUE INDEX unique_flight_id_seat_no_idx ON ticket (flight_id, seat_no);
-- flight_id + seat_no

INSERT INTO airport(code, country, city)
VALUES ('MNK', 'Белорусь', 'Минск'),
       ('LDN', 'Англия', 'Лондон'),
       ('MSK', 'Россия', 'Москва'),
       ('BSL', 'Испания', 'Барселона');

INSERT INTO aircraft (model)
VALUES ('Боинг 777-300'),
       ('Боинг 737-300'),
       ('Аэробус A320-200'),
       ('Суперджет-100');

INSERT INTO seat(aircraft_id, seat_no)
SELECT a.id, s.column1
FROM aircraft a
         CROSS JOIN (VALUES ('A1'), ('A2'), ('B1'), ('B2'), ('C1'), ('C2'), ('D1'), ('D2')) as s (column1);


INSERT INTO flight (flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code, aircraft_id,
                    status)
values
    ('MN3002', '2020-06-14T14:30', 'MNK', '2020-06-14T18:07', 'LDN', 1, 'ARRIVED'),
    ('MN3002', '2020-06-16T09:15', 'LDN', '2020-06-16T13:00', 'MNK', 1, 'ARRIVED'),
    ('BC2801', '2020-07-28T23:25', 'MNK', '2020-07-29T02:43', 'LDN', 2, 'ARRIVED'),
    ('BC2801', '2020-08-01T11:00', 'LDN', '2020-08-01T14:15', 'MNK', 2, 'DEPARTED'),
    ('TR3103', '2020-05-03T13:10', 'MSK', '2020-05-03T18:38', 'BSL', 3, 'ARRIVED'),
    ('TR3103', '2020-05-10T07:15', 'BSL', '2020-05-10T012:44', 'MSK', 3, 'CANCELLED'),
    ('CV9827', '2020-09-09T18:00', 'MNK', '2020-09-09T19:15', 'MSK', 4, 'SCHEDULED'),
    ('CV9827', '2020-09-19T08:55', 'MSK', '2020-09-19T10:05', 'MNK', 4, 'SCHEDULED'),
    ('QS8712', '2020-12-18T03:35', 'MNK', '2020-12-18T06:46', 'LDN', 2, 'ARRIVED');

INSERT INTO ticket (passport_no, passenger_name, flight_id, seat_no, cost)
values ('112233', 'Иван Иванов', 1, 'A1', 200),
       ('23234A7', 'Петр Петров', 1, 'B1', 180),
       ('SS988D', 'Светлана Светикова', 1, 'B2', 175),
       ('QYASDE', 'Андрей Андреев', 1, 'C2', 175),
       ('POQ234', 'Иван Кожемякин', 1, 'D1', 160),
       ('898123', 'Олег Рубцов', 1, 'A2', 198),
       ('555321', 'Екатерина Петренко', 2, 'A1', 250),
       ('QO23OO', 'Иван Розмаринов', 2, 'B2', 225),
       ('9883IO', 'Иван Кожемякин', 2, 'C1', 217),
       ('123UI2', 'Андрей Буйнов', 2, 'C2', 227),
       ('SS988S', 'Светлана Светикова', 2, 'D2', 277),
       ('234222', 'Дмитрий Трусцов', 3, 'А1', 300),
       ('AS23PPA', 'Максим Комсомольцев', 3, 'А2', 285),
       ('322349AD', 'Эдуард Щеглов', 3, 'B1', 99),
       ('DL123S', 'Игорь Беркутов', 3, 'B2', 199),
       ('MVM111', 'Алексей Щербин', 3, 'C1', 299),
       ('ZZZ111', 'Денис Колобков', 3, 'C2', 230),
       ('223452', 'Иван Старовойтов', 3, 'D1', 180),
       ('423444', 'Людмила Старовойтова', 3, 'D2', 224),
       ('RT34TR', 'Степан Дор', 4, 'A1', 129),
       ('9996661', 'Анастасия Шепелева', 4, 'A2', 152),
       ('23423', 'Иван Старовойтов', 4, 'B1', 140),
       ('234232', 'Людмила Старовойтова', 4, 'B2', 140),
       ('53422', 'Роман Дронов', 4, 'D2', 109),
       ('2342466', 'Иван Иванов', 5, 'С2', 170),
       ('NMNBV2', 'Лариса Тельникова', 5, 'С1', 185),
       ('DSA586', 'Лариса Привольная', 5, 'A1', 204),
       ('DSA583', 'Артур Мирный', 5, 'B1', 189),
       ('DSA581', 'Евгений Кудрявцев', 6, 'A1', 204),
       ('EE2344', 'Дмитрий Трусцов', 6, 'A2', 214),
       ('AS23PP', 'Максим Комсомольцев', 6, 'B2', 176),
       ('424266', 'Иван Иванов', 6, 'B1', 135),
       ('309623', 'Татьяна Крот', 6, 'С1', 155),
       ('319623', 'Юрий Дувинков', 6, 'D1', 125),
       ('322349', 'Эдуард Щеглов', 7, 'A1', 69),
       ('DIOPSL', 'Евгений Безфамильная', 7, 'A2', 58),
       ('DIOPS1', 'Константин Швец', 7, 'D1', 65),
       ('DIOPS2', 'Юлия Швец', 7, 'D2', 65),
       ('1IOPS2', 'Ник Говриленко', 7, 'C2', 73),
       ('999666', 'Анастасия Шепелева', 7, 'B1', 66),
       ('23234ASS', 'Петр Петров', 7, 'C1', 80),
       ('QYASDE12', 'Андрей Андреев', 8, 'A1', 100),
       ('1QAZD2', 'Лариса Потемнкина', 8, 'A2', 89),
       ('5QAZD2', 'Карл Хмелев', 8, 'B2', 79),
       ('2QAZD2', 'Жанна Хмелева', 8, 'С2', 77),
       ('BMXND1', 'Светлана Хмурая', 8, 'В2', 94),
       ('BMXND2', 'Кирилл Сарычев', 8, 'D1', 81),
       ('MMJJD5', 'Светлана Светикова', 9, 'A2', 222),
       ('SS978D', 'Андрей Желудь', 9, 'A1', 198),
       ('SS968D', 'Дмитрий Воснецов', 9, 'B1', 243),
       ('SS958D', 'Максим Гребцов', 9, 'С1', 251),
       ('DGDDDF2', 'Иван Иванов', 9, 'С2', 135),
       ('NMNBV25', 'Лариса Тельникова', 9, 'B2', 217),
       ('23234A', 'Петр Петров', 9, 'D1', 189),
       ('123951', 'Полина Зверева', 9, 'D2', 234);
