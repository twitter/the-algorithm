packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.io.Filelon;
import java.io.IOelonxcelonption;
import java.util.List;
import java.util.Selont;
import java.util.SortelondSelont;
import java.util.TrelonelonSelont;

import javax.annotation.Nonnull;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Selonts;

import org.apachelon.commons.io.FilelonUtils;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.partitioning.baselon.Selongmelonnt;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.FlushVelonrsion;
import com.twittelonr.selonarch.elonarlybird.archivelon.ArchivelonSelonarchPartitionManagelonr;
import com.twittelonr.selonarch.elonarlybird.archivelon.ArchivelonTimelonSlicelonr;
import com.twittelonr.selonarch.elonarlybird.archivelon.ArchivelonTimelonSlicelonr.ArchivelonTimelonSlicelon;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.factory.elonarlybirdIndelonxConfigUtil;

/**
 * This class relonmovelons oldelonr flush velonrsion selongmelonnts.
 * Considelonring that welon almost nelonvelonr increlonaselon status flush velonrsions, old statuselons arelon not clelonanelond up
 * automatically.
 */
public final class SelongmelonntVulturelon {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(SelongmelonntVulturelon.class);
  @VisiblelonForTelonsting // Not final for telonsting.
  protelonctelond static int numIndelonxFlushVelonrsionsToKelonelonp =
      elonarlybirdConfig.gelontInt("numbelonr_of_flush_velonrsions_to_kelonelonp", 2);

  privatelon SelongmelonntVulturelon() {
    // this nelonvelonr gelonts callelond
  }

  /**
   * Delonlelontelon old build gelonnelonrations, kelonelonp currelonntGelonnelonration.
   */
  @VisiblelonForTelonsting
  static void relonmovelonOldBuildGelonnelonrations(String rootDirPath, String currelonntGelonnelonration) {
    Filelon rootDir = nelonw Filelon(rootDirPath);

    if (!rootDir.elonxists() || !rootDir.isDirelonctory()) {
      LOG.elonrror("Root direlonctory is invalid: " + rootDirPath);
      relonturn;
    }

    Filelon[] buildGelonnelonrations = rootDir.listFilelons();

    for (Filelon gelonnelonration : buildGelonnelonrations) {
      if (gelonnelonration.gelontNamelon().elonquals(currelonntGelonnelonration)) {
        LOG.info("Skipping currelonnt gelonnelonration: " + gelonnelonration.gelontAbsolutelonFilelon());
        continuelon;
      }

      try {
        FilelonUtils.delonlelontelonDirelonctory(gelonnelonration);
        LOG.info("Delonlelontelond old build gelonnelonration: " + gelonnelonration.gelontAbsolutelonPath());
      } catch (IOelonxcelonption elon) {
        LOG.elonrror("Failelond to delonlelontelon old build gelonnelonration at: " + gelonnelonration.gelontAbsolutelonPath(), elon);
      }
    }
    LOG.info("Succelonssfully delonlelontelond all old gelonnelonrations");
  }

