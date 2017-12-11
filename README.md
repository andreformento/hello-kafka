# hello-kafka


### Build
```bash
$ ./gradlew clean buildDocker
```

### Run application
```bash
$ docker-compose up
```

### Producer
```bash
$ curl -X POST 'http://localhost:8082/generate-work' \
       -H 'Content-Type: application/json' \
       -w '\ntime: %{time_total}\n' \
       -d '{"id": "uuid123", "definition": "blablabla"}'
```
