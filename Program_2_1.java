import javax.swing.*;

import static com.jogamp.opengl.GL4.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;

public class Program_2_1 extends JFrame implements GLEventListener {
    private GLCanvas myCanvas;

    public Program_2_1() {
        /*
        Small program which opens a window and changes the background color
         */
        setTitle("Chapter2 - program 1");
        setSize(600, 400);
        ;
        setLocation(200, 200);
        myCanvas = new GLCanvas();
        myCanvas.addGLEventListener(this);
        this.add(myCanvas);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Program_2_1();
    }

    public void init(GLAutoDrawable drawable) {
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void dispose(GLAutoDrawable drawable) {
    }

    public void display(GLAutoDrawable drawable) {
        GL4 gl = (GL4) GLContext.getCurrentGL();
        gl.glClearColor(1.0f,0.0f,0.0f,0.0f);
        gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    ;
}
