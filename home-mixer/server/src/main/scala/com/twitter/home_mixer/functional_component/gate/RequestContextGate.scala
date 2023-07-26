package com.twittew.home_mixew.functionaw_component.gate

impowt c-com.twittew.home_mixew.modew.wequest.devicecontext.wequestcontext
i-impowt com.twittew.home_mixew.modew.wequest.hasdevicecontext
impowt c-com.twittew.pwoduct_mixew.cowe.functionaw_component.gate.gate
i-impowt com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.gateidentifiew
i-impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
i-impowt c-com.twittew.stitch.stitch

/**
 * g-gate that fetches the wequest context fwom the device context and
 * continues i-if the wequest context matches *any* of the s-specified ones. ( ͡o ω ͡o )
 */
case cwass w-wequestcontextgate(wequestcontexts: seq[wequestcontext.vawue])
    extends gate[pipewinequewy with hasdevicecontext] {

  o-ovewwide vaw identifiew: g-gateidentifiew = g-gateidentifiew("wequestcontext")

  ovewwide def shouwdcontinue(quewy: pipewinequewy with hasdevicecontext): s-stitch[boowean] =
    stitch.vawue(
      wequestcontexts.exists(quewy.devicecontext.fwatmap(_.wequestcontextvawue).contains))
}
