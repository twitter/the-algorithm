 nclude "flows.thr ft"
 nclude "recently_engaged_user_ d.thr ft"

na space java com.tw ter.follow_recom ndat ons.thr ftjava
#@na space scala com.tw ter.follow_recom ndat ons.thr ftscala
#@na space strato com.tw ter.follow_recom ndat ons

struct Prof le {
    1: requ red  64 prof le d(personalDataType='User d')
}(hasPersonalData='true')

struct Search {
    1: requ red str ng searchQuery(personalDataType='SearchQuery')
}(hasPersonalData='true')

struct Rux {
    1: requ red  64 focalAuthor d(personalDataType='User d')
}(hasPersonalData='true')

struct Top c {
  1: requ red  64 top c d(personalDataType = 'Top cFollow')
}(hasPersonalData='true')

struct React veFollow {
    1: requ red l st< 64> follo dUser ds(personalDataType='User d')
}(hasPersonalData='true')

struct Nux nterests {
    1: opt onal flows.FlowContext flowContext // set for recom ndat on  ns de an  nteract ve flow
    2: opt onal l st< 64> utt nterest ds //  f prov ded,   use t se  nterest ds for generat ng cand dates  nstead of for example fetch ng user selected  nterests
}(hasPersonalData='true')

struct AdCampa gnTarget {
    1: requ red l st< 64> s m larToUser ds(personalDataType='User d')
}(hasPersonalData='true')

struct ConnectTab {
    1: requ red l st< 64> byfSeedUser ds(personalDataType='User d')
    2: requ red l st< 64> s m larToUser ds(personalDataType='User d')
    3: requ red l st<recently_engaged_user_ d.RecentlyEngagedUser d> recentlyEngagedUser ds
}(hasPersonalData='true')

struct S m larToUser {
    1: requ red  64 s m larToUser d(personalDataType='User d')
}(hasPersonalData='true')

struct PostNuxFollowTask {
    1: opt onal flows.FlowContext flowContext // set for recom ndat on  ns de an  nteract ve flow
}(hasPersonalData='true')

un on D splayContext {
    1: Prof le prof le
    2: Search search
    3: Rux rux
    4: Top c top c
    5: React veFollow react veFollow
    6: Nux nterests nux nterests
    7: AdCampa gnTarget adCampa gnTarget
    8: ConnectTab connectTab
    9: S m larToUser s m larToUser
    10: PostNuxFollowTask postNuxFollowTask
}(hasPersonalData='true')
