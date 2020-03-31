package org.might.instancecontroller.models.servers;

import java.io.Serializable;
import java.util.List;

public class Image implements Serializable {

    private static final long serialVersionUID = 4721982255482832067L;

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

}
