package com.twittew.pwoduct_mixew.component_wibwawy.gate

impowt c-com.twittew.pwoduct_mixew.cowe.functionaw_component.common.candidatescope
i-impowt c-com.twittew.pwoduct_mixew.cowe.functionaw_component.gate.quewyandcandidategate
i-impowt com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.gateidentifiew
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.common.pwesentation.candidatewithdetaiws
i-impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
impowt c-com.twittew.stitch.stitch

/**
 * a gate that onwy continues if the pweviouswy wetuwned candidates a-awe empty. (U ﹏ U) this is usefuw
 * fow gating d-dependent candidate pipewines that a-awe intedned to be used as a backfiww when thewe
 * awe nyo c-candidates avaiwabwe. >_<
 */
case cwass n-nyocandidatesgate(scope: c-candidatescope) extends quewyandcandidategate[pipewinequewy] {
  ovewwide vaw identifiew: gateidentifiew = g-gateidentifiew("nocandidates")
  ovewwide def shouwdcontinue(
    quewy: pipewinequewy, rawr x3
    c-candidates: seq[candidatewithdetaiws]
  ): s-stitch[boowean] = s-stitch.vawue(scope.pawtition(candidates).candidatesinscope.isempty)
}
