package io.github.malczuuu.checkmate.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;

@AnalyzeClasses(
    packages = "io.github.malczuuu.checkmate.archunit",
    importOptions = {ImportOption.OnlyIncludeTests.class})
class ArchunitTestArchTests {

  /**
   * This verifies that all test classes (containing {@code @Test} or {@code @ArchTest}) must end
   * with {@code "...Tests"} suffix, and not {@code "...Test"}.
   */
  @ArchTest
  public void testClassesMustEndWithPluralTests(JavaClasses classes) {
    NamingRules.TEST_CLASSES_MUST_BE_PLURAL.check(classes);
  }
}
