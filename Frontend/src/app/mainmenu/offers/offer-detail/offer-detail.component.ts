import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';

import { Offer } from '../offer.model';
import { OfferService } from '../../../shared/offer.service';
import {DataStorageService} from "../../../shared/data-storage.service";

@Component({
  selector: 'app-recipe-detail',
  templateUrl: './offer-detail.component.html',
  styleUrls: ['./offer-detail.component.css']
})
export class OfferDetailComponent implements OnInit {
  offer!: Offer;
  index!: number;

  constructor(private dataStorageService: DataStorageService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.route.params
      .subscribe(
        (params: Params) => {
          this.index = +params['index'];
          this.offer = this.dataStorageService.getOffer(this.index);
        }
      );
  }

}
