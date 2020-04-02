import { Component, OnInit } from '@angular/core';
import { InstanceService } from "../instance.service";
import { Instance } from "../models/Instance";

@Component({
  selector: 'app-endpoints',
  templateUrl: './endpoints.component.html',
  styleUrls: ['./endpoints.component.css']
})
export class EndpointsComponent implements OnInit {

  title="Function configuration";
  functions: Array<Instance>;

  constructor(private instanceService: InstanceService) { }

  getInstances(): void {
    this.instanceService.getInstances()
      .subscribe(data => {
        // let correctedData = [];
        // data.forEach((el) => {
        //   correctedData.push(Server.fromJSON(el));
        // });
        // this.servers = correctedData;
        this.functions = data
      });
  }

  ngOnInit() {
    this.getInstances();
  }

}
