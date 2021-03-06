#include <com.badlogic.gdx.physics.box2d.joints.GearJoint.h>

//@line:28

#include <Box2D/Box2D.h> 
	 JNIEXPORT void JNICALL Java_com_badlogic_gdx_physics_box2d_joints_GearJoint_jniSetRatio(JNIEnv* env, jobject object, jlong addr, jfloat ratio) {


//@line:41

		b2GearJoint* joint =  (b2GearJoint*)addr;
		joint->SetRatio( ratio );
	

}

JNIEXPORT jfloat JNICALL Java_com_badlogic_gdx_physics_box2d_joints_GearJoint_jniGetRatio(JNIEnv* env, jobject object, jlong addr) {


//@line:51

		b2GearJoint* joint =  (b2GearJoint*)addr;
		return joint->GetRatio();
	

}

