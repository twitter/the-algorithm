package com.tw ter.t etyp e.federated.columns

 mport com.tw ter.st ch.MapGroup
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.access.Access.LdapGroup
 mport com.tw ter.strato.catalog.Fetch
 mport com.tw ter.strato.catalog.Op tadata
 mport com.tw ter.strato.conf g.AnyOf
 mport com.tw ter.strato.conf g.Contact nfo
 mport com.tw ter.strato.conf g.FromColumns
 mport com.tw ter.strato.conf g.Has
 mport com.tw ter.strato.conf g.Path
 mport com.tw ter.strato.conf g.Pol cy
 mport com.tw ter.strato.data.Conv
 mport com.tw ter.strato.data.Descr pt on.Pla nText
 mport com.tw ter.strato.data.L fecycle.Product on
 mport com.tw ter.strato.fed.StratoFed
 mport com.tw ter.strato.response.Err
 mport com.tw ter.strato.thr ft.ScroogeConv
 mport com.tw ter.t etyp e.{thr ftscala => thr ft}
 mport com.tw ter.t etyp e.T et d
 mport com.tw ter.t etyp e.thr ftscala.federated.GetStoredT etsV ew
 mport com.tw ter.t etyp e.thr ftscala.federated.GetStoredT etsResponse
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try

class GetStoredT etsColumn(
  getStoredT ets: thr ft.GetStoredT etsRequest => Future[Seq[thr ft.GetStoredT etsResult]])
    extends StratoFed.Column(GetStoredT etsColumn.Path)
    w h StratoFed.Fetch.St ch {

  overr de val contact nfo: Contact nfo = T etyp eContact nfo
  overr de val  tadata: Op tadata = Op tadata(
    l fecycle = So (Product on),
    descr pt on = So (Pla nText("Fetc s hydrated T ets regardless of T et state."))
  )
  overr de val pol cy: Pol cy = AnyOf(
    Seq(
      FromColumns(
        Set(
          Path("t etyp e/data-prov der/storedT ets.User"),
          Path("note_t et/data-prov der/noteT etForZ pb rd.User"))),
      Has(LdapGroup("t etyp e-team"))
    ))

  overr de type Key = T et d
  overr de type V ew = GetStoredT etsV ew
  overr de type Value = GetStoredT etsResponse

  overr de val keyConv: Conv[Key] = Conv.ofType
  overr de val v ewConv: Conv[V ew] = ScroogeConv.fromStruct[GetStoredT etsV ew]
  overr de val valueConv: Conv[Value] = ScroogeConv.fromStruct[GetStoredT etsResponse]

  overr de def fetch(key: Key, v ew: V ew): St ch[Result[Value]] = {
    St ch.call(key, Group(v ew))
  }

  pr vate case class Group(v ew: GetStoredT etsV ew)
      extends MapGroup[T et d, Fetch.Result[GetStoredT etsResponse]] {
    overr de protected def run(
      keys: Seq[T et d]
    ): Future[T et d => Try[Result[GetStoredT etsResponse]]] = {
      val opt ons = thr ft.GetStoredT etsOpt ons(
        bypassV s b l yF lter ng = v ew.bypassV s b l yF lter ng,
        forUser d = v ew.forUser d,
        add  onalF eld ds = v ew.add  onalF eld ds
      )

      getStoredT ets(thr ft.GetStoredT etsRequest(keys, So (opt ons)))
        .map(transformAndGroupByT et d)
        .handle {
          case _ =>
            _ => Throw[Result[GetStoredT etsResponse]](Err(Err. nternal))
        }
    }

    pr vate def transformAndGroupByT et d(
      results: Seq[thr ft.GetStoredT etsResult]
    ): Map[T et d, Try[Fetch.Result[GetStoredT etsResponse]]] = {
      results
        .map(result => GetStoredT etsResponse(result.storedT et))
        .groupBy(_.storedT et.t et d)
        .map {
          case (t et d, Seq(result)) => (t et d, Return(Fetch.Result.found(result)))
          case (t et d, mult pleResults) =>
            (
              t et d,
              Throw(Err(Err.BadRequest, s"Got ${mult pleResults.s ze} results for $t et d")))
        }
    }

  }
}

object GetStoredT etsColumn {
  val Path = "t etyp e/ nternal/getStoredT ets.T et"
}
