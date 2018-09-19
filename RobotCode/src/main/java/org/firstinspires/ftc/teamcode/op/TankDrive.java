package org.firstinspires.ftc.teamcode.op;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import java.util.Locale;

public class TankDrive extends SubSystem {
  double leftY, rightY;
  double multiplier = 1.0;

  public TankDrive() {
    super("TankDrive");
  }

  @Override public void run() {
    while(!t.isInterrupted()) {
      leftY = scaleInput(-gamepad1.left_stick_y);
      rightY = scaleInput(gamepad1.right_stick_y);

      leftY = Range.clip(leftY, -1, 1);
      rightY = Range.clip(rightY, -1, 1);
      leftY *= multiplier;
      rightY *= multiplier;
      left.setPower(leftY);
      right.setPower(rightY);
    }
  }

  @Override public void setup() {
    left.setDirection(DcMotor.Direction.REVERSE);
  }

  @Override public String report() {
    return String.format(Locale.US, "left %d right %d", leftY, rightY);
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
