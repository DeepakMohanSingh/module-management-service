package com.tus.module.service;

import java.util.List;

import com.tus.module.dto.ModuleDto;

public interface IModuleService {
	public List<ModuleDto> getModules();

	public ModuleDto getModuleById(Long id);

	public ModuleDto addModule(ModuleDto moduleDto);

	public ModuleDto updateModule(Long id, ModuleDto moduleDto);

	public void deleteModule(Long id);
}
