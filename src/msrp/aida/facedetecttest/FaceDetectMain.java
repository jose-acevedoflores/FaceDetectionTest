package msrp.aida.facedetecttest;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Face;
import android.hardware.Camera.FaceDetectionListener;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;

public class FaceDetectMain extends Activity  {

	private Camera mCamera;
	private CameraPreview mPreview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.facedetect_main);
		// Create an instance of Camera
		mCamera = getCameraInstance();
		mCamera.setFaceDetectionListener(new FaceDetect());
		mCamera.setDisplayOrientation(90);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);

		// Create our Preview view and set it as the content of our activity.
		mPreview = new CameraPreview(this, mCamera);

		preview.addView(mPreview);
		preview.setVisibility(View.INVISIBLE);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mCamera != null){
			mCamera.release();        // release the camera for other applications
			mCamera = null;
		}            
	}


	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance(){
		Camera c = null;
		try {
			c = Camera.open(CameraInfo.CAMERA_FACING_FRONT); // attempt to get a Camera instance
		}
		catch (Exception e){
			// Camera is not available (in use or does not exist)
		}
		return c; // returns null if camera is unavailable
	}

}
/**
 * 
 * @author joseacevedo
 *
 */
class FaceDetect implements FaceDetectionListener{

	private int previousY=0; 
	private long prevTime;
	
	@Override
	public void onFaceDetection(Face[] faces, Camera camera) {
		if(faces.length >= 1)
		{	
//			Log.d("FaceDetectTest", "face detected: "+ faces.length +
//					" Face 1 Location X: " + faces[0].rect.centerX() +
//					" || Y: " + faces[0].rect.centerY() );
			
			
			if( Math.abs(faces[0].rect.centerY() ) - Math.abs(previousY) >= 40 && System.currentTimeMillis() -  prevTime  < 1000)
				System.out.println("YEs");
			
			prevTime = System.currentTimeMillis();
			previousY = faces[0].rect.centerY();
		}
		else
			Log.d("FaceDetectTest", "Wu " + faces.length);
	}

}

