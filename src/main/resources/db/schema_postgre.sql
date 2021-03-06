DROP TABLE IF EXISTS models CASCADE;
DROP TABLE IF EXISTS brands CASCADE;
DROP TABLE  IF EXISTS engines CASCADE;
DROP TABLE  IF EXISTS bodytype CASCADE;
DROP TABLE  IF EXISTS car CASCADE;
DROP TABLE  IF EXISTS authorities CASCADE;
DROP TABLE  IF EXISTS carusers CASCADE;
DROP TABLE  IF EXISTS ads CASCADE;

CREATE TABLE carusers
(
    id serial PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(100) NOT NULL,
    enabled BOOLEAN default true,
    CONSTRAINT login_unique UNIQUE (username)
);

CREATE TABLE authorities
(
    id serial PRIMARY KEY,
    user_id INT NOT NULL references carusers(id),
    username VARCHAR(100) NOT NULL,
    authority VARCHAR(100)  NOT NULL,
    CONSTRAINT rolname_unique UNIQUE (authority, username)
);

CREATE TABLE IF NOT EXISTS persistent_logins
(
    series VARCHAR(64) NOT NULL,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL,
    username character varying(100) NOT NULL REFERENCES carusers (username),
    CONSTRAINT pl_series_key PRIMARY KEY (series)
);

CREATE TABLE brands
(
    id serial PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE models
(
    id serial PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    brand_id INT NOT NULL references brands(id)
);


CREATE TABLE engines
(
    id serial PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE bodytype
(
    id serial PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE car
(
    id serial PRIMARY KEY,
    brand_id INT NOT NULL references brands(id),
    model_id INT NOT NULL references models(id),
    body_id INT NOT NULL references bodytype(id),
    engine_id INT NOT NULL references engines(id),
    car_year    VARCHAR(255),
    mileage    INT,
    color    VARCHAR(255)
);


CREATE TABLE ads
(
    id serial PRIMARY KEY,
    descr     VARCHAR(1000)            NOT NULL,
    created   TIMESTAMP DEFAULT now() NOT NULL,
    fileimage bytea,
    price     INT    NOT NULL,
    sold      BOOLEAN   DEFAULT false  NOT NULL,
    car_id    INT    REFERENCES car (id) ON DELETE SET NULL,
    user_id   INT    NOT NULL REFERENCES carusers (id) ON DELETE CASCADE
);


