package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.OpenCVManager;

public class AutoContainer extends LinearOpMode {
  OpenCVManager manager = new OpenCVManager(hardwareMap.appContext, 0);

  @Override
  public void runOpMode() throws InterruptedException {
    manager.enable();
    AutoTransitioner.transitionOnStop(this, "TELEOP");
    waitForStart();
    manager.disable();
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
