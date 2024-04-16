package com.tw ter.t  l neranker.conf g

 mport com.tw ter.app.Flags
 mport com.tw ter.f nagle.mtls.aut nt cat on.EmptyServ ce dent f er
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.t  l nes.conf g.CommonFlags
 mport com.tw ter.t  l nes.conf g.Conf gUt ls
 mport com.tw ter.t  l nes.conf g.Datacenter
 mport com.tw ter.t  l nes.conf g.Env
 mport com.tw ter.t  l nes.conf g.Prov desServ ce dent f er
 mport java.net. netSocketAddress
 mport com.tw ter.app.Flag

class T  l neRankerFlags(flag: Flags)
    extends CommonFlags(flag)
    w h Conf gUt ls
    w h Prov desServ ce dent f er {
  val dc: Flag[Str ng] = flag(
    "dc",
    "smf1",
    "Na  of data center  n wh ch t   nstance w ll execute"
  )
  val env ron nt: Flag[Str ng] = flag(
    "env ron nt",
    "devel",
    "T   sos env ron nt  n wh ch t   nstance w ll be runn ng"
  )
  val maxConcurrency: Flag[ nt] = flag(
    "maxConcurrency",
    200,
    "Max mum concurrent requests"
  )
  val serv cePort: Flag[ netSocketAddress] = flag(
    "serv ce.port",
    new  netSocketAddress(8287),
    "Port number that t  thr ft serv ce w ll l sten on"
  )
  val serv ceCompactPort: Flag[ netSocketAddress] = flag(
    "serv ce.compact.port",
    new  netSocketAddress(8288),
    "Port number that t  TCompactProtocol-based thr ft serv ce w ll l sten on"
  )

  val serv ce dent f er: Flag[Serv ce dent f er] = flag[Serv ce dent f er](
    "serv ce. dent f er",
    EmptyServ ce dent f er,
    "serv ce  dent f er for t  serv ce for use w h mutual TLS, " +
      "format  s expected to be -serv ce. dent f er=\"role:serv ce:env ron nt:zone\""
  )

  val opportun st cTlsLevel = flag[Str ng](
    "opportun st c.tls.level",
    "des red",
    "T  server's Opportun st cTls level."
  )

  val requestRateL m : Flag[Double] = flag[Double](
    "requestRateL m ",
    1000.0,
    "Request rate l m  to be used by t  cl ent request author zer"
  )

  val enableThr ftmuxCompress on = flag(
    "enableThr ftmuxServerCompress on",
    true,
    "bu ld server w h thr ftmux compress on enabled"
  )

  def getDatacenter: Datacenter.Value = getDC(dc())
  def getEnv: Env.Value = getEnv(env ron nt())
  overr de def getServ ce dent f er: Serv ce dent f er = serv ce dent f er()
}
