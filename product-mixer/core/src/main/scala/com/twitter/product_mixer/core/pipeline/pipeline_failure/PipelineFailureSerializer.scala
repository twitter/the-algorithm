package com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure

 mport com.fasterxml.jackson.core.JsonGenerator
 mport com.fasterxml.jackson.datab nd.JsonSer al zer
 mport com.fasterxml.jackson.datab nd.Ser al zerProv der
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f erStack

pr vate[p pel ne_fa lure] class P pel neFa lureSer al zer()
    extends JsonSer al zer[P pel neFa lure] {

  pr vate sealed tra  BaseSer al zableExcept on

  pr vate case class Ser al zableExcept on(
    `class`: Str ng,
     ssage: Str ng,
    stackTrace: Seq[Str ng],
    cause: Opt on[BaseSer al zableExcept on])
      extends BaseSer al zableExcept on

  pr vate case class Ser al zableP pel neFa lure(
    category: Str ng,
    reason: Str ng,
    underly ng: Opt on[BaseSer al zableExcept on],
    componentStack: Opt on[Component dent f erStack],
    stackTrace: Seq[Str ng])
      extends BaseSer al zableExcept on

  pr vate def ser al zeStackTrace(stackTrace: Array[StackTraceEle nt]): Seq[Str ng] =
    stackTrace.map(stackTraceEle nt => "at " + stackTraceEle nt.toStr ng)

  pr vate def mkSer al zableExcept on(
    t: Throwable,
    recurs onDepth:  nt = 0
  ): Opt on[BaseSer al zableExcept on] = {
    t match {
      case _  f recurs onDepth > 4 =>
        //  n t  unfortunate case of a super deep cha n of except ons, stop  f   get too deep
        None
      case p pel neFa lure: P pel neFa lure =>
        So (
          Ser al zableP pel neFa lure(
            category =
              p pel neFa lure.category.categoryNa  + "/" + p pel neFa lure.category.fa lureNa ,
            reason = p pel neFa lure.reason,
            underly ng =
              p pel neFa lure.underly ng.flatMap(mkSer al zableExcept on(_, recurs onDepth + 1)),
            componentStack = p pel neFa lure.componentStack,
            stackTrace = ser al zeStackTrace(p pel neFa lure.getStackTrace)
          ))
      case t =>
        So (
          Ser al zableExcept on(
            `class` = t.getClass.getNa ,
             ssage = t.get ssage,
            stackTrace = ser al zeStackTrace(t.getStackTrace),
            cause = Opt on(t.getCause).flatMap(mkSer al zableExcept on(_, recurs onDepth + 1))
          )
        )
    }
  }

  overr de def ser al ze(
    p pel neFa lure: P pel neFa lure,
    gen: JsonGenerator,
    ser al zers: Ser al zerProv der
  ): Un  = ser al zers.defaultSer al zeValue(mkSer al zableExcept on(p pel neFa lure), gen)
}
