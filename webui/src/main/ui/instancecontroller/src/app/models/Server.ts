import { PowerState } from "./PowerState";

export class Server {
  name: string;
  image?: ImgFlv;
  instance_name?: string;
  addresses: Addresses;
  links?: Array<Link>;
  flavor?: ImgFlv;
  status?: string;
  created?: string;
  id: string;
  user_id?: string;
  progress?: string;
  updated?: string;
  task_state?: string;
  power_state?: number;
  vm_state?: string;

  static fromJSON(json: ServerJSON): Server {
    let server = Object.create(Server.prototype);
    return Object.assign(server, json, {
      created: new Date(json["created"]).toLocaleString(),
      instance_name: json["OS-EXT-SRV-ATTR:instance_name"],
      vm_state: json["OS-EXT-STS:vm_state"],
      task_state: json["OS-EXT-STS:task_state"],
      power_state: PowerState[json["OS-EXT-STS:power_state"]],
      }
    );
  }
}

export interface ServerJSON {
  name: string;
  image?: ImgFlv;
  instance_name?: string;
  addresses: Addresses;
  links?: Array<Link>;
  flavor?: ImgFlv;
  status?: string;
  created?: string;
  id: string;
  user_id?: string;
  progress?: string;
  updated?: string;
  task_state?: string;
  power_state?: string;
  vm_state?: string;
}

export interface Addresses {
  networks?: Networks;
}

export interface Networks {
  external_network?: Array<Network>;
  internal_network?: Array<Network>;
}

export interface Network {
  macAddr: string;
  version: number;
  addr: string;
  type: string;
}

export interface ImgFlv {
  id?: string;
  links?: Array<Link>;
}

export interface Link {
  href?: string;
  rel?: string;
}
