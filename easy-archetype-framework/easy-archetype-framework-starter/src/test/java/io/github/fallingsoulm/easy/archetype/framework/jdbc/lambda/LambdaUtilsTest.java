package io.github.fallingsoulm.easy.archetype.framework.lambda;

import lombok.Data;
import org.junit.jupiter.api.Test;

class LambdaUtilsTest {

	@Test
	void columnToString() {
		User user = new User();
		LambdaUtils<User> lambdaUtils = new LambdaUtils<User>(User.class);
		String s = lambdaUtils.columnToString(User::getName);

		System.out.println(s);

	}

	@Data
	public static class User {
		private String name;
	}

}