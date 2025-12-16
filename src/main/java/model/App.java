package model;

import java.util.List;
import java.util.Objects;

/**
 * This class Represents the Application In PlayStore
 * This Class Stores Like id,name,author name,version,features,rating,installCount,installed status.
 */

public final class App {

    private final int id;
    private String name;
    private final String authorName;
    private String description;
    private double version;
    private List<String> features;
    private double rating =0;
    private boolean installed;
    private int installedCount;

    /**
     * This Constructor new app creation details
     * @param id             Unique identifier for the app.
     * @param name           Name of the Application
     * @param authorName     Author of the application
     * @param version        Current Version Number
     * @param features       Application features
     */
    public App(final int id, final String name,final String authorName,final String description, final double version, final  List<String> features) {
        this.id = id;
        this.name = name;
        this.authorName = authorName;
        this.description = description;
        this.version = version;
        this.features = features;
        this.installedCount =0;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public String getAuthorName(){ return authorName; }

    public String getDescription() { return description; }

    public double getVersion() { return version; }

    public  List<String> getFeatures() {return features; }

    public boolean isInstalled() {return installed; }

    public int getInstallCount(){ return installedCount; }

    public void setName(final String name) { this.name = name; }

    public void setDescription(final String description) { this.description = description; }

    public void setVersion(final double version) { this.version = version; }

    public void setFeatures(final  List<String> features) {this.features = features; }

    public void install() {this.installed = true; }

    public void uninstall() {this.installed = false; }

    public void installedCount() {
        installedCount++;
    }

    @Override
    public String toString() {
        return "App{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authorName='" + authorName + '\'' +
                ", description='" + description + '\'' +
                ", version=" + version +
                ", features='" + features + '\'' +
                ", rating=" + rating +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        App app = (App) object;
        return id == app.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
