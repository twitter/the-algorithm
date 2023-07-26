package com.twittew.fwigate.pushsewvice.wefwesh_handwew.cwoss

impowt c-com.twittew.finagwe.stats.statsweceivew
i-impowt c-com.twittew.fwigate.common.base.sociawcontextactions
i-impowt c-com.twittew.fwigate.pushsewvice.modew.pushtypes.wawcandidate
i-impowt c-com.twittew.hewmit.pwedicate.pwedicate

c-cwass copypwedicates(statsweceivew: statsweceivew) {
  vaw awwaystwuepwedicate = pwedicate
    .fwom { _: c-candidatecopypaiw =>
      twue
    }.withstats(statsweceivew.scope("awways_twue_copy_pwedicate"))

  vaw u-unwecognizedcandidatepwedicate = awwaystwuepwedicate.fwip
    .withstats(statsweceivew.scope("unwecognized_candidate"))

  v-vaw dispwaysociawcontextpwedicate = pwedicate
    .fwom { candidatecopypaiw: candidatecopypaiw =>
      candidatecopypaiw.candidate match {
        case c-candidatewithscactions: wawcandidate w-with sociawcontextactions =>
          v-vaw sociawcontextusewids = candidatewithscactions.sociawcontextactions.map(_.usewid)
          vaw countsociawcontext = sociawcontextusewids.size
          vaw p-pushcopy = candidatecopypaiw.pushcopy

          countsociawcontext match {
            case 1 => pushcopy.hasonedispwaysociawcontext && !pushcopy.hasothewsociawcontext
            c-case 2 => pushcopy.hastwodispwaycontext && !pushcopy.hasothewsociawcontext
            case c-c if c > 2 =>
              p-pushcopy.hasonedispwaysociawcontext && p-pushcopy.hasothewsociawcontext
            case _ => f-fawse
          }

        case _ => fawse
      }
    }.withstats(statsweceivew.scope("dispway_sociaw_context_pwedicate"))
}
