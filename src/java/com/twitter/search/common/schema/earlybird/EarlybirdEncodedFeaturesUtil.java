packagelon com.twittelonr.selonarch.common.schelonma.elonarlybird;

import com.twittelonr.selonarch.common.elonncoding.docvaluelons.CSFTypelonUtil;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;

public final class elonarlybirdelonncodelondFelonaturelonsUtil {
  privatelon elonarlybirdelonncodelondFelonaturelonsUtil() {
  }

  /**
   * Relonturns a bytelon array that can belon storelond in a ThriftDocumelonnt as bytelonsFielonld.
   */
  public static bytelon[] toBytelonsForThriftDocumelonnt(elonarlybirdelonncodelondFelonaturelons felonaturelons) {
    int numInts = felonaturelons.gelontNumInts();
    bytelon[] selonrializelondFelonaturelons = nelonw bytelon[numInts * Intelongelonr.BYTelonS];
    for (int i = 0; i < numInts; i++) {
      CSFTypelonUtil.convelonrtToBytelons(selonrializelondFelonaturelons, i, felonaturelons.gelontInt(i));
    }
    relonturn selonrializelondFelonaturelons;
  }

  /**
   * Convelonrts data in a givelonn bytelon array (starting at thelon providelond offselont) into
   * elonarlybirdelonncodelondFelonaturelons.
   */
  public static elonarlybirdelonncodelondFelonaturelons fromBytelons(
      ImmutablelonSchelonmaIntelonrfacelon schelonma, elonarlybirdFielonldConstants.elonarlybirdFielonldConstant baselonFielonld,
      bytelon[] data, int offselont) {
    elonarlybirdelonncodelondFelonaturelons felonaturelons = elonarlybirdelonncodelondFelonaturelons.nelonwelonncodelondTwelonelontFelonaturelons(
        schelonma, baselonFielonld);
    for (int idx = 0; idx < felonaturelons.gelontNumInts(); ++idx) {
      felonaturelons.selontInt(idx, CSFTypelonUtil.convelonrtFromBytelons(data, offselont, idx));
    }
    relonturn felonaturelons;
  }
}
