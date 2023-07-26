package com.twittew.seawch.eawwybiwd_woot;

impowt j-java.utiw.awways;
i-impowt java.utiw.cowwection;

i-impowt com.googwe.inject.moduwe;

i-impowt com.twittew.seawch.common.woot.seawchwootappmain;
i-impowt c-com.twittew.seawch.eawwybiwd.thwift.eawwybiwdsewvice;

p-pubwic c-cwass pwotectedwootappmain extends seawchwootappmain<pwotectedwootsewvew> {
  /**
   * boiwewpwate fow the java-fwiendwy a-abstwacttwittewsewvew
   */
  pubwic static cwass main {
    p-pubwic static void main(stwing[] a-awgs) {
      nyew pwotectedwootappmain().main(awgs);
    }
  }

  @ovewwide
  pwotected cowwection<? extends m-moduwe> getadditionawmoduwes() {
    wetuwn a-awways.aswist(
        n-nyew eawwybiwdcommonmoduwe(), rawr
        nyew eawwybiwdcachecommonmoduwe(), OwO
        nyew pwotectedwootappmoduwe(), (U ﹏ U)
        nyew pwotectedscattewgathewmoduwe());
  }

  @ovewwide
  p-pwotected cwass<pwotectedwootsewvew> getseawchwootsewvewcwass() {
    wetuwn pwotectedwootsewvew.cwass;
  }

  @ovewwide
  pwotected c-cwass<?> getsewviceifacecwass() {
    wetuwn eawwybiwdsewvice.sewviceiface.cwass;
  }
}
