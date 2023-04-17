# Bolk Order API

### Table of contents:
#### 1. Introduction
#### 2. Project description
#### 3. Interface Description
#### 4. Running the application
#### 5. Interface functionality

### Introduction:

The purpose of this project was to help Bolk company resolve one of their problems in the warehouse.

If the order has less than 13 pallets, then the warehouse employee should measure all the pallets, write
down the information and then bring to the management, for a management to manually process an order and send to a distributor
like DHL or DPD. 

### Project description:

In order to help Bolk with their problem, it was decided to come up with an Order API or simply a web interface,
which would optimise the time-wasting process of gathering data, then bringing it to the management... 

Now, the employee doesn't need to care about writing down and delivering the measurements or other data related to an order,
everything will be in one place. 

### Interface description:

The interface works in the following way:

1. It receives the data from Bolk database and provides the overview of the list of orders
> **Note:**
> Unfortunately, Bolk couldn't provide us the access to their database, so for the prototype it was decided to use local database
2. According to the role (Employee/Admin), the interface would have different options to work with data, let's consider depending on a role:
3. **_Employee_**: this role is dedicated for a warehouse employee, who's primary task is to measure the pallets (length, width, height, etc.), and then save this data to the database. For this purpose we've created a form, where the employee can easily provide all the necessary data for each unit per order, and then simply submit the data.  No other action is provided.
4. **_Admin_**: this role is dedicated strictly to the management of a warehouse. Admin role is granted the same actions as employee, but admin can also edit the order, edit the saved units of order, delete order, delete units, register new employees.
> **Note:**
> For the sake of security it was decided for admin to register people _only_ with 'employee' role.

This is a quick overview to provide an understanding of the purpose of the interface and its functionality. In the next section, a more detailed description will be provided.

### Running the application:

In order to run the application, multiple steps are needed to be made. 
> **Note:**
> Since this is a prototype and the actual application is not deployed, you will need to install several applications on your computer.

In the list below, you will find a guide of how to run and test the application.

