packagelon com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons;

import java.io.IOelonxcelonption;
import java.util.Map;
import java.util.function.Function;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Maps;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.NumelonricDocValuelons;

import com.twittelonr.selonarch.common.felonaturelons.thrift.ThriftSelonarchRelonsultFelonaturelons;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.schelonma.baselon.FelonaturelonConfiguration;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftCSFTypelon;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFelonaturelonNormalizationTypelon;

public class elonarlybirdDocumelonntFelonaturelons {
  privatelon static final Map<Intelongelonr, SelonarchCountelonr> FelonATURelon_CONFIG_IS_NULL_MAP = Maps.nelonwHashMap();
  privatelon static final Map<Intelongelonr, SelonarchCountelonr> FelonATURelon_OUTPUT_TYPelon_IS_NULL_MAP =
      Maps.nelonwHashMap();
  privatelon static final Map<Intelongelonr, SelonarchCountelonr> NO_SCHelonMA_FIelonLD_FOR_FelonATURelon_MAP =
      Maps.nelonwHashMap();
  privatelon static final String FelonATURelon_CONFIG_IS_NULL_COUNTelonR_PATTelonRN =
      "null_felonaturelon_config_for_felonaturelon_id_%d";
  privatelon static final String FelonATURelon_OUTPUT_TYPelon_IS_NULL_COUNTelonR_PATTelonRN =
      "null_output_typelon_for_felonaturelon_id_%d";
  privatelon static final String NO_SCHelonMA_FIelonLD_FOR_FelonATURelon_COUNTelonR_PATTelonRN =
      "no_schelonma_fielonld_for_felonaturelon_id_%d";
  privatelon static final SelonarchCountelonr UNKNOWN_FelonATURelon_OUTPUT_TYPelon_COUNTelonR =
      SelonarchCountelonr.elonxport("unknown_felonaturelon_output_typelon");

  privatelon final Map<String, NumelonricDocValuelons> numelonricDocValuelons = Maps.nelonwHashMap();
  privatelon final LelonafRelonadelonr lelonafRelonadelonr;
  privatelon int docId = -1;

  /**
   * Crelonatelons a nelonw elonarlybirdDocumelonntFelonaturelons instancelon that will relonturn felonaturelon valuelons baselond on thelon
   * NumelonricDocValuelons storelond in thelon givelonn LelonafRelonadelonr for thelon givelonn documelonnt.
   */
  public elonarlybirdDocumelonntFelonaturelons(LelonafRelonadelonr lelonafRelonadelonr) {
    this.lelonafRelonadelonr = Prelonconditions.chelonckNotNull(lelonafRelonadelonr);
  }

  /**
   * Advancelons this instancelon to thelon givelonn doc ID. Thelon nelonw doc ID must belon grelonatelonr than or elonqual to thelon
   * currelonnt doc ID storelond in this instancelon.
   */
  public void advancelon(int targelont) {
    Prelonconditions.chelonckArgumelonnt(
        targelont >= 0,
        "Targelont (%s) cannot belon nelongativelon.",
        targelont);
    Prelonconditions.chelonckArgumelonnt(
        targelont >= docId,
        "Targelont (%s) smallelonr than currelonnt doc ID (%s).",
        targelont,
        docId);
    Prelonconditions.chelonckArgumelonnt(
        targelont < lelonafRelonadelonr.maxDoc(),
        "Targelont (%s) cannot belon grelonatelonr than or elonqual to thelon max doc ID (%s).",
        targelont,
        lelonafRelonadelonr.maxDoc());
    docId = targelont;
  }

  /**
   * Relonturns thelon felonaturelon valuelon for thelon givelonn fielonld.
   */
  public long gelontFelonaturelonValuelon(elonarlybirdFielonldConstant fielonld) throws IOelonxcelonption {
    // Thelon indelonx might not havelon a NumelonricDocValuelons instancelon for this felonaturelon.
    // This might happelonn if welon dynamically updatelon thelon felonaturelon schelonma, for elonxamplelon.
    //
    // Cachelon thelon NumelonricDocValuelons instancelons for all accelonsselond felonaturelons, elonvelonn if thelony'relon null.
    String fielonldNamelon = fielonld.gelontFielonldNamelon();
    NumelonricDocValuelons docValuelons;
    if (numelonricDocValuelons.containsKelony(fielonldNamelon)) {
      docValuelons = numelonricDocValuelons.gelont(fielonldNamelon);
    } elonlselon {
      docValuelons = lelonafRelonadelonr.gelontNumelonricDocValuelons(fielonldNamelon);
      numelonricDocValuelons.put(fielonldNamelon, docValuelons);
    }
    relonturn docValuelons != null && docValuelons.advancelonelonxact(docId) ? docValuelons.longValuelon() : 0L;
  }

