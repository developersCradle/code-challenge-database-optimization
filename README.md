
<p align="center">
  <img id="domaCare" src="domaCareSymbol.png" alt="alt text" width="400"/>
</p>





> [!IMPORTANT]
> Coding assignment
> Messaging application
>
> ● We will provide a project template of standard RESTful web service <br>
> ● The template is using plain SQL strings since wiring up what we have in the backend would be too much effort for this <br>
> ● Your task is to create database structure and some simple endpoints for an imaginary messaging application <br>
> ● Imagine, that the service is used by lots of users, so try to optimize database structure for large volumes of data <br>
>
> Requirements: <br>
> ● User should be able to send messages (like email) to recipients who are other users of the messaging application. <br>
> ● Message should contain title, message body and timestamp of when it was sent <br>
> ● Message has a maximum of 5 recipients <br>
> ● User should have a name <br>
> Output <br>
> Database <br>
> ● CREATE TABLE statements for the database structure in the database sql file <br>
> ● Optionally test data as INSERT statements in the same file <br>
> Endpoints implemented in the project template <br>
> ● Send new message <br>
> ● Read messages addressed to a specified user <br>
> ● Statistics endpoint: Top 10 users (by sent message count) sorted by decreasing sent message count for the last 30 days <br>



# Cache Control Directives

The cache control directives are being used with `CacheControl` class provided by JAX-RS. Currently it is under benchmarking to find which scenario fits for this problem. [See more about](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9)

# Response Format

 This approach allows for concise and expressive code when building responses for RESTful endpoints with proper status code. This API will return in following format: 

```http
Status: Message of occurrence
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

Returns a list of messages from the inbox of the user. `SentAt` in format **yyyy-MM-dd**

#### Request Body (JSON Example)

```json
[
  {
    "title": "Message Title 1",
    "body": "Message Body 1",
    "sentAt": "2024-02-25",
    "senderId": 2
  },
  {
    "title": "Message Title 2",
    "body": "Message Body 2",
    "sentAt": "2024-02-26",
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

```http
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

- `receiverIds` should have **minimum** number of recipients: 1 and **maximum** number of recipients: 5


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

```http
curl -X POST   -H "Content-Type: application/json"   -d '{"Invian": "Hello from deadlines", "body": "This should have done before 1 week, but school and other job happened", "senderId": 1, "receiverIds": [456, 789]}' http://localhost:8080/messages/send

```

# StatisticsResource

`StatisticsResource` is responsible for handling statistics related reports.

## Method

### Get Top Users with Message Count

#### Endpoint

- **Method**: GET
- **Path**: `/statistics/top-users`

#### Response

- **Status**: 200 OK - Successful retrieval of top users with message count.
- **Status**: 404 Not Found - No top users found.
- **Status**: 500 Internal Server Error - Internal server error during processing.

#### Example

```http
GET /statistics/top-users
```
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