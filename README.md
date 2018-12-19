# LWJGL and ThinMatrix
A 3D Game Engine with LWJGL 3. The code is in Java 11 and Kotlin 1.3.

It follows the 3D Game Engine tutorials by [@ThinMatrix](https://twitter.com/ThinMatrix).

There is a branch for each video in this playlist: [OpenGL 3D Game Tutorials](https://www.youtube.com/playlist?list=PLRIWtICgwaX0u7Rf9zkZhLoLuZVfUksDP)

I'm also using the online book [3D Game Development with LWJGL 3](https://ahbejarano.gitbook.io/lwjglgamedev/) as reference.

You will need:
* [JDK 11](https://openjdk.java.net/) OpenJDK or Oracle's OpenJDK are fine.  
* [Maven](https://maven.apache.org/).
* [IntelliJ IDEA](https://www.jetbrains.com/idea/), community version is free.

Clone the repository, checkout the respective branch and then in IntelliJ IDEA: File > Open the directory. IDEA will 
recognize that we are using Maven and setup everything for you.

Note that since I'm using JDK 11, LWJGL 3 and Maven, we can use more modern ways to do things and also 
applying my own experience writing Java code.

In OSX you will need to add the following VM option in IDEA, go to Run > Edit Configurations:
```
-XstartOnFirstThread
```

This is the code for the video: [OpenGL 3D Game Tutorial 2: VAOs and VBOs](https://youtu.be/WMiggUPst-Q)