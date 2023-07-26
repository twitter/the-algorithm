package com.twittew.home_mixew.functionaw_component.gate

impowt c-com.twittew.common_intewnaw.anawytics.twittew_cwient_usew_agent_pawsew.usewagent
i-impowt com.twittew.pwoduct_mixew.cowe.featuwe.featuwe
i-impowt com.twittew.pwoduct_mixew.cowe.functionaw_component.gate.gate
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.gateidentifiew
i-impowt c-com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
i-impowt com.twittew.stitch.stitch
impowt com.twittew.timewinemixew.cwients.pewsistence.timewinewesponsev3
impowt com.twittew.timewinemixew.injection.stowe.pewsistence.timewinepewsistenceutiws
impowt com.twittew.timewines.configapi.pawam
impowt com.twittew.timewines.utiw.cwient_info.cwientpwatfowm
impowt c-com.twittew.timewinesewvice.modew.wich.entityidtype
impowt com.twittew.utiw.duwation
i-impowt com.twittew.utiw.time

/**
 * g-gate used to weduce the fwequency of injections. 🥺 nyote that the a-actuaw intewvaw between injections m-may be
 * wess t-than the specified mininjectionintewvawpawam if data is unavaiwabwe ow missing. o.O fow exampwe, /(^•ω•^) being d-deweted by
 * the pewsistence stowe via a ttw ow simiwaw mechanism. nyaa~~
 *
 * @pawam mininjectionintewvawpawam t-the desiwed minimum intewvaw between i-injections
 * @pawam p-pewsistenceentwiesfeatuwe t-the featuwe f-fow wetwieving pewsisted timewine wesponses
 */
c-case cwass timewinespewsistencestowewastinjectiongate(
  mininjectionintewvawpawam: pawam[duwation], nyaa~~
  p-pewsistenceentwiesfeatuwe: featuwe[pipewinequewy, :3 seq[timewinewesponsev3]], 😳😳😳
  entityidtype: entityidtype.vawue)
    extends g-gate[pipewinequewy]
    with t-timewinepewsistenceutiws {

  o-ovewwide v-vaw identifiew: gateidentifiew = gateidentifiew("timewinespewsistencestowewastinjection")

  ovewwide def s-shouwdcontinue(quewy: p-pipewinequewy): stitch[boowean] =
    s-stitch(
      q-quewy.quewytime.since(getwastinjectiontime(quewy)) > quewy.pawams(mininjectionintewvawpawam))

  p-pwivate def getwastinjectiontime(quewy: p-pipewinequewy) = quewy.featuwes
    .fwatmap { featuwemap =>
      v-vaw timewinewesponses = featuwemap.getowewse(pewsistenceentwiesfeatuwe, (˘ω˘) seq.empty)
      vaw cwientpwatfowm = c-cwientpwatfowm.fwomquewyoptions(
        cwientappid = q-quewy.cwientcontext.appid, ^^
        usewagent = q-quewy.cwientcontext.usewagent.fwatmap(usewagent.fwomstwing)
      )
      vaw sowtedwesponses = wesponsebycwient(cwientpwatfowm, :3 timewinewesponses)
      vaw watestwesponsewithentityidtypeentwy =
        sowtedwesponses.find(_.entwies.exists(_.entityidtype == entityidtype))

      watestwesponsewithentityidtypeentwy.map(_.sewvedtime)
    }.getowewse(time.bottom)
}
