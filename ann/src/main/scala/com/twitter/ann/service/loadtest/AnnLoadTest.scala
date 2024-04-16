package com.tw ter.ann.serv ce.loadtest

 mport com.tw ter.ann.common.Embedd ngType.Embedd ngVector
 mport com.tw ter.ann.common.{Appendable, D stance, Ent yEmbedd ng, Queryable, Runt  Params}
 mport com.tw ter.ut l.logg ng.Logger
 mport com.tw ter.ut l.{Durat on, Future}

class Ann ndexQueryLoadTest(
  worker: AnnLoadTestWorker = new AnnLoadTestWorker()) {
  lazy val logger = Logger(getClass.getNa )

  def performQuer es[T, P <: Runt  Params, D <: D stance[D]](
    queryable: Queryable[T, P, D],
    qps:  nt,
    durat on: Durat on,
    quer es: Seq[Query[T]],
    concurrencyLevel:  nt,
    runt  Conf gurat ons: Seq[QueryT  Conf gurat on[T, P]]
  ): Future[Un ] = {
    logger. nfo(s"Query set: ${quer es.s ze}")
    val res = Future.traverseSequent ally(runt  Conf gurat ons) { conf g =>
      logger. nfo(s"Run load test w h runt   conf g $conf g")
      worker.runW hQps(
        queryable,
        quer es,
        qps,
        durat on,
        conf g,
        concurrencyLevel
      )
    }
    res.onSuccess { _ =>
      logger. nfo(s"Done loadtest w h $qps for ${durat on. nM ll seconds / 1000} sec")
    }
    res.un 
  }
}

/**
 * @param embedd ng Embedd ng vector
 * @param trueNe ghb s L st of true ne ghb   ds. Empty  n case true ne ghb s dataset not ava lable
 * @tparam T Type of ne ghb 
 */
case class Query[T](embedd ng: Embedd ngVector, trueNe ghb s: Seq[T] = Seq.empty)

class Ann ndexBu ldLoadTest(
  bu ldRecorder: LoadTestBu ldRecorder,
  embedd ng ndexer: Embedd ng ndexer = new Embedd ng ndexer()) {
  lazy val logger = Logger(getClass.getNa )
  def  ndexEmbedd ngs[T, P <: Runt  Params, D <: D stance[D]](
    appendable: Appendable[T, P, D],
     ndexSet: Seq[Ent yEmbedd ng[T]],
    concurrencyLevel:  nt
  ): Future[Queryable[T, P, D]] = {
    logger. nfo(s" ndex set: ${ ndexSet.s ze}")
    val queryable = embedd ng ndexer
      . ndexEmbedd ngs(
        appendable,
        bu ldRecorder,
         ndexSet,
        concurrencyLevel
      ).onSuccess(_ => logger. nfo(s"Done  ndex ng.."))

    queryable
  }
}
