import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ItemService } from '../../services/item.service';

@Component({
  selector: 'app-item-modification',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './item-modification.component.html',
  styleUrl: './item-modification.component.css'
})
export class ItemModificationComponent {

  id: any;
  item: any;

  constructor(public router: Router, public activatedRoute: ActivatedRoute, public itemService: ItemService){}

  ngOnInit(){
    this.id = this.activatedRoute.snapshot.params['id'];
    this.itemService.getItem(this.id).subscribe(
      item => this.item = item.content,
      error => console.log(error)
    );
  }

  itemImage(id: number){
    this.itemService.getItemImage(id);
  }
}
