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
	console.log("Creating socket for front");
	super({
			url:  "/socket",
			options: {
				transports: ["polling", "websocket"],
				// reconnectionAttempts: 4,
				extraHeaders: {
					Authorization: getToken() ?? '',
				}
			}
		});
  };
  
}