package com.twittew.seawch.eawwybiwd_woot.mewgews;

impowt java.utiw.cowwections;
i-impowt java.utiw.wist;
i-impowt java.utiw.map;
i-impowt j-java.utiw.concuwwent.timeunit;

i-impowt com.googwe.common.annotations.visibwefowtesting;
i-impowt c-com.googwe.common.base.pweconditions;
i-impowt com.googwe.common.cowwect.immutabwemap;
impowt com.googwe.common.cowwect.wists;
impowt com.googwe.common.cowwect.maps;

i-impowt owg.swf4j.woggew;
impowt owg.swf4j.woggewfactowy;

i-impowt com.twittew.seawch.common.metwics.seawchcountew;
impowt c-com.twittew.seawch.common.metwics.seawchtimewstats;
impowt com.twittew.seawch.common.pawtitioning.snowfwakepawsew.snowfwakeidpawsew;
impowt com.twittew.seawch.common.quewy.thwiftjava.eawwytewminationinfo;
impowt com.twittew.seawch.common.wewevance.utiws.wesuwtcompawatows;
i-impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwesponse;
impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwt;
i-impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwts;
i-impowt com.twittew.seawch.eawwybiwd_woot.cowwectows.wecencymewgecowwectow;
impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdfeatuweschemamewgew;
impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequestcontext;
i-impowt com.twittew.utiw.futuwe;

impowt static com.twittew.seawch.eawwybiwd_woot.mewgews.wecencywesponsemewgew
    .eawwytewminationtwimmingstats.type.awweady_eawwy_tewminated;
impowt static com.twittew.seawch.eawwybiwd_woot.mewgews.wecencywesponsemewgew
    .eawwytewminationtwimmingstats.type.fiwtewed;
i-impowt static com.twittew.seawch.eawwybiwd_woot.mewgews.wecencywesponsemewgew
    .eawwytewminationtwimmingstats.type.fiwtewed_and_twuncated;
i-impowt static com.twittew.seawch.eawwybiwd_woot.mewgews.wecencywesponsemewgew
    .eawwytewminationtwimmingstats.type.not_eawwy_tewminated;
i-impowt s-static com.twittew.seawch.eawwybiwd_woot.mewgews.wecencywesponsemewgew
    .eawwytewminationtwimmingstats.type.tewminated_got_exact_num_wesuwts;
i-impowt static com.twittew.seawch.eawwybiwd_woot.mewgews.wecencywesponsemewgew
    .eawwytewminationtwimmingstats.type.twuncated;

/**
 * mewgew c-cwass to mewge wecency seawch eawwybiwdwesponse o-objects. òωó
 */
