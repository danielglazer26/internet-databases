import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AddOfferComponent} from "./add-offer/add-offer.component";
import {LoginComponent} from "./login/login.component";
import {OffersComponent} from "./offers/offers.component";
import {SidenavWrapperComponent} from "./sidenav-wrapper/sidenav-wrapper.component";
import {HomeComponent} from "./home/home.component";

const routes: Routes = [
  {
    path: '',
    component: SidenavWrapperComponent,
    children: [
      {
        path: '',
        component: HomeComponent
      },
      {
        path: 'offers',
        component: OffersComponent
      },
      {
        path: 'add-offer',
        component: AddOfferComponent
      },
      {
        path: 'login',
        component: LoginComponent
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
