import { Component } from '@angular/core';

@Component({
  selector: 'app-sidenav-wrapper',
  templateUrl: './sidenav-wrapper.component.html',
  styleUrls: ['./sidenav-wrapper.component.scss']
})
export class SidenavWrapperComponent {

  isExpanded: boolean = false;

  currentMenu: number = 0

  constructor() { }

  ngOnInit(): void {
  }

  onMenuChange(menuId: number){
    this.currentMenu = menuId
  }

  menuItemStyleClassResolver(menuId: number){
    if(menuId == this.currentMenu)
      return "background: #00b6bd;"
    else
      return ""
  }

}
