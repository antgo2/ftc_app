package org.firstinspires.ftc.teamcode.op;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import java.util.Locale;

@TeleOp(name = "TELEOP")
public class MainTeleOp extends OpMode {
  static DcMotor fl, fr, bl, br;
  static double flp = 0, frp = 0, blp = 0, brp = 0;
  private DriveSystem drive = new DriveSystem();
  private static MainTeleOp instance = new MainTeleOp();
  static Gamepad driver1 = instance.gamepad1;
  static Gamepad driver2 = instance.gamepad2;

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
    telemetry.addData("DriveSystem flp", String.format(Locale.US, "%.2f", flp));
    telemetry.addData("DriveSystem frp", String.format(Locale.US, "%.2f", frp));
    telemetry.addData("DriveSystem blp", String.format(Locale.US, "%.2f", blp));
    telemetry.addData("DriveSystem brp", String.format(Locale.US, "%.2f", brp));
  }

  @Override
  public void stop() {
    drive.interrupt();
  }
}
