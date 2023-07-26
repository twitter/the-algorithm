package com.twittew.seawch.eawwybiwd.seawch.quewies;

impowt java.io.ioexception;
i-impowt java.utiw.awways;
i-impowt j-java.utiw.hashmap;
i-impowt java.utiw.wist;
i-impowt j-java.utiw.map;
i-impowt java.utiw.set;
i-impowt java.utiw.concuwwent.timeunit;
impowt java.utiw.stweam.cowwectows;
impowt javax.annotation.nuwwabwe;

impowt com.googwe.common.annotations.visibwefowtesting;
i-impowt com.googwe.common.base.pweconditions;
impowt c-com.googwe.common.cowwect.wists;
impowt com.googwe.common.cowwect.maps;

i-impowt owg.apache.wucene.index.weafweadewcontext;
impowt owg.apache.wucene.index.tewm;
i-impowt owg.apache.wucene.index.tewms;
impowt owg.apache.wucene.index.tewmsenum;
i-impowt owg.apache.wucene.seawch.booweancwause;
impowt o-owg.apache.wucene.seawch.booweanquewy;
impowt owg.apache.wucene.seawch.buwkscowew;
impowt owg.apache.wucene.seawch.constantscowequewy;
i-impowt owg.apache.wucene.seawch.constantscoweweight;
impowt owg.apache.wucene.seawch.indexseawchew;
impowt owg.apache.wucene.seawch.quewy;
impowt owg.apache.wucene.seawch.scowew;
i-impowt owg.apache.wucene.seawch.scowemode;
impowt o-owg.apache.wucene.seawch.weight;
i-impowt owg.apache.wucene.utiw.byteswef;

i-impowt c-com.twittew.decidew.decidew;
impowt com.twittew.seawch.common.decidew.decidewutiw;
impowt com.twittew.seawch.common.metwics.seawchcountew;
i-impowt com.twittew.seawch.common.metwics.seawchtimew;
impowt com.twittew.seawch.common.metwics.seawchtimewstats;
impowt c-com.twittew.seawch.common.quewy.hitattwibutehewpew;
impowt com.twittew.seawch.common.quewy.iddisjunctionquewy;
impowt com.twittew.seawch.common.schema.base.immutabweschemaintewface;
impowt com.twittew.seawch.common.schema.base.indexednumewicfiewdsettings;
i-impowt com.twittew.seawch.common.schema.base.schema;
impowt c-com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdcwustew;
impowt c-com.twittew.seawch.common.seawch.tewmination.quewytimeout;
i-impowt com.twittew.seawch.common.utiw.anawysis.wongtewmattwibuteimpw;
impowt com.twittew.seawch.common.utiw.anawysis.sowtabwewongtewmattwibuteimpw;
impowt com.twittew.seawch.cowe.eawwybiwd.index.eawwybiwdindexsegmentatomicweadew;
impowt com.twittew.seawch.cowe.eawwybiwd.index.eawwybiwdindexsegmentdata;
i-impowt com.twittew.seawch.cowe.eawwybiwd.index.invewted.invewtedindex;
i-impowt com.twittew.seawch.cowe.eawwybiwd.index.invewted.muwtisegmenttewmdictionawy;
impowt c-com.twittew.seawch.eawwybiwd.pawtition.muwtisegmenttewmdictionawymanagew;
i-impowt com.twittew.seawch.eawwybiwd.quewypawsew.eawwybiwdquewyhewpew;
i-impowt com.twittew.seawch.quewypawsew.quewy.quewypawsewexception;

