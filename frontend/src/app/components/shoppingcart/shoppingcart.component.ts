import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ShoppingCart } from '../../models/ShoppingCart.model';
import { LoginService } from '../../services/login.service';
import { ShoppingCartService } from '../../services/shoppingCart.service';
import { ItemToBuy } from '../../models/ItemToBuy.model';
import { ItemService } from '../../services/item.service';

@Component({
  selector: 'app-shoppingcart',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './shoppingcart.component.html',
  styleUrl: './shoppingcart.component.css'
})
export class ShoppingcartComponent {

  newOrder: boolean;
  itemsToBuy: [] = [];
  firstItem: ItemToBuy;

  constructor(public loginService: LoginService, public shoppingCartService: ShoppingCartService, public itemService: ItemService){}

  ngOnInit(){
    this.shoppingCartService.getUserItems(this.loginService.getCurrentUser().username).subscribe(
      items => {
        this.itemsToBuy = items.content;
        this.firstItem = items.content[0];
        if(items.content.length > 0){
          this.newOrder = false;
        }else{
          this.newOrder = true;
        }
      },
      error => console.log(error)
    );
  }

  getItemImage(id: number | undefined){
    return this.itemService.getItemImage(id);
  }
  
  deleteItem(){}

}
