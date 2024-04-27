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

public class ModuleServiceImplTest {

	@Mock
	private ModuleRepository moduleRepository;

	@InjectMocks
	private ModuleServiceImpl moduleService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	private String moduleName = "CI/CD";
	private String moduleDescription = "Learn the continous integration and deployment of applications";

	@Test
	void getModulesTest() {
		List<Module> sampleModules = List.of(new Module(1L, moduleName, moduleDescription), new Module(1L,
				"Microservices Architecture", "Learn the microservices architecture with Spring Boot app development"));

		when(moduleRepository.findAll()).thenReturn(sampleModules);

		List<ModuleDto> response = moduleService.getModules();

		assertEquals(sampleModules.size(), response.size());
		verify(moduleRepository).findAll();
	}
	
	@Test
	void getModuleByIdTest() {
		Long idToSearch = 1L;
		Module sampleModule = new Module(idToSearch, moduleName, moduleDescription);
		
		when(moduleRepository.findById(idToSearch)).thenReturn(Optional.of(sampleModule));

		ModuleDto response = moduleService.getModuleById(idToSearch);

		assertEquals(sampleModule.getId(), response.getId());
		verify(moduleRepository).findById(idToSearch);
	}
	
	@Test
	void getModuleByIdNotFoundTest() {
		Long idToSearch = 1L;
		
		when(moduleRepository.findById(idToSearch)).thenReturn(Optional.empty());

		NotFoundException exception = assertThrows(NotFoundException.class, () -> moduleService.getModuleById(idToSearch));
		assertEquals(String.format(ModuleConstants.MODULE_NOT_FOUND, idToSearch), exception.getMessage());
		verify(moduleRepository).findById(idToSearch);
	}
	
	@Test
	void addModuleTest() {
		ModuleDto sampleModuleDto = new ModuleDto(null, moduleName, moduleDescription);
		Module sampleModule = new Module(1L, moduleName, moduleDescription);
		
		when(moduleRepository.save(any())).thenReturn(sampleModule);

		ModuleDto response = moduleService.addModule(sampleModuleDto);

		assertEquals(sampleModule.getId(), response.getId());
		verify(moduleRepository).save(any());
	}
	
	@Test
	void updateModuleTest() {
		Long idToUpdate = 1L;
		ModuleDto sampleModuleDto = new ModuleDto(null, moduleName, moduleDescription);
		Module sampleModule = new Module(idToUpdate, moduleName, moduleDescription);
		
		when(moduleRepository.findById(idToUpdate)).thenReturn(Optional.of(sampleModule));
		when(moduleRepository.save(any())).thenReturn(sampleModule);

		ModuleDto response = moduleService.updateModule(idToUpdate, sampleModuleDto);

		assertEquals(sampleModule.getId(), response.getId());
		verify(moduleRepository).findById(idToUpdate);
		verify(moduleRepository).save(any());
	}
	
	@Test
	void updateModuleNotFoundTest() {
		Long idToSearch = 100L;
		when(moduleRepository.findById(idToSearch)).thenReturn(Optional.empty());

		NotFoundException exception = assertThrows(NotFoundException.class,
				() -> moduleService.getModuleById(idToSearch));
		assertEquals(String.format(ModuleConstants.MODULE_NOT_FOUND, idToSearch), exception.getMessage());
	}
	
	@Test
	void deleteModuleTest() {
		Long idToDelete = 1L;
		Module sampleModule = new Module(idToDelete, moduleName, moduleDescription);
		
		when(moduleRepository.findById(idToDelete)).thenReturn(Optional.of(sampleModule));

		moduleService.deleteModule(1L);

		verify(moduleRepository).findById(idToDelete);
		verify(moduleRepository).deleteById(idToDelete);
	}
	
	@Test
	void deleteModuleNotFoundTest(){
		Long idToSearch = 100L;
		when(moduleRepository.findById(idToSearch)).thenReturn(Optional.empty());

		NotFoundException exception = assertThrows(NotFoundException.class, () -> moduleService.deleteModule(idToSearch));
		assertEquals(String.format(ModuleConstants.MODULE_NOT_FOUND, idToSearch), exception.getMessage());
	}
}
