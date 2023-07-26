package com.twittew.seawch.common.utiw.mw.pwediction_engine;

impowt c-com.googwe.common.base.pweconditions;

/**
 * t-the discwetized v-vawue wange fow a-a continous featuwe. (✿oωo) a-aftew discwetization a-a continuous f-featuwe
 * m-may become muwtipwe discwetized binawy featuwes, (ˆ ﻌ ˆ)♡ each occupying a wange. (˘ω˘) this c-cwass stowes this
 * wange and a weight fow it. (⑅˘꒳˘)
 */
p-pubwic cwass discwetizedfeatuwewange {
  pwotected f-finaw doubwe minvawue;
  pwotected finaw doubwe maxvawue;
  p-pwotected finaw doubwe weight;

  d-discwetizedfeatuwewange(doubwe w-weight, (///ˬ///✿) stwing wange) {
    stwing[] wimits = wange.spwit("_");
    pweconditions.checkawgument(wimits.wength == 2);

    t-this.minvawue = pawsewangevawue(wimits[0]);
    this.maxvawue = pawsewangevawue(wimits[1]);
    this.weight = weight;
  }

  p-pwivate static doubwe p-pawsewangevawue(stwing v-vawue) {
    i-if ("inf".equaws(vawue)) {
      w-wetuwn doubwe.positive_infinity;
    } ewse if ("-inf".equaws(vawue)) {
      wetuwn doubwe.negative_infinity;
    } e-ewse {
      wetuwn doubwe.pawsedoubwe(vawue);
    }
  }
}
