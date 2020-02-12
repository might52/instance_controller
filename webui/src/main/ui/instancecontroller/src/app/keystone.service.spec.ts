import { TestBed } from '@angular/core/testing';

import { KeystoneService } from './keystone.service';

describe('KeystoneService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: KeystoneService = TestBed.get(KeystoneService);
    expect(service).toBeTruthy();
  });
});
