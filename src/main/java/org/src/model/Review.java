package org.src.model;

import java.util.Objects;

/**
 * Represents a user review for a specific application.
 *
 * <p>This class encapsulates details such as the rating, user comment, and the identity of the
 * reviewer.
 */
public final class Review {
  private int reviewId;
  private int userId;
  private String userName;
  private int appId;
  private double rating;
  private String comment;

  /** Default constructor required for JSON deserialization. */
  public Review() {}

  /**
   * Constructs a new Review instance with all details.
   *
   * @param reviewId the unique ID of the review
   * @param userId the ID of the user creating the review
   * @param userName the display name of the reviewer
   * @param appId the ID of the application being reviewed
   * @param rating the numeric rating
   * @param comment the textual feedback provided by the user
   */
  public Review(
      final int reviewId,
      final int userId,
      final String userName,
      final int appId,
      final double rating,
      final String comment) {
    this.reviewId = reviewId;
    this.userId = userId;
    this.userName = userName;
    this.appId = appId;
    this.rating = rating;
    this.comment = comment;
  }

  public int getReviewId() {
    return reviewId;
  }

  public void setReviewId(int reviewId) {
    this.reviewId = reviewId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public int getAppId() {
    return appId;
  }

  public void setAppId(int appId) {
    this.appId = appId;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  @Override
  public String toString() {
    return userName + ": " + rating + "* - " + comment;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    Review review = (Review) object;
    return reviewId == review.reviewId;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(reviewId);
  }
}
