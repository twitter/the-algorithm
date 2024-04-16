 nclude "logg ng/flows.thr ft"
 nclude "logg ng/recently_engaged_user_ d.thr ft"

na space java com.tw ter.follow_recom ndat ons.logg ng.thr ftjava
#@na space scala com.tw ter.follow_recom ndat ons.logg ng.thr ftscala
#@na space strato com.tw ter.follow_recom ndat ons.logg ng

// Offl ne equal of Prof le D splayContext
struct Offl neProf le {
    1: requ red  64 prof le d(personalDataType='User d')
}(pers sted='true', hasPersonalData='true')

// Offl ne equal of Search D splayContext
struct Offl neSearch {
    1: requ red str ng searchQuery(personalDataType='SearchQuery')
}(pers sted='true', hasPersonalData='true')

// Offl ne equal of Rux Land ng Page D splayContext
struct Offl neRux {
  1: requ red  64 focalAuthor d(personalDataType="User d")
}(pers sted='true', hasPersonalData='true')

// Offl ne equal of Top c D splayContext
struct Offl neTop c {
  1: requ red  64 top c d(personalDataType = 'Top cFollow')
}(pers sted='true', hasPersonalData='true')

struct Offl neReact veFollow {
    1: requ red l st< 64> follo dUser ds(personalDataType='User d')
}(pers sted='true', hasPersonalData='true')

struct Offl neNux nterests {
    1: opt onal flows.Offl neFlowContext flowContext // set for recom ndat on  ns de an  nteract ve flow
}(pers sted='true', hasPersonalData='true')

struct Offl neAdCampa gnTarget {
    1: requ red l st< 64> s m larToUser ds(personalDataType='User d')
}(pers sted='true', hasPersonalData='true')

struct Offl neConnectTab {
    1: requ red l st< 64> byfSeedUser ds(personalDataType='User d')
    2: requ red l st< 64> s m larToUser ds(personalDataType='User d')
    3: requ red l st<recently_engaged_user_ d.RecentlyEngagedUser d> recentlyEngagedUser ds
}(pers sted='true', hasPersonalData='true')

struct Offl neS m larToUser {
    1: requ red  64 s m larToUser d(personalDataType='User d')
}(pers sted='true', hasPersonalData='true')

struct Offl nePostNuxFollowTask {
    1: opt onal flows.Offl neFlowContext flowContext // set for recom ndat on  ns de an  nteract ve flow
}(pers sted='true', hasPersonalData='true')

// Offl ne equal of D splayContext
un on Offl neD splayContext {
    1: Offl neProf le prof le
    2: Offl neSearch search
    3: Offl neRux rux
    4: Offl neTop c top c
    5: Offl neReact veFollow react veFollow
    6: Offl neNux nterests nux nterests
    7: Offl neAdCampa gnTarget adCampa gnTarget
    8: Offl neConnectTab connectTab
    9: Offl neS m larToUser s m larToUser
    10: Offl nePostNuxFollowTask postNuxFollowTask
}(pers sted='true', hasPersonalData='true')
