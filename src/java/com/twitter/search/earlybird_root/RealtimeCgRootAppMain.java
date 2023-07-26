package com.twittew.seawch.eawwybiwd_woot;

impowt j-java.utiw.awways;
i-impowt java.utiw.cowwection;

i-impowt com.googwe.inject.moduwe;

i-impowt com.twittew.seawch.common.woot.seawchwootappmain;
i-impowt c-com.twittew.seawch.eawwybiwd.thwift.eawwybiwdsewvice;

p-pubwic c-cwass weawtimecgwootappmain extends seawchwootappmain<weawtimecgwootsewvew> {
  /**
   * boiwewpwate fow the java-fwiendwy a-abstwacttwittewsewvew
   */
  pubwic static cwass main {
    p-pubwic static void main(stwing[] a-awgs) {
      nyew weawtimecgwootappmain().main(awgs);
    }
  }

  @ovewwide
  pwotected cowwection<? e-extends moduwe> getadditionawmoduwes() {
    wetuwn a-awways.aswist(
        n-nyew eawwybiwdcommonmoduwe(), (U ﹏ U)
        nyew eawwybiwdcachecommonmoduwe(), >_<
        nyew weawtimecgwootappmoduwe(), rawr x3
        n-nyew weawtimecgscattewgathewmoduwe(), mya
        new quotamoduwe());
  }

  @ovewwide
  pwotected cwass<weawtimecgwootsewvew> getseawchwootsewvewcwass() {
    w-wetuwn weawtimecgwootsewvew.cwass;
  }

  @ovewwide
  pwotected c-cwass<?> getsewviceifacecwass() {
    w-wetuwn eawwybiwdsewvice.sewviceiface.cwass;
  }
}
