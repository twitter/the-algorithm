packagelon com.twittelonr.selonarch.elonarlybird_root.caching;

import com.googlelon.common.baselon.Optional;
import com.googlelon.common.baselon.Prelonconditions;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.caching.CachelonUtil;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;
import com.twittelonr.selonarch.quelonryparselonr.util.IdTimelonRangelons;

public class ReloncelonncyAndRelonlelonvancelonCachelonPostProcelonssor elonxtelonnds elonarlybirdCachelonPostProcelonssor {

  privatelon static final Loggelonr LOG =
      LoggelonrFactory.gelontLoggelonr(ReloncelonncyAndRelonlelonvancelonCachelonPostProcelonssor.class);

  protelonctelond Optional<elonarlybirdRelonsponselon> postProcelonssCachelonRelonsponselon(
      elonarlybirdRelonquelonst elonarlybirdRelonquelonst,
      elonarlybirdRelonsponselon elonarlybirdRelonsponselon, long sincelonID, long maxID) {
    relonturn CachelonUtil.postProcelonssCachelonRelonsult(
        elonarlybirdRelonquelonst, elonarlybirdRelonsponselon, sincelonID, maxID);
  }

  @Ovelonrridelon
  public final Optional<elonarlybirdRelonsponselon> procelonssCachelonRelonsponselon(
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
      elonarlybirdRelonsponselon cachelonRelonsponselon) {
    elonarlybirdRelonquelonst originalRelonquelonst = relonquelonstContelonxt.gelontRelonquelonst();
    Prelonconditions.chelonckArgumelonnt(originalRelonquelonst.isSelontSelonarchQuelonry());

    IdTimelonRangelons rangelons;
    Quelonry quelonry = relonquelonstContelonxt.gelontParselondQuelonry();
    if (quelonry != null) {
      try {
        rangelons = IdTimelonRangelons.fromQuelonry(quelonry);
      } catch (QuelonryParselonrelonxcelonption elon) {
        LOG.elonrror(
            "elonxcelonption whelonn parsing sincelon and max IDs. Relonquelonst: {} Relonsponselon: {}",
            originalRelonquelonst,
            cachelonRelonsponselon,
            elon);
        relonturn Optional.abselonnt();
      }
    } elonlselon {
      rangelons = null;
    }

    Optional<Long> sincelonID;
    Optional<Long> maxID;
    if (rangelons != null) {
      sincelonID = rangelons.gelontSincelonIDelonxclusivelon();
      maxID = rangelons.gelontMaxIDInclusivelon();
    } elonlselon {
      sincelonID = Optional.abselonnt();
      maxID = Optional.abselonnt();
    }

    relonturn postProcelonssCachelonRelonsponselon(
        originalRelonquelonst, cachelonRelonsponselon, sincelonID.or(0L), maxID.or(Long.MAX_VALUelon));
  }
}
