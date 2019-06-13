package org.damm.userservice.repository;

import org.damm.userservice.pojo.LoginAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginAttemptRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int save(LoginAttempt loginAttempt) {
		String sql = "insert into public.login_attempt (success, login_email, user_id, failure_reason) values (?,?,?,?)";
		return jdbcTemplate.update(sql, loginAttempt.isSuccess(), loginAttempt.getLoginEmail(),
				loginAttempt.getUserId(), loginAttempt.getFailureReason());
	}
}
