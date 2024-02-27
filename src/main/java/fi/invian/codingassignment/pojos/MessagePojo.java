package fi.invian.codingassignment.pojos;

import java.sql.Timestamp;
import java.util.List;

public class MessagePojo {
        
		private int messagePojoId;
        private String title;
        private String body;
        private Timestamp sentAt;
        private int senderId;
        private List<Integer> receiverIds; 

    
		public MessagePojo() {
        }

        public MessagePojo(String title, String body, int senderId) {
            this.title = title;
            this.body = body;
            this.senderId = senderId;
        }

     
		public int getMessagePojoId() {
            return messagePojoId;
        }

        public void setMessagePojoId(int MessagePojoId) {
            this.messagePojoId = MessagePojoId;
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

        public Timestamp getSentAt() {
            return sentAt;
        }

        public void setSentAt(Timestamp sentAt) {
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
     			return "MessagePojo [messagePojoId=" + messagePojoId + ", title=" + title + ", body=" + body + ", sentAt="
     					+ sentAt + ", senderId=" + senderId + "]";
     		}

    }

