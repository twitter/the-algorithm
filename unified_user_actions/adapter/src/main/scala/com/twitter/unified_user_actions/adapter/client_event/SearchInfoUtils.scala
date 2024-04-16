package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.cl entapp.thr ftscala.{ em => LogEvent em}
 mport com.tw ter.search.common.constants.thr ftscala.Thr ftQueryS ce
 mport com.tw ter.search.common.constants.thr ftscala.T etResultS ce
 mport com.tw ter.search.common.constants.thr ftscala.UserResultS ce
 mport com.tw ter.suggests.controller_data.search_response. em_types.thr ftscala. emTypesControllerData
 mport com.tw ter.suggests.controller_data.search_response. em_types.thr ftscala. emTypesControllerData.T etTypesControllerData
 mport com.tw ter.suggests.controller_data.search_response. em_types.thr ftscala. emTypesControllerData.UserTypesControllerData
 mport com.tw ter.suggests.controller_data.search_response.request.thr ftscala.RequestControllerData
 mport com.tw ter.suggests.controller_data.search_response.thr ftscala.SearchResponseControllerData.V1
 mport com.tw ter.suggests.controller_data.search_response.thr ftscala.SearchResponseControllerDataAl ases.V1Al as
 mport com.tw ter.suggests.controller_data.thr ftscala.ControllerData.V2
 mport com.tw ter.suggests.controller_data.v2.thr ftscala.ControllerData.SearchResponse
 mport com.tw ter.un f ed_user_act ons.thr ftscala.SearchQueryF lterType
 mport com.tw ter.un f ed_user_act ons.thr ftscala.SearchQueryF lterType._

class Search nfoUt ls( em: LogEvent em) {
  pr vate val searchControllerDataOpt: Opt on[V1Al as] =  em.suggest onDeta ls.flatMap { sd =>
    sd.decodedControllerData.flatMap { decodedControllerData =>
      decodedControllerData match {
        case V2(v2ControllerData) =>
          v2ControllerData match {
            case SearchResponse(searchResponseControllerData) =>
              searchResponseControllerData match {
                case V1(searchResponseControllerDataV1) =>
                  So (searchResponseControllerDataV1)
                case _ => None
              }
            case _ =>
              None
          }
        case _ => None
      }
    }
  }

  pr vate val requestControllerDataOptFrom em: Opt on[RequestControllerData] =
    searchControllerDataOpt.flatMap { searchControllerData =>
      searchControllerData.requestControllerData
    }
  pr vate val  emTypesControllerDataOptFrom em: Opt on[ emTypesControllerData] =
    searchControllerDataOpt.flatMap { searchControllerData =>
      searchControllerData. emTypesControllerData
    }

  def c ckB (b map: Long,  dx:  nt): Boolean = {
    (b map / Math.pow(2,  dx)).to nt % 2 == 1
  }

  def getQueryOptFromSearchDeta ls(logEvent: LogEvent): Opt on[Str ng] = {
    logEvent.searchDeta ls.flatMap { sd => sd.query }
  }

  def getQueryOptFromControllerDataFrom em: Opt on[Str ng] = {
    requestControllerDataOptFrom em.flatMap { rd => rd.rawQuery }
  }

  def getQueryOptFrom em(logEvent: LogEvent): Opt on[Str ng] = {
    // F rst   try to get t  query from controller data, and  f that's not ava lable,   fall
    // back to query  n search deta ls.  f both are None, queryOpt  s None.
    getQueryOptFromControllerDataFrom em.orElse(getQueryOptFromSearchDeta ls(logEvent))
  }

  def getT etTypesOptFromControllerDataFrom em: Opt on[T etTypesControllerData] = {
     emTypesControllerDataOptFrom em.flatMap {  emTypes =>
       emTypes match {
        case T etTypesControllerData(t etTypesControllerData) =>
          So (T etTypesControllerData(t etTypesControllerData))
        case _ => None
      }
    }
  }

  def getUserTypesOptFromControllerDataFrom em: Opt on[UserTypesControllerData] = {
     emTypesControllerDataOptFrom em.flatMap {  emTypes =>
       emTypes match {
        case UserTypesControllerData(userTypesControllerData) =>
          So (UserTypesControllerData(userTypesControllerData))
        case _ => None
      }
    }
  }

  def getQueryS ceOptFromControllerDataFrom em: Opt on[Thr ftQueryS ce] = {
    requestControllerDataOptFrom em
      .flatMap { rd => rd.queryS ce }
      .flatMap { queryS ceVal => Thr ftQueryS ce.get(queryS ceVal) }
  }

  def getT etResultS ces: Opt on[Set[T etResultS ce]] = {
    getT etTypesOptFromControllerDataFrom em
      .flatMap { cd => cd.t etTypesControllerData.t etTypesB map }
      .map { t etTypesB map =>
        T etResultS ce.l st.f lter { t => c ckB (t etTypesB map, t.value) }.toSet
      }
  }

  def getUserResultS ces: Opt on[Set[UserResultS ce]] = {
    getUserTypesOptFromControllerDataFrom em
      .flatMap { cd => cd.userTypesControllerData.userTypesB map }
      .map { userTypesB map =>
        UserResultS ce.l st.f lter { t => c ckB (userTypesB map, t.value) }.toSet
      }
  }

  def getQueryF lterType(logEvent: LogEvent): Opt on[SearchQueryF lterType] = {
    val searchTab = logEvent.eventNa space.map(_.cl ent).flatMap {
      case So ("m5") | So ("andro d") => logEvent.eventNa space.flatMap(_.ele nt)
      case _ => logEvent.eventNa space.flatMap(_.sect on)
    }
    searchTab.flatMap {
      case "search_f lter_top" => So (Top)
      case "search_f lter_l ve" => So (Latest)
      // andro d uses search_f lter_t ets  nstead of search_f lter_l ve
      case "search_f lter_t ets" => So (Latest)
      case "search_f lter_user" => So (People)
      case "search_f lter_ mage" => So (Photos)
      case "search_f lter_v deo" => So (V deos)
      case _ => None
    }
  }

  def getRequestJo n d: Opt on[Long] = requestControllerDataOptFrom em.flatMap(_.requestJo n d)

  def getTrace d: Opt on[Long] = requestControllerDataOptFrom em.flatMap(_.trace d)

}
