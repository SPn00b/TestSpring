package junitlearning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.condition.DisabledForJreRange;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledInNativeImage;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.StringUtils;

@DisplayName("My Test Cases Course Start 1st Test Case")
public class TestJunit/* implements TestLifecycleLogger */ {

	Logger logger = Logger.getLogger(TestJunit.class.getName());

	private final Calculator calculator = new Calculator();

	Stack<Object> stack;

	@ParameterizedTest
	@ValueSource(strings = { "racecar", "radar", "able was I ere I saw elba" })
	void palindromes(String candidate) {
		assertTrue(StringUtils.isBlank(candidate));
	}

	@RepeatedTest(value = 8, failureThreshold = 2)
	void repeatedTestWithFailureThreshold(RepetitionInfo repetitionInfo) {
		// Simulate unexpected failure every second repetition
		if (repetitionInfo.getCurrentRepetition() % 2 == 0) {
			fail("Boom!");
		}
	}

	@BeforeEach
	void beforeEach(TestInfo testInfo, RepetitionInfo repetitionInfo) {
		var currentRepetition = repetitionInfo.getCurrentRepetition();
		var totalRepetitions = repetitionInfo.getTotalRepetitions();
		var methodName = testInfo.getTestMethod().get().getName();
		logger.info(String.format("About to execute repetition %d of %d for %s", //
				currentRepetition, totalRepetitions, methodName));
	}

	@RepeatedTest(10)
	void repeatedTest() {
		// ...
	}

	//	@Test
	//	void isEqualValue() {
	//		assertEquals(1, "a".length(), "is always equal");
	//	}

	@Test
	@DisplayName("is instantiated with new Stack()")
	void isInstantiatedWithNew() {
		new Stack<>();
	}

	@Nested
	@DisplayName("when new")
	class WhenNew {

		@BeforeEach
		void createNewStack() {
			stack = new Stack<>();
		}

		@Test
		@DisplayName("is empty")
		void isEmpty() {
			assertTrue(stack.isEmpty());
		}

		@Test
		@DisplayName("throws EmptyStackException when popped")
		void throwsExceptionWhenPopped() {
			assertThrows(EmptyStackException.class, stack::pop);
		}

		@Test
		@DisplayName("throws EmptyStackException when peeked")
		void throwsExceptionWhenPeeked() {
			assertThrows(EmptyStackException.class, stack::peek);
		}

		@Nested
		@DisplayName("after pushing an element")
		class AfterPushing {

			String anElement = "an element";

			@BeforeEach
			void pushAnElement() {
				stack.push(anElement);
			}

			@Test
			@DisplayName("it is no longer empty")
			void isNotEmpty() {
				assertFalse(stack.isEmpty());
			}

			@Test
			@DisplayName("returns the element when popped and is empty")
			void returnElementWhenPopped() {
				assertEquals(anElement, stack.pop());
				assertTrue(stack.isEmpty());
			}

			@Test
			@DisplayName("returns the element when peeked but remains not empty")
			void returnElementWhenPeeked() {
				assertEquals(anElement, stack.peek());
				assertFalse(stack.isEmpty());
			}
		}
	}

	@Test
	@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
	void disabledIf() {
		// ...
	}

	@Test
	@EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*Java*.")
	void onlyOnJAVAHOME() {
		// ...
	}

	@Test
	@EnabledIfEnvironmentVariable(named = "ENV", matches = "staging-server")
	void onlyOnStagingServer() {
		// ...
	}

	@Test
	@DisabledIfEnvironmentVariable(named = "ENV", matches = ".*development.*")
	void notOnDeveloperWorkstation() {
		// ...
	}

	@Test
	@EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
	void onlyOn64BitArchitectures() {
		// ...
	}

	@Test
	@DisabledIfSystemProperty(named = "ci-server", matches = "true")
	void notOnCiServer() {
		// ...
	}

	@Test
	@EnabledInNativeImage
	void onlyWithinNativeImage() {
		// ...
	}

	@Test
	@DisabledInNativeImage
	void neverWithinNativeImage() {
		// ...
	}

	@Test
	@EnabledOnJre(value = JRE.JAVA_8)
	void onlyOnJava8() {
		// ...
	}

