# Release Manager
#### A service that updates system version based on deployed services.

---
### API
`POST: /deploy`
</br>
`body: {
    serviceName: String,
    serviceVersionNumber: int
}`
</br>
`Response: SystemVersionNumber: int`

`GET: /services`
</br>
`request params: {
    systemVersion: int
}`
</br>
`body: [{
    name: String,
    version: int
}]`
---
### How to run
`./gradlew bootRun` will run the service on the port `8080`

---
### Request examples:
To get the system version number: 
```
curl --location --request GET 'localhost:8080/services?systemVersion=1'
```
To send a deployment notification and update the system version number:
```
curl --location --request POST 'localhost:8080/deploy' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "B",
    "version": 6
}'
```
