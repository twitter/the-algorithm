package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala.EventNa space
 mport com.tw ter.cl entapp.thr ftscala. em
 mport com.tw ter.cl entapp.thr ftscala. emType.Top c
 mport com.tw ter.gu de.scr b ng.thr ftscala.Top cModule tadata
 mport com.tw ter.gu de.scr b ng.thr ftscala.TransparentGu deDeta ls
 mport com.tw ter.suggests.controller_data.ho _h l_top c_annotat on_prompt.thr ftscala.Ho H lTop cAnnotat onPromptControllerData
 mport com.tw ter.suggests.controller_data.ho _h l_top c_annotat on_prompt.v1.thr ftscala.{
  Ho H lTop cAnnotat onPromptControllerData => Ho H lTop cAnnotat onPromptControllerDataV1
}
 mport com.tw ter.suggests.controller_data.ho _top c_annotat on_prompt.thr ftscala.Ho Top cAnnotat onPromptControllerData
 mport com.tw ter.suggests.controller_data.ho _top c_annotat on_prompt.v1.thr ftscala.{
  Ho Top cAnnotat onPromptControllerData => Ho Top cAnnotat onPromptControllerDataV1
}
 mport com.tw ter.suggests.controller_data.ho _top c_follow_prompt.thr ftscala.Ho Top cFollowPromptControllerData
 mport com.tw ter.suggests.controller_data.ho _top c_follow_prompt.v1.thr ftscala.{
  Ho Top cFollowPromptControllerData => Ho Top cFollowPromptControllerDataV1
}
 mport com.tw ter.suggests.controller_data.ho _t ets.thr ftscala.Ho T etsControllerData
 mport com.tw ter.suggests.controller_data.ho _t ets.v1.thr ftscala.{
  Ho T etsControllerData => Ho T etsControllerDataV1
}
 mport com.tw ter.suggests.controller_data.search_response. em_types.thr ftscala. emTypesControllerData
 mport com.tw ter.suggests.controller_data.search_response.thr ftscala.SearchResponseControllerData
 mport com.tw ter.suggests.controller_data.search_response.top c_follow_prompt.thr ftscala.SearchTop cFollowPromptControllerData
 mport com.tw ter.suggests.controller_data.search_response.t et_types.thr ftscala.T etTypesControllerData
 mport com.tw ter.suggests.controller_data.search_response.v1.thr ftscala.{
  SearchResponseControllerData => SearchResponseControllerDataV1
}
 mport com.tw ter.suggests.controller_data.thr ftscala.ControllerData
 mport com.tw ter.suggests.controller_data.t  l nes_top c.thr ftscala.T  l nesTop cControllerData
 mport com.tw ter.suggests.controller_data.t  l nes_top c.v1.thr ftscala.{
  T  l nesTop cControllerData => T  l nesTop cControllerDataV1
}
 mport com.tw ter.suggests.controller_data.v2.thr ftscala.{ControllerData => ControllerDataV2}
 mport com.tw ter.ut l.Try

object Top c dUt ls {
  val Doma n d: Long = 131 // Top cal Doma n

  def getTop c d(
     em:  em,
    na space: EventNa space
  ): Opt on[Long] =
    getTop c dFromHo Search( em)
      .orElse(getTop cFromGu de( em))
      .orElse(getTop cFromOnboard ng( em, na space))
      .orElse(getTop c dFrom em( em))

  def getTop c dFrom em( em:  em): Opt on[Long] =
     f ( em. emType.conta ns(Top c))
       em. d
    else None

  def getTop c dFromHo Search(
     em:  em
  ): Opt on[Long] = {
    val decodedControllerData =  em.suggest onDeta ls.flatMap(_.decodedControllerData)
    decodedControllerData match {
      case So (
            ControllerData.V2(
              ControllerDataV2.Ho T ets(
                Ho T etsControllerData.V1(ho T ets: Ho T etsControllerDataV1)))
          ) =>
        ho T ets.top c d
      case So (
            ControllerData.V2(
              ControllerDataV2.Ho Top cFollowPrompt(
                Ho Top cFollowPromptControllerData.V1(
                  ho Top cFollowPrompt: Ho Top cFollowPromptControllerDataV1)))
          ) =>
        ho Top cFollowPrompt.top c d
      case So (
            ControllerData.V2(
              ControllerDataV2.T  l nesTop c(
                T  l nesTop cControllerData.V1(
                  t  l nesTop c: T  l nesTop cControllerDataV1
                )))
          ) =>
        So (t  l nesTop c.top c d)
      case So (
            ControllerData.V2(
              ControllerDataV2.SearchResponse(
                SearchResponseControllerData.V1(s: SearchResponseControllerDataV1)))
          ) =>
        s. emTypesControllerData match {
          case So (
                 emTypesControllerData.Top cFollowControllerData(
                  top cFollowControllerData: SearchTop cFollowPromptControllerData)) =>
            top cFollowControllerData.top c d
          case So (
                 emTypesControllerData.T etTypesControllerData(
                  t etTypesControllerData: T etTypesControllerData)) =>
            t etTypesControllerData.top c d
          case _ => None
        }
      case So (
            ControllerData.V2(
              ControllerDataV2.Ho Top cAnnotat onPrompt(
                Ho Top cAnnotat onPromptControllerData.V1(
                  ho Top cAnnotat onPrompt: Ho Top cAnnotat onPromptControllerDataV1
                )))
          ) =>
        So (ho Top cAnnotat onPrompt.top c d)
      case So (
            ControllerData.V2(
              ControllerDataV2.Ho H lTop cAnnotat onPrompt(
                Ho H lTop cAnnotat onPromptControllerData.V1(
                  ho H lTop cAnnotat onPrompt: Ho H lTop cAnnotat onPromptControllerDataV1
                )))
          ) =>
        So (ho H lTop cAnnotat onPrompt.top c d)

      case _ => None
    }
  }

  def getTop cFromOnboard ng(
     em:  em,
    na space: EventNa space
  ): Opt on[Long] =
     f (na space.page.conta ns("onboard ng") &&
      (na space.sect on.ex sts(_.conta ns("top c")) ||
      na space.component.ex sts(_.conta ns("top c")) ||
      na space.ele nt.ex sts(_.conta ns("top c")))) {
       em.descr pt on.flatMap { descr pt on =>
        // descr pt on: " d=123,ma n=xyz,row=1"
        val tokens = descr pt on.spl (","). adOpt on.map(_.spl ("="))
        tokens match {
          case So (Array(" d", token, _*)) => Try(token.toLong).toOpt on
          case _ => None
        }
      }
    } else None

  def getTop cFromGu de(
     em:  em
  ): Opt on[Long] =
     em.gu de emDeta ls.flatMap {
      _.transparentGu deDeta ls match {
        case So (TransparentGu deDeta ls.Top c tadata(top c tadata)) =>
          top c tadata match {
            case Top cModule tadata.Ttt nterest(_) =>
              None
            case Top cModule tadata.Semant cCore nterest(semant cCore nterest) =>
               f (semant cCore nterest.doma n d == Doma n d.toStr ng)
                Try(semant cCore nterest.ent y d.toLong).toOpt on
              else None
            case Top cModule tadata.S mCluster nterest(_) =>
              None
            case Top cModule tadata.UnknownUn onF eld(_) => None
          }
        case _ => None
      }
    }
}
