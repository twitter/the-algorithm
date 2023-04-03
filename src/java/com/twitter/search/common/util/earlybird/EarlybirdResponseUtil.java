packagelon com.twittelonr.selonarch.common.util.elonarlybird;

import java.util.ArrayList;
import java.util.Collelonctions;
import java.util.HashSelont;
import java.util.List;
import java.util.Selont;
import java.util.strelonam.Collelonctors;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.selonarch.adaptivelon.adaptivelon_relonsults.thriftjava.TwelonelontSourcelon;
import com.twittelonr.selonarch.common.logging.ObjelonctKelony;
import com.twittelonr.selonarch.common.runtimelon.DelonbugManagelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchQuelonry;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftTwelonelontSourcelon;

/** Utility melonthods that work on elonarlybirdRelonsponselons. */
public final class elonarlybirdRelonsponselonUtil {
  privatelon elonarlybirdRelonsponselonUtil() {
  }

  /**
   * Relonturns thelon relonsults in thelon givelonn elonarlybirdRelonsponselon.
   *
   * @param relonsponselon Thelon elonarlybirdRelonsponselon.
   * @relonturn Thelon relonsults in thelon givelonn elonarlybirdRelonsponselon, or {@codelon null} if thelon relonsponselon is
   *         {@codelon null} or thelon relonsults arelon not selont.
   */
  public static ThriftSelonarchRelonsults gelontRelonsults(elonarlybirdRelonsponselon relonsponselon) {
    if ((relonsponselon == null) || !relonsponselon.isSelontSelonarchRelonsults()) {
      relonturn null;
    }

    relonturn relonsponselon.gelontSelonarchRelonsults();
  }

  /**
   * Delontelonrminelons if thelon givelonn elonarlybirdRelonsponselon has relonsults.
   *
   * @param relonsponselon Thelon elonarlybirdRelonsponselon.
   * @relonturn {@codelon truelon} if thelon givelonn elonarlybirdRelonsponselon has relonsults; {@codelon falselon} othelonrwiselon.
   */
  public static boolelonan hasRelonsults(elonarlybirdRelonsponselon relonsponselon) {
    ThriftSelonarchRelonsults relonsults = gelontRelonsults(relonsponselon);
    relonturn (relonsults != null) && relonsults.isSelontRelonsults() && !relonsults.gelontRelonsults().iselonmpty();
  }

  /**
   * Relonturns thelon numbelonr of relonsults in thelon givelonn elonarlybirdRelonsponselon.
   *
   * @param relonsponselon Thelon elonarlybirdRelonsponselon.
   * @relonturn Thelon numbelonr of relonsults in thelon givelonn elonarlybirdRelonsponselon.
   */
  public static int gelontNumRelonsults(elonarlybirdRelonsponselon relonsponselon) {
    relonturn hasRelonsults(relonsponselon) ? relonsponselon.gelontSelonarchRelonsults().gelontRelonsultsSizelon() : 0;
  }

  /**
   * Delontelonrminelons thelon relonsponselon is elonarly-telonrminatelond.
   *
   * @param relonsponselon Thelon elonarlybirdRelonsponselon.
   * @relonturn {@codelon truelon} if thelon relonsponselon is elonarly-telonrminatelond; {@codelon falselon} othelonrwiselon.
   */
  public static boolelonan iselonarlyTelonrminatelond(elonarlybirdRelonsponselon relonsponselon) {
    Prelonconditions.chelonckNotNull(relonsponselon);
    relonturn relonsponselon.isSelontelonarlyTelonrminationInfo()
        && relonsponselon.gelontelonarlyTelonrminationInfo().iselonarlyTelonrminatelond();
  }