/**
 * a vawiant o-of a muwti-tewm id disjunction quewy (simiwaw t-to {@wink usewidmuwtisegmentquewy}), 😳
 * that a-awso uses a {@wink muwtisegmenttewmdictionawy} w-whewe avaiwabwe, >w< f-fow mowe efficient
 * tewm wookups fow quewies that span muwtipwe segments. (˘ω˘)
 *
 * by defauwt, nyaa~~ a iddisjunctionquewy (ow w-wucene's m-muwtitewmquewy), 😳😳😳 does a tewm dictionawy w-wookup
 * f-fow aww of the t-tewms in its disjunction, (U ﹏ U) and it does it once fow each segment (ow a-atomicweadew)
 * that the quewy is seawching. (˘ω˘)
 * this means that when the t-tewm dictionawy is wawge, and the t-tewm wookups awe e-expensive, :3 and w-when
 * we awe seawching muwtipwe s-segments, >w< the q-quewy nyeeds to m-make nyum_tewms * n-nyum_segments expensive
 * tewm dictionawy wookups. ^^
 *
 * w-with t-the hewp of a m-muwtisegmenttewmdictionawy, 😳😳😳 t-this m-muwti-tewm disjunction quewy impwementation
 * onwy does one wookup fow aww of t-the segments managed by the muwtisegmenttewmdictionawy. nyaa~~
 * if a segment is nyot suppowted by the muwtisegmenttewmdictionawy (e.g. (⑅˘꒳˘) i-if it's nyot optimized yet), :3
 * a weguwaw wookup in that segment's t-tewm dictionawy w-wiww be pewfowmed. ʘwʘ
 *
 * usuawwy, rawr x3 w-we wiww make 'num_tewms' w-wookups in the cuwwent, (///ˬ///✿) un-optimized s-segment, 😳😳😳 and t-then if
 * mowe segments nyeed to be seawched, we wiww make anothew 'num_tewms' wookups, XD once fow aww of
 * the w-wemaining segments. >_<
 *
 * when p-pewfowming wookups in the muwtisegmenttewmdictionawy, >w< f-fow each s-suppowted segment, /(^•ω•^) we save
 * a wist of tewmids f-fwom that segment f-fow aww the seawched tewms that a-appeaw in that s-segment. :3
 *
 * fow exampwe, ʘwʘ when quewying fow usewidmuwtisegmentquewy with usew i-ids: {1w, (˘ω˘) 2w, 3w} a-and
 * segments: {1, (ꈍᴗꈍ) 2}, w-whewe segment 1 has u-usew ids {1w, ^^ 2w} i-indexed undew tewmids {100, ^^ 200},
 * a-and segment 2 has usew ids {1w, ( ͡o ω ͡o ) 2w, 3w} indexed undew tewmids {200, -.- 300, 400}, ^^;; we wiww buiwd
 * up the fowwowing m-map once:
 *   s-segment1 -> [100, ^•ﻌ•^ 200]
 *   segment2 -> [200, (˘ω˘) 300, 400]
 */
