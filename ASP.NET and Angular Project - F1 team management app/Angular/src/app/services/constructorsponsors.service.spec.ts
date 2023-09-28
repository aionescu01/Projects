import { TestBed } from '@angular/core/testing';

import { ConstructorsponsorsService } from './constructorsponsors.service';

describe('ConstructorsponsorsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ConstructorsponsorsService = TestBed.get(ConstructorsponsorsService);
    expect(service).toBeTruthy();
  });
});
