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
import {MatGridListModule} from "@angular/material/grid-list";
import {MatFormFieldModule} from "@angular/material/form-field";
import {TextFieldModule} from "@angular/cdk/text-field";
import {MatInputModule} from "@angular/material/input";
import {MatPaginatorModule} from "@angular/material/paginator";
import {RegisterComponent} from './register/register.component';
import {MatDialogModule} from "@angular/material/dialog";
import {DragScrollModule} from "ngx-drag-scroll";
import {MatTableModule} from "@angular/material/table";
import { UserProfileComponent } from './user-profile/user-profile.component';
import { OfferWithDeleteComponent } from './user-profile/offer-with-delete/offer-with-delete.component';

@NgModule({
  declarations: [AddOfferComponent, LoginComponent, HomeComponent, SidenavWrapperComponent,
    CarouselComponent, OfferComponent, OfferListComponent,
    OfferItemComponent, OfferDetailComponent, RegisterComponent, UserProfileComponent, OfferWithDeleteComponent],
  imports: [
    CommonModule,
    MainMenuRoutingModule,
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
    MatPaginatorModule,
    MatDialogModule,
    DragScrollModule,
    MatTableModule
  ]
})

export class MainMenuModule {
}
