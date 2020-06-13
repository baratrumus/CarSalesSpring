-- admin/1 user/1 --
INSERT INTO carusers(username, password, email, phone, enabled)
VALUES ('admin', '$2a$10$OjfMhs1B.ZX0MK9RmSpHBOOgNWdm2coJDPak4qwcjqdg1MQsj8i1a', 'admin@mail.ru', '+79167682319', 'true'),
       ('user', '$2a$10$OjfMhs1B.ZX0MK9RmSpHBOOgNWdm2coJDPak4qwcjqdg1MQsj8i1a', 'user@mail.ru', '+79166309281', 'true');

INSERT INTO authorities (user_id, username, authority)
VALUES (1, 'admin', 'ROLE_ADMIN'),
       (1, 'admin', 'ROLE_USER'),
       (2, 'user', 'ROLE_USER');

INSERT INTO brands (name)
VALUES ('Ford'),
       ('BMW'),
       ('Subaru'),
       ('Honda'),
       ('Vaz'),
       ('Audi');

INSERT INTO models (name, brand_id)
VALUES ('Focus', 1),
       ('Mustang', 1),
       ('Fusion', 1),
       ('M6', 2),
       ('X6', 2),
       ('i8', 2),
       ('Outback', 3),
       ('Legasy', 3),
       ('Impreza', 3),
       ('Accord', 4),
       ('Civik', 4),
       ('Pilot', 4),
       ('Largus', 5),
       ('Vesta', 5),
       ('2121', 5),
       ('A4', 6),
       ('S8', 6),
       ('Q5', 6);

INSERT INTO engines(name)
VALUES ('Injector'),
       ('Carburetor'),
       ('Turbo Petrol'),
       ('Turbo Diesel');

INSERT INTO bodytype(name)
VALUES ('Sedan'),
       ('Station wagon'),
       ('Pickup'),
       ('Hatchback'),
       ('Coupe'),
       ('Cabriolet');

INSERT INTO car (brand_id, model_id, engine_id, body_id, car_year, color, mileage)
VALUES (3, 8, 1, 1, '1997', 'dark blue', 185000),
       (1, 1, 1, 2, '2013', 'black', 140000),
       (2, 4, 3, 5, '2007', 'black', 77600),
       (4, 11, 1, 4, '2018', 'black', 15300),
       (6, 16, 4, 6, '2004', 'metallic grey', 156300);

INSERT INTO ads(user_id, car_id, price, created, descr, fileimage)
VALUES (1, 1, 3200, '2020-04-16',  'I have owned the car for 5 years and have done extensive restoration to it as it was not in the best condition when I purchased it. The car has had a total respray in its factor colour!

184,xxx genuine kms, twin sunroof, electric folding mirrors, heated mirrors, fog lights, suede cream seats, factory option cargo net built into rear seats, climate control.

Imported from Japan to NZ by the previous owner then brought to Aus under his ownership. I am the 3rd owner and have taken amazing care of the car. I have all receipts for mechanical work and the respray and touchup.',

''),

(1, 2, 16000, '2020-06-11', 'This Ford Focus 2013 ST loves weaving in and out of city traffic. It''s been serviced regularly and has a full service history. Make phone calls safely and conveniently through the bluetooth enabled audio system. My 2013 Ford Focus is in fantastic condition as it has always been maintained at a high standard. Selling due to upgrade. 139200 km on the clock only. It is exceptional value at $16,000. It has a clear accident history. Always been garaged and looked after meticulously',
''),

(1, 3, 43990, '2020-06-05',  '2007 BLACK BMW M6 COUPE IN SAPPHIRE BLACK METALLIC.. A TRUE BEAST YET FULL OF CLASS.. TRAVELLING A LOW 77,600 KM''S WITH A FULL SERVICE HISTORY.. 2ND OWNER FROM NEW! JUST SERVICED AND SAFETY TO GO.
COMPETITIVE FINANCE AVAILABLE!
TRADE IN''S WELCOME!',
''),

(2, 4, 29999, '2020-05-25', 'This is Great Example of a Ex  Australia Executive Driven Vehicle. Vehicle Presents in Great Condition and Carries its Balance of  New Car Warranty. **Test drive today**  Pre Owned Sales Office located in the  showroom. We offer a 3 year, 175,000km Auto Protection Plan for your peace of mind and capped price servicing to help you manage your cars running costs. To ar your complete vehicle presentation contact us today.',
''),

(2, 5, 12800, '2020-05-19', 'I am selling my 2004 Audi Tourer cabriolet A4. One owner
 Has been serviced every 6 months last one 13 february 2020. The car is in good condition,
 I Just renewed the Registration until 26 March 2021. New battery warranty until January 2023
Feb 2020: New ignition coils. Front brake rotors and front brake pads.
It is a great car really nice to drive',
'');
