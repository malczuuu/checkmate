package io.github.malczuuu.checkmate.archunit

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest

@AnalyzeClasses(
    packages = ["io.github.malczuuu.checkmate"],
    importOptions = [ImportOption.OnlyIncludeTests::class],
)
class TestkitTestArchTests {

    /**
     * This verifies that all test classes (containing `@Test` or `@ArchTest`) must end with `"...Tests"` suffix, and
     * not `"...Test"`.
     */
    @ArchTest
    fun testClassesMustEndWithPluralTests(classes: JavaClasses) {
        NamingRules.TEST_CLASSES_PLURAL.check(classes)
    }
}
