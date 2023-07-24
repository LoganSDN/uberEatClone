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
    console.log('One instance of SocketService')
    this.authService.token$.subscribe((token) => {this.token = token});
  }

  get socketServer() {
    return this.driverSocket;
  }

  onConnect(){
    if (this.token != undefined){
      this.driverSocket = this.driverSocket == undefined ? new DriversocketConfig() : this.driverSocket;
      
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
