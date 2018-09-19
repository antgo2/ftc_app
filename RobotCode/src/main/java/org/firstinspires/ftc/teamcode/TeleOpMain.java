package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.op.ConveyorBelt;
import org.firstinspires.ftc.teamcode.op.SubSystem;
import org.firstinspires.ftc.teamcode.op.TankDrive;

@TeleOp(name = "TELEOP")
public class TeleOpMain extends OpMode {
  protected static DcMotor left, right, conveyor;

  @Override public void init() {
    left = hardwareMap.dcMotor.get("left");
    right = hardwareMap.dcMotor.get("right");
    conveyor = hardwareMap.dcMotor.get("lift");
    SubSystem.put(new TankDrive(), new ConveyorBelt());
    for(SubSystem s : SubSystem.get()) {
      s.setup();
    }
  }

  @Override
  public void start() {
    for(SubSystem s : SubSystem.get()) {
      s.t.start();
    }
  }

  @Override
  public void loop() {
    for(SubSystem s : SubSystem.get()) {
      telemetry.addData(s.TAG, s.report());
    }
  }

  @Override
  public void stop() {
    for(SubSystem s : SubSystem.get()) {
      s.t.interrupt();
    }
  }
}