1. In order to run the application, since it is a Java application, you will need the following things to be installed:
   - Java related:
      - [JDK](https://www.oracle.com/java/technologies/downloads/) to install Java on your machine.
      - To run Java applications install IDE [Intellij Ultimate](https://www.jetbrains.com/idea/promo/?source=google&medium=cpc&campaign=9736965250&term=intellij%20idea&content=602143185826&gclid=CjwKCAjw0N6hBhAUEiwAXab-TdkqdCdxwmoTHhoFwPPL2iKFJGwLzJShBXakDdxmf7YilmiXTk9uQBoCUbIQAvD_BwE).
      - >**Note:** it is **_extremely_** important to install IDE with running Server functionality, since this web app will run on local server.
   - Database related:
      - Install [PostgreSQL](https://www.postgresql.org/download/) for database to work on your local machine.
      - Install [pdAdmin4](https://www.pgadmin.org/download/) to work with database. Create a new database with any database name of your preference, username and password.
      - >**Note:** **_Remember_** the credentials that you have entered when created new database, namely name of db, username and password. You will need them later in the project to connect and use database.
   - Follow the instructions on websites to install the aforementioned apps.
2. When you are all set up with installed apps, you need to open the project file in Intellij. 
3. Follow `bolk/src/main/resources` folder and find `application.yml`. There you will need to fill in 3 parameters.
   - ```username: ```_enter your username_
   - ```password: ```_enter your password if you provided any_
   - ```url: ```_enter your database after jdbc:postgresql://localhost:5432/_ url.
   - >Example: jdbc:postgresql://localhost:5432/postgres
4. In the same folder find data.sql file. This is a DDL source file with hardcoded data to test the application.
   - To run this DDL source you have to connect to your local database with Intellij. Find Database tab in Intellij, find `+` symbol, find PostgreSQL, enter needed data, test connection.
   - After that, navigate to data.sql. There you will be able to run it and save hardcoded data into the database.

Now you are all set up. Navigate to `src/main/java/bolk_app` and find `Main.java` file.

Open the file and click on a green triangle to run the application.
The next thing to do is to open any browser of your preferences and enter `localhost:8080` in the search bar. If everything was done correctly, 
you will be navigated to the login page. As you may have noticed, when running `data.sql`, you didn't save users' information
into the database. In order to save users' data, there are two ways. For testing purposes, the easiest option is to:
1. Navigate to `src/main/java/bolk_app/reg_login/services` and find `LoginService.java`. Comment out lines 42-45. To comment out a line
two slashes should be placed in front of the line.
>**Should look like this**:
```
//        String passwordEncoded = PasswordEncoder.getPasswordEncoded(password);
//        if (!user.getPassword().equals(passwordEncoded)) {
//            throw new IllegalStateException("wrong password");
//        }
```
2. Open Database tab, on the same line where you found '+' symbol find a console symbol
   (symbol after 2 blue arrows). Open the console. Copy and paste these lines:
```
INSERT INTO users (id, email, name, password, role, token)
VALUES
(1, 'emp@gmail.com', 'name1', '12345', 'EMPLOYEE', ''),
(2, 'admin@gmail.com', 'name2', '1234', 'ADMIN', '')
```
3. Run the console. Now rerun the application, try to log in. Try both log in with `emp@gmail.com`
 and `admin@gmail.com`.

The other option of adding credentials data for employee and admin will help you keep the password to be hashed in the database, but it will
require more steps to perform:
1. Download [Postman](https://www.google.com/search?client=safari&rls=en&q=downloaad+postman&ie=UTF-8&oe=UTF-8)
2. Run the application bolk app (with `Main.java`)
3. Open Postman
4. In Postman open the window to make requests (you should see [this](https://www.google.com/search?q=postman&client=safari&rls=en&source=lnms&tbm=isch&sa=X&ved=2ahUKEwi-_-mYjqz-AhU1gv0HHThWAgsQ_AUoAXoECAIQAw&biw=1440&bih=795&dpr=2#imgrc=NtspD__aZyuGJM) window)
5. Choose POST method to make request, and paste this code into 'Body' 
```
{
    "name" : "Michael",
    "email" : "emp@gmail.com",
    "password" : "12345"
}
```
6. Paste this `localhost:8080/register` into `url` field
7. Click send to send request
   - If you did everything correct, you will receive email as a response, if you made a mistake, you will see error message
8. You've added employee email, now paste below code instead of employee's one and send request one more time
```
{
    "name" : "John",
    "email" : "admin@gmail.com",
    "password" : "12345"
}
```
9. If for both emails you've got email as a response, now open the Intellij and then open the console in Database tab.
10. Paste this code into console and run it, you should see table of users as a response.
```
SELECT * FROM users
```
11. Look at the column `id` of the row where the email is `admin@gmail.com`. Remember this `id` value
12. Open the console again. Paste this and insert your `id` from the previous step instead of a question mark `?` and run the console:
```
UPDATE users
SET role = 'ADMIN'
WHERE id = ?
```
13. Now you have two emails, for employee and for admin, with their passwords being hashed.

>**Note**:
> Regarding the css styling. This project uses css framework called Tailwind. In order to use it and have a beautiful styling, follow [this](https://tailwindcss.com/docs/installation) link to check for its documentation and guide of how to install it.
### Interface functionality:

As it was said earlier, the application has 2 roles, namely 'Employee' and 'Admin'.

1. If you logged in as 'employee' with emp@gmail.com/12345 in login page, you should be redirected to the main page with overview of the orders
2. There you should see `ALL`, `PENDING`, `FINISHED`, `LOGOUT` tabs, all of them provide functionality according to its names. 
3. `PENDING` and `FINISHED` are statuses of orders
4. As you logged in as employee, you are able only to edit `PENDING` orders.
5. By clicking on `Edit` button you will be redirected to the page with order's form, where you need to provide measurements for pallets. As you enetered measurements, click on `Next` button until you save all units. Then click `Submit` to save units for order.
6. You will be redirected to overview page again. 

That is all that user with `EMPLOYEE` role can perform. 

Now, let's consider functionalities that `ADMIN` has.

1. `ADMIN` can perform the same actions as `EMPLOYEE`, namely saving `PENDING` orders.
2. Apart from it, `ADMIN` can register new employees with `EMPLOYEE` role. This can be done by clicking on `REGISTER` tab in the overview page.
3. Moreover, `ADMIN` can edit pallets for orders with `FINISHED` status. Click on `Edit` button to do this.
4. Lastly, `ADMIN` can delete both orders and units. If he deletes orders, all units associated with this order will be deleted as well.

>**Note**:
> When the user (both `ADMIN` and `EMPLOYEE`) logs in, he will receive a token. This token expires the same date at 10pm (can be changed to the preferred value). While the app is running, this token will be checked every hour, if it has expired or not, If the token has expired, the user be automatically logged out and navigated to login page. However, the token is not checked for being expired on form pages, for a user to finish editing orders/units and not interrupting this process. 
