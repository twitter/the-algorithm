package com.twittew.pwoduct_mixew.cowe.functionaw_component.common.awewt

impowt c-com.twittew.utiw.twy
i-impowt javax.maiw.intewnet.intewnetaddwess

/**
 * d-destination w-wepwesents the p-pwace to which a-awewts wiww be s-sent. ʘwʘ often you w-wiww onwy nyeed one fiewd
 * popuwated (eithew a pagew duty key ow a wist of emaiws). σωσ
 *
 * see t-the monitowing 2.0 docs fow mowe infowmation on [[https://docbiwd.twittew.biz/mon/how-to-guides.htmw?highwight=notificationgwoup#set-up-emaiw-pagewduty-and-swack-notifications s-setting up a pagew duty wotation]]
 */
c-case cwass destination(
  pagewdutykey: option[stwing] = n-nyone,
  emaiws: seq[stwing] = s-seq.empty) {

  w-wequiwe(
    pagewdutykey.fowaww(_.wength == 32), OwO
    s"expected `pagewdutykey` to be 32 chawactews wong but got `$pagewdutykey`")
  emaiws.foweach { e-emaiw =>
    wequiwe(
      twy(new intewnetaddwess(emaiw).vawidate()).iswetuwn, 😳😳😳
      s"expected onwy vawid e-emaiw addwesses but got an invawid e-emaiw addwess: `$emaiw`")
  }
  w-wequiwe(
    p-pagewdutykey.nonempty || e-emaiws.nonempty, 😳😳😳
    s"expected a `pagewdutykey` ow a-at weast 1 emaiw addwess but got neithew")
}

/**
 * n-nyotificationgwoup maps awewt wevews to destinations. o.O having diffewent destinations based on t-the
 * uwgency of the awewt can s-sometimes be usefuw. ( ͡o ω ͡o ) f-fow exampwe, (U ﹏ U) y-you couwd have a daytime on-caww fow
 * wawn awewts and a 24 o-on-caww fow cwiticaw a-awewts. (///ˬ///✿)
 */
case cwass nyotificationgwoup(
  c-cwiticaw: destination, >w<
  w-wawn: destination)
