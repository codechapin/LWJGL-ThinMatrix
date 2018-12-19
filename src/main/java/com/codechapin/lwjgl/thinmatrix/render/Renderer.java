package com.codechapin.lwjgl.thinmatrix.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Renderer {

  public void prepare() {
    glClear(GL_COLOR_BUFFER_BIT);
    glClearColor(1, 0, 0, 1);
  }

  public void render(RawModel model) {
    glBindVertexArray(model.getVaoID());
    glEnableVertexAttribArray(0);
    glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
    glDisableVertexAttribArray(0);
    glBindVertexArray(0);
  }
}
