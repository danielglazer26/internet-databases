import {NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import {AddOfferComponent} from "./add-offer/add-offer.component";
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";
import {OffersComponent} from "./offers/offers.component";
import {SidenavWrapperComponent} from "./sidenav-wrapper/sidenav-wrapper.component";
import {MainMenuRoutingModule} from "./mainmenu-routing.module";
import { CarouselComponent } from './carousel/carousel.component';
import {FormsModule} from "@angular/forms";
import {MAT_RADIO_DEFAULT_OPTIONS, MatRadioModule} from "@angular/material/radio";
import {MatButtonModule} from "@angular/material/button";

@NgModule({
  declarations: [AddOfferComponent, LoginComponent, HomeComponent, OffersComponent, SidenavWrapperComponent, CarouselComponent],
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
    MatButtonModule
  ]
})
export class MainMenuModule {}
