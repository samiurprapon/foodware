# FoodWare Server
This project contains all the backend codes for Foodware - Food Catering Serive

Express.js, Sequelize is used in this project.


## Getting Started
### Prerequisites
- node.js 14.15.x
- npm 6.14.x

### Installing & Configuration
1) Install dependencies
```
    npm install
```
2) change database credential on `config/config.js`
```
{
"development" : {
    "username" : "",
    "password" : "",
    "database" : "foodware"
    }
}
```
3) Run MySQL server on `apache` or `nginx` 
4) Create a new database and named as `foodware`

### Run the server
```
    npm test
```

## APIs
### Auth Route
#### Register
`POST /api/auth/register`
```
{
    email,
    password, 
    type
}
```
**Description**: creates a new user; first user will be assigned as an admin user. Password is stored in `SHA512` format
#### Login
`POST /api/auth/login`
```
{
    email,
    password
}
```
**Description**: logs in to the server. Server will return a JWT token as:
```javascript
{
  "message": "logged in successfully",
  "user_type": "0",
  "user" : {
      ---
      ---
  },
  "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ODQ4MjU1NjJhOWRlMDE5NmM5MTI4ZmIiLCJ1c2VybmFtZSI6InRlc3RlciIsImFkbWluIjp0cnVlLCJpYXQiOjE0ODExMjMxNjMsImV4cCI6MTQ4MTcyNzk2MywiaXNzIjoidmVsb3BlcnQuY29tIiwic3ViIjoidXNlckluZm8ifQ.vh8LPqxYWJtO6Bxe7reL7sEon13dYFFnhpnyyEmaLBk",
  "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ODQ4MjU1NjJhOWRlMDE5NmM5MTI4ZmIiLCJ1c2VybmFtZSI6InRlc3RlciIsImFkbWluIjp0cnVlLCJpYXQiOjE0ODExMjMxNjMsImV4cCI6MTQ4MTcyNzk2MywiaXNzIjoidmVsb3BlcnQuY29tIiwic3ViIjoidXNlckluZm8ifQ.vh8LPqxYWJtO6Bxe7reL7sEon13dYFFnhpnyyEmaLBk"
}
```

#### Check
`GET /api/auth/check` 
```
{
    access_token
}
```

**Description**: checks the JWT. Token from `Authorization` from should be passed as Url-encoded query or `x-access-token` header

## License
This project is licensed under [GPL-3.0 License](https://opensource.org/licenses/GPL-3.0).  
Copyright (c) 2020 [Samiur Prapon](https://samiurprapon.github.io/).