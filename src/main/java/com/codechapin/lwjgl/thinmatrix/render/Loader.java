package com.codechapin.lwjgl.thinmatrix.render;

import com.codechapin.lwjgl.thinmatrix.models.RawModel;
import com.codechapin.lwjgl.thinmatrix.textures.Texture;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Loader {
  private List<Integer> vaos = new ArrayList<>();
  private List<Integer> vbos = new ArrayList<>();
  private List<Integer> textures = new ArrayList<>();

  public RawModel loadToVAO(float[] positions, float[] textureCoords, int[] indices) {
    var vaoID = createVAO();
    bindIndicesBuffer(indices);
    storeDataInAttributeList(0, 3, positions);
    storeDataInAttributeList(1, 2, textureCoords);
    unbindVAO();

    return new RawModel(vaoID, indices.length);
  }

  public Texture loadTexture(Path path) {

    var id = glGenTextures();
    textures.add(id);

    glBindTexture(GL_TEXTURE_2D, id);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

    var width = BufferUtils.createIntBuffer(1);
    var height = BufferUtils.createIntBuffer(1);
    var comp = BufferUtils.createIntBuffer(1);

    var data = stbi_load(path.toString(), width, height, comp, 4);

    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(), height.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
    stbi_image_free(data);

    return new Texture(id);
  }

  public void cleanUp() {
    for (var vao : vaos) {
      glDeleteVertexArrays(vao);
    }

    for (var vbo : vbos) {
      glDeleteBuffers(vbo);
    }

    for (var texture : textures) {
      glDeleteTextures(texture);
    }
  }

  private int createVAO() {
    var vaoID = glGenVertexArrays();
    vaos.add(vaoID);

    glBindVertexArray(vaoID);

    return vaoID;
  }

  private void storeDataInAttributeList(int attributeNumber, int size, float[] data) {
    int vboID = glGenBuffers();
    vbos.add(vboID);

    glBindBuffer(GL_ARRAY_BUFFER, vboID);

    var buffer = storeDataInFloatBuffer(data);
    glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    glVertexAttribPointer(attributeNumber, size, GL_FLOAT, false, 0, 0);
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
