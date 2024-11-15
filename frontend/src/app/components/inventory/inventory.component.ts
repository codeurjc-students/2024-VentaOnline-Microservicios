import { Component } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { FormsModule } from '@angular/forms';
import { ItemService } from '../../services/item.service';
import { Item } from '../../models/Item.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-inventory',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './inventory.component.html',
  styleUrl: './inventory.component.css'
})
export class InventoryComponent {

  name: any;
  capacity: number = 0;
  founded: number = 0;
  foundedItems: Item[] = [];
  items: Item[] = [];
  data: any;
  tam: number = 10;
  tam1: number = 10;

  constructor(public loginService: LoginService, public itemService: ItemService){}

  ngOnInit(){
    this.itemService.getItems(this.tam).subscribe(
      items => {
        this.capacity = items.totalElements - 1;
        this.items = items.content;
      },
      error => console.log(error)
    );
  }

  getUnknownImage(){
    this.loginService.getAnonymousUserImage();
  }

  itemImage(id: number | undefined){
    return  this.itemService.getItemImage(id);
  }

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

  moreInventoryFoundedItems(){
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
