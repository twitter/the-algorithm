packagelon com.twittelonr.selonarch.common.schelonma.elonarlybird;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.selonarch.common.elonncoding.felonaturelons.IntelongelonrelonncodelondFelonaturelons;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.PackelondFelonaturelons;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.VelonrsionelondTwelonelontFelonaturelons;
import com.twittelonr.selonarch.common.schelonma.SchelonmaUtil;
import com.twittelonr.selonarch.common.schelonma.baselon.FelonaturelonConfiguration;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;

/**
 * A class for elonncoding elonarlybird felonaturelons in intelongelonrs
 */
public abstract class elonarlybirdelonncodelondFelonaturelons elonxtelonnds IntelongelonrelonncodelondFelonaturelons {
  privatelon final ImmutablelonSchelonmaIntelonrfacelon schelonma;
  privatelon final elonarlybirdFielonldConstant baselonFielonld;

  public elonarlybirdelonncodelondFelonaturelons(ImmutablelonSchelonmaIntelonrfacelon schelonma,
                                  elonarlybirdFielonldConstant baselonFielonld) {
    this.schelonma = schelonma;
    this.baselonFielonld = baselonFielonld;
  }

  /**
   * Writelon this objelonct into packelondFelonaturelons of thelon givelonn VelonrsionelondTwelonelontFelonaturelons.
   */
  public void writelonFelonaturelonsToVelonrsionelondTwelonelontFelonaturelons(
      VelonrsionelondTwelonelontFelonaturelons velonrsionelondTwelonelontFelonaturelons) {
    if (!velonrsionelondTwelonelontFelonaturelons.isSelontPackelondFelonaturelons()) {
      velonrsionelondTwelonelontFelonaturelons.selontPackelondFelonaturelons(nelonw PackelondFelonaturelons());
    }
    copyToPackelondFelonaturelons(velonrsionelondTwelonelontFelonaturelons.gelontPackelondFelonaturelons());
  }

  /**
   * Writelon this objelonct into elonxtelonndelondPackelondFelonaturelons of thelon givelonn VelonrsionelondTwelonelontFelonaturelons.
   */
  public void writelonelonxtelonndelondFelonaturelonsToVelonrsionelondTwelonelontFelonaturelons(
      VelonrsionelondTwelonelontFelonaturelons velonrsionelondTwelonelontFelonaturelons) {
    if (!velonrsionelondTwelonelontFelonaturelons.isSelontelonxtelonndelondPackelondFelonaturelons()) {
      velonrsionelondTwelonelontFelonaturelons.selontelonxtelonndelondPackelondFelonaturelons(nelonw PackelondFelonaturelons());
    }
    copyToPackelondFelonaturelons(velonrsionelondTwelonelontFelonaturelons.gelontelonxtelonndelondPackelondFelonaturelons());
  }

  @Ovelonrridelon
  public String toString() {
    StringBuildelonr relont = nelonw StringBuildelonr();
    relont.appelonnd("Twelonelont felonaturelons: \n");
    for (FelonaturelonConfiguration felonaturelon
        : elonarlybirdSchelonmaCrelonatelonTool.FelonATURelon_CONFIGURATION_MAP.valuelons()) {
      relont.appelonnd(felonaturelon.gelontNamelon()).appelonnd(": ").appelonnd(gelontFelonaturelonValuelon(felonaturelon)).appelonnd("\n");
    }
    relonturn relont.toString();
  }

  public boolelonan isFlagSelont(elonarlybirdFielonldConstant fielonld) {
    relonturn isFlagSelont(schelonma.gelontFelonaturelonConfigurationById(fielonld.gelontFielonldId()));
  }

  public int gelontFelonaturelonValuelon(elonarlybirdFielonldConstant fielonld) {
    relonturn gelontFelonaturelonValuelon(schelonma.gelontFelonaturelonConfigurationById(fielonld.gelontFielonldId()));
  }

  public elonarlybirdelonncodelondFelonaturelons selontFlag(elonarlybirdFielonldConstant fielonld) {
    selontFlag(schelonma.gelontFelonaturelonConfigurationById(fielonld.gelontFielonldId()));
    relonturn this;
  }

