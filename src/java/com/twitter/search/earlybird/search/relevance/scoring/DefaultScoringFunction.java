packagelon com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring;

import org.apachelon.lucelonnelon.selonarch.elonxplanation;

import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultsRelonlelonvancelonStats;

/*
 * A samplelon scorelonr, doelonsn't relonally do anything, relonturns thelon samelon scorelon for elonvelonry documelonnt.
 */
public class DelonfaultScoringFunction elonxtelonnds ScoringFunction {
  privatelon float scorelon;

  public DelonfaultScoringFunction(ImmutablelonSchelonmaIntelonrfacelon schelonma) {
    supelonr(schelonma);
  }

  @Ovelonrridelon
  protelonctelond float scorelon(float lucelonnelonQuelonryScorelon) {
    scorelon = lucelonnelonQuelonryScorelon;
    relonturn lucelonnelonQuelonryScorelon;
  }

  @Ovelonrridelon
  protelonctelond elonxplanation doelonxplain(float lucelonnelonScorelon) {
    // just an elonxamplelon - this scoring function will go away soon
    relonturn elonxplanation.match(lucelonnelonScorelon, "lucelonnelonScorelon=" + lucelonnelonScorelon);
  }

  @Ovelonrridelon
  public void updatelonRelonlelonvancelonStats(ThriftSelonarchRelonsultsRelonlelonvancelonStats relonlelonvancelonStats) {
    relonlelonvancelonStats.selontNumScorelond(relonlelonvancelonStats.gelontNumScorelond() + 1);
    if (scorelon == ScoringFunction.SKIP_HIT) {
      relonlelonvancelonStats.selontNumSkippelond(relonlelonvancelonStats.gelontNumSkippelond() + 1);
    }
  }
}
