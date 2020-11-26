# MessageProxy

This app listens for HTTP on 8080 port for distributed sensors data.
It emulates sending data to a backend by appending strings to *sensor.txt* file.
You can revoke write permissions to this file emulate backend unavailability.

## Testing MessageProxy

* build & run app: $mvn spring-boot:run
* $curl http://localhost:8080/sensor?data=success
* verify that *sensor.txt* is created in working directory with passed *success* string
* fail backend: $chmod -w sensor.txt
* $curl http://localhost:8080/sensor?data=error1
* $curl http://localhost:8080/sensor?data=error2
* app should warn in log it failed to send data with consequent retries
* fix backend: $chmod +w sensor.txt
* on next retry new sensor strings *error1* and *error2* should be appended to *sensor.txt*   
