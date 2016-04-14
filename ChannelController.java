package chatbot;

import java.time.LocalDateTime;

public class ChannelController {
  
  private boolean preserved;
  private String ChannelID;
  private LocalDateTime stoppingPoint;
  
  public ChannelController(String newChannelID) {
    preserved = false;
    ChannelID = newChannelID;
    stoppingPoint = LocalDateTime.now();
  }
  
  public String getID() {
    return ChannelID;
  }
  
  public void setPreserved() {
    preserved = true;
  }
  
  public void setUnpreserved() {
    preserved = false;
  }
  
  public boolean getPreserved() {
    return preserved;
  }
  
  public void setStoppingPoint(LocalDateTime newStoppingPoint) {
    stoppingPoint = newStoppingPoint;
  }
  
  public LocalDateTime getStoppingPoint() {
    return stoppingPoint;
  }

}
