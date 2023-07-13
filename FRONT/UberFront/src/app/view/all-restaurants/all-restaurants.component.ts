import { Component, OnInit } from '@angular/core';
import { APICallService } from 'src/app/services/api-call.service';
import { lastValueFrom } from 'rxjs';
import { RestaurantI } from 'src/app/interfaces/restaurant';
import { Router } from '@angular/router';

@Component({
  selector: 'app-all-restaurants',
  templateUrl: './all-restaurants.component.html',
  styleUrls: ['./all-restaurants.component.scss']
})
export class AllRestaurantsComponent implements OnInit {

  restaurants: RestaurantI[] = [];
  isLoading: boolean = true;

  constructor(private _apiCallService: APICallService,
              private _router: Router) {}

  async ngOnInit() {
    this.restaurants = await lastValueFrom(this._apiCallService.get('/api/restaurant/all')) as any[];
    this.isLoading = false;
  }

  openRes(res: RestaurantI) {
    this._router.navigate(['/restaurant-details'], { queryParams: { id: res.id } });
  }

}
