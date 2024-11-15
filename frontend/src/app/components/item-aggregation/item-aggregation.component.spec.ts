import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemAggregationComponent } from './item-aggregation.component';

describe('ItemAggregationComponent', () => {
  let component: ItemAggregationComponent;
  let fixture: ComponentFixture<ItemAggregationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ItemAggregationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ItemAggregationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
