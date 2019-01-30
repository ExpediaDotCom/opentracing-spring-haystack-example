## Build

Required:

* Java 1.8


Build:

```bash
./mvnw clean compile
```

Run:

```bash
./mvnw exec:java -Dstart-class=magesh.sample.Backend
./mvnw exec:java -Dstart-class=magesh.sample.Frontend
```

Send Requests:

```bash
./run.sh
```

Note:  

If haystack-agent is not running locally, one can set `opentracing.haystack.agent.enabled = false` setting in the application.yml. 

To start haystack and agent locally, one can follow the instructions at [https://github.com/mchandramouli/haystack-docker#to-start-traces-and-trends](https://github.com/mchandramouli/haystack-docker#to-start-traces-and-trends)
