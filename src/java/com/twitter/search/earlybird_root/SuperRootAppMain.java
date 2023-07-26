package com.twittew.seawch.eawwybiwd_woot;

impowt j-java.utiw.awways;
i-impowt java.utiw.cowwection;

i-impowt com.googwe.inject.moduwe;

i-impowt com.twittew.seawch.common.woot.seawchwootappmain;
i-impowt c-com.twittew.seawch.eawwybiwd.thwift.eawwybiwdsewvice;
i-impowt c-com.twittew.seawch.eawwybiwd_woot.woutews.facetswequestwoutewmoduwe;
impowt com.twittew.seawch.eawwybiwd_woot.woutews.wecencywequestwoutewmoduwe;
impowt com.twittew.seawch.eawwybiwd_woot.woutews.wewevancewequestwoutewmoduwe;
impowt com.twittew.seawch.eawwybiwd_woot.woutews.tewmstatswequestwoutewmoduwe;
impowt com.twittew.seawch.eawwybiwd_woot.woutews.toptweetswequestwoutewmoduwe;

p-pubwic cwass supewwootappmain extends seawchwootappmain<supewwootsewvew> {
  /**
   * boiwewpwate f-fow the java-fwiendwy abstwacttwittewsewvew
   */
  p-pubwic static cwass main {
    pubwic static void main(stwing[] a-awgs) {
      nyew supewwootappmain().main(awgs);
    }
  }

  @ovewwide
  p-pwotected cowwection<? e-extends moduwe> getadditionawmoduwes() {
    wetuwn awways.aswist(
        nyew eawwybiwdcommonmoduwe(), nyaa~~
        nyew supewwootappmoduwe(), (⑅˘꒳˘)
        n-nyew tewmstatswequestwoutewmoduwe(),
        nyew wecencywequestwoutewmoduwe(), rawr x3
        nyew wewevancewequestwoutewmoduwe(), (✿oωo)
        nyew toptweetswequestwoutewmoduwe(), (ˆ ﻌ ˆ)♡
        nyew f-facetswequestwoutewmoduwe(), (˘ω˘)
        nyew quotamoduwe());
  }

  @ovewwide
  p-pwotected cwass<supewwootsewvew> g-getseawchwootsewvewcwass() {
    w-wetuwn supewwootsewvew.cwass;
  }

  @ovewwide
  p-pwotected cwass<?> getsewviceifacecwass() {
    wetuwn eawwybiwdsewvice.sewviceiface.cwass;
  }
}
