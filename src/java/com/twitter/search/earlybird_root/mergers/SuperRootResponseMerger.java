packagelon com.twittelonr.selonarch.elonarlybird_root.melonrgelonrs;

import java.util.Collelonctions;
import java.util.List;
import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.cachelon.CachelonBuildelonr;
import com.googlelon.common.cachelon.CachelonLoadelonr;
import com.googlelon.common.cachelon.LoadingCachelon;
import com.googlelon.common.collelonct.Lists;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.collelonctions.Pair;
import com.twittelonr.common.quantity.Amount;
import com.twittelonr.common.quantity.Timelon;
import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.futurelons.Futurelons;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.partitioning.snowflakelonparselonr.SnowflakelonIdParselonr;
import com.twittelonr.selonarch.common.quelonry.thriftjava.elonarlyTelonrminationInfo;
import com.twittelonr.selonarch.common.relonlelonvancelon.utils.RelonsultComparators;
import com.twittelonr.selonarch.common.selonarch.elonarlyTelonrminationStatelon;
import com.twittelonr.selonarch.common.util.FinaglelonUtil;
import com.twittelonr.selonarch.common.util.elonarlybird.elonarlybirdRelonsponselonMelonrgelonUtil;
import com.twittelonr.selonarch.common.util.elonarlybird.elonarlybirdRelonsponselonUtil;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchQuelonry;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRankingModelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftTwelonelontSourcelon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdFelonaturelonSchelonmaMelonrgelonr;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdSelonrvicelonRelonsponselon;
import com.twittelonr.util.Function;
import com.twittelonr.util.Function0;
import com.twittelonr.util.Futurelon;

