import { Component, OnInit } from '@angular/core';
import { APICallService } from 'src/app/services/api-call.service';
import { lastValueFrom } from 'rxjs';

@Component({
  selector: 'app-all-restaurants',
  templateUrl: './all-restaurants.component.html',
  styleUrls: ['./all-restaurants.component.scss']
})
export class AllRestaurantsComponent implements OnInit {

  constructor(private _apiCallService: APICallService) { }

  async ngOnInit() {
    const restaurants = await lastValueFrom(this._apiCallService.get('/api/restaurant/all'));
    console.log(restaurants);
  }

}
