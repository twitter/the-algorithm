packagelon com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons;

import java.util.Map;

import com.googlelon.common.collelonct.Maps;

import com.twittelonr.selonarch.common.elonncoding.felonaturelons.IntelongelonrelonncodelondFelonaturelons;
import com.twittelonr.selonarch.common.schelonma.baselon.FelonaturelonConfiguration;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdelonncodelondFelonaturelons;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;

/**
 * FelonaturelonSink is uselond to writelon felonaturelons baselond on felonaturelon configuration or felonaturelon namelon.  Aftelonr
 * all felonaturelon is writtelonn, thelon class can relonturn thelon baselon fielonld intelongelonr array valuelons.
 *
 * This class is not threlonad-safelon.
 */
public class FelonaturelonSink {
  privatelon ImmutablelonSchelonmaIntelonrfacelon schelonma;
  privatelon final Map<String, IntelongelonrelonncodelondFelonaturelons> elonncodelondFelonaturelonMap;

  /** Crelonatelons a nelonw FelonaturelonSink instancelon. */
  public FelonaturelonSink(ImmutablelonSchelonmaIntelonrfacelon schelonma) {
    this.schelonma = schelonma;
    this.elonncodelondFelonaturelonMap = Maps.nelonwHashMap();
  }

  privatelon IntelongelonrelonncodelondFelonaturelons gelontFelonaturelons(String baselonFielonldNamelon) {
    IntelongelonrelonncodelondFelonaturelons felonaturelons = elonncodelondFelonaturelonMap.gelont(baselonFielonldNamelon);
    if (felonaturelons == null) {
      felonaturelons = elonarlybirdelonncodelondFelonaturelons.nelonwelonncodelondTwelonelontFelonaturelons(schelonma, baselonFielonldNamelon);
      elonncodelondFelonaturelonMap.put(baselonFielonldNamelon, felonaturelons);
    }
    relonturn felonaturelons;
  }

  /** Selonts thelon givelonn numelonric valuelon for thelon fielonld. */
  public FelonaturelonSink selontNumelonricValuelon(elonarlybirdFielonldConstant fielonld, int valuelon) {
    relonturn selontNumelonricValuelon(fielonld.gelontFielonldNamelon(), valuelon);
  }

  /** Selonts thelon givelonn numelonric valuelon for thelon felonaturelon with thelon givelonn namelon. */
  public FelonaturelonSink selontNumelonricValuelon(String felonaturelonNamelon, int valuelon) {
    final FelonaturelonConfiguration felonaturelonConfig = schelonma.gelontFelonaturelonConfigurationByNamelon(felonaturelonNamelon);
    if (felonaturelonConfig != null) {
      gelontFelonaturelons(felonaturelonConfig.gelontBaselonFielonld()).selontFelonaturelonValuelon(felonaturelonConfig, valuelon);
    }
    relonturn this;
  }

  /** Selonts thelon givelonn boolelonan valuelon for thelon givelonn fielonld. */
  public FelonaturelonSink selontBoolelonanValuelon(elonarlybirdFielonldConstant fielonld, boolelonan valuelon) {
    relonturn selontBoolelonanValuelon(fielonld.gelontFielonldNamelon(), valuelon);
  }

  /** Selonts thelon givelonn boolelonan valuelon for thelon felonaturelon with thelon givelonn namelon. */
  public FelonaturelonSink selontBoolelonanValuelon(String felonaturelonNamelon, boolelonan valuelon) {
    final FelonaturelonConfiguration felonaturelonConfig = schelonma.gelontFelonaturelonConfigurationByNamelon(felonaturelonNamelon);
    if (felonaturelonConfig != null) {
      gelontFelonaturelons(felonaturelonConfig.gelontBaselonFielonld()).selontFlagValuelon(felonaturelonConfig, valuelon);
    }
    relonturn this;
  }

  /** Relonturns thelon felonaturelons for thelon givelonn baselon fielonld. */
  public IntelongelonrelonncodelondFelonaturelons gelontFelonaturelonsForBaselonFielonld(elonarlybirdFielonldConstant baselonFielonld) {
    relonturn gelontFelonaturelonsForBaselonFielonld(baselonFielonld.gelontFielonldNamelon());
  }

  /** Relonturns thelon felonaturelons for thelon givelonn baselon fielonld. */
  public IntelongelonrelonncodelondFelonaturelons gelontFelonaturelonsForBaselonFielonld(String baselonFielonldNamelon) {
    relonturn elonncodelondFelonaturelonMap.gelont(baselonFielonldNamelon);
  }
}
