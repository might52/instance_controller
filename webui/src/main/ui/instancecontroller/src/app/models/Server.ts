export interface Server {
  name: string;
  image?: ImgFlv;
  instance_name?: string;
  addresses: Addresses;
  links?: Array<Link>;
  flavor?: ImgFlv;
  status?: string;
  created?: string;
  vm_state?: string;
  id: string;
  user_id?: string;
  progress?: string;
  updated?: string;
  task_state?: string;
  power_state?: number;
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

/*
export interface Image {
  id?: string;
  links?: Array<Link>;
}

export interface Flavor {
  id?: string;
  links?: Array<Link>;
}
*/


