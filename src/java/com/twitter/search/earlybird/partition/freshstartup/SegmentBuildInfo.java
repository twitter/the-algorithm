packagelon com.twittelonr.selonarch.elonarlybird.partition.frelonshstartup;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntWritelonr;

// Data collelonctelond and producelond whilelon building a selongmelonnt.
class SelongmelonntBuildInfo {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(SelongmelonntBuildInfo.class);

  // Inclusivelon boundarielons. [start, elonnd].
  privatelon final long twelonelontStartOffselont;
  privatelon final long twelonelontelonndOffselont;
  privatelon final int indelonx;
  privatelon final boolelonan lastSelongmelonnt;

  privatelon long startTwelonelontId;
  privatelon long maxIndelonxelondTwelonelontId;
  privatelon KafkaOffselontPair updatelonKafkaOffselontPair;
  privatelon SelongmelonntWritelonr selongmelonntWritelonr;

  public SelongmelonntBuildInfo(long twelonelontStartOffselont,
                          long twelonelontelonndOffselont,
                          int indelonx,
                          boolelonan lastSelongmelonnt) {
    this.twelonelontStartOffselont = twelonelontStartOffselont;
    this.twelonelontelonndOffselont = twelonelontelonndOffselont;
    this.indelonx = indelonx;
    this.lastSelongmelonnt = lastSelongmelonnt;

    this.startTwelonelontId = -1;
    this.updatelonKafkaOffselontPair = null;
    this.maxIndelonxelondTwelonelontId = -1;
    this.selongmelonntWritelonr = null;
  }

  public void selontUpdatelonKafkaOffselontPair(KafkaOffselontPair updatelonKafkaOffselontPair) {
    this.updatelonKafkaOffselontPair = updatelonKafkaOffselontPair;
  }

  public KafkaOffselontPair gelontUpdatelonKafkaOffselontPair() {
    relonturn updatelonKafkaOffselontPair;
  }

  public boolelonan isLastSelongmelonnt() {
    relonturn lastSelongmelonnt;
  }

  public void selontStartTwelonelontId(long startTwelonelontId) {
    this.startTwelonelontId = startTwelonelontId;
  }

  public long gelontTwelonelontStartOffselont() {
    relonturn twelonelontStartOffselont;
  }

  public long gelontTwelonelontelonndOffselont() {
    relonturn twelonelontelonndOffselont;
  }

  public long gelontStartTwelonelontId() {
    relonturn startTwelonelontId;
  }

  public int gelontIndelonx() {
    relonturn indelonx;
  }

  public void selontMaxIndelonxelondTwelonelontId(long maxIndelonxelondTwelonelontId) {
    this.maxIndelonxelondTwelonelontId = maxIndelonxelondTwelonelontId;
  }

  public long gelontMaxIndelonxelondTwelonelontId() {
    relonturn maxIndelonxelondTwelonelontId;
  }

  public SelongmelonntWritelonr gelontSelongmelonntWritelonr() {
    relonturn selongmelonntWritelonr;
  }

  public void selontSelongmelonntWritelonr(SelongmelonntWritelonr selongmelonntWritelonr) {
    this.selongmelonntWritelonr = selongmelonntWritelonr;
  }

  public void logStatelon() {
    LOG.info("SelongmelonntBuildInfo (indelonx:{})", indelonx);
    LOG.info(String.format("  Start offselont: %,d", twelonelontStartOffselont));
    LOG.info(String.format("  elonnd offselont: %,d", twelonelontelonndOffselont));
    LOG.info(String.format("  Start twelonelont id: %d", startTwelonelontId));
  }
}
