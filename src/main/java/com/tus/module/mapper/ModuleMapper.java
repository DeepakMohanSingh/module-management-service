package com.tus.module.mapper;

import com.tus.module.entity.Module;
import com.tus.module.dto.ModuleDto;

public class ModuleMapper {
	public static ModuleDto mapModuleToModuleDto(Module module) {
		return new ModuleDto(module.getId(), module.getName(), module.getDescription());
	}

	public static Module mapModuleDtoToModule(ModuleDto moduleDto) {
		return new Module(moduleDto.getId(), moduleDto.getName(), moduleDto.getDescription());
	}
}
