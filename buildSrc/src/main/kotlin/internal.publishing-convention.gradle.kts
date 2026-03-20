plugins {
    id("internal.common-convention")
    id("maven-publish")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            plugins.withType<JavaPlugin> {
                from(components["java"])
            }

            plugins.withType<JavaPlatformPlugin> {
                from(components["javaPlatform"])
            }

            pom {
                name = project.name
                description = project.description
            }
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/malczuuu/checkmate")
            credentials {
                username = project.findProperty("gpr.user")?.toString() ?: System.getenv("GPR_USER")
                password = project.findProperty("gpr.token")?.toString() ?: System.getenv("GPR_TOKEN")
            }
        }
    }
}
