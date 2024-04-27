package com.tus.module.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tus.module.constant.ModuleConstants;
import com.tus.module.dto.ModuleDto;
import com.tus.module.repository.ModuleRepository;
import com.tus.module.entity.Module;
import com.tus.module.exception.NotFoundException;

public class ModuleServiceImplTests {

	@Mock
	private ModuleRepository moduleRepository;

	@InjectMocks
	private ModuleServiceImpl moduleService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getModulesTest() {
		List<Module> sampleModules = List.of(
				new Module(1L, "CI/CD", "Learn the continous integration and deployment of applications"),
				new Module(1L, "Microservices Architecture",
						"Learn the microservices architecture with Spring Boot app development"));

		when(moduleRepository.findAll()).thenReturn(sampleModules);

		List<ModuleDto> response = moduleService.getModules();

		assertEquals(sampleModules.size(), response.size());
		verify(moduleRepository).findAll();
	}
	
	@Test
	void getModuleByIdTest() {
		Long idToSearch = 1L;
		Module sampleModule = new Module(idToSearch, "CI/CD", "Learn the continous integration and deployment of applications");
		
		when(moduleRepository.findById(idToSearch)).thenReturn(Optional.of(sampleModule));

		ModuleDto response = moduleService.getModuleById(idToSearch);

		assertEquals(sampleModule.getId(), response.getId());
		verify(moduleRepository).findById(idToSearch);
	}
	
	@Test
	void getModuleById_NotFound_Test() {
		Long idToSearch = 1L;
		
		when(moduleRepository.findById(idToSearch)).thenReturn(Optional.empty());

		NotFoundException exception = assertThrows(NotFoundException.class, () -> moduleService.getModuleById(idToSearch));
		assertEquals(String.format(ModuleConstants.MODULE_NOT_FOUND, idToSearch), exception.getMessage());
		verify(moduleRepository).findById(idToSearch);
	}
	
	@Test
	void addModuleTest() {
		ModuleDto sampleModuleDto = new ModuleDto(null, "CI/CD", "Learn the continous integration and deployment of applications");
		Module sampleModule = new Module(1L, "CI/CD", "Learn the continous integration and deployment of applications");
		
		when(moduleRepository.save(any())).thenReturn(sampleModule);

		ModuleDto response = moduleService.addModule(sampleModuleDto);

		assertEquals(sampleModule.getId(), response.getId());
		verify(moduleRepository).save(any());
	}
	
	@Test
	void updateModuleTest() {
		Long idToUpdate = 1L;
		ModuleDto sampleModuleDto = new ModuleDto(null, "CI/CD", "Learn the continous integration and deployment of applications");
		Module sampleModule = new Module(idToUpdate, "CI/CD", "Learn the continous integration and deployment of applications");
		
		when(moduleRepository.findById(idToUpdate)).thenReturn(Optional.of(sampleModule));
		when(moduleRepository.save(any())).thenReturn(sampleModule);

		ModuleDto response = moduleService.updateModule(idToUpdate, sampleModuleDto);

		assertEquals(sampleModule.getId(), response.getId());
		verify(moduleRepository).findById(idToUpdate);
		verify(moduleRepository).save(any());
	}
	
	@Test
	void updateModule_NotFound_Test() {
		Long idToSearch = 100L;
		when(moduleRepository.findById(idToSearch)).thenReturn(Optional.empty());

		NotFoundException exception = assertThrows(NotFoundException.class,
				() -> moduleService.getModuleById(idToSearch));
		assertEquals(String.format(ModuleConstants.MODULE_NOT_FOUND, idToSearch), exception.getMessage());
	}
	
	@Test
	void deleteModuleTest() {
		Long idToDelete = 1L;
		Module sampleModule = new Module(idToDelete, "CI/CD", "Learn the continous integration and deployment of applications");
		
		when(moduleRepository.findById(idToDelete)).thenReturn(Optional.of(sampleModule));

		moduleService.deleteModule(1L);

		verify(moduleRepository).findById(idToDelete);
		verify(moduleRepository).deleteById(idToDelete);
	}
	
	@Test
	void deleteModule_NotFound_Test(){
		Long idToSearch = 100L;
		when(moduleRepository.findById(idToSearch)).thenReturn(Optional.empty());

		NotFoundException exception = assertThrows(NotFoundException.class, () -> moduleService.getModuleById(idToSearch));
		assertEquals(String.format(ModuleConstants.MODULE_NOT_FOUND, idToSearch), exception.getMessage());
	}
}
