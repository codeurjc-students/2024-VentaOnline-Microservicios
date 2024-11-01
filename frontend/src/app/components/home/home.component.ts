import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { Component } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { Item } from '../../models/Item.model';
import { ItemService } from '../../services/item.service';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home.component.html'
  //styleUrls: ['./home.component.css']
})
export class HomeComponent {

  name: any;
  itemsFounded: Item[] = [];
  items: Item[] = [];

  constructor(private router: Router, public loginService: LoginService, public itemService: ItemService){}

  ngOnInit(){
    this.itemService.getItems().subscribe(
      items => {

      },
      error => alert(error)
    );
  }

  logout(){
    //this.loginService.logout();
    //this.router.navigate(['/home']);
  }

  searchItem(){
    this.itemService.getFoundedItems().subscribe(
      items => {
        let data: any = items;
        //console.log(data);
        for(var i=0; i<data.content.length; i++){
          if(data.content[i].title == this.name){
            this.itemsFounded.push(data.content[i]);
          }
        }
      },
      error => alert(error)
    );
  }

}
