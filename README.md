# api comparator base64

## Provide 3 http endpoints

/v1/diff/<ID>/left 
/v1/diff/<ID>/right
/v1/diff/<ID>

## The solution was based on SpringBoot and JDK8

* run 'mvn clean install' to execute all of the tests
### API

* The api return HTTP status code 200 (OK) when evertihing goes ok
* And when there are Errors return HTTP status code 500 (Internal server error)
