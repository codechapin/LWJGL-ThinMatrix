package com.codechapin.lwjgl.thinmatrix.render;

import com.codechapin.lwjgl.thinmatrix.models.TexturedModel;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Renderer {

  public void prepare() {
    glClear(GL_COLOR_BUFFER_BIT);
    glClearColor(1, 0, 0, 1);
  }

  public void render(TexturedModel texturedModel) {
    var model = texturedModel.getRawModel();

    glBindVertexArray(model.getVaoID());
    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);
    glActiveTexture(GL_TEXTURE);
    glBindTexture(GL_TEXTURE_2D, texturedModel.getTexture().getId());
    glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
    glDisableVertexAttribArray(0);
    glDisableVertexAttribArray(1);
    glBindVertexArray(0);
  }
}
