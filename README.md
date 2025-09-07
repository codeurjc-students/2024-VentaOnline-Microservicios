# 2024-VentaOnline-Microservicios

| Name and Lastname | Email | GitHub account |
| --- | --- | --- |
| Maria Amparo Alami Mochi | ma.alami.2020@alumnos.urjc.es | MaAlami2020 |

#### Team coordination zone: [Jira](https://webapp1.atlassian.net/jira/software/c/projects/VM/boards/2)

# Main aspects of the web application

## **1. Entities**
- User:
    - id
    - name
    - password
    - passwordConfirmation
    - Direction --> 1:1
    - List< Item > --> N:N
    - ShoppingCart ---> 1:1
    - ItemToBuy ---> 1:M
    - Order ---> 1:M
- Direction:
    - id
    - street
    - number
    - zip code
    - city
    - User ---> 1:1
- Item:
    - id
    - name
    - description
    - stock
    - price
    - List< ItemToBuy > ---> 1:M
    - List < User > ---> N:M
    - Type (JEANS, T-SHIRTS, DRESS)
    - Gender (WOMAN, MAN, UNISEX)
- ItemToBuy:
    - id
    - Item ---> N:1
    - count
    - User ---> N:1
- ShoppingCart:
    - id
    - User
    - totalCost
    - List< ItemToBuy > ---> 1:M
- Order:
    - id
    - List< ItemToBuy > ---> 1:M
    - User ---> N:1
    - totalCost
    - creationDate: Date
    - State (PENDING, IN PROGRESS, CONFIRMED)

## **1.1. Entities relationships**

- An order is generated from products
- An user uses a shopping cart
- An user generates an order
- Items can be inserted in a shopping cart
- An user defines an address which an order will be sent in
- More than an unit of a single item can be found in a shopping cart
- An user has favorite items

## **1.2. Entities diagram**

![entities_diagram](/images/Model3.jpg)

## **2. User permissionss**

### All users:
1. As an user, it may be able to search products, so on the home screen there will be a dialog box where I can enter some information about an item, and I will be shown all the filtered articles after clicking on a search button.

### Anonymous user:
1. As an anonymous user, I must be able to register in the app. To do this, I must be able to click on a link or button from the home screen that redirects me to a page where I can insert my personal data, and have a registration button to confirm my registration in the application after filling in the fields.

2. As an anonymous user, I should be able to see an error message or be redirected to the error page in case there are empty dialog boxes or the information in some field does not meet a certain standard (e.g. password character range) 

### Registered user:
1. As a registered user, I must be able to log in to the app. To do this, I must be able to click on a link or button from the home screen that redirects me to a page where I can insert my email and password, and have a login button to confirm my operation in the application after filling in the fields. 

2. As a registered user, I must be able to see a error message in case the information in some filed is not correct.

3. A registered user must be able to add a product to a shopping cart, so that by clicking on the image of an item they are redirected to a screen where information about it is shown and the user can select color, size, quantity, and click on a button to confirm the insertion in the cart.  As soon as you insert a single product into the cart, you have 60 minutes to generate the order or continue inserting more products; After that time, the basket is automatically emptied if it is not empty, and the stock of product availability is increased. 

4. As a registered user, you should be able to remove a product from my shopping cart. To do this, from the home screen I must be able to click on a shopping cart icon and be redirected to a page where I am shown each item I added to the cart with a button or delete icon of a product in the cart, the sum of all the products in the basket updated after a deletion or addition. 

5. As a registered user, I must be able to buy, so that, having added products to my cart in the cart, I have a "confirm" button that after pressing it redirects me to the order page where I can see the generated order along with the other existing ones, ordered by date from most to least recent, and with a button next to each one to be able to download it in PDF or send it by email.

6. As a registered user, I must be able to modify the information of my profile, so that from the home screen I must have a button that redirects me to a page where my name, email, profile picture, address appears; and I am permitted to modify any information, including the password, even if I am not shown such information. 

7. As a registered user, I must be able to see all the orders I have made, that is, I must be redirected to a page where each one will be displayed, ordered by date from most to least recent, and with a button next to it to be able to download it in PDF or send it by email, after pressing on a button from the home screen.

### Registered and administrator user:
1. As a registered user and administrator, I must be able to log out, so from the home screen I must have a button to perform this action.

### Administrator user:
1. As an admin user, I must have a button or link that redirects me to a page where I am shown different graphs with statistics on sales (e.g. bar chart of the number of items sold grouped by size, bar chart with the profits in (€) generated from the items sold grouped by product type)

2. As an admin, I can view orders created by customers 

3. As an administrator I can see the details of an order (all its fields) 

4. As an administrator, I must be able to change the status of an order

