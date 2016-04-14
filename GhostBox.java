package chatbot;

import java.lang.StringBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.api.EventDispatcher;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.MentionEvent;
import sx.blah.discord.handle.impl.events.MessageAcknowledgedEvent;
import sx.blah.discord.handle.impl.events.MessageSendEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.ChannelCreateEvent;
import sx.blah.discord.handle.impl.events.ChannelDeleteEvent;
import sx.blah.discord.api.IListener;
import sx.blah.discord.handle.obj.IChannel; 
import sx.blah.discord.handle.obj.IMessage; 
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.util.HTTP429Exception; 
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MessageList;
import sx.blah.discord.util.MissingPermissionsException;





public class GhostBox {
  
  public static IDiscordClient client;
  public static GhostBox INSTANCE;
  public static final String tokenID = "MTY5NjY1OTQzMDA4NTc1NDg4.Ce9eFw.FML6FmXSlhnUD2HPmBvOAWDxlTE";
  public static final String BLACKLISTEDCHARS = "1234567890!@#$%^&*():;<>.,?/|-_=+`~[]{}";
  public int numOfMessages = 5;
  public int counter = 0;
  // A list of channels by IDs
  public ArrayList<ChannelController> channelList = new ArrayList<ChannelController>();
  
  GhostBox() {
  }
  
  public GhostBox login() {
    
    GhostBox bot = null;
    
    ClientBuilder clientBuilder = new ClientBuilder();
    clientBuilder.withToken(tokenID);
    
    try {
      client = clientBuilder.login();
    }
    catch (DiscordException e) {
      System.err.println("Error occurred while logging in!"); 
      e.printStackTrace(); 

    }
    
    return bot;
  }
  
  public void mainLoop() {
    
    client.getDispatcher().registerListener(new IListener<GuildCreateEvent>() {

    public void handle(GuildCreateEvent event) {
      for (int i = 0; i < event.getGuild().getChannels().size(); i++)
      {
        channelList.add(new ChannelController(event.getGuild().getChannels().get(i).getID()));
      }
      
  }
    
  });
    
    client.getDispatcher().registerListener(new IListener<ChannelCreateEvent>() {
      
      public void handle(ChannelCreateEvent event) {
          
          channelList.add(new ChannelController(event.getChannel().getID()));
        
    }
      
    });
    
    client.getDispatcher().registerListener(new IListener<ChannelDeleteEvent>() {
    
    public void handle(ChannelDeleteEvent event) { 
      
      for (int i = 0; i < channelList.size(); i++)
      {
        
        if (channelList.get(i).getID().matches(event.getChannel().getID())) {
          channelList.remove(i);
          return;
        }
      }
  } 
    
    
  });

    client.getDispatcher().registerListener(new IListener<MessageSendEvent>() {
      
      public void handle(MessageSendEvent event) {
        counter++;
        if (counter >= numOfMessages)
        {
          updatePreserve();
        }
      } 
      
      
    });
    
    client.getDispatcher().registerListener(new IListener<MentionEvent>() {
      
      public void handle(MentionEvent event) { 
        IMessage message = event.getMessage(); //Gets the message from the event object NOTE: This is not the content of the message, but the object itself 
        IChannel channel = message.getChannel(); //Gets the channel in which this message was sent. 
        try { 
            // Determine what the message is and do the appropriate action
            StringBuilder sb = new StringBuilder(message.getContent());
            
            // Trimming all non-letter characters
            
            for (int i = 0; i < sb.length(); i++)
            {
              for (int j = 0; j < BLACKLISTEDCHARS.length(); j++)
              {
                if (sb.charAt(i) == BLACKLISTEDCHARS.charAt(j))
                {
                  sb.deleteCharAt(i);
                }
              }
            }
            
            if (sb.toString().toLowerCase().contains("blinky"))
            {
              new MessageBuilder(GhostBox.client).withChannel(channel).withContent("No").build(); 
            }
            else if (sb.toString().toLowerCase().contains("pinky"))
            {
              String roleID = "no role";
              for (int i = 0; i < message.getGuild().getRoles().size(); i++)
              {
                if (message.getGuild().getRoles().get(i).getName().toLowerCase().contains("pinky"))
                {
                  roleID = message.getGuild().getRoles().get(i).getID();
                }
              }
              message.getAuthor().getClient().getGuildByID(message.getGuild().getID()).editUserRoles(message.getAuthor(), new IRole[]{message.getGuild().getRoleByID(roleID)});
              
              new MessageBuilder(GhostBox.client).withChannel(channel).withContent("You are now a Pinky").build(); 
            }
            else if (sb.toString().toLowerCase().contains("inky") && !sb.toString().toLowerCase().contains("blinky") && !sb.toString().toLowerCase().contains(" pinky"))
            {
              String roleID = "no role";
              for (int i = 0; i < message.getGuild().getRoles().size(); i++)
              {
                if (message.getGuild().getRoles().get(i).getName().toLowerCase().contains("inky") && !message.getGuild().getRoles().get(i).getName().toLowerCase().contains("blinky") && !message.getGuild().getRoles().get(i).getName().toLowerCase().contains("pinky"))
                {
                  roleID = message.getGuild().getRoles().get(i).getID();
                }
              }
              message.getAuthor().getClient().getGuildByID(message.getGuild().getID()).editUserRoles(message.getAuthor(), new IRole[]{message.getGuild().getRoleByID(roleID)});
            
              new MessageBuilder(GhostBox.client).withChannel(channel).withContent("You are now an Inky").build(); 
            }
            else if (sb.toString().toLowerCase().contains("clyde"))
            {
              String roleID = "no role";
              for (int i = 0; i < message.getGuild().getRoles().size(); i++)
              {
                if (message.getGuild().getRoles().get(i).getName().toLowerCase().contains("clyde"))
                {
                  roleID = message.getGuild().getRoles().get(i).getID();
                }
              }
              message.getAuthor().getClient().getGuildByID(message.getGuild().getID()).editUserRoles(message.getAuthor(), new IRole[]{message.getGuild().getRoleByID(roleID)});
            
              new MessageBuilder(GhostBox.client).withChannel(channel).withContent("You are now a Clyde").build(); 
            }
            
            // Marks channel as preserved, meaning that any new messages are deleted are a certain amount of time
            else if (sb.toString().toLowerCase().contains("preserve") && message.getAuthor().getName().matches("Splebel")) {
              
              preserve(channel);
              
            }
              
            else if (sb.toString().toLowerCase().contains("update") && message.getAuthor().getName().matches("Splebel")) { 
              updatePreserve();
              
            }
          
        } catch (HTTP429Exception e) { //HTTP429Exception thrown. The bot is sending messages too quickly! 
            System.err.print("Sending messages too quickly!"); 
            e.printStackTrace(); 
        } catch (DiscordException e) { //DiscordException thrown. Many possibilities. 
            System.err.print(e.getErrorMessage()); //Print the error message sent by Discord 
            e.printStackTrace(); 
        } catch (MissingPermissionsException e) { //MissingPermissionsException thrown. The bot doesn't have permission to send the message! 
            System.err.print("Missing permissions for channel!"); 
            e.printStackTrace(); 
        }
    }
      
      
    });
    
    
    /**client.getDispatcher().registerListener(new IListener<MessageSendEvent>() {
    
    public void handle(MessageSendEvent event) { 
      IMessage message = event.getMessage(); //Gets the message from the event object NOTE: This is not the content of the message, but the object itself 
      IChannel channel = message.getChannel(); //Gets the channel in which this message was sent. 
      try { 
          //Builds (sends) and new message in the channel that the original message was sent with the content of the original message. 
          new MessageBuilder(GhostBox.client).withChannel(channel).withContent("message sent").build(); 
      } catch (HTTP429Exception e) { //HTTP429Exception thrown. The bot is sending messages too quickly! 
          System.err.print("Sending messages too quickly!"); 
          e.printStackTrace(); 
      } catch (DiscordException e) { //DiscordException thrown. Many possibilities. 
          System.err.print(e.getErrorMessage()); //Print the error message sent by Discord 
          e.printStackTrace(); 
      } catch (MissingPermissionsException e) { //MissingPermissionsException thrown. The bot doesn't have permission to send the message! 
          System.err.print("Missing permissions for channel!"); 
          e.printStackTrace(); 
      }
  }
    
  }); **/


  }
  
 
  
