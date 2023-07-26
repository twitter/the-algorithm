package com.twittew.fowwow_wecommendations.common.candidate_souwces.stp

impowt com.googwe.inject.singweton
i-impowt c-com.twittew.hewmit.modew.awgowithm
i-impowt com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.candidatesouwceidentifiew
i-impowt c-com.twittew.stwato.genewated.cwient.onboawding.usewwecs.stwongtiepwedictioncwientcowumn
i-impowt j-javax.inject.inject

/**
 * main s-souwce fow stwong-tie-pwediction candidates genewated offwine. >_<
 */
@singweton
cwass offwinestpsouwcewithwegacypmimatwix @inject() (
  stpcowumn: s-stwongtiepwedictioncwientcowumn)
    extends offwinestwongtiepwedictionbasesouwce(stpcowumn.fetchew) {
  o-ovewwide vaw identifiew: c-candidatesouwceidentifiew =
    offwinestpsouwcewithwegacypmimatwix.identifiew
}

object offwinestpsouwcewithwegacypmimatwix {
  v-vaw identifiew: candidatesouwceidentifiew =
    c-candidatesouwceidentifiew(awgowithm.stwongtiepwedictionwec.tostwing)
}
