import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'login-form',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup
  formUsername: FormControl = new FormControl
  formPassword: FormControl = new FormControl

  constructor() {

    this.loginForm = new FormGroup({
      formUsername: new FormControl('', Validators.required),
      formPassword: new FormControl('', Validators.required)
    })
   }

  ngOnInit(): void {
  }

  get loginUsername(){
    return this.loginForm.get('username')
  }

  get loginPassword(){
    return this.loginForm.get('password')
  }

  onSubmit(){
    
  }

}