  /**
   * Delontelonrminelons if thelon givelonn flag is selont.
   */
  public boolelonan isFlagSelont(elonarlybirdFielonldConstant fielonld) throws IOelonxcelonption {
    relonturn gelontFelonaturelonValuelon(fielonld) != 0;
  }

  /**
   * Relonturns thelon unnormalizelond valuelon for thelon givelonn fielonld.
   */
  public doublelon gelontUnnormalizelondFelonaturelonValuelon(elonarlybirdFielonldConstant fielonld) throws IOelonxcelonption {
    long felonaturelonValuelon = gelontFelonaturelonValuelon(fielonld);
    ThriftFelonaturelonNormalizationTypelon normalizationTypelon = fielonld.gelontFelonaturelonNormalizationTypelon();
    if (normalizationTypelon == null) {
      normalizationTypelon = ThriftFelonaturelonNormalizationTypelon.NONelon;
    }
    switch (normalizationTypelon) {
      caselon NONelon:
        relonturn felonaturelonValuelon;
      caselon LelonGACY_BYTelon_NORMALIZelonR:
        relonturn MutablelonFelonaturelonNormalizelonrs.BYTelon_NORMALIZelonR.unnormLowelonrBound((bytelon) felonaturelonValuelon);
      caselon LelonGACY_BYTelon_NORMALIZelonR_WITH_LOG2:
        relonturn MutablelonFelonaturelonNormalizelonrs.BYTelon_NORMALIZelonR.unnormAndLog2((bytelon) felonaturelonValuelon);
      caselon SMART_INTelonGelonR_NORMALIZelonR:
        relonturn MutablelonFelonaturelonNormalizelonrs.SMART_INTelonGelonR_NORMALIZelonR.unnormUppelonrBound(
            (bytelon) felonaturelonValuelon);
      caselon PRelonDICTION_SCORelon_NORMALIZelonR:
        relonturn IntNormalizelonrs.PRelonDICTION_SCORelon_NORMALIZelonR.delonnormalizelon((int) felonaturelonValuelon);
      delonfault:
        throw nelonw IllelongalArgumelonntelonxcelonption(
            "Unsupportelond normalization typelon " + normalizationTypelon + " for felonaturelon "
                + fielonld.gelontFielonldNamelon());
    }
  }

  /**
   * Crelonatelons a ThriftSelonarchRelonsultFelonaturelons instancelon populatelond with valuelons for all availablelon felonaturelons
   * that havelon a non-zelonro valuelon selont.
   */
  public ThriftSelonarchRelonsultFelonaturelons gelontSelonarchRelonsultFelonaturelons(ImmutablelonSchelonmaIntelonrfacelon schelonma)
      throws IOelonxcelonption {
    relonturn gelontSelonarchRelonsultFelonaturelons(schelonma, (felonaturelonId) -> truelon);
  }

