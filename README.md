koFinance Data Processing and Access Control Backend
---------------------------------------------------
Built this as part of a screening assignmet. The idea was to create a backend that handles financial records where different users have different levels of access.

The Problem I Was Solving
---------------------------------------------------
Imagine a company that tracks its income and expenses. Not everyone should be able to add or delete records. A viewer should only see the summary. An analyst should be able to read the data. Only an admin should be able to create or modify anything.

That is exactly what this backend does.
---------------------------------------------------
Live API
https://finance-data-processing-backend-yxpg.onrender.com

Note: First request may take 30-60 seconds as the server spins up on free tier.

Roles
---------------------------------------------------
Admin Can do everything. Create users, assign roles, add transactions, update them, delete them.

Analyst Can read transactions and view dashboard insights. Cannot create or modify anything.

Viewer Can only see the summary dashboard. Total income, total expense, net balance. Nothing else.

Tech I Used
---------------------------------------------------
Java 17 Spring Boot 3 Spring Security JWT for authentication MongoDB Atlas Maven

Running This Locally

You need Java 17 installed.

Clone the repo first:
---------------------------------------------------
git clone https://github.com/Manas-Rastogi/Finance-Data-Processing-Backend.git

Go into the folder and update application.yml:

spring:
  data:
    mongodb:
      uri: your_mongodb_atlas_uri
      database: Finance_DB

Then run:

./mvnw spring-boot:run

Server starts at http://localhost:8080

Or use the live deployed API directly:
https://finance-data-processing-backend-yxpg.onrender.com

How Login Works
---------------------------------------------------
Register a user:

POST /Auth/create/user
{
  "name": "Manas",
  "email": "manas@gmail.com",
  "password": "12345"
}

Login:

POST /Auth/login
{
  "email": "manas@gmail.com",
  "password": "12345"
}

You get a token back. Use it in every request:

Authorization: Bearer <token>

APIs

Auth:
POST   /Auth/create/user   — register
POST   /Auth/login         — login, get token

Users (Admin only):
GET    /users/GetAllUser   — list all users
PATCH  /users/{id}/role    — change role
PATCH  /users/{id}/status  — activate or deactivate

Transactions:
POST   /transactions?ID={userId}   — create (Admin)
GET    /transactions               — list with filters (Admin, Analyst)
GET    /transactions/{id}          — single record (Admin, Analyst)
PATCH  /transactions/{id}          — update (Admin)
DELETE /transactions/{id}          — soft delete (Admin)

Filtering supported on GET /transactions:
?type=income
?category=salary
?type=income&category=salary
?page=0&limit=10

Dashboard:
GET /dashboard/summary        — income, expense, balance (All roles)
GET /dashboard/category-wise  — breakdown by category (Admin, Analyst)
GET /dashboard/trends         — monthly data (Admin, Analyst)

Data Structure

User:
id
name
email
password       — bcrypt hashed, never returned in response
role           — admin / analyst / viewer
isActive       — true or false
createdAt

Transaction:
id
amount
type           — income or expense
category       — salary, food, rent, etc
date
notes
createdBy      — id of the admin who created it
isDeleted      — soft delete flag
createdAt

Things I Kept in Mind

Soft delete:
Transactions are never removed from the database.
isDeleted is set to true instead.
This keeps audit history intact.

Password security:
BCrypt is used to hash passwords.
Password is never returned in any API response.

Role defaults:
Every new user gets viewer role on registration.
Admin has to manually promote them.

JWT:
Stateless authentication.
Token carries the user role and is verified on every request.

Dashboard:
All calculations happen on the server.
Client just gets the final numbers.

Assumptions

Categories are free text, no fixed list enforced
Dashboard shows company wide data, not per user
A deleted transaction returns 404, not the record
Admin cannot deactivate or change their own role

Folder Structure

Controller     — handles incoming requests
Service        — business logic lives here
Entity         — MongoDB document structure
DTO            — what goes in and what comes out
Repository     — database queries
Security       — JWT filter and security config
Exception      — global error handling

What I Would Add With More Time

Swagger UI for live API documentation
Unit tests for service layer
Date range filtering on transactions
Export transactions as CSV
