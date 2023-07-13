import { Component } from '@angular/core';
import { ProductI } from 'src/app/interfaces/restaurant';
import { APICallService } from 'src/app/services/api-call.service';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';
import { lastValueFrom } from 'rxjs';
import { UserI } from 'src/app/interfaces/user';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent {
productList: ProductI[] = [];
user!: UserI;

  constructor(private _cartService: CartService,
              private _authService: AuthService,
              private _apiCallService: APICallService) {
    this._cartService.productsList$.subscribe((productsList) => {
      this.productList = productsList || [];
    });
    this._authService.user$.subscribe((user) => {
      console.log(user);
      this.user = user || {} as UserI;
    });
  }

  removeFromCart(product: ProductI) {
    this._cartService.removeFromCart(product);
  }

  getTotal() {
    return this.productList.reduce((acc, product) => acc + product.price!, 0);
  }

  clearCart() {
    this._cartService.clearCart();
  }

  async checkout() {
    const post = {
      "productsList": this._cartService.getCart(),
      "totalAmount": this.getTotal(),
      "susId": this.user.id,
      "resId": this._cartService.restaurantId,
      "status": "ONGOING"
    };
    console.log(post)
    const res = await lastValueFrom(this._apiCallService.post('/api/order/new-order', post));
    console.log(res);
  }
}
