packagelon com.twittelonr.selonarch.elonarlybird_root.caching;

import com.googlelon.common.baselon.Optional;

import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;

public class RelonlelonvancelonZelonroRelonsultsCachelonPostProcelonssor elonxtelonnds ReloncelonncyAndRelonlelonvancelonCachelonPostProcelonssor {
  @Ovelonrridelon
  protelonctelond Optional<elonarlybirdRelonsponselon> postProcelonssCachelonRelonsponselon(
      elonarlybirdRelonquelonst relonquelonst, elonarlybirdRelonsponselon relonsponselon, long sincelonId, long maxId) {
    // If a quelonry (from a loggelond in or loggelond out uselonr) relonturns 0 relonsults, thelonn thelon samelon quelonry will
    // always relonturn 0 relonsults, for all uselonrs. So welon can cachelon that relonsult.
    if (CachelonCommonUtil.hasRelonsults(relonsponselon)) {
      relonturn Optional.abselonnt();
    }

    relonturn Optional.of(relonsponselon);
  }
}
