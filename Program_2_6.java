import javax.swing.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import com.jogamp.opengl.util.*;
import jdk.jshell.execution.Util;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL2ES2.GL_COMPILE_STATUS;
import static com.jogamp.opengl.GL2ES2.GL_LINK_STATUS;

public class Program_2_6  extends JFrame implements GLEventListener{
    private GLCanvas myCanvas;
    private int renderingProgram;
    private int vao[] = new int[1];
    private float x = 0.0f;
    private float inc = 0.01f;

    public Program_2_6() {
        /*
        Small program which opens a window and draws a small dot
         */
        setTitle("Chapter2 - program 6");
        setSize(600, 400);
        ;
        setLocation(200, 200);
        myCanvas = new GLCanvas();
        myCanvas.addGLEventListener(this);
        this.add(myCanvas);
        this.setVisible(true);
        Animator animtr = new Animator(myCanvas);
        animtr.start();
    }

    public void display(GLAutoDrawable drawable) {
        GL4 gl = (GL4) GLContext.getCurrentGL();
        gl.glClear(GL_DEPTH_BUFFER_BIT);
        gl.glClear(GL_COLOR_BUFFER_BIT);
        gl.glUseProgram(renderingProgram);
        x += inc;
        if (x > 1.0f) inc = -0.01f;
        if (x < -1.0f) inc = 0.01f;
        int offsetLoc = gl.glGetUniformLocation(renderingProgram, "offset");
        gl.glProgramUniform1f(renderingProgram, offsetLoc, x);
        gl.glDrawArrays(GL_TRIANGLES, 0, 3);
    }


    public static void main(String[] args) {
        new Program_2_6();

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void dispose(GLAutoDrawable drawable) {
    }

    public void init(GLAutoDrawable drawable) {
        GL4 gl = (GL4) GLContext.getCurrentGL();
        renderingProgram =Utils.createShadeProgram("vertShader.glsl","fragShader.glsl",false);
        gl.glGenVertexArrays(vao.length, vao, 0);
        gl.glBindVertexArray(vao[0]);
    }
}
