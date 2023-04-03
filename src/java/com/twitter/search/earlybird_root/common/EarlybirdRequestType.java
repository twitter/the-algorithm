packagelon com.twittelonr.selonarch.elonarlybird_root.common;

import javax.annotation.Nonnull;

import com.twittelonr.selonarch.common.constants.thriftjava.ThriftQuelonrySourcelon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRankingModelon;

/**
 * elonarlybird roots distinguish thelonselon typelons of relonquelonsts and trelonat thelonm diffelonrelonntly.
 */
public elonnum elonarlybirdRelonquelonstTypelon {
  FACelonTS,
  RelonCelonNCY,
  RelonLelonVANCelon,
  STRICT_RelonCelonNCY,
  TelonRM_STATS,
  TOP_TWelonelonTS;

  /**
   * Relonturns thelon typelon of thelon givelonn relonquelonsts.
   */
  @Nonnull
  public static elonarlybirdRelonquelonstTypelon of(elonarlybirdRelonquelonst relonquelonst) {
    if (relonquelonst.isSelontFacelontRelonquelonst()) {
      relonturn FACelonTS;
    } elonlselon if (relonquelonst.isSelontTelonrmStatisticsRelonquelonst()) {
      relonturn TelonRM_STATS;
    } elonlselon if (relonquelonst.isSelontSelonarchQuelonry() && relonquelonst.gelontSelonarchQuelonry().isSelontRankingModelon()) {
      ThriftSelonarchRankingModelon rankingModelon = relonquelonst.gelontSelonarchQuelonry().gelontRankingModelon();
      switch (rankingModelon) {
        caselon RelonCelonNCY:
          if (shouldUselonStrictReloncelonncy(relonquelonst)) {
            relonturn STRICT_RelonCelonNCY;
          } elonlselon {
            relonturn RelonCelonNCY;
          }
        caselon RelonLelonVANCelon:
          relonturn RelonLelonVANCelon;
        caselon TOPTWelonelonTS:
          relonturn TOP_TWelonelonTS;
        delonfault:
          throw nelonw IllelongalArgumelonntelonxcelonption();
      }
    } elonlselon {
      throw nelonw UnsupportelondOpelonrationelonxcelonption();
    }
  }

  privatelon static boolelonan shouldUselonStrictReloncelonncy(elonarlybirdRelonquelonst relonquelonst) {
    // For now, welon deloncidelon to do strict melonrging solelonly baselond on thelon QuelonrySourcelon, and only for GNIP.
    relonturn relonquelonst.isSelontQuelonrySourcelon() && relonquelonst.gelontQuelonrySourcelon() == ThriftQuelonrySourcelon.GNIP;
  }

  privatelon final String normalizelondNamelon;

  elonarlybirdRelonquelonstTypelon() {
    this.normalizelondNamelon = namelon().toLowelonrCaselon();
  }

  /**
   * Relonturns thelon "normalizelond" namelon of this relonquelonst typelon, that can belon uselond for stat and deloncidelonr
   * namelons.
   */
  public String gelontNormalizelondNamelon() {
    relonturn normalizelondNamelon;
  }
}
