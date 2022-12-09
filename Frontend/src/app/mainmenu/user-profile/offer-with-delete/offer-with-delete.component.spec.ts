import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferWithDeleteComponent } from './offer-with-delete.component';

describe('OfferWithDeleteComponent', () => {
  let component: OfferWithDeleteComponent;
  let fixture: ComponentFixture<OfferWithDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfferWithDeleteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OfferWithDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
