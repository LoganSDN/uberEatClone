import { Injectable } from '@angular/core';
import { DriversocketConfig } from './driversocket.socket';
import { AuthService } from '../auth.service';
import { OrderI } from 'src/app/interfaces/restaurant';

@Injectable({
  providedIn: 'root'
})
export class DriverSocketService {

  private driverSocket : DriversocketConfig | undefined = undefined;
  private token :string|undefined = undefined;
  constructor(private readonly authService: AuthService) {
    this.authService.token$.subscribe((token) => {this.token = token});
  }

  async onConnect(){
    if (this.token != undefined){
      this.driverSocket = new DriversocketConfig();
      await this.driverSocket.connect();
      this.driverSocket.on("connect_error", (err :any) => {
        console.log(`connect_error due to ${err.message}`);
      });
      console.log("connecting?");
      return this.driverSocket.fromEvent<OrderI>("NewOrder");
    }
    return undefined;
  }

  onAcceptOrder(){

  }
}
