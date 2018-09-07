package org.firstinspires.ftc.teamcode.op;

import com.qualcomm.robotcore.util.Range;
import static org.firstinspires.ftc.teamcode.op.MainTeleOp.*;

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
public class DriveSystem extends Thread {
  @Override
  public void run() {
    while(!isInterrupted()) {
      double gamepad1LeftY = scaleInput(-driver1.left_stick_y);
      double gamepad1LeftX = scaleInput(driver1.left_stick_x);
      double gamepad1RightX = scaleInput(driver1.right_stick_x);

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

  private double scaleInput(double dVal)  {
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
    // return scaled value.
    return (dVal < 0) ? -scaleArray[index] : scaleArray[index];
  }
}
