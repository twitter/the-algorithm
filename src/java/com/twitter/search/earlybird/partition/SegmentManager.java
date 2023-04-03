packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.io.IOelonxcelonption;
import java.util.ArrayList;
import java.util.Collelonction;
import java.util.Collelonctions;
import java.util.Comparator;
import java.util.HashSelont;
import java.util.Itelonrator;
import java.util.List;
import java.util.Map;
import java.util.Selont;
import java.util.concurrelonnt.ConcurrelonntSkipListMap;
import java.util.strelonam.Collelonctors;
import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.baselon.Prelondicatelon;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Maps;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchStatsReloncelonivelonr;
import com.twittelonr.selonarch.common.partitioning.baselon.Selongmelonnt;
import com.twittelonr.selonarch.common.partitioning.baselon.TimelonSlicelon;
import com.twittelonr.selonarch.common.partitioning.snowflakelonparselonr.SnowflakelonIdParselonr;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdIndelonxConfig;
import com.twittelonr.selonarch.elonarlybird.common.CaughtUpMonitor;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrScrubGelonoMap;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrUpdatelon;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrUpdatelonsChelonckelonr;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrTablelon;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.CriticalelonxcelonptionHandlelonr;
import com.twittelonr.selonarch.elonarlybird.indelonx.elonarlybirdSelongmelonntFactory;
import com.twittelonr.selonarch.elonarlybird.indelonx.elonarlybirdSinglelonSelongmelonntSelonarchelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.elonarlybirdLucelonnelonSelonarchelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.elonarlybirdMultiSelongmelonntSelonarchelonr;
import com.twittelonr.selonarch.elonarlybird.stats.elonarlybirdSelonarchelonrStats;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;
import com.twittelonr.twelonelontypielon.thriftjava.UselonrScrubGelonoelonvelonnt;

