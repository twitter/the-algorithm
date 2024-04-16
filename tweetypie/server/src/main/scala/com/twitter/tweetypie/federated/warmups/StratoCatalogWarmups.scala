package com.tw ter.t etyp e
package federated
package warmups

 mport com.tw ter.context.Tw terContext
 mport com.tw ter.context.thr ftscala.V e r
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.access.Access
 mport com.tw ter.strato.access.Access.AccessToken
 mport com.tw ter.strato.access.Access.Aut nt catedTw terUser d
 mport com.tw ter.strato.access.Access.Aut nt catedTw terUserNotSuspended
 mport com.tw ter.strato.access.Access.Tw terUser d
 mport com.tw ter.strato.access.Access.Tw terUserNotSuspended
 mport com.tw ter.strato.catalog.Ops
 mport com.tw ter.strato.cl ent.Stat cCl ent
 mport com.tw ter.strato.context.StratoContext
 mport com.tw ter.strato.opcontext.DarkRequest
 mport com.tw ter.strato.opcontext.OpContext
 mport com.tw ter.strato.test.conf g.bouncer.TestPr nc pals
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._
 mport com.tw ter.t etyp e.federated.columns.CreateRet etColumn
 mport com.tw ter.t etyp e.federated.columns.CreateT etColumn
 mport com.tw ter.t etyp e.federated.columns.DeleteT etColumn
 mport com.tw ter.t etyp e.federated.columns.Unret etColumn
 mport com.tw ter.t etyp e.serv ce.WarmupQuer esSett ngs
 mport com.tw ter.t etyp e.thr ftscala.graphql._
 mport com.tw ter.ut l.logg ng.Logger
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Stopwatch

object StratoCatalogWarmups {
  pr vate[t ] val log = Logger(getClass)

  // Performs warmup quer es, fa l ng after 30 seconds
  def warmup(
    warmupSett ngs: WarmupQuer esSett ngs,
    catalog: Part alFunct on[Str ng, Ops]
  ): Future[Un ] = {
    val elapsed = Stopwatch.start()
    // note:   need to supply bouncer pr nc pals  re, because t 
    //       columns are gated by a bouncer pol cy
    Access
      .w hPr nc pals(WarmupPr nc pals) {
        StratoContext.w hOpContext(WarmupOpContext) {
          Tw terContext.let(v e r = WarmupV e r) {
            warmupSett ngs.cl ent d.asCurrent {
              St ch.run(executeDarkly(catalog))
            }
          }
        }
      }
      .onSuccess { _ => log. nfo("warmup completed  n %s".format(elapsed())) }
      .onFa lure { t => log.error("could not complete warmup quer es before startup.", t) }
  }

  pr vate val WarmupTw terUser d = 0L

  pr vate val WarmupPr nc pals = Set(
    TestPr nc pals.normalStratoBouncerAccessPr nc pal,
    Aut nt catedTw terUser d(WarmupTw terUser d),
    Tw terUser d(WarmupTw terUser d),
    Tw terUserNotSuspended,
    Aut nt catedTw terUserNotSuspended,
    AccessToken( sWr able = true)
  )

  pr vate[t ] val R bCl ent d = 0L

  pr vate[t ] val WarmupV e r = V e r(
    user d = So (WarmupTw terUser d),
    aut nt catedUser d = So (WarmupTw terUser d),
    cl entAppl cat on d = So (R bCl ent d),
  )

  pr vate[t ] val WarmupOpContext =
    OpContext
      .safetyLevel(SafetyLevel.T etWr esAp .na )
      .copy(darkRequest = So (DarkRequest()))
      .toThr ft()

  pr vate[t ] val EllenOscarSelf e = 440322224407314432L

  pr vate[t ] val Tw terContext: Tw terContext =
    com.tw ter.context.Tw terContext(com.tw ter.t etyp e.Tw terContextPerm )

  pr vate[t ] def executeDarkly(catalog: Part alFunct on[Str ng, Ops]): St ch[Un ] = {
    val stratoCl ent = new Stat cCl ent(catalog)
    val t etCreator =
      stratoCl ent.executer[CreateT etRequest, CreateT etResponseW hSubqueryPrefetch ems](
        CreateT etColumn.Path)

    val t etDeletor =
      stratoCl ent
        .executer[DeleteT etRequest, DeleteT etResponseW hSubqueryPrefetch ems](
          DeleteT etColumn.Path)

    val ret etCreator =
      stratoCl ent
        .executer[CreateRet etRequest, CreateRet etResponseW hSubqueryPrefetch ems](
          CreateRet etColumn.Path)

    val unret etor =
      stratoCl ent
        .executer[Unret etRequest, Unret etResponseW hSubqueryPrefetch ems](
          Unret etColumn.Path)

    val st chCreateT et =
      t etCreator
        .execute(CreateT etRequest("gett ng war r"))
        .onSuccess(_ => log. nfo(s"${CreateT etColumn.Path} warmup success"))
        .onFa lure(e => log. nfo(s"${CreateT etColumn.Path} warmup fa l: $e"))

    val st chDeleteT et =
      t etDeletor
        .execute(DeleteT etRequest(-1L))
        .onSuccess(_ => log. nfo(s"${DeleteT etColumn.Path} warmup success"))
        .onFa lure(e => log. nfo(s"${DeleteT etColumn.Path} warmup fa l: $e"))

    val st chCreateRet et =
      ret etCreator
        .execute(CreateRet etRequest(EllenOscarSelf e))
        .onSuccess(_ => log. nfo(s"${CreateRet etColumn.Path} warmup success"))
        .onFa lure(e => log. nfo(s"${CreateRet etColumn.Path} warmup fa l: $e"))

    val st chUnret et =
      unret etor
        .execute(Unret etRequest(EllenOscarSelf e))
        .onSuccess(_ => log. nfo(s"${Unret etColumn.Path} warmup success"))
        .onFa lure(e => log. nfo(s"${Unret etColumn.Path} warmup fa l: $e"))

    St ch
      .jo n(
        st chCreateT et,
        st chDeleteT et,
        st chCreateRet et,
        st chUnret et,
      ).un 
  }
}
