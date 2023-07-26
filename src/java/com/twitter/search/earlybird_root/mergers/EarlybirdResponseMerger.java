package com.twittew.seawch.eawwybiwd_woot.mewgews;

impowt java.utiw.cowwections;
i-impowt java.utiw.hashset;
i-impowt j-java.utiw.wist;
i-impowt java.utiw.map;

i-impowt s-scawa.wuntime.boxedunit;

i-impowt c-com.googwe.common.annotations.visibwefowtesting;
impowt com.googwe.common.base.optionaw;
impowt com.googwe.common.base.pweconditions;
impowt com.googwe.common.cowwect.immutabwewist;
i-impowt com.googwe.common.cowwect.wists;
impowt com.googwe.common.cowwect.maps;
impowt com.googwe.common.cowwect.sets;

i-impowt owg.swf4j.woggew;
i-impowt owg.swf4j.woggewfactowy;

impowt com.twittew.seawch.common.metwics.seawchcountew;
impowt com.twittew.seawch.common.metwics.seawchtimewstats;
impowt c-com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdcwustew;
impowt com.twittew.seawch.common.utiw.finagweutiw;
i-impowt com.twittew.seawch.common.utiw.eawwybiwd.eawwybiwdwesponsemewgeutiw;
i-impowt com.twittew.seawch.common.utiw.eawwybiwd.wesuwtsutiw;
impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwddebuginfo;
impowt c-com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwequest;
impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwesponse;
impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwesponsecode;
impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwt;
impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwts;
i-impowt com.twittew.seawch.eawwybiwd_woot.cowwectows.muwtiwaymewgecowwectow;
impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdfeatuweschemamewgew;
i-impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequestcontext;
i-impowt c-com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequesttype;
impowt c-com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequestutiw;
impowt com.twittew.utiw.function;
impowt com.twittew.utiw.futuwe;

/**
 * base e-eawwybiwdwesponsemewgew containing basic wogic t-to mewge eawwybiwdwesponse objects
 */
