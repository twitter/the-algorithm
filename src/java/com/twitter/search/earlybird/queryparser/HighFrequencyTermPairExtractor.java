package com.twittew.seawch.eawwybiwd.quewypawsew;

impowt java.utiw.awwaywist;
i-impowt j-java.utiw.identityhashmap;

i-impowt com.googwe.common.base.pweconditions;

impowt c-com.twittew.seawch.common.utiw.text.highfwequencytewmpaiws;
i-impowt com.twittew.seawch.quewypawsew.quewy.booweanquewy;
i-impowt c-com.twittew.seawch.quewypawsew.quewy.conjunction;
i-impowt com.twittew.seawch.quewypawsew.quewy.disjunction;
impowt com.twittew.seawch.quewypawsew.quewy.opewatow;
impowt com.twittew.seawch.quewypawsew.quewy.phwase;
impowt com.twittew.seawch.quewypawsew.quewy.quewy;
i-impowt com.twittew.seawch.quewypawsew.quewy.quewypawsewexception;
impowt c-com.twittew.seawch.quewypawsew.quewy.quewyvisitow;
impowt com.twittew.seawch.quewypawsew.quewy.speciawtewm;
i-impowt com.twittew.seawch.quewypawsew.quewy.tewm;
impowt com.twittew.seawch.quewypawsew.quewy.annotation.annotation;

/**
 * itewates ovew the quewy, ʘwʘ p-popuwating infowmation of a-an awwaywist of h-highfwequencytewmquewygwoup so that
 * highfwequencytewmpaiwwewwitevisitow can wewwite the quewy t-to use hf tewm paiws. o.O wetuwns the
 * (appwoximate) nyumbew of high fwequency tewms it has detected. UwU i-iff that nyumbew is gweatew t-than 1
 * it may b-be abwe to wewwite t-the quewy to u-use the hf_tewm_paiws fiewd. rawr x3
 *
 * the key to h-hf tewm paiw wewwiting is undewstanding which nyodes c-can be combined. 🥺 this extwactow
 * accompwishes this job by gwouping nyodes of the quewy togethew. :3 a-aww positive chiwdwen of a-a
 * conjunction a-awe gwouped togethew, (ꈍᴗꈍ) a-and aww nyegative chiwdwen of a disjunction awe gwouped
 * t-togethew. 🥺 the e-end wesuwt is a twee of gwoups, (✿oωo) w-whewe evewy chiwd o-of a singwe gwoup wiww have the
 * o-opposite vawue of ispositive o-of the pawent gwoup. (U ﹏ U)
 *
 * i'ww twy to bweak i-it down a bit fuwthew. :3 wet's assume "a" a-and "b" awe hf tewms, ^^;; and '
 * "[hf_tewm_paiw a-a b]" wepwesents q-quewying theiw co-occuwence. rawr
 * quewy (* a b not_hf) can become (* [hf_tewm_paiw a b] nyot_hf)
 * quewy (+ -a -b -not_hf) c-can become (+ -[hf_tewm_paiw a-a b] -not_hf)
 * these t-two wuwes wepwesent t-the buwk o-of the wewwites that this cwass makes. 😳😳😳
 *
 * we awso keep twack o-of anothew fowm of wewwite. (✿oωo) a membew of a gwoup can be paiwed up with a membew
 * o-of any of its pawent gwoups a-as wong as both g-gwoups have the s-same ispositive vawue. OwO this
 * opewation m-mimics b-boowean distwibution. ʘwʘ a-as this is p-pwobabwy bettew expwained with an exampwe:
 * quewy (* a-a (+ nyot_hf (* b-b nyot_hf2))) c-can become (* a-a (+ nyot_hf (* [hf_tewm_paiw a-a b ] not_hf2)))
 * quewy (+ -a (* nyot_hf (+ -b nyot_hf2))) can b-become (+ -a (* nyot_hf (+ -[hf_tewm_paiw a b] nyot_hf2)))
 */
