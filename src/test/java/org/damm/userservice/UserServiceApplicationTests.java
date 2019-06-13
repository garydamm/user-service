package org.damm.userservice;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;

import org.damm.userservice.pojo.LoginRequest;
import org.damm.userservice.pojo.LoginResponse;
import org.damm.userservice.pojo.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private String email;

	private String password;

	private String name;

	@Before
	public void before() {
		email = "myemail";
		password = "password";
		name = "myname";
	}

	@After
	public void after() throws SQLException {
		jdbcTemplate.update(
				"delete from public.login_attempt where user_id = (select id from public.user where email = ?)", email);
		jdbcTemplate.update("delete from public.user where email = ?", email);
	}

	@Test
	public void createUser() throws Exception {
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setName(name);

		String usersJson = objectMapper.writeValueAsString(user);
		mockMvc.perform(post("/user").content(usersJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void login() throws Exception {
		createUser();
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail(email);
		loginRequest.setPassword(password);

		String loginJson = objectMapper.writeValueAsString(loginRequest);
		MvcResult result = mockMvc
				.perform(post("/user/login").content(loginJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		LoginResponse loginResponse = objectMapper.readValue(contentAsString, LoginResponse.class);
		assertEquals(name, loginResponse.getName());
	}

}
