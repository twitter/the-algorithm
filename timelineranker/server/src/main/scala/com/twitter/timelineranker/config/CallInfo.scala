package com.tw ter.t  l neranker.conf g

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ut l.Durat on
 mport java.ut l.concurrent.T  Un 

/**
 *  nformat on about a s ngle  thod call.
 *
 * T  purpose of t  class  s to allow one to express a call graph and latency assoc ated w h each (sub)call.
 * Once a call graph  s def ned, call ng getOverAllLatency() off t  top level call returns total t   taken by that call.
 * That value can t n be compared w h t  t  out budget allocated to that call to see  f t 
 * value f s w h n t  overall t  out budget of that call.
 *
 * T   s useful  n case of a complex call graph w re    s hard to  ntally est mate t  effect on
 * overall latency w n updat ng t  out value of one or more sub-calls.
 *
 * @param  thodNa  na  of t  called  thod.
 * @param latency P999 Latency  ncurred  n call ng a serv ce  f t   thod calls an external serv ce. Ot rw se 0.
 * @param dependsOn Ot r calls that t  call depends on.
 */
case class Call(
   thodNa : Str ng,
  latency: Durat on = 0.m ll seconds,
  dependsOn: Seq[Call] = N l) {

  /**
   * Latency  ncurred  n t  call as  ll as recurs vely all calls t  call depends on.
   */
  def getOverAllLatency: Durat on = {
    val dependencyLatency =  f (dependsOn. sEmpty) {
      0.m ll seconds
    } else {
      dependsOn.map(_.getOverAllLatency).max
    }
    latency + dependencyLatency
  }

  /**
   * Call paths start ng at t  call and recurs vely travers ng all dependenc es.
   * Typ cally used for debugg ng or logg ng.
   */
  def getLatencyPaths: Str ng = {
    val sb = new Str ngBu lder
    getLatencyPaths(sb, 1)
    sb.toStr ng
  }

  def getLatencyPaths(sb: Str ngBu lder, level:  nt): Un  = {
    sb.append(s"${getPref x(level)} ${getLatencyStr ng(getOverAllLatency)} $ thodNa \n")
     f ((latency > 0.m ll seconds) && !dependsOn. sEmpty) {
      sb.append(s"${getPref x(level + 1)} ${getLatencyStr ng(latency)} self\n")
    }
    dependsOn.foreach(_.getLatencyPaths(sb, level + 1))
  }

  pr vate def getLatencyStr ng(latencyValue: Durat on): Str ng = {
    val latencyMs = latencyValue. nUn (T  Un .M LL SECONDS)
    f"[$latencyMs%3d]"
  }

  pr vate def getPref x(level:  nt): Str ng = {
    " " * (level * 4) + "--"
  }
}

/**
 *  nformat on about t  getRecapT etCand dates call.
 *
 * Acronyms used:
 *     : call  nternal to TLR
 * EB  : Earlyb rd (search super root)
 * GZ  : G zmoduck
 * MH  : Manhattan
 * SGS : Soc al graph serv ce
 *
 * T  latency values are based on p9999 values observed over 1  ek.
 */
object GetRecycledT etCand datesCall {
  val getUserProf le nfo: Call = Call("GZ.getUserProf le nfo", 200.m ll seconds)
  val getUserLanguages: Call = Call("MH.getUserLanguages", 300.m ll seconds) // p99: 15ms

  val getFollow ng: Call = Call("SGS.getFollow ng", 250.m ll seconds) // p99: 75ms
  val getMutuallyFollow ng: Call =
    Call("SGS.getMutuallyFollow ng", 400.m ll seconds, Seq(getFollow ng)) // p99: 100
  val getV s b l yProf les: Call =
    Call("SGS.getV s b l yProf les", 400.m ll seconds, Seq(getFollow ng)) // p99: 100
  val getV s b l yData: Call = Call(
    "getV s b l yData",
    dependsOn = Seq(getFollow ng, getMutuallyFollow ng, getV s b l yProf les)
  )
  val getT etsForRecapRegular: Call =
    Call("EB.getT etsForRecap(regular)", 500.m ll seconds, Seq(getV s b l yData)) // p99: 250
  val getT etsForRecapProtected: Call =
    Call("EB.getT etsForRecap(protected)", 250.m ll seconds, Seq(getV s b l yData)) // p99: 150
  val getSearchResults: Call =
    Call("getSearchResults", dependsOn = Seq(getT etsForRecapRegular, getT etsForRecapProtected))
  val getT etsScoredForRecap: Call =
    Call("EB.getT etsScoredForRecap", 400.m ll seconds, Seq(getSearchResults)) // p99: 100

  val hydrateSearchResults: Call = Call("hydrateSearchResults")
  val getS ceT etSearchResults: Call =
    Call("getS ceT etSearchResults", dependsOn = Seq(getSearchResults))
  val hydrateT ets: Call =
    Call("hydrateT ets", dependsOn = Seq(getSearchResults, hydrateSearchResults))
  val hydrateS ceT ets: Call =
    Call("hydrateS ceT ets", dependsOn = Seq(getS ceT etSearchResults, hydrateSearchResults))
  val topLevel: Call = Call(
    "getRecapT etCand dates",
    dependsOn = Seq(
      getUserProf le nfo,
      getUserLanguages,
      getV s b l yData,
      getSearchResults,
      hydrateSearchResults,
      hydrateS ceT ets
    )
  )
}
