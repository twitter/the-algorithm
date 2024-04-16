package com.tw ter.servo.cac 

 mport com.google.common.cac .{Cac Bu lder, RemovalL stener}
 mport com.tw ter.ut l.Durat on
 mport java.ut l.concurrent.T  Un 

object  nProcessCac  {

  /**
   * Apply a read f lter to exclude  ems  n an  nProcessCac 
   */
  def w hF lter[K, V](
    underly ng:  nProcessCac [K, V]
  )(
    shouldF lter: (K, V) => Boolean
  ):  nProcessCac [K, V] =
    new  nProcessCac [K, V] {
      def get(key: K): Opt on[V] = underly ng.get(key) f lterNot { shouldF lter(key, _) }
      def set(key: K, value: V) = underly ng.set(key, value)
    }
}

/**
 * An  n-process cac   nterface.    s d st nct from a map  n that:
 * 1) All  thods must be threadsafe
 * 2) A value set  n cac   s not guaranteed to rema n  n t  cac .
 */
tra   nProcessCac [K, V] {
  def get(key: K): Opt on[V]
  def set(key: K, value: V): Un 
}

/**
 *  n-process  mple ntat on of a cac  w h LRU semant cs and a TTL.
 */
class Exp r ngLru nProcessCac [K, V](
  ttl: Durat on,
  max mumS ze:  nt,
  removalL stener: Opt on[RemovalL stener[K, V]] = None: None.type)
    extends  nProcessCac [K, V] {

  pr vate[t ] val cac Bu lder =
    Cac Bu lder.newBu lder
      .as nstanceOf[Cac Bu lder[K, V]]
      .exp reAfterWr e(ttl. nM ll seconds, T  Un .M LL SECONDS)
      . n  alCapac y(max mumS ze)
      .max mumS ze(max mumS ze)

  pr vate[t ] val cac  =
    removalL stener match {
      case So (l stener) =>
        cac Bu lder
          .removalL stener(l stener)
          .bu ld[K, V]()
      case None =>
        cac Bu lder
          .bu ld[K, V]()
    }

  def get(key: K): Opt on[V] = Opt on(cac .get fPresent(key))

  def set(key: K, value: V): Un  = cac .put(key, value)
}