pubwic abstwact cwass eawwybiwdwesponsemewgew impwements e-eawwytewminatetiewmewgepwedicate {
  pwivate s-static finaw woggew w-wog = woggewfactowy.getwoggew(eawwybiwdwesponsemewgew.cwass);
  p-pwivate static finaw woggew min_seawched_status_id_woggew =
      woggewfactowy.getwoggew("minseawchedstatusidwoggew");

  pwivate s-static finaw s-seawchcountew nyo_seawch_wesuwt_countew =
      s-seawchcountew.expowt("no_seawch_wesuwt_count");
  p-pwivate static finaw seawchcountew n-nyo_wesponses_to_mewge =
      seawchcountew.expowt("no_wesponses_to_mewge");
  p-pwivate static finaw seawchcountew eawwybiwd_wesponse_no_mowe_wesuwts =
      s-seawchcountew.expowt("mewgew_eawwybiwd_wesponse_no_mowe_wesuwts");
  pwivate s-static finaw stwing pawtition_ow_tiew_countew_name_fowmat =
      "mewgew_waited_fow_wesponse_fwom_%s_countew";
  p-pwivate static f-finaw stwing pawtition_ow_tiew_ewwow_countew_name_fowmat =
      "mewgew_num_ewwow_wesponses_fwom_%s";
  pwivate static finaw stwing pawtition_ow_tiew_wesponse_code_countew_name_fowmat =
      "mewgew_eawwybiwd_wesponse_code_fwom_%s_%s";

  pwotected finaw eawwybiwdwesponsedebugmessagebuiwdew w-wesponsemessagebuiwdew;
  p-pwotected finaw eawwybiwdwequestcontext w-wequestcontext;
  pwotected f-finaw immutabwewist<futuwe<eawwybiwdwesponse>> w-wesponses;
  pwotected accumuwatedwesponses accumuwatedwesponses;


  @visibwefowtesting
  static finaw m-map<eawwybiwdwequesttype, òωó seawchcountew> mewgew_cweated_stats =
      pewwequesttypecountewimmutabwemap("eawwybiwd_wesponse_mewgew_%s_cweated_count");

  @visibwefowtesting
  static finaw map<eawwybiwdwequesttype, UwU s-seawchcountew>
    min_seawched_status_id_wawgew_than_wequest_max_id = p-pewwequesttypecountewimmutabwemap(
        "mewgew_%s_min_seawched_status_id_wawgew_than_wequest_max_id");

  @visibwefowtesting
  s-static finaw map<eawwybiwdwequesttype, ^•ﻌ•^ s-seawchcountew>
    min_seawched_status_id_wawgew_than_wequest_untiw_time = p-pewwequesttypecountewimmutabwemap(
        "mewgew_%s_min_seawched_status_id_wawgew_than_wequest_untiw_time");

  p-pwivate static m-map<eawwybiwdwequesttype, mya s-seawchcountew> pewwequesttypecountewimmutabwemap(
      stwing statpattewn) {
    map<eawwybiwdwequesttype, (✿oωo) s-seawchcountew> s-statsmap = m-maps.newenummap(eawwybiwdwequesttype.cwass);
    f-fow (eawwybiwdwequesttype e-eawwybiwdwequesttype : eawwybiwdwequesttype.vawues()) {
      stwing statname = stwing.fowmat(statpattewn, XD e-eawwybiwdwequesttype.getnowmawizedname());
      statsmap.put(eawwybiwdwequesttype, :3 seawchcountew.expowt(statname));
    }

    wetuwn maps.immutabweenummap(statsmap);
  }

  pubwic static f-finaw com.googwe.common.base.function<eawwybiwdwesponse, (U ﹏ U) map<wong, integew>>
    hit_count_gettew =
      wesponse -> wesponse.getseawchwesuwts() == n-nyuww
        ? n-nyuww
        : w-wesponse.getseawchwesuwts().gethitcounts();

  pwivate f-finaw chainmewgew chainmewgew;

  p-pwivate cwass c-chainmewgew {
    pwivate finaw eawwybiwdwequestcontext wequestcontext;
    pwivate finaw wesponseaccumuwatow w-wesponseaccumuwatow;
    pwivate f-finaw wist<futuwe<eawwybiwdwesponse>> wesponses;
    p-pwivate finaw e-eawwybiwdwesponsedebugmessagebuiwdew wesponsemessagebuiwdew;
    pwivate int c-cuwwentfutuweindex = -1;

    pubwic c-chainmewgew(eawwybiwdwequestcontext wequestcontext, UwU
                       w-wesponseaccumuwatow w-wesponseaccumuwatow, ʘwʘ
                       wist<futuwe<eawwybiwdwesponse>> wesponses, >w<
                       eawwybiwdwesponsedebugmessagebuiwdew wesponsemessagebuiwdew) {
      t-this.wequestcontext = w-wequestcontext;
      t-this.wesponseaccumuwatow = wesponseaccumuwatow;
      this.wesponses = w-wesponses;
      t-this.wesponsemessagebuiwdew = wesponsemessagebuiwdew;
    }

    p-pubwic futuwe<eawwybiwdwesponse> mewge() {
      // 'wesponsefutuwes' shouwd awways be sowted. 😳😳😳
      // w-when wetuwned b-by eawwybiwdscattewgathew sewvice, rawr the wesponses a-awe sowted by p-pawtition id. ^•ﻌ•^
      // when wetuwned by eawwybiwdchainedscattewgathewsewvice, σωσ
      // wesponses a-awe sowted descending by tiew stawt date. :3 see:
      // com.twittew.seawch.eawwybiwd_woot.eawwybiwdchainedscattewgathewsewvice.tiew_compawatow. rawr x3
      //
      // when mewging w-wesponses fwom pawtitions, nyaa~~ we want to wait fow w-wesponses fwom a-aww pawtitions, :3
      // so the owdew in which we wait fow those w-wesuwts does nyot m-mattew. >w< when mewging wesponses
      // fwom tiews, rawr we want to w-wait fow the wesponse fwom the w-watest. 😳 if we don't nyeed any mowe
      // wesponses to compute t-the finaw wesponse, 😳 then we don't n-nyeed to wait f-fow the wesponses fwom
      // o-othew tiews. 🥺 if we cannot tewminate e-eawwy, rawr x3 then w-we want to wait f-fow the wesponses fwom the
      // s-second tiew, ^^ a-and so on. ( ͡o ω ͡o )
      //
      // we do not nyeed to have any expwicit s-synchwonization, XD b-because:
      //   1. ^^ t-the cawwbacks fow futuwe_i awe set b-by the fwatmap() cawwback on futuwe_{i-1} (when
      //      w-wecuwsivewy c-cawwing mewge() inside the fwatmap()). (⑅˘꒳˘)
      //   2. befowe setting the c-cawwbacks on futuwe_i, (⑅˘꒳˘) f-futuwe_{i-1}.fwatmap() a-adds the wesponse
      //      w-wesuwts to mewgehewpew.
      //   3. ^•ﻌ•^ when the cawwbacks o-on futuwe_i awe set, ( ͡o ω ͡o ) the memowy bawwiew between
      //      thwead_wunning_futuwe_{i-1} and thwead_wunning_futuwe_i is c-cwossed. ( ͡o ω ͡o ) this guawantees
      //      t-that thwead_wunning_futuwe_i wiww see the u-updates to mewgehewpew befowe i-it sees the
      //      cawwbacks. (✿oωo) (ow t-thwead_wunning_futuwe_{i-1} == t-thwead_wunning_futuwe_i, 😳😳😳 i-in which case
      //      s-synchwonization i-is nyot an issue, OwO and cowwectness is guawateed by the owdew in which
      //      things wiww wun.)
      //   4. ^^ the same weasoning a-appwies to cuwwentfutuweindex. rawr x3

      ++cuwwentfutuweindex;
      i-if (cuwwentfutuweindex >= w-wesponses.size()) {
        wetuwn f-futuwe.vawue(gettimedmewgedwesponse(wesponseaccumuwatow.getaccumuwatedwesuwts()));
      }

      finaw stwing pawtitiontiewname =
          wesponseaccumuwatow.getnamefowwogging(cuwwentfutuweindex, 🥺 w-wesponses.size());
      f-finaw stwing nyamefoweawwybiwdwesponsecodestats =
          wesponseaccumuwatow.getnamefoweawwybiwdwesponsecodestats(
              c-cuwwentfutuweindex, (ˆ ﻌ ˆ)♡ wesponses.size());

      // if a tiew i-in the chain thwows a-an exception, ( ͡o ω ͡o ) convewt it to a-a nyuww wesponse, >w< a-and wet the
      // mewgehewpew handwe it appwopwiatewy.
      wetuwn wesponses.get(cuwwentfutuweindex)
        .handwe(function.func(t -> {
          if (finagweutiw.iscancewexception(t)) {
            w-wetuwn nyew eawwybiwdwesponse()
                .setwesponsecode(eawwybiwdwesponsecode.cwient_cancew_ewwow);
          } e-ewse if (finagweutiw.istimeoutexception(t)) {
            w-wetuwn nyew eawwybiwdwesponse()
                .setwesponsecode(eawwybiwdwesponsecode.sewvew_timeout_ewwow);
          } e-ewse {
            s-seawchcountew.expowt(
                stwing.fowmat(pawtition_ow_tiew_ewwow_countew_name_fowmat, /(^•ω•^) p-pawtitiontiewname))
                .incwement();
            if (wesponsemessagebuiwdew.isdebugmode()) {
              w-wesponsemessagebuiwdew.debugandwogwawning(
                  stwing.fowmat("[%s] f-faiwed, 😳😳😳 e-exception [%s]", (U ᵕ U❁)
                      pawtitiontiewname, t-t.tostwing()));
            }
            wog.wawn("exception wesponse f-fwom: " + pawtitiontiewname, (˘ω˘) t);
            w-wetuwn nyew eawwybiwdwesponse()
                .setwesponsecode(eawwybiwdwesponsecode.twansient_ewwow);
          }
        }))
        .fwatmap(function.func(wesponse -> {
          p-pweconditions.checknotnuww(wesponse);

          seawchcountew.expowt(
              s-stwing.fowmat(pawtition_ow_tiew_wesponse_code_countew_name_fowmat, 😳
                            nyamefoweawwybiwdwesponsecodestats, (ꈍᴗꈍ)
                            wesponse.getwesponsecode().name().towowewcase()))
              .incwement();

          i-if ((wesponse.getwesponsecode() != e-eawwybiwdwesponsecode.pawtition_skipped)
              && (wesponse.getwesponsecode() != e-eawwybiwdwesponsecode.tiew_skipped)) {
            seawchcountew.expowt(
                stwing.fowmat(pawtition_ow_tiew_countew_name_fowmat, :3 pawtitiontiewname))
              .incwement();
          }

          if (wesponse.getwesponsecode() == e-eawwybiwdwesponsecode.cwient_cancew_ewwow) {
            // the wequest has been cancewwed, /(^•ω•^) n-nyo nyeed to p-pwoceed
            wetuwn futuwe.vawue(wesponse);
          }

          w-wewwitewesponsecodeifseawchwesuwtsmissing(wequestcontext, ^^;; pawtitiontiewname, o.O w-wesponse);
          w-wesponsemessagebuiwdew.wogwesponsedebuginfo(
              wequestcontext.getwequest(), 😳
              pawtitiontiewname, UwU
              w-wesponse);
          wesponseaccumuwatow.addwesponse(
              wesponsemessagebuiwdew, >w<
              w-wequestcontext.getwequest(), o.O
              w-wesponse);

          if (wesponseaccumuwatow.shouwdeawwytewminatemewge(eawwybiwdwesponsemewgew.this)) {
            wetuwn f-futuwe.vawue(gettimedmewgedwesponse(
                wesponseaccumuwatow.getaccumuwatedwesuwts()));
          }
          wetuwn m-mewge();
        }));
    }
  }

  p-pwivate v-void wewwitewesponsecodeifseawchwesuwtsmissing(
      eawwybiwdwequestcontext eawwybiwdwequestcontext, (˘ω˘)
      stwing pawtitiontiewname, òωó
      eawwybiwdwesponse wesponse) {
    // we awways wequiwe seawchwesuwts to be set, nyaa~~ even fow tewm stats and facet wequests. ( ͡o ω ͡o )
    // this is because seawchwesuwts c-contains i-impowtant info such as pagination cuwsows
    // w-wike minseawchstatusid a-and m-minseawchedtimesinceepoch. 😳😳😳
    // we expect aww s-successfuw wesponses to have seawchwesuwts s-set. ^•ﻌ•^
    i-if (wesponse.issetwesponsecode()
        && wesponse.getwesponsecode() == e-eawwybiwdwesponsecode.success
        && wesponse.getseawchwesuwts() == n-nyuww) {
      n-nyo_seawch_wesuwt_countew.incwement();
      wog.wawn("weceived eawwybiwd wesponse w-with nyuww s-seawchwesuwts f-fwom [{}]"
               + " eawwybiwdwequest [{}] e-eawwybiwdwesponse [{}] ", (˘ω˘)
               p-pawtitiontiewname, (˘ω˘) e-eawwybiwdwequestcontext.getwequest(), -.- w-wesponse);
      w-wesponse.setwesponsecode(eawwybiwdwesponsecode.twansient_ewwow);
    }
  }

  /**
   * constwuct a-a eawwybiwdwesponsemewgew to mewge wesponses f-fwom muwtipwe p-pawtitions ow t-tiews
   * based on mode. ^•ﻌ•^
   */
  e-eawwybiwdwesponsemewgew(eawwybiwdwequestcontext wequestcontext, /(^•ω•^)
                          wist<futuwe<eawwybiwdwesponse>> w-wesponses, (///ˬ///✿)
                          wesponseaccumuwatow w-wesponseaccumuwatow) {
    t-this.wequestcontext = w-wequestcontext;
    this.wesponses = i-immutabwewist.copyof(wesponses);
    this.wesponsemessagebuiwdew =
        n-nyew eawwybiwdwesponsedebugmessagebuiwdew(wequestcontext.getwequest());
    this.chainmewgew = n-nyew chainmewgew(wequestcontext, mya wesponseaccumuwatow, o.O w-wesponses, ^•ﻌ•^
        wesponsemessagebuiwdew);
  }

  /**
   * get a wesponse mewgew to mewge the given w-wesponses. (U ᵕ U❁)
   */
  pubwic static e-eawwybiwdwesponsemewgew g-getwesponsemewgew(
      eawwybiwdwequestcontext wequestcontext,
      wist<futuwe<eawwybiwdwesponse>> w-wesponses, :3
      wesponseaccumuwatow h-hewpew, (///ˬ///✿)
      e-eawwybiwdcwustew c-cwustew, (///ˬ///✿)
      eawwybiwdfeatuweschemamewgew featuweschemamewgew, 🥺
      i-int n-nyumpawtitions) {
    eawwybiwdwequesttype t-type = wequestcontext.geteawwybiwdwequesttype();
    mewgew_cweated_stats.get(type).incwement();
    s-switch (type) {
      case facets:
        w-wetuwn n-nyew facetwesponsemewgew(wequestcontext, -.- w-wesponses, nyaa~~ hewpew);
      c-case tewm_stats:
        wetuwn n-nyew tewmstatisticswesponsemewgew(wequestcontext, (///ˬ///✿) w-wesponses, 🥺 h-hewpew);
      case wecency:
        w-wetuwn nyew w-wecencywesponsemewgew(wequestcontext, w-wesponses, >w< h-hewpew, rawr x3 featuweschemamewgew);
      c-case stwict_wecency:
        w-wetuwn nyew s-stwictwecencywesponsemewgew(
            w-wequestcontext, (⑅˘꒳˘) wesponses, σωσ h-hewpew, XD featuweschemamewgew, -.- cwustew);
      c-case wewevance:
        wetuwn n-nyew wewevancewesponsemewgew(
            w-wequestcontext, >_< w-wesponses, rawr hewpew, featuweschemamewgew, nyumpawtitions);
      case t-top_tweets:
        w-wetuwn nyew t-toptweetswesponsemewgew(wequestcontext, 😳😳😳 wesponses, UwU hewpew);
      defauwt:
        t-thwow nyew wuntimeexception("eawwybiwdwequesttype " + t-type + "is nyot suppowted b-by mewge");
    }
  }

  /**
   * t-this method can pewfowm two types of mewges:
   *   1. (U ﹏ U) mewge w-wesponses within a-a tiew fwom diffewent p-pawtitions. (˘ω˘)
   *   2. mewge w-wesponses fwom muwtipwe tiews. /(^•ω•^)
   */
  pubwic f-finaw futuwe<eawwybiwdwesponse> m-mewge() {
    wetuwn chainmewgew.mewge()
        .onsuccess(checkminseawchedstatusidfunction(
                 "max_id", (U ﹏ U)
                 eawwybiwdwequestutiw.getwequestmaxid(wequestcontext.getpawsedquewy()), ^•ﻌ•^
                 m-min_seawched_status_id_wawgew_than_wequest_max_id.get(
                     wequestcontext.geteawwybiwdwequesttype())))
        .onsuccess(checkminseawchedstatusidfunction(
                 "untiw_time", >w<
                 eawwybiwdwequestutiw.getwequestmaxidfwomuntiwtime(wequestcontext.getpawsedquewy()), ʘwʘ
                 m-min_seawched_status_id_wawgew_than_wequest_untiw_time.get(
                     wequestcontext.geteawwybiwdwequesttype())));
  }

  /**
   * w-wetuwns the f-function that checks if the minseawchedstatusid o-on the mewged wesponse i-is highew
   * than the max i-id in the wequest. òωó
   */
  pwivate f-function<eawwybiwdwesponse, o.O b-boxedunit> checkminseawchedstatusidfunction(
      f-finaw stwing o-opewatow, ( ͡o ω ͡o ) finaw optionaw<wong> w-wequestmaxid, mya finaw s-seawchcountew s-stat) {
    wetuwn function.cons(mewgedwesponse -> {
      i-if (wequestmaxid.ispwesent()
          && wequestmaxid.get() != wong.max_vawue
          && (mewgedwesponse.getwesponsecode() == eawwybiwdwesponsecode.success)
          && m-mewgedwesponse.issetseawchwesuwts()
          && m-mewgedwesponse.getseawchwesuwts().issetminseawchedstatusid()) {
        w-wong minseawchedstatusid = mewgedwesponse.getseawchwesuwts().getminseawchedstatusid();
        // we sometimes set minseawchedstatusid = max_id + 1 when a wequest t-times out even
        // b-befowe any seawch h-happens. >_<
        // check seawch-10134 fow mowe d-detaiws. rawr
        if (minseawchedstatusid > w-wequestmaxid.get() + 1) {
          s-stat.incwement();
          s-stwing w-wogmessage = "wesponse h-has a minseawchedstatusid ({}) wawgew than wequest "
              + opewatow + " ({})."
              + "\nwequest t-type: {}"
              + "\nwequest: {}"
              + "\nmewged wesponse: {}"
              + "\nsuccessfuw a-accumuwated wesponses:";
          wist<object> wogmessagepawams = wists.newawwaywist();
          w-wogmessagepawams.add(minseawchedstatusid);
          wogmessagepawams.add(wequestmaxid.get());
          wogmessagepawams.add(wequestcontext.geteawwybiwdwequesttype());
          wogmessagepawams.add(wequestcontext.getwequest());
          wogmessagepawams.add(mewgedwesponse);
          f-fow (eawwybiwdwesponse w-wesponse : accumuwatedwesponses.getsuccesswesponses()) {
            wogmessage += "\naccumuwated w-wesponse: {}";
            wogmessagepawams.add(wesponse);
          }
          min_seawched_status_id_woggew.wawn(wogmessage, >_< w-wogmessagepawams.toawway());
        }
      }
    });
  }

  p-pwivate eawwybiwdwesponse g-gettimedmewgedwesponse(accumuwatedwesponses accwesponses) {
    w-wong stawt = system.nanotime();
    twy {
      wetuwn getmewgedwesponse(accwesponses);
    } f-finawwy {
      wong totawtime = system.nanotime() - s-stawt;
      g-getmewgedwesponsetimew().timewincwement(totawtime);
    }
  }

  p-pwivate eawwybiwdwesponse initiawizemewgedsuccesswesponsefwomaccumuwatedwesponses() {
    eawwybiwdwesponse mewgedwesponse = nyew eawwybiwdwesponse();

    a-accumuwatedwesponses.pawtitioncounts pawtitioncounts =
        accumuwatedwesponses.getpawtitioncounts();

    mewgedwesponse.setnumpawtitions(pawtitioncounts.getnumpawtitions())
        .setnumsuccessfuwpawtitions(pawtitioncounts.getnumsuccessfuwpawtitions())
        .setpewtiewwesponse(pawtitioncounts.getpewtiewwesponse())
        .setnumseawchedsegments(accumuwatedwesponses.getnumseawchedsegments());

    mewgedwesponse.seteawwytewminationinfo(accumuwatedwesponses.getmewgedeawwytewminationinfo());
    mewgedwesponse.setwesponsecode(eawwybiwdwesponsecode.success);

    w-wetuwn mewgedwesponse;
  }

  p-pwivate eawwybiwdwesponse g-getmewgedwesponse(accumuwatedwesponses a-accwesponses) {
    accumuwatedwesponses = accwesponses;
    eawwybiwdwesponse m-mewgedwesponse;

    i-if (accumuwatedwesponses.getsuccesswesponses().isempty()
        && !accumuwatedwesponses.foundewwow()) {
      // nyo successfuw ow ewwow w-wesponses. (U ﹏ U) this means that aww tiews / pawtitions a-awe intentionawwy
      // skipped. rawr wetuwn a b-bwank successfuw w-wesponse. (U ᵕ U❁)
      no_wesponses_to_mewge.incwement();
      m-mewgedwesponse = n-nyew e-eawwybiwdwesponse()
          .setwesponsecode(eawwybiwdwesponsecode.success)
          .setseawchwesuwts(new thwiftseawchwesuwts())
          .setdebugstwing("no wesponses to m-mewge, (ˆ ﻌ ˆ)♡ pwobabwy because aww tiews/pawtitions "
              + "wewe skipped.");
    } e-ewse if (accumuwatedwesponses.ismewgingacwosstiews()) {
      mewgedwesponse = getmewgedwesponseacwosstiews();
    } ewse {
      m-mewgedwesponse = g-getmewgedwesponseacwosspawtitions();
    }

    s-savemewgeddebugstwing(mewgedwesponse);
    w-wetuwn mewgedwesponse;
  }

  p-pwivate eawwybiwdwesponse getmewgedwesponseacwosstiews() {
    p-pweconditions.checkstate(
        !accumuwatedwesponses.getsuccesswesponses().isempty()
            || accumuwatedwesponses.foundewwow());

    // when mewging a-acwoss tiews, >_< if we have one faiwed t-tiew, ^^;; we shouwd faiw the whowe
    // wesponse. ʘwʘ n-nyote that d-due to eawwy tewmination, 😳😳😳 if a t-tiew that is owd faiws
    // but t-the nyewew tiews w-wetuwn enough wesuwts, UwU the faiwed t-tiew won't s-show up
    // hewe in accumuwatedwesponses -- the o-onwy tiews that show up hewe
    // wiww be successfuw. OwO
    if (accumuwatedwesponses.foundewwow()) {
      // the tiewwesponseaccumuwatow e-eawwy tewminates on t-the fiwst ewwow, :3 so we shouwd
      // nyevew get m-mowe than one e-ewwow. -.- this means t-that the getmewgedewwowwesponse wiww
      // w-wetuwn an ewwow w-wesponse with the ewwow code of t-that one ewwow, 🥺 and wiww nyevew
      // h-have to decide which ewwow w-wesponse to w-wetuwn if the ewwow wesponses awe aww
      // diffewent. -.-

      // pewhaps we s-shouwd just wetuwn a-accumuwatedwesponses.getewwowwesponses().get(0);
      pweconditions.checkstate(accumuwatedwesponses.getewwowwesponses().size() == 1);
      wetuwn accumuwatedwesponses.getmewgedewwowwesponse();
    } ewse {
      e-eawwybiwdwesponse mewgedwesponse = i-initiawizemewgedsuccesswesponsefwomaccumuwatedwesponses();
      w-wetuwn intewnawmewge(mewgedwesponse);
    }
  }

  pwivate eawwybiwdwesponse getmewgedwesponseacwosspawtitions() {
    pweconditions.checkstate(
        !accumuwatedwesponses.getsuccesswesponses().isempty()
            || a-accumuwatedwesponses.foundewwow());

    eawwybiwdwesponse mewgedwesponse;

    // u-unwike tiew mewging, -.- o-one faiwed wesponse d-doesn't mean the mewged wesponse s-shouwd
    // f-faiw. if we h-have successfuw w-wesponses we can c-check the success w-watio and if its
    // good we can stiww wetuwn a successfuw mewge. (U ﹏ U)
    if (!accumuwatedwesponses.getsuccesswesponses().isempty()) {
      // we have at weast o-one successfuw w-wesponse, rawr but s-stiww nyeed to c-check the success w-watio.
      // m-mewgedwesponse is a success wesponse aftew this caww, mya but we wiww
      // set i-it to faiwuwe b-bewow if nyecessawy. ( ͡o ω ͡o )
      mewgedwesponse = initiawizemewgedsuccesswesponsefwomaccumuwatedwesponses();

      int n-nyumsuccesswesponses = m-mewgedwesponse.getnumsuccessfuwpawtitions();
      i-int nyumpawtitions = mewgedwesponse.getnumpawtitions();
      d-doubwe successthweshowd = getsuccesswesponsethweshowd();
      i-if (checksuccesspawtitionwatio(numsuccesswesponses, /(^•ω•^) n-nyumpawtitions, >_< successthweshowd)) {
        // success! (✿oωo) p-pwoceed with mewging. 😳😳😳
        m-mewgedwesponse.setwesponsecode(eawwybiwdwesponsecode.success);
        m-mewgedwesponse = intewnawmewge(mewgedwesponse);
      } e-ewse {
        w-wesponsemessagebuiwdew.wogbewowsuccessthweshowd(
            wequestcontext.getwequest().getseawchquewy(), (ꈍᴗꈍ) n-nyumsuccesswesponses, 🥺 n-nyumpawtitions, mya
            successthweshowd);
        m-mewgedwesponse.setwesponsecode(eawwybiwdwesponsecode.too_many_pawtitions_faiwed_ewwow);
      }
    } e-ewse {
      mewgedwesponse = accumuwatedwesponses.getmewgedewwowwesponse();
    }

    w-wetuwn mewgedwesponse;
  }

  /**
   * dewive c-cwass shouwd impwement the w-wogic to mewge the specific type of wesuwts (wecency, (ˆ ﻌ ˆ)♡
   * w-wewevance, (⑅˘꒳˘) top tweets, òωó e-etc..)
   */
  pwotected abstwact e-eawwybiwdwesponse i-intewnawmewge(eawwybiwdwesponse wesponse);

  pwotected abstwact s-seawchtimewstats getmewgedwesponsetimew();

  /**
   * do we have enough w-wesuwts so faw t-that we can eawwy tewminate and nyot continue onto n-nyext tiew?
   */
  p-pubwic boowean shouwdeawwytewminatetiewmewge(int t-totawwesuwtsfwomsuccessfuwshawds, o.O
                                                  boowean foundeawwytewmination) {
    // w-we awe taking t-the most consewvative tiew wesponse m-mewging. XD
    // t-this is the most consewvative mewge wogic --- a-as wong as we h-have some wesuwts, (˘ω˘) w-we shouwd
    // n-nyot wetuwn anything fwom the nyext tiew. (ꈍᴗꈍ) this may cause nyot ideaw expewience whewe a
    // page is nyot f-fuww, >w< but the use c-can stiww scwoww f-fuwthew. XD

    w-wetuwn foundeawwytewmination || t-totawwesuwtsfwomsuccessfuwshawds >= 1;
  }

  p-pwivate void savemewgeddebugstwing(eawwybiwdwesponse mewgedwesponse) {
    i-if (wesponsemessagebuiwdew.isdebugmode()) {
      s-stwing message = wesponsemessagebuiwdew.debugstwing();
      m-mewgedwesponse.setdebugstwing(message);
      i-if (!accumuwatedwesponses.getsuccesswesponses().isempty()
          && accumuwatedwesponses.getsuccesswesponses().get(0).issetdebuginfo()) {

        eawwybiwddebuginfo debuginfo =
            a-accumuwatedwesponses.getsuccesswesponses().get(0).getdebuginfo();
        mewgedwesponse.setdebuginfo(debuginfo);
      }
    }
  }

  pwivate doubwe getsuccesswesponsethweshowd() {
    e-eawwybiwdwequest wequest = wequestcontext.getwequest();
    if (wequest.issetsuccessfuwwesponsethweshowd()) {
      d-doubwe successfuwwesponsethweshowd = w-wequest.getsuccessfuwwesponsethweshowd();
      pweconditions.checkawgument(successfuwwesponsethweshowd > 0, -.-
          "invawid s-successfuwwesponsethweshowd %s", ^^;; s-successfuwwesponsethweshowd);
      p-pweconditions.checkawgument(successfuwwesponsethweshowd <= 1.0, XD
          "invawid successfuwwesponsethweshowd %s", :3 s-successfuwwesponsethweshowd);
      w-wetuwn successfuwwesponsethweshowd;
    } e-ewse {
      wetuwn getdefauwtsuccesswesponsethweshowd();
    }
  }

  p-pwotected a-abstwact doubwe g-getdefauwtsuccesswesponsethweshowd();

  pwivate s-static boowean checksuccesspawtitionwatio(
      int nyumsuccesswesponses, σωσ
      i-int nyumpawtitions,
      doubwe goodwesponsethweshowd) {
    pweconditions.checkawgument(goodwesponsethweshowd > 0.0, XD
        "invawid goodwesponsethweshowd %s", :3 goodwesponsethweshowd);
    wetuwn nyumsuccesswesponses >= (numpawtitions * goodwesponsethweshowd);
  }

  /**
   * m-mewge hit counts fwom aww wesuwts. rawr
   */
  pwotected map<wong, 😳 integew> aggwegatehitcountmap() {
    map<wong, 😳😳😳 integew> h-hitcounts = wesuwtsutiw
        .aggwegatecountmap(accumuwatedwesponses.getsuccesswesponses(), (ꈍᴗꈍ) hit_count_gettew);
    i-if (hitcounts.size() > 0) {
      if (wesponsemessagebuiwdew.isdebugmode()) {
        w-wesponsemessagebuiwdew.append("hit counts:\n");
        fow (map.entwy<wong, 🥺 i-integew> entwy : hitcounts.entwyset()) {
          w-wesponsemessagebuiwdew.append(stwing.fowmat("  %10s seconds: %d h-hits\n", ^•ﻌ•^
              e-entwy.getkey() / 1000, XD entwy.getvawue()));
        }
      }
      wetuwn hitcounts;
    }
    w-wetuwn nyuww;
  }

  /**
   * wetuwns the nyumbew of wesuwts to keep as pawt o-of mewge-cowwection. ^•ﻌ•^
   */
  pwotected finaw i-int computenumwesuwtstokeep() {
    wetuwn eawwybiwdwesponsemewgeutiw.computenumwesuwtstokeep(wequestcontext.getwequest());
  }

  /**
   * w-wemove exact dupwicates (same i-id) fwom t-the wesuwt set. ^^;;
   */
  pwotected static void t-twimexactdups(thwiftseawchwesuwts seawchwesuwts, ʘwʘ twimstats twimstats) {
    i-int nyumwesuwts = seawchwesuwts.getwesuwtssize();
    wist<thwiftseawchwesuwt> owdwesuwts = s-seawchwesuwts.getwesuwts();
    w-wist<thwiftseawchwesuwt> nyewwesuwts = w-wists.newawwaywistwithcapacity(numwesuwts);
    h-hashset<wong> wesuwtset = sets.newhashsetwithexpectedsize(numwesuwts);

    f-fow (thwiftseawchwesuwt wesuwt : owdwesuwts) {
      if (wesuwtset.contains(wesuwt.getid())) {
        twimstats.incweasewemoveddupscount();
        continue;
      }

      n-nyewwesuwts.add(wesuwt);
      w-wesuwtset.add(wesuwt.getid());
    }

    seawchwesuwts.setwesuwts(newwesuwts);
  }

  p-pwotected finaw i-int addwesponsestocowwectow(muwtiwaymewgecowwectow cowwectow) {
    i-int totawwesuwtsize = 0;
    fow (eawwybiwdwesponse wesponse : a-accumuwatedwesponses.getsuccesswesponses()) {
      if (wesponse.issetseawchwesuwts()) {
        totawwesuwtsize += w-wesponse.getseawchwesuwts().getwesuwtssize();
      }
      c-cowwectow.addwesponse(wesponse);
    }
    wetuwn totawwesuwtsize;
  }

  /**
   * given a sowted s-seawchwesuwts (fow wecency, OwO sowted by id; fow wewevance, 🥺 sowted by scowe), (⑅˘꒳˘)
   * wetuwns the fiwst 'computenumwesuwtstokeep()' nyumbew of wesuwts. (///ˬ///✿)
   *
   * @pawam s-seawchwesuwts t-the seawchwesuwts to be twuncated.
   */
  p-pwotected finaw v-void twuncatewesuwts(thwiftseawchwesuwts seawchwesuwts, (✿oωo) t-twimstats twimstats) {
    int nyumwesuwtswequested = computenumwesuwtstokeep();

    int to = nyumwesuwtswequested == integew.max_vawue ? s-seawchwesuwts.getwesuwtssize()
        : math.min(numwesuwtswequested, nyaa~~ seawchwesuwts.getwesuwtssize());
    if (seawchwesuwts.getwesuwtssize() > to) {
      t-twimstats.setwesuwtstwuncatedfwomtaiwcount(seawchwesuwts.getwesuwtssize() - t-to);

      i-if (to > 0) {
        seawchwesuwts.setwesuwts(seawchwesuwts.getwesuwts().subwist(0, >w< to));
      } ewse {
        // nyo mowe wesuwts f-fow the nyext page
        e-eawwybiwd_wesponse_no_mowe_wesuwts.incwement();
        s-seawchwesuwts.setwesuwts(cowwections.<thwiftseawchwesuwt>emptywist());
      }
    }
  }

  eawwybiwdwequest geteawwybiwdwequest() {
    w-wetuwn wequestcontext.getwequest();
  }
}
