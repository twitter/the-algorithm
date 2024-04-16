package com.tw ter.product_m xer.core.model.marshall ng.response.urt.react on

sealed abstract class T  l neReact onExecut on

case class  m d ateT  l neReact on(key: Str ng) extends T  l neReact onExecut on

case class RemoteT  l neReact on(
  requestParams: Map[Str ng, Str ng],
  t  out nSeconds: Opt on[Short])
    extends T  l neReact onExecut on