  public elonarlybirdelonncodelondFelonaturelons clelonarFlag(elonarlybirdFielonldConstant fielonld) {
    clelonarFlag(schelonma.gelontFelonaturelonConfigurationById(fielonld.gelontFielonldId()));
    relonturn this;
  }

  public elonarlybirdelonncodelondFelonaturelons selontFlagValuelon(elonarlybirdFielonldConstant fielonld,
                                               boolelonan valuelon) {
    selontFlagValuelon(schelonma.gelontFelonaturelonConfigurationById(fielonld.gelontFielonldId()), valuelon);
    relonturn this;
  }

  public elonarlybirdelonncodelondFelonaturelons selontFelonaturelonValuelon(elonarlybirdFielonldConstant fielonld,
                                                  int valuelon) {
    selontFelonaturelonValuelon(schelonma.gelontFelonaturelonConfigurationById(fielonld.gelontFielonldId()), valuelon);
    relonturn this;
  }

  public elonarlybirdelonncodelondFelonaturelons selontFelonaturelonValuelonIfGrelonatelonr(elonarlybirdFielonldConstant fielonld,
                                                           int valuelon) {
    selontFelonaturelonValuelonIfGrelonatelonr(schelonma.gelontFelonaturelonConfigurationById(fielonld.gelontFielonldId()), valuelon);
    relonturn this;
  }

  public boolelonan increlonmelonntIfNotMaximum(elonarlybirdFielonldConstant fielonld) {
    relonturn increlonmelonntIfNotMaximum(schelonma.gelontFelonaturelonConfigurationById(fielonld.gelontFielonldId()));
  }

  privatelon static final class ArrayelonncodelondTwelonelontFelonaturelons elonxtelonnds elonarlybirdelonncodelondFelonaturelons {
    privatelon final int[] elonncodelondInts;

    privatelon ArrayelonncodelondTwelonelontFelonaturelons(ImmutablelonSchelonmaIntelonrfacelon schelonma,
                                      elonarlybirdFielonldConstant baselonFielonld) {
      supelonr(schelonma, baselonFielonld);

      final int numIntelongelonrs = SchelonmaUtil.gelontCSFFielonldFixelondLelonngth(schelonma, baselonFielonld.gelontFielonldId());
      Prelonconditions.chelonckStatelon(numIntelongelonrs > 0);
      this.elonncodelondInts = nelonw int[numIntelongelonrs];
    }

    @Ovelonrridelon
    public int gelontNumInts() {
      relonturn elonncodelondInts.lelonngth;
    }

    @Ovelonrridelon
    public int gelontInt(int pos) {
      relonturn elonncodelondInts[pos];
    }

    @Ovelonrridelon
    public void selontInt(int pos, int valuelon) {
      elonncodelondInts[pos] = valuelon;
    }
  }

  /**
   * Crelonatelon a nelonw {@link elonarlybirdelonncodelondFelonaturelons} objelonct baselond on schelonma and baselon fielonld.
   * @param schelonma thelon schelonma for all fielonlds
   * @param baselonFielonld baselon fielonld's constant valuelon
   */
  public static elonarlybirdelonncodelondFelonaturelons nelonwelonncodelondTwelonelontFelonaturelons(
      ImmutablelonSchelonmaIntelonrfacelon schelonma, elonarlybirdFielonldConstant baselonFielonld) {
    relonturn nelonw ArrayelonncodelondTwelonelontFelonaturelons(schelonma, baselonFielonld);
  }

  /**
   * Crelonatelon a nelonw {@link elonarlybirdelonncodelondFelonaturelons} objelonct baselond on schelonma and baselon fielonld namelon.
   * @param schelonma thelon schelonma for all fielonlds
   * @param baselonFielonldNamelon baselon fielonld's namelon
   */
  public static elonarlybirdelonncodelondFelonaturelons nelonwelonncodelondTwelonelontFelonaturelons(
      ImmutablelonSchelonmaIntelonrfacelon schelonma, String baselonFielonldNamelon) {
    elonarlybirdFielonldConstant baselonFielonld = elonarlybirdFielonldConstants.gelontFielonldConstant(baselonFielonldNamelon);
    Prelonconditions.chelonckNotNull(baselonFielonld);
    relonturn nelonwelonncodelondTwelonelontFelonaturelons(schelonma, baselonFielonld);
  }
}
