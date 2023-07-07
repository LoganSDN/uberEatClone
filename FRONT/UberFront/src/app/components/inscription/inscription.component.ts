import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { lastValueFrom } from 'rxjs';
import { APICallService } from 'src/app/services/api-call.service';

@Component({
  selector: 'app-inscription',
  templateUrl: './inscription.component.html',
  styleUrls: ['./inscription.component.scss']
})
export class InscriptionComponent {
  signUpForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)]),
    confirmPassword: new FormControl('', [Validators.required, Validators.minLength(6)])
  });

  constructor(private _apiCallService: APICallService) { }

  signUp(): void {
    console.log(this.signUpForm.value);
  }

  async onSubmit() {
    const newUser = await lastValueFrom(this._apiCallService.post('/api/users/add',{ ...this.signUpForm.value, roles: ['ROLE_CUSTOMER']}))
    console.log(newUser);
  }
}
