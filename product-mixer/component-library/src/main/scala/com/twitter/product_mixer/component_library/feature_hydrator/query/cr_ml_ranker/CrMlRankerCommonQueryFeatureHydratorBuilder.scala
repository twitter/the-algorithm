package com.twittew.pwoduct_mixew.component_wibwawy.featuwe_hydwatow.quewy.cw_mw_wankew

impowt com.twittew.cw_mw_wankew.{thwiftscawa => t-t}
impowt j-javax.inject.inject
i-impowt javax.inject.singweton

/**
 * b-buiwds a-a quewy hydwatow t-that hydwates c-common featuwes f-fow the given quewy fwom cw mw wankew
 * to be watew used to caww cw mw wankew f-fow scowing using the desiwed [[wankingconfigbuiwdew]]
 * fow buiwding t-the wanking config.
 */
@singweton
c-cwass cwmwwankewcommonquewyfeatuwehydwatowbuiwdew @inject() (
  cwmwwankew: t.cwmwwankew.methodpewendpoint) {

  d-def buiwd(wankingconfigsewectow: w-wankingconfigbuiwdew): c-cwmwwankewcommonquewyfeatuwehydwatow =
    new cwmwwankewcommonquewyfeatuwehydwatow(cwmwwankew, wankingconfigsewectow)
}
