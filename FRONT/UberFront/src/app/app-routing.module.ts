import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './view/homepage/homepage.component';
import { SignInComponent } from './view/sign-in/sign-in.component';
import { SignUpComponent } from './view/sign-up/sign-up.component';
import { AllRestaurantsComponent } from './view/all-restaurants/all-restaurants.component';
import { ProfileComponent } from './view/profile/profile.component';
import { NewPartnerComponent } from './view/new-partner/new-partner.component';
import { DriverProfileComponent } from './view/driver-profile/driver-profile.component';
import { SignupDriverComponent } from './view/signup-driver/signup-driver.component';

const routes: Routes = [
  { path: '', component: HomepageComponent },
  { path: 'home', component: HomepageComponent },
  { path: 'sign-in', component: SignInComponent },
  { path: 'sign-up', component: SignUpComponent},
  { path: 'restaurants', component: AllRestaurantsComponent},
  { path: 'new-partner', component: NewPartnerComponent},
  { path: 'profile', component: ProfileComponent},
  { path: 'driver-profile', component: DriverProfileComponent},
  { path: 'driver-signup', component: SignupDriverComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
