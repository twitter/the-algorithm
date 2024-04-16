package com.tw ter.product_m xer.component_l brary.module.http

 mport com.google. nject.Prov des
 mport com.tw ter.f nagle.http.ProxyCredent als
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.ut l.secur y.{Credent als => Credent alsUt l}
 mport java. o.F le
 mport javax. nject.S ngleton

object ProxyCredent alsModule extends Tw terModule {
  f nal val HttpCl entW hProxyCredent alsPath = "http_cl ent.proxy.proxy_credent als_path"

  flag[Str ng](HttpCl entW hProxyCredent alsPath, "", "Path t  load t  proxy credent als")

  @Prov des
  @S ngleton
  def prov desProxyCredent als(
    @Flag(HttpCl entW hProxyCredent alsPath) proxyCredent alsPath: Str ng,
  ): ProxyCredent als = {
    val credent alsF le = new F le(proxyCredent alsPath)
    ProxyCredent als(Credent alsUt l(credent alsF le))
      .getOrElse(throw M ss ngProxyCredent alsExcept on)
  }

  object M ss ngProxyCredent alsExcept on extends Except on("Proxy Credent als not found")
}
