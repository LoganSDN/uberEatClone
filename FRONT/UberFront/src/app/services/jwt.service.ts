import { Injectable } from '@angular/core';
import jwt_decode from "jwt-decode";
import { DecodedTokenI } from '../interfaces/token';

@Injectable({
  providedIn: 'root'
})
export class JwtService {
  constructor() { }

  decodeToken(token: string): DecodedTokenI {
    return jwt_decode(token);
  }
}
