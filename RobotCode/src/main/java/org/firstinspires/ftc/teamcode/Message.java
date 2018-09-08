package org.firstinspires.ftc.teamcode;

import android.util.Base64;
import android.util.Pair;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

//implementation of DataURI scheme
public class Message {
  String type = "";
  String extension = "";
  byte[] data = new byte[]{};
  Charset charset = Charset.forName("UTF-8");

  public Message(){}
  public Message(String type, String extension, byte[] data) {
    this.extension = extension;
    this.data = data;
    this.type = type;
  }
  public Message(String type, String extension, String data) {
    this.extension = extension;
    this.type = type;
    this.data = data.getBytes(charset);
  }

  public Message(String data) {
    this("text/plain", "base64", data);
  }

  @Override public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(type).append(";");
    sb.append(extension).append(",");
    if(extension.equals("base64")) {
      sb.append(Base64.encodeToString(data, Base64.DEFAULT));
    } else {
      sb.append(new String(data, charset));
    }
    return sb.toString();
  }

  public void setMediaType(String type) {
    if(type.contains("/")) this.type = type;
  }

  public String getMediaType() {
    return type;
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
    String[] part1 = input.split(";");
    String[] part2 = part1[1].split(",");
    String extension = part2[0];
    if(extension.equals("base64")) {
      return new Message(part1[0], extension, Base64.decode(part2[1], Base64.DEFAULT));
    }
    return new Message(part1[0], extension, part2[1]);
  }
}
