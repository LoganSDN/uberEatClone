import { Component, EventEmitter, Output } from '@angular/core';
import { ProductI } from 'src/app/interfaces/restaurant';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  isLogged: boolean = false;
  productsList: ProductI[] = [];

  constructor(private _authService: AuthService,
              private _cartService: CartService) {
    this._authService.isLogged$.subscribe((isLogged) => {
      console.log("I received a new value for isLogged", isLogged)
      this.isLogged = isLogged;
    });
    this._cartService.productsList$.subscribe((productsList) => {
      console.log("I received a new value for productsList", productsList)
      this.productsList = productsList || [];
    });
  }

  @Output() openNavEvent = new EventEmitter<void>();

  openNav(): void {
    this.openNavEvent.emit();
  }

  logout() {
    this._authService.logout();
  }

  removeFromCart(product: ProductI) {
    this._cartService.removeFromCart(product);
  }
}
