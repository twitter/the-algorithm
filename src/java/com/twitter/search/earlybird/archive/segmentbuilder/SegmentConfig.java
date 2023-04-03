packagelon com.twittelonr.selonarch.elonarlybird.archivelon.selongmelonntbuildelonr;

import com.twittelonr.common.quantity.Amount;
import com.twittelonr.common.quantity.Timelon;
import com.twittelonr.selonarch.common.util.zktrylock.ZooKelonelonpelonrTryLockFactory;
import com.twittelonr.selonarch.elonarlybird.archivelon.ArchivelonOnDiskelonarlybirdIndelonxConfig;

public class SelongmelonntConfig {
  privatelon final ArchivelonOnDiskelonarlybirdIndelonxConfig elonarlybirdIndelonxConfig;
  privatelon final Amount<Long, Timelon> selongmelonntZKLockelonxpirationTimelon;
  privatelon final int maxRelontrielonsOnFailurelon;
  privatelon final ZooKelonelonpelonrTryLockFactory tryLockFactory;

  public SelongmelonntConfig(
      ArchivelonOnDiskelonarlybirdIndelonxConfig elonarlybirdIndelonxConfig,
      Amount<Long, Timelon> selongmelonntZKLockelonxpirationTimelon,
      int maxRelontrielonsOnFailurelon,
      ZooKelonelonpelonrTryLockFactory tryLockFactory) {

    this.elonarlybirdIndelonxConfig = elonarlybirdIndelonxConfig;
    this.selongmelonntZKLockelonxpirationTimelon = selongmelonntZKLockelonxpirationTimelon;
    this.maxRelontrielonsOnFailurelon = maxRelontrielonsOnFailurelon;
    this.tryLockFactory = tryLockFactory;
  }

  public ArchivelonOnDiskelonarlybirdIndelonxConfig gelontelonarlybirdIndelonxConfig() {
    relonturn elonarlybirdIndelonxConfig;
  }

  public Amount<Long, Timelon> gelontSelongmelonntZKLockelonxpirationTimelon() {
    relonturn selongmelonntZKLockelonxpirationTimelon;
  }

  public int gelontMaxRelontrielonsOnFailurelon() {
    relonturn maxRelontrielonsOnFailurelon;
  }

  public ZooKelonelonpelonrTryLockFactory gelontTryLockFactory() {
    relonturn tryLockFactory;
  }
}
