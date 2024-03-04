
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



-- DUMMY DATA - START

-- Start - For Users Tables

-- Some data for Users, batch inserts
-- INSERT INTO Users (name)
-- VALUES ('Some random name 1'),
--        ('Some random name 2'),
--        ('Some random name 3'),
--        ('Some random name 4'),
--        ('Some random name 5'),
--        ('Some random name 6'),
--        ('Some random name 7'),
--        ('Some random name 8'),
--        ('Some random name 9'),
--        ('Some random name 10');


-- TODO HEIKKI(Performance, SQL) If uses index. For large ammount of data. Consider dropping insert and after insert re-create index for tables. 
-- TODO HEIKKI(SQL, SQL) Verify this works alaso in HeidiSQL. Works in MySQL

DELIMITER //

CREATE PROCEDURE addingMultipleUserForBenchmark(IN num_users INT)

BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= num_users DO
        INSERT INTO Users (name) VALUES (CONCAT('Some random name ', i));
        SET i = i + 1;
    END WHILE;
END //

DELIMITER ;

-- Making 1000 users
CALL addingMultipleUserForBenchmark(1000);


DELIMITER //

CREATE PROCEDURE addingMultipleMessagesForBenchmark(IN num_messages INT)
BEGIN

    DECLARE i INT DEFAULT 0;

    WHILE i < num_messages DO
        INSERT INTO Messages (title, body, sender_id)
        VALUES (
            CONCAT('Message Title ', i + 1),
            CONCAT('Message Body ', i + 1),
            1  -- Replace with the actual sender_id
        );

        SET i = i + 1;
    END WHILE;
END //

DELIMITER ;

CALL addingMultipleMessagesForBenchmark(1000);

-- DUMMY DATA - STOP





-- Sample data for messages
INSERT INTO Messages SET title = 'Hello', body = 'Hi there!', sender_id = 1;
INSERT INTO Messages SET title = 'Meeting Tomorrow', body = 'Don\'t forget the meeting tomorrow at 10 AM.', sender_id = 1;
INSERT INTO Messages SET title = 'Important Update', body = 'Please review the document attached for the latest update.', sender_id = 1;

-- Same, but with some random timestamp

INSERT INTO Messages SET title = 'Joku juttu', body = 'Fast. We need to speed up.', sent_at = '2020-02-25 14:30:00', sender_id = 2;
INSERT INTO Messages SET title = 'Joku raportti', body = 'Please submit Invians test by Tuesday.', sent_at = '2020-02-24 09:45:00', sender_id = 3;


-- Inserting recipients for sample messages

-- TODO HEIKKI(Performance, SQL) For large ammount of data. Consider dropping insert and after insert re-create index for tables

INSERT INTO Recipients SET message_id = 1, receiver_id = 1;
INSERT INTO Recipients SET message_id = 2, receiver_id = 1;
INSERT INTO Recipients SET message_id = 3, receiver_id = 1;
INSERT INTO Recipients SET message_id = 4, receiver_id = 1;
INSERT INTO Recipients SET message_id = 5, receiver_id = 1; -- Message has a maximum of 5 recipients

-- Inserting additional recipients, some random ones

INSERT INTO Recipients SET message_id = 1, receiver_id = 2;
INSERT INTO Recipients SET message_id = 2, receiver_id = 3;
INSERT INTO Recipients SET message_id = 3, receiver_id = 4;
INSERT INTO Recipients SET message_id = 4, receiver_id = 5;
INSERT INTO Recipients SET message_id = 5, receiver_id = 6;

INSERT INTO Recipients SET message_id = 3, receiver_id = 1;
INSERT INTO Recipients SET message_id = 4, receiver_id = 2;
INSERT INTO Recipients SET message_id = 5, receiver_id = 4;
