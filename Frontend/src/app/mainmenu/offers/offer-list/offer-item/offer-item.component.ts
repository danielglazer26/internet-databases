import { Component, OnInit, Input } from '@angular/core';

import { Offer } from '../../offer.model';

@Component({
  selector: 'app-recipe-item',
  templateUrl: './offer-item.component.html',
  styleUrls: ['./offer-item.component.css']
})
export class OfferItemComponent implements OnInit {
  @Input() recipe!: Offer;
  @Input() index!: number;

  ngOnInit() {
  }
}
