package io.github.malczuuu.checkmate.spring.kafka;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import io.github.malczuuu.checkmate.archunit.NamingRules;

@AnalyzeClasses(
    packages = "io.github.malczuuu.checkmate.spring.kafka",
    importOptions = {ImportOption.OnlyIncludeTests.class})
class KafkaTestArchTests {

  /**
   * This verifies that all test classes (containing {@code @Test} or {@code @ArchTest}) must end
   * with {@code "...Tests"} suffix, and not {@code "...Test"}.
   */
  @ArchTest
  public void testClassesMustEndWithPluralTests(JavaClasses classes) {
    NamingRules.TEST_CLASSES_MUST_BE_PLURAL.check(classes);
  }
}
