import { Component, ElementRef, TemplateRef, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { UserDTO } from '../../dtos/user.dto';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-user',
  templateUrl: './edituser.component.html' ,
  styleUrls: ['./edituser.component.css'] 
})
export class EditUserComponent{

  public user!: UserDTO;
  public users!: UserDTO[];

  @ViewChild("file")
  public file!: ElementRef;

  @ViewChild("messageErrorModal")
  public messageErrorModal!: TemplateRef<void>;

  public removeImage!: boolean;
  public messageError!: string;

  constructor(
    private router: Router,
    activatedRoute: ActivatedRoute,
    private usersService: UserService,
    private modalService: NgbModal
  ) {
    const id = activatedRoute.snapshot.params["id"];

    if (id) {
      this.usersService.getUser().subscribe(
        (user) => {
          this.user = user;
        },
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
        numTicketsBought: 0, 
        favoriteGenre: '', 
        image: false, 
        roles: [], 
        tickets: [] };
      
    }

  }

  public goBack() {
    this.router.navigate(["/"]); 
  }

  save() {

    this.usersService.createOrReplaceUser(this.user).subscribe(
      (user: UserDTO) => this.uploadImage(user),
      (error) => {
        this.messageError = "Error creating new user: " + error;
        this.modalService.open(this.messageErrorModal, { centered: true });
      }
    );
  }

  uploadImage(user: UserDTO): void {
    const image = this.file.nativeElement.files[0];

    console.log(this.removeImage);

    if (image) {
      let formData = new FormData();
      formData.append("imageFile", image);

      this.usersService.createOrReplaceUserImage(user, formData).subscribe(
        (_) => this.afterUploadImage(user),
        (error) => {
          this.messageError = "Error uploading user image: " + error;
          this.modalService.open(this.messageErrorModal, { centered: true });
        }
      );
    } else if (this.removeImage) {
      this.usersService.deleteUserImage(user).subscribe(
        (_) => this.afterUploadImage(user),
        (error) => {
          this.messageError = "Error uploading user image: " + error;
          this.modalService.open(this.messageErrorModal, { centered: true });
        }
      );
    } else {
      this.afterUploadImage(user);
    }
  }

  private afterUploadImage(user: UserDTO) {
    this.router.navigate(["/users/", user.id]);
  }

  userImage() {
    return this.user.image
      ? "/api/v1/users/me/image"
      : "/assets/images/no_image.png";
  }

}