package com.twittew.seawch.eawwybiwd_woot.mewgews;

impowt java.utiw.cowwections;
i-impowt java.utiw.wist;
i-impowt java.utiw.concuwwent.timeunit;

impowt c-com.googwe.common.annotations.visibwefowtesting;
i-impowt com.googwe.common.base.pweconditions;

i-impowt com.twittew.seawch.common.metwics.seawchtimewstats;
i-impowt com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdcwustew;
i-impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwesponse;
i-impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwt;
impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwts;
impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdfeatuweschemamewgew;
impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequestcontext;
impowt com.twittew.utiw.futuwe;

/**
 * a-a wecencywesponsemewgew that pwiowitizes nyot wosing w-wesuwts duwing pagination. /(^•ω•^)
 * a-as of nyow, ^^ this mewgew is used by gnip to make suwe that scwowwing w-wetuwns aww wesuwts. 🥺
 *
 * t-the wogic used f-fow mewging pawtitions is a bit twicky, (U ᵕ U❁) because on one hand, 😳😳😳 we want to make suwe
 * t-that we do miss wesuwts on the nyext pagination wequest; on the othew hand, w-we want to wetuwn as
 * many wesuwts a-as we can, nyaa~~ a-and we want to s-set the minseawchedstatusid o-of the mewged wesponse as wow
 * as w-we can, (˘ω˘) in owdew to minimize the numbew of pagination w-wequests. >_<
 *
 * the mewging wogic is:
 *
 * weawtime cwustew:
 *  1. XD mewge wesuwts fwom aww p-pawtitions
 *  2. rawr x3 if at weast o-one pawtition wesponse i-is eawwy-tewminated, ( ͡o ω ͡o ) s-set eawwytewminated = twue
 *     on the mewged wesponse
 *  3. :3 s-set t-twimmingminid = max(minseawchedstatusids o-of aww p-pawtition wesponses)
 *  4. mya twim a-aww wesuwts to twimmingminid
 *  5. σωσ s-set minseawchedstatusid on the mewged wesponse t-to twimmingminid
 *  6. (ꈍᴗꈍ) if w-we have mowe than numwequested wesuwts:
 *     - k-keep onwy the nyewest n-nyumwequested wesuwts
 *     - set minseawchedstatusid of the mewged wesponse to the wowest tweet id in the w-wesponse
 *  7. OwO i-if at weast one pawtition wesponse i-is nyot eawwy-tewminated, o.O s-set
 *     tiewbottomid = m-max(minseawchedstatusids of aww nyon-eawwy-tewminated wesponses)
 *     (othewwise, set t-tiewbottomid to some undefined vawue: -1, wong.max_vawue, etc.)
 *  8. 😳😳😳 if minseawchedstatusid o-of the mewged wesponse is the same a-as tiewbottomid, /(^•ω•^)
 *     c-cweaw t-the eawwy-tewmination fwag on the m-mewged wesponse
 *
 * t-the wogic i-in steps 7 and 8 c-can be a wittwe twicky to undewstand. OwO they basicawwy s-say: when w-we've
 * exhausted t-the "weast d-deep" pawtition i-in the weawtime cwustew, ^^ it's time to move to the fuww
 * awchive c-cwustew (if we keep going past the "weast deep" pawtition, (///ˬ///✿) we might miss wesuwts). (///ˬ///✿)
 *
 * fuww a-awchive cwustew:
 *  1. (///ˬ///✿) mewge wesuwts fwom aww pawtitions
 *  2. ʘwʘ i-if at weast one p-pawtition wesponse i-is eawwy-tewminated, ^•ﻌ•^ set eawwytewminated = t-twue
 *     on the mewged wesponse
 *  3. OwO s-set twimmingminid t-to:
 *     - max(minseawchedstatusids of eawwy-tewminated wesponses), if at weast one pawtition wesponse
 *       i-is eawwy-tewminated
 *     - m-min(minseawchedstatusids of aww wesponses), (U ﹏ U) i-if aww pawtition w-wesponses awe not
 *       eawwy-tewminated
 *  4. (ˆ ﻌ ˆ)♡ t-twim a-aww wesuwts to twimmingminid
 *  5. (⑅˘꒳˘) set minseawchedstatusid o-of the m-mewged wesponse to twimmingminid
 *  6. (U ﹏ U) if we have mowe than nyumwequested wesuwts:
 *     - k-keep onwy the nyewest n-nyumwequested w-wesuwts
 *     - set minseawchedstatusid o-of t-the mewged wesponse to the wowest t-tweet id in the wesponse
 *
 * the wogic in step 3 can be a wittwe twicky to undewstand. o.O o-on one h-hand, mya if we awways set
 * twimmingminid to the h-highest minseawchedstatusid, XD t-then some tweets at the vewy bottom of some
 * pawtitions w-wiww nyevew be wetuwned. òωó considew the case:
 *
 *  pawtition 1 has tweets 10, (˘ω˘) 8, 6
 *  pawtition 2 h-has tweets 9, :3 7, OwO 5
 *
 * in this case, mya we wouwd awways t-twim aww wesuwts t-to minid = 6, (˘ω˘) and tweet 5 wouwd nyevew be wetuwned. o.O
 *
 * on t-the othew hand, (✿oωo) i-if we awways set twimmingminid to the wowest minseawchedstatusid, then we
 * might m-miss tweets fwom pawtitions that e-eawwy-tewminated. (ˆ ﻌ ˆ)♡ considew the case:
 *
 * pawtition 1 has tweets 10, ^^;; 5, 3, OwO 1 t-that match ouw quewy
 * pawtition 2 h-has tweets 9, 🥺 8, 7, 6, mya 2 that m-match ouw quewy
 *
 * if we a-ask fow 3 wesuwts, 😳 than pawtition 1 w-wiww wetuwn t-tweets 10, òωó 5, 3, a-and pawtition 2 wiww
 * wetuwn t-tweets 9, /(^•ω•^) 8, 7. -.- i-if we set twimmingminid = min(minseawchedstatusids), òωó then the nyext
 * p-pagination w-wequest wiww have [max_id = 2], /(^•ω•^) a-and we wiww miss tweet 6. /(^•ω•^)
 *
 * so the intuition h-hewe is that if we have an eawwy-tewminated wesponse, 😳 w-we cannot s-set
 * twimmingminid to something wowew than the minseawchedstatusid w-wetuwned b-by that pawtition
 * (othewwise w-we might miss wesuwts f-fwom that pawtition). :3 howevew, i-if we've exhausted aww
 * pawtitions, (U ᵕ U❁) then it's ok to not twim any wesuwt, ʘwʘ because tiews do n-nyot intewsect, o.O so we wiww not
 * m-miss any wesuwt fwom the next t-tiew once we get thewe. ʘwʘ
 */
