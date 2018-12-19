package com.codechapin.lwjgl.thinmatrix;

import com.codechapin.lwjgl.thinmatrix.render.DisplayManager;
import com.codechapin.lwjgl.thinmatrix.render.Loader;
import com.codechapin.lwjgl.thinmatrix.render.Renderer;

/**
 *
 *
 */
public class MainGameLoop {
  public static void main(String[] args) {
    var display = new DisplayManager();
    display.create();

    var loader = new Loader();
    var renderer = new Renderer();

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

    var model = loader.loadToVAO(vertices, indices);

    while (!display.isCloseRequested()) {
      renderer.prepare();
      // game logic
      renderer.render(model);
      display.update();
    }

    loader.cleanUp();
    display.close();
  }
}