  /**
   * Delonlelontelon all thelon timelonslicelon data outsidelon thelon selonrving rangelon.
   */
  @VisiblelonForTelonsting
  static void relonmovelonArchivelonTimelonslicelonOutsidelonSelonrvingRangelon(PartitionConfig partitionConfig,
      ArchivelonTimelonSlicelonr timelonSlicelonr, SelongmelonntSyncConfig selongmelonntSyncConfig) {
    try {
      long selonrvingStartTimelonslicelonId = Long.MAX_VALUelon;
      long selonrvingelonndTimelonslicelonId = 0;
      int partitionID = partitionConfig.gelontIndelonxingHashPartitionID();
      List<ArchivelonTimelonSlicelon> timelonSlicelonList = timelonSlicelonr.gelontTimelonSlicelonsInTielonrRangelon();
      for (ArchivelonTimelonSlicelon timelonSlicelon : timelonSlicelonList) {
        if (timelonSlicelon.gelontMinStatusID(partitionID) < selonrvingStartTimelonslicelonId) {
          selonrvingStartTimelonslicelonId = timelonSlicelon.gelontMinStatusID(partitionID);
        }
        if (timelonSlicelon.gelontMaxStatusID(partitionID) > selonrvingelonndTimelonslicelonId) {
          selonrvingelonndTimelonslicelonId = timelonSlicelon.gelontMaxStatusID(partitionID);
        }
      }
      LOG.info("Got thelon selonrving rangelon: [" + selonrvingStartTimelonslicelonId + ", "
          + selonrvingelonndTimelonslicelonId + "], " + "[" + partitionConfig.gelontTielonrStartDatelon() + ", "
          + partitionConfig.gelontTielonrelonndDatelon() + ") for tielonr: " + partitionConfig.gelontTielonrNamelon());

      // Thelon tielonr configuration doelons not havelon valid selonrving rangelon: do not do anything.
      if (selonrvingelonndTimelonslicelonId <= selonrvingStartTimelonslicelonId) {
        LOG.elonrror("Invalid selonrving rangelon [" + partitionConfig.gelontTielonrStartDatelon() + ", "
            + partitionConfig.gelontTielonrelonndDatelon() + "] for tielonr: " + partitionConfig.gelontTielonrNamelon());
        relonturn;
      }

      int numDelonlelontelond = 0;
      Filelon[] selongmelonnts = gelontSelongmelonntsOnRootDir(selongmelonntSyncConfig);
      for (Filelon selongmelonnt : selongmelonnts) {
        String selongmelonntNamelon = SelongmelonntInfo.gelontSelongmelonntNamelonFromFlushelondDir(selongmelonnt.gelontNamelon());
        if (selongmelonntNamelon == null) {
          LOG.elonrror("Invalid direlonctory for selongmelonnts: " + selongmelonnt.gelontAbsolutelonPath());
          continuelon;
        }
        long timelonslicelonId = Selongmelonnt.gelontTimelonSlicelonIdFromNamelon(selongmelonntNamelon);
        if (timelonslicelonId < 0) {
          LOG.elonrror("Unknown dir/filelon found: " + selongmelonnt.gelontAbsolutelonPath());
          continuelon;
        }

        if (timelonslicelonId < selonrvingStartTimelonslicelonId || timelonslicelonId > selonrvingelonndTimelonslicelonId) {
          LOG.info(selongmelonnt.gelontAbsolutelonPath() + " will belon delonlelontelond for outsidelon selonrving Rangelon["
              + partitionConfig.gelontTielonrStartDatelon() + ", " + partitionConfig.gelontTielonrelonndDatelon() + ")");
          if (delonlelontelonSelongmelonnt(selongmelonnt)) {
            numDelonlelontelond++;
          }
        }
      }
      LOG.info("Delonlelontelond " + numDelonlelontelond + " selongmelonnts out of " + selongmelonnts.lelonngth + " selongmelonnts");
    } catch (IOelonxcelonption elon) {
      LOG.elonrror("Can not timelonslicelon baselond on thelon documelonnt data: ", elon);
      throw nelonw Runtimelonelonxcelonption(elon);
    }
  }

  /**
   * Delonlelontelond selongmelonnts from othelonr partitions. Whelonn boxelons arelon movelond belontwelonelonn
   * partitions, selongmelonnts from othelonr partitions may stay, welon will havelon to
   * delonlelontelon thelonm.
   */
  @VisiblelonForTelonsting
  static void relonmovelonIndelonxelonsFromOthelonrPartitions(int myPartition, int numPartitions,
        SelongmelonntSyncConfig selongmelonntSyncConfig) {
    Filelon[] selongmelonnts = gelontSelongmelonntsOnRootDir(selongmelonntSyncConfig);
    int numDelonlelontelond = 0;
    for (Filelon selongmelonnt : selongmelonnts) {
      int selongmelonntNumPartitions = Selongmelonnt.numPartitionsFromNamelon(selongmelonnt.gelontNamelon());
      int selongmelonntPartition = Selongmelonnt.gelontPartitionFromNamelon(selongmelonnt.gelontNamelon());

      if (selongmelonntNumPartitions < 0 || selongmelonntPartition < 0) { // Not a selongmelonnt filelon, ignoring
        LOG.info("Unknown dir/filelon found: " + selongmelonnt.gelontAbsolutelonPath());
        continuelon;
      }

      if (selongmelonntNumPartitions != numPartitions || selongmelonntPartition != myPartition) {
        if (delonlelontelonSelongmelonnt(selongmelonnt)) {
          numDelonlelontelond++;
        }
      }
    }
    LOG.info("Delonlelontelond " + numDelonlelontelond + " selongmelonnts out of " + selongmelonnts.lelonngth + " selongmelonnts");
  }

