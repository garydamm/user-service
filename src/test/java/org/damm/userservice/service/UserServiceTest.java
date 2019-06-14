package org.damm.userservice.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.damm.userservice.exception.InvalidLoginException;
import org.damm.userservice.pojo.LoginAttempt;
import org.damm.userservice.pojo.LoginRequest;
import org.damm.userservice.pojo.User;
import org.damm.userservice.repository.LoginAttemptRepository;
import org.damm.userservice.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class UserServiceTest {

	private LoginAttempt loginAttempt;
	private UserService userService;
	private LoginRequest loginRequest;

	@Before
	public void before() {
		UserRepository userRepository = mock(UserRepository.class);
		LoginAttemptRepository loginAttemptRepository = mock(LoginAttemptRepository.class);

		ArgumentCaptor<LoginAttempt> argument = ArgumentCaptor.forClass(LoginAttempt.class);
		Mockito.when(loginAttemptRepository.save(argument.capture())).thenAnswer(new Answer<Integer>() {
			@Override
			public Integer answer(InvocationOnMock invocation) {
				captureLoginAttempt(argument.getValue());
				return 1;
			}
		});

		userService = new UserService();
		userService.setUserRepository(userRepository);
		userService.setLoginAttemptRepository(loginAttemptRepository);

		User user = new User();
		user.setEmail("email");
		user.setPassword("password");
		user.setName("name");

		when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

		loginRequest = new LoginRequest();
		loginRequest.setEmail(user.getEmail());
		loginRequest.setPassword(user.getPassword());

	}

	@Test
	public void success() {
		userService.login(loginRequest);
		assertEquals(true, loginAttempt.isSuccess());
	}

	@Test(expected = InvalidLoginException.class)
	public void invalidPassword() {
		loginRequest.setPassword("bad");
		userService.login(loginRequest);
	}

	@Test(expected = InvalidLoginException.class)
	public void invalidUser() {
		loginRequest.setEmail("bad");
		userService.login(loginRequest);
	}

	private LoginAttempt captureLoginAttempt(LoginAttempt loginAttempt) {
		this.loginAttempt = loginAttempt;
		return this.loginAttempt;
	}

}
