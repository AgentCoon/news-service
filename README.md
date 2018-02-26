# News Service

## Overview

A service fetching top news and browsing news sources from [News API](https://newsapi.org).


## API

### Documentation

The API is documented using [RAML](http://raml.org/) V1.
It's available after starting the application under http://localhost:8080/api


## Quick start

### Dependencies

#### Java
From [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
JDK 8 is required to run application

#### Maven
[Apache Maven 3.x](http://maven.apache.org/download.cgi)


### Running application

Application runs as a standard Java program with a main method.

module: news-app

main class: com.agentcoon.news.app.dropwizard.NewsApplication

parameters: server ${workspace_loc}/news-configuration/src/main/resources/local/newsservice.yml

Application is running under http://localhost:8080.

## Build

Using Maven from top level of the project
```
mvn clean package verify
```
