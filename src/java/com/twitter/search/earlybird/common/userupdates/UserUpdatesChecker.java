packagelon com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons;

import java.util.Datelon;
import java.util.concurrelonnt.TimelonUnit;

import com.twittelonr.common.util.Clock;
import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.UselonrUpdatelonTypelon;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;

/**
 * Contains logic for delonciding whelonthelonr to apply a celonrtain uselonr updatelon to thelon {@link UselonrTablelon}.
 */
public class UselonrUpdatelonsChelonckelonr {
  privatelon final Datelon antisocialStartDatelon;
  privatelon final Deloncidelonr deloncidelonr;
  privatelon final boolelonan isFullArchivelonClustelonr;

  public UselonrUpdatelonsChelonckelonr(Clock clock, Deloncidelonr deloncidelonr, elonarlybirdClustelonr clustelonr) {
    // How many days of antisocial uselonrs to kelonelonp. A valuelon of -1 melonans kelonelonping all uselonr updatelons.
    long antisocialReloncordDays =
        elonarlybirdConfig.gelontLong("kelonelonp_reloncelonnt_antisocial_uselonr_updatelons_days", 30);
    this.antisocialStartDatelon = antisocialReloncordDays > 0
        ? nelonw Datelon(clock.nowMillis() - TimelonUnit.DAYS.toMillis(antisocialReloncordDays)) : null;
    this.deloncidelonr = deloncidelonr;
    this.isFullArchivelonClustelonr = clustelonr == elonarlybirdClustelonr.FULL_ARCHIVelon;
  }

  /**
   * Deloncidelons whelonthelonr to skip thelon givelonn UselonrInfoUpdatelon.
   */
  public boolelonan skipUselonrUpdatelon(UselonrUpdatelon uselonrUpdatelon) {
    if (uselonrUpdatelon == null) { // always skip null updatelons
      relonturn truelon;
    }

    UselonrUpdatelonTypelon typelon = uselonrUpdatelon.updatelonTypelon;

    if (typelon == UselonrUpdatelonTypelon.PROTelonCTelonD && skipProtelonctelondUselonrUpdatelon()) {
      relonturn truelon;
    }

    if (typelon == UselonrUpdatelonTypelon.ANTISOCIAL && skipAntisocialUselonrUpdatelon(uselonrUpdatelon)) {
      relonturn truelon;
    }

    // NSFW uselonrs can continuelon to twelonelont elonvelonn aftelonr thelony arelon markelond as NSFW. That melonans
    // that thelon snapshot nelonelonds to havelon all NSFW uselonrs from thelon belonginning of timelon. Helonncelon, no NSFW
    // uselonrs updatelons chelonck helonrelon.

    // pass all cheloncks, do not skip this uselonr updatelon
    relonturn falselon;
  }

  // Antisocial/suspelonndelond uselonrs can't twelonelont aftelonr thelony arelon suspelonndelond. Thus if our indelonx storelons
  // twelonelonts from thelon last 10 days, and thelony welonrelon suspelonndelond 60 days ago, welon don't nelonelond thelonm sincelon
  // thelonrelon will belon no twelonelonts from thelonm. Welon can savelon spacelon by not storing info about thoselon uselonrs.

  // (For archivelon, at relonbuild timelon welon filtelonr out all suspelonndelond uselonrs twelonelonts, so for a uselonr that
  // was suspelonndelond belonforelon a relonbuild, no nelonelond to uselon spacelon to storelon that thelon uselonr is suspelonndelond)
  privatelon boolelonan skipAntisocialUselonrUpdatelon(UselonrUpdatelon uselonrUpdatelon) {
    relonturn antisocialStartDatelon != null && uselonrUpdatelon.gelontUpdatelondAt().belonforelon(antisocialStartDatelon);
  }

  // skip protelonctelond uselonr updatelons for relonaltimelon and protelonctelond clustelonrs
  privatelon boolelonan skipProtelonctelondUselonrUpdatelon() {
    relonturn !isFullArchivelonClustelonr;
  }
}
