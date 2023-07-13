import { Injectable } from '@angular/core';
import { UserI } from '../interfaces/user';
import { BehaviorSubject } from 'rxjs';
import { Router } from '@angular/router';
import { JwtService } from './jwt.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _isLoggedSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  private _tokenSubject: BehaviorSubject<string | undefined> = new BehaviorSubject<string | undefined>(localStorage.getItem('token') as string | undefined);
  private _userSubject: BehaviorSubject<UserI | undefined> = new BehaviorSubject<UserI | undefined>(undefined);

  isLogged$ = this._isLoggedSubject.asObservable();
  token$ = this._tokenSubject.asObservable();
  user$ = this._userSubject.asObservable();

  constructor(private _router: Router,
              private _jwtService: JwtService) {
    this._tokenSubject.next(localStorage.getItem('token') as string | undefined);
    this._userSubject.next(this._jwtService.decodeToken(this.token as string)?.user);
    this._isLoggedSubject.next(this.isLoggedIn());
  }

  get isLogged(): boolean {
    return this._isLoggedSubject.value;
  }

  set isLogged(value: boolean) {
    this._isLoggedSubject.next(value);
    }

  get token(): string | undefined {
    return this._tokenSubject.value;
  }

  set token(value: string | undefined) {
    this._tokenSubject.next(value);
    if (value) {
      localStorage.setItem('token', value);
    } else {
      localStorage.removeItem('token');
    }
  }

  get user(): UserI | undefined {
    return this._userSubject.value;
  }

  set user(value: UserI | undefined) {
    this._userSubject.next(value);
  }

  isLoggedIn(): boolean {
    return !!this.token;
  }

  logout(): void {
    this.token = undefined;
    this.user = undefined;
    this.isLogged = false;
    window.localStorage.removeItem('token');
    this._router.navigate(['/']);
  }
}