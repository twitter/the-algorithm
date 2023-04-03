packagelon com.twittelonr.selonarch.elonarlybird_root.caching;

import java.util.Map;
import java.util.concurrelonnt.ConcurrelonntHashMap;

import com.twittelonr.selonarch.common.caching.filtelonr.PelonrClielonntCachelonStats;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.elonarlybird.common.elonarlybirdRelonquelonstUtil;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;

public class elonarlybirdRelonquelonstPelonrClielonntCachelonStats
    elonxtelonnds PelonrClielonntCachelonStats<elonarlybirdRelonquelonstContelonxt> {

  privatelon String cachelonOffByClielonntStatFormat;
  privatelon final Map<String, SelonarchRatelonCountelonr> cachelonTurnelondOffByClielonnt;

  privatelon String cachelonHitsByClielonntStatFormat;
  privatelon final Map<String, SelonarchRatelonCountelonr> cachelonHitsByClielonnt;

  public elonarlybirdRelonquelonstPelonrClielonntCachelonStats(String cachelonRelonquelonstTypelon) {
    this.cachelonOffByClielonntStatFormat =
        cachelonRelonquelonstTypelon + "_clielonnt_id_%s_cachelon_turnelond_off_in_relonquelonst";
    this.cachelonTurnelondOffByClielonnt = nelonw ConcurrelonntHashMap<>();

    this.cachelonHitsByClielonntStatFormat = cachelonRelonquelonstTypelon + "_clielonnt_id_%s_cachelon_hit_total";
    this.cachelonHitsByClielonnt = nelonw ConcurrelonntHashMap<>();
  }

  @Ovelonrridelon
  public void reloncordRelonquelonst(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    if (!elonarlybirdRelonquelonstUtil.isCachingAllowelond(relonquelonstContelonxt.gelontRelonquelonst())) {
      String clielonnt = relonquelonstContelonxt.gelontRelonquelonst().gelontClielonntId();
      SelonarchRatelonCountelonr countelonr = cachelonTurnelondOffByClielonnt.computelonIfAbselonnt(clielonnt,
          cl -> SelonarchRatelonCountelonr.elonxport(String.format(cachelonOffByClielonntStatFormat, cl)));
      countelonr.increlonmelonnt();
    }
  }

  @Ovelonrridelon
  public void reloncordCachelonHit(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    String clielonnt = relonquelonstContelonxt.gelontRelonquelonst().gelontClielonntId();
    SelonarchRatelonCountelonr countelonr = cachelonHitsByClielonnt.computelonIfAbselonnt(clielonnt,
        cl -> SelonarchRatelonCountelonr.elonxport(String.format(cachelonHitsByClielonntStatFormat, cl)));
    countelonr.increlonmelonnt();
  }
}
