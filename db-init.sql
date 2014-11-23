USE c_cs108_kmblake;

-- Create user table

DROP TABLE IF EXISTS users;
 -- remove table if it already exists and start from scratch

CREATE TABLE users (
    id INTEGER NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    username VARCHAR(255),
    password VARCHAR(255),
    salt VARCHAR(255),
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
    time TIME,
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

-- what happens if they don't select randomize and questions don't have an order specificied?
CREATE TABLE questions (
    question_id INTEGER NOT NULL AUTO_INCREMENT,
    quiz_id INTEGER,
    question_number INTEGER,
    question_type VARCHAR(255),
    PRIMARY KEY(question_id)
);

DROP TABLE IF EXISTS question_response;
 -- remove table if it already exists and start from scratch


-- type 1
CREATE TABLE question_response (
    question_id INTEGER NOT NULL,
    question TEXT,
    answer TEXT,
    PRIMARY KEY(question_id)
);

DROP TABLE IF EXISTS fill_in_the_blank;
 -- remove table if it already exists and start from scratch

-- type 2
CREATE TABLE fill_in_the_blank (
    question_id INTEGER NOT NULL,
    question TEXT,
    answer TEXT,
    PRIMARY KEY(question_id)
);

DROP TABLE IF EXISTS multiple_choice;
 -- remove table if it already exists and start from scratch

-- type 3
CREATE TABLE multiple_choice (
    question_id INTEGER NOT NULL,
    question TEXT,
    PRIMARY KEY(question_id)
);

DROP TABLE IF EXISTS multiple_choice_answers;
 -- remove table if it already exists and start from scratch

CREATE TABLE multiple_choice_answers (
    id INTEGER NOT NULL AUTO_INCREMENT,
    question_id INTEGER,
    answer TEXT,
    correct BOOLEAN,
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS picture_response;
 -- remove table if it already exists and start from scratch

-- type 4
CREATE TABLE picture_response (
    question_id INTEGER,
    image_url TEXT,
    answer TEXT,
    PRIMARY KEY(question_id)
);

DROP TABLE IF EXISTS multiple_answer;
 -- remove table if it already exists and start from scratch

-- type 5
CREATE TABLE multiple_answer (
    question_id INTEGER,
    question TEXT,
    PRIMARY KEY(question_id)
);

DROP TABLE IF EXISTS multiple_answer_answers;
 -- remove table if it already exists and start from scratch

CREATE TABLE multiple_answer_answers (
    id INTEGER NOT NULL AUTO_INCREMENT,
    question_id INTEGER,
    answer TEXT,
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS multiple_choice_multiple_answer;
 -- remove table if it already exists and start from scratch

-- type 6
CREATE TABLE multiple_choice_multiple_answer (
    question_id INTEGER NOT NULL,
    question TEXT,
    PRIMARY KEY(question_id)
);

DROP TABLE IF EXISTS question_types;
 -- remove table if it already exists and start from scratch

CREATE TABLE question_types (
    id INTEGER NOT NULL AUTO_INCREMENT,
    type VARCHAR(255),
    type_name VARCHAR(255),
    PRIMARY KEY(id)
);

INSERT INTO question_types (type, type_name) VALUES
  ("question_response", "Question-Response"), ("fill_in_the_blank", "Fill in the Blank"), ("multiple_choice", "Multiple Choice"), ("picture_response", "Picture-Response"), ("multiple_answer", "Multiple Answer"), ("multiple_choice_multiple_answer", "Multiple Choice with Multiple Answers");

