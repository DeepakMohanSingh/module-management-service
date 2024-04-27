package com.tus.module;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ModuleApplicationTests {
	
	@Test
	public void contextLoads() {
        ModuleApplication.main(new String[]{});
		assertTrue(true);
	}
	
}
