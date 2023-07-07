import { Component, EventEmitter, Output } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  isLogged: boolean = false;

  constructor(private _authService: AuthService) {
    this._authService.isLogged$.subscribe((isLogged) => {
      console.log("I received a new value for isLogged", isLogged)
      this.isLogged = isLogged;
    });
  }

  @Output() openNavEvent = new EventEmitter<void>();

  openNav(): void {
    this.openNavEvent.emit();
  }

  logout() {
    this._authService.logout();
  }
}
