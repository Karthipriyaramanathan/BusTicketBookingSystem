/**
 * 
 */
package com.customerservices;

import java.sql.Timestamp;

/**
 * @author KARTHIPRIYA R
 *
 */
public class Feedback implements Comparable<Feedback>{
    private int feedbackId;
    private int userId;
    private String comments;
    private int rating;
    private Timestamp feedbackDate;
	/**
	 * @param feedbackId
	 * @param userId
	 * @param comments
	 * @param rating
	 * @param feedbackDate
	 */
	public Feedback(int feedbackId, int userId, String comments, int rating, Timestamp feedbackDate) {
		super();
		this.feedbackId = feedbackId;
		this.userId = userId;
		this.comments = comments;
		this.rating = rating;
		this.feedbackDate = feedbackDate;
	}
	/**
	 * @return the feedbackId
	 */
	public int getFeedbackId() {
		return feedbackId;
	}

	/**
	 * @param feedbackId the feedbackId to set
	 */
	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * @return the feedbackDate
	 */
	public Timestamp getFeedbackDate() {
		return feedbackDate;
	}

	/**
	 * @param feedbackDate the feedbackDate to set
	 */
	public void setFeedbackDate(Timestamp feedbackDate) {
		this.feedbackDate = feedbackDate;
	}
	@Override
    public int compareTo(Feedback other) {
        // Compare feedbacks based on rating
        return Integer.compare(this.rating, other.rating);
    }
	@Override
	public String toString() {
	    return String.format("------------------------------------------------------------------------------------------------------------------------\n" +
	                         "| %-11s | %-7s | %-30s | %-7s | %-20s    |\n" +
	                         "------------------------------------------------------------------------------------------------------------------------\n" +
	                         "| %-11d | %-7d | %-30s | %-7d | %-20s |\n" +
	                         "------------------------------------------------------------------------------------------------------------------------",
	                         "FeedbackID", "UserID", "Comments", "Rating", "FeedbackDate",
	                         getFeedbackId(), getUserId(), getComments(), getRating(), getFeedbackDate());
	}

	
}
