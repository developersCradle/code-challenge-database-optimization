

# MessageResource

This resource is responsible for handling messages in the system.

## Send New Message

### Endpoint
- **Path:** `/messages/send`
- **HTTP Method:** POST
- **Consumes:** `application/json`

### Description
Send a new message in the system.

#### Request Body (JSON Example)
```
{
  "senderId": 1,
  "body": "Some Body",
  "title": "Some Title",
  "receiverIds": [1, 2, 3, 4, 5]
}
```
#### Response
> HTTP Status: 201 Created

The message was sent successfully.
> HTTP Status: 409 Conflict

An issue occurred, and the record conflicts with the one in the database.

The response will include appropriate status codes indicating the success or failure of the message sending operation

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