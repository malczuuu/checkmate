package io.github.malczuuu.checkmate.archunit

import com.tngtech.archunit.base.DescribedPredicate
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import org.junit.jupiter.api.Test

/**
 * Shared ArchUnit rules enforcing naming conventions across all modules in the Checkmate project.
 *
 * Import this object in any module's arch-test suite to apply the same constraints uniformly.
 */
object NamingRules {

  /**
   * An [ArchRule] that asserts every class containing at least one method annotated with [Test] or
   * [ArchTest] has a simple name ending with `"Tests"` (plural form) rather than `"Test"`.
   */
  val TEST_CLASSES_MUST_BE_PLURAL: ArchRule =
      ArchRuleDefinition.classes()
          .that()
          .containAnyMethodsThat(
              DescribedPredicate.describe("are annotated with @Test or @ArchTest") {
                it.isAnnotatedWith(Test::class.java) || it.isAnnotatedWith(ArchTest::class.java)
              }
          )
          .should()
          .haveSimpleNameEndingWith("Tests")
}
