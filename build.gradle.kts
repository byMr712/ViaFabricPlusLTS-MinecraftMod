plugins {
    id("configuration.transitive_jar_in_jar")
    id("via.maven_publish")
    id("base.junit")
    id("extra.unlock_build_errors")
}

// Comment during Minecraft updates to update data diff files
tasks.test {
    enabled = false
}

configure<net.fabricmc.loom.api.fabricapi.FabricApiExtension> {
    configureTests {
        createSourceSet.set(true)
        modId.set("viafabricplus-test")
        enableGameTests.set(false)
        enableClientGameTests.set(true)
        eula.set(true)
    }
}

tasks.matching { it.name == "runClientGameTest" }.configureEach {
    (this as JavaExec).jvmArgs("-Dfabric.client.gametest.disableNetworkSynchronizer=true")
}

dependencies {
    "gametestImplementation"("net.fabricmc.fabric-api:fabric-api:${libs.versions.fabric.api.get()}")

    jarInJar(projects.viafabricplusApi) {
        exclude("net.fabricmc", "fabric-loader")
    }

    jarInJar(platform(libs.fabric.api.bom))
    jarInJar(libs.fabric.api.base)
    jarInJar(libs.fabric.resource.loader.v1)
    jarInJar(libs.fabric.resource.loader.v0)
    jarInJar(libs.fabric.networking.api.v1)
    jarInJar(libs.fabric.command.api.v2)
    jarInJar(libs.fabric.lifecycle.events.v1)
    jarInJar(libs.fabric.particles.v1)
    jarInJar(libs.fabric.registry.sync.v0)

    jarInJar(libs.reflect)
    jarInJar(libs.classic4j)

    compileOnly(libs.modmenu)
}
tasks.named<Jar>("jar") {
    archiveFileName.set("ViaFabricPlusLTS-26.3-byMr712.jar")
}

allprojects {
    tasks.configureEach {
        if (name == "sourcesJar" || name == "remapSourcesJar" || name == "javadocJar") {
            enabled = false
        }
    }
}