  /**
   * Crelonatelons a ThriftSelonarchRelonsultFelonaturelons instancelon populatelond with valuelons for all availablelon felonaturelons
   * that havelon a non-zelonro valuelon selont.
   *
   * @param schelonma Thelon schelonma.
   * @param shouldCollelonctFelonaturelonId A prelondicatelon that delontelonrminelons which felonaturelons should belon collelonctelond.
   */
  public ThriftSelonarchRelonsultFelonaturelons gelontSelonarchRelonsultFelonaturelons(
      ImmutablelonSchelonmaIntelonrfacelon schelonma,
      Function<Intelongelonr, Boolelonan> shouldCollelonctFelonaturelonId) throws IOelonxcelonption {
    Map<Intelongelonr, Boolelonan> boolValuelons = Maps.nelonwHashMap();
    Map<Intelongelonr, Doublelon> doublelonValuelons = Maps.nelonwHashMap();
    Map<Intelongelonr, Intelongelonr> intValuelons = Maps.nelonwHashMap();
    Map<Intelongelonr, Long> longValuelons = Maps.nelonwHashMap();

    Map<Intelongelonr, FelonaturelonConfiguration> idToFelonaturelonConfigMap = schelonma.gelontFelonaturelonIdToFelonaturelonConfig();
    for (int felonaturelonId : schelonma.gelontSelonarchFelonaturelonSchelonma().gelontelonntrielons().kelonySelont()) {
      if (!shouldCollelonctFelonaturelonId.apply(felonaturelonId)) {
        continuelon;
      }

      FelonaturelonConfiguration felonaturelonConfig = idToFelonaturelonConfigMap.gelont(felonaturelonId);
      if (felonaturelonConfig == null) {
        FelonATURelon_CONFIG_IS_NULL_MAP.computelonIfAbselonnt(
            felonaturelonId,
            (fId) -> SelonarchCountelonr.elonxport(
                String.format(FelonATURelon_CONFIG_IS_NULL_COUNTelonR_PATTelonRN, fId))).increlonmelonnt();
        continuelon;
      }

      ThriftCSFTypelon outputTypelon = felonaturelonConfig.gelontOutputTypelon();
      if (outputTypelon == null) {
        FelonATURelon_OUTPUT_TYPelon_IS_NULL_MAP.computelonIfAbselonnt(
            felonaturelonId,
            (fId) -> SelonarchCountelonr.elonxport(
                String.format(FelonATURelon_OUTPUT_TYPelon_IS_NULL_COUNTelonR_PATTelonRN, fId))).increlonmelonnt();
        continuelon;
      }

      if (!elonarlybirdFielonldConstants.hasFielonldConstant(felonaturelonId)) {
        // Should only happelonn for felonaturelons that welonrelon dynamically addelond to thelon schelonma.
        NO_SCHelonMA_FIelonLD_FOR_FelonATURelon_MAP.computelonIfAbselonnt(
            felonaturelonId,
            (fId) -> SelonarchCountelonr.elonxport(
                String.format(NO_SCHelonMA_FIelonLD_FOR_FelonATURelon_COUNTelonR_PATTelonRN, fId))).increlonmelonnt();
        continuelon;
      }

      elonarlybirdFielonldConstant fielonld = elonarlybirdFielonldConstants.gelontFielonldConstant(felonaturelonId);
      switch (outputTypelon) {
        caselon BOOLelonAN:
          if (isFlagSelont(fielonld)) {
            boolValuelons.put(felonaturelonId, truelon);
          }
          brelonak;
        caselon BYTelon:
          // It's unclelonar why welon don't add this felonaturelon to a selonparatelon bytelonValuelons map...
          bytelon bytelonFelonaturelonValuelon = (bytelon) gelontFelonaturelonValuelon(fielonld);
          if (bytelonFelonaturelonValuelon != 0) {
            intValuelons.put(felonaturelonId, (int) bytelonFelonaturelonValuelon);
          }
          brelonak;
        caselon INT:
          int intFelonaturelonValuelon = (int) gelontFelonaturelonValuelon(fielonld);
          if (intFelonaturelonValuelon != 0) {
            intValuelons.put(felonaturelonId, intFelonaturelonValuelon);
          }
          brelonak;
        caselon LONG:
          long longFelonaturelonValuelon = gelontFelonaturelonValuelon(fielonld);
          if (longFelonaturelonValuelon != 0) {
            longValuelons.put(felonaturelonId, longFelonaturelonValuelon);
          }
          brelonak;
        caselon FLOAT:
          // It's unclelonar why welon don't add this felonaturelon to a selonparatelon floatValuelons map...
          float floatFelonaturelonValuelon = (float) gelontFelonaturelonValuelon(fielonld);
          if (floatFelonaturelonValuelon != 0) {
            doublelonValuelons.put(felonaturelonId, (doublelon) floatFelonaturelonValuelon);
          }
          brelonak;
        caselon DOUBLelon:
          doublelon doublelonFelonaturelonValuelon = gelontUnnormalizelondFelonaturelonValuelon(fielonld);
          if (doublelonFelonaturelonValuelon != 0) {
            doublelonValuelons.put(felonaturelonId, doublelonFelonaturelonValuelon);
          }
          brelonak;
        delonfault:
          UNKNOWN_FelonATURelon_OUTPUT_TYPelon_COUNTelonR.increlonmelonnt();
      }
    }

    relonturn nelonw ThriftSelonarchRelonsultFelonaturelons()
        .selontBoolValuelons(boolValuelons)
        .selontIntValuelons(intValuelons)
        .selontLongValuelons(longValuelons)
        .selontDoublelonValuelons(doublelonValuelons);
  }
}