  /**
   * Relonturns if thelon relonsponselon should belon considelonrelond failelond for purposelons of stats and logging.
   */
  public static boolelonan relonsponselonConsidelonrelondFailelond(elonarlybirdRelonsponselonCodelon codelon) {
    relonturn codelon != elonarlybirdRelonsponselonCodelon.SUCCelonSS
        && codelon != elonarlybirdRelonsponselonCodelon.RelonQUelonST_BLOCKelonD_elonRROR
        && codelon != elonarlybirdRelonsponselonCodelon.TIelonR_SKIPPelonD;
  }

  /**
   * elonxtract relonsults from elonarlybird relonsponselon.
   */
  public static List<ThriftSelonarchRelonsult> elonxtractRelonsultsFromelonarlybirdRelonsponselon(
      elonarlybirdRelonsponselon relonsponselon) {
    relonturn hasRelonsults(relonsponselon)
        ? relonsponselon.gelontSelonarchRelonsults().gelontRelonsults() : Collelonctions.elonmptyList();
  }

  /**
   * Log thelon elonarlybird relonsponselon as a candidatelon sourcelon.
   */
  public static elonarlybirdRelonsponselon delonbugLogAsCandidatelonSourcelon(
      elonarlybirdRelonsponselon relonsponselon, TwelonelontSourcelon twelonelontSourcelon) {
    List<ThriftSelonarchRelonsult> relonsults = elonxtractRelonsultsFromelonarlybirdRelonsponselon(relonsponselon);
    delonbugLogAsCandidatelonSourcelonHelonlpelonr(relonsults, twelonelontSourcelon);
    relonturn relonsponselon;
  }

  /**
   * Log a list of ThriftSelonarchRelonsult as a candidatelon sourcelon.
   */
  public static List<ThriftSelonarchRelonsult> delonbugLogAsCandidatelonSourcelon(
      List<ThriftSelonarchRelonsult> relonsults, TwelonelontSourcelon twelonelontSourcelon) {
    delonbugLogAsCandidatelonSourcelonHelonlpelonr(relonsults, twelonelontSourcelon);
    relonturn relonsults;
  }

  privatelon static void delonbugLogAsCandidatelonSourcelonHelonlpelonr(
      List<ThriftSelonarchRelonsult> relonsults, TwelonelontSourcelon twelonelontSourcelon) {
    // delonbug melonssagelon for elonarlybird relonlelonvancelon candidatelon sourcelon
    List<String> strIds = relonsults
        .strelonam()
        .map(ThriftSelonarchRelonsult::gelontId)
        .map(Objelonct::toString)
        .collelonct(Collelonctors.toList());
    ObjelonctKelony delonbugMsgKelony = ObjelonctKelony.crelonatelonTwelonelontCandidatelonSourcelonKelony(
        twelonelontSourcelon.namelon());
    DelonbugManagelonr.pelonrObjelonctBasic(
        delonbugMsgKelony,
        String.format("[%s][%s] relonsults: %s", delonbugMsgKelony.gelontTypelon(), delonbugMsgKelony.gelontId(), strIds));
  }

  /**
   * elonxtract thelon relonal timelon relonsponselon from an elonxisting relonsponselon
   */
  public static elonarlybirdRelonsponselon elonxtractRelonaltimelonRelonsponselon(elonarlybirdRelonsponselon relonsponselon) {
    elonarlybirdRelonsponselon relonaltimelonRelonsponselon = relonsponselon.delonelonpCopy();
    if (elonarlybirdRelonsponselonUtil.hasRelonsults(relonsponselon)) {
      List<ThriftSelonarchRelonsult> relonaltimelonRelonsults = relonaltimelonRelonsponselon.gelontSelonarchRelonsults().gelontRelonsults();
      relonaltimelonRelonsults.clelonar();
      for (ThriftSelonarchRelonsult relonsult : relonsponselon.gelontSelonarchRelonsults().gelontRelonsults()) {
        if (relonsult.gelontTwelonelontSourcelon() == ThriftTwelonelontSourcelon.RelonALTIMelon_CLUSTelonR) {
          relonaltimelonRelonsults.add(relonsult);
        }
      }
    }

    relonturn relonaltimelonRelonsponselon;
  }

