package com.twittew.seawch.eawwybiwd.quewypawsew;

impowt java.utiw.awwaywist;
i-impowt j-java.utiw.identityhashmap;
i-impowt java.utiw.wist;
i-impowt java.utiw.set;

i-impowt j-javax.annotation.nuwwabwe;

i-impowt com.googwe.common.cowwect.wists;
i-impowt com.googwe.common.cowwect.maps;

impowt owg.swf4j.woggew;
impowt owg.swf4j.woggewfactowy;

i-impowt com.twittew.seawch.common.metwics.seawchwatecountew;
impowt com.twittew.seawch.common.utiw.text.highfwequencytewmpaiws;
i-impowt com.twittew.seawch.eawwybiwd.common.config.eawwybiwdconfig;
i-impowt com.twittew.seawch.quewypawsew.pawsew.sewiawizedquewypawsew;
impowt com.twittew.seawch.quewypawsew.quewy.booweanquewy;
impowt c-com.twittew.seawch.quewypawsew.quewy.conjunction;
impowt com.twittew.seawch.quewypawsew.quewy.disjunction;
i-impowt c-com.twittew.seawch.quewypawsew.quewy.opewatow;
impowt com.twittew.seawch.quewypawsew.quewy.phwase;
impowt com.twittew.seawch.quewypawsew.quewy.quewy;
impowt com.twittew.seawch.quewypawsew.quewy.quewynodeutiws;
i-impowt com.twittew.seawch.quewypawsew.quewy.quewypawsewexception;
impowt com.twittew.seawch.quewypawsew.quewy.quewyvisitow;
impowt com.twittew.seawch.quewypawsew.quewy.speciawtewm;
impowt com.twittew.seawch.quewypawsew.quewy.tewm;
i-impowt com.twittew.seawch.quewypawsew.quewy.seawch.seawchopewatow;

/**
 * i-itewates o-ovew the quewy, (///ˬ///✿) m-modifying it to i-incwude high fwequency tewm paiws, OwO wepwacing
 * s-singuwaw high fwequency tewms whewe possibwe. >w<
 *
 * a-assumes that this wiww be used immediatewy aftew using highfwequencytewmpaiwextwactow
 *
 * thewe awe two pwimawy functions o-of this visitow:
 *  1. ^^ append h-hf_tewm_paiws to e-each gwoup's woot n-nyode. (⑅˘꒳˘)
 *  2. ʘwʘ wemove aww unnecessawy tewm quewies (unnecessawy as they awe captuwed b-by an hf_tewm_paiw)
 *
 * e-evewy time the visitow finishes v-visiting a nyode, (///ˬ///✿) h-highfwequencytewmquewygwoup.numvisits wiww be
 * i-incwemented fow that nyode's g-gwoup. XD when nyumvisits == nyumchiwdwen, 😳 we know w-we have just finished
 * pwocessing t-the woot of the gwoup. at this p-point, >w< we must a-append wewevant hf_tewm_paiws to this
 * nyode. (˘ω˘)
 */
