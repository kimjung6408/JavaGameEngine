#version 400 core

in vec4 clipSpace;
in vec2 texCoords;
in vec3 toCameraVector;
in vec3 fromLightVector;

out vec4 outColor;

uniform sampler2D ReflectTexture;
uniform sampler2D RefractTexture;
uniform sampler2D DUDVMap;
uniform sampler2D NormalMap;
uniform sampler2D DepthMap;

uniform float moveFactor;
uniform vec3 lightColor;

//constant effect values
const float waveStrength=0.02;
const float ReflectionIntensity=1.0;
const float ShineDamper=40.0;
const float Reflectivity= 0.8;

void main()
{
	//normalized coordinate device
	vec2 ndc=(clipSpace.xy/clipSpace.w)/2.0 + 0.5;
	
	vec2 RefractTexCoords= vec2(ndc.x, ndc.y);
	vec2 ReflectTexCoords= vec2(ndc.x, -ndc.y);
	
	//depth mapping for removing glitches
	float near=0.1;
	float far=2000.0;
	float depth= texture (DepthMap, RefractTexCoords).r;
	
	float floorDistance= 2.0*near*far/(far+near-(2.0*depth-1.0)*(far-near));
	
	depth=gl_FragCoord.z;
	float waterDistance= 2.0*near*far/(far+near-(2.0*depth-1.0)*(far-near));
	
	float waterDepth= floorDistance - waterDistance;
	
	
	//apply dudv_map distortion.
	vec2 distort1= texture( DUDVMap, vec2(texCoords.x+moveFactor, texCoords.y)).rg*2.0-1.0;
	distort1*=waveStrength;
	vec2 distort2= texture( DUDVMap, texCoords+distort1+moveFactor).rg*2.0-1.0;
	distort2*=waveStrength;
	
	vec2 totalDistort= distort1+distort2;
	totalDistort*= clamp(waterDepth/5.0, 0.0, 1.0);
	
	RefractTexCoords+=totalDistort;
	ReflectTexCoords+=totalDistort;
	
	//fix by clamping
	ReflectTexCoords.x=clamp(ReflectTexCoords.x, 0.001, 0.999);
	ReflectTexCoords.y=clamp(ReflectTexCoords.y, -0.999, -0.001);
	
	vec4 RefractColor= texture(RefractTexture, RefractTexCoords);
	vec4 ReflectColor= texture(ReflectTexture, ReflectTexCoords);
	
		//normal mapping
	vec4 normalMapColor= texture(NormalMap, totalDistort);
	vec3 normal= vec3 ( normalMapColor.r*2.0-1.0,
									  normalMapColor.b*3.0,
									  normalMapColor.g*2.0-1.0
									  );
	normal = normalize(normal);
	
	
	//calculate fresnel factor
	float refractiveFactor= dot(toCameraVector, normal);
	refractiveFactor= pow(refractiveFactor, ReflectionIntensity);
	
	//calculate specular color;
	vec3 reflectedLight= reflect(fromLightVector, normal);
	float specular = max(dot(reflectedLight, toCameraVector), 0.0);
	specular= pow(specular, ShineDamper);
	vec3 Specular=lightColor* specular * Reflectivity;
	Specular*= clamp(waterDepth/5.0, 0.0, 1.0);
	
	//apply fresnel effect
	outColor= mix(ReflectColor, RefractColor, refractiveFactor);
	
	//blue tint
	outColor= mix( outColor, vec4(0.0, 0.3, 0.5, 1.0), 0.2) + vec4(Specular , 0.0);
}