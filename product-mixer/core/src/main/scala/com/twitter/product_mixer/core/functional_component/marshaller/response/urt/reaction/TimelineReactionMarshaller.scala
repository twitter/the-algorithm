package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.react on

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.react on. m d ateT  l neReact on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.react on.RemoteT  l neReact on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.react on.T  l neReact on
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T  l neReact onMarshaller @ nject() () {
  def apply(t  l neReact on: T  l neReact on): urt.T  l neReact on = {
    val execut on = t  l neReact on.execut on match {
      case  m d ateT  l neReact on(key) =>
        urt.T  l neReact onExecut on. m d ate(urt. m d ateT  l neReact on(key))
      case RemoteT  l neReact on(requestParams, t  out nSeconds) =>
        urt.T  l neReact onExecut on.Remote(
          urt.RemoteT  l neReact on(
            requestParams,
            t  out nSeconds
          ))
    }
    urt.T  l neReact on(
      execut on = execut on,
      maxExecut onCount = t  l neReact on.maxExecut onCount
    )
  }
}
