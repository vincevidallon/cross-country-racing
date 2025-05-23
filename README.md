# SENG201 2025 Project: Cross Country Racing
The Cross Country Racing game is a JavaFX-based application made for the SENG201 project. The primary focus of
this application is to make a game in which players are able to buy, sell, maintain, and race vehicles with the aim
of accumulating the highest amount of prize money possible over a selected number of days. 


## Authors
- Sky Harrington-Baker
- Vince Vidallon
## Prerequisites
- JDK >= 21 [click here to get the latest stable OpenJDK release (as of writing this README)](https://jdk.java.net/21/)
- *(optional)* Gradle [Download](https://gradle.org/releases/) and [Install](https://gradle.org/install/)


## What's Included
This project contains the following contents:
- Source code and resources for the application
- JavaFX dependencies and configuration
- JUnit 5 Unit Tests
- ZIP archive with the following contents:
  - UML Use Case Diagram (PNG)
  - UML Class Diagram (PNG)
  - Project Report (PDF)
  - Built JAR file


## Importing Project (Using IntelliJ)
IntelliJ has built-in support for Gradle. To import the project:

- Launch IntelliJ and choose `Open` from the start-up window.
- Select the project and click open
- At this point in the bottom right notifications you may be prompted to 'load gradle scripts', If so, click load


## Run Project 
1. Open a command line interface inside the project directory and run `./gradlew run` to run the app.
2. The app should then open a new window, this may not be displayed over IntelliJ but can be easily selected from the taskbar

## Build and Run Jar
1. Open a command line interface inside the project directory and run `./gradlew jar` to create a packaged Jar. The Jar file is located at build/libs/vvi29_sha378_CrossCountryRacing.jar
2. Navigate to the build/libs/ directory (this can be done with `cd build/libs`)
3. Run the command `java -jar vvi29_sha378_CrossCountryRacing.jar` to open the application.

## Run Tests
1. Open a command line interface inside the project directory and run `./gradlew test` to run the tests.
2. Test results should be printed to the command line

