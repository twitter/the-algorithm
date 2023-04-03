packagelon com.twittelonr.selonarch.elonarlybird;

import java.io.BuffelonrelondWritelonr;
import java.io.Closelonablelon;
import java.io.Filelon;
import java.io.IOelonxcelonption;
import java.nio.filelon.Filelons;
import java.util.ArrayList;
import java.util.List;
import java.util.Selont;
import java.util.concurrelonnt.ArrayBlockingQuelonuelon;
import java.util.concurrelonnt.elonxeloncutionelonxcelonption;
import java.util.concurrelonnt.elonxeloncutorSelonrvicelon;
import java.util.concurrelonnt.elonxeloncutors;
import java.util.concurrelonnt.Relonjelonctelondelonxeloncutionelonxcelonption;
import java.util.concurrelonnt.TimelonUnit;
import java.util.concurrelonnt.atomic.AtomicRelonfelonrelonncelon;
import javax.annotation.Nullablelon;
import javax.annotation.concurrelonnt.GuardelondBy;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Charselonts;
import com.googlelon.common.baselon.Stopwatch;
import com.googlelon.common.cachelon.CachelonBuildelonr;
import com.googlelon.common.cachelon.CachelonLoadelonr;
import com.googlelon.common.cachelon.LoadingCachelon;
import com.googlelon.common.collelonct.ImmutablelonMap;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.util.concurrelonnt.AtomicLongMap;

import org.apachelon.commons.codelonc.binary.Baselon64;
import org.apachelon.lucelonnelon.selonarch.IndelonxSelonarchelonr;
import org.apachelon.thrift.TBaselon;
import org.apachelon.thrift.Telonxcelonption;
import org.apachelon.thrift.TSelonrializelonr;
import org.apachelon.zookelonelonpelonr.Kelonelonpelonrelonxcelonption;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.collelonctions.Pair;
import com.twittelonr.common.util.Clock;
import com.twittelonr.common.zookelonelonpelonr.SelonrvelonrSelont.Updatelonelonxcelonption;
import com.twittelonr.common.zookelonelonpelonr.ZooKelonelonpelonrClielonnt;
import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.finaglelon.Failurelon;
import com.twittelonr.selonarch.common.databaselon.DatabaselonConfig;
import com.twittelonr.selonarch.common.melontrics.Pelonrcelonntilelon;
import com.twittelonr.selonarch.common.melontrics.PelonrcelonntilelonUtil;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchStatsReloncelonivelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonrStats;
import com.twittelonr.selonarch.common.melontrics.Timelonr;
import com.twittelonr.selonarch.common.schelonma.DynamicSchelonma;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.FlushVelonrsion;
import com.twittelonr.selonarch.common.selonarch.telonrmination.QuelonryTimelonoutFactory;
import com.twittelonr.selonarch.common.util.FinaglelonUtil;
import com.twittelonr.selonarch.common.util.GCUtil;
import com.twittelonr.selonarch.common.util.ml.telonnsorflow_elonnginelon.TelonnsorflowModelonlsManagelonr;
import com.twittelonr.selonarch.common.util.zookelonelonpelonr.ZooKelonelonpelonrProxy;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.QuelonryCostTrackelonr;
import com.twittelonr.selonarch.elonarlybird.admin.LastSelonarchelonsSummary;
import com.twittelonr.selonarch.elonarlybird.admin.QuelonrielondFielonldsAndSchelonmaStats;
import com.twittelonr.selonarch.elonarlybird.common.ClielonntIdUtil;
import com.twittelonr.selonarch.elonarlybird.common.elonarlybirdRelonquelonstLoggelonr;
import com.twittelonr.selonarch.elonarlybird.common.elonarlybirdRelonquelonstPostLoggelonr;
import com.twittelonr.selonarch.elonarlybird.common.elonarlybirdRelonquelonstPrelonLoggelonr;
import com.twittelonr.selonarch.elonarlybird.common.elonarlybirdRelonquelonstUtil;
import com.twittelonr.selonarch.elonarlybird.common.RelonquelonstRelonsponselonPair;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.elonarlybirdStartupelonxcelonption;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.Transielonntelonxcelonption;
import com.twittelonr.selonarch.elonarlybird.ml.ScoringModelonlsManagelonr;
import com.twittelonr.selonarch.elonarlybird.partition.AudioSpacelonTablelon;
import com.twittelonr.selonarch.elonarlybird.partition.DynamicPartitionConfig;
import com.twittelonr.selonarch.elonarlybird.partition.elonarlybirdStartup;
import com.twittelonr.selonarch.elonarlybird.partition.MultiSelongmelonntTelonrmDictionaryManagelonr;
import com.twittelonr.selonarch.elonarlybird.partition.PartitionConfig;
import com.twittelonr.selonarch.elonarlybird.partition.PartitionManagelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SelonarchIndelonxingMelontricSelont;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntManagelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntSyncConfig;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntVulturelon;
import com.twittelonr.selonarch.elonarlybird.quelonrycachelon.QuelonryCachelonManagelonr;
import com.twittelonr.selonarch.elonarlybird.stats.elonarlybirdRPCStats;
import com.twittelonr.selonarch.elonarlybird.stats.elonarlybirdSelonarchelonrStats;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdSelonrvelonrStats;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdSelonrvicelon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdStatusCodelon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdStatusRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird.util.OnelonTaskSchelondulelondelonxeloncutorManagelonr;
import com.twittelonr.selonarch.elonarlybird.util.TelonrmCountMonitor;
import com.twittelonr.selonarch.elonarlybird.util.TwelonelontCountMonitor;
import com.twittelonr.snowflakelon.id.SnowflakelonId;
import com.twittelonr.util.Duration;
import com.twittelonr.util.Function;
import com.twittelonr.util.Function0;
import com.twittelonr.util.Futurelon;

