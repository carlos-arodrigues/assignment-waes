# Assignment Scalable Web

Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints 
	```<host>/v1/diff/<ID>/left
	```<host>/v1/diff/<ID>/right
The provided data needs to be diff-ed and the results shall be available on a third end point <host>/v1/diff/<ID>
The results shall provide the following info in JSON format
- If equal return that
- If not of equal size just return that
- If of same size provide insight in where the diffs are, actual diffs are not needed.
	So mainly offsets + length in the data
Make assumptions in the implementation explicit, choices are good but need to be communicated


### Prerequisites

**Having a MySQL instance running on localhost:3306**

* Run `startup.sql` script there. 
* User the **"root"** user and **password** as password :P.
* Or you can change it on `application.properties` (spring.datasource.username e spring.datasource.password)


## Running the tests

* To test just execute as JUnit the classes 
-DiffUnitTest for rule tests.
-DiffIntTest for integration tests.

## Deployment

* Run as Java Application the class AssignmentWaesApplication
* Or run the jar generated on target folder

## Built With

* [Spring Boot](https://spring.io/docs) - The framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Swagger2](https://swagger.io/docs/) - To doc endpoint's api

## Author

* **Carlos Antonio Rodrigues** - [carlos-arodrigues](https://github.com/carlos-arodrigues)

## Suggestions for improvement

* Configure cache on requests to get insights
* Could be load balanced by using of Spring Cloud
* Other options will depends of the constraints.

