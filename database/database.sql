
-- Version 4

-- TODO HEIKKI(Optimization, MariaDB) ANALYZE TABLE and OPTIMIZE TABLE 

-- Models Users
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name TEXT NOT NULL
);

-- Models Messages
CREATE TABLE Messages (
    message_id INT AUTO_INCREMENT PRIMARY KEY,
    title TEXT NOT NULL, -- Message always needs title
    body TEXT NOT NULL, -- Message always needs body
    sent_at DATE DEFAULT (CURRENT_DATE),
    sender_id INT NOT NULL, -- Message always needs sender
    FOREIGN KEY (sender_id) REFERENCES Users(user_id) -- In MariaDB, index is automatically created on the columns involved in the foreign key relationship.
);

-- Models Recipients of sent messages
CREATE TABLE Recipients (
    message_id INT NOT NULL, -- Recipient always needs a message.  NOT NULL, querying might be more efficient.
    receiver_id INT NOT NULL, -- Recipient always needs receiver. NOT NULL, querying might be more efficient.
    PRIMARY KEY (message_id, receiver_id),
    FOREIGN KEY (message_id) REFERENCES Messages(message_id), -- In MariaDB, index is automatically created on the columns involved in the foreign key relationship.
    FOREIGN KEY (receiver_id) REFERENCES Users(user_id) -- In MariaDB, index is automatically created on the columns involved in the foreign key relationship.
);



-- We read query Recipients for calculations, so we index it for now.
CREATE INDEX idx_receiver_id ON Recipients(receiver_id);



-- DUMMY DATA - START

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


-- TODO HEIKKI(Performance, SQL) If uses index. For large amount of data. Consider dropping insert and after insert re-create index for tables. 
-- TODO HEIKKI(SQL, SQL) Verify this works also  in MariaDB. Works in MySQL
 
DELIMITER //

CREATE PROCEDURE addingMultipleUsersForBenchmark(IN num_users INT)

BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= num_users DO
        INSERT INTO Users (name) VALUES (CONCAT('Some random name ', i));
        SET i = i + 1;
    END WHILE;
END //

DELIMITER ;

-- Making 1000 users
CALL addingMultipleUsersForBenchmark(1000);


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





-- Sample data for messages with CURRENT_DATE

INSERT INTO Messages (title, body, sent_at, sender_id) VALUES ('Daily Update 1', 'Here is your daily update. 1', CURRENT_DATE, 2);
INSERT INTO Messages (title, body, sent_at, sender_id) VALUES ('Daily Update 2', 'Here is your daily update. 2', CURRENT_DATE, 2);
INSERT INTO Messages (title, body, sent_at, sender_id) VALUES ('Daily Update 3', 'Here is your daily update. 3', CURRENT_DATE, 2);
INSERT INTO Messages (title, body, sent_at, sender_id) VALUES ('Daily Update 4', 'Here is your daily update. 4', CURRENT_DATE, 2);
INSERT INTO Messages (title, body, sent_at, sender_id) VALUES ('Daily Update 5', 'Here is your daily update. 5', CURRENT_DATE, 2);


INSERT INTO Messages (title, body, sent_at, sender_id) VALUES ('Meeting Reminder', 'Don''t forget the meeting tomorrow!', '2022-03-05', 1);
INSERT INTO Messages (title, body, sent_at, sender_id) VALUES ('Meeting Reminder', 'Don''t forget the meeting tomorrow!', '2022-03-05', 3);
INSERT INTO Messages (title, body, sent_at, sender_id) VALUES ('Meeting Reminder', 'Don''t forget the meeting tomorrow!', '2022-03-05', 3);
INSERT INTO Messages (title, body, sent_at, sender_id) VALUES ('Meeting Reminder', 'Don''t forget the meeting tomorrow!', '2022-03-05', 4);
INSERT INTO Messages (title, body, sent_at, sender_id) VALUES ('Meeting Reminder', 'Don''t forget the meeting tomorrow!', '2022-03-05', 5);
INSERT INTO Messages (title, body, sent_at, sender_id) VALUES ('Meeting Reminder', 'Don''t forget the meeting tomorrow!', '2022-03-05', 6);


-- TODO HEIKKI(Performance, SQL) For large amount of data. Consider dropping insert and after insert re-create index for tables

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

INSERT INTO Recipients SET message_id = 4, receiver_id = 2;
INSERT INTO Recipients SET message_id = 5, receiver_id = 4;
