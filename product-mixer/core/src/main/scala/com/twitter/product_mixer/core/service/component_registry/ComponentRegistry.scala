package com.tw ter.product_m xer.core.serv ce.component_reg stry

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.model.common.Component
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f erStack
 mport com.tw ter.ut l.Act v y
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Try
 mport com.tw ter.ut l.logg ng.Logg ng
 mport java.ut l.concurrent.ConcurrentHashMap
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport scala.collect on.JavaConverters._

/**
 * T  [[ComponentReg stry]] works closely w h [[Component dent f er]]s and t  [[com.tw ter.product_m xer.core.product.reg stry.ProductP pel neReg stry]]
 * to prov de t  Product M xer fra work  nformat on about t  [[com.tw ter.product_m xer.core.p pel ne.P pel ne]]s and [[Component]]s
 * that make up an appl cat on.
 *
 * T  reg strat on allows us to conf gure alerts and dashboards,
 * to query y  appl cat on structure lett ng us d splay t  graph of t  execut on and t  results of quer es,
 * and to garner  ns ght  nto usages.
 *
 * T  reg stry  s a snapshot of t  state of t  world w n p pel nes  re last bu lt successfully.
 * For most serv ces, t  only happens once on startup. Ho ver, so  serv ces may rebu ld t  r
 * p pel nes dynam cally later on.
 */

@S ngleton
class ComponentReg stry @ nject() (statsRece ver: StatsRece ver) {
  //  n  ally pend ng unt l t  f rst snapshot  s bu lt by [[ProductP pel neReg stry]]
  pr vate val (snapshotAct v y, snapshotW ness) = Act v y[ComponentReg strySnapshot]()
  pr vate val snapshotCount = statsRece ver.counter("ComponentReg stry", "SnapshotCount")

  def get: Future[ComponentReg strySnapshot] = snapshotAct v y.values.toFuture.lo rFromTry
  pr vate[core] def set(snapshot: ComponentReg strySnapshot): Un  = {
    snapshotCount. ncr()
    snapshotW ness.not fy(Try(snapshot))
  }
}

class ComponentReg strySnapshot() extends Logg ng {

  /** for stor ng t  [[Reg steredComponent]]s */
  pr vate[t ] val componentReg stry =
    new ConcurrentHashMap[Component dent f er, Reg steredComponent]

  /** for determ n ng t  ch ldren of a [[Component dent f er]] */
  pr vate[t ] val componentCh ldren =
    new ConcurrentHashMap[Component dent f er, Set[Component dent f er]]

  /** for determ n ng [[Component dent f er]] un queness w h n a g ven [[Component dent f erStack]] */
  pr vate[t ] val componentH erarchy =
    new ConcurrentHashMap[Component dent f erStack, Set[Component dent f er]]

