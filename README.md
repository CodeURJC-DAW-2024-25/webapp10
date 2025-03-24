# webapp10
# webapp10- Preparation
## Web name
- TicketZone Fest
## Team members
|       Name           |              Email               |   Github User | 
|----------------------|----------------------------------|---------------|
|Alberto Manjón López  |	a.manjon.2022@alumnos.urjc.es   |	AlbertoML1999 |
|César Valero Bueno    |	c.valerob.2022@alumnos.urjc.es  |	Ccsar1        |
|Irene García López    |	i.garcialop.2021@alumnos.urjc.es|	irenegarccia  |
|Andrea Garrobo Guzmán |	a.garrobo.2022@alumnos.urjc.es  |	Garrobo08     |
|Natalia Méndez Barrios|	n.mendez.2022@alumnos.urjc.es	  | nataliaM4     |
## Used tools
- Trello: https://trello.com/invite/b/67a0a2b06ec66c976eff46db/ATTI16834166fedd9d33d0250a16bf8225fb595C9992/ticketzone-fest
## Entities
- User 
- Concert 
- Artist 
- Ticket
  ![Database Diagram](https://github.com/user-attachments/assets/c8531ed8-ceec-4814-afc1-2b46540b25e8)
## User permissions
- Anonymous user: View concerts
- Registered user: View concerts, Buy tickets , View tickets 
- Admin: Add artists, Add concerts, Modify artists, Modify concerts, Delete artists, Delete concerts
## Images
- User: Profile picture
- Artist: Profile picture
- Concert: Advertising poster
## Graphs 
- Bar graph: The graph will display the number of tickets sold per concert.
- Pie chart: There will be one chart for each concert, showing the range of ages of the users who purchased the tickets.
## Complementary technology
- PDFs generator
- Use of Google Maps 
## Algorithm
- Each artist will be classified by its genre. When a registered user signs in, the concerts displayed first will be organized based on the genres of concerts the user has previously purchased tickets for.
- The concerts that have sold the most tickets will appear first.

# webapp10- Practice 1
## Screenshots
![indexScreenshot](https://github.com/user-attachments/assets/af78d42a-6cea-4a6b-9a13-7b85600316a3)
- Description: main page that includes all the information about the different concerts and artists, in addition to allowing navigation to user registration, login and modification of artists and concerts if you are an administrator.

![concertInfoScreenshot](https://github.com/user-attachments/assets/ec2b5a2d-966b-4981-9725-8b9aa2de64c7)
- Description: page that includes concert information such as date, time, location, map, different diagrams as well as including participating artists.

![loginScreenshot](https://github.com/user-attachments/assets/08271d53-d12a-43d8-bc06-e78d54b97756)
- Description: page that allows the user to login in by introducing the username and password.

![registerScreenshot](https://github.com/user-attachments/assets/59484c12-38cf-4e4d-84fb-29fbaf33053e)
- Description: page that allows users to register by filling out the different fields of the form with their personal information, like their name, username, personal photo, phone number, email, password and age.

![newConcertScreenshot](https://github.com/user-attachments/assets/7092d58b-3eec-45b8-82ca-f0af7b9f310b)
- Description: page that allows the administrator to register a new concert by filling out the different fields of the form with the concert information, like the name, the artists name and information, more concert details, date, stadium price, track price time and location.

![newArtistScreenshot](https://github.com/user-attachments/assets/b29c700f-2bc2-4991-b84d-fb9b3ea61ae2)
- Description: page that allows the administrator to register a new artist by filling out the different fields of the form with the artist information, like the name and musical style.

![purchasePageScreenshot](https://github.com/user-attachments/assets/2cb1d4b8-bcd8-497f-8e6c-7208231a56d3)
- Description: page that allows the user to buy tickets for the concert by selecting, allowing the user to choose the tickets they want to buy and whether they want it to be in the stands or on the track.

![userPageScreenshot](https://github.com/user-attachments/assets/2e535d5d-6da4-46c2-b18f-929103e3c3e7)
- Description: page that includes the personal information entered by the user, as well as a history of tickets purchased with the option to download the ticket, if it has not been previously downloaded

![editArtistScreenshot](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/imagesReadme/editArtistScreenshot.png)
- Description: page that allows the administrator to modify an existing artist by changing out the different fields of the form

![editConcertScreenshot](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/imagesReadme/editConcertScreenshot.png)
- Description: page that allows the administrator to modify an existing concert by changing out the different fields of the form

## Navigation Diagram
![diagramScreenshot](https://github.com/user-attachments/assets/bcf3b361-6f70-4818-9937-f3b3f48c1bfb)


## 🚀 Execution Instructions

### 📌 Requirements
- **Java**: JDK 21
- **Maven**: 4.0.0
- **Spring Boot**: 3.4.2
- **MySQL**: 8.0.33
- **Visual Studio Code** (or other IDE)

### 🔧 Installation

#### 1️⃣ Clone the repository
To clone the project using **VSCode**, follow these steps:
- Open **VSCode**
- Go to **Source Control** (`Ctrl + Shift + G`)
- Click **Clone Repository** → **"Clone from GitHub"**
- Select your repository and choose a local folder
- Open the cloned project in VSCode

#### 2️⃣ Configure the database
- Open **MySQL Workbench**
- Click on **Database** → **Connect to Database**
- Enter the credentials:  
  - **User**: `root`
  - **Password**: `password`
- Click **OK** to establish the connection
- If the database does not exist, open the **VSCode terminal** and run:
  ```sql
  CREATE DATABASE concerts;

### ▶️ Execution
####  Run from VSCode
- Open the **Spring Boot Dashboard** in VSCode and start the application

### 🌐 Access the Application
- **Web Interface**: [`https://localhost:8443`](https://localhost:8443)

#### Test Credentials
| Role            |Username | Password  |  
|-----------------|---------|-----------|  
| Registered User | `user`  | `user`    |  
| Administrator   | `admin` | `admin`   |  
    

## Database entities diagram
![dbDiagram](https://github.com/user-attachments/assets/c6b5bf1c-4bf1-4fa4-a63f-d2d6995e5b43)


## Classes and templates diagram
![Classes Templates_diagram](https://github.com/user-attachments/assets/11da09d1-bdd2-4431-a61e-feb14b06b7ac)

## Members participation
- **Alberto Manjón López**
  - *Task done descriptions*
    - Implement controllers like: RegisteredWebController in order to add the register function; LoginWebController to add the login function; GraphicController to add the pie graphic; CustomErrorController to add the redirect to error URLs.
    - Implement functions like: Register, login, purchase tickets, pie graphic, edit concert.
    - Implement services like: UserService.
    - Implement Models like: ticket and user, and in general modify some things in all the models in order to add the functionalities.
    - Corrections in: WebController in order to add the functionalities of purchase tickets, edit concerts and some corrections; Security in general.
    - Modify templates in order to correct the web like the header, userPage, concertInfo; add error.html, editConcert.html.

| **Section**                               |                 | **Details**                                                               |
| ----------------------------------------  | --------------- | ------------------------------------------------------------------------- |
| **Most significant commits**              | **Commit 1**    | [Add login and register controller and function](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/52c0821a2f639b8ed67da6bc5fc17ce41f01a608)    |
|                                           | **Commit 2**    | [Add ticket purchasing functionality and update concert retrieval method](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/9e36c0b3899f4cd0cc97fad20ffdea701e4dfa0d)     |
|                                           | **Commit 3**    | [Add error page](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/50096b1de6aed1c435dd7a2aede5aece178626c5)     |
|                                           | **Commit 4**    | [Add edit concert](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/1c11e4c259f33c811a9c49582bda24583e8a7ad1)     |
|                                           | **Commit 5**    | [Add pie graphic](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/a642ad7afe0e00caa307c0c28699e899d2f32792)     |
| **Files that have participated the most** | **File 1**      | [WebController.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/controller/WebController.java)|
|                                           | **File 2**      | [User.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blame/main/backend/src/main/java/es/codeurjc/backend/model/User.java)|
|                                           | **File 3**      | [userPage.html](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/resources/templates/userPage.html)|
|                                           | **File 4**      | [editConcert.html](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/resources/templates/editConcert.html)|
|                                           | **File 5**      | [register.html](https://github.com/CodeURJC-DAW-2024-25/webapp10/blame/main/backend/src/main/resources/templates/register.html)|


- **César Valero Bueno**
  - *Task done descriptions*
    - Organized database entities models, their attributes, relations... Implemented features such as user registration, adding new concerts, deleting artists and concerts, and image use in concerts and users. Implemented the query for the algorithm, helped with some of the error handling, and helped fix other different issues and errors.

| **Section**                               |                 | **Details**       |
| ----------------------------------------  | --------------- | ----------------- |
| **Most significant commits**              | **Commit 1**    | [Implement posibility for registered users to have a profile picture](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/e3d74079f9d30902c7cf5423eadadfac99eddf03)|
|                                           | **Commit 2**    | [Implemented query in algorithm for recomended concerts](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/a110005d23c9af2dddcca337e77d7d7913eae88f)|
|                                           | **Commit 3**    | [Fix model entities attributes](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/19af8aa60f8a7d3bc9f1ff5733f6776150fc6277)|
|                                           | **Commit 4**    | [Refactor web controller and add /concer/{id}; create ArtistService, ConcertService, and TicketService](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/05700877121a450f902fcbbee96322941c79d7a2)|
|                                           | **Commit 5**    | [Improve error handling and validation messages in new concert form](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/03e0e5cd74293fdffe1790a49ceef8f31be46497)|
| **Files that have participated the most** | **File 1**      | [WebController.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/controller/WebController.java)  |
|                                           | **File 2**      | [Concert.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/model/Concert.java)  |
|                                           | **File 3**      | [User.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/model/User.java)  |
|                                           | **File 4**      | [RegisteredWebController.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/controller/RegisteredWebController.java)  |
|                                           | **File 5**      | [ConcertService.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/service/ConcertService.java)  |

- **Irene García López**
  - *Task done descriptions*
    - In the development of this web application, I have implemented the artist editing functionality, created the concert info model, added a PDF download feature, and integrated an iframe displaying the map for each concert. I also ensured that a "purchase successful" message appears after completing a purchase and that concert images load correctly on the index page, purchase page, and concert info page. Additionally, I added default images for missing concert and user profile pictures. I handled the entire database initialization, improved the application's responsiveness for different screen sizes, implemented most of the changes to Mustache templates, and set up the initial configuration for web security.

| **Section**                               |                 | **Details**       |
| ----------------------------------------  | --------------- | ----------------- |
| **Most significant commits**              | **Commit 1**    | [Edit artist funcionality](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/f475b12b70f7cf306504191b44475118b544010a)|
|                                           | **Commit 2**    | [Add tickets download functionality as PDF](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/63824cf7f9529745045d7d56d1a82ade2a12296a)|
|                                           | **Commit 3**    | [Success message for ticket purchase, fix profile photo in header and scrollable purchase history](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/10f34c58258ea73eab6f35e32a3e398257c7185b)|
|                                           | **Commit 4**    | [Security changes](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/96afa4efe0a6fa198ca6e5d3fd63918073df0da2)|
|                                           | **Commit 5**    | [Fix concert images and concert location](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/56a7d0878346a418ee7203c9e5cd880c66a95b37)|
| **Files that have participated the most** | **File 1**      | [WebSecurityConfig.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/5617bebc65a0129922b22b41e2576e4b65f8289e/backend/src/main/java/es/codeurjc/backend/security/WebSecurityConfig.java)  |
|                                           | **File 2**      | [WebController.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/5617bebc65a0129922b22b41e2576e4b65f8289e/backend/src/main/java/es/codeurjc/backend/controller/WebController.java)  |
|                                           | **File 3**      | [DatabaseInitializer.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/5617bebc65a0129922b22b41e2576e4b65f8289e/backend/src/main/java/es/codeurjc/backend/service/DatabaseInitializer.java)  |
|                                           | **File 4**      | [concertInfo.html](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/5617bebc65a0129922b22b41e2576e4b65f8289e/backend/src/main/resources/templates/concertInfo.html)  |
|                                           | **File 5**      | [editArtist.html](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/5617bebc65a0129922b22b41e2576e4b65f8289e/backend/src/main/resources/templates/editArtist.html)  |

- **Andrea Garrobo Guzmán**
  - *Task done descriptions*
    - I made the pagination of the website, the load more button along with the spinner animation, as well as made sure that when loading more items they had the same appearance. I also added security with the keystore tool and helped all pages to be called from port 8443. Then I implemented the Bar graph and fixed the ticket count to be used in both graphs. An error page, in case a gig was selected that didn't exist and the action to delete a gig for the admin, which my colleague later improved to work correctly.

| **Section**                               |                 | **Details**       |
| ----------------------------------------  | --------------- | ----------------- |
| **Most significant commits**              | **Commit 1**    | [fixed load more button, added security with keystore tool and changed server port to 8443](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/b49ef396c36d10d0c8f07b4ec20b89832b877118)|
|                                           | **Commit 2**    | [Bar chart and ticket count](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/faaf842a7ab4a9a6983c2c8b916634f5cd404d36)|
|                                           | **Commit 3**    | [Error page if the concert is not found](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/66a5e4d63b976b9106061f4860921649d6193f4f)|
|                                           | **Commit 4**    | [Delete concert but it always redirects me to /errore](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/4ce3ad7c2746a05c45789e4a72f5f08577e502da)|
|                                           | **Commit 5**    | [load more with AJAX](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/43361bbaf576289072e5c8f6c98855ea086e96c7)|
| **Files that have participated the most** | **File 1**      | [GraphicController.java](backend/src/main/java/es/codeurjc/backend/controller/GraphicController.java)  |
|                                           | **File 2**      | [loadMore.js](backend/src/main/resources/static/js/loadMore.js)  |
|                                           | **File 3**      | [WebController.java](backend/src/main/java/es/codeurjc/backend/controller/WebController.java)  |
|                                           | **File 4**      | [barGraphics.js](backend/src/main/resources/static/js/barGraphics.js)  |
|                                           | **File 5**      | [index.html](backend/src/main/resources/templates/index.html)  |


- **Natalia Méndez Barrios**
  - *Task done descriptions*
    - I developed the model for "Artist" and "Concert," added security restrictions to protect certain resources, and implemented GET and POST methods in the controller for adding a new artist and a new concert. Additionally, I implemented a GET method to delete an artist, with logic to prevent the removal of an artist if they are the only one associated with a concert, ensuring that a concert cannot exist without artists. Finally, I made modifications to the HTML to ensure the proper functionality of various features.

| **Section**                               |                 | **Details**                                                       |
| ----------------------------------------  | --------------- | ----------------------------------------------------------------- |
| **Most significant commits**              | **Commit 1**    | [Add delete artist functionality](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/a9013f71b1c55d39e52f1ebf0af9d86e1d08cb63)    |
|                                           | **Commit 2**    | [newArtist controller](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/a34bcaf44a9b5bbec705d9191606b78ad3558b20)     |
|                                           | **Commit 3**    | [newConcert changes](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/8e8e99e3b5d1ecf5c430972d206a7e495faf7fc9)     |
|                                           | **Commit 4**    | [artist and concert entities](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/58a577ba9f267fb111091ed7836a8dfacc8a0339)     |
|                                           | **Commit 5**    | [Add validation and required fields for new concert form inputs](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/ba6bb4d5665a49b8b255e0bc4357794c44bc160c)     |
| **Files that have participated the most** | **File 1**      | [WebController](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/controller/WebController.java)|
|                                           | **File 2**      | [Artist](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/model/Artist.java)|
|                                           | **File 3**      | [Concert](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/model/Concert.java)|
|                                           | **File 4**      | [newArtist](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/resources/templates/newArtist.html)|
|                                           | **File 5**      | [newConcert](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/resources/templates/newConcert.html)|

# webapp10- Practice 2
## API REST Documentation

## Classes and templates diagram
![updated_classesDiagram](https://github.com/user-attachments/assets/c49d41b6-10e7-44e1-bd49-553319b3a879)

## 🚀 Docker app execution Instructions
 
---

# 🚀 **Execution Instructions**  

---

## 📌 **Requirements**  
- **Docker**: 27.5.1 
- **Docker Compose**: v2.32.4
- **Java**: JDK 21  
- **Maven**: 4.0.0  
- **Spring Boot**: 3.4.2  
- **MySQL**: 8.0.33  
- **Visual Studio Code** (or any other IDE)  

---

## 🔧 **Installation & Setup**  

### 1️⃣ **Clone the repository**  
To clone the project using VSCode, follow these steps:  
- Open **VSCode**  
- Go to **Source Control** (`Ctrl + Shift + G`)  
- Click **Clone Repository** → **"Clone from GitHub"**  
- Select your repository and choose a local folder  
- Open the cloned project in VSCode  

### 2️⃣ **Login to Docker**  
Before running the application, you must log in to Docker:  
```sh
docker login
```
Enter your **Docker username** and **password** when prompted.  

### 3️⃣ **Run the application**  
Navigate to the project directory containing `docker-compose.yml` and execute:  
```sh
docker-compose up --build
```
This command will build and start all necessary containers, including the database and backend.  

### 4️⃣ **Wait for the application to start**  
Once all services are running, the application will be ready for use.  

---

## 🌐 **Access the Application**  
- **Web Interface:** [https://localhost/](https://localhost/)  

---

## 💾 **Database Configuration (if needed)**  
If the database is not set up correctly, follow these steps:  
- Open **MySQL Workbench**  
- Click on **Database → Connect to Database**  
- Enter the credentials:  
  - **User:** `root`  
  - **Password:** `password`  
- Click **OK** to establish the connection  
- If the database does not exist, open a terminal and run:  
  ```sql
  CREATE DATABASE concerts;
  ```  

---

## 🔑 **Test Credentials**  
| Role              | Username | Password |  
|-------------------|---------|----------|  
| Registered User  | `user`  | `user`   |  
| Administrator    | `admin` | `admin`  |  

---

## ⏹ **Stop the application**  
To stop and remove the running containers:  
```sh
docker-compose down
```

---

## 🛠 **Troubleshooting**  
If the application does not start correctly, try the following steps:  
```sh
mvn clean
docker-compose down
docker-compose up --build
```

---

## Docker image construction Documentation

## Deploy Virtual Machine Documentation

## Deploy Virtual Machine URL

## Members participation
- **Alberto Manjón López**
  - *Task done descriptions*
    - Add edit user functionality, edit all the services in order to adapt to the DTOs and API Rest and secure the correct functionality of the web, add NoSuchElementExceptionCA, edit controllers (webController, graphicController, RegisteredWebController)and most of the DTOs with its mappers, to adapt all the changes to the application's functionalities and make it work with functionalities such as register user, images, newConcert, editConcert, deleteConcert, deleteArtist, purchaseTickets and pie graphic.

| **Section**                               |                 | **Details**                                                               |
| ----------------------------------------  | --------------- | ------------------------------------------------------------------------- |
| **Most significant commits**              | **Commit 1**    | [Add edit user](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/a2ad0ec74ec021f9c39e22dd51c2019abbafb34a)|
|                                           | **Commit 2**    | [edit services in order to apply the DTOs and API REST](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/f474ce4d7940693f9558d95615ecf3b96f4d5dd5)|
|                                           | **Commit 3**    | [Modified concerts and user to adapt into DTOs, and correct errors in file in order to compile](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/e503578bd952fd7a8523cfc36ea53b6dcc22a088)|
|                                           | **Commit 4**    | [Refactor user registration, edit user and user images in order to apply the services and UserDTO and NewUserDTO](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/b1fe5edc529b177a3df74ca36bf402174004c5a1)|
|                                           | **Commit 5**    | [correct delete artist functionalities and other errors](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/033a96de4d444261fbdf96e55720b7513ecee0fa)|
| **Files that have participated the most** | **File 1**      | [WebController](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/controller/WebController.java)|
|                                           | **File 2**      | [UserService](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/service/UserService.java)|
|                                           | **File 3**      | [ConcertService](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/service/ConcertService.java)|
|                                           | **File 4**      | [editUser.html](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/resources/templates/editUser.html)|
|                                           | **File 5**      | [RegisteredWebController](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/controller/RegisteredWebController.java)|


- **César Valero Bueno**
  - *Task done descriptions*
    - Implemented API REST controllers, created the Postman collection, assisted creating the Dockerfile, and helped configure the docker-compose.yml file.

| **Section**                               |                 | **Details**       |
| ----------------------------------------  | --------------- | ----------------- |
| **Most significant commits**              | **Commit 1**    | [Add REST controllers for Artist, Ticket, and User](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/4959316df37d992a4b814cf24ad6b1c9548bc4e9)|
|                                           | **Commit 2**    | [Add Docker configuration and update dependencies for Java 21](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/4fd4e090d37343f82a7732e79245d6d8c0550ae0)|
|                                           | **Commit 3**    | [Fix Ticket and User REST Controllers](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/c676729e9acc10749b70cf102d9d9eaaf8f0e2e6)|
|                                           | **Commit 4**    | [Implement graphic generation in API REST](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/441689ae92a074811ffa972239f078cb13f5d218)|
|                                           | **Commit 5**    | [Add user and concert image management functionality in the API, updated the Postman collection and removed password from API answers](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/a5a7292c8f17c91246f2f8d293ccd5c0e511c7d5)|
| **Files that have participated the most** | **File 1**      | [api.postman_collection.json](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/api.postman_collection.json)|
|                                           | **File 2**      | [UserRestController.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/controller/UserRestController.java)|
|                                           | **File 3**      | [GraphicRestController.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/controller/GraphicRestController.java)|
|                                           | **File 4**      | [TicketRestController.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/controller/TicketRestController.java)|
|                                           | **File 5**      | [ArtistRestController.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/controller/ArtistRestController.java)|

- **Irene García López**
   - *Task done descriptions*
    - In this second practice, I have implemented all the initial DTOs and updated the web controller to support ticket-related functions. I have also developed the initial mappers to facilitate data conversion between entities and DTOs. Additionally, I implemented form validations and error handling to ensure proper user input validation. Furthermore, I enabled the correct functionality for removing images, ensuring that a default image is set when deleting a concert image or a user's profile picture during the editing process. Additionally, I added Dockerized Application Execution Instructions.

| **Section**                               |                 | **Details**       |
| ----------------------------------------  | --------------- | ----------------- |
| **Most significant commits**              | **Commit 1**    | [Add DTO and Mapper classes, update Artist model and add MapStruct dependencies](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/256c2f1ee0b49f8d33cfd0a09ccf4b44b416fbb6)|
|                                           | **Commit 2**    | [Form validation and error handling for new artist, edit artist and new concert](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/93b8ed93107f1c4af25796a1c8bce3613157e456)|
|                                           | **Commit 3**    | [Error handling for edit concert, form validation and fix other problems](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/3fb3e7004e8d5e42191f368fccfa905f10e87ee9)|
|                                           | **Commit 4**    | [Implement NewTicketDTO, related methods and fix TicketDTO problems](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/479337023fd44ed5f1ae2d3ea159619a828f8ce0)|
|                                           | **Commit 5**    | [Remove image correctly, form validation of edit user and error handling](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/145fa0d11892a58e950acd9e6e06504ee04b13e3)|
| **Files that have participated the most** | **File 1**      | [WebController.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/a7dd9abb972c8c284db9c3ff74d14dbb2034357a/backend/src/main/java/es/codeurjc/backend/controller/WebController.java)|
|                                           | **File 2**      | [editArtist.html](https://github.com/CodeURJC-DAW-2024-25/webapp10/blame/a7dd9abb972c8c284db9c3ff74d14dbb2034357a/backend/src/main/resources/templates/editArtist.html)|
|                                           | **File 3**      | [ConcertDTO.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blame/a7dd9abb972c8c284db9c3ff74d14dbb2034357a/backend/src/main/java/es/codeurjc/backend/dto/concert/ConcertDTO.java)|
|                                           | **File 4**      | [WebSecurityConfig.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blame/a7dd9abb972c8c284db9c3ff74d14dbb2034357a/backend/src/main/java/es/codeurjc/backend/security/WebSecurityConfig.java)|
|                                           | **File 5**      | [editConcert.html](https://github.com/CodeURJC-DAW-2024-25/webapp10/blame/a7dd9abb972c8c284db9c3ff74d14dbb2034357a/backend/src/main/resources/templates/editConcert.html)|

- **Andrea Garrobo Guzmán**
  - *Task done descriptions*
    - 

| **Section**                               |                 | **Details**       |
| ----------------------------------------  | --------------- | ----------------- |
| **Most significant commits**              | **Commit 1**    | []()|
|                                           | **Commit 2**    | []()|
|                                           | **Commit 3**    | []()|
|                                           | **Commit 4**    | []()|
|                                           | **Commit 5**    | []()|
| **Files that have participated the most** | **File 1**      | []()|
|                                           | **File 2**      | []()|
|                                           | **File 3**      | []()|
|                                           | **File 4**      | []()|
|                                           | **File 5**      | []()|


- **Natalia Méndez Barrios**
  - *Task done descriptions*
    - 

| **Section**                               |                 | **Details**                                                       |
| ----------------------------------------  | --------------- | ----------------------------------------------------------------- |
| **Most significant commits**              | **Commit 1**    | []()|
|                                           | **Commit 2**    | []()|
|                                           | **Commit 3**    | []()|
|                                           | **Commit 4**    | []()|
|                                           | **Commit 5**    | []()|
| **Files that have participated the most** | **File 1**      | []()|
|                                           | **File 2**      | []()|
|                                           | **File 3**      | []()|
|                                           | **File 4**      | []()|
|                                           | **File 5**      | []()|
