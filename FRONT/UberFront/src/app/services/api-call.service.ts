import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class APICallService {
  constructor(private readonly _httpClient: HttpClient,
              private readonly _authService: AuthService) { }

  header: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + this._authService.token
  });

  get(url: string) {
    return this._httpClient.get(url, { headers: this.header });
  }

  post(url: string, body: any) {
    return this._httpClient.post(url, body, { headers: this.header });
  }

  put(url: string, body: any) {
    return this._httpClient.put(url, body, { headers: this.header });
  }
}
