import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { HeaderService } from './header.service';

@Injectable({
  providedIn: 'root'
})
export class APICallService {
  constructor(
    private readonly _httpClient: HttpClient,
    private readonly _authService: AuthService,
    private _headerService: HeaderService) {
    this._headerService.header$?.subscribe((header) => {
      if (header) {
        this._header = header;
      }
    });
  }

  private _header: HttpHeaders = new HttpHeaders();

  get(url: string) {
    console.log(this._header);
    return this._httpClient.get(url, { headers: this._header ? this._header : new HttpHeaders()});
  }

  post(url: string, body: any) {
    return this._httpClient.post(url, body,  { headers: this._header ? this._header : new HttpHeaders()});
  }

  put(url: string, body: any) {
    return this._httpClient.put(url, body);
  }
}
