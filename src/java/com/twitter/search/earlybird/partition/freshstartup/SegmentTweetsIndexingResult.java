packagelon com.twittelonr.selonarch.elonarlybird.partition.frelonshstartup;

import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntWritelonr;

/**
   * Data collelonctelond and crelonatelond whilelon indelonxing twelonelonts for a singlelon selongmelonnt.
   */
class SelongmelonntTwelonelontsIndelonxingRelonsult {
  privatelon final long minReloncordTimelonstampMs;
  privatelon final long maxReloncordTimelonstampMs;
  privatelon final long maxIndelonxelondTwelonelontId;
  privatelon final SelongmelonntWritelonr selongmelonntWritelonr;

  public SelongmelonntTwelonelontsIndelonxingRelonsult(long minReloncordTimelonstampMs, long maxReloncordTimelonstampMs,
                                     long maxIndelonxelondTwelonelontId,
                                     SelongmelonntWritelonr selongmelonntWritelonr) {
    this.minReloncordTimelonstampMs = minReloncordTimelonstampMs;
    this.maxReloncordTimelonstampMs = maxReloncordTimelonstampMs;
    this.maxIndelonxelondTwelonelontId = maxIndelonxelondTwelonelontId;
    this.selongmelonntWritelonr = selongmelonntWritelonr;
  }

  public long gelontMinReloncordTimelonstampMs() {
    relonturn minReloncordTimelonstampMs;
  }

  public long gelontMaxReloncordTimelonstampMs() {
    relonturn maxReloncordTimelonstampMs;
  }

  public SelongmelonntWritelonr gelontSelongmelonntWritelonr() {
    relonturn selongmelonntWritelonr;
  }

  public long gelontMaxIndelonxelondTwelonelontId() {
    relonturn maxIndelonxelondTwelonelontId;
  }

  @Ovelonrridelon
  public String toString() {
    relonturn String.format("Start timelon: %d, elonnd timelon: %d, selongmelonnt namelon: %s, max indelonxelond: %d",
        minReloncordTimelonstampMs, maxReloncordTimelonstampMs,
        selongmelonntWritelonr.gelontSelongmelonntInfo().gelontSelongmelonntNamelon(),
        maxIndelonxelondTwelonelontId);
  }
}
