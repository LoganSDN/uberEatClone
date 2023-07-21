import { HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { EventSourcePolyfill, MessageEvent } from 'event-source-polyfill';
import { Observable } from 'rxjs';
import { OrderStatus } from 'src/app/interfaces/order-status';
import { HeaderService } from 'src/app/services/header.service';

@Component({
  selector: 'app-progress-bar',
  templateUrl: './progress-bar.component.html',
  styleUrls: ['./progress-bar.component.scss']
})
export class ProgressBarComponent {
  lastActiveIndex: number = 0;
  currentState: OrderStatus = {"status": "Awaiting validation from restaurant"} as OrderStatus;
  header: HttpHeaders | undefined;
  progressCells: boolean[] = [true, false, false, false, false];

  constructor(private _headerService: HeaderService) {
    this._headerService.header$.subscribe(header => {
      this.header = header;
    });
  }

  ngOnInit(): void {
    const eventSourceOptions = {
      headers: {
        Authorization: this.header?.get('Authorization') || '',
        'Content-type': 'application/json',
      },
    };
    const source = new EventSourcePolyfill('/api/order-status/order/1', eventSourceOptions);

    const messageListener = (message: MessageEvent) => {
      const data: OrderStatus = JSON.parse(message.data);
      this.updateProgressBar(data);
    };

    source.addEventListener('message', messageListener);
  }

  updateProgressBar(data: OrderStatus): void {
    this.currentState = data;
    this.lastActiveIndex = data.step - 1;
    this.progressCells = this.progressCells.map((_, index) => {
      if (index < data.step) {
        setTimeout(() => true, index * 500);
        return true;
      } else {
        return false;
      }
    });
    console.log(this.progressCells);
  }
}
