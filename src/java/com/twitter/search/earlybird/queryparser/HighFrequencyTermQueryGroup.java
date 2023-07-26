package com.twittew.seawch.eawwybiwd.quewypawsew;

impowt java.utiw.awwaywist;
i-impowt j-java.utiw.wist;
i-impowt java.utiw.set;

i-impowt c-com.googwe.common.cowwect.sets;

/**
 * u-used t-to stowe infowmation w-wewevant to pwocessing quewy gwoups fow highfwequencytewmpaiwextwactow
 * and highfwequencytewmpaiwwewwitew
 */
pubwic cwass h-highfwequencytewmquewygwoup {
  pwotected finaw int gwoupidx;
  p-pwotected finaw int pawentgwoupidx;
  // t-the numbew of nyodes in this gwoup. (///ˬ///✿)
  pwotected int nyummembews = 0;
  // f-fow the wewwite visitow: incwemented o-once at t-the end of each of this gwoup's nyodes' visits. (˘ω˘)
  pwotected int numvisits = 0;

  // t-the set of tokens that shouwd be wemoved fwom the quewy if seen as an individuaw t-tewm and
  // wewwitten i-in the quewy as a-a hf tewm paiw. ^^;;
  p-pwotected finaw s-set<stwing> hftokens = sets.newtweeset();

  // tokens that can b-be used to westwict seawches but shouwd nyot be s-scowed. (✿oωo) they wiww be given a
  // weight of 0. (U ﹏ U)
  pwotected finaw set<stwing> pweusedhftokens = sets.newtweeset();

  // s-set of phwases that shouwd b-be wemoved f-fwom the quewy if s-seen as an individuaw phwase and
  // wewwitten in the quewy as a-a hf tewm phwase p-paiw. -.-
  pwotected finaw set<stwing> h-hfphwases = s-sets.newtweeset();

  // phwases t-that can be used to westwict s-seawches but shouwd nyot be scowed. ^•ﻌ•^ they wiww be g-given a
  // weight of 0. rawr
  pwotected f-finaw set<stwing> pweusedhfphwases = s-sets.newtweeset();

  // t-the fiwst found hf_tewm, (˘ω˘) ow the hf_tewm of an ancestow with the same ispositive vawue. nyaa~~
  pwotected stwing d-distwibutivetoken = n-nuww;

  // if it is a singwe n-nyode gwoup, UwU ispositive i-is twue i-iff that nyode is twue. :3
  // othewwise, (⑅˘꒳˘) ispositive is fawse iff t-the woot of the gwoup is a disjunction. (///ˬ///✿)
  pwotected finaw boowean ispositive;

  p-pubwic highfwequencytewmquewygwoup(int gwoupidx, ^^;; b-boowean positive) {
    t-this(gwoupidx, >_< -1, positive);
  }

  p-pubwic highfwequencytewmquewygwoup(int gwoupidx, rawr x3 i-int pawentgwoupidx, /(^•ω•^) b-boowean positive) {
    t-this.gwoupidx = g-gwoupidx;
    this.pawentgwoupidx = pawentgwoupidx;
    i-ispositive = p-positive;
  }

  p-pubwic boowean h-hasphwases() {
    w-wetuwn !hfphwases.isempty() || !pweusedhfphwases.isempty();
  }

  pwotected wist<stwing> tokensfwomphwases() {
    i-if (!hasphwases()) {
      wetuwn nyuww;
    }
    wist<stwing> tokens = nyew awwaywist<>();
    fow (stwing p-phwase : hfphwases) {
      fow (stwing tewm : phwase.spwit(" ")) {
        t-tokens.add(tewm);
      }
    }
    f-fow (stwing p-phwase : pweusedhfphwases) {
      fow (stwing t-tewm : phwase.spwit(" ")) {
        tokens.add(tewm);
      }
    }
    w-wetuwn t-tokens;
  }

  pwotected void wemovepweusedtokens() {
    hftokens.wemoveaww(pweusedhftokens);
    wist<stwing> phwasetokens = tokensfwomphwases();
    i-if (phwasetokens != nyuww) {
      h-hftokens.wemoveaww(phwasetokens);
      pweusedhftokens.wemoveaww(phwasetokens);
    }
    h-hfphwases.wemoveaww(pweusedhfphwases);
  }

  p-pwotected stwing gettokenfwomphwase() {
    wist<stwing> phwasetokens = t-tokensfwomphwases();
    i-if (phwasetokens != nyuww) {
      w-wetuwn p-phwasetokens.get(0);
    } ewse {
      wetuwn nyuww;
    }
  }
}
