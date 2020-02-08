import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from "./dashboard/dashboard.component";
import { InventoryComponent } from "./inventory/inventory.component";
import { EndpointsComponent } from "./endpoints/endpoints.component";

const routes: Routes = [
  { path: "dashboard", component: DashboardComponent },
  { path: "inventory", component: InventoryComponent },
  { path: "endpoints", component: EndpointsComponent },
  { path: "", redirectTo: '/dashboard', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
