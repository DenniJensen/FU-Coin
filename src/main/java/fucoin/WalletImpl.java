package fucoin;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import fucoin.events.AcceptJoin;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by dennish on 05/06/15.
 */
public class WalletImpl implements Wallet {
  private String address ;
  private ActorRef ownRef;
  private int moneyAmount ;
  private Vector<WalletPointer> allKnownNeighbors ;
  private Vector<ActorRef> allKnownRefs;
  private Vector<Wallet> synchronizedNeighbors;

  public WalletImpl() {
    allKnownNeighbors = new Vector<WalletPointer>();
    synchronizedNeighbors = new Vector<Wallet>();
  }


  @Override
  public Vector<WalletPointer> join(WalletPointer walletPointer) {
    allKnownNeighbors.add(walletPointer);
    return allKnownNeighbors;
  }

  @Override
  public Vector<WalletPointer> searchWallet(String address) {
    return null;
  }

  @Override
  public void storeOrUpdateWallet(Wallet w) {

  }

  @Override
  public void invalidateWallet(Wallet w) {

  }

  @Override
  public void receiveTransaction(int amount) {

  }

  private void leave (){

  }

  private void performTransaction(WalletPointer w, int amount){

  }


  public void initKnownNodes(Vector<WalletPointer> walletPointers) {
    allKnownNeighbors = walletPointers;
    for (WalletPointer walletPointer : allKnownNeighbors) {
      if (walletPointer.getAddress().equals(address)) {
        walletPointers.remove(walletPointer);
        return;
      }
    }
  }
}
