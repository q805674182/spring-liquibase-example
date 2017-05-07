# spring-liquibase-example
An example project for implementing Liquibase into a Spring project.

Due to Spring Boot, Liquibase will will attempt to run any changesets that have not previously been ran against your database. 

Liquibase supports several different file formats such as YAML or JSON, but I chose to use XML.

Currently, this project will create a table called "account" on a PosgreSQL database. Also, this projects API end points supports basic CRUD operations for this table.

To start the project, which will attempt to execute the Liquibase changesets, execute this command:  
`mvn spring-boot:run`

**Documentation:**  
https://spring.io/  
http://www.liquibase.org/  
https://www.postgresql.org/
