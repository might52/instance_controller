import { Component, OnInit } from '@angular/core';
import { ComputeService } from "../compute.service";
import { Server } from "../models/Server";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  title="Instance state";
  servers: Array<Server>;

  constructor(private computeService: ComputeService) { }

  getServers(): void {
    this.computeService.getServers()
      .subscribe(data => this.servers = data);
  }

  ngOnInit() {
    this.getServers();
  }

}
