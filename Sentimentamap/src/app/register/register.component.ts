import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  validatingForm: FormGroup

  constructor() {
    this.validatingForm = new FormGroup({
      registerUsername: new FormControl('', Validators.required),
      registerEmail: new FormControl('', [Validators.required, Validators.email]),
      registerPassword: new FormControl('', Validators.required),
      registerPasswordConfirmation: new FormControl('', Validators.required)
    })
   }

  ngOnInit(): void {
    
  }

  get registerName() {
    return this.validatingForm.get('registerName');
  }

  get registerEmail() {
    return this.validatingForm.get('registerEmail');
  }

  get registerPassword() {
    return this.validatingForm.get('registerPassword');
  }

  get registerPasswordConfirmation() {
    return this.validatingForm.get('registerPasswordConfirmation');
  }

}
