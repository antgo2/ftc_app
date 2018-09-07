package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "AUTONOMOUS")
public class AutoTransitionerOp extends LinearOpMode {
  @Override
  public void runOpMode() throws InterruptedException {
    String target = "";
    while(true) {
      if(gamepad1.b) {
        target = "RED";
        break;
      } else if(gamepad1.x) {
        target = "BLUE";
        break;
      }
    }
    AutoTransitioner.transitionOnStop(this, target);
    requestOpModeStop();
    waitForStart();
  }
}
