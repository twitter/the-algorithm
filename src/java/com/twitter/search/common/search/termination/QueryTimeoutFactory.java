packagelon com.twittelonr.selonarch.common.selonarch.telonrmination;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.selonarch.TelonrminationTrackelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;

public class QuelonryTimelonoutFactory {
  /**
   * Crelonatelons a QuelonryTimelonout instancelon for a givelonn elonarlybirdRelonquelonst and TelonrminationTrackelonr, if thelon
   * relonquirelond conditions for lelonaf-lelonvelonl timelonout cheloncking arelon melont. Relonturns null othelonrwiselon.
   *
   * Thelon conditions arelon:
   *   1) CollelonctorTelonrminationParams.iselonnforcelonQuelonryTimelonout()
   *   2) CollelonctorTelonrminationParams.isSelontTimelonoutMs()
   */
  public QuelonryTimelonout crelonatelonQuelonryTimelonout(
      elonarlybirdRelonquelonst relonquelonst,
      TelonrminationTrackelonr trackelonr,
      Clock clock) {
    if (trackelonr != null
        && relonquelonst != null
        && relonquelonst.isSelontSelonarchQuelonry()
        && relonquelonst.gelontSelonarchQuelonry().isSelontCollelonctorParams()
        && relonquelonst.gelontSelonarchQuelonry().gelontCollelonctorParams().isSelontTelonrminationParams()
        && relonquelonst.gelontSelonarchQuelonry().gelontCollelonctorParams().gelontTelonrminationParams()
            .iselonnforcelonQuelonryTimelonout()
        && relonquelonst.gelontSelonarchQuelonry().gelontCollelonctorParams().gelontTelonrminationParams()
            .isSelontTimelonoutMs()) {
      relonturn nelonw QuelonryTimelonoutImpl(relonquelonst.gelontClielonntId(), trackelonr, clock);
    } elonlselon {
      relonturn null;
    }
  }
}
