package org.firstinspires.ftc.teamcode.op;

import com.qualcomm.robotcore.hardware.DcMotor;
import java.util.Locale;

public class ConveyorBelt extends SubSystem {
  double raisePower = 0.5;

  public ConveyorBelt() {
    super("ConveyorBelt");
  }

  @Override public void run() {
    while(!t.isInterrupted()) {
      if(gamepad2.y) conveyor.setPower(raisePower);
      else if(gamepad2.a) conveyor.setPower(-raisePower);
      else conveyor.setPower(0);
    }
  }

  @Override public void setup() {
    conveyor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
  }

  @Override public String report() {
    return String.format(Locale.US, "power %d", raisePower);
  }
}
