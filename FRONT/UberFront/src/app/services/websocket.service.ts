import { Injectable } from '@angular/core';
import * as SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  webSocketEndPoint: string = 'http://localhost:8080/ws';
  topic: string = "/topic/message";
  stompClient: any;

  constructor() { }

  connect() {
    console.log("Initialize WebSocket Connection");
    let ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe(this.topic, (sdkEvent: any) => {
        this.onMessageReceived(sdkEvent);
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
    this.stompClient.send(destination, {}, JSON.stringify({txt: message}));
  }

  onMessageReceived(message: any) {
    console.log("Message Recieved from Server :: " + message.body);
  }
}
