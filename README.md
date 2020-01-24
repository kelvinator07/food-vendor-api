# Byteworks Food Vendor API Assessment

## Setup Instructions

### This application uses Swagger UI and H2 database all configured for easy testing just open this URL `http://localhost:8080/swagger-ui.html` after running application to test end Points
    
Use this login details if you get a prompt... User: `kelvinator` Password: `password-hash`.


1. Clone this repository and open in your favourite IDE.


2. Open the `application.properties` file under the resources directory.

    Replace properties for `spring.mail.username` to your email and `spring.mail.password` to your password respectively.

    Note that the application uses H2 (Testing purposes) and PostgreSQL for Production
    
3. Clean and install project using `mvnw clean install` command on your terminal 

4.  Run Application using `mvnw spring-boot:run` command on your terminal.
    
5. Open Swagger UI URL `http://localhost:8080/swagger-ui.html` to test end Points
    
    Use this login details if you get a prompt... User: `Kelvin1` Password: `password-hash`.
    
    For H2 (Default in memory Database for testing)
    
    Now, once we get our Spring application running, we can navigate at the `http://localhost:8080/h2-console`.
    
    JDBC URL: jdbc:h2:mem:testdb
        
    For PostgreSQL

    Create a `foodvendor` schema in your Postgres database.
    
    Update the `application.properties` file under the resources directory with your postgres database details using the format below:



   ```
   spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
   spring.jpa.hibernate.ddl-auto=create-drop
   spring.jpa.show-sql=true
   
   spring.datasource.url=jdbc:postgresql://localhost:5432/foodvendor?useSSL=false&serverTimezone=GMT
   spring.datasource.username=<yourusername>
   spring.datasource.password=<yourpassword>
   spring.datasource.driver-class-name=org.postgresql.Driver
   ```
   
   You do not need to create the database tables as hibernate does that.
   
   Run the queries below to get some meals to work with.
   
       
   ```
   insert into meal (id, name, price) values (1, 'Rice', 500.0);
   insert into meal (id, name, price) values (2, 'Beans', 200.0);
   insert into meal (id, name, price) values (3, 'Chicken', 900.0);
   insert into meal (id, name, price) values (4, 'Yam', 300.0);
   insert into meal (id, name, price) values (5, 'Amala', 400.0);
   insert into meal (id, name, price) values (6, 'Salad', 300.0);
   ```

## API Endpoints

<table>
<tr><th>HTTP VERB</th><th>ENDPOINTS</th><th>DESCRIPTION</th></tr>
<tr><td>User Controller</td></tr>
<tr><td>POST</td><td>/api/v1/auth/register</td><td>Creates user account</td></tr>
<tr><td>POST</td><td>/api/v1/auth/login</td><td>Login to application</td></tr>
<tr><td>GET</td><td>/api/v1/users</td><td>Get all users</td></tr>
<tr><td>GET</td><td>/api/v1/users/{id}</td><td>Get user by ID</td></tr>
<tr><td>Order Controller</td></tr>
<tr><td>GET</td><td>/api/v1/meals</td><td>Get all available meals</td></tr>
<tr><td>GET</td><td>/api/v1/orders</td><td>Get all orders</td></tr>
<tr><td>POST</td><td>/api/v1/orders</td><td>Make an order</td></tr>
<tr><td>GET</td><td>/api/v1/orders/{id}</td><td>Get order by ID</td></tr>
</table>