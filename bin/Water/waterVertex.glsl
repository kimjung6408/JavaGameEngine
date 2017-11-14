#version 400 core


in vec3 position;

out vec4 clipSpace;
out vec2 texCoords;
out vec3 toCameraVector;
out vec3 fromLightVector;

uniform mat4 WorldMat;
uniform mat4 ViewMat;
uniform mat4 ProjMat;

uniform vec3 cameraPosition;
uniform vec3 lightPosition;

const float tiling=0.75;

void main()
{
	vec4 PosW= WorldMat * vec4 (position.x,0.0, position.y, 1.0);
	clipSpace= ProjMat*ViewMat* PosW;
	gl_Position = clipSpace;
	
	//calculate toCameraVector
	toCameraVector= normalize(cameraPosition-PosW.xyz);
	
	//calculate fromLightVector
	fromLightVector= normalize(PosW.xyz-lightPosition);
	
	texCoords= vec2(position.x/2.0+0.5, position.y/2.0+0.5)*tiling;
}