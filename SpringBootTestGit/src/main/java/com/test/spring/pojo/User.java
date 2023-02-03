package com.test.spring.pojo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.UuidGenerator.Style;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usersdata")
public class User implements UserDetails {

	/**
	 *
	 */
	private static final long serialVersionUID = -7291655991631079699L;

	@Id
	@org.hibernate.validator.constraints.UUID
	@UuidGenerator(style = Style.TIME)
	private String UUID;
	@Column(name = "email", nullable = false, unique = true)
	@Email(message = "Not valid email")
	@NotBlank(message = "email cannot be blank")
	@Size(min = 5, max = 50, message = "email should be between 5 and 50 character")
	private String email;
	@Column
	@NotBlank(message = "password cannot be blank")
	@Size(min = 8, max = 30, message = "password should be atleast 8 character long and maximum 50 character long")
	private String password;
	@Column(name = "account_non_locked")
	private boolean accountNonLocked;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "rolesdata", joinColumns = @JoinColumn(name = "UUID"))
	@Column(name = "user_role")
	private List<String> roles;
	@Column
	@NotBlank(message = "username cannot be blank")
	@Size(min = 1, max = 25)
	private String username;

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public User() {
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// return List.of();
//		List<GrantedAuthority> authorities = new ArrayList<>();
//		for (String role : getRoles()) {
//			authorities.add(new SimpleGrantedAuthority(role));
//		}
//		return authorities;

		return getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}