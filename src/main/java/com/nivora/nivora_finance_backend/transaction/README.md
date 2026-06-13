# Transaction Module

The Transaction Module is responsible for handling money transfers between users and maintaining transaction history within Nivora Finance.

## Features

* Transfer money between users
* Retrieve transaction history for the authenticated user
* Retrieve transaction details by transaction ID
* Prevent self-transfers
* Validate minimum and maximum transfer limits
* Validate receiver existence
* Validate sufficient wallet balance
* Support idempotency keys to prevent duplicate transfer requests
* Protect transaction details from unauthorized access
* Return transaction direction (CREDIT/DEBIT) for transaction history

---

## APIs

### Transfer Money

**POST** `/api/v1/transactions/transfer`

Transfers money from the authenticated user to another user.

#### Request Headers

```http
Authorization: Bearer <JWT_TOKEN>
Idempotency-Key: <UNIQUE_KEY>
```

#### Request Body

```json
{
    "receiverId": 2,
    "amount": 10
}
```

#### Success Response

```json
{
    "transactionId": 7,
    "senderId": 1,
    "receiverId": 2,
    "amount": 10,
    "status": "SUCCESS",
    "type": "TRANSFER",
    "createdAt": "2026-06-13T08:31:26.489643",
    "message": "Transfer successful"
}
```

---

### Get My Transactions

**GET** `/api/v1/transactions`

Returns all transactions where the authenticated user is either the sender or receiver.

#### Request Headers

```http
Authorization: Bearer <JWT_TOKEN>
```

#### Response

```json
[
    {
        "transactionId": 1,
        "senderId": 1,
        "receiverId": 2,
        "amount": 20,
        "status": "SUCCESS",
        "type": "TRANSFER",
        "direction": "DEBIT",
        "createdAt": "2026-06-13T07:43:37.47804"
    }
]
```

---

### Get Transaction By ID

**GET** `/api/v1/transactions/{transactionId}`

Returns details of a specific transaction if the authenticated user owns it.

#### Request Headers

```http
Authorization: Bearer <JWT_TOKEN>
```

#### Response

```json
{
    "transactionId": 7,
    "senderId": 1,
    "receiverId": 2,
    "amount": 10,
    "status": "SUCCESS",
    "type": "TRANSFER",
    "direction": "DEBIT",
    "createdAt": "2026-06-13T08:31:26.489643"
}
```

---

## Business Rules

* Minimum transfer amount: **$1**
* Maximum transfer amount: **$100**
* Users cannot transfer money to themselves.
* Users cannot transfer more than their available wallet balance.
* The receiver must exist.
* Both sender and receiver must have wallets.
* Users can only view transactions that belong to them.

---

## Idempotency Support

Transfers support idempotency keys to prevent duplicate transaction processing.

Example:

```http
Idempotency-Key: transfer-001
```

Submitting the same key multiple times prevents accidental duplicate transfers.

---

## Unit Tests

The Transaction Module includes unit tests covering:

* Successful transfers
* Insufficient balance scenarios
* Self-transfer prevention
* Invalid transfer amounts
* Receiver not found scenarios
* Transaction history retrieval
* CREDIT/DEBIT direction mapping
* Transaction detail retrieval
* Unauthorized transaction access
* Transaction not found scenarios

---

## Technologies Used

* Spring Boot
* Spring Security
* Spring Data JPA
* PostgreSQL
* Redis
* JUnit 5
* Mockito
* Lombok
