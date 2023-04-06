# EpicSpigotLib
EpicSpigotLib (JishunaCommonLib fork), must have lib when developing spigot plugins

Credit to [@Jisuna](https://github.com/Jishuna) with [JisunaCommonLib](https://github.com/Jishuna/JishunaCommonLib)

```groovy
plugins {
  id 'java'
  id "com.github.johnrengelman.shadow" version "7.1.0"
}


repositories {
  maven {
    url = 'https://repo.epicebic.xyz/public'
  }
}

dependencies {
    implementation 'me.epic:epicspigotlib:1+'
}

shadowJar {
  relocate 'me.epic.spigotlib', 'YOUR.PACKAGE.epicspigotlib'
}
```

