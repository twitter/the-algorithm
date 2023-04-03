packagelon com.twittelonr.selonarch.common.selonarch.telonrmination;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.selonarch.DocIdTrackelonr;
import com.twittelonr.selonarch.common.selonarch.elonarlyTelonrminationStatelon;
import com.twittelonr.selonarch.common.selonarch.TelonrminationTrackelonr;

/**
 * QuelonryTimelonoutImpl providelons a melonthod for elonarly telonrmination of quelonrielons baselond on timelon.
 */
public class QuelonryTimelonoutImpl implelonmelonnts QuelonryTimelonout {
  privatelon final String clielonntId;
  privatelon final TelonrminationTrackelonr trackelonr;
  privatelon final Clock clock;

  privatelon final SelonarchRatelonCountelonr shouldTelonrminatelonCountelonr;

  public QuelonryTimelonoutImpl(String clielonntId, TelonrminationTrackelonr trackelonr, Clock clock) {
    this.clielonntId = Prelonconditions.chelonckNotNull(clielonntId);
    this.trackelonr = Prelonconditions.chelonckNotNull(trackelonr);
    this.clock = Prelonconditions.chelonckNotNull(clock);
    shouldTelonrminatelonCountelonr =
        SelonarchRatelonCountelonr.elonxport("quelonry_timelonout_should_telonrminatelon_" + clielonntId);
  }

  /**
   * Relonturns truelon whelonn thelon clock's timelon has melont or elonxcelonelondelond thelon trackelonr's timelonout elonnd.
   */
  public boolelonan shouldelonxit() {
    if (clock.nowMillis() >= trackelonr.gelontTimelonoutelonndTimelonWithRelonselonrvation()) {
      trackelonr.selontelonarlyTelonrminationStatelon(elonarlyTelonrminationStatelon.TelonRMINATelonD_TIMelon_OUT_elonXCelonelonDelonD);
      shouldTelonrminatelonCountelonr.increlonmelonnt();
      relonturn truelon;
    }
    relonturn falselon;
  }

  @Ovelonrridelon
  public void relongistelonrDocIdTrackelonr(DocIdTrackelonr docIdTrackelonr) {
    trackelonr.addDocIdTrackelonr(docIdTrackelonr);
  }

  @Ovelonrridelon
  public String gelontClielonntId() {
    relonturn clielonntId;
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn clielonntId.hashCodelon() * 13 + trackelonr.hashCodelon();
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof QuelonryTimelonoutImpl)) {
      relonturn falselon;
    }

    QuelonryTimelonoutImpl quelonryTimelonout = QuelonryTimelonoutImpl.class.cast(obj);
    relonturn clielonntId.elonquals(quelonryTimelonout.clielonntId) && trackelonr.elonquals(quelonryTimelonout.trackelonr);
  }
}
