plugins {
    id("internal.kotlin-convention")
    id("java-library")
}

// https://cristian.io/post/gradle-publish-bom/
// publishing {
//     publications {
//         maven(MavenPublication) {
//             from components.java
//                     artifact(tasks["sourcesJar"])
//             artifact(tasks["javadocJar"])
//
//             pom.withXml {
//                 asNode().dependencyManagement.dependencies.dependency.findAll { node ->
//                     node.scope[0].text().equals('import')
//                 }.each { node -> node.replaceNode {} }
//             }
//
//             versionMapping {
//                 usage('java-api') {
//                     fromResolutionOf('runtimeClasspath')
//                 }
//                 usage('java-runtime') {
//                     fromResolutionResult()
//                 }
//             }
//         }
//     }
// }
