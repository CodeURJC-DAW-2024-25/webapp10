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
- **Open API Specification (api-docs.yaml)**: (https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/api-docs/api-docs.yaml)
- **HTML API Documentation**: (https://raw.githack.com/CodeURJC-DAW-2024-25/webapp10/main/backend/api-docs/api-docs.html)

## Classes and templates diagram
![updated_classesDiagram](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/imagesReadme/Classes&Templates_diagram.png)

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
```sh
git clone https://github.com/CodeURJC-DAW-2024-25/webapp10  

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
## 📌 **Requirements**
1. Have Docker installed.
2. Have created a properly configured Dockerfile.
3. Have the repository cloned.
4. Have access to the terminal with sufficient permissions to be able to run Docker.
### 1️⃣ **Check if we have Docker installed**  
Check the version of Docker that we have installed with the following command:
```sh
docker --version
```
### 2️⃣ **Clone the repository**  
```sh
git clone https://github.com/CodeURJC-DAW-2024-25/webapp10.git
```
### 3️⃣ **Navigate to the directory containing create_image.sh:**  
```sh
cd webapp10/Backend/
```
### 4️⃣ **Give execution permission to the file named create_image.sh**
```sh
chmod +x create_image.sh
```
### 5 **Run the Script**
```sh
./create_image.sh
```

---


## Deploy Virtual Machine Documentation
- Download the private key (appWeb10.key) to your local machine. 
- To access the virtual machine, ensure you are connected to the university's network, either directly or through MyApps. 

### 1️⃣ **Connect to the Virtual Machine** 
- Open a terminal in the directory where the `appWeb10.key` file is downloaded and use the following command to connect:  
```sh
ssh -i ssh-keys/appWeb10.key vmuser@10.100.139.103
```
- or you can also use this command:
```sh
ssh -i ssh-keys/appWeb10.key vmuser@appWeb10.dawgis.etsii.urjc.es
```
### 2️⃣ **Clone the repository in the virtual machine**
### 3️⃣ **Deploying the Application with Docker Compose**
```sh
docker-compose up --build
```
### 4️⃣ **Stop the deployment**
```sh
docker-compose down
```

## Deploy Virtual Machine URL
- URL to access the deployed application: (https://appweb10.dawgis.etsii.urjc.es/)

---

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
    - In this lab, I was in charge of ensuring the security of the practice, as well as generating the documentation with OpenApi. I created the script that creates the image, although later for convenience we created a simpler one. I also created a script to clean up Docker, which we later decided to eliminate because we ultimately didn't need it. I created it so we wouldn't be constantly running cleanup commands to test the application. This is how I worked with my partner to validate the form fields.
I didn't help much with the DTOs because the team agreed that three people would be in charge of that, as there were many conflicts, and we often all helped from the same computer to make it much easier.

| **Section**                               |                 | **Details**       |
| ----------------------------------------  | --------------- | ----------------- |
| **Most significant commits**              | **Commit 1**    | [Security in Spring](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/545dc5ec254f794c51c81df99cd7ad6625b0142c)|
|                                           | **Commit 2**    | [Script to build the image](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/d2fc3364a4cf1cac62a1d62a8cd6cebc8043dfe6)|
|                                           | **Commit 3**    | [OpenApi documentation](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/a02085b9f568244e1e9dd266a0bc52d2827c39d9)|
|                                           | **Commit 4**    | [Finish putting it correctly](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/f84487b257f342798e54d25accdd0c2c07ed1e8a)|                    
| **Files that have participated the most** | **File 1**      | [WebSecurityConfig.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/security/WebSecurityConfig.java)|
|                                           | **File 2**      | [pom.xml](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/pom.xml)|
|                                           | **File 3**      | [create_image.sh](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/create_image.sh)|
|                                           | **File 4**      | [WebController.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/controller/WebController.java)|
|                                           | **File 5**      | [LoginController.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/controller/auth/LoginController.java)|


- **Natalia Méndez Barrios**
  - *Task done descriptions*
    - In this project, I refactored parts of the WebController to work with DTOs and updated the ArtistService to handle DTOs, transforming them into domain objects before saving them in the database. Additionally, I contributed to the ConcertRestController and modified DTOs and the WebController to ensure empty fields are not allowed when creating a concert or an artist.

| **Section**                               |                 | **Details**                                                       |
| ----------------------------------------  | --------------- | ----------------------------------------------------------------- |
| **Most significant commits**              | **Commit 1**    | [API REST for concerts](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/cd4715aaf59d8bd5f15ab62c4fce4270668ddbdd)|
|                                           | **Commit 2**    | [DTO, Service and Controller for artists](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/065fce04e64a093e3ef80111dd785fc4c6006f79)|
|                                           | **Commit 3**    | [Handle cocert creation](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/ec8833741324b138e18d064d6aee031ff33eaaa4)|
|                                           | **Commit 4**    | [Validation for concert creation](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/eecb919ef74542627be24598935e70937a0b1408)|
|                                           | **Commit 5**    | [Validation for new artist and edit artist](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/06c4a83c8362ab2cd864d83eb5dcf5b074e20413)|
| **Files that have participated the most** | **File 1**      | [ConcertRestController.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/controller/ConcertRestController.java)|
|                                           | **File 2**      | [WebController.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/controller/WebController.java)|
|                                           | **File 3**      | [NewArtistRequestDTO.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/dto/artist/NewArtistRequestDTO.java)|
|                                           | **File 4**      | [ArtistService.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/service/ArtistService.java)|
|                                           | **File 5**      | [ConcertService.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/service/ConcertService.java)|

# webapp10- Practice 3
## Classes and templates diagram
![classes_template_diagramSPA](https://github.com/user-attachments/assets/229be03a-6ebe-4938-bc65-50d45cd413e4)
- Blue: @Service
- Purple: @Component
- Red: @Template
## Preparing the development environment
-In order to execute the app, do the following steps
### 1️⃣ **Install angular** 
- Use the following commands to install Angular CLI globally:  
```sh
npm install -g @angular/cli

```
### 2️⃣ **Build the Docker Image**
- Run the following script to create the Docker image:
```sh
./docker/create_image.sh
```
### 3️⃣ **Publish the Docker Image**
```sh
./docker/publish_image.sh
```
### 4️⃣ **Navigate to the Docker directory**
```sh
cd docker
```
### 5️⃣ **Deploy the Application with Docker Compose**
```sh
docker-compose up
```
### 6️⃣ **URL**
- URL to access in local: (https://localhost/new)
- URL to access the deployed application: (https://appweb10.dawgis.etsii.urjc.es/new/)

---
## Members participation
- **Alberto Manjón López**
  - *Task done descriptions*
    - Add classes suchs as UserDTO, UserService, TicketService, general CSS, make some functionalities such as edit user functionality ,purchase tickets functionality, update download tickets functionality , update the algorithm of order the concerts by tickets bought by an user and make some modifications in the header.

| **Section**                               |                 | **Details**                                                               |
| ----------------------------------------  | --------------- | ------------------------------------------------------------------------- |
| **Most significant commits**              | **Commit 1**    | [Add userDTO and userService and add general css and initialize users](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/d66ad510c817007b8c2380833920e61c3d86b3c5)|
|                                           | **Commit 2**    | [Add purchase tickets functionality](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/a392cc3c440d8da75a0c7e793bf27f8d70a81b69)|
|                                           | **Commit 3**    | [Download ticket function and refactor backend download ticket functions](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/398e8bbcda5b2c2b0ace478603acf740c08c716c)|
|                                           | **Commit 4**    | [Update edit user function](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/f024afa6f68dc2e70237100138805d3509b914ca)|
|                                           | **Commit 5**    | [Working in edit user function](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/d7c65277d1adfd6491347e5d26d9000b9232b509)|
| **Files that have participated the most** | **File 1**      | [user.service.ts](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/frontend/src/app/services/user.service.ts)|
|                                           | **File 2**      | [edituser.component.html](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/frontend/src/app/components/user/edituser.component.html)|
|                                           | **File 3**      | [edituser.component.ts](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/frontend/src/app/components/user/edituser.component.ts)|
|                                           | **File 4**      | [purchase.component.html](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/frontend/src/app/components/concerts/purchase.component.html)|
|                                           | **File 5**      | [purchase.component.ts](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/frontend/src/app/components/concerts/purchase.component.ts)|


- **César Valero Bueno**
  - *Task done descriptions*
    - Implemented Artist, Concert, and Ticket DTOs, Concert and Artist Services and functionalities such as Create and Edit Concerts. Updated the execution context in create_image.sh. Made some fixes such as: improve Concerts component for looser coupling, configure Docker Compose to correctly pull images from DockerHub, modify the UserRestController to use user id instead of /me, ensure the user appears logged in in the header across all pages, fix map in Concert Info page, resolve minor navigation issues, and fix some image related problems.

| **Section**                               |                 | **Details**                                                               |
| ----------------------------------------  | --------------- | ------------------------------------------------------------------------- |
| **Most significant commits**              | **Commit 1**    | [Add DTOs for Artist, Concert, and Ticket entities](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/28618a680b06a650001d1998ba8806d44eeb19ba)|
|                                           | **Commit 2**    | [Add ConcertService for managing concert data and images](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/21de261a4b357c867ba5b2f63e42820d7f214f99)|
|                                           | **Commit 3**    | [Use concert service in concerts component](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/869528a3c309cf5c38678bb35aed9ae942f6eb4d)|
|                                           | **Commit 4**    | [Add ArtistService for managing artist data with CRUD operations](http://github.com/CodeURJC-DAW-2024-25/webapp10/commit/0bfd06f9cc4e9362afc6b42b8784c86ed8d38329)|
|                                           | **Commit 5**    | [Add ConcertFormComponent for creating and editing concerts with form validation](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/a3392cce7691a51f90aeadd9e0f9fb16e94f4f61)|
| **Files that have participated the most** | **File 1**      | [concert-form.component.html](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/frontend/src/app/components/concerts/concert-form.component.html)|
|                                           | **File 2**      | [concert-form.component.ts](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/frontend/src/app/components/concerts/concert-form.component.ts)|
|                                           | **File 3**      | [concert.service.ts](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/frontend/src/app/services/concert.service.ts)|
|                                           | **File 4**      | [artist.service.ts](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/frontend/src/app/services/artist.service.ts)|
|                                           | **File 5**      | [concerts.component.ts](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/frontend/src/app/components/concerts/concerts.component.ts)|


- **Irene García López**
  - *Task done descriptions*
    - I have implemented the user authentication functionality, including the login, registration, and user page components, form validation, and error handling for invalid credentials. I also developed the unauthorized access component and configured route guards to protect both user pages and admin-only sections based on user roles, ensuring secure and restricted navigation. Furthermore, I configured the publication of the Angular frontend as SPA served by the Spring Boot backend and I created startup batch and shell scripts to automate the project launch with Docker.

| **Section**                               |                 | **Details**                                                               |
| ----------------------------------------  | --------------- | ------------------------------------------------------------------------- |
| **Most significant commits**              | **Commit 1**    | [Implement unauthorized access component and route guard handling, update concert and artist forms, fix artist list visibility, and remove redundant code](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/e6c406f3a98d63a6233082735ffb6e2fcfa51ce8)|
|                                           | **Commit 2**    | [Implement user registration component with form and validation](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/7a4c080fdc8e2d8435e9fcd9cd95981022194386)|
|                                           | **Commit 3**    | [Implemented login component and fix registration functionality](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/6667c17d6a2c31577c0b6b7036c64e639f3c4381)|
|                                           | **Commit 4**    | [Implemented user page component, update user service methods and update styles](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/a4720dc541a52fef63c7192622e8f2d4c2071dfe)|
|                                           | **Commit 5**    | [Deployed Angular application integrated with Spring Boot backend](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/442a25f17b7175aa664b46883fdca5943b755965)|
| **Files that have participated the most** | **File 1**      | [user-page.component.ts](https://github.com/CodeURJC-DAW-2024-25/webapp10/blame/main/frontend/src/app/components/user/user-page.component.ts)|
|                                           | **File 2**      | [register.component.ts](https://github.com/CodeURJC-DAW-2024-25/webapp10/blame/main/frontend/src/app/components/register/register.component.ts)|
|                                           | **File 3**      | [user-page.component.html](https://github.com/CodeURJC-DAW-2024-25/webapp10/blame/main/frontend/src/app/components/user/user-page.component.html)|
|                                           | **File 4**      | [register.component.html](https://github.com/CodeURJC-DAW-2024-25/webapp10/blame/main/frontend/src/app/components/register/register.component.html)|
|                                           | **File 5**      | [auth.service.ts](https://github.com/CodeURJC-DAW-2024-25/webapp10/blame/main/frontend/src/app/services/auth.service.ts)|



- **Andrea Garrobo Guzmán**
  - *Task done descriptions*
    - I've taken care of the functionality for loading more elements on the main page, as well as including the profile and homepage images. Then I created the conventional error page, so that when you get an unexpected error, it sends you to this page, and the error page upon login if you did it wrong.
    - In addition to the previous practice, I corrected the graphics section because it returned the data in images and not as JSON, and if it was that way, we couldn't use it for this practice with Angular, so I made it return JSON.
    - I also moved the Docker folder to the root of the project and started configuring the DockerFile for this practice.

| **Section**                               |                 | **Details**                                                               |
| ----------------------------------------  | --------------- | ------------------------------------------------------------------------- |
| **Most significant commits**              | **Commit 1**    | [Setting up DockerFile to create Angular image and creating the client error page (Angular)
](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/51be4f5b145161b7cc5b4a187289feed9fb56788)|
|                                           | **Commit 2**    | [Modify the aesthetics of the error pages and add a login error
](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/be85e2e92e3d678ed58cc2f5f38d26d3676e504a)|
|                                           | **Commit 3**    | [Images visible
](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/b5520e5e1577e9ab7fd970a0d1ce16e7034e03f9)|
|                                           | **Commit 4**    | [Load more functionality
](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/8a785b62bb02a6893c7e9bd98c69c96152ee9a2d)|
|                                           | **Commit 5**    | [Charts return JSON not images
](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/5521fee70b9e662b18751f3e9763cc37afae8741)|
| **Files that have participated the most** | **File 1**      | [error.component.ts](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/frontend/src/app/components/error/error.component.ts)|
|                                           | **File 2**      | [header.component.ts](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/frontend/src/app/components/header/header.component.ts)|
|                                           | **File 3**      | [concerts.component.ts](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/frontend/src/app/components/concerts/concerts.component.ts)|
|                                           | **File 4**      | [GraphicRestController.java](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/backend/src/main/java/es/codeurjc/backend/controller/GraphicRestController.java)|
|                                           | **File 5**      | [Dockerfile](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/docker/Dockerfile)|



- **Natalia Méndez Barrios**
  - *Task done descriptions*
    - Developed the main page responsible for handling the Single Page Application (SPA) logic.
    - Created and integrated the Header and Footer components, which are displayed across all pages.
    - Implemented the Index component that lists all available concerts on the website.
    - Built the Artist Form component, used for both adding and editing artists. It dynamically loads existing artist data into the form when editing.
    - Developed the Concert Info component, which displays detailed information about a concert, including features such as statistics graphs (pie chart and bar chart), and buttons for deleting or editing artists and concerts.

| **Section**                               |                 | **Details**                                                               |
| ----------------------------------------  | --------------- | ------------------------------------------------------------------------- |
| **Most significant commits**              | **Commit 1**    | [add concert listing feature with header and footer components](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/ecffe0d0a839cf897e7a7ba8d604c44266e95a20)|
|                                           | **Commit 2**    | [Add new artist and edit artist functionality](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/ecffe0d0a839cf897e7a7ba8d604c44266e95a20)|
|                                           | **Commit 3**    | [add concert info component](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/ecffe0d0a839cf897e7a7ba8d604c44266e95a20)|
|                                           | **Commit 4**    | [delete artist, delete concert and check user status on concert info](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/d77b0d91a88085cd83ac71146cd5914166b4382d)|
|                                           | **Commit 5**    | [add graphics service and graphics managment in concert info](https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/d77b0d91a88085cd83ac71146cd5914166b4382d)|
| **Files that have participated the most** | **File 1**      | [concerts.component.ts](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/frontend/src/app/components/concerts/concerts.component.ts)|
|                                           | **File 2**      | [concert-info.component.ts](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/frontend/src/app/components/concerts/concert-info.component.ts)|
|                                           | **File 3**      | [artist-form.component.ts](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/frontend/src/app/components/artists/artist-form.component.ts)|
|                                           | **File 4**      | [header-form.component.html](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/frontend/src/app/components/header/header.component.html)|
|                                           | **File 5**      | [concerts.component.html](https://github.com/CodeURJC-DAW-2024-25/webapp10/blob/main/frontend/src/app/components/concerts/concerts.component.html)|

## Youtube Video
