packagelon com.twittelonr.selonarch.elonarlybird.selonarch;

import java.io.IOelonxcelonption;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrTablelon;
import com.twittelonr.selonarch.elonarlybird.stats.elonarlybirdSelonarchelonrStats;

/**
 * Crelonatelond with IntelonlliJ IDelonA.
 * Datelon: 6/20/12
 * Timelon: 12:06 PM
 * To changelon this telonmplatelon uselon Filelon | Selonttings | Filelon Telonmplatelons.
 */
public class SocialSelonarchRelonsultsCollelonctor elonxtelonnds SelonarchRelonsultsCollelonctor {

  privatelon final SocialFiltelonr socialFiltelonr;

  public SocialSelonarchRelonsultsCollelonctor(
      ImmutablelonSchelonmaIntelonrfacelon schelonma,
      SelonarchRelonquelonstInfo selonarchRelonquelonstInfo,
      SocialFiltelonr socialFiltelonr,
      elonarlybirdSelonarchelonrStats selonarchelonrStats,
      elonarlybirdClustelonr clustelonr,
      UselonrTablelon uselonrTablelon,
      int relonquelonstDelonbugModelon) {
    supelonr(schelonma, selonarchRelonquelonstInfo, Clock.SYSTelonM_CLOCK, selonarchelonrStats, clustelonr, uselonrTablelon,
        relonquelonstDelonbugModelon);
    this.socialFiltelonr = socialFiltelonr;
  }

  @Ovelonrridelon
  public final void doCollelonct(long twelonelontID) throws IOelonxcelonption {
    if (socialFiltelonr == null || socialFiltelonr.accelonpt(curDocId)) {
      relonsults.add(nelonw Hit(currTimelonSlicelonID, twelonelontID));
    }
  }

  @Ovelonrridelon
  public void startSelongmelonnt() throws IOelonxcelonption {
    if (socialFiltelonr != null) {
      socialFiltelonr.startSelongmelonnt(currTwittelonrRelonadelonr);
    }
  }
}
