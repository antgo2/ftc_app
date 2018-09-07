package org.firstinspires.ftc.teamcode;

import android.util.Base64;
import android.util.Pair;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;

//implementation of DataURI scheme
public class Message {
  ArrayList<Pair<String, String>> mediaTypes = new ArrayList<>();
  String extension = "";
  byte[] data = new byte[]{};
  Charset charset = Charset.forName("UTF-8");

  public Message(){}
  public Message(String[] types, String extension, byte[] data) {
    this.extension = extension;
    this.data = data;
    for(String type : types) {
      String[] parts = type.split("=");
      Pair<String, String> tmp = new Pair<>(parts[0], parts[1]);
      mediaTypes.add(tmp);
    }
    for(Pair<String, String> pair : mediaTypes) {
      if(pair.first.equals("charset")) {
        charset = Charset.forName(pair.second);
      }
    }
  }
  public Message(String[] types, String extension, String data) {
    this.extension = extension;
    for(String type : types) {
      String[] parts = type.split("=");
      Pair<String, String> tmp = new Pair<>(parts[0], parts[1]);
      mediaTypes.add(tmp);
    }
    for(Pair<String, String> pair : mediaTypes) {
      if(pair.first.equals("charset")) {
        charset = Charset.forName(pair.second);
      }
    }
    this.data = data.getBytes(charset);
  }

  public Message(String data) throws UnsupportedEncodingException {
    this(new String[]{"text/plain"}, "base64", data);
  }

  @Override public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("data:");
    for(Pair<String, String> pair : mediaTypes) {
      sb.append(pair.first);
      if(!pair.second.equals("")) {
        sb.append("=").append(pair.second);
      }
      sb.append(";");
    }
    sb.append(extension).append(",");
    if(extension.equals("base64")) {
      sb.append(Base64.encodeToString(data, Base64.DEFAULT));
    } else {
      sb.append(new String(data, charset));
    }
    return sb.toString();
  }

  public void setMediaType(String type) {
    for(Pair<String, String> pair : mediaTypes) {
      if(pair.first.contains("/") && pair.second.equals("")) {
        mediaTypes.remove(pair);
        mediaTypes.add(new Pair<>(type, ""));
      }
    }
    if(!mediaTypes.contains(new Pair<>(type, ""))) {
      mediaTypes.add(new Pair<>(type, ""));
    }
  }

  public String getMediaType() {
    for(Pair<String, String> pair : mediaTypes) {
      if(pair.first.contains("/") && pair.second.equals("")) {
        return pair.first;
      }
    }
    return "";
  }

  public void addMediaType(String a, String b) {
    if(!mediaTypes.contains(new Pair<>(a, b))) {
      mediaTypes.add(new Pair<>(a, b));
    }
  }

  public void removeMediaType(String a, String b) {
    if(mediaTypes.contains(new Pair<>(a, b))) {
      mediaTypes.remove(new Pair<>(a, b));
    }
  }

  public void setExtension(String newExtension) {
    extension = newExtension;
  }

  public String getExtension() {
    return extension;
  }

  public void setCharset(String name) {
    try {
      charset = Charset.forName(name);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Charset getCharset() {
    return charset;
  }

  public void setData(String newData) {
    data = newData.getBytes(charset);
  }

  public String getData() {
    return new String(data, charset);
  }

  public static Message parse(String input) {
    int semiLastIndex = input.lastIndexOf(';');
    String[] mediaTypes = input.substring(0, semiLastIndex-1).split(";");
    String[] dataRaw = input.substring(semiLastIndex+1, input.length()).split(",");
    return new Message(mediaTypes, dataRaw[0], Base64.decode(dataRaw[1], Base64.DEFAULT));
  }
}
