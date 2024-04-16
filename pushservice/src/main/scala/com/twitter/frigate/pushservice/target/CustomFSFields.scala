package com.tw ter.fr gate.pushserv ce.target

 mport com.tw ter.featuresw c s.FSCustomMap nput
 mport com.tw ter.featuresw c s.pars ng.DynMap
 mport com.tw ter.fr gate.common.store.dev ce nfo.Dev ce nfo
 mport com.tw ter.fr gate.pushserv ce.ut l.Nsfw nfo
 mport com.tw ter.g zmoduck.thr ftscala.User

object CustomFSF elds {
  pr vate val  sReturn ngUser = " s_return ng_user"
  pr vate val DaysS nceS gnup = "days_s nce_s gnup"
  pr vate val DaysS nceLog n = "days_s nce_log n"
  pr vate val DaysS nceReact vat on = "days_s nce_react vat on"
  pr vate val React vat onDate = "react vat on_date"
  pr vate val FollowGraphS ze = "follow_graph_s ze"
  pr vate val G zmoduckUserType = "g zmoduck_user_type"
  pr vate val UserAge = "mr_user_age"
  pr vate val Sens  veOpt n = "sens  ve_opt_ n"
  pr vate val NsfwFollowRat o = "nsfw_follow_rat o"
  pr vate val TotalFollows = "follow_count"
  pr vate val NsfwRealGraphScore = "nsfw_real_graph_score"
  pr vate val NsfwProf leV s  = "nsfw_prof le_v s "
  pr vate val TotalSearc s = "total_searc s"
  pr vate val NsfwSearchScore = "nsfw_search_score"
  pr vate val HasReportedNsfw = "nsfw_reported"
  pr vate val HasD sl kedNsfw = "nsfw_d sl ked"
  pr vate val UserState = "user_state"
  pr vate val MrUserState = "mr_user_state"
  pr vate val NumDaysRece vedPush nLast30Days =
    "num_days_rece ved_push_ n_last_30_days"
  pr vate val Recom ndat onsSett ng = "recom ndat ons_sett ng"
  pr vate val Top csSett ng = "top cs_sett ng"
  pr vate val SpacesSett ng = "spaces_sett ng"
  pr vate val NewsSett ng = "news_sett ng"
  pr vate val L veV deoSett ng = "l ve_v deo_sett ng"
  pr vate val HasRecentPushableRebDev ce = "has_recent_pushable_r b_dev ce"
  pr vate val RequestS ce = "request_s ce"
}

case class CustomFSF elds(
   sReact vatedUser: Boolean,
  daysS nceS gnup:  nt,
  numDaysRece vedPush nLast30Days:  nt,
  daysS nceLog n: Opt on[ nt],
  daysS nceReact vat on: Opt on[ nt],
  user: Opt on[User],
  userState: Opt on[Str ng],
  mrUserState: Opt on[Str ng],
  react vat onDate: Opt on[Str ng],
  requestS ce: Opt on[Str ng],
  userAge: Opt on[ nt],
  nsfw nfo: Opt on[Nsfw nfo],
  dev ce nfo: Opt on[Dev ce nfo]) {

   mport CustomFSF elds._

  pr vate val keyValMap: Map[Str ng, Any] = Map(
     sReturn ngUser ->  sReact vatedUser,
    DaysS nceS gnup -> daysS nceS gnup,
    DaysS nceLog n -> daysS nceLog n,
    NumDaysRece vedPush nLast30Days -> numDaysRece vedPush nLast30Days
  ) ++
    daysS nceReact vat on.map(DaysS nceReact vat on -> _) ++
    react vat onDate.map(React vat onDate -> _) ++
    user.flatMap(_.counts.map(counts => FollowGraphS ze -> counts.follow ng)) ++
    user.map(u => G zmoduckUserType -> u.userType.na ) ++
    userState.map(UserState -> _) ++
    mrUserState.map(MrUserState -> _) ++
    requestS ce.map(RequestS ce -> _) ++
    userAge.map(UserAge -> _) ++
    nsfw nfo.flatMap(_.senst veOpt n).map(Sens  veOpt n -> _) ++
    nsfw nfo
      .map { ns nfo =>
        Map[Str ng, Any](
          NsfwFollowRat o -> ns nfo.nsfwFollowRat o,
          TotalFollows -> ns nfo.totalFollowCount,
          NsfwRealGraphScore -> ns nfo.realGraphScore,
          NsfwProf leV s  -> ns nfo.nsfwProf leV s s,
          TotalSearc s -> ns nfo.totalSearc s,
          NsfwSearchScore -> ns nfo.searchNsfwScore,
          HasReportedNsfw -> ns nfo.hasReported,
          HasD sl kedNsfw -> ns nfo.hasD sl ked
        )
      }.getOrElse(Map.empty[Str ng, Any]) ++
    dev ce nfo
      .map { dev ce nfo =>
        Map[Str ng, Boolean](
          Recom ndat onsSett ng -> dev ce nfo. sRecom ndat onsEl g ble,
          Top csSett ng -> dev ce nfo. sTop csEl g ble,
          SpacesSett ng -> dev ce nfo. sSpacesEl g ble,
          L veV deoSett ng -> dev ce nfo. sBroadcastsEl g ble,
          NewsSett ng -> dev ce nfo. sNewsEl g ble,
          HasRecentPushableRebDev ce -> dev ce nfo.hasRecentPushableR bDev ce
        )
      }.getOrElse(Map.empty[Str ng, Boolean])

  val fsMap = FSCustomMap nput(DynMap(keyValMap))
}
