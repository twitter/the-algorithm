package com.tw ter.t etyp e.repos ory

 mport com.tw ter.conta ner.thr ftscala.Mater al zeAsT etF eldsRequest
 mport com.tw ter.conta ner.thr ftscala.Mater al zeAsT etRequest
 mport com.tw ter.conta ner.{thr ftscala => ccs}
 mport com.tw ter.st ch.SeqGroup
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.Return
 mport com.tw ter.t etyp e.{thr ftscala => tp}
 mport com.tw ter.t etyp e.backends
 mport com.tw ter.t etyp e.thr ftscala.GetT etF eldsResult
 mport com.tw ter.t etyp e.thr ftscala.GetT etResult
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Try

/**
 * A spec al k nd of t et  s that, w n [[tp.T et.underly ngCreat vesConta ner d]]  s presented.
 * t etyp e w ll delegate hydrat on of t  t et to creat ves conta ner serv ce.
 */
object Creat vesConta nerMater al zat onRepos ory {

  type GetT etType =
    (ccs.Mater al zeAsT etRequest, Opt on[tp.GetT etOpt ons]) => St ch[tp.GetT etResult]

  type GetT etF eldsType =
    (
      ccs.Mater al zeAsT etF eldsRequest,
      tp.GetT etF eldsOpt ons
    ) => St ch[tp.GetT etF eldsResult]

  def apply(
    mater al zeAsT et: backends.Creat vesConta nerServ ce.Mater al zeAsT et
  ): GetT etType = {
    case class RequestGroup(opts: Opt on[tp.GetT etOpt ons])
        extends SeqGroup[ccs.Mater al zeAsT etRequest, tp.GetT etResult] {
      overr de protected def run(
        keys: Seq[Mater al zeAsT etRequest]
      ): Future[Seq[Try[GetT etResult]]] =
        mater al zeAsT et(ccs.Mater al zeAsT etRequests(keys, opts)).map {
          res: Seq[GetT etResult] => res.map(Return(_))
        }
    }

    (request, opt ons) => St ch.call(request, RequestGroup(opt ons))
  }

  def mater al zeAsT etF elds(
    mater al zeAsT etF elds: backends.Creat vesConta nerServ ce.Mater al zeAsT etF elds
  ): GetT etF eldsType = {
    case class RequestGroup(opts: tp.GetT etF eldsOpt ons)
        extends SeqGroup[ccs.Mater al zeAsT etF eldsRequest, tp.GetT etF eldsResult] {
      overr de protected def run(
        keys: Seq[Mater al zeAsT etF eldsRequest]
      ): Future[Seq[Try[GetT etF eldsResult]]] =
        mater al zeAsT etF elds(ccs.Mater al zeAsT etF eldsRequests(keys, opts)).map {
          res: Seq[GetT etF eldsResult] => res.map(Return(_))
        }
    }

    (request, opt ons) => St ch.call(request, RequestGroup(opt ons))
  }
}
