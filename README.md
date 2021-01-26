#Dextra - Simple Authentication Sample

## Testing the application

To **test** the application, you can use:

- ```./gradlew check```

With this command:
- all style validations will be performed
- all unit and integration tests will be performed
- a report of tests performed will be available at: *./build/reports/tests/test/index.html*
- a test coverage report will be available at: *./build/reports/jacoco/test/html/index.html*
- a quality code report will be available at: *./build/reports/detekt/detekt.html*

## Buildind the application

To **build** the the application, you can use:

- Run ```./gradlew build``` to build the application

## Running the application

- Make sure you have JDK 11 on your JAVA_HOME env

- Run API with Gradle: ```./gradlew bootRun ```

API will be available in [http://localhost:8080]

You can check the API documentation in [http://localhost:8080/swagger-ui.html]




<div hidden>
```

@startuml authDiagram

boundary "Partner Service" as PartnerService
autoactivate on

PartnerService  -> AuthenticationController: Login as Partner
AuthenticationController  ->  AuthenticationService: POST /auth/login {clientId}
AuthenticationController <--  AuthenticationService: Partner Token
PartnerService  <-- AuthenticationController: Partner Login

PartnerService  -> AuthenticationController: Login as User
AuthenticationController  ->  AuthenticationService: POST /auth/account/login [PartnerToken] + {accountId}
AuthenticationController <--  AuthenticationService: Account specific token
PartnerService  <-- AuthenticationController: Account Login


PartnerService -> AccountController: Get Account Users as Partner
AccountController -> AccountService: Get /account/{accountId}/users + [PartnerToken]
AccountController <-- AccountService: Account Users
PartnerService <-- AccountController:  Account Users 


PartnerService -> AccountController: Get Account Invoice as User
AccountController -> AccountService: Get Account Invoice as User /account/{accountId} [AccountToken]
AccountController <-- AccountService: Get Account Invoice 
PartnerService <-- AccountController: Account Invoice

@enduml
```
</div>

![](authDiagram.svg)