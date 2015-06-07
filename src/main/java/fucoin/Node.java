package fucoin;

import akka.actor.UntypedActor;
import fucoin.events.AcceptJoin;
import fucoin.events.FoundWallet;
import fucoin.events.SyncWallet;

import java.util.Vector;

public class Node extends UntypedActor {

  private Wallet wallet;
  private int numberOfSearchRequest;

  public Node(String address) {
    wallet = new WalletImpl(address);
  }

  @Override
  public void preStart() {
  }

  @Override
  public void onReceive(Object message) {
    if (message.equals("join")) {
      handleJoinRequest();
    }
    if (message instanceof AcceptJoin) {
      handleAcceptJoinRequest((AcceptJoin) message);
    }
    if (message.equals("searchMe")) {
      Wallet foundWallet = wallet.searchWallet(getSender().path().toString());
      FoundWallet fw = new FoundWallet(foundWallet);
      getSender().tell(fw, getSelf());
    }
    if (message instanceof FoundWallet) {
      FoundWallet foundWallet = (FoundWallet) message;
      if (numberOfSearchRequest > 0) {
        if (foundWallet.getWallet() == null) {
          numberOfSearchRequest--;
          if (numberOfSearchRequest == 0) {
            synchronizeOwnWalletToAllNodes();
          }
        } else {
          numberOfSearchRequest = 0;
          wallet = foundWallet.getWallet();
          synchronizeOwnWalletToAllNodes();
        }
      }
    }
    if (message instanceof SyncWallet) {
      Wallet toSyncWallet = ((SyncWallet) message).getWallet();
      wallet.storeOrUpdateWallet(toSyncWallet);
    }
  }

  private void synchronizeOwnWalletToAllNodes() {
    notifyAll(new SyncWallet(this.wallet));
  }

  private void handleJoinRequest() {
    String acceptedAddress = getSender().path().toString();
    WalletPointer walletPointer = new WalletPointer(acceptedAddress);
    AcceptJoin acceptJoin = new AcceptJoin(wallet.join(walletPointer), acceptedAddress);
    getSender().tell(acceptJoin, getSelf());
  }

  private void handleAcceptJoinRequest(AcceptJoin acceptJoin) {
    initKnownNodes(acceptJoin);
    searchOwnWallInNetwork();
  }

  private void initKnownNodes(AcceptJoin acceptJoin) {
    String ownAddressInNetwork = acceptJoin.getAcceptedAddress();
    wallet = new WalletImpl(ownAddressInNetwork);
    System.out.println("Join accept");
    Vector<WalletPointer> walletPointers = acceptJoin.getWalletPointers();
    wallet.initKnownNodes(walletPointers);
  }

  private void searchOwnWallInNetwork() {
    this.numberOfSearchRequest = wallet.getAllKnownNeighbors().size();
    notifyAll("searchMe");
  }

  private void notifyAll(Object event) {
    String address;
    for (WalletPointer walletPointer : wallet.getAllKnownNeighbors()) {
      address = walletPointer.getAddress();
      getContext().actorFor(address).tell(event, getSelf());
    }
  }
}
