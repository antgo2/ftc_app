package org.firstinspires.ftc.teamcode.op;

import java.util.ArrayList;
import java.util.Arrays;
import org.firstinspires.ftc.teamcode.TeleOpMain;

public abstract class SubSystem extends TeleOpMain implements Runnable {
  static ArrayList<SubSystem> list = new ArrayList<>();
  public Thread t = new Thread(this);
  public String TAG;
  public SubSystem(String tag) {
    this.TAG = tag;
  }

  public abstract void setup();
  public abstract String report();

  public static ArrayList<SubSystem> get() {
    return list;
  }

  public static void put(SubSystem... s) {
    list.addAll(Arrays.asList(s));
  }
}
