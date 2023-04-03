packagelon com.twittelonr.selonarch.elonarlybird;

import java.io.IOelonxcelonption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Itelonrator;
import java.util.List;
import java.util.Map;
import java.util.Selont;
import java.util.strelonam.Collelonctors;
import javax.annotation.Nonnull;
import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Joinelonr;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonMap;
import com.googlelon.common.collelonct.ImmutablelonSelont;
import com.googlelon.common.collelonct.Lists;

import org.apachelon.commons.lang.StringUtils;
import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.quelonryparselonr.classic.Parselonelonxcelonption;
import org.apachelon.lucelonnelon.quelonryparselonr.classic.QuelonryParselonr;
import org.apachelon.lucelonnelon.selonarch.BoolelonanClauselon.Occur;
import org.apachelon.lucelonnelon.selonarch.BoolelonanQuelonry;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.thrift.Telonxcelonption;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.util.Clock;
import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.selonarch.common.databaselon.DatabaselonConfig;
import com.twittelonr.selonarch.common.deloncidelonr.DeloncidelonrUtil;
import com.twittelonr.selonarch.common.felonaturelons.thrift.ThriftSelonarchFelonaturelonSchelonma;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonr;
import com.twittelonr.selonarch.common.partitioning.baselon.Selongmelonnt;
import com.twittelonr.selonarch.common.quelonry.MappablelonFielonld;
import com.twittelonr.selonarch.common.quelonry.QuelonryHitAttributelonHelonlpelonr;
import com.twittelonr.selonarch.common.quelonry.thriftjava.CollelonctorParams;
import com.twittelonr.selonarch.common.quelonry.thriftjava.CollelonctorTelonrminationParams;
import com.twittelonr.selonarch.common.quelonry.thriftjava.elonarlyTelonrminationInfo;
import com.twittelonr.selonarch.common.ranking.thriftjava.ThriftRankingParams;
import com.twittelonr.selonarch.common.ranking.thriftjava.ThriftScoringFunctionTypelon;
import com.twittelonr.selonarch.common.relonsults.thriftjava.FielonldHitList;
import com.twittelonr.selonarch.common.schelonma.SchelonmaUtil;
import com.twittelonr.selonarch.common.schelonma.SelonarchWhitelonspacelonAnalyzelonr;
import com.twittelonr.selonarch.common.schelonma.baselon.FielonldWelonightDelonfault;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.selonarch.TelonrminationTrackelonr;
import com.twittelonr.selonarch.common.selonarch.TwittelonrelonarlyTelonrminationCollelonctor;
import com.twittelonr.selonarch.common.selonarch.telonrmination.QuelonryTimelonoutFactory;
import com.twittelonr.selonarch.common.util.elonarlybird.elonarlybirdRelonsponselonUtil;
import com.twittelonr.selonarch.common.util.ml.telonnsorflow_elonnginelon.TelonnsorflowModelonlsManagelonr;
import com.twittelonr.selonarch.common.util.thrift.ThriftUtils;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontCountStatelon;
import com.twittelonr.selonarch.elonarlybird.common.ClielonntIdUtil;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.Clielonntelonxcelonption;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.Transielonntelonxcelonption;
import com.twittelonr.selonarch.elonarlybird.indelonx.facelonts.FacelontSkipList;
import com.twittelonr.selonarch.elonarlybird.ml.ScoringModelonlsManagelonr;
import com.twittelonr.selonarch.elonarlybird.partition.AudioSpacelonTablelon;
import com.twittelonr.selonarch.elonarlybird.partition.MultiSelongmelonntTelonrmDictionaryManagelonr;
import com.twittelonr.selonarch.elonarlybird.partition.PartitionConfig;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntInfo;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntManagelonr;
import com.twittelonr.selonarch.elonarlybird.quelonrycachelon.QuelonryCachelonConvelonrsionRulelons;
import com.twittelonr.selonarch.elonarlybird.quelonrycachelon.QuelonryCachelonManagelonr;
import com.twittelonr.selonarch.elonarlybird.quelonryparselonr.DelontelonctFielonldAnnotationVisitor;
import com.twittelonr.selonarch.elonarlybird.quelonryparselonr.elonarlybirdLucelonnelonQuelonryVisitor;
import com.twittelonr.selonarch.elonarlybird.quelonryparselonr.HighFrelonquelonncyTelonrmPairRelonwritelonVisitor;
import com.twittelonr.selonarch.elonarlybird.quelonryparselonr.LucelonnelonRelonlelonvancelonQuelonryVisitor;
import com.twittelonr.selonarch.elonarlybird.quelonryparselonr.ProtelonctelondOpelonratorQuelonryRelonwritelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.AbstractRelonsultsCollelonctor;
import com.twittelonr.selonarch.elonarlybird.selonarch.AntiGamingFiltelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons.BadUselonrRelonpFiltelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.elonarlybirdLucelonnelonSelonarchelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.elonarlybirdMultiSelongmelonntSelonarchelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons.MatchAllDocsQuelonry;
import com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons.RelonquirelondStatusIDsFiltelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.SelonarchRelonquelonstInfo;
import com.twittelonr.selonarch.elonarlybird.selonarch.SelonarchRelonsultsCollelonctor;
import com.twittelonr.selonarch.elonarlybird.selonarch.SelonarchRelonsultsInfo;
import com.twittelonr.selonarch.elonarlybird.selonarch.SimplelonSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird.selonarch.SocialFiltelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.SocialSelonarchRelonsultsCollelonctor;
import com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons.UselonrFlagselonxcludelonFiltelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons.UselonrIdMultiSelongmelonntQuelonry;
import com.twittelonr.selonarch.elonarlybird.selonarch.facelonts.elonntityAnnotationCollelonctor;
import com.twittelonr.selonarch.elonarlybird.selonarch.facelonts.elonxpandelondUrlCollelonctor;
import com.twittelonr.selonarch.elonarlybird.selonarch.facelonts.elonxplainFacelontRelonsultsCollelonctor;
import com.twittelonr.selonarch.elonarlybird.selonarch.facelonts.FacelontRankingModulelon;
import com.twittelonr.selonarch.elonarlybird.selonarch.facelonts.FacelontRelonsultsCollelonctor;
import com.twittelonr.selonarch.elonarlybird.selonarch.facelonts.FacelontSelonarchRelonquelonstInfo;
import com.twittelonr.selonarch.elonarlybird.selonarch.facelonts.NamelondelonntityCollelonctor;
import com.twittelonr.selonarch.elonarlybird.selonarch.facelonts.SpacelonFacelontCollelonctor;
import com.twittelonr.selonarch.elonarlybird.selonarch.facelonts.TelonrmStatisticsCollelonctor;
import com.twittelonr.selonarch.elonarlybird.selonarch.facelonts.TelonrmStatisticsRelonquelonstInfo;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.RelonlelonvancelonSelonarchRelonquelonstInfo;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.RelonlelonvancelonSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.collelonctors.AbstractRelonlelonvancelonCollelonctor;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.collelonctors.BatchRelonlelonvancelonTopCollelonctor;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.collelonctors.RelonlelonvancelonAllCollelonctor;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.collelonctors.RelonlelonvancelonTopCollelonctor;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring.RelonlelonvancelonQuelonry;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring.ScoringFunction;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring.ScoringFunctionProvidelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring.TelonnsorflowBaselondScoringFunction;
import com.twittelonr.selonarch.elonarlybird.stats.elonarlybirdRPCStats;
import com.twittelonr.selonarch.elonarlybird.stats.elonarlybirdSelonarchelonrStats;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdDelonbugInfo;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontCount;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontCountMelontadata;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontFielonldRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontFielonldRelonsults;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontRelonsults;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchQuelonry;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRankingModelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonlelonvancelonOptions;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultelonxtraMelontadata;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultMelontadataOptions;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftTelonrmRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftTelonrmStatisticsRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftTelonrmStatisticsRelonsults;
import com.twittelonr.selonarch.elonarlybird.util.elonarlybirdSelonarchRelonsultUtil;
import com.twittelonr.selonarch.quelonryparselonr.parselonr.SelonrializelondQuelonryParselonr;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Conjunction;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Disjunction;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryNodelonUtils;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.annotation.Annotation;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchOpelonrator;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchOpelonratorConstants;
import com.twittelonr.selonarch.quelonryparselonr.util.IdTimelonRangelons;
import com.twittelonr.selonarch.quelonryparselonr.visitors.ConvelonrsionVisitor;
import com.twittelonr.selonarch.quelonryparselonr.visitors.DelontelonctPositivelonOpelonratorVisitor;
import com.twittelonr.selonarch.quelonryparselonr.visitors.NamelondDisjunctionVisitor;
import com.twittelonr.selonarch.quelonryparselonr.visitors.ProximityGroupRelonwritelonVisitor;
import com.twittelonr.selonarch.quelonryparselonr.visitors.StripAnnotationsVisitor;

import static com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchOpelonrator.Typelon.UNTIL_TIMelon;

/**
 * This class providelons thelon basic selonarch() melonthod:
 * - convelonrts thelon thrift relonquelonst objelonct into what lucelonnelon elonxpeloncts.
 * - gelonts thelon selongmelonnt.
 * - handlelons all elonrrors, and prelonparelons thelon relonsponselon in caselon of elonrror.
 *
 * Welon havelon onelon instancelon of this class pelonr selonarch reloncelonivelond.
 */
