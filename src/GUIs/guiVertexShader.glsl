#version 140

in vec2 position;

out vec2 textureCoords;

uniform mat4 WorldMat;
uniform vec2 Resolution;
uniform vec2 WindowPos;

void main(void){

	vec2 identityPos= (WorldMat*vec4(position, 0.0, 1.0)).xy+vec2(-1.0,1.0);
	vec2 translatePos=2.0*WindowPos/Resolution;
	vec2 screenPos=identityPos+ vec2(translatePos.x, -translatePos.y);
	
	gl_Position = vec4(screenPos, 0.0, 1.0);
	textureCoords = vec2((position.x+1.0)/2.0, 1 - (position.y+1.0)/2.0);
}