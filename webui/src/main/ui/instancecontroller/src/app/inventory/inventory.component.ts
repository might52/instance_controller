import { Component, OnInit } from '@angular/core';
import { ComputeService } from "../compute.service";
import { Server } from "../models/Server";

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {

  title="Inventory";
  servers: Array<Server>;

  static SHUT_OFF: "SHUTOFF";

  constructor(private computeService: ComputeService) { }

  getServers(): void {
    this.computeService.getServers()
      .subscribe(data => this.servers = data);
  }

  ngOnInit() {
    this.getServers();
  }

}


