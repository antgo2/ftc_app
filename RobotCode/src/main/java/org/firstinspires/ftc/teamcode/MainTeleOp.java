package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

/*
 *       X FRONT X
        X           X
      X  FL       FR  X
              X
             XXX
              X
      X  BL       BR  X
        X           X
          X BACK  X
 */
@TeleOp(name = "TELEOP")
public class MainTeleOp extends OpMode {
  DcMotor fl, fr, bl, br;
  DriveSystem drive = new DriveSystem();
  double flp = 0, frp = 0, blp = 0, brp = 0;

  @Override
  public void init() {
    fr = hardwareMap.dcMotor.get("fr");
    fl = hardwareMap.dcMotor.get("fl");
    bl = hardwareMap.dcMotor.get("bl");
    br = hardwareMap.dcMotor.get("br");

    fr.setDirection(DcMotor.Direction.REVERSE);
    fl.setDirection(DcMotor.Direction.REVERSE);
    bl.setDirection(DcMotor.Direction.REVERSE);
    br.setDirection(DcMotor.Direction.REVERSE);
    drive.start();
  }

  @Override
  public void loop() {
    telemetry.addData("DriveSystem", String.format("%.2f %.2f %.2f %.2f", flp, frp, blp, brp));
  }

  @Override
  public void stop() {
    drive.interrupt();
  }

  private class DriveSystem extends Thread {
    @Override
    public void run() {
      while(!isInterrupted()) {
        double gamepad1LeftY = scaleInput(-gamepad1.left_stick_y);
        double gamepad1LeftX = scaleInput(gamepad1.left_stick_x);
        double gamepad1RightX = scaleInput(gamepad1.right_stick_x);

        flp = -gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
        frp = gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
        brp = gamepad1LeftY + gamepad1LeftX - gamepad1RightX;
        blp = -gamepad1LeftY + gamepad1LeftX - gamepad1RightX;
        flp = Range.clip(flp, -1, 1);
        frp = Range.clip(frp, -1, 1);
        brp = Range.clip(brp, -1, 1);
        blp = Range.clip(blp, -1, 1);

        fl.setPower(flp);
        fr.setPower(frp);
        br.setPower(brp);
        bl.setPower(blp);
      }
    }

    double scaleInput(double dVal)  {
      double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
              0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

      // get the corresponding index for the scaleInput array.
      int index = (int) (dVal * 16.0);

      // index should be positive.
      if (index < 0) {
        index = -index;
      }

      // index cannot exceed size of array minus 1.
      if (index > 16) {
        index = 16;
      }

      // get value from the array.
      double dScale = 0.0;
      if (dVal < 0) {
        dScale = -scaleArray[index];
      } else {
        dScale = scaleArray[index];
      }

      // return scaled value.
      return dScale;
    }
  }
}
