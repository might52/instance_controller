package org.might.instancecontroller.models.servers;

import java.io.Serializable;

public class Link implements Serializable {

    private static final long serialVersionUID = -5467907854893799481L;

    private String href;
     private String rel;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    @Override
    public String toString() {
        return "Link{" +
                "href='" + href + '\'' +
                ", rel='" + rel + '\'' +
                '}';
    }
}
