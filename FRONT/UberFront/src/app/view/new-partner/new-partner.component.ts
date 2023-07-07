import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Observable, startWith, map, Subscription } from 'rxjs';
import Fuse from 'fuse.js';
import { PlacesService } from 'src/app/services/places.service';

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

  @ViewChild('addressText') addressText!: ElementRef;

  protected placeSubscription!: Subscription;

  constructor(private _placesService: PlacesService) { }

  ngAfterViewInit(): void {
    this._placesService.getPlaceAutocomplete(this.addressText);
  }

  onAddressChange(): void {
    this.placeSubscription =
      this._placesService.placeObservable$.subscribe(
        (place) => {
          if (place) {
            console.log('nouvelle adresse : ', place.formatted_address);
          }
        }
      );
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

  submit() {
    const post = {
      ...this.partnerForm.value,
      roles: ['ROLE_OWNER']
    }
    this._placesService.getCity(this._placesService.placeSubject.value!);
    console.log(post);
  }
}
