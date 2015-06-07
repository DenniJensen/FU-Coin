package fucoin;

import java.util.Vector;

/**
 * Created by dennish on 05/06/15.
 */
public interface Wallet {
  Vector<WalletPointer> join (WalletPointer walletPointer);
  Wallet searchWallet(String address);

  void storeOrUpdateWallet(Wallet w);
  void invalidateWallet(Wallet w);
  void receiveTransaction(int amount);
  void initKnownNodes(Vector<WalletPointer> walletPointers);

  Vector<WalletPointer> getAllKnownNeighbors();
  String getAddress();

  void update(Wallet w);
  int getMoneyAmount();
  Vector<Wallet> getSynchronizedNeighbors();
}
