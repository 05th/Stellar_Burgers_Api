# Stellar Burgers

## Task 2: API

We should to test API endpoints for Stellar Burgers.

The API documentation will come in handy. It describes all the endpoints of the service. You need to test only those that are specified in the task. Everything else is just for context.

### User creation:

* create a unique user;
* create a user who is already registered;
* create a user and leave one of the required fields blank.


### User login:

* login under an existing user,
* login with incorrect username and password.
* Changing user data:

* with authorization
* without authorization
* For both situations, you need to check that any field can be changed. For an unauthorized user, it also means that the system will return an error.

### Creating an order:

* with authorization
* without authorization
* with the ingredients
* without ingredients
* with the wrong ingredient hash.
* Getting orders from a specific user:

* authorized user,
* unauthorized user.


### What should be done:

* Create a separate repository for API tests.
* Create a Maven project.
* Connect JUnit 4, RestAssured and Allure.
* Write tests.
* Make a report in Allure.
### ===================================================
