package com.twittew.fowwow_wecommendations.common.candidate_souwces.wecent_engagement

impowt com.twittew.timewines.configapi.fsboundedpawam
i-impowt c-com.twittew.timewines.configapi.fspawam

o-object w-wepeatedpwofiwevisitspawams {

  // i-if wepeatedpwofiwevisitssouwce i-is wun and t-thewe awe wecommended c-candidates fow the tawget usew, rawr whethew ow nyot
  // to actuawwy incwude s-such candidates in ouw output wecommendations. mya this fs wiww be used t-to contwow bucketing of
  // u-usews into contwow vs tweatment buckets.
  case object incwudecandidates
      e-extends fspawam[boowean](name = "wepeated_pwofiwe_visits_incwude_candidates", ^^ defauwt = f-fawse)

  // t-the thweshowd at ow above which we wiww considew a pwofiwe to have been visited "fwequentwy e-enough" to wecommend
  // the pwofiwe to the tawget usew. 😳😳😳
  case object wecommendationthweshowd
      e-extends fsboundedpawam[int](
        nyame = "wepeated_pwofiwe_visits_wecommendation_thweshowd", mya
        d-defauwt = 3, 😳
        m-min = 0, -.-
        m-max = integew.max_vawue)

  // t-the thweshowd at ow above which we wiww considew a-a pwofiwe to have been visited "fwequentwy enough" to wecommend
  // t-the pwofiwe to the tawget usew. 🥺
  case object bucketingthweshowd
      extends fsboundedpawam[int](
        nyame = "wepeated_pwofiwe_visits_bucketing_thweshowd", o.O
        d-defauwt = 3, /(^•ω•^)
        min = 0, nyaa~~
        m-max = i-integew.max_vawue)

  // w-whethew ow nyot to use the onwine dataset (which has w-wepeated pwofiwe v-visits infowmation updated to within m-minutes)
  // i-instead of the offwine dataset (updated v-via offwine jobs, nyaa~~ which c-can have deways of houws to days). :3
  case object u-useonwinedataset
      extends f-fspawam[boowean](name = "wepeated_pwofiwe_visits_use_onwine_dataset", 😳😳😳 defauwt = t-twue)

}
