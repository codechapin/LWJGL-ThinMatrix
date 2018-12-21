package com.codechapin.lwjgl.thinmatrix.models;

import com.codechapin.lwjgl.thinmatrix.textures.Texture;

public class TexturedModel {
  private RawModel rawModel;
  private Texture texture;

  public TexturedModel(RawModel rawModel, Texture texture) {
    this.rawModel = rawModel;
    this.texture = texture;
  }

  public RawModel getRawModel() {
    return rawModel;
  }

  public Texture getTexture() {
    return texture;
  }
}
