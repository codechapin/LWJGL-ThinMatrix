package com.codechapin.lwjgl.thinmatrix;

import com.codechapin.lwjgl.thinmatrix.render.DisplayManager;

/**
 *
 *
 */
public class MainGameLoop {
  public static void main(String[] args) {
    var display = new DisplayManager();
    display.create();

    while (!display.isCloseRequested()) {
      // game logic
      // render
      display.update();
    }

    display.close();
  }
}
