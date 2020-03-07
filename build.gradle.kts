import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    java
    id("com.github.johnrengelman.shadow") version "5.2.0"
    maven
    `maven-publish`
}

subprojects {
    apply {
        plugin("java")
    }
}

allprojects {
    group = "com.proximyst"
    version = "0.1.1"

    repositories {
        maven {
            name = "spigotmc"
            url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")

            content {
                includeGroup("org.bukkit")
                includeGroup("org.spigotmc")
            }
        }

        maven {
            name = "sonatype"
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")

            content {
                includeGroup("net.md-5")
            }
        }

        maven {
            name = "papermc-snapshots"
            url = uri("https://papermc.io/repo/repository/maven-snapshots/")

            content {
                includeGroup("com.destroystokyo.paper")
                includeGroup("io.github.waterfallmc")
                includeGroup("io.papermc")
            }
        }

        maven {
            name = "papermc"
            url = uri("https://papermc.io/repo/repository/maven-public/")

            content {
                includeGroup("com.destroystokyo.paper")
                includeGroup("io.github.waterfallmc")
                includeGroup("io.papermc")
            }
        }

        maven {
            name = "proxi-nexus"
            url = uri("https://nexus.proximyst.com/repository/maven-public/")
        }

        jcenter()
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        compileOnly("com.destroystokyo.paper:paper-api:1.15-R0.1-SNAPSHOT")
        compileOnly("org.jetbrains:annotations:19.0.0")
    }
}

fun DependencyHandlerScope.untransitiveProjects(vararg projects: String) {
    for (proj in projects)
        implementation(project(proj)) {
            isTransitive = false
        }
}

dependencies {
    untransitiveProjects(
        "common",
        "v1_15_r1"
    )
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = sourceCompatibility
}

val sourcesJar = tasks.create<Jar>("sourcesJar") {
    from(sourceSets.main.get().allJava)
    archiveClassifier.set("sources")
}

val javadocJar = tasks.create<Jar>("javadocJar") {
    from(tasks.javadoc)
    archiveClassifier.set("javadoc")
}

publishing {
    publications {
        create<MavenPublication>("shadow") {
            project.shadow.component(this)
            artifact(javadocJar)
            artifact(sourcesJar)
        }
    }
    repositories {
        maven {
            name = "proxi-nexus"
            url = uri("https://nexus.proximyst.com/repository/maven-any/")
            credentials {
                val proxiUser: String? by project
                val proxiPassword: String? by project
                username = proxiUser
                password = proxiPassword
            }
        }
    }
}

tasks.processResources.configure {
    from("src/main/resources") {
        include("plugin.yml")
        filter<ReplaceTokens>(
            "tokens" to mapOf(
                "VERSION" to project.version.toString()
            )
        )
    }
}
