# Minevictus NMS

The NMS API for Minevictus.

## Building

MV-NMS can be built through running the `shadowJar` task, or by just fetching
it on <https://jenkins.proximyst.com> with an authorised user.

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

First, the plugin will load. Once loaded, the `BukkitVersion` enum will have
its `#isUnknownVersion` method tell whether the current version is supported.
If this is `false`, `#getCurrentVersion` will throw an exception upon call.
`#getOptionalVersion` is always safe and will never return any `null`s or throw
exceptions.

All the methods of the variants of `BukkitVersion` return cached instances.

## The current interfaces:

- `INmsVillager#clearVillagerReputations(@NotNull Villager)`: \
  Clears all the gossips from a given villager.
- `INmsItems#serializeItemStack(@NotNull ItemStack)`: \
  Serialises an item to `byte[]`.
- `INmsItems#deserializeItemStack(byte[])`: \
  Deserialises an item from a `byte[]` to a `@NotNull ItemStack`.
- `INmsEntity#rotate(@NotNull Entity, float, float)`: \
  Rotate an entity without teleporting it.
- `INmsEntity#rotateLivingEntity(@NotNull LivingEntity, float, float)`: \
  Rotate a living entity, this is sometimes needed with entities such as the Ender Dragon, 
  but most of the time the method above is enough.
- `INmsEntity#setInvisible(@NotNull Entity, boolean)`: \
  Make an entity invisible. Bukkit doesn't let you make some of them so we have to use NMS.
- `INmsEntity#setInvulnerable(@NotNull Entity, boolean)`: \
  Make an entity invulnerable.