public class elonarlybirdSelonarchelonr {
  public elonnum QuelonryModelon {
    // Plelonaselon think belonforelon adding morelon quelonry modelons: can this belon implelonmelonntelond in a gelonnelonral way?
    RelonCelonNCY(nelonw elonarlybirdRPCStats("selonarch_reloncelonncy")),
    FACelonTS(nelonw elonarlybirdRPCStats("selonarch_facelonts")),
    TelonRM_STATS(nelonw elonarlybirdRPCStats("selonarch_telonrmstats")),
    RelonLelonVANCelon(nelonw elonarlybirdRPCStats("selonarch_relonlelonvancelon")),
    TOP_TWelonelonTS(nelonw elonarlybirdRPCStats("selonarch_toptwelonelonts"));

    privatelon final elonarlybirdRPCStats relonquelonstStats;

    QuelonryModelon(elonarlybirdRPCStats relonquelonstStats) {
      this.relonquelonstStats = relonquelonstStats;
    }

    public elonarlybirdRPCStats gelontRelonquelonstStats() {
      relonturn relonquelonstStats;
    }
  }

  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonarlybirdSelonarchelonr.class);
  privatelon static final String MATCH_ALL_SelonRIALIZelonD_QUelonRY = "(* )";
  /**
   * gelonnelonric fielonld annotations can belon mappelond to a concrelontelon fielonld in thelon indelonx using this mapping
   * via {@link com.twittelonr.selonarch.quelonryparselonr.quelonry.annotation.Annotation.Typelon#MAPPABLelon_FIelonLD}
   */
  privatelon static final Map<MappablelonFielonld, String> MAPPABLelon_FIelonLD_MAP =
      ImmutablelonMap.of(
          MappablelonFielonld.URL,
          elonarlybirdFielonldConstant.RelonSOLVelonD_LINKS_TelonXT_FIelonLD.gelontFielonldNamelon());

  privatelon static final String ALLOW_QUelonRY_SPelonCIFIC_SIGNAL_DelonCIDelonR_KelonY
      = "allow_quelonry_speloncific_scorelon_adjustmelonnts";

  @VisiblelonForTelonsting
  public static final String ALLOW_AUTHOR_SPelonCIFIC_SIGNAL_DelonCIDelonR_KelonY
      = "allow_author_speloncific_scorelon_adjustmelonnts";

  privatelon static final String USelon_MULTI_TelonRM_DISJUNCTION_FOR_LIKelonD_BY_USelonR_IDS_DelonCIDelonR_KelonY
      = "uselon_multi_telonrm_disjunction_for_likelond_by_uselonr_ids";

  privatelon static final String ALLOW_CAMelonLCASelon_USelonRNAMelon_FIelonLD_WelonIGHT_OVelonRRIDelon_DelonCIDelonR_KelonY_PRelonFIX
      = "allow_camelonlcaselon_uselonrnamelon_fielonld_welonight_ovelonrridelon_in_";

  privatelon static final String ALLOW_TOKelonNIZelonD_DISPLAY_NAMelon_FIelonLD_WelonIGHT_OVelonRRIDelon_DelonCIDelonR_KelonY_PRelonFIX
      = "allow_tokelonnizelond_display_namelon_fielonld_welonight_ovelonrridelon_in_";

  privatelon static final boolelonan ALLOW_QUelonRY_SPelonCIFIC_SIGNAL_CONFIG
      = elonarlybirdConfig.gelontBool("allow_quelonry_speloncific_scorelon_adjustmelonnts", falselon);

  privatelon static final boolelonan ALLOW_AUTHOR_SPelonCIFIC_SIGNAL_CONFIG
      = elonarlybirdConfig.gelontBool("allow_author_speloncific_scorelon_adjustmelonnts", falselon);

  public static final int DelonFAULT_NUM_FACelonT_RelonSULTS = 100;

  privatelon final ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot;
  privatelon final elonarlybirdClustelonr clustelonr;

  privatelon final Clock clock;
  privatelon final Deloncidelonr deloncidelonr;

  // Thelon actual relonquelonst thrift.
  privatelon final elonarlybirdRelonquelonst relonquelonst;

  // selonarchQuelonry from insidelon thelon relonquelonst.
  privatelon final ThriftSelonarchQuelonry selonarchQuelonry;

  // CollelonctorParams from insidelon thelon selonarchQuelonry;
  privatelon final CollelonctorParams collelonctorParams;

  // Parselond quelonry (parselond from selonrializelond quelonry string in relonquelonst).
  privatelon com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry parselondQuelonry;
  privatelon boolelonan parselondQuelonryAllowNullcast;
  privatelon IdTimelonRangelons idTimelonRangelons;

  // Lucelonnelon velonrsion of thelon abovelon.  This is what welon will actually belon elonxeloncuting.
  privatelon org.apachelon.lucelonnelon.selonarch.Quelonry lucelonnelonQuelonry;

  // Uselond for quelonrielons whelonrelon welon want to collelonct pelonr-fielonld hit attribution
  @Nullablelon
  privatelon QuelonryHitAttributelonHelonlpelonr hitAttributelonHelonlpelonr;

  // Delonbugging info can belon appelonndelond to this buffelonr.
  privatelon final StringBuildelonr melonssagelonBuffelonr = nelonw StringBuildelonr(1024);
  privatelon final elonarlybirdDelonbugInfo delonbugInfo = nelonw elonarlybirdDelonbugInfo();

  // Thelon selongmelonnt welon arelon selonarching, or null for thelon multi-selonarchelonr.
  privatelon Selongmelonnt selongmelonnt = null;

  // Truelon iff welon arelon selonarching all selongmelonnts (multi-selonarchelonr).
  privatelon final boolelonan selonarchAllSelongmelonnts;

  // Tracking telonrmination critelonria for this quelonry
  privatelon final TelonrminationTrackelonr telonrminationTrackelonr;

  privatelon elonarlybirdLucelonnelonSelonarchelonr selonarchelonr = null;

  privatelon final SelongmelonntManagelonr selongmelonntManagelonr;
  privatelon final QuelonryCachelonManagelonr quelonryCachelonManagelonr;
  privatelon final ScoringModelonlsManagelonr scoringModelonlsManagelonr;
  privatelon final TelonnsorflowModelonlsManagelonr telonnsorflowModelonlsManagelonr;

  privatelon AntiGamingFiltelonr antiGamingFiltelonr = null;

  privatelon final boolelonan selonarchHighFrelonquelonncyTelonrmPairs =
      elonarlybirdConfig.gelontBool("selonarch_high_frelonquelonncy_telonrm_pairs", falselon);

  // How long to allow post-telonrmination whelonn elonnforcing quelonry timelonout
  privatelon final int elonnforcelonQuelonryTimelonoutBuffelonrMillis =
      elonarlybirdConfig.gelontInt("elonnforcelon_quelonry_timelonout_buffelonr_millis", 50);

  privatelon elonarlybirdRPCStats relonquelonstStats;

  privatelon QuelonryTimelonoutFactory quelonryTimelonoutFactory;

  // elonxportelond stats
  privatelon final elonarlybirdSelonarchelonrStats selonarchelonrStats;

  @VisiblelonForTelonsting
  public static final SelonarchCountelonr FIelonLD_WelonIGHT_OVelonRRIDelon_MAP_NON_NULL_COUNT =
      SelonarchCountelonr.elonxport("fielonld_welonight_ovelonrridelon_map_non_null_count");
  @VisiblelonForTelonsting
  public static final SelonarchCountelonr DROPPelonD_CAMelonLCASelon_USelonRNAMelon_FIelonLD_WelonIGHT_OVelonRRIDelon =
      SelonarchCountelonr.elonxport("droppelond_camelonlcaselon_uselonrnamelon_fielonld_welonight_ovelonrridelon");
  @VisiblelonForTelonsting
  public static final SelonarchCountelonr DROPPelonD_TOKelonNIZelonD_DISPLAY_NAMelon_FIelonLD_WelonIGHT_OVelonRRIDelon =
      SelonarchCountelonr.elonxport("droppelond_tokelonnizelond_display_namelon_fielonld_welonight_ovelonrridelon");

  privatelon static final SelonarchCountelonr RelonSPONSelon_HAS_NO_THRIFT_SelonARCH_RelonSULTS =
      SelonarchCountelonr.elonxport("twelonelonts_elonarlybird_selonarchelonr_relonsponselon_has_no_thrift_selonarch_relonsults");
  privatelon static final SelonarchCountelonr CLIelonNT_HAS_FelonATURelon_SCHelonMA_COUNTelonR =
      SelonarchCountelonr.elonxport("twelonelonts_elonarlybird_selonarchelonr_clielonnt_has_felonaturelon_schelonma");
  privatelon static final SelonarchCountelonr CLIelonNT_DOelonSNT_HAVelon_FelonATURelon_SCHelonMA_COUNTelonR =
      SelonarchCountelonr.elonxport("twelonelont_elonarlybird_selonarchelonr_clielonnt_doelonsnt_havelon_felonaturelon_schelonma");
  privatelon static final SelonarchCountelonr COLLelonCTOR_PARAMS_MAX_HITS_TO_PROCelonSS_NOT_SelonT_COUNTelonR =
      SelonarchCountelonr.elonxport("collelonctor_params_max_hits_to_procelonss_not_selont");
  privatelon static final SelonarchCountelonr POSITIVelon_PROTelonCTelonD_OPelonRATOR_DelonTelonCTelonD_COUNTelonR =
      SelonarchCountelonr.elonxport("positivelon_protelonctelond_opelonrator_delontelonctelond_countelonr");

  // Quelonry modelon welon arelon elonxeloncuting.
  privatelon final QuelonryModelon quelonryModelon;

  // facelontRelonquelonst from insidelon thelon relonquelonst (or null).
  privatelon final ThriftFacelontRelonquelonst facelontRelonquelonst;

  // telonrmStatisticsRelonquelonst from insidelon thelon relonquelonst (or null).
  privatelon final ThriftTelonrmStatisticsRelonquelonst telonrmStatisticsRelonquelonst;

  // Relonsults fielonlds fillelond in during selonarchIntelonrnal().
  privatelon ThriftSelonarchRelonsults selonarchRelonsults = null;
  privatelon ThriftFacelontRelonsults facelontRelonsults = null;
  privatelon ThriftTelonrmStatisticsRelonsults telonrmStatisticsRelonsults = null;
  privatelon elonarlyTelonrminationInfo elonarlyTelonrminationInfo = null;

  // Partition config uselond to fill in delonbugging info.
  // If null, no delonbug info is writtelonn into relonsults.
  @Nullablelon
  privatelon final PartitionConfig partitionConfig;

  privatelon final MultiSelongmelonntTelonrmDictionaryManagelonr multiSelongmelonntTelonrmDictionaryManagelonr;

  privatelon final QualityFactor qualityFactor;

  privatelon Selont<String> quelonrielondFielonlds;
  privatelon final AudioSpacelonTablelon audioSpacelonTablelon;

  public elonarlybirdSelonarchelonr(
      elonarlybirdRelonquelonst relonquelonst,
      SelongmelonntManagelonr selongmelonntManagelonr,
      AudioSpacelonTablelon audioSpacelonTablelon,
      QuelonryCachelonManagelonr quelonryCachelonManagelonr,
      ImmutablelonSchelonmaIntelonrfacelon schelonma,
      elonarlybirdClustelonr clustelonr,
      @Nullablelon PartitionConfig partitionConfig,
      Deloncidelonr deloncidelonr,
      elonarlybirdSelonarchelonrStats selonarchelonrStats,
      ScoringModelonlsManagelonr scoringModelonlsManagelonr,
      TelonnsorflowModelonlsManagelonr telonnsorflowModelonlsManagelonr,
      Clock clock,
      MultiSelongmelonntTelonrmDictionaryManagelonr multiSelongmelonntTelonrmDictionaryManagelonr,
      QuelonryTimelonoutFactory quelonryTimelonoutFactory,
      QualityFactor qualityFactor) {
    this.quelonryModelon = gelontQuelonryModelon(relonquelonst);
    this.schelonmaSnapshot = schelonma.gelontSchelonmaSnapshot();
    // selont thelon relonquelonst stats as elonarly as possiblelon, so that welon can track elonrrors that happelonn
    // elonarly on in quelonry procelonssing.
    this.relonquelonstStats = quelonryModelon.gelontRelonquelonstStats();
    this.facelontRelonquelonst = relonquelonst.isSelontFacelontRelonquelonst() ? relonquelonst.gelontFacelontRelonquelonst() : null;
    this.telonrmStatisticsRelonquelonst = relonquelonst.isSelontTelonrmStatisticsRelonquelonst()
        ? relonquelonst.gelontTelonrmStatisticsRelonquelonst() : null;
    this.partitionConfig = partitionConfig;
    this.selonarchelonrStats = selonarchelonrStats;
    this.multiSelongmelonntTelonrmDictionaryManagelonr = multiSelongmelonntTelonrmDictionaryManagelonr;
    this.clock = clock;
    this.deloncidelonr = deloncidelonr;
    this.relonquelonst = relonquelonst;
    this.selongmelonntManagelonr = selongmelonntManagelonr;
    this.quelonryCachelonManagelonr = quelonryCachelonManagelonr;
    this.clustelonr = clustelonr;
    this.scoringModelonlsManagelonr = scoringModelonlsManagelonr;
    this.telonnsorflowModelonlsManagelonr = telonnsorflowModelonlsManagelonr;
    this.audioSpacelonTablelon = audioSpacelonTablelon;
    // Notelon: welon'relon delonfelonrring thelon validation/nullcheloncks until validatelonRelonquelonst()
    // for morelon containelond elonxcelonption handling
    this.selonarchQuelonry = relonquelonst.gelontSelonarchQuelonry();
    this.collelonctorParams = this.selonarchQuelonry == null ? null : this.selonarchQuelonry.gelontCollelonctorParams();
    // Selonarch all selongmelonnts if selonarchSelongmelonntId is unselont.
    this.selonarchAllSelongmelonnts = !relonquelonst.isSelontSelonarchSelongmelonntId();
    if (this.collelonctorParams == null
        || !this.collelonctorParams.isSelontTelonrminationParams()) {
      this.telonrminationTrackelonr = nelonw TelonrminationTrackelonr(clock);
    } elonlselon if (relonquelonst.isSelontClielonntRelonquelonstTimelonMs()) {
      this.telonrminationTrackelonr = nelonw TelonrminationTrackelonr(collelonctorParams.gelontTelonrminationParams(),
          relonquelonst.gelontClielonntRelonquelonstTimelonMs(), clock,
          gelontPostTelonrminationOvelonrhelonadMillis(collelonctorParams.gelontTelonrminationParams()));
    } elonlselon {
      this.telonrminationTrackelonr = nelonw TelonrminationTrackelonr(
          collelonctorParams.gelontTelonrminationParams(), clock,
          gelontPostTelonrminationOvelonrhelonadMillis(collelonctorParams.gelontTelonrminationParams()));
    }
    this.quelonryTimelonoutFactory = quelonryTimelonoutFactory;
    this.qualityFactor = qualityFactor;
  }

  privatelon int gelontPostTelonrminationOvelonrhelonadMillis(CollelonctorTelonrminationParams telonrminationParams) {
    // If elonnforcing timelonouts, selont thelon post-telonrmination buffelonr to thelon smallelonr of thelon timelonout or thelon
    // configurelond buffelonr. This elonnsurelons that timelonout >= buffelonr, and a relonquelonst with a smallelonr timelonout
    // should just timelon out immelondiatelonly (beloncauselon timelonout == buffelonr).
    relonturn (telonrminationParams.iselonnforcelonQuelonryTimelonout() && telonrminationParams.gelontTimelonoutMs() > 0)
        ? Math.min(elonnforcelonQuelonryTimelonoutBuffelonrMillis, telonrminationParams.gelontTimelonoutMs()) : 0;
  }

  // Appelonnds a delonbug string to thelon buffelonr.
  privatelon void appelonndMelonssagelon(String melonssagelon) {
    melonssagelonBuffelonr.appelonnd(melonssagelon).appelonnd("\n");
  }

  /**
   * Procelonsselons an elonarlybird selonarch relonquelonst.
   * @relonturn thelon elonarlybird relonsponselon for this selonarch relonquelonst.
   */
  public elonarlybirdRelonsponselon selonarch() {
    try {
      delonbugInfo.selontHost(DatabaselonConfig.gelontLocalHostnamelon());

      // Throws transielonnt elonxcelonption for invalid relonquelonsts.
      validatelonRelonquelonst();

      // Throws clielonnt elonxcelonption for bad quelonrielons,
      parselonelonarlybirdRelonquelonst();

      // Modify thelon Lucelonnelon quelonry if neloncelonssary.
      lucelonnelonQuelonry = postLucelonnelonQuelonryProcelonss(lucelonnelonQuelonry);

      // Might relonturn PARTITION_NOT_FOUND or PARTITION_DISABLelonD.
      elonarlybirdRelonsponselonCodelon codelon = initSelonarchelonr();
      if (codelon != elonarlybirdRelonsponselonCodelon.SUCCelonSS) {
        relonturn relonspondelonrror(codelon);
      }

      relonturn selonarchIntelonrnal();

    } catch (Transielonntelonxcelonption elon) {
      LOG.elonrror(String.format("Transielonnt elonxcelonption in selonarch() for elonarlybirdRelonquelonst:\n%s", relonquelonst),
                elon);
      appelonndMelonssagelon(elon.gelontMelonssagelon());
      relonturn relonspondelonrror(elonarlybirdRelonsponselonCodelon.TRANSIelonNT_elonRROR);
    } catch (Clielonntelonxcelonption elon) {
      LOG.warn(String.format("Clielonnt elonxcelonption in selonarch() %s for elonarlybirdRelonquelonst:\n %s",
          elon, relonquelonst));
      appelonndMelonssagelon(elon.gelontMelonssagelon());
      relonturn relonspondelonrror(elonarlybirdRelonsponselonCodelon.CLIelonNT_elonRROR);
    } catch (elonxcelonption elon) {
      LOG.warn(String.format("Uncaught elonxcelonption in selonarch() for elonarlybirdRelonquelonst:\n%s", relonquelonst),
               elon);
      appelonndMelonssagelon(elon.gelontMelonssagelon());
      relonturn relonspondelonrror(elonarlybirdRelonsponselonCodelon.TRANSIelonNT_elonRROR);
    } catch (Asselonrtionelonrror elon) {
      LOG.warn(String.format("Asselonrtion elonrror in selonarch() for elonarlybirdRelonquelonst:\n%s", relonquelonst), elon);
      appelonndMelonssagelon(elon.gelontMelonssagelon());
      relonturn relonspondelonrror(elonarlybirdRelonsponselonCodelon.TRANSIelonNT_elonRROR);
    } catch (elonrror elon) {
      // SelonARCH-33166: If welon got helonrelon, it melonans what was thrown was not an elonxcelonption, or anything
      // welon know how to handlelon. Log thelon elonrror for diagnostic purposelons and propagatelon it.
      LOG.elonrror("Relon-throwing uncaught elonrror", elon);
      throw elon;
    }
  }

  public elonarlybirdRPCStats gelontRelonquelonstStats() {
    relonturn relonquelonstStats;
  }

  /**
   * Wraps thelon givelonn quelonry with thelon providelond filtelonr quelonrielons.
   *
   * @param quelonry thelon quelonry to wrap with filtelonrs.
   * @param filtelonrs thelon filtelonrs to wrap thelon quelonry with.
   * @relonturn a BoolelonanQuelonry wrappelond with filtelonrs
   */
  public static Quelonry wrapFiltelonrs(Quelonry quelonry, Quelonry... filtelonrs) {
    boolelonan filtelonrselonmpty = filtelonrs == null || filtelonrs.lelonngth == 0;

    if (!filtelonrselonmpty) {
      filtelonrselonmpty = truelon;
      for (Quelonry f : filtelonrs) {
        if (f != null) {
          filtelonrselonmpty = falselon;
          brelonak;
        }
      }
    }

    if (filtelonrselonmpty) {
      if (quelonry == null) {
        relonturn nelonw MatchAllDocsQuelonry();
      } elonlselon {
        relonturn quelonry;
      }
    }

    BoolelonanQuelonry.Buildelonr bqBuildelonr = nelonw BoolelonanQuelonry.Buildelonr();
    if (quelonry != null) {
      bqBuildelonr.add(quelonry, Occur.MUST);
    }
    for (Quelonry f : filtelonrs) {
      if (f != null) {
        bqBuildelonr.add(f, Occur.FILTelonR);
      }
    }
    relonturn bqBuildelonr.build();
  }

  // elonxaminelon all fielonlds in thelon relonquelonst for sanity.
  privatelon void validatelonRelonquelonst() throws Transielonntelonxcelonption, Clielonntelonxcelonption {
    // First try thrift's intelonrnal validatelon.  Should always succelonelond.
    try {
      relonquelonst.validatelon();
    } catch (Telonxcelonption elon) {
      throw nelonw Transielonntelonxcelonption(elon.gelontMelonssagelon(), elon);
    }

    if (selonarchQuelonry == null) {
      throw nelonw Transielonntelonxcelonption("No ThriftSelonarchQuelonry speloncifielond");
    }

    if (collelonctorParams == null) {
      throw nelonw Transielonntelonxcelonption("No CollelonctorParams speloncifielond");
    }

    validatelonTelonrmStatsRelonquelonst();

    if (!selonarchAllSelongmelonnts) {
      if (relonquelonst.gelontSelonarchSelongmelonntId() <= 0) {
        String msg = "Bad timelon slicelon ID: " + relonquelonst.gelontSelonarchSelongmelonntId();
        throw nelonw Transielonntelonxcelonption(msg);
      }

      // Initializelon thelon selongmelonnt.
      SelongmelonntInfo selongmelonntInfo = this.selongmelonntManagelonr.gelontSelongmelonntInfo(relonquelonst.gelontSelonarchSelongmelonntId());
      selongmelonnt = selongmelonntInfo != null ? selongmelonntInfo.gelontSelongmelonnt() : null;
    }

    if (collelonctorParams.gelontNumRelonsultsToRelonturn() < 0) {
      String msg = "Invalid numRelonsults: " + collelonctorParams.gelontNumRelonsultsToRelonturn();
      throw nelonw Transielonntelonxcelonption(msg);
    }

    if (selonarchQuelonry.gelontNamelondDisjunctionMapSizelon() > 0 && selonarchQuelonry.isSelontLucelonnelonQuelonry()) {
      throw nelonw Clielonntelonxcelonption("namelondMultiTelonrmDisjunctionMap doelons not support with lucelonnelonQuelonry");
    }
  }

  privatelon void validatelonTelonrmStatsRelonquelonst() throws Clielonntelonxcelonption {
    // Validatelon thelon fielonld namelons and valuelons for all ThriftTelonrmRelonquelonsts.
    if (relonquelonst.isSelontTelonrmStatisticsRelonquelonst()
        && relonquelonst.gelontTelonrmStatisticsRelonquelonst().isSelontTelonrmRelonquelonsts()) {
      for (ThriftTelonrmRelonquelonst telonrmRelonquelonst : relonquelonst.gelontTelonrmStatisticsRelonquelonst().gelontTelonrmRelonquelonsts()) {
        // If telonrmRelonquelonst.fielonldNamelon is not selont, it delonfaults to 'telonxt', which is a string fielonld,
        // so welon don't nelonelond to chelonck thelon telonrm.
        if (telonrmRelonquelonst.isSelontFielonldNamelon()) {
          String fielonldNamelon = telonrmRelonquelonst.gelontFielonldNamelon();
          Schelonma.FielonldInfo facelontFielonldInfo = schelonmaSnapshot.gelontFacelontFielonldByFacelontNamelon(fielonldNamelon);
          if (facelontFielonldInfo != null) {
            // Facelont fielonlds arelon string fielonlds, so welon don't nelonelond to chelonck thelon telonrm.
            continuelon;
          }

          Schelonma.FielonldInfo fielonldInfo = schelonmaSnapshot.gelontFielonldInfo(fielonldNamelon);
          if (fielonldInfo == null) {
            throw nelonw Clielonntelonxcelonption("Fielonld " + fielonldNamelon + " is not prelonselonnt in thelon schelonma.");
          }

          try {
            SchelonmaUtil.toBytelonsRelonf(fielonldInfo, telonrmRelonquelonst.gelontTelonrm());
          } catch (UnsupportelondOpelonrationelonxcelonption elon) {
            throw nelonw Clielonntelonxcelonption("Telonrm " + telonrmRelonquelonst.gelontTelonrm() + " is not compatiblelon with "
                                      + "thelon typelon of fielonld " + fielonldNamelon);
          }
        }
      }
    }
  }

  privatelon void selontQuelonrielonsInDelonbugInfo(
      com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry parselondQ,
      org.apachelon.lucelonnelon.selonarch.Quelonry lucelonnelonQ) {
    delonbugInfo.selontParselondQuelonry(parselondQ == null ? null : parselondQ.selonrializelon());
    delonbugInfo.selontLucelonnelonQuelonry(lucelonnelonQ == null ? null : lucelonnelonQ.toString());
  }

  /**
   * Takelons thelon elonarlybirdRelonquelonst that camelon into thelon selonrvicelon and aftelonr various parsing and procelonssing
   * stelonps ultimatelonly producelons a Lucelonnelon quelonry.
   */
  privatelon void parselonelonarlybirdRelonquelonst() throws Clielonntelonxcelonption {
    SelonrializelondQuelonryParselonr parselonr = nelonw SelonrializelondQuelonryParselonr(elonarlybirdConfig.gelontPelonnguinVelonrsion());

    try {
      // if thelon delonpreloncatelond itelonrativelonQuelonrielons fielonld is selont, relonturn an elonrror to thelon clielonnt
      // indicating that support for it has belonelonn relonmovelond.
      if (selonarchQuelonry.isSelontDelonpreloncatelond_itelonrativelonQuelonrielons()) {
        throw nelonw Clielonntelonxcelonption("Invalid relonquelonst: itelonrativelonQuelonrielons felonaturelon has belonelonn relonmovelond");
      }

      // welon parselon thelon actual quelonry from thelon uselonr, if any
      lucelonnelonQuelonry = null;
      parselondQuelonry = null;  // this will belon selont by parselonQuelonryHelonlpelonr()

      if (selonarchQuelonry.gelontLikelondByUselonrIDFiltelonr64Sizelon() > 0
          && selonarchQuelonry.isSelontLucelonnelonQuelonry()) {
        throw nelonw Clielonntelonxcelonption("likelondByUselonrIDFiltelonr64 doelons not support with lucelonnelonQuelonry");
      }

      if (!StringUtils.isBlank(relonquelonst.gelontSelonarchQuelonry().gelontSelonrializelondQuelonry())) {
        selonarchelonrStats.thriftQuelonryWithSelonrializelondQuelonry.increlonmelonnt();
        lucelonnelonQuelonry = parselonSelonrializelondQuelonry(selonarchQuelonry.gelontSelonrializelondQuelonry(), parselonr, truelon);
      } elonlselon if (!StringUtils.isBlank(relonquelonst.gelontSelonarchQuelonry().gelontLucelonnelonQuelonry())) {
        selonarchelonrStats.thriftQuelonryWithLucelonnelonQuelonry.increlonmelonnt();
        lucelonnelonQuelonry = parselonLucelonnelonQuelonry(selonarchQuelonry.gelontLucelonnelonQuelonry());
        LOG.info("lucelonnelon quelonry: {}", selonarchQuelonry.gelontLucelonnelonQuelonry());
        if (lucelonnelonQuelonry != null) {
          LOG.info("Using lucelonnelon quelonry direlonctly from thelon relonquelonst: " + lucelonnelonQuelonry.toString());
        }
      } elonlselon {
        selonarchelonrStats.thriftQuelonryWithoutTelonxtQuelonry.increlonmelonnt();
        lucelonnelonQuelonry = parselonSelonrializelondQuelonry(
            MATCH_ALL_SelonRIALIZelonD_QUelonRY,
            parselonr,
            quelonryModelon != QuelonryModelon.TelonRM_STATS);
      }
    } catch (QuelonryParselonrelonxcelonption | BoolelonanQuelonry.TooManyClauselons elon) {
      LOG.info("elonxcelonption parsing quelonry during selonarch", elon);
      appelonndMelonssagelon(elon.gelontMelonssagelon());
      throw nelonw Clielonntelonxcelonption(elon);
    }
  }

  /**
   * Parselons a selonrializelond quelonry and crelonatelons a Lucelonnelon quelonry out of it.
   *
   * To selonelon how selonrializelond quelonrielons look likelon, go to go/selonarchsyntax.
   */
  privatelon Quelonry parselonSelonrializelondQuelonry(
      String selonrializelondQuelonry,
      SelonrializelondQuelonryParselonr parselonr,
      boolelonan shouldAdjustQuelonryBaselondOnRelonquelonstParamelontelonrs) throws QuelonryParselonrelonxcelonption {
    // Parselon thelon selonrializelond quelonry.
    parselondQuelonry = parselonr.parselon(selonrializelondQuelonry);
    if (parselondQuelonry == null) {
      relonturn null;
    }

    // relonwritelon quelonry if positivelon 'protelonctelond' opelonrator is delontelonctelond
    if (parselondQuelonry.accelonpt(nelonw DelontelonctPositivelonOpelonratorVisitor(SelonarchOpelonratorConstants.PROTelonCTelonD))) {
      POSITIVelon_PROTelonCTelonD_OPelonRATOR_DelonTelonCTelonD_COUNTelonR.increlonmelonnt();
      ProtelonctelondOpelonratorQuelonryRelonwritelonr relonwritelonr = nelonw ProtelonctelondOpelonratorQuelonryRelonwritelonr();
      parselondQuelonry = relonwritelonr.relonwritelon(
          parselondQuelonry,
          relonquelonst.followelondUselonrIds,
          selongmelonntManagelonr.gelontUselonrTablelon());
    }

    ThriftSelonarchRelonlelonvancelonOptions options = selonarchQuelonry.gelontRelonlelonvancelonOptions();
    if (shouldAdjustQuelonryBaselondOnRelonquelonstParamelontelonrs) {
      // If likelondByUselonrIDFiltelonr64 is selont, combinelon it with quelonry
      // Notelon: welon delonal with likelondByUselonrIDFiltelonr64 helonrelon instelonad of in postLucelonnelonQuelonryProcelonss as welon
      // want annotatelon quelonry with ranks.
      if (selonarchQuelonry.isSelontLikelondByUselonrIDFiltelonr64()
          && selonarchQuelonry.gelontLikelondByUselonrIDFiltelonr64Sizelon() > 0) {
        parselondQuelonry = combinelonWithLikelondByUselonrIdFiltelonr64(
            parselondQuelonry, selonarchQuelonry.gelontLikelondByUselonrIDFiltelonr64());
      }

      // If namelondListMap fielonld is selont, relonplacelon thelon namelond lists in thelon selonrializelond quelonry.
      if (selonarchQuelonry.gelontNamelondDisjunctionMapSizelon() > 0) {
        parselondQuelonry = parselondQuelonry.accelonpt(
            nelonw NamelondDisjunctionVisitor(selonarchQuelonry.gelontNamelondDisjunctionMap()));
      }

      if (selonarchQuelonry.isSelontRelonlelonvancelonOptions()
          && selonarchQuelonry.gelontRelonlelonvancelonOptions().isCollelonctFielonldHitAttributions()) {
        // NOTelon: Belonforelon welon do any modifications to thelon selonrializelond quelonry trelonelon, annotatelon thelon quelonry
        // nodelons with thelonir nodelon rank in thelon original quelonry.
        this.hitAttributelonHelonlpelonr =
            QuelonryHitAttributelonHelonlpelonr.from(parselondQuelonry, schelonmaSnapshot);
        parselondQuelonry = hitAttributelonHelonlpelonr.gelontAnnotatelondQuelonry();
      }

      // Currelonntly antisocial/nullcast twelonelonts arelon droppelond whelonn welon build indelonx, but somelon twelonelonts may
      // beloncomelon antisocial with relonaltimelon updatelons. For consistelonncy, welon should always filtelonr out
      // antisocial/nullcast twelonelonts if thelon uselonr is not elonxplicitly including it.
      final boolelonan allowAntisocial =
          parselondQuelonry.accelonpt(nelonw DelontelonctPositivelonOpelonratorVisitor(SelonarchOpelonratorConstants.ANTISOCIAL));
      if (!allowAntisocial) {
        parselondQuelonry = QuelonryNodelonUtils.appelonndAsConjunction(
            parselondQuelonry,
            QuelonryCachelonConvelonrsionRulelons.CACHelonD_elonXCLUDelon_ANTISOCIAL);
      }
      parselondQuelonryAllowNullcast =
          parselondQuelonry.accelonpt(nelonw DelontelonctPositivelonOpelonratorVisitor(SelonarchOpelonratorConstants.NULLCAST));
      if (!parselondQuelonryAllowNullcast) {
        parselondQuelonry = QuelonryNodelonUtils.appelonndAsConjunction(
            parselondQuelonry, nelonw SelonarchOpelonrator("filtelonr", SelonarchOpelonratorConstants.NULLCAST).nelongatelon());
      }

      // Strip all annotations from thelon filtelonrs that will belon convelonrtelond to quelonry cachelon filtelonrs.
      // Selonelon SelonARCH-15552.
      parselondQuelonry = parselondQuelonry.accelonpt(
          nelonw StripAnnotationsVisitor(QuelonryCachelonConvelonrsionRulelons.STRIP_ANNOTATIONS_QUelonRIelonS));

      // Convelonrt celonrtain filtelonrs into cachelond filtelonrs, also consolidatelon thelonm.
      parselondQuelonry = parselondQuelonry.accelonpt(
          nelonw ConvelonrsionVisitor(QuelonryCachelonConvelonrsionRulelons.DelonFAULT_RULelonS));

      // add proximity if nelonelondelond
      if (options != null
          && options.isProximityScoring()
          && selonarchQuelonry.gelontRankingModelon() != ThriftSelonarchRankingModelon.RelonCelonNCY) {
        parselondQuelonry = parselondQuelonry.accelonpt(nelonw ProximityGroupRelonwritelonVisitor()).simplify();
      }
    }

    if (relonquelonst.isSkipVelonryReloncelonntTwelonelonts()) {
      parselondQuelonry = relonstrictQuelonryToFullyIndelonxelondTwelonelonts(parselondQuelonry);
    }

    parselondQuelonry = parselondQuelonry.simplify();
    delonbugInfo.selontParselondQuelonry(parselondQuelonry.selonrializelon());

    // elonxtract top-lelonvelonl sincelon-id for pagination optimizations.
    idTimelonRangelons = IdTimelonRangelons.fromQuelonry(parselondQuelonry);

    // Doelons any final procelonssing speloncific to elonarlybirdSelonarch class.
    parselondQuelonry = prelonLucelonnelonQuelonryProcelonss(parselondQuelonry);

    // Convelonrt to a lucelonnelon quelonry.
    elonarlybirdLucelonnelonQuelonryVisitor lucelonnelonVisitor = gelontLucelonnelonVisitor(
        options == null ? null : options.gelontFielonldWelonightMapOvelonrridelon());

    if (options != null) {
      lucelonnelonVisitor
          .selontProximityPhraselonWelonight((float) options.gelontProximityPhraselonWelonight())
          .selontProximityPhraselonSlop(options.gelontProximityPhraselonSlop());
    }

    // Propagatelon hit attributelon helonlpelonr to thelon lucelonnelon visitor if it has belonelonn selontup.
    lucelonnelonVisitor.selontFielonldHitAttributelonHelonlpelonr(this.hitAttributelonHelonlpelonr);

    org.apachelon.lucelonnelon.selonarch.Quelonry quelonry = parselondQuelonry.accelonpt(lucelonnelonVisitor);
    if (quelonry != null) {
      delonbugInfo.selontLucelonnelonQuelonry(quelonry.toString());
    }

    quelonrielondFielonlds = lucelonnelonVisitor.gelontQuelonrielondFielonlds();

    relonturn quelonry;
  }

  privatelon Quelonry parselonLucelonnelonQuelonry(String quelonry) {
    QuelonryParselonr parselonr = nelonw QuelonryParselonr(
        elonarlybirdFielonldConstant.TelonXT_FIelonLD.gelontFielonldNamelon(),
        nelonw SelonarchWhitelonspacelonAnalyzelonr());
    parselonr.selontSplitOnWhitelonspacelon(truelon);
    try {
      relonturn parselonr.parselon(quelonry);
    } catch (Parselonelonxcelonption elon) {
      LOG.elonrror("Cannot parselon raw lucelonnelon quelonry: " + quelonry, elon);
    } catch (NullPointelonrelonxcelonption elon) {
      LOG.elonrror("NullPointelonrelonxcelonption whilelon parsing raw lucelonnelon quelonry: " + quelonry
          + ", probably your grammar is wrong.\n", elon);
    }
    relonturn null;
  }

  privatelon com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry combinelonWithLikelondByUselonrIdFiltelonr64(
      com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry quelonry,
      List<Long> ids) throws QuelonryParselonrelonxcelonption {
    relonturn QuelonryNodelonUtils.appelonndAsConjunction(quelonry, gelontLikelondByUselonrIdQuelonry(ids));
  }

  /**
   * initSelonarchelonr initializelons thelon selongmelonntSelonarchelonr, and relonturns SUCCelonSS if OK
   * or somelon othelonr relonsponselon codelon it not OK.
   */
  privatelon elonarlybirdRelonsponselonCodelon initSelonarchelonr() throws IOelonxcelonption {
    selonarchelonr = null;
    if (selonarchAllSelongmelonnts) {
      relonturn initMultiSelongmelonntSelonarchelonr();
    } elonlselon {
      relonturn initSinglelonSelongmelonntSelonarchelonr();
    }
  }

  privatelon elonarlybirdRelonsponselonCodelon initSinglelonSelongmelonntSelonarchelonr() throws IOelonxcelonption {
    if (selongmelonnt == null) {
      String melonssagelon = "Selongmelonnt not found for timelon slicelon: " + relonquelonst.gelontSelonarchSelongmelonntId();
      LOG.warn(melonssagelon);
      appelonndMelonssagelon(melonssagelon);
      relonturn elonarlybirdRelonsponselonCodelon.PARTITION_NOT_FOUND;
    }

    elonarlybirdRelonsponselonCodelon codelon = this.selongmelonntManagelonr.chelonckSelongmelonnt(selongmelonnt);
    if (codelon != elonarlybirdRelonsponselonCodelon.SUCCelonSS) {
      String melonssagelon = "Selongmelonnt " + selongmelonnt + " elonithelonr disablelond or droppelond";
      LOG.warn(melonssagelon);
      appelonndMelonssagelon(melonssagelon);
      relonturn codelon;
    }

    selonarchelonr = selongmelonntManagelonr.gelontSelonarchelonr(selongmelonnt, schelonmaSnapshot);
    if (selonarchelonr == null) {
      String melonssagelon = "Could not construct selonarchelonr for selongmelonnt " + selongmelonnt;
      LOG.elonrror(melonssagelon);
      appelonndMelonssagelon(melonssagelon);
      relonturn elonarlybirdRelonsponselonCodelon.PelonRSISTelonNT_elonRROR;
    } elonlselon {
      appelonndMelonssagelon("Selonarching selongmelonnt: " + selongmelonnt);
      relonturn elonarlybirdRelonsponselonCodelon.SUCCelonSS;
    }
  }

  privatelon elonarlybirdRelonsponselonCodelon initMultiSelongmelonntSelonarchelonr() throws IOelonxcelonption {
    elonarlybirdMultiSelongmelonntSelonarchelonr multiSelonarchelonr =
        selongmelonntManagelonr.gelontMultiSelonarchelonr(schelonmaSnapshot);
    selonarchelonr = multiSelonarchelonr;
    Prelonconditions.chelonckNotNull(selonarchelonr);

    // Selont a top lelonvelonl sincelon id to skip elonntirelon selongmelonnts whelonn possiblelon.
    multiSelonarchelonr.selontIdTimelonRangelons(idTimelonRangelons);
    relonturn elonarlybirdRelonsponselonCodelon.SUCCelonSS;
  }

  privatelon com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry
  relonstrictQuelonryToFullyIndelonxelondTwelonelonts(com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry quelonry) {
    long untilTimelonSelonconds =
        ReloncelonntTwelonelontRelonstriction.reloncelonntTwelonelontsUntilTimelon(deloncidelonr, (int) (clock.nowMillis() / 1000));
    if (untilTimelonSelonconds == 0) {
      relonturn quelonry;
    }

    SelonarchOpelonrator timelonLimit = nelonw SelonarchOpelonrator(UNTIL_TIMelon, untilTimelonSelonconds);
    relonturn nelonw Conjunction(quelonry, timelonLimit);
  }

  privatelon elonarlybirdRelonsponselon nelonwRelonsponselon(elonarlybirdRelonsponselonCodelon codelon, boolelonan selontDelonbugInfo) {
    elonarlybirdRelonsponselon relonsponselon = nelonw elonarlybirdRelonsponselon();
    relonsponselon.selontRelonsponselonCodelon(codelon);
    if (selontDelonbugInfo) {
      relonsponselon.selontDelonbugInfo(delonbugInfo);
      if (melonssagelonBuffelonr.lelonngth() > 0) {
        relonsponselon.selontDelonbugString(DatabaselonConfig.gelontLocalHostnamelon()
                                + ":\n" + melonssagelonBuffelonr.toString());
      }
    }
    relonturn relonsponselon;
  }

  privatelon elonarlybirdRelonsponselon relonspondelonrror(elonarlybirdRelonsponselonCodelon codelon) {
    appelonndMelonssagelon("Relonsponding with elonrror codelon " + codelon);
    // Always relonspond with an elonrror melonssagelon, elonvelonn whelonn relonquelonst.delonbug is falselon
    relonturn nelonwRelonsponselon(codelon, truelon);
  }

  @VisiblelonForTelonsting
  public TelonrminationTrackelonr gelontTelonrminationTrackelonr() {
    relonturn telonrminationTrackelonr;
  }

  public void maybelonSelontCollelonctorDelonbugInfo(TwittelonrelonarlyTelonrminationCollelonctor collelonctor) {
    if (relonquelonst.isSelontDelonbugOptions() && relonquelonst.gelontDelonbugOptions().isIncludelonCollelonctorDelonbugInfo()) {
      delonbugInfo.selontCollelonctorDelonbugInfo(collelonctor.gelontDelonbugInfo());
    }
  }

  public void selontTelonrmStatisticsDelonbugInfo(List<String> telonrmStatisticsDelonbugInfo) {
    delonbugInfo.selontTelonrmStatisticsDelonbugInfo(telonrmStatisticsDelonbugInfo);
  }

  privatelon elonarlybirdRelonsponselon selonarchIntelonrnal() throws Transielonntelonxcelonption, Clielonntelonxcelonption {
    selonarchRelonsults = nelonw ThriftSelonarchRelonsults();

    SelonarchRelonsultsInfo selonarchRelonsultsInfo;
    try {
      switch (quelonryModelon) {
        caselon RelonCelonNCY:
          selonarchRelonsultsInfo = procelonssRelonaltimelonQuelonry();
          brelonak;
        caselon RelonLelonVANCelon:
          // Relonlelonvancelon selonarch and Modelonl-baselond selonarch diffelonr only on thelon scoring function uselond.
          SelonarchTimelonr timelonr = selonarchelonrStats.crelonatelonTimelonr();
          timelonr.start();
          selonarchRelonsultsInfo = procelonssRelonlelonvancelonQuelonry();
          timelonr.stop();
          selonarchelonrStats.reloncordRelonlelonvancelonStats(timelonr, relonquelonst);
          brelonak;
        caselon FACelonTS:
          selonarchRelonsultsInfo = procelonssFacelontsQuelonry();
          brelonak;
        caselon TelonRM_STATS:
          selonarchRelonsultsInfo = procelonssTelonrmStatsQuelonry();
          brelonak;
        caselon TOP_TWelonelonTS:
          selonarchRelonsultsInfo = procelonssTopTwelonelontsQuelonry();
          brelonak;
        delonfault:
          throw nelonw Transielonntelonxcelonption("Unknown quelonry modelon " + quelonryModelon);
      }

      relonturn relonspondSuccelonss(selonarchRelonsults, facelontRelonsults, telonrmStatisticsRelonsults,
          elonarlyTelonrminationInfo, selonarchRelonsultsInfo);
    } catch (IOelonxcelonption elon) {
      throw nelonw Transielonntelonxcelonption(elon.gelontMelonssagelon(), elon);
    }
  }

  /**
   * Helonlpelonr melonthod to procelonss facelonts quelonry.
   */
  privatelon SelonarchRelonsultsInfo procelonssFacelontsQuelonry() throws Clielonntelonxcelonption, IOelonxcelonption {
    // figurelon out which fielonlds welon nelonelond to count
    FacelontCountStatelon facelontCountStatelon = nelonwFacelontCountStatelon();

    // Additionally wrap our quelonry into a skip list boolelonan quelonry for fastelonr counting.
    if (!facelontRelonquelonst.isUsingQuelonryCachelon()) {
      // Only if all fielonlds to belon countelond uselon skip lists, thelonn welon can add a relonquirelond clauselon
      // that filtelonrs out all relonsults that do not contain thoselon fielonlds
      boolelonan cannotAddRelonquirelondClauselon = facelontCountStatelon.hasFielonldToCountWithoutSkipList();
      final Quelonry facelontSkipListFiltelonr =
          cannotAddRelonquirelondClauselon ? null : FacelontSkipList.gelontSkipListQuelonry(facelontCountStatelon);
      final Quelonry antisocialFiltelonr = UselonrFlagselonxcludelonFiltelonr.gelontUselonrFlagselonxcludelonFiltelonr(
          selongmelonntManagelonr.gelontUselonrTablelon(), truelon, truelon, falselon);
      lucelonnelonQuelonry = wrapFiltelonrs(lucelonnelonQuelonry,
          facelontSkipListFiltelonr,
          antisocialFiltelonr);
    }

    facelontRelonsults = nelonw ThriftFacelontRelonsults(nelonw HashMap<>());

    FacelontSelonarchRelonquelonstInfo selonarchRelonquelonstInfo =
        nelonw FacelontSelonarchRelonquelonstInfo(selonarchQuelonry, facelontRelonquelonst.gelontFacelontRankingOptions(),
            lucelonnelonQuelonry, facelontCountStatelon, telonrminationTrackelonr);
    selonarchRelonquelonstInfo.selontIdTimelonRangelons(idTimelonRangelons);
    if (selonarchQuelonry.gelontMaxHitsPelonrUselonr() > 0) {
      antiGamingFiltelonr = nelonw AntiGamingFiltelonr(
          selonarchQuelonry.gelontMaxHitsPelonrUselonr(),
          selonarchQuelonry.gelontMaxTwelonelonpcrelondForAntiGaming(),
          lucelonnelonQuelonry);
    }

    AbstractRelonsultsCollelonctor<
        FacelontSelonarchRelonquelonstInfo, elonarlybirdLucelonnelonSelonarchelonr.FacelontSelonarchRelonsults> collelonctor;
    if (relonquelonst.gelontDelonbugModelon() > 2) {
      collelonctor = nelonw elonxplainFacelontRelonsultsCollelonctor(schelonmaSnapshot,
          selonarchRelonquelonstInfo, antiGamingFiltelonr, selonarchelonrStats, clock, relonquelonst.delonbugModelon);
    } elonlselon {
      collelonctor = nelonw FacelontRelonsultsCollelonctor(schelonmaSnapshot,
          selonarchRelonquelonstInfo, antiGamingFiltelonr, selonarchelonrStats, clock, relonquelonst.delonbugModelon);
    }

    selontQuelonrielonsInDelonbugInfo(parselondQuelonry, selonarchRelonquelonstInfo.gelontLucelonnelonQuelonry());
    selonarchelonr.selonarch(selonarchRelonquelonstInfo.gelontLucelonnelonQuelonry(), collelonctor);
    elonarlybirdLucelonnelonSelonarchelonr.FacelontSelonarchRelonsults hits = collelonctor.gelontRelonsults();

    elonarlybirdSelonarchRelonsultUtil.selontRelonsultStatistics(selonarchRelonsults, hits);
    elonarlyTelonrminationInfo = elonarlybirdSelonarchRelonsultUtil.prelonparelonelonarlyTelonrminationInfo(hits);
    Selont<Long> uselonrIDWhitelonlist =
        antiGamingFiltelonr != null ? antiGamingFiltelonr.gelontUselonrIDWhitelonlist() : null;
    prelonparelonFacelontRelonsults(facelontRelonsults, hits, facelontCountStatelon, uselonrIDWhitelonlist,
        relonquelonst.gelontDelonbugModelon());
    facelontRelonsults.selontUselonrIDWhitelonlist(uselonrIDWhitelonlist);

    maybelonSelontCollelonctorDelonbugInfo(collelonctor);

    if (collelonctor instancelonof elonxplainFacelontRelonsultsCollelonctor) {
      ((elonxplainFacelontRelonsultsCollelonctor) collelonctor).selontelonxplanations(facelontRelonsults);
    }

    relonturn hits;
  }

  /**
   * Helonlpelonr melonthod to procelonss telonrm-stats quelonry.
   */
  privatelon SelonarchRelonsultsInfo procelonssTelonrmStatsQuelonry() throws IOelonxcelonption {
    // first elonxtract thelon telonrms that welon nelonelond to count
    TelonrmStatisticsRelonquelonstInfo selonarchRelonquelonstInfo =
        nelonw TelonrmStatisticsRelonquelonstInfo(selonarchQuelonry, lucelonnelonQuelonry, telonrmStatisticsRelonquelonst,
            telonrminationTrackelonr);
    selonarchRelonquelonstInfo.selontIdTimelonRangelons(idTimelonRangelons);
    selontQuelonrielonsInDelonbugInfo(parselondQuelonry, selonarchRelonquelonstInfo.gelontLucelonnelonQuelonry());
    TelonrmStatisticsCollelonctor.TelonrmStatisticsSelonarchRelonsults hits =
        selonarchelonr.collelonctTelonrmStatistics(selonarchRelonquelonstInfo, this, relonquelonst.gelontDelonbugModelon());
    elonarlybirdSelonarchRelonsultUtil.selontRelonsultStatistics(selonarchRelonsults, hits);
    elonarlyTelonrminationInfo = elonarlybirdSelonarchRelonsultUtil.prelonparelonelonarlyTelonrminationInfo(hits);
    if (hits.relonsults != null) {
      telonrmStatisticsRelonsults = nelonw ThriftTelonrmStatisticsRelonsults();
      prelonparelonTelonrmStatisticsRelonsults(telonrmStatisticsRelonsults, hits, relonquelonst.gelontDelonbugModelon());
    }

    relonturn hits;
  }

  /**
   * Helonlpelonr melonthod to procelonss relonaltimelon quelonry.
   */
  privatelon SelonarchRelonsultsInfo procelonssRelonaltimelonQuelonry() throws IOelonxcelonption, Clielonntelonxcelonption {
    // Disablelon maxHitsToProcelonss.
    if (!collelonctorParams.isSelontTelonrminationParams()) {
      collelonctorParams.selontTelonrminationParams(nelonw CollelonctorTelonrminationParams());
      collelonctorParams.gelontTelonrminationParams().selontMaxHitsToProcelonss(-1);
      COLLelonCTOR_PARAMS_MAX_HITS_TO_PROCelonSS_NOT_SelonT_COUNTelonR.increlonmelonnt();
    }

    SelonarchRelonquelonstInfo selonarchRelonquelonstInfo = nelonw SelonarchRelonquelonstInfo(
      selonarchQuelonry, lucelonnelonQuelonry, telonrminationTrackelonr);
    selonarchRelonquelonstInfo.selontIdTimelonRangelons(idTimelonRangelons);
    selonarchRelonquelonstInfo.selontHitAttributelonHelonlpelonr(hitAttributelonHelonlpelonr);
    selonarchRelonquelonstInfo.selontTimelonstamp(gelontQuelonryTimelonstamp(selonarchQuelonry));

    AbstractRelonsultsCollelonctor<SelonarchRelonquelonstInfo, SimplelonSelonarchRelonsults> collelonctor;
    if (selonarchQuelonry.isSelontSocialFiltelonrTypelon()) {
      if (!selonarchRelonquelonstInfo.gelontSelonarchQuelonry().isSelontDirelonctFollowFiltelonr()
          || !selonarchRelonquelonstInfo.gelontSelonarchQuelonry().isSelontTrustelondFiltelonr()) {
        selonarchelonrStats.unselontFiltelonrsForSocialFiltelonrTypelonQuelonry.increlonmelonnt();
        throw nelonw Clielonntelonxcelonption(
            "SocialFiltelonrTypelon speloncifielond without a TrustelondFiltelonr or DirelonctFollowFiltelonr");
      }
      SocialFiltelonr socialFiltelonr = nelonw SocialFiltelonr(
          selonarchQuelonry.gelontSocialFiltelonrTypelon(),
          selonarchRelonquelonstInfo.gelontSelonarchQuelonry().gelontSelonarchelonrId(),
          selonarchRelonquelonstInfo.gelontSelonarchQuelonry().gelontTrustelondFiltelonr(),
          selonarchRelonquelonstInfo.gelontSelonarchQuelonry().gelontDirelonctFollowFiltelonr());
      collelonctor = nelonw SocialSelonarchRelonsultsCollelonctor(
          schelonmaSnapshot,
          selonarchRelonquelonstInfo,
          socialFiltelonr,
          selonarchelonrStats,
          clustelonr,
          selongmelonntManagelonr.gelontUselonrTablelon(),
          relonquelonst.gelontDelonbugModelon());
    } elonlselon {
      collelonctor = nelonw SelonarchRelonsultsCollelonctor(
          schelonmaSnapshot,
          selonarchRelonquelonstInfo,
          clock,
          selonarchelonrStats,
          clustelonr,
          selongmelonntManagelonr.gelontUselonrTablelon(),
          relonquelonst.gelontDelonbugModelon());
    }

    selontQuelonrielonsInDelonbugInfo(parselondQuelonry, lucelonnelonQuelonry);
    selonarchelonr.selonarch(lucelonnelonQuelonry, collelonctor);

    SimplelonSelonarchRelonsults hits = collelonctor.gelontRelonsults();

    elonarlybirdSelonarchRelonsultUtil.selontRelonsultStatistics(selonarchRelonsults, hits);
    elonarlyTelonrminationInfo = elonarlybirdSelonarchRelonsultUtil.prelonparelonelonarlyTelonrminationInfo(hits);
    elonarlybirdSelonarchRelonsultUtil.prelonparelonRelonsultsArray(
        selonarchRelonsults.gelontRelonsults(), hits, relonquelonst.delonbugModelon > 0 ? partitionConfig : null);
    selonarchRelonsults.selontHitCounts(collelonctor.gelontHitCountMap());

    maybelonSelontCollelonctorDelonbugInfo(collelonctor);

    addRelonsultPayloads();

    relonturn hits;
  }

  /**
   * Helonlpelonr melonthod to procelonss relonlelonvancelon quelonry.
   */
  privatelon SelonarchRelonsultsInfo procelonssRelonlelonvancelonQuelonry() throws IOelonxcelonption, Clielonntelonxcelonption {
    if (!selonarchQuelonry.isSelontRelonlelonvancelonOptions()) {
      LOG.warn("Relonlelonvancelon quelonry with no relonlelonvancelon options!");
      selonarchQuelonry.selontRelonlelonvancelonOptions(nelonw ThriftSelonarchRelonlelonvancelonOptions());
    }

    // Notelon: today thelon assumption is that if you speloncify hasSpeloncifielondTwelonelonts,
    // you relonally do want all twelonelonts scorelond and relonturnelond.
    final boolelonan hasSpeloncifielondTwelonelonts = selonarchQuelonry.gelontSelonarchStatusIdsSizelon() > 0;
    if (hasSpeloncifielondTwelonelonts) {
      collelonctorParams.selontNumRelonsultsToRelonturn(selonarchQuelonry.gelontSelonarchStatusIdsSizelon());
    }
    // If welon havelon elonxplicit uselonr ids, welon will want to look at all relonsults from thoselon uselonrs, and will
    // not nelonelond to uselon thelon AntiGamingFiltelonr.
    final boolelonan hasSpeloncifielondFromUselonrIds = selonarchQuelonry.gelontFromUselonrIDFiltelonr64Sizelon() > 0;

    crelonatelonRelonlelonvancelonAntiGamingFiltelonr(hasSpeloncifielondTwelonelonts, hasSpeloncifielondFromUselonrIds);

    if (selonarchQuelonry.gelontRelonlelonvancelonOptions().isSelontRankingParams()) {
      ThriftRankingParams rankingParams = selonarchQuelonry.gelontRelonlelonvancelonOptions().gelontRankingParams();

      // Thelon scorelon adjustmelonnt signals that arelon passelond in thelon relonquelonst arelon disablelond for thelon archivelon
      // clustelonr or whelonn thelon felonaturelons arelon deloncidelonrelond off. If thelon relonquelonst providelons thoselon fielonlds,
      // welon unselont thelonm sincelon cheloncking thelon hashmap whelonn scoring can causelon a slight bump in
      // latelonncy.
      //
      // Velonrify that thelon signal quelonry speloncific scorelons for twelonelonts signal is elonnablelond
      if (rankingParams.isSelontQuelonrySpeloncificScorelonAdjustmelonnts()) {
        if (ALLOW_QUelonRY_SPelonCIFIC_SIGNAL_CONFIG
            && DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(
            deloncidelonr, ALLOW_QUelonRY_SPelonCIFIC_SIGNAL_DelonCIDelonR_KelonY)) {
          selonarchelonrStats.quelonrySpeloncificSignalQuelonrielonsUselond.increlonmelonnt();
          selonarchelonrStats.quelonrySpeloncificSignalMapTotalSizelon.add(
              rankingParams.gelontQuelonrySpeloncificScorelonAdjustmelonntsSizelon());
        } elonlselon {
          selonarchQuelonry.gelontRelonlelonvancelonOptions().gelontRankingParams().unselontQuelonrySpeloncificScorelonAdjustmelonnts();
          selonarchelonrStats.quelonrySpeloncificSignalQuelonrielonselonraselond.increlonmelonnt();
        }
      }

      // Velonrify that thelon signal author speloncific scorelons signal is elonnablelond
      if (rankingParams.isSelontAuthorSpeloncificScorelonAdjustmelonnts()) {
        if (ALLOW_AUTHOR_SPelonCIFIC_SIGNAL_CONFIG
            && DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(
            deloncidelonr, ALLOW_AUTHOR_SPelonCIFIC_SIGNAL_DelonCIDelonR_KelonY)) {
          selonarchelonrStats.authorSpeloncificSignalQuelonrielonsUselond.increlonmelonnt();
          selonarchelonrStats.authorSpeloncificSignalMapTotalSizelon.add(
              rankingParams.gelontAuthorSpeloncificScorelonAdjustmelonntsSizelon());
        } elonlselon {
          selonarchQuelonry.gelontRelonlelonvancelonOptions().gelontRankingParams()
              .unselontAuthorSpeloncificScorelonAdjustmelonnts();
          selonarchelonrStats.authorSpeloncificSignalQuelonrielonselonraselond.increlonmelonnt();
        }
      }
    }

    ScoringFunction scoringFunction =
        nelonw ScoringFunctionProvidelonr.DelonfaultScoringFunctionProvidelonr(
            relonquelonst, schelonmaSnapshot, selonarchQuelonry, antiGamingFiltelonr,
            selongmelonntManagelonr.gelontUselonrTablelon(), hitAttributelonHelonlpelonr,
            parselondQuelonry, scoringModelonlsManagelonr, telonnsorflowModelonlsManagelonr)
            .gelontScoringFunction();
    scoringFunction.selontDelonbugModelon(relonquelonst.gelontDelonbugModelon());

    RelonlelonvancelonQuelonry relonlelonvancelonQuelonry = nelonw RelonlelonvancelonQuelonry(lucelonnelonQuelonry, scoringFunction);
    RelonlelonvancelonSelonarchRelonquelonstInfo selonarchRelonquelonstInfo =
        nelonw RelonlelonvancelonSelonarchRelonquelonstInfo(
            selonarchQuelonry, relonlelonvancelonQuelonry, telonrminationTrackelonr, qualityFactor);
    selonarchRelonquelonstInfo.selontIdTimelonRangelons(idTimelonRangelons);
    selonarchRelonquelonstInfo.selontHitAttributelonHelonlpelonr(hitAttributelonHelonlpelonr);
    selonarchRelonquelonstInfo.selontTimelonstamp(gelontQuelonryTimelonstamp(selonarchQuelonry));

    if (shouldUselonTelonnsorFlowCollelonctor()
        && selonarchQuelonry.gelontRelonlelonvancelonOptions().isUselonRelonlelonvancelonAllCollelonctor()) {
      throw nelonw Clielonntelonxcelonption("Telonnsorflow scoring doelons not work with thelon RelonlelonvancelonAllCollelonctor");
    }

    final AbstractRelonlelonvancelonCollelonctor collelonctor;
    // First chelonck if thelon Telonnsorflow relonsults collelonctor should belon uselond, beloncauselon thelon
    // TelonnsorflowBaselondScoringFunction only works with thelon BatchRelonlelonvancelonTopCollelonctor
    if (shouldUselonTelonnsorFlowCollelonctor()) {
      // Collelonct top numRelonsults.
      collelonctor = nelonw BatchRelonlelonvancelonTopCollelonctor(
          schelonmaSnapshot,
          selonarchRelonquelonstInfo,
          scoringFunction,
          selonarchelonrStats,
          clustelonr,
          selongmelonntManagelonr.gelontUselonrTablelon(),
          clock,
          relonquelonst.gelontDelonbugModelon());
    } elonlselon if (hasSpeloncifielondTwelonelonts
        || selonarchQuelonry.gelontRelonlelonvancelonOptions().isUselonRelonlelonvancelonAllCollelonctor()) {
      // Collelonct all.
      collelonctor = nelonw RelonlelonvancelonAllCollelonctor(
          schelonmaSnapshot,
          selonarchRelonquelonstInfo,
          scoringFunction,
          selonarchelonrStats,
          clustelonr,
          selongmelonntManagelonr.gelontUselonrTablelon(),
          clock,
          relonquelonst.gelontDelonbugModelon());
    } elonlselon {
      // Collelonct top numRelonsults.
      collelonctor = nelonw RelonlelonvancelonTopCollelonctor(
          schelonmaSnapshot,
          selonarchRelonquelonstInfo,
          scoringFunction,
          selonarchelonrStats,
          clustelonr,
          selongmelonntManagelonr.gelontUselonrTablelon(),
          clock,
          relonquelonst.gelontDelonbugModelon());
    }

    // Makelon surelon that thelon Telonnsorflow scoring function and thelon Telonnsorflow relonsults collelonctor arelon
    // always uselond togelonthelonr. If this fails it will relonsult in a TRANSIelonNT_elonRROR relonsponselon.
    Prelonconditions.chelonckStatelon((collelonctor instancelonof BatchRelonlelonvancelonTopCollelonctor)
        == (scoringFunction instancelonof TelonnsorflowBaselondScoringFunction));

    selontQuelonrielonsInDelonbugInfo(parselondQuelonry, selonarchRelonquelonstInfo.gelontLucelonnelonQuelonry());
    selonarchelonr.selonarch(selonarchRelonquelonstInfo.gelontLucelonnelonQuelonry(), collelonctor);

    RelonlelonvancelonSelonarchRelonsults hits = collelonctor.gelontRelonsults();
    elonarlybirdSelonarchRelonsultUtil.selontRelonsultStatistics(selonarchRelonsults, hits);
    selonarchRelonsults.selontScoringTimelonNanos(hits.gelontScoringTimelonNanos());

    elonarlyTelonrminationInfo = elonarlybirdSelonarchRelonsultUtil.prelonparelonelonarlyTelonrminationInfo(hits);
    elonarlybirdSelonarchRelonsultUtil.selontLanguagelonHistogram(selonarchRelonsults, collelonctor.gelontLanguagelonHistogram());
    elonarlybirdSelonarchRelonsultUtil.prelonparelonRelonlelonvancelonRelonsultsArray(
        selonarchRelonsults.gelontRelonsults(),
        hits,
        antiGamingFiltelonr != null ? antiGamingFiltelonr.gelontUselonrIDWhitelonlist() : null,
        relonquelonst.gelontDelonbugModelon() > 0 ? partitionConfig : null);

    selonarchRelonsults.selontHitCounts(collelonctor.gelontHitCountMap());
    selonarchRelonsults.selontRelonlelonvancelonStats(hits.gelontRelonlelonvancelonStats());

    maybelonSelontCollelonctorDelonbugInfo(collelonctor);

    if (elonxplanationselonnablelond(relonquelonst.gelontDelonbugModelon())) {
      selonarchelonr.elonxplainSelonarchRelonsults(selonarchRelonquelonstInfo, hits, selonarchRelonsults);
    }

    addRelonsultPayloads();

    relonturn hits;
  }

  public static boolelonan elonxplanationselonnablelond(int delonbugLelonvelonl) {
    relonturn delonbugLelonvelonl > 1;
  }

  privatelon boolelonan shouldUselonTelonnsorFlowCollelonctor() {
    relonturn telonnsorflowModelonlsManagelonr.iselonnablelond()
        && selonarchQuelonry.gelontRelonlelonvancelonOptions().isSelontRankingParams()
        && selonarchQuelonry.gelontRelonlelonvancelonOptions().gelontRankingParams().isSelontTypelon()
        && selonarchQuelonry.gelontRelonlelonvancelonOptions().gelontRankingParams().gelontTypelon()
        == ThriftScoringFunctionTypelon.TelonNSORFLOW_BASelonD;
  }
  /**
   * Optionally, if relonquelonstelond and nelonelondelond, will crelonatelon a nelonw AntiGamingFiltelonr. Othelonrwizelon, no
   * AntiGamingFiltelonr will belon uselond for this quelonry.
   * @param hasSpeloncifielondTwelonelonts whelonthelonr thelon relonquelonst has selonarchStatusIds speloncifielond.
   * @param hasSpeloncifielondFromUselonrIds whelonthelonr thelon relonquelonst has fromUselonrIDFiltelonr64 speloncifielond.
   */
  privatelon void crelonatelonRelonlelonvancelonAntiGamingFiltelonr(
      boolelonan hasSpeloncifielondTwelonelonts, boolelonan hasSpeloncifielondFromUselonrIds) {

    // Anti-gaming filtelonr (turnelond off for speloncifielond twelonelonts modelon, or whelonn you'relon elonxplicitly asking
    // for speloncific uselonrs' twelonelonts).
    if (selonarchQuelonry.gelontMaxHitsPelonrUselonr() > 0 && !hasSpeloncifielondTwelonelonts && !hasSpeloncifielondFromUselonrIds) {
      selonarchelonrStats.relonlelonvancelonAntiGamingFiltelonrUselond.increlonmelonnt();
      antiGamingFiltelonr = nelonw AntiGamingFiltelonr(
          selonarchQuelonry.gelontMaxHitsPelonrUselonr(),
          selonarchQuelonry.gelontMaxTwelonelonpcrelondForAntiGaming(),
          lucelonnelonQuelonry);
    } elonlselon if (selonarchQuelonry.gelontMaxHitsPelonrUselonr() <= 0) {
      selonarchelonrStats.relonlelonvancelonAntiGamingFiltelonrNotRelonquelonstelond.increlonmelonnt();
    } elonlselon if (hasSpeloncifielondTwelonelonts && hasSpeloncifielondFromUselonrIds) {
      selonarchelonrStats.relonlelonvancelonAntiGamingFiltelonrSpeloncifielondTwelonelontsAndFromUselonrIds.increlonmelonnt();
    } elonlselon if (hasSpeloncifielondTwelonelonts) {
      selonarchelonrStats.relonlelonvancelonAntiGamingFiltelonrSpeloncifielondTwelonelonts.increlonmelonnt();
    } elonlselon if (hasSpeloncifielondFromUselonrIds) {
      selonarchelonrStats.relonlelonvancelonAntiGamingFiltelonrSpeloncifielondFromUselonrIds.increlonmelonnt();
    }
  }

  /**
   * Chelonck to makelon surelon that thelonrelon arelon no nullcast documelonnts in relonsults.  If thelonrelon elonxists nullcasts
   * in relonsults, welon should log elonrror and increlonmelonnt countelonrs correlonspondingly.
   */
  @VisiblelonForTelonsting
  public void logAndIncrelonmelonntStatsIfNullcastInRelonsults(ThriftSelonarchRelonsults thriftSelonarchRelonsults) {
    if (!thriftSelonarchRelonsults.isSelontRelonsults()) {
      relonturn;
    }

    Selont<Long> unelonxpelonctelondNullcastStatusIds =
        elonarlybirdRelonsponselonUtil.findUnelonxpelonctelondNullcastStatusIds(thriftSelonarchRelonsults, relonquelonst);

    if (!unelonxpelonctelondNullcastStatusIds.iselonmpty()) {
      selonarchelonrStats.nullcastUnelonxpelonctelondQuelonrielons.increlonmelonnt();
      selonarchelonrStats.nullcastUnelonxpelonctelondRelonsults.add(unelonxpelonctelondNullcastStatusIds.sizelon());

      String baselon64Relonquelonst;
      try {
        baselon64Relonquelonst = ThriftUtils.toBaselon64elonncodelondString(relonquelonst);
      } catch (Telonxcelonption elon) {
        baselon64Relonquelonst = "Failelond to parselon baselon 64 relonquelonst";
      }
      LOG.elonrror(
          "Found unelonxpelonctelond nullcast twelonelonts: {} | parselondQuelonry: {} | relonquelonst: {} | relonsponselon: {} | "
              + "relonquelonst baselon 64: {}",
          Joinelonr.on(",").join(unelonxpelonctelondNullcastStatusIds),
          parselondQuelonry.selonrializelon(),
          relonquelonst,
          thriftSelonarchRelonsults,
          baselon64Relonquelonst);
    }
  }

  privatelon void addRelonsultPayloads() throws IOelonxcelonption {
    if (selonarchQuelonry.gelontRelonsultMelontadataOptions() != null) {
      if (selonarchQuelonry.gelontRelonsultMelontadataOptions().isGelontTwelonelontUrls()) {
        selonarchelonr.fillFacelontRelonsults(nelonw elonxpandelondUrlCollelonctor(), selonarchRelonsults);
      }

      if (selonarchQuelonry.gelontRelonsultMelontadataOptions().isGelontNamelondelonntitielons()) {
        selonarchelonr.fillFacelontRelonsults(nelonw NamelondelonntityCollelonctor(), selonarchRelonsults);
      }

      if (selonarchQuelonry.gelontRelonsultMelontadataOptions().isGelontelonntityAnnotations()) {
        selonarchelonr.fillFacelontRelonsults(nelonw elonntityAnnotationCollelonctor(), selonarchRelonsults);
      }

      if (selonarchQuelonry.gelontRelonsultMelontadataOptions().isGelontSpacelons()) {
        selonarchelonr.fillFacelontRelonsults(nelonw SpacelonFacelontCollelonctor(audioSpacelonTablelon), selonarchRelonsults);
      }
    }
  }

  /**
   * Helonlpelonr melonthod to procelonss top twelonelonts quelonry.
   */
  privatelon SelonarchRelonsultsInfo procelonssTopTwelonelontsQuelonry() throws IOelonxcelonption, Clielonntelonxcelonption {
    // selont dummy relonlelonvancelon options if it's not availablelon, but this shouldn't happelonn in prod
    if (!selonarchQuelonry.isSelontRelonlelonvancelonOptions()) {
      selonarchQuelonry.selontRelonlelonvancelonOptions(nelonw ThriftSelonarchRelonlelonvancelonOptions());
    }
    if (!selonarchQuelonry.gelontRelonlelonvancelonOptions().isSelontRankingParams()) {
      selonarchQuelonry.gelontRelonlelonvancelonOptions().selontRankingParams(
          // this is important, or it's gonna pick DelonfaultScoringFunction which prelontty much
          // doelons nothing.
          nelonw ThriftRankingParams().selontTypelon(ThriftScoringFunctionTypelon.TOPTWelonelonTS));
    }
    ScoringFunction scoringFunction = nelonw ScoringFunctionProvidelonr.DelonfaultScoringFunctionProvidelonr(
        relonquelonst, schelonmaSnapshot, selonarchQuelonry, null,
        selongmelonntManagelonr.gelontUselonrTablelon(), hitAttributelonHelonlpelonr, parselondQuelonry,
        scoringModelonlsManagelonr, telonnsorflowModelonlsManagelonr)
        .gelontScoringFunction();
    scoringFunction.selontDelonbugModelon(relonquelonst.gelontDelonbugModelon());

    RelonlelonvancelonQuelonry relonlelonvancelonQuelonry = nelonw RelonlelonvancelonQuelonry(lucelonnelonQuelonry, scoringFunction);
    RelonlelonvancelonSelonarchRelonquelonstInfo selonarchRelonquelonstInfo =
        nelonw RelonlelonvancelonSelonarchRelonquelonstInfo(
            selonarchQuelonry, relonlelonvancelonQuelonry, telonrminationTrackelonr, qualityFactor);
    selonarchRelonquelonstInfo.selontIdTimelonRangelons(idTimelonRangelons);
    selonarchRelonquelonstInfo.selontTimelonstamp(gelontQuelonryTimelonstamp(selonarchQuelonry));

    final AbstractRelonlelonvancelonCollelonctor collelonctor =
        nelonw RelonlelonvancelonTopCollelonctor(
            schelonmaSnapshot,
            selonarchRelonquelonstInfo,
            scoringFunction,
            selonarchelonrStats,
            clustelonr,
            selongmelonntManagelonr.gelontUselonrTablelon(),
            clock,
            relonquelonst.gelontDelonbugModelon());

    selontQuelonrielonsInDelonbugInfo(parselondQuelonry, selonarchRelonquelonstInfo.gelontLucelonnelonQuelonry());
    selonarchelonr.selonarch(selonarchRelonquelonstInfo.gelontLucelonnelonQuelonry(), collelonctor);

    RelonlelonvancelonSelonarchRelonsults hits = collelonctor.gelontRelonsults();
    elonarlybirdSelonarchRelonsultUtil.selontRelonsultStatistics(selonarchRelonsults, hits);
    selonarchRelonsults.selontScoringTimelonNanos(hits.gelontScoringTimelonNanos());
    elonarlyTelonrminationInfo = elonarlybirdSelonarchRelonsultUtil.prelonparelonelonarlyTelonrminationInfo(hits);
    elonarlybirdSelonarchRelonsultUtil.selontLanguagelonHistogram(
        selonarchRelonsults,
        collelonctor.gelontLanguagelonHistogram());
    elonarlybirdSelonarchRelonsultUtil.prelonparelonRelonlelonvancelonRelonsultsArray(
        selonarchRelonsults.gelontRelonsults(),
        hits,
        null,
        relonquelonst.gelontDelonbugModelon() > 0 ? partitionConfig : null);

    selonarchRelonsults.selontHitCounts(collelonctor.gelontHitCountMap());
    selonarchRelonsults.selontRelonlelonvancelonStats(hits.gelontRelonlelonvancelonStats());

    maybelonSelontCollelonctorDelonbugInfo(collelonctor);

    if (elonxplanationselonnablelond(relonquelonst.gelontDelonbugModelon())
        && selonarchQuelonry.isSelontRelonlelonvancelonOptions()
        && selonarchQuelonry.gelontRelonlelonvancelonOptions().isSelontRankingParams()) {
      selonarchelonr.elonxplainSelonarchRelonsults(selonarchRelonquelonstInfo, hits, selonarchRelonsults);
    }

    addRelonsultPayloads();

    relonturn hits;
  }

  privatelon FacelontCountStatelon nelonwFacelontCountStatelon() throws Clielonntelonxcelonption {
    int minNumFacelontRelonsults = DelonFAULT_NUM_FACelonT_RelonSULTS;
    if (facelontRelonquelonst.isSelontFacelontRankingOptions()
        && facelontRelonquelonst.gelontFacelontRankingOptions().isSelontNumCandidatelonsFromelonarlybird()) {
      minNumFacelontRelonsults = facelontRelonquelonst.gelontFacelontRankingOptions().gelontNumCandidatelonsFromelonarlybird();
    }

    // figurelon out which fielonlds welon nelonelond to count
    FacelontCountStatelon facelontCountStatelon = nelonw FacelontCountStatelon(schelonmaSnapshot, minNumFacelontRelonsults);

    // all catelongorielons if nonelon!
    if (facelontRelonquelonst.gelontFacelontFielonlds() == null || facelontRelonquelonst.gelontFacelontFielonlds().iselonmpty()) {
      for (Schelonma.FielonldInfo facelontFielonld : schelonmaSnapshot.gelontFacelontFielonlds()) {
        facelontCountStatelon.addFacelont(
            facelontFielonld.gelontFielonldTypelon().gelontFacelontNamelon(), DelonFAULT_NUM_FACelonT_RelonSULTS);
      }
    } elonlselon {
      Itelonrator<ThriftFacelontFielonldRelonquelonst> it = facelontRelonquelonst.gelontFacelontFielonldsItelonrator();
      whilelon (it.hasNelonxt()) {
        ThriftFacelontFielonldRelonquelonst facelontFielonldRelonquelonst = it.nelonxt();
        Schelonma.FielonldInfo facelont = schelonmaSnapshot.gelontFacelontFielonldByFacelontNamelon(
            facelontFielonldRelonquelonst.gelontFielonldNamelon());
        if (facelont != null) {
          facelontCountStatelon.addFacelont(
              facelont.gelontFielonldTypelon().gelontFacelontNamelon(), facelontFielonldRelonquelonst.gelontNumRelonsults());
        } elonlselon {
          throw nelonw Clielonntelonxcelonption("Unknown facelont fielonld: " + facelontFielonldRelonquelonst.gelontFielonldNamelon());
        }
      }
    }
    relonturn facelontCountStatelon;
  }

  privatelon com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry prelonLucelonnelonQuelonryProcelonss(
      com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry twittelonrQuelonry) throws QuelonryParselonrelonxcelonption {

    com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry quelonry = twittelonrQuelonry;
    if (selonarchHighFrelonquelonncyTelonrmPairs && !includelonsCardFielonld(selonarchQuelonry, quelonry)) {
      // Procelonss high frelonquelonncy telonrm pairs. Works belonst whelonn quelonry is as flat as possiblelon.
      quelonry = HighFrelonquelonncyTelonrmPairRelonwritelonVisitor.safelonRelonwritelon(
          quelonry,
          DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(
              deloncidelonr, "elonnablelon_hf_telonrm_pair_nelongativelon_disjunction_relonwritelon"));
    }
    relonturn quelonry.simplify();
  }

  privatelon Quelonry postLucelonnelonQuelonryProcelonss(final Quelonry quelonry) throws Clielonntelonxcelonption {
    if (StringUtils.isBlank(relonquelonst.gelontSelonarchQuelonry().gelontSelonrializelondQuelonry())
        && StringUtils.isBlank(relonquelonst.gelontSelonarchQuelonry().gelontLucelonnelonQuelonry())) {
      selonarchelonrStats.numRelonquelonstsWithBlankQuelonry.gelont(quelonryModelon).increlonmelonnt();
      if (selonarchQuelonry.gelontSelonarchStatusIdsSizelon() == 0
          && selonarchQuelonry.gelontFromUselonrIDFiltelonr64Sizelon() == 0
          && selonarchQuelonry.gelontLikelondByUselonrIDFiltelonr64Sizelon() == 0) {
        // No quelonry or ids to selonarch.  This is only allowelond in somelon modelons.
        if (quelonryModelon == QuelonryModelon.RelonCelonNCY
            || quelonryModelon == QuelonryModelon.RelonLelonVANCelon
            || quelonryModelon == QuelonryModelon.TOP_TWelonelonTS) {
          throw nelonw Clielonntelonxcelonption(
              "No quelonry or status ids for " + quelonryModelon.toString().toLowelonrCaselon() + " quelonry");
        }
      }
    }

    // Wrap thelon quelonry as nelonelondelond with additional quelonry filtelonrs.
    List<Quelonry> filtelonrs = Lists.nelonwArrayList();

    // Min twelonelonp crelond filtelonr.
    if (selonarchQuelonry.isSelontMinTwelonelonpCrelondFiltelonr()) {
      selonarchelonrStats.addelondFiltelonrBadUselonrRelonp.increlonmelonnt();
      filtelonrs.add(BadUselonrRelonpFiltelonr.gelontBadUselonrRelonpFiltelonr(selonarchQuelonry.gelontMinTwelonelonpCrelondFiltelonr()));
    }

    if (selonarchQuelonry.gelontFromUselonrIDFiltelonr64Sizelon() > 0) {
      this.quelonrielondFielonlds.add(elonarlybirdFielonldConstant.FROM_USelonR_ID_FIelonLD.gelontFielonldNamelon());
      this.selonarchelonrStats.addelondFiltelonrFromUselonrIds.increlonmelonnt();
      try {
        filtelonrs.add(UselonrIdMultiSelongmelonntQuelonry.crelonatelonIdDisjunctionQuelonry(
            "from_uselonr_id_filtelonr",
            selonarchQuelonry.gelontFromUselonrIDFiltelonr64(),
            elonarlybirdFielonldConstant.FROM_USelonR_ID_FIelonLD.gelontFielonldNamelon(),
            schelonmaSnapshot,
            multiSelongmelonntTelonrmDictionaryManagelonr,
            deloncidelonr,
            clustelonr,
            Lists.nelonwArrayList(),
            null,
            quelonryTimelonoutFactory.crelonatelonQuelonryTimelonout(relonquelonst, telonrminationTrackelonr, clock)));
      } catch (QuelonryParselonrelonxcelonption elon) {
        throw nelonw Clielonntelonxcelonption(elon);
      }
    }

    // Wrap thelon lucelonnelon quelonry with thelonselon filtelonrs.
    Quelonry wrappelondQuelonry = wrapFiltelonrs(quelonry, filtelonrs.toArray(nelonw Quelonry[filtelonrs.sizelon()]));

    // If selonarchStatusIds is selont, additionally modify thelon quelonry to selonarch elonxactly thelonselon
    // ids, using thelon lucelonnelonQuelonry only for scoring.
    if (selonarchQuelonry.gelontSelonarchStatusIdsSizelon() > 0) {
      this.selonarchelonrStats.addelondFiltelonrTwelonelontIds.increlonmelonnt();

      final Quelonry quelonryForScoring = wrappelondQuelonry;
      final Quelonry quelonryForRelontrielonval =
          RelonquirelondStatusIDsFiltelonr.gelontRelonquirelondStatusIDsQuelonry(selonarchQuelonry.gelontSelonarchStatusIds());

      relonturn nelonw BoolelonanQuelonry.Buildelonr()
          .add(quelonryForRelontrielonval, Occur.MUST)
          .add(quelonryForScoring, Occur.SHOULD)
          .build();
    }

    relonturn wrappelondQuelonry;
  }

  privatelon com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry gelontLikelondByUselonrIdQuelonry(
      List<Long> ids) throws QuelonryParselonrelonxcelonption {
    if (DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(
        deloncidelonr, USelon_MULTI_TelonRM_DISJUNCTION_FOR_LIKelonD_BY_USelonR_IDS_DelonCIDelonR_KelonY)) {
      // relonwritelon LikelondByUselonrIdFiltelonr64 to a multi_telonrm_disjuntion quelonry
      relonturn crelonatelonMultiTelonrmDisjunctionQuelonryForLikelondByUselonrIds(ids);
    } elonlselon {
      // relonwritelon LikelondByUselonrIdFiltelonr64 to a disjunction of multiplelon likelond_by_uselonr_ids quelonry
      relonturn crelonatelonDisjunctionQuelonryForLikelondByUselonrIds(ids);
    }
  }

  /**
   * Relonturns thelon Lucelonnelon quelonry visitor that should belon applielond to thelon original relonquelonst.
   *
   * @param fielonldWelonightMapOvelonrridelon Thelon pelonr-fielonld welonight ovelonrridelons.
   */
  @VisiblelonForTelonsting
  public elonarlybirdLucelonnelonQuelonryVisitor gelontLucelonnelonVisitor(
      Map<String, Doublelon> fielonldWelonightMapOvelonrridelon) {
    String clustelonrNamelon = clustelonr.gelontNamelonForStats();
    // Iff in relonlelonvancelon modelon _and_ intelonprelontelonSincelonId is falselon, welon turn off sincelon_id
    // opelonrator by using LucelonnelonRelonlelonvancelonQuelonryVisitor.

    if (selonarchQuelonry.gelontRankingModelon() == ThriftSelonarchRankingModelon.RelonLelonVANCelon
        && selonarchQuelonry.gelontRelonlelonvancelonOptions() != null
        && !selonarchQuelonry.gelontRelonlelonvancelonOptions().isIntelonrprelontSincelonId()) {
      // hack!  relonselont top lelonvelonl sincelon id, which is thelon samelon thing LucelonnelonRelonlelonvancelonVisitor
      // is doing.
      idTimelonRangelons = null;
      relonturn nelonw LucelonnelonRelonlelonvancelonQuelonryVisitor(
          schelonmaSnapshot,
          quelonryCachelonManagelonr,
          selongmelonntManagelonr.gelontUselonrTablelon(),
          selongmelonntManagelonr.gelontUselonrScrubGelonoMap(),
          telonrminationTrackelonr,
          FielonldWelonightDelonfault.ovelonrridelonFielonldWelonightMap(
              schelonmaSnapshot.gelontFielonldWelonightMap(),
              dropBadFielonldWelonightOvelonrridelons(fielonldWelonightMapOvelonrridelon, deloncidelonr, clustelonrNamelon)),
          MAPPABLelon_FIelonLD_MAP,
          multiSelongmelonntTelonrmDictionaryManagelonr,
          deloncidelonr,
          clustelonr,
          quelonryTimelonoutFactory.crelonatelonQuelonryTimelonout(
              relonquelonst, telonrminationTrackelonr, clock));
    } elonlselon {
      relonturn nelonw elonarlybirdLucelonnelonQuelonryVisitor(
          schelonmaSnapshot,
          quelonryCachelonManagelonr,
          selongmelonntManagelonr.gelontUselonrTablelon(),
          selongmelonntManagelonr.gelontUselonrScrubGelonoMap(),
          telonrminationTrackelonr,
          FielonldWelonightDelonfault.ovelonrridelonFielonldWelonightMap(
              schelonmaSnapshot.gelontFielonldWelonightMap(),
              dropBadFielonldWelonightOvelonrridelons(fielonldWelonightMapOvelonrridelon, deloncidelonr, clustelonrNamelon)),
          MAPPABLelon_FIelonLD_MAP,
          multiSelongmelonntTelonrmDictionaryManagelonr,
          deloncidelonr,
          clustelonr,
          quelonryTimelonoutFactory.crelonatelonQuelonryTimelonout(
              relonquelonst, telonrminationTrackelonr, clock));
    }
  }

  privatelon void prelonparelonFacelontRelonsults(ThriftFacelontRelonsults thriftFacelontRelonsults,
                                     elonarlybirdLucelonnelonSelonarchelonr.FacelontSelonarchRelonsults hits,
                                     FacelontCountStatelon<ThriftFacelontFielonldRelonsults> facelontCountStatelon,
                                     Selont<Long> uselonrIDWhitelonlist,
                                     bytelon delonbugModelon) throws IOelonxcelonption {
    for (FacelontRankingModulelon rankingModulelon : FacelontRankingModulelon.RelonGISTelonRelonD_RANKING_MODULelonS) {
      rankingModulelon.prelonparelonRelonsults(hits, facelontCountStatelon);
    }

    Map<Telonrm, ThriftFacelontCount> allFacelontRelonsults = nelonw HashMap<>();

    Itelonrator<FacelontCountStatelon.FacelontFielonldRelonsults<ThriftFacelontFielonldRelonsults>> fielonldRelonsultsItelonrator =
        facelontCountStatelon.gelontFacelontFielonldRelonsultsItelonrator();
    whilelon (fielonldRelonsultsItelonrator.hasNelonxt()) {

      FacelontCountStatelon.FacelontFielonldRelonsults<ThriftFacelontFielonldRelonsults> facelontFielonldRelonsults =
          fielonldRelonsultsItelonrator.nelonxt();

      if (facelontFielonldRelonsults.relonsults == null) {
        // relonturn elonmpty relonsultselont for this facelont
        List<ThriftFacelontCount> elonmptyList = nelonw ArrayList<>();
        facelontFielonldRelonsults.relonsults = nelonw ThriftFacelontFielonldRelonsults(elonmptyList, 0);
      }
      thriftFacelontRelonsults.putToFacelontFielonlds(facelontFielonldRelonsults.facelontNamelon,
          facelontFielonldRelonsults.relonsults);

      Schelonma.FielonldInfo fielonld = schelonmaSnapshot.gelontFacelontFielonldByFacelontNamelon(
          facelontFielonldRelonsults.facelontNamelon);

      for (ThriftFacelontCount relonsult : facelontFielonldRelonsults.relonsults.topFacelonts) {
        if (relonsult.facelontLabelonl != null) {
          allFacelontRelonsults.put(nelonw Telonrm(fielonld.gelontNamelon(), relonsult.facelontLabelonl), relonsult);
        } elonlselon {
          LOG.warn("Null facelontLabelonl, fielonld: {}, relonsult: {}", fielonld.gelontNamelon(), relonsult);
        }
      }
    }

    selonarchelonr.fillFacelontRelonsultMelontadata(allFacelontRelonsults, schelonmaSnapshot, delonbugModelon);

    if (uselonrIDWhitelonlist != null) {
      for (ThriftFacelontCount facelontCount : allFacelontRelonsults.valuelons()) {
        ThriftFacelontCountMelontadata melontadata = facelontCount.gelontMelontadata();
        if (melontadata != null) {
          melontadata.selontDontFiltelonrUselonr(uselonrIDWhitelonlist.contains(melontadata.gelontTwittelonrUselonrId()));
        }
      }
    }
  }

  privatelon void prelonparelonTelonrmStatisticsRelonsults(
      ThriftTelonrmStatisticsRelonsults telonrmStatistics,
      TelonrmStatisticsCollelonctor.TelonrmStatisticsSelonarchRelonsults hits,
      bytelon delonbugModelon) throws IOelonxcelonption {

    telonrmStatistics.selontBinIds(hits.binIds);
    telonrmStatistics.selontHistogramSelonttings(telonrmStatisticsRelonquelonst.gelontHistogramSelonttings());
    telonrmStatistics.selontTelonrmRelonsults(hits.relonsults);
    selontTelonrmStatisticsDelonbugInfo(hits.gelontTelonrmStatisticsDelonbugInfo());

    if (hits.lastComplelontelonBinId != -1) {
      telonrmStatistics.selontMinComplelontelonBinId(hits.lastComplelontelonBinId);
    } elonlselon {
      SelonarchRatelonCountelonr.elonxport(String.format(
          "telonrm_stats_%s_unselont_min_complelontelon_bin_id", relonquelonst.gelontClielonntId())).increlonmelonnt();
    }

    if (idTimelonRangelons != null
        && idTimelonRangelons.gelontUntilTimelonelonxclusivelon().isPrelonselonnt()
        && hits.gelontMinSelonarchelondTimelon() > idTimelonRangelons.gelontUntilTimelonelonxclusivelon().gelont()) {
      SelonarchRatelonCountelonr.elonxport(String.format(
          "telonrm_stats_%s_min_selonarchelond_timelon_aftelonr_until_timelon", relonquelonst.gelontClielonntId())).increlonmelonnt();
    }

    selonarchelonr.fillTelonrmStatsMelontadata(telonrmStatistics, schelonmaSnapshot, delonbugModelon);
  }

  privatelon elonarlybirdRelonsponselon relonspondSuccelonss(
      ThriftSelonarchRelonsults thriftSelonarchRelonsults,
      ThriftFacelontRelonsults thriftFacelontRelonsults,
      ThriftTelonrmStatisticsRelonsults telonrmStatisticRelonsults,
      @Nonnull elonarlyTelonrminationInfo elonarlyTelonrminationStatelon,
      @Nonnull SelonarchRelonsultsInfo selonarchRelonsultsInfo) {

    Prelonconditions.chelonckNotNull(elonarlyTelonrminationStatelon);
    Prelonconditions.chelonckNotNull(selonarchRelonsultsInfo);

    elonxportelonarlyTelonrminationStats(elonarlyTelonrminationStatelon);

    elonarlybirdRelonsponselon relonsponselon =
        nelonwRelonsponselon(elonarlybirdRelonsponselonCodelon.SUCCelonSS, relonquelonst.gelontDelonbugModelon() > 0);
    relonsponselon.selontelonarlyTelonrminationInfo(elonarlyTelonrminationStatelon);
    relonsponselon.selontNumSelonarchelondSelongmelonnts(selonarchRelonsultsInfo.gelontNumSelonarchelondSelongmelonnts());

    if (thriftSelonarchRelonsults != null) {
      // Nullcast chelonck is only uselond whelonn parselond quelonry is availablelon: if thelonrelon is no parselond quelonry,
      // welon would not add possiblelon elonxcludelon nullcast filtelonr.
      if (parselondQuelonry != null && !parselondQuelonryAllowNullcast) {
        logAndIncrelonmelonntStatsIfNullcastInRelonsults(thriftSelonarchRelonsults);
      }
      relonsponselon.selontSelonarchRelonsults(thriftSelonarchRelonsults);
    } elonlselon {
      RelonSPONSelon_HAS_NO_THRIFT_SelonARCH_RelonSULTS.increlonmelonnt();
    }
    if (thriftFacelontRelonsults != null) {
      relonsponselon.selontFacelontRelonsults(thriftFacelontRelonsults);
    }
    if (telonrmStatisticRelonsults != null) {
      relonsponselon.selontTelonrmStatisticsRelonsults(telonrmStatisticRelonsults);
    }

    appelonndFelonaturelonSchelonmaIfNelonelondelond(relonsponselon);

    appelonndLikelondByUselonrIdsIfNelonelondelond(relonsponselon);

    relonturn relonsponselon;
  }

  privatelon void elonxportelonarlyTelonrminationStats(@Nonnull elonarlyTelonrminationInfo elonarlyTelonrminationStatelon) {
    if (elonarlyTelonrminationStatelon.isSelontelonarlyTelonrminationRelonason()) {
      SelonarchRatelonCountelonr.elonxport(String.format("elonarly_telonrmination_%s_%s",
          ClielonntIdUtil.formatClielonntId(relonquelonst.gelontClielonntId()),
          elonarlyTelonrminationStatelon.gelontelonarlyTelonrminationRelonason())).increlonmelonnt();
      SelonarchRatelonCountelonr.elonxport(String.format("elonarly_telonrmination_%s_%s",
          ClielonntIdUtil.formatClielonntIdAndRelonquelonstTypelon(
              relonquelonst.gelontClielonntId(), quelonryModelon.namelon().toLowelonrCaselon()),
          elonarlyTelonrminationStatelon.gelontelonarlyTelonrminationRelonason())).increlonmelonnt();
    }
  }

  /**
   * Builds a rank -> uselonrId map for likelond_by_uselonr_id quelonrielons that relonquelonst hit attribution, and
   * appelonnds thelon relonsulting map to thelon relonsponselon.
   */
  privatelon void appelonndLikelondByUselonrIdsIfNelonelondelond(elonarlybirdRelonsponselon relonsponselon) {
    // Chelonck if uselonr askelond for likelondByUselonrIds list in relonsponselon
    ThriftSelonarchRelonlelonvancelonOptions relonsultRelonlelonvancelonOptions =
        relonquelonst.gelontSelonarchQuelonry().gelontRelonlelonvancelonOptions();
    if ((relonsultRelonlelonvancelonOptions == null)
        || !relonsultRelonlelonvancelonOptions.isCollelonctFielonldHitAttributions()) {
      relonturn;
    }

    // Makelon surelon welon havelon relonsults in relonsponselon and hit attribution helonlpelonr is selont up correlonctly
    if (!relonsponselon.isSelontSelonarchRelonsults() || hitAttributelonHelonlpelonr == null) {
      relonturn;
    }

    // Gelont rank to nodelon map
    Map<com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry, Intelongelonr> nodelonToRankMap =
        Prelonconditions.chelonckNotNull(hitAttributelonHelonlpelonr.gelontNodelonToRankMap());

    Map<com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry, List<Intelongelonr>> elonxpandelondNodelonToRankMap =
        Prelonconditions.chelonckNotNull(hitAttributelonHelonlpelonr.gelontelonxpandelondNodelonToRankMap());

    // Build a rank to id map
    ImmutablelonMap.Buildelonr<Intelongelonr, Long> buildelonr = ImmutablelonMap.buildelonr();
    for (com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry quelonry : nodelonToRankMap.kelonySelont()) {
      if (quelonry instancelonof SelonarchOpelonrator) {
        SelonarchOpelonrator op = (SelonarchOpelonrator) quelonry;
        if (elonxpandelondNodelonToRankMap.containsKelony(quelonry)) {
          // for multi_telonrm_disjunction caselon
          List<Intelongelonr> ranks = elonxpandelondNodelonToRankMap.gelont(op);
          Prelonconditions.chelonckArgumelonnt(op.gelontNumOpelonrands() == ranks.sizelon() + 1);
          for (int i = 0; i < ranks.sizelon(); ++i) {
            buildelonr.put(ranks.gelont(i), Long.valuelonOf(op.gelontOpelonrands().gelont(i + 1)));
          }
        } elonlselon if (op.gelontOpelonratorTypelon() == SelonarchOpelonrator.Typelon.LIKelonD_BY_USelonR_ID) {
          // for likelond_by_uselonr_id caselon
          Prelonconditions.chelonckArgumelonnt(op.gelontAnnotationOf(Annotation.Typelon.NODelon_RANK).isPrelonselonnt());
          buildelonr.put(
              (Intelongelonr) op.gelontAnnotationOf(Annotation.Typelon.NODelon_RANK).gelont().gelontValuelon(),
              Long.valuelonOf(op.gelontOpelonrands().gelont(0)));
        }
      }
    }
    Map<Intelongelonr, Long> rankToIdMap = buildelonr.build();

    // Appelonnd likelond_by_uselonr_id filelond into relonsult
    for (ThriftSelonarchRelonsult relonsult : relonsponselon.gelontSelonarchRelonsults().gelontRelonsults()) {
      if (relonsult.isSelontMelontadata()
          && relonsult.gelontMelontadata().isSelontFielonldHitAttribution()
          && relonsult.gelontMelontadata().gelontFielonldHitAttribution().isSelontHitMap()) {

        List<Long> likelondByUselonrIdList = Lists.nelonwArrayList();

        Map<Intelongelonr, FielonldHitList> hitMap =
            relonsult.gelontMelontadata().gelontFielonldHitAttribution().gelontHitMap();
        // itelonratelon hit attributions
        for (int rank : hitMap.kelonySelont()) {
          if (rankToIdMap.containsKelony(rank)) {
            likelondByUselonrIdList.add(rankToIdMap.gelont(rank));
          }
        }
        if (!relonsult.gelontMelontadata().isSelontelonxtraMelontadata()) {
          relonsult.gelontMelontadata().selontelonxtraMelontadata(nelonw ThriftSelonarchRelonsultelonxtraMelontadata());
        }
        relonsult.gelontMelontadata().gelontelonxtraMelontadata().selontLikelondByUselonrIds(likelondByUselonrIdList);
      }
    }
  }

  privatelon void appelonndFelonaturelonSchelonmaIfNelonelondelond(elonarlybirdRelonsponselon relonsponselon) {
    // Do not appelonnd thelon schelonma if thelon clielonnt didn't relonquelonst it.
    ThriftSelonarchRelonsultMelontadataOptions relonsultMelontadataOptions =
        relonquelonst.gelontSelonarchQuelonry().gelontRelonsultMelontadataOptions();
    if ((relonsultMelontadataOptions == null) || !relonsultMelontadataOptions.isRelonturnSelonarchRelonsultFelonaturelons()) {
      relonturn;
    }

    if (!relonsponselon.isSelontSelonarchRelonsults()) {
      relonturn;
    }

    ThriftSelonarchFelonaturelonSchelonma felonaturelonSchelonma = schelonmaSnapshot.gelontSelonarchFelonaturelonSchelonma();
    Prelonconditions.chelonckStatelon(
        felonaturelonSchelonma.isSelontSchelonmaSpeloncifielonr(),
        "Thelon felonaturelon schelonma doelonsn't havelon a schelonma speloncifielonr selont: {}", felonaturelonSchelonma);

    // If thelon clielonnt has this schelonma, welon only nelonelond to relonturn thelon schelonma velonrsion.
    // If thelon clielonnt doelonsn't havelon this schelonma, welon nelonelond to relonturn thelon schelonma elonntrielons too.
    if (relonsultMelontadataOptions.isSelontFelonaturelonSchelonmasAvailablelonInClielonnt()
        && relonsultMelontadataOptions.gelontFelonaturelonSchelonmasAvailablelonInClielonnt().contains(
        felonaturelonSchelonma.gelontSchelonmaSpeloncifielonr())) {
      CLIelonNT_HAS_FelonATURelon_SCHelonMA_COUNTelonR.increlonmelonnt();
      ThriftSelonarchFelonaturelonSchelonma relonsponselonFelonaturelonSchelonma = nelonw ThriftSelonarchFelonaturelonSchelonma();
      relonsponselonFelonaturelonSchelonma.selontSchelonmaSpeloncifielonr(felonaturelonSchelonma.gelontSchelonmaSpeloncifielonr());
      relonsponselon.gelontSelonarchRelonsults().selontFelonaturelonSchelonma(relonsponselonFelonaturelonSchelonma);
    } elonlselon {
      CLIelonNT_DOelonSNT_HAVelon_FelonATURelon_SCHelonMA_COUNTelonR.increlonmelonnt();
      Prelonconditions.chelonckStatelon(felonaturelonSchelonma.isSelontelonntrielons(),
          "elonntrielons arelon not selont in thelon felonaturelon schelonma: " + felonaturelonSchelonma);
      relonsponselon.gelontSelonarchRelonsults().selontFelonaturelonSchelonma(felonaturelonSchelonma);
    }
  }

  privatelon static long gelontQuelonryTimelonstamp(ThriftSelonarchQuelonry quelonry) {
    relonturn quelonry != null && quelonry.isSelontTimelonstampMseloncs() ? quelonry.gelontTimelonstampMseloncs() : 0;
  }

  privatelon static boolelonan includelonsCardFielonld(ThriftSelonarchQuelonry selonarchQuelonry,
                                           com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry quelonry)
      throws QuelonryParselonrelonxcelonption {

    if (selonarchQuelonry.isSelontRelonlelonvancelonOptions()) {
      ThriftSelonarchRelonlelonvancelonOptions options = selonarchQuelonry.gelontRelonlelonvancelonOptions();
      if (options.isSelontFielonldWelonightMapOvelonrridelon()
          && (options.gelontFielonldWelonightMapOvelonrridelon().containsKelony(
              elonarlybirdFielonldConstant.CARD_TITLelon_FIelonLD.gelontFielonldNamelon())
          || options.gelontFielonldWelonightMapOvelonrridelon()
          .containsKelony(elonarlybirdFielonldConstant.CARD_DelonSCRIPTION_FIelonLD.gelontFielonldNamelon()))) {

        relonturn truelon;
      }
    }

    relonturn quelonry.accelonpt(nelonw DelontelonctFielonldAnnotationVisitor(ImmutablelonSelont.of(
        elonarlybirdFielonldConstant.CARD_TITLelon_FIelonLD.gelontFielonldNamelon(),
        elonarlybirdFielonldConstant.CARD_DelonSCRIPTION_FIelonLD.gelontFielonldNamelon())));
  }

  privatelon static QuelonryModelon gelontQuelonryModelon(elonarlybirdRelonquelonst relonquelonst) {
    if (relonquelonst.isSelontFacelontRelonquelonst()) {
      relonturn QuelonryModelon.FACelonTS;
    } elonlselon if (relonquelonst.isSelontTelonrmStatisticsRelonquelonst()) {
      relonturn QuelonryModelon.TelonRM_STATS;
    }

    // Reloncelonncy modelon until welon delontelonrminelon othelonrwiselon.
    QuelonryModelon quelonryModelon = QuelonryModelon.RelonCelonNCY;
    ThriftSelonarchQuelonry selonarchQuelonry = relonquelonst.gelontSelonarchQuelonry();
    if (selonarchQuelonry != null) {
      switch (selonarchQuelonry.gelontRankingModelon()) {
        caselon RelonCelonNCY:
          quelonryModelon = QuelonryModelon.RelonCelonNCY;
          brelonak;
        caselon RelonLelonVANCelon:
          quelonryModelon = QuelonryModelon.RelonLelonVANCelon;
          brelonak;
        caselon TOPTWelonelonTS:
          quelonryModelon = QuelonryModelon.TOP_TWelonelonTS;
          brelonak;
        delonfault:
          brelonak;
      }
    }

    if (selonarchQuelonry == null
        || !selonarchQuelonry.isSelontSelonrializelondQuelonry()
        || selonarchQuelonry.gelontSelonrializelondQuelonry().iselonmpty()) {
      LOG.delonbug("Selonarch quelonry was elonmpty, quelonry modelon was " + quelonryModelon);
    }

    relonturn quelonryModelon;
  }

  privatelon static <V> ImmutablelonMap<String, V> dropBadFielonldWelonightOvelonrridelons(
      Map<String, V> map, Deloncidelonr deloncidelonr, String clustelonrNamelon) {

    if (map == null) {
      relonturn null;
    }

    FIelonLD_WelonIGHT_OVelonRRIDelon_MAP_NON_NULL_COUNT.increlonmelonnt();
    ImmutablelonMap.Buildelonr<String, V> buildelonr = ImmutablelonMap.buildelonr();

    for (Map.elonntry<String, V> elonntry : map.elonntrySelont()) {
      if (elonarlybirdFielonldConstant.CAMelonLCASelon_USelonR_HANDLelon_FIelonLD.gelontFielonldNamelon().elonquals(elonntry.gelontKelony())
          && !isAllowelondCamelonlcaselonUselonrnamelonFielonldWelonightOvelonrridelon(deloncidelonr, clustelonrNamelon)) {
        DROPPelonD_CAMelonLCASelon_USelonRNAMelon_FIelonLD_WelonIGHT_OVelonRRIDelon.increlonmelonnt();
      } elonlselon if (elonarlybirdFielonldConstant.TOKelonNIZelonD_USelonR_NAMelon_FIelonLD.gelontFielonldNamelon().elonquals(
                     elonntry.gelontKelony())
          && !isAllowelondTokelonnizelondScrelonelonnNamelonFielonldWelonightOvelonrridelon(deloncidelonr, clustelonrNamelon)) {
        DROPPelonD_TOKelonNIZelonD_DISPLAY_NAMelon_FIelonLD_WelonIGHT_OVelonRRIDelon.increlonmelonnt();
      } elonlselon {
        buildelonr.put(elonntry.gelontKelony(), elonntry.gelontValuelon());
      }
    }

    relonturn buildelonr.build();
  }

  privatelon static boolelonan isAllowelondCamelonlcaselonUselonrnamelonFielonldWelonightOvelonrridelon(
      Deloncidelonr deloncidelonr, String clustelonrNamelon) {
    relonturn DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(deloncidelonr,
        ALLOW_CAMelonLCASelon_USelonRNAMelon_FIelonLD_WelonIGHT_OVelonRRIDelon_DelonCIDelonR_KelonY_PRelonFIX + clustelonrNamelon);
  }

  privatelon static boolelonan isAllowelondTokelonnizelondScrelonelonnNamelonFielonldWelonightOvelonrridelon(
      Deloncidelonr deloncidelonr, String clustelonrNamelon) {
    relonturn DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(deloncidelonr,
        ALLOW_TOKelonNIZelonD_DISPLAY_NAMelon_FIelonLD_WelonIGHT_OVelonRRIDelon_DelonCIDelonR_KelonY_PRelonFIX + clustelonrNamelon);
  }

  privatelon static com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry
  crelonatelonMultiTelonrmDisjunctionQuelonryForLikelondByUselonrIds(List<Long> ids) throws QuelonryParselonrelonxcelonption {
    List<String> opelonrands = nelonw ArrayList<>(ids.sizelon() + 1);
    opelonrands.add(elonarlybirdFielonldConstant.LIKelonD_BY_USelonR_ID_FIelonLD.gelontFielonldNamelon());
    for (long id : ids) {
      opelonrands.add(String.valuelonOf(id));
    }
    relonturn nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.MULTI_TelonRM_DISJUNCTION, opelonrands)
        .simplify();
  }

  privatelon static com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry crelonatelonDisjunctionQuelonryForLikelondByUselonrIds(
      List<Long> ids) throws QuelonryParselonrelonxcelonption {
    relonturn nelonw Disjunction(
        ids.strelonam()
            .map(id -> nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.LIKelonD_BY_USelonR_ID, id))
            .collelonct(Collelonctors.toList()))
        .simplify();
  }

  public com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry gelontParselondQuelonry() {
    relonturn parselondQuelonry;
  }

  /**
   * Gelont thelon indelonx fielonlds that welonrelon quelonrielond aftelonr this selonarchelonr complelontelond its job.
   * @relonturn
   */
  public Selont<String> gelontQuelonrielondFielonlds() {
    relonturn quelonrielondFielonlds;
  }

  public Quelonry gelontLucelonnelonQuelonry() {
    relonturn lucelonnelonQuelonry;
  }
}
