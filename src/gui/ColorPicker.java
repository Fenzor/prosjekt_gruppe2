/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import org.lwjgl.opengl.GL20;
import org.newdawn.slick.Color;

/**
 *
 * @author Lars Aksel
 */
public class ColorPicker extends Shader {
    private Color picker;
    private Color replace;
    
    public ColorPicker(Color picker, Color replace) {
        super();
        super.setShaderPaths("res/shader/vertexShader.vert", "res/shader/picker.frag");
        this.picker = picker;
        this.replace = replace;
        
    }
    
    @Override
    public void useShader() {        
        super.useShader();
        int pickColorId = GL20.glGetUniformLocation(shaderProg, "pickColor");
        int replaceColorId = GL20.glGetUniformLocation(shaderProg, "replaceColor");
        int texture1 = GL20.glGetUniformLocation(shaderProg, "colorMap");
        GL20.glUniform4f(pickColorId, picker.r, picker.g, picker.b, picker.a);
        GL20.glUniform4f(replaceColorId, replace.r, replace.g, replace.b, replace.a);
        GL20.glUniform1i(texture1, 0);
    }
}
