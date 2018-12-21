package com.codechapin.lwjgl.thinmatrix.shaders;

import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticShader extends ShaderProgram {
  private static final Path VERTEX_PATH = Paths.get("VertexShader.glsl");
  private static final Path FRAGMENT_PATH = Paths.get("FragmentShader.glsl");

  public StaticShader() {
    super(VERTEX_PATH, FRAGMENT_PATH);
  }

  @Override
  protected void bindAttributes() {
    super.bindAttribute(0, "position");
    super.bindAttribute(1, "textureCoords");
  }
}
