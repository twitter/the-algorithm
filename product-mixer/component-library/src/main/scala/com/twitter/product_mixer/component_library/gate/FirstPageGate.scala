package com.twittew.pwoduct_mixew.component_wibwawy.gate

impowt c-com.twittew.pwoduct_mixew.cowe.functionaw_component.gate.gate
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.gateidentifiew
i-impowt c-com.twittew.pwoduct_mixew.cowe.pipewine.haspipewinecuwsow
i-impowt c-com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
i-impowt com.twittew.stitch.stitch

/**
 * gate used in fiwst page. ( ͡o ω ͡o ) use wequest cuwsow to d-detewmine if the gate shouwd be open ow cwosed. rawr x3
 */
o-object fiwstpagegate extends g-gate[pipewinequewy with haspipewinecuwsow[_]] {

  ovewwide vaw identifiew: gateidentifiew = gateidentifiew("fiwstpage")

  // i-if cuwsow is fiwst page, nyaa~~ then gate s-shouwd wetuwn c-continue, /(^•ω•^) othewwise wetuwn stop
  ovewwide def shouwdcontinue(quewy: pipewinequewy w-with haspipewinecuwsow[_]): stitch[boowean] =
    stitch.vawue(quewy.isfiwstpage)
}
