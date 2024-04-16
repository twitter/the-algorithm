package com.tw ter.t etyp e
package federated.columns

 mport com.tw ter. o.Buf
 mport com.tw ter.scrooge.TF eldBlob
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.access.Access
 mport com.tw ter.strato.catalog.Op tadata
 mport com.tw ter.strato.conf g.AllowAll
 mport com.tw ter.strato.conf g.Contact nfo
 mport com.tw ter.strato.conf g.Pol cy
 mport com.tw ter.strato.data.Conv
 mport com.tw ter.strato.data.Descr pt on.Pla nText
 mport com.tw ter.strato.data.L fecycle.Product on
 mport com.tw ter.strato.data.Type
 mport com.tw ter.strato.data.Val
 mport com.tw ter.strato.fed.StratoFed
 mport com.tw ter.strato.opcontext.OpContext
 mport com.tw ter.strato.ser al zat on.MVal
 mport com.tw ter.strato.ser al zat on.Thr ft
 mport com.tw ter.strato.ut l.Str ngs
 mport com.tw ter.t etyp e.thr ftscala.GetT etF eldsResult
 mport com.tw ter.t etyp e.thr ftscala.SetAdd  onalF eldsRequest
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.t etyp e.thr ftscala.T etF eldsResultState.Found
 mport com.tw ter.ut l.Future
 mport org.apac .thr ft.protocol.TF eld

/**
 * Federated strato column to return t et f elds
 * @param federatedF eldsGroup Group to be used for St ch batch ng.
 *         T   s a funct on that takes a GroupOpt ons and returns a FederatedF eldGroup.
 *         Us ng a funct on that accepts a GroupOpt ons allows for St ch to handle a new group for d st nct GroupOpt ons.
 * @param setAdd  onalF elds Handler to set add  onal f elds on t ets.
 * @param stratoValueType Type to be returned by t  strato column.
 * @param tf eld T et thr ft f eld to be stored
 * @param pathNa  Path to be used  n t  strato catalog
 */
class FederatedF eldColumn(
  federatedF eldsGroup: FederatedF eldGroupBu lder.Type,
  setAdd  onalF elds: SetAdd  onalF eldsRequest => Future[Un ],
  stratoValueType: Type,
  tf eld: TF eld,
  pathOverr de: Opt on[Str ng] = None)
    extends StratoFed.Column(pathOverr de.getOrElse(FederatedF eldColumn.makeColumnPath(tf eld)))
    w h StratoFed.Fetch.St chW hContext
    w h StratoFed.Put.St ch {

  type Key = Long
  type V ew = Un 
  type Value = Val.T

  overr de val keyConv: Conv[Key] = Conv.ofType
  overr de val v ewConv: Conv[V ew] = Conv.ofType
  overr de val valueConv: Conv[Value] = Conv(stratoValueType,  dent y,  dent y)

  overr de val pol cy: Pol cy = AllowAll

  /*
   * A fetch that prox es GetT etF eldsColumn.fetch but only requests and
   * returns one spec f c f eld.
   */
  overr de def fetch(t et d: Key, v ew: V ew, opContext: OpContext): St ch[Result[Value]] = {

    val tw terUser d: Opt on[User d] = Access.getTw terUser d match {
      // Access.getTw terUser d should return a value w n request  s made on behalf of a user
      // and w ll not return a value ot rw se
      case So (tw terUser) => So (tw terUser. d)
      case None => None
    }

    val st chGroup = federatedF eldsGroup(GroupOpt ons(tw terUser d))

    St ch
      .call(FederatedF eldReq(t et d, tf eld. d), st chGroup).map {
        result: GetT etF eldsResult =>
          result.t etResult match {
            case Found(f) =>
              f.t et.getF eldBlob(tf eld. d) match {
                case So (v: TF eldBlob) =>
                  found(blobToVal(v))
                case None => m ss ng
              }
            case _ => m ss ng
          }
      }

  }

  /*
   * A strato put  nterface for wr  ng a s ngle add  onal f eld to a t et
   */
  overr de def put(t et d: Key, value: Val.T): St ch[Un ] = {
    val t et: T et = T et( d = t et d).setF eld(valToBlob(value))
    val request: SetAdd  onalF eldsRequest = SetAdd  onalF eldsRequest(t et)
    St ch.callFuture(setAdd  onalF elds(request))
  }

  val mval: Thr ft.Codec = MVal.codec(stratoValueType).thr ft(4)

  def valToBlob(value: Val.T): TF eldBlob =
    TF eldBlob(tf eld, mval.wr e[Buf](value, Thr ft.compactProto))

  def blobToVal(thr ftF eldBlob: TF eldBlob): Val.T =
    mval.read(thr ftF eldBlob.content, Thr ft.compactProto)

  overr de val contact nfo: Contact nfo = T etyp eContact nfo
  overr de val  tadata: Op tadata = Op tadata(
    l fecycle = So (Product on),
    descr pt on = So (Pla nText(s"A federated column for t  f eld t et.$stratoValueType"))
  )
}

object FederatedF eldColumn {
  val  dAllowl st: Seq[Short] = Seq(
    T et.CoreDataF eld. d,
    T et.LanguageF eld. d,
    T et.Conversat onMutedF eld. d
  )
  val  D_START = 157
  val  D_END = 32000

  pr vate val M grat onF elds: Seq[Short] = Seq(157)

  def  sFederatedF eld( d: Short) =  d >=  D_START &&  d <  D_END ||  dAllowl st.conta ns( d)

  def  sM grat onFederatedF eld(tF eld: TF eld): Boolean = M grat onF elds.conta ns(tF eld. d)

  /* federated f eld column strato conf gs must conform to t 
   * path na  sc   for t etyp e to p ck t m up
   */
  def makeColumnPath(tF eld: TF eld) = {
    val columnNa  = Str ngs.toCa lCase(tF eld.na .str pSuff x(" d"))
    s"t etyp e/f elds/${columnNa }.T et"
  }

  def makeV1ColumnPath(tF eld: TF eld): Str ng = {
    val columnNa  = Str ngs.toCa lCase(tF eld.na .str pSuff x(" d"))
    s"t etyp e/f elds/$columnNa -V1.T et"
  }
}
