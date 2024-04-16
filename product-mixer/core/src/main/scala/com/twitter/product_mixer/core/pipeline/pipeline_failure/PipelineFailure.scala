package com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure

 mport com.fasterxml.jackson.datab nd.annotat on.JsonSer al ze
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f erStack
 mport scala.ut l.control.NoStackTrace

/**
 * P pel ne Fa lures represent p pel ne requests that  re not able to complete.
 *
 * A p pel ne result w ll always def ne e  r a result or a fa lure.
 *
 * T  reason f eld should not be d splayed to end-users, and  s free to change over t  .
 *   should always be free of pr vate user data such that   can log  .
 *
 * T  p pel ne can class fy  's own fa lures  nto categor es (t  outs,  nval d argu nts,
 * rate l m ed, etc) such that t  caller can choose how to handle  .
 *
 * @note [[componentStack]] should only be set by t  product m xer fra work,
 *         should **NOT** be set w n mak ng a [[P pel neFa lure]]
 */
@JsonSer al ze(us ng = classOf[P pel neFa lureSer al zer])
case class P pel neFa lure(
  category: P pel neFa lureCategory,
  reason: Str ng,
  underly ng: Opt on[Throwable] = None,
  componentStack: Opt on[Component dent f erStack] = None)
    extends Except on(
      "P pel neFa lure(" +
        s"category = $category, " +
        s"reason = $reason, " +
        s"underly ng = $underly ng, " +
        s"componentStack = $componentStack)",
      underly ng.orNull
    ) {
  overr de def toStr ng: Str ng = get ssage

  /** Returns an updated copy of t  [[P pel neFa lure]] w h t  sa  except on stacktrace */
  def copy(
    category: P pel neFa lureCategory = t .category,
    reason: Str ng = t .reason,
    underly ng: Opt on[Throwable] = t .underly ng,
    componentStack: Opt on[Component dent f erStack] = t .componentStack
  ): P pel neFa lure = {
    val newP pel neFa lure =
      new P pel neFa lure(category, reason, underly ng, componentStack) w h NoStackTrace
    newP pel neFa lure.setStackTrace(t .getStackTrace)
    newP pel neFa lure
  }
}
