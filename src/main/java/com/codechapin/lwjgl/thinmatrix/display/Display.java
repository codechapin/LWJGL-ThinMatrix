package com.codechapin.lwjgl.thinmatrix.display;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Display {
  private final Sync sync;
  private final int majorVersion;
  private final int minorVersion;

  private boolean profileCore;
  private boolean forwardCompatible;

  private String title;

  // The window handle
  private long window;


  public Display(int majorVersion, int minorVersion) {
    sync = new Sync();

    this.majorVersion = majorVersion;
    this.minorVersion = minorVersion;
  }

  public void setProfileCore(boolean profileCore) {
    this.profileCore = profileCore;
  }

  public void setForwardCompatible(boolean forwardCompatible) {
    this.forwardCompatible = forwardCompatible;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void create(int width, int height) {
    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    GLFWErrorCallback.createPrint(System.err).set();

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if (!glfwInit()) {
      throw new IllegalStateException("Unable to initialize GLFW");
    }

    // Configure GLFW
    glfwDefaultWindowHints(); // optional, the current window hints are already the default
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, majorVersion);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, minorVersion);

    if (profileCore) {
      glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    }

    if (forwardCompatible) {
      glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
    } else {
      glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_FALSE);
    }

    // Create the window
    window = glfwCreateWindow(width, height, title, NULL, NULL);
    if (window == NULL) {
      throw new RuntimeException("Failed to create the GLFW window");
    }

    // Setup a key callback. It will be called every time a key is pressed, repeated or released.
    glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
      if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
        glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
      }
    });

    // Get the thread stack and push a new frame
    try (MemoryStack stack = stackPush()) {
      var pWidth = stack.mallocInt(1); // int*
      var pHeight = stack.mallocInt(1); // int*

      // Get the window size passed to glfwCreateWindow
      glfwGetWindowSize(window, pWidth, pHeight);

      // Get the resolution of the primary monitor
      var videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

      // Center the window
      if (videoMode != null) {
        glfwSetWindowPos(
            window,
            (videoMode.width() - pWidth.get(0)) / 2,
            (videoMode.height() - pHeight.get(0)) / 2
        );
      }
    } // the stack frame is popped automatically

    // Make the OpenGL context current
    glfwMakeContextCurrent(window);

    // Make the window visible
    glfwShowWindow(window);

    // This line is critical for LWJGL's interoperation with GLFW's
    // OpenGL context, or any context that is managed externally.
    // LWJGL detects the context that is current in the current thread,
    // creates the GLCapabilities instance and makes the OpenGL
    // bindings available for use.
    createCapabilities();
  }

  public void sync(int fps) {
    sync.sync(fps);
  }

  public void update() {
    glfwSwapBuffers(window); // swap the color buffers

    // Poll for window events. The key callback above will only be
    // invoked during this call.
    glfwPollEvents();
  }

  public boolean isCloseRequested() {
    return glfwWindowShouldClose(window);
  }

  public void destroy() {
    // Free the window callbacks and destroy the window
    glfwFreeCallbacks(window);
    glfwDestroyWindow(window);

    // Terminate GLFW and free the error callback
    glfwTerminate();
    var callback = glfwSetErrorCallback(null);
    if (callback != null) {
      callback.free();
    }
  }

}
