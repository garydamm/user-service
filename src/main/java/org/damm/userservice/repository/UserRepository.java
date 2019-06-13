package org.damm.userservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.damm.userservice.exception.InvalidUserException;
import org.damm.userservice.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int create(User user) {
		String sql = "insert into public.user (name, email, password) values (?,?,?)";
		try {
			return jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword());
		} catch (DuplicateKeyException e) {
			throw new InvalidUserException("Email Exists");
		} catch (DataAccessException e) {
			throw new InvalidUserException("Bad Data");
		}
	}

	public User findByEmail(String email) {
		String sql = "select id,name, email, password from public.user where email = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new RowMapper<User>() {
				@Override
				public User mapRow(ResultSet rs, int rowNum) throws SQLException {
					User user = new User();
					user.setId(rs.getLong("id"));
					user.setEmail(rs.getString("email"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					return user;
				}
			}, email);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
