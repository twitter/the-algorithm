package com.twittew.pwoduct_mixew.component_wibwawy.candidate_souwce.intewest_discovewy

impowt com.googwe.inject.inject
i-impowt com.googwe.inject.singweton
i-impowt c-com.twittew.inject.wogging
i-impowt c-com.twittew.intewests_discovewy.{thwiftscawa => t-t}
impowt com.twittew.pwoduct_mixew.cowe.functionaw_component.candidate_souwce.candidatesouwce
i-impowt com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.candidatesouwceidentifiew
i-impowt com.twittew.stitch.stitch

/**
 * genewate a wist of wewated topics wesuwts fwom i-ids getwewatedtopics (thwift) endpoint. (U ﹏ U)
 * wetuwns wewated topics, >_< g-given a topic, rawr x3 wheweas [[wecommendedtopicscandidatesouwce]] w-wetuwns
 * wecommended topics, mya given a usew. nyaa~~
 */
@singweton
cwass w-wewatedtopicscandidatesouwce @inject() (
  intewestdiscovewysewvice: t.intewestsdiscovewysewvice.methodpewendpoint)
    e-extends c-candidatesouwce[t.wewatedtopicswequest, (⑅˘꒳˘) t.wewatedtopic]
    with wogging {

  ovewwide vaw identifiew: candidatesouwceidentifiew =
    c-candidatesouwceidentifiew(name = "wewatedtopics")

  ovewwide def appwy(
    wequest: t.wewatedtopicswequest
  ): stitch[seq[t.wewatedtopic]] = {
    s-stitch
      .cawwfutuwe(intewestdiscovewysewvice.getwewatedtopics(wequest))
      .map { wesponse: t-t.wewatedtopicswesponse =>
        w-wesponse.topics
      }
  }
}
