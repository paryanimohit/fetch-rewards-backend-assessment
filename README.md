# Fetch Rewards Backend Assessment 

(https://fetch-hiring.s3.us-east-1.amazonaws.com/points.pdf)

A Rest API developed with Spring Boot framework that deals with handelling user transactions for Fetch Rewards. The major functionalities include:
```
1. Adding Transactions for a specific Payer and Date.
2. Spending the points available for each payer based on the oldest transaction.
3. Return Payer points balance.
```
The following are pre-requisites to run the application:

1. Java 16 
```
https://www.oracle.com/java/technologies/javase-jdk16-downloads.html
```
2. MAVEN 
```
https://maven.apache.org/download.cgi (For a guide to install MAVEN, refer to (https://youtu.be/RfCWg5ay5B0)
```
3. POSTMAN 
```
https://www.postman.com/downloads/
```
Installation Instructions for the Web-Service:
```
1. Clone the repository

git clone https://github.com/paryanimohit/fetch-rewards-backend-assessment.git
```
```
2. Navigate to the rest-service folder through command line
```
```
3. Run command 

mvn clean install
```
```
4. Run command 

java -jar target/rest-service-0.0.1-SNAPSHOT.jar
```
```
5. The server can be accessed at

localhost:8080/api/transactions
```
```
6. The H2 Database can be accessed at 

localhost:8080/h2
```
```
7. To login to database, JDBC URL is located on Command Line as: 

H2 console available at '/h2'. Database available at 'jdbc:h2:mem:46fbc39c-e5a8-42e2-8f31-6f0280a50c2c'. 

(This is a dynamic URL and is changed each time the server is run)
```
The following routes can be run on POSTMAN:

1. To add Transactions for specific payer and date: 
```
localhost:8080/api/transactions POST

Example JSON : { "payer": "DANNON", "points": 1000, "timestamp": "2020-11-02T14:00:00Z" }
```
Example Output/Response : 
```
{
    "payer": "DANNON",
    "points": 1000,
    "timestamp": "2020-11-02T14:00:00.000+00:00"
}
```
2. To Spend points:
```
localhost:8080/api/transactions/points POST

Example JSON: { "points": 5000 }
```
Example Output/Response : 
```
[
    {
        "payer": "UNILEVER",
        "points": -200
    },
    {
        "payer": "DANNON",
        "points": -100
    },
    {
        "payer": "MILLER COORS",
        "points": -4700
    }
]
```
3. To get all Payers and Balances
```
localhost:8080/api/transactions/points GET
```
Example Output/Response:
```
{
    "UNILEVER": 0,
    "MILLER COORS": 5300,
    "DANNON": 1000
}
```
4. To get all transactions
```
localhost:8080/api/transactions GET
```
Example Output/Response: 
```
[
    {
        "payer": "DANNON",
        "points": 1000,
        "timestamp": "2020-11-02T14:00:00.000+00:00"
    },
    {
        "payer": "UNILEVER",
        "points": 200,
        "timestamp": "2020-10-31T11:00:00.000+00:00"
    },
    {
        "payer": "DANNON",
        "points": -200,
        "timestamp": "2020-10-31T15:00:00.000+00:00"
    },
    {
        "payer": "MILLER COORS",
        "points": 10000,
        "timestamp": "2020-11-01T14:00:00.000+00:00"
    },
    {
        "payer": "DANNON",
        "points": 300,
        "timestamp": "2020-10-31T10:00:00.000+00:00"
    },
    {
        "payer": "DANNON",
        "points": -300,
        "timestamp": "2021-04-29T00:46:21.247+00:00"
    },
    {
        "payer": "UNILEVER",
        "points": -200,
        "timestamp": "2021-04-29T00:46:21.247+00:00"
    },
    {
        "payer": "DANNON",
        "points": 200,
        "timestamp": "2021-04-29T00:46:21.247+00:00"
    },
    {
        "payer": "MILLER COORS",
        "points": -4700,
        "timestamp": "2021-04-29T00:46:21.247+00:00"
    }
]
