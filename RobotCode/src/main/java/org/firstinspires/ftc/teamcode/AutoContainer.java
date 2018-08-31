package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class AutoContainer extends LinearOpMode {
  OpenCVManager manager = new OpenCVManager(hardwareMap.appContext, 0);

  @Override
  public void runOpMode() throws InterruptedException {
    manager.enable();
    waitForStart();
    manager.disable();
  }

  @Autonomous(name = "RED")
  class RedAugment extends AutoContainer {

  }

  @Autonomous(name = "BLUE")
  class BlueAugment extends AutoContainer {

  }
}
