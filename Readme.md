# Spring Boot Exchange Rate Application


## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

3. MySQL - 8.x.x

## Steps to setup

**1. Clone the application**

```bash
git clone https://github.com/callicoder/spring-boot-flyway-example.git
```

**2. Create Mysql database**
```bash
create database exchange_app
```
+ Flyway scripts are under `src/main/resources/db.migration/`



**3. Change mysql username and password as per your installation**

+ open `src/main/resources/application.properties`

+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation


**4. "From" & "To" Currency is mentioned in application.properties file

+ open `src/main/resources/application.properties`

+ change `exchange.rate.from.currency` and `exchange.rate.to.currency` as per your needs


**5. Build and run the app using maven**

```bash
cd spring-boot-flyway-example
mvn package
java -jar target/flyway-demo-0.0.1-SNAPSHOT.jar
```

You can also run the app without packaging it using -

```bash
mvn spring-boot:run
```

**6.1 API to fetch latest exchange rates**
+ http://localhost:8080/exchange-rate/fx
```
The above API fetches data from external url only if the data for current date is not present in DB
```
**Output**
`
{
"sourceCurrency": "USD",
"exchangeRates": [
{
"conversionDate": "2024-07-25",
"targetCurrency": "CZK",
"rate": 23.398
},
{
"conversionDate": "2024-07-25",
"targetCurrency": "EUR",
"rate": 0.92157
},
{
"conversionDate": "2024-07-25",
"targetCurrency": "GBP",
"rate": 0.7767
},
{
"conversionDate": "2024-07-25",
"targetCurrency": "JPY",
"rate": 152.63
}
]
}
`


**7. API to fetch exchange rates for target Currency**
+ http://localhost:8080/exchange-rate/fx/{targetCurrency}
```
The above API fetches data for one currency from external url only if the data for that last 3 days is not present in DB
```
**Output**
`
{
"sourceCurrency": "USD",
"fromDate": "2024-07-24",
"toDate": "2024-07-26",
"exchangeRates": [
{
"conversionDate": "2024-07-24",
"targetCurrency": "CZK",
"rate": 23.438
},
{
"conversionDate": "2024-07-25",
"targetCurrency": "CZK",
"rate": 23.398
},
{
"conversionDate": "2024-07-26",
"targetCurrency": "CZK",
"rate": 23.399
}
]
}
`