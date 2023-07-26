package com.twittew.seawch.eawwybiwd_woot;

impowt j-java.utiw.awways;
i-impowt java.utiw.cowwection;

i-impowt com.googwe.inject.moduwe;

i-impowt com.twittew.seawch.common.woot.seawchwootappmain;
i-impowt c-com.twittew.seawch.eawwybiwd.thwift.eawwybiwdsewvice;

p-pubwic c-cwass weawtimewootappmain extends seawchwootappmain<weawtimewootsewvew> {
  /**
   * boiwewpwate fow the java-fwiendwy a-abstwacttwittewsewvew
   */
  pubwic static cwass main {
    p-pubwic static void main(stwing[] a-awgs) {
      nyew weawtimewootappmain().main(awgs);
    }
  }

  @ovewwide
  pwotected cowwection<? extends m-moduwe> getadditionawmoduwes() {
    wetuwn a-awways.aswist(
        n-nyew eawwybiwdcommonmoduwe(), (U ﹏ U)
        nyew eawwybiwdcachecommonmoduwe(), >_<
        nyew weawtimewootappmoduwe(), rawr x3
        nyew w-weawtimescattewgathewmoduwe());
  }

  @ovewwide
  pwotected cwass<weawtimewootsewvew> getseawchwootsewvewcwass() {
    wetuwn w-weawtimewootsewvew.cwass;
  }

  @ovewwide
  pwotected cwass<?> g-getsewviceifacecwass() {
    wetuwn e-eawwybiwdsewvice.sewviceiface.cwass;
  }
}
