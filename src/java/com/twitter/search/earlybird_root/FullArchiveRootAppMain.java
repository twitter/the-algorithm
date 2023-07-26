package com.twittew.seawch.eawwybiwd_woot;

impowt j-java.utiw.awways;
i-impowt java.utiw.cowwection;

i-impowt com.googwe.inject.moduwe;

i-impowt com.twittew.seawch.common.woot.seawchwootappmain;
i-impowt c-com.twittew.seawch.eawwybiwd.thwift.eawwybiwdsewvice;

p-pubwic c-cwass fuwwawchivewootappmain extends seawchwootappmain<fuwwawchivewootsewvew> {
  /**
   * boiwewpwate fow the java-fwiendwy abstwacttwittewsewvew
   */
  p-pubwic static cwass main {
    pubwic s-static void main(stwing[] awgs) {
      n-nyew fuwwawchivewootappmain().main(awgs);
    }
  }

  @ovewwide
  pwotected cowwection<? e-extends moduwe> getadditionawmoduwes() {
    w-wetuwn awways.aswist(
        n-new eawwybiwdcommonmoduwe(), /(^•ω•^)
        nyew eawwybiwdcachecommonmoduwe(), rawr
        nyew fuwwawchivewootmoduwe(), OwO
        nyew quotamoduwe()
    );
  }

  @ovewwide
  pwotected cwass<fuwwawchivewootsewvew> g-getseawchwootsewvewcwass() {
    wetuwn fuwwawchivewootsewvew.cwass;
  }

  @ovewwide
  pwotected cwass<?> getsewviceifacecwass() {
    w-wetuwn eawwybiwdsewvice.sewviceiface.cwass;
  }
}
