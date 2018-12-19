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
        // left bottom triangle
        -0.5f, 0.5f, 0f,
        -0.5f, -0.5f, 0f,
        0.5f, -0.5f, 0f,
        // right top triangle
        0.5f, -0.5f, 0f,
        0.5f, 0.5f, 0f,
        -0.5f, 0.5f, 0f
    };

    var model = loader.loadToVAO(vertices);

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
