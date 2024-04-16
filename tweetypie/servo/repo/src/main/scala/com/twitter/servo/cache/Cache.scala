package com.tw ter.servo.cac 

 mport com.google.common.cac .Cac Bu lder
 mport com.tw ter.f nagle. mcac d.ut l.NotFound
 mport com.tw ter.servo.ut l.ThreadLocalStr ngBu lder
 mport com.tw ter.ut l.{Durat on, Future, Return}
 mport java.ut l.concurrent.T  Un 
 mport scala.collect on.mutable
 mport scala.collect on.JavaConverters._

/**
 * opaque tra  used for getW hC cksum calls.
 * t   mple ntat on should be pr vate to t  cac ,
 * to  nh b  peek ng
 */
tra  C cksum extends Any

object ScopedCac Key {
  pr vate[ScopedCac Key] val bu lder = new ThreadLocalStr ngBu lder(64)
}

/**
 * base class for cac  keys need ng scop ng
 *
 * @param globalNa space
 *  t  project-level na space
 * @param cac Na space
 *  t  cac -level na space
 * @param vers on
 *  t  vers on of ser al zat on for values
 * @param scopes
 *  add  onal key scopes
 */
abstract class ScopedCac Key(
  globalNa space: Str ng,
  cac Na space: Str ng,
  vers on:  nt,
  scopes: Str ng*) {
   mport constants._

  overr de lazy val toStr ng = {
    val bu lder = ScopedCac Key
      .bu lder()
      .append(globalNa space)
      .append(Colon)
      .append(cac Na space)
      .append(Colon)
      .append(vers on)

    scopes foreach {
      bu lder.append(Colon).append(_)
    }

    bu lder.toStr ng
  }
}

/**
 * Shared tra  for read ng from a cac 
 */
tra  ReadCac [K, V] {
  def get(keys: Seq[K]): Future[KeyValueResult[K, V]]

  /**
   * get t  value w h an opaque c cksum that can be passed  n
   * a c ckAndSet operat on.  f t re  s a deser al zat on error,
   * t  c cksum  s st ll returned
   */
  def getW hC cksum(keys: Seq[K]): Future[CsKeyValueResult[K, V]]

  /**
   * release any underly ng res ces
   */
  def release(): Un 
}

/**
 * allows one ReadCac  to wrap anot r
 */
tra  ReadCac Wrapper[K, V, T  <: ReadCac [K, V]] extends ReadCac [K, V] {
  def underly ngCac : T 

  overr de def get(keys: Seq[K]) = underly ngCac .get(keys)

  overr de def getW hC cksum(keys: Seq[K]) = underly ngCac .getW hC cksum(keys)

  overr de def release() = underly ngCac .release()
}

/**
 * S mple tra  for a cac  support ng mult -get and s ngle set
 */
tra  Cac [K, V] extends ReadCac [K, V] {
  def add(key: K, value: V): Future[Boolean]

  def c ckAndSet(key: K, value: V, c cksum: C cksum): Future[Boolean]

  def set(key: K, value: V): Future[Un ]

  def set(pa rs: Seq[(K, V)]): Future[Un ] = {
    Future.jo n {
      pa rs map {
        case (key, value) => set(key, value)
      }
    }
  }

  /**
   * Replaces t  value for an ex st ng key.   f t  key doesn't ex st, t  has no effect.
   * @return true  f replaced, false  f not found
   */
  def replace(key: K, value: V): Future[Boolean]

  /**
   * Deletes a value from cac .
   * @return true  f deleted, false  f not found
   */
  def delete(key: K): Future[Boolean]
}

/**
 * allows one cac  to wrap anot r
 */
tra  Cac Wrapper[K, V] extends Cac [K, V] w h ReadCac Wrapper[K, V, Cac [K, V]] {
  overr de def add(key: K, value: V) = underly ngCac .add(key, value)

  overr de def c ckAndSet(key: K, value: V, c cksum: C cksum) =
    underly ngCac .c ckAndSet(key, value, c cksum)

  overr de def set(key: K, value: V) = underly ngCac .set(key, value)

  overr de def replace(key: K, value: V) = underly ngCac .replace(key, value)

  overr de def delete(key: K) = underly ngCac .delete(key)
}

/**
 * Sw ch bet en two cac s w h a dec der value
 */
