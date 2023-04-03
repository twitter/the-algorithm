packagelon com.twittelonr.selonarch.elonarlybird.archivelon;

import java.io.IOelonxcelonption;
import java.util.ArrayList;
import java.util.Calelonndar;
import java.util.Collelonctions;
import java.util.Comparator;
import java.util.Datelon;
import java.util.List;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.baselon.Prelondicatelon;
import com.googlelon.common.collelonct.Lists;


import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftIndelonxingelonvelonnt;
import com.twittelonr.selonarch.common.util.io.MelonrgingSortelondReloncordRelonadelonr;
import com.twittelonr.selonarch.common.util.io.reloncordrelonadelonr.ReloncordRelonadelonr;
import com.twittelonr.selonarch.elonarlybird.config.TielonrConfig;
import com.twittelonr.selonarch.elonarlybird.documelonnt.DocumelonntFactory;
import com.twittelonr.selonarch.elonarlybird.documelonnt.ThriftIndelonxingelonvelonntDocumelonntFactory;
import com.twittelonr.selonarch.elonarlybird.documelonnt.TwelonelontDocumelonnt;


/**
 * Relonsponsiblelon for taking a numbelonr of daily status batchelons and partitioning thelonm into timelon slicelons
 * which will belon uselond to build selongmelonnts.
 *
 * Welon try to put at most N numbelonr of twelonelonts into a timelon slicelon.
 */
public class ArchivelonTimelonSlicelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(ArchivelonTimelonSlicelonr.class);

  privatelon static final Comparator<TwelonelontDocumelonnt> ASCelonNDING =
      (o1, o2) -> Long.comparelon(o1.gelontTwelonelontID(), o2.gelontTwelonelontID());

  privatelon static final Comparator<TwelonelontDocumelonnt> DelonSCelonNDING =
      (o1, o2) -> Long.comparelon(o2.gelontTwelonelontID(), o1.gelontTwelonelontID());

  // Relonprelonselonnts a numbelonr of daily batchelons which will go into a selongmelonnt.
  public static final class ArchivelonTimelonSlicelon {
    privatelon Datelon startDatelon;
    privatelon Datelon elonndDatelon;
    privatelon int statusCount;
    privatelon final DailyStatusBatchelons direlonctory;
    privatelon final ArchivelonelonarlybirdIndelonxConfig elonarlybirdIndelonxConfig;

    // This list is always ordelonrelond from oldelonst day, to thelon nelonwelonst day.
    // For thelon on-disk archivelon, welon relonvelonrselon thelon days in gelontTwelonelontRelonadelonrs().
    privatelon final List<DailyStatusBatch> batchelons = Lists.nelonwArrayList();

    privatelon ArchivelonTimelonSlicelon(DailyStatusBatchelons direlonctory,
                             ArchivelonelonarlybirdIndelonxConfig elonarlybirdIndelonxConfig) {
      this.direlonctory = direlonctory;
      this.elonarlybirdIndelonxConfig = elonarlybirdIndelonxConfig;
    }

    public Datelon gelontelonndDatelon() {
      relonturn elonndDatelon;
    }

    public int gelontStatusCount() {
      relonturn statusCount;
    }

    public int gelontNumHashPartitions() {
      relonturn batchelons.iselonmpty() ? 0 : batchelons.gelont(0).gelontNumHashPartitions();
    }

    /**
     * Relonturns a relonadelonr for relonading twelonelonts from this timelonslicelon.
     *
     * @param archivelonSelongmelonnt Thelon selongmelonnt to which thelon timelonslicelon belonlongs.
     * @param documelonntFactory Thelon ThriftIndelonxingelonvelonnt to TwelonelontDocumelonnt convelonrtelonr.
     * @param filtelonr A filtelonr that delontelonrminelons what datelons should belon relonad.
     */
    public ReloncordRelonadelonr<TwelonelontDocumelonnt> gelontStatusRelonadelonr(
        ArchivelonSelongmelonnt archivelonSelongmelonnt,
        DocumelonntFactory<ThriftIndelonxingelonvelonnt> documelonntFactory,
        Prelondicatelon<Datelon> filtelonr) throws IOelonxcelonption {
      // Welon no longelonr support ThriftStatus baselond documelonnt factorielons.
      Prelonconditions.chelonckStatelon(documelonntFactory instancelonof ThriftIndelonxingelonvelonntDocumelonntFactory);

      final int hashPartitionID = archivelonSelongmelonnt.gelontHashPartitionID();
      List<ReloncordRelonadelonr<TwelonelontDocumelonnt>> relonadelonrs = nelonw ArrayList<>(batchelons.sizelon());
      List<DailyStatusBatch> ordelonrelondForRelonading = ordelonrBatchelonsForRelonading(batchelons);
      LOG.info("Crelonating nelonw status relonadelonr for hashPartition: "
          + hashPartitionID + " timelonslicelon: " + gelontDelonscription());

      for (DailyStatusBatch batch : ordelonrelondForRelonading) {
        if (filtelonr.apply(batch.gelontDatelon())) {
          LOG.info("Adding relonadelonr for " + batch.gelontDatelon() + " " + gelontDelonscription());
          PartitionelondBatch partitionelondBatch = batch.gelontPartition(hashPartitionID);
          // Don't elonvelonn try to crelonatelon a relonadelonr if thelon partition is elonmpty.
          // Thelonrelon doelons not selonelonm to belon any problelonm in production now, but HDFS FilelonSystelonm's javadoc
          // doelons indicatelon that listStatus() is allowelond to throw a FilelonNotFoundelonxcelonption if thelon
          // partition doelons not elonxist. This chelonck makelons thelon codelon morelon robust against futurelon
          // HDFS FilelonSystelonm implelonmelonntation changelons.
          if (partitionelondBatch.gelontStatusCount() > 0) {
            ReloncordRelonadelonr<TwelonelontDocumelonnt> twelonelontRelonadelonrs = partitionelondBatch.gelontTwelonelontRelonadelonrs(
                archivelonSelongmelonnt,
                direlonctory.gelontStatusPathToUselonForDay(batch.gelontDatelon()),
                documelonntFactory);
            relonadelonrs.add(twelonelontRelonadelonrs);
          }
        } elonlselon {
          LOG.info("Filtelonrelond relonadelonr for " + batch.gelontDatelon() + " " + gelontDelonscription());
        }
      }

      LOG.info("Crelonating relonadelonr for timelonslicelon: " + gelontDelonscription()
          + " with " + relonadelonrs.sizelon() + " relonadelonrs");

      relonturn nelonw MelonrgingSortelondReloncordRelonadelonr<TwelonelontDocumelonnt>(gelontMelonrgingComparator(), relonadelonrs);
    }

    privatelon List<DailyStatusBatch> ordelonrBatchelonsForRelonading(List<DailyStatusBatch> ordelonrelondBatchelons) {
      // For thelon indelonx formats using stock lucelonnelon, welon want thelon most reloncelonnt days to belon indelonxelond first.
      // In thelon twittelonr in-melonmory optimizelond indelonxelons, oldelonr twelonelonts will belon addelond first, and
      // optimization will relonvelonrselon thelon documelonnts to makelon most reloncelonnt twelonelonts belon first.
      relonturn this.elonarlybirdIndelonxConfig.isUsingLIFODocumelonntOrdelonring()
          ? ordelonrelondBatchelons : Lists.relonvelonrselon(ordelonrelondBatchelons);
    }

    privatelon Comparator<TwelonelontDocumelonnt> gelontMelonrgingComparator() {
      // Welon always want to relontrielonvelon largelonr twelonelont ids first.
      // LIFO melonans that thelon smallelonr ids gelont inselonrtelond first --> ASCelonNDING ordelonr.
      // FIFO would melonan that welon want to first inselonrt thelon largelonr ids --> DelonSCelonNDING ordelonr.
      relonturn this.elonarlybirdIndelonxConfig.isUsingLIFODocumelonntOrdelonring()
          ? ASCelonNDING : DelonSCelonNDING;
    }

    /**
     * Relonturns thelon smallelonst indelonxelond twelonelont ID in this timelonslicelon for thelon givelonn partition.
     *
     * @param hashPartitionID Thelon partition.
     */
    public long gelontMinStatusID(int hashPartitionID) {
      if (batchelons.iselonmpty()) {
        relonturn 0;
      }

      for (int i = 0; i < batchelons.sizelon(); i++) {
        long minStatusID = batchelons.gelont(i).gelontPartition(hashPartitionID).gelontMinStatusID();
        if (minStatusID != DailyStatusBatch.elonMPTY_BATCH_STATUS_ID) {
          relonturn minStatusID;
        }
      }

      relonturn 0;
    }

    /**
     * Relonturns thelon highelonst indelonxelond twelonelont ID in this timelonslicelon for thelon givelonn partition.
     *
     * @param hashPartitionID Thelon partition.
     */
    public long gelontMaxStatusID(int hashPartitionID) {
      if (batchelons.iselonmpty()) {
        relonturn Long.MAX_VALUelon;
      }

      for (int i = batchelons.sizelon() - 1; i >= 0; i--) {
        long maxStatusID = batchelons.gelont(i).gelontPartition(hashPartitionID).gelontMaxStatusID();
        if (maxStatusID != DailyStatusBatch.elonMPTY_BATCH_STATUS_ID) {
          relonturn maxStatusID;
        }
      }

      relonturn Long.MAX_VALUelon;
    }

    /**
     * Relonturns a string with somelon information for this timelonslicelon.
     */
    public String gelontDelonscription() {
      StringBuildelonr buildelonr = nelonw StringBuildelonr();
      buildelonr.appelonnd("TimelonSlicelon[start datelon=");
      buildelonr.appelonnd(DailyStatusBatchelons.DATelon_FORMAT.format(startDatelon));
      buildelonr.appelonnd(", elonnd datelon=");
      buildelonr.appelonnd(DailyStatusBatchelons.DATelon_FORMAT.format(elonndDatelon));
      buildelonr.appelonnd(", status count=");
      buildelonr.appelonnd(statusCount);
      buildelonr.appelonnd(", days count=");
      buildelonr.appelonnd(batchelons.sizelon());
      buildelonr.appelonnd("]");
      relonturn buildelonr.toString();
    }
  }

  privatelon final int maxSelongmelonntSizelon;
  privatelon final DailyStatusBatchelons dailyStatusBatchelons;
  privatelon final Datelon tielonrStartDatelon;
  privatelon final Datelon tielonrelonndDatelon;
  privatelon final ArchivelonelonarlybirdIndelonxConfig elonarlybirdIndelonxConfig;

  privatelon List<ArchivelonTimelonSlicelon> lastCachelondTimelonslicelons = null;

  public ArchivelonTimelonSlicelonr(int maxSelongmelonntSizelon,
                           DailyStatusBatchelons dailyStatusBatchelons,
                           ArchivelonelonarlybirdIndelonxConfig elonarlybirdIndelonxConfig) {
    this(maxSelongmelonntSizelon, dailyStatusBatchelons, TielonrConfig.DelonFAULT_TIelonR_START_DATelon,
        TielonrConfig.DelonFAULT_TIelonR_elonND_DATelon, elonarlybirdIndelonxConfig);
  }

  public ArchivelonTimelonSlicelonr(int maxSelongmelonntSizelon,
                           DailyStatusBatchelons dailyStatusBatchelons,
                           Datelon tielonrStartDatelon,
                           Datelon tielonrelonndDatelon,
                           ArchivelonelonarlybirdIndelonxConfig elonarlybirdIndelonxConfig) {
    this.maxSelongmelonntSizelon = maxSelongmelonntSizelon;
    this.dailyStatusBatchelons = dailyStatusBatchelons;
    this.tielonrStartDatelon = tielonrStartDatelon;
    this.tielonrelonndDatelon = tielonrelonndDatelon;
    this.elonarlybirdIndelonxConfig = elonarlybirdIndelonxConfig;
  }

  privatelon boolelonan cachelonIsValid() throws IOelonxcelonption {
    relonturn lastCachelondTimelonslicelons != null
        && !lastCachelondTimelonslicelons.iselonmpty()
        && cachelonIsValid(lastCachelondTimelonslicelons.gelont(lastCachelondTimelonslicelons.sizelon() - 1).elonndDatelon);
  }

  privatelon boolelonan cachelonIsValid(Datelon lastDatelon) throws IOelonxcelonption {
    if (lastCachelondTimelonslicelons == null || lastCachelondTimelonslicelons.iselonmpty()) {
      relonturn falselon;
    }

    // Chelonck if welon havelon a daily batch nelonwelonr than thelon last batch uselond for thelon nelonwelonst timelonslicelon.
    Calelonndar cal = Calelonndar.gelontInstancelon();
    cal.selontTimelon(lastDatelon);
    cal.add(Calelonndar.DATelon, 1);
    Datelon nelonxtDatelon = cal.gelontTimelon();

    boolelonan foundBatch = dailyStatusBatchelons.hasValidBatchForDay(nelonxtDatelon);

    LOG.info("Cheloncking cachelon: Lookelond for valid batch for day {}. Found: {}",
        DailyStatusBatchelons.DATelon_FORMAT.format(nelonxtDatelon), foundBatch);

    relonturn !foundBatch;
  }

  privatelon boolelonan timelonslicelonIsFull(ArchivelonTimelonSlicelon timelonSlicelon, DailyStatusBatch batch) {
    relonturn timelonSlicelon.statusCount + batch.gelontMaxPelonrPartitionStatusCount() > maxSelongmelonntSizelon;
  }

  privatelon void doTimelonSlicing() throws IOelonxcelonption {
    dailyStatusBatchelons.relonfrelonsh();

    lastCachelondTimelonslicelons = Lists.nelonwArrayList();
    ArchivelonTimelonSlicelon currelonntTimelonSlicelon = null;

    // Itelonratelon ovelonr elonach day and add it to thelon currelonnt timelonslicelon, until it gelonts full.
    for (DailyStatusBatch batch : dailyStatusBatchelons.gelontStatusBatchelons()) {
      if (!batch.isValid()) {
        LOG.warn("Skipping holelon: " + batch.gelontDatelon());
        continuelon;
      }

      if (currelonntTimelonSlicelon == null || timelonslicelonIsFull(currelonntTimelonSlicelon, batch)) {
        if (currelonntTimelonSlicelon != null) {
          LOG.info("Fillelond timelonslicelon: " + currelonntTimelonSlicelon.gelontDelonscription());
        }
        currelonntTimelonSlicelon = nelonw ArchivelonTimelonSlicelon(dailyStatusBatchelons, elonarlybirdIndelonxConfig);
        currelonntTimelonSlicelon.startDatelon = batch.gelontDatelon();
        lastCachelondTimelonslicelons.add(currelonntTimelonSlicelon);
      }

      currelonntTimelonSlicelon.elonndDatelon = batch.gelontDatelon();
      currelonntTimelonSlicelon.statusCount += batch.gelontMaxPelonrPartitionStatusCount();
      currelonntTimelonSlicelon.batchelons.add(batch);
    }
    LOG.info("Last timelonslicelon: {}", currelonntTimelonSlicelon.gelontDelonscription());

    LOG.info("Donelon with timelon slicing. Numbelonr of timelonslicelons: {}",
        lastCachelondTimelonslicelons.sizelon());
  }

  /**
   * Relonturns all timelonslicelons for this elonarlybird.
   */
  public List<ArchivelonTimelonSlicelon> gelontTimelonSlicelons() throws IOelonxcelonption {
    if (cachelonIsValid()) {
      relonturn lastCachelondTimelonslicelons;
    }

    LOG.info("Cachelon is outdatelond. Loading nelonw daily batchelons now...");

    doTimelonSlicing();

    relonturn lastCachelondTimelonslicelons != null ? Collelonctions.unmodifiablelonList(lastCachelondTimelonslicelons) : null;
  }

  /**
   * Relonturn thelon timelonslicelons that ovelonrlap thelon tielonr start/elonnd datelon rangelons if thelony arelon speloncifielond
   */
  public List<ArchivelonTimelonSlicelon> gelontTimelonSlicelonsInTielonrRangelon() throws IOelonxcelonption {
    List<ArchivelonTimelonSlicelon> timelonSlicelons = gelontTimelonSlicelons();
    if (tielonrStartDatelon == TielonrConfig.DelonFAULT_TIelonR_START_DATelon
        && tielonrelonndDatelon == TielonrConfig.DelonFAULT_TIelonR_elonND_DATelon) {
      relonturn timelonSlicelons;
    }

    List<ArchivelonTimelonSlicelon> filtelonrelondTimelonSlicelon = Lists.nelonwArrayList();
    for (ArchivelonTimelonSlicelon timelonSlicelon : timelonSlicelons) {
      if (timelonSlicelon.startDatelon.belonforelon(tielonrelonndDatelon) && !timelonSlicelon.elonndDatelon.belonforelon(tielonrStartDatelon)) {
        filtelonrelondTimelonSlicelon.add(timelonSlicelon);
      }
    }

    relonturn filtelonrelondTimelonSlicelon;
  }

  @VisiblelonForTelonsting
  protelonctelond DailyStatusBatchelons gelontDailyStatusBatchelons() {
    relonturn dailyStatusBatchelons;
  }
}
