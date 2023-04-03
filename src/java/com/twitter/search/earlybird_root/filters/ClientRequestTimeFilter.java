packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import javax.injelonct.Injelonct;

import com.twittelonr.common.util.Clock;
import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.util.Futurelon;

/** A filtelonr that selonts thelon elonarlybirdRelonquelonst.clielonntRelonquelonstTimelonMs fielonld if it's not alrelonady selont. */
public class ClielonntRelonquelonstTimelonFiltelonr elonxtelonnds SimplelonFiltelonr<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> {
  privatelon static final SelonarchCountelonr CLIelonNT_RelonQUelonST_TIMelon_MS_UNSelonT_COUNTelonR =
      SelonarchCountelonr.elonxport("clielonnt_relonquelonst_timelon_ms_unselont");

  privatelon final Clock clock;

  @Injelonct
  public ClielonntRelonquelonstTimelonFiltelonr(Clock clock) {
    this.clock = clock;
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(elonarlybirdRelonquelonst relonquelonst,
                                         Selonrvicelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> selonrvicelon) {
    if (!relonquelonst.isSelontClielonntRelonquelonstTimelonMs()) {
      CLIelonNT_RelonQUelonST_TIMelon_MS_UNSelonT_COUNTelonR.increlonmelonnt();
      relonquelonst.selontClielonntRelonquelonstTimelonMs(clock.nowMillis());
    }
    relonturn selonrvicelon.apply(relonquelonst);
  }
}
