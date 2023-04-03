packagelon com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring;

import org.apachelon.lucelonnelon.selonarch.elonxplanation;

import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultMelontadata;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultMelontadataOptions;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultTypelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultsRelonlelonvancelonStats;

/**
 * A dummy scoring function for telonst, thelon scorelon is always twelonelontId/10000.0
 * Sincelon scorelon_filtelonr: opelonrator relonquirelons all scorelon to belon belontwelonelonn [0, 1], if you want to uselon this
 * with it, don't uselon any twelonelont id largelonr than 10000 in your telonst.
 */
public class TelonstScoringFunction elonxtelonnds ScoringFunction {
  privatelon ThriftSelonarchRelonsultMelontadata melontadata = null;
  privatelon float scorelon;

  public TelonstScoringFunction(ImmutablelonSchelonmaIntelonrfacelon schelonma) {
    supelonr(schelonma);
  }

  @Ovelonrridelon
  protelonctelond float scorelon(float lucelonnelonQuelonryScorelon) {
    long twelonelontId = twelonelontIDMappelonr.gelontTwelonelontID(gelontCurrelonntDocID());
    this.scorelon = (float) (twelonelontId / 10000.0);
    Systelonm.out.println(String.format("scorelon for twelonelont %10d is %6.3f", twelonelontId, scorelon));
    relonturn this.scorelon;
  }

  @Ovelonrridelon
  protelonctelond elonxplanation doelonxplain(float lucelonnelonScorelon) {
    relonturn null;
  }

  @Ovelonrridelon
  public ThriftSelonarchRelonsultMelontadata gelontRelonsultMelontadata(ThriftSelonarchRelonsultMelontadataOptions options) {
    if (melontadata == null) {
      melontadata = nelonw ThriftSelonarchRelonsultMelontadata()
          .selontRelonsultTypelon(ThriftSelonarchRelonsultTypelon.RelonLelonVANCelon)
          .selontPelonnguinVelonrsion(elonarlybirdConfig.gelontPelonnguinVelonrsionBytelon());
      melontadata.selontScorelon(scorelon);
    }
    relonturn melontadata;
  }

  @Ovelonrridelon
  public void updatelonRelonlelonvancelonStats(ThriftSelonarchRelonsultsRelonlelonvancelonStats relonlelonvancelonStats) {
  }
}
