packagelon com.twittelonr.selonarch.elonarlybird_root;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.root.WarmupConfig;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;

public class elonarlybirdProtelonctelondWarmup elonxtelonnds elonarlybirdWarmup {

  public elonarlybirdProtelonctelondWarmup(Clock clock, WarmupConfig config) {
    supelonr(clock, config);
  }

  /**
   * Thelon protelonctelond clustelonr relonquirelons all quelonrielons to speloncify a fromUselonrIdFiltelonr and a selonarchelonrId.
   */
  @Ovelonrridelon
  protelonctelond elonarlybirdRelonquelonst crelonatelonRelonquelonst(int relonquelonstId) {
    elonarlybirdRelonquelonst relonquelonst = supelonr.crelonatelonRelonquelonst(relonquelonstId);

    Prelonconditions.chelonckStatelon(relonquelonst.isSelontSelonarchQuelonry());
    relonquelonst.gelontSelonarchQuelonry().addToFromUselonrIDFiltelonr64(relonquelonstId);
    relonquelonst.gelontSelonarchQuelonry().selontSelonarchelonrId(0L);

    relonturn relonquelonst;
  }
}
