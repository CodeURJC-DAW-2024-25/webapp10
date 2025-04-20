import { Component, OnInit } from '@angular/core';
import { UserService } from './services/user.service';
import { UserDTO } from './dtos/user.dto';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
})
export class AppComponent implements OnInit {

  title = 'frontend';

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.createInitialUsers();
  }

  createInitialUsers() {
    const initialUsers: UserDTO[] = [
      {
        fullName: "Registered User",
        userName: "user",
        phone: 123456789,
        email: "user@example.com",
        password: "user", 
        age: 20,
        numTicketsBpught: 0,
        favoriteGenre: "",
        image: false,
        roles: ["USER"],
        tickets: []
      },
      {
        fullName: "Admin User",
        userName: "admin",
        phone: 987654321,
        email: "admin@example.com",
        password: "admin", 
        age: 70,
        numTicketsBpught: 0,
        favoriteGenre: "",
        image: false,
        roles: ["USER", "ADMIN"],
        tickets: []
      }
    ];

    initialUsers.forEach(user => {
      this.userService.addOrUpdateUser(user).subscribe(
        (response) => {
          console.log('Usuario creado:', response);
        },
        (error) => {
          console.error('Error al crear el usuario:', error);
        }
      );
    });
  }
  
}