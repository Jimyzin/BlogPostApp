INSERT INTO AUTHORITY (AUTHORITY_ID, AUTHORITY)
VALUES (1, 'ROLE_ADMIN');
INSERT INTO AUTHORITY (AUTHORITY_ID, AUTHORITY)
VALUES (2, 'ROLE_USER');

INSERT INTO USER (USER_ID, PASSWORD, USERNAME, AUTHORITY_ID)
VALUES (1, '$2a$12$DyZcTJljvpxqbvQPeUptYuJtKMweFp8KGktVc7B9SHBCvEgmKsbrK', 'admin', 1);
INSERT INTO USER (USER_ID, PASSWORD, USERNAME, AUTHORITY_ID)
VALUES (2, '$2a$10$I.aQETJOwbu6Vwc/RZSVNuqKP4Pqe2GF6lNwpNWM3HB3OY6Stmyum', 'user2', 2);