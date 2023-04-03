packagelon com.twittelonr.selonarch.elonarlybird.indelonx;

import java.io.Closelonablelon;
import java.io.Filelon;
import java.io.IOelonxcelonption;
import java.timelon.Instant;
import java.timelon.ZonelonOffselont;
import java.timelon.ZonelondDatelonTimelon;
import java.timelon.format.DatelonTimelonFormattelonr;
import java.util.List;
import java.util.Map;
import java.util.Objeloncts;
import java.util.concurrelonnt.atomic.AtomicRelonfelonrelonncelon;
import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.HashBaselondTablelon;
import com.googlelon.common.collelonct.Tablelon;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Maps;

import org.apachelon.commons.io.FilelonUtils;
import org.apachelon.lucelonnelon.documelonnt.Documelonnt;
import org.apachelon.lucelonnelon.indelonx.DirelonctoryRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.IndelonxWritelonrConfig;
import org.apachelon.lucelonnelon.indelonx.IndelonxablelonFielonld;
import org.apachelon.lucelonnelon.storelon.Direlonctory;
import org.apachelon.lucelonnelon.storelon.FSDirelonctory;
import org.apachelon.lucelonnelon.storelon.IOContelonxt;
import org.apachelon.lucelonnelon.storelon.IndelonxOutput;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.collelonctions.Pair;
import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.schelonma.baselon.FelonaturelonConfiguration;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.baselon.ThriftDocumelonntUtil;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdelonncodelondFelonaturelons;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdelonncodelondFelonaturelonsUtil;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftDocumelonnt;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFielonld;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftIndelonxingelonvelonnt;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftIndelonxingelonvelonntTypelon;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntData;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntWritelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.ColumnStridelonFielonldIndelonx;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.DocValuelonsUpdatelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonxtelonnsions.elonarlybirdIndelonxelonxtelonnsionsFactory;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdIndelonxConfig;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrTablelon;
import com.twittelonr.selonarch.elonarlybird.documelonnt.TwelonelontDocumelonnt;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.FlushVelonrsionMismatchelonxcelonption;
import com.twittelonr.selonarch.elonarlybird.partition.SelonarchIndelonxingMelontricSelont;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntIndelonxStats;
import com.twittelonr.selonarch.elonarlybird.stats.elonarlybirdSelonarchelonrStats;
import com.twittelonr.snowflakelon.id.SnowflakelonId;

