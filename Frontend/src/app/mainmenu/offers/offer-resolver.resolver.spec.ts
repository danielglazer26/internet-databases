import { TestBed } from '@angular/core/testing';

import { OfferResolverResolver } from './offer-resolver.resolver';

describe('OfferResolverResolver', () => {
  let resolver: OfferResolverResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(OfferResolverResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
