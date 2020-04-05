export class Instance {
  function: Function;
  servers: Array<FunctionServer>;
  isEnableEdit: Boolean;
  /*
    static fromJSON(json: InstanceJSON): Instance {
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
  */
}

export interface Function {
  id: number;
  name: string;
  description: string;
  prefix: string;
  configuration: Configuration;
  image: Image;
  flavor: Flavor;
  scaleInAbility: Boolean;
  scaleOutAbility: Boolean;
}

export interface FunctionServer {
  id: number;
  name: string;
  serverId: string;
}

export interface Configuration {
  id: number;
  script: string;
}

export interface Flavor extends BaseRef{
}

export interface Image extends BaseRef{
}

export interface BaseRef {
  id: number;
  reference: string;
}

export interface InstanceJSON {
  id: string;
  name: string;
}
