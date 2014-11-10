USE c_cs108_kmblake;

-- Create user table

DROP TABLE IF EXISTS users;
 -- remove table if it already exists and start from scratch

CREATE TABLE users (
    id INTEGER NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    username VARCHAR(255),
    PRIMARY KEY(id)
);

INSERT INTO users (first_name, last_name, username) 
    VALUES ("John", "Doe", "jdoe");

DROP TABLE IF EXISTS quiz_history;
 -- remove table if it already exists and start from scratch

CREATE TABLE quiz_history (
    id INTEGER NOT NULL AUTO_INCREMENT,
    user_id INTEGER,
    quiz_id INTEGER,
    score INTEGER,
    time DOUBLE,
    taken_on DATETIME,
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS friends;
 -- remove table if it already exists and start from scratch

CREATE TABLE friends (
    id INTEGER NOT NULL AUTO_INCREMENT,
    requested_by INTEGER,
    requested_for INTEGER,
    approved BOOLEAN,
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS message_types;
 -- remove table if it already exists and start from scratch

CREATE TABLE message_types (
    id INTEGER NOT NULL AUTO_INCREMENT,
    type_name VARCHAR(255),
    PRIMARY KEY(id)
);

INSERT INTO message_types (type_name) VALUES
  ("Friend Request"), ("Challenge"), ("Note");

DROP TABLE IF EXISTS messages;
 -- remove table if it already exists and start from scratch

CREATE TABLE messages (
    id INTEGER NOT NULL AUTO_INCREMENT,
    sender INTEGER,
    recipient INTEGER,
    message_type_id INTEGER,
    body TEXT,
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS quizzes;
 -- remove table if it already exists and start from scratch

CREATE TABLE quizzes (
    id INTEGER NOT NULL AUTO_INCREMENT,
    title VARCHAR(255),
    created_by INTEGER,
    created_on DATETIME,
    description TEXT,
    randomized BOOLEAN,
    multiple_pages BOOLEAN,
    immediate_feedback BOOLEAN,
    practice_mode BOOLEAN,
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS questions;
 -- remove table if it already exists and start from scratch

CREATE TABLE questions (
    id INTEGER NOT NULL AUTO_INCREMENT,
    quiz_id INTEGER,
    question TEXT,
    question_number INTEGER,
    question_type_id INTEGER,
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS question_types;
 -- remove table if it already exists and start from scratch

CREATE TABLE question_types (
    id INTEGER NOT NULL AUTO_INCREMENT,
    type_name VARCHAR(255),
    PRIMARY KEY(id)
);

INSERT INTO question_types (type_name) VALUES
  ("Question-Response"), ("Fill in the Blank"), ("Multiple Choice"), ("Picture-Response"),
  ("Multiple Answer"), ("Multiple Choice with Mulitple Answers");

DROP TABLE IF EXISTS answers;
 -- remove table if it already exists and start from scratch

CREATE TABLE answers (
    id INTEGER NOT NULL AUTO_INCREMENT,
    question_id INTEGER,
    answer TEXT,
    correct BOOLEAN,
    PRIMARY KEY(id)
);