	@Test
	@EnabledOnJre(value = { JRE.JAVA_9, JRE.JAVA_10 })
	void onJava9Or10() {
		// ...
	}

	@Test
	@EnabledForJreRange(min = JRE.JAVA_9, max = JRE.JAVA_11)
	void fromJava9to11() {
		// ...
	}

	@Test
	@EnabledForJreRange(min = JRE.JAVA_9)
	void fromJava9toCurrentJavaFeatureNumber() {
		// ...
	}

	@Test
	@EnabledForJreRange(max = JRE.JAVA_11)
	void fromJava8To11() {
		// ...
	}

	@Test
	@DisabledOnJre(value = JRE.JAVA_9)
	void notOnJava9() {
		// ...
	}

	@Test
	@DisabledForJreRange(min = JRE.JAVA_9, max = JRE.JAVA_11)
	void notFromJava9to11() {
		// ...
	}

	@Test
	@DisabledForJreRange(min = JRE.JAVA_9)
	void notFromJava9toCurrentJavaFeatureNumber() {
		// ...
	}

	@Test
	@DisabledForJreRange(max = JRE.JAVA_11)
	void notFromJava8to11() {
		// ...
	}

	@Test
	@EnabledOnOs(value = OS.WINDOWS)
	void onlyOnWindowsOs() {
		// ...
	}

	@Test
	@DisabledOnOs(architectures = "x86_64")
	void notOnX86_64() {
		// ...
	}

	@Test
	public void assertTimeoutWithNoMessage() {
		var bookService = new BookService();

		for (var i = 1; i <= 1000; i++) {
			bookService.addBook(new Book(String.valueOf(i), "Head First Java", "Wrox"));
		}

		List<String> actualTitles = new ArrayList<>();

		assertTimeout(Duration.ofMillis(1), () -> {
			actualTitles.addAll(bookService.getBookTitlesByPublisher("Wrox"));
		});

		assertEquals(1000, actualTitles.size());

	}

	@Test
	public void assertTimeoutWithMessage() {
		var bookService = new BookService();

		for (var i = 1; i <= 1000; i++) {
			bookService.addBook(new Book(String.valueOf(i), "Head First Java", "Wrox"));
		}

		List<String> actualTitles = new ArrayList<>();

		assertTimeout(Duration.ofMillis(1), () -> {
			Thread.sleep(1);
			actualTitles.addAll(bookService.getBookTitlesByPublisher("Wrox"));
		}, "Performance issues with getBookTitlesByPublisher() method !");

		assertEquals(1000, actualTitles.size());

	}

	@Test
	public void assertTimeoutWithMessageSupplier() {
		var bookService = new BookService();

		for (var i = 1; i <= 1000; i++) {
			bookService.addBook(new Book(String.valueOf(i), "Head First Java", "Wrox"));
		}

		List<String> actualTitles = new ArrayList<>();

		assertTimeout(Duration.ofNanos(1), () -> {
			Thread.sleep(1);
			actualTitles.addAll(bookService.getBookTitlesByPublisher("Wrox"));
		}, () -> "Performance issues with getBookTitlesByPublisher() method !");

		assertEquals(1000, actualTitles.size());
	}

	@DisplayName("1st Test Case")
	@Test
	void addition() {
		assertEquals(2, calculator.add(1, 1));
	}

	@DisplayName("2nd Test Case")
	@Test
	public void testAdd() {
		var str = "Junit is working fine";
		assertEquals("Junit is working fine", str);
	}

	@BeforeAll
	static void initAll() {
		System.out.println("BeforeAll");
	}

	@BeforeEach
	void init() {
		System.out.println("BeforeEach");
	}

	@Test
	void succeedingTest() {
		System.out.println("Test");
	}

	@Test
	void failingTest() {
		fail("a failing test");
		System.out.println("fail");
	}

	@Test
	@Disabled("for demonstration purposes")
	void skippedTest() {
		// not executed
		System.out.println("Disabled");
	}

	@Test
	void abortedTest() {
		assumeTrue("abc".contains("Z"));
		fail("test should have been aborted");
		System.out.println("aborted");
	}

	@AfterEach
	void tearDown() {
		System.out.println("AfterEach");
	}

	@AfterAll
	static void tearDownAll() {
		System.out.println("AfterAll");
	}

}