  private void preserve(IChannel channel) {
    
    IMessage anchorMessage = channel.getMessages().getLatestMessage();
    
    try { 
      //Builds (sends) and new message in the channel that the original message was sent with the content of the original message.
      for (int i = 0; i < channelList.size(); i++)
      {
        if (channelList.get(i).getID().matches(channel.getID())) {
          
          channelList.get(i).setPreserved();
          channelList.get(i).setStoppingPoint(anchorMessage.getCreationDate());
          new MessageBuilder(GhostBox.client).withChannel(channel).withContent("Channel Preserved").build(); 
          return;
        }
      }
      
      new MessageBuilder(GhostBox.client).withChannel(channel).withContent("Channel could not be preserved").build(); 
  } catch (HTTP429Exception e) { //HTTP429Exception thrown. The bot is sending messages too quickly! 
      System.err.print("Sending messages too quickly!"); 
      e.printStackTrace(); 
  } catch (DiscordException e) { //DiscordException thrown. Many possibilities. 
      System.err.print(e.getErrorMessage()); //Print the error message sent by Discord 
      e.printStackTrace(); 
  } catch (MissingPermissionsException e) { //MissingPermissionsException thrown. The bot doesn't have permission to send the message! 
      System.err.print("Missing permissions for channel!"); 
      e.printStackTrace(); 
  }
    
  }
  
  public void updatePreserve() {
    for (int i = 0; i < channelList.size(); i++)
    {
      if (channelList.get(i).getPreserved() == true)
      {
        MessageList messages = client.getChannelByID(channelList.get(i).getID()).getMessages();
        for (int j = 0; j < messages.size(); j++)
        {
          if (messages.get(j).getCreationDate().isAfter(channelList.get(i).getStoppingPoint()))
          {
            try {
              messages.get(j).delete();
            } catch (MissingPermissionsException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            } catch (HTTP429Exception e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            } catch (DiscordException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
        }
      }
    }
  }
  
  

}
