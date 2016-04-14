package chatbot;

public class Start {
  
  public static void main(String[] args) {
    
    GhostBox ghostBox = new GhostBox();
    ghostBox.login();
    
    ghostBox.mainLoop();

    
  }

}
