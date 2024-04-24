package com.tus.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.tus.module.controller.ModuleController;
import com.tus.module.dto.ModuleDto;
import com.tus.module.dto.ResponseDto;
import com.tus.module.service.IModuleService;

class ModuleApplicationTests {

	@Mock
	private IModuleService moduleService;

	@InjectMocks
	private ModuleController moduleController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getModulesTest() {
		List<ModuleDto> sampleModules = List.of(
				new ModuleDto(1L, "CI/CD", "Learn the continous integration and deployment of applications"),
				new ModuleDto(1L, "Microservices Architecture",
						"Learn the microservices architecture with Spring Boot app development"));

		when(moduleService.getModules()).thenReturn(sampleModules);
		ResponseEntity<ResponseDto<List<ModuleDto>>> modulesResponse = moduleController.getModules();

		assertEquals(sampleModules.size(), modulesResponse.getBody().getData().size(), "");
		verify(moduleService).getModules();
	}

}
