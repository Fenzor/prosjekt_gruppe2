#version 330

uniform vec4 pickColor;
uniform vec4 replaceColor;
uniform sampler2D colorMap;

in vec4 vVaryingTexCoords;
out vec4 vFragColor;
 
void main(void)
{
    gl_FragColor = vec4(1,0,0,0.5f);
}