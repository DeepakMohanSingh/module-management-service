package com.tus.module.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tus.module.entity.Module;
import com.tus.module.exception.NotFoundException;
import com.tus.module.dto.ModuleDto;
import com.tus.module.mapper.ModuleMapper;
import com.tus.module.repository.ModuleRepository;
import com.tus.module.service.IModuleService;

@Service
public class ModuleServiceImpl implements IModuleService {

	private ModuleRepository moduleRepository;

	public ModuleServiceImpl(ModuleRepository moduleRepository) {
		this.moduleRepository = moduleRepository;
	}

	@Override
	public List<ModuleDto> getModules() {
		List<Module> modules = moduleRepository.findAll();
		List<ModuleDto> moduleDtoList = new ArrayList<>();
		modules.forEach(module -> moduleDtoList.add(ModuleMapper.mapModuleToModuleDto(module)));
		return moduleDtoList;
	}

	@Override
	public ModuleDto getModuleById(Long id) {
		Module module = moduleRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
		return ModuleMapper.mapModuleToModuleDto(module);
	}

	@Override
	public ModuleDto addModule(ModuleDto moduleDto) {
		Module module = moduleRepository.save(ModuleMapper.mapModuleDtoToModule(moduleDto));
		return ModuleMapper.mapModuleToModuleDto(module);
	}

	@Override
	public ModuleDto updateModule(Long id, ModuleDto moduleDto) {
		moduleRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
		moduleDto.setId(id);
		Module module = moduleRepository.save(ModuleMapper.mapModuleDtoToModule(moduleDto));
		return ModuleMapper.mapModuleToModuleDto(module);
	}

	@Override
	public void deleteModule(Long id) {
		moduleRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
		moduleRepository.deleteById(id);
	}

}
