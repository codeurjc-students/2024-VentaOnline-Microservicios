import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { OrderComponent } from './components/order/order.component';
import { AdminOrderComponent } from './components/admin-order/admin-order.component'; 
import { InventoryComponent } from './components/inventory/inventory.component';
import { ShoppingcartComponent } from './components/shoppingcart/shoppingcart.component';
import { LogoutComponent } from './components/logout/logout.component';
import { ItemAggregationComponent } from './components/item-aggregation/item-aggregation.component';
import { ProductComponent } from './components/product/product.component';

export const routes: Routes = [
    { path: 'home', component: HomeComponent },
    { path: 'signup', component: SignupComponent },
    { path: 'orders/user/:username', component: OrderComponent },
    { path: 'orders/admin', component: AdminOrderComponent },
    { path: 'items', component: InventoryComponent },
    { path: 'items/:id/favourites/:username/new', component: HomeComponent },
    { path: 'items/:id/page', component: ProductComponent},
    { path: 'items/page', component: ItemAggregationComponent },
    { path: 'signup', component: SignupComponent },
    { path: 'login', component: LoginComponent },
    { path: 'logout', component: LogoutComponent },
    { path: 'shoppingcart/page', component: ShoppingcartComponent},
    { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
  export class AppRoutingModule { }