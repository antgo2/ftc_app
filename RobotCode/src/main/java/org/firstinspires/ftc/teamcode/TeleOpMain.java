package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "TELEOP")
public class TeleOpMain extends OpMode {
  private static DcMotor left, right, forklift;
  private TankDrive drive = new TankDrive();
  private ArmSystem arm = new ArmSystem();

  @Override public void init() {
    left = hardwareMap.dcMotor.get("left");
    right = hardwareMap.dcMotor.get("right");
    forklift = hardwareMap.dcMotor.get("lift");
    drive.init();
    arm.init();
  }

  @Override
  public void start() {
    drive.start();
    arm.start();
  }

  @Override
  public void loop() {
    drive.report();
    arm.report();
  }

  @Override
  public void stop() {
    drive.interrupt();
    arm.interrupt();
  }

  class TankDrive extends Thread implements SubSystem {
    double leftY, rightY;
    double multiplier = 1.0;

    @Override public void init() {
      left.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override public void run() {
      while(!isInterrupted()) {
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

    @Override public void report() {
      telemetry.addData("TankDrive", "left %d right %d", leftY, rightY);
    }
  }

  class ArmSystem extends Thread implements SubSystem {
    int minTick = 0, tick = 0;
    double raisePower = 0.5;
    double inches = 12.0;
    int maxTick = (int) (inches * 25.4);

    @Override public void init() {
      forklift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override public void run() {
      while(!isInterrupted()) {
        if(gamepad2.y && tick < maxTick) {
          forklift.setPower(raisePower);
          tick++;
        } else if(gamepad2.a && tick > minTick) {
          forklift.setPower(-raisePower);
          tick--;
        } else {
          forklift.setPower(0);
        }
      }
    }

    @Override public void report() {
      telemetry.addData("ArmSystem", "power %d tick %d", raisePower, tick);
    }
  }
}
