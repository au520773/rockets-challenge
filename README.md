# rockets-challenge

## Brief explanation

This service is made in java with Spring Boot. 
It handles incoming rocket messages in the form of POST requests, and publishes them to 
Apache Kafka which is used as an event streaming platform.
The rocket service also acts as consumer of these events, and when new data is received it is stored in a local database.
The database is an in memory H2 database for simplicity, i.e. it does not persist data between runs.
The rocket service exposes some endpoints for getting the latest rocket data, which it fetches from the db.

## How to run locally

Download Apache Kafka:
https://dlcdn.apache.org/kafka/3.2.0/kafka_2.13-3.2.0.tgz

### Starting Kafka
Open command line and cd into kafka_2.13-3.2.0 folder.
Run the following commands in that order in separate shells to start zookeeper and Kafka server.

On windows:
1. bin\windows\zookeeper-server-start.bat config\zookeeper.properties
2. bin\windows\kafka-server-start.bat config\server.properties

On macOS:
1. ./bin/zookeeper-server-start.sh config/zookeeper.properties
2. ./bin/kafka-server-start.sh config/server.properties

### Starting rocket service
Run the rockets-service.jar in the target project folder
1. java -jar ./rockets-service.jar

Note: this requires installation of java 17. 

JAVA_HOME environment variable should point to java 17 jdk.

### Starting test program
Run test program with http against port 8080.
1. ./rockets launch "http://localhost:8080/messages" --message-delay=1000ms --concurrency-level=1

## Rest services provided by this BFF
| **Service endpoints    | **Verb** | **Description**                                                                                                            |
|:-----------------------|:---------|:---------------------------------------------------------------------------------------------------------------------------|
| /messages              | POST     | Publish a rocket message containing metadata and a messsage                                                                |
| /rockets <sup>1)</sup> | GET      | Fetches a list of all rockets in the system                                                                                |
| /rockets/{channel}     | GET      | Fetches a single rocket from its channel                                                                                   |
| /h2-console            | GET      | Run in browser in order to inspect and query db.<br/> Login credentials are listed in application.properties project file. |

<sup>1)<sup> optional sortBy query parameter allows for sorting based on rocket properties. Always sorting in ascending order, example: /rockets?sortBy=speed