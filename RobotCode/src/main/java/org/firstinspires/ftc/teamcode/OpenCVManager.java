package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.util.ArrayList;

public class OpenCVManager implements CameraBridgeViewBase.CvCameraViewListener2 {
  static {
    System.loadLibrary("opencv_java3");
  }

  private JavaCameraView view;
  private Activity activity;
  private ArrayList<Listener> list = new ArrayList<>();

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

  interface Listener {
    public void process(Mat rgba, Mat gray, Mat modifiable);
  }

  public void addListener(Listener listener) {
    list.add(listener);
  }

  public void removeListener(Listener listener) {
    list.remove(listener);
  }

  @Override
  public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame frame) {
    Mat modified = frame.gray();
    Mat rgba = frame.rgba();
    Mat gray = frame.gray();
    Core.transpose(modified, modified);
    Core.transpose(rgba, rgba);
    Core.transpose(gray, gray);
    Core.flip(modified, modified, 1);
    Core.flip(rgba, rgba, 1);
    Core.flip(gray, gray, 1);
    for(Listener listener : list) {
      listener.process(rgba, gray, modified);
    }
    return modified;
  }
}