class Dec derableCac [K, V](pr mary: Cac [K, V], secondary: Cac [K, V],  sAva lable: => Boolean)
    extends Cac Wrapper[K, V] {
  overr de def underly ngCac  =  f ( sAva lable) pr mary else secondary
}

pr vate object MutableMapCac  {
  case class  ntC cksum( :  nt) extends AnyVal w h C cksum
}

/**
 *  mple ntat on of a Cac  w h a mutable.Map
 */
class MutableMapCac [K, V](underly ng: mutable.Map[K, V]) extends Cac [K, V] {
   mport MutableMapCac . ntC cksum

  protected[t ] def c cksum(value: V): C cksum =  ntC cksum(value.hashCode)

  overr de def get(keys: Seq[K]): Future[KeyValueResult[K, V]] = Future {
    val founds = Map.newBu lder[K, V]
    val  er = keys. erator
    wh le ( er.hasNext) {
      val key =  er.next()
      synchron zed {
        underly ng.get(key)
      } match {
        case So (v) => founds += key -> v
        case None =>
      }
    }
    val found = founds.result()
    val notFound = NotFound(keys, found.keySet)
    KeyValueResult(found, notFound)
  }

  overr de def getW hC cksum(keys: Seq[K]): Future[CsKeyValueResult[K, V]] = Future {
    val founds = Map.newBu lder[K, (Return[V], C cksum)]
    val  er = keys. erator
    wh le ( er.hasNext) {
      val key =  er.next()
      synchron zed {
        underly ng.get(key)
      } match {
        case So (value) => founds += key -> (Return(value), c cksum(value))
        case None =>
      }
    }
    val found = founds.result()
    val notFound = NotFound(keys, found.keySet)
    KeyValueResult(found, notFound)
  }

  overr de def add(key: K, value: V): Future[Boolean] =
    synchron zed {
      underly ng.get(key) match {
        case So (_) =>
          Future.False
        case None =>
          underly ng += key -> value
          Future.True
      }
    }

  overr de def c ckAndSet(key: K, value: V, cs: C cksum): Future[Boolean] =
    synchron zed {
      underly ng.get(key) match {
        case So (current) =>
           f (c cksum(current) == cs) {
            // c cksums match, set value
            underly ng += key -> value
            Future.True
          } else {
            // c cksums d dn't match, so no set
            Future.False
          }
        case None =>
          //  f noth ng t re, t  c cksums can't be compared
          Future.False
      }
    }

  overr de def set(key: K, value: V): Future[Un ] = {
    synchron zed {
      underly ng += key -> value
    }
    Future.Done
  }

  overr de def replace(key: K, value: V): Future[Boolean] = synchron zed {
     f (underly ng.conta ns(key)) {
      underly ng(key) = value
      Future.True
    } else {
      Future.False
    }
  }

  overr de def delete(key: K): Future[Boolean] = synchron zed {
     f (underly ng.remove(key).nonEmpty) Future.True else Future.False
  }

  overr de def release(): Un  = synchron zed {
    underly ng.clear()
  }
}

/**
 *  n- mory  mple ntat on of a cac  w h LRU semant cs and a TTL.
 */
class Exp r ngLruCac [K, V](ttl: Durat on, max mumS ze:  nt)
    extends MutableMapCac [K, V](
      // TODO: cons der w r ng t  Cac   nterface d rectly to t 
      // Guava Cac ,  nstead of  ntroduc ng two layers of  nd rect on
      Cac Bu lder.newBu lder
        .as nstanceOf[Cac Bu lder[K, V]]
        .exp reAfterWr e(ttl. nM ll seconds, T  Un .M LL SECONDS)
        . n  alCapac y(max mumS ze)
        .max mumS ze(max mumS ze)
        .bu ld[K, V]()
        .asMap
        .asScala
    )

/**
 * An empty cac  that stays empty
 */
class NullCac [K, V] extends Cac [K, V] {
  lazy val futureTrue = Future.value(true)
  overr de def get(keys: Seq[K]) = Future.value(KeyValueResult(notFound = keys.toSet))
  overr de def getW hC cksum(keys: Seq[K]) = Future.value(KeyValueResult(notFound = keys.toSet))
  overr de def add(key: K, value: V) = futureTrue
  overr de def c ckAndSet(key: K, value: V, c cksum: C cksum) = Future.value(true)
  overr de def set(key: K, value: V) = Future.Done
  overr de def replace(key: K, value: V) = futureTrue
  overr de def delete(key: K) = futureTrue
  overr de def release() = ()
}
