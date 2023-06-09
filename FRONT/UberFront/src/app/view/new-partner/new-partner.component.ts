import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Observable, startWith, map, Subscription, lastValueFrom } from 'rxjs';
import Fuse from 'fuse.js';
import { PlacesService } from 'src/app/services/places.service';
import { APICallService } from 'src/app/services/api-call.service';

@Component({
  selector: 'app-new-partner',
  templateUrl: './new-partner.component.html',
  styleUrls: ['./new-partner.component.scss']
})
export class NewPartnerComponent implements OnInit {
  partnerForm = new FormGroup({
    address: new FormControl('', Validators.required),
    restaurantName: new FormControl('', Validators.required),
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required),
    phone: new FormControl('', Validators.required)
  });

  suggestedAddresses: string[] = [];

  filteredAddresses!: Observable<string[]>;


  protected placeSubscription!: Subscription;

  constructor(private _placesService: PlacesService,
              private _apiCallService: APICallService) { }

  ngAfterViewInit(): void {
  }

  ngOnInit() {
    this.partnerForm.get('address')!.valueChanges.pipe(
      startWith(''),
      map(value => this.getAddressSuggestions(value as string))
    ).subscribe();
  }

  getAddressSuggestions(address: string): void {
    this._placesService.getPlaceSuggestions(address).subscribe(suggestedAddresses => {
      this.suggestedAddresses = suggestedAddresses;
    });
  }
  private _parseAddress(address: string): Promise<any> {
    const addrProperties: string[] = address.split(',');
    
    return new Promise((resolve, reject) => {
      this._placesService.geocodeAddress(address).subscribe(
        (coords) => {
          const res = {
            street: addrProperties[0],
            city: addrProperties[1],
            lat: coords.lat,
            lng: coords.lng,
            ZIP: '69007', // TO CHANGE
          };
          console.log(res);
          resolve(res);
        },
        (error) => {
          console.error('Error geocoding address', error);
          reject(error);
        }
      );
    });
  }
  
  async submit() {
    try {
      const address = await this._parseAddress(
        this.partnerForm.get('address')!.value as string
      );
      const post = {
        address: address,
        name: this.partnerForm.get('restaurantName')!.value,
        openingTime: '10:00',
        closingTime: '22:00',
      };
      console.log(post)
      await lastValueFrom(this._apiCallService.post('/api/restaurant/add', post));
    } catch (error) {
      // Gérez les erreurs ici
    }
      // firstName: this.partnerForm.get('firstName')!.value,
      // lastName: this.partnerForm.get('lastName')!.value,
      // email: this.partnerForm.get('email')!.value,
      // password: this.partnerForm.get('password')!.value,
      // roles: ['ROLE_OWNER']
  }
}
