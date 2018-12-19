package com.codechapin.lwjgl.thinmatrix.render;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class Loader {
  private ArrayList<Integer> vaos = new ArrayList<>();
  private ArrayList<Integer> vbos = new ArrayList<>();

  public RawModel loadToVAO(float[] positions) {
    var vaoID = createVAO();
    storeDataInAttributeList(0, positions);
    unbindVAO();

    return new RawModel(vaoID, positions.length / 3);
  }

  public void cleanUp() {
    for (var vao : vaos) {
      glDeleteVertexArrays(vao);
    }

    for (var vbo : vbos) {
      glDeleteBuffers(vbo);
    }

  }

  private int createVAO() {
    var vaoID = glGenVertexArrays();
    vaos.add(vaoID);

    glBindVertexArray(vaoID);

    return vaoID;
  }

  private void storeDataInAttributeList(int attributeNumber, float[] data) {
    int vboID = glGenBuffers();
    vbos.add(vboID);

    glBindBuffer(GL_ARRAY_BUFFER, vboID);

    var buffer = storeDataInFloatBuffer(data);
    glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    glVertexAttribPointer(attributeNumber, 3, GL_FLOAT, false, 0, 0);
    glBindBuffer(GL_ARRAY_BUFFER, 0);
  }

  private void unbindVAO() {
    glBindVertexArray(0);
  }

  private FloatBuffer storeDataInFloatBuffer(float[] data) {
    var buffer = BufferUtils.createFloatBuffer(data.length);
    buffer.put(data);
    buffer.flip();

    return buffer;
  }

}
