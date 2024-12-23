import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule, RouterOutlet } from '@angular/router';
import { Component } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { Item } from '../../models/Item.model';
import { ItemService } from '../../services/item.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, RouterOutlet],
  templateUrl: './home.component.html',
  providers: [ItemService, LoginService]
})
export class HomeComponent {

  name: any;
  capacity: number = 0;
  founded: number = 0;
  private tam: number = 10;
  private tam1: number = 10;
  foundedItems: Item[] = [];
  items: Item[] = [];
  data: any;
  favourites: number = 0;
  favouritesItems: Item[] = [];
  id: number = 39;
  
  constructor(private router: Router, public loginService: LoginService, public itemService: ItemService){}

  ngOnInit(){
    this.itemService.getItems(this.tam).subscribe(
      items => {
        this.capacity = items.totalElements-1;
        this.items = items.content;
      },
      error => console.log(error)
    );   
  }

  registration(){
    this.router.navigate(['/signup']);
  }

  logout(){
    this.loginService.logout();
    this.router.navigate(['/home']);
  }

  //showFavouritesItems(){
  //  this.itemService.getUserFavouritesItems(this.tam, this.loginService.getCurrentUser().username).subscribe(
  //    items => {
  //      this.favourites = items.totalElements-1;
  //      this.favouritesItems = items.content;
  //    },
  //    error => console.log(error)
  //  );
  //}

  searchItem(){
    this.itemService.getTotalItems().subscribe(
      items => {
        
        let firstItems: Item[] = [];

        //items founded by a name
				for(var i=0; i < items.length; i++) {
					if(items[i].name.includes(this.name)){
						this.foundedItems.push(items[i]);
					}
				}
        this.founded = this.foundedItems.length;
        
        //first 10 items founded by a name
        let count = 0;
        for(var i=0; i < this.foundedItems.length; i++){
          if(count < this.tam1){
            count++;
            firstItems.push(this.foundedItems[i]);
            //console.log(this.foundedItems[i]);
          }
          else
            break;
        }  
        this.data = firstItems;  

      },
      error => console.log(error)
    );
  }

  getAnonymousImage(){
    return this.loginService.getAnonymousUserImage();
  }

  itemImage(item: Item){
    return  this.itemService.getItemImage(item);
  }

  showMoreItems(){
    this.tam += 10;
    this.itemService.getItems(this.tam).subscribe(
      items => this.items = items.content,
      error => console.log(error)
    );
  }

  showMoreFavItems(){}

  showMoreFoundedItems(){
    this.tam1 += 10;
    this.itemService.getItems(this.tam1).subscribe(
      items => {
        let firstItems: Item[] = [];

         //the rest ot items founded by a name
         let count = 0;
         for(var i=0; i < this.foundedItems.length; i++){
           if(count < this.tam1){
             count++;
             firstItems.push(this.foundedItems[i]);
           }
           else
             break;
         }  
         this.data = firstItems;  
      },
      error => console.log(error)
    );
  }

}