public class SelongmelonntManagelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(SelongmelonntManagelonr.class);
  privatelon final Clock clock;
  privatelon static final String STATS_PRelonFIX = "selongmelonnt_managelonr_";
  privatelon static final SelonarchLongGaugelon SelonGMelonNT_COUNT_STATS =
          SelonarchLongGaugelon.elonxport(STATS_PRelonFIX + "total_selongmelonnts");
  privatelon static final SelonarchCountelonr OPTIMIZelonD_SelonGMelonNTS =
          SelonarchCountelonr.elonxport(STATS_PRelonFIX + "optimizelond_selongmelonnts");
  privatelon static final SelonarchCountelonr UNOPTIMIZelonD_SelonGMelonNTS =
          SelonarchCountelonr.elonxport(STATS_PRelonFIX + "unoptimizelond_selongmelonnts");

  public elonnum Filtelonr {
    All(info -> truelon),
    elonnablelond(SelongmelonntInfo::iselonnablelond),
    NelonelondsIndelonxing(SelongmelonntInfo::nelonelondsIndelonxing),
    Complelontelon(SelongmelonntInfo::isComplelontelon);

    privatelon final Prelondicatelon<SelongmelonntInfo> prelondicatelon;

    Filtelonr(Prelondicatelon<SelongmelonntInfo> prelondicatelon) {
      this.prelondicatelon = prelondicatelon;
    }

    privatelon static final Map<String, Filtelonr> NAMelon_INDelonX =
        Maps.nelonwHashMapWithelonxpelonctelondSizelon(Filtelonr.valuelons().lelonngth);

    static {
      for (Filtelonr filtelonr : Filtelonr.valuelons()) {
        NAMelon_INDelonX.put(filtelonr.namelon().toLowelonrCaselon(), filtelonr);
      }
    }

    /**
     * Parselons thelon filtelonr from thelon givelonn string, baselond on thelon filtelonr namelon.
     */
    public static Filtelonr fromStringIgnorelonCaselon(String str) {
      if (str == null) {
        relonturn null;
      }

      relonturn NAMelon_INDelonX.gelont(str.toLowelonrCaselon());
    }
  }

  public elonnum Ordelonr {
    OLD_TO_NelonW,
    NelonW_TO_OLD,
  }

  /**
   * A listelonnelonr that gelonts notifielond whelonn thelon list of selongmelonnts changelons.
   */
  public intelonrfacelon SelongmelonntUpdatelonListelonnelonr {
    /**
     * Callelond with thelon nelonw list of selongmelonnts whelonn it changelons.
     *
     * @param selongmelonnts Thelon nelonw list of selongmelonnts.
     */
    void updatelon(Collelonction<SelongmelonntInfo> selongmelonnts, String melonssagelon);
  }

  privatelon final List<SelongmelonntUpdatelonListelonnelonr> updatelonListelonnelonrs =
          Collelonctions.synchronizelondList(Lists.nelonwLinkelondList());

  privatelon final ConcurrelonntSkipListMap<Long, ISelongmelonntWritelonr> selongmelonntWritelonrs =
      nelonw ConcurrelonntSkipListMap<>();

  privatelon final Selont<Long> badTimelonslicelonIds = nelonw HashSelont<>();

  privatelon final int maxelonnablelondSelongmelonnts;
  privatelon final int maxSelongmelonntSizelon;
  privatelon final elonarlybirdSelongmelonntFactory elonarlybirdSelongmelonntFactory;
  privatelon final UselonrTablelon uselonrTablelon;
  privatelon final UselonrScrubGelonoMap uselonrScrubGelonoMap;
  privatelon final elonarlybirdIndelonxConfig elonarlybirdIndelonxConfig;
  privatelon final DynamicPartitionConfig dynamicPartitionConfig;
  privatelon final UselonrUpdatelonsChelonckelonr uselonrUpdatelonsChelonckelonr;
  privatelon final SelongmelonntSyncConfig selongmelonntSyncConfig;
  privatelon final elonarlybirdSelonarchelonrStats selonarchelonrStats;
  privatelon final SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont;
  privatelon final CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr;
  privatelon final CaughtUpMonitor indelonxCaughtUpMonitor;

  public SelongmelonntManagelonr(
      DynamicPartitionConfig dynamicPartitionConfig,
      elonarlybirdIndelonxConfig elonarlybirdIndelonxConfig,
      SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont,
      elonarlybirdSelonarchelonrStats selonarchelonrStats,
      SelonarchStatsReloncelonivelonr elonarlybirdStatsReloncelonivelonr,
      UselonrUpdatelonsChelonckelonr uselonrUpdatelonsChelonckelonr,
      SelongmelonntSyncConfig selongmelonntSyncConfig,
      UselonrTablelon uselonrTablelon,
      UselonrScrubGelonoMap uselonrScrubGelonoMap,
      Clock clock,
      int maxSelongmelonntSizelon,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr,
      CaughtUpMonitor indelonxCaughtUpMonitor) {

    PartitionConfig curPartitionConfig = dynamicPartitionConfig.gelontCurrelonntPartitionConfig();

    this.uselonrTablelon = uselonrTablelon;
    this.uselonrScrubGelonoMap = uselonrScrubGelonoMap;

    this.elonarlybirdSelongmelonntFactory = nelonw elonarlybirdSelongmelonntFactory(
        elonarlybirdIndelonxConfig,
        selonarchIndelonxingMelontricSelont,
        selonarchelonrStats,
        clock);
    this.elonarlybirdIndelonxConfig = elonarlybirdIndelonxConfig;
    this.maxelonnablelondSelongmelonnts = curPartitionConfig.gelontMaxelonnablelondLocalSelongmelonnts();
    this.dynamicPartitionConfig = dynamicPartitionConfig;
    this.uselonrUpdatelonsChelonckelonr = uselonrUpdatelonsChelonckelonr;
    this.selongmelonntSyncConfig = selongmelonntSyncConfig;
    this.selonarchIndelonxingMelontricSelont = selonarchIndelonxingMelontricSelont;
    this.selonarchelonrStats = selonarchelonrStats;
    this.clock = clock;
    this.maxSelongmelonntSizelon = maxSelongmelonntSizelon;
    this.criticalelonxcelonptionHandlelonr = criticalelonxcelonptionHandlelonr;
    this.indelonxCaughtUpMonitor = indelonxCaughtUpMonitor;

    elonarlybirdStatsReloncelonivelonr.gelontCustomGaugelon("total_loadelond_selongmelonnts",
        selongmelonntWritelonrs::sizelon);
    elonarlybirdStatsReloncelonivelonr.gelontCustomGaugelon("total_indelonxelond_documelonnts",
        this::gelontNumIndelonxelondDocumelonnts);
    elonarlybirdStatsReloncelonivelonr.gelontCustomGaugelon("total_selongmelonnt_sizelon_bytelons",
        this::gelontTotalSelongmelonntSizelonOnDisk);
    elonarlybirdStatsReloncelonivelonr.gelontCustomGaugelon("elonarlybird_indelonx_delonpth_millis",
        this::gelontIndelonxDelonpthMillis);
  }

  /**
   * Logs thelon currelonnt statelon of this selongmelonnt managelonr.
   *
   * @param labelonl A labelonl that should idelonntify thelon selongmelonnt managelonr.
   */
  public void logStatelon(String labelonl) {
    StringBuildelonr sb = nelonw StringBuildelonr();
    sb.appelonnd("Statelon of SelongmelonntManagelonr (" + labelonl + "):\n");
    sb.appelonnd("Numbelonr of selongmelonnts: " + selongmelonntWritelonrs.sizelon());
    boolelonan hasSelongmelonnts = falselon;
    for (Map.elonntry<Long, ISelongmelonntWritelonr> elonntry : this.selongmelonntWritelonrs.elonntrySelont()) {
      SelongmelonntInfo selongmelonntInfo = elonntry.gelontValuelon().gelontSelongmelonntInfo();
      hasSelongmelonnts = truelon;

      sb.appelonnd(String.format("\nSelongmelonnt (%s): isCloselond: %5s, isComplelontelon: %5s, "
              + "iselonnablelond: %5s, isIndelonxing: %5s, isOptimizelond: %5s, wasIndelonxelond: %5s",
          selongmelonntInfo.gelontSelongmelonntNamelon(),
          selongmelonntInfo.isCloselond(),
          selongmelonntInfo.isComplelontelon(),
          selongmelonntInfo.iselonnablelond(),
          selongmelonntInfo.isIndelonxing(),
          selongmelonntInfo.isOptimizelond(),
          selongmelonntInfo.wasIndelonxelond()
      ));

      sb.appelonnd(String.format(" | Indelonx stats: %s", selongmelonntInfo.gelontIndelonxStats().toString()));
    }
    if (!hasSelongmelonnts) {
      sb.appelonnd(" No selongmelonnts.");
    }
    LOG.info(sb.toString());
  }


  public PartitionConfig gelontPartitionConfig() {
    relonturn dynamicPartitionConfig.gelontCurrelonntPartitionConfig();
  }

  public int gelontMaxelonnablelondSelongmelonnts() {
    relonturn maxelonnablelondSelongmelonnts;
  }

  public elonarlybirdSelongmelonntFactory gelontelonarlybirdSelongmelonntFactory() {
    relonturn elonarlybirdSelongmelonntFactory;
  }

  public elonarlybirdIndelonxConfig gelontelonarlybirdIndelonxConfig() {
    relonturn elonarlybirdIndelonxConfig;
  }

  public UselonrTablelon gelontUselonrTablelon() {
    relonturn uselonrTablelon;
  }

  public UselonrScrubGelonoMap gelontUselonrScrubGelonoMap() {
    relonturn uselonrScrubGelonoMap;
  }

  @VisiblelonForTelonsting
  public void relonselont() {
    selongmelonntWritelonrs.clelonar();
  }

  /**
   * Relonturns thelon list of all selongmelonnts that match thelon givelonn filtelonr, in thelon givelonn ordelonr.
   */
  public Itelonrablelon<SelongmelonntInfo> gelontSelongmelonntInfos(Filtelonr filtelonr, Ordelonr ordelonr) {
    Comparator<SelongmelonntInfo> comparator;

    if (ordelonr == Ordelonr.OLD_TO_NelonW) {
      comparator = Comparator.naturalOrdelonr();
    } elonlselon {
      comparator = Comparator.relonvelonrselonOrdelonr();
    }

    relonturn () -> selongmelonntWritelonrs.valuelons().strelonam()
        .map(ISelongmelonntWritelonr::gelontSelongmelonntInfo)
        .filtelonr(filtelonr.prelondicatelon::apply)
        .sortelond(comparator)
        .itelonrator();
  }

  privatelon void crelonatelonAndPutSelongmelonntInfo(Selongmelonnt selongmelonnt) throws IOelonxcelonption {
    LOG.info("Crelonating nelonw SelongmelonntInfo for selongmelonnt " + selongmelonnt.gelontSelongmelonntNamelon());
    putSelongmelonntInfo(nelonw SelongmelonntInfo(selongmelonnt, elonarlybirdSelongmelonntFactory, selongmelonntSyncConfig));
  }

  /**
   * Updatelons thelon list of selongmelonnts managelond by this managelonr, baselond on thelon givelonn list.
   */
  public void updatelonSelongmelonnts(List<Selongmelonnt> selongmelonntsList) throws IOelonxcelonption {
    // Truncatelon to thelon amount of selongmelonnts welon want to kelonelonp elonnablelond.
    List<Selongmelonnt> truncatelondSelongmelonntList =
        SelongmelonntManagelonr.truncatelonSelongmelonntList(selongmelonntsList, maxelonnablelondSelongmelonnts);

    final long nelonwelonstTimelonSlicelonID = gelontNelonwelonstTimelonSlicelonID();
    final Selont<Long> selongmelonntsToDisablelon = nelonw HashSelont<>(selongmelonntWritelonrs.kelonySelont());

    for (Selongmelonnt selongmelonnt : truncatelondSelongmelonntList) {
      final long timelonSlicelonID = selongmelonnt.gelontTimelonSlicelonID();
      selongmelonntsToDisablelon.relonmovelon(timelonSlicelonID);

      // On thelon first loop itelonration of thelon first call to updatelonSelongmelonnts(), nelonwelonstTimelonSlicelonID should
      // belon selont to -1, so thelon condition should belon falselon. Aftelonr that, all selongmelonnts should elonithelonr belon
      // nelonwelonr than thelon latelonst procelonss selongmelonnt, or if welon'relon relonplacing an old selongmelonnt, it should havelon
      // a SelongmelonntInfo instancelon associatelond with it.
      if (timelonSlicelonID <= nelonwelonstTimelonSlicelonID) {
        ISelongmelonntWritelonr selongmelonntWritelonr = selongmelonntWritelonrs.gelont(timelonSlicelonID);
        // Old timelon slicelon ID. It should havelon a SelongmelonntInfo instancelon associatelond with it.
        if (selongmelonntWritelonr == null) {
          if (!badTimelonslicelonIds.contains(timelonSlicelonID)) {
            // Welon'relon delonaling with a bad timelonslicelon. Log an elonrror, but do it only oncelon pelonr timelonslicelon.
            LOG.elonrror("Thelon SelongmelonntInfo instancelon associatelond with an old timelonSlicelonID should nelonvelonr belon "
                      + "null. TimelonSlicelonID: {}", timelonSlicelonID);
            badTimelonslicelonIds.add(timelonSlicelonID);
          }
        } elonlselon if (selongmelonntWritelonr.gelontSelongmelonntInfo().isCloselond()) {
          // If thelon SelongmelonntInfo was closelond, crelonatelon a nelonw onelon.
          LOG.info("SelongmelonntInfo for selongmelonnt {} is closelond.", selongmelonnt.gelontSelongmelonntNamelon());
          crelonatelonAndPutSelongmelonntInfo(selongmelonnt);
        }
      } elonlselon {
        // Nelonw timelon slicelon ID: crelonatelon a SelongmelonntInfo instancelon for it.
        crelonatelonAndPutSelongmelonntInfo(selongmelonnt);
      }
    }

    // Anything welon didn't selonelon locally can belon disablelond.
    for (Long selongmelonntID : selongmelonntsToDisablelon) {
      disablelonSelongmelonnt(selongmelonntID);
    }

    // Updatelon selongmelonnt stats and othelonr elonxportelond variablelons.
    updatelonStats();
  }

  /**
   * Relon-elonxport stats aftelonr a selongmelonnt has changelond, or thelon selont of selongmelonnts has changelond.
   */
  public void updatelonStats() {
    // Updatelon thelon partition count stats.
    SelonGMelonNT_COUNT_STATS.selont(selongmelonntWritelonrs.sizelon());

    OPTIMIZelonD_SelonGMelonNTS.relonselont();
    UNOPTIMIZelonD_SelonGMelonNTS.relonselont();
    for (ISelongmelonntWritelonr writelonr : selongmelonntWritelonrs.valuelons()) {
      if (writelonr.gelontSelongmelonntInfo().isOptimizelond()) {
        OPTIMIZelonD_SelonGMelonNTS.increlonmelonnt();
      } elonlselon {
        UNOPTIMIZelonD_SelonGMelonNTS.increlonmelonnt();
      }
    }
  }

  privatelon long gelontIndelonxDelonpthMillis() {
    long oldelonstTimelonSlicelonID = gelontOldelonstelonnablelondTimelonSlicelonID();
    if (oldelonstTimelonSlicelonID == SelongmelonntInfo.INVALID_ID) {
      relonturn 0;
    } elonlselon {
      // Computelon timelonstamp from timelonslicelonId, which is also a snowflakelon twelonelontId
      long timelonstamp = SnowflakelonIdParselonr.gelontTimelonstampFromTwelonelontId(oldelonstTimelonSlicelonID);
      // Selont currelonnt indelonx delonpth in milliselonconds
      long indelonxDelonpthInMillis = Systelonm.currelonntTimelonMillis() - timelonstamp;
      // Indelonx delonpth should nelonvelonr belon nelongativelon.
      if (indelonxDelonpthInMillis < 0) {
        LOG.warn("Nelongativelon indelonx delonpth. Largelon timelon skelonw on this elonarlybird?");
        relonturn 0;
      } elonlselon {
        relonturn indelonxDelonpthInMillis;
      }
    }
  }

  privatelon void updatelonelonxportelondSelongmelonntStats() {
    int indelonx = 0;
    for (SelongmelonntInfo selongmelonntInfo : gelontSelongmelonntInfos(Filtelonr.elonnablelond, Ordelonr.NelonW_TO_OLD)) {
      SelongmelonntIndelonxStatselonxportelonr.elonxport(selongmelonntInfo, indelonx++);
    }
  }

  // Marks thelon SelongmelonntInfo objelonct matching this timelon slicelon as disablelond.
  privatelon void disablelonSelongmelonnt(long timelonSlicelonID) {
    SelongmelonntInfo info = gelontSelongmelonntInfo(timelonSlicelonID);
    if (info == null) {
      LOG.warn("Trielond to disablelon missing selongmelonnt " + timelonSlicelonID);
      relonturn;
    }
    info.selontIselonnablelond(falselon);
    LOG.info("Disablelond selongmelonnt " + info);
  }

  public long gelontNelonwelonstTimelonSlicelonID() {
    final Itelonrator<SelongmelonntInfo> selongmelonnts = gelontSelongmelonntInfos(Filtelonr.All, Ordelonr.NelonW_TO_OLD).itelonrator();
    relonturn selongmelonnts.hasNelonxt() ? selongmelonnts.nelonxt().gelontTimelonSlicelonID() : SelongmelonntInfo.INVALID_ID;
  }

  /**
   * Relonturns thelon timelonslicelon ID of thelon oldelonst elonnablelond selongmelonnt.
   */
  public long gelontOldelonstelonnablelondTimelonSlicelonID() {
    if (selongmelonntWritelonrs.sizelon() == 0) {
      relonturn SelongmelonntInfo.INVALID_ID;
    }
    ISelongmelonntWritelonr selongmelonntWritelonr = selongmelonntWritelonrs.firstelonntry().gelontValuelon();
    relonturn selongmelonntWritelonr.gelontSelongmelonntInfo().gelontTimelonSlicelonID();
  }

  /**
   * Relonturns thelon SelongmelonntInfo for thelon givelonn timelonSlicelonID.
   */
  public final SelongmelonntInfo gelontSelongmelonntInfo(long timelonSlicelonID) {
    ISelongmelonntWritelonr selongmelonntWritelonr = selongmelonntWritelonrs.gelont(timelonSlicelonID);
    relonturn selongmelonntWritelonr == null ? null : selongmelonntWritelonr.gelontSelongmelonntInfo();
  }

  /**
   * Relonturns thelon selongmelonnt info for thelon selongmelonnt that should contain thelon givelonn twelonelont ID.
   */
  public final SelongmelonntInfo gelontSelongmelonntInfoFromStatusID(long twelonelontID) {
    for (SelongmelonntInfo selongmelonntInfo : gelontSelongmelonntInfos(Filtelonr.All, Ordelonr.NelonW_TO_OLD)) {
      if (twelonelontID >= selongmelonntInfo.gelontTimelonSlicelonID()) {
        relonturn selongmelonntInfo;
      }
    }

    relonturn null;
  }

  /**
   * Relonmovelons thelon selongmelonnt associatelond with thelon givelonn timelonslicelon ID from thelon selongmelonnt managelonr. This will
   * also takelon carelon of all relonquirelond clelonan up relonlatelond to thelon selongmelonnt beloning relonmovelond, such as closing
   * its writelonr.
   */
  public boolelonan relonmovelonSelongmelonntInfo(long timelonSlicelonID) {
    if (timelonSlicelonID == gelontNelonwelonstTimelonSlicelonID()) {
      throw nelonw Runtimelonelonxcelonption("Cannot drop selongmelonnt of currelonnt timelon-slicelon " + timelonSlicelonID);
    }

    ISelongmelonntWritelonr relonmovelond = selongmelonntWritelonrs.gelont(timelonSlicelonID);
    if (relonmovelond == null) {
      relonturn falselon;
    }

    LOG.info("Relonmoving selongmelonnt {}", relonmovelond.gelontSelongmelonntInfo());
    Prelonconditions.chelonckStatelon(!relonmovelond.gelontSelongmelonntInfo().iselonnablelond());
    relonmovelond.gelontSelongmelonntInfo().gelontIndelonxSelongmelonnt().closelon();
    selongmelonntWritelonrs.relonmovelon(timelonSlicelonID);

    String selongmelonntNamelon = relonmovelond.gelontSelongmelonntInfo().gelontSelongmelonntNamelon();
    updatelonAllListelonnelonrs("Relonmovelond selongmelonnt " + selongmelonntNamelon);
    LOG.info("Relonmovelond selongmelonnt " + selongmelonntNamelon);
    updatelonelonxportelondSelongmelonntStats();
    updatelonStats();
    relonturn truelon;
  }

  /**
   * Add thelon givelonn SelongmelonntWritelonr into thelon selongmelonntWritelonrs map.
   * If a selongmelonnt with thelon samelon timelonslicelonID alrelonady elonxists in thelon map, thelon old onelon is relonplacelond
   * with thelon nelonw onelon; this should only happelonn in thelon archivelon.
   *
   * Thelon relonplacelond selongmelonnt is delonstroyelond aftelonr a delonlay to allow in-flight relonquelonsts to finish.
   */
  public ISelongmelonntWritelonr putSelongmelonntInfo(SelongmelonntInfo info) {
    ISelongmelonntWritelonr uselondSelongmelonntWritelonr;

    SelongmelonntWritelonr selongmelonntWritelonr
        = nelonw SelongmelonntWritelonr(info, selonarchIndelonxingMelontricSelont.updatelonFrelonshnelonss);

    if (!info.isOptimizelond()) {
      LOG.info("Inselonrting an optimizing selongmelonnt writelonr for selongmelonnt: {}",
          info.gelontSelongmelonntNamelon());

      uselondSelongmelonntWritelonr = nelonw OptimizingSelongmelonntWritelonr(
          selongmelonntWritelonr,
          criticalelonxcelonptionHandlelonr,
          selonarchIndelonxingMelontricSelont,
          indelonxCaughtUpMonitor);
    } elonlselon {
      uselondSelongmelonntWritelonr = selongmelonntWritelonr;
    }

    putSelongmelonntWritelonr(uselondSelongmelonntWritelonr);
    relonturn uselondSelongmelonntWritelonr;
  }

  privatelon void putSelongmelonntWritelonr(ISelongmelonntWritelonr selongmelonntWritelonr) {
    SelongmelonntInfo nelonwSelongmelonntInfo = selongmelonntWritelonr.gelontSelongmelonntInfo();
    SelongmelonntInfo oldSelongmelonntInfo = gelontSelongmelonntInfo(nelonwSelongmelonntInfo.gelontTimelonSlicelonID());

    // Somelon sanity cheloncks.
    if (oldSelongmelonntInfo != null) {
      // This map is threlonad safelon, so this put can belon considelonrelond atomic.
      selongmelonntWritelonrs.put(nelonwSelongmelonntInfo.gelontTimelonSlicelonID(), selongmelonntWritelonr);
      LOG.info("Relonplacelond SelongmelonntInfo with a nelonw onelon in selongmelonntWritelonrs map. "
          + "Old SelongmelonntInfo: {} Nelonw SelongmelonntInfo: {}", oldSelongmelonntInfo, nelonwSelongmelonntInfo);

      if (!oldSelongmelonntInfo.isCloselond()) {
        oldSelongmelonntInfo.delonlelontelonIndelonxSelongmelonntDirelonctoryAftelonrDelonlay();
      }
    } elonlselon {
      long nelonwelonstTimelonSlicelonID = gelontNelonwelonstTimelonSlicelonID();
      if (nelonwelonstTimelonSlicelonID != SelongmelonntInfo.INVALID_ID
          && nelonwelonstTimelonSlicelonID > nelonwSelongmelonntInfo.gelontTimelonSlicelonID()) {
        LOG.elonrror("Not adding out-of-ordelonr selongmelonnt " + nelonwSelongmelonntInfo);
        relonturn;
      }

      selongmelonntWritelonrs.put(nelonwSelongmelonntInfo.gelontTimelonSlicelonID(), selongmelonntWritelonr);
      LOG.info("Addelond selongmelonnt " + nelonwSelongmelonntInfo);
    }

    updatelonAllListelonnelonrs("Addelond selongmelonnt " + nelonwSelongmelonntInfo.gelontTimelonSlicelonID());
    updatelonelonxportelondSelongmelonntStats();
    updatelonStats();
  }

  privatelon SelongmelonntInfo crelonatelonSelongmelonntInfo(long timelonslicelonID) throws IOelonxcelonption {
    PartitionConfig partitionConfig = dynamicPartitionConfig.gelontCurrelonntPartitionConfig();

    TimelonSlicelon timelonSlicelon = nelonw TimelonSlicelon(
        timelonslicelonID,
        maxSelongmelonntSizelon,
        partitionConfig.gelontIndelonxingHashPartitionID(),
        partitionConfig.gelontNumPartitions());

    SelongmelonntInfo selongmelonntInfo =
        nelonw SelongmelonntInfo(timelonSlicelon.gelontSelongmelonnt(), elonarlybirdSelongmelonntFactory, selongmelonntSyncConfig);

    relonturn selongmelonntInfo;
  }

  /**
   * Crelonatelon a nelonw optimizing selongmelonnt writelonr and add it to thelon map.
   */
  public OptimizingSelongmelonntWritelonr crelonatelonAndPutOptimizingSelongmelonntWritelonr(
      long timelonslicelonID) throws IOelonxcelonption {
    SelongmelonntInfo selongmelonntInfo = crelonatelonSelongmelonntInfo(timelonslicelonID);

    OptimizingSelongmelonntWritelonr writelonr = nelonw OptimizingSelongmelonntWritelonr(
        nelonw SelongmelonntWritelonr(selongmelonntInfo, selonarchIndelonxingMelontricSelont.updatelonFrelonshnelonss),
        criticalelonxcelonptionHandlelonr,
        selonarchIndelonxingMelontricSelont,
        indelonxCaughtUpMonitor);

    putSelongmelonntWritelonr(writelonr);
    relonturn writelonr;
  }

  /**
   * Crelonatelon a nelonw selongmelonnt writelonr.
   */
  public SelongmelonntWritelonr crelonatelonSelongmelonntWritelonr(long timelonslicelonID) throws IOelonxcelonption {
    SelongmelonntInfo selongmelonntInfo = crelonatelonSelongmelonntInfo(timelonslicelonID);

    SelongmelonntWritelonr writelonr = nelonw SelongmelonntWritelonr(
        selongmelonntInfo, selonarchIndelonxingMelontricSelont.updatelonFrelonshnelonss);

    relonturn writelonr;
  }

  privatelon void updatelonAllListelonnelonrs(String melonssagelon) {
    List<SelongmelonntInfo> selongmelonntInfos = selongmelonntWritelonrs.valuelons().strelonam()
        .map(ISelongmelonntWritelonr::gelontSelongmelonntInfo)
        .collelonct(Collelonctors.toList());
    for (SelongmelonntUpdatelonListelonnelonr listelonnelonr : updatelonListelonnelonrs) {
      try {
        listelonnelonr.updatelon(selongmelonntInfos, melonssagelon);
      } catch (elonxcelonption elon) {
        LOG.warn("SelongmelonntManagelonr: Unablelon to call updatelon() on listelonnelonr.", elon);
      }
    }
  }

  // Relonturns truelon if thelon map contains a SelongmelonntInfo matching thelon givelonn timelon slicelon.
  public final boolelonan hasSelongmelonntInfo(long timelonSlicelonID) {
    relonturn selongmelonntWritelonrs.containsKelony(timelonSlicelonID);
  }

  public void addUpdatelonListelonnelonr(SelongmelonntUpdatelonListelonnelonr listelonnelonr) {
    updatelonListelonnelonrs.add(listelonnelonr);
  }

  /**
   * Look up thelon selongmelonnt containing thelon givelonn status id.
   * If found, its timelonslicelon id is relonturnelond.
   * If nonelon found, -1 is relonturnelond.
   */
  public long lookupTimelonSlicelonID(long statusID) throws IOelonxcelonption {
    SelongmelonntInfo selongmelonntInfo = gelontSelongmelonntInfoForID(statusID);
    if (selongmelonntInfo == null) {
      relonturn -1;
    }
    if (!selongmelonntInfo.gelontIndelonxSelongmelonnt().hasDocumelonnt(statusID)) {
        relonturn -1;
    }

    relonturn selongmelonntInfo.gelontTimelonSlicelonID();
  }

  /**
   * Truncatelons thelon givelonn selongmelonnt list to thelon speloncifielond numbelonr of selongmelonnts, by kelonelonping thelon nelonwelonst
   * selongmelonnts.
   */
  @VisiblelonForTelonsting
  public static List<Selongmelonnt> truncatelonSelongmelonntList(List<Selongmelonnt> selongmelonntList, int maxNumSelongmelonnts) {
    // Maybelon cut-off thelon belonginning of thelon sortelond list of IDs.
    if (maxNumSelongmelonnts > 0 && maxNumSelongmelonnts < selongmelonntList.sizelon()) {
      relonturn selongmelonntList.subList(selongmelonntList.sizelon() - maxNumSelongmelonnts, selongmelonntList.sizelon());
    } elonlselon {
      relonturn selongmelonntList;
    }
  }

  @VisiblelonForTelonsting
  public void selontOffelonnsivelon(long uselonrID, boolelonan offelonnsivelon) {
    uselonrTablelon.selontOffelonnsivelon(uselonrID, offelonnsivelon);
  }

  @VisiblelonForTelonsting
  public void selontAntisocial(long uselonrID, boolelonan antisocial) {
    uselonrTablelon.selontAntisocial(uselonrID, antisocial);
  }

  /**
   * Relonturns a selonarchelonr for all selongmelonnts.
   */
  public elonarlybirdMultiSelongmelonntSelonarchelonr gelontMultiSelonarchelonr(ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot)
      throws IOelonxcelonption {
    relonturn nelonw elonarlybirdMultiSelongmelonntSelonarchelonr(
        schelonmaSnapshot,
        gelontSelonarchelonrs(schelonmaSnapshot, Filtelonr.All, Ordelonr.NelonW_TO_OLD),
        selonarchelonrStats,
        clock);
  }

  /**
   * Relonturns a nelonw selonarchelonr for thelon givelonn selongmelonnt.
   */
  @Nullablelon
  public elonarlybirdLucelonnelonSelonarchelonr gelontSelonarchelonr(
      Selongmelonnt selongmelonnt,
      ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot) throws IOelonxcelonption {
    relonturn gelontSelonarchelonr(selongmelonnt.gelontTimelonSlicelonID(), schelonmaSnapshot);
  }

  /**
   * Gelont max twelonelont id across all elonnablelond selongmelonnts.
   * @relonturn max twelonelont id or -1 if nonelon found
   */
  public long gelontMaxTwelonelontIdFromelonnablelondSelongmelonnts() {
    for (SelongmelonntInfo selongmelonntInfo : gelontSelongmelonntInfos(Filtelonr.elonnablelond, Ordelonr.NelonW_TO_OLD)) {
      long maxTwelonelontId = selongmelonntInfo.gelontIndelonxSelongmelonnt().gelontMaxTwelonelontId();
      if (maxTwelonelontId != -1) {
        relonturn maxTwelonelontId;
      }
    }

    relonturn -1;
  }

  /**
   * Crelonatelon a twelonelont indelonx selonarchelonr on thelon selongmelonnt relonprelonselonntelond by thelon timelonslicelon id.  For production
   * selonarch selonssion, thelon schelonma snapshot should belon always passelond in to makelon surelon that thelon schelonma
   * usagelon insidelon scoring is consistelonnt.
   *
   * For non-production usagelon, likelon onelon-off delonbugging selonarch, you can uselon thelon function call without
   * thelon schelonma snapshot.
   *
   * @param timelonSlicelonID thelon timelonslicelon id, which relonprelonselonnts thelon indelonx selongmelonnt
   * @param schelonmaSnapshot thelon schelonma snapshot
   * @relonturn thelon twelonelont indelonx selonarchelonr
   */
  @Nullablelon
  public elonarlybirdSinglelonSelongmelonntSelonarchelonr gelontSelonarchelonr(
      long timelonSlicelonID,
      ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot) throws IOelonxcelonption {
    SelongmelonntInfo selongmelonntInfo = gelontSelongmelonntInfo(timelonSlicelonID);
    if (selongmelonntInfo == null) {
      relonturn null;
    }
    relonturn selongmelonntInfo.gelontIndelonxSelongmelonnt().gelontSelonarchelonr(uselonrTablelon, schelonmaSnapshot);
  }

  /**
   * Relonturns a nelonw selonarchelonr for thelon selongmelonnt with thelon givelonn timelonslicelon ID. If thelon givelonn timelonslicelon ID
   * doelons not correlonspond to any activelon selongmelonnt, {@codelon null} is relonturnelond.
   *
   * @param timelonSlicelonID Thelon selongmelonnt's timelonslicelon ID.
   * @relonturn A nelonw selonarchelonr for thelon selongmelonnt with thelon givelonn timelonslicelon ID.
   */
  @Nullablelon
  public elonarlybirdSinglelonSelongmelonntSelonarchelonr gelontSelonarchelonr(long timelonSlicelonID) throws IOelonxcelonption {
    SelongmelonntInfo selongmelonntInfo = gelontSelongmelonntInfo(timelonSlicelonID);
    if (selongmelonntInfo == null) {
      relonturn null;
    }
    relonturn selongmelonntInfo.gelontIndelonxSelongmelonnt().gelontSelonarchelonr(uselonrTablelon);
  }

  @Nullablelon
  public elonarlybirdRelonsponselonCodelon chelonckSelongmelonnt(Selongmelonnt selongmelonnt) {
    relonturn chelonckSelongmelonntIntelonrnal(gelontSelongmelonntInfo(selongmelonnt.gelontTimelonSlicelonID()));
  }

  privatelon static elonarlybirdRelonsponselonCodelon chelonckSelongmelonntIntelonrnal(SelongmelonntInfo info) {
    if (info == null) {
      relonturn elonarlybirdRelonsponselonCodelon.PARTITION_NOT_FOUND;
    } elonlselon if (info.iselonnablelond()) {
      relonturn elonarlybirdRelonsponselonCodelon.SUCCelonSS;
    } elonlselon {
      relonturn elonarlybirdRelonsponselonCodelon.PARTITION_DISABLelonD;
    }
  }

  privatelon List<elonarlybirdSinglelonSelongmelonntSelonarchelonr> gelontSelonarchelonrs(
      ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot,
      Filtelonr filtelonr,
      Ordelonr ordelonr) throws IOelonxcelonption {
    List<elonarlybirdSinglelonSelongmelonntSelonarchelonr> selonarchelonrs = Lists.nelonwArrayList();
    for (SelongmelonntInfo selongmelonntInfo : gelontSelongmelonntInfos(filtelonr, ordelonr)) {
      elonarlybirdSinglelonSelongmelonntSelonarchelonr selonarchelonr =
          selongmelonntInfo.gelontIndelonxSelongmelonnt().gelontSelonarchelonr(uselonrTablelon, schelonmaSnapshot);
      if (selonarchelonr != null) {
        selonarchelonrs.add(selonarchelonr);
      }
    }
    relonturn selonarchelonrs;
  }

  /**
   * Gelonts melontadata for selongmelonnts for delonbugging purposelons.
   */
  public List<String> gelontSelongmelonntMelontadata() {
    List<String> selongmelonntMelontadata = nelonw ArrayList<>();
    for (SelongmelonntInfo selongmelonnt : gelontSelongmelonntInfos(Filtelonr.All, Ordelonr.OLD_TO_NelonW)) {
      selongmelonntMelontadata.add(selongmelonnt.gelontSelongmelonntMelontadata());
    }
    relonturn selongmelonntMelontadata;
  }

  /**
   * Gelonts info for quelonry cachelons to belon displayelond in an admin pagelon.
   */
  public String gelontQuelonryCachelonsData() {
    StringBuildelonr output = nelonw StringBuildelonr();
    for (SelongmelonntInfo selongmelonnt : gelontSelongmelonntInfos(Filtelonr.All, Ordelonr.OLD_TO_NelonW)) {
      output.appelonnd(selongmelonnt.gelontQuelonryCachelonsData() + "\n");
    }
    relonturn output.toString();
  }

  /**
   * Indelonx thelon givelonn uselonr updatelon. Relonturns falselon if thelon givelonn updatelon is skippelond.
   */
  public boolelonan indelonxUselonrUpdatelon(UselonrUpdatelon uselonrUpdatelon) {
    relonturn uselonrTablelon.indelonxUselonrUpdatelon(uselonrUpdatelonsChelonckelonr, uselonrUpdatelon);
  }

  /**
   * Indelonx thelon givelonn UselonrScrubGelonoelonvelonnt.
   * @param uselonrScrubGelonoelonvelonnt
   */
  public void indelonxUselonrScrubGelonoelonvelonnt(UselonrScrubGelonoelonvelonnt uselonrScrubGelonoelonvelonnt) {
    uselonrScrubGelonoMap.indelonxUselonrScrubGelonoelonvelonnt(uselonrScrubGelonoelonvelonnt);
  }

  /**
   * Relonturn how many documelonnts this selongmelonnt managelonr has indelonxelond in all of its elonnablelond selongmelonnts.
   */
  public long gelontNumIndelonxelondDocumelonnts() {
    // Ordelonr helonrelon doelonsn't mattelonr, welon just want all elonnablelond selongmelonnts, and allocatelon
    // as littlelon as nelonelondelond.
    long indelonxelondDocs = 0;
    for (SelongmelonntInfo selongmelonntInfo : gelontSelongmelonntInfos(Filtelonr.elonnablelond, Ordelonr.OLD_TO_NelonW)) {
      indelonxelondDocs += selongmelonntInfo.gelontIndelonxSelongmelonnt().gelontIndelonxStats().gelontStatusCount();
    }
    relonturn indelonxelondDocs;
  }

  /**
   * Relonturn how many partial updatelons this selongmelonnt managelonr has applielond
   * in all of its elonnablelond selongmelonnts.
   */
  public long gelontNumPartialUpdatelons() {
    long partialUpdatelons = 0;
    for (SelongmelonntInfo selongmelonntInfo : gelontSelongmelonntInfos(Filtelonr.elonnablelond, Ordelonr.OLD_TO_NelonW)) {
      partialUpdatelons += selongmelonntInfo.gelontIndelonxSelongmelonnt().gelontIndelonxStats().gelontPartialUpdatelonCount();
    }
    relonturn partialUpdatelons;
  }

  /**
   * Relonturns thelon selongmelonnt info for thelon selongmelonnt containing thelon givelonn twelonelont ID.
   */
  public SelongmelonntInfo gelontSelongmelonntInfoForID(long twelonelontID) {
    ISelongmelonntWritelonr selongmelonntWritelonr = gelontSelongmelonntWritelonrForID(twelonelontID);
    relonturn selongmelonntWritelonr == null ? null : selongmelonntWritelonr.gelontSelongmelonntInfo();
  }

  /**
   * Relonturns thelon selongmelonnt writelonr for thelon selongmelonnt containing thelon givelonn twelonelont ID.
   */
  @Nullablelon
  public ISelongmelonntWritelonr gelontSelongmelonntWritelonrForID(long twelonelontID) {
    Map.elonntry<Long, ISelongmelonntWritelonr> elonntry = selongmelonntWritelonrs.floorelonntry(twelonelontID);
    relonturn elonntry == null ? null : elonntry.gelontValuelon();
  }

  /**
   * Relonmovelon old selongmelonnts until welon havelon lelonss than or elonqual to thelon numbelonr of max elonnablelond selongmelonnts.
   */
  public void relonmovelonelonxcelonssSelongmelonnts() {
    int relonmovelondSelongmelonntCount = 0;
    whilelon (selongmelonntWritelonrs.sizelon() > gelontMaxelonnablelondSelongmelonnts()) {
      long timelonslicelonID = gelontOldelonstelonnablelondTimelonSlicelonID();
      disablelonSelongmelonnt(timelonslicelonID);
      relonmovelonSelongmelonntInfo(timelonslicelonID);
      relonmovelondSelongmelonntCount += 1;
    }
    LOG.info("Selongmelonnt managelonr relonmovelond {} elonxcelonss selongmelonnts", relonmovelondSelongmelonntCount);
  }

  /**
   * Relonturns total indelonx sizelon on disk across all elonnablelond selongmelonnts in this selongmelonnt managelonr.
   */
  privatelon long gelontTotalSelongmelonntSizelonOnDisk() {
    long totalIndelonxSizelon = 0;
    for (SelongmelonntInfo selongmelonntInfo : gelontSelongmelonntInfos(Filtelonr.elonnablelond, Ordelonr.OLD_TO_NelonW)) {
      totalIndelonxSizelon += selongmelonntInfo.gelontIndelonxSelongmelonnt().gelontIndelonxStats().gelontIndelonxSizelonOnDiskInBytelons();
    }
    relonturn totalIndelonxSizelon;
  }

  @VisiblelonForTelonsting
  ISelongmelonntWritelonr gelontSelongmelonntWritelonrWithoutCrelonationForTelonsts(long timelonslicelonID) {
    relonturn selongmelonntWritelonrs.gelont(timelonslicelonID);
  }

  @VisiblelonForTelonsting
  ArrayList<Long> gelontTimelonSlicelonIdsForTelonsts() {
    relonturn nelonw ArrayList<Long>(selongmelonntWritelonrs.kelonySelont());
  }
}
