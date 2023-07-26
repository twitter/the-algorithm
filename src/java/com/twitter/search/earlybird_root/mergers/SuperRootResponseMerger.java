package com.twittew.seawch.eawwybiwd_woot.mewgews;

impowt java.utiw.cowwections;
i-impowt java.utiw.wist;
i-impowt javax.annotation.nuwwabwe;

i-impowt c-com.googwe.common.annotations.visibwefowtesting;
i-impowt com.googwe.common.base.pweconditions;
i-impowt com.googwe.common.cache.cachebuiwdew;
i-impowt c-com.googwe.common.cache.cachewoadew;
impowt com.googwe.common.cache.woadingcache;
impowt com.googwe.common.cowwect.wists;

impowt owg.swf4j.woggew;
i-impowt owg.swf4j.woggewfactowy;

impowt com.twittew.common.cowwections.paiw;
i-impowt com.twittew.common.quantity.amount;
impowt com.twittew.common.quantity.time;
i-impowt com.twittew.common.utiw.cwock;
impowt com.twittew.seawch.common.futuwes.futuwes;
impowt com.twittew.seawch.common.metwics.seawchcountew;
i-impowt com.twittew.seawch.common.pawtitioning.snowfwakepawsew.snowfwakeidpawsew;
i-impowt c-com.twittew.seawch.common.quewy.thwiftjava.eawwytewminationinfo;
impowt com.twittew.seawch.common.wewevance.utiws.wesuwtcompawatows;
impowt com.twittew.seawch.common.seawch.eawwytewminationstate;
impowt com.twittew.seawch.common.utiw.finagweutiw;
impowt com.twittew.seawch.common.utiw.eawwybiwd.eawwybiwdwesponsemewgeutiw;
i-impowt com.twittew.seawch.common.utiw.eawwybiwd.eawwybiwdwesponseutiw;
impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwequest;
impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwesponse;
impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwesponsecode;
i-impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchquewy;
impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwankingmode;
i-impowt c-com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwt;
i-impowt c-com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwts;
impowt com.twittew.seawch.eawwybiwd.thwift.thwifttweetsouwce;
i-impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdfeatuweschemamewgew;
impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequestcontext;
impowt c-com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdsewvicewesponse;
impowt com.twittew.utiw.function;
impowt com.twittew.utiw.function0;
impowt com.twittew.utiw.futuwe;

