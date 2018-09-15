package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.TeleOpMain.*;

public class ArmSystem extends Thread {
  int minTick = 0, maxTick = 500, tick = 0;
  double raisePower = 0.5;

  @Override public void run() {
    while(!isInterrupted()) {
      if(driver2.y && tick < maxTick) {
        forklift.setPower(raisePower);
        tick++;
      } else if(driver2.a && tick > minTick) {
        forklift.setPower(-raisePower);
        tick--;
      } else {
        forklift.setPower(0);
      }
    }
  }
}
