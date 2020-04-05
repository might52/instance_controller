import {Component, OnInit} from '@angular/core';
import {InstanceService} from "../instance.service";
import {Instance} from "../models/Instance";

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
        data.forEach((el)=> {
          el.isEnableEdit = true;
        });
        this.functions = data;
      });
  }

  getInstance(id: number): Instance {
    let inst = new Instance();
    this.instanceService.getInstance(id).subscribe(data => {
      inst = data;
    });

    return inst;
  }

  enableEdit(id:number):void {
    this.functions.forEach(el => {
      if (el.function.id === id) {
        el.isEnableEdit=!el.isEnableEdit;
      }
    })
  }

  cancelChanges(id:number):void {
    this.functions.forEach(el => {
      if (el.function.id == id) {
        el.isEnableEdit=!el.isEnableEdit;
      }
      this.getInstances();
    });
  }

  saveChanges(id:number):void {
    this.functions.forEach(el => {
      if (el.function.id == id) {
        el.isEnableEdit=!el.isEnableEdit;
        this.instanceService.saveInstance(id, el.function);
      }
    });
  }

  ngOnInit() {
    this.getInstances();
  }
}
