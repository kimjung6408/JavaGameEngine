#version 400 core

in vec3 position;
in vec2 texCoords;
in vec3 normal;

out vec2 pass_texCoords;
out vec3 pass_normal;
out vec3 pass_WorldPos;

uniform mat4 WorldMat;
uniform mat4 ViewMat;
uniform mat4 ProjMat;

uniform vec4 plane;


void main()
{
	vec4 PosW=WorldMat* vec4(position, 1.0);
	gl_Position = ProjMat*ViewMat*PosW;
	
	gl_ClipDistance[0]=dot(plane, PosW);
	
	pass_texCoords=texCoords;
	pass_normal=normalize((inverse(transpose(WorldMat))*vec4(normal, 1.0)).xyz);
	pass_WorldPos=PosW.xyz;
}