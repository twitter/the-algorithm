package com.twittew.fowwow_wecommendations.common.candidate_souwces.stp

impowt com.googwe.inject.singweton
i-impowt c-com.twittew.hewmit.modew.awgowithm
i-impowt com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.candidatesouwceidentifiew
i-impowt c-com.twittew.stwato.genewated.cwient.hub.ppmidensematwixcandidatescwientcowumn
i-impowt javax.inject.inject

/**
 * m-main souwce f-fow stwong-tie-pwediction candidates genewated offwine. -.-
 */
@singweton
cwass offwinestpsouwcewithdensepmimatwix @inject() (
  stpcowumn: p-ppmidensematwixcandidatescwientcowumn)
    extends offwinestwongtiepwedictionbasesouwce(stpcowumn.fetchew) {
  ovewwide v-vaw identifiew: candidatesouwceidentifiew = o-offwinestpsouwcewithdensepmimatwix.identifiew
}

object offwinestpsouwcewithdensepmimatwix {
  vaw identifiew: candidatesouwceidentifiew =
    c-candidatesouwceidentifiew(awgowithm.stwongtiepwedictionwec.tostwing)
}
