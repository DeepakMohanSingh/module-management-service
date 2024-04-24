package com.tus.module.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.tus.module.dto.ModuleDto;
import com.tus.module.service.IModuleService;
import com.tus.module.dto.ResponseDto;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

	private IModuleService moduleService;

	public ModuleController(IModuleService moduleService) {
		this.moduleService = moduleService;
	}

	@GetMapping
	public ResponseEntity<ResponseDto<List<ModuleDto>>> getModules() {
		return new ResponseEntity<>(new ResponseDto<>(moduleService.getModules(), null), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseDto<ModuleDto>> getModuleById(@PathVariable Long id) {
		return new ResponseEntity<>(new ResponseDto<>(moduleService.getModuleById(id), null), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ResponseDto<ModuleDto>> addModule(@Validated @RequestBody ModuleDto moduleDto,
			WebRequest webRequest) {
		ModuleDto savedModuleDto = moduleService.addModule(moduleDto);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", webRequest.getDescription(false).substring(4) + "/" + savedModuleDto.getId());
		return new ResponseEntity<>(new ResponseDto<>(savedModuleDto, null), HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<ResponseDto<ModuleDto>> updateModule(@PathVariable Long id,
			@Validated @RequestBody ModuleDto moduleDto, WebRequest webRequest) {
		ModuleDto savedModuleDto = moduleService.updateModule(id, moduleDto);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", webRequest.getDescription(false).substring(4));
		return new ResponseEntity<>(new ResponseDto<>(savedModuleDto, null), HttpStatus.CREATED);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
		moduleService.deleteModule(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
