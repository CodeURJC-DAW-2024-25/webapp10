# webapp10
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
- Admin: Add artists, Add concerts, Modify artists, Modify concerts
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
## Screenshots
![indexScreenshot](https://github.com/user-attachments/assets/af78d42a-6cea-4a6b-9a13-7b85600316a3)
- Description: main page that includes all the information about the different concerts and artists, in addition to allowing navigation to user registration, login and modification of artists and concerts if you are an administrator.

![concertInfoScreenshot](https://github.com/user-attachments/assets/3870bc0f-7e1b-40ba-874c-ad4cbd2c7634)
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

## Navigation Diagram
![diagramScreenshot](https://github.com/user-attachments/assets/dd84c2c3-78b2-423a-835e-fbace9b5e235)

## Execution instructions

## Database entities diagram
![dbDiagram](https://github.com/user-attachments/assets/c6b5bf1c-4bf1-4fa4-a63f-d2d6995e5b43)


## Classes and templates diagram
![class_templates_diagram](https://github.com/user-attachments/assets/a3243b7a-25b6-4591-9252-16dd8809ac2f)


## Members participation
- **Alberto Manjón López**
  - *Task done descriptions*
    - Task
  - *More significant commits*
    - Commit 1 : Add login and register controller and function (https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/52c0821a2f639b8ed67da6bc5fc17ce41f01a608)
    - Commit 2 : Enhance concert management features: add ticket purchasing functionality and update concert retrieval method (https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/9e36c0b3899f4cd0cc97fad20ffdea701e4dfa0d)
    - Commit 3 : Add error page (https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/50096b1de6aed1c435dd7a2aede5aece178626c5)
    - Commit 4 : Add Ticket and User model methods (https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/936a428dc7d79278cb081e5325fdd0b683b44cd9)
    - Commit 5 : Add pie graphic (https://github.com/CodeURJC-DAW-2024-25/webapp10/commit/a642ad7afe0e00caa307c0c28699e899d2f32792)
  - *Files that have participated the most*
    - File 1:
    - File 2:
    - File 3:
    - File 4:
    - File 5:
- **César Valero Bueno**
  - *Task done descriptions*
    - Task
  - *More significant commits*
    - Commit 1 :
    - Commit 2 : 
    - Commit 3 : 
    - Commit 4 : 
    - Commit 5 : 
  - *Files that have participated the most*
    - File 1:
    - File 2:
    - File 3:
    - File 4:
    - File 5:
- **Irene García López**
  - *Task done descriptions*
    - Task
  - *More significant commits*
    - Commit 1 :
    - Commit 2 : 
    - Commit 3 : 
    - Commit 4 : 
    - Commit 5 : 
  - *Files that have participated the most*
    - File 1:
    - File 2:
    - File 3:
    - File 4:
    - File 5:
- **Andrea Garrobo Guzmán**
  - *Task done descriptions*
    - Task
  - *More significant commits*
    - Commit 1 :
    - Commit 2 : 
    - Commit 3 : 
    - Commit 4 : 
    - Commit 5 : 
  - *Files that have participated the most*
    - File 1:
    - File 2:
    - File 3:
    - File 4:
    - File 5:
- **Natalia Méndez Barrios**
  - *Task done descriptions*
    - Task
  - *More significant commits*
    - Commit 1 :
    - Commit 2 : 
    - Commit 3 : 
    - Commit 4 : 
    - Commit 5 : 
  - *Files that have participated the most*
    - File 1:
    - File 2:
    - File 3:
    - File 4:
    - File 5:
  - Commit 5 :
    
