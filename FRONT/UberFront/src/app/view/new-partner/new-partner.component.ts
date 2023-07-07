import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Observable, startWith, map } from 'rxjs';
import Fuse from 'fuse.js';

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

  ngOnInit() {
    this.filteredAddresses = this.partnerForm.get('address')!.valueChanges.pipe(
      startWith(''),
      map(value => this.getAddressSuggestions(value as string))
    );
  }

  getAddressSuggestions(address: string): string[] {
    const addresses = [
      '123 Rue Principale',
      '456 Avenue des Fleurs',
      '789 Boulevard Central',
      '10 Rue de la Liberté',
      '22 Rue du Commerce'
    ];

    const options = {
      includeScore: true,
      threshold: 0.4,
      keys: ['address']
    };

    const fuse = new Fuse(addresses.map(a => ({ address: a })), options);

    const results = fuse.search(address);
    const bestMatches = results.map(result => result.item.address);

    this.suggestedAddresses = bestMatches;

    return this.suggestedAddresses;
  }

  submit() {
    const post = {
      ...this.partnerForm.value,
      roles: ['ROLE_OWNER']
    }
    console.log(post);
  }
}
