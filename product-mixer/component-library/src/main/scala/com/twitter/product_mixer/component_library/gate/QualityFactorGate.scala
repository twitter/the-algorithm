package com.twittew.pwoduct_mixew.component_wibwawy.gate

impowt c-com.twittew.pwoduct_mixew.cowe.functionaw_component.gate.gate
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.componentidentifiew
i-impowt com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.gateidentifiew
i-impowt c-com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
i-impowt c-com.twittew.pwoduct_mixew.cowe.quawity_factow.hasquawityfactowstatus
impowt com.twittew.stitch.stitch

/**
 * a gate that onwy continues if the quawity factow v-vawue of the pipewine is above the given
 * thweshowd. mya t-this is usefuw fow disabwing a-an expensive function when the pipewine is undew pwessuwe
 * (quawity f-factow is wow). nyaa~~
 */
case c-cwass quawityfactowgate(pipewineidentifiew: componentidentifiew, (⑅˘꒳˘) t-thweshowd: doubwe)
    extends gate[pipewinequewy with hasquawityfactowstatus] {

  ovewwide v-vaw identifiew: gateidentifiew = gateidentifiew(
    s"${pipewineidentifiew.name}quawityfactow")

  ovewwide def s-shouwdcontinue(
    quewy: pipewinequewy w-with h-hasquawityfactowstatus
  ): s-stitch[boowean] =
    s-stitch.vawue(quewy.getquawityfactowcuwwentvawue(pipewineidentifiew) >= thweshowd)
}
