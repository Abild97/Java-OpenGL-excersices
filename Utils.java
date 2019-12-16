import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.glu.GLU;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import static com.jogamp.opengl.GL.GL_NO_ERROR;
import static com.jogamp.opengl.GL2ES2.*;

public class Utils {

    public static int createShadeProgram(String vShaderPath, String fShaderPath, boolean laptopPath) {

        if(laptopPath);
        else
        {
            vShaderPath = "C:\\Users\\abild\\IdeaProjects\\3D Grapichs ISA\\out\\production\\3D Grapichs ISA\\" + vShaderPath;
            fShaderPath = "C:\\Users\\abild\\IdeaProjects\\3D Grapichs ISA\\out\\production\\3D Grapichs ISA\\" + fShaderPath;
        }
        GL4 gl = (GL4) GLContext.getCurrentGL();
        int[] vertCompiled = new int[1];
        int[] fragCompiled = new int[1];
        int[] linked = new int[1];

        String[] vshaderSource = readShaderSource(vShaderPath);
        String[] fshaderSource = readShaderSource(fShaderPath);

        int vShader = gl.glCreateShader(GL2ES2.GL_VERTEX_SHADER);
        gl.glShaderSource(vShader, 3, vshaderSource, null, 0);
        gl.glCompileShader(vShader);
        Utils.checkOpengGLError();
        gl.glGetShaderiv(vShader, GL_COMPILE_STATUS, vertCompiled, 0);

        if(vertCompiled[0] != 1)
        {
            System.out.println("Vertex compilation failed");
            Utils.printShaderLog(vShader);
        }

        int fShader = gl.glCreateShader(GL2ES2.GL_FRAGMENT_SHADER);
        gl.glShaderSource(fShader, 4, fshaderSource, null, 0);
        gl.glCompileShader(fShader);
        Utils.checkOpengGLError();
        gl.glGetShaderiv(fShader, GL_COMPILE_STATUS, fragCompiled, 0);
        if (fragCompiled[0] != 1)
        {
            System.out.println("Fragment compilation failed.");
            Utils.printShaderLog(fShader);
        }

        if((vertCompiled[0] != 1) || (fragCompiled[0] != 1))
        {
            System.out.println("\nCompilation error; return-flags:");
            System.out.println(" vertCompiled = " + vertCompiled[0] +"; fragCompiled = " + fragCompiled[0]);
        }


        gl.glShaderSource(vShader, vshaderSource.length, vshaderSource, null, 0);
        gl.glShaderSource(fShader, fshaderSource.length, fshaderSource, null,0);

        int vfProgram = gl.glCreateProgram();
        gl.glAttachShader(vfProgram, vShader);
        gl.glAttachShader(vfProgram, fShader);
        gl.glLinkProgram(vfProgram);
        Utils.checkOpengGLError();
        gl.glGetProgramiv(vfProgram, GL_LINK_STATUS, linked, 0);
        if (linked[0] != 1 )
        {
            System.out.println("linking failed.");
            Utils.printProgramLog(vfProgram);
        }


        gl.glDeleteShader(vShader);
        gl.glDeleteShader(fShader);
        return vfProgram;

    }


    public static String[] readShaderSource(String filename)
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

    public static void printShaderLog(int shader){
        GL4 gl = (GL4) GLContext.getCurrentGL();
        int[] len = new int[1];
        int[] chWrttn  = new int[1];
        byte[] log = null;

        gl.glGetShaderiv(shader, GL_INFO_LOG_LENGTH, len, 0);
        if (len[0] > 0)
        {
            log = new byte[len[0]];
            gl.glGetShaderInfoLog(shader, len[0], chWrttn, 0, log, 0);
            System.out.println("Shader Info Log: ");
            for (int i = 0; i < log.length; i++)
            {
                System.out.print((char) log[i]);
            }
        }
    }

    public static void printProgramLog(int prog)
    {
        GL4 gl = (GL4) GLContext.getCurrentGL();
        int[] len = new int[1];
        int[] chWrttn  = new int[1];
        byte[] log = null;

        gl.glGetProgramiv(prog, GL_INFO_LOG_LENGTH, len, 0);
        if (len[0] > 0)
        {
            log = new byte[len[0]];
            gl.glGetProgramInfoLog(prog, len[0], chWrttn, 0, log, 0);
            System.out.println("Shader Info Log: ");
            for (int i = 0; i < log.length; i++)
            {
                System.out.print((char) log[i]);
            }
        }
    }

    public static  boolean checkOpengGLError()
    {
        GL4 gl = (GL4) GLContext.getCurrentGL();
        boolean foundError = false;
        GLU glu = new GLU();
        int glErr = gl.glGetError();
        while(glErr != GL_NO_ERROR)
        {
            System.err.println("glError: " + glu.gluErrorString(glErr));
            foundError = true;
            glErr = gl.glGetError();
        }
        return  foundError;
    }

}
