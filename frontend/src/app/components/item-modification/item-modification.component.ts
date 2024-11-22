import { CommonModule } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ItemService } from '../../services/item.service';
import { FormsModule } from '@angular/forms';
import { Item } from '../../models/Item.model';

@Component({
  selector: 'app-item-modification',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './item-modification.component.html',
  styleUrl: './item-modification.component.css'
})
export class ItemModificationComponent {

  id: any;
  item: any;
  sizes: any;
  stock: any;
  stocks: any;

  @ViewChild('imageFile')
  imageFile: any;

  constructor(public router: Router, public activatedRoute: ActivatedRoute, public itemService: ItemService){}

  ngOnInit(){
    this.id = this.activatedRoute.snapshot.params['id'];
    this.itemService.getItem(this.id).subscribe(
      item => {
        this.item = item.content;
        this.sizes = item.content.totalSizes;
      },
      error => console.log(error)     
    );
  }

  addStock(){
    this.itemService.setStock(this.item.id, this.stock).subscribe(
      (_:any) => this.router.navigate(['/items/' + this.item.id + '/page']),
      (error:any) => console.log(error)
    );
  }

  itemImage(item: Item){
    this.itemService.getItemImage(item);
  }

  updateItem(){
    this.itemService.putItem(this.item).subscribe(
      item => {
        this.item = item.content;
        this.router.navigate(['/items/' + this.item.id + '/page']);
      },
      error => console.log(error)
    );    
  }


}
