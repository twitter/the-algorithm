package com.tw ter.t  l neranker.para ters.ut l

 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.t  l nes.conf gap .BaseRequestContext
 mport com.tw ter.t  l nes.conf gap .W hExper  ntContext
 mport com.tw ter.t  l nes.conf gap .W hFeatureContext
 mport com.tw ter.t  l nes.conf gap .W hUser d
 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.t  l neserv ce.Dev ceContext
 mport com.tw ter.t  l neserv ce.model.RequestContextFactory
 mport com.tw ter.ut l.Future

tra  CommonRequestContext
    extends BaseRequestContext
    w h W hExper  ntContext
    w h W hUser d
    w h W hFeatureContext

tra  RequestContextBu lder {
  def apply(
    rec p entUser d: Opt on[User d],
    dev ceContext: Opt on[Dev ceContext]
  ): Future[CommonRequestContext]
}

class RequestContextBu lder mpl(requestContextFactory: RequestContextFactory)
    extends RequestContextBu lder {
  overr de def apply(
    rec p entUser d: Opt on[User d],
    dev ceContextOpt: Opt on[Dev ceContext]
  ): Future[CommonRequestContext] = {
    val requestContextFut = requestContextFactory(
      contextualUser dOpt = rec p entUser d,
      dev ceContext = dev ceContextOpt.getOrElse(Dev ceContext.empty),
      exper  ntConf gurat onOpt = None,
      requestLogOpt = None,
      contextualUserContext = None,
      useRolesCac  = Gate.True,
      t  l ne d = None
    )

    requestContextFut.map { requestContext =>
      new CommonRequestContext {
        overr de val user d = rec p entUser d
        overr de val exper  ntContext = requestContext.exper  ntContext
        overr de val featureContext = requestContext.featureContext
      }
    }
  }
}
