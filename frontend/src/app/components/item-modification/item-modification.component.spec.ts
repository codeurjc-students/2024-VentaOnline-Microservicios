import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemModificationComponent } from './item-modification.component';

describe('ItemModificationComponent', () => {
  let component: ItemModificationComponent;
  let fixture: ComponentFixture<ItemModificationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ItemModificationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ItemModificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
