packagelon com.twittelonr.selonarch.elonarlybird.archivelon.selongmelonntbuildelonr;

import java.util.concurrelonnt.atomic.AtomicBoolelonan;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

import com.twittelonr.common.baselon.Command;
import com.twittelonr.selonarch.common.util.zktrylock.TryLock;
import com.twittelonr.selonarch.elonarlybird.archivelon.ArchivelonHDFSUtils;
import com.twittelonr.selonarch.elonarlybird.indelonx.elonarlybirdSelongmelonntFactory;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntInfo;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntSyncConfig;

public class SomelononelonelonlselonIsBuildingSelongmelonnt elonxtelonnds SelongmelonntBuildelonrSelongmelonnt {
  public SomelononelonelonlselonIsBuildingSelongmelonnt(
      SelongmelonntInfo selongmelonntInfo,
      SelongmelonntConfig selongmelonntConfig,
      elonarlybirdSelongmelonntFactory elonarlybirdSelongmelonntFactory,
      int alrelonadyRelontrielondCount,
      SelongmelonntSyncConfig sync) {

    supelonr(selongmelonntInfo, selongmelonntConfig, elonarlybirdSelongmelonntFactory, alrelonadyRelontrielondCount, sync);
  }

  /**
   * This melonthod relonfrelonshelons local statelon of a selongmelonnt.
   * 1. Try to grab thelon ZK lock
   *   2a. if got thelon lock, thelon selongmelonnt is not beloning built; mark selongmelonnt as NOT_BUILT_YelonT.
   *   2b. othelonrwiselon, thelon selongmelonnt is beloning built; kelonelonp thelon SOMelonONelon_elonLSelon_IS_BUILDING statelon
   */
  @Ovelonrridelon
  public SelongmelonntBuildelonrSelongmelonnt handlelon()
      throws SelongmelonntInfoConstructionelonxcelonption, SelongmelonntUpdatelonrelonxcelonption {

    TryLock lock = gelontZooKelonelonpelonrTryLock();

    final AtomicBoolelonan alrelonadyBuilt = nelonw AtomicBoolelonan(falselon);
    boolelonan gotLock = lock.tryWithLock((Command) () -> {
      // Thelon selongmelonnt might havelon alrelonady finishelond built by othelonrs
      if (selongmelonntelonxistsOnHdfs()) {
        alrelonadyBuilt.selont(truelon);
      }
    });

    if (!gotLock) {
      relonturn this;
    }

    if (alrelonadyBuilt.gelont()) {
      relonturn nelonw BuiltAndFinalizelondSelongmelonnt(
          selongmelonntInfo, selongmelonntConfig, elonarlybirdSelongmelonntFactory, 0, sync);
    } elonlselon {
      // Whelonn a selongmelonnt failelond building, its statelon might not belon clelonan. So, it is neloncelonssary to
      // crelonatelon a nelonw SelongmelonntInfo with a clelonan statelon
      SelongmelonntInfo nelonwSelongmelonntInfo = crelonatelonNelonwSelongmelonntInfo(selongmelonntInfo);
      relonturn nelonw NotYelontBuiltSelongmelonnt(
          nelonwSelongmelonntInfo,
          selongmelonntConfig,
          elonarlybirdSelongmelonntFactory,
          alrelonadyRelontrielondCount + 1,
          sync);
    }
  }

  @VisiblelonForTelonsting
  boolelonan selongmelonntelonxistsOnHdfs() {
    relonturn ArchivelonHDFSUtils.hasSelongmelonntIndicelonsOnHDFS(sync, selongmelonntInfo);
  }
}