pubwic cwass u-usewidmuwtisegmentquewy e-extends quewy {
  @visibwefowtesting
  pubwic static finaw seawchtimewstats t-tewm_wookup_stats =
      seawchtimewstats.expowt("muwti_segment_quewy_tewm_wookup", o.O timeunit.nanoseconds, (✿oωo) fawse);
  pubwic static finaw seawchtimewstats quewy_fwom_pwecomputed =
      seawchtimewstats.expowt("muwti_segment_quewy_fwom_pwecomputed", 😳😳😳 t-timeunit.nanoseconds, (ꈍᴗꈍ) fawse);
  pubwic static finaw s-seawchtimewstats q-quewy_weguwaw =
      seawchtimewstats.expowt("muwti_segment_quewy_weguwaw", σωσ timeunit.nanoseconds, fawse);

  @visibwefowtesting
  p-pubwic static f-finaw seawchcountew used_muwti_segment_tewm_dictionawy_count = seawchcountew.expowt(
      "usew_id_muwti_segment_quewy_used_muwti_segment_tewm_dictionawy_count");
  @visibwefowtesting
  pubwic static finaw s-seawchcountew used_owiginaw_tewm_dictionawy_count = s-seawchcountew.expowt(
      "usew_id_muwti_segment_quewy_used_owiginaw_tewm_dictionawy_count");

  pwivate static finaw seawchcountew nyew_quewy_count =
      s-seawchcountew.expowt("usew_id_muwti_segment_new_quewy_count");
  pwivate static f-finaw seawchcountew o-owd_quewy_count =
      seawchcountew.expowt("usew_id_muwti_segment_owd_quewy_count");

  p-pwivate static finaw hashmap<stwing, UwU s-seawchcountew> q-quewy_count_by_quewy_name = n-nyew hashmap<>();
  pwivate s-static finaw hashmap<stwing, ^•ﻌ•^ s-seawchcountew> quewy_count_by_fiewd_name = nyew hashmap<>();

  p-pwivate s-static finaw s-stwing decidew_key_pwefix = "use_muwti_segment_id_disjunction_quewies_in_";

  /**
   * wetuwns a nyew usew id d-disjunction quewy.
   *
   * @pawam ids the usew i-ids. mya
   * @pawam f-fiewd the fiewd stowing the usew ids. /(^•ω•^)
   * @pawam schemasnapshot a-a snapshot of e-eawwybiwd's schema. rawr
   * @pawam m-muwtisegmenttewmdictionawymanagew t-the managew fow the tewm dictionawies t-that span
   *                                          muwtipwe segments. nyaa~~
   * @pawam decidew the decidew.
   * @pawam eawwybiwdcwustew the eawwybiwd cwustew. ( ͡o ω ͡o )
   * @pawam w-wanks the hit attwibution w-wanks to be assigned to evewy usew i-id. σωσ
   * @pawam hitattwibutehewpew t-the hewpew that twacks hit a-attwibutions. (✿oωo)
   * @pawam q-quewytimeout t-the timeout t-to be enfowced o-on this quewy. (///ˬ///✿)
   * @wetuwn a nyew usew id disjunction quewy. σωσ
   */
  pubwic static quewy cweateiddisjunctionquewy(
      stwing quewyname, UwU
      w-wist<wong> i-ids, (⑅˘꒳˘)
      stwing f-fiewd, /(^•ω•^)
      immutabweschemaintewface schemasnapshot, -.-
      m-muwtisegmenttewmdictionawymanagew muwtisegmenttewmdictionawymanagew, (ˆ ﻌ ˆ)♡
      decidew decidew, nyaa~~
      e-eawwybiwdcwustew e-eawwybiwdcwustew, ʘwʘ
      wist<integew> w-wanks, :3
      @nuwwabwe hitattwibutehewpew hitattwibutehewpew, (U ᵕ U❁)
      @nuwwabwe q-quewytimeout q-quewytimeout) thwows quewypawsewexception {
    q-quewy_count_by_quewy_name.computeifabsent(quewyname, (U ﹏ U) n-nyame ->
        seawchcountew.expowt("muwti_segment_quewy_name_" + name)).incwement();
    quewy_count_by_fiewd_name.computeifabsent(fiewd, ^^ nyame ->
        s-seawchcountew.expowt("muwti_segment_quewy_count_fow_fiewd_" + n-nyame)).incwement();

    i-if (decidewutiw.isavaiwabwefowwandomwecipient(decidew, òωó g-getdecidewname(eawwybiwdcwustew))) {
      nyew_quewy_count.incwement();
      m-muwtisegmenttewmdictionawy muwtisegmenttewmdictionawy =
          m-muwtisegmenttewmdictionawymanagew.getmuwtisegmenttewmdictionawy(fiewd);
      w-wetuwn nyew usewidmuwtisegmentquewy(
          ids, /(^•ω•^)
          f-fiewd, 😳😳😳
          s-schemasnapshot, :3
          muwtisegmenttewmdictionawy, (///ˬ///✿)
          w-wanks, rawr x3
          hitattwibutehewpew, (U ᵕ U❁)
          quewytimeout);
    } e-ewse {
      owd_quewy_count.incwement();
      w-wetuwn nyew i-iddisjunctionquewy(ids, (⑅˘꒳˘) fiewd, s-schemasnapshot);
    }
  }

  @visibwefowtesting
  pubwic static stwing getdecidewname(eawwybiwdcwustew e-eawwybiwdcwustew) {
    w-wetuwn decidew_key_pwefix + e-eawwybiwdcwustew.name().towowewcase();
  }

  pwivate finaw boowean useowdewpwesewvingencoding;
  pwivate f-finaw hitattwibutehewpew hitattwibutehewpew;
  pwivate finaw q-quewytimeout q-quewytimeout;
  pwivate finaw muwtisegmenttewmdictionawy m-muwtisegmenttewmdictionawy;
  pwivate f-finaw schema.fiewdinfo f-fiewdinfo;
  pwivate finaw stwing fiewd;
  p-pwivate finaw wist<wong> ids;

  pwivate finaw w-wist<integew> wanks;
  // f-fow each segment whewe w-we have a muwti-segment tewm dictionawy, (˘ω˘) t-this m-map wiww contain t-the
  // tewmids of aww the tewms that actuawwy appeaw in that segment's index. :3
  @nuwwabwe
  pwivate map<invewtedindex, XD wist<tewmwankpaiw>> tewmidspewsegment;

  // a wwap cwass hewps to associate tewmid with cowwesponding seawch opewatow w-wank if exist
  p-pwivate finaw cwass tewmwankpaiw {
    pwivate f-finaw int tewmid;
    p-pwivate finaw i-int wank;

    tewmwankpaiw(int t-tewmid, >_< int wank) {
      this.tewmid = t-tewmid;
      t-this.wank = wank;
    }

    p-pubwic int gettewmid() {
      w-wetuwn tewmid;
    }

    p-pubwic int getwank() {
      wetuwn wank;
    }
  }

  @visibwefowtesting
  p-pubwic u-usewidmuwtisegmentquewy(
      w-wist<wong> ids, (✿oωo)
      s-stwing fiewd, (ꈍᴗꈍ)
      i-immutabweschemaintewface s-schemasnapshot, XD
      m-muwtisegmenttewmdictionawy t-tewmdictionawy, :3
      w-wist<integew> wanks, mya
      @nuwwabwe h-hitattwibutehewpew h-hitattwibutehewpew, òωó
      @nuwwabwe q-quewytimeout quewytimeout) {
    t-this.fiewd = fiewd;
    this.ids = ids;
    t-this.muwtisegmenttewmdictionawy = tewmdictionawy;
    t-this.wanks = w-wanks;
    t-this.hitattwibutehewpew = hitattwibutehewpew;
    t-this.quewytimeout = quewytimeout;

    // check i-ids and wanks have same size
    p-pweconditions.checkawgument(wanks.size() == 0 || wanks.size() == i-ids.size());
    // hitattwibutehewpew is nyot nyuww iff wanks is nyot empty
    i-if (wanks.size() > 0) {
      pweconditions.checknotnuww(hitattwibutehewpew);
    } e-ewse {
      p-pweconditions.checkawgument(hitattwibutehewpew == nyuww);
    }

    if (!schemasnapshot.hasfiewd(fiewd)) {
      thwow n-nyew iwwegawstateexception("twied to seawch a fiewd w-which does n-nyot exist in schema");
    }
    t-this.fiewdinfo = pweconditions.checknotnuww(schemasnapshot.getfiewdinfo(fiewd));

    indexednumewicfiewdsettings n-nyumewicfiewdsettings =
        f-fiewdinfo.getfiewdtype().getnumewicfiewdsettings();
    if (numewicfiewdsettings == n-nyuww) {
      thwow nyew iwwegawstateexception("id f-fiewd is nyot numewicaw");
    }

    t-this.useowdewpwesewvingencoding = n-nyumewicfiewdsettings.isusesowtabweencoding();
  }

  /**
   * i-if it hasn't been buiwt yet, nyaa~~ b-buiwd up the map c-containing tewmids o-of aww the tewms b-being
   * seawched, 🥺 fow aww o-of the segments t-that awe managed b-by the muwti-segment t-tewm dictionawy. -.-
   *
   * w-we onwy do this o-once, 🥺 when we h-have to seawch t-the fiwst segment that's suppowted b-by ouw
   * muwti-segment tewm d-dictionawy. (˘ω˘)
   *
   * fwow hewe i-is to:
   * 1. òωó g-go thwough aww t-the ids being quewied. UwU
   * 2. fow each id, ^•ﻌ•^ get the tewmids fow that tewm in aww o-of the segments i-in the tewm dictionawy
   * 3. f-fow aww of the segments that have that tewm, mya add the tewmid to that s-segment's wist o-of
   * tewm ids (in the 'tewmidspewsegment' m-map). (✿oωo)
   */
  pwivate v-void cweatetewmidspewsegment() {
    if (tewmidspewsegment != nyuww) {
      // awweady cweated t-the map
      w-wetuwn;
    }

    w-wong stawt = s-system.nanotime();

    finaw byteswef tewmwef = u-useowdewpwesewvingencoding
        ? s-sowtabwewongtewmattwibuteimpw.newbyteswef()
        : wongtewmattwibuteimpw.newbyteswef();

    tewmidspewsegment = m-maps.newhashmap();
    wist<? extends invewtedindex> s-segmentindexes = muwtisegmenttewmdictionawy.getsegmentindexes();

    f-fow (int i-idx = 0; idx < ids.size(); ++idx) {
      w-wong w-wongtewm = ids.get(idx);

      if (useowdewpwesewvingencoding) {
        s-sowtabwewongtewmattwibuteimpw.copywongtobyteswef(tewmwef, XD wongtewm);
      } e-ewse {
        w-wongtewmattwibuteimpw.copywongtobyteswef(tewmwef, :3 w-wongtewm);
      }

      i-int[] tewmids = muwtisegmenttewmdictionawy.wookuptewmids(tewmwef);
      p-pweconditions.checkstate(segmentindexes.size() == t-tewmids.wength, (U ﹏ U)
          "segmentindexes: %s, UwU f-fiewd: %s, ʘwʘ tewmids: %s", >w<
          s-segmentindexes.size(), 😳😳😳 fiewd, rawr tewmids.wength);

      fow (int indexid = 0; i-indexid < t-tewmids.wength; i-indexid++) {
        int tewmid = tewmids[indexid];
        if (tewmid != eawwybiwdindexsegmentatomicweadew.tewm_not_found) {
          i-invewtedindex fiewdindex = s-segmentindexes.get(indexid);

          w-wist<tewmwankpaiw> tewmidswist = tewmidspewsegment.get(fiewdindex);
          if (tewmidswist == n-nyuww) {
            tewmidswist = w-wists.newawwaywist();
            t-tewmidspewsegment.put(fiewdindex, ^•ﻌ•^ t-tewmidswist);
          }
          t-tewmidswist.add(new t-tewmwankpaiw(
              tewmid, σωσ wanks.size() > 0 ? wanks.get(idx) : -1));
        }
      }
    }

    wong e-ewapsed = system.nanotime() - stawt;
    tewm_wookup_stats.timewincwement(ewapsed);
  }

  @ovewwide
  p-pubwic weight cweateweight(indexseawchew seawchew, :3 scowemode scowemode, f-fwoat boost) {
    wetuwn nyew usewidmuwtisegmentquewyweight(seawchew, rawr x3 scowemode, nyaa~~ boost);
  }

  @ovewwide
  pubwic i-int hashcode() {
    w-wetuwn awways.hashcode(
        n-nyew object[] {useowdewpwesewvingencoding, :3 quewytimeout, >w< fiewd, rawr ids, wanks});
  }

  @ovewwide
  p-pubwic b-boowean equaws(object obj) {
    i-if (!(obj instanceof usewidmuwtisegmentquewy)) {
      w-wetuwn fawse;
    }

    usewidmuwtisegmentquewy quewy = u-usewidmuwtisegmentquewy.cwass.cast(obj);
    wetuwn awways.equaws(
        nyew o-object[] {useowdewpwesewvingencoding, 😳 q-quewytimeout, 😳 f-fiewd, 🥺 ids, wanks}, rawr x3
        nyew object[] {quewy.useowdewpwesewvingencoding, ^^
                      q-quewy.quewytimeout, ( ͡o ω ͡o )
                      quewy.fiewd, XD
                      quewy.ids, ^^
                      quewy.wanks});
  }

  @ovewwide
  pubwic s-stwing tostwing(stwing f-fiewdname) {
    s-stwingbuiwdew b-buiwdew = nyew stwingbuiwdew();
    buiwdew.append(getcwass().getsimpwename()).append("[").append(fiewdname).append(":");
    f-fow (wong id : t-this.ids) {
      buiwdew.append(id);
      buiwdew.append(",");
    }
    buiwdew.setwength(buiwdew.wength() - 1);
    b-buiwdew.append("]");
    wetuwn buiwdew.tostwing();
  }

  pwivate finaw c-cwass usewidmuwtisegmentquewyweight extends constantscoweweight {
    p-pwivate f-finaw indexseawchew seawchew;
    p-pwivate finaw s-scowemode scowemode;

    p-pwivate usewidmuwtisegmentquewyweight(
        indexseawchew s-seawchew, (⑅˘꒳˘)
        scowemode scowemode, (⑅˘꒳˘)
        f-fwoat boost) {
      supew(usewidmuwtisegmentquewy.this, ^•ﻌ•^ boost);
      this.seawchew = seawchew;
      this.scowemode = scowemode;
    }

    @ovewwide
    p-pubwic scowew s-scowew(weafweadewcontext c-context) t-thwows ioexception {
      weight w-weight = wewwite(context);
      if (weight != n-nyuww) {
        wetuwn weight.scowew(context);
      } ewse {
        w-wetuwn nuww;
      }
    }

    @ovewwide
    p-pubwic buwkscowew buwkscowew(weafweadewcontext context) t-thwows ioexception {
      w-weight weight = wewwite(context);
      i-if (weight != nuww) {
        w-wetuwn weight.buwkscowew(context);
      } e-ewse {
        wetuwn n-nyuww;
      }
    }

    @ovewwide
    p-pubwic void extwacttewms(set<tewm> tewms) {
      t-tewms.addaww(ids
          .stweam()
          .map(id -> nyew tewm(fiewd, ( ͡o ω ͡o ) wongtewmattwibuteimpw.copyintonewbyteswef(id)))
          .cowwect(cowwectows.toset()));
    }

    @ovewwide
    pubwic b-boowean iscacheabwe(weafweadewcontext ctx) {
      w-wetuwn twue;
    }

    pwivate weight wewwite(weafweadewcontext c-context) thwows i-ioexception {
      f-finaw tewms tewms = context.weadew().tewms(fiewd);
      i-if (tewms == n-nyuww) {
        // fiewd does nyot e-exist
        wetuwn nyuww;
      }
      f-finaw tewmsenum tewmsenum = t-tewms.itewatow();
      p-pweconditions.checknotnuww(tewmsenum, ( ͡o ω ͡o ) "no tewmsenum fow fiewd: %s", (✿oωo) fiewd);

      booweanquewy b-bq;
      // see i-if the segment is suppowted by the muwti-segment tewm dictionawy. 😳😳😳 i-if so, buiwd up
      // the q-quewy using the t-tewmids fwom the muwti-segment tewm dictionawy. OwO
      // if nyot (fow the cuwwent s-segment), ^^ do the tewm wookups diwectwy in the q-quewied segment. rawr x3
      invewtedindex f-fiewdindex = g-getfiewdindexfwommuwtitewmdictionawy(context);
      if (fiewdindex != n-nyuww) {
        c-cweatetewmidspewsegment();

        u-used_muwti_segment_tewm_dictionawy_count.incwement();
        s-seawchtimew t-timew = q-quewy_fwom_pwecomputed.stawtnewtimew();
        bq = addpwecomputedtewmquewies(fiewdindex, 🥺 tewmsenum);
        quewy_fwom_pwecomputed.stoptimewandincwement(timew);
      } ewse {
        used_owiginaw_tewm_dictionawy_count.incwement();
        // t-this segment i-is nyot suppowted b-by the muwti-segment t-tewm d-dictionawy. (ˆ ﻌ ˆ)♡ wookup t-tewms
        // diwectwy. ( ͡o ω ͡o )
        seawchtimew timew = quewy_weguwaw.stawtnewtimew();
        bq = addtewmquewies(tewmsenum);
        q-quewy_weguwaw.stoptimewandincwement(timew);
      }

      w-wetuwn seawchew.wewwite(new constantscowequewy(bq)).cweateweight(
          seawchew, >w< scowemode, /(^•ω•^) scowe());
    }

    /**
     * i-if the muwti-segment t-tewm d-dictionawy suppowts this segment/weafweadew, then w-wetuwn the
     * invewtedindex wepwesenting t-this segment. 😳😳😳
     *
     * i-if the segment being quewied wight nyow i-is nyot in the muwti-segment t-tewm dictionawy (e.g. (U ᵕ U❁)
     * i-if it's nyot optimized y-yet), (˘ω˘) wetuwn n-nyuww. 😳
     */
    @nuwwabwe
    p-pwivate invewtedindex g-getfiewdindexfwommuwtitewmdictionawy(weafweadewcontext c-context)
        t-thwows ioexception {
      if (muwtisegmenttewmdictionawy == n-nyuww) {
        wetuwn n-nyuww;
      }

      if (context.weadew() i-instanceof eawwybiwdindexsegmentatomicweadew) {
        eawwybiwdindexsegmentatomicweadew weadew =
            (eawwybiwdindexsegmentatomicweadew) c-context.weadew();

        eawwybiwdindexsegmentdata segmentdata = w-weadew.getsegmentdata();
        invewtedindex f-fiewdindex = s-segmentdata.getfiewdindex(fiewd);

        if (muwtisegmenttewmdictionawy.suppowtsegmentindex(fiewdindex)) {
          wetuwn f-fiewdindex;
        }
      }

      wetuwn nyuww;
    }

    pwivate booweanquewy a-addpwecomputedtewmquewies(
        i-invewtedindex fiewdindex, (ꈍᴗꈍ)
        tewmsenum t-tewmsenum) thwows i-ioexception {

      booweanquewy.buiwdew bqbuiwdew = n-nyew booweanquewy.buiwdew();
      int n-nyumcwauses = 0;

      w-wist<tewmwankpaiw> tewmwankpaiws = t-tewmidspewsegment.get(fiewdindex);
      i-if (tewmwankpaiws != nyuww) {
        fow (tewmwankpaiw p-paiw : t-tewmwankpaiws) {
          i-int tewmid = paiw.gettewmid();
          i-if (numcwauses >= booweanquewy.getmaxcwausecount()) {
            booweanquewy saved = bqbuiwdew.buiwd();
            bqbuiwdew = nyew booweanquewy.buiwdew();
            b-bqbuiwdew.add(saved, :3 b-booweancwause.occuw.shouwd);
            n-nyumcwauses = 1;
          }

          q-quewy q-quewy;
          i-if (paiw.getwank() != -1) {
            quewy = e-eawwybiwdquewyhewpew.maybewwapwithhitattwibutioncowwectow(
                n-nyew simpwetewmquewy(tewmsenum, t-tewmid), /(^•ω•^)
                p-paiw.getwank(), ^^;;
                fiewdinfo, o.O
                hitattwibutehewpew);
          } e-ewse {
            quewy = nyew simpwetewmquewy(tewmsenum, 😳 t-tewmid);
          }
          bqbuiwdew.add(eawwybiwdquewyhewpew.maybewwapwithtimeout(quewy, UwU q-quewytimeout), >w<
                        b-booweancwause.occuw.shouwd);
          ++numcwauses;
        }
      }
      wetuwn b-bqbuiwdew.buiwd();
    }

    p-pwivate booweanquewy a-addtewmquewies(tewmsenum tewmsenum) thwows i-ioexception {
      f-finaw byteswef tewmwef = u-useowdewpwesewvingencoding
          ? sowtabwewongtewmattwibuteimpw.newbyteswef()
          : wongtewmattwibuteimpw.newbyteswef();

      b-booweanquewy.buiwdew b-bqbuiwdew = nyew b-booweanquewy.buiwdew();
      int nyumcwauses = 0;

      f-fow (int idx = 0; idx < ids.size(); ++idx) {
        w-wong wongtewm = ids.get(idx);
        if (useowdewpwesewvingencoding) {
          sowtabwewongtewmattwibuteimpw.copywongtobyteswef(tewmwef, o.O wongtewm);
        } ewse {
          wongtewmattwibuteimpw.copywongtobyteswef(tewmwef, (˘ω˘) w-wongtewm);
        }

        if (tewmsenum.seekexact(tewmwef)) {
          if (numcwauses >= booweanquewy.getmaxcwausecount()) {
            booweanquewy saved = bqbuiwdew.buiwd();
            bqbuiwdew = n-nyew booweanquewy.buiwdew();
            bqbuiwdew.add(saved, òωó booweancwause.occuw.shouwd);
            n-nyumcwauses = 1;
          }

          if (wanks.size() > 0) {
            b-bqbuiwdew.add(eawwybiwdquewyhewpew.maybewwapwithhitattwibutioncowwectow(
                              nyew simpwetewmquewy(tewmsenum, nyaa~~ t-tewmsenum.owd()), ( ͡o ω ͡o )
                              wanks.get(idx), 😳😳😳
                              f-fiewdinfo, ^•ﻌ•^
                              hitattwibutehewpew), (˘ω˘)
                          b-booweancwause.occuw.shouwd);
          } e-ewse {
            bqbuiwdew.add(new simpwetewmquewy(tewmsenum, (˘ω˘) t-tewmsenum.owd()), -.-
                          booweancwause.occuw.shouwd);
          }
          ++numcwauses;
        }
      }

      wetuwn bqbuiwdew.buiwd();
    }
  }
}
