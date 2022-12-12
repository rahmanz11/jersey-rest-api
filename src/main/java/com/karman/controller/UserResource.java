package com.karman.controller;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.karman.config.RandomString;
import com.karman.exception.ResourceNotFoundException;
import com.karman.model.User;
import com.karman.service.AuthenticationService;

@Path("users")
public class UserResource {

	private AuthenticationService userService;
	
	@Inject
	public UserResource(AuthenticationService userService) {
		this.userService = userService;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@NotBlank(message = "Name is required") @QueryParam(value = "name") String name,
						@NotBlank(message = "Password is required") @QueryParam(value = "password") String password) {
		User user = userService.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Name " + name + " not found"));
		RandomString session = new RandomString();
		String token = session.nextString();
		user.setHash(token);
		userService.update(user);
		return token;
	}

	@GET
	@Path("{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam(value = "userId") Long userId) {
		return userService.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Id " + userId + " not found"));
	}

	@POST
	public String createUser(@Valid User user) {
		userService.save(user);
		return "User created";
	}

	@PUT
	@Path("{userId}")
	public String updateUser(@PathParam(value = "userId") Long userId, @Valid User user) {
		return userService.findById(userId).map(p -> {
			p.setName(user.getName());
			userService.update(p);
			return "User updated";
		}).orElseThrow(() -> new ResourceNotFoundException("User Id " + userId + " not found"));
	}

	@DELETE
	@Path("{userId}")
	public String deleteUser(@PathParam(value = "userId") Long userId) {
		return userService.findById(userId).map(p -> {
			userService.deleteById(userId);
			return "User deleted";
		}).orElseThrow(() -> new ResourceNotFoundException("User Id " + userId + " not found"));
	}
}