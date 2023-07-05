import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { TokenI, DecodedTokenI } from 'src/app/interfaces/token';
import { APICallService } from 'src/app/services/api-call.service';
import { AuthService } from 'src/app/services/auth.service';
import { HeaderService } from 'src/app/services/header.service';
import { JwtService } from 'src/app/services/jwt.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  tokenResponse: TokenI | undefined;

  loginForm = new FormGroup({
    email: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  });

  loading = false;

  constructor(private _apiCallService: APICallService,
    private _authService: AuthService,
    private _snackBar: MatSnackBar,
    private _jwtService: JwtService,
    private _router: Router) { }


  async onConnect() {
    try {
      this.loading = true;
      this.tokenResponse = await lastValueFrom(this._apiCallService.post('/api/auth/login', this.loginForm.value)) as TokenI;
    } catch (error) {
      console.log(error);
      this._snackBar.open('An error occurred. Please try again.', 'Dismiss', {
        duration: 5000,
      });
    } finally {
      this.loading = false;
      console.log('finally');
      console.log(this.tokenResponse);
      if (this.tokenResponse) {
        this._authService.token = this.tokenResponse.accessToken;
        const decodedToken: DecodedTokenI = this._jwtService.decodeToken(this.tokenResponse.accessToken);
        this._authService.user = decodedToken.user
        this._authService.isLogged = true;
        this._snackBar.open('Vous êtes connecté', 'OK', {
          duration: 1000,
        });
        this._router.navigate(['/', 'restaurants']);
      }
    }
  }
}
