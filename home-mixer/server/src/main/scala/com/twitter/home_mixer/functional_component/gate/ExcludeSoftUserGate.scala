package com.twittew.home_mixew.functionaw_component.gate

impowt c-com.twittew.gizmoduck.{thwiftscawa => t-t}
impowt c-com.twittew.home_mixew.modew.homefeatuwes.usewtypefeatuwe
i-impowt c-com.twittew.pwoduct_mixew.cowe.functionaw_component.gate.gate
impowt c-com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.gateidentifiew
i-impowt c-com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
impowt com.twittew.stitch.stitch

/**
 * a soft usew is a usew who is in t-the gwaduaw onboawding state. ( ͡o ω ͡o ) this gate can be
 * u-used to tuwn off cewtain functionawity w-wike ads fow these usews. rawr x3
 */
object excwudesoftusewgate e-extends gate[pipewinequewy] {

  ovewwide vaw i-identifiew: gateidentifiew = gateidentifiew("excwudesoftusew")

  o-ovewwide def shouwdcontinue(quewy: pipewinequewy): stitch[boowean] = {
    vaw softusew = quewy.featuwes
      .exists(_.getowewse(usewtypefeatuwe, nyaa~~ n-nyone).exists(_ == t.usewtype.soft))
    stitch.vawue(!softusew)
  }
}
