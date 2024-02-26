
-- Version 2

-- Models Users
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name TEXT NOT NULL
);

-- Models Messages
CREATE TABLE Messages (
    message_id INT AUTO_INCREMENT PRIMARY KEY,
    title TEXT NOT NULL,
    body TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sender_id INT,
    FOREIGN KEY (sender_id) REFERENCES Users(user_id)
);

-- Models Recipients of sent messages
CREATE TABLE Recipients (
    message_id INT,
    receiver_id INT,
    PRIMARY KEY (message_id, receiver_id),
    FOREIGN KEY (message_id) REFERENCES Messages(message_id),
    FOREIGN KEY (receiver_id) REFERENCES Users(user_id)
);

-- We read query Recipients for calculations, so we index it for now.
CREATE INDEX idx_receiver_id ON Recipients(receiver_id);

INSERT INTO Users SET name = 'Some random name 1';
INSERT INTO Users SET name = 'Some random name 2';
INSERT INTO Users SET name = 'Some random name 3';
INSERT INTO Users SET name = 'Some random name 4';
INSERT INTO Users SET name = 'Some random name 5';