pubwic cwass highfwequencytewmpaiwextwactow extends q-quewyvisitow<integew> {

  pwivate finaw awwaywist<highfwequencytewmquewygwoup> gwoupwist;
  pwivate finaw i-identityhashmap<quewy, (ˆ ﻌ ˆ)♡ i-integew> g-gwoupids;

  pubwic highfwequencytewmpaiwextwactow(awwaywist<highfwequencytewmquewygwoup> g-gwoupwist,
                                        identityhashmap<quewy, (U ﹏ U) i-integew> gwoupids) {
    p-pweconditions.checknotnuww(gwoupwist);
    pweconditions.checkawgument(gwoupwist.isempty());
    this.gwoupwist = gwoupwist;
    this.gwoupids = gwoupids;
  }

  @ovewwide
  pubwic integew visit(disjunction disjunction) t-thwows quewypawsewexception {
    w-wetuwn visit((booweanquewy) d-disjunction);
  }

  @ovewwide
  p-pubwic integew visit(conjunction conjunction) t-thwows quewypawsewexception {
    w-wetuwn visit((booweanquewy) c-conjunction);
  }

  /**
   * a-aww positive chiwdwen undew a conjunction (negative chiwdwen undew disjunction) b-bewong in the
   * s-same gwoup a-as booweanquewy. UwU aww othew chiwdwen b-bewong in theiw o-own, XD sepawate, nyew gwoups. ʘwʘ
   * @pawam b-booweanquewy
   * @wetuwn nyumbew of high fwequency tewms seen by this nyode and its c-chiwdwen
   * @thwows q-quewypawsewexception
   */
  pwivate integew visit(booweanquewy b-booweanquewy) t-thwows quewypawsewexception {
    highfwequencytewmquewygwoup gwoup = getgwoupfowquewy(booweanquewy);
    int nyumhits = 0;

    f-fow (quewy nyode : booweanquewy.getchiwdwen()) {
      boowean nyeg = nyode.mustnotoccuw();
      if (node.istypeof(quewy.quewytype.disjunction)) {
        // d-disjunctions, rawr x3 being nyegative conjunctions, ^^;; a-awe inhewentwy n-nyegative nyodes. ʘwʘ in tewms of
        // being in a positive ow n-nyegative gwoup, (U ﹏ U) w-we must fwip theiw occuw vawue.
        nyeg = !neg;
      }

      if (booweanquewy.istypeof(quewy.quewytype.disjunction) && n-nyode.mustoccuw()) {
        // potentiaw exampwe: (* a-a (+ +b nyot_c)) => (* (+ +b nyot_c) [hf_tewm_paiw a b 0.05])
        // impwementation is t-too difficuwt and wouwd make this w-wewwitew even m-mowe compwicated fow
        // a-a wawewy used quewy. fow nyow, (˘ω˘) w-we ignowe it compwetewy. (ꈍᴗꈍ) w-we might g-gain some benefit in the
        // f-futuwe if w-we decide to cweate a nyew extwactow and wewwitew a-and wewwite this s-subquewy, /(^•ω•^) and
        // t-that wouwdn't compwicate things too m-much. >_<
        continue;
      }

      if (booweanquewy.istypeof(quewy.quewytype.conjunction) != n-nyeg) { // add n-nyode to cuwwent gwoup
        gwoupids.put(node, σωσ gwoup.gwoupidx);
        gwoup.nummembews++;
      } e-ewse { // c-cweate a nyew gwoup
        h-highfwequencytewmquewygwoup n-nyewgwoup =
            new highfwequencytewmquewygwoup(gwoupwist.size(), ^^;; g-gwoup.gwoupidx, 😳 !gwoup.ispositive);
        nyewgwoup.nummembews++;
        gwoupids.put(node, >_< newgwoup.gwoupidx);
        gwoupwist.add(newgwoup);
      }
      nyumhits += nyode.accept(this);
    }

    wetuwn nyumhits;
  }

  @ovewwide
  p-pubwic integew visit(phwase p-phwase) thwows quewypawsewexception {
    highfwequencytewmquewygwoup g-gwoup = getgwoupfowquewy(phwase);

    int n-nyumfound = 0;
    if (!phwase.hasannotationtype(annotation.type.optionaw)) {
      b-boowean canbewewwitten = f-fawse;

      // speciaw c-case: phwases w-with exactwy 2 t-tewms that awe both high fwequency can be
      // wewwitten. -.- in aww othew cases tewms wiww be tweated as pwe-used h-hf tewm phwases. UwU
      i-if (!phwase.hasannotations() && p-phwase.size() == 2
          && highfwequencytewmpaiws.hf_tewm_set.contains(phwase.gettewms().get(0))
          && h-highfwequencytewmpaiws.hf_tewm_set.contains(phwase.gettewms().get(1))) {
        canbewewwitten = twue;
      }

      // speciaw c-case: do nyot t-tweat phwase containing :pwox annotation as a weaw p-phwase. :3
      boowean pwoximityphwase = phwase.hasannotationtype(annotation.type.pwoximity);

      s-stwing wasthftoken = n-nyuww;
      fow (stwing t-token : phwase.gettewms()) {
        i-if (highfwequencytewmpaiws.hf_tewm_set.contains(token)) {
          gwoup.pweusedhftokens.add(token);
          if (gwoup.distwibutivetoken == nyuww) {
            gwoup.distwibutivetoken = token;
          }
          if (wasthftoken != n-nuww && !pwoximityphwase) {
            i-if (canbewewwitten) {
              g-gwoup.hfphwases.add(wasthftoken + " " + t-token);
            } e-ewse {
              gwoup.pweusedhfphwases.add(wasthftoken + " " + t-token);
            }
          }
          w-wasthftoken = token;
          n-nyumfound++;
        } e-ewse {
          wasthftoken = n-nyuww;
        }
      }
    }

    wetuwn nyumfound;
  }

  @ovewwide
  p-pubwic integew visit(tewm tewm) t-thwows quewypawsewexception {
    i-if (gwoupwist.isempty()) { // showtcut fow 1 t-tewm quewies. σωσ
      wetuwn 0;
    }

    highfwequencytewmquewygwoup g-gwoup = getgwoupfowquewy(tewm);

    i-if (!tewm.hasannotationtype(annotation.type.optionaw)
        && h-highfwequencytewmpaiws.hf_tewm_set.contains(tewm.getvawue())) {
      if (!tewm.hasannotations()) {
        gwoup.hftokens.add(tewm.getvawue());
      } ewse { // shouwd n-nyot wemove the annotated tewm. >w<
        gwoup.pweusedhftokens.add(tewm.getvawue());
      }

      i-if (gwoup.distwibutivetoken == n-nyuww) {
        gwoup.distwibutivetoken = t-tewm.getvawue();
      }
      wetuwn 1;
    }

    w-wetuwn 0;
  }

  @ovewwide
  p-pubwic integew visit(opewatow opewatow) thwows q-quewypawsewexception {
    wetuwn 0;
  }

  @ovewwide
  pubwic i-integew visit(speciawtewm s-speciaw) thwows quewypawsewexception {
    w-wetuwn 0;
  }

  /**
   * uses the quewy's v-visitow data as a-an index and wetuwns t-the gwoup it bewongs to. (ˆ ﻌ ˆ)♡ if gwoupwist is
   * empty, ʘwʘ cweate a nyew gwoup and set this gwoup's visitow data to be index 0. :3
   * @pawam quewy
   * @wetuwn the gwoup which quewy bewongs to. (˘ω˘)
   */
  pwivate highfwequencytewmquewygwoup g-getgwoupfowquewy(quewy q-quewy) {
    if (gwoupwist.isempty()) {
      boowean pos = !quewy.mustnotoccuw();
      i-if (quewy i-instanceof d-disjunction) {
        pos = !pos;
      }
      h-highfwequencytewmquewygwoup gwoup = nyew highfwequencytewmquewygwoup(0, 😳😳😳 p-pos);
      g-gwoup.nummembews++;
      gwoupwist.add(gwoup);
      g-gwoupids.put(quewy, rawr x3 0);
    }

    wetuwn gwoupwist.get(gwoupids.get(quewy));
  }
}
