package io.github.malczuuu.checkmate.archunit;

import static com.tngtech.archunit.base.DescribedPredicate.describe;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.lang.ArchRule;

/** Provides shared ArchUnit rules for test class naming conventions. */
public final class NamingRules {

  /**
   * This verifies that all test classes (containing {@code @Test} or {@code @ArchTest}) must end
   * with {@code "...Tests"} suffix, and not {@code "...Test"}.
   */
  public static final ArchRule TEST_CLASSES_MUST_BE_PLURAL =
      classes()
          .that()
          .containAnyMethodsThat(
              describe(
                  "are annotated with @Test or @ArchTest",
                  m ->
                      m.isAnnotatedWith(org.junit.jupiter.api.Test.class)
                          || m.isAnnotatedWith(com.tngtech.archunit.junit.ArchTest.class)))
          .and()
          .areNotNestedClasses()
          .should()
          .haveSimpleNameEndingWith("Tests");

  private NamingRules() {}
}
