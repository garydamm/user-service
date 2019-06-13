package org.damm.userservice.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class LoginAttempt {

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date date;

	private boolean success;
	private String loginEmail;
	private Long userId;
	private String failureReason;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
