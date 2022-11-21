import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';
import {MatToolbarModule} from '@angular/material/toolbar';
import {AddOfferComponent} from "./add-offer/add-offer.component";
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";
import {SidenavWrapperComponent} from "./sidenav-wrapper/sidenav-wrapper.component";
import {MainMenuRoutingModule} from "./mainmenu-routing.module";
import {CarouselComponent} from './carousel/carousel.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatRadioModule} from "@angular/material/radio";
import {MatButtonModule} from "@angular/material/button";
import {OfferComponent} from "./offers/offer.component";
import {OfferListComponent} from "./offers/offer-list/offer-list.component";
import {OfferItemComponent} from "./offers/offer-list/offer-item/offer-item.component";
import {OfferDetailComponent} from "./offers/offer-detail/offer-detail.component";
import {OfferService} from "./offers/offer.service";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatFormFieldModule} from "@angular/material/form-field";
import {TextFieldModule} from "@angular/cdk/text-field";
import {MatInputModule} from "@angular/material/input";
import {MatPaginatorModule} from "@angular/material/paginator";

@NgModule({
  declarations: [AddOfferComponent, LoginComponent, HomeComponent, SidenavWrapperComponent,
    CarouselComponent, OfferComponent, OfferListComponent,
    OfferItemComponent, OfferDetailComponent],
  imports: [
    CommonModule,
    MainMenuRoutingModule,

    // NG Material Modules
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatToolbarModule,
    FormsModule,
    MatRadioModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatGridListModule,
    MatFormFieldModule,
    TextFieldModule,
    MatInputModule,
    MatPaginatorModule
  ],
  providers: [OfferService],
})
export class MainMenuModule {
}
