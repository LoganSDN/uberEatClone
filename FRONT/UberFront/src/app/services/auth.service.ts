import { Injectable } from '@angular/core';
import { UserI } from '../interfaces/user';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _isLoggedSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  private _tokenSubject: BehaviorSubject<string | undefined> = new BehaviorSubject<string | undefined>(undefined);
  private _userSubject: BehaviorSubject<UserI | undefined> = new BehaviorSubject<UserI | undefined>(undefined);

  isLogged$ = this._isLoggedSubject.asObservable();
  token$ = this._tokenSubject.asObservable();
  user$ = this._userSubject.asObservable();

  constructor() { }

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
  }

  get user(): UserI | undefined {
    return this._userSubject.value;
  }

  set user(value: UserI | undefined) {
    this._userSubject.next(value);
  }
}