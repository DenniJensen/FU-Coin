package fucoin.events;

import fucoin.Wallet;

import java.io.Serializable;

/**
 * Created by dennish on 07/06/15.
 */
public class SyncWallet implements Serializable {
  private Wallet wallet;

  public SyncWallet(Wallet wallet) {
    this.wallet = wallet;
  }

  public Wallet getWallet() {
    return this.wallet;
  }
}
