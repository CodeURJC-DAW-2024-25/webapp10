import { Component, ElementRef, TemplateRef, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service'; 
import { UserDTO } from '../../dtos/user.dto';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-user',
  templateUrl: './edituser.component.html'
})
export class EditUserComponent {

  public user!: UserDTO;
  public users!: UserDTO[];

  @ViewChild("file") public file!: ElementRef;
  @ViewChild("messageErrorModal") public messageErrorModal!: TemplateRef<void>;

  public removeImage!: boolean;
  public messageError!: string;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private usersService: UserService,
    private modalService: NgbModal,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/login']);
      return;
    }

    const id = this.activatedRoute.snapshot.paramMap.get("id");

    if (id) {
      this.usersService.getCurrentUser().subscribe({
        next: (loggedUser) => {
          if (loggedUser.id !== Number(id)) {
            this.router.navigate(['/error/unauthorized']);
            return;
          }

          this.usersService.getUser(id).subscribe({
            next: (user) => {
              this.user = user;
            },
            error: (error) => console.error(error)
          });
        },
        error: (error) => {
          console.error(error);
          this.router.navigate(['/login']);
        }
      });

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
        tickets: []
      };
    }
  }

  public goBack() {
    this.router.navigate(["/"]);
  }

  save() {
    this.usersService.createOrReplaceUser(this.user).subscribe({
      next: (user: UserDTO) => this.uploadImage(user),
      error: (error) => {
        this.messageError = "Error creating new user: " + error;
        this.modalService.open(this.messageErrorModal, { centered: true });
      }
    });
  }

  uploadImage(user: UserDTO): void {
    const image = this.file.nativeElement.files[0];

    console.log(this.removeImage);

    if (image) {
      let formData = new FormData();
      formData.append("imageFile", image);

      this.usersService.createOrReplaceUserImage(user, formData).subscribe({
        next: (_) => this.afterUploadImage(user),
        error: (error) => {
          this.messageError = "Error uploading user image: " + error;
          this.modalService.open(this.messageErrorModal, { centered: true });
        }
      });
    } else if (this.removeImage) {
      this.usersService.deleteUserImage(user).subscribe({
        next: () => this.afterUploadImage(user),
        error: (error) => {
          this.messageError = "Error uploading user image: " + error;
          this.modalService.open(this.messageErrorModal, { centered: true });
        }
      });
    } else {
      this.afterUploadImage(user);
    }
  }

  private afterUploadImage(user: UserDTO) {
    this.usersService.updateCurrentUser(user);
    window.location.href = "/";
  }

  userImage() {
    return this.user.image
      ? "/api/v1/users/" + this.user.id + "/image"
      : "/assets/images/noprofilephoto.png";
  }

}
