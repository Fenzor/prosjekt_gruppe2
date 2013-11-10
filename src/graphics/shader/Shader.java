/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics.shader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 *
 * @author larsat
 */
public class Shader {
    protected int shaderProg;
    protected int fragShaderObj;
    protected int vertexShaderObj;
    
    public Shader(String vertexShaderPath, String fragmentShaderPath) throws ParseException {
        shaderProg = GL20.glCreateProgram();
        vertexShaderObj = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        fragShaderObj = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        String vertexProg = readShaderProgram(vertexShaderPath);
        String fragProg = readShaderProgram(fragmentShaderPath);
        GL20.glShaderSource(vertexShaderObj, vertexProg);
        GL20.glShaderSource(fragShaderObj, fragProg);
        GL20.glCompileShader(vertexShaderObj);
        GL20.glCompileShader(fragShaderObj);
        
        if (GL20.glGetShaderi(vertexShaderObj, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) System.out.println("Vertex-shader compile error!\n" + GL20.glGetShaderInfoLog(vertexShaderObj, 1024));
        if (GL20.glGetShaderi(fragShaderObj, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) System.out.println("Fragment-shader compile error!\n" + GL20.glGetShaderInfoLog(fragShaderObj, 1024));
        
        GL20.glAttachShader(shaderProg, fragShaderObj);
        GL20.glAttachShader(shaderProg, vertexShaderObj);
        GL20.glLinkProgram(shaderProg);
        GL20.glValidateProgram(shaderProg);
    }
    
    public Shader() {
    }
    
    protected void setShaderPaths(String vertexShaderPath, String fragmentShaderPath) {
        String vertexProg = null;
        String fragProg = null;
        shaderProg = GL20.glCreateProgram();
        vertexShaderObj = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        fragShaderObj = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        try {
            vertexProg = readShaderProgram(vertexShaderPath);
            fragProg = readShaderProgram(fragmentShaderPath);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        GL20.glShaderSource(vertexShaderObj, vertexProg);
        GL20.glShaderSource(fragShaderObj, fragProg);
        GL20.glCompileShader(vertexShaderObj);
        GL20.glCompileShader(fragShaderObj);
        
        if (GL20.glGetShaderi(vertexShaderObj, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) System.out.println("Vertex-shader compile error!\n" + GL20.glGetShaderInfoLog(vertexShaderObj, 1024));
        if (GL20.glGetShaderi(fragShaderObj, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) System.out.println("Fragment-shader compile error!\n" + GL20.glGetShaderInfoLog(fragShaderObj, 1024));
        
        GL20.glAttachShader(shaderProg, fragShaderObj);
        GL20.glAttachShader(shaderProg, vertexShaderObj);
        GL20.glLinkProgram(shaderProg);
        GL20.glValidateProgram(shaderProg);
    }
    
    private String readShaderProgram(String path) throws ParseException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
        } catch (IOException ex) {
            Logger.getLogger(Shader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.toString();
    }
    
    public void useShader() {        
        GL20.glUseProgram(shaderProg); 
    }
    
    public void detachShader() {
        GL20.glUseProgram(0);  
    }
    
    public void deleteShader() {
        GL20.glDeleteProgram(shaderProg);
        GL20.glDeleteShader(vertexShaderObj);
        GL20.glDeleteShader(fragShaderObj);        
    }
}
