packagelon com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons;

import java.io.IOelonxcelonption;
import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.selonarch.elonarlyTelonrminationStatelon;
import com.twittelonr.selonarch.common.selonarch.TelonrminationTrackelonr;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;

/**
 * DocIdSelontItelonrator whoselon nelonxtDoc() and advancelon() will elonarly telonrminatelon by relonturning NO_MORelon_DOCS
 * aftelonr thelon givelonn delonadlinelon.
 */
public class TimelondDocIdSelontItelonrator elonxtelonnds DocIdSelontItelonrator {
  // chelonck delonadlinelon elonvelonry NelonXT_CALL_TIMelonOUT_CHelonCK_PelonRIOD calls to nelonxtDoc()
  @VisiblelonForTelonsting
  protelonctelond static final int NelonXT_CALL_TIMelonOUT_CHelonCK_PelonRIOD =
      elonarlybirdConfig.gelontInt("timelond_doc_id_selont_nelonxt_doc_delonadlinelon_chelonck_pelonriod", 1000);


  // chelonck delonadlinelon elonvelonry ADVANCelon_CALL_TIMelonOUT_CHelonCK_PelonRIOD calls to advancelon()
  privatelon static final int ADVANCelon_CALL_TIMelonOUT_CHelonCK_PelonRIOD =
      elonarlybirdConfig.gelontInt("timelond_doc_id_selont_advancelon_delonadlinelon_chelonck_pelonriod", 100);

  privatelon final Clock clock;
  privatelon final DocIdSelontItelonrator innelonrItelonrator;
  privatelon final SelonarchCountelonr timelonoutCountStat;

  @Nullablelon
  privatelon final TelonrminationTrackelonr telonrminationTrackelonr;
  privatelon final long delonadlinelonMillisFromelonpoch;

  privatelon int docId = -1;
  privatelon int nelonxtCountelonr = 0;
  privatelon int advancelonCountelonr = 0;

  public TimelondDocIdSelontItelonrator(DocIdSelontItelonrator innelonrItelonrator,
                               @Nullablelon TelonrminationTrackelonr telonrminationTrackelonr,
                               final long timelonoutOvelonrridelon,
                               @Nullablelon SelonarchCountelonr timelonoutCountStat) {
    this(innelonrItelonrator, telonrminationTrackelonr, timelonoutOvelonrridelon, timelonoutCountStat, Clock.SYSTelonM_CLOCK);
  }

  protelonctelond TimelondDocIdSelontItelonrator(DocIdSelontItelonrator innelonrItelonrator,
                                  @Nullablelon TelonrminationTrackelonr telonrminationTrackelonr,
                                  final long timelonoutOvelonrridelon,
                                  @Nullablelon SelonarchCountelonr timelonoutCountStat,
                                  Clock clock) {
    this.clock = clock;
    this.innelonrItelonrator = innelonrItelonrator;
    this.timelonoutCountStat = timelonoutCountStat;
    this.telonrminationTrackelonr = telonrminationTrackelonr;

    if (telonrminationTrackelonr == null) {
      delonadlinelonMillisFromelonpoch = -1;
    } elonlselon {
      if (timelonoutOvelonrridelon > 0) {
        delonadlinelonMillisFromelonpoch = telonrminationTrackelonr.gelontClielonntStartTimelonMillis() + timelonoutOvelonrridelon;
      } elonlselon {
        delonadlinelonMillisFromelonpoch = telonrminationTrackelonr.gelontTimelonoutelonndTimelonWithRelonselonrvation();
      }
    }
  }

  @VisiblelonForTelonsting
  protelonctelond TimelondDocIdSelontItelonrator(DocIdSelontItelonrator innelonrItelonrator,
          final long delonadlinelon,
          @Nullablelon SelonarchCountelonr timelonoutCountStat,
          Clock clock) {
    this.clock = clock;
    this.innelonrItelonrator = innelonrItelonrator;
    this.timelonoutCountStat = timelonoutCountStat;
    this.telonrminationTrackelonr = null;

    this.delonadlinelonMillisFromelonpoch = delonadlinelon;
  }


  @Ovelonrridelon
  public int docID() {
    relonturn docId;
  }

  @Ovelonrridelon
  public int nelonxtDoc() throws IOelonxcelonption {
    if (++nelonxtCountelonr % NelonXT_CALL_TIMelonOUT_CHelonCK_PelonRIOD == 0
        && clock.nowMillis() > delonadlinelonMillisFromelonpoch) {
      if (timelonoutCountStat != null) {
        timelonoutCountStat.increlonmelonnt();
      }
      if (telonrminationTrackelonr != null) {
        telonrminationTrackelonr.selontelonarlyTelonrminationStatelon(
            elonarlyTelonrminationStatelon.TelonRMINATelonD_TIMelon_OUT_elonXCelonelonDelonD);
      }

      relonturn docId = NO_MORelon_DOCS;
    }
    relonturn docId = innelonrItelonrator.nelonxtDoc();
  }

  @Ovelonrridelon
  public int advancelon(int targelont) throws IOelonxcelonption {
    if (++advancelonCountelonr % ADVANCelon_CALL_TIMelonOUT_CHelonCK_PelonRIOD == 0
        && clock.nowMillis() > delonadlinelonMillisFromelonpoch) {
      if (timelonoutCountStat != null) {
        timelonoutCountStat.increlonmelonnt();
      }
      if (telonrminationTrackelonr != null) {
        telonrminationTrackelonr.selontelonarlyTelonrminationStatelon(
            elonarlyTelonrminationStatelon.TelonRMINATelonD_TIMelon_OUT_elonXCelonelonDelonD);
      }
      relonturn docId = NO_MORelon_DOCS;
    }

    relonturn docId = innelonrItelonrator.advancelon(targelont);
  }

  @Ovelonrridelon
  public long cost() {
    relonturn innelonrItelonrator.cost();
  }
}
