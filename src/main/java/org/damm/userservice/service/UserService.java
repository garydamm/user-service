package org.damm.userservice.service;

import org.damm.userservice.exception.InvalidLoginException;
import org.damm.userservice.pojo.LoginAttempt;
import org.damm.userservice.pojo.LoginRequest;
import org.damm.userservice.pojo.User;
import org.damm.userservice.repository.LoginAttemptRepository;
import org.damm.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoginAttemptRepository loginAttemptRepository;

	public void create(User user) {
		userRepository.create(user);
	}

	public User login(LoginRequest loginRequest) {

		String failureMessage = null;
		LoginAttempt loginAttempt = new LoginAttempt();
		loginAttempt.setLoginEmail(loginRequest.getEmail());

		User user = userRepository.findByEmail(loginRequest.getEmail());
		if (user == null) {
			failureMessage = "invalid email";
			loginAttempt.setFailureReason(failureMessage);
		} else if (!user.getPassword().equals(loginRequest.getPassword())) {
			failureMessage = "invalid password";
			loginAttempt.setUserId(user.getId());
			loginAttempt.setFailureReason(failureMessage);
		} else {
			loginAttempt.setSuccess(true);
			loginAttempt.setUserId(user.getId());
		}
		loginAttemptRepository.save(loginAttempt);
		if (loginAttempt.isSuccess()) {
			return user;
		} else {
			throw new InvalidLoginException(failureMessage);
		}
	}

	protected void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	protected void setLoginAttemptRepository(LoginAttemptRepository loginAttemptRepository) {
		this.loginAttemptRepository = loginAttemptRepository;
	}

}
