#version 140

in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D guiTexture;
uniform float Alpha;

void main(void){

	vec4 texColor= texture(guiTexture,textureCoords);
	out_Color= vec4(texColor.xyz, texColor.a*Alpha);
	
}