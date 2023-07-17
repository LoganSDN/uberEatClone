import { Injectable } from '@angular/core';
import * as SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { AuthService } from './auth.service';
import { BehaviorSubject, Observable, Subscription } from 'rxjs';
import { UserI } from '../interfaces/user';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  webSocketEndPoint: string = 'http://localhost:8080/ws';
  topic: string = "/topic/message";
  stompClient: any;
  user: any;
  token: String | undefined = undefined;

  constructor(private authService: AuthService) {
    this.authService.user$.subscribe((user) => {
      if (user)
        this.user = user;
      else
        this.user = undefined
    });
    this.authService.token$.subscribe((token) => {
      if (token) {
        this.token = token;
      } else {
        this.token = undefined;
      }
    });
  }
  connect() {
    if (!this.token)
      return;
    console.log("Initialize WebSocket Connection");
    let ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);
    this.stompClient.connect({}, () => {
      let session = this.getSessionId(this.stompClient.ws._transport.url);
      console.log("Session = ", session);
      this.stompClient.subscribe("/user/" + session + "/bite", () => console.log("works"));
      this.stompClient.send("/app/connect",{authorization: "Bearer " + this.token},JSON.stringify(this.token));
    this.stompClient.subscribe(this.topic, (sdkEvent: any) => {
      this.onMessageReceived(sdkEvent);
      // this.stompClient.subscribe("/user/"+ ws.)
    });
  }, this.errorCallBack);
  };

disconnect() {
  if (this.stompClient !== null) {
    this.stompClient.disconnect();
  }
  console.log("Disconnected");
}

errorCallBack(error: any) {
  console.log("errorCallBack -> " + error)
  setTimeout(() => {
    this.connect();
  }, 5000);
}

send(destination: string, message: string) {
  this.stompClient.send(destination, {}, JSON.stringify({ txt: message }));
}

getSessionId(url : string){
  url = url.replace(
    "ws://localhost:8080/ws/", "");
  url = url.replace("/websocket", "");
  url = url.replace(/^[0-9]+\//, "");
  return url;
}

onMessageReceived(message: any) {
  console.log("Message Recieved from Server :: " + message.body);
}
};
