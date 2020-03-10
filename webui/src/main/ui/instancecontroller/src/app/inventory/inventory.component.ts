import {Component, Input, OnInit} from '@angular/core';
import {ComputeService} from "../compute.service";
import {Server} from "../models/Server";
import {ServerActions} from "../models/ServerActions";

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {

  title="Inventory";
  servers: Array<Server>;
  serverActions = ServerActions;

  constructor(
    private computeService: ComputeService) {

  }

  getServers(): void {
    this.computeService.getServers()
      .subscribe(data => this.servers = data);
  }

  serverAction(serverActions:ServerActions, serverId:String): void {
    console.log(`serverAction: ${serverActions}`);
    switch (serverActions) {
      case ServerActions.START:
        console.log(`serverAction: ${ServerActions} from START case`);
        this.computeService.startServer(serverId);
        break;
      case ServerActions.STOP:
        console.log(`serverAction: ${ServerActions} from STOP case`);
        this.computeService.stopServer(serverId);
        break;
      case ServerActions.SOFT_REBOOT:
        console.log(`serverAction: ${ServerActions} from SOFT_REBOOT case`);
        this.computeService.softRebootServer(serverId);
        break;
      case ServerActions.HARD_REBOOT:
        console.log(`serverAction: ${ServerActions} from HARD_REBOOT case`);
        this.computeService.hardRebootServer(serverId);
        break;
      default:
        console.log(`serverAction: ${ServerActions} from default case`);
        break;
    }
  }

  ngOnInit() {
    this.getServers();
  }

}


