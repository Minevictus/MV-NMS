# Minevictus NMS

The NMS API for Minevictus.

## Depend

Gradle (Groovy):

```groovy
repositories {
  maven {
    name = 'proxi-nexus'
    url = 'https://nexus.proximyst.com/repository/maven-public/'
  }
}

dependencies {
  compileOnly 'com.proximyst:mvnms:+'
}
```

Gradle (Kotlin):

```groovy
repositories {
  maven {
    name = "proxi-nexus"
    url = uri("https://nexus.proximyst.com/repository/maven-public/")
  }
}

dependencies {
  compileOnly("com.proximyst:mvnms:+")
}
```

Maven:

```xml
<figure it="out yourself" />
```

plugin.yml:

```yaml
depends: [MV-NMS]
```

## Usage

First, the plugin will load. Once loaded, the `BukkitVersion` enum will have its
`#isUnknownVersion` method tell whether the current version is supported. If this is
`false`, `#getCurrentVersion` will throw an exception upon call.

All the methods of the variants of `BukkitVersion` return cached instances, so long
as the version attempted isn't `UNKNOWN`, which throws exceptions on every instance.

## The current interfaces:

- `INmsVillager#clearVillagerReputations(Villager)`: \
  Clears all the gossips from a given villager.