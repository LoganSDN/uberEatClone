import { Component } from '@angular/core';
import { WebSocketService } from 'src/app/services/websocket.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent {
  constructor(private websocketService: WebSocketService) { }

  connect() {
    this.websocketService.connect();
  }

  disconnect() {
    this.websocketService.disconnect();
  }

  send() {
    this.websocketService.send("/app/dely", "hello");
  }
}
