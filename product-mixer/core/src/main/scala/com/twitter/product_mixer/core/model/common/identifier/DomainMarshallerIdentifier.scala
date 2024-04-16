package com.tw ter.product_m xer.core.model.common. dent f er

/**
 * Doma n Marshaller  dent f er
 *
 * @note T  class should always rema n effect vely `f nal`.  f for any reason t  `sealed`
 *       mod f er  s removed, t  equals()  mple ntat on must be updated  n order to handle class
 *        n r or equal y (see note on t  equals  thod below)
 */
sealed abstract class Doma nMarshaller dent f er(overr de val na : Str ng)
    extends Component dent f er("Doma nMarshaller", na ) {

  /**
   * @ n r doc
   */
  overr de def canEqual(that: Any): Boolean = that. s nstanceOf[Doma nMarshaller dent f er]

  /**
   * H gh performance  mple ntat on of equals  thod that leverages:
   *  - Referent al equal y short c rcu 
   *  - Cac d hashcode equal y short c rcu 
   *  - F eld values are only c cked  f t  hashCodes are equal to handle t  unl kely case
   *    of a hashCode coll s on
   *  - Removal of c ck for `that` be ng an equals-compat ble descendant s nce t  class  s f nal
   *
   * @note `cand date.canEqual(t )`  s not necessary because t  class  s f nal
   * @see [[http://www.art ma.com/p ns1ed/object-equal y.html Programm ng  n Scala,
   *      Chapter 28]] for d scuss on and des gn.
   */
  overr de def equals(that: Any): Boolean =
    that match {
      case  dent f er: Doma nMarshaller dent f er =>
        // Note  dent f er.canEqual(t )  s not necessary because t  class  s effect vely f nal
        ((t  eq  dent f er)
          || ((hashCode ==  dent f er.hashCode) && ((componentType ==  dent f er.componentType) && (na  ==  dent f er.na ))))
      case _ =>
        false
    }

  /**
   * Leverage doma n-spec f c constra nts (see notes below) to safely construct and cac  t 
   * hashCode as a val, such that    s  nstant ated once on object construct on. T  prevents t 
   * need to recompute t  hashCode on each hashCode()  nvocat on, wh ch  s t  behav or of t 
   * Scala comp ler case class-generated hashCode() s nce   cannot make assumpt ons regard ng f eld
   * object mutab l y and hashCode  mple ntat ons.
   *
   * @note Cach ng t  hashCode  s only safe  f all of t  f elds used to construct t  hashCode
   *       are  mmutable. T   ncludes:
   *       -  nab l y to mutate t  object reference on for an ex st ng  nstant ated  dent f er
   *       ( .e. each f eld  s a val)
   *       -  nab l y to mutate t  f eld object  nstance  self ( .e. each f eld  s an  mmutable
   *       -  nab l y to mutate t  f eld object  nstance  self ( .e. each f eld  s an  mmutable
   *       data structure), assum ng stable hashCode  mple ntat ons for t se objects
   *
   * @note  n order for t  hashCode to be cons stent w h object equal y, `##` must be used for
   *       boxed nu r c types and null. As such, always prefer `.##` over `.hashCode()`.
   */
  overr de val hashCode:  nt = 31 * componentType.## + na .##
}

object Doma nMarshaller dent f er {
  def apply(na : Str ng)( mpl c  s ceF le: s cecode.F le): Doma nMarshaller dent f er = {
     f (Component dent f er. sVal dNa (na ))
      new Doma nMarshaller dent f er(na ) {
        overr de val f le: s cecode.F le = s ceF le
      }
    else
      throw new  llegalArgu ntExcept on(s" llegal Doma nMarshaller dent f er: $na ")
  }
}