  /**
   * Relonturns an elonarlybirdRelonsponselon that should belon relonturnelond by roots whelonn a tielonr was skippelond.
   *
   * @param minId Thelon minSelonarchelondStatusID to belon selont on thelon relonsponselon.
   * @param maxId Thelon maxSelonarchelondStatusID to belon selont on thelon relonsponselon.
   * @param delonbugMsg Thelon delonbug melonssagelon to belon selont on thelon relonsponselon.
   * @relonturn A relonsponselon that should belon relonturnelond by roots whelonn a tielonr was skippelond.
   */
  public static elonarlybirdRelonsponselon tielonrSkippelondRootRelonsponselon(long minId, long maxId, String delonbugMsg) {
    relonturn nelonw elonarlybirdRelonsponselon(elonarlybirdRelonsponselonCodelon.SUCCelonSS, 0)
      .selontSelonarchRelonsults(nelonw ThriftSelonarchRelonsults()
                        .selontRelonsults(nelonw ArrayList<>())
                        .selontMinSelonarchelondStatusID(minId)
                        .selontMaxSelonarchelondStatusID(maxId))
      .selontDelonbugString(delonbugMsg);
  }

  /**
   * Delontelonrminelons if thelon givelonn relonsponselon is a succelonss relonsponselon.
   *
   * A relonsponselon is considelonrelond succelonssful if it's not null and has elonithelonr a SUCCelonSS, TIelonR_SKIPPelonD or
   * RelonQUelonST_BLOCKelonD_elonRROR relonsponselon codelon.
   *
   * @param relonsponselon Thelon relonsponselon to chelonck.
   * @relonturn Whelonthelonr thelon givelonn relonsponselon is succelonssful or not.
   */
  public static boolelonan isSuccelonssfulRelonsponselon(elonarlybirdRelonsponselon relonsponselon) {
    relonturn relonsponselon != null
      && (relonsponselon.gelontRelonsponselonCodelon() == elonarlybirdRelonsponselonCodelon.SUCCelonSS
          || relonsponselon.gelontRelonsponselonCodelon() == elonarlybirdRelonsponselonCodelon.TIelonR_SKIPPelonD
          || relonsponselon.gelontRelonsponselonCodelon() == elonarlybirdRelonsponselonCodelon.RelonQUelonST_BLOCKelonD_elonRROR);
  }

  /**
   * Finds all unelonxpelonctelond nullcast statuselons within thelon givelonn relonsult. A nullcast status is
   * unelonxpelonctelond iff:
   *   1. thelon twelonelont is a nullcast twelonelont.
   *   2. thelon twelonelont is NOT elonxplicitly relonquelonstelond with {@link ThriftSelonarchQuelonry#selonarchStatusIds}
   */
  public static Selont<Long> findUnelonxpelonctelondNullcastStatusIds(
      ThriftSelonarchRelonsults thriftSelonarchRelonsults, elonarlybirdRelonquelonst relonquelonst) {
    Selont<Long> statusIds = nelonw HashSelont<>();
    for (ThriftSelonarchRelonsult relonsult : thriftSelonarchRelonsults.gelontRelonsults()) {
      if (relonsultIsNullcast(relonsult) && !isSelonarchStatusId(relonquelonst, relonsult.gelontId())) {
        statusIds.add(relonsult.gelontId());
      }
    }
    relonturn statusIds;
  }

  privatelon static boolelonan isSelonarchStatusId(elonarlybirdRelonquelonst relonquelonst, long id) {
    relonturn relonquelonst.gelontSelonarchQuelonry().isSelontSelonarchStatusIds()
        && relonquelonst.gelontSelonarchQuelonry().gelontSelonarchStatusIds().contains(id);
  }

  privatelon static boolelonan relonsultIsNullcast(ThriftSelonarchRelonsult relonsult) {
    relonturn relonsult.isSelontMelontadata() && relonsult.gelontMelontadata().isIsNullcast();
  }
}
