package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.support.annotation.Nullable;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoWSD;

public class Config extends NanoWSD implements SharedPreferences {
  private static Config instance = null;
  private static String ipAddr = "";
  private static final int port = 8081;
  static HashMap<String, String> props = null;
  static File conf = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/FIRST", "config.conf");

  @SuppressWarnings("unchecked")
  private Config(String hostname, int port) throws IOException {
    super(hostname, port);
    if(!conf.exists()) conf.createNewFile();
    try(FileInputStream fis = new FileInputStream(conf); ObjectInputStream ois = new ObjectInputStream(fis)) {
      props = (HashMap<String, String>) ois.readObject();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
  }

  public static Config getInstance() {
    if(instance == null) {
      WifiManager wm = (WifiManager) AppUtil.getInstance().getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
      int ip = wm != null ? wm.getConnectionInfo().getIpAddress() : 0;
      ipAddr = String.format(Locale.US,"%d.%d.%d.%d", (ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff), (ip >> 24 & 0xff));
      try {
        instance = new Config(ipAddr, port);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return instance;
  }

  public static void close() {
    try(FileOutputStream fos = new FileOutputStream(conf); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
      oos.writeObject(props);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if(instance.wasStarted()) instance.stop();
  }

  @Override
  protected WebSocket openWebSocket(IHTTPSession ihttpSession) {
    return new ConfigSocket(ihttpSession);
  }

  @Override
  protected Response serveHttp(IHTTPSession session) {
    Context context = AppUtil.getInstance().getActivity();
    try(Scanner s = new Scanner(context.getAssets().open("interface.html")).useDelimiter("\\A")) {
      StringBuilder sb = new StringBuilder(s.next());
      String meta = String.format(Locale.US, "<meta name='origin' content='%s:%d'/>", ipAddr, port);
      String ret = sb.insert(s.next().indexOf("<head>"+7), meta).toString();
      return newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_HTML, ret);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return newFixedLengthResponse(Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "Not Found");
  }

  @Override
  public Map<String, ?> getAll() {
    return props;
  }

  @Nullable
  @Override
  public String getString(String s, @Nullable String s1) {
    if(props.get(s) == null) {
      props.put(s, s1 != null ? s1 : "");
      return s1;
    }
    return props.get(s);
  }

  @Nullable
  @Override
  public Set<String> getStringSet(String s, @Nullable Set<String> set) {
    if(props.get(s) == null) {
      StringBuilder result = new StringBuilder();
      for(String string : set) {
        result.append(string);
        result.append(",");
      }
      props.put(s, result.length() > 0 ? result.substring(0, result.length() - 1): "");
      return set;
    }
    return new HashSet<>(Arrays.asList(props.get(s).split(",")));
  }

  @Override
  public int getInt(String s, int i) {
    if(props.get(s) == null) {
      props.put(s, String.valueOf(i));
      return i;
    }
    return Integer.parseInt(props.get(s));
  }

  @Override
  public long getLong(String s, long l) {
    if(props.get(s) == null) {
      props.put(s, String.valueOf(l));
      return l;
    }
    return Long.parseLong(props.get(s));
  }

  @Override
  public float getFloat(String s, float v) {
    if(props.get(s) == null) {
      props.put(s, String.valueOf(v));
      return v;
    }
    return Float.parseFloat(props.get(s));
  }

  @Override
  public boolean getBoolean(String s, boolean b) {
    if(props.get(s) == null) {
      props.put(s, String.valueOf(b));
      return b;
    }
    return Boolean.parseBoolean(props.get(s));
  }

  public double getDouble(String s, double d) {
    if(props.get(s) == null) {
      props.put(s, String.valueOf(d));
      return d;
    }
    return Double.parseDouble(props.get(s));
  }

  @Override
  public boolean contains(String s) {
    return props.get(s) != null || props.containsKey(s);
  }

  @Override
  public Editor edit() {
    return null;
  }

  @Override
  public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {}

  @Override
  public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {}
}
