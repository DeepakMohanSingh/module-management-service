package com.tus.module.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModuleDto {
	private Long id;
	
	@NotEmpty(message = "Name cannot be null or empty")
	@Size(min = 3, max = 35, message = "Number of characters should be between 2 and 36")
	private String name;
	
	@NotEmpty(message = "Description cannot be null or empty")
	@Size(min = 3, max = 35, message = "Number of characters should be between 2 and 50")
	private String description;
}
