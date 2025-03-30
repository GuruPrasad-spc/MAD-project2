pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
       // create("libs") {
         //   from(files("gradle/libs.versions.toml")) // Ensure this file exists
        //}
    }
}

rootProject.name = "MAD-project"
include(":app")
