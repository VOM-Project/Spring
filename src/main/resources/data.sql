DROP TABLE IF EXISTS region;

CREATE TABLE REGION
(
    id      BIGINT      NOT NULL AUTO_INCREMENT,
    name    VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO REGION (name)
values ('서울특별시'),
       ('경기도'),
       ('인천광역시'),
       ('강원도'),
       ('충청도'),
       ('대전광역시'),
       ('세종특별시'),
       ('광주광역시'),
       ('전라도'),
       ('경상도'),
       ('대구광역시'),
       ('울산광역시'),
       ('부산광역시'),
       ('제주도');

DROP TABLE IF EXISTS Keyword;

CREATE TABLE KEYWORD
(
    id      BIGINT      NOT NULL AUTO_INCREMENT,
    name    VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO KEYWORD (name)
values ('공부');
INSERT INTO KEYWORD (name)
values ('운동');
INSERT INTO KEYWORD (name)
values ('일상');
INSERT INTO KEYWORD (name)
values ('여행');
INSERT INTO KEYWORD (name)
values ('맛집');
INSERT INTO KEYWORD (name)
values ('영화');
INSERT INTO KEYWORD (name)
values ('독서');
INSERT INTO KEYWORD (name)
values ('음악');
INSERT INTO KEYWORD (name)
values ('개발');
INSERT INTO KEYWORD (name)
values ('디자인');
INSERT INTO KEYWORD (name)
values ('기획');
INSERT INTO KEYWORD (name)
values ('영어');
INSERT INTO KEYWORD (name)
values ('사진');
INSERT INTO KEYWORD (name)
values ('패션');

Insert INTO MEMBER(email, nickname, role)
values ('test1@example.com', '지민', 'USER');
Insert INTO MEMBER(email, nickname, role)
values ('test2@example.com', '윤지', 'USER');

