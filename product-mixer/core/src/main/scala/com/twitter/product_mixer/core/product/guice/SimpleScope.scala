package com.tw ter.product_m xer.core.product.gu ce

 mport com.google. nject.Key
 mport com.google. nject.OutOfScopeExcept on
 mport com.google. nject.Prov der
 mport com.google. nject.Scope
 mport com.google. nject.Scopes
 mport com.tw ter.ut l.Local
 mport scala.collect on.concurrent
 mport scala.collect on.mutable

/**
 * A scala-esque  mple ntat on of S mpleScope: https://g hub.com/google/gu ce/w k /CustomScopes# mple nt ng-scope
 *
 * Scopes t  execut on of a s ngle block of code v a `let`
 */
class S mpleScope extends Scope {

  pr vate val values = new Local[concurrent.Map[Key[_], Any]]()

  /**
   * Execute a block w h a fresh scope.
   *
   *   can opt onally supply a map of  n  alObjects to 'seed' t  new scope.
   */
  def let[T]( n  alObjects: Map[Key[_], Any] = Map.empty)(f: => T): T = {
    val newMap: concurrent.Map[Key[_], Any] = concurrent.Tr eMap.empty

     n  alObjects.foreach { case (key, value) => newMap.put(key, value) }

    values.let(newMap)(f)
  }

  overr de def scope[T](
    key: Key[T],
    unscoped: Prov der[T]
  ): Prov der[T] = () => {
    val scopedObjects: mutable.Map[Key[T], Any] = getScopedObjectMap(key)

    scopedObjects
      .get(key).map(_.as nstanceOf[T]).getOrElse {
        val objectFromUnscoped: T = unscoped.get()

         f (Scopes. sC rcularProxy(objectFromUnscoped)) {
          objectFromUnscoped // Don't re mber prox es
        } else {
          scopedObjects.put(key, objectFromUnscoped)
          objectFromUnscoped
        }
      }
  }

  def getScopedObjectMap[T](key: Key[T]): concurrent.Map[Key[T], Any] = {
    values()
      .getOrElse(
        throw new OutOfScopeExcept on(s"Cannot access $key outs de of a scop ng block")
      ).as nstanceOf[concurrent.Map[Key[T], Any]]
  }
}

object S mpleScope {

  val SEEDED_KEY_PROV DER: Prov der[Noth ng] = () =>
    throw new  llegalStateExcept on(
      """ f   got  re t n    ans that y  code asked for scoped object wh ch should have
      | been expl c ly seeded  n t  scope by call ng S mpleScope.seed(),
      | but was not.""".str pMarg n)
}
