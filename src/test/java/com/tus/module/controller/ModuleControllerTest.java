package com.tus.module.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.tus.module.dto.ModuleDto;
import com.tus.module.exception.NotFoundException;
import com.tus.module.service.IModuleService;

@WebMvcTest(ModuleController.class)
public class ModuleControllerTest {

	@MockBean
	private IModuleService moduleService;

	@InjectMocks
	private ModuleController moduleController;

	@Autowired
	MockMvc mockMvc;
	
	@Captor
	ArgumentCaptor<ModuleDto> captor;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	private String path = "/api/modules";
	private String dataPath = "$.data";
	private String moduleName = "CI/CD";
	private String moduleDescription = "Learn the CI and CD of applications";

	@Test
	void getModulesTest() throws Exception {
		List<ModuleDto> sampleModules = List.of(new ModuleDto(1L, moduleName, moduleDescription), new ModuleDto(1L,
				"Microservices Architecture", "Learn the microservices architecture with Spring Boot app development"));

		when(moduleService.getModules()).thenReturn(sampleModules);
		
		mockMvc.perform(get(path))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath(dataPath + ".length()").value(2));
		verify(moduleService).getModules();
	}

	@Test
	void getModuleByIdTest() throws Exception {
		ModuleDto sampleModule = new ModuleDto(1L, moduleName, moduleDescription);

		when(moduleService.getModuleById(1L)).thenReturn(sampleModule);
		
		mockMvc.perform(get(path + "/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath(dataPath).exists())
				.andExpect(jsonPath(dataPath + ".id").value(sampleModule.getId()));
		verify(moduleService).getModuleById(1L);
	}
	
	@Test
	void getModuleByIdNotFoundTest() throws Exception {
		Long idToSearch = 1L;
		when(moduleService.getModuleById(idToSearch)).thenThrow(new NotFoundException(idToSearch));
		
		mockMvc.perform(get(path + "/1"))
				.andDo(print())
				.andExpect(status().isNotFound());
		verify(moduleService).getModuleById(idToSearch);
	}
	
	@Test
	void getModuleByIdRuntimeException_Test() throws Exception {
		Long idToSearch = 1L;
		when(moduleService.getModuleById(idToSearch)).thenThrow(new RuntimeException("Test runtime exception"));
		
		mockMvc.perform(get(path + "/1"))
				.andDo(print())
				.andExpect(status().isInternalServerError());
		verify(moduleService).getModuleById(idToSearch);
	}

	@Test
	void addModuleTest() throws Exception {
		ModuleDto sampleModule = new ModuleDto(1L, moduleName, moduleDescription);

		when(moduleService.addModule(any())).thenReturn(sampleModule);
		
		ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
		
		mockMvc.perform(post(path)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(sampleModule)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath(dataPath).exists())
				.andExpect(jsonPath(dataPath + ".id").value(sampleModule.getId()))
				.andExpect(header().string("Location", "/api/modules/1"))
				.andReturn();
		verify(moduleService).addModule(captor.capture());
		
		assertEquals(sampleModule.getId(), captor.getValue().getId());
	}
	
	@Test
	void addModuleInvalidBodyTest() throws Exception {
		ModuleDto sampleModule = new ModuleDto(1L, "CI", moduleDescription);

		when(moduleService.addModule(any())).thenReturn(sampleModule);
		
		ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
		
		mockMvc.perform(post(path)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(sampleModule)))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void updateModuleTest() throws Exception {
		ModuleDto sampleModule = new ModuleDto(1L, moduleName, moduleDescription);

		when(moduleService.updateModule(anyLong(), any())).thenReturn(sampleModule);
		
		ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
		
		mockMvc.perform(put(path + "/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(sampleModule)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath(dataPath).exists())
				.andExpect(jsonPath(dataPath + ".id").value(sampleModule.getId()))
				.andReturn();
		verify(moduleService).updateModule(anyLong(), captor.capture());
		
		assertEquals(sampleModule.getId(), captor.getValue().getId());
	}
	
	@Test
	void deleteModuleByIdTest() throws Exception {
		
		mockMvc.perform(delete(path + "/1"))
				.andDo(print())
				.andExpect(status().isOk());
		verify(moduleService).deleteModule(1L);
	}
}
