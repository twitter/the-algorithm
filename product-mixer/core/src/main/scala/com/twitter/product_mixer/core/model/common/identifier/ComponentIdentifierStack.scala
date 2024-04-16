package com.tw ter.product_m xer.core.model.common. dent f er

 mport com.fasterxml.jackson.datab nd.annotat on.JsonSer al ze

/**
 * A non-empty  mmutable stack of [[Component dent f er]]s
 *
 * [[Component dent f erStack]] does not support remov ng [[Component dent f er]]s,
 *  nstead a [[Component dent f erStack]] should be used by add ng new [[Component dent f er]]s
 * as process ng enters a g ven `Component`, t n d scarded after.
 * Th nk of t  as s m lar to a let-scoped var able, w re t  let-scope  s t  g ven component.
 */
@JsonSer al ze(us ng = classOf[Component dent f erStackSer al zer])
class Component dent f erStack pr vate (val component dent f ers: L st[Component dent f er]) {

  /** Make a new [[Component dent f erStack]] w h t  [[Component dent f er]] added at t  top */
  def push(newComponent dent f er: Component dent f er): Component dent f erStack =
    new Component dent f erStack(newComponent dent f er :: component dent f ers)

  /** Make a new [[Component dent f erStack]] w h t  [[Component dent f er]]s added at t  top */
  def push(newComponent dent f ers: Component dent f erStack): Component dent f erStack =
    new Component dent f erStack(
      newComponent dent f ers.component dent f ers ::: component dent f ers)

  /** Make a new [[Component dent f erStack]] w h t  [[Component dent f er]]s added at t  top */
  def push(newComponent dent f ers: Opt on[Component dent f erStack]): Component dent f erStack = {
    newComponent dent f ers match {
      case So (newComponent dent f ers) => push(newComponent dent f ers)
      case None => t 
    }
  }

  /** Return t  top ele nt of t  [[Component dent f erStack]] */
  val peek: Component dent f er = component dent f ers. ad

  /** Return t  s ze of t  [[Component dent f erStack]] */
  def s ze:  nt = component dent f ers.length

  overr de def toStr ng: Str ng =
    s"Component dent f erStack(component dent f ers = $component dent f ers)"

  overr de def equals(obj: Any): Boolean = {
    obj match {
      case component dent f erStack: Component dent f erStack
           f component dent f erStack.eq(t ) ||
            component dent f erStack.component dent f ers == component dent f ers =>
        true
      case _ => false
    }
  }
}

object Component dent f erStack {

  /**
   * Returns a [[Component dent f erStack]] from t  g ven [[Component dent f er]]s,
   * w re t  top of t  stack  s t  left-most [[Component dent f er]]
   */
  def apply(
    component dent f er: Component dent f er,
    component dent f erStack: Component dent f er*
  ) =
    new Component dent f erStack(component dent f er :: component dent f erStack.toL st)
}
