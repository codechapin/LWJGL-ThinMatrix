package com.codechapin.lwjgl.thinmatrix.shaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.codechapin.lwjgl.thinmatrix.utils.IOUtils.println;
import static java.lang.System.exit;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram {
  private static final Path GLSL_ROOT = Paths.get(".", "src", "main", "glsl");

  private int vertexShaderID;
  private int fragmentShaderID;
  private int programID;

  public ShaderProgram(Path vertexPath, Path fragmentPath) {
    vertexShaderID = loadShader(vertexPath, GL_VERTEX_SHADER);
    fragmentShaderID = loadShader(fragmentPath, GL_FRAGMENT_SHADER);

    programID = glCreateProgram();
    glAttachShader(programID, vertexShaderID);
    glAttachShader(programID, fragmentShaderID);

    bindAttributes();

    glLinkProgram(programID);
    glValidateProgram(programID);
  }

  public void start() {
    glUseProgram(programID);
  }

  public void stop() {
    glUseProgram(0);
  }

  public void cleanUp() {
    stop();
    glDetachShader(programID, vertexShaderID);
    glDetachShader(programID, fragmentShaderID);
    glDeleteShader(vertexShaderID);
    glDeleteShader(fragmentShaderID);
    glDeleteProgram(programID);
  }

  protected abstract void bindAttributes();

  protected void bindAttribute(int attribute, String variableName) {
    glBindAttribLocation(programID, attribute, variableName);
  }

  private int loadShader(Path filePath, int type) {
    try {
      var shaderSource = Files.readString(GLSL_ROOT.resolve(filePath));

      var shaderID = glCreateShader(type);
      glShaderSource(shaderID, shaderSource);
      glCompileShader(shaderID);

      if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
        println(glGetShaderInfoLog(shaderID, 500));
        println("Could not compile shader.");
        exit(-1);
      }

      return shaderID;
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage(), ex);
    }
  }
}