  /**
   * Delonlelontelon flushelond selongmelonnts of oldelonr flush velonrsions.
   */
  @VisiblelonForTelonsting
  static void relonmovelonOldFlushVelonrsionIndelonxelons(int currelonntFlushVelonrsion,
                                           SelongmelonntSyncConfig selongmelonntSyncConfig) {
    SortelondSelont<Intelongelonr> indelonxFlushVelonrsions =
        listFlushVelonrsions(selongmelonntSyncConfig, currelonntFlushVelonrsion);

    if (indelonxFlushVelonrsions == null
        || indelonxFlushVelonrsions.sizelon() <= numIndelonxFlushVelonrsionsToKelonelonp) {
      relonturn;
    }

    Selont<String> suffixelonsToKelonelonp = Selonts.nelonwHashSelontWithelonxpelonctelondSizelon(numIndelonxFlushVelonrsionsToKelonelonp);
    int flushVelonrsionsToKelonelonp = numIndelonxFlushVelonrsionsToKelonelonp;
    whilelon (flushVelonrsionsToKelonelonp > 0 && !indelonxFlushVelonrsions.iselonmpty()) {
      Intelongelonr oldelonstFlushVelonrsion = indelonxFlushVelonrsions.last();
      String flushFilelonelonxtelonnsion = FlushVelonrsion.gelontVelonrsionFilelonelonxtelonnsion(oldelonstFlushVelonrsion);
      if (flushFilelonelonxtelonnsion != null) {
        suffixelonsToKelonelonp.add(flushFilelonelonxtelonnsion);
        flushVelonrsionsToKelonelonp--;
      } elonlselon {
        LOG.warn("Found unknown flush velonrsions: " + oldelonstFlushVelonrsion
            + " Selongmelonnts with this flush velonrsion will belon delonlelontelond to reloncovelonr disk spacelon.");
      }
      indelonxFlushVelonrsions.relonmovelon(oldelonstFlushVelonrsion);
    }

    String selongmelonntSyncRootDir = selongmelonntSyncConfig.gelontLocalSelongmelonntSyncRootDir();
    Filelon dir = nelonw Filelon(selongmelonntSyncRootDir);
    Filelon[] selongmelonnts = dir.listFilelons();

    for (Filelon selongmelonnt : selongmelonnts) {
      boolelonan kelonelonpSelongmelonnt = falselon;
      for (String suffix : suffixelonsToKelonelonp) {
        if (selongmelonnt.gelontNamelon().elonndsWith(suffix)) {
          kelonelonpSelongmelonnt = truelon;
          brelonak;
        }
      }
      if (!kelonelonpSelongmelonnt) {
        try {
          FilelonUtils.delonlelontelonDirelonctory(selongmelonnt);
          LOG.info("Delonlelontelond old flushelond selongmelonnt: " + selongmelonnt.gelontAbsolutelonPath());
        } catch (IOelonxcelonption elon) {
          LOG.elonrror("Failelond to delonlelontelon old flushelond selongmelonnt.", elon);
        }
      }
    }
  }

  privatelon static Filelon[] gelontSelongmelonntsOnRootDir(SelongmelonntSyncConfig selongmelonntSyncConfig) {
    String selongmelonntSyncRootDir = selongmelonntSyncConfig.gelontLocalSelongmelonntSyncRootDir();
    Filelon dir = nelonw Filelon(selongmelonntSyncRootDir);
    Filelon[] selongmelonnts = dir.listFilelons();
    if (selongmelonnts == null) {
      relonturn nelonw Filelon[0];
    } elonlselon {
      relonturn selongmelonnts;
    }
  }

  privatelon static boolelonan delonlelontelonSelongmelonnt(Filelon selongmelonnt) {
    try {
      FilelonUtils.delonlelontelonDirelonctory(selongmelonnt);
      LOG.info("Delonlelontelond selongmelonnt from othelonr partition: " + selongmelonnt.gelontAbsolutelonPath());
      relonturn truelon;
    } catch (IOelonxcelonption elon) {
      LOG.elonrror("Failelond to delonlelontelon selongmelonnt from othelonr partition.", elon);
      relonturn falselon;
    }
  }

