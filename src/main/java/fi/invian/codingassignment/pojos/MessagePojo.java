package fi.invian.codingassignment.pojos;

import java.sql.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MessagePojo {

	private int messageId;

	@NotEmpty(message = "Message should contain title")
	private String title;
	@NotEmpty(message = "Message should contain body")
	private String body;
	@NotEmpty(message = "Message should have sender")
	private int senderId;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date sentAt;
	

	@Size(min = 1, max = 5, message = "Message should have min 1 and maxium 5 recipients")
	private List<Integer> receiverIds;

	public MessagePojo(int messageId, String title, String body, Date sentAt, int senderId, List<Integer> receiverIds) {
		this.messageId = messageId;
		this.title = title;
		this.body = body;
		this.sentAt = sentAt;
		this.senderId = senderId;
		this.receiverIds = receiverIds;
	}

	public MessagePojo(String title, String body, Date sentAt, int senderId) {
		this.title = title;
		this.body = body;
		this.sentAt = sentAt;
		this.senderId = senderId;
	}

	public MessagePojo() {

	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	/*
	 * TODO minor bug. Date is fomatted in JSON 00 based. Meaning, 01 month will be
	 * one less
	 */
	public String getSentAt() {
//		return sentAt; Figure this one out
		return sentAt.toString();
	}

	public void setSentAt(Date sentAt) {
		this.sentAt = sentAt;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public List<Integer> getReceiverIds() {
		return receiverIds;
	}

	public void setReceiverIds(List<Integer> receiverIds) {
		this.receiverIds = receiverIds;
	}

	@Override
	public String toString() {
		return "MessagePojo [messageId=" + messageId + ", title=" + title + ", body=" + body + ", sentAt=" + sentAt
				+ ", senderId=" + senderId + ", receiverIds=" + receiverIds + "]";
	}

}
