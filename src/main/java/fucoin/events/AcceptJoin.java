package fucoin.events;

import fucoin.WalletPointer;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by dennish on 05/06/15.
 */
public class AcceptJoin implements Serializable {

  private final Vector<WalletPointer> walletPointers;

  public AcceptJoin(Vector<WalletPointer> walletPointers) {
    this.walletPointers = walletPointers;
  }

  public Vector<WalletPointer> getWalletPointers() {
    return walletPointers;
  }
}
