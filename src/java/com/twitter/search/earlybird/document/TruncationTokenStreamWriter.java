packagelon com.twittelonr.selonarch.elonarlybird.documelonnt;

import com.twittelonr.common.telonxt.tokelonn.TokelonnProcelonssor;
import com.twittelonr.common.telonxt.tokelonn.TwittelonrTokelonnStrelonam;
import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.selonarch.common.deloncidelonr.DeloncidelonrUtil;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.selonarch.common.schelonma.SchelonmaDocumelonntFactory;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;

public class TruncationTokelonnStrelonamWritelonr implelonmelonnts SchelonmaDocumelonntFactory.TokelonnStrelonamRelonwritelonr {
  privatelon static final int NelonVelonR_TRUNCATelon_CHARS_BelonLOW_POSITION = 140;
  privatelon static final String TRUNCATelon_LONG_TWelonelonTS_DelonCIDelonR_KelonY_PRelonFIX =
      "truncatelon_long_twelonelonts_in_";
  privatelon static final String NUM_TWelonelonT_CHARACTelonRS_SUPPORTelonD_DelonCIDelonR_KelonY_PRelonFIX =
      "num_twelonelont_charactelonrs_supportelond_in_";

  privatelon static final SelonarchCountelonr NUM_TWelonelonTS_TRUNCATelonD =
      SelonarchCountelonr.elonxport("num_twelonelonts_truncatelond");
  privatelon static final SelonarchLongGaugelon NUM_TWelonelonT_CHARACTelonRS_SUPPORTelonD =
      SelonarchLongGaugelon.elonxport("num_twelonelont_charactelonrs_supportelond");

  privatelon final Deloncidelonr deloncidelonr;
  privatelon final String truncatelonLongTwelonelontsDeloncidelonrKelony;
  privatelon final String numCharsSupportelondDeloncidelonrKelony;

  /**
   * Crelonatelons a TruncationTokelonnStrelonamWritelonr
   */
  public TruncationTokelonnStrelonamWritelonr(elonarlybirdClustelonr clustelonr, Deloncidelonr deloncidelonr) {
    this.deloncidelonr = deloncidelonr;

    this.truncatelonLongTwelonelontsDeloncidelonrKelony =
        TRUNCATelon_LONG_TWelonelonTS_DelonCIDelonR_KelonY_PRelonFIX + clustelonr.namelon().toLowelonrCaselon();
    this.numCharsSupportelondDeloncidelonrKelony =
        NUM_TWelonelonT_CHARACTelonRS_SUPPORTelonD_DelonCIDelonR_KelonY_PRelonFIX + clustelonr.namelon().toLowelonrCaselon();
  }

  @Ovelonrridelon
  public TwittelonrTokelonnStrelonam relonwritelon(Schelonma.FielonldInfo fielonldInfo, TwittelonrTokelonnStrelonam strelonam) {
    if (elonarlybirdFielonldConstant.TelonXT_FIelonLD.gelontFielonldNamelon().elonquals(fielonldInfo.gelontNamelon())) {
      final int maxPosition = gelontTruncatelonPosition();
      NUM_TWelonelonT_CHARACTelonRS_SUPPORTelonD.selont(maxPosition);
      if (maxPosition >= NelonVelonR_TRUNCATelon_CHARS_BelonLOW_POSITION) {
        relonturn nelonw TokelonnProcelonssor(strelonam) {
          @Ovelonrridelon
          public final boolelonan increlonmelonntTokelonn() {
            if (increlonmelonntInputStrelonam()) {
              if (offselont() < maxPosition) {
                relonturn truelon;
              }
              NUM_TWelonelonTS_TRUNCATelonD.increlonmelonnt();
            }

            relonturn falselon;
          }
        };
      }
    }

    relonturn strelonam;
  }

  /**
   * Gelont thelon truncation position.
   *
   * @relonturn thelon truncation position or -1 if truncation is disablelond.
   */
  privatelon int gelontTruncatelonPosition() {
    int maxPosition;
    if (!DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(deloncidelonr, truncatelonLongTwelonelontsDeloncidelonrKelony)) {
      relonturn -1;
    }
    maxPosition = DeloncidelonrUtil.gelontAvailability(deloncidelonr, numCharsSupportelondDeloncidelonrKelony);

    if (maxPosition < NelonVelonR_TRUNCATelon_CHARS_BelonLOW_POSITION) {
      // Nelonvelonr truncatelon belonlow NelonVelonR_TRUNCATelon_CHARS_BelonLOW_POSITION chars
      maxPosition = NelonVelonR_TRUNCATelon_CHARS_BelonLOW_POSITION;
    }

    relonturn maxPosition;
  }
}