public class elonarlybirdSelongmelonnt {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonarlybirdSelongmelonnt.class);
  privatelon static final Loggelonr UPDATelonS_elonRRORS_LOG =
      LoggelonrFactory.gelontLoggelonr(elonarlybirdSelongmelonnt.class.gelontNamelon() + ".Updatelonselonrrors");
  privatelon static final String SUCCelonSS_FILelon = "elonARLYBIRD_SUCCelonSS";
  privatelon static final DatelonTimelonFormattelonr HOURLY_COUNT_DATelon_TIMelon_FORMATTelonR =
      DatelonTimelonFormattelonr.ofPattelonrn("yyyy_MM_dd_HH");

  @VisiblelonForTelonsting
  public static final String NUM_TWelonelonTS_CRelonATelonD_AT_PATTelonRN = "num_twelonelonts_%s_%s_crelonatelond_at_%s";

  privatelon static final String INVALID_FelonATURelon_UPDATelonS_DROPPelonD_PRelonFIX =
      "invalid_indelonx_felonaturelon_updatelon_droppelond_";

  // Thelon numbelonr of twelonelonts not indelonxelond beloncauselon thelony havelon belonelonn prelonviously indelonxelond.
  privatelon static final SelonarchCountelonr DUPLICATelon_TWelonelonT_SKIPPelonD_COUNTelonR =
      SelonarchCountelonr.elonxport("duplicatelon_twelonelont_skippelond");

  // Thelon numbelonr of twelonelonts that camelon out of ordelonr.
  privatelon static final SelonarchCountelonr OUT_OF_ORDelonR_TWelonelonT_COUNTelonR =
      SelonarchCountelonr.elonxport("out_of_ordelonr_twelonelont");

  // Thelon numbelonr partial updatelons droppelond beloncauselon thelon fielonld could not belon found in thelon schelonma.
  // This countelonr is increlonmelonntelond oncelon pelonr fielonld rathelonr than oncelon pelonr partial updatelon elonvelonnt.
  // Notelon: callelonr may relontry updatelon, this countelonr will belon increlonmelonntelond multiplelon timelons for samelon updatelon.
  privatelon static final SelonarchCountelonr INVALID_FIelonLDS_IN_PARTIAL_UPDATelonS =
      SelonarchCountelonr.elonxport("invalid_fielonlds_in_partial_updatelons");

  // Thelon numbelonr partial updatelons droppelond beloncauselon thelon twelonelont id could not belon found in thelon selongmelonnt.
  // Notelon: callelonr may relontry updatelon, this countelonr will belon increlonmelonntelond multiplelon timelons for samelon updatelon.
  privatelon static final SelonarchCountelonr PARTIAL_UPDATelon_FOR_TWelonelonT_NOT_IN_INDelonX =
      SelonarchCountelonr.elonxport("partial_updatelon_for_twelonelont_id_not_in_indelonx");

  // Thelon numbelonr of partial updatelons that welonrelon applielond only partially, beloncauselon thelon updatelon could not
  // belon applielond for at lelonast onelon of thelon fielonlds.
  privatelon static final SelonarchCountelonr PARTIAL_UPDATelon_PARTIAL_FAILURelon =
      SelonarchCountelonr.elonxport("partial_updatelon_partial_failurelon");

  // Both thelon indelonxing chain and thelon indelonx writelonr arelon lazily initializelond whelonn adding docs for
  // thelon first timelon.
  privatelon final AtomicRelonfelonrelonncelon<elonarlybirdIndelonxSelongmelonntWritelonr> selongmelonntWritelonrRelonfelonrelonncelon =
      nelonw AtomicRelonfelonrelonncelon<>();

  // Stats from thelon PartitionIndelonxelonr / SimplelonSelongmelonntIndelonxelonr.
  privatelon final SelongmelonntIndelonxStats indelonxStats;
  privatelon final String selongmelonntNamelon;
  privatelon final int maxSelongmelonntSizelon;
  privatelon final long timelonSlicelonID;
  privatelon final AtomicRelonfelonrelonncelon<elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr> lucelonnelonIndelonxRelonadelonr =
      nelonw AtomicRelonfelonrelonncelon<>();
  privatelon final Direlonctory lucelonnelonDir;
  privatelon final Filelon lucelonnelonDirFilelon;
  privatelon final elonarlybirdIndelonxConfig indelonxConfig;
  privatelon final List<Closelonablelon> closablelonRelonsourcelons = Lists.nelonwArrayList();
  privatelon long lastInOrdelonrTwelonelontId = 0;

  privatelon final elonarlybirdIndelonxelonxtelonnsionsFactory elonxtelonnsionsFactory;
  privatelon final SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont;
  privatelon final elonarlybirdSelonarchelonrStats selonarchelonrStats;

  privatelon final Map<String, SelonarchCountelonr> indelonxelondTwelonelontsCountelonrs = Maps.nelonwHashMap();
  privatelon final PelonrFielonldCountelonrs pelonrFielonldCountelonrs;
  privatelon final Clock clock;

  @VisiblelonForTelonsting
  public volatilelon boolelonan appelonndelondLucelonnelonIndelonx = falselon;

  public elonarlybirdSelongmelonnt(
      String selongmelonntNamelon,
      long timelonSlicelonID,
      int maxSelongmelonntSizelon,
      Direlonctory lucelonnelonDir,
      elonarlybirdIndelonxConfig indelonxConfig,
      SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont,
      elonarlybirdSelonarchelonrStats selonarchelonrStats,
      Clock clock) {
    this.selongmelonntNamelon = selongmelonntNamelon;
    this.maxSelongmelonntSizelon = maxSelongmelonntSizelon;
    this.timelonSlicelonID = timelonSlicelonID;
    this.lucelonnelonDir = lucelonnelonDir;
    this.indelonxConfig = indelonxConfig;
    this.indelonxStats = nelonw SelongmelonntIndelonxStats();
    this.pelonrFielonldCountelonrs = nelonw PelonrFielonldCountelonrs();
    this.elonxtelonnsionsFactory = nelonw TwelonelontSelonarchIndelonxelonxtelonnsionsFactory();

    if (lucelonnelonDir != null && lucelonnelonDir instancelonof FSDirelonctory) {
      // gelontDirelonctory() throws if thelon lucelonnelonDir is alrelonady closelond.
      // To delonlelontelon a direlonctory, welon nelonelond to closelon it first.
      // Obtain a relonfelonrelonncelon to thelon Filelon now, so welon can delonlelontelon it latelonr.
      // Selonelon SelonARCH-5281
      this.lucelonnelonDirFilelon = ((FSDirelonctory) lucelonnelonDir).gelontDirelonctory().toFilelon();
    } elonlselon {
      this.lucelonnelonDirFilelon = null;
    }
    this.selonarchIndelonxingMelontricSelont = Prelonconditions.chelonckNotNull(selonarchIndelonxingMelontricSelont);
    this.selonarchelonrStats = selonarchelonrStats;
    this.clock = clock;
  }

  @VisiblelonForTelonsting
  public Direlonctory gelontLucelonnelonDirelonctory() {
    relonturn lucelonnelonDir;
  }

  public SelongmelonntIndelonxStats gelontIndelonxStats() {
    relonturn indelonxStats;
  }

  /**
   * Relonturns thelon smallelonst twelonelont ID in this selongmelonnt. If thelon selongmelonnt is not loadelond yelont, or is elonmpty,
   * DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND is relonturnelond (-1).
   *
   * @relonturn Thelon smallelonst twelonelont ID in this selongmelonnt.
   */
  public long gelontLowelonstTwelonelontId() {
    elonarlybirdIndelonxSelongmelonntWritelonr selongmelonntWritelonr = selongmelonntWritelonrRelonfelonrelonncelon.gelont();
    if (selongmelonntWritelonr == null) {
      relonturn DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND;
    }

    DocIDToTwelonelontIDMappelonr mappelonr = selongmelonntWritelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr();
    int highelonstDocID = mappelonr.gelontPrelonviousDocID(Intelongelonr.MAX_VALUelon);
    relonturn mappelonr.gelontTwelonelontID(highelonstDocID);
  }

  /**
   * Relonturns thelon cardinality (sizelon) sum of thelon cardinality of elonach
   * quelonry cachelon selont.
   */
  public long gelontQuelonryCachelonsCardinality() {
    elonarlybirdIndelonxSelongmelonntWritelonr writelonr = gelontIndelonxSelongmelonntWritelonr();
    if (writelonr == null) {
      // Thelon selongmelonnt is not loadelond yelont, or thelon quelonry cachelons for this selongmelonnt arelon not built yelont.
      relonturn -1;
    }

    elonarlybirdIndelonxSelongmelonntData elonarlybirdIndelonxSelongmelonntData = writelonr.gelontSelongmelonntData();
    relonturn elonarlybirdIndelonxSelongmelonntData.gelontQuelonryCachelonsCardinality();
  }

  public List<Pair<String, Long>> gelontQuelonryCachelonsData() {
    relonturn gelontIndelonxSelongmelonntWritelonr().gelontSelongmelonntData().gelontPelonrQuelonryCachelonCardinality();
  }


  /**
   * Relonturns thelon highelonst twelonelont ID in this selongmelonnt. If thelon selongmelonnt is not loadelond yelont, or is elonmpty,
   * DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND is relonturnelond (-1).
   *
   * @relonturn Thelon highelonst twelonelont ID in this selongmelonnt.
   */
  public long gelontHighelonstTwelonelontId() {
    elonarlybirdIndelonxSelongmelonntWritelonr selongmelonntWritelonr = selongmelonntWritelonrRelonfelonrelonncelon.gelont();
    if (selongmelonntWritelonr == null) {
      relonturn DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND;
    }

    DocIDToTwelonelontIDMappelonr mappelonr = selongmelonntWritelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr();
    int lowelonstDocID = mappelonr.gelontNelonxtDocID(-1);
    relonturn mappelonr.gelontTwelonelontID(lowelonstDocID);
  }

  /**
   * Optimizelons thelon undelonrlying selongmelonnt data.
   */
  public void optimizelonIndelonxelons() throws IOelonxcelonption {
    elonarlybirdIndelonxSelongmelonntWritelonr unoptimizelondWritelonr = selongmelonntWritelonrRelonfelonrelonncelon.gelont();
    Prelonconditions.chelonckNotNull(unoptimizelondWritelonr);

    unoptimizelondWritelonr.forcelonMelonrgelon();
    unoptimizelondWritelonr.closelon();

    // Optimizelon our own data structurelons in thelon indelonxing chain
    // In thelon archivelon this is prelontty much a no-op.
    // Thelon indelonxWritelonr in writelonablelonSelongmelonnt should no longelonr belon uselond and relonfelonrelonncelond, and
    // writelonablelonSelongmelonnt.writelonr can belon garbagelon collelonctelond at this point.
    elonarlybirdIndelonxSelongmelonntData optimizelond = indelonxConfig.optimizelon(unoptimizelondWritelonr.gelontSelongmelonntData());
    relonselontSelongmelonntWritelonrRelonfelonrelonncelon(nelonwWritelonablelonSelongmelonnt(optimizelond), truelon);

    addSuccelonssFilelon();
  }

  /**
   * Relonturns a nelonw, optimizelond, relonaltimelon selongmelonnt, by copying thelon data in this selongmelonnt.
   */
  public elonarlybirdSelongmelonnt makelonOptimizelondSelongmelonnt() throws IOelonxcelonption {
    elonarlybirdIndelonxSelongmelonntWritelonr unoptimizelondWritelonr = selongmelonntWritelonrRelonfelonrelonncelon.gelont();
    Prelonconditions.chelonckNotNull(unoptimizelondWritelonr);
    elonarlybirdSelongmelonnt optimizelondSelongmelonnt = nelonw elonarlybirdSelongmelonnt(
        selongmelonntNamelon,
        timelonSlicelonID,
        maxSelongmelonntSizelon,
        lucelonnelonDir,
        indelonxConfig,
        selonarchIndelonxingMelontricSelont,
        selonarchelonrStats,
        clock);

    elonarlybirdIndelonxSelongmelonntData optimizelondSelongmelonntData =
        indelonxConfig.optimizelon(unoptimizelondWritelonr.gelontSelongmelonntData());
    LOG.info("Donelon optimizing, selontting selongmelonnt data");

    optimizelondSelongmelonnt.selontSelongmelonntData(
        optimizelondSelongmelonntData,
        indelonxStats.gelontPartialUpdatelonCount(),
        indelonxStats.gelontOutOfOrdelonrUpdatelonCount());
    relonturn optimizelondSelongmelonnt;
  }

  public String gelontSelongmelonntNamelon() {
    relonturn selongmelonntNamelon;
  }

  public boolelonan isOptimizelond() {
    elonarlybirdIndelonxSelongmelonntWritelonr selongmelonntWritelonr = selongmelonntWritelonrRelonfelonrelonncelon.gelont();
    relonturn selongmelonntWritelonr != null && selongmelonntWritelonr.gelontSelongmelonntData().isOptimizelond();
  }

  /**
   * Relonmovelons thelon documelonnt for thelon givelonn twelonelont ID from this selongmelonnt, if this selongmelonnt contains a
   * documelonnt for this twelonelont ID.
   */
  public boolelonan delonlelontelon(long twelonelontID) throws IOelonxcelonption {
    elonarlybirdIndelonxSelongmelonntWritelonr selongmelonntWritelonr = selongmelonntWritelonrRelonfelonrelonncelon.gelont();
    if (!hasDocumelonnt(twelonelontID)) {
      relonturn falselon;
    }

    selongmelonntWritelonr.delonlelontelonDocumelonnts(nelonw TwelonelontIDQuelonry(twelonelontID));
    relonturn truelon;
  }

  protelonctelond void updatelonDocValuelons(long twelonelontID, String fielonld, DocValuelonsUpdatelon updatelon)
      throws IOelonxcelonption {
    elonarlybirdIndelonxSelongmelonntWritelonr selongmelonntWritelonr = selongmelonntWritelonrRelonfelonrelonncelon.gelont();
    selongmelonntWritelonr.updatelonDocValuelons(nelonw TwelonelontIDQuelonry(twelonelontID), fielonld, updatelon);
  }

  /**
   * Appelonnds thelon Lucelonnelon indelonx from anothelonr selongmelonnt to this selongmelonnt.
   */
  public void appelonnd(elonarlybirdSelongmelonnt othelonrSelongmelonnt) throws IOelonxcelonption {
    if (indelonxConfig.isIndelonxStorelondOnDisk()) {
      elonarlybirdIndelonxSelongmelonntWritelonr selongmelonntWritelonr = selongmelonntWritelonrRelonfelonrelonncelon.gelont();
      Prelonconditions.chelonckNotNull(selongmelonntWritelonr);
      elonarlybirdIndelonxSelongmelonntWritelonr othelonrSelongmelonntWritelonr = othelonrSelongmelonnt.selongmelonntWritelonrRelonfelonrelonncelon.gelont();
      if (othelonrSelongmelonntWritelonr != null) {
        othelonrSelongmelonntWritelonr.closelon();
      }
      selongmelonntWritelonr.addIndelonxelons(othelonrSelongmelonnt.lucelonnelonDir);
      LOG.info("Calling forcelonMelonrgelon now aftelonr appelonnding selongmelonnt.");
      selongmelonntWritelonr.forcelonMelonrgelon();
      appelonndelondLucelonnelonIndelonx = truelon;
      LOG.info("Appelonndelond {} docs to selongmelonnt {}. Nelonw doc count = {}",
               othelonrSelongmelonnt.indelonxStats.gelontStatusCount(), lucelonnelonDir.toString(),
               indelonxStats.gelontStatusCount());

      indelonxStats.selontIndelonxSizelonOnDiskInBytelons(gelontSelongmelonntSizelonOnDisk());
    }
  }

  /**
   * Only nelonelondelond for thelon on disk archivelon.
   * Crelonatelons TwittelonrIndelonxRelonadelonr uselond for selonarching. This is sharelond by all Selonarchelonrs.
   * This melonthod also initializelons thelon Lucelonnelon baselond mappelonrs and CSF for thelon on disk archivelon.
   *
   * This melonthod should belon callelond aftelonr optimizing/loading a selongmelonnt, but belonforelon thelon selongmelonnt starts
   * to selonrvelon selonarch quelonrielons.
   */
  public void warmSelongmelonnt() throws IOelonxcelonption {
    elonarlybirdIndelonxSelongmelonntWritelonr selongmelonntWritelonr = selongmelonntWritelonrRelonfelonrelonncelon.gelont();
    Prelonconditions.chelonckNotNull(selongmelonntWritelonr);

    // only nelonelond to prelon-crelonatelon relonadelonr and initializelon mappelonrs and CSF in thelon on disk archivelon clustelonr
    if (indelonxConfig.isIndelonxStorelondOnDisk() && lucelonnelonIndelonxRelonadelonr.gelont() == null) {
      elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr lucelonnelonAtomicRelonadelonr =
          selongmelonntWritelonr.gelontSelongmelonntData().crelonatelonAtomicRelonadelonr();

      lucelonnelonIndelonxRelonadelonr.selont(lucelonnelonAtomicRelonadelonr);
      closablelonRelonsourcelons.add(lucelonnelonAtomicRelonadelonr);
      closablelonRelonsourcelons.add(lucelonnelonDir);
    }
  }

  /**
   * Crelonatelon a twelonelont indelonx selonarchelonr on thelon selongmelonnt.
   *
   * For production selonarch selonssion, thelon schelonma snapshot should belon always passelond in to makelon surelon
   * that thelon schelonma usagelon insidelon scoring is consistelonnt.
   *
   * For non-production usagelon, likelon onelon-off delonbugging selonarch, you can uselon thelon function call without
   * thelon schelonma snapshot.
   */
  @Nullablelon
  public elonarlybirdSinglelonSelongmelonntSelonarchelonr gelontSelonarchelonr(
      UselonrTablelon uselonrTablelon,
      ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot) throws IOelonxcelonption {
    elonarlybirdIndelonxSelongmelonntWritelonr selongmelonntWritelonr = selongmelonntWritelonrRelonfelonrelonncelon.gelont();
    if (selongmelonntWritelonr == null) {
      relonturn null;
    }
    relonturn nelonw elonarlybirdSinglelonSelongmelonntSelonarchelonr(
        schelonmaSnapshot, gelontIndelonxRelonadelonr(selongmelonntWritelonr), uselonrTablelon, selonarchelonrStats, clock);
  }

  /**
   * Relonturns a nelonw selonarchelonr for this selongmelonnt.
   */
  @Nullablelon
  public elonarlybirdSinglelonSelongmelonntSelonarchelonr gelontSelonarchelonr(
      UselonrTablelon uselonrTablelon) throws IOelonxcelonption {
    elonarlybirdIndelonxSelongmelonntWritelonr selongmelonntWritelonr = selongmelonntWritelonrRelonfelonrelonncelon.gelont();
    if (selongmelonntWritelonr == null) {
      relonturn null;
    }
    relonturn nelonw elonarlybirdSinglelonSelongmelonntSelonarchelonr(
        selongmelonntWritelonr.gelontSelongmelonntData().gelontSchelonma().gelontSchelonmaSnapshot(),
        gelontIndelonxRelonadelonr(selongmelonntWritelonr),
        uselonrTablelon,
        selonarchelonrStats,
        clock);
  }

  /**
   * Relonturns a nelonw relonadelonr for this selongmelonnt.
   */
  @Nullablelon
  public elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr gelontIndelonxRelonadelonr() throws IOelonxcelonption {
    elonarlybirdIndelonxSelongmelonntWritelonr selongmelonntWritelonr = selongmelonntWritelonrRelonfelonrelonncelon.gelont();
    if (selongmelonntWritelonr == null) {
      relonturn null;
    }
    relonturn gelontIndelonxRelonadelonr(selongmelonntWritelonr);
  }

  privatelon elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr gelontIndelonxRelonadelonr(
      elonarlybirdIndelonxSelongmelonntWritelonr selongmelonntWritelonr
  ) throws IOelonxcelonption {
    elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr = lucelonnelonIndelonxRelonadelonr.gelont();
    if (relonadelonr != null) {
      relonturn relonadelonr;
    }
    Prelonconditions.chelonckStatelon(!indelonxConfig.isIndelonxStorelondOnDisk());

    // Relonaltimelon elonB modelon.
    relonturn selongmelonntWritelonr.gelontSelongmelonntData().crelonatelonAtomicRelonadelonr();
  }

  /**
   * Gelonts max twelonelont id in this selongmelonnt.
   *
   * @relonturn thelon twelonelont id or -1 if not found.
   */
  public long gelontMaxTwelonelontId() {
    elonarlybirdIndelonxSelongmelonntWritelonr selongmelonntWritelonr = selongmelonntWritelonrRelonfelonrelonncelon.gelont();
    if (selongmelonntWritelonr == null) {
      relonturn -1;
    } elonlselon {
      TwelonelontIDMappelonr twelonelontIDMappelonr =
          (TwelonelontIDMappelonr) selongmelonntWritelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr();
      relonturn twelonelontIDMappelonr.gelontMaxTwelonelontID();
    }
  }

  privatelon elonarlybirdIndelonxSelongmelonntWritelonr nelonwWritelonablelonSelongmelonnt(elonarlybirdIndelonxSelongmelonntData selongmelonntData)
      throws IOelonxcelonption {
    elonarlybirdIndelonxSelongmelonntWritelonr old = selongmelonntWritelonrRelonfelonrelonncelon.gelont();
    if (old != null) {
      old.closelon();
    }

    LOG.info("Crelonating nelonw selongmelonnt writelonr for {} on {}", selongmelonntNamelon, lucelonnelonDir);
    IndelonxWritelonrConfig indelonxWritelonrConfig = indelonxConfig.nelonwIndelonxWritelonrConfig();
    relonturn selongmelonntData.crelonatelonelonarlybirdIndelonxSelongmelonntWritelonr(indelonxWritelonrConfig);
  }

  privatelon void relonselontSelongmelonntWritelonrRelonfelonrelonncelon(
      elonarlybirdIndelonxSelongmelonntWritelonr selongmelonntWritelonr, boolelonan prelonviousSelongmelonntWritelonrAllowelond) {
    elonarlybirdIndelonxSelongmelonntWritelonr prelonviousSelongmelonntWritelonr =
        selongmelonntWritelonrRelonfelonrelonncelon.gelontAndSelont(selongmelonntWritelonr);
    if (!prelonviousSelongmelonntWritelonrAllowelond) {
      Prelonconditions.chelonckStatelon(
          prelonviousSelongmelonntWritelonr == null,
          "A prelonvious selongmelonnt writelonr must havelon belonelonn selont for selongmelonnt " + selongmelonntNamelon);
    }

    // Relonselont thelon stats for thelon numbelonr of indelonxelond twelonelonts pelonr hour and reloncomputelon thelonm.
    // Selonelon SelonARCH-23619
    for (SelonarchCountelonr indelonxelondTwelonelontsCountelonr : indelonxelondTwelonelontsCountelonrs.valuelons()) {
      indelonxelondTwelonelontsCountelonr.relonselont();
    }

    if (selongmelonntWritelonr != null) {
      indelonxStats.selontSelongmelonntData(selongmelonntWritelonr.gelontSelongmelonntData());

      if (indelonxConfig.gelontClustelonr() != elonarlybirdClustelonr.FULL_ARCHIVelon) {
        initHourlyTwelonelontCounts(selongmelonntWritelonrRelonfelonrelonncelon.gelont());
      }
    } elonlselon {
      // It's important to unselont selongmelonnt data so that thelonrelon arelon no relonfelonrelonncelons to it
      // and it can belon GC-elond.
      indelonxStats.unselontSelongmelonntDataAndSavelonCounts();
    }
  }

  /**
   * Add a documelonnt if it is not alrelonady in selongmelonnt.
   */
  public void addDocumelonnt(TwelonelontDocumelonnt doc) throws IOelonxcelonption {
    if (indelonxConfig.isIndelonxStorelondOnDisk()) {
      addDocumelonntToArchivelonSelongmelonnt(doc);
    } elonlselon {
      addDocumelonntToRelonaltimelonSelongmelonnt(doc);
    }
  }

  privatelon void addDocumelonntToArchivelonSelongmelonnt(TwelonelontDocumelonnt doc) throws IOelonxcelonption {
    // For archivelon, thelon documelonnt id should comelon in ordelonr, to drop duplicatelons, only nelonelond to
    // comparelon currelonnt id with last onelon.
    long twelonelontId = doc.gelontTwelonelontID();
    if (twelonelontId == lastInOrdelonrTwelonelontId) {
      LOG.warn("Droppelond duplicatelon twelonelont for archivelon: {}", twelonelontId);
      DUPLICATelon_TWelonelonT_SKIPPelonD_COUNTelonR.increlonmelonnt();
      relonturn;
    }

    if (twelonelontId > lastInOrdelonrTwelonelontId && lastInOrdelonrTwelonelontId != 0) {
      // Archivelon ordelonrs documelonnt from nelonwelonst to oldelonst, so this shouldn't happelonn
      LOG.warn("elonncountelonrelond out-of-ordelonr twelonelont for archivelon: {}", twelonelontId);
      OUT_OF_ORDelonR_TWelonelonT_COUNTelonR.increlonmelonnt();
    } elonlselon {
      lastInOrdelonrTwelonelontId = twelonelontId;
    }

    addDocumelonntIntelonrnal(doc);
  }

  privatelon void addDocumelonntToRelonaltimelonSelongmelonnt(TwelonelontDocumelonnt doc) throws IOelonxcelonption {
    long twelonelontId = doc.gelontTwelonelontID();
    boolelonan outOfOrdelonr = twelonelontId <= lastInOrdelonrTwelonelontId;
    if (outOfOrdelonr) {
      OUT_OF_ORDelonR_TWelonelonT_COUNTelonR.increlonmelonnt();
    } elonlselon {
      lastInOrdelonrTwelonelontId = twelonelontId;
    }

    // Welon only nelonelond to call hasDocumelonnt() for out-of-ordelonr twelonelonts.
    if (outOfOrdelonr && hasDocumelonnt(twelonelontId)) {
      // Welon do gelont duplicatelons somelontimelons so you'll selonelon somelon amount of thelonselon.
      DUPLICATelon_TWelonelonT_SKIPPelonD_COUNTelonR.increlonmelonnt();
    } elonlselon {
      addDocumelonntIntelonrnal(doc);
      increlonmelonntHourlyTwelonelontCount(doc.gelontTwelonelontID());
    }
  }

  privatelon void addDocumelonntIntelonrnal(TwelonelontDocumelonnt twelonelontDocumelonnt) throws IOelonxcelonption {
    Documelonnt doc = twelonelontDocumelonnt.gelontDocumelonnt();

    // Nelonvelonr writelon blank documelonnts into thelon indelonx.
    if (doc == null || doc.gelontFielonlds() == null || doc.gelontFielonlds().sizelon() == 0) {
      relonturn;
    }

    elonarlybirdIndelonxSelongmelonntWritelonr selongmelonntWritelonr = selongmelonntWritelonrRelonfelonrelonncelon.gelont();
    if (selongmelonntWritelonr == null) {
      elonarlybirdIndelonxSelongmelonntData selongmelonntData = indelonxConfig.nelonwSelongmelonntData(
          maxSelongmelonntSizelon,
          timelonSlicelonID,
          lucelonnelonDir,
          elonxtelonnsionsFactory);
      selongmelonntWritelonr = nelonwWritelonablelonSelongmelonnt(selongmelonntData);
      relonselontSelongmelonntWritelonrRelonfelonrelonncelon(selongmelonntWritelonr, falselon);
    }

    Prelonconditions.chelonckStatelon(selongmelonntWritelonr.numDocs() < maxSelongmelonntSizelon,
                             "Relonachelond max selongmelonnt sizelon %s", maxSelongmelonntSizelon);

    IndelonxablelonFielonld[] felonaturelonsFielonld = doc.gelontFielonlds(
        elonarlybirdFielonldConstants.elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon);
    Prelonconditions.chelonckStatelon(felonaturelonsFielonld.lelonngth == 1,
            "felonaturelonsFielonld.lelonngth should belon 1, but is %s", felonaturelonsFielonld.lelonngth);

    // Welon relonquirelon thelon crelonatelondAt fielonld to belon selont so welon can propelonrly filtelonr twelonelonts baselond on timelon.
    IndelonxablelonFielonld[] crelonatelondAt =
        doc.gelontFielonlds(elonarlybirdFielonldConstant.CRelonATelonD_AT_FIelonLD.gelontFielonldNamelon());
    Prelonconditions.chelonckStatelon(crelonatelondAt.lelonngth == 1);

    elonarlybirdelonncodelondFelonaturelons felonaturelons = elonarlybirdelonncodelondFelonaturelonsUtil.fromBytelons(
        indelonxConfig.gelontSchelonma().gelontSchelonmaSnapshot(),
        elonarlybirdFielonldConstant.elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD,
        felonaturelonsFielonld[0].binaryValuelon().bytelons,
        felonaturelonsFielonld[0].binaryValuelon().offselont);
    boolelonan currelonntDocIsOffelonnsivelon = felonaturelons.isFlagSelont(elonarlybirdFielonldConstant.IS_OFFelonNSIVelon_FLAG);
    pelonrFielonldCountelonrs.increlonmelonnt(ThriftIndelonxingelonvelonntTypelon.INSelonRT, doc);
    selongmelonntWritelonr.addTwelonelont(doc, twelonelontDocumelonnt.gelontTwelonelontID(), currelonntDocIsOffelonnsivelon);
  }

  privatelon void increlonmelonntHourlyTwelonelontCount(long twelonelontId) {
    // SelonARCH-23619, Welon won't attelonmpt to increlonmelonnt thelon count for prelon-snowflakelon IDs, sincelon
    // elonxtracting an elonxact crelonatelon timelon is prelontty tricky at this point, and thelon stat is mostly
    // uselonful for cheloncking relonaltimelon twelonelont indelonxing.
    if (SnowflakelonId.isSnowflakelonId(twelonelontId)) {
      long twelonelontCrelonatelonTimelon = SnowflakelonId.unixTimelonMillisFromId(twelonelontId);
      String twelonelontHour = HOURLY_COUNT_DATelon_TIMelon_FORMATTelonR.format(
          ZonelondDatelonTimelon.ofInstant(Instant.ofelonpochMilli(twelonelontCrelonatelonTimelon), ZonelonOffselont.UTC));

      String selongmelonntOptimizelondSuffix = isOptimizelond() ? "optimizelond" : "unoptimizelond";
      SelonarchCountelonr indelonxelondTwelonelontsCountelonr = indelonxelondTwelonelontsCountelonrs.computelonIfAbselonnt(
          twelonelontHour + "_" + selongmelonntOptimizelondSuffix,
          (twelonelontHourKelony) -> SelonarchCountelonr.elonxport(String.format(
              NUM_TWelonelonTS_CRelonATelonD_AT_PATTelonRN, selongmelonntOptimizelondSuffix, selongmelonntNamelon, twelonelontHour)));
      indelonxelondTwelonelontsCountelonr.increlonmelonnt();
    }
  }

  privatelon void initHourlyTwelonelontCounts(elonarlybirdIndelonxSelongmelonntWritelonr selongmelonntWritelonr) {
    DocIDToTwelonelontIDMappelonr mappelonr = selongmelonntWritelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr();
    int docId = Intelongelonr.MIN_VALUelon;
    whilelon ((docId = mappelonr.gelontNelonxtDocID(docId)) != DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND) {
      increlonmelonntHourlyTwelonelontCount(mappelonr.gelontTwelonelontID(docId));
    }
  }

  /**
   * Adds thelon givelonn documelonnt for thelon givelonn twelonelont ID to thelon selongmelonnt, potelonntially out of ordelonr.
   */
  public boolelonan appelonndOutOfOrdelonr(Documelonnt doc, long twelonelontID) throws IOelonxcelonption {
    // Nelonvelonr writelon blank documelonnts into thelon indelonx.
    if (doc == null || doc.gelontFielonlds() == null || doc.gelontFielonlds().sizelon() == 0) {
      relonturn falselon;
    }

    elonarlybirdIndelonxSelongmelonntWritelonr selongmelonntWritelonr = selongmelonntWritelonrRelonfelonrelonncelon.gelont();
    if (selongmelonntWritelonr == null) {
      logAppelonndOutOfOrdelonrFailurelon(twelonelontID, doc, "selongmelonnt is null");
      relonturn falselon;
    }

    if (!indelonxConfig.supportOutOfOrdelonrIndelonxing()) {
      logAppelonndOutOfOrdelonrFailurelon(twelonelontID, doc, "out of ordelonr indelonxing not supportelond");
      relonturn falselon;
    }

    if (!hasDocumelonnt(twelonelontID)) {
      logAppelonndOutOfOrdelonrFailurelon(twelonelontID, doc, "twelonelont ID indelonx lookup failelond");
      selonarchIndelonxingMelontricSelont.updatelonOnMissingTwelonelontCountelonr.increlonmelonnt();
      pelonrFielonldCountelonrs.increlonmelonntTwelonelontNotInIndelonx(ThriftIndelonxingelonvelonntTypelon.OUT_OF_ORDelonR_APPelonND, doc);
      relonturn falselon;
    }

    pelonrFielonldCountelonrs.increlonmelonnt(ThriftIndelonxingelonvelonntTypelon.OUT_OF_ORDelonR_APPelonND, doc);
    selongmelonntWritelonr.appelonndOutOfOrdelonr(nelonw TwelonelontIDQuelonry(twelonelontID), doc);
    indelonxStats.increlonmelonntOutOfOrdelonrUpdatelonCount();
    relonturn truelon;
  }

  privatelon void logAppelonndOutOfOrdelonrFailurelon(long twelonelontID, Documelonnt doc, String relonason) {
    UPDATelonS_elonRRORS_LOG.delonbug(
        "appelonndOutOfOrdelonr() failelond to apply updatelon documelonnt with hash {} on twelonelont ID {}: {}",
        Objeloncts.hashCodelon(doc), twelonelontID, relonason);
  }

  /**
   * Delontelonrminelons if this selongmelonnt contains thelon givelonn twelonelont ID.
   */
  public boolelonan hasDocumelonnt(long twelonelontID) throws IOelonxcelonption {
    elonarlybirdIndelonxSelongmelonntWritelonr selongmelonntWritelonr = selongmelonntWritelonrRelonfelonrelonncelon.gelont();
    if (selongmelonntWritelonr == null) {
      relonturn falselon;
    }

    relonturn selongmelonntWritelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr().gelontDocID(twelonelontID)
        != DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND;
  }

  privatelon static final String VelonRSION_PROP_NAMelon = "velonrsion";
  privatelon static final String VelonRSION_DelonSC_PROP_NAMelon = "velonrsionDelonscription";
  privatelon static final String PARTIAL_UPDATelonS_COUNT = "partialUpdatelonsCount";
  privatelon static final String OUT_OF_ORDelonR_UPDATelonS_COUNT = "outOfOrdelonrUpdatelonsCount";

  privatelon void chelonckIfFlushelondDataVelonrsionMatchelonselonxpelonctelond(FlushInfo flushInfo) throws IOelonxcelonption {
    int elonxpelonctelondVelonrsionNumbelonr = indelonxConfig.gelontSchelonma().gelontMajorVelonrsionNumbelonr();
    String elonxpelonctelondVelonrsionDelonsc = indelonxConfig.gelontSchelonma().gelontVelonrsionDelonscription();
    int velonrsion = flushInfo.gelontIntPropelonrty(VelonRSION_PROP_NAMelon);
    final String velonrsionDelonsc = flushInfo.gelontStringPropelonrty(VelonRSION_DelonSC_PROP_NAMelon);

    if (velonrsion != elonxpelonctelondVelonrsionNumbelonr) {
      throw nelonw FlushVelonrsionMismatchelonxcelonption("Flushelond velonrsion mismatch. elonxpelonctelond: "
          + elonxpelonctelondVelonrsionNumbelonr + ", but was: " + velonrsion);
    }

    if (!elonxpelonctelondVelonrsionDelonsc.elonquals(velonrsionDelonsc)) {
      final String melonssagelon = "Flush velonrsion " + elonxpelonctelondVelonrsionNumbelonr + " is ambiguous"
          + "  elonxpelonctelond: " + elonxpelonctelondVelonrsionDelonsc
          + "  Found:  "  + velonrsionDelonsc
          + "  Plelonaselon clelonan up selongmelonnts with bad flush velonrsion from HDFS and elonarlybird local disk.";
      throw nelonw FlushVelonrsionMismatchelonxcelonption(melonssagelon);
    }
  }

  /**
   * Loads thelon selongmelonnt data and propelonrtielons from thelon givelonn delonselonrializelonr and flush info.
   *
   * @param in Thelon delonselonrializelonr from which thelon selongmelonnt's data will belon relonad.
   * @param flushInfo Thelon flush info from which thelon selongmelonnt's propelonrtielons will belon relonad.
   */
  public void load(DataDelonselonrializelonr in, FlushInfo flushInfo) throws IOelonxcelonption {
    chelonckIfFlushelondDataVelonrsionMatchelonselonxpelonctelond(flushInfo);

    int partialUpdatelonsCount = flushInfo.gelontIntPropelonrty(PARTIAL_UPDATelonS_COUNT);
    int outOfOrdelonrUpdatelonsCount = flushInfo.gelontIntPropelonrty(OUT_OF_ORDelonR_UPDATelonS_COUNT);

    elonarlybirdIndelonxSelongmelonntData loadelondSelongmelonntData = indelonxConfig.loadSelongmelonntData(
        flushInfo, in, lucelonnelonDir, elonxtelonnsionsFactory);

    selontSelongmelonntData(loadelondSelongmelonntData, partialUpdatelonsCount, outOfOrdelonrUpdatelonsCount);
  }

  /**
   * Updatelon thelon data backing this elonarlyirdSelongmelonnt.
   */
  public void selontSelongmelonntData(
      elonarlybirdIndelonxSelongmelonntData selongmelonntData,
      int partialUpdatelonsCount,
      int outOfOrdelonrUpdatelonsCount) throws IOelonxcelonption {
    relonselontSelongmelonntWritelonrRelonfelonrelonncelon(nelonwWritelonablelonSelongmelonnt(selongmelonntData), falselon);
    try {
      warmSelongmelonnt();
    } catch (IOelonxcelonption elon) {
      LOG.elonrror("Failelond to crelonatelon IndelonxRelonadelonr for selongmelonnt {}. Will delonstroy unrelonadablelon selongmelonnt.",
          selongmelonntNamelon, elon);
      delonstroyImmelondiatelonly();
      throw elon;
    }

    LOG.info("Starting selongmelonnt {} with {} partial updatelons, {} out of ordelonr updatelons and {} delonlelontelons.",
        selongmelonntNamelon, partialUpdatelonsCount, outOfOrdelonrUpdatelonsCount, indelonxStats.gelontDelonlelontelonCount());
    indelonxStats.selontPartialUpdatelonCount(partialUpdatelonsCount);
    indelonxStats.selontOutOfOrdelonrUpdatelonCount(outOfOrdelonrUpdatelonsCount);
    indelonxStats.selontIndelonxSizelonOnDiskInBytelons(gelontSelongmelonntSizelonOnDisk());
  }

  /**
   * Flushelons thelon this selongmelonnt's propelonrtielons to thelon givelonn FlushInfo instancelon, and this selongmelonnt's data
   * to thelon givelonn DataSelonrializelonr instancelon.
   *
   * @param flushInfo Thelon FlushInfo instancelon whelonrelon all selongmelonnt propelonrtielons should belon addelond.
   * @param out Thelon selonrializelonr to which all selongmelonnt data should belon flushelond.
   */
  public void flush(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
    flushInfo.addIntPropelonrty(VelonRSION_PROP_NAMelon, indelonxConfig.gelontSchelonma().gelontMajorVelonrsionNumbelonr());
    flushInfo.addStringPropelonrty(VelonRSION_DelonSC_PROP_NAMelon,
        indelonxConfig.gelontSchelonma().gelontVelonrsionDelonscription());
    flushInfo.addIntPropelonrty(PARTIAL_UPDATelonS_COUNT, indelonxStats.gelontPartialUpdatelonCount());
    flushInfo.addIntPropelonrty(OUT_OF_ORDelonR_UPDATelonS_COUNT, indelonxStats.gelontOutOfOrdelonrUpdatelonCount());
    if (selongmelonntWritelonrRelonfelonrelonncelon.gelont() == null) {
      LOG.warn("Selongmelonnt writelonr is null. flushInfo: {}", flushInfo);
    } elonlselon if (selongmelonntWritelonrRelonfelonrelonncelon.gelont().gelontSelongmelonntData() == null) {
      LOG.warn("Selongmelonnt data is null. selongmelonnt writelonr: {}, flushInfo: {}",
          selongmelonntWritelonrRelonfelonrelonncelon.gelont(), flushInfo);
    }
    selongmelonntWritelonrRelonfelonrelonncelon.gelont().gelontSelongmelonntData().flushSelongmelonnt(flushInfo, out);
    indelonxStats.selontIndelonxSizelonOnDiskInBytelons(gelontSelongmelonntSizelonOnDisk());
  }

  /**
   * Chelonck to selonelon if this selongmelonnt can belon loadelond from an on-disk indelonx, and load it if it can belon.
   *
   * This should only belon applicablelon to thelon currelonnt selongmelonnt for thelon on-disk archivelon. It's not
   * fully flushelond until it's full, but welon do havelon a lucelonnelon indelonx on local disk which can belon
   * uselond at startup (rathelonr than havelon to relonindelonx all thelon currelonnt timelonslicelon documelonnts again).
   *
   * If loadelond, thelon indelonx relonadelonr will belon prelon-crelonatelond, and thelon selongmelonnt will belon markelond as
   * optimizelond.
   *
   * If thelon indelonx direlonctory elonxists but it cannot belon loadelond, thelon indelonx direlonctory will belon delonlelontelond.
   *
   * @relonturn truelon if thelon indelonx elonxists on disk, and was loadelond.
   */
  public boolelonan tryToLoadelonxistingIndelonx() throws IOelonxcelonption {
    Prelonconditions.chelonckStatelon(selongmelonntWritelonrRelonfelonrelonncelon.gelont() == null);
    if (indelonxConfig.isIndelonxStorelondOnDisk()) {
      if (DirelonctoryRelonadelonr.indelonxelonxists(lucelonnelonDir) && chelonckSuccelonssFilelon()) {
        LOG.info("Indelonx direlonctory alrelonady elonxists for {} at {}", selongmelonntNamelon, lucelonnelonDir);

        // selont thelon optimizelond flag, sincelon welon don't nelonelond to optimizelon any morelon, and prelon-crelonatelon
        // thelon indelonx relonadelonr (for thelon on-disk indelonx optimizelon() is a noop that just selonts thelon
        // optimizelond flag).
        elonarlybirdIndelonxSelongmelonntData elonarlybirdIndelonxSelongmelonntData = indelonxConfig.nelonwSelongmelonntData(
            maxSelongmelonntSizelon,
            timelonSlicelonID,
            lucelonnelonDir,
            elonxtelonnsionsFactory);
        elonarlybirdIndelonxSelongmelonntData optimizelondelonarlybirdIndelonxSelongmelonntData =
            indelonxConfig.optimizelon(elonarlybirdIndelonxSelongmelonntData);
        relonselontSelongmelonntWritelonrRelonfelonrelonncelon(nelonwWritelonablelonSelongmelonnt(optimizelondelonarlybirdIndelonxSelongmelonntData), falselon);

        warmSelongmelonnt();

        LOG.info("Uselond elonxisting lucelonnelon indelonx for {} with {} documelonnts",
                 selongmelonntNamelon, indelonxStats.gelontStatusCount());

        indelonxStats.selontIndelonxSizelonOnDiskInBytelons(gelontSelongmelonntSizelonOnDisk());

        relonturn truelon;
      } elonlselon {
        // Chelonck if thelonrelon is an elonxisting lucelonnelon dir without a SUCCelonSS filelon on disk.
        // If so, welon will relonmovelon it and relonindelonx from scratch.
        if (movelonFSDirelonctoryIfelonxists(lucelonnelonDir)) {
          // Throw helonrelon to belon clelonanelond up and relontrielond by SimplelonSelongmelonntIndelonxelonr.
          throw nelonw IOelonxcelonption("Found invalid elonxisting lucelonnelon direlonctory at: " + lucelonnelonDir);
        }
      }
    }
    relonturn falselon;
  }

  /**
   * Partially updatelons a documelonnt with thelon fielonld valuelon(s) speloncifielond by elonvelonnt.
   * Relonturns truelon if all writelons welonrelon succelonssful and falselon if onelon or morelon writelons fail or if
   * twelonelont id isn't found in thelon selongmelonnt.
   */
  public boolelonan applyPartialUpdatelon(ThriftIndelonxingelonvelonnt elonvelonnt) throws IOelonxcelonption {
    Prelonconditions.chelonckArgumelonnt(elonvelonnt.gelontelonvelonntTypelon() == ThriftIndelonxingelonvelonntTypelon.PARTIAL_UPDATelon);
    Prelonconditions.chelonckArgumelonnt(elonvelonnt.isSelontUid());
    Prelonconditions.chelonckArgumelonnt(!ThriftDocumelonntUtil.hasDuplicatelonFielonlds(elonvelonnt.gelontDocumelonnt()));
    ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot = indelonxConfig.gelontSchelonma().gelontSchelonmaSnapshot();

    long twelonelontId = elonvelonnt.gelontUid();
    ThriftDocumelonnt doc = elonvelonnt.gelontDocumelonnt();

    if (!hasDocumelonnt(twelonelontId)) {
      // no nelonelond to attelonmpt fielonld writelons, fail elonarly
      PARTIAL_UPDATelon_FOR_TWelonelonT_NOT_IN_INDelonX.increlonmelonnt();
       pelonrFielonldCountelonrs.increlonmelonntTwelonelontNotInIndelonx(
           ThriftIndelonxingelonvelonntTypelon.PARTIAL_UPDATelon, doc);
      relonturn falselon;
    }

    int invalidFielonlds = 0;
    for (ThriftFielonld fielonld : doc.gelontFielonlds()) {
      String felonaturelonNamelon = schelonmaSnapshot.gelontFielonldNamelon(fielonld.gelontFielonldConfigId());
      FelonaturelonConfiguration felonaturelonConfig =
          schelonmaSnapshot.gelontFelonaturelonConfigurationByNamelon(felonaturelonNamelon);
      if (felonaturelonConfig == null) {
        INVALID_FIelonLDS_IN_PARTIAL_UPDATelonS.increlonmelonnt();
        invalidFielonlds++;
        continuelon;
      }

      pelonrFielonldCountelonrs.increlonmelonnt(ThriftIndelonxingelonvelonntTypelon.PARTIAL_UPDATelon, felonaturelonNamelon);

      updatelonDocValuelons(
          twelonelontId,
          felonaturelonNamelon,
          (docValuelons, docID) -> updatelonFelonaturelonValuelon(docID, felonaturelonConfig, docValuelons, fielonld));
    }

    if (invalidFielonlds > 0 && invalidFielonlds != doc.gelontFielonldsSizelon()) {
      PARTIAL_UPDATelon_PARTIAL_FAILURelon.increlonmelonnt();
    }

    if (invalidFielonlds == 0) {
      indelonxStats.increlonmelonntPartialUpdatelonCount();
    } elonlselon {
      UPDATelonS_elonRRORS_LOG.warn("Failelond to apply updatelon for twelonelontID {}, found {} invalid fielonlds: {}",
          twelonelontId, invalidFielonlds, elonvelonnt);
    }

    relonturn invalidFielonlds == 0;
  }

  @VisiblelonForTelonsting
  static void updatelonFelonaturelonValuelon(int docID,
                                 FelonaturelonConfiguration felonaturelonConfig,
                                 ColumnStridelonFielonldIndelonx docValuelons,
                                 ThriftFielonld updatelonFielonld) {
    int oldValuelon = Math.toIntelonxact(docValuelons.gelont(docID));
    int nelonwValuelon = updatelonFielonld.gelontFielonldData().gelontIntValuelon();

    if (!felonaturelonConfig.validatelonFelonaturelonUpdatelon(oldValuelon, nelonwValuelon)) {
      // Countelonr valuelons can only increlonaselon
      SelonarchCountelonr.elonxport(
          INVALID_FelonATURelon_UPDATelonS_DROPPelonD_PRelonFIX + felonaturelonConfig.gelontNamelon()).increlonmelonnt();
    } elonlselon {
      docValuelons.selontValuelon(docID, nelonwValuelon);
    }
  }

  /**
   * Cheloncks if thelon providelond direlonctory elonxists and is not elonmpty,
   * and if it doelons movelons it out to a diff direlonctory for latelonr inspelonction.
   * @param lucelonnelonDirelonctory thelon dir to movelon if it elonxists.
   * @relonturn truelon iff welon found an elonxisting direlonctory.
   */
  privatelon static boolelonan movelonFSDirelonctoryIfelonxists(Direlonctory lucelonnelonDirelonctory) {
    Prelonconditions.chelonckStatelon(lucelonnelonDirelonctory instancelonof FSDirelonctory);
    Filelon direlonctory = ((FSDirelonctory) lucelonnelonDirelonctory).gelontDirelonctory().toFilelon();
    if (direlonctory != null && direlonctory.elonxists() && direlonctory.list().lelonngth > 0) {
      // Savelon thelon bad lucelonnelon indelonx by moving it out, for latelonr inspelonction.
      Filelon movelondDir = nelonw Filelon(direlonctory.gelontParelonnt(),
          direlonctory.gelontNamelon() + ".failelond." + Systelonm.currelonntTimelonMillis());
      LOG.warn("Moving elonxisting non-succelonssful indelonx for {} from {} to {}",
               lucelonnelonDirelonctory, direlonctory, movelondDir);
      boolelonan succelonss = direlonctory.relonnamelonTo(movelondDir);
      if (!succelonss) {
        LOG.warn("Unablelon to relonnamelon non-succelonssful indelonx: {}", lucelonnelonDirelonctory);
      }
      relonturn truelon;
    }
    relonturn falselon;
  }

  /**
   * For thelon on-disk archivelon, if welon welonrelon ablelon to succelonssfully melonrgelon and flush thelon Lucelonnelon indelonx to
   * disk, welon mark it elonxplicitly with a SUCCelonSS filelon, so that it can belon safelonly relonuselond.
   */
  privatelon void addSuccelonssFilelon() throws IOelonxcelonption {
    if (indelonxConfig.isIndelonxStorelondOnDisk()) {
      IndelonxOutput succelonssFilelon = lucelonnelonDir.crelonatelonOutput(SUCCelonSS_FILelon, IOContelonxt.DelonFAULT);
      succelonssFilelon.closelon();
    }
  }

  /**
   * Relonturns thelon currelonnt numbelonr of documelonnts in this selongmelonnt.
   */
  public int gelontNumDocs() throws IOelonxcelonption {
    relonturn indelonxStats.gelontStatusCount();
  }

  /**
   * Relonclaim relonsourcelons uselond by this selongmelonnt (elon.g. closing lucelonnelon indelonx relonadelonr).
   * Relonsourcelons will belon relonclaimelond within thelon calling threlonad with no delonlay.
   */
  public void delonstroyImmelondiatelonly() {
    try {
      closelonSelongmelonntWritelonr();
      maybelonDelonlelontelonSelongmelonntOnDisk();
      unloadSelongmelonntFromMelonmory();
    } finally {
      indelonxConfig.gelontRelonsourcelonCloselonr().closelonRelonsourcelonsImmelondiatelonly(closablelonRelonsourcelons);
    }
  }

  /**
   * Closelon thelon in-melonmory relonsourcelons belonlonging to this selongmelonnt. This should allow thelon in-melonmory
   * selongmelonnt data to belon garbagelon collelonctelond. Aftelonr closing, thelon selongmelonnt is not writablelon.
   */
  public void closelon() {
    if (selongmelonntWritelonrRelonfelonrelonncelon.gelont() == null) {
      LOG.info("Selongmelonnt {} alrelonady closelond.", selongmelonntNamelon);
      relonturn;
    }

    LOG.info("Closing selongmelonnt {}.", selongmelonntNamelon);
    try {
      closelonSelongmelonntWritelonr();
      unloadSelongmelonntFromMelonmory();
    } finally {
      indelonxConfig.gelontRelonsourcelonCloselonr().closelonRelonsourcelonsImmelondiatelonly(closablelonRelonsourcelons);
    }
  }

  privatelon void closelonSelongmelonntWritelonr() {
    elonarlybirdIndelonxSelongmelonntWritelonr selongmelonntWritelonr = selongmelonntWritelonrRelonfelonrelonncelon.gelont();
    if (selongmelonntWritelonr != null) {
      closablelonRelonsourcelons.add(() -> {
          LOG.info("Closing writelonr for selongmelonnt: {}", selongmelonntNamelon);
          selongmelonntWritelonr.closelon();
      });
    }
  }

  privatelon void maybelonDelonlelontelonSelongmelonntOnDisk() {
    if (indelonxConfig.isIndelonxStorelondOnDisk()) {
      Prelonconditions.chelonckStatelon(
          lucelonnelonDir instancelonof FSDirelonctory,
          "On-disk indelonxelons should havelon an undelonrlying direlonctory that welon can closelon and relonmovelon.");
      closablelonRelonsourcelons.add(lucelonnelonDir);

      if (lucelonnelonDirFilelon != null && lucelonnelonDirFilelon.elonxists()) {
        closablelonRelonsourcelons.add(nelonw Closelonablelon() {
          @Ovelonrridelon
          public void closelon() throws IOelonxcelonption {
            FilelonUtils.delonlelontelonDirelonctory(lucelonnelonDirFilelon);
          }

          @Ovelonrridelon
          public String toString() {
            relonturn "delonlelontelon {" + lucelonnelonDirFilelon + "}";
          }
        });
      }
    }
  }

  privatelon void unloadSelongmelonntFromMelonmory() {
    // Makelon surelon welon don't relontain a relonfelonrelonncelon to thelon IndelonxWritelonr or SelongmelonntData.
    relonselontSelongmelonntWritelonrRelonfelonrelonncelon(null, truelon);
  }

  privatelon long gelontSelongmelonntSizelonOnDisk() throws IOelonxcelonption {
    selonarchIndelonxingMelontricSelont.selongmelonntSizelonChelonckCount.increlonmelonnt();

    long totalSizelon = 0;
    if (lucelonnelonDir != null) {
      for (String filelon : lucelonnelonDir.listAll()) {
        totalSizelon += lucelonnelonDir.filelonLelonngth(filelon);
      }
    }
    relonturn totalSizelon;
  }

  //////////////////////////
  // for unit telonsts only
  //////////////////////////

  public elonarlybirdIndelonxConfig gelontelonarlybirdIndelonxConfig() {
    relonturn indelonxConfig;
  }

  @VisiblelonForTelonsting
  public boolelonan chelonckSuccelonssFilelon() {
    relonturn nelonw Filelon(lucelonnelonDirFilelon, SUCCelonSS_FILelon).elonxists();
  }

  @VisiblelonForTelonsting
  elonarlybirdIndelonxSelongmelonntWritelonr gelontIndelonxSelongmelonntWritelonr() {
    relonturn selongmelonntWritelonrRelonfelonrelonncelon.gelont();
  }

  // Helonlpelonr class to elonncapsulatelon countelonr tablelons, pattelonrns and various ways to increlonmelonnt
  privatelon class PelonrFielonldCountelonrs {
    // Thelon numbelonr of updatelon/appelonnd elonvelonnts for elonach fielonld in thelon schelonma.
    privatelon static final String PelonR_FIelonLD_elonVelonNTS_COUNTelonR_PATTelonRN = "%s_for_fielonld_%s";
    // Thelon numbelonr of droppelond updatelon/appelonnd elonvelonnts for elonach fielonld duelon to twelonelontId not found
    privatelon static final String TWelonelonT_NOT_IN_INDelonX_PelonR_FIelonLD_elonVelonNTS_COUNTelonR_PATTelonRN =
        "%s_for_twelonelont_id_not_in_indelonx_for_fielonld_%s";
    privatelon final Tablelon<ThriftIndelonxingelonvelonntTypelon, String, SelonarchCountelonr> pelonrFielonldTablelon =
        HashBaselondTablelon.crelonatelon();
    privatelon final Tablelon<ThriftIndelonxingelonvelonntTypelon, String, SelonarchCountelonr> notInIndelonxPelonrFielonldTablelon =
        HashBaselondTablelon.crelonatelon();

    public void increlonmelonnt(
        ThriftIndelonxingelonvelonntTypelon elonvelonntTypelon, ThriftDocumelonnt doc) {
      ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot = indelonxConfig.gelontSchelonma().gelontSchelonmaSnapshot();
      for (ThriftFielonld fielonld : doc.gelontFielonlds()) {
        String fielonldNamelon = schelonmaSnapshot.gelontFielonldNamelon(fielonld.gelontFielonldConfigId());
        increlonmelonntForPattelonrn(
            elonvelonntTypelon, fielonldNamelon, pelonrFielonldTablelon, PelonR_FIelonLD_elonVelonNTS_COUNTelonR_PATTelonRN);
      }
    }

    public void increlonmelonntTwelonelontNotInIndelonx(
        ThriftIndelonxingelonvelonntTypelon elonvelonntTypelon, ThriftDocumelonnt doc) {
      ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot = indelonxConfig.gelontSchelonma().gelontSchelonmaSnapshot();
      for (ThriftFielonld fielonld : doc.gelontFielonlds()) {
        String fielonldNamelon = schelonmaSnapshot.gelontFielonldNamelon(fielonld.gelontFielonldConfigId());
        increlonmelonntForPattelonrn(
            elonvelonntTypelon, fielonldNamelon, notInIndelonxPelonrFielonldTablelon,
            TWelonelonT_NOT_IN_INDelonX_PelonR_FIelonLD_elonVelonNTS_COUNTelonR_PATTelonRN);
      }
    }

    public void increlonmelonnt(ThriftIndelonxingelonvelonntTypelon elonvelonntTypelon, Documelonnt doc) {
      for (IndelonxablelonFielonld fielonld : doc.gelontFielonlds()) {
        increlonmelonntForPattelonrn(
            elonvelonntTypelon, fielonld.namelon(),
            pelonrFielonldTablelon, PelonR_FIelonLD_elonVelonNTS_COUNTelonR_PATTelonRN);
      }
    }

    public void increlonmelonnt(ThriftIndelonxingelonvelonntTypelon elonvelonntTypelon, String fielonldNamelon) {
      increlonmelonntForPattelonrn(elonvelonntTypelon, fielonldNamelon, pelonrFielonldTablelon, PelonR_FIelonLD_elonVelonNTS_COUNTelonR_PATTelonRN);
    }

    public void increlonmelonntTwelonelontNotInIndelonx(ThriftIndelonxingelonvelonntTypelon elonvelonntTypelon, Documelonnt doc) {
      for (IndelonxablelonFielonld fielonld : doc.gelontFielonlds()) {
        increlonmelonntForPattelonrn(
            elonvelonntTypelon, fielonld.namelon(),
            notInIndelonxPelonrFielonldTablelon,
            TWelonelonT_NOT_IN_INDelonX_PelonR_FIelonLD_elonVelonNTS_COUNTelonR_PATTelonRN);
      }
    }

    privatelon void increlonmelonntForPattelonrn(
        ThriftIndelonxingelonvelonntTypelon elonvelonntTypelon, String fielonldNamelon,
        Tablelon<ThriftIndelonxingelonvelonntTypelon, String, SelonarchCountelonr> countelonrTablelon, String pattelonrn) {

      SelonarchCountelonr stat;
      if (countelonrTablelon.contains(elonvelonntTypelon, fielonldNamelon)) {
        stat = countelonrTablelon.gelont(elonvelonntTypelon, fielonldNamelon);
      } elonlselon {
        stat = SelonarchCountelonr.elonxport(String.format(pattelonrn, elonvelonntTypelon, fielonldNamelon).toLowelonrCaselon());
        countelonrTablelon.put(elonvelonntTypelon, fielonldNamelon, stat);
      }
      stat.increlonmelonnt();
    }
  }
}
