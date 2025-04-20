import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../../services/user.service';
import { ActivatedRoute } from '@angular/router';
import { UserDTO } from '../../dtos/user.dto';

@Component({
  selector: 'app-user',
  templateUrl: './edituser.component.html'/* ,
  styleUrls: ['./edituser.component.css'] */
})
export class EditUserComponent implements OnInit{

  user!: UserDTO;
  users!: UserDTO[];

  constructor(
    private activatedRoute: ActivatedRoute,
    private service: UserService
  ) {}


  ngOnInit(): void {
    let id = this.activatedRoute.snapshot.params['id'];

    if (id === '0') {
      console.warn('Editing user with ID 0');
    }
    
    if (id) {
      this.service.getUser(id).subscribe(
        (user: UserDTO) => this.user = user,
        (error) => console.error(error)
      );
    } else {
      this.user = { 
        fullName: '', 
        userName: '', 
        phone: 0, 
        email: '', 
        password: '', 
        age: 0, 
        numTicketsBpught: 0, 
        favoriteGenre: '', 
        image: false, 
        roles: [], 
        tickets: [] 
      };
    }
  }

  goBack() {
    window.history.back();
  }

  save() {
    this.service.addOrUpdateUser(this.user).subscribe(
      (user) => {
        console.log("Usuario guardado", user);
        window.history.back(); 
      },
      (error) => console.error('Error al guardar el usuario: ' + error)
    );
    window.history.back();
  }

}