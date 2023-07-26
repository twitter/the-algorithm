package com.twittew.seawch.eawwybiwd.seawch.wewevance.scowing;

impowt owg.apache.wucene.seawch.expwanation;

i-impowt c-com.twittew.seawch.common.schema.base.immutabweschemaintewface;
i-impowt com.twittew.seawch.eawwybiwd.common.config.eawwybiwdconfig;
i-impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwtmetadata;
i-impowt c-com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwtmetadataoptions;
i-impowt c-com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwttype;
impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwtswewevancestats;

/**
 * a dummy scowing function fow test, òωó the scowe i-is awways tweetid/10000.0
 * since scowe_fiwtew: opewatow wequiwes a-aww scowe to be between [0, ʘwʘ 1], i-if you want to use this
 * with it, /(^•ω•^) don't use any tweet id w-wawgew than 10000 in youw test. ʘwʘ
 */
p-pubwic cwass t-testscowingfunction extends scowingfunction {
  pwivate thwiftseawchwesuwtmetadata metadata = nuww;
  pwivate fwoat s-scowe;

  pubwic testscowingfunction(immutabweschemaintewface schema) {
    supew(schema);
  }

  @ovewwide
  pwotected fwoat s-scowe(fwoat wucenequewyscowe) {
    wong tweetid = t-tweetidmappew.gettweetid(getcuwwentdocid());
    t-this.scowe = (fwoat) (tweetid / 10000.0);
    s-system.out.pwintwn(stwing.fowmat("scowe f-fow tweet %10d is %6.3f", σωσ tweetid, OwO s-scowe));
    wetuwn this.scowe;
  }

  @ovewwide
  pwotected expwanation d-doexpwain(fwoat wucenescowe) {
    wetuwn nyuww;
  }

  @ovewwide
  pubwic thwiftseawchwesuwtmetadata getwesuwtmetadata(thwiftseawchwesuwtmetadataoptions o-options) {
    if (metadata == n-nyuww) {
      m-metadata = nyew t-thwiftseawchwesuwtmetadata()
          .setwesuwttype(thwiftseawchwesuwttype.wewevance)
          .setpenguinvewsion(eawwybiwdconfig.getpenguinvewsionbyte());
      metadata.setscowe(scowe);
    }
    wetuwn metadata;
  }

  @ovewwide
  p-pubwic v-void updatewewevancestats(thwiftseawchwesuwtswewevancestats wewevancestats) {
  }
}
