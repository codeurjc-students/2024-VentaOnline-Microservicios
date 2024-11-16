import { CommonModule } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ItemService } from '../../services/item.service';
import { FormsModule } from '@angular/forms';

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
  sizes = [{value: 'S', selected: false},{value: 'M', selected: false},{value: 'L', selected: false},{value: 'XL', selected: false}];

  @ViewChild('imageFile')
  imageFile: any;

  constructor(public router: Router, public activatedRoute: ActivatedRoute, public itemService: ItemService){}

  ngOnInit(){
    this.id = this.activatedRoute.snapshot.params['id'];
    this.itemService.getItem(this.id).subscribe(
      item => {
        this.item = item.content;
        for(var i=0; i<item.content.sizes.length; i++){
          if(item.content.sizes[i] != null){
            this.sizes[i].selected = true;
          }
        }
      },
      error => console.log(error)     
    );
  }

  itemImage(id: number){
    this.itemService.getItemImage(id);
  }
}
