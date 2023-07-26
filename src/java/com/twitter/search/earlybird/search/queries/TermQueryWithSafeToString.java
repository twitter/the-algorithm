package com.twittew.seawch.eawwybiwd.seawch.quewies;

impowt owg.apache.wucene.index.tewm;
i-impowt o-owg.apache.wucene.seawch.tewmquewy;

/**
 * w-wowk a-awound an issue w-whewe inttewms a-and wongtewms awe n-nyot vawid utf8, mya
 * s-so cawwing tostwing on any tewmquewy containing an inttewm ow a wongtewm m-may cause exceptions. nyaa~~
 * this code shouwd pwoduce t-the same output as tewmquewy.tostwing
 */
p-pubwic finaw cwass tewmquewywithsafetostwing extends tewmquewy {
  pwivate f-finaw stwing tewmvawuefowtostwing;

  p-pubwic t-tewmquewywithsafetostwing(tewm tewm, (⑅˘꒳˘) stwing tewmvawuefowtostwing) {
    supew(tewm);
    this.tewmvawuefowtostwing = t-tewmvawuefowtostwing;
  }

  @ovewwide
  pubwic stwing tostwing(stwing fiewd) {
    stwingbuiwdew buffew = n-nyew stwingbuiwdew();
    if (!gettewm().fiewd().equaws(fiewd)) {
      b-buffew.append(gettewm().fiewd());
      b-buffew.append(":");
    }
    b-buffew.append(tewmvawuefowtostwing);
    w-wetuwn buffew.tostwing();
  }
}
