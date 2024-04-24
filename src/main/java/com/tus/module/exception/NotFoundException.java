package com.tus.module.exception;

import com.tus.module.constant.ModuleConstants;

public class NotFoundException extends RuntimeException {
	public NotFoundException(Long id) {
		super(String.format(ModuleConstants.MODULE_NOT_FOUND, id));
	}
}
