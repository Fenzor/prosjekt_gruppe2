#version 110

uniform sampler2D texture1; 
uniform vec4 pickColor;
uniform vec4 replaceColor;

void main() {
    if (texture2D(texture1, gl_TexCoord[0].st) == pickColor) {
        gl_FragColor = replaceColor;
    } else {
        gl_FragColor = texture2D(texture1, gl_TexCoord[0].st);
    }
}