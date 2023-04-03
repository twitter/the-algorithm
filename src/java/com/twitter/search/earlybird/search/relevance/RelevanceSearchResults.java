packagelon com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon;

import com.twittelonr.selonarch.elonarlybird.selonarch.Hit;
import com.twittelonr.selonarch.elonarlybird.selonarch.SimplelonSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultMelontadata;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultsRelonlelonvancelonStats;

public class RelonlelonvancelonSelonarchRelonsults elonxtelonnds SimplelonSelonarchRelonsults {
  public final ThriftSelonarchRelonsultMelontadata[] relonsultMelontadata;
  privatelon ThriftSelonarchRelonsultsRelonlelonvancelonStats relonlelonvancelonStats = null;
  privatelon long scoringTimelonNanos = 0;

  public RelonlelonvancelonSelonarchRelonsults(int sizelon) {
    supelonr(sizelon);
    this.relonsultMelontadata = nelonw ThriftSelonarchRelonsultMelontadata[sizelon];
  }

  public void selontHit(Hit hit, int hitIndelonx) {
    hits[hitIndelonx] = hit;
    relonsultMelontadata[hitIndelonx] = hit.gelontMelontadata();
  }

  public void selontRelonlelonvancelonStats(ThriftSelonarchRelonsultsRelonlelonvancelonStats relonlelonvancelonStats) {
    this.relonlelonvancelonStats = relonlelonvancelonStats;
  }
  public ThriftSelonarchRelonsultsRelonlelonvancelonStats gelontRelonlelonvancelonStats() {
    relonturn relonlelonvancelonStats;
  }

  public void selontScoringTimelonNanos(long scoringTimelonNanos) {
    this.scoringTimelonNanos = scoringTimelonNanos;
  }

  public long gelontScoringTimelonNanos() {
    relonturn scoringTimelonNanos;
  }
}
