package com.tw ter.v s b l y.models

 mport com.tw ter.context.Tw terContext
 mport com.tw ter.context.thr ftscala.V e r
 mport com.tw ter.featuresw c s.{UserAgent => FSUserAgent}
 mport com.tw ter.f natra.request.ut l.AddressUt ls

case class V e rContext(
  user d: Opt on[Long] = None,
  guest d: Opt on[Long] = None,
  userAgentStr: Opt on[Str ng] = None,
  cl entAppl cat on d: Opt on[Long] = None,
  aud  p: Str ng = "0.0.0.0",
  requestCountryCode: Opt on[Str ng] = None,
  requestLanguageCode: Opt on[Str ng] = None,
  dev ce d: Opt on[Str ng] = None,
   pTags: Set[Str ng] = Set.empty,
   sVer f edCrawler: Boolean = false,
  userRoles: Opt on[Set[Str ng]] = None) {
  val fsUserAgent: Opt on[FSUserAgent] = userAgentStr.flatMap(ua => FSUserAgent(userAgent = ua))

  val  sTwOff ce: Boolean =  pTags.conta ns(AddressUt ls.Twoff ce pTag)
}

object V e rContext {
  def fromContext: V e rContext = v e rContext.getOrElse(V e rContext())

  def fromContextW hV e r dFallback(v e r d: Opt on[Long]): V e rContext =
    v e rContext
      .map { v e r =>
         f (v e r.user d. sEmpty) {
          v e r.copy(user d = v e r d)
        } else {
          v e r
        }
      }.getOrElse(V e rContext(v e r d))

  pr vate def v e rContext: Opt on[V e rContext] =
    Tw terContext(Tw terContextPerm )().map(apply)

  def apply(v e r: V e r): V e rContext = new V e rContext(
    user d = v e r.user d,
    guest d = v e r.guest d,
    userAgentStr = v e r.userAgent,
    cl entAppl cat on d = v e r.cl entAppl cat on d,
    aud  p = v e r.aud  p.getOrElse("0.0.0.0"),
    requestCountryCode = v e r.requestCountryCode collect { case value => value.toLo rCase },
    requestLanguageCode = v e r.requestLanguageCode collect { case value => value.toLo rCase },
    dev ce d = v e r.dev ce d,
     pTags = v e r. pTags.toSet,
     sVer f edCrawler = v e r. sVer f edCrawler.getOrElse(false)
  )
}
