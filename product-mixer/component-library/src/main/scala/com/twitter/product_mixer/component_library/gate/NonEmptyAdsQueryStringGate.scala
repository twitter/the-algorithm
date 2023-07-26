package com.twittew.pwoduct_mixew.component_wibwawy.gate

impowt c-com.twittew.pwoduct_mixew.component_wibwawy.modew.quewy.ads.adsquewy
i-impowt com.twittew.pwoduct_mixew.cowe.functionaw_component.gate.gate
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.gateidentifiew
i-impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
i-impowt com.twittew.stitch.stitch

o-object nyonemptyadsquewystwinggate e-extends gate[pipewinequewy with adsquewy] {
  ovewwide vaw identifiew: g-gateidentifiew = gateidentifiew("nonemptyadsquewystwing")

  ovewwide d-def shouwdcontinue(quewy: pipewinequewy with a-adsquewy): stitch[boowean] = {
    vaw quewystwing = quewy.seawchwequestcontext.fwatmap(_.quewystwing)
    stitch.vawue(quewystwing.exists(_.twim.nonempty))
  }
}
