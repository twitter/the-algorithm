package com.tw ter.t etyp e
package serv ce

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.ut l.Awa 
 mport scala.ut l.control.NonFatal

/**
 * Sett ngs for t  art f c al t et fetch ng requests that are sent to warmup t 
 * server before aut nt c requests are processed.
 */
case class WarmupQuer esSett ngs(
  realT etRequestCycles:  nt = 100,
  requestT  out: Durat on = 3.seconds,
  cl ent d: Cl ent d = Cl ent d("t etyp e.warmup"),
  requestT  Range: Durat on = 10.m nutes,
  maxConcurrency:  nt = 20)

object T etServ ceWar r {

  /**
   * Load  nfo from perspect ve of TLS test account w h short favor es t  l ne.
   */
  val ForUser d = 3511687034L // @m kestltestact1
}

/**
 * Generates requests to getT ets for t  purpose of warm ng up t  code paths used
 *  n fetch ng t ets.
 */
class T etServ ceWar r(
  warmupSett ngs: WarmupQuer esSett ngs,
  requestOpt ons: GetT etOpt ons = GetT etOpt ons( ncludePlaces = true,
     ncludeRet etCount = true,  ncludeReplyCount = true,  ncludeFavor eCount = true,
     ncludeCards = true, cardsPlatformKey = So (" Phone-13"),  ncludePerspect vals = true,
     ncludeQuotedT et = true, forUser d = So (T etServ ceWar r.ForUser d)))
    extends (Thr ftT etServ ce => Un ) {
   mport warmupSett ngs._

  pr vate val realT et ds =
    Seq(
      20L, // just sett ng up   twttr
      456190426412617728L, // protected user t et
      455477977715707904L, // suspended user t et
      440322224407314432L, // ellen oscar self e
      372173241290612736L, // gaga  nt ons 1d
      456965485179838464L, //  d a tagged t et
      525421442918121473L, // t et w h card
      527214829807759360L, // t et w h annotat on
      472788687571677184L // t et w h quote t et
    )

  pr vate val log = Logger(getClass)

  /**
   * Executes t  warmup quer es, wa  ng for t m to complete or unt l
   * t  warmupT  out occurs.
   */
  def apply(serv ce: Thr ftT etServ ce): Un  = {
    val warmupStart = T  .now
    log. nfo("warm ng up...")
    warmup(serv ce)
    val warmupDurat on = T  .now.s nce(warmupStart)
    log. nfo("warmup took " + warmupDurat on)
  }

  /**
   * Executes t  warmup quer es, return ng w n all responses have completed or t  d-out.
   */
  pr vate[t ] def warmup(serv ce: Thr ftT etServ ce): Un  =
    cl ent d.asCurrent {
      val request = GetT etsRequest(realT et ds, opt ons = So (requestOpt ons))
      val requests = Seq.f ll(realT etRequestCycles)(request)
      val requestGroups = requests.grouped(maxConcurrency)

      for (requests <- requestGroups) {
        val responses = requests.map(serv ce.getT ets(_))
        try {
          Awa .ready(Future.jo n(responses), requestT  out)
        } catch {
          // Awa .ready throws except ons on t  outs and
          //  nterrupt ons. T  prevents those except ons from
          // bubbl ng up.
          case NonFatal(_) =>
        }
      }
    }
}
