package model;

import java.util.Objects;

/**
 * Represents a user review for a specific app.
 * <p>
 * This class encapsulates the rating, comment, and user details associated
 * with a review. It serves as a data transfer object between the database
 * and the app logic.
 */
public class Review {
    private final int reviewId;
    private int userId;
    private String userName;
    private int appId;
    private double rating;
    private String comment;

    /**
     * Constructs a new Review instance.
     * <p>
     * Use {@code reviewId = 0} when creating a new review that hasn't been
     * saved to the database yet.
     *
     * @param reviewId The unique ID of the review.
     * @param userId   The ID of the user who wrote the review.
     * @param userName The username of the reviewer (for display).
     * @param appId    The ID of the app being reviewed.
     * @param rating   The numeric rating given (e.g., 1.0 to 5.0).
     * @param comment  The text feedback provided by the user.
     */
    public Review(final int reviewId, final int userId, final String userName, final int appId, final double rating,final String comment) {
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

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getAppId() {
        return appId;
    }

    public double getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }

    public void setComment(final String comment) {
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