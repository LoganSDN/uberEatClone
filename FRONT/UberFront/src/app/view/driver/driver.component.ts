import { Component } from '@angular/core';
import { OrderI } from 'src/app/interfaces/restaurant';
import { DriverSocketService } from 'src/app/services/socket/driver-socket.service';

@Component({
  selector: 'app-driver',
  templateUrl: './driver.component.html',
  styleUrls: ['./driver.component.scss']
})
export class DriverComponent {

  private order :OrderI | undefined = undefined;
  constructor(private readonly driverSocket: DriverSocketService){
  }

  async onConnect(){
    (await this.driverSocket.onConnect())?.subscribe((order : OrderI) => {
      console.log("Received New Order:", order);
      this.order  = order;
    });
  }
}
