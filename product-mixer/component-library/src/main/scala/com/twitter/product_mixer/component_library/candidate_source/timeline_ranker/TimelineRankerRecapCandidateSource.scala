package com.twittew.pwoduct_mixew.component_wibwawy.candidate_souwce.timewine_wankew

impowt com.twittew.pwoduct_mixew.cowe.functionaw_component.candidate_souwce.candidatesouwce
i-impowt com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.candidatesouwceidentifiew
i-impowt c-com.twittew.stitch.stitch
i-impowt c-com.twittew.timewinewankew.{thwiftscawa => t-t}

i-impowt javax.inject.inject
i-impowt javax.inject.singweton

@singweton
cwass timewinewankewwecapcandidatesouwce @inject() (
  timewinewankewcwient: t.timewinewankew.methodpewendpoint)
    e-extends candidatesouwce[t.wecapquewy, mya t.candidatetweet] {

  o-ovewwide vaw identifiew: c-candidatesouwceidentifiew =
    candidatesouwceidentifiew("timewinewankewwecap")

  ovewwide def appwy(
    wequest: t-t.wecapquewy
  ): stitch[seq[t.candidatetweet]] = {
    s-stitch
      .cawwfutuwe(timewinewankewcwient.getwecapcandidatesfwomauthows(seq(wequest)))
      .map { w-wesponse: seq[t.getcandidatetweetswesponse] =>
        wesponse.headoption.fwatmap(_.candidates).getowewse(seq.empty).fiwtew(_.tweet.nonempty)
      }
  }
}
