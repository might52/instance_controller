/*
 * MIT License
 *
 * Copyright (c) 2024 Andrei F._
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.might.instancecontroller.dba.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "function")
public class Function {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "prefix", unique = true)
    private String prefix;
    @ManyToOne(targetEntity = Configuration.class, optional = false)
    private Configuration configuration;
    @ManyToOne(targetEntity = Image.class, optional = false)
    private Image image;
    @ManyToOne(targetEntity = Flavor.class, optional = false)
    private Flavor flavor;
    @Column(name = "scaleInAbility", nullable = false)
    private Boolean scaleInAbility;
    @Column(name = "scaleOutAbility", nullable = false)
    private Boolean scaleOutAbility;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Flavor getFlavor() {
        return flavor;
    }

    public void setFlavor(Flavor flavor) {
        this.flavor = flavor;
    }

    public Boolean getScaleInAbility() {
        return scaleInAbility;
    }

    public void setScaleInAbility(Boolean scaleInAbility) {
        this.scaleInAbility = scaleInAbility;
    }

    public Boolean getScaleOutAbility() {
        return scaleOutAbility;
    }

    public void setScaleOutAbility(Boolean scaleOutAbility) {
        this.scaleOutAbility = scaleOutAbility;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Function)) {
            return false;
        }

        Function function = (Function) o;

        if (!getName().equals(function.getName())) {
            return false;
        }

        if (!getPrefix().equals(function.getPrefix())) {
            return false;
        }

        if (getConfiguration() != null ? !getConfiguration().equals(function.getConfiguration()) : function.getConfiguration() != null) {
            return false;
        }

        if (!getImage().equals(function.getImage())) {
            return false;
        }

        if (!getFlavor().equals(function.getFlavor())) {
            return false;
        }

        if (!getScaleInAbility().equals(function.getScaleInAbility())) {
            return false;
        }

        return getScaleOutAbility().equals(function.getScaleOutAbility());
    }

}
