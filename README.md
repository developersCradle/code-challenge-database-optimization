- [ ] Add come caching
- [ ] Test batteries, for heavy data operations

# Response Format

 This approach allows for concise and expressive code when building responses for RESTful endpoints with proper status code. This API will return in following format: 

```json
Status: Message of occurance
```

# MessageResource

`MessageResource` is responsible for handling messages in the system.

## Get Messages for User Inbox

This endpoint retrieves messages for a specified user's inbox.

### Endpoint

- **Path:** `/messages/{userId}/inbox`
- **HTTP Method:** GET
- **Produces:** `application/json`

### Parameters

- **userId** - An integer representing the ID of the user for whom messages are to be retrieved.

### Response

> Success (HTTP Status: 200 OK)

Returns a list of messages from the inbox of the user.

#### Request Body (JSON Example)

```json
[
  {
    "title": "Message Title 1",
    "body": "Message Body 1",
    "sentAt": "2024-02-25T14:30:00",
    "senderId": 2
  },
  {
    "title": "Message Title 2",
    "body": "Message Body 2",
    "sentAt": "2024-02-26T10:15:00",
    "senderId": 3
  },
  // ... other messages
]
```

> Not Found (HTTP Status: 404 Not Found)

Returns an error message when no messages are found for the specified user or User not found from the system.

```json
{
  "Not Found: User not found from the system." or "Not Found: No messages found for the user."
}
```

> Internal Server Error (HTTP Status: 500 Internal Server Error)

Returns an error message for any internal server error during message retrieval.

```json
{
  "Internal Server Error: Error retrieving messages for the user."
}
```

### Example Usage

```
curl -X GET http://localhost:8080/messages/1/inbox
```

# Send New Message

This endpoint sends a new message in the system.

### Endpoint
- **Path:** `/messages/send`
- **HTTP Method:** POST
- **Consumes:** `application/json`
- **Produces:** `application/json`

#### Request Body (JSON Example)

```json
{
  "title": "Message Title",
  "body": "Message Body",
  "senderId": 123,
  "receiverIds": [456, 789]
}
```

## Constraints

- The `title`, `body`, `senderId`, and `receiverIds` fields are required and must not be blank or empty.

#### Response

> HTTP Status: 201 Created

Returns a success message when the message is sent successfully.

```json
{
  "Created: The message was sent successfully."
}

```

> HTTP Status: 404 Not Found

An issue occurred, and the record conflicts with the one in the database.

```json
{
  "Not Found: Sender not found from the system."
}

```

> HTTP Status: 409 Conflict

An issue occurred, and the record conflicts with the one in the database.

```json
{
  "Conflict: The record conflicting with record on the database."
}

```

> Internal Server Error (HTTP Status: 500 Internal Server Error)

Error message when there is a unique constraint violation or SQL error.

```json
{
  "Internal Server Error: Unique constraint violation or SQL error"
}

```

### Notes

Rollbacks are employed in case of errors to ensure that the database remains in a consistent state.

### Example Usage

```
curl -X POST   -H "Content-Type: application/json"   -d '{"Invian": "Hello from deadlines", "body": "This should have done before 1 week, but school and other job happened", "senderId": 1, "receiverIds": [456, 789]}' http://localhost:8080/messages/send

```

# StatisticsResource

`StatisticsResource` is responsible for handling statistics related reports.


<hr>

# Requirements
- Maven
- Docker
- Docker-compose (installed separately from Docker)

# Running / redeploying
## Linux / macOS
`make`

## Windows
Look at the file named `Makefile` and run the same commands in the root directory of the project

# Accessing
## REST server
http://localhost:8080

You should get a congratulation message. If not, try again a few seconds later - the database initialization takes some time. If it still doesn't work, look at the logs.

## MariaDB database
localhost:3306

Username is `root` and password is `root_password`

# Developing
The SQL statements in `database.sql` are run on every redeploy on the empty database, so evolving the database schema works just like code. Change the statements, redeploy and there it is. No need for `ALTER TABLE` statements.

Note that it is not possible to run the REST server outside of Docker.

# Debugging
## REST server
Java remote debug port is at `localhost:5005`

Logs can be read with `docker logs codingassignment`

## MariaDB database
Logs can be read with `docker logs codingassignment-db`