import { Component, OnInit } from '@angular/core';
import { InstanceService } from "../instance.service";
import { Instance } from "../models/Instance";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  title="Function state";
  instances: Array<Instance>;

  constructor(private instanceService: InstanceService) { }

  getInstances(): void {
    this.instanceService.getInstances()
      .subscribe(data => {
        // let correctedData = [];
        // data.forEach((el) => {
        //   correctedData.push(Server.fromJSON(el));
        // });
        // this.servers = correctedData;
        this.instances = data
      });
  }

  ngOnInit() {
    this.getInstances();
  }

}
