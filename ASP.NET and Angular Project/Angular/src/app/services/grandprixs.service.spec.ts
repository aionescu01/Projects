import { TestBed } from '@angular/core/testing';

import { GrandprixsService } from './grandprixs.service';

describe('GrandprixsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GrandprixsService = TestBed.get(GrandprixsService);
    expect(service).toBeTruthy();
  });
});