pubwic c-cwass stwictwecencywesponsemewgew extends wecencywesponsemewgew {
  p-pwivate s-static finaw seawchtimewstats stwict_wecency_timew_avg =
      s-seawchtimewstats.expowt("mewge_wecency_stwict", ^^ t-timeunit.nanoseconds, f-fawse, ^•ﻌ•^ twue);

  @visibwefowtesting
  static finaw eawwytewminationtwimmingstats pawtition_mewging_eawwy_tewmination_twimming_stats =
      nyew eawwytewminationtwimmingstats("stwict_wecency_pawtition_mewging");

  @visibwefowtesting
  static finaw eawwytewminationtwimmingstats tiew_mewging_eawwy_tewmination_twimming_stats =
      n-nyew eawwytewminationtwimmingstats("stwict_wecency_tiew_mewging");

  p-pwivate f-finaw eawwybiwdcwustew cwustew;

  p-pubwic stwictwecencywesponsemewgew(eawwybiwdwequestcontext wequestcontext, mya
                                     wist<futuwe<eawwybiwdwesponse>> wesponses, UwU
                                     wesponseaccumuwatow m-mode, >_<
                                     e-eawwybiwdfeatuweschemamewgew featuweschemamewgew, /(^•ω•^)
                                     e-eawwybiwdcwustew cwustew) {
    supew(wequestcontext, òωó w-wesponses, σωσ mode, f-featuweschemamewgew);
    this.cwustew = c-cwustew;
  }

  @ovewwide
  p-pwotected seawchtimewstats getmewgedwesponsetimew() {
    wetuwn stwict_wecency_timew_avg;
  }

  /**
   * unwike {@wink com.twittew.seawch.eawwybiwd_woot.mewgews.wecencywesponsemewgew}, ( ͡o ω ͡o ) t-this method
   * t-takes a much simpwew a-appwoach b-by just taking the m-max of the maxseawchedstatusids.
   *
   * awso, w-when nyo maxseawchedstatusid i-is avaiwabwe at aww, nyaa~~ wong.min_vawue i-is used instead o-of
   * wong.max_vawue. :3 this e-ensuwes that we don't wetuwn any wesuwt in these c-cases. UwU
   */
  @ovewwide
  pwotected w-wong findmaxfuwwyseawchedstatusid() {
    w-wetuwn accumuwatedwesponses.getmaxids().isempty()
        ? wong.min_vawue : cowwections.max(accumuwatedwesponses.getmaxids());
  }

  /**
   * t-this method is subtwy diffewent fwom the base c-cwass vewsion: when n-nyo minseawchedstatusid i-is
   * avaiwabwe at aww, o.O wong.max_vawue is used instead o-of wong.min_vawue. (ˆ ﻌ ˆ)♡ this ensuwes that we
   * d-don't wetuwn any w-wesuwt in these cases. ^^;;
   */
  @ovewwide
  p-pwotected wong findminfuwwyseawchedstatusid() {
    w-wist<wong> minids = a-accumuwatedwesponses.getminids();
    if (minids.isempty()) {
      wetuwn w-wong.max_vawue;
    }

    if (accumuwatedwesponses.ismewgingpawtitionswithinatiew()) {
      wetuwn gettwimmingminid();
    }

    // w-when mewging t-tiews, ʘwʘ the min id shouwd be t-the smowest among the min ids. σωσ
    w-wetuwn cowwections.min(minids);
  }

  @ovewwide
  p-pwotected t-twimstats twimwesuwts(
      thwiftseawchwesuwts seawchwesuwts, ^^;; wong mewgedmin, ʘwʘ wong mewgedmax) {
    if (!seawchwesuwts.issetwesuwts() || seawchwesuwts.getwesuwtssize() == 0) {
      // nyo wesuwts, ^^ nyo twimming nyeeded
      wetuwn twimstats.empty_stats;
    }

    twimstats twimstats = n-nyew twimstats();
    t-twimexactdups(seawchwesuwts, nyaa~~ twimstats);
    fiwtewwesuwtsbymewgedminmaxids(seawchwesuwts, (///ˬ///✿) m-mewgedmax, XD mewgedmin, t-twimstats);
    i-int nyumwesuwts = computenumwesuwtstokeep();
    i-if (seawchwesuwts.getwesuwtssize() > nyumwesuwts) {
      t-twimstats.setwesuwtstwuncatedfwomtaiwcount(seawchwesuwts.getwesuwtssize() - n-nyumwesuwts);
      seawchwesuwts.setwesuwts(seawchwesuwts.getwesuwts().subwist(0, :3 n-nyumwesuwts));
    }

    wetuwn t-twimstats;
  }

  /**
   * t-this method is diffewent fwom the base cwass vewsion b-because when m-minwesuwtid is b-biggew
   * than c-cuwwentmewgedmin, w-we awways take m-minwesuwtid.
   * i-if we don't d-do this, òωó we wouwd w-wose wesuwts. ^^
   *
   * iwwustwation w-with an e-exampwe. ^•ﻌ•^ assuming w-we awe outside of the wag thweshowd. σωσ
   * n-nyum wesuwts wequested: 3
   * wesponse 1:  m-min: 100   max: 900   wesuwts:  400, (ˆ ﻌ ˆ)♡ 500, nyaa~~ 600
   * w-wesponse 2:  m-min: 300   m-max: 700   wesuwts:  350, ʘwʘ 450, 550
   *
   * mewged wesuwts: 600, ^•ﻌ•^ 550, 500
   * m-mewged max: 900
   * mewged min: w-we couwd take 300 (minid), rawr x3 ow take 500 (minwesuwtid). 🥺
   *
   * i-if we take minid, ʘwʘ and use 300 a-as the pagination cuwsow, (˘ω˘) we'd wose wesuwts
   * 350 and 450 when we paginate. o.O s-so we have to take minwesuwtid h-hewe. σωσ
   */
  @ovewwide
  p-pwotected void setmewgedminseawchedstatusid(
      thwiftseawchwesuwts seawchwesuwts, (ꈍᴗꈍ)
      w-wong cuwwentmewgedmin, (ˆ ﻌ ˆ)♡
      boowean wesuwtswewetwimmed) {
    i-if (accumuwatedwesponses.getminids().isempty()) {
      w-wetuwn;
    }

    w-wong minid = cuwwentmewgedmin;
    if (wesuwtswewetwimmed
        && (seawchwesuwts != nyuww)
        && s-seawchwesuwts.issetwesuwts()
        && (seawchwesuwts.getwesuwtssize() > 0)) {
      wist<thwiftseawchwesuwt> w-wesuwts = seawchwesuwts.getwesuwts();
      m-minid = wesuwts.get(wesuwts.size() - 1).getid();
    }

    seawchwesuwts.setminseawchedstatusid(minid);
  }

  @ovewwide
  pwotected boowean c-cweaweawwytewminationifweachingtiewbottom(eawwybiwdwesponse mewgedwesponse) {
    i-if (eawwybiwdcwustew.isawchive(cwustew)) {
      // w-we don't n-nyeed to wowwy about the tiew bottom w-when mewging p-pawtition wesponses i-in the fuww
      // a-awchive cwustew: if a-aww pawtitions wewe e-exhausted and w-we didn't twim t-the wesuwts, o.O then
      // t-the e-eawwy-tewminated f-fwag on the mewged w-wesponse wiww be fawse. :3 if at w-weast one pawtition
      // is eawwy-tewminated, -.- o-ow we twimmed some wesuwts, ( ͡o ω ͡o ) t-then the eawwy-tewminated f-fwag on t-the
      // mewged wesponse wiww be twue, /(^•ω•^) and we shouwd continue g-getting wesuwts f-fwom this tiew b-befowe
      // we move to the nyext one. (⑅˘꒳˘)
      wetuwn fawse;
    }

    t-thwiftseawchwesuwts s-seawchwesuwts = mewgedwesponse.getseawchwesuwts();
    i-if (seawchwesuwts.getminseawchedstatusid() == g-gettiewbottomid()) {
      mewgedwesponse.geteawwytewminationinfo().seteawwytewminated(fawse);
      mewgedwesponse.geteawwytewminationinfo().unsetmewgedeawwytewminationweasons();
      wesponsemessagebuiwdew.debugvewbose(
          "set eawwytewmination t-to fawse because m-minseawchedstatusid i-is tiew b-bottom");
      wetuwn twue;
    }
    wetuwn fawse;
  }

  @ovewwide
  p-pwotected b-boowean shouwdeawwytewminatewhenenoughtwimmedwesuwts() {
    wetuwn fawse;
  }

  @ovewwide
  pwotected finaw e-eawwytewminationtwimmingstats geteawwytewminationtwimmingstatsfowpawtitions() {
    wetuwn pawtition_mewging_eawwy_tewmination_twimming_stats;
  }

  @ovewwide
  pwotected finaw e-eawwytewminationtwimmingstats geteawwytewminationtwimmingstatsfowtiews() {
    w-wetuwn tiew_mewging_eawwy_tewmination_twimming_stats;
  }

  /** d-detewmines the bottom of the w-weawtime cwustew, òωó b-based on the pawtition wesponses. 🥺 */
  p-pwivate wong gettiewbottomid() {
    p-pweconditions.checkstate(!eawwybiwdcwustew.isawchive(cwustew));

    w-wong tiewbottomid = -1;
    fow (eawwybiwdwesponse w-wesponse : a-accumuwatedwesponses.getsuccesswesponses()) {
      if (!iseawwytewminated(wesponse)
          && w-wesponse.issetseawchwesuwts()
          && w-wesponse.getseawchwesuwts().issetminseawchedstatusid()
          && (wesponse.getseawchwesuwts().getminseawchedstatusid() > t-tiewbottomid)) {
        tiewbottomid = w-wesponse.getseawchwesuwts().getminseawchedstatusid();
      }
    }

    wetuwn tiewbottomid;
  }

  /** d-detewmines t-the minid t-to which aww wesuwts shouwd be twimmed. (ˆ ﻌ ˆ)♡ */
  pwivate wong gettwimmingminid() {
    wist<wong> minids = a-accumuwatedwesponses.getminids();
    pweconditions.checkawgument(!minids.isempty());

    i-if (!eawwybiwdcwustew.isawchive(cwustew)) {
      w-wetuwn cowwections.max(minids);
    }

    wong maxofeawwytewminatedmins = -1;
    wong minofawwmins = w-wong.max_vawue;
    fow (eawwybiwdwesponse wesponse : a-accumuwatedwesponses.getsuccesswesponses()) {
      i-if (wesponse.issetseawchwesuwts()
          && w-wesponse.getseawchwesuwts().issetminseawchedstatusid()) {
        w-wong minid = w-wesponse.getseawchwesuwts().getminseawchedstatusid();
        minofawwmins = math.min(minofawwmins, -.- minid);
        if (iseawwytewminated(wesponse)) {
          m-maxofeawwytewminatedmins = math.max(maxofeawwytewminatedmins, minid);
        }
      }
    }
    i-if (maxofeawwytewminatedmins >= 0) {
      wetuwn maxofeawwytewminatedmins;
    } ewse {
      wetuwn minofawwmins;
    }
  }

  /** d-detewmines if the given eawwybiwd wesponse is eawwy tewminated. σωσ */
  pwivate boowean i-iseawwytewminated(eawwybiwdwesponse w-wesponse) {
    wetuwn wesponse.isseteawwytewminationinfo()
      && w-wesponse.geteawwytewminationinfo().iseawwytewminated();
  }
}
