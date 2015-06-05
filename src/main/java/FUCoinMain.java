import akka.actor.ActorSystem;
import akka.actor.Props;
import fucoin.Node;

public class FUCoinMain {
  public static void main(String[] args) {
    System.out.println("Hallo Fu Coiner");
    ActorSystem actorSystem = ActorSystem.create("FUcoin");
    Props props = new Props(Node.class);
    actorSystem.actorFor(props);
  }
}
