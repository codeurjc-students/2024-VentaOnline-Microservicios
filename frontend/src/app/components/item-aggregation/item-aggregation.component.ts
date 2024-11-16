import { CommonModule } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { Item } from '../../models/Item.model';
import { Direction } from 'readline';
import { ShoppingCart } from '../../models/ShoppingCart.model';
import { User } from '../../models/User.model';
import { ItemService } from '../../services/item.service';

@Component({
  selector: 'app-item-aggregation',
  standalone: true,
  imports: [RouterModule, CommonModule, FormsModule],
  templateUrl: './item-aggregation.component.html',
  styleUrl: './item-aggregation.component.css'
})
export class ItemAggregationComponent {

  item: Item;
  sizes = [{value: 'S', selected: false},{value: 'M', selected: false},{value: 'L', selected: false},{value: 'XL', selected: false}];
  user: User;
  shoppingCart: ShoppingCart;

  @ViewChild('imageFile')
  imageFile: any;

  constructor(public itemService: ItemService, public router: Router) {
    let stocks = [0,0,0,0];

    this.shoppingCart = {totalCost: 0, items: []};
    this.user = {username: '', name: '', email: '', password: '', passwordConfirmation: '', rol: 'USER', direction: {street: '', number: '', zipCode: '', city: ''}, favouritesItems: [], shoppingCart: this.shoppingCart, orders: []};

    this.item = {name:'', description:'', price:0, gender:'', type:'', stock:0, sizes:[], stocks:stocks, favouritesUsers:[], itemsToBuy:[]}
  }

  addItem(){
    if(!this.item.name || !this.item.description || !this.item.price || !this.item.gender || !this.item.type){
      alert("some fields are empty");
    }else {
      let image: any;
      image = this.imageFile.nativeElements.files[0];
      if(!image){
        alert("please insert a picture for the product");
      }else{
        this.itemService.setItem(this.item).subscribe(
          (item:any) => this.addItemImage(item),
          (error:any) => console.log(error)
        );
      }
    }
  }

  addItemImage(item: Item){
    const image = this.imageFile.nativeElements.files[0];
    if(image){
      let formData = new FormData();
      formData.append("imageFile",image);
      this.itemService.setItemImage(item, formData).subscribe(
        (_:any) => {
          alert("item succesfully created");
          this.router.navigate(['/items/page']);
        },
        (error:any) => console.log(error)
      );
    }
  }
}
