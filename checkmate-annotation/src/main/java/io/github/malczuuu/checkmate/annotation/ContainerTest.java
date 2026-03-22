package io.github.malczuuu.checkmate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.Tag;

/**
 * Meta-annotation for test fields that require Testcontainers infrastructure. Used for filtering
 * out integration tests for a quick build.
 */
@Tag(ContainerTest.TAG_NAME)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ContainerTest {

  /**
   * The tag name used to mark tests that require Testcontainers infrastructure. Tests annotated
   * with {@code @ContainerTest} will be tagged with this name, allowing them to be included or
   * excluded in test runs using JUnit's tagging and filtering features.
   */
  String TAG_NAME = "testcontainers";
}
