package com.tw ter.s mclustersann.f lters

 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.Serv ce
 mport com.tw ter.f nagle.S mpleF lter
 mport com.tw ter.relevance_platform.s mclustersann.mult cluster.Serv ceNa Mapper
 mport com.tw ter.scrooge.Request
 mport com.tw ter.scrooge.Response
 mport com.tw ter.s mclustersann.except ons. nval dRequestForS mClustersAnnVar antExcept on
 mport com.tw ter.s mclustersann.thr ftscala.S mClustersANNServ ce
 mport com.tw ter.ut l.Future
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class S mClustersAnnVar antF lter @ nject() (
  serv ceNa Mapper: Serv ceNa Mapper,
  serv ce dent f er: Serv ce dent f er,
) extends S mpleF lter[Request[S mClustersANNServ ce.GetT etCand dates.Args], Response[
      S mClustersANNServ ce.GetT etCand dates.SuccessType
    ]] {
  overr de def apply(
    request: Request[S mClustersANNServ ce.GetT etCand dates.Args],
    serv ce: Serv ce[Request[S mClustersANNServ ce.GetT etCand dates.Args], Response[
      S mClustersANNServ ce.GetT etCand dates.SuccessType
    ]]
  ): Future[Response[S mClustersANNServ ce.GetT etCand dates.SuccessType]] = {

    val dateRequest(request)
    serv ce(request)
  }

  pr vate def val dateRequest(
    request: Request[S mClustersANNServ ce.GetT etCand dates.Args]
  ): Un  = {
    val modelVers on = request.args.query.s ceEmbedd ng d.modelVers on
    val embedd ngType = request.args.query.conf g.cand dateEmbedd ngType

    val actualServ ceNa  = serv ce dent f er.serv ce

    val expectedServ ceNa  = serv ceNa Mapper.getServ ceNa (modelVers on, embedd ngType)

    expectedServ ceNa  match {
      case So (na )  f na  == actualServ ceNa  => ()
      case _ =>
        throw  nval dRequestForS mClustersAnnVar antExcept on(
          modelVers on,
          embedd ngType,
          actualServ ceNa ,
          expectedServ ceNa )
    }
  }
}
