package org.firstinspires.ftc.teamcode.auto;

import android.os.Bundle;
import com.qualcomm.robotcore.exception.DuplicateNameException;
import java.util.HashMap;

public abstract class State {
  private static HashMap<String, State> list = new HashMap<>();

  public State(String name) {
    if(list.get(name) != null) throw new DuplicateNameException(name);
    list.put(name, this);
  }

  abstract void run(Bundle args);

  public static void transmit(String next, Bundle args) {
    if(list.size() <= 1) throw new IndexOutOfBoundsException();
    list.get(next).run(args);
  }
}