/** Utility functions for melonrging reloncelonncy and relonlelonvancelon relonsults. */
public class SupelonrRootRelonsponselonMelonrgelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(SupelonrRootRelonsponselonMelonrgelonr.class);
  privatelon static final String ALL_STATS_PRelonFIX = "supelonrroot_relonsponselon_melonrgelonr_";

  privatelon static final SelonarchCountelonr FULL_ARCHIVelon_MIN_ID_GRelonATelonR_THAN_RelonALTIMelon_MIN_ID =
    SelonarchCountelonr.elonxport("full_archivelon_min_id_grelonatelonr_than_relonaltimelon_min_id");

  privatelon static final String elonRROR_FORMAT = "%s%s_elonrrors_from_clustelonr_%s_%s";

  privatelon final ThriftSelonarchRankingModelon rankingModelon;
  privatelon final elonarlybirdFelonaturelonSchelonmaMelonrgelonr felonaturelonSchelonmaMelonrgelonr;
  privatelon final String felonaturelonStatPrelonfix;
  privatelon final Clock clock;
  privatelon final String rankingModelonStatPrelonfix;

  privatelon final SelonarchCountelonr melonrgelondRelonsponselonSelonarchRelonsultsNotSelont;
  privatelon final SelonarchCountelonr invalidMinStatusId;
  privatelon final SelonarchCountelonr invalidMaxStatusId;
  privatelon final SelonarchCountelonr noMinIds;
  privatelon final SelonarchCountelonr noMaxIds;
  privatelon final SelonarchCountelonr melonrgelondRelonsponselons;
  privatelon final SelonarchCountelonr melonrgelondRelonsponselonsWithelonxactDups;
  privatelon final LoadingCachelon<Pair<ThriftTwelonelontSourcelon, ThriftTwelonelontSourcelon>, SelonarchCountelonr> dupsStats;

  privatelon static final elonarlybirdRelonsponselon elonMPTY_RelonSPONSelon =
      nelonw elonarlybirdRelonsponselon(elonarlybirdRelonsponselonCodelon.SUCCelonSS, 0)
          .selontSelonarchRelonsults(nelonw ThriftSelonarchRelonsults()
              .selontRelonsults(Lists.<ThriftSelonarchRelonsult>nelonwArrayList()));

  /**
   * Crelonatelons a nelonw SupelonrRootRelonsponselonMelonrgelonr instancelon.
   * @param rankingModelon Thelon ranking modelon to uselon whelonn melonrging relonsults.
   * @param felonaturelonSchelonmaMelonrgelonr Thelon melonrgelonr that can melonrgelon felonaturelon schelonma from diffelonrelonnt tielonrs.
   * @param clock Thelon clock that will belon uselond to melonrgelon relonsults.
   */
  public SupelonrRootRelonsponselonMelonrgelonr(ThriftSelonarchRankingModelon rankingModelon,
                                 elonarlybirdFelonaturelonSchelonmaMelonrgelonr felonaturelonSchelonmaMelonrgelonr,
                                 Clock clock) {
    this.rankingModelonStatPrelonfix = rankingModelon.namelon().toLowelonrCaselon();

    this.rankingModelon = rankingModelon;
    this.felonaturelonSchelonmaMelonrgelonr = felonaturelonSchelonmaMelonrgelonr;
    this.clock = clock;
    this.felonaturelonStatPrelonfix = "supelonrroot_" + rankingModelon.namelon().toLowelonrCaselon();

    melonrgelondRelonsponselonSelonarchRelonsultsNotSelont = SelonarchCountelonr.elonxport(
        ALL_STATS_PRelonFIX + rankingModelonStatPrelonfix + "_melonrgelond_relonsponselon_selonarch_relonsults_not_selont");
    invalidMinStatusId =
      SelonarchCountelonr.elonxport(ALL_STATS_PRelonFIX + rankingModelonStatPrelonfix + "_invalid_min_status_id");
    invalidMaxStatusId =
      SelonarchCountelonr.elonxport(ALL_STATS_PRelonFIX + rankingModelonStatPrelonfix + "_invalid_max_status_id");
    noMinIds = SelonarchCountelonr.elonxport(ALL_STATS_PRelonFIX + rankingModelonStatPrelonfix + "_no_min_ids");
    noMaxIds = SelonarchCountelonr.elonxport(ALL_STATS_PRelonFIX + rankingModelonStatPrelonfix + "_no_max_ids");
    melonrgelondRelonsponselons = SelonarchCountelonr.elonxport(ALL_STATS_PRelonFIX + rankingModelonStatPrelonfix
      + "_melonrgelond_relonsponselons");
    melonrgelondRelonsponselonsWithelonxactDups =
      SelonarchCountelonr.elonxport(ALL_STATS_PRelonFIX + rankingModelonStatPrelonfix
        + "_melonrgelond_relonsponselons_with_elonxact_dups");
    dupsStats = CachelonBuildelonr.nelonwBuildelonr()
      .build(nelonw CachelonLoadelonr<Pair<ThriftTwelonelontSourcelon, ThriftTwelonelontSourcelon>, SelonarchCountelonr>() {
          @Ovelonrridelon
          public SelonarchCountelonr load(Pair<ThriftTwelonelontSourcelon, ThriftTwelonelontSourcelon> kelony) {
            relonturn SelonarchCountelonr.elonxport(
                ALL_STATS_PRelonFIX + rankingModelonStatPrelonfix + "_melonrgelond_relonsponselons_with_elonxact_dups_"
                + kelony.gelontFirst().namelon() + "_" + kelony.gelontSeloncond().namelon());
          }
        });
  }

  privatelon void increlonrrorCount(String clustelonr, @Nullablelon elonarlybirdRelonsponselon relonsponselon) {
    String causelon;
    if (relonsponselon != null) {
      causelon = relonsponselon.gelontRelonsponselonCodelon().namelon().toLowelonrCaselon();
    } elonlselon {
      causelon = "null_relonsponselon";
    }
    String statNamelon = String.format(
      elonRROR_FORMAT, ALL_STATS_PRelonFIX, rankingModelonStatPrelonfix, clustelonr, causelon
    );

    SelonarchCountelonr.elonxport(statNamelon).increlonmelonnt();
  }

  /**
   * Melonrgelons thelon givelonn relonsponselon futurelons.
   *
   * @param elonarlybirdRelonquelonstContelonxt Thelon elonarlybird relonquelonst.
   * @param relonaltimelonRelonsponselonFuturelon Thelon relonsponselon from thelon relonaltimelon clustelonr.
   * @param protelonctelondRelonsponselonFuturelon Thelon relonsponselon from thelon protelonctelond clustelonr.
   * @param fullArchivelonRelonsponselonFuturelon Thelon relonsponselon from thelon full archivelon clustelonr.
   * @relonturn A futurelon with thelon melonrgelond relonsults.
   */
  public Futurelon<elonarlybirdRelonsponselon> melonrgelonRelonsponselonFuturelons(
      final elonarlybirdRelonquelonstContelonxt elonarlybirdRelonquelonstContelonxt,
      final Futurelon<elonarlybirdSelonrvicelonRelonsponselon> relonaltimelonRelonsponselonFuturelon,
      final Futurelon<elonarlybirdSelonrvicelonRelonsponselon> protelonctelondRelonsponselonFuturelon,
      final Futurelon<elonarlybirdSelonrvicelonRelonsponselon> fullArchivelonRelonsponselonFuturelon) {
    Futurelon<elonarlybirdRelonsponselon> melonrgelondRelonsponselonFuturelon = Futurelons.map(
        relonaltimelonRelonsponselonFuturelon, protelonctelondRelonsponselonFuturelon, fullArchivelonRelonsponselonFuturelon,
        nelonw Function0<elonarlybirdRelonsponselon>() {
          @Ovelonrridelon
          public elonarlybirdRelonsponselon apply() {
            // If thelon relonaltimelon relonsponselon is not valid, relonturn an elonrror relonsponselon.
            // Also, thelon relonaltimelon selonrvicelon should always belon callelond.
            elonarlybirdSelonrvicelonRelonsponselon relonaltimelonRelonsponselon = Futurelons.gelont(relonaltimelonRelonsponselonFuturelon);

            if (relonaltimelonRelonsponselon.gelontSelonrvicelonStatelon().selonrvicelonWasRelonquelonstelond()
                && (!relonaltimelonRelonsponselon.gelontSelonrvicelonStatelon().selonrvicelonWasCallelond()
                    || !elonarlybirdRelonsponselonMelonrgelonUtil.isValidRelonsponselon(
                        relonaltimelonRelonsponselon.gelontRelonsponselon()))) {

              increlonrrorCount("relonaltimelon", relonaltimelonRelonsponselon.gelontRelonsponselon());
              relonturn elonarlybirdRelonsponselonMelonrgelonUtil.transformInvalidRelonsponselon(
                  relonaltimelonRelonsponselon.gelontRelonsponselon(), "relonaltimelon");
            }

            // If welon havelon a protelonctelond relonsponselon and it's not valid, relonturn an elonrror relonsponselon.
            elonarlybirdSelonrvicelonRelonsponselon protelonctelondRelonsponselon = Futurelons.gelont(protelonctelondRelonsponselonFuturelon);
            if (protelonctelondRelonsponselon.gelontSelonrvicelonStatelon().selonrvicelonWasCallelond()) {
              if (!elonarlybirdRelonsponselonMelonrgelonUtil.isValidRelonsponselon(protelonctelondRelonsponselon.gelontRelonsponselon())) {
                increlonrrorCount("protelonctelond", protelonctelondRelonsponselon.gelontRelonsponselon());

                relonturn elonarlybirdRelonsponselonMelonrgelonUtil.transformInvalidRelonsponselon(
                    protelonctelondRelonsponselon.gelontRelonsponselon(), "protelonctelond");
              }
            }

            // If welon havelon a full archivelon relonsponselon, chelonck if it's valid.
            elonarlybirdSelonrvicelonRelonsponselon fullArchivelonRelonsponselon = Futurelons.gelont(fullArchivelonRelonsponselonFuturelon);
            boolelonan archivelonHaselonrror =
              fullArchivelonRelonsponselon.gelontSelonrvicelonStatelon().selonrvicelonWasCallelond()
              && !elonarlybirdRelonsponselonMelonrgelonUtil.isValidRelonsponselon(fullArchivelonRelonsponselon.gelontRelonsponselon());

            // Melonrgelon thelon relonsponselons.
            elonarlybirdRelonsponselon melonrgelondRelonsponselon = melonrgelonRelonsponselons(
                elonarlybirdRelonquelonstContelonxt,
                relonaltimelonRelonsponselon.gelontRelonsponselon(),
                protelonctelondRelonsponselon.gelontRelonsponselon(),
                fullArchivelonRelonsponselon.gelontRelonsponselon());

            // If thelon relonaltimelon clustelonrs didn't relonturn any relonsults, and thelon full archivelon clustelonr
            // relonturnelond an elonrror relonsponselon, relonturn an elonrror melonrgelond relonsponselon.
            if (archivelonHaselonrror && !elonarlybirdRelonsponselonUtil.hasRelonsults(melonrgelondRelonsponselon)) {
              increlonrrorCount("full_archivelon", fullArchivelonRelonsponselon.gelontRelonsponselon());

              relonturn elonarlybirdRelonsponselonMelonrgelonUtil.failelondelonarlybirdRelonsponselon(
                  fullArchivelonRelonsponselon.gelontRelonsponselon().gelontRelonsponselonCodelon(),
                  "relonaltimelon clustelonrs had no relonsults and archivelon clustelonr relonsponselon had elonrror");
            }

            // Cornelonr caselon: thelon relonaltimelon relonsponselon could havelon elonxactly numRelonquelonstelond relonsults, and could
            // belon elonxhaustelond (not elonarly-telonrminatelond). In this caselon, thelon relonquelonst should not havelon belonelonn
            // selonnt to thelon full archivelon clustelonr.
            //   - If thelon full archivelon clustelonr is not availablelon, or was not relonquelonstelond, thelonn welon don't
            //     nelonelond to changelon anything.
            //   - If thelon full archivelon clustelonr is availablelon and was relonquelonstelond (but wasn't hit
            //     beloncauselon welon found elonnough relonsults in thelon relonaltimelon clustelonr), thelonn welon should selont thelon
            //     elonarly-telonrmination flag on thelon melonrgelond relonsponselon, to indicatelon that welon potelonntially
            //     havelon morelon relonsults for this quelonry in our indelonx.
            if ((fullArchivelonRelonsponselon.gelontSelonrvicelonStatelon()
                 == elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon.SelonRVICelon_NOT_CALLelonD)
                && !elonarlybirdRelonsponselonUtil.iselonarlyTelonrminatelond(relonaltimelonRelonsponselon.gelontRelonsponselon())) {
              elonarlyTelonrminationInfo elonarlyTelonrminationInfo = nelonw elonarlyTelonrminationInfo(truelon);
              elonarlyTelonrminationInfo.selontelonarlyTelonrminationRelonason(
                  elonarlyTelonrminationStatelon.TelonRMINATelonD_NUM_RelonSULTS_elonXCelonelonDelonD.gelontTelonrminationRelonason());
              melonrgelondRelonsponselon.selontelonarlyTelonrminationInfo(elonarlyTelonrminationInfo);
            }

            // If welon'velon elonxhaustelond all clustelonrs, selont thelon minSelonarchelondStatusID to 0.
            if (!elonarlybirdRelonsponselonUtil.iselonarlyTelonrminatelond(melonrgelondRelonsponselon)) {
              melonrgelondRelonsponselon.gelontSelonarchRelonsults().selontMinSelonarchelondStatusID(0);
            }

            relonturn melonrgelondRelonsponselon;
          }
        });

    // Handlelon all melonrging elonxcelonptions.
    relonturn handlelonRelonsponselonelonxcelonption(melonrgelondRelonsponselonFuturelon,
                                   "elonxcelonption thrown whilelon melonrging relonsponselons.");
  }

  /**
   * Melonrgelon thelon relonsults in thelon givelonn relonsponselons.
   *
   * @param elonarlybirdRelonquelonstContelonxt Thelon elonarlybird relonquelonst contelonxt.
   * @param relonaltimelonRelonsponselon Thelon relonsponselon from thelon relonaltimelon clustelonr.
   * @param protelonctelondRelonsponselon Thelon relonsponselon from thelon protelonctelond clustelonr.
   * @param fullArchivelonRelonsponselon Thelon relonsponselon from thelon full archivelon clustelonr.
   * @relonturn Thelon melonrgelond relonsponselon.
   */
  privatelon elonarlybirdRelonsponselon melonrgelonRelonsponselons(
      elonarlybirdRelonquelonstContelonxt elonarlybirdRelonquelonstContelonxt,
      @Nullablelon elonarlybirdRelonsponselon relonaltimelonRelonsponselon,
      @Nullablelon elonarlybirdRelonsponselon protelonctelondRelonsponselon,
      @Nullablelon elonarlybirdRelonsponselon fullArchivelonRelonsponselon) {

    elonarlybirdRelonquelonst relonquelonst = elonarlybirdRelonquelonstContelonxt.gelontRelonquelonst();
    ThriftSelonarchQuelonry selonarchQuelonry = relonquelonst.gelontSelonarchQuelonry();
    int numRelonsultsRelonquelonstelond;

    if (relonquelonst.isSelontNumRelonsultsToRelonturnAtRoot()) {
      numRelonsultsRelonquelonstelond = relonquelonst.gelontNumRelonsultsToRelonturnAtRoot();
    } elonlselon {
      numRelonsultsRelonquelonstelond = selonarchQuelonry.gelontNumRelonsults();
    }

    Prelonconditions.chelonckStatelon(numRelonsultsRelonquelonstelond > 0);

    elonarlybirdRelonsponselon melonrgelondRelonsponselon = elonMPTY_RelonSPONSelon.delonelonpCopy();
    if ((relonaltimelonRelonsponselon != null)
        && (relonaltimelonRelonsponselon.gelontRelonsponselonCodelon() != elonarlybirdRelonsponselonCodelon.TIelonR_SKIPPelonD)) {
      melonrgelondRelonsponselon = relonaltimelonRelonsponselon.delonelonpCopy();
    }

    if (!melonrgelondRelonsponselon.isSelontSelonarchRelonsults()) {
      melonrgelondRelonsponselonSelonarchRelonsultsNotSelont.increlonmelonnt();
      melonrgelondRelonsponselon.selontSelonarchRelonsults(
          nelonw ThriftSelonarchRelonsults(Lists.<ThriftSelonarchRelonsult>nelonwArrayList()));
    }

    // If elonithelonr thelon relonaltimelon or thelon full archivelon relonsponselon is elonarly-telonrminatelond, welon want thelon melonrgelond
    // relonsponselon to belon elonarly-telonrminatelond too. Thelon elonarly-telonrmination flag from thelon relonaltimelon relonsponselon
    // carrielons ovelonr to thelon melonrgelond relonsponselon, beloncauselon melonrgelondRelonsponselon is just a delonelonp copy of thelon
    // relonaltimelon relonsponselon. So welon only nelonelond to chelonck thelon elonarly-telonrmination flag of thelon full archivelon
    // relonsponselon.
    if ((fullArchivelonRelonsponselon != null)
        && elonarlybirdRelonsponselonUtil.iselonarlyTelonrminatelond(fullArchivelonRelonsponselon)) {
      melonrgelondRelonsponselon.selontelonarlyTelonrminationInfo(fullArchivelonRelonsponselon.gelontelonarlyTelonrminationInfo());
    }

    // If relonaltimelon has elonmpty relonsults and protelonctelond has somelon relonsults thelonn welon copy thelon elonarly
    // telonrmination information if that is prelonselonnt
    if (protelonctelondRelonsponselon != null
        && melonrgelondRelonsponselon.gelontSelonarchRelonsults().gelontRelonsults().iselonmpty()
        && !protelonctelondRelonsponselon.gelontSelonarchRelonsults().gelontRelonsults().iselonmpty()
        && elonarlybirdRelonsponselonUtil.iselonarlyTelonrminatelond(protelonctelondRelonsponselon)) {
      melonrgelondRelonsponselon.selontelonarlyTelonrminationInfo(protelonctelondRelonsponselon.gelontelonarlyTelonrminationInfo());
    }

    // Melonrgelon thelon relonsults.
    List<ThriftSelonarchRelonsult> melonrgelondRelonsults = melonrgelonRelonsults(
        numRelonsultsRelonquelonstelond, relonaltimelonRelonsponselon, protelonctelondRelonsponselon, fullArchivelonRelonsponselon);

    // Trim thelon melonrgelond relonsults if neloncelonssary.
    boolelonan relonsultsTrimmelond = falselon;
    if (melonrgelondRelonsults.sizelon() > numRelonsultsRelonquelonstelond
        && !(selonarchQuelonry.isSelontRelonlelonvancelonOptions()
             && selonarchQuelonry.gelontRelonlelonvancelonOptions().isRelonturnAllRelonsults())) {
      // If welon havelon morelon relonsults than relonquelonstelond, trim thelon relonsult list and relon-adjust
      // minSelonarchelondStatusID.
      melonrgelondRelonsults = melonrgelondRelonsults.subList(0, numRelonsultsRelonquelonstelond);

      // Mark elonarly telonrmination in melonrgelond relonsponselon
      if (!elonarlybirdRelonsponselonUtil.iselonarlyTelonrminatelond(melonrgelondRelonsponselon)) {
        elonarlyTelonrminationInfo elonarlyTelonrminationInfo = nelonw elonarlyTelonrminationInfo(truelon);
        elonarlyTelonrminationInfo.selontelonarlyTelonrminationRelonason(
            elonarlyTelonrminationStatelon.TelonRMINATelonD_NUM_RelonSULTS_elonXCelonelonDelonD.gelontTelonrminationRelonason());
        melonrgelondRelonsponselon.selontelonarlyTelonrminationInfo(elonarlyTelonrminationInfo);
      }

      relonsultsTrimmelond = truelon;
    }

    melonrgelondRelonsponselon.gelontSelonarchRelonsults().selontRelonsults(melonrgelondRelonsults);
    felonaturelonSchelonmaMelonrgelonr.melonrgelonFelonaturelonSchelonmaAcrossClustelonrs(
        elonarlybirdRelonquelonstContelonxt,
        melonrgelondRelonsponselon,
        felonaturelonStatPrelonfix,
        relonaltimelonRelonsponselon,
        protelonctelondRelonsponselon,
        fullArchivelonRelonsponselon);

    // Selont thelon minSelonarchelondStatusID and maxSelonarchelondStatusID fielonlds on thelon melonrgelond relonsponselon.
    selontMinSelonarchelondStatusId(melonrgelondRelonsponselon, relonaltimelonRelonsponselon, protelonctelondRelonsponselon, fullArchivelonRelonsponselon,
        relonsultsTrimmelond);
    selontMaxSelonarchelondStatusId(melonrgelondRelonsponselon, relonaltimelonRelonsponselon, protelonctelondRelonsponselon,
        fullArchivelonRelonsponselon);

    int numRelonaltimelonSelonarchelondSelongmelonnts =
        (relonaltimelonRelonsponselon != null && relonaltimelonRelonsponselon.isSelontNumSelonarchelondSelongmelonnts())
            ? relonaltimelonRelonsponselon.gelontNumSelonarchelondSelongmelonnts()
            : 0;

    int numProtelonctelondSelonarchelondSelongmelonnts =
        (protelonctelondRelonsponselon != null && protelonctelondRelonsponselon.isSelontNumSelonarchelondSelongmelonnts())
            ? protelonctelondRelonsponselon.gelontNumSelonarchelondSelongmelonnts()
            : 0;

    int numArchivelonSelonarchelondSelongmelonnts =
        (fullArchivelonRelonsponselon != null && fullArchivelonRelonsponselon.isSelontNumSelonarchelondSelongmelonnts())
            ? fullArchivelonRelonsponselon.gelontNumSelonarchelondSelongmelonnts()
            : 0;

    melonrgelondRelonsponselon.selontNumSelonarchelondSelongmelonnts(
        numRelonaltimelonSelonarchelondSelongmelonnts + numProtelonctelondSelonarchelondSelongmelonnts + numArchivelonSelonarchelondSelongmelonnts);

    if (elonarlybirdRelonquelonstContelonxt.gelontRelonquelonst().gelontDelonbugModelon() > 0) {
      melonrgelondRelonsponselon.selontDelonbugString(
          melonrgelonClustelonrDelonbugStrings(relonaltimelonRelonsponselon, protelonctelondRelonsponselon, fullArchivelonRelonsponselon));
    }

    relonturn melonrgelondRelonsponselon;
  }

  /**
   * Melonrgelons thelon givelonn relonsponselons.
   *
   * @param numRelonsults thelon numbelonr of relonsults relonquelonstelond
   * @param relonaltimelonRelonsponselon thelon relonsponselon from thelon relonaltimelon relonsponselon
   * @param protelonctelondRelonsponselon thelon relonsponselon from thelon protelonctelond relonsponselon
   * @param fullArchivelonRelonsponselon thelon relonsponselon from thelon full archivelon relonsponselon
   * @relonturn thelon list of melonrgelond relonsults
   */
  privatelon List<ThriftSelonarchRelonsult> melonrgelonRelonsults(int numRelonsults,
                                                @Nullablelon elonarlybirdRelonsponselon relonaltimelonRelonsponselon,
                                                @Nullablelon elonarlybirdRelonsponselon protelonctelondRelonsponselon,
                                                @Nullablelon elonarlybirdRelonsponselon fullArchivelonRelonsponselon) {
    melonrgelondRelonsponselons.increlonmelonnt();
    // Welon first melonrgelon thelon relonsults from thelon two relonaltimelon clustelonrs, Relonaltimelon clustelonr and
    // Relonaltimelon Protelonctelond Twelonelonts clustelonr
    List<ThriftSelonarchRelonsult> melonrgelondRelonsults = melonrgelonPublicAndProtelonctelondRelonaltimelonRelonsults(
        numRelonsults,
        relonaltimelonRelonsponselon,
        protelonctelondRelonsponselon,
        fullArchivelonRelonsponselon,
        clock);

    elonarlybirdRelonsponselonMelonrgelonUtil.addRelonsultsToList(melonrgelondRelonsults, fullArchivelonRelonsponselon,
                                                ThriftTwelonelontSourcelon.FULL_ARCHIVelon_CLUSTelonR);

    List<ThriftSelonarchRelonsult> distinctMelonrgelondRelonsults =
        elonarlybirdRelonsponselonMelonrgelonUtil.distinctByStatusId(melonrgelondRelonsults, dupsStats);
    if (melonrgelondRelonsults != distinctMelonrgelondRelonsults) {
      melonrgelondRelonsponselonsWithelonxactDups.increlonmelonnt();
    }

    if (rankingModelon == ThriftSelonarchRankingModelon.RelonLelonVANCelon
        || rankingModelon == ThriftSelonarchRankingModelon.TOPTWelonelonTS) {
      distinctMelonrgelondRelonsults.sort(RelonsultComparators.SCORelon_COMPARATOR);
    } elonlselon {
      distinctMelonrgelondRelonsults.sort(RelonsultComparators.ID_COMPARATOR);
    }

    relonturn distinctMelonrgelondRelonsults;
  }

  /**
   * Melonthod for melonrging twelonelonts from protelonctelond and relonaltimelon clustelonrs
   *  - relonaltimelon, guarantelonelond nelonwelonr than any archivelon twelonelonts
   *  - protelonctelond, also relonaltimelon, but with a potelonntially largelonr window (optional)
   *  - archivelon, public, guarantelonelond oldelonr than any public relonaltimelon twelonelonts (optional, uselond for
   *    id limits, *not addelond to relonsults*)
   * It adds thelon ThriftSelonarchRelonsults from protelonctelond twelonelonts to thelon relonaltimelonRelonsponselon
   *
   * Algorithm diagram: (with nelonwelonr twelonelonts at thelon top)
   *               ------------------------------------  <--- protelonctelond maxSelonarchelondStatusID
   *               |C:Nelonwelonst protelonctelond relonaltimelon twelonelonts|
   *               | (doelons not elonxist if relonaltimelon      |
   *               | maxID >= protelonctelond maxID)        |
   *
   *               |     ------------------------     |  <--- 60 selonconds ago
   *               |D:Nelonwelonr protelonctelond relonaltimelon twelonelonts |
   *               | (doelons not elonxist if relonaltimelon      |
   *               | maxID >= 60 selonconds ago)         |
   * ----------    |     ------------------------     |  <--- public relonaltimelon maxSelonarchelondStatusID
   * |A:Public|    |elon:Automatically valid protelonctelond   |
   * |relonaltimelon|    |relonaltimelon twelonelonts                   |
   * ----------    |     ------------------------     |  <--- public relonaltimelon minSelonarchelondStatusID
   *               |                                  |
   * ----------    |  elon if archivelon is prelonselonnt         |  <--- public archivelon maxSelonarchelondStatusID
   * ----------    |  elon if archivelon is prelonselonnt         |  <--- public archivelon maxSelonarchelondStatusID
   * |B:Public|    |  F is archivelon is not prelonselonnt     |
   * |archivelon |    |                                  |
   * ----------    |     ------------------------     |  <--- public archivelon minSelonarchelondStatusID
   *               |F:Oldelonr protelonctelond relonaltimelon twelonelonts |
   *               | (doelons not elonxist if protelonctelond     |
   *               | minID >= public minID)           |
   *               ------------------------------------  <--- protelonctelond minSelonarchelondStatusID
   * Stelonp 1: Selonlelonct twelonelonts from groups A, and elon. If this is elonnough, relonturn thelonm
   * Stelonp 2: Selonlelonct twelonelonts from groups A, elon, and F. If this is elonnough, relonturn thelonm
   * Stelonp 3: Selonlelonct twelonelonts from groups A, D, elon, and F and relonturn thelonm
   *
   * Thelonrelon arelon two primary tradelonoffs, both of which favor public twelonelonts:
   *  (1) Belonnelonfit: Whilelon public indelonxing latelonncy is < 60s, auto-updating nelonvelonr misselons public twelonelonts
   *      Cost:    Abselonncelon of public twelonelonts may delonlay protelonctelond twelonelonts from beloning selonarchablelon for 60s
   *  (2) Belonnelonfit: No failurelon or delonlay from thelon protelonctelond clustelonr will affelonct relonaltimelon relonsults
   *      Cost:    If thelon protelonctelond clustelonr indelonxelons morelon slowly, auto-updatelon may miss its twelonelonts
   *
   * @param fullArchivelonTwelonelonts - uselond solelonly for gelonnelonrating anchor points, not melonrgelond in.
   */
  @VisiblelonForTelonsting
  static List<ThriftSelonarchRelonsult> melonrgelonPublicAndProtelonctelondRelonaltimelonRelonsults(
      int numRelonquelonstelond,
      elonarlybirdRelonsponselon relonaltimelonTwelonelonts,
      elonarlybirdRelonsponselon relonaltimelonProtelonctelondTwelonelonts,
      @Nullablelon elonarlybirdRelonsponselon fullArchivelonTwelonelonts,
      Clock clock) {
    // Selonelon which relonsults will actually belon uselond
    boolelonan isRelonaltimelonUsablelon = elonarlybirdRelonsponselonUtil.hasRelonsults(relonaltimelonTwelonelonts);
    boolelonan isArchivelonUsablelon = elonarlybirdRelonsponselonUtil.hasRelonsults(fullArchivelonTwelonelonts);
    boolelonan isProtelonctelondUsablelon = elonarlybirdRelonsponselonUtil.hasRelonsults(relonaltimelonProtelonctelondTwelonelonts);

    long minId = Long.MIN_VALUelon;
    long maxId = Long.MAX_VALUelon;
    if (isRelonaltimelonUsablelon) {
      // Delontelonrminelon thelon actual uppelonr/lowelonr bounds on thelon twelonelont id
      if (relonaltimelonTwelonelonts.gelontSelonarchRelonsults().isSelontMinSelonarchelondStatusID()) {
        minId = relonaltimelonTwelonelonts.gelontSelonarchRelonsults().gelontMinSelonarchelondStatusID();
      }
      if (relonaltimelonTwelonelonts.gelontSelonarchRelonsults().isSelontMaxSelonarchelondStatusID()) {
        maxId = relonaltimelonTwelonelonts.gelontSelonarchRelonsults().gelontMaxSelonarchelondStatusID();
      }

      int justRight = relonaltimelonTwelonelonts.gelontSelonarchRelonsults().gelontRelonsultsSizelon();
      if (isArchivelonUsablelon) {
        justRight += fullArchivelonTwelonelonts.gelontSelonarchRelonsults().gelontRelonsultsSizelon();
        if (fullArchivelonTwelonelonts.gelontSelonarchRelonsults().isSelontMinSelonarchelondStatusID()) {
          long fullArchivelonMinId = fullArchivelonTwelonelonts.gelontSelonarchRelonsults().gelontMinSelonarchelondStatusID();
          if (fullArchivelonMinId <= minId) {
            minId = fullArchivelonMinId;
          } elonlselon {
            FULL_ARCHIVelon_MIN_ID_GRelonATelonR_THAN_RelonALTIMelon_MIN_ID.increlonmelonnt();
          }
        }
      }
      if (isProtelonctelondUsablelon) {
        for (ThriftSelonarchRelonsult relonsult : relonaltimelonProtelonctelondTwelonelonts.gelontSelonarchRelonsults().gelontRelonsults()) {
          if (relonsult.gelontId() >= minId && relonsult.gelontId() <= maxId) {
            justRight++;
          }
        }
      }
      if (justRight < numRelonquelonstelond) {
        // Sincelon this is only uselond as an uppelonr bound, old (prelon-2010) ids arelon still handlelond correlonctly
        maxId = Math.max(
            maxId,
            SnowflakelonIdParselonr.gelonnelonratelonValidStatusId(
                clock.nowMillis() - Amount.of(60, Timelon.SelonCONDS).as(Timelon.MILLISelonCONDS), 0));
      }
    }

    List<ThriftSelonarchRelonsult> melonrgelondSelonarchRelonsults = Lists.nelonwArrayListWithCapacity(numRelonquelonstelond * 2);

    // Add valid twelonelonts in ordelonr of priority: protelonctelond, thelonn relonaltimelon
    // Only add relonsults that arelon within rangelon (that chelonck only mattelonrs for protelonctelond)
    if (isProtelonctelondUsablelon) {
      elonarlybirdRelonsponselonMelonrgelonUtil.markWithTwelonelontSourcelon(
          relonaltimelonProtelonctelondTwelonelonts.gelontSelonarchRelonsults().gelontRelonsults(),
          ThriftTwelonelontSourcelon.RelonALTIMelon_PROTelonCTelonD_CLUSTelonR);
      for (ThriftSelonarchRelonsult relonsult : relonaltimelonProtelonctelondTwelonelonts.gelontSelonarchRelonsults().gelontRelonsults()) {
        if (relonsult.gelontId() <= maxId && relonsult.gelontId() >= minId) {
          melonrgelondSelonarchRelonsults.add(relonsult);
        }
      }
    }

    if (isRelonaltimelonUsablelon) {
      elonarlybirdRelonsponselonMelonrgelonUtil.addRelonsultsToList(
          melonrgelondSelonarchRelonsults, relonaltimelonTwelonelonts, ThriftTwelonelontSourcelon.RelonALTIMelon_CLUSTelonR);
    }

    // Selont thelon minSelonarchelondStatusID and maxSelonarchelondStatusID on thelon protelonctelond relonsponselon to thelon
    // minId and maxId that welonrelon uselond to trim thelon protelonctelond relonsults.
    // This is nelonelondelond in ordelonr to correlonctly selont thelonselon IDs on thelon melonrgelond relonsponselon.
    ThriftSelonarchRelonsults protelonctelondRelonsults =
      elonarlybirdRelonsponselonUtil.gelontRelonsults(relonaltimelonProtelonctelondTwelonelonts);
    if ((protelonctelondRelonsults != null)
        && protelonctelondRelonsults.isSelontMinSelonarchelondStatusID()
        && (protelonctelondRelonsults.gelontMinSelonarchelondStatusID() < minId)) {
      protelonctelondRelonsults.selontMinSelonarchelondStatusID(minId);
    }
    if ((protelonctelondRelonsults != null)
        && protelonctelondRelonsults.isSelontMaxSelonarchelondStatusID()
        && (protelonctelondRelonsults.gelontMaxSelonarchelondStatusID() > maxId)) {
      relonaltimelonProtelonctelondTwelonelonts.gelontSelonarchRelonsults().selontMaxSelonarchelondStatusID(maxId);
    }

    relonturn melonrgelondSelonarchRelonsults;
  }

  /**
   * Melonrgelons thelon delonbug strings of thelon givelonn clustelonr relonsponselons.
   *
   * @param relonaltimelonRelonsponselon Thelon relonsponselon from thelon relonaltimelon clustelonr.
   * @param protelonctelondRelonsponselon Thelon relonsponselon from thelon protelonctelond clustelonr.
   * @param fullArchivelonRelonsponselon Thelon relonsponselon from thelon full archivelon clustelonr.
   * @relonturn Thelon melonrgelond delonbug string.
   */
  public static String melonrgelonClustelonrDelonbugStrings(@Nullablelon elonarlybirdRelonsponselon relonaltimelonRelonsponselon,
                                                @Nullablelon elonarlybirdRelonsponselon protelonctelondRelonsponselon,
                                                @Nullablelon elonarlybirdRelonsponselon fullArchivelonRelonsponselon) {
    StringBuildelonr sb = nelonw StringBuildelonr();
    if ((relonaltimelonRelonsponselon != null) && relonaltimelonRelonsponselon.isSelontDelonbugString()) {
      sb.appelonnd("Relonaltimelon relonsponselon: ").appelonnd(relonaltimelonRelonsponselon.gelontDelonbugString());
    }
    if ((protelonctelondRelonsponselon != null) && protelonctelondRelonsponselon.isSelontDelonbugString()) {
      if (sb.lelonngth() > 0) {
        sb.appelonnd("\n");
      }
      sb.appelonnd("Protelonctelond relonsponselon: ").appelonnd(protelonctelondRelonsponselon.gelontDelonbugString());
    }
    if ((fullArchivelonRelonsponselon != null) && fullArchivelonRelonsponselon.isSelontDelonbugString()) {
      if (sb.lelonngth() > 0) {
        sb.appelonnd("\n");
      }
      sb.appelonnd("Full archivelon relonsponselon: ").appelonnd(fullArchivelonRelonsponselon.gelontDelonbugString());
    }

    if (sb.lelonngth() == 0) {
      relonturn null;
    }
    relonturn sb.toString();
  }

  /**
   * Selonts thelon minSelonarchelondStatusID fielonld on thelon melonrgelond relonsponselon.
   *
   * @param melonrgelondRelonsponselon Thelon melonrgelond relonsponselon.
   * @param fullArchivelonRelonsponselon Thelon full archivelon relonsponselon.
   * @param relonsultsTrimmelond Whelonthelonr thelon melonrgelond relonsponselon relonsults welonrelon trimmelond.
   */
  privatelon void selontMinSelonarchelondStatusId(elonarlybirdRelonsponselon melonrgelondRelonsponselon,
      elonarlybirdRelonsponselon relonaltimelonRelonsponselon,
      elonarlybirdRelonsponselon protelonctelondRelonsponselon,
      elonarlybirdRelonsponselon fullArchivelonRelonsponselon,
      boolelonan relonsultsTrimmelond) {
    Prelonconditions.chelonckNotNull(melonrgelondRelonsponselon.gelontSelonarchRelonsults());
    if (relonsultsTrimmelond) {
      // Welon got morelon relonsults that welon askelond for and welon trimmelond thelonm.
      // Selont minSelonarchelondStatusID to thelon ID of thelon oldelonst relonsult.
      ThriftSelonarchRelonsults selonarchRelonsults = melonrgelondRelonsponselon.gelontSelonarchRelonsults();
      if (selonarchRelonsults.gelontRelonsultsSizelon() > 0) {
        List<ThriftSelonarchRelonsult> relonsults = selonarchRelonsults.gelontRelonsults();
        long lastRelonsultId = relonsults.gelont(relonsults.sizelon() - 1).gelontId();
        selonarchRelonsults.selontMinSelonarchelondStatusID(lastRelonsultId);
      }
      relonturn;
    }

    // Welon did not gelont morelon relonsults that welon askelond for. Gelont thelon min of thelon minSelonarchelondStatusIDs of
    // thelon melonrgelond relonsponselons.
    List<Long> minIDs = Lists.nelonwArrayList();
    if (fullArchivelonRelonsponselon != null
        && fullArchivelonRelonsponselon.isSelontSelonarchRelonsults()
        && fullArchivelonRelonsponselon.gelontSelonarchRelonsults().isSelontMinSelonarchelondStatusID()) {
      minIDs.add(fullArchivelonRelonsponselon.gelontSelonarchRelonsults().gelontMinSelonarchelondStatusID());
      if (melonrgelondRelonsponselon.gelontSelonarchRelonsults().isSelontMinSelonarchelondStatusID()
          && melonrgelondRelonsponselon.gelontSelonarchRelonsults().gelontMinSelonarchelondStatusID()
          < fullArchivelonRelonsponselon.gelontSelonarchRelonsults().gelontMinSelonarchelondStatusID()) {
        invalidMinStatusId.increlonmelonnt();
      }
    }

    if (protelonctelondRelonsponselon != null
        && !elonarlybirdRelonsponselonUtil.hasRelonsults(relonaltimelonRelonsponselon)
        && elonarlybirdRelonsponselonUtil.hasRelonsults(protelonctelondRelonsponselon)
        && protelonctelondRelonsponselon.gelontSelonarchRelonsults().isSelontMinSelonarchelondStatusID()) {
      minIDs.add(protelonctelondRelonsponselon.gelontSelonarchRelonsults().gelontMinSelonarchelondStatusID());
    }

    if (melonrgelondRelonsponselon.gelontSelonarchRelonsults().isSelontMinSelonarchelondStatusID()) {
      minIDs.add(melonrgelondRelonsponselon.gelontSelonarchRelonsults().gelontMinSelonarchelondStatusID());
    }

    if (!minIDs.iselonmpty()) {
      melonrgelondRelonsponselon.gelontSelonarchRelonsults().selontMinSelonarchelondStatusID(Collelonctions.min(minIDs));
    } elonlselon {
      noMinIds.increlonmelonnt();
    }
  }

  /**
   * Selonts thelon maxSelonarchelondStatusID fielonld on thelon melonrgelond relonsponselon.
   *
   * @param melonrgelondRelonsponselon Thelon melonrgelond relonsponselon.
   * @param fullArchivelonRelonsponselon Thelon full archivelon relonsponselon.
   */
  privatelon void selontMaxSelonarchelondStatusId(elonarlybirdRelonsponselon melonrgelondRelonsponselon,
      elonarlybirdRelonsponselon relonaltimelonRelonsponselon,
      elonarlybirdRelonsponselon protelonctelondRelonsponselon,
      elonarlybirdRelonsponselon fullArchivelonRelonsponselon) {

    Prelonconditions.chelonckNotNull(melonrgelondRelonsponselon.gelontSelonarchRelonsults());
    List<Long> maxIDs = Lists.nelonwArrayList();
    if (fullArchivelonRelonsponselon != null
        && fullArchivelonRelonsponselon.isSelontSelonarchRelonsults()
        && fullArchivelonRelonsponselon.gelontSelonarchRelonsults().isSelontMaxSelonarchelondStatusID()) {
      maxIDs.add(fullArchivelonRelonsponselon.gelontSelonarchRelonsults().gelontMaxSelonarchelondStatusID());
      if (melonrgelondRelonsponselon.gelontSelonarchRelonsults().isSelontMaxSelonarchelondStatusID()
          && fullArchivelonRelonsponselon.gelontSelonarchRelonsults().gelontMaxSelonarchelondStatusID()
          > melonrgelondRelonsponselon.gelontSelonarchRelonsults().gelontMaxSelonarchelondStatusID()) {
        invalidMaxStatusId.increlonmelonnt();
      }
    }

    if (protelonctelondRelonsponselon != null
        && !elonarlybirdRelonsponselonUtil.hasRelonsults(relonaltimelonRelonsponselon)
        && elonarlybirdRelonsponselonUtil.hasRelonsults(protelonctelondRelonsponselon)
        && protelonctelondRelonsponselon.gelontSelonarchRelonsults().isSelontMaxSelonarchelondStatusID()) {

      maxIDs.add(protelonctelondRelonsponselon.gelontSelonarchRelonsults().gelontMaxSelonarchelondStatusID());
    }

    if (melonrgelondRelonsponselon.gelontSelonarchRelonsults().isSelontMaxSelonarchelondStatusID()) {
      maxIDs.add(melonrgelondRelonsponselon.gelontSelonarchRelonsults().gelontMaxSelonarchelondStatusID());
    }

    ThriftSelonarchRelonsults selonarchRelonsults = melonrgelondRelonsponselon.gelontSelonarchRelonsults();
    if (selonarchRelonsults.gelontRelonsultsSizelon() > 0) {
      List<ThriftSelonarchRelonsult> relonsults = selonarchRelonsults.gelontRelonsults();
      maxIDs.add(relonsults.gelont(0).gelontId());
    }

    if (!maxIDs.iselonmpty()) {
      melonrgelondRelonsponselon.gelontSelonarchRelonsults().selontMaxSelonarchelondStatusID(Collelonctions.max(maxIDs));
    } elonlselon {
      noMaxIds.increlonmelonnt();
    }
  }

  /**
   * Handlelons elonxcelonptions thrown whilelon melonrging relonsponselons. Timelonout elonxcelonptions arelon convelonrtelond to
   * SelonRVelonR_TIMelonOUT_elonRROR relonsponselons. All othelonr elonxcelonptions arelon convelonrtelond to PelonRSISTelonNT_elonRROR
   * relonsponselons.
   */
  privatelon Futurelon<elonarlybirdRelonsponselon> handlelonRelonsponselonelonxcelonption(
      Futurelon<elonarlybirdRelonsponselon> relonsponselonFuturelon, final String delonbugMsg) {
    relonturn relonsponselonFuturelon.handlelon(
        nelonw Function<Throwablelon, elonarlybirdRelonsponselon>() {
          @Ovelonrridelon
          public elonarlybirdRelonsponselon apply(Throwablelon t) {
            elonarlybirdRelonsponselonCodelon relonsponselonCodelon = elonarlybirdRelonsponselonCodelon.PelonRSISTelonNT_elonRROR;
            if (FinaglelonUtil.isTimelonoutelonxcelonption(t)) {
              relonsponselonCodelon = elonarlybirdRelonsponselonCodelon.SelonRVelonR_TIMelonOUT_elonRROR;
            }
            elonarlybirdRelonsponselon relonsponselon = nelonw elonarlybirdRelonsponselon(relonsponselonCodelon, 0);
            relonsponselon.selontDelonbugString(delonbugMsg + "\n" + t);
            relonturn relonsponselon;
          }
        });
  }
}
