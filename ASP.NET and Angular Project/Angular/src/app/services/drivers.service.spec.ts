import { TestBed } from '@angular/core/testing';

import { DriversService } from './drivers.service';

describe('CarsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DriversService = TestBed.get(DriversService);
    expect(service).toBeTruthy();
  });
});
