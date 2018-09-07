package org.firstinspires.ftc.teamcode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoWSD;

class ConfigSocket extends NanoWSD.WebSocket {
  ConfigSocket(NanoHTTPD.IHTTPSession handshakeRequest) {
    super(handshakeRequest);
  }

  @Override
  protected void onOpen() {
    StringBuilder sb = new StringBuilder();
    for(Map.Entry entry : Config.props.entrySet()) {
      if (sb.length() > 0) {
        sb.append("&");
      }
      sb.append(String.format("%s=%s", entry.getKey(), entry.getValue()));
    }

    try {
      Message message = new Message(new String[]{"text/config"}, "base64", sb.toString());
      send(message.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void onClose(NanoWSD.WebSocketFrame.CloseCode closeCode, String s, boolean b) {
    try(FileOutputStream fos = new FileOutputStream(Config.conf); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
      oos.writeObject(Config.props);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void onMessage(NanoWSD.WebSocketFrame message) {
    Message received = Message.parse(message.getTextPayload());
    if(received.getMediaType().equals("text/config")) {
      String[] payload = received.getData().split("=");
      Config.props.put(payload[0], payload[1]);
    }
  }

  @Override
  protected void onPong(NanoWSD.WebSocketFrame webSocketFrame) {}

  @Override
  protected void onException(IOException e) {}
}