  /**
   * Reg ster t  g ven [[Component]] at t  end of path prov ded by `parent dent f erStack`
   * or throws an except on  f add ng t  component results  n an  nval d conf gurat on.
   *
   * @throws Ch ldComponentColl s onExcept on  f a [[Component]] w h t  sa  [[Component dent f er]]  s reg stered
   *                                          more than once under t  sa  parent.
   *                                          e.g.  f   reg ster `ComponentA` under `ProductA -> P pel neA` tw ce,
   *                                          t  except on w ll be thrown w n reg ster ng `ComponentA` t  second
   *                                          t  . T   s pretty much always a conf gurat on error due to copy-past ng
   *                                          and forgett ng to update t   dent f er, or acc dentally us ng t  sa 
   *                                          component tw ce under t  sa  parent.  f t  d dn't throw, stats from
   *                                          t se 2 components would be  nd st ngu shable.
   *
   * @throws Component dent f erColl s onExcept on  f a [[Component]] w h t  sa  [[Component dent f er]]  s reg stered
   *                                               but  's type  s not t  sa  as a prev ously reg stered [[Component]]
   *                                               w h t  sa  [[Component dent f er]]
   *                                               e.g.  f   reg ster 2 [[Component]]s w h t  sa  [[Component dent f er]]
   *                                               such as `new Component` and an  nstance of
   *                                               `class  Component extends Component` t  `new Component` w ll have a
   *                                               type of `Component` and t  ot r one w ll have a type of ` Component`
   *                                               wh ch w ll throw. T   s usually due to copy-past ng a component as
   *                                               a start ng po nt and forgett ng to update t   dent f er.  f t 
   *                                               d dn't throw, absolute stats from t se 2 components would be
   *                                                nd st ngu shable.
   *
   *
   * @note t  w ll log deta ls of component  dent f er reuse  f t  underl ng components are not equal, but ot rw se are of t  sa  class.
   *       T  r stats w ll be  rged and  nd st ngu shable but s nce t y are t  sa  na  and sa  class,   assu  t  d fferences are
   *       m nor enough that t   s okay, but make a note  n t  log at startup  n case so one sees unexpected  tr cs,   can look
   *       back at t  logs and see t  deta ls.
   *
   * @param component t  component to reg ster
   * @param parent dent f erStack t  complete [[Component dent f erStack]] exclud ng t  current [[Component]]'s [[Component dent f er]]
   */
  def reg ster(
    component: Component,
    parent dent f erStack: Component dent f erStack
  ): Un  = synchron zed {
    val  dent f er = component. dent f er
    val parent dent f er = parent dent f erStack.peek

    val reg steredComponent =
      Reg steredComponent( dent f er, component, component. dent f er.f le.value)

    componentReg stry.asScala
      .get( dent f er)
      .f lter(_.component != component) // only do t  foreach  f t  components aren't equal
      .foreach {
        case ex st ngComponent  f ex st ngComponent.component.getClass != component.getClass =>
          /**
           * T  sa  component may be reg stered under d fferent parent components.
           * Ho ver, d fferent component types cannot use t  sa  component  dent f er.
           *
           * T  catc s so  copy-past ng of a conf g or component and forgett ng to update t   dent f er.
           */
          throw new Component dent f erColl s onExcept on(
            component dent f er =  dent f er,
            component = reg steredComponent,
            ex st ngComponent = componentReg stry.get( dent f er),
            parent dent f erStack = parent dent f erStack,
            ex st ng dent f erStack = componentH erarchy.search[Component dent f erStack](
              1,
              (stack,  dent f ers) =>  f ( dent f ers.conta ns( dent f er)) stack else null)
          )
        case ex st ngComponent =>
          /**
           * T  sa  component may be reg stered under d fferent parent components.
           * Ho ver,  f t  components are not equal   __may be__ a conf gurat on error
           * so   log a deta led descr pt on of t   ssue  n case t y need to debug.
           *
           * T  warns custo rs of so  copy-past ng of a conf g or component and forgett ng to update t 
           *  dent f er and of reus ng components w h hard-coded values wh ch are conf gured d fferently.
           */
          val ex st ng dent f erStack = componentH erarchy.search[Component dent f erStack](
            1,
            (stack,  dent f ers) =>  f ( dent f ers.conta ns( dent f er)) stack else null)
          logger. nfo(
            s"Found dupl cate  dent f ers for non-equal components, $ dent f er from ${reg steredComponent.s ceF le} " +
              s"under ${parent dent f erStack.component dent f ers.reverse.mkStr ng(" -> ")} " +
              s"was already def ned and  s unequal to ${ex st ngComponent.s ceF le} " +
              s"under ${ex st ng dent f erStack.component dent f ers.reverse.mkStr ng(" -> ")}. " +
              s" rg ng t se components  n t  reg stry, t  w ll result  n t  r  tr cs be ng  rged. " +
              s" f t se components should have separate  tr cs, cons der prov d ng un que  dent f ers for t m  nstead."
          )
      }

    /** T  sa  component may not be reg stered mult ple t  s under t  sa  parent */
     f (componentH erarchy.getOrDefault(parent dent f erStack, Set.empty).conta ns( dent f er))
      throw new Ch ldComponentColl s onExcept on( dent f er, parent dent f erStack)

    // add component to reg stry
    componentReg stry.put fAbsent( dent f er, reg steredComponent)
    // add component to parent's `ch ldren` set for easy lookup
    componentCh ldren. rge(parent dent f er, Set( dent f er), _ ++ _)
    // add t  component to t  h erarchy under  's parent's  dent f er stack
    componentH erarchy. rge(parent dent f erStack, Set( dent f er), _ ++ _)
  }

  def getAllReg steredComponents: Seq[Reg steredComponent] =
    componentReg stry.values.asScala.toSeq.sorted

  def getCh ldComponents(component: Component dent f er): Seq[Component dent f er] =
    Opt on(componentCh ldren.get(component)) match {
      case So (components) => components.toSeq.sorted(Component dent f er.order ng)
      case None => Seq.empty
    }
}

class Component dent f erColl s onExcept on(
  component dent f er: Component dent f er,
  component: Reg steredComponent,
  ex st ngComponent: Reg steredComponent,
  parent dent f erStack: Component dent f erStack,
  ex st ng dent f erStack: Component dent f erStack)
    extends  llegalArgu ntExcept on(
      s"Tr ed to reg ster component $component dent f er: of type ${component.component.getClass} from ${component.s ceF le} " +
        s"under ${parent dent f erStack.component dent f ers.reverse.mkStr ng(" -> ")} " +
        s"but   was already def ned w h a d fferent type ${ex st ngComponent.component.getClass} from ${ex st ngComponent.s ceF le} " +
        s"under ${ex st ng dent f erStack.component dent f ers.reverse.mkStr ng(" -> ")}. " +
        s"Ensure   aren't reus ng a component  dent f er wh ch can happen w n copy-past ng ex st ng component code by acc dent")

class Ch ldComponentColl s onExcept on(
  component dent f er: Component dent f er,
  parent dent f erStack: Component dent f erStack)
    extends  llegalArgu ntExcept on(
      s"Component $component dent f er already def ned under parent component $parent dent f erStack")
