package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeManagerImpl;

public class AutoTransitioner extends Thread {
  private static final AutoTransitioner INSTANCE = new AutoTransitioner();

  private OpMode onStop;
  private String transitionTo;
  private OpModeManagerImpl opModeManager;
  private boolean run = true;

  private AutoTransitioner() {
    this.start();
  }

  @Override
  public void run() {
    try {
      while (run) {
        synchronized (this) {
          if (onStop != null && opModeManager.getActiveOpMode() != onStop) {
            Thread.sleep(250);
            opModeManager.initActiveOpMode(transitionTo);
            reset();
          }
        }
        Thread.sleep(50);
      }
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }

  private void setNewTransition(OpMode onStop, String transitionTo) {
    synchronized (this) {
      this.onStop = onStop;
      this.transitionTo = transitionTo;
      this.opModeManager = (OpModeManagerImpl) onStop.internalOpModeServices;
    }
  }

  private void reset() {
    this.onStop = null;
    this.transitionTo = null;
    this.opModeManager = null;
    run = false;
  }

  public static void transitionOnStop(OpMode onStop, String transitionTo) {
    INSTANCE.setNewTransition(onStop, transitionTo);
  }
}
