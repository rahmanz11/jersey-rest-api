package com.karman.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import com.karman.controller.UserResource;
import com.karman.repo.UserRepository;
import com.karman.service.AuthenticationService;
import com.karman.service.impl.AuthenticationServiceImpl;

public class AppConfig extends ResourceConfig {

	public AppConfig() {
		register(UserResource.class);
		register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(AuthenticationServiceImpl.class).to(AuthenticationService.class);
				bind(UserRepository.class).to(UserRepository.class);
			}
		});
		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
	}
}