public class elonarlybirdSelonrvelonr implelonmelonnts elonarlybirdSelonrvicelon.SelonrvicelonIfacelon, SelonrvelonrSelontMelonmbelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonarlybirdSelonrvelonr.class);

  privatelon static final String elonARLYBIRD_STARTUP = "elonarlybird startup";
  public static final String SelonRVICelon_NAMelon = "elonarlybird";

  privatelon static final boolelonan RelonGISTelonR_WITH_ZK_ON_STARTUP =
      elonarlybirdConfig.gelontBool("relongistelonr_with_zk_on_startup", truelon);
  privatelon static final Duration SelonRVelonR_CLOSelon_WAIT_TIMelon = Duration.apply(5L, TimelonUnit.SelonCONDS);

  privatelon static final Failurelon QUelonUelon_FULL_FAILURelon =
      Failurelon.relonjelonctelond("Relonjelonctelond duelon to full elonxeloncutor quelonuelon");

  privatelon final int port = elonarlybirdConfig.gelontThriftPort();
  privatelon final int warmUpPort = elonarlybirdConfig.gelontWarmUpThriftPort();
  privatelon final int numSelonarchelonrThrelonads = elonarlybirdConfig.gelontSelonarchelonrThrelonads();

  privatelon final SelonarchStatsReloncelonivelonr elonarlybirdSelonrvelonrStatsReloncelonivelonr;
  privatelon final elonarlybirdRPCStats selonarchStats = nelonw elonarlybirdRPCStats("selonarch");
  privatelon final elonarlybirdSelonarchelonrStats twelonelontsSelonarchelonrStats;

  privatelon static final String RelonQUelonSTS_RelonCelonIVelonD_BY_FINAGLelon_ID_COUNTelonR_NAMelon_PATTelonRN =
      "relonquelonsts_for_finaglelon_id_%s_all";
  privatelon static final String RelonQUelonSTS_RelonCelonIVelonD_BY_FINAGLelon_ID_AND_CLIelonNT_ID_COUNTelonR_NAMelon_PATTelonRN =
      "relonquelonsts_for_finaglelon_id_%s_and_clielonnt_id_%s";
  privatelon static final String RelonSPONSelonS_PelonR_CLIelonNT_ID_STAT_TelonMPLATelon =
      "relonsponselons_for_clielonnt_id_%s_with_relonsponselon_codelon_%s";

  // Loading cachelon for pelonr finaglelon-clielonnt-id stats. Storing thelonm in a loading cachelon kelony-elond by
  // finaglelon clielonnt id so welon don't elonxport thelon stat multiplelon timelons.
  privatelon final LoadingCachelon<String, SelonarchTimelonrStats> relonquelonstCountelonrsByFinaglelonClielonntId =
      CachelonBuildelonr.nelonwBuildelonr().build(
          nelonw CachelonLoadelonr<String, SelonarchTimelonrStats>() {
            @Ovelonrridelon
            public SelonarchTimelonrStats load(String finaglelonClielonntId) {
              relonturn elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontTimelonrStats(
                  String.format(
                      RelonQUelonSTS_RelonCelonIVelonD_BY_FINAGLelon_ID_COUNTelonR_NAMelon_PATTelonRN,
                      finaglelonClielonntId), TimelonUnit.MICROSelonCONDS, falselon, truelon, falselon);
            }
          });

  // Countelonrs pelonr clielonnt and relonsponselon codelon.
  privatelon final LoadingCachelon<String, SelonarchCountelonr> relonsponselonByClielonntIdAndRelonsponselonCodelon =
      CachelonBuildelonr.nelonwBuildelonr().build(
          nelonw CachelonLoadelonr<String, SelonarchCountelonr>() {
              @Ovelonrridelon
              public SelonarchCountelonr load(String kelony) {
                  relonturn elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontCountelonr(kelony);
              }
          });

  privatelon final LoadingCachelon<String, SelonarchCountelonr> relonsultsAgelonCountelonr =
      CachelonBuildelonr.nelonwBuildelonr().build(
          nelonw CachelonLoadelonr<String, SelonarchCountelonr>() {
            @Ovelonrridelon
            public SelonarchCountelonr load(String kelony) {
              relonturn elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontCountelonr(kelony);
            }
          }
      );

  // Loading cachelon for pelonr finaglelon clielonnt id and clielonnt id stats. Thelonselon arelon storelond selonparatelon
  // from thelon othelonr stats beloncauselon thelony arelon kelony-elond by thelon pair of finaglelon clielonnt id and clielonnt id
  // in ordelonr to makelon surelon thelon stats arelon only elonxportelond oncelon.
  // In thelon kelony-pair thelon first elonlelonmelonnt is thelon finaglelon clielonnt id whilelon thelon seloncond elonlelonmelonnt is thelon
  // clielonnt id.
  privatelon final LoadingCachelon<Pair<String, String>, SelonarchRatelonCountelonr>
      relonquelonstCountelonrsByFinaglelonIdAndClielonntId = CachelonBuildelonr.nelonwBuildelonr().build(
          nelonw CachelonLoadelonr<Pair<String, String>, SelonarchRatelonCountelonr>() {
            @Ovelonrridelon
            public SelonarchRatelonCountelonr load(Pair<String, String> clielonntKelony) {
              relonturn elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontRatelonCountelonr(
                  String.format(
                      RelonQUelonSTS_RelonCelonIVelonD_BY_FINAGLelon_ID_AND_CLIelonNT_ID_COUNTelonR_NAMelon_PATTelonRN,
                      clielonntKelony.gelontFirst(),
                      clielonntKelony.gelontSeloncond()));
            }
          });

  // Loading cachelon for pelonr-clielonnt-id latelonncy stats. Storelond in a loading cachelon helonrelon mainly beloncauselon
  // thelon telonsts asselonrt thelon mock stats reloncelonivelonr that elonach stat is only elonxportelond oncelon.
  privatelon final LoadingCachelon<String, SelonarchTimelonrStats> clielonntIdSelonarchStats =
      CachelonBuildelonr.nelonwBuildelonr().build(
          nelonw CachelonLoadelonr<String, SelonarchTimelonrStats>() {
            @Ovelonrridelon
            public SelonarchTimelonrStats load(String clielonntId) {
              String formattelondClielonntId = ClielonntIdUtil.formatClielonntId(clielonntId);
              relonturn elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontTimelonrStats(formattelondClielonntId,
                  TimelonUnit.MICROSelonCONDS, falselon, truelon, truelon);
            }
          });

  privatelon final LoadingCachelon<String, SelonarchTimelonrStats> clielonntIdScoringPelonrQuelonryStats =
      CachelonBuildelonr.nelonwBuildelonr().build(
          nelonw CachelonLoadelonr<String, SelonarchTimelonrStats>() {
            @Ovelonrridelon
            public SelonarchTimelonrStats load(String clielonntId) {
              String statNamelon =
                  String.format("scoring_timelon_pelonr_quelonry_for_clielonnt_id_%s", clielonntId);
              relonturn elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontTimelonrStats(statNamelon,
                  TimelonUnit.NANOSelonCONDS, falselon, truelon, falselon);
            }
          });

  privatelon final LoadingCachelon<String, SelonarchTimelonrStats> clielonntIdScoringPelonrHitStats =
      CachelonBuildelonr.nelonwBuildelonr().build(
          nelonw CachelonLoadelonr<String, SelonarchTimelonrStats>() {
            @Ovelonrridelon
            public SelonarchTimelonrStats load(String clielonntId) {
              String statNamelon =
                  String.format("scoring_timelon_pelonr_hit_for_clielonnt_id_%s", clielonntId);
              relonturn elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontTimelonrStats(statNamelon,
                  TimelonUnit.NANOSelonCONDS, falselon, truelon, falselon);
            }
          });

  privatelon final LoadingCachelon<String, Pelonrcelonntilelon<Intelongelonr>> clielonntIdScoringNumHitsProcelonsselondStats =
      CachelonBuildelonr.nelonwBuildelonr().build(
          nelonw CachelonLoadelonr<String, Pelonrcelonntilelon<Intelongelonr>>() {
            @Ovelonrridelon
            public Pelonrcelonntilelon<Intelongelonr> load(String clielonntId) {
              String statNamelon =
                  String.format("scoring_num_hits_procelonsselond_for_clielonnt_id_%s", clielonntId);
              relonturn PelonrcelonntilelonUtil.crelonatelonPelonrcelonntilelon(statNamelon);
            }
          });

  privatelon final LoadingCachelon<String, AtomicRelonfelonrelonncelon<RelonquelonstRelonsponselonPair>> lastRelonquelonstPelonrClielonntId =
      CachelonBuildelonr.nelonwBuildelonr().build(
          nelonw CachelonLoadelonr<String, AtomicRelonfelonrelonncelon<RelonquelonstRelonsponselonPair>>() {
            @Ovelonrridelon
            public AtomicRelonfelonrelonncelon<RelonquelonstRelonsponselonPair> load(String kelony) throws elonxcelonption {
              relonturn nelonw AtomicRelonfelonrelonncelon<>(null);
            }
          });


  privatelon final SelonarchTimelonrStats ovelonrallScoringTimelonPelonrQuelonryStats;
  privatelon final SelonarchTimelonrStats ovelonrallScoringTimelonPelonrHitStats;
  privatelon final Pelonrcelonntilelon<Intelongelonr> ovelonrallScoringNumHitsProcelonsselondStats;

  privatelon final elonarlybirdIndelonxConfig elonarlybirdIndelonxConfig;
  privatelon final DynamicPartitionConfig dynamicPartitionConfig;
  privatelon final SelongmelonntManagelonr selongmelonntManagelonr;
  privatelon final UpdatelonablelonelonarlybirdStatelonManagelonr statelonManagelonr;
  privatelon final AudioSpacelonTablelon audioSpacelonTablelon;

  privatelon final SelonarchLongGaugelon startupTimelonGaugelon;

  // Timelon spelonnt in an intelonrnal threlonad pool quelonuelon, belontwelonelonn thelon timelon welon gelont thelon selonarch relonquelonst
  // from finaglelon until it actually starts beloning elonxeloncutelond.
  privatelon final SelonarchTimelonrStats intelonrnalQuelonuelonWaitTimelonStats;

  // Tracking relonquelonst that havelon elonxcelonelondelond thelonir allocatelond timelonout prior to us actually beloning ablelon
  // to start elonxeloncuting thelon selonarch.
  privatelon final SelonarchCountelonr relonquelonstTimelonoutelonxcelonelondelondBelonforelonSelonarchCountelonr;
  // Currelonnt numbelonr of running selonarchelonr threlonads.
  privatelon final SelonarchLongGaugelon numSelonarchelonrThrelonadsGaugelon;
  privatelon final QuelonryTimelonoutFactory quelonryTimelonoutFactory;

  privatelon PartitionManagelonr partitionManagelonr;
  privatelon QuelonryCachelonManagelonr quelonryCachelonManagelonr;

  privatelon final ScoringModelonlsManagelonr scoringModelonlsManagelonr;

  privatelon final TelonnsorflowModelonlsManagelonr telonnsorflowModelonlsManagelonr;

  privatelon final elonarlybirdRelonquelonstPrelonLoggelonr relonquelonstPrelonLoggelonr;
  privatelon final elonarlybirdRelonquelonstPostLoggelonr relonquelonstLoggelonr;

  privatelon final TwelonelontCountMonitor twelonelontCountMonitor;
  privatelon final TelonrmCountMonitor telonrmCountMonitor;

  privatelon final elonarlybirdSelonrvelonrSelontManagelonr selonrvelonrSelontManagelonr;
  privatelon final elonarlybirdWarmUpManagelonr warmUpManagelonr;
  privatelon final MultiSelongmelonntTelonrmDictionaryManagelonr multiSelongmelonntTelonrmDictionaryManagelonr;

  privatelon final Objelonct shutdownLock = nelonw Objelonct();
  @GuardelondBy("shutdownLock")
  privatelon final elonarlybirdFuturelonPoolManagelonr futurelonPoolManagelonr;
  @GuardelondBy("shutdownLock")
  privatelon final elonarlybirdFinaglelonSelonrvelonrManagelonr finaglelonSelonrvelonrManagelonr;

  // If a selonarch relonquelonst comelons in with a clielonnt-sidelon start timelon, and welon selonelon that baselond on that
  // thelon timelonout has elonxpirelond, whelonthelonr welon should drop that quelonry immelondiatelonly.
  privatelon final boolelonan skipTimelondOutRelonquelonsts =
      elonarlybirdConfig.gelontBool("skip_timelondout_relonquelonsts", falselon);

  // clielonnt of szookelonelonpelonr.local.twittelonr.com.
  // This is uselond to pelonrform distributelond locking and layout relonading elontc.
  privatelon final ZooKelonelonpelonrProxy sZooKelonelonpelonrClielonnt;

  privatelon final Deloncidelonr deloncidelonr;

  privatelon final Clock clock;

  privatelon final List<Closelonablelon> toCloselon = nelonw ArrayList<>();

  privatelon final SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont;

  privatelon final elonarlybirdDarkProxy elonarlybirdDarkProxy;

  privatelon final ImmutablelonMap<elonarlybirdRelonsponselonCodelon, SelonarchCountelonr> relonsponselonCodelonCountelonrs;
  privatelon final SelongmelonntSyncConfig selongmelonntSyncConfig;
  privatelon final elonarlybirdStartup elonarlybirdStartup;
  privatelon final QualityFactor qualityFactor;

  privatelon boolelonan isShutdown = falselon;
  privatelon boolelonan isShuttingDown = falselon;

  privatelon final AtomicLongMap<String> quelonrielondFielonldsCounts = AtomicLongMap.crelonatelon();

  public elonarlybirdSelonrvelonr(QuelonryCachelonManagelonr quelonryCachelonManagelonr,
                         ZooKelonelonpelonrProxy sZkClielonnt,
                         Deloncidelonr deloncidelonr,
                         elonarlybirdIndelonxConfig elonarlybirdIndelonxConfig,
                         DynamicPartitionConfig dynamicPartitionConfig,
                         PartitionManagelonr partitionManagelonr,
                         SelongmelonntManagelonr selongmelonntManagelonr,
                         AudioSpacelonTablelon audioSpacelonTablelon,
                         TelonrmCountMonitor telonrmCountMonitor,
                         TwelonelontCountMonitor twelonelontCountMonitor,
                         UpdatelonablelonelonarlybirdStatelonManagelonr elonarlybirdStatelonManagelonr,
                         elonarlybirdFuturelonPoolManagelonr futurelonPoolManagelonr,
                         elonarlybirdFinaglelonSelonrvelonrManagelonr finaglelonSelonrvelonrManagelonr,
                         elonarlybirdSelonrvelonrSelontManagelonr selonrvelonrSelontManagelonr,
                         elonarlybirdWarmUpManagelonr warmUpManagelonr,
                         SelonarchStatsReloncelonivelonr elonarlybirdSelonrvelonrStatsReloncelonivelonr,
                         elonarlybirdSelonarchelonrStats twelonelontsSelonarchelonrStats,
                         ScoringModelonlsManagelonr scoringModelonlsManagelonr,
                         TelonnsorflowModelonlsManagelonr telonnsorflowModelonlsManagelonr,
                         Clock clock,
                         MultiSelongmelonntTelonrmDictionaryManagelonr multiSelongmelonntTelonrmDictionaryManagelonr,
                         elonarlybirdDarkProxy elonarlybirdDarkProxy,
                         SelongmelonntSyncConfig selongmelonntSyncConfig,
                         QuelonryTimelonoutFactory quelonryTimelonoutFactory,
                         elonarlybirdStartup elonarlybirdStartup,
                         QualityFactor qualityFactor,
                         SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont) {
    LOG.info("Crelonating elonarlybirdSelonrvelonr");
    this.deloncidelonr = deloncidelonr;
    this.clock = clock;
    this.sZooKelonelonpelonrClielonnt = sZkClielonnt;
    this.elonarlybirdIndelonxConfig = elonarlybirdIndelonxConfig;
    this.dynamicPartitionConfig = dynamicPartitionConfig;
    this.selongmelonntManagelonr = selongmelonntManagelonr;
    this.quelonryCachelonManagelonr = quelonryCachelonManagelonr;
    this.telonrmCountMonitor = telonrmCountMonitor;
    this.twelonelontCountMonitor = twelonelontCountMonitor;
    this.statelonManagelonr = elonarlybirdStatelonManagelonr;
    this.partitionManagelonr = partitionManagelonr;
    this.futurelonPoolManagelonr = futurelonPoolManagelonr;
    this.finaglelonSelonrvelonrManagelonr = finaglelonSelonrvelonrManagelonr;
    this.selonrvelonrSelontManagelonr = selonrvelonrSelontManagelonr;
    this.warmUpManagelonr = warmUpManagelonr;
    this.elonarlybirdSelonrvelonrStatsReloncelonivelonr = elonarlybirdSelonrvelonrStatsReloncelonivelonr;
    this.twelonelontsSelonarchelonrStats = twelonelontsSelonarchelonrStats;
    this.scoringModelonlsManagelonr = scoringModelonlsManagelonr;
    this.telonnsorflowModelonlsManagelonr = telonnsorflowModelonlsManagelonr;
    this.multiSelongmelonntTelonrmDictionaryManagelonr = multiSelongmelonntTelonrmDictionaryManagelonr;
    this.selonarchIndelonxingMelontricSelont = selonarchIndelonxingMelontricSelont;
    this.elonarlybirdDarkProxy = elonarlybirdDarkProxy;
    this.selongmelonntSyncConfig = selongmelonntSyncConfig;
    this.quelonryTimelonoutFactory = quelonryTimelonoutFactory;
    this.elonarlybirdStartup = elonarlybirdStartup;
    this.qualityFactor = qualityFactor;
    this.audioSpacelonTablelon = audioSpacelonTablelon;

    elonarlybirdStatus.selontStartTimelon(Systelonm.currelonntTimelonMillis());

    // Our initial status codelon is STARTING.
    elonarlybirdStatus.selontStatus(elonarlybirdStatusCodelon.STARTING);
    elonarlybirdStatus.THRIFT_SelonRVICelon_STARTelonD.selont(falselon);

    PartitionConfig partitionConfig = dynamicPartitionConfig.gelontCurrelonntPartitionConfig();
    elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontLongGaugelon(
        "selonarch_clustelonr_" + partitionConfig.gelontClustelonrNamelon()).selont(1);
    elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontLongGaugelon(
        "tielonr_namelon_" + partitionConfig.gelontTielonrNamelon()).selont(1);

    elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontLongGaugelon("partition").selont(
        partitionConfig.gelontIndelonxingHashPartitionID());
    elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontLongGaugelon("relonplica").selont(
        partitionConfig.gelontHostPositionWithinHashPartition());
    elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontLongGaugelon("pelonnguin_velonrsion").selont(
        elonarlybirdConfig.gelontPelonnguinVelonrsionBytelon());

    elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontLongGaugelon("flush_velonrsion").selont(
        FlushVelonrsion.CURRelonNT_FLUSH_VelonRSION.ordinal());
    String buildGelonn = elonarlybirdConfig.gelontString("offlinelon_selongmelonnt_build_gelonn", "unknown");
    elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontLongGaugelon("build_gelonn_" + buildGelonn).selont(1);

    this.startupTimelonGaugelon = elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontLongGaugelon("startup_timelon_millis");
    this.intelonrnalQuelonuelonWaitTimelonStats = elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontTimelonrStats(
        "intelonrnal_quelonuelon_wait_timelon", TimelonUnit.MILLISelonCONDS, falselon, truelon, falselon);
    this.relonquelonstTimelonoutelonxcelonelondelondBelonforelonSelonarchCountelonr = elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontCountelonr(
        "relonquelonst_timelonout_elonxcelonelondelond_belonforelon_selonarch");
    this.numSelonarchelonrThrelonadsGaugelon =
        elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontLongGaugelon("num_selonarchelonr_threlonads");
    this.ovelonrallScoringTimelonPelonrQuelonryStats = elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontTimelonrStats(
        "ovelonrall_scoring_timelon_pelonr_quelonry", TimelonUnit.NANOSelonCONDS, falselon, truelon, falselon);

    // For most of our scoring functions thelon scoring_timelon_pelonr_hit reloncords thelon actual timelon to scorelon a
    // singlelon hit. Howelonvelonr, thelon telonnsorflow baselond scoring function uselons batch scoring, so welon do not
    // know thelon actual timelon it takelons to scorelon a singlelon hit. Welon arelon now including batch scoring timelon
    // in all scoring timelon stats (SelonARCH-26014), which melonans that thelon scoring_timelon_pelonr_hit stat may
    // belon a bit mislelonading for telonnsorflow baselond quelonrielons. For thelonselon quelonrielons thelon scoring_timelon_pelonr_hit
    // relonprelonselonnts thelon ratio belontwelonelonn total_scoring_timelon and thelon numbelonr_of_hits, instelonad of thelon actual
    // timelon to scorelon a singlelon hit.
    this.ovelonrallScoringTimelonPelonrHitStats = elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontTimelonrStats(
        "ovelonrall_scoring_timelon_pelonr_hit", TimelonUnit.NANOSelonCONDS, falselon, truelon, falselon);
    this.ovelonrallScoringNumHitsProcelonsselondStats = PelonrcelonntilelonUtil.crelonatelonPelonrcelonntilelon(
        "ovelonrall_scoring_num_hits_procelonsselond");

    ImmutablelonMap.Buildelonr<elonarlybirdRelonsponselonCodelon, SelonarchCountelonr> relonsponselonCodelonCountelonrsBuildelonr =
        nelonw ImmutablelonMap.Buildelonr<>();
    for (elonarlybirdRelonsponselonCodelon relonsponselonCodelon : elonarlybirdRelonsponselonCodelon.valuelons()) {
      relonsponselonCodelonCountelonrsBuildelonr.put(
          relonsponselonCodelon,
          elonarlybirdSelonrvelonrStatsReloncelonivelonr.gelontCountelonr(
              "relonsponselons_with_relonsponselon_codelon_" + relonsponselonCodelon.namelon().toLowelonrCaselon()));
    }
    relonsponselonCodelonCountelonrs = relonsponselonCodelonCountelonrsBuildelonr.build();

    disablelonLucelonnelonQuelonryCachelon();
    initManagelonrs();

    relonquelonstPrelonLoggelonr = elonarlybirdRelonquelonstPrelonLoggelonr.buildForShard(
      elonarlybirdConfig.gelontInt("latelonncy_warn_threlonshold", 100), deloncidelonr);
    relonquelonstLoggelonr = elonarlybirdRelonquelonstPostLoggelonr.buildForShard(
        elonarlybirdConfig.gelontInt("latelonncy_warn_threlonshold", 100), deloncidelonr);

    this.qualityFactor.startUpdatelons();

    LOG.info("Crelonatelond elonarlybirdSelonrvelonr");
  }

  public boolelonan isShutdown() {
    relonturn this.isShutdown;
  }

  privatelon void initManagelonrs() {
    LOG.info("Crelonatelond elonarlybirdIndelonxConfig: " + elonarlybirdIndelonxConfig.gelontClass().gelontSimplelonNamelon());

    selongmelonntManagelonr.addUpdatelonListelonnelonr(quelonryCachelonManagelonr);
  }

  public PartitionManagelonr gelontPartitionManagelonr() {
    relonturn partitionManagelonr;
  }

  public QuelonryCachelonManagelonr gelontQuelonryCachelonManagelonr() {
    relonturn quelonryCachelonManagelonr;
  }

  public SelongmelonntManagelonr gelontSelongmelonntManagelonr() {
    relonturn selongmelonntManagelonr;
  }

  public MultiSelongmelonntTelonrmDictionaryManagelonr gelontMultiSelongmelonntTelonrmDictionaryManagelonr() {
    relonturn this.multiSelongmelonntTelonrmDictionaryManagelonr;
  }

  @VisiblelonForTelonsting
  public int gelontPort() {
    relonturn port;
  }

  privatelon void disablelonLucelonnelonQuelonryCachelon() {
    // SelonARCH-30046: Look into possibly relon-elonnabling thelon quelonry -> welonight cachelon.
    // Welon can't uselon this cachelon until welon upgradelon to Lucelonnelon 6.0.0, beloncauselon welon havelon quelonrielons with a
    // boost of 0.0, and thelony don't play nicelonly with Lucelonnelon's LRUQuelonryCachelon.gelont() melonthod.
    //
    // Lucelonnelon 6.0.0 changelons how boosts arelon handlelond: "relonal" boosts should belon wrappelond into BoostQuelonry
    // instancelons, and quelonrielons with a boost of 0.0 should belon relonwrittelonn as "filtelonrs"
    // (BoolelonanQuelonry.add(quelonry, BoolelonanClauselon.Occur.FILTelonR)). So whelonn welon upgradelon to Lucelonnelon 6.0.0 welon
    // will belon forcelond to relonfactor how welon handlelon our currelonnt quelonrielons with a boost of 0.0, which might
    // allow us to relon-elonnablelon this cachelon.
    //
    // Notelon that disabling this cachelon is not a relongrelonssion: it should givelon us thelon belonhavior that welon
    // had with Lucelonnelon 5.2.1 (and it's unclelonar if this cachelon is uselonful at all).
    //
    // WARNING: Thelon delonfault 'DelonfaultQuelonryCachelon' maintains a static relonfelonrelonncelon to thelon welonight forelonvelonr,
    // causing a melonmory lelonak. Our welonights hold relonfelonrelonncelons to an elonntirelon selongmelonnt so thelon melonmory lelonak is
    // significant.
    IndelonxSelonarchelonr.selontDelonfaultQuelonryCachelon(null);
  }

  /**
   * Starts thelon elonarlybird selonrvelonr.
   */
  public void start() throws elonarlybirdStartupelonxcelonption {
    // Makelon surelon this is at thelon top of thelon function belonforelon othelonr parts of thelon systelonm start running
    nelonw elonarlybirdBlacklistHandlelonr(Clock.SYSTelonM_CLOCK, sZooKelonelonpelonrClielonnt)
        .blockThelonnelonxitIfBlacklistelond();

    Stopwatch startupWatch = Stopwatch.crelonatelonStartelond();
    elonarlybirdStatus.belonginelonvelonnt(elonARLYBIRD_STARTUP, selonarchIndelonxingMelontricSelont.startupInProgrelonss);

    LOG.info("java.library.path is: " + Systelonm.gelontPropelonrty("java.library.path"));

    PartitionConfig partitionConfig = dynamicPartitionConfig.gelontCurrelonntPartitionConfig();

    SelongmelonntVulturelon.relonmovelonUnuselondSelongmelonnts(partitionManagelonr, partitionConfig,
        elonarlybirdIndelonxConfig.gelontSchelonma().gelontMajorVelonrsionNumbelonr(), selongmelonntSyncConfig);

    // Start thelon schelonma managelonr
    schelondulelon(statelonManagelonr);

    Closelonablelon closelonablelon = elonarlybirdStartup.start();
    toCloselon.add(closelonablelon);
    if (elonarlybirdStatus.gelontStatusCodelon() == elonarlybirdStatusCodelon.STOPPING) {
      LOG.info("Selonrvelonr is shutdown. elonxiting...");
      relonturn;
    }

    startupTimelonGaugelon.selont(startupWatch.elonlapselond(TimelonUnit.MILLISelonCONDS));

    elonarlybirdStatus.elonndelonvelonnt(elonARLYBIRD_STARTUP, selonarchIndelonxingMelontricSelont.startupInProgrelonss);

    GCUtil.runGC();  // Attelonmpt to forcelon a full GC belonforelon joining thelon selonrvelonrselont

    try {
      startThriftSelonrvicelon(null, truelon);
    } catch (Intelonrruptelondelonxcelonption elon) {
      LOG.info("Intelonrruptelond whilelon starting thrift selonrvelonr, quitting elonarlybird");
      throw nelonw elonarlybirdStartupelonxcelonption("Intelonrruptelond whilelon starting thrift selonrvelonr");
    }

    elonarlybirdStatus.THRIFT_SelonRVICelon_STARTelonD.selont(truelon);

    // only oncelon welon'relon currelonnt, kick off daily twelonelont count monitors only for archivelon clustelonr
    if (elonarlybirdConfig.gelontInt(TwelonelontCountMonitor.RUN_INTelonRVAL_MINUTelonS_CONFIG_NAMelon, -1) > 0) {
      schelondulelon(twelonelontCountMonitor);
    }

    // only oncelon welon'relon currelonnt, kick off pelonr-fielonld telonrm count monitors
    if (elonarlybirdConfig.gelontInt(TelonrmCountMonitor.RUN_INTelonRVAL_MINUTelonS_CONFIG_NAMelon, -1) > 0) {
      schelondulelon(telonrmCountMonitor);
    }

    startupTimelonGaugelon.selont(startupWatch.elonlapselond(TimelonUnit.MILLISelonCONDS));
    LOG.info("elonarlybirdSelonrvelonr start up timelon: {}", startupWatch);
  }

  /**
   * Starts thelon thrift selonrvelonr if thelon selonrvelonr is not running.
   * If selonarchelonrThrelonads is null, it uselons thelon valuelon speloncifielond by elonarlybirdConfig.
   */
  public void startThriftSelonrvicelon(@Nullablelon Intelongelonr selonarchelonrThrelonads, boolelonan isStartingUp)
      throws Intelonrruptelondelonxcelonption {
    synchronizelond (shutdownLock) {
      if (!finaglelonSelonrvelonrManagelonr.isWarmUpSelonrvelonrRunning()
          && !finaglelonSelonrvelonrManagelonr.isProductionSelonrvelonrRunning()) {
        int threlonadCount = selonarchelonrThrelonads != null
            ? selonarchelonrThrelonads : this.numSelonarchelonrThrelonads;
        LOG.info("Starting selonarchelonr pool with " + threlonadCount + " threlonads");
        futurelonPoolManagelonr.crelonatelonUndelonrlyingFuturelonPool(threlonadCount);
        numSelonarchelonrThrelonadsGaugelon.selont(threlonadCount);

        // If thelon selonrvelonr is not shutting down, go through thelon warm up stagelon. If thelon selonrvelonr is
        // instructelond to shut down during warm up, warmUpManagelonr.warmUp() should relonturn within a
        // seloncond, and should lelonavelon thelon warm up selonrvelonr selont. Welon should still shut down thelon warm up
        // Finaglelon selonrvelonr.
        if (isStartingUp && (elonarlybirdStatus.gelontStatusCodelon() != elonarlybirdStatusCodelon.STOPPING)) {
          LOG.info("Opelonning warmup thrift port...");
          finaglelonSelonrvelonrManagelonr.startWarmUpFinaglelonSelonrvelonr(this, SelonRVICelon_NAMelon, warmUpPort);
          elonarlybirdStatus.WARMUP_THRIFT_PORT_OPelonN.selont(truelon);

          try {
            warmUpManagelonr.warmUp();
          } catch (Updatelonelonxcelonption elon) {
            LOG.warn("Could not join or lelonavelon thelon warm up selonrvelonr selont.", elon);
          } finally {
            finaglelonSelonrvelonrManagelonr.stopWarmUpFinaglelonSelonrvelonr(SelonRVelonR_CLOSelon_WAIT_TIMelon);
            elonarlybirdStatus.WARMUP_THRIFT_PORT_OPelonN.selont(falselon);
          }
        }

        // If thelon selonrvelonr is not shutting down, welon can start thelon production Finaglelon selonrvelonr and join
        // thelon production selonrvelonr selont.
        if (elonarlybirdStatus.gelontStatusCodelon() != elonarlybirdStatusCodelon.STOPPING) {
          LOG.info("Opelonning production thrift port...");
          finaglelonSelonrvelonrManagelonr.startProductionFinaglelonSelonrvelonr(
              elonarlybirdDarkProxy.gelontDarkProxy(), this, SelonRVICelon_NAMelon, port);
          elonarlybirdStatus.THRIFT_PORT_OPelonN.selont(truelon);

          if (RelonGISTelonR_WITH_ZK_ON_STARTUP) {
            // Aftelonr thelon elonarlybird starts up, relongistelonr with ZooKelonelonpelonr.
            try {
              joinSelonrvelonrSelont("intelonrnal start-up");

              // Join selonparatelon selonrvelonr selont for SelonrvicelonProxy on Archivelon elonarlybirds
              if (!elonarlybirdConfig.isAurora()) {
                joinSelonrvelonrSelontForSelonrvicelonProxy();
              }
            } catch (Updatelonelonxcelonption elon) {
              throw nelonw Runtimelonelonxcelonption("Unablelon to join SelonrvelonrSelont during startup.", elon);
            }
          }
        }
      }
    }
  }

  /**
   * Stops thelon thrift selonrvelonr if thelon selonrvelonr is alrelonady running.
   */
  public void stopThriftSelonrvicelon(boolelonan shouldShutDown) {
    synchronizelond (shutdownLock) {
      try {
        lelonavelonSelonrvelonrSelont(shouldShutDown ? "intelonrnal shutdown" : "admin stopThriftSelonrvicelon");
      } catch (Updatelonelonxcelonption elon) {
        LOG.warn("Lelonaving production SelonrvelonrSelont failelond.", elon);
      }

      if (finaglelonSelonrvelonrManagelonr.isProductionSelonrvelonrRunning()) {
        try {
          finaglelonSelonrvelonrManagelonr.stopProductionFinaglelonSelonrvelonr(SelonRVelonR_CLOSelon_WAIT_TIMelon);
          futurelonPoolManagelonr.stopUndelonrlyingFuturelonPool(
              SelonRVelonR_CLOSelon_WAIT_TIMelon.inSelonconds(), TimelonUnit.SelonCONDS);
          numSelonarchelonrThrelonadsGaugelon.selont(0);
        } catch (Intelonrruptelondelonxcelonption elon) {
          LOG.elonrror("Intelonrruptelond whilelon stopping thrift selonrvicelon", elon);
          Threlonad.currelonntThrelonad().intelonrrupt();
        }
        elonarlybirdStatus.THRIFT_PORT_OPelonN.selont(falselon);
      }
    }
  }

  /**
   * Gelonts a string with information about thelon last relonquelonst welon'velon selonelonn from elonach clielonnt.
   */
  public Futurelon<String> gelontLastSelonarchelonsByClielonnt(boolelonan includelonRelonsults) {
    LastSelonarchelonsSummary summary = nelonw LastSelonarchelonsSummary(
        lastRelonquelonstPelonrClielonntId, clielonntIdSelonarchStats, includelonRelonsults);
    relonturn Futurelon.valuelon(summary.gelontSummary());
  }

  /**
   * Thelon following arelon all thelon Thrift RPC melonthods inhelonritelond from elonarlybirdSelonrvicelon.Ifacelon
   */

  // Thrift gelontNamelon RPC.
  @Ovelonrridelon
  public Futurelon<String> gelontNamelon() {
    relonturn Futurelon.valuelon(SelonRVICelon_NAMelon);
  }

  // Thrift gelontStatus RPC.
  @Ovelonrridelon
  public Futurelon<elonarlybirdStatusRelonsponselon> gelontStatus() {
    elonarlybirdStatusRelonsponselon relonsponselon = nelonw elonarlybirdStatusRelonsponselon();
    relonsponselon.selontCodelon(elonarlybirdStatus.gelontStatusCodelon());
    relonsponselon.selontAlivelonSincelon(elonarlybirdStatus.gelontStartTimelon());
    relonsponselon.selontMelonssagelon(elonarlybirdStatus.gelontStatusMelonssagelon());
    relonturn Futurelon.valuelon(relonsponselon);
  }

  public Futurelon<List<String>> gelontSelongmelonntMelontadata() {
    relonturn Futurelon.valuelon(selongmelonntManagelonr.gelontSelongmelonntMelontadata());
  }

  public Futurelon<String> gelontQuelonryCachelonsData() {
    relonturn Futurelon.valuelon(selongmelonntManagelonr.gelontQuelonryCachelonsData());
  }

  /**
   * Gelont a telonxt summary for which fielonlds did welon uselon in a schelonma.
   */
  public Futurelon<String> gelontQuelonrielondFielonldsAndSchelonmaStats() {
    ImmutablelonSchelonmaIntelonrfacelon schelonma = this.elonarlybirdIndelonxConfig.gelontSchelonma().gelontSchelonmaSnapshot();

    QuelonrielondFielonldsAndSchelonmaStats summary = nelonw QuelonrielondFielonldsAndSchelonmaStats(schelonma,
        quelonrielondFielonldsCounts);
    relonturn Futurelon.valuelon(summary.gelontSummary());
  }

  /**
   * Shuts down thelon elonarlybird selonrvelonr.
   */
  public void shutdown() {
    LOG.info("shutdown(): status selont to STOPPING");
    elonarlybirdStatus.selontStatus(elonarlybirdStatusCodelon.STOPPING);
    try {
      LOG.info("Stopping Finaglelon selonrvelonr.");
      stopThriftSelonrvicelon(truelon);
      elonarlybirdStatus.THRIFT_SelonRVICelon_STARTelonD.selont(falselon);

      if (quelonryCachelonManagelonr != null) {
        quelonryCachelonManagelonr.shutdown();
      } elonlselon {
        LOG.info("No quelonryCachelonManagelonr to shut down");
      }

      elonarlybirdIndelonxConfig.gelontRelonsourcelonCloselonr().shutdownelonxeloncutor();

      isShuttingDown = truelon;
      LOG.info("Closing {} closelonablelons.", toCloselon.sizelon());
      for (Closelonablelon closelonablelon : toCloselon) {
        closelonablelon.closelon();
      }
    } catch (Intelonrruptelondelonxcelonption | IOelonxcelonption elon) {
      elonarlybirdStatus.selontStatus(elonarlybirdStatusCodelon.UNHelonALTHY, elon.gelontMelonssagelon());
      LOG.elonrror("Intelonrruptelond during shutdown, status selont to UNHelonALTHY");
    }
    LOG.info("elonarlybird selonrvelonr stoppelond!");
    isShutdown = truelon;
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> selonarch(final elonarlybirdRelonquelonst relonquelonst) {
    final long relonquelonstReloncelonivelondTimelonMillis = Systelonm.currelonntTimelonMillis();
    // Reloncord clock diff as elonarly as possiblelon.
    elonarlybirdRelonquelonstUtil.reloncordClielonntClockDiff(relonquelonst);

    if (!futurelonPoolManagelonr.isPoolRelonady()) {
      relonturn Futurelon.elonxcelonption(nelonw Transielonntelonxcelonption("elonarlybird not yelont ablelon to handlelon relonquelonsts."));
    }

    relonturn futurelonPoolManagelonr.apply(nelonw Function0<elonarlybirdRelonsponselon>() {
      @Ovelonrridelon
      public elonarlybirdRelonsponselon apply() {
        relonturn doSelonarch(relonquelonst, relonquelonstReloncelonivelondTimelonMillis);
      }
    }).relonscuelon(Function.func(
        // relonspond with Nack whelonn thelon quelonuelon is full
        t -> Futurelon.elonxcelonption((t instancelonof Relonjelonctelondelonxeloncutionelonxcelonption) ? QUelonUelon_FULL_FAILURelon : t)));
  }

  privatelon elonarlybirdRelonsponselon doSelonarch(elonarlybirdRelonquelonst relonquelonst, long relonquelonstReloncelonivelondTimelonMillis) {
    final long quelonuelonWaitTimelon = Systelonm.currelonntTimelonMillis() - relonquelonstReloncelonivelondTimelonMillis;
    intelonrnalQuelonuelonWaitTimelonStats.timelonrIncrelonmelonnt(quelonuelonWaitTimelon);

    // relonquelonst relonstart timelon, not to belon confuselond with startTimelon which is selonrvelonr relonstart timelon
    Timelonr timelonr = nelonw Timelonr(TimelonUnit.MICROSelonCONDS);

    relonquelonstPrelonLoggelonr.logRelonquelonst(relonquelonst);

    String clielonntId = ClielonntIdUtil.gelontClielonntIdFromRelonquelonst(relonquelonst);
    String finaglelonClielonntId = FinaglelonUtil.gelontFinaglelonClielonntNamelon();
    relonquelonstCountelonrsByFinaglelonIdAndClielonntId.gelontUnchelonckelond(nelonw Pair<>(finaglelonClielonntId, clielonntId))
        .increlonmelonnt();

    elonarlybirdRelonquelonstUtil.chelonckAndSelontCollelonctorParams(relonquelonst);

    // If thelon thrift loggelonr is busy logging, quelonuelon thelon thrift relonquelonst for logging.
    if (elonarlybirdThriftRelonquelonstLoggingUtil.thriftLoggelonrBusy) {
      elonarlybirdThriftRelonquelonstLoggingUtil.RelonQUelonST_BUFFelonR.offelonr(relonquelonst);
    }

    elonarlybirdRelonquelonstUtil.logAndFixelonxcelonssivelonValuelons(relonquelonst);

    final elonarlybirdSelonarchelonr selonarchelonr = nelonw elonarlybirdSelonarchelonr(
        relonquelonst,
        selongmelonntManagelonr,
        audioSpacelonTablelon,
        quelonryCachelonManagelonr,
        elonarlybirdIndelonxConfig.gelontSchelonma().gelontSchelonmaSnapshot(),
        elonarlybirdIndelonxConfig.gelontClustelonr(),
        dynamicPartitionConfig.gelontCurrelonntPartitionConfig(),
        deloncidelonr,
        twelonelontsSelonarchelonrStats,
        scoringModelonlsManagelonr,
        telonnsorflowModelonlsManagelonr,
        clock,
        multiSelongmelonntTelonrmDictionaryManagelonr,
        quelonryTimelonoutFactory,
        qualityFactor);

    QuelonryCostTrackelonr quelonryCostTrackelonr = QuelonryCostTrackelonr.gelontTrackelonr();
    elonarlybirdRelonsponselon relonsponselon = null;
    try {
      if (skipTimelondOutRelonquelonsts
          && selonarchelonr.gelontTelonrminationTrackelonr().gelontTimelonoutelonndTimelonWithRelonselonrvation()
          <= clock.nowMillis()) {
        relonquelonstTimelonoutelonxcelonelondelondBelonforelonSelonarchCountelonr.increlonmelonnt();
        relonsponselon = nelonw elonarlybirdRelonsponselon();
        relonsponselon.selontRelonsponselonCodelon(elonarlybirdRelonsponselonCodelon.SelonRVelonR_TIMelonOUT_elonRROR);
      } elonlselon {
        quelonryCostTrackelonr.relonselont();
        relonsponselon = selonarchelonr.selonarch();
      }
    } finally {
      if (relonsponselon == null) {
        // This can only happelonn if welon failelond to catch an elonxcelonption in thelon selonarchelonr.
        LOG.elonrror("Relonsponselon was null: " + relonquelonst.toString());
        relonsponselon = nelonw elonarlybirdRelonsponselon();
        relonsponselon.selontRelonsponselonCodelon(elonarlybirdRelonsponselonCodelon.TRANSIelonNT_elonRROR);
      }

      if (relonsponselon.gelontSelonarchRelonsults() == null) {
        List<ThriftSelonarchRelonsult> elonmptyRelonsultSelont = Lists.nelonwArrayList();
        relonsponselon.selontSelonarchRelonsults(nelonw ThriftSelonarchRelonsults(elonmptyRelonsultSelont));
      }

      long relonqLatelonncy = timelonr.stop();
      relonsponselon.selontRelonsponselonTimelon(relonqLatelonncy / 1000);
      relonsponselon.selontRelonsponselonTimelonMicros(relonqLatelonncy);
      relonsponselon.gelontSelonarchRelonsults().selontQuelonryCost(quelonryCostTrackelonr.gelontTotalCost());

      relonquelonstLoggelonr.logRelonquelonst(relonquelonst, relonsponselon, timelonr);

      int numRelonsults = elonarlybirdRelonquelonstLoggelonr.numRelonsultsForLog(relonsponselon);
      boolelonan succelonss = relonsponselon.gelontRelonsponselonCodelon() == elonarlybirdRelonsponselonCodelon.SUCCelonSS;
      boolelonan clielonntelonrror = relonsponselon.gelontRelonsponselonCodelon() == elonarlybirdRelonsponselonCodelon.CLIelonNT_elonRROR;
      boolelonan elonarlyTelonrminatelond = (relonsponselon.gelontSelonarchRelonsults().isSelontNumPartitionselonarlyTelonrminatelond()
          && relonsponselon.gelontSelonarchRelonsults().gelontNumPartitionselonarlyTelonrminatelond() > 0)
          || selonarchelonr.gelontTelonrminationTrackelonr().iselonarlyTelonrminatelond();
      // Updatelon telonrmination stats.
      selonarchelonr.gelontTelonrminationTrackelonr().gelontelonarlyTelonrminationStatelon().increlonmelonntCount();

      selonarchStats.relonquelonstComplelontelon(relonqLatelonncy, numRelonsults, succelonss, elonarlyTelonrminatelond, clielonntelonrror);
      if (selonarchelonr.gelontRelonquelonstStats() != null) {
        selonarchelonr.gelontRelonquelonstStats().relonquelonstComplelontelon(relonqLatelonncy, numRelonsults, succelonss,
            elonarlyTelonrminatelond, clielonntelonrror);
      }

      gelontRelonsponselonCodelonCountelonr(relonsponselon.gelontRelonsponselonCodelon()).increlonmelonnt();
      // Adding this countelonr to makelon it elonasielonr to delonbug caselons whelonrelon welon selonelon a spikelon in
      // bad clielonnt relonquelonst elonrrors but don't know whelonrelon thelony'relon coming from. (Thelon
      // altelonrnativelon is to ssh to a machinelon in thelon clustelonr and samplelon
      // /var/log/elonarlybird/elonarlybird.failelond_relonquelonsts).
      gelontClielonntIdRelonsponselonCodelonCountelonr(clielonntId, relonsponselon.gelontRelonsponselonCodelon()).increlonmelonnt();

      // elonxport relonquelonst latelonncy as a stat.
      clielonntIdSelonarchStats.gelontUnchelonckelond(clielonntId).timelonrIncrelonmelonnt(relonqLatelonncy);
      relonquelonstCountelonrsByFinaglelonClielonntId.gelontUnchelonckelond(finaglelonClielonntId).timelonrIncrelonmelonnt(relonqLatelonncy);
      addelonarlybirdSelonrvelonrStats(relonsponselon, quelonuelonWaitTimelon);
      // elonxport scoring stats for thelon relonquelonst.
      elonxportScoringTimelonStats(relonsponselon, clielonntId);
    }

    Selont<String> quelonrielondFielonlds = selonarchelonr.gelontQuelonrielondFielonlds();
    if (quelonrielondFielonlds != null) {
      for (String quelonrielondFielonld : quelonrielondFielonlds) {
        quelonrielondFielonldsCounts.increlonmelonntAndGelont(quelonrielondFielonld);
      }
    }

    // Increlonmelonnt countelonrs for agelon of thelon relonturnelond relonsults.
    if (relonsponselon.gelontSelonarchRelonsults() != null && relonsponselon.gelontSelonarchRelonsults().gelontRelonsults() != null) {
      long currelonntTimelon = Systelonm.currelonntTimelonMillis();
      for (ThriftSelonarchRelonsult relonsult : relonsponselon.gelontSelonarchRelonsults().gelontRelonsults()) {
        long twelonelontId = relonsult.gelontId();
        if (SnowflakelonId.isSnowflakelonId(twelonelontId)) {
          long agelonMillis = Math.max(0L,
              currelonntTimelon - SnowflakelonId.unixTimelonMillisFromId(twelonelontId));
          int agelonDays = Duration.fromMilliselonconds(agelonMillis).inDays();

          if (elonarlybirdConfig.isRelonaltimelonOrProtelonctelond()) {
            String kelony = "relonsult_agelon_in_days_" + agelonDays;
            relonsultsAgelonCountelonr.gelontUnchelonckelond(kelony).increlonmelonnt();
          } elonlselon {
            int agelonYelonars = agelonDays / 365;
            String kelony = "relonsult_agelon_in_yelonars_" + agelonYelonars;
            relonsultsAgelonCountelonr.gelontUnchelonckelond(kelony).increlonmelonnt();
          }
        }
      }
    }

    try {
      lastRelonquelonstPelonrClielonntId.gelont(clielonntId).selont(
          nelonw RelonquelonstRelonsponselonPair(relonquelonst, selonarchelonr.gelontParselondQuelonry(),
              selonarchelonr.gelontLucelonnelonQuelonry(), relonsponselon));
    } catch (elonxeloncutionelonxcelonption elonx) {
      // Not a big problelonm, welon'll just noticelon that thelon admin pagelon doelonsn't work, and it
      // probably won't happelonn.
    }


    relonturn relonsponselon;
  }

  privatelon void elonxportScoringTimelonStats(elonarlybirdRelonsponselon relonsponselon, String clielonntId) {
    if (relonsponselon.isSelontSelonarchRelonsults()
        && relonsponselon.gelontSelonarchRelonsults().isSelontScoringTimelonNanos()
        && relonsponselon.gelontSelonarchRelonsults().isSelontNumHitsProcelonsselond()) {
      int numHitsProcelonsselond = relonsponselon.gelontSelonarchRelonsults().gelontNumHitsProcelonsselond();
      long scoringTimelonNanos = relonsponselon.gelontSelonarchRelonsults().gelontScoringTimelonNanos();

      if (numHitsProcelonsselond > 0) {
        // Only computelon and relonport scoring timelon pelonr hit whelonn welon havelon hits. (i.elon. welon don't just want
        // to relonport 0's for caselons whelonrelon thelonrelon welonrelon no hits, and only want to relonport lelongit pelonr-hit
        // timelons.
        long scoringTimelonPelonrHit = scoringTimelonNanos / numHitsProcelonsselond;

        this.clielonntIdScoringPelonrHitStats.gelontUnchelonckelond(clielonntId).timelonrIncrelonmelonnt(scoringTimelonPelonrHit);
        this.ovelonrallScoringTimelonPelonrHitStats.timelonrIncrelonmelonnt(scoringTimelonPelonrHit);
      }

      this.clielonntIdScoringPelonrQuelonryStats.gelontUnchelonckelond(clielonntId).timelonrIncrelonmelonnt(scoringTimelonNanos);
      this.ovelonrallScoringTimelonPelonrQuelonryStats.timelonrIncrelonmelonnt(scoringTimelonNanos);

      // Thelon num hits procelonsselond stats helonrelon arelon scopelond only to quelonrielons that welonrelon actually scorelond.
      // This would elonxcludelon quelonrielons likelon telonrm stats (that would othelonrwiselon havelon hugelon num hits
      // procelonsselond).
      this.clielonntIdScoringNumHitsProcelonsselondStats.gelontUnchelonckelond(clielonntId).reloncord(numHitsProcelonsselond);
      this.ovelonrallScoringNumHitsProcelonsselondStats.reloncord(numHitsProcelonsselond);
    }
  }

  privatelon void addelonarlybirdSelonrvelonrStats(elonarlybirdRelonsponselon relonsponselon, long quelonuelonWaitTimelon) {
    PartitionConfig curPartitionConfig = dynamicPartitionConfig.gelontCurrelonntPartitionConfig();
    elonarlybirdSelonrvelonrStats elonarlybirdSelonrvelonrStats = nelonw elonarlybirdSelonrvelonrStats();
    relonsponselon.selontelonarlybirdSelonrvelonrStats(elonarlybirdSelonrvelonrStats);
    elonarlybirdSelonrvelonrStats.selontHostnamelon(DatabaselonConfig.gelontLocalHostnamelon());
    elonarlybirdSelonrvelonrStats.selontPartition(curPartitionConfig.gelontIndelonxingHashPartitionID());
    elonarlybirdSelonrvelonrStats.selontTielonrNamelon(curPartitionConfig.gelontTielonrNamelon());
    elonarlybirdSelonrvelonrStats.selontCurrelonntQps(selonarchStats.gelontRelonquelonstRatelon());
    elonarlybirdSelonrvelonrStats.selontQuelonuelonTimelonMillis(quelonuelonWaitTimelon);
    elonarlybirdSelonrvelonrStats.selontAvelonragelonQuelonuelonTimelonMillis(
        (long) (doublelon) intelonrnalQuelonuelonWaitTimelonStats.relonad());
    elonarlybirdSelonrvelonrStats.selontAvelonragelonLatelonncyMicros(selonarchStats.gelontAvelonragelonLatelonncy());
  }

  @Ovelonrridelon
  public void joinSelonrvelonrSelont(String uselonrnamelon) throws Updatelonelonxcelonption {
    selonrvelonrSelontManagelonr.joinSelonrvelonrSelont(uselonrnamelon);
  }


  @Ovelonrridelon
  public int gelontNumbelonrOfSelonrvelonrSelontMelonmbelonrs() throws Intelonrruptelondelonxcelonption,
      ZooKelonelonpelonrClielonnt.ZooKelonelonpelonrConnelonctionelonxcelonption, Kelonelonpelonrelonxcelonption {
    relonturn selonrvelonrSelontManagelonr.gelontNumbelonrOfSelonrvelonrSelontMelonmbelonrs();
  }

  @Ovelonrridelon
  public void lelonavelonSelonrvelonrSelont(String uselonrnamelon) throws Updatelonelonxcelonption {
    selonrvelonrSelontManagelonr.lelonavelonSelonrvelonrSelont(uselonrnamelon);
  }

  @Ovelonrridelon
  public void joinSelonrvelonrSelontForSelonrvicelonProxy() {
    selonrvelonrSelontManagelonr.joinSelonrvelonrSelontForSelonrvicelonProxy();
  }

  @VisiblelonForTelonsting
  protelonctelond static class elonarlybirdThriftRelonquelonstLoggingUtil {
    privatelon static final int DelonFAULT_MAX_elonNTRIelonS_TO_LOG = 50000;
    privatelon static final int DelonFAULT_BUFFelonR_SIZelon = 10000;
    privatelon static final int DelonFAULT_LOGGING_SLelonelonP_MS = 100;

    @VisiblelonForTelonsting
    protelonctelond static volatilelon boolelonan thriftLoggelonrBusy = falselon;
    privatelon static final elonxeloncutorSelonrvicelon LOGGING_elonXelonCUTOR = elonxeloncutors.nelonwCachelondThrelonadPool();

    // Synchronizelond circular buffelonr uselond for buffelonring relonquelonsts.
    // If buffelonr is full, thelon oldelonst relonquelonsts arelon relonplacelond. This should not belon a problelonm for
    // logging purposelon.
    @VisiblelonForTelonsting
    protelonctelond static final ArrayBlockingQuelonuelon<elonarlybirdRelonquelonst> RelonQUelonST_BUFFelonR =
        nelonw ArrayBlockingQuelonuelon<>(DelonFAULT_BUFFelonR_SIZelon);


    /**
     * Crelonatelon a selonparatelon threlonad to log thrift relonquelonst to thelon givelonn filelon. If a threlonad is alrelonady
     * logging thrift relonquelonsts, this doelons nothing and throws an IOelonxcelonption indicating that thelon
     * logging threlonad is busy.
     *
     * @param logFilelon Filelon to log to.
     * @param maxelonntrielonsToLog Numbelonr of elonntrielons to log.
     * @param postLoggingHook Codelon to run aftelonr logging finishelons. Only uselond for telonsting as of now.
     */
    @VisiblelonForTelonsting
    protelonctelond static synchronizelond void startThriftLogging(final Filelon logFilelon,
                                                          final int maxelonntrielonsToLog,
                                                          final Runnablelon postLoggingHook)
        throws IOelonxcelonption {
      if (thriftLoggelonrBusy) {
        throw nelonw IOelonxcelonption("Alrelonady busy logging thrift relonquelonst. No action takelonn.");
      }

      if (!logFilelon.canWritelon()) {
        throw nelonw IOelonxcelonption("Unablelon to opelonn log filelon for writing:  " + logFilelon);
      }

      final BuffelonrelondWritelonr thriftLogWritelonr =
          Filelons.nelonwBuffelonrelondWritelonr(logFilelon.toPath(), Charselonts.UTF_8);

      // TSelonrializelonr uselond by thelon writelonr threlonad.
      final TSelonrializelonr selonrializelonr = nelonw TSelonrializelonr();

      RelonQUelonST_BUFFelonR.clelonar();
      thriftLoggelonrBusy = truelon;
      LOG.info("Startelond to log thrift relonquelonsts into filelon " + logFilelon.gelontAbsolutelonPath());
      LOGGING_elonXelonCUTOR.submit(() -> {
        try {
          int count = 0;
          whilelon (count < maxelonntrielonsToLog) {
            if (RelonQUelonST_BUFFelonR.iselonmpty()) {
              Threlonad.slelonelonp(DelonFAULT_LOGGING_SLelonelonP_MS);
              continuelon;
            }

            try {
              elonarlybirdRelonquelonst elonbRelonquelonst = RelonQUelonST_BUFFelonR.poll();
              String logLinelon = selonrializelonThriftObjelonct(elonbRelonquelonst, selonrializelonr);
              thriftLogWritelonr.writelon(logLinelon);
              count++;
            } catch (Telonxcelonption elon) {
              LOG.warn("Unablelon to selonrializelon elonarlybirdRelonquelonst for logging.", elon);
            }
          }
          relonturn count;
        } finally {
          thriftLogWritelonr.closelon();
          thriftLoggelonrBusy = falselon;
          LOG.info("Finishelond logging thrift relonquelonsts into filelon " + logFilelon.gelontAbsolutelonPath());
          RelonQUelonST_BUFFelonR.clelonar();
          if (postLoggingHook != null) {
            postLoggingHook.run();
          }
        }
      });
    }

    /**
     * Selonrializelon a thrift objelonct to a baselon 64 elonncodelond string.
     */
    privatelon static String selonrializelonThriftObjelonct(TBaselon<?, ?> tObjelonct, TSelonrializelonr selonrializelonr)
        throws Telonxcelonption {
      relonturn nelonw Baselon64().elonncodelonToString(selonrializelonr.selonrializelon(tObjelonct)) + "\n";
    }
  }

  /**
   * Start to log thrift elonarlybirdRelonquelonsts.
   *
   * @param logFilelon Log filelon to writelon to.
   * @param numRelonquelonstsToLog Numbelonr of relonquelonsts to collelonct.  Delonfault valuelon of 50000 uselond if
   * 0 or nelongativelon numbelonrs arelon pass in.
   */
  public void startThriftLogging(Filelon logFilelon, int numRelonquelonstsToLog) throws IOelonxcelonption {
    int relonquelonstToLog = numRelonquelonstsToLog <= 0
        ? elonarlybirdThriftRelonquelonstLoggingUtil.DelonFAULT_MAX_elonNTRIelonS_TO_LOG : numRelonquelonstsToLog;
    elonarlybirdThriftRelonquelonstLoggingUtil.startThriftLogging(logFilelon, relonquelonstToLog, null);
  }

  @VisiblelonForTelonsting
  @Ovelonrridelon
  public boolelonan isInSelonrvelonrSelont() {
    relonturn selonrvelonrSelontManagelonr.isInSelonrvelonrSelont();
  }

  @VisiblelonForTelonsting
  SelonarchCountelonr gelontRelonsponselonCodelonCountelonr(elonarlybirdRelonsponselonCodelon relonsponselonCodelon) {
    relonturn relonsponselonCodelonCountelonrs.gelont(relonsponselonCodelon);
  }

  @VisiblelonForTelonsting
  SelonarchCountelonr gelontClielonntIdRelonsponselonCodelonCountelonr(
      String clielonntId, elonarlybirdRelonsponselonCodelon relonsponselonCodelon) {
    String kelony = String.format(RelonSPONSelonS_PelonR_CLIelonNT_ID_STAT_TelonMPLATelon,
            clielonntId, relonsponselonCodelon.namelon().toLowelonrCaselon());
    relonturn relonsponselonByClielonntIdAndRelonsponselonCodelon.gelontUnchelonckelond(kelony);
  }

  public void selontNoShutdownWhelonnNotInLayout(boolelonan noShutdown) {
    statelonManagelonr.selontNoShutdownWhelonnNotInLayout(noShutdown);
  }

  privatelon void schelondulelon(OnelonTaskSchelondulelondelonxeloncutorManagelonr managelonr) {
    if (!isShuttingDown) {
      managelonr.schelondulelon();
      toCloselon.add(managelonr);
    }
  }

  public DynamicSchelonma gelontSchelonma() {
    relonturn elonarlybirdIndelonxConfig.gelontSchelonma();
  }

  public AudioSpacelonTablelon gelontAudioSpacelonTablelon() {
    relonturn audioSpacelonTablelon;
  }
}
