package com.androidtitan.simplehero.model;

import java.util.List;

/**
 * Created by amohnacs on 10/22/16.
 */

public class Hero {

    private String name;
    private String description;
    private String imageUrl;
    private String imageExtension;

    public Hero() {

    }

    public Hero(String name, String description, String imageUrl, String imageExtension) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.imageExtension = imageExtension;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageExtension() {
        return imageExtension;
    }

    public void setImageExtension(String imageExtension) {
        this.imageExtension = imageExtension;
    }

}