5. As a user, I must be able to see the products offered by the store when the app is launched. 

    - Also, as an anonymous user, I should not be redirected to any page if I click on a product 

    - In addition, as a registered user, I must have a button or click on the image of a product in order to buy it.  

    - Also, as a registered user, I should be able to see the products I have selected as favorites 

6. As an administrator user, I must have a button to add a new product to the store, so that by clicking on it, I will be redirected to a page where, for a product, I will define its name, description, available stock, price, image, size, color, gender, category; and can press on an add button, so that any user can see it on their home screen.

7. As an admin user, I must be able to remove a product from the store, so next to each item there should be a delete button or icon, and after clicking on it, the item is no longer available in the store and is not visible to any user. 

8. As an administrator user, I must have a button to edit the information of a product so that, from the home screen, having clicked on a button or image of a product, I am redirected to the page where the description of the product is shown, I can modify its information, apart from showing a message as an alert when there are 5 or less stocks available of the item.  and save your changes to update the product. 
## **3. Navigation diagram**

Anonymous and registered user navigation:

![navigation_I](/images/navigation_diagram.PNG)

Administrator user navigation:

![navigation_I](/images/navigation_diagram_II.PNG)

## SETTING
keytool -genkey -keyalg RSA -alias tienda -keystore keystore.jks -storepass password -validity 360 -keysize 2048

EXECUTE THIS CODE ON THE PROJECT WHERE THE KESTORE.JKS IS LOCATED
keytool -exportcert -alias tienda -keystore keystore.jks -rfc -file tienda.crt 

EXECUTE THIS CODE ON THE GATEWAY PROJECT
keytool -import -alias tienda -file tienda.crt -keystore gateway-truststore.jks

HOW DO I DOCUMENTED TE API REST

1. added plugins and dependency on the pom.xml file,
2. wake up the project with mvn spring-boot:run
3. download the following file: https://localhost:8443/v3/api-docs.yaml and include it on the project path backend/api-docs
4. execute the command mvn generate-source where the pom.xml find. In my case, I move the pom.xml file to the path backend and from that route I execute the command mentioned

BUILDING
(How to generate the .jar file)
- type on the root the project of the terminal --> mvn package

DOCKER AND DOCKER COMPOSE
(Service store)
create the network (if it does not exists)
0. docker network create springboot-mysql-net
Add to the springboot-mysql-net network the database
1. docker run --name mysqldb2 --network springboot-mysql-net -e MYSQL_ROOT_PASSWORD=Mundialmente1 -e MYSQL_DATABASE=items -e MYSQL_PASSWORD=Mundialmente1 -d mysql:8.0.33
check out the insertion
2. docker exec -it ebd bash

2. docker run --name redisdb --network springboot-mysql-net -p 6379:6379 -d redis:latest
create the image of the website
3. docker build -t springbootmysql .
Add to the springboot-mysql-net network the image of the website
4. docker run --name store-container --network springboot-mysql-net -p 8443:8443 -d springbootmysql
check connectivity
5. docker network inspect springboot-mysql-net

(Service gateway)
0. docker network create springboot-mysql-net
1. docker build -t springbootgateway .
2. docker run --name springboot-container1 --network springboot-gateway-net -p 8442:8442 -d springbootgateway
3. docker network inspect springboot-gateway-net

send the image a docker hub
4. docker tag springbootmysql:latest maalami/springbootmysql:latest
5. docker push maalami/springbootmysql:latest


NETWORKS

1. springboot-mysql-net

IMPORT DB FROM LOCALHOST TO GKE DB
0. create db
gcloud sql databases create onlinestore --instance=onlinestore-mysql
0. create bucket for the db
gcloud storage buckets create gs://onlinestore-bucket   --location=us-central1
0. copy the db items table to gke db bucket
gcloud storage cp items.sql gs://onlinestore-bucket/
1. Asigne admin roles to the admin
gcloud projects add-iam-policy-binding gothic-avenue-470908-m7   --member="serviceAccount:p939569016236-d1a8i9@gcp-sa-cloud-sql.iam.gserviceaccount.com"   --role="roles/storage.objectViewer"
1. create instance for the db
gcloud sql databases create onlinestore --instance=onlinestore-mysql
2. import db item table to gke database bucket
gcloud sql import sql onlinestore-mysql gs://onlinestore-bucket/items.sql --database=onlinestore
3. credentials for the cloud sql for the project id: gothic-avenue-470908-m7 -> google cloud > IAM > cuentas de servicio > claves > "agregar clave" -> devuelve archivo .json
kubectl create secret generic cloudsql-instance-credentials   --from-file=credentials.json=/gothic-avenue-470908-m7-c89af71c9378.json
