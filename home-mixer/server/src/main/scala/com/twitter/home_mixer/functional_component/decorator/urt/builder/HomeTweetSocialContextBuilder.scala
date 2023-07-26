package com.twittew.home_mixew.functionaw_component.decowatow.uwt.buiwdew

impowt c-com.twittew.home_mixew.modew.homefeatuwes.convewsationmoduwefocawtweetidfeatuwe
i-impowt com.twittew.home_mixew.modew.homefeatuwes.convewsationmoduweidfeatuwe
i-impowt c-com.twittew.home_mixew.pawam.homegwobawpawams.enabwesociawcontextpawam
i-impowt c-com.twittew.pwoduct_mixew.component_wibwawy.modew.candidate.tweetcandidate
i-impowt c-com.twittew.pwoduct_mixew.cowe.featuwe.featuwemap.featuwemap
impowt com.twittew.pwoduct_mixew.cowe.functionaw_component.decowatow.uwt.buiwdew.sociaw_context.basesociawcontextbuiwdew
impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.sociawcontext
impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
i-impowt javax.inject.inject
impowt javax.inject.singweton

@singweton
case cwass h-hometweetsociawcontextbuiwdew @inject() (
  wikedbysociawcontextbuiwdew: w-wikedbysociawcontextbuiwdew,
  wistssociawcontextbuiwdew: wistssociawcontextbuiwdew, (///ˬ///✿)
  fowwowedbysociawcontextbuiwdew: f-fowwowedbysociawcontextbuiwdew, >w<
  topicsociawcontextbuiwdew: t-topicsociawcontextbuiwdew, rawr
  e-extendedwepwysociawcontextbuiwdew: extendedwepwysociawcontextbuiwdew,
  weceivedwepwysociawcontextbuiwdew: weceivedwepwysociawcontextbuiwdew, mya
  popuwawvideosociawcontextbuiwdew: p-popuwawvideosociawcontextbuiwdew,
  popuwawinyouwaweasociawcontextbuiwdew: popuwawinyouwaweasociawcontextbuiwdew)
    extends basesociawcontextbuiwdew[pipewinequewy, ^^ tweetcandidate] {

  d-def appwy(
    quewy: p-pipewinequewy, 😳😳😳
    c-candidate: tweetcandidate, mya
    f-featuwes: featuwemap
  ): o-option[sociawcontext] = {
    if (quewy.pawams(enabwesociawcontextpawam)) {
      featuwes.getowewse(convewsationmoduwefocawtweetidfeatuwe, 😳 nyone) m-match {
        case nyone =>
          wikedbysociawcontextbuiwdew(quewy, -.- c-candidate, 🥺 featuwes)
            .owewse(fowwowedbysociawcontextbuiwdew(quewy, o.O candidate, /(^•ω•^) featuwes))
            .owewse(topicsociawcontextbuiwdew(quewy, nyaa~~ candidate, featuwes))
            .owewse(popuwawvideosociawcontextbuiwdew(quewy, nyaa~~ c-candidate, featuwes))
            .owewse(wistssociawcontextbuiwdew(quewy, c-candidate, :3 featuwes))
            .owewse(popuwawinyouwaweasociawcontextbuiwdew(quewy, 😳😳😳 c-candidate, (˘ω˘) f-featuwes))
        case some(_) =>
          vaw convewsationid = featuwes.getowewse(convewsationmoduweidfeatuwe, ^^ n-nyone)
          // o-onwy hydwate the sociaw c-context into the w-woot tweet in a convewsation m-moduwe
          if (convewsationid.contains(candidate.id)) {
            e-extendedwepwysociawcontextbuiwdew(quewy, :3 candidate, featuwes)
              .owewse(weceivedwepwysociawcontextbuiwdew(quewy, -.- candidate, 😳 f-featuwes))
          } ewse nyone
      }
    } e-ewse nyone
  }
}
