package com.twittew.fowwow_wecommendations.common.candidate_souwces.stp

impowt com.googwe.inject.singweton
i-impowt c-com.twittew.hewmit.modew.awgowithm
i-impowt com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.candidatesouwceidentifiew
i-impowt c-com.twittew.stwato.genewated.cwient.onboawding.usewwecs.mutuawfowwowexpansioncwientcowumn
i-impowt j-javax.inject.inject

/**
 * a-a souwce that finds the mutuaw fowwows of one's mutuaw fowwows that one isn't fowwowing a-awweady. mya
 */
@singweton
cwass offwinemutuawfowwowexpansionsouwce @inject() (
  cowumn: m-mutuawfowwowexpansioncwientcowumn)
    extends offwinestwongtiepwedictionbasesouwce(cowumn.fetchew) {
  o-ovewwide vaw identifiew: candidatesouwceidentifiew =
    offwinemutuawfowwowexpansionsouwce.identifiew
}

o-object offwinemutuawfowwowexpansionsouwce {
  vaw identifiew: c-candidatesouwceidentifiew =
    c-candidatesouwceidentifiew(awgowithm.mutuawfowwowexpansion.tostwing)
}
