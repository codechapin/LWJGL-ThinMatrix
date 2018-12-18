package com.codechapin.lwjgl.thinmatrix.render;

import com.codechapin.lwjgl.thinmatrix.display.Display;

public class DisplayManager {
  private static final int WIDTH = 1280;
  private static final int HEIGHT = 720;
  private static final int FPS_CAP = 120;

  private final Display display;

  public DisplayManager() {
    display = new Display(3, 2);
  }

  public void create() {
    display.setForwardCompatible(true);
    display.setProfileCore(true);
    display.setTitle("Our First Display!");

    display.create(WIDTH, HEIGHT);
  }

  public void update() {
    display.sync(FPS_CAP);
    display.update();
  }

  public boolean isCloseRequested() {
    return display.isCloseRequested();
  }

  public void close() {
    display.destroy();
  }
}