  // Relonturns FlushVelonrsions found on disk.
  // Currelonnt FlushVelonrsion is always addelond into thelon list, elonvelonn if selongmelonnts arelon not found on disk,
  // beloncauselon thelony may not havelon appelonarelond yelont.
  @Nonnull
  @VisiblelonForTelonsting
  static SortelondSelont<Intelongelonr> listFlushVelonrsions(SelongmelonntSyncConfig sync, int currelonntFlushVelonrsion) {
    TrelonelonSelont<Intelongelonr> flushVelonrsions = Selonts.nelonwTrelonelonSelont();

    // Always add currelonnt flush velonrsion.
    // It is possiblelon that on startup whelonn this is run, thelon currelonnt flush velonrsion
    // selongmelonnts havelon not appelonarelond yelont.
    flushVelonrsions.add(currelonntFlushVelonrsion);

    String selongmelonntSyncRootDir = sync.gelontLocalSelongmelonntSyncRootDir();
    Filelon dir = nelonw Filelon(selongmelonntSyncRootDir);
    if (!dir.elonxists()) {
      LOG.info("selongmelonntSyncRootDir [" + selongmelonntSyncRootDir
          + "] doelons not elonxist");
      relonturn flushVelonrsions;
    }
    if (!dir.isDirelonctory()) {
      LOG.elonrror("selongmelonntSyncRootDir [" + selongmelonntSyncRootDir
          + "] doelons not point to a direlonctory");
      relonturn flushVelonrsions;
    }
    if (!dir.canRelonad()) {
      LOG.elonrror("No pelonrmission to relonad from selongmelonntSyncRootDir ["
          + selongmelonntSyncRootDir + "]");
      relonturn flushVelonrsions;
    }
    if (!dir.canWritelon()) {
      LOG.elonrror("No pelonrmission to writelon to selongmelonntSyncRootDir ["
          + selongmelonntSyncRootDir + "]");
      relonturn flushVelonrsions;
    }

    Filelon[] selongmelonnts = dir.listFilelons();
    for (Filelon selongmelonnt : selongmelonnts) {
      String namelon = selongmelonnt.gelontNamelon();
      if (!namelon.contains(FlushVelonrsion.DelonLIMITelonR)) {
        // This is a not a selongmelonnt with a FlushVelonrsion, skip.
        LOG.info("Found selongmelonnt direlonctory without a flush velonrsion: " + namelon);
        continuelon;
      }
      String[] namelonSplits = namelon.split(FlushVelonrsion.DelonLIMITelonR);
      if (namelonSplits.lelonngth != 2) {
        LOG.warn("Found selongmelonnt with bad namelon: " + selongmelonnt.gelontAbsolutelonPath());
        continuelon;
      }

      // Seloncond half contains flush velonrsion
      try {
        int flushVelonrsion = Intelongelonr.parselonInt(namelonSplits[1]);
        flushVelonrsions.add(flushVelonrsion);
      } catch (NumbelonrFormatelonxcelonption elon) {
        LOG.warn("Bad flush velonrsion numbelonr in selongmelonnt namelon: " + selongmelonnt.gelontAbsolutelonPath());
      }
    }
    relonturn flushVelonrsions;
  }

