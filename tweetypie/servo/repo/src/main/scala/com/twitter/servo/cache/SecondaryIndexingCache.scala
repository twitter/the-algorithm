package com.tw ter.servo.cac 

 mport com.tw ter.logg ng.Logger
 mport com.tw ter.ut l.{Future, Return, Throw, Try}

object Secondary ndex ngCac  {
  type  ndexMapp ng[S, V] = V => Try[Opt on[S]]
}

/**
 * Stores a secondary  ndex w never set  s called,
 * us ng a mapp ng from value to secondary  ndex
 */
class Secondary ndex ngCac [K, S, V](
  overr de val underly ngCac : Cac [K, Cac d[V]],
  secondary ndexCac : Cac [S, Cac d[K]],
  secondary ndex: Secondary ndex ngCac . ndexMapp ng[S, V])
    extends Cac Wrapper[K, Cac d[V]] {
  protected[t ] val log = Logger.get(getClass.getS mpleNa )

  protected[t ] def setSecondary ndex(key: K, cac dValue: Cac d[V]): Future[Un ] =
    cac dValue.value match {
      case So (value) =>
        secondary ndex(value) match {
          case Return(So ( ndex)) =>
            val cac dKey = cac dValue.copy(value = So (key))
            secondary ndexCac .set( ndex, cac dKey)
          case Return.None =>
            Future.Done
          case Throw(t) =>
            log.error(t, "fa led to determ ne secondary  ndex for: %s", cac dValue)
            Future.Done
        }
      //  f  're stor ng a tombstone, no secondary  ndex can be made
      case None => Future.Done
    }

  overr de def set(key: K, cac dValue: Cac d[V]): Future[Un ] =
    super.set(key, cac dValue) flatMap { _ =>
      setSecondary ndex(key, cac dValue)
    }

  overr de def c ckAndSet(key: K, cac dValue: Cac d[V], c cksum: C cksum): Future[Boolean] =
    super.c ckAndSet(key, cac dValue, c cksum) flatMap { wasStored =>
       f (wasStored)
        // do a stra ght set of t  secondary  ndex, but only  f t  CAS succeeded
        setSecondary ndex(key, cac dValue) map { _ =>
          true
        }
      else
        Future.value(false)
    }

  overr de def add(key: K, cac dValue: Cac d[V]): Future[Boolean] =
    super.add(key, cac dValue) flatMap { wasAdded =>
       f (wasAdded)
        // do a stra ght set of t  secondary  ndex, but only  f t  add succeeded
        setSecondary ndex(key, cac dValue) map { _ =>
          true
        }
      else
        Future.value(false)
    }

  overr de def replace(key: K, cac dValue: Cac d[V]): Future[Boolean] =
    super.replace(key, cac dValue) flatMap { wasReplaced =>
       f (wasReplaced)
        setSecondary ndex(key, cac dValue) map { _ =>
          true
        }
      else
        Future.value(false)
    }

  overr de def release(): Un  = {
    underly ngCac .release()
    secondary ndexCac .release()
  }

  def w hSecondary ndex[T](
    secondary ndex ngCac : Cac [T, Cac d[K]],
    secondary ndex: Secondary ndex ngCac . ndexMapp ng[T, V]
  ): Secondary ndex ngCac [K, T, V] =
    new Secondary ndex ngCac [K, T, V](t , secondary ndex ngCac , secondary ndex)
}
