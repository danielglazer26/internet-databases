import {Injectable} from '@angular/core';

import {Offer} from './offer.model';;
import {Subject} from "rxjs";

@Injectable()
export class OfferService {
  recipesChanged = new Subject<Offer[]>();

  private recipes: Offer[] = [
    new Offer(
      'Tasty Schnitzel',
      'A super-tasty Schnitzel - just awesome!',
      'https://www.bhg.com/thmb/3Vf9GXp3T-adDlU6tKpTbb-AEyE=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/white-modern-house-curved-patio-archway-c0a4a3b3-aa51b24d14d0464ea15d36e05aa85ac9.jpg',
   ),
    new Offer('Big Fat Burger',
      'What else you need to say?',
      'https://www.bhg.com/thmb/I277gk9XbxINQiJ8hGNqrKprOWU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/large-cape-cod-exterior-circular-driveway-0abc3587-ae293581d2384f9088652b6705c0ef56.jpg',
  ),
    new Offer('Big Fat Burger',
      'What else you need to say?',
      'https://wp-tid.zillowstatic.com/11/GettyImages-482137501-featured-129f0e.jpg',
),
    new Offer('Big Fat Burger',
      'What else you need to say?',
      'https://images.pexels.com/photos/8031881/pexels-photo-8031881.jpeg'),
    new Offer('Big Fat Burger',
      'What else you need to say?',
      'https://wp-tid.zillowstatic.com/11/GettyImages-482137501-featured-129f0e.jpg'),
    new Offer('Big Fat Burger',
      'What else you need to say?',
      'https://doorsteps-ar.rdcpix.com/4d51b9541635b530861a5257b1b4c9d1c-f2139429951od-w480_h360_x2.webp'),
    new Offer('Big Fat Burger',
      'What else you need to say?',
      'https://www.bhg.com/thmb/I277gk9XbxINQiJ8hGNqrKprOWU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/large-cape-cod-exterior-circular-driveway-0abc3587-ae293581d2384f9088652b6705c0ef56.jpg'),
    new Offer('Big Fat Burger',
      'What else you need to say?',
      'https://images.pexels.com/photos/106399/pexels-photo-106399.jpeg'),
    new Offer('Big Fat Burger',
      'What else you need to say?',
      'https://images.pexels.com/photos/8031881/pexels-photo-8031881.jpeg'),
    new Offer('Big Fat Burger',
      'What else you need to say?',
      'https://images.pexels.com/photos/106399/pexels-photo-106399.jpeg'),
    new Offer('Big Fat Burger',
      'What else you need to say?',
      'https://www.bhg.com/thmb/I277gk9XbxINQiJ8hGNqrKprOWU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/large-cape-cod-exterior-circular-driveway-0abc3587-ae293581d2384f9088652b6705c0ef56.jpg'),
    new Offer('Big Fat Burger',
      'What else you need to say?',
      'https://www.bhg.com/thmb/I277gk9XbxINQiJ8hGNqrKprOWU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/large-cape-cod-exterior-circular-driveway-0abc3587-ae293581d2384f9088652b6705c0ef56.jpg'),
    new Offer('Big Fat Burger',
      'What else you need to say?',
      'https://www.bhg.com/thmb/I277gk9XbxINQiJ8hGNqrKprOWU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/large-cape-cod-exterior-circular-driveway-0abc3587-ae293581d2384f9088652b6705c0ef56.jpg'),
    new Offer('Big Fat Burger',
      'What else you need to say?',
      'https://www.bhg.com/thmb/I277gk9XbxINQiJ8hGNqrKprOWU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/large-cape-cod-exterior-circular-driveway-0abc3587-ae293581d2384f9088652b6705c0ef56.jpg'),
    new Offer('Big Fat Burger',
      'What else you need to say?',
      'https://www.bhg.com/thmb/I277gk9XbxINQiJ8hGNqrKprOWU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/large-cape-cod-exterior-circular-driveway-0abc3587-ae293581d2384f9088652b6705c0ef56.jpg'),
    new Offer('Big Fat Burger',
      'What else you need to say?',
      'https://www.bhg.com/thmb/I277gk9XbxINQiJ8hGNqrKprOWU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/large-cape-cod-exterior-circular-driveway-0abc3587-ae293581d2384f9088652b6705c0ef56.jpg'),
    new Offer('Big Fat Burger',
      'What else you need to say?',
      'https://www.bhg.com/thmb/I277gk9XbxINQiJ8hGNqrKprOWU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/large-cape-cod-exterior-circular-driveway-0abc3587-ae293581d2384f9088652b6705c0ef56.jpg'),
    new Offer('Big Fat Burger',
      'What else you need to say?',
      'https://www.bhg.com/thmb/I277gk9XbxINQiJ8hGNqrKprOWU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/large-cape-cod-exterior-circular-driveway-0abc3587-ae293581d2384f9088652b6705c0ef56.jpg'),
    new Offer('Big Fat Burger',
      'What else you need to say?',
      'https://www.bhg.com/thmb/I277gk9XbxINQiJ8hGNqrKprOWU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/large-cape-cod-exterior-circular-driveway-0abc3587-ae293581d2384f9088652b6705c0ef56.jpg'),
    new Offer('Big Fat Burger',
      'What else you need to say?',
      'https://www.bhg.com/thmb/I277gk9XbxINQiJ8hGNqrKprOWU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/large-cape-cod-exterior-circular-driveway-0abc3587-ae293581d2384f9088652b6705c0ef56.jpg')
  ];

  constructor(){
  }

  getRecipes() {
    return this.recipes.slice();
  }

  getRecipe(index: number) {
    return this.recipes[index];
  }


  addRecipe(recipe: Offer) {
    this.recipes.push(recipe);
    this.recipesChanged.next(this.recipes.slice());
  }

  updateRecipe(index: number, newRecipe: Offer) {
    this.recipes[index] = newRecipe;
    this.recipesChanged.next(this.recipes.slice());
  }

  deleteRecipe(index: number) {
    this.recipes.splice(index, 1);
    this.recipesChanged.next(this.recipes.slice());
  }
}
