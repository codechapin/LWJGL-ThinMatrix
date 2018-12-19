package com.codechapin.lwjgl.thinmatrix.render;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class Loader {
  private ArrayList<Integer> vaos = new ArrayList<>();
  private ArrayList<Integer> vbos = new ArrayList<>();

  public RawModel loadToVAO(float[] positions, int[] indices) {
    var vaoID = createVAO();
    bindIndicesBuffer(indices);
    storeDataInAttributeList(0, positions);
    unbindVAO();

    return new RawModel(vaoID, indices.length);
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

  private void bindIndicesBuffer(int[] indices) {
    var vboID = glGenBuffers();
    vbos.add(vboID);

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);

    var buffer = storeDataInIntBuffer(indices);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
  }

  private IntBuffer storeDataInIntBuffer(int[] data) {
    var buffer = BufferUtils.createIntBuffer(data.length);
    buffer.put(data);
    buffer.flip();

    return buffer;
  }

  private FloatBuffer storeDataInFloatBuffer(float[] data) {
    var buffer = BufferUtils.createFloatBuffer(data.length);
    buffer.put(data);
    buffer.flip();

    return buffer;
  }

}
