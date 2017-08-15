package com.hb.lottery.domain;

public class Header {

	   private String messengerID;
	    private String timestamp;
	    private String transactionType;
	    private String digest;

	    public String getMessengerID() {
	        return messengerID;
	    }

	    public void setMessengerID(String messengerID) {
	        this.messengerID = messengerID;
	    }

	    public String getTimestamp() {
	        return timestamp;
	    }

	    public void setTimestamp(String timestamp) {
	        this.timestamp = timestamp;
	    }

	    public String getTransactionType() {
	        return transactionType;
	    }

	    public void setTransactionType(String transactionType) {
	        this.transactionType = transactionType;
	    }

	    public String getDigest() {
	        return digest;
	    }

	    public void setDigest(String digest) {
	        this.digest = digest;
	    }
}