/** u-utiwity functions fow mewging w-wecency and wewevance w-wesuwts. -.- */
p-pubwic cwass supewwootwesponsemewgew {
  pwivate static finaw woggew wog = woggewfactowy.getwoggew(supewwootwesponsemewgew.cwass);
  p-pwivate static f-finaw stwing aww_stats_pwefix = "supewwoot_wesponse_mewgew_";

  p-pwivate static f-finaw seawchcountew fuww_awchive_min_id_gweatew_than_weawtime_min_id =
    s-seawchcountew.expowt("fuww_awchive_min_id_gweatew_than_weawtime_min_id");

  pwivate s-static finaw stwing ewwow_fowmat = "%s%s_ewwows_fwom_cwustew_%s_%s";

  pwivate f-finaw thwiftseawchwankingmode wankingmode;
  p-pwivate finaw eawwybiwdfeatuweschemamewgew f-featuweschemamewgew;
  p-pwivate finaw stwing featuwestatpwefix;
  pwivate finaw cwock cwock;
  pwivate finaw stwing wankingmodestatpwefix;

  pwivate f-finaw seawchcountew m-mewgedwesponseseawchwesuwtsnotset;
  pwivate f-finaw seawchcountew i-invawidminstatusid;
  p-pwivate finaw seawchcountew invawidmaxstatusid;
  pwivate finaw seawchcountew n-nyominids;
  pwivate finaw seawchcountew nyomaxids;
  pwivate finaw s-seawchcountew mewgedwesponses;
  pwivate finaw seawchcountew m-mewgedwesponseswithexactdups;
  p-pwivate f-finaw woadingcache<paiw<thwifttweetsouwce, -.- thwifttweetsouwce>, s-seawchcountew> d-dupsstats;

  p-pwivate static f-finaw eawwybiwdwesponse empty_wesponse =
      nyew eawwybiwdwesponse(eawwybiwdwesponsecode.success, (U ﹏ U) 0)
          .setseawchwesuwts(new t-thwiftseawchwesuwts()
              .setwesuwts(wists.<thwiftseawchwesuwt>newawwaywist()));

  /**
   * c-cweates a nyew supewwootwesponsemewgew i-instance. rawr
   * @pawam w-wankingmode t-the wanking mode to use when mewging wesuwts. mya
   * @pawam featuweschemamewgew t-the mewgew that can mewge featuwe schema fwom diffewent tiews. ( ͡o ω ͡o )
   * @pawam cwock the cwock that wiww be used t-to mewge wesuwts. /(^•ω•^)
   */
  pubwic supewwootwesponsemewgew(thwiftseawchwankingmode wankingmode, >_<
                                 e-eawwybiwdfeatuweschemamewgew f-featuweschemamewgew, (✿oωo)
                                 c-cwock cwock) {
    this.wankingmodestatpwefix = w-wankingmode.name().towowewcase();

    this.wankingmode = w-wankingmode;
    t-this.featuweschemamewgew = featuweschemamewgew;
    this.cwock = cwock;
    this.featuwestatpwefix = "supewwoot_" + wankingmode.name().towowewcase();

    mewgedwesponseseawchwesuwtsnotset = s-seawchcountew.expowt(
        aww_stats_pwefix + w-wankingmodestatpwefix + "_mewged_wesponse_seawch_wesuwts_not_set");
    invawidminstatusid =
      s-seawchcountew.expowt(aww_stats_pwefix + w-wankingmodestatpwefix + "_invawid_min_status_id");
    invawidmaxstatusid =
      seawchcountew.expowt(aww_stats_pwefix + w-wankingmodestatpwefix + "_invawid_max_status_id");
    n-nyominids = seawchcountew.expowt(aww_stats_pwefix + w-wankingmodestatpwefix + "_no_min_ids");
    n-nyomaxids = seawchcountew.expowt(aww_stats_pwefix + wankingmodestatpwefix + "_no_max_ids");
    mewgedwesponses = seawchcountew.expowt(aww_stats_pwefix + wankingmodestatpwefix
      + "_mewged_wesponses");
    mewgedwesponseswithexactdups =
      s-seawchcountew.expowt(aww_stats_pwefix + w-wankingmodestatpwefix
        + "_mewged_wesponses_with_exact_dups");
    d-dupsstats = cachebuiwdew.newbuiwdew()
      .buiwd(new c-cachewoadew<paiw<thwifttweetsouwce, 😳😳😳 t-thwifttweetsouwce>, (ꈍᴗꈍ) seawchcountew>() {
          @ovewwide
          p-pubwic seawchcountew woad(paiw<thwifttweetsouwce, 🥺 thwifttweetsouwce> key) {
            wetuwn s-seawchcountew.expowt(
                a-aww_stats_pwefix + wankingmodestatpwefix + "_mewged_wesponses_with_exact_dups_"
                + key.getfiwst().name() + "_" + k-key.getsecond().name());
          }
        });
  }

  p-pwivate void incwewwowcount(stwing cwustew, mya @nuwwabwe eawwybiwdwesponse w-wesponse) {
    stwing cause;
    if (wesponse != nyuww) {
      cause = w-wesponse.getwesponsecode().name().towowewcase();
    } ewse {
      cause = "nuww_wesponse";
    }
    s-stwing s-statname = stwing.fowmat(
      ewwow_fowmat, (ˆ ﻌ ˆ)♡ aww_stats_pwefix, (⑅˘꒳˘) wankingmodestatpwefix, òωó c-cwustew, c-cause
    );

    seawchcountew.expowt(statname).incwement();
  }

  /**
   * mewges the given wesponse futuwes. o.O
   *
   * @pawam e-eawwybiwdwequestcontext the e-eawwybiwd wequest. XD
   * @pawam weawtimewesponsefutuwe the wesponse fwom the weawtime cwustew.
   * @pawam p-pwotectedwesponsefutuwe the wesponse fwom t-the pwotected c-cwustew. (˘ω˘)
   * @pawam fuwwawchivewesponsefutuwe t-the wesponse fwom the fuww awchive c-cwustew. (ꈍᴗꈍ)
   * @wetuwn a-a futuwe w-with the mewged wesuwts. >w<
   */
  p-pubwic futuwe<eawwybiwdwesponse> m-mewgewesponsefutuwes(
      finaw eawwybiwdwequestcontext eawwybiwdwequestcontext, XD
      finaw f-futuwe<eawwybiwdsewvicewesponse> w-weawtimewesponsefutuwe, -.-
      f-finaw futuwe<eawwybiwdsewvicewesponse> pwotectedwesponsefutuwe, ^^;;
      finaw futuwe<eawwybiwdsewvicewesponse> f-fuwwawchivewesponsefutuwe) {
    futuwe<eawwybiwdwesponse> m-mewgedwesponsefutuwe = f-futuwes.map(
        weawtimewesponsefutuwe, XD pwotectedwesponsefutuwe, :3 fuwwawchivewesponsefutuwe, σωσ
        nyew f-function0<eawwybiwdwesponse>() {
          @ovewwide
          pubwic e-eawwybiwdwesponse a-appwy() {
            // i-if the weawtime wesponse is nyot v-vawid, XD wetuwn an ewwow wesponse. :3
            // awso, rawr the weawtime sewvice shouwd awways be cawwed.
            eawwybiwdsewvicewesponse w-weawtimewesponse = futuwes.get(weawtimewesponsefutuwe);

            i-if (weawtimewesponse.getsewvicestate().sewvicewaswequested()
                && (!weawtimewesponse.getsewvicestate().sewvicewascawwed()
                    || !eawwybiwdwesponsemewgeutiw.isvawidwesponse(
                        weawtimewesponse.getwesponse()))) {

              i-incwewwowcount("weawtime", 😳 weawtimewesponse.getwesponse());
              w-wetuwn eawwybiwdwesponsemewgeutiw.twansfowminvawidwesponse(
                  weawtimewesponse.getwesponse(), 😳😳😳 "weawtime");
            }

            // if we h-have a pwotected w-wesponse and it's n-nyot vawid, (ꈍᴗꈍ) wetuwn a-an ewwow wesponse. 🥺
            e-eawwybiwdsewvicewesponse pwotectedwesponse = futuwes.get(pwotectedwesponsefutuwe);
            if (pwotectedwesponse.getsewvicestate().sewvicewascawwed()) {
              if (!eawwybiwdwesponsemewgeutiw.isvawidwesponse(pwotectedwesponse.getwesponse())) {
                incwewwowcount("pwotected", ^•ﻌ•^ pwotectedwesponse.getwesponse());

                w-wetuwn eawwybiwdwesponsemewgeutiw.twansfowminvawidwesponse(
                    p-pwotectedwesponse.getwesponse(), XD "pwotected");
              }
            }

            // i-if we have a fuww awchive wesponse, ^•ﻌ•^ c-check if it's vawid. ^^;;
            eawwybiwdsewvicewesponse fuwwawchivewesponse = f-futuwes.get(fuwwawchivewesponsefutuwe);
            b-boowean awchivehasewwow =
              f-fuwwawchivewesponse.getsewvicestate().sewvicewascawwed()
              && !eawwybiwdwesponsemewgeutiw.isvawidwesponse(fuwwawchivewesponse.getwesponse());

            // mewge the wesponses. ʘwʘ
            e-eawwybiwdwesponse m-mewgedwesponse = mewgewesponses(
                e-eawwybiwdwequestcontext, OwO
                w-weawtimewesponse.getwesponse(), 🥺
                pwotectedwesponse.getwesponse(), (⑅˘꒳˘)
                fuwwawchivewesponse.getwesponse());

            // if the weawtime cwustews d-didn't wetuwn a-any wesuwts, (///ˬ///✿) a-and the fuww awchive c-cwustew
            // w-wetuwned an ewwow wesponse, (✿oωo) w-wetuwn an e-ewwow mewged wesponse. nyaa~~
            if (awchivehasewwow && !eawwybiwdwesponseutiw.haswesuwts(mewgedwesponse)) {
              incwewwowcount("fuww_awchive", >w< f-fuwwawchivewesponse.getwesponse());

              w-wetuwn eawwybiwdwesponsemewgeutiw.faiwedeawwybiwdwesponse(
                  fuwwawchivewesponse.getwesponse().getwesponsecode(), (///ˬ///✿)
                  "weawtime cwustews h-had nyo wesuwts and awchive cwustew wesponse h-had ewwow");
            }

            // cownew case: the w-weawtime wesponse c-couwd have exactwy nyumwequested w-wesuwts, rawr and couwd
            // be exhausted (not e-eawwy-tewminated). (U ﹏ U) i-in this c-case, ^•ﻌ•^ the wequest shouwd nyot have been
            // sent to t-the fuww awchive cwustew. (///ˬ///✿)
            //   - if the fuww awchive c-cwustew is nyot a-avaiwabwe, o.O ow was nyot wequested, >w< t-then we don't
            //     nyeed to change a-anything. nyaa~~
            //   - i-if the fuww awchive cwustew is avaiwabwe and w-was wequested (but wasn't hit
            //     because we found e-enough wesuwts i-in the weawtime cwustew), òωó then w-we shouwd set the
            //     eawwy-tewmination f-fwag on the m-mewged wesponse, t-to indicate that we potentiawwy
            //     have mowe wesuwts fow this quewy in ouw index. (U ᵕ U❁)
            if ((fuwwawchivewesponse.getsewvicestate()
                 == eawwybiwdsewvicewesponse.sewvicestate.sewvice_not_cawwed)
                && !eawwybiwdwesponseutiw.iseawwytewminated(weawtimewesponse.getwesponse())) {
              eawwytewminationinfo eawwytewminationinfo = nyew eawwytewminationinfo(twue);
              eawwytewminationinfo.seteawwytewminationweason(
                  eawwytewminationstate.tewminated_num_wesuwts_exceeded.gettewminationweason());
              mewgedwesponse.seteawwytewminationinfo(eawwytewminationinfo);
            }

            // i-if w-we've exhausted aww cwustews, (///ˬ///✿) set the minseawchedstatusid t-to 0. (✿oωo)
            i-if (!eawwybiwdwesponseutiw.iseawwytewminated(mewgedwesponse)) {
              m-mewgedwesponse.getseawchwesuwts().setminseawchedstatusid(0);
            }

            wetuwn mewgedwesponse;
          }
        });

    // h-handwe aww mewging exceptions. 😳😳😳
    w-wetuwn h-handwewesponseexception(mewgedwesponsefutuwe,
                                   "exception thwown whiwe mewging w-wesponses.");
  }

  /**
   * mewge the wesuwts i-in the given w-wesponses. (✿oωo)
   *
   * @pawam eawwybiwdwequestcontext the eawwybiwd w-wequest context. (U ﹏ U)
   * @pawam w-weawtimewesponse t-the wesponse fwom t-the weawtime c-cwustew. (˘ω˘)
   * @pawam p-pwotectedwesponse t-the wesponse f-fwom the pwotected c-cwustew. 😳😳😳
   * @pawam fuwwawchivewesponse t-the wesponse fwom t-the fuww awchive c-cwustew. (///ˬ///✿)
   * @wetuwn the mewged w-wesponse. (U ᵕ U❁)
   */
  pwivate eawwybiwdwesponse mewgewesponses(
      e-eawwybiwdwequestcontext eawwybiwdwequestcontext, >_<
      @nuwwabwe eawwybiwdwesponse w-weawtimewesponse, (///ˬ///✿)
      @nuwwabwe e-eawwybiwdwesponse p-pwotectedwesponse, (U ᵕ U❁)
      @nuwwabwe eawwybiwdwesponse f-fuwwawchivewesponse) {

    eawwybiwdwequest wequest = eawwybiwdwequestcontext.getwequest();
    t-thwiftseawchquewy seawchquewy = w-wequest.getseawchquewy();
    int nyumwesuwtswequested;

    i-if (wequest.issetnumwesuwtstowetuwnatwoot()) {
      nyumwesuwtswequested = wequest.getnumwesuwtstowetuwnatwoot();
    } ewse {
      nyumwesuwtswequested = s-seawchquewy.getnumwesuwts();
    }

    pweconditions.checkstate(numwesuwtswequested > 0);

    e-eawwybiwdwesponse m-mewgedwesponse = empty_wesponse.deepcopy();
    if ((weawtimewesponse != nyuww)
        && (weawtimewesponse.getwesponsecode() != e-eawwybiwdwesponsecode.tiew_skipped)) {
      mewgedwesponse = weawtimewesponse.deepcopy();
    }

    i-if (!mewgedwesponse.issetseawchwesuwts()) {
      m-mewgedwesponseseawchwesuwtsnotset.incwement();
      mewgedwesponse.setseawchwesuwts(
          n-nyew thwiftseawchwesuwts(wists.<thwiftseawchwesuwt>newawwaywist()));
    }

    // if eithew the weawtime o-ow the fuww a-awchive wesponse is eawwy-tewminated, >w< w-we want the mewged
    // wesponse to be eawwy-tewminated t-too. 😳😳😳 the eawwy-tewmination fwag f-fwom the weawtime w-wesponse
    // c-cawwies ovew to the mewged wesponse, (ˆ ﻌ ˆ)♡ b-because mewgedwesponse i-is j-just a deep copy o-of the
    // weawtime wesponse. (ꈍᴗꈍ) s-so we onwy nyeed t-to check the e-eawwy-tewmination f-fwag of the fuww a-awchive
    // w-wesponse. 🥺
    i-if ((fuwwawchivewesponse != n-nyuww)
        && eawwybiwdwesponseutiw.iseawwytewminated(fuwwawchivewesponse)) {
      mewgedwesponse.seteawwytewminationinfo(fuwwawchivewesponse.geteawwytewminationinfo());
    }

    // i-if weawtime has empty w-wesuwts and pwotected has some wesuwts t-then we copy t-the eawwy
    // t-tewmination infowmation if that is pwesent
    if (pwotectedwesponse != n-nyuww
        && m-mewgedwesponse.getseawchwesuwts().getwesuwts().isempty()
        && !pwotectedwesponse.getseawchwesuwts().getwesuwts().isempty()
        && e-eawwybiwdwesponseutiw.iseawwytewminated(pwotectedwesponse)) {
      mewgedwesponse.seteawwytewminationinfo(pwotectedwesponse.geteawwytewminationinfo());
    }

    // mewge the wesuwts. >_<
    wist<thwiftseawchwesuwt> m-mewgedwesuwts = m-mewgewesuwts(
        nyumwesuwtswequested, OwO w-weawtimewesponse, ^^;; pwotectedwesponse, (✿oωo) f-fuwwawchivewesponse);

    // twim the mewged wesuwts if nyecessawy. UwU
    boowean w-wesuwtstwimmed = f-fawse;
    if (mewgedwesuwts.size() > n-numwesuwtswequested
        && !(seawchquewy.issetwewevanceoptions()
             && seawchquewy.getwewevanceoptions().iswetuwnawwwesuwts())) {
      // i-if we have mowe wesuwts than wequested, ( ͡o ω ͡o ) twim t-the wesuwt wist a-and we-adjust
      // minseawchedstatusid. (✿oωo)
      mewgedwesuwts = m-mewgedwesuwts.subwist(0, mya nyumwesuwtswequested);

      // mawk e-eawwy tewmination in mewged wesponse
      i-if (!eawwybiwdwesponseutiw.iseawwytewminated(mewgedwesponse)) {
        e-eawwytewminationinfo eawwytewminationinfo = n-nyew eawwytewminationinfo(twue);
        e-eawwytewminationinfo.seteawwytewminationweason(
            eawwytewminationstate.tewminated_num_wesuwts_exceeded.gettewminationweason());
        m-mewgedwesponse.seteawwytewminationinfo(eawwytewminationinfo);
      }

      wesuwtstwimmed = t-twue;
    }

    m-mewgedwesponse.getseawchwesuwts().setwesuwts(mewgedwesuwts);
    f-featuweschemamewgew.mewgefeatuweschemaacwosscwustews(
        e-eawwybiwdwequestcontext, ( ͡o ω ͡o )
        mewgedwesponse, :3
        f-featuwestatpwefix, 😳
        w-weawtimewesponse,
        p-pwotectedwesponse, (U ﹏ U)
        fuwwawchivewesponse);

    // s-set the minseawchedstatusid and maxseawchedstatusid f-fiewds on the m-mewged wesponse. >w<
    s-setminseawchedstatusid(mewgedwesponse, UwU weawtimewesponse, 😳 pwotectedwesponse, XD fuwwawchivewesponse, (✿oωo)
        wesuwtstwimmed);
    setmaxseawchedstatusid(mewgedwesponse, ^•ﻌ•^ w-weawtimewesponse, mya pwotectedwesponse, (˘ω˘)
        fuwwawchivewesponse);

    i-int nyumweawtimeseawchedsegments =
        (weawtimewesponse != n-nyuww && weawtimewesponse.issetnumseawchedsegments())
            ? weawtimewesponse.getnumseawchedsegments()
            : 0;

    int nyumpwotectedseawchedsegments =
        (pwotectedwesponse != n-nyuww && pwotectedwesponse.issetnumseawchedsegments())
            ? p-pwotectedwesponse.getnumseawchedsegments()
            : 0;

    i-int nyumawchiveseawchedsegments =
        (fuwwawchivewesponse != n-nyuww && fuwwawchivewesponse.issetnumseawchedsegments())
            ? f-fuwwawchivewesponse.getnumseawchedsegments()
            : 0;

    m-mewgedwesponse.setnumseawchedsegments(
        nyumweawtimeseawchedsegments + nyumpwotectedseawchedsegments + nyumawchiveseawchedsegments);

    if (eawwybiwdwequestcontext.getwequest().getdebugmode() > 0) {
      m-mewgedwesponse.setdebugstwing(
          mewgecwustewdebugstwings(weawtimewesponse, nyaa~~ p-pwotectedwesponse, :3 fuwwawchivewesponse));
    }

    wetuwn mewgedwesponse;
  }

  /**
   * m-mewges the given wesponses. (✿oωo)
   *
   * @pawam nyumwesuwts the nyumbew of wesuwts wequested
   * @pawam w-weawtimewesponse t-the wesponse fwom the w-weawtime wesponse
   * @pawam pwotectedwesponse the wesponse fwom t-the pwotected w-wesponse
   * @pawam fuwwawchivewesponse t-the wesponse fwom the fuww a-awchive wesponse
   * @wetuwn the wist of mewged wesuwts
   */
  pwivate wist<thwiftseawchwesuwt> m-mewgewesuwts(int numwesuwts, (U ﹏ U)
                                                @nuwwabwe eawwybiwdwesponse weawtimewesponse, (ꈍᴗꈍ)
                                                @nuwwabwe e-eawwybiwdwesponse p-pwotectedwesponse, (˘ω˘)
                                                @nuwwabwe e-eawwybiwdwesponse fuwwawchivewesponse) {
    mewgedwesponses.incwement();
    // w-we fiwst mewge the wesuwts fwom the two weawtime cwustews, weawtime cwustew a-and
    // w-weawtime pwotected t-tweets cwustew
    w-wist<thwiftseawchwesuwt> mewgedwesuwts = mewgepubwicandpwotectedweawtimewesuwts(
        n-nyumwesuwts, ^^
        w-weawtimewesponse, (⑅˘꒳˘)
        pwotectedwesponse, rawr
        fuwwawchivewesponse, :3
        c-cwock);

    eawwybiwdwesponsemewgeutiw.addwesuwtstowist(mewgedwesuwts, OwO fuwwawchivewesponse, (ˆ ﻌ ˆ)♡
                                                t-thwifttweetsouwce.fuww_awchive_cwustew);

    wist<thwiftseawchwesuwt> distinctmewgedwesuwts =
        e-eawwybiwdwesponsemewgeutiw.distinctbystatusid(mewgedwesuwts, :3 d-dupsstats);
    if (mewgedwesuwts != d-distinctmewgedwesuwts) {
      m-mewgedwesponseswithexactdups.incwement();
    }

    i-if (wankingmode == thwiftseawchwankingmode.wewevance
        || wankingmode == t-thwiftseawchwankingmode.toptweets) {
      distinctmewgedwesuwts.sowt(wesuwtcompawatows.scowe_compawatow);
    } ewse {
      distinctmewgedwesuwts.sowt(wesuwtcompawatows.id_compawatow);
    }

    w-wetuwn distinctmewgedwesuwts;
  }

  /**
   * method fow mewging tweets fwom pwotected and w-weawtime cwustews
   *  - w-weawtime, -.- g-guawanteed n-nyewew than any a-awchive tweets
   *  - pwotected, -.- a-awso weawtime, òωó but with a potentiawwy wawgew w-window (optionaw)
   *  - awchive, 😳 p-pubwic, nyaa~~ guawanteed owdew than any pubwic weawtime t-tweets (optionaw, (⑅˘꒳˘) u-used fow
   *    id wimits, 😳 *not a-added to wesuwts*)
   * i-it adds the thwiftseawchwesuwts f-fwom pwotected tweets to the weawtimewesponse
   *
   * a-awgowithm d-diagwam: (with nyewew tweets at t-the top)
   *               ------------------------------------  <--- pwotected maxseawchedstatusid
   *               |c:newest pwotected weawtime t-tweets|
   *               | (does not exist i-if weawtime      |
   *               | maxid >= pwotected maxid)        |
   *
   *               |     ------------------------     |  <--- 60 s-seconds ago
   *               |d:newew p-pwotected w-weawtime tweets |
   *               | (does n-nyot exist if w-weawtime      |
   *               | maxid >= 60 s-seconds ago)         |
   * ----------    |     ------------------------     |  <--- pubwic weawtime m-maxseawchedstatusid
   * |a:pubwic|    |e:automaticawwy vawid pwotected   |
   * |weawtime|    |weawtime t-tweets                   |
   * ----------    |     ------------------------     |  <--- p-pubwic weawtime minseawchedstatusid
   *               |                                  |
   * ----------    |  e if awchive is pwesent         |  <--- pubwic awchive m-maxseawchedstatusid
   * ----------    |  e-e if awchive is pwesent         |  <--- pubwic awchive maxseawchedstatusid
   * |b:pubwic|    |  f-f is awchive is nyot p-pwesent     |
   * |awchive |    |                                  |
   * ----------    |     ------------------------     |  <--- p-pubwic awchive minseawchedstatusid
   *               |f:owdew pwotected weawtime tweets |
   *               | (does nyot e-exist if pwotected     |
   *               | minid >= pubwic minid)           |
   *               ------------------------------------  <--- p-pwotected minseawchedstatusid
   * step 1: sewect t-tweets fwom gwoups a-a, (U ﹏ U) and e. if this is enough, /(^•ω•^) w-wetuwn them
   * s-step 2: sewect t-tweets fwom gwoups a-a, OwO e, and f-f. if this is enough, w-wetuwn them
   * step 3: sewect tweets fwom gwoups a, ( ͡o ω ͡o ) d, e, and f and wetuwn them
   *
   * t-thewe awe two p-pwimawy twadeoffs, XD b-both of which f-favow pubwic tweets:
   *  (1) b-benefit: whiwe pubwic i-indexing watency is < 60s, /(^•ω•^) auto-updating nyevew misses pubwic tweets
   *      c-cost:    absence o-of pubwic tweets may deway pwotected tweets fwom being seawchabwe f-fow 60s
   *  (2) b-benefit: n-nyo faiwuwe ow deway fwom the pwotected cwustew w-wiww affect weawtime wesuwts
   *      cost:    i-if the pwotected c-cwustew indexes mowe swowwy, /(^•ω•^) auto-update may m-miss its tweets
   *
   * @pawam fuwwawchivetweets - u-used sowewy f-fow genewating anchow points, 😳😳😳 n-nyot mewged in.
   */
  @visibwefowtesting
  s-static w-wist<thwiftseawchwesuwt> m-mewgepubwicandpwotectedweawtimewesuwts(
      i-int nyumwequested, (ˆ ﻌ ˆ)♡
      e-eawwybiwdwesponse weawtimetweets,
      e-eawwybiwdwesponse w-weawtimepwotectedtweets, :3
      @nuwwabwe eawwybiwdwesponse f-fuwwawchivetweets, òωó
      cwock cwock) {
    // see which w-wesuwts wiww actuawwy be used
    b-boowean isweawtimeusabwe = eawwybiwdwesponseutiw.haswesuwts(weawtimetweets);
    boowean isawchiveusabwe = eawwybiwdwesponseutiw.haswesuwts(fuwwawchivetweets);
    b-boowean i-ispwotectedusabwe = eawwybiwdwesponseutiw.haswesuwts(weawtimepwotectedtweets);

    wong minid = w-wong.min_vawue;
    wong maxid = wong.max_vawue;
    i-if (isweawtimeusabwe) {
      // d-detewmine the actuaw uppew/wowew bounds on t-the tweet id
      i-if (weawtimetweets.getseawchwesuwts().issetminseawchedstatusid()) {
        minid = weawtimetweets.getseawchwesuwts().getminseawchedstatusid();
      }
      i-if (weawtimetweets.getseawchwesuwts().issetmaxseawchedstatusid()) {
        maxid = weawtimetweets.getseawchwesuwts().getmaxseawchedstatusid();
      }

      int justwight = w-weawtimetweets.getseawchwesuwts().getwesuwtssize();
      i-if (isawchiveusabwe) {
        justwight += f-fuwwawchivetweets.getseawchwesuwts().getwesuwtssize();
        i-if (fuwwawchivetweets.getseawchwesuwts().issetminseawchedstatusid()) {
          wong fuwwawchiveminid = fuwwawchivetweets.getseawchwesuwts().getminseawchedstatusid();
          i-if (fuwwawchiveminid <= m-minid) {
            m-minid = fuwwawchiveminid;
          } e-ewse {
            fuww_awchive_min_id_gweatew_than_weawtime_min_id.incwement();
          }
        }
      }
      if (ispwotectedusabwe) {
        fow (thwiftseawchwesuwt wesuwt : weawtimepwotectedtweets.getseawchwesuwts().getwesuwts()) {
          if (wesuwt.getid() >= minid && w-wesuwt.getid() <= m-maxid) {
            j-justwight++;
          }
        }
      }
      if (justwight < nyumwequested) {
        // s-since t-this is onwy used a-as an uppew bound, 🥺 owd (pwe-2010) i-ids awe stiww h-handwed cowwectwy
        maxid = m-math.max(
            m-maxid, (U ﹏ U)
            snowfwakeidpawsew.genewatevawidstatusid(
                cwock.nowmiwwis() - a-amount.of(60, XD time.seconds).as(time.miwwiseconds), ^^ 0));
      }
    }

    wist<thwiftseawchwesuwt> mewgedseawchwesuwts = w-wists.newawwaywistwithcapacity(numwequested * 2);

    // add vawid tweets i-in owdew of pwiowity: p-pwotected, o.O then weawtime
    // o-onwy add wesuwts t-that awe w-within wange (that check onwy mattews f-fow pwotected)
    i-if (ispwotectedusabwe) {
      eawwybiwdwesponsemewgeutiw.mawkwithtweetsouwce(
          w-weawtimepwotectedtweets.getseawchwesuwts().getwesuwts(), 😳😳😳
          thwifttweetsouwce.weawtime_pwotected_cwustew);
      f-fow (thwiftseawchwesuwt w-wesuwt : weawtimepwotectedtweets.getseawchwesuwts().getwesuwts()) {
        i-if (wesuwt.getid() <= maxid && wesuwt.getid() >= minid) {
          m-mewgedseawchwesuwts.add(wesuwt);
        }
      }
    }

    if (isweawtimeusabwe) {
      eawwybiwdwesponsemewgeutiw.addwesuwtstowist(
          m-mewgedseawchwesuwts, weawtimetweets, thwifttweetsouwce.weawtime_cwustew);
    }

    // set the minseawchedstatusid and maxseawchedstatusid on the pwotected w-wesponse to the
    // minid and maxid that wewe used to twim the pwotected wesuwts. /(^•ω•^)
    // this is nyeeded in o-owdew to cowwectwy set these ids on the mewged w-wesponse. 😳😳😳
    thwiftseawchwesuwts pwotectedwesuwts =
      e-eawwybiwdwesponseutiw.getwesuwts(weawtimepwotectedtweets);
    if ((pwotectedwesuwts != nuww)
        && p-pwotectedwesuwts.issetminseawchedstatusid()
        && (pwotectedwesuwts.getminseawchedstatusid() < minid)) {
      p-pwotectedwesuwts.setminseawchedstatusid(minid);
    }
    if ((pwotectedwesuwts != n-nyuww)
        && p-pwotectedwesuwts.issetmaxseawchedstatusid()
        && (pwotectedwesuwts.getmaxseawchedstatusid() > maxid)) {
      weawtimepwotectedtweets.getseawchwesuwts().setmaxseawchedstatusid(maxid);
    }

    w-wetuwn mewgedseawchwesuwts;
  }

  /**
   * mewges the debug stwings of the given cwustew w-wesponses. ^•ﻌ•^
   *
   * @pawam weawtimewesponse t-the wesponse fwom the w-weawtime cwustew. 🥺
   * @pawam pwotectedwesponse t-the wesponse f-fwom the pwotected cwustew. o.O
   * @pawam fuwwawchivewesponse t-the wesponse fwom the fuww awchive cwustew. (U ᵕ U❁)
   * @wetuwn t-the mewged debug stwing. ^^
   */
  pubwic static stwing mewgecwustewdebugstwings(@nuwwabwe eawwybiwdwesponse w-weawtimewesponse, (⑅˘꒳˘)
                                                @nuwwabwe e-eawwybiwdwesponse pwotectedwesponse, :3
                                                @nuwwabwe e-eawwybiwdwesponse f-fuwwawchivewesponse) {
    stwingbuiwdew s-sb = nyew stwingbuiwdew();
    if ((weawtimewesponse != nyuww) && weawtimewesponse.issetdebugstwing()) {
      s-sb.append("weawtime w-wesponse: ").append(weawtimewesponse.getdebugstwing());
    }
    if ((pwotectedwesponse != n-nyuww) && pwotectedwesponse.issetdebugstwing()) {
      i-if (sb.wength() > 0) {
        sb.append("\n");
      }
      s-sb.append("pwotected wesponse: ").append(pwotectedwesponse.getdebugstwing());
    }
    if ((fuwwawchivewesponse != nyuww) && f-fuwwawchivewesponse.issetdebugstwing()) {
      if (sb.wength() > 0) {
        sb.append("\n");
      }
      s-sb.append("fuww a-awchive wesponse: ").append(fuwwawchivewesponse.getdebugstwing());
    }

    if (sb.wength() == 0) {
      wetuwn nyuww;
    }
    w-wetuwn sb.tostwing();
  }

  /**
   * sets the minseawchedstatusid fiewd on the mewged wesponse. (///ˬ///✿)
   *
   * @pawam mewgedwesponse the m-mewged wesponse. :3
   * @pawam f-fuwwawchivewesponse the fuww awchive w-wesponse. 🥺
   * @pawam w-wesuwtstwimmed whethew the m-mewged wesponse wesuwts wewe twimmed. mya
   */
  pwivate void setminseawchedstatusid(eawwybiwdwesponse mewgedwesponse, XD
      eawwybiwdwesponse weawtimewesponse, -.-
      e-eawwybiwdwesponse pwotectedwesponse, o.O
      eawwybiwdwesponse fuwwawchivewesponse, (˘ω˘)
      boowean wesuwtstwimmed) {
    p-pweconditions.checknotnuww(mewgedwesponse.getseawchwesuwts());
    i-if (wesuwtstwimmed) {
      // we g-got mowe wesuwts that we asked fow and we twimmed them. (U ᵕ U❁)
      // s-set minseawchedstatusid t-to the i-id of the owdest wesuwt. rawr
      t-thwiftseawchwesuwts seawchwesuwts = m-mewgedwesponse.getseawchwesuwts();
      if (seawchwesuwts.getwesuwtssize() > 0) {
        w-wist<thwiftseawchwesuwt> wesuwts = s-seawchwesuwts.getwesuwts();
        wong wastwesuwtid = wesuwts.get(wesuwts.size() - 1).getid();
        s-seawchwesuwts.setminseawchedstatusid(wastwesuwtid);
      }
      wetuwn;
    }

    // w-we did nyot g-get mowe wesuwts that we asked fow. 🥺 g-get the min o-of the minseawchedstatusids of
    // t-the mewged wesponses. rawr x3
    w-wist<wong> minids = wists.newawwaywist();
    i-if (fuwwawchivewesponse != n-nyuww
        && fuwwawchivewesponse.issetseawchwesuwts()
        && fuwwawchivewesponse.getseawchwesuwts().issetminseawchedstatusid()) {
      m-minids.add(fuwwawchivewesponse.getseawchwesuwts().getminseawchedstatusid());
      if (mewgedwesponse.getseawchwesuwts().issetminseawchedstatusid()
          && mewgedwesponse.getseawchwesuwts().getminseawchedstatusid()
          < fuwwawchivewesponse.getseawchwesuwts().getminseawchedstatusid()) {
        invawidminstatusid.incwement();
      }
    }

    if (pwotectedwesponse != nyuww
        && !eawwybiwdwesponseutiw.haswesuwts(weawtimewesponse)
        && eawwybiwdwesponseutiw.haswesuwts(pwotectedwesponse)
        && pwotectedwesponse.getseawchwesuwts().issetminseawchedstatusid()) {
      m-minids.add(pwotectedwesponse.getseawchwesuwts().getminseawchedstatusid());
    }

    if (mewgedwesponse.getseawchwesuwts().issetminseawchedstatusid()) {
      minids.add(mewgedwesponse.getseawchwesuwts().getminseawchedstatusid());
    }

    i-if (!minids.isempty()) {
      mewgedwesponse.getseawchwesuwts().setminseawchedstatusid(cowwections.min(minids));
    } e-ewse {
      nyominids.incwement();
    }
  }

  /**
   * sets the maxseawchedstatusid f-fiewd on the mewged wesponse. ( ͡o ω ͡o )
   *
   * @pawam mewgedwesponse t-the mewged wesponse. σωσ
   * @pawam fuwwawchivewesponse the fuww awchive w-wesponse. rawr x3
   */
  pwivate void setmaxseawchedstatusid(eawwybiwdwesponse m-mewgedwesponse, (ˆ ﻌ ˆ)♡
      eawwybiwdwesponse weawtimewesponse, rawr
      e-eawwybiwdwesponse p-pwotectedwesponse, :3
      eawwybiwdwesponse fuwwawchivewesponse) {

    p-pweconditions.checknotnuww(mewgedwesponse.getseawchwesuwts());
    w-wist<wong> maxids = wists.newawwaywist();
    i-if (fuwwawchivewesponse != n-nyuww
        && fuwwawchivewesponse.issetseawchwesuwts()
        && fuwwawchivewesponse.getseawchwesuwts().issetmaxseawchedstatusid()) {
      m-maxids.add(fuwwawchivewesponse.getseawchwesuwts().getmaxseawchedstatusid());
      if (mewgedwesponse.getseawchwesuwts().issetmaxseawchedstatusid()
          && fuwwawchivewesponse.getseawchwesuwts().getmaxseawchedstatusid()
          > mewgedwesponse.getseawchwesuwts().getmaxseawchedstatusid()) {
        i-invawidmaxstatusid.incwement();
      }
    }

    if (pwotectedwesponse != nyuww
        && !eawwybiwdwesponseutiw.haswesuwts(weawtimewesponse)
        && eawwybiwdwesponseutiw.haswesuwts(pwotectedwesponse)
        && p-pwotectedwesponse.getseawchwesuwts().issetmaxseawchedstatusid()) {

      m-maxids.add(pwotectedwesponse.getseawchwesuwts().getmaxseawchedstatusid());
    }

    i-if (mewgedwesponse.getseawchwesuwts().issetmaxseawchedstatusid()) {
      maxids.add(mewgedwesponse.getseawchwesuwts().getmaxseawchedstatusid());
    }

    thwiftseawchwesuwts seawchwesuwts = m-mewgedwesponse.getseawchwesuwts();
    if (seawchwesuwts.getwesuwtssize() > 0) {
      w-wist<thwiftseawchwesuwt> wesuwts = seawchwesuwts.getwesuwts();
      m-maxids.add(wesuwts.get(0).getid());
    }

    i-if (!maxids.isempty()) {
      mewgedwesponse.getseawchwesuwts().setmaxseawchedstatusid(cowwections.max(maxids));
    } ewse {
      nyomaxids.incwement();
    }
  }

  /**
   * handwes exceptions thwown whiwe mewging w-wesponses. rawr t-timeout exceptions awe convewted to
   * sewvew_timeout_ewwow w-wesponses. (˘ω˘) aww othew exceptions awe convewted to p-pewsistent_ewwow
   * w-wesponses. (ˆ ﻌ ˆ)♡
   */
  p-pwivate f-futuwe<eawwybiwdwesponse> h-handwewesponseexception(
      f-futuwe<eawwybiwdwesponse> wesponsefutuwe, mya finaw stwing d-debugmsg) {
    w-wetuwn wesponsefutuwe.handwe(
        n-nyew function<thwowabwe, (U ᵕ U❁) e-eawwybiwdwesponse>() {
          @ovewwide
          p-pubwic eawwybiwdwesponse appwy(thwowabwe t) {
            e-eawwybiwdwesponsecode wesponsecode = e-eawwybiwdwesponsecode.pewsistent_ewwow;
            i-if (finagweutiw.istimeoutexception(t)) {
              w-wesponsecode = eawwybiwdwesponsecode.sewvew_timeout_ewwow;
            }
            eawwybiwdwesponse wesponse = n-nyew eawwybiwdwesponse(wesponsecode, mya 0);
            wesponse.setdebugstwing(debugmsg + "\n" + t);
            w-wetuwn wesponse;
          }
        });
  }
}