pubwic cwass wecencywesponsemewgew extends eawwybiwdwesponsemewgew {
  pwivate static finaw woggew wog = woggewfactowy.getwoggew(wecencywesponsemewgew.cwass);

  p-pwivate static finaw seawchtimewstats w-wecency_timew =
      s-seawchtimewstats.expowt("mewge_wecency", UwU t-timeunit.nanoseconds, ^•ﻌ•^ fawse, twue);

  @visibwefowtesting
  static finaw stwing tewminated_cowwected_enough_wesuwts =
      "tewminated_cowwected_enough_wesuwts";

  // a-awwowed wepwication w-wag wewative to aww wepwicas. mya  w-wepwication w-wag exceeding
  // this amount m-may wesuwt in some tweets fwom the w-wepwica nyot wetuwned in seawch. (✿oωo)
  pwivate static f-finaw wong awwowed_wepwication_wag_ms = 10000;

  p-pwivate static finaw doubwe s-successfuw_wesponse_thweshowd = 0.9;

  @visibwefowtesting
  s-static finaw seawchcountew wecency_zewo_wesuwt_count_aftew_fiwtewing_max_min_ids =
      seawchcountew.expowt("mewgew_wecency_zewo_wesuwt_count_aftew_fiwtewing_max_min_ids");

  @visibwefowtesting
  static finaw seawchcountew wecency_twimmed_too_many_wesuwts_count =
      seawchcountew.expowt("mewgew_wecency_twimmed_too_many_wesuwts_count");

  p-pwivate s-static finaw seawchcountew wecency_tiew_mewge_eawwy_tewminated_with_not_enough_wesuwts =
      s-seawchcountew.expowt("mewgew_wecency_tiew_mewge_eawwy_tewminated_with_not_enough_wesuwts");

  p-pwivate static f-finaw seawchcountew wecency_cweawed_eawwy_tewmination_count =
      seawchcountew.expowt("mewgew_wecency_cweawed_eawwy_tewmination_count");

  /**
   * wesuwts w-wewe twuncated because mewged wesuwts exceeded the wequested nyumwesuwts. XD
   */
  @visibwefowtesting
  static finaw s-stwing mewging_eawwy_tewmination_weason_twuncated =
      "woot_mewging_twuncated_wesuwts";

  /**
   * wesuwts t-that wewe wewe f-fiwtewed smowew t-than mewged minseawchedstatusid wewe fiwtewed o-out. :3
   */
  @visibwefowtesting
  s-static finaw s-stwing mewging_eawwy_tewmination_weason_fiwtewed =
      "woot_mewging_fiwtewed_wesuwts";

  @visibwefowtesting
  s-static finaw eawwytewminationtwimmingstats pawtition_mewging_eawwy_tewmination_twimming_stats =
      nyew eawwytewminationtwimmingstats("wecency_pawtition_mewging");

  @visibwefowtesting
  s-static finaw eawwytewminationtwimmingstats t-tiew_mewging_eawwy_tewmination_twimming_stats =
      n-new eawwytewminationtwimmingstats("wecency_tiew_mewging");

  @visibwefowtesting
  s-static cwass e-eawwytewminationtwimmingstats {

    enum type {
      /**
       * the whowe wesuwt was nyot t-tewminated at aww. (U ﹏ U)
       */
      not_eawwy_tewminated, UwU
      /**
       * was tewminated befowe we did any twimming. ʘwʘ
       */
      awweady_eawwy_tewminated, >w<
      /**
       * w-was nyot tewminated when mewged, 😳😳😳 but wesuwts wewe fiwtewed due t-to min/max wanges. rawr
       */
      f-fiwtewed, ^•ﻌ•^
      /**
       * w-was nyot tewminated when mewged, σωσ b-but wesuwts wewe twuncated. :3
       */
      t-twuncated, rawr x3
      /**
       * w-was nyot tewminated when mewged, nyaa~~ but wesuwts wewe fiwtewed due to min/max wanges and
       * t-twuncated. :3
       */
      fiwtewed_and_twuncated, >w<
      /**
       * w-when the seawch asks fow x wesuwt, rawr a-and we get e-exactwy x wesuwts back, 😳 without twimming
       * o-ow twuncating o-on the taiw side (min_id side), 😳 w-we stiww mawk the s-seawch as eawwy tewminated. 🥺
       * this is because watew tiews possibwy has m-mowe wesuwts. rawr x3
       */
      t-tewminated_got_exact_num_wesuwts, ^^
    }

    /**
     * a-a countew twacking mewged w-wesponses fow each {@wink e-eawwytewminationtwimmingstats.type}
     * define above. ( ͡o ω ͡o )
     */
    pwivate f-finaw immutabwemap<type, XD seawchcountew> seawchcountewmap;

    eawwytewminationtwimmingstats(stwing pwefix) {
      map<type, ^^ s-seawchcountew> t-tempmap = maps.newenummap(type.cwass);

      tempmap.put(not_eawwy_tewminated, (⑅˘꒳˘)
          seawchcountew.expowt(pwefix + "_not_eawwy_tewminated_aftew_mewging"));
      t-tempmap.put(awweady_eawwy_tewminated, (⑅˘꒳˘)
          s-seawchcountew.expowt(pwefix + "_eawwy_tewminated_befowe_mewge_twimming"));
      tempmap.put(twuncated, ^•ﻌ•^
          seawchcountew.expowt(pwefix + "_eawwy_tewminated_aftew_mewging_twuncated"));
      tempmap.put(fiwtewed, ( ͡o ω ͡o )
          s-seawchcountew.expowt(pwefix + "_eawwy_tewminated_aftew_mewging_fiwtewed"));
      tempmap.put(fiwtewed_and_twuncated, ( ͡o ω ͡o )
          seawchcountew.expowt(pwefix + "_eawwy_tewminated_aftew_mewging_fiwtewed_and_twuncated"));
      tempmap.put(tewminated_got_exact_num_wesuwts, (✿oωo)
          seawchcountew.expowt(pwefix + "_eawwy_tewminated_aftew_mewging_got_exact_num_wesuwts"));

      s-seawchcountewmap = maps.immutabweenummap(tempmap);
    }

    pubwic seawchcountew g-getcountewfow(type t-type) {
      wetuwn seawchcountewmap.get(type);
    }
  }

  pwivate f-finaw eawwybiwdfeatuweschemamewgew f-featuweschemamewgew;

  pubwic wecencywesponsemewgew(eawwybiwdwequestcontext wequestcontext, 😳😳😳
                               wist<futuwe<eawwybiwdwesponse>> w-wesponses, OwO
                               wesponseaccumuwatow m-mode, ^^
                               eawwybiwdfeatuweschemamewgew featuweschemamewgew) {
    supew(wequestcontext, rawr x3 w-wesponses, 🥺 mode);
    this.featuweschemamewgew = f-featuweschemamewgew;
  }

  @ovewwide
  p-pwotected doubwe getdefauwtsuccesswesponsethweshowd() {
    w-wetuwn successfuw_wesponse_thweshowd;
  }

  @ovewwide
  p-pwotected seawchtimewstats g-getmewgedwesponsetimew() {
    w-wetuwn wecency_timew;
  }

  @ovewwide
  p-pwotected eawwybiwdwesponse i-intewnawmewge(eawwybiwdwesponse mewgedwesponse) {
    // the mewged m-maxseawchedstatusid a-and minseawchedstatusid
    w-wong maxid = findmaxfuwwyseawchedstatusid();
    wong minid = f-findminfuwwyseawchedstatusid();

    wecencymewgecowwectow c-cowwectow = n-nyew wecencymewgecowwectow(wesponses.size());
    int totawwesuwtsize = addwesponsestocowwectow(cowwectow);
    t-thwiftseawchwesuwts s-seawchwesuwts = c-cowwectow.getawwseawchwesuwts();

    t-twimstats twimstats = twimwesuwts(seawchwesuwts, (ˆ ﻌ ˆ)♡ m-minid, ( ͡o ω ͡o ) maxid);
    setmewgedmaxseawchedstatusid(seawchwesuwts, >w< maxid);
    setmewgedminseawchedstatusid(
        seawchwesuwts, /(^•ω•^) minid, 😳😳😳 twimstats.getwesuwtstwuncatedfwomtaiwcount() > 0);

    m-mewgedwesponse.setseawchwesuwts(seawchwesuwts);

    // ovewwide s-some components of the wesponse a-as appwopwiate to weaw-time. (U ᵕ U❁)
    s-seawchwesuwts.sethitcounts(aggwegatehitcountmap());
    if (accumuwatedwesponses.ismewgingpawtitionswithinatiew()
        && c-cweaweawwytewminationifweachingtiewbottom(mewgedwesponse)) {
      w-wecency_cweawed_eawwy_tewmination_count.incwement();
    } e-ewse {
      seteawwytewminationfowtwimmedwesuwts(mewgedwesponse, (˘ω˘) t-twimstats);
    }

    w-wesponsemessagebuiwdew.debugvewbose("hits: %s %s", 😳 totawwesuwtsize, (ꈍᴗꈍ) twimstats);
    wesponsemessagebuiwdew.debugvewbose(
        "hash pawtitioned eawwybiwd caww compweted successfuwwy: %s", :3 m-mewgedwesponse);

    featuweschemamewgew.cowwectandsetfeatuweschemainwesponse(
        s-seawchwesuwts,
        w-wequestcontext, /(^•ω•^)
        "mewgew_wecency_tiew", ^^;;
        accumuwatedwesponses.getsuccesswesponses());

    wetuwn mewgedwesponse;
  }

  /**
   * w-when we weached tiew bottom, o.O pagination can stop wowking e-even though we h-haven't got
   * aww wesuwts. 😳 e.g.
   * w-wesuwts fwom pawtition 1:  [101 91 81], UwU minseawchedstatusid i-is 81
   * wesuwts f-fwom pawtition 2:  [102 92], >w<  minseawchedstatusid i-is 92, n-nyot eawwy tewminated. o.O
   *
   * aftew mewge, we get [102, (˘ω˘) 101, òωó 92], with minwesuwtid == 92. nyaa~~ since w-wesuwts fwom
   * p-pawtition 2 i-is nyot eawwy tewminated, ( ͡o ω ͡o ) 92 i-is t-the tiew bottom hewe. 😳😳😳 since wesuwts a-awe
   * fiwtewed, ^•ﻌ•^ e-eawwy tewmination fow mewged w-wesuwt is set t-to twue, so bwendew wiww caww a-again, (˘ω˘)
   * with maxdocid == 91. (˘ω˘) this time we get w-wesuwt:
   * wesuwts fwom pawtition 1: [91 81], -.- m-minseawchedstatusid i-is 81
   * wesuwts fwom pawtition 2: [], ^•ﻌ•^ m-minseawchedstatusid is stiww 92
   * aftew mewge w-we get [] and minseawchedstatusid i-is stiww 92. /(^•ω•^) n-nyo pwogwess can be made on
   * pagination and cwients get stuck. (///ˬ///✿)
   *
   * s-so in this case, mya we cweaw the eawwy t-tewmination fwag t-to teww bwendew thewe is nyo mowe
   * w-wesuwt in this tiew. o.O tweets b-bewow tiew b-bottom wiww be missed, ^•ﻌ•^ but that awso happens
   * w-without this step, (U ᵕ U❁) as the nyext pagination caww w-wiww wetuwn empty w-wesuwts anyway. :3
   * so even i-if thewe is nyot ovewwap between t-tiews, (///ˬ///✿) this is s-stiww bettew. (///ˬ///✿)
   *
   * w-wetuwn twue if eawwy tewmination is cweawed due to this, othewwise wetuwn fawse. 🥺
   * to be safe, -.- we do nyothing hewe to keep existing behaviow and onwy ovewwide it in
   * stwictwecencywesponsemewgew. nyaa~~
   */
  pwotected b-boowean cweaweawwytewminationifweachingtiewbottom(eawwybiwdwesponse m-mewgedwesponse) {
    wetuwn fawse;
  }

  /**
   * detewmines i-if the mewged w-wesponse shouwd b-be eawwy-tewminated when it h-has exactwy as many
   * twimmed w-wesuwts as wequested, (///ˬ///✿) a-as is nyot eawwy-tewminated b-because of othew weasons. 🥺
   */
  p-pwotected b-boowean shouwdeawwytewminatewhenenoughtwimmedwesuwts() {
    wetuwn twue;
  }

  /**
   * i-if the e-end wesuwts wewe t-twimmed in any w-way, >w< wefwect that i-in the wesponse a-as a quewy that w-was
   * eawwy t-tewminated. rawr x3 a w-wesponse can be eithew (1) twuncated b-because we m-mewged mowe wesuwts t-than
   * nani was asked fow w-with nyumwesuwts, (⑅˘꒳˘) ow (2) we fiwtewed wesuwts that w-wewe smowew than the
   * mewged m-minseawchedstatusid. σωσ
   *
   * @pawam m-mewgedwesponse t-the mewged wesponse. XD
   * @pawam t-twimstats twim stats f-fow this mewge. -.-
   */
  pwivate v-void seteawwytewminationfowtwimmedwesuwts(
      eawwybiwdwesponse m-mewgedwesponse, >_<
      twimstats twimstats) {

    wesponsemessagebuiwdew.debugvewbose("checking fow mewge twimming, rawr t-twimstats %s", 😳😳😳 twimstats);

    e-eawwytewminationtwimmingstats s-stats = geteawwytewminationtwimmingstats();

    eawwytewminationinfo eawwytewminationinfo = mewgedwesponse.geteawwytewminationinfo();
    p-pweconditions.checknotnuww(eawwytewminationinfo);

    if (!eawwytewminationinfo.iseawwytewminated()) {
      i-if (twimstats.getminidfiwtewcount() > 0 || t-twimstats.getwesuwtstwuncatedfwomtaiwcount() > 0) {
        w-wesponsemessagebuiwdew.debugvewbose("setting eawwy tewmination, UwU twimstats: %s, w-wesuwts: %s",
            t-twimstats, (U ﹏ U) mewgedwesponse);

        e-eawwytewminationinfo.seteawwytewminated(twue);
        addeawwytewminationweasons(eawwytewminationinfo, (˘ω˘) twimstats);

        i-if (twimstats.getminidfiwtewcount() > 0
            && twimstats.getwesuwtstwuncatedfwomtaiwcount() > 0) {
          s-stats.getcountewfow(fiwtewed_and_twuncated).incwement();
        } e-ewse if (twimstats.getminidfiwtewcount() > 0) {
          s-stats.getcountewfow(fiwtewed).incwement();
        } ewse if (twimstats.getwesuwtstwuncatedfwomtaiwcount() > 0) {
          s-stats.getcountewfow(twuncated).incwement();
        } e-ewse {
          p-pweconditions.checkstate(fawse, /(^•ω•^) "invawid t-twimstats: %s", twimstats);
        }
      } e-ewse i-if ((computenumwesuwtstokeep() == m-mewgedwesponse.getseawchwesuwts().getwesuwtssize())
                 && s-shouwdeawwytewminatewhenenoughtwimmedwesuwts()) {
        e-eawwytewminationinfo.seteawwytewminated(twue);
        e-eawwytewminationinfo.addtomewgedeawwytewminationweasons(
            t-tewminated_cowwected_enough_wesuwts);
        stats.getcountewfow(tewminated_got_exact_num_wesuwts).incwement();
      } e-ewse {
        stats.getcountewfow(not_eawwy_tewminated).incwement();
      }
    } e-ewse {
      stats.getcountewfow(awweady_eawwy_tewminated).incwement();
      // even i-if the wesuwts wewe awweady m-mawked as eawwy t-tewminated, (U ﹏ U) we can a-add additionaw
      // weasons fow debugging (if the mewged w-wesuwts wewe fiwtewed o-ow twuncated). ^•ﻌ•^
      a-addeawwytewminationweasons(eawwytewminationinfo, >w< twimstats);
    }
  }

  pwivate void addeawwytewminationweasons(
      e-eawwytewminationinfo e-eawwytewminationinfo, ʘwʘ
      twimstats twimstats) {

    i-if (twimstats.getminidfiwtewcount() > 0) {
      e-eawwytewminationinfo.addtomewgedeawwytewminationweasons(
          mewging_eawwy_tewmination_weason_fiwtewed);
    }

    if (twimstats.getwesuwtstwuncatedfwomtaiwcount() > 0) {
      eawwytewminationinfo.addtomewgedeawwytewminationweasons(
          m-mewging_eawwy_tewmination_weason_twuncated);
    }
  }

  p-pwivate eawwytewminationtwimmingstats g-geteawwytewminationtwimmingstats() {
    i-if (accumuwatedwesponses.ismewgingpawtitionswithinatiew()) {
      wetuwn geteawwytewminationtwimmingstatsfowpawtitions();
    } e-ewse {
      w-wetuwn geteawwytewminationtwimmingstatsfowtiews();
    }
  }

  pwotected eawwytewminationtwimmingstats geteawwytewminationtwimmingstatsfowpawtitions() {
    w-wetuwn pawtition_mewging_eawwy_tewmination_twimming_stats;
  }

  pwotected eawwytewminationtwimmingstats geteawwytewminationtwimmingstatsfowtiews() {
    w-wetuwn tiew_mewging_eawwy_tewmination_twimming_stats;
  }

  /**
   * i-if we get enough w-wesuwts, òωó nyo need to go on. o.O
   * i-if one of the p-pawtitions eawwy tewminated, ( ͡o ω ͡o ) w-we can't go on ow ewse thewe couwd b-be a gap. mya
   */
  @ovewwide
  p-pubwic boowean s-shouwdeawwytewminatetiewmewge(int t-totawwesuwtsfwomsuccessfuwshawds,
                                                  boowean foundeawwytewmination) {


    i-int w-wesuwtswequested = c-computenumwesuwtstokeep();

    boowean shouwdeawwytewminate = f-foundeawwytewmination
        || totawwesuwtsfwomsuccessfuwshawds >= wesuwtswequested;

    if (shouwdeawwytewminate && t-totawwesuwtsfwomsuccessfuwshawds < w-wesuwtswequested) {
      w-wecency_tiew_mewge_eawwy_tewminated_with_not_enough_wesuwts.incwement();
    }

    wetuwn shouwdeawwytewminate;
  }

  /**
   * find the min status id t-that has been _compwetewy_ seawched a-acwoss aww pawtitions. >_< t-the
   * wawgest min status id acwoss a-aww pawtitions. rawr
   *
   * @wetuwn the min seawched s-status id found
   */
  p-pwotected w-wong findminfuwwyseawchedstatusid() {
    w-wist<wong> minids = a-accumuwatedwesponses.getminids();
    if (minids.isempty()) {
      wetuwn wong.min_vawue;
    }

    if (accumuwatedwesponses.ismewgingpawtitionswithinatiew()) {
      // when mewging pawtitions, >_< t-the min id shouwd be the w-wawgest among the min ids. (U ﹏ U)
      wetuwn cowwections.max(accumuwatedwesponses.getminids());
    } ewse {
      // w-when mewging tiews, rawr the min id shouwd be the smowest among the min ids. (U ᵕ U❁)
      w-wetuwn cowwections.min(accumuwatedwesponses.getminids());
    }
  }

  /**
   * f-find the max status id that has b-been _compwetewy_ seawched acwoss aww pawtitions. (ˆ ﻌ ˆ)♡ t-the
   * smowest m-max status id acwoss aww pawtitions. >_<
   *
   * t-this is whewe we weconciwe wepwication w-wag by sewecting the owdest maxid fwom the
   * pawtitions s-seawched. ^^;;
   *
   * @wetuwn the max seawched status id found
   */
   p-pwotected w-wong findmaxfuwwyseawchedstatusid() {
    wist<wong> m-maxids = accumuwatedwesponses.getmaxids();
    if (maxids.isempty()) {
      w-wetuwn wong.max_vawue;
    }
    cowwections.sowt(maxids);

    finaw wong nyewest = maxids.get(maxids.size() - 1);
    finaw wong nyewesttimestamp = s-snowfwakeidpawsew.gettimestampfwomtweetid(newest);

    f-fow (int i = 0; i-i < maxids.size(); i-i++) {
      wong owdest = maxids.get(i);
      w-wong owdesttimestamp = snowfwakeidpawsew.gettimestampfwomtweetid(owdest);
      w-wong dewtams = nyewesttimestamp - owdesttimestamp;

      i-if (i == 0) {
        wog.debug("max dewta is {}", ʘwʘ d-dewtams);
      }

      if (dewtams < awwowed_wepwication_wag_ms) {
        i-if (i != 0) {
          w-wog.debug("{} pawtition w-wepwicas wagging m-mowe than {} m-ms", 😳😳😳 i, awwowed_wepwication_wag_ms);
        }
        wetuwn owdest;
      }
    }

    // can't g-get hewe - by this point owdest == nyewest, UwU and d-dewta is 0. OwO
    wetuwn nyewest;
  }

  /**
   * twim the thwiftseawchwesuwts if we have enough w-wesuwts, :3 to wetuwn t-the fiwst
   * 'computenumwesuwtstokeep()' nyumbew o-of wesuwts. -.-
   *
   * i-if w-we don't have enough wesuwts aftew t-twimming, 🥺 this function wiww fiwst twy to back f-fiww
   * owdew wesuwts, -.- then n-nyewew wesuwts
   *
   * @pawam seawchwesuwts thwiftseawchwesuwts that howd the t-to be twimmed wist<thwiftseawchwesuwt>
   * @wetuwn t-twimstats containing statistics a-about how many wesuwts being w-wemoved
   */
  p-pwotected twimstats twimwesuwts(
      t-thwiftseawchwesuwts s-seawchwesuwts, -.-
      wong mewgedmin, (U ﹏ U)
      w-wong mewgedmax) {
    if (!seawchwesuwts.issetwesuwts() || seawchwesuwts.getwesuwtssize() == 0) {
      // nyo wesuwts, rawr nyo t-twimming nyeeded
      wetuwn t-twimstats.empty_stats;
    }

    if (wequestcontext.getwequest().getseawchquewy().issetseawchstatusids()) {
      // nyot a nyowmaw s-seawch, mya nyo t-twimming nyeeded
      w-wetuwn twimstats.empty_stats;
    }

    t-twimstats twimstats = n-nyew twimstats();
    twimexactdups(seawchwesuwts, ( ͡o ω ͡o ) t-twimstats);

    int n-numwesuwtswequested = computenumwesuwtstokeep();
    i-if (shouwdskiptwimmingwhennotenoughwesuwts(seawchwesuwts, /(^•ω•^) nyumwesuwtswequested)) {
      //////////////////////////////////////////////////////////
      // w-we don't have enough wesuwts, >_< wet's nyot do twimming
      //////////////////////////////////////////////////////////
      wetuwn twimstats;
    }

    i-if (accumuwatedwesponses.ismewgingpawtitionswithinatiew()) {
      t-twimwesuwtsbasedseawchedwange(
          seawchwesuwts, (✿oωo) twimstats, 😳😳😳 nyumwesuwtswequested, (ꈍᴗꈍ) m-mewgedmin, 🥺 mewgedmax);
    }

    // w-wespect "computenumwesuwtstokeep()" h-hewe, mya onwy keep "computenumwesuwtstokeep()" wesuwts. (ˆ ﻌ ˆ)♡
    twuncatewesuwts(seawchwesuwts, twimstats);

    wetuwn t-twimstats;
  }

  /**
   * when thewe's nyot enough w-wesuwts, (⑅˘꒳˘) we don't wemove wesuwts b-based on the s-seawched wange. òωó
   * this has a-a twadeoff:  with t-this, o.O we don't w-weduce ouw wecaww w-when we awweady d-don't have enough
   * w-wesuwts. XD howevew, (˘ω˘) with this, (ꈍᴗꈍ) we can wose wesuwts whiwe paginating because we wetuwn wesuwts
   * o-outside o-of the vawid s-seawched wange. >w<
   */
  p-pwotected b-boowean shouwdskiptwimmingwhennotenoughwesuwts(
      t-thwiftseawchwesuwts seawchwesuwts, XD int nyumwesuwtswequested) {
    wetuwn seawchwesuwts.getwesuwtssize() <= n-nyumwesuwtswequested;
  }


  /**
   * t-twim wesuwts based on seawch wange. -.- the seawch wange [x, y-y] is detewmined b-by:
   *   x-x is the maximun of the minimun seawch ids;
   *   y-y is the minimun of the maximum seawch ids. ^^;;
   *
   * i-ids out s-side of this wange awe wemoved. XD
   * if we do nyot g-get enough wesuwts aftew the w-wemovaw, :3 we add i-ids back untiw we get enough wesuwts. σωσ
   * w-we fiwst a-add ids back f-fwom the owdew s-side back. XD if thewe's s-stiww nyot e-enough wesuwts, :3
   * we stawt a-adding ids fwom t-the nyewew side back. rawr
   */
  pwivate v-void twimwesuwtsbasedseawchedwange(thwiftseawchwesuwts seawchwesuwts, 😳
                                             twimstats t-twimstats,
                                             int nyumwesuwtswequested, 😳😳😳
                                             w-wong mewgedmin, (ꈍᴗꈍ)
                                             wong mewgedmax) {
    ///////////////////////////////////////////////////////////////////
    // w-we have mowe wesuwts t-than wequested, 🥺 wet's do some twimming
    ///////////////////////////////////////////////////////////////////

    // s-save the owiginaw wesuwts befowe twimming
    w-wist<thwiftseawchwesuwt> o-owiginawwesuwts = seawchwesuwts.getwesuwts();

    fiwtewwesuwtsbymewgedminmaxids(seawchwesuwts, ^•ﻌ•^ m-mewgedmax, mewgedmin, XD t-twimstats);

    // this d-does happen. ^•ﻌ•^ it is hawd to say nyani we shouwd d-do hewe so we j-just wetuwn the owiginaw
    // w-wesuwt hewe. ^^;;
    i-if (seawchwesuwts.getwesuwtssize() == 0) {
      wecency_zewo_wesuwt_count_aftew_fiwtewing_max_min_ids.incwement();
      seawchwesuwts.setwesuwts(owiginawwesuwts);

      // c-cwean up min/mix f-fiwtewed count, ʘwʘ s-since we'we bwinging b-back nanievew we just fiwtewed. OwO
      twimstats.cweawmaxidfiwtewcount();
      twimstats.cweawminidfiwtewcount();

      if (wog.isdebugenabwed() || wesponsemessagebuiwdew.isdebugmode()) {
        stwing ewwmsg = "no twimming i-is done a-as fiwtewed wesuwts i-is empty. 🥺 "
            + "maxid=" + m-mewgedmax + ",minid=" + m-mewgedmin;
        w-wog.debug(ewwmsg);
        wesponsemessagebuiwdew.append(ewwmsg + "\n");
      }
    } ewse {
      // o-oops! (⑅˘꒳˘) w-we'we twimming too many wesuwts. (///ˬ///✿) w-wet's put some b-back
      if (seawchwesuwts.getwesuwtssize() < nyumwesuwtswequested) {
        wecency_twimmed_too_many_wesuwts_count.incwement();

        w-wist<thwiftseawchwesuwt> twimmedwesuwts = seawchwesuwts.getwesuwts();
        w-wong fiwsttwimmedwesuwtid = t-twimmedwesuwts.get(0).getid();
        wong w-wasttwimmedwesuwtid = twimmedwesuwts.get(twimmedwesuwts.size() - 1).getid();

        // f-fiwst, (✿oωo) t-twy to back f-fiww with owdew wesuwts
        i-int i = 0;
        f-fow (; i < owiginawwesuwts.size(); ++i) {
          thwiftseawchwesuwt w-wesuwt = owiginawwesuwts.get(i);
          i-if (wesuwt.getid() < w-wasttwimmedwesuwtid) {
            t-twimmedwesuwts.add(wesuwt);
            twimstats.decweaseminidfiwtewcount();
            i-if (twimmedwesuwts.size() >= nyumwesuwtswequested) {
              bweak;
            }
          }
        }

        // s-stiww nyot enough wesuwts? back fiww with nyewew wesuwts
        // find the owdest of the nyewew wesuwts
        i-if (twimmedwesuwts.size() < nyumwesuwtswequested) {
          // stiww nyot enough wesuwts? back fiww with nyewew wesuwts
          // find the owdest of the n-nyewew wesuwts
          fow (i = owiginawwesuwts.size() - 1; i >= 0; --i) {
            t-thwiftseawchwesuwt wesuwt = o-owiginawwesuwts.get(i);
            if (wesuwt.getid() > fiwsttwimmedwesuwtid) {
              twimmedwesuwts.add(wesuwt);
              twimstats.decweasemaxidfiwtewcount();
              i-if (twimmedwesuwts.size() >= nyumwesuwtswequested) {
                b-bweak;
              }
            }
          }

          // nyewew wesuwts w-wewe added t-to the back of the wist, we-sowt
          cowwections.sowt(twimmedwesuwts, nyaa~~ w-wesuwtcompawatows.id_compawatow);
        }
      }
    }
  }

  pwotected void setmewgedminseawchedstatusid(
      thwiftseawchwesuwts s-seawchwesuwts, >w<
      wong cuwwentmewgedmin, (///ˬ///✿)
      b-boowean wesuwtswewetwimmed) {
    if (accumuwatedwesponses.getminids().isempty()) {
      w-wetuwn;
    }

    wong mewged;
    i-if (seawchwesuwts == n-nyuww
        || !seawchwesuwts.issetwesuwts()
        || seawchwesuwts.getwesuwtssize() == 0) {
      mewged = cuwwentmewgedmin;
    } e-ewse {
      wist<thwiftseawchwesuwt> wesuwts = seawchwesuwts.getwesuwts();
      w-wong fiwstwesuwtid = wesuwts.get(0).getid();
      wong wastwesuwtid = wesuwts.get(wesuwts.size() - 1).getid();
      mewged = m-math.min(fiwstwesuwtid, rawr w-wastwesuwtid);
      if (!wesuwtswewetwimmed) {
        // i-if the wesuwts w-wewe twimmed, (U ﹏ U) we want to set m-minseawchedstatusid to the smowest
        // tweet id in the wesponse. ^•ﻌ•^ othewwise, (///ˬ///✿) we want to t-take the min between t-that, o.O and
        // the cuwwent m-minseawchedstatusid. >w<
        m-mewged = math.min(mewged, nyaa~~ cuwwentmewgedmin);
      }
    }

    s-seawchwesuwts.setminseawchedstatusid(mewged);
  }

  pwivate void setmewgedmaxseawchedstatusid(
      t-thwiftseawchwesuwts seawchwesuwts, òωó
      wong cuwwentmewgedmax) {
    if (accumuwatedwesponses.getmaxids().isempty()) {
      w-wetuwn;
    }

    w-wong mewged;
    if (seawchwesuwts == nyuww
        || !seawchwesuwts.issetwesuwts()
        || s-seawchwesuwts.getwesuwtssize() == 0) {
      mewged = cuwwentmewgedmax;
    } ewse {
      wist<thwiftseawchwesuwt> wesuwts = seawchwesuwts.getwesuwts();
      wong fiwstwesuwtid = wesuwts.get(0).getid();
      w-wong w-wastwesuwtid = wesuwts.get(wesuwts.size() - 1).getid();
      w-wong maxwesuwtid = m-math.max(fiwstwesuwtid, (U ᵕ U❁) wastwesuwtid);
      m-mewged = math.max(maxwesuwtid, (///ˬ///✿) cuwwentmewgedmax);
    }

    seawchwesuwts.setmaxseawchedstatusid(mewged);
  }

  pwotected static void fiwtewwesuwtsbymewgedminmaxids(
      thwiftseawchwesuwts wesuwts, (✿oωo) wong m-maxstatusid, 😳😳😳 wong minstatusid, (✿oωo) twimstats twimstats) {
    wist<thwiftseawchwesuwt> twimedwesuwts =
        w-wists.newawwaywistwithcapacity(wesuwts.getwesuwtssize());

    f-fow (thwiftseawchwesuwt w-wesuwt : wesuwts.getwesuwts()) {
      wong statusid = wesuwt.getid();

      if (statusid > maxstatusid) {
        t-twimstats.incweasemaxidfiwtewcount();
      } e-ewse if (statusid < m-minstatusid) {
        twimstats.incweaseminidfiwtewcount();
      } ewse {
        t-twimedwesuwts.add(wesuwt);
      }
    }

    wesuwts.setwesuwts(twimedwesuwts);
  }
}
