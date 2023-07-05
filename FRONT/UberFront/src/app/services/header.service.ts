import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { APICallService } from './api-call.service';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class HeaderService {
  private _headerSubject: BehaviorSubject<HttpHeaders | undefined> = new BehaviorSubject<HttpHeaders | undefined>(undefined);
  
  header$ = this._headerSubject.asObservable();
  constructor(private _authService: AuthService) {
    this._authService.token$.subscribe((token) => {
      if (token) {
        this.header = new HttpHeaders().set('Authorization', `Bearer ${token}`).set('Content-Type', 'application/json');
      } else {
        this.header = undefined;
      }
    });
    this._headerSubject.next(this.header);
  }

  get header(): HttpHeaders | undefined {
    return this._headerSubject.value;
  }

  set header(value: HttpHeaders | undefined) {
    this._headerSubject.next(value);
  }
}
