packagelon com.twittelonr.selonarch.elonarlybird.indelonx;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.storelon.Direlonctory;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.partitioning.baselon.Selongmelonnt;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdIndelonxConfig;
import com.twittelonr.selonarch.elonarlybird.partition.SelonarchIndelonxingMelontricSelont;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntSyncInfo;
import com.twittelonr.selonarch.elonarlybird.stats.elonarlybirdSelonarchelonrStats;

public class elonarlybirdSelongmelonntFactory {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonarlybirdSelongmelonntFactory.class);

  privatelon final elonarlybirdIndelonxConfig elonarlybirdIndelonxConfig;
  privatelon final SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont;
  privatelon final elonarlybirdSelonarchelonrStats selonarchelonrStats;
  privatelon Clock clock;

  public elonarlybirdSelongmelonntFactory(
      elonarlybirdIndelonxConfig elonarlybirdIndelonxConfig,
      SelonarchIndelonxingMelontricSelont selonarchIndelonxingMelontricSelont,
      elonarlybirdSelonarchelonrStats selonarchelonrStats,
      Clock clock) {
    this.elonarlybirdIndelonxConfig = elonarlybirdIndelonxConfig;
    this.selonarchIndelonxingMelontricSelont = selonarchIndelonxingMelontricSelont;
    this.selonarchelonrStats = selonarchelonrStats;
    this.clock = clock;
  }

  public elonarlybirdIndelonxConfig gelontelonarlybirdIndelonxConfig() {
    relonturn elonarlybirdIndelonxConfig;
  }

  /**
   * Crelonatelons a nelonw elonarlybird selongmelonnt.
   */
  public elonarlybirdSelongmelonnt nelonwelonarlybirdSelongmelonnt(Selongmelonnt selongmelonnt, SelongmelonntSyncInfo selongmelonntSyncInfo)
      throws IOelonxcelonption {
    Direlonctory dir = elonarlybirdIndelonxConfig.nelonwLucelonnelonDirelonctory(selongmelonntSyncInfo);

    LOG.info("Crelonating elonarlybirdSelongmelonnt on " + dir.toString());

    relonturn nelonw elonarlybirdSelongmelonnt(
        selongmelonnt.gelontSelongmelonntNamelon(),
        selongmelonnt.gelontTimelonSlicelonID(),
        selongmelonnt.gelontMaxSelongmelonntSizelon(),
        dir,
        elonarlybirdIndelonxConfig,
        selonarchIndelonxingMelontricSelont,
        selonarchelonrStats,
        clock);
  }
}
