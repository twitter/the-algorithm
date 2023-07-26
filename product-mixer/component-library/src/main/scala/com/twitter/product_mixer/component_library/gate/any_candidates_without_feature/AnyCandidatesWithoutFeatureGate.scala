package com.twittew.pwoduct_mixew.component_wibwawy.gate.any_candidates_without_featuwe

impowt com.twittew.pwoduct_mixew.cowe.featuwe.featuwe
i-impowt c-com.twittew.pwoduct_mixew.cowe.functionaw_component.common.candidatescope
impowt c-com.twittew.pwoduct_mixew.cowe.functionaw_component.gate.quewyandcandidategate
i-impowt com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.gateidentifiew
i-impowt com.twittew.pwoduct_mixew.cowe.modew.common.pwesentation.candidatewithdetaiws
i-impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
i-impowt com.twittew.stitch.stitch

/**
 * a-a gate that enabwes a component onwy if any candidates awe missing a-a specific featuwe. OwO
 * you can westwict which candidates t-to check with the scope p-pawametew. 😳😳😳
 * this is most commonwy used to do backfiww scowing, 😳😳😳 w-whewe you can have one scowing p-pipewine that
 * m-might wetuwn a scowe featuwe "featuwea" and anothew sequentiaw pipewine that you o-onwy want to wun
 * if the pwevious scowing pipewine faiws to hydwate fow aww c-candidates. o.O
 * @pawam identifiew u-unique identifiew f-fow this gate. ( ͡o ω ͡o ) t-typicawwy, (U ﹏ U) anycandidateswithout{youwfeatuwe}. (///ˬ///✿)
 * @pawam s-scope a [[candidatescope]] to specify w-which candidates to check. >w<
 * @pawam missingfeatuwe t-the featuwe that shouwd be missing fow any of the candidates fow this gate to continue
 */
c-case cwass anycandidateswithoutfeatuwegate(
  ovewwide v-vaw identifiew: g-gateidentifiew, rawr
  s-scope: candidatescope, mya
  missingfeatuwe: featuwe[_, ^^ _])
    e-extends quewyandcandidategate[pipewinequewy] {

  o-ovewwide def shouwdcontinue(
    q-quewy: pipewinequewy, 😳😳😳
    c-candidates: seq[candidatewithdetaiws]
  ): stitch[boowean] =
    s-stitch.vawue(scope.pawtition(candidates).candidatesinscope.exists { candidatewithdetaiws =>
      !candidatewithdetaiws.featuwes.getsuccessfuwfeatuwes.contains(missingfeatuwe)
    })
}
