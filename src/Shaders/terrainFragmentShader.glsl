#version 400 core


in vec2 pass_texCoords;
in vec3 pass_normal;
in vec3 pass_WorldPos;

out vec4 out_Color;

uniform vec3 lightPos;
uniform vec3 lightDiffuseColor;
uniform vec3 lightSpecularColor;
uniform vec3 lightAttenuation;

uniform float shineDamper;
uniform float reflectivity;
uniform vec3 cameraPos;

uniform float texDensity;

uniform sampler2D diffuseTex1;

vec3 ambient= vec3(0.1,0.1, 0.1);

vec3 compute_diffuse(vec3 lightDirection, vec3 lightColor, vec3 normal)
{
	float NdotL= clamp(dot(-lightDirection, normal), 0.0, 1.0);
	vec3 result=lightColor*NdotL;
	
	return result;
}

vec3 compute_specular(vec3 lightDir, vec3 lightColor, vec3 normal, vec3 toEye)
{
	vec3 reflected= normalize(reflect(lightDir, normal));
	
	float RdotP= clamp(dot(reflected, toEye) , 0.0, 1.0);
	
	vec3 specular= pow(RdotP, shineDamper)*lightColor*reflectivity;
	
	return specular;
}


void main( void )
{
	vec3 lightDir= normalize(pass_WorldPos-lightPos);
	vec3 toEye= normalize(cameraPos-pass_WorldPos);
	
	vec4 texColor= texture(diffuseTex1, pass_texCoords*texDensity);
	vec4 diffuseColor= texColor*vec4(compute_diffuse(lightDir, lightDiffuseColor, pass_normal), 1.0);
	vec3 specularColor=compute_specular(lightDir, lightSpecularColor, pass_normal, toEye);
	
	//compute attenuation
	float dist= distance( pass_WorldPos, lightPos);
	float att= dot (vec3(1.0, dist, dist*dist) , lightAttenuation);
	
	out_Color= vec4((ambient+diffuseColor.xyz+specularColor)/att, diffuseColor.w);
}