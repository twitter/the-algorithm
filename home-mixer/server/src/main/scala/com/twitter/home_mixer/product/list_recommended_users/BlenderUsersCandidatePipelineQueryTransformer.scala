package com.tw ter.ho _m xer.product.l st_recom nded_users

 mport com.tw ter.ho _m xer.product.l st_recom nded_users.model.L stRecom ndedUsersQuery
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er
 mport com.tw ter.search.adapt ve.adapt ve_results.thr ftscala.ResultType
 mport com.tw ter.search.blender.adapt ve_search.thr ftscala.Adapt veSearchRequest
 mport com.tw ter.search.blender.thr ftscala.Thr ftBlenderRequest
 mport com.tw ter.search.blender.thr ftscala.Thr ftBlenderT etyp eOpt ons
 mport com.tw ter.search.blender.thr ftscala.Thr ftBlenderWorkflow D
 mport com.tw ter.search.common.constants.thr ftscala.Thr ftQueryS ce
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel

object BlenderUsersCand dateP pel neQueryTransfor r
    extends Cand dateP pel neQueryTransfor r[L stRecom ndedUsersQuery, Thr ftBlenderRequest] {

  overr de val  dent f er: Transfor r dent f er = Transfor r dent f er("BlenderUsers")

  /**
   * T   s a user-def ned descr ptor used by Blender to track t  s ce of traff c, and  
   *  s d fferent from a cl ent  d, wh ch  s set dur ng F nagle cl ent construct on.
   */
  pr vate val Cl entAppNa  = "t  l nem xer.l st_recom nded_users"

  overr de def transform(query: L stRecom ndedUsersQuery): Thr ftBlenderRequest = {

    Thr ftBlenderRequest(
      workflow D = So (Thr ftBlenderWorkflow D.Adapt veSearch),
      user D = So (query.getRequ redUser d), // perspect val
      u Lang = query.cl entContext.languageCode, // perspect val
      cl entAppNa  = So (Cl entAppNa ),
      adapt veSearchRequest = So (
        Adapt veSearchRequest(
          rawQuery = query.l stNa ,
          numResults = 40,
          getPromotedContent = false,
          resultF lter = So (ResultType.User),
        )
      ),
      queryS ce = So (Thr ftQueryS ce.TypedQuery),
      getCorrect ons = true,
      t etyp eOpt ons = So (
        Thr ftBlenderT etyp eOpt ons(
          safetyLevel = So (SafetyLevel.Recom ndat ons)
        )
      )
    )
  }
}
