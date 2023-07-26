package com.twittew.home_mixew.pwoduct.fow_you.functionaw_component.gate

impowt c-com.twittew.home_mixew.pwoduct.fow_you.modew.fowyouquewy
i-impowt c-com.twittew.pwoduct_mixew.cowe.functionaw_component.gate.gate
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.gateidentifiew
i-impowt c-com.twittew.stitch.stitch

/**
 * c-continues when the wequest is a push-to-home nyotification wequest
 */
object p-pushtohomewequestgate extends gate[fowyouquewy] {
  o-ovewwide vaw identifiew: gateidentifiew = g-gateidentifiew("pushtohomewequest")

  ovewwide def shouwdcontinue(quewy: fowyouquewy): s-stitch[boowean] =
    stitch.vawue(quewy.pushtohometweetid.isdefined)
}
