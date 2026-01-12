package org.src.model;

import java.util.List;
import java.util.Objects;

/**
 * Represents an Application in the PlayStore.
 *
 * <p>This model class holds details about the app, such as its name, author, version, ratings, and
 * associated reviews.
 */
public final class App {

  private int appId;
  private String name;
  private User author;
  private String description;
  private double version;
  private List<String> features;
  private double rating = 0;
  private int installedCount = 0;
  private List<Review> reviews;

  /** Default constructor required by Jackson for JSON deserialization. */
  public App() {}

  /**
   * Constructs a new App with specific details.
   *
   * @param appId Unique identifier for the app.
   * @param name Name of the Application.
   * @param author Author of the application.
   * @param description Short description of the app.
   * @param version Current Version Number.
   * @param features List of application features.
   * @param rating Application rating.
   * @param installedCount Total number of installations.
   */
  public App(
      final int appId,
      final String name,
      User author,
      final String description,
      final double version,
      final List<String> features,
      final double rating,
      final int installedCount) {
    this.appId = appId;
    this.name = name;
    this.author = author;
    this.description = description;
    this.version = version;
    this.features = features;
    this.rating = rating;
    this.installedCount = installedCount;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAppId() {
    return appId;
  }

  public void setAppId(int appId) {
    this.appId = appId;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  /**
   * Helper method to get the author's username safely.
   *
   * @return the username of the author
   */
  public String getAuthorName() {
    return author.getUsername();
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getVersion() {
    return version;
  }

  public void setVersion(double version) {
    this.version = version;
  }

  public List<String> getFeatures() {
    return features;
  }

  public void setFeatures(List<String> features) {
    this.features = features;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public int getInstalledCount() {
    return installedCount;
  }

  public void setInstalledCount(int installedCount) {
    this.installedCount = installedCount;
  }

  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }

  @Override
  public String toString() {
    String authorName = (author != null) ? author.getUsername() : "Unknown";
    return "App{"
        + "id="
        + appId
        + ", name='"
        + name
        + '\''
        + ", authorName='"
        + authorName
        + '\''
        + ", description='"
        + description
        + '\''
        + ", version="
        + version
        + ", rating="
        + rating
        + '}';
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
