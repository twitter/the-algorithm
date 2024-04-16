package com.tw ter.t  l neranker.model

 mport com.tw ter.t  l neranker.{thr ftscala => thr ft}
 mport com.tw ter.ut l.Future

object Cand dateT etsResult {
  val Empty: Cand dateT etsResult = Cand dateT etsResult(N l, N l)
  val EmptyFuture: Future[Cand dateT etsResult] = Future.value(Empty)
  val EmptyCand dateT et: Seq[Cand dateT et] = Seq.empty[Cand dateT et]

  def fromThr ft(response: thr ft.GetCand dateT etsResponse): Cand dateT etsResult = {
    val cand dates = response.cand dates
      .map(_.map(Cand dateT et.fromThr ft))
      .getOrElse(EmptyCand dateT et)
    val s ceT ets = response.s ceT ets
      .map(_.map(Cand dateT et.fromThr ft))
      .getOrElse(EmptyCand dateT et)
     f (s ceT ets.nonEmpty) {
      requ re(cand dates.nonEmpty, "s ceT ets cannot have a value  f cand dates l st  s empty.")
    }
    Cand dateT etsResult(cand dates, s ceT ets)
  }
}

case class Cand dateT etsResult(
  cand dates: Seq[Cand dateT et],
  s ceT ets: Seq[Cand dateT et]) {

  def toThr ft: thr ft.GetCand dateT etsResponse = {
    val thr ftCand dates = cand dates.map(_.toThr ft)
    val thr ftS ceT ets = s ceT ets.map(_.toThr ft)
    thr ft.GetCand dateT etsResponse(
      cand dates = So (thr ftCand dates),
      s ceT ets = So (thr ftS ceT ets)
    )
  }
}
