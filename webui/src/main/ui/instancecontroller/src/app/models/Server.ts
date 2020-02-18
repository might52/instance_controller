export interface Server {
  name: string;
  image?: Image;
  instance_name?: string;
  addresses: Addresses;
  links?: Array<Link>;
  flavor?: Flavor;
  status?: string;
  created?: string;
  vm_state?: string;
  id: string;
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

export interface Image {
  id?: string;
  links?: Array<Link>;
}

export interface Link {
  href?: string;
  rel?: string;
}

export interface Flavor {
  id?: string;
  links?: Array<Link>;
}

