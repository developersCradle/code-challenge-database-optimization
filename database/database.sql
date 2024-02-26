
-- Write your CREATE TABLE statements here and optionally your INSERT statements if you want static test data
-- CREATE TABLE example_table (
--    hello_message TEXT NOT NULL
-- );

-- Version 1
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE Messages (
    message_id INT AUTO_INCREMENT PRIMARY KEY,
    title TEXT NOT NULL,
    body TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sender_id INT,
    FOREIGN KEY (sender_id) REFERENCES Users(user_id),
);


-- We read query users, so we are going to index it for now. Users table are not frequantly manipulated, so its even better
-- CREATE INDEX idx_user_id ON Users(user_id);
-- CREATE INDEX idx_user_name ON Users(name);

INSERT INTO Users SET name = 'Some random name 1';
INSERT INTO Users SET name = 'Some random name 2';
INSERT INTO Users SET name = 'Some random name 3';
INSERT INTO Users SET name = 'Some random name 4';
INSERT INTO Users SET name = 'Some random name 5';
