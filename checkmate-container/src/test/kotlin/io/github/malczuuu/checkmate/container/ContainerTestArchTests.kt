package io.github.malczuuu.checkmate.container

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import io.github.malczuuu.checkmate.archunit.NamingRules

@AnalyzeClasses(
    packages = ["io.github.malczuuu.checkmate.container"],
    importOptions = [ImportOption.OnlyIncludeTests::class],
)
class ContainerTestArchTests {

  /**
   * This verifies that all test classes (containing `@Test` or `@ArchTest`) must end with
   * `"...Tests"` suffix, and not `"...Test"`.
   */
  @ArchTest
  fun testClassesMustEndWithPluralTests(classes: JavaClasses) {
    NamingRules.TEST_CLASSES_MUST_BE_PLURAL.check(classes)
  }
}
