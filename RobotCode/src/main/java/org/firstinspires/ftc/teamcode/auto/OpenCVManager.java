package org.firstinspires.ftc.teamcode.auto;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import org.firstinspires.ftc.teamcode.R;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.core.Core;
import org.opencv.core.Mat;

public class OpenCVManager implements CameraBridgeViewBase.CvCameraViewListener2 {
  private JavaCameraView view;
  private Activity activity;
  private Listener integrated = new Listener() {@Override public Mat process(Mat rgba, Mat gray) { return gray; }};

  public OpenCVManager(final Context context, final int camIndex) {
    this.activity = (Activity) context;
    final CameraBridgeViewBase.CvCameraViewListener2 self = this;
    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        view = new JavaCameraView(context, camIndex);
        view.setCameraIndex(camIndex);
        view.setCvCameraViewListener(self);
      }
    });
  }

  public void enable() {
    view.enableView();
    ViewGroup parent = (ViewGroup) activity.findViewById(R.id.cameraMonitorViewId);
    if(parent.indexOfChild(view) == -1) parent.addView(view);
  }

  public void disable() {
    view.disableView();
    ViewGroup parent = (ViewGroup) activity.findViewById(R.id.cameraMonitorViewId);
    if(parent.indexOfChild(view) != -1) parent.removeView(view);
  }

  @Override
  public void onCameraViewStarted(int i, int i1) {}

  @Override
  public void onCameraViewStopped() {}

  public interface Listener {
    public Mat process(Mat rgba, Mat gray);
  }

  public void setListener(Listener listener) {
    integrated = listener;
  }

  @Override
  public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame frame) {
    Mat rgba = frame.rgba();
    Mat gray = frame.gray();
    Core.transpose(rgba, rgba);
    Core.transpose(gray, gray);
    Core.flip(rgba, rgba, 1);
    Core.flip(gray, gray, 1);
    return integrated.process(rgba, gray);
  }
}
