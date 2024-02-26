/**
 * 
 */
package com.customerservices;

import java.sql.Timestamp;

/**
 * @author KARTHIPRIYA R
 *
 */
public class FAQ {
		private static int faqId=9;
	    private String question;
	    private String answer;
	    private int userId;
	    private String category;
	    private Timestamp creationDate;

		/**
		 * @param question
		 * @param answer
		 * @param category
		 */
		public FAQ(int faqid,String question, String answer, String category) {
			super();
			this.faqId=faqId++;
			this.question = question;
			this.answer = answer;
			this.category = category;
		}
		/**
		 * @param question
		 * @param answer
		 * @param userId
		 * @param category
		 * @param creationDate
		 */
		public FAQ( int faqId,String question, String answer, int userId, String category, Timestamp creationDate) {
			super();
			this.faqId=faqId++;
			this.question = question;
			this.answer = answer;
			this.userId = userId;
			this.category = category;
			this.creationDate = creationDate;
		}
		/**
		 * @return the faqId
		 */
		public int getFaqId() {
			return faqId;
		}
		/**
		 * @param faqId the faqId to set
		 */
		public void setFaqId(int faqId) {
			this.faqId = faqId;
		}
		/**
		 * @return the question
		 */
		public String getQuestion() {
			return question;
		}
		/**
		 * @param question the question to set
		 */
		public void setQuestion(String question) {
			this.question = question;
		}
		/**
		 * @return the answer
		 */
		public String getAnswer() {
			return answer;
		}
		/**
		 * @param answer the answer to set
		 */
		public void setAnswer(String answer) {
			this.answer = answer;
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
		 * @return the category
		 */
		public String getCategory() {
			return category;
		}
		/**
		 * @param category the category to set
		 */
		public void setCategory(String category) {
			this.category = category;
		}
		/**
		 * @return the creationDate
		 */
		public Timestamp getCreationDate() {
			return creationDate;
		}
		/**
		 * @param creationDate the creationDate to set
		 */
		public void setCreationDate(Timestamp creationDate) {
			this.creationDate = creationDate;
		}
		@Override
		public String toString() {
			return "FAQ [Question : " + getQuestion() + ",Answer :" + getAnswer()
					+ "UserId :" + getUserId() + ",Category :" + getCategory() + ",CreationDate :"
					+ getCreationDate() + "]";
		}
}
