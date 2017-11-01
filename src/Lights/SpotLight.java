package Lights;

import org.lwjgl.util.vector.Vector3f;

public class SpotLight extends Light{
		private float rangeAngle;
		private Vector3f attenuation;
		private Vector3f direction;
		
		public SpotLight(Vector3f position, Vector3f direction, Vector3f attenuation, Vector3f diffuse, Vector3f specular, float rangeAngle)
		{
			super(LightType.SPOT_LIGHT, position, diffuse, specular);
			this.direction=direction;
			this.attenuation=attenuation;
			this.rangeAngle=rangeAngle;
		}

		public float getRangeAngle() {
			return rangeAngle;
		}

		public void setRangeAngle(float rangeAngle) {
			this.rangeAngle = rangeAngle;
		}

		public Vector3f getAttenuation() {
			return attenuation;
		}

		public void setAttenuation(float constant, float linear, float square) {
			this.attenuation =new Vector3f(constant, linear, square);
		}

		public Vector3f getDirection() {
			return direction;
		}

		public void setDirection(Vector3f direction) {
			this.direction = direction;
		}
		
		
}
