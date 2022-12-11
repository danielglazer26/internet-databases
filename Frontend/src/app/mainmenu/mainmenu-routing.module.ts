import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AddOfferComponent} from "./add-offer/add-offer.component";
import {LoginComponent} from "./login/login.component";
import {SidenavWrapperComponent} from "./sidenav-wrapper/sidenav-wrapper.component";
import {OfferComponent} from "./offers/offer.component";
import {OfferDetailComponent} from "./offers/offer-detail/offer-detail.component";
import {RegisterComponent} from "./register/register.component";
import {UserProfileComponent} from "./user-profile/user-profile.component";
import {AdministratorPanelComponent} from "./administrator-panel/administrator-panel.component";

const routes: Routes = [
  {
    path: '',
    component: SidenavWrapperComponent,
    children: [
      {
        path: '',
        component: OfferComponent
      },
      {
        path: 'add-offer',
        component: AddOfferComponent
      },
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'offer-detail/:index/:source',
        component: OfferDetailComponent
      },
      {
        path: 'register',
        component: RegisterComponent
      },
      {
        path: 'user-profile',
        component: UserProfileComponent
      },
      {
        path: 'administrator-panel',
        component: AdministratorPanelComponent
      }
    ]
  },
  {
    path: '**',
    redirectTo: '/',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class MainMenuRoutingModule {
}
