package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import java.util.Locale;

@TeleOp(name = "TELEOP")
public class TeleOpMain extends OpMode {
  static DcMotor left, right, forklift;
  static double leftp = 0, rightp = 0;
  private TankDriveSystem drive = new TankDriveSystem();
  private ArmSystem arm = new ArmSystem();
  private static TeleOpMain instance = new TeleOpMain();
  static Gamepad driver1 = instance.gamepad1;
  static Gamepad driver2 = instance.gamepad2;

  @Override public void init() {
    left = hardwareMap.dcMotor.get("left");
    right = hardwareMap.dcMotor.get("right");
    forklift = hardwareMap.dcMotor.get("lift");

    left.setDirection(DcMotor.Direction.REVERSE);
    forklift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    drive.start();
    arm.start();
  }

  @Override
  public void loop() {
    telemetry.addData("TankSystem left", String.format(Locale.US, "%.2f", leftp));
    telemetry.addData("TankSystem right", String.format(Locale.US, "%.2f", rightp));
  }

  @Override
  public void stop() {
    drive.interrupt();
    arm.interrupt();
  }
}
