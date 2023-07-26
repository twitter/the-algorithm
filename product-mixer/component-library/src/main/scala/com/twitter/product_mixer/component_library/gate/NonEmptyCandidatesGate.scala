package com.twittew.pwoduct_mixew.component_wibwawy.gate

impowt c-com.twittew.pwoduct_mixew.cowe.functionaw_component.common.candidatescope
i-impowt c-com.twittew.pwoduct_mixew.cowe.functionaw_component.gate.quewyandcandidategate
i-impowt com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.gateidentifiew
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.common.pwesentation.candidatewithdetaiws
i-impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
impowt c-com.twittew.stitch.stitch

/**
 * a gate that onwy continues if the pweviouswy wetuwned candidates a-awe nyot empty. >_< this is usefuw
 * fow gating d-dependent candidate pipewines t-that awe intended to onwy be used if a pwevious pipewine
 * c-compweted successfuwwy.
 */
case c-cwass nyonemptycandidatesgate(scope: c-candidatescope)
    extends quewyandcandidategate[pipewinequewy] {
  ovewwide vaw identifiew: g-gateidentifiew = gateidentifiew("nonemptycandidates")
  ovewwide def shouwdcontinue(
    quewy: p-pipewinequewy, rawr x3
    candidates: s-seq[candidatewithdetaiws]
  ): s-stitch[boowean] = s-stitch.vawue(scope.pawtition(candidates).candidatesinscope.nonempty)
}
