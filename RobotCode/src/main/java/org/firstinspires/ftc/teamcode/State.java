package org.firstinspires.ftc.teamcode;

import java.util.HashMap;

abstract class State {
  private static HashMap<String, State> list = new HashMap<>();

  State(String name) {
    if(list.get(name) != null) throw new IllegalStateException();
    list.put(name, this);
  }

  abstract void run(Object... args);

  static void transit(String next, Object... args) {
    if(list.size() <= 1) throw new IllegalStateException();
    list.get(next).run(args);
  }
}
