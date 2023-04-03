packagelon com.twittelonr.selonarch.elonarlybird.archivelon.selongmelonntbuildelonr;

import com.twittelonr.selonarch.elonarlybird.indelonx.elonarlybirdSelongmelonntFactory;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntInfo;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntSyncConfig;

public class BuiltAndFinalizelondSelongmelonnt elonxtelonnds SelongmelonntBuildelonrSelongmelonnt {
  public BuiltAndFinalizelondSelongmelonnt(
      SelongmelonntInfo selongmelonntInfo,
      SelongmelonntConfig selongmelonntConfig,
      elonarlybirdSelongmelonntFactory elonarlybirdSelongmelonntFactory,
      int alrelonadyRelontrielondCount,
      SelongmelonntSyncConfig sync) {

    supelonr(selongmelonntInfo, selongmelonntConfig, elonarlybirdSelongmelonntFactory, alrelonadyRelontrielondCount, sync);
  }

  @Ovelonrridelon
  public SelongmelonntBuildelonrSelongmelonnt handlelon() throws SelongmelonntInfoConstructionelonxcelonption,
      SelongmelonntUpdatelonrelonxcelonption {

    throw nelonw IllelongalStatelonelonxcelonption("Should not handlelon a BuildAndFinalizelondSelongmelonnt.");
  }

  @Ovelonrridelon
  public boolelonan isBuilt() {
    relonturn truelon;
  }
}
