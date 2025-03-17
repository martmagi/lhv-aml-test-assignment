# LHV AML Test Assignment

The description for test assignment can be found [here](https://recruit-main.s3.eu-north-1.amazonaws.com/production/lhv.SCui7v2qyQ/2bWj6A3ApjvfaSx7/lhv-aml.pdf).

### Running

Current implementation covers the requirements in `BlacklistNameMatcherTest`, which can be run through an IDE or command line with\
`./gradlew test --tests com.assignment.matcher.BlacklistNameMatcherTest`
The project is configured to use JDK version `21`.

### TODO

List of needed improvements if this were to be a real microservice.

* Performance tests in case of large blacklist
* Properly structure the packages for code maintainability purposes
* Add batch import of blacklist names
* Add batch validation of names
* Add error handling
* Add logging
* Add CI/CD scripts
* Improve test coverage
  * Add E2E tests
  * Import blacklist from file, currently hardcoded
* Add static code analysis etc OWASP/ErrorProne/SpotBugs
* Add observability e.g. Prometheus
