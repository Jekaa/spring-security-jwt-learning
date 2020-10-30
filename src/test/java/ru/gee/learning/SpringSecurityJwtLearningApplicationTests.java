package ru.gee.learning;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SpringSecurityJwtLearningApplicationTests {

	@Test
	void contextLoads() {
		Map<String, String> map = new HashMap<>();
		map.put("a", "b");
	}

}
