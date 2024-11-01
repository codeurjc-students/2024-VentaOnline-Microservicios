import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { OrderComponent } from './components/order/order.component';
import { AdminOrderComponent } from './components/admin-order/admin-order.component'; 
import { InventoryComponent } from './components/inventory/inventory.component';

export const routes: Routes = [
    { path: 'home', component: HomeComponent },
    { path: 'signup', component: SignupComponent },
    { path: 'orders/user/:username', component: OrderComponent },
    { path: 'orders/admin', component: AdminOrderComponent },
    { path: 'items', component: InventoryComponent },
    { path: 'signup', component: SignupComponent },
    { path: 'login', component: LoginComponent },
    { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
  export class AppRoutingModule { }