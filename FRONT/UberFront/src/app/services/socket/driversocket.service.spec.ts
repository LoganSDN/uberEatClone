import { TestBed } from '@angular/core/testing';

import { DriversocketService } from './driversocket.socket';

describe('DriversocketService', () => {
  let service: DriversocketService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DriversocketService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
