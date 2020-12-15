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
```json
{
    "development" : {
        "username" : "username",
        "password" : "password",
        "database" : "foodware"
    }
}
```
3) write `node` on terminal and generate random `Secret Number` for `ACCESS_TOKEN_SECRET` and `REFRESH_TOKEN_SECRET`
4) On terminal paste this code and press enter
```
    require('crypto').randomBytes(64).toString('hex');
``` 
5) create `.env` file on root and paste two generated value as follows
```
    # server port
    PORT=3000

    # environment for production
    NODE_ENV=production

    # authentication
    ACCESS_TOKEN_SECRET=ae9dc2ea3ec0bd564a0aa227f0e7db4ae90df7ab8b2d102bd98ccfc63
    REFRESH_TOKEN_SECRET=8d7014df0afc704c5a9159427b95a6eb42f44d16d658c8e1b4673c61
``` 
**or** , Edit `config/secrets.json`
```json
{
    "PORT": 3000,
    "NODE_ENV": "development",
    "PASSWORD_SECRET": "secret1",
    "ACCESS_TOKEN_SECRET":"secret2",
    "REFRESH_TOKEN_SECRET": "secret3"
}
``` 

6) Run MySQL server on `apache` or `nginx` 
7) Create a new database and named as `foodware`

### Run the server
```
    npm test
```

## APIs

### Auth Route

#### Register
`POST /api/auth/register`
```json
{
    "email" : "john@example.com",
    "password" : "123456",
    "type" : "vendor"
}
```
**Description**: creates a new user; first user will be assigned as an admin user. Password is stored in `SHA512` format
#### Login
`POST /api/auth/login`
```json
{
    "email" : "john@example.com",
    "password" : "123456"
}
```
**Description**: logs in to the server. Server will return a JWT token as:
```json
{
  "message": "Login successfully!",
  "type": "vendor",
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InByYXBvbjNAdGVzdC5jb20iLCJ0eXBlIjoidmVuZG9yIiwiaWF0IjoxNjA4MDY0MTE1LCJleHAiOjE2MDgwNjY4MTV9.tfuZTC3J8D7nCyLYDzO2d6k-uHHwoQOmNtDKipsCySA",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InByYXBvbjNAdGVzdC5jb20iLCJwYXNzd29yZCI6IiQyYiQxMCRqNFUvbnVDLnlwZ2lNRC5Bbk5SdFZ1em53emNWVHpCd2JPTDhTaWlKa1RrbDFnck5SRGE4RyIsInR5cGUiOiJ2ZW5kb3IiLCJpYXQiOjE2MDgwNjQxMTUsImV4cCI6MTYxMDY1NjExNX0.J6uIMHHCzIhIrZWeB3nyLq7OlfZHJ7-lGhsdstW85J4"
}
```

#### Check
`POST /api/auth/refresh` 
```http
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InByYXBvbjNAdGVzdC5jb20iLCJwYXNzd29yZCI6IiQyYiQxMCRqNFUvbnVDLnlwZ2lNRC5Bbk5SdFZ1em53emNWVHpCd2JPTDhTaWlKa1RrbDFnck5SRGE4RyIsInR5cGUiOiJ2ZW5kb3IiLCJpYXQiOjE2MDgwNjQxMTUsImV4cCI6MTYxMDY1NjExNX0.J6uIMHHCzIhIrZWeB3nyLq7OlfZHJ7-lGhsdstW85J4

```

**Description**: checks the JWT. Token from `Authorization` from should be passed as Url-encoded query or `x-access-token` header

```json
{
  "message": "Token refreshed Successfully!",
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InByYXBvbjRAdGVzdC5jb20iLCJ0eXBlIjoidmVuZG9yIiwiaWF0IjoxNjA4MDY0MjkzLCJleHAiOjE2MDgwNjY5OTN9.410H5v_uipB8l7JrNDuOLoCIg__IokGlCukJB99MJO0"
}
```

## License
This project is licensed under [GPL-3.0 License](https://opensource.org/licenses/GPL-3.0).  
Copyright (c) 2020 [Samiur Prapon](https://samiurprapon.github.io/).