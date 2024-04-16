package com.tw ter.t etyp e.federated.columns

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.MapGroup
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.catalog.Fetch
 mport com.tw ter.strato.catalog.Op tadata
 mport com.tw ter.strato.conf g.AllowAll
 mport com.tw ter.strato.conf g.Contact nfo
 mport com.tw ter.strato.conf g.Pol cy
 mport com.tw ter.strato.data.Conv
 mport com.tw ter.strato.data.Descr pt on.Pla nText
 mport com.tw ter.strato.data.L fecycle.Product on
 mport com.tw ter.strato.fed.StratoFed
 mport com.tw ter.strato.opcontext.OpContext
 mport com.tw ter.strato.response.Err
 mport com.tw ter.strato.thr ft.ScroogeConv
 mport com.tw ter.t etyp e.T et d
 mport com.tw ter.t etyp e.cl ent_ d.PreferForwardedServ ce dent f erForStrato
 mport com.tw ter.t etyp e.thr ftscala.GetT etF eldsOpt ons
 mport com.tw ter.t etyp e.thr ftscala.GetT etF eldsRequest
 mport com.tw ter.t etyp e.thr ftscala.GetT etF eldsResult
 mport com.tw ter.t etyp e.thr ftscala.T etV s b l yPol cy
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Try

/**
 * Strato federated column  mple nt ng GetT etF elds as a Fetch.
 */
