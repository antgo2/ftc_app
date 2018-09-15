package org.firstinspires.ftc.teamcode.auto;

import android.graphics.Color;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class AutoContainer extends LinearOpMode implements OpenCVManager.Listener {
  OpenCVManager manager = new OpenCVManager(hardwareMap.appContext, 0);
  Scalar cubeLower = null;//HSV
  Scalar cubeUpper = null;//HSV
  float[] cubeLowerHSV = new float[3];
  float[] cubeUpperHSV = new float[3];
  int[] cubeLowerRGB = new int[] {255, 230, 160};
  int[] cubeUpperRGB = new int[] {225, 180, 60};

  @Override
  public void runOpMode() throws InterruptedException {
    manager.enable();
    AutoTransitioner.transitionOnStop(this, "TELEOP");
    Color.RGBToHSV(cubeLowerRGB[0], cubeLowerRGB[1], cubeLowerRGB[2], cubeLowerHSV);
    Color.RGBToHSV(cubeUpperRGB[0], cubeUpperRGB[1], cubeUpperRGB[2], cubeUpperHSV);
    for(int i = 0; i <= 2; i++) {
      if(i == 0) {
        cubeLowerHSV[i] /= 2;
        cubeUpperHSV[i] /= 2;
      } else {
        cubeLowerHSV[i] *= 255;
        cubeUpperHSV[i] *= 255;
      }
    }
    cubeLower = new Scalar(cubeLowerHSV[0], cubeLowerHSV[1], cubeLowerHSV[2]);
    cubeUpper = new Scalar(cubeUpperHSV[0], cubeUpperHSV[1], cubeUpperHSV[2]);
    waitForStart();

    manager.setListener(this);
    manager.disable();
  }

  @Override public Mat process(Mat rgba, Mat gray) {
    Imgproc.cvtColor(rgba, rgba, Imgproc.COLOR_RGBA2RGB);
    Mat hsv = new Mat(), mask = new Mat();
    Imgproc.cvtColor(rgba, hsv, Imgproc.COLOR_RGB2HSV);
    Core.inRange(hsv, cubeLower, cubeUpper, mask);
    return mask;
  }

  @Autonomous(name = "FACING DEPOT")
  class DepotAugment extends AutoContainer {
    @Override
    public void runOpMode() throws InterruptedException {
      super.runOpMode();
    }
  }

  @Autonomous(name = "FACING CRATER")
  class CraterAugment extends AutoContainer {
    @Override
    public void runOpMode() throws InterruptedException {
      super.runOpMode();
    }
  }
}
