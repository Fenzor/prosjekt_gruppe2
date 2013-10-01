/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.BufferedReader;
import java.io.FileReader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.newdawn.slick.Color;

/**
 *
 * @author Lars Aksel
 */
public class ColorPicker {
    private int shaderProg;
    private int fragShaderObj;
    private int vertexShaderObj;
    private Color picker;
    private Color replace;
    
    public ColorPicker(Color picker, Color replace) {
        this.picker = picker;
        this.replace = replace;
        this.shaderProg = GL20.glCreateProgram();       
        this.vertexShaderObj = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        this.fragShaderObj = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER); 
        
        String vertexProg = readShaderProgram("res/shader/vertexShader.vert");
        String fragProg = readShaderProgram("res/shader/picker.frag");
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
    
    private String readShaderProgram(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    
    public void useShader() {        
        GL20.glUseProgram(shaderProg);  
        int pickColorId = GL20.glGetUniformLocation(shaderProg, "pickColor");
        int replaceColorId = GL20.glGetUniformLocation(shaderProg, "replaceColor");
        int texture1 = GL20.glGetUniformLocation(shaderProg, "colorMap");
        GL20.glUniform4f(pickColorId, picker.r, picker.g, picker.b, picker.a);
        GL20.glUniform4f(replaceColorId, replace.r, replace.g, replace.b, replace.a);
        GL20.glUniform1i(texture1, 0);
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
