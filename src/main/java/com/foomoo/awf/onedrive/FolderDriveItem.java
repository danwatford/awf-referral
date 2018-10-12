package com.foomoo.awf.onedrive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FolderDriveItem {

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private final Map<String, Object> folder = Collections.emptyMap();

    public FolderDriveItem() {

    }

    public FolderDriveItem(@JsonProperty  String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getFolder() {
        return folder;
    }

    @Override
    public String toString() {
        return "FolderDriveItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", folder=" + folder +
                '}';
    }
}
