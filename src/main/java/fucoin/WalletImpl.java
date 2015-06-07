package fucoin;

import java.util.Vector;

public class WalletImpl implements Wallet, Comparable<Wallet>{
  private String address ;
  private int moneyAmount ;
  private Vector<WalletPointer> allKnownNeighbors;
  private Vector<Wallet> synchronizedNeighbors;

  public WalletImpl(String address) {
    this.address = address;
    this.allKnownNeighbors = new Vector<WalletPointer>();
    this.synchronizedNeighbors = new Vector<Wallet>();
  }

  @Override
  public Vector<WalletPointer> join(WalletPointer walletPointer) {
    allKnownNeighbors.add(walletPointer);
    return allKnownNeighbors;
  }

  @Override
  public Wallet searchWallet(String address) {
    for(Wallet wallet : synchronizedNeighbors) {
      if(wallet.getAddress().equals(address)) {
        return wallet;
      }
    }
    return null;
  }

  @Override
  public void storeOrUpdateWallet(Wallet w) {
    if (!synchronizedNeighbors.contains(w)){
      this.synchronizedNeighbors.add(w);
    } else {
      for (Wallet wallet : synchronizedNeighbors) {
        if (wallet.equals(w)) {
          wallet.update(w);
          return;
        }
      }
    }
  }

  @Override
  public String getAddress() {
    return this.address;
  }

  @Override
  public void update(Wallet w) {
    this.moneyAmount = w.getMoneyAmount();
    this.synchronizedNeighbors = w.getSynchronizedNeighbors();
    this.allKnownNeighbors = w.getAllKnownNeighbors();
  }

  @Override
  public void invalidateWallet(Wallet w) {

  }

  @Override
  public void receiveTransaction(int amount) {

  }

  public Vector<WalletPointer> getAllKnownNeighbors() {
    return allKnownNeighbors;
  }

  public int getMoneyAmount() {
    return moneyAmount;
  }

  public Vector<Wallet> getSynchronizedNeighbors() {
    return synchronizedNeighbors;
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

  @Override
  public int compareTo(Wallet o) {
    if (o.getAddress().equals(this.address)) {
      return 0;
    }
    return 1;
  }
}
