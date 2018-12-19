package com.codechapin.lwjgl.thinmatrix.render;

public class RawModel {
  private final int vaoID;
  private final int vertexCount;

  public RawModel(int vaoID, int vertexCount) {
    this.vaoID = vaoID;
    this.vertexCount = vertexCount;
  }

  public int getVaoID() {
    return vaoID;
  }

  public int getVertexCount() {
    return vertexCount;
  }
}
