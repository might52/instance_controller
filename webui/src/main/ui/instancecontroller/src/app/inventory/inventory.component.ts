import { Component, OnInit } from '@angular/core';
import { ComputeService } from "../compute.service";
import { Server, Network, Flavor, Link, Image, Networks, Addresses } from "../models/Server"

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {

  title="Inventory";
  servers: Array<Server>;

  constructor(private computeService: ComputeService) {
  }

  getServers(): void {
    this.computeService.getServers()
      .subscribe(data => this.servers = data);
  }

  ngOnInit() {
    this.getServers();
  }

}


