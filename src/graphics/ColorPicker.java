/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import org.lwjgl.opengl.GL20;
import org.newdawn.slick.Color;

/**
 *
 * @author Lars Aksel
 */
public class ColorPicker extends Shader {
    private Color picker;
    private Color replace;
    private int pickColorId = GL20.glGetUniformLocation(shaderProg, "pickColor");
    private int replaceColorId = GL20.glGetUniformLocation(shaderProg, "replaceColor");
    private int texture1 = GL20.glGetUniformLocation(shaderProg, "colorMap");
    
    public ColorPicker(Color picker, Color replace) {
        super();
        super.setShaderPaths("res/shader/vertexShader.vert", "res/shader/picker.frag");
        this.picker = picker;
        this.replace = replace;
        this.pickColorId = GL20.glGetUniformLocation(shaderProg, "pickColor");
        this.replaceColorId = GL20.glGetUniformLocation(shaderProg, "replaceColor");
        this.texture1 = GL20.glGetUniformLocation(shaderProg, "colorMap");
        
    }
    
    @Override
    public void useShader() {        
        super.useShader();
        GL20.glUniform4f(pickColorId, picker.r, picker.g, picker.b, picker.a);
        GL20.glUniform4f(replaceColorId, replace.r, replace.g, replace.b, replace.a);
        GL20.glUniform1i(texture1, 0);
    }
}
