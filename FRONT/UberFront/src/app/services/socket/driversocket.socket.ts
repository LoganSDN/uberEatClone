import { Injectable } from '@angular/core';
import { Socket } from 'ngx-socket-io';

export function getToken(){
  return localStorage.getItem("token");
}

@Injectable({
  providedIn: 'root'
})
export class DriversocketConfig extends Socket {
  constructor(){
	super({
			url:  "/socket",
			options: {
				autoConnect: false,
				// reconnectionAttempts: 4,
				extraHeaders: {
					Authorization: getToken() ?? '',
				}
			}
		});
  };
}