CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE tasks (
                       id BIGSERIAL PRIMARY KEY,
                       userId VARCHAR(50) NOT NULL,
                       title VARCHAR(50) NOT NULL,
                       description VARCHAR(500),
                       creationDate TIMESTAMP NOT NULL,
                       targetDate TIMESTAMP,
                       completed BOOLEAN DEFAULT FALSE,
                       deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE notifications (
                        id BIGSERIAL PRIMARY KEY,
                        userId VARCHAR(50) NOT NULL,
                        message VARCHAR(100) NOT NULL,
                        read BOOLEAN DEFAULT FALSE
);