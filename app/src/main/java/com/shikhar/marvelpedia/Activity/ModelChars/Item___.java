
package com.shikhar.marvelpedia.Activity.ModelChars;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item___ {

    @SerializedName("resourceURI")
    @Expose
    private String resourceURI;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Item___() {
    }

    /**
     * 
     * @param resourceURI
     * @param name
     */
    public Item___(String resourceURI, String name) {
        super();
        this.resourceURI = resourceURI;
        this.name = name;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
