# Parking System
A command line app for managing the parking system. 
This app uses Java to run and stores the data in Mysql DB.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites
What things you need to install the software and how to install them

- Java 1.8
- Maven 3.6.0
- Mysql 5.7.36

### Installing
A step by step series of examples that tell you how to get a development env running:

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html

3.Install MySql:

https://dev.mysql.com/downloads/mysql/

After downloading the mysql installer and installing it, you will be asked to configure the password for the default `root` account.

### Creating database
Post installation of MySQL, Java and Maven, you will have to set up the tables and data in the database.

For this, please run the sql commands present in the `Data.sql` file under the `resources` folder in the code base.

### Configuring database access
Before running the app, you have to configure the database properties files. 

Inside the `src/main/resources/database.properties` and `src/test/resources/database.properties` files, replace the value of the keys: 

`db.url.prod` and `db.url.test` with the url of your databases,

`db.user` and `db.password` with your database credentials.

### Running App
Finally, you will be ready to import the code into an IDE of your choice and run the App.java to launch the application.

### Building the project
To build the project, go to the folder that contains the pom.xml file and execute the below command:

`mvn clean package`

This will create an executable jar into the `target` folder: `parking-system-1.0-SNAPSHOT-jar-with-dependencies.jar`.

### Testing
The app has unit tests and integration tests written.

To run the tests from maven, execute the below command:

`mvn test` for unit tests,

`mvn verify` for unit and integration tests.

### Documentation and reports
To create the project documentation, the javadoc and all the reports (Surefire, Jacoco, Findbugs, Checkstyle), you have to execute:

`mvn site`.

Then open `file:///ROOT_FOLDER_OF_YOUR_PROJECT/target/site/index.html`

### Deployment
Go to the `target` folder and execute:

`java -jar parking-system-1.0-SNAPSHOT-jar-with-dependencies.jar`.
