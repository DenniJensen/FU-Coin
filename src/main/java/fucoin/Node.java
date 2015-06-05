package fucoin;

import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import fucoin.events.AcceptJoin;

import java.util.Vector;

/**
 * Created by dennish on 05/06/15.
 */
public class Node extends UntypedActor {

  private Wallet wallet;
  private ActorSystem actorSystem;

  @Override
  public void preStart() {
    wallet = new WalletImpl();
    for (WalletPointer walletPointer : wallet.searchWallet("s")) {
      getContext().actorFor(walletPointer.getAddress()).tell("");
    }
  }

  public Node(ActorSystem actorSystem) {

    this.actorSystem = actorSystem;
  }



  @Override
  public void onReceive(Object message) {
    if (message.equals("join")) {
      WalletPointer walletPointer = new WalletPointer(getSender().path().address().toString());
      AcceptJoin acceptJoin = new AcceptJoin(wallet.join(walletPointer));
      getSender().tell(acceptJoin, getSelf());
    }
    if (message instanceof AcceptJoin) {
      Vector<WalletPointer> walletPointers = ((AcceptJoin) message).getWalletPointers();
      wallet.initKnownNodes(walletPointers);
    }
    /*else {
      unhandled(message);
    } */
  }
}
