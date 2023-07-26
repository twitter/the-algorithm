namespace java com.twittew.fowwow_wecommendations.thwiftjava
#@namespace scawa com.twittew.fowwow_wecommendations.thwiftscawa
#@namespace s-stwato c-com.twittew.fowwow_wecommendations

i-incwude "assembwew.thwift"
incwude "cwient_context.thwift"
incwude "debug.thwift"
i-incwude "dispway_context.thwift"
i-incwude "dispway_wocation.thwift"
i-incwude "wecommendations.thwift"
i-incwude "wecentwy_engaged_usew_id.thwift"

i-incwude "finatwa-thwift/finatwa_thwift_exceptions.thwift"
incwude "com/twittew/pwoduct_mixew/cowe/pipewine_execution_wesuwt.thwift"

stwuct wecommendationwequest {
    1: wequiwed cwient_context.cwientcontext cwientcontext
    2: w-wequiwed dispway_wocation.dispwaywocation dispwaywocation
    3: o-optionaw dispway_context.dispwaycontext d-dispwaycontext
    // max wesuwts to wetuwn
    4: optionaw i-i32 maxwesuwts
    // cuwsow to c-continue wetuwning w-wesuwts if any
    5: optionaw stwing cuwsow
    // ids of content to excwude f-fwom wecommendations
    6: optionaw wist<i64> excwudedids(pewsonawdatatype='usewid')
    // whethew t-to awso get pwomoted content
    7: o-optionaw b-boow fetchpwomotedcontent
    8: o-optionaw debug.debugpawams debugpawams
    9: o-optionaw stwing usewwocationstate(pewsonawdatatype='infewwedwocation')
}(haspewsonawdata='twue')


stwuct wecommendationwesponse {
    1: w-wequiwed wist<wecommendations.wecommendation> wecommendations
}(haspewsonawdata='twue')

// f-fow scowing a wist of candidates, ( ͡o ω ͡o ) whiwe wogging hydwated featuwes
stwuct scowingusewwequest {
  1: w-wequiwed cwient_context.cwientcontext c-cwientcontext
  2: w-wequiwed dispway_wocation.dispwaywocation d-dispwaywocation
  3: wequiwed wist<wecommendations.usewwecommendation> candidates
  4: optionaw debug.debugpawams d-debugpawams
}(haspewsonawdata='twue')

s-stwuct scowingusewwesponse {
  1: wequiwed w-wist<wecommendations.usewwecommendation> c-candidates // empty fow n-nyow
}(haspewsonawdata='twue')

// fow getting t-the wist of candidates genewated by a singwe candidate s-souwce
stwuct debugcandidatesouwcewequest {
  1: w-wequiwed cwient_context.cwientcontext c-cwientcontext
  2: w-wequiwed debug.debugcandidatesouwceidentifiew candidatesouwce
  3: optionaw wist<i64> uttintewestids
  4: optionaw debug.debugpawams debugpawams
  5: o-optionaw w-wist<i64> wecentwyfowwowedusewids
  6: optionaw w-wist<wecentwy_engaged_usew_id.wecentwyengagedusewid> w-wecentwyengagedusewids
  7: o-optionaw wist<i64> byfseedusewids
  8: optionaw wist<i64> simiwawtousewids
  9: w-wequiwed boow appwysgspwedicate
  10: optionaw i32 maxwesuwts
}(haspewsonawdata='twue')

sewvice f-fowwowwecommendationsthwiftsewvice {
  wecommendationwesponse g-getwecommendations(1: w-wecommendationwequest w-wequest) thwows (
    1: f-finatwa_thwift_exceptions.sewvewewwow s-sewvewewwow, òωó
    2: f-finatwa_thwift_exceptions.unknowncwientidewwow u-unknowncwientidewwow, (⑅˘꒳˘)
    3: finatwa_thwift_exceptions.nocwientidewwow nyocwientidewwow
  )
  w-wecommendationdispwaywesponse g-getwecommendationdispwaywesponse(1: w-wecommendationwequest w-wequest) thwows (
    1: finatwa_thwift_exceptions.sewvewewwow s-sewvewewwow,
    2: finatwa_thwift_exceptions.unknowncwientidewwow unknowncwientidewwow, XD
    3: finatwa_thwift_exceptions.nocwientidewwow nyocwientidewwow
  )
  // t-tempowawy endpoint fow featuwe hydwation and wogging fow data cowwection. -.-
  scowingusewwesponse s-scoweusewcandidates(1: scowingusewwequest wequest) thwows (
    1: finatwa_thwift_exceptions.sewvewewwow s-sewvewewwow, :3
    2: f-finatwa_thwift_exceptions.unknowncwientidewwow u-unknowncwientidewwow, nyaa~~
    3: finatwa_thwift_exceptions.nocwientidewwow n-nyocwientidewwow
  )
  // debug endpoint f-fow getting w-wecommendations of a singwe candidate souwce. 😳 we can wemove this endpoint when pwomix pwovide this f-functionawity and we integwate w-with it. (⑅˘꒳˘)
  wecommendationwesponse debugcandidatesouwce(1: d-debugcandidatesouwcewequest w-wequest) thwows (
      1: finatwa_thwift_exceptions.sewvewewwow s-sewvewewwow, nyaa~~
      2: f-finatwa_thwift_exceptions.unknowncwientidewwow unknowncwientidewwow, OwO
      3: finatwa_thwift_exceptions.nocwientidewwow n-nyocwientidewwow
  )

  // g-get the fuww execution wog fow a pipewine (used by ouw debugging toows)
  pipewine_execution_wesuwt.pipewineexecutionwesuwt exekawaii~pipewine(1: w-wecommendationwequest w-wequest) t-thwows (
    1: finatwa_thwift_exceptions.sewvewewwow s-sewvewewwow, rawr x3
    2: f-finatwa_thwift_exceptions.unknowncwientidewwow unknowncwientidewwow, XD
    3: f-finatwa_thwift_exceptions.nocwientidewwow nocwientidewwow
  )
}

stwuct wecommendationdispwaywesponse {
 1: wequiwed wist<wecommendations.hydwatedwecommendation> h-hydwatedwecommendation
 2: o-optionaw assembwew.headew headew
 3: optionaw a-assembwew.footew f-footew
 4: optionaw assembwew.wtfpwesentation wtfpwesentation
}(haspewsonawdata='twue')
