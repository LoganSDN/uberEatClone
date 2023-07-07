/// <reference types="@types/googlemaps" />

import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlacesService {
  public placeSubject: BehaviorSubject<google.maps.places.PlaceResult | undefined> = new BehaviorSubject<google.maps.places.PlaceResult | undefined>(undefined);
  public placeObservable$ = this.placeSubject.asObservable();
  
  constructor() { }

  getPlaceSuggestions(input: string): Observable<string[]> {
    const autocompleteService = new google.maps.places.AutocompleteService();
    const request: google.maps.places.AutocompletionRequest = {
      input: input,
      componentRestrictions: {
        country: ['FR']
      }
    };
    return new Observable<string[]>(observer => {
      autocompleteService.getPlacePredictions(request, predictions => {
        observer.next(predictions?.map(prediction => prediction.description));
        observer.complete();
      });
    });
  }
}
