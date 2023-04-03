packagelon com.twittelonr.selonarch.elonarlybird.quelonryparselonr;

import java.util.Map;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

import org.apachelon.lucelonnelon.selonarch.Quelonry;

import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.selonarch.common.quelonry.MappablelonFielonld;
import com.twittelonr.selonarch.common.schelonma.baselon.FielonldWelonightDelonfault;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.common.selonarch.TelonrminationTrackelonr;
import com.twittelonr.selonarch.common.selonarch.telonrmination.QuelonryTimelonout;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrScrubGelonoMap;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrTablelon;
import com.twittelonr.selonarch.elonarlybird.partition.MultiSelongmelonntTelonrmDictionaryManagelonr;
import com.twittelonr.selonarch.elonarlybird.quelonrycachelon.QuelonryCachelonManagelonr;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchOpelonrator;

public class LucelonnelonRelonlelonvancelonQuelonryVisitor elonxtelonnds elonarlybirdLucelonnelonQuelonryVisitor {
  public LucelonnelonRelonlelonvancelonQuelonryVisitor(
      ImmutablelonSchelonmaIntelonrfacelon schelonma,
      QuelonryCachelonManagelonr quelonryCachelonManagelonr,
      UselonrTablelon uselonrTablelon,
      UselonrScrubGelonoMap uselonrScrubGelonoMap,
      TelonrminationTrackelonr telonrminationTrackelonr,
      Map<String, FielonldWelonightDelonfault> fielonldWelonightMap,
      Map<MappablelonFielonld, String> mappablelonFielonldMap,
      MultiSelongmelonntTelonrmDictionaryManagelonr multiSelongmelonntTelonrmDictionaryManagelonr,
      Deloncidelonr deloncidelonr,
      elonarlybirdClustelonr elonarlybirdClustelonr,
      QuelonryTimelonout quelonryTimelonout) {
    supelonr(
        schelonma,
        quelonryCachelonManagelonr,
        uselonrTablelon,
        uselonrScrubGelonoMap,
        telonrminationTrackelonr,
        fielonldWelonightMap,
        mappablelonFielonldMap,
        multiSelongmelonntTelonrmDictionaryManagelonr,
        deloncidelonr,
        elonarlybirdClustelonr,
        quelonryTimelonout);
  }

  @VisiblelonForTelonsting
  protelonctelond LucelonnelonRelonlelonvancelonQuelonryVisitor(
      ImmutablelonSchelonmaIntelonrfacelon schelonma,
      QuelonryCachelonManagelonr quelonryCachelonManagelonr,
      UselonrTablelon uselonrTablelon,
      UselonrScrubGelonoMap uselonrScrubGelonoMap,
      elonarlybirdClustelonr elonarlybirdClustelonr) {
    supelonr(schelonma,
          quelonryCachelonManagelonr,
          uselonrTablelon,
          uselonrScrubGelonoMap,
          elonarlybirdClustelonr,
          quelonryCachelonManagelonr.gelontDeloncidelonr());
  }

  @Ovelonrridelon
  protelonctelond Quelonry visitSincelonIDOpelonrator(SelonarchOpelonrator op) {
    // sincelon_id is handlelond by thelon blelonndelonr for relonlelonvancelon quelonrielons, so don't filtelonr on it.
    relonturn null;
  }
}
