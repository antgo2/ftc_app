package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.auto.AutoTransitioner;
import org.firstinspires.ftc.teamcode.auto.OpenCVManager;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

@Autonomous(name = "Cube Detection")
public class DetectCube extends LinearOpMode implements OpenCVManager.Listener {
  Scalar min = new Scalar(204, 255, 255);//BGR
  Scalar max = new Scalar(0, 255, 255);//BGR

  @Override public void runOpMode() {
    AutoTransitioner.transitionOnStop(this, "TELEOP");
    DcMotor left = hardwareMap.dcMotor.get("left");
    DcMotor right = hardwareMap.dcMotor.get("right");
    left.setDirection(DcMotor.Direction.REVERSE);
    OpenCVManager manager = new OpenCVManager(hardwareMap.appContext, 0);
    manager.setListener(this);

    waitForStart();
    manager.enable();
    while(opModeIsActive()) {
      idle();
    }
    manager.disable();
  }

  @Override public Mat process(Mat rgba, Mat gray) {
    Mat bgr = new Mat(), mask = new Mat();
    Imgproc.cvtColor(rgba, bgr, Imgproc.COLOR_RGBA2BGR);
    Core.inRange(bgr, min, max, mask);
    return mask;
  }
}
