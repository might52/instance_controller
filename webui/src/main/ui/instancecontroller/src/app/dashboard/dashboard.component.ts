import { Component, OnInit } from '@angular/core';
import { InstanceService } from "../instance.service";
import { Instance } from "../models/Instance";
import {interval, Subscription} from "rxjs";
import {log} from "util";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  title="Dashboard";
  instances: Array<Instance>;
  private updateDashboard: Subscription;
  constructor(private instanceService: InstanceService) { }

  createNewInstance(id: number): void {
    this.instanceService.createNewInstance(id);
  }

  releaseInstance(serverId: number, functionId: number) {
    this.instanceService.releaseInstance(serverId, functionId);
  }

  releaseLastInstance(functionId: number) {
    this.instanceService.releaseLastInstance(functionId);
  }

  reInstantiateInstance(serverId: number) {
    this.instanceService.reInstantiateInstance(serverId);
  }

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
    this.updateDashboard = interval(10000).subscribe(
      (val) => {
        log("perform the refresh dashboard");
        this.getInstances();
      });
  }
}
