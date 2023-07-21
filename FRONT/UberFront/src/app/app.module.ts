import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './components/app/app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { SearchBarComponent } from './components/search-bar/search-bar.component';
import { HomepageComponent } from './view/homepage/homepage.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { SignInComponent } from './view/sign-in/sign-in.component';
import { SignUpComponent } from './view/sign-up/sign-up.component';
import { CarouselComponent } from './components/carousel/carousel.component';
import { FooterComponent } from './components/footer/footer.component';
import { MatDividerModule } from '@angular/material/divider';
import { LoginComponent } from './components/login/login.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { InscriptionComponent } from './components/inscription/inscription.component';
import { AllRestaurantsComponent } from './view/all-restaurants/all-restaurants.component';
import { ProfileComponent } from './view/profile/profile.component';
import { NewPartnerComponent } from './view/new-partner/new-partner.component';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { DriverProfileComponent } from './view/driver-profile/driver-profile.component';
import { SignupDriverComponent } from './view/signup-driver/signup-driver.component';
import { OneRestaurantComponent } from './view/one-restaurant/one-restaurant.component';
import { MatMenuModule } from '@angular/material/menu';
import { CartComponent } from './view/cart/cart.component';
import { MatCardModule } from '@angular/material/card';
import { DriverComponent } from './view/driver/driver.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    SearchBarComponent,
    HomepageComponent,
    SidebarComponent,
    SignInComponent,
    SignUpComponent,
    CarouselComponent,
    FooterComponent,
    LoginComponent,
    InscriptionComponent,
    AllRestaurantsComponent,
    ProfileComponent,
    NewPartnerComponent,
    DriverProfileComponent,
    SignupDriverComponent,
    OneRestaurantComponent,
    CartComponent,
    DriverComponent
  ],
  imports: [
    MatButtonModule,
    MatIconModule,
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatSelectModule,
    MatDividerModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatAutocompleteModule,
    MatMenuModule,
    MatCardModule
  ],
  providers: [HttpClientModule],
  bootstrap: [AppComponent]
})
export class AppModule { }
