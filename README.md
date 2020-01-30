# Dog Breed
An API to retrieve a random dog breed.

## Getting Started

* Clone the repository using Git:  
```git clone https://github.com/markcardamis/dog-breed.git```
* Change into the directory using ```cd dog-breed```
* Input your AWS S3 credentials in application.properties file ```vim src/main/resources/application.properties```
* Make sure the tests pass ```./gradlew clean test```
* Run the program using ```./gradlew bootRun```
* Make a POST request to save a dog ```http://localhost:8080/api/v1/dog```
* Make a GET request to get all dogs ```http://localhost:8080/api/v1/dog```
* Make a GET request to get a dogs ```http://localhost:8080/api/v1/dog/{id}```
* Make a GET request to search ```http://localhost:8080/api/v1/dog/search?name={query}```
* Make a DELETE request to remove a dog ```http://localhost:8080/api/v1/dog/{id}}```