class GetT etF eldsColumn(
  handler: GetT etF eldsRequest => Future[Seq[GetT etF eldsResult]],
  stats: StatsRece ver)
    extends StratoFed.Column(GetT etF eldsColumn.Path)
    w h StratoFed.Fetch.St chW hContext {

  /**
   * At t  po nt, t  fetch op w ll reject any requests that spec fy
   * v s b l yPol cy ot r than USER_V S BLE, so no access control  s needed.
   */
  overr de val pol cy: Pol cy = AllowAll

  overr de type Key = T et d
  overr de type V ew = GetT etF eldsOpt ons
  overr de type Value = GetT etF eldsResult

  overr de val keyConv: Conv[Key] = Conv.ofType
  overr de val v ewConv: Conv[V ew] = ScroogeConv.fromStruct[GetT etF eldsOpt ons]
  overr de val valueConv: Conv[Value] = ScroogeConv.fromStruct[GetT etF eldsResult]

  overr de val contact nfo: Contact nfo = T etyp eContact nfo
  overr de val  tadata: Op tadata = Op tadata(
    l fecycle = So (Product on),
    descr pt on =
      So (Pla nText("Get of t ets that allows fetch ng only spec f c subsets of t  data.")),
  )

  val safetyOpContextOnlyCounter = stats.counter("safety_op_context_only")
  val safetyOpContextOnlyValueScope = stats.scope("safety_op_context_only_value")
  val safetyOpContextOnlyCallerScope = stats.scope("safety_op_context_only_caller")

  val safetyV ewOnlyCounter = stats.counter("safety_v ew_only")
  val safetyV ewOnlyValueScope = stats.scope("safety_v ew_only_value")
  val safetyV ewOnlyCallerScope = stats.scope("safety_v ew_only_caller")

  val safetyLevel ncons stencyCounter = stats.counter("safety_level_ ncons stency")
  val safetyLevel ncons stencyValueScope = stats.scope("safety_level_ ncons stency_value")
  val safetyLevel ncons stencyCallerScope = stats.scope("safety_level_ ncons stency_caller")

  overr de def fetch(key: Key, v ew: V ew, ctx: OpContext): St ch[Result[Value]] = {
    compareSafetyLevel(v ew, ctx)
    c ckV s b l yPol cyUserV s ble(v ew).flatMap { _ =>
      St ch.call(key, Group(v ew))
    }
  }

  /**
   * Only allow [[T etV s b l yPol cy.UserV s ble]] v s b l yPol cy.
   *
   * T  column requ res access pol cy  n order to serve requests w h v s b l yPol cy
   * ot r than [[T etV s b l yPol cy.UserV s ble]]. Before   support access control,
   * reject all requests that are not safe.
   */
  pr vate def c ckV s b l yPol cyUserV s ble(v ew: V ew): St ch[Un ] =
    v ew.v s b l yPol cy match {
      case T etV s b l yPol cy.UserV s ble => St ch.value(Un )
      case ot rValue =>
        St ch.except on(
          Err(
            Err.BadRequest,
            "GetT etF elds does not support access control on Strato yet. "
              + s" nce v s b l yPol cy can only take t  default ${T etV s b l yPol cy.UserV s ble} value, "
              + s"got: ${ot rValue}."
          ))
    }

  /** Compare t  SafetyLevels  n t  V ew and OpContext */
  pr vate def compareSafetyLevel(v ew: V ew, ctx: OpContext): Un  =
    (v ew.safetyLevel, ctx.safetyLevel) match {
      case (None, None) =>
      case (So (v ewSafety), None) => {
        safetyV ewOnlyCounter. ncr()
        safetyV ewOnlyValueScope.counter(v ewSafety.na ). ncr()
        PreferForwardedServ ce dent f erForStrato.serv ce dent f er
          .foreach(serv ce d => safetyV ewOnlyCallerScope.counter(serv ce d.toStr ng). ncr())
      }
      case (None, So (ctxSafety)) => {
        safetyOpContextOnlyCounter. ncr()
        safetyOpContextOnlyValueScope.counter(ctxSafety.na ). ncr()
        PreferForwardedServ ce dent f erForStrato.serv ce dent f er
          .foreach(serv ce d => safetyOpContextOnlyCallerScope.counter(serv ce d.toStr ng). ncr())
      }
      case (So (v ewSafety), So (ctxSafety)) =>
        def safeStr ngEquals(a: Str ng, b: Str ng) =
          a.toLo rCase().tr m().equals(b.toLo rCase().tr m())
         f (!safeStr ngEquals(v ewSafety.na , ctxSafety.na )) {
          safetyLevel ncons stencyCounter. ncr()
          safetyLevel ncons stencyValueScope.counter(v ewSafety.na  + '-' + ctxSafety.na ). ncr()
          PreferForwardedServ ce dent f erForStrato.serv ce dent f er
            .foreach(serv ce d =>
              safetyLevel ncons stencyCallerScope.counter(serv ce d.toStr ng). ncr())
        }
    }

  /**
   *  ans of batch ng of [[GetT etF eldsColumn]] calls.
   *
   * Only calls  ssued aga nst t  sa   nstance of [[GetT etF eldsColumn]]
   * are batc d as St ch clusters group objects based on equal y,
   * and nested case class  mpl c ly captures [[GetT etF eldsColumn]] reference.
   */
  pr vate case class Group(v ew: GetT etF eldsOpt ons)
      extends MapGroup[T et d, Fetch.Result[GetT etF eldsResult]] {

    /**
     * Batc s g ven [[T et d]] lookups  n a s ngle [[GetT etF eldsRequest]]
     * and returns a result mapped by [[T et d]].
     */
    overr de protected def run(
      keys: Seq[T et d]
    ): Future[T et d => Try[Fetch.Result[GetT etF eldsResult]]] =
      handler(
        GetT etF eldsRequest(
          // Sort ng t  keys makes for s mpler matc rs  n t  tests
          // as match ng on a Seq needs to be  n order.
          t et ds = keys.sorted,
          opt ons = v ew,
        )).map(groupByT et d)

    /**
     * Groups g ven [[GetT etF eldsResult]] objects by [[T et d]] and returns t  mapp ng.
     */
    pr vate def groupByT et d(
      allResults: Seq[GetT etF eldsResult]
    ): T et d => Try[Fetch.Result[GetT etF eldsResult]] = {
      allResults
        .groupBy(_.t et d)
        .mapValues {
          case Seq(result) => Try(Fetch.Result.found(result))
          case manyResults =>
            Try {
              throw Err(
                Err.Dependency,
                s"Expected one result per t eet  D, got ${manyResults.length}")
            }
        }
    }
  }
}

object GetT etF eldsColumn {
  val Path = "t etyp e/getT etF elds.T et"
}