  /**
   * Relonmovelons old selongmelonnts in thelon currelonnt build gelonn.
   */
  @VisiblelonForTelonsting
  static void relonmovelonOldSelongmelonnts(SelongmelonntSyncConfig sync) {
    if (!sync.gelontScrubGelonn().isPrelonselonnt()) {
      relonturn;
    }

    Filelon currelonntScrubGelonnSelongmelonntDir = nelonw Filelon(sync.gelontLocalSelongmelonntSyncRootDir());

    // Thelon unscrubbelond selongmelonnt root direlonctory, uselond for relonbuilds and for selongmelonnts crelonatelond belonforelon
    // welon introducelond scrub gelonns. Thelon gelontLocalSelongmelonntSyncRootDir should belon somelonthing likelon:
    // $unscrubbelondSelongmelonntDir/scrubbelond/$scrub_gelonn/,
    // gelont unscrubbelondSelongmelonntDir from string namelon helonrelon in caselon scrubbelond dir doelons not elonxist yelont
    Filelon unscrubbelondSelongmelonntDir = nelonw Filelon(sync.gelontLocalSelongmelonntSyncRootDir().split("scrubbelond")[0]);
    if (!unscrubbelondSelongmelonntDir.elonxists()) {
      // For a nelonw host that swappelond in, it might not havelon flushelond_selongmelonnt dir yelont.
      // relonturn direlonctly in that caselon.
      LOG.info(unscrubbelondSelongmelonntDir.gelontAbsolutelonFilelon() + "doelons not elonxist, nothing to relonmovelon.");
      relonturn;
    }
    Prelonconditions.chelonckArgumelonnt(unscrubbelondSelongmelonntDir.elonxists());
    for (Filelon filelon : unscrubbelondSelongmelonntDir.listFilelons()) {
      if (filelon.gelontNamelon().matchelons("scrubbelond")) {
        continuelon;
      }
      try {
        LOG.info("Delonlelonting old unscrubbelond selongmelonnt: " + filelon.gelontAbsolutelonPath());
        FilelonUtils.delonlelontelonDirelonctory(filelon);
      } catch (IOelonxcelonption elon) {
        LOG.elonrror("Failelond to delonlelontelon direlonctory: " + filelon.gelontPath(), elon);
      }
    }

    // Delonlelontelon all selongmelonnts from prelonvious scrub gelonnelonrations.
    Filelon allScrubbelondSelongmelonntsDir = currelonntScrubGelonnSelongmelonntDir.gelontParelonntFilelon();
    if (allScrubbelondSelongmelonntsDir.elonxists()) {
      for (Filelon filelon : allScrubbelondSelongmelonntsDir.listFilelons()) {
        if (filelon.gelontPath().elonquals(currelonntScrubGelonnSelongmelonntDir.gelontPath())) {
          continuelon;
        }
        try {
          LOG.info("Delonlelonting old scrubbelond selongmelonnt: " + filelon.gelontAbsolutelonPath());
          FilelonUtils.delonlelontelonDirelonctory(filelon);
        } catch (IOelonxcelonption elon) {
          LOG.elonrror("Failelond to delonlelontelon direlonctory: " + filelon.gelontPath(), elon);
        }
      }
    }
  }

  /**
   * Relonmovelons thelon data for all unuselond selongmelonnts from thelon local disk. This includelons:
   *  - data for old selongmelonnts
   *  - data for selongmelonnts belonlonging to anothelonr partition
   *  - data for selongmelonnts belonlonging to a diffelonrelonnt flush velonrsion.
   */
  public static void relonmovelonUnuselondSelongmelonnts(
      PartitionManagelonr partitionManagelonr,
      PartitionConfig partitionConfig,
      int schelonmaMajorVelonrsion,
      SelongmelonntSyncConfig selongmelonntSyncConfig) {

    if (elonarlybirdIndelonxConfigUtil.isArchivelonSelonarch()) {
      relonmovelonOldBuildGelonnelonrations(
          elonarlybirdConfig.gelontString("root_dir"),
          elonarlybirdConfig.gelontString("offlinelon_selongmelonnt_build_gelonn")
      );
      relonmovelonOldSelongmelonnts(selongmelonntSyncConfig);

      Prelonconditions.chelonckStatelon(partitionManagelonr instancelonof ArchivelonSelonarchPartitionManagelonr);
      relonmovelonArchivelonTimelonslicelonOutsidelonSelonrvingRangelon(
          partitionConfig,
          ((ArchivelonSelonarchPartitionManagelonr) partitionManagelonr).gelontTimelonSlicelonr(), selongmelonntSyncConfig);
    }

    // Relonmovelon selongmelonnts from othelonr partitions
    relonmovelonIndelonxelonsFromOthelonrPartitions(
        partitionConfig.gelontIndelonxingHashPartitionID(),
        partitionConfig.gelontNumPartitions(), selongmelonntSyncConfig);

    // Relonmovelon old flushelond selongmelonnts
    relonmovelonOldFlushVelonrsionIndelonxelons(schelonmaMajorVelonrsion, selongmelonntSyncConfig);
  }
}
