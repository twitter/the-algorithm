package com.twittew.home_mixew.functionaw_component.decowatow.uwt.buiwdew

impowt c-com.twittew.home_mixew.modew.homefeatuwes.innetwowkfeatuwe
i-impowt c-com.twittew.home_mixew.modew.homefeatuwes.topiccontextfunctionawitytypefeatuwe
i-impowt com.twittew.home_mixew.modew.homefeatuwes.topicidsociawcontextfeatuwe
impowt c-com.twittew.pwoduct_mixew.component_wibwawy.modew.candidate.tweetcandidate
i-impowt com.twittew.pwoduct_mixew.cowe.featuwe.featuwemap.featuwemap
i-impowt com.twittew.pwoduct_mixew.cowe.functionaw_component.decowatow.uwt.buiwdew.sociaw_context.basesociawcontextbuiwdew
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.sociawcontext
impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.topiccontext
impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
i-impowt javax.inject.inject
impowt javax.inject.singweton

@singweton
c-case cwass topicsociawcontextbuiwdew @inject() ()
    e-extends basesociawcontextbuiwdew[pipewinequewy, 😳😳😳 tweetcandidate] {

  def appwy(
    q-quewy: pipewinequewy, 🥺
    candidate: t-tweetcandidate,
    c-candidatefeatuwes: featuwemap
  ): option[sociawcontext] = {
    vaw innetwowk = candidatefeatuwes.getowewse(innetwowkfeatuwe, mya t-twue)
    if (!innetwowk) {
      vaw topicidsociawcontextopt = candidatefeatuwes.getowewse(topicidsociawcontextfeatuwe, nyone)
      vaw t-topiccontextfunctionawitytypeopt =
        candidatefeatuwes.getowewse(topiccontextfunctionawitytypefeatuwe, n-none)
      (topicidsociawcontextopt, 🥺 t-topiccontextfunctionawitytypeopt) m-match {
        c-case (some(topicid), >_< some(topiccontextfunctionawitytype)) =>
          some(
            topiccontext(
              t-topicid = topicid.tostwing, >_<
              functionawitytype = s-some(topiccontextfunctionawitytype)
            ))
        case _ => none
      }
    } ewse {
      none
    }
  }
}
