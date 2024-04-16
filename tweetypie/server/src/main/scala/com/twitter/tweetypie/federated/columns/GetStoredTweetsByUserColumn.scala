package com.tw ter.t etyp e.federated.columns

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
 mport com.tw ter.t etyp e.User d
 mport com.tw ter.t etyp e.thr ftscala.federated.GetStoredT etsByUserV ew
 mport com.tw ter.t etyp e.thr ftscala.federated.GetStoredT etsByUserResponse
 mport com.tw ter.t etyp e.{thr ftscala => thr ft}
 mport com.tw ter.ut l.Future

class GetStoredT etsByUserColumn(
  handler: thr ft.GetStoredT etsByUserRequest => Future[thr ft.GetStoredT etsByUserResult])
    extends StratoFed.Column(GetStoredT etsByUserColumn.Path)
    w h StratoFed.Fetch.St ch {

  overr de val contact nfo: Contact nfo = T etyp eContact nfo
  overr de val  tadata: Op tadata = Op tadata(
    l fecycle = So (Product on),
    descr pt on =
      So (Pla nText("Fetc s hydrated T ets for a part cular User regardless of T et state."))
  )
  overr de val pol cy: Pol cy = AnyOf(
    Seq(
      FromColumns(Set(Path("t etyp e/data-prov der/storedT ets.User"))),
      Has(LdapGroup("t etyp e-team"))
    ))

  overr de type Key = User d
  overr de type V ew = GetStoredT etsByUserV ew
  overr de type Value = GetStoredT etsByUserResponse

  overr de val keyConv: Conv[Key] = Conv.ofType
  overr de val v ewConv: Conv[V ew] = ScroogeConv.fromStruct[GetStoredT etsByUserV ew]
  overr de val valueConv: Conv[Value] = ScroogeConv.fromStruct[GetStoredT etsByUserResponse]

  overr de def fetch(key: Key, v ew: V ew): St ch[Result[Value]] = {
    val request = thr ft.GetStoredT etsByUserRequest(
      user d = key,
      opt ons = So (
        thr ft.GetStoredT etsByUserOpt ons(
          bypassV s b l yF lter ng = v ew.bypassV s b l yF lter ng,
          setForUser d = v ew.setForUser d,
          startT  Msec = v ew.startT  Msec,
          endT  Msec = v ew.endT  Msec,
          cursor = v ew.cursor,
          startFromOldest = v ew.startFromOldest,
          add  onalF eld ds = v ew.add  onalF eld ds
        ))
    )

    St ch
      .callFuture(handler(request))
      .map { result =>
        Fetch.Result.found(
          GetStoredT etsByUserResponse(
            storedT ets = result.storedT ets,
            cursor = result.cursor
          ))
      }
      .rescue {
        case _ => St ch.except on(Err(Err. nternal))
      }
  }

}

object GetStoredT etsByUserColumn {
  val Path = "t etyp e/ nternal/getStoredT ets.User"
}
