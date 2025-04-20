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
        numTicketsBought: 0,
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
        numTicketsBought: 0,
        favoriteGenre: "",
        image: false,
        roles: ["USER", "ADMIN"],
        tickets: []
      }
    ];

    initialUsers.forEach(user => {
      this.userService.createOrReplaceUser(user).subscribe(
        (response) => {
          console.log('User created:', response);
        },
        (error) => {
          console.error('Error:', error);
        }
      );
    });
  }
  
}