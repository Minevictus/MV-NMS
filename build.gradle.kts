import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.util.*

plugins {
    java
    `java-library`
    id("com.github.johnrengelman.shadow") version "5.2.0"
    `maven-publish`
    id("net.minecrell.plugin-yml.bukkit") version "0.3.0"
}

run {
    val props = Properties()
    rootDir.listFiles { file -> file.extension == "properties" && file.nameWithoutExtension != "gradle" }
        ?.forEach {
            println("Loading ${it.name}...")
            it.inputStream().use {
                props.load(it)
            }
        }
    props.forEach {
        project.ext[it.key.toString()] = it.value
    }
}

subprojects {
    apply {
        plugin("java")
        plugin("java-library")
    }
}

allprojects {
    group = "com.proximyst"
    version = "0.1.8"

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

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = sourceCompatibility
    }

    java {
        withJavadocJar()
        withSourcesJar()
    }
}

fun DependencyHandlerScope.untransitiveProjects(vararg projects: String) {
    for (proj in projects)
        implementation(project(proj)) {
            isTransitive = false
        }
}

dependencies {
    api(project(":common")) {
        isTransitive = false
    }
    untransitiveProjects(
        "v1_15_r1"
    )
}

tasks.withType<ShadowJar> {
    this.archiveClassifier.set(null as String?)
}

bukkit {
    // The name is set to a constant to have it never mutate based on root project.
    name = "MV-NMS"

    main = "com.proximyst.mvnms.MvNms"
    apiVersion = "1.15"
    authors = listOf("Proximyst", "NahuLD")
}

publishing {
    publications {
        create<MavenPublication>("shadow") {
            project.shadow.component(this)
            artifact(project.tasks.getByName("javadocJar"))
            artifact(project.tasks.getByName("sourcesJar"))
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