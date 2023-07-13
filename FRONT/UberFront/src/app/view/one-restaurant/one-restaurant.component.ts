import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductI, RestaurantI } from 'src/app/interfaces/restaurant';
import { APICallService } from 'src/app/services/api-call.service';
import { lastValueFrom } from 'rxjs';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-one-restaurant',
  templateUrl: './one-restaurant.component.html',
  styleUrls: ['./one-restaurant.component.scss']
})
export class OneRestaurantComponent {
  restaurantId: string | undefined;
  restaurant: RestaurantI | undefined;

  constructor(private _route: ActivatedRoute,
              private _apiCallService: APICallService,
              private _cartService: CartService) { }

  ngOnInit() {
    this._route.queryParams.subscribe(async (params) => {
      this.restaurantId = params['id'];
      this.restaurant = await lastValueFrom(this._apiCallService.get(`/api/restaurant/${this.restaurantId}`));
    });
  }

  addToShoppingCart(product: ProductI) {
    this._cartService.addToCart(product, +this.restaurantId!);
  }
}
