import javax.swing.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;

import static com.jogamp.opengl.GL.GL_POINTS;
import static com.jogamp.opengl.GL.GL_TRIANGLES;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class Program_2_4  extends JFrame implements GLEventListener{
    private GLCanvas myCanvas;
    private int renderingProgram;
    private int vao[] = new int[1];

    public Program_2_4() {
        /*
        Small program which opens a window and draws a small dot
         */
        setTitle("Chapter2 - program 4");
        setSize(600, 400);
        ;
        setLocation(200, 200);
        myCanvas = new GLCanvas();
        myCanvas.addGLEventListener(this);
        this.add(myCanvas);
        this.setVisible(true);
    }

    public void display(GLAutoDrawable drawable) {
        GL4 gl = (GL4) GLContext.getCurrentGL();
        gl.glUseProgram(renderingProgram);
        gl.glPointSize(30.0f);
        gl.glDrawArrays(GL_TRIANGLES, 0, 3);
    }

    private int createShadeProgram() {
        GL4 gl = (GL4) GLContext.getCurrentGL();
        String[] vshaderSource = readShaderSource("C:\\Users\\abild\\IdeaProjects\\3D Grapichs ISA\\out\\production\\3D Grapichs ISA\\vertShader.glsl");
        String[] fshaderSource = readShaderSource("C:\\Users\\abild\\IdeaProjects\\3D Grapichs ISA\\out\\production\\3D Grapichs ISA\\fragShader.glsl");

        int vShader = gl.glCreateShader(GL2ES2.GL_VERTEX_SHADER);
        gl.glShaderSource(vShader, 3, vshaderSource, null, 0);
        gl.glCompileShader(vShader);

        int fShader = gl.glCreateShader(GL2ES2.GL_FRAGMENT_SHADER);
        gl.glShaderSource(fShader, 4, fshaderSource, null, 0);
        gl.glCompileShader(fShader);



        gl.glShaderSource(vShader, vshaderSource.length, vshaderSource, null, 0);
        gl.glShaderSource(fShader, fshaderSource.length, fshaderSource, null,0);

        int vfProgram = gl.glCreateProgram();
        gl.glAttachShader(vfProgram, vShader);
        gl.glAttachShader(vfProgram, fShader);
        gl.glLinkProgram(vfProgram);

        gl.glDeleteShader(vShader);
        gl.glDeleteShader(fShader);
        return vfProgram;

    }

    public static void main(String[] args) {
        new Program_2_4();

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void dispose(GLAutoDrawable drawable) {
    }

    public void init(GLAutoDrawable drawable) {
        GL4 gl = (GL4) GLContext.getCurrentGL();
        renderingProgram = createShadeProgram();
        gl.glGenVertexArrays(vao.length, vao, 0);
        gl.glBindVertexArray(vao[0]);
    }


    private String[] readShaderSource(String filename)
    {
        Vector<String> lines = new Vector<String>();
        Scanner sc;
        String[] program;
        try
        {
            sc = new Scanner(new File(filename));
            while (sc.hasNext())
            {
                lines.addElement(sc.nextLine());
            }
            program = new String[lines.size()];
            for(int i = 0; i < lines.size(); i++)
            {
                program[i] = (String) lines.elementAt(i) + "\n";
            }
        }
        catch (IOException e)
        {
            System.err.println("IOException reading file: " + e);
            return null;
        }
        return program;
    }

}
