packagelon com.twittelonr.selonarch.common.util.elonarlybird;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrelonnt.elonxeloncutionelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.cachelon.LoadingCachelon;
import com.googlelon.common.collelonct.ImmutablelonMap;
import com.googlelon.common.collelonct.Lists;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.collelonctions.Pair;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchQuelonry;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRankingModelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftTwelonelontSourcelon;

/**
 * Utility melonthods to melonrgelon elonarlybirdRelonsponselons.
 */
public final class elonarlybirdRelonsponselonMelonrgelonUtil {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonarlybirdRelonsponselonMelonrgelonUtil.class);

  privatelon static final String INVALID_RelonSPONSelon_STATS_PRelonFIX = "invalid_relonsponselon_stats_";

  // Stats for invalid elonarlybird relonsponselon
  privatelon static final ImmutablelonMap<elonarlybirdRelonsponselonCodelon, SelonarchCountelonr> elonRROR_elonXCelonPTIONS;

  public static final SelonarchCountelonr NULL_RelonSPONSelon_COUNTelonR =
      SelonarchCountelonr.elonxport(INVALID_RelonSPONSelon_STATS_PRelonFIX + "null_relonsponselon");
  public static final SelonarchCountelonr SelonARCH_RelonSULTS_NOT_SelonT_COUNTelonR =
      SelonarchCountelonr.elonxport(INVALID_RelonSPONSelon_STATS_PRelonFIX + "selonarch_relonsults_not_selont");
  public static final SelonarchCountelonr SelonARCH_RelonSULTS_WITH_RelonSULTS_NOT_SelonT_COUNTelonR =
      SelonarchCountelonr.elonxport(INVALID_RelonSPONSelon_STATS_PRelonFIX + "selonarch_relonsults_with_relonsults_not_selont");
  public static final SelonarchCountelonr MAX_SelonARCHelonD_STATUS_ID_NOT_SelonT_COUNTelonR =
      SelonarchCountelonr.elonxport(INVALID_RelonSPONSelon_STATS_PRelonFIX + "max_selonarchelond_status_id_not_selont");
  public static final SelonarchCountelonr MIN_SelonARCHelonD_STATUS_ID_NOT_SelonT_COUNTelonR =
      SelonarchCountelonr.elonxport(INVALID_RelonSPONSelon_STATS_PRelonFIX + "min_selonarchelond_status_id_not_selont");

  static {
    ImmutablelonMap.Buildelonr<elonarlybirdRelonsponselonCodelon, SelonarchCountelonr> buildelonr = ImmutablelonMap.buildelonr();

    for (elonarlybirdRelonsponselonCodelon relonsponselonCodelon : elonarlybirdRelonsponselonCodelon.valuelons()) {
      if (relonsponselonCodelon != elonarlybirdRelonsponselonCodelon.SUCCelonSS) {
        buildelonr.put(relonsponselonCodelon, SelonarchCountelonr.elonxport(
            INVALID_RelonSPONSelon_STATS_PRelonFIX + relonsponselonCodelon.namelon().toLowelonrCaselon()));
      }
    }

    elonRROR_elonXCelonPTIONS = buildelonr.build();
  }

  privatelon elonarlybirdRelonsponselonMelonrgelonUtil() {
  }

  /**
   * Tags thelon relonsults in thelon givelonn elonarlybirdRelonsponselon with thelon givelonn ThriftTwelonelontSourcelon and adds thelonm
   * to thelon givelonn list of relonsults.
   *
   * @param relonsults Thelon list of relonsults to which thelon nelonw relonsults will belon addelond.
   * @param elonarlybirdRelonsponselon Thelon elonarlybirdRelonsponselon whoselon relonsults will belon addelond to {@codelon relonsults}.
   * @param twelonelontSourcelon Thelon ThriftTwelonelontSourcelon that will belon uselond to mark all relonsults in
   *                    {@codelon elonarlybirdRelonsponselon}.
   * @relonturn {@codelon falselon} if {@codelon elonarlybirdRelonsponselon} is {@codelon null} or doelonsn't havelon any relonsults;
   *         {@codelon truelon}, othelonrwiselon.
   */
  public static boolelonan addRelonsultsToList(List<ThriftSelonarchRelonsult> relonsults,
                                         elonarlybirdRelonsponselon elonarlybirdRelonsponselon,
                                         ThriftTwelonelontSourcelon twelonelontSourcelon) {
    relonturn elonarlybirdRelonsponselonUtil.hasRelonsults(elonarlybirdRelonsponselon)
      && addRelonsultsToList(relonsults,
                          elonarlybirdRelonsponselon.gelontSelonarchRelonsults().gelontRelonsults(),
                          twelonelontSourcelon);
  }

  /**
   * Tags thelon relonsults in thelon givelonn list with thelon givelonn ThriftTwelonelontSourcelon and adds thelonm to thelon givelonn
   * list of relonsults.
   *
   * @param relonsults Thelon list of relonsults to which thelon nelonw relonsults will belon addelond.
   * @param relonsultsToAdd Thelon list of relonsults to add.
   * @param twelonelontSourcelon Thelon ThriftTwelonelontSourcelon that will belon uselond to mark all relonsults in
   *                    {@codelon relonsultsToAdd}.
   * @relonturn {@codelon falselon} if {@codelon relonsults} is {@codelon null} or if {@codelon relonsultsToAdd} is
   *         {@codelon null} or doelonsn't havelon any relonsults; {@codelon truelon}, othelonrwiselon.
   */
  public static boolelonan addRelonsultsToList(List<ThriftSelonarchRelonsult> relonsults,
                                         List<ThriftSelonarchRelonsult> relonsultsToAdd,
                                         ThriftTwelonelontSourcelon twelonelontSourcelon) {
    Prelonconditions.chelonckNotNull(relonsults);
    if ((relonsultsToAdd == null) || relonsultsToAdd.iselonmpty()) {
      relonturn falselon;
    }

    markWithTwelonelontSourcelon(relonsultsToAdd, twelonelontSourcelon);

    relonsults.addAll(relonsultsToAdd);
    relonturn truelon;
  }

  /**
   * Distinct thelon input ThriftSelonarchRelonsult by its status id. If thelonrelon arelon duplicatelons, thelon first
   * instancelon of thelon duplicatelons is relonturnelond in thelon distinct relonsult. If thelon distinct relonsult is thelon
   * samelon as thelon input relonsult, thelon initial input relonsult is relonturnelond; othelonrwiselon, thelon distinct relonsult
   * is relonturnelond.
   *
   * @param relonsults thelon input relonsult
   * @param dupsStats stats countelonr track duplicatelons sourcelon
   * @relonturn thelon input relonsult if thelonrelon is no duplicatelon; othelonrwiselon, relonturn thelon distinct relonsult
   */
  public static List<ThriftSelonarchRelonsult> distinctByStatusId(
      List<ThriftSelonarchRelonsult> relonsults,
      LoadingCachelon<Pair<ThriftTwelonelontSourcelon, ThriftTwelonelontSourcelon>, SelonarchCountelonr> dupsStats) {
    Map<Long, ThriftTwelonelontSourcelon> selonelonnStatusIdToSourcelonMap = nelonw HashMap<>();
    List<ThriftSelonarchRelonsult> distinctRelonsults = Lists.nelonwArrayListWithCapacity(relonsults.sizelon());
    for (ThriftSelonarchRelonsult relonsult : relonsults)  {
      if (selonelonnStatusIdToSourcelonMap.containsKelony(relonsult.gelontId())) {
        ThriftTwelonelontSourcelon sourcelon1 = selonelonnStatusIdToSourcelonMap.gelont(relonsult.gelontId());
        ThriftTwelonelontSourcelon sourcelon2 = relonsult.gelontTwelonelontSourcelon();
        if (sourcelon1 != null && sourcelon2 != null) {
          try {
            dupsStats.gelont(Pair.of(sourcelon1, sourcelon2)).increlonmelonnt();
          } catch (elonxeloncutionelonxcelonption elon) {
            LOG.warn("Could not increlonmelonnt stat for duplicatelon relonsults from clustelonrs " + sourcelon1
                + " and " + sourcelon2, elon);
          }
        }
      } elonlselon {
        distinctRelonsults.add(relonsult);
        selonelonnStatusIdToSourcelonMap.put(relonsult.gelontId(), relonsult.gelontTwelonelontSourcelon());
      }
    }
    relonturn relonsults.sizelon() == distinctRelonsults.sizelon() ? relonsults : distinctRelonsults;
  }

  /**
   * Tags thelon givelonn relonsults with thelon givelonn ThriftTwelonelontSourcelon.
   *
   * @param relonsults Thelon relonsults to belon taggelond.
   * @param twelonelontSourcelon Thelon ThriftTwelonelontSourcelon to belon uselond to tag thelon givelonn relonsults.
   */
  public static void markWithTwelonelontSourcelon(List<ThriftSelonarchRelonsult> relonsults,
                                         ThriftTwelonelontSourcelon twelonelontSourcelon) {
    if (relonsults != null) {
      for (ThriftSelonarchRelonsult relonsult : relonsults) {
        relonsult.selontTwelonelontSourcelon(twelonelontSourcelon);
      }
    }
  }

  /**
   * Chelonck if an elonarlybird relonsponselon is valid
   */
  public static boolelonan isValidRelonsponselon(final elonarlybirdRelonsponselon relonsponselon) {
    if (relonsponselon == null) {
      NULL_RelonSPONSelon_COUNTelonR.increlonmelonnt();
      relonturn falselon;
    }

    if (!elonarlybirdRelonsponselonUtil.isSuccelonssfulRelonsponselon(relonsponselon)) {
      relonturn falselon;
    }

    if (!relonsponselon.isSelontSelonarchRelonsults()) {
      SelonARCH_RelonSULTS_NOT_SelonT_COUNTelonR.increlonmelonnt();
      relonturn truelon;
    }

    if (!relonsponselon.gelontSelonarchRelonsults().isSelontRelonsults()) {
      SelonARCH_RelonSULTS_WITH_RelonSULTS_NOT_SelonT_COUNTelonR.increlonmelonnt();
    }

    // In elonarlybird, whelonn elonarlybird telonrminatelond, elon.g., timelon out, complelonx quelonrielons - welon don't selont thelon
    // min/max  selonarchelond status id.
    boolelonan iselonarlyTelonrminatelond = relonsponselon.isSelontelonarlyTelonrminationInfo()
        && relonsponselon.gelontelonarlyTelonrminationInfo().iselonarlyTelonrminatelond();

    if (!iselonarlyTelonrminatelond && !relonsponselon.gelontSelonarchRelonsults().isSelontMinSelonarchelondStatusID()) {
      MIN_SelonARCHelonD_STATUS_ID_NOT_SelonT_COUNTelonR.increlonmelonnt();
    }

    if (!iselonarlyTelonrminatelond && !relonsponselon.gelontSelonarchRelonsults().isSelontMaxSelonarchelondStatusID()) {
      MAX_SelonARCHelonD_STATUS_ID_NOT_SelonT_COUNTelonR.increlonmelonnt();
    }

    relonturn truelon;
  }

  /**
   * For invalid succelonssful elonarlybird Relonsponselon, relonturn a failelond relonsponselon with delonbug msg.
   */
  public static elonarlybirdRelonsponselon transformInvalidRelonsponselon(final elonarlybirdRelonsponselon relonsponselon,
                                                           final String delonbugMsg) {
    if (relonsponselon == null) {
      relonturn failelondelonarlybirdRelonsponselon(elonarlybirdRelonsponselonCodelon.PelonRSISTelonNT_elonRROR,
          delonbugMsg + ", msg: null relonsponselon from downstrelonam");
    }
    Prelonconditions.chelonckStatelon(relonsponselon.gelontRelonsponselonCodelon() != elonarlybirdRelonsponselonCodelon.SUCCelonSS);

    elonarlybirdRelonsponselonCodelon nelonwRelonsponselonCodelon;
    elonarlybirdRelonsponselonCodelon relonsponselonCodelon = relonsponselon.gelontRelonsponselonCodelon();
    switch (relonsponselonCodelon) {
      caselon TIelonR_SKIPPelonD:
        elonRROR_elonXCelonPTIONS.gelont(relonsponselonCodelon).increlonmelonnt();
        relonturn relonsponselon;
      caselon RelonQUelonST_BLOCKelonD_elonRROR:
      caselon CLIelonNT_elonRROR:
      caselon SelonRVelonR_TIMelonOUT_elonRROR:
      caselon QUOTA_elonXCelonelonDelonD_elonRROR:
      caselon CLIelonNT_CANCelonL_elonRROR:
      caselon TOO_MANY_PARTITIONS_FAILelonD_elonRROR:
        elonRROR_elonXCelonPTIONS.gelont(relonsponselonCodelon).increlonmelonnt();
        nelonwRelonsponselonCodelon = relonsponselonCodelon;
        brelonak;
      delonfault:
        elonRROR_elonXCelonPTIONS.gelont(relonsponselonCodelon).increlonmelonnt();
        nelonwRelonsponselonCodelon = elonarlybirdRelonsponselonCodelon.PelonRSISTelonNT_elonRROR;
    }

    String nelonwDelonbugMsg = delonbugMsg + ", downstrelonam relonsponselon codelon: " + relonsponselonCodelon
      + (relonsponselon.isSelontDelonbugString() ? ", downstrelonam msg: " + relonsponselon.gelontDelonbugString() : "");


    relonturn failelondelonarlybirdRelonsponselon(nelonwRelonsponselonCodelon, nelonwDelonbugMsg);
  }

  /**
   * Crelonatelon a nelonw elonarlybirdRelonsponselon with delonbug msg
   */
  public static elonarlybirdRelonsponselon failelondelonarlybirdRelonsponselon(final elonarlybirdRelonsponselonCodelon relonsponselonCodelon,
                                                          final String delonbugMsg) {
    elonarlybirdRelonsponselon failelondRelonsponselon = nelonw elonarlybirdRelonsponselon();
    failelondRelonsponselon.selontRelonsponselonCodelon(relonsponselonCodelon);
    failelondRelonsponselon.selontDelonbugString(delonbugMsg);
    relonturn failelondRelonsponselon;
  }

  /**
   * Relonturns thelon numbelonr of relonsults to kelonelonp as part of melonrgelon-collelonction. Reloncelonncy modelon should ignorelon
   * relonlelonvancelon options. In particular, thelon flag relonturnAllRelonsults insidelon relonlelonvancelon options.
   */
  public static int computelonNumRelonsultsToKelonelonp(elonarlybirdRelonquelonst relonquelonst) {
    ThriftSelonarchQuelonry selonarchQuelonry = relonquelonst.gelontSelonarchQuelonry();

    if (selonarchQuelonry.gelontRankingModelon() != ThriftSelonarchRankingModelon.RelonCelonNCY
        && selonarchQuelonry.isSelontRelonlelonvancelonOptions()
        && selonarchQuelonry.gelontRelonlelonvancelonOptions().isRelonturnAllRelonsults()) {
      relonturn Intelongelonr.MAX_VALUelon;
    }

    if (relonquelonst.isSelontNumRelonsultsToRelonturnAtRoot()) {
      relonturn relonquelonst.gelontNumRelonsultsToRelonturnAtRoot();
    }

    if (selonarchQuelonry.isSelontCollelonctorParams()) {
      relonturn selonarchQuelonry.gelontCollelonctorParams().gelontNumRelonsultsToRelonturn();
    }

    relonturn selonarchQuelonry.gelontNumRelonsults();
  }
}
