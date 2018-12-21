package com.codechapin.lwjgl.thinmatrix;

import com.codechapin.lwjgl.thinmatrix.models.TexturedModel;
import com.codechapin.lwjgl.thinmatrix.render.DisplayManager;
import com.codechapin.lwjgl.thinmatrix.render.Loader;
import com.codechapin.lwjgl.thinmatrix.render.Renderer;
import com.codechapin.lwjgl.thinmatrix.shaders.StaticShader;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 *
 */
public class MainGameLoop {
  private static final Path TEXTURES_ROOT = Paths.get(".", "src", "main", "resources", "textures");

  public static void main(String[] args) {
    var display = new DisplayManager();
    display.create();

    var loader = new Loader();
    var renderer = new Renderer();
    var shader = new StaticShader();

    float[] vertices = {
        -0.5f, 0.5f, 0f,    // V0
        -0.5f, -0.5f, 0f,   // V1
        0.5f, -0.5f, 0f,    // V2
        0.5f, 0.5f, 0f      // V3
    };

    int[] indices = {
        0, 1, 3,            // Top left triangle (V0, V1, V3)
        3, 1, 2             // Bottom right triangle (V3, V1, V2)
    };

    float[] textureCoords = {
        0, 0,               // V0
        0, 1,               // V1
        1, 1,               // V2
        1, 0                // V3
    };

    var model = loader.loadToVAO(vertices, textureCoords, indices);
    var texture = loader.loadTexture(TEXTURES_ROOT.resolve(Paths.get("cat.png")));
    var texturedModel = new TexturedModel(model, texture);

    while (!display.isCloseRequested()) {
      // game logic
      renderer.prepare();
      shader.start();
      renderer.render(texturedModel);
      shader.stop();
      display.update();
    }

    shader.cleanUp();
    loader.cleanUp();
    display.close();
  }
}
