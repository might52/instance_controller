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

  createNewInstance(id: number): void {
    this.instanceService.createNewInstance(id);
    this.getInstances();
  }

  deleteInstance(): void {
    this.getInstances();
  }

  ngOnInit() {
    this.getInstances();
  }

  releaseInstance(serverId: number, functionId: number) {
    this.instanceService.releaseInstance(serverId, functionId);
    this.getInstances();
  }

  releaseLastInstance(functionId: number) {
    this.instanceService.releaseLastInstance(functionId);
    this.getInstances();
  }
}
