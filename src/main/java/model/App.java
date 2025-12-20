package model;

import java.util.List;
import java.util.Objects;

/**
 * This class Represents the Application In PlayStore
 * This Class Stores Like id,name,author name,version,features,rating,installCount,installed status.
 */

public final class App {

    private final int appId;
    private String name;
    private Author author;
    private String description;
    private double version;
    private List<String> features;
    private double rating =0;
    private boolean installed;
    private int installedCount;
    private List<Review> reviews;

    /**
     * This Constructor new app creation details
     * @param appId             Unique identifier for the app.
     * @param name           Name of the Application
     * @param author     Author of the application
     * @param version        Current Version Number
     * @param features       Application features
     * @param rating         Appilication rating
     */
    public App(final int appId, final String name,Author author,final String description, final double version, final  List<String> features,final double rating,final int installedCount) {
        this.appId = appId;
        this.name = name;
        this.author = author;
        this.description = description;
        this.version = version;
        this.features = features;
        this.rating= rating;
        this.installedCount =0;
    }

    public int getAppId() {
        return appId;
    }

    public String getName() {
        return name;
    }

    public String getAuthorName() {
        return author.getAuthorName();
    }

    public String getDescription() {
        return description;
    }

    public double getVersion() {
        return version;
    }

    public double getRating() {
        return rating;
    }

    public int getInstalledCount() {
        return installedCount;
    }

    public List<String> getFeatures() {
        return features;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setFeatures(final List<String> features) {
        this.features = features;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setVersion(final double version) {
        this.version = version;
    }

    public boolean isInstalled() {
        return installed;
    }

    public void setReviews(final List<Review> reviews) {
        this.reviews = reviews;
    }

    public void install() {this.installed = true; }
    public void uninstall() {this.installed = false; }
    public void installedCount() {
        installedCount++;
    }

    @Override
    public String toString() {
        return "App{" +
                "id=" + appId +
                ", name='" + name + '\'' +
                ", authorName='" + author.getAuthorName() + '\'' +
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
        return appId == app.appId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(appId);
    }
}