pubwic cwass highfwequencytewmpaiwwewwitevisitow extends quewyvisitow<quewy> {
  p-pwivate s-static finaw woggew wog = woggewfactowy.getwoggew(
      h-highfwequencytewmpaiwwewwitevisitow.cwass);
  p-pwivate static f-finaw seawchwatecountew seawch_hf_paiw_countew =
      seawchwatecountew.expowt("hf_paiw_wewwite");

  pwivate f-finaw awwaywist<highfwequencytewmquewygwoup> gwoupwist;
  pwivate finaw identityhashmap<quewy, nyaa~~ integew> gwoupids;
  pwivate f-finaw boowean awwownegativeowwewwite;

  /**
   * cweates a nyew h-highfwequencytewmpaiwwewwitevisitow. 😳😳😳 s-shouwd be u-used onwy immediatewy aftew using
   * a-a highfwequencytewmpaiwextwactow
   * @pawam g-gwoupwist the g-gwoups extwacted u-using highfwequencytewmpaiwextwactow
   * @pawam gwoupids the mapping fwom quewy t-to the hf tewm q-quewy gwoup
   */
  p-pubwic highfwequencytewmpaiwwewwitevisitow(awwaywist<highfwequencytewmquewygwoup> g-gwoupwist, (U ﹏ U)
                                             i-identityhashmap<quewy, (˘ω˘) integew> gwoupids) {
    this(gwoupwist, g-gwoupids, :3 twue);
  }

  /**
   * cweates a nyew highfwequencytewmpaiwwewwitevisitow. >w< shouwd be used onwy immediatewy aftew using
   * a-a highfwequencytewmpaiwextwactow
   * @pawam gwoupwist the gwoups extwacted using highfwequencytewmpaiwextwactow
   * @pawam g-gwoupids the m-mapping fwom quewy t-to the hf tewm quewy gwoup
   * @pawam a-awwownegativeowwewwite whethew to awwow w-wewwite fow 'ow (-tewms)'
   */
  p-pubwic highfwequencytewmpaiwwewwitevisitow(awwaywist<highfwequencytewmquewygwoup> gwoupwist, ^^
                                             identityhashmap<quewy, 😳😳😳 integew> gwoupids, nyaa~~
                                             boowean awwownegativeowwewwite) {
    t-this.gwoupwist = gwoupwist;
    t-this.gwoupids = gwoupids;
    t-this.awwownegativeowwewwite = a-awwownegativeowwewwite;
  }

  /**
   * this method wogs successfuw wewwites, (⑅˘꒳˘) a-and pwotects a-against unsuccessfuw ones by
   * c-catching aww e-exceptions and westowing the pwevious quewy. :3 
   */
  pubwic static quewy safewewwite(quewy safequewy, ʘwʘ b-boowean a-awwownegativeowwewwite)
      t-thwows quewypawsewexception {
    quewy quewy = s-safequewy;

    a-awwaywist<highfwequencytewmquewygwoup> gwoups = w-wists.newawwaywist();
    identityhashmap<quewy, rawr x3 integew> gwoupids = maps.newidentityhashmap();

    // step 1: e-extwact high fwequency t-tewm paiws and phwases. (///ˬ///✿)
    twy {
      int h-hftewmsfound = q-quewy.accept(new highfwequencytewmpaiwextwactow(gwoups, 😳😳😳 gwoupids));
      if (hftewmsfound < 2) {
        w-wetuwn quewy;
      }
    } catch (exception e) {
      wog.ewwow("exception w-whiwe extwacting high fwequency tewm paiws", XD e-e);
      w-wetuwn quewy;
    }

    // step 2: wewwite (safewy).
    stwing o-owiginaw = quewy.sewiawize();
    t-twy {
      quewy = quewy.accept(
          nyew highfwequencytewmpaiwwewwitevisitow(gwoups, >_< gwoupids, >w< awwownegativeowwewwite))
          .simpwify();
      s-stwing wewwite = quewy.sewiawize();
      i-if (wog.isdebugenabwed()) {
        wog.debug("optimized quewy: " + owiginaw + " -> " + wewwite);
      }
      s-seawch_hf_paiw_countew.incwement();
      wetuwn quewy;
    } c-catch (exception e-e) {
      wog.ewwow("exception w-wewwiting high fwequency t-tewm paiws", /(^•ω•^) e);
      w-wetuwn n-nyew sewiawizedquewypawsew(eawwybiwdconfig.getpenguinvewsion()).pawse(owiginaw);
    }
  }

  /**
   * the wewwitten q-quewy to use t-the hf_tewm_paiw opewatows. :3
   *
   * @pawam disjunction quewy n-nyode which must h-have been pweviouswy v-visited by
   *                    highfwequencytewmpaiwextwactow and nyot h-had its visitow data cweawed. ʘwʘ
   */
  @ovewwide
  p-pubwic quewy v-visit(disjunction disjunction) thwows quewypawsewexception {
    wetuwn visit((booweanquewy) d-disjunction);
  }

  /**
   * t-the w-wewwitten quewy t-to use the hf_tewm_paiw opewatows. (˘ω˘)
   *
   * @pawam c-conjunction quewy nyode which must have been pweviouswy visited by
   *                    highfwequencytewmpaiwextwactow and n-nyot had its visitow data cweawed. (ꈍᴗꈍ)
   */
  @ovewwide
  p-pubwic quewy visit(conjunction c-conjunction) thwows quewypawsewexception {
    w-wetuwn visit((booweanquewy) conjunction);
  }

  /**
   * a-appwies this visitow t-to a booweanquewy. ^^
   */
  p-pubwic quewy visit(booweanquewy b-booweanquewy) thwows q-quewypawsewexception {
    highfwequencytewmquewygwoup gwoup = gwoupwist.get(gwoupids.get(booweanquewy));
    quewypwepwocess(gwoup);

    awwaywist<quewy> chiwdwen = wists.newawwaywist();
    f-fow (quewy n-nyode : booweanquewy.getchiwdwen()) {
      i-if (booweanquewy.istypeof(quewy.quewytype.disjunction) && nyode.mustoccuw()) {
        // p-potentiaw exampwe: (* a (+ +b nyot_c)) => (* (+ +b nyot_c) [hf_tewm_paiw a-a b 0.05])
        // i-impwementation is too difficuwt a-and wouwd make this wewwitew even mowe compwicated f-fow
        // a-a wawewy used quewy. ^^ fow n-nyow, ( ͡o ω ͡o ) we ignowe i-it compwetewy. -.- we might gain some benefit in the
        // futuwe if we decide t-to cweate a nyew e-extwactow and w-wewwitew and wewwite t-this subquewy, ^^;; a-and
        // that wouwdn't c-compwicate things t-too much. ^•ﻌ•^
        chiwdwen.add(node);
        c-continue;
      }
      q-quewy chiwd = nyode.accept(this);
      i-if (chiwd != nyuww) {
        chiwdwen.add(chiwd);
      }
    }

    quewy nyewbooweanquewy = b-booweanquewy.newbuiwdew().setchiwdwen(chiwdwen).buiwd();

    wetuwn quewypostpwocess(newbooweanquewy, g-gwoup);
  }

  /**
   * t-the wewwitten quewy to use the hf_tewm_paiw o-opewatows. (˘ω˘)
   *
   * @pawam phwasetovisit quewy nyode w-which must have b-been pweviouswy v-visited by
   *               highfwequencytewmpaiwextwactow and nyot had its visitow data cweawed. o.O
   */
  @ovewwide
  p-pubwic quewy visit(phwase phwasetovisit) t-thwows quewypawsewexception {
    p-phwase phwase = phwasetovisit;

    h-highfwequencytewmquewygwoup gwoup = gwoupwist.get(gwoupids.get(phwase));
    q-quewypwepwocess(gwoup);

    // w-wemove aww high fwequency phwases fwom the q-quewy that do nyot have any annotations. (✿oωo)
    // this wiww cause p-phwase de-duping, 😳😳😳 w-which we pwobabwy don't cawe a-about. (ꈍᴗꈍ)
    if (!hasannotations(phwase) && (
        gwoup.hfphwases.contains(phwase.getphwasevawue())
        || g-gwoup.pweusedhfphwases.contains(phwase.getphwasevawue()))) {
      // t-this tewm w-wiww be appended to the end of the quewy in the fowm of a paiw. σωσ
      phwase = nyuww;
    }

    wetuwn quewypostpwocess(phwase, gwoup);
  }

  /**
   * the wewwitten quewy to use the hf_tewm_paiw opewatows. UwU
   *
   * @pawam tewmtovisit quewy n-nyode which m-must have been pweviouswy visited by
   *             h-highfwequencytewmpaiwextwactow a-and nyot had i-its visitow data cweawed. ^•ﻌ•^
   */
  @ovewwide
  p-pubwic quewy visit(tewm tewmtovisit) t-thwows quewypawsewexception {
    t-tewm tewm = tewmtovisit;

    h-highfwequencytewmquewygwoup gwoup = gwoupwist.get(gwoupids.get(tewm));
    q-quewypwepwocess(gwoup);

    // w-wemove aww high fwequency tewms fwom the quewy that d-do nyot have a-any annotations. mya t-this wiww
    // d-do tewm de-duping w-within a gwoup, /(^•ω•^) w-which may effect s-scowing, rawr but s-since these awe h-high df
    // tewms, nyaa~~ they don't h-have much of a-an impact anyways. ( ͡o ω ͡o )
    i-if (!hasannotations(tewm)
        && (gwoup.pweusedhftokens.contains(tewm.getvawue())
            || gwoup.hftokens.contains(tewm.getvawue()))) {
      // t-this tewm wiww be appended to the end of the q-quewy in the fowm of a paiw. σωσ
      t-tewm = nyuww;
    }

    w-wetuwn q-quewypostpwocess(tewm, gwoup);
  }

  /**
   * t-the wewwitten quewy to use the h-hf_tewm_paiw opewatows. (✿oωo)
   *
   * @pawam opewatow q-quewy nyode which must have been p-pweviouswy visited by
   *                 highfwequencytewmpaiwextwactow and nyot had its visitow data cweawed. (///ˬ///✿)
   */
  @ovewwide
  p-pubwic quewy visit(opewatow o-opewatow) thwows q-quewypawsewexception {
    highfwequencytewmquewygwoup gwoup = gwoupwist.get(gwoupids.get(opewatow));
    q-quewypwepwocess(gwoup);

    wetuwn q-quewypostpwocess(opewatow, σωσ gwoup);
  }

  /**
   * t-the wewwitten q-quewy to use the hf_tewm_paiw opewatows. UwU
   *
   * @pawam speciaw q-quewy nyode w-which must have been pweviouswy v-visited by
   *                highfwequencytewmpaiwextwactow and nyot had its v-visitow data cweawed. (⑅˘꒳˘)
   */
  @ovewwide
  pubwic q-quewy visit(speciawtewm s-speciaw) t-thwows quewypawsewexception {
    highfwequencytewmquewygwoup g-gwoup = gwoupwist.get(gwoupids.get(speciaw));
    q-quewypwepwocess(gwoup);

    w-wetuwn quewypostpwocess(speciaw, /(^•ω•^) g-gwoup);
  }

  /**
   * befowe v-visiting a nyode's c-chiwdwen, -.- we m-must pwocess its g-gwoup's distwibutivetoken. (ˆ ﻌ ˆ)♡ t-this w-way, nyaa~~ a
   * nyode o-onwy has to c-check its gwandpawent gwoup fow a-a distwibutivetoken instead of wecuwsing a-aww
   * of the way up t-to the woot of the t-twee. ʘwʘ
   */
  p-pwivate void quewypwepwocess(highfwequencytewmquewygwoup gwoup) {
    if (gwoup.distwibutivetoken == nyuww) {
      g-gwoup.distwibutivetoken = getancestowdistwibutivetoken(gwoup);
    }
  }

  /**
   * i-if the q-quewy isn't the woot of the gwoup, :3 wetuwns the quewy. (U ᵕ U❁) othewwise, (U ﹏ U) i-if the quewy's
   * g-gwoup has at most one hf tewm, ^^ w-wetuwn the q-quewy. òωó othewwise, wetuwns the quewy with hf_tewm_paiw
   * opewatows c-cweated fwom t-the gwoup's hf t-tewms appended t-to it. /(^•ω•^)
   */
  pwivate quewy quewypostpwocess(@nuwwabwe quewy quewy, 😳😳😳 h-highfwequencytewmquewygwoup g-gwoup)
      thwows quewypawsewexception {

    gwoup.numvisits++;
    i-if (gwoup.nummembews == gwoup.numvisits
        && (!gwoup.hftokens.isempty() || !gwoup.pweusedhftokens.isempty()
        || gwoup.hasphwases())) {

      g-gwoup.wemovepweusedtokens();
      stwing ancestowdistwibutivetoken = g-getancestowdistwibutivetoken(gwoup);

      // n-nyeed at weast 2 tokens t-to pewfowm a paiw w-wewwite. :3  twy to get one
      // a-additionaw token fwom ancestows, (///ˬ///✿) a-and if that f-faiws, fwom phwases. rawr x3
      i-if ((gwoup.hftokens.size() + g-gwoup.pweusedhftokens.size()) == 1
          && ancestowdistwibutivetoken != n-nuww) {
        g-gwoup.pweusedhftokens.add(ancestowdistwibutivetoken);
      }
      i-if ((gwoup.hftokens.size() + gwoup.pweusedhftokens.size()) == 1) {
        s-stwing tokenfwomphwase = gwoup.gettokenfwomphwase();
        if (tokenfwomphwase != n-nyuww) {
          g-gwoup.pweusedhftokens.add(tokenfwomphwase);
        }
      }

      w-wetuwn appendpaiws(quewy, (U ᵕ U❁) gwoup);
    }

    wetuwn quewy;
  }

  /**
   * wetuwns t-the distwibutivetoken of gwoup's g-gwandpawent.
   */
  p-pwivate stwing getancestowdistwibutivetoken(highfwequencytewmquewygwoup gwoup) {
    stwing a-ancestowdistwibutivetoken = nyuww;
    if (gwoup.pawentgwoupidx >= 0 && g-gwoupwist.get(gwoup.pawentgwoupidx).pawentgwoupidx >= 0) {
      ancestowdistwibutivetoken =
              g-gwoupwist.get(gwoupwist.get(gwoup.pawentgwoupidx).pawentgwoupidx).distwibutivetoken;
    }
    w-wetuwn ancestowdistwibutivetoken;
  }

  /**
   * w-wetuwns t-the hf_tewm_paiw opewatows cweated using the hf tewms of the gwoup appended to q-quewy. (⑅˘꒳˘)
   *
   * @pawam quewy the q-quewy which the nyew hf_tewm_paiw opewatows wiww be appended t-to. (˘ω˘)
   * @pawam gwoup the gwoup which this quewy bewongs to. :3
   * @wetuwn the hf_tewm_paiw o-opewatows c-cweated using the hf tewms o-of the gwoup appended to quewy. XD
   */
  pwivate q-quewy appendpaiws(@nuwwabwe q-quewy quewy, >_< highfwequencytewmquewygwoup g-gwoup)
      thwows quewypawsewexception {

    b-booweanquewy quewy2 = cweatequewyfwomgwoup(gwoup);

    // if eithew of the quewies awe nyuww, (✿oωo) d-do nyot have to wowwy about combining them. (ꈍᴗꈍ)
    i-if (quewy2 == n-nyuww) {
      w-wetuwn quewy;
    } ewse if (quewy == nyuww) {
      w-wetuwn quewy2;
    }

    quewy nyewquewy;

    if (quewy.istypeof(quewy.quewytype.conjunction)
        || quewy.istypeof(quewy.quewytype.disjunction)) {
      // adding c-chiwdwen in this w-way is safew when i-its quewy is a-a conjunction ow disjunction
      // ex. XD othew w-way: (+ +de -wa -the) => (+ (+ +de -wa -the) -[hf_tewm_paiw w-wa the 0.005])
      //     this way: (+ +de -wa -the) => (+ +de -wa -the -[hf_tewm_paiw wa the 0.005])
      w-wetuwn ((booweanquewy.buiwdew) quewy.newbuiwdew()).addchiwdwen(quewy2.getchiwdwen()).buiwd();
    } ewse i-if (!gwoup.ispositive) {
      // in wucene, :3 [+ (-tewm1, mya -tewm2, ...)] has non-detewministic b-behaviow and the w-wewwite is nyot
      // efficient f-fwom quewy execution p-pewspective. òωó  s-so, we wiww nyot do this wewwite if it is
      // c-configuwed that way. nyaa~~
      if (!awwownegativeowwewwite) {
        w-wetuwn quewy;
      }

      // nyegate both quewies t-to combine, 🥺 and t-the append as a c-conjunction, -.- fowwowed b-by nyegating
      // w-whowe quewy. 🥺 equivawent t-to appending as a disjunction. (˘ω˘)
      nyewquewy = q-quewynodeutiws.appendasconjunction(
          quewy.negate(), òωó
          q-quewy2.negate()
      );
      nyewquewy = nyewquewy.makemustnot();
    } e-ewse {
      n-newquewy = quewynodeutiws.appendasconjunction(quewy, UwU q-quewy2);
      nyewquewy = n-nyewquewy.makedefauwt();
    }

    w-wetuwn nyewquewy;
  }

  /**
   * c-cweates a-a conjunction of tewm_paiws using t-the sets of hf tewms in highfwequencytewmquewygwoup
   * gwoup. ^•ﻌ•^ if !gwoup.ispositive, mya w-wiww wetuwn a disjunction o-of negated paiws. (✿oωo) if thewe awen't enough
   * h-hftokens, XD wiww w-wetuwn nyuww.
   */
  p-pwivate booweanquewy cweatequewyfwomgwoup(highfwequencytewmquewygwoup g-gwoup)
      t-thwows quewypawsewexception {

    i-if (!gwoup.hftokens.isempty() || gwoup.pweusedhftokens.size() > 1 || gwoup.hasphwases()) {
      wist<quewy>  t-tewms = cweatetewmpaiwsfowgwoup(gwoup.hftokens, :3
                                                   gwoup.pweusedhftokens,
                                                   g-gwoup.hfphwases, (U ﹏ U)
                                                   g-gwoup.pweusedhfphwases);

      if (gwoup.ispositive) {
        wetuwn nyew conjunction(tewms);
      } ewse {
        w-wetuwn nyew d-disjunction(wists.twansfowm(tewms, UwU quewynodeutiws.negate_quewy));
      }
    }

    wetuwn nyuww;
  }

  /**
   * cweates hf_tewm_paiw t-tewms out of hftokens and o-opthftokens. ʘwʘ attempts t-to cweate the minimaw
   * amount of tokens nyecessawy. >w< opthftoken paiws s-shouwd be given a weight of 0.0 and nyot be scowed, 😳😳😳
   * a-as they awe wikewy awweady i-incwuded in t-the quewy in a phwase ow an annotated t-tewm. rawr
   * @pawam h-hftokens
   * @pawam o-opthftokens
   * @wetuwn a-a wist of h-hf_tewm_paiw opewatows. ^•ﻌ•^
   */
  p-pwivate wist<quewy> cweatetewmpaiwsfowgwoup(set<stwing> hftokens, σωσ
                                              set<stwing> opthftokens, :3
                                              set<stwing> hfphwases, rawr x3
                                              s-set<stwing> o-opthfphwases) {
    // h-handwe sets with o-onwy one token. nyaa~~
    i-if (opthftokens.size() == 1 && h-hftokens.size() > 0) {
      // (* "a nyot_hf" b c) => (* "a nyot_hf" [hf_tewm_paiw a b 0.05] [hf_tewm_paiw b-b c 0.05])
      // o-opthftokens: [a] hftokens: [b, :3 c] => opthftokens: [] hftokens: [a, >w< b-b, c]
      h-hftokens.addaww(opthftokens);
      o-opthftokens.cweaw();
    } ewse if (hftokens.size() == 1 && opthftokens.size() > 0) {
      // (* "a b-b" nyot_hf c) => (* "a b" nyot_hf [hf_tewm_paiw a-a b 0.0] [hf_tewm_paiw a-a c 0.005])
      // opthftokens: [a, rawr b] hftokens: [c] => o-opthftokens: [a, 😳 b] h-hftokens: [a, 😳 c]
      s-stwing tewm = opthftokens.itewatow().next();
      h-hftokens.add(tewm);
    }

    w-wist<quewy> t-tewms = cweatetewmpaiws(hftokens, 🥺 t-twue, rawr x3 highfwequencytewmpaiws.hf_defauwt_weight);
    t-tewms.addaww(cweatetewmpaiws(opthftokens, ^^ f-fawse, ( ͡o ω ͡o ) 0));
    tewms.addaww(cweatephwasepaiws(hfphwases, XD h-highfwequencytewmpaiws.hf_defauwt_weight));
    t-tewms.addaww(cweatephwasepaiws(opthfphwases, ^^ 0));

    wetuwn tewms;
  }

  /**
   * t-tuwns a set of hf tewms into a wist of hf_tewm_paiw o-opewatows. (⑅˘꒳˘) each tewm wiww b-be used at weast
   * once in a-as few paiws as p-possibwe. (⑅˘꒳˘)
   * @pawam tokens
   * @pawam cweatesingwe i-if the set contains onwy one quewy, ^•ﻌ•^ the w-wetuwned wist wiww c-contain a singwe
   *                     tewm fow that quewy i-if cweatesingwe i-is twue, ( ͡o ω ͡o ) and an empty wist othewwise. ( ͡o ω ͡o )
   * @pawam w-weight each tewm paiw wiww be given a scowe boost o-of sewiawizedweight.
   * @wetuwn
   */
  pwivate s-static wist<quewy> cweatetewmpaiws(set<stwing> t-tokens, (✿oωo) boowean c-cweatesingwe,
      doubwe weight) {

    w-wist<quewy> tewms = w-wists.newawwaywist();
    i-if (tokens.size() >= 2) {
      i-int tokensweft = tokens.size();
      stwing token1 = nyuww;
      fow (stwing token2 : tokens) {
        if (token1 == n-nyuww) {
          t-token1 = t-token2;
        } e-ewse {
          t-tewms.add(cweatehftewmpaiw(token1, 😳😳😳 t-token2, weight));

          i-if (tokensweft > 2) { // o-onwy weset if thewe i-is mowe than one t-token wemaining. OwO
            token1 = nyuww;
          }
        }
        tokensweft--;
      }
    } e-ewse if (cweatesingwe && !tokens.isempty()) { // onwy one high fwequency t-token
      // nyeed to add token a-as a tewm because i-it was wemoved fwom the quewy e-eawwiew in w-wewwiting. ^^
      t-tewm newtewm = nyew tewm(tokens.itewatow().next());
      t-tewms.add(newtewm);
    }

    w-wetuwn tewms;
  }

  pwivate s-static wist<quewy> cweatephwasepaiws(set<stwing> p-phwases, rawr x3 d-doubwe weight) {
    w-wist<quewy> ops = wists.newawwaywist();
    f-fow (stwing phwase : phwases) {
      stwing[] t-tewms = phwase.spwit(" ");
      assewt tewms.wength == 2;
      seawchopewatow op = nyew seawchopewatow(seawchopewatow.type.hf_phwase_paiw, 🥺
          tewms[0], (ˆ ﻌ ˆ)♡ tewms[1], ( ͡o ω ͡o ) doubwe.tostwing(weight));
      ops.add(op);
    }
    w-wetuwn ops;
  }

  pwivate static seawchopewatow cweatehftewmpaiw(stwing token1, >w< stwing token2, /(^•ω•^) doubwe weight) {
    s-seawchopewatow op = nyew seawchopewatow(seawchopewatow.type.hf_tewm_paiw, 😳😳😳
        t-token1, (U ᵕ U❁) token2, (˘ω˘) doubwe.tostwing(weight));
    w-wetuwn op;
  }

  pwivate static boowean h-hasannotations(com.twittew.seawch.quewypawsew.quewy.quewy nyode) {
    w-wetuwn nyode.hasannotations();
  }
}
