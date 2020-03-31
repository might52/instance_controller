package org.might.instancecontroller.models.servers;

import java.io.Serializable;
import java.util.List;

public class Flavor implements Serializable {

    private static final long serialVersionUID = -7641110751549841463L;

    private String id;
    private List<Link> links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "Flavor{" +
                "id='" + id + '\'' +
                ", links=" + links +
                '}';
    }
}
