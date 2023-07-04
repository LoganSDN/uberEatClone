import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent {
  
  isLogged: boolean = false;

  constructor(private _authService: AuthService) {
    this._authService.isLogged$.subscribe((isLogged) => {
      console.log("I received a new value for isLogged", isLogged)
      this.isLogged = isLogged;
    });
  }

  toggleNav() {
    if (document.getElementById('mySidenav')!.style.width == '300px') {
      this._closeNav();
    } else {
      this._openNav();
    }
  }

  private _openNav(): void {
    document.getElementById('mySidenav')!.style.width = '300px';
    document.body.style.marginLeft = '300px';
  }

  private _closeNav(): void {
    document.getElementById('mySidenav')!.style.width = '0';
    document.body.style.marginLeft = '0';
    document.body.style.backgroundColor = 'white';
  }
}