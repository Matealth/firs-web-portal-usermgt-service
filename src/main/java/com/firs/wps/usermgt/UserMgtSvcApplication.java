package com.firs.wps.usermgt;

import com.firs.wps.usermgt.entity.Role;
import com.firs.wps.usermgt.enums.RoleEnum;
import com.firs.wps.usermgt.repo.RoleRepo;
import com.firs.wps.usermgt.request.CreateUserRequest;
import com.firs.wps.usermgt.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class UserMgtSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMgtSvcApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserService userService, RoleRepo roleRepo) {
		return args -> {
			if (roleRepo.findAll().isEmpty()) {
				roleRepo.save(Role.builder().name("ROLE_ADMIN").build());
				roleRepo.save(Role.builder().name("ROLE_USER").build());
			}

			Set<RoleEnum> role = new HashSet<>();
			role.add(RoleEnum.ADMIN);
			CreateUserRequest createUserRequest = CreateUserRequest.builder()
					.username("morufoye@gmail.com")
					.roles(role)
					.address("No 2 Asokoro street, Abuja")
					.phone("08057790564")
					.firstName("moruff")
					.lastName("oyewole")
					.build();
			userService.createUser(createUserRequest);
		};

	}

}
