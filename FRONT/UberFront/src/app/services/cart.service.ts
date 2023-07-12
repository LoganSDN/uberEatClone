import { Injectable } from '@angular/core';
import { ProductI } from '../interfaces/restaurant';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  productsList: BehaviorSubject<ProductI[] | undefined> = new BehaviorSubject<ProductI[] | undefined>(undefined);
  productsList$ = this.productsList.asObservable();
  restaurantId: number | undefined;

  constructor() { }

  addToCart(product: ProductI, restaurantId: number) {
    let productsList = this.productsList.getValue();
    if (productsList && productsList.length > 0 && this.restaurantId === restaurantId) {
      productsList.push(product);
    } else {
      this.restaurantId = restaurantId;
      productsList = [product];
    }
    this.productsList.next(productsList);
  }

  removeFromCart(product: ProductI) {
    let productsList = this.productsList.getValue();
    if (productsList) {
      productsList = productsList.filter(p => p.id !== product.id);
      this.productsList.next(productsList);
    }
  }

  clearCart() {
    this.productsList.next([]);
  }

  getCart() {
    return this.productsList.getValue();
  }
}
