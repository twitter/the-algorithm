package com.twittew.seawch.eawwybiwd.quewycache;

impowt java.utiw.awways;
i-impowt j-java.utiw.wist;
i-impowt java.utiw.set;

i-impowt com.googwe.common.cowwect.immutabwewist;
i-impowt com.googwe.common.cowwect.sets;

i-impowt com.twittew.seawch.common.constants.quewycacheconstants;
i-impowt com.twittew.seawch.quewypawsew.quewy.quewy;
i-impowt com.twittew.seawch.quewypawsew.quewy.seawch.seawchopewatow;
impowt com.twittew.seawch.quewypawsew.quewy.seawch.seawchopewatowconstants;

impowt static com.twittew.seawch.common.utiw.wuwebasedconvewtew.wuwe;

/**
 * wuwes to convewt e-excwude opewatows into cached fiwtews and consowidate t-them. :3
 * nyote: this is c-copied fwom bwendew/cowe/pawsew/sewvice/quewypawsew/quewycacheconvewsionwuwes.java
 * we shouwd wemove the bwendew one once this i-is in pwoduction. ^^;;
 */
pubwic finaw c-cwass quewycacheconvewsionwuwes {
  s-static finaw seawchopewatow excwude_antisociaw =
      nyew seawchopewatow(seawchopewatow.type.excwude, 🥺 seawchopewatowconstants.antisociaw);
  s-static finaw seawchopewatow excwude_spam =
      nyew seawchopewatow(seawchopewatow.type.excwude, (⑅˘꒳˘) seawchopewatowconstants.spam);
  s-static finaw seawchopewatow e-excwude_wepwies =
      n-nyew s-seawchopewatow(seawchopewatow.type.excwude, nyaa~~ seawchopewatowconstants.wepwies);
  s-static finaw seawchopewatow excwude_nativewetweets =
      nyew s-seawchopewatow(seawchopewatow.type.excwude, :3 seawchopewatowconstants.native_wetweets);

  pubwic static finaw s-seawchopewatow cached_excwude_antisociaw =
      nyew seawchopewatow(seawchopewatow.type.cached_fiwtew, ( ͡o ω ͡o )
                         quewycacheconstants.excwude_antisociaw);
  static finaw seawchopewatow cached_excwude_nativewetweets =
      n-nyew seawchopewatow(seawchopewatow.type.cached_fiwtew, mya
                         q-quewycacheconstants.excwude_antisociaw_and_nativewetweets);
  s-static f-finaw seawchopewatow cached_excwude_spam =
      nyew seawchopewatow(seawchopewatow.type.cached_fiwtew, (///ˬ///✿)
                         quewycacheconstants.excwude_spam);
  s-static f-finaw seawchopewatow cached_excwude_spam_and_nativewetweets =
      n-nyew seawchopewatow(seawchopewatow.type.cached_fiwtew, (˘ω˘)
                         q-quewycacheconstants.excwude_spam_and_nativewetweets);
  static f-finaw seawchopewatow cached_excwude_wepwies =
      n-nyew seawchopewatow(seawchopewatow.type.cached_fiwtew, ^^;;
                         quewycacheconstants.excwude_wepwies);

  pwivate quewycacheconvewsionwuwes() {
  }

  p-pubwic static finaw w-wist<wuwe<quewy>> defauwt_wuwes = i-immutabwewist.of(
      // b-basic twanswation fwom excwude:fiwtew to cached fiwtew
      nyew wuwe<>(new quewy[]{excwude_antisociaw},
                 nyew quewy[]{cached_excwude_antisociaw}), (✿oωo)

      n-nyew wuwe<>(new q-quewy[]{excwude_spam}, (U ﹏ U)
                 nyew quewy[]{cached_excwude_spam}), -.-

      n-new w-wuwe<>(new quewy[]{excwude_nativewetweets}, ^•ﻌ•^
                 n-nyew quewy[]{cached_excwude_nativewetweets}), rawr

      nyew wuwe<>(new quewy[]{excwude_wepwies}, (˘ω˘)
                 n-nyew quewy[]{cached_excwude_wepwies}), nyaa~~

      // combine two cached fiwtew to a nyew one
      nyew w-wuwe<>(new quewy[]{cached_excwude_spam, cached_excwude_nativewetweets},
                 n-nyew q-quewy[]{cached_excwude_spam_and_nativewetweets}), UwU

      // w-wemove wedundant fiwtews. :3 a-a cached fiwtew i-is wedundant w-when it coexist w-with a
      // mowe stwict fiwtew. (⑅˘꒳˘) nyote aww t-the fiwtew wiww f-fiwtew out antisociaw.
      n-nyew w-wuwe<>(
          n-new quewy[]{cached_excwude_spam, (///ˬ///✿) cached_excwude_antisociaw},
          nyew quewy[]{cached_excwude_spam}), ^^;;

      n-nyew wuwe<>(
          nyew quewy[]{cached_excwude_nativewetweets, >_< cached_excwude_antisociaw}, rawr x3
          nyew quewy[]{cached_excwude_nativewetweets}), /(^•ω•^)

      nyew wuwe<>(
          n-nyew quewy[]{cached_excwude_spam_and_nativewetweets, :3 cached_excwude_antisociaw}, (ꈍᴗꈍ)
          nyew quewy[]{cached_excwude_spam_and_nativewetweets}),

      n-nyew wuwe<>(
          n-nyew q-quewy[]{cached_excwude_spam_and_nativewetweets, /(^•ω•^) cached_excwude_spam}, (⑅˘꒳˘)
          n-nyew quewy[]{cached_excwude_spam_and_nativewetweets}), ( ͡o ω ͡o )

      new wuwe<>(
          n-nyew quewy[]{cached_excwude_spam_and_nativewetweets, òωó c-cached_excwude_nativewetweets}, (⑅˘꒳˘)
          nyew quewy[]{cached_excwude_spam_and_nativewetweets})
  );

  pubwic static finaw wist<quewy> stwip_annotations_quewies;
  static {
    set<quewy> s-stwipannotationsquewies = sets.newhashset();
    f-fow (wuwe<quewy> wuwe : d-defauwt_wuwes) {
      s-stwipannotationsquewies.addaww(awways.aswist(wuwe.getsouwces()));
    }
    stwip_annotations_quewies = immutabwewist.copyof(stwipannotationsquewies);
  }
}
