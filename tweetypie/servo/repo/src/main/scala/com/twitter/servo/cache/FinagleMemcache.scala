package com.tw ter.servo.cac 

 mport com.tw ter.f nagle. mcac d.{CasResult, Cl ent}
 mport com.tw ter.f nagle.serv ce.RetryPol cy
 mport com.tw ter.f nagle.{Backoff,  mcac d, T  outExcept on, Wr eExcept on}
 mport com.tw ter.hash ng.KeyHas r
 mport com.tw ter. o.Buf
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.ut l._

case class  mcac RetryPol cy(
  wr eExcept onBackoffs: Backoff,
  t  outBackoffs: Backoff)
    extends RetryPol cy[Try[Noth ng]] {
  overr de def apply(r: Try[Noth ng]) = r match {
    case Throw(_: Wr eExcept on) => onWr eExcept on
    case Throw(_: T  outExcept on) => onT  outExcept on
    case _ => None
  }

  pr vate[t ] def onT  outExcept on = consu (t  outBackoffs.toStream) { ta l =>
    copy(t  outBackoffs = Backoff.fromStream(ta l))
  }

  pr vate[t ] def onWr eExcept on = consu (wr eExcept onBackoffs.toStream) { ta l =>
    copy(wr eExcept onBackoffs = Backoff.fromStream(ta l))
  }

  pr vate[t ] def consu (s: Stream[Durat on])(f: Stream[Durat on] =>  mcac RetryPol cy) = {
    s. adOpt on map { durat on =>
      (durat on, f(s.ta l))
    }
  }
}

object F nagle mcac Factory {
  val DefaultHashNa  = "fnv1-32"

  def apply(cl ent:  mcac d.Cl ent, dest: Str ng, hashNa : Str ng = DefaultHashNa ) =
    new F nagle mcac Factory(cl ent, dest, hashNa )
}

class F nagle mcac Factory pr vate[cac ] (
  cl ent:  mcac d.Cl ent,
  dest: Str ng,
  hashNa : Str ng)
    extends  mcac Factory {

  def apply():  mcac  = {
    val keyHas r = KeyHas r.byNa (hashNa )
    new F nagle mcac (cl ent.w hKeyHas r(keyHas r).newT mcac Cl ent(dest), hashNa )
  }
}

object F nagle mcac  {
  val NoFlags = 0
  val logger = Logger(getClass)
}

/**
 * Adapter for a [[ mcac ]] (type al as for [[TtlCac ]]) from a F nagle  mcac d
 * [[Cl ent]].
 */
class F nagle mcac (cl ent: Cl ent, hashNa : Str ng = F nagle mcac Factory.DefaultHashNa )
    extends  mcac  {

   mport F nagle mcac .NoFlags

  pr vate[t ] case class BufferC cksum(buffer: Buf) extends C cksum

  def release(): Un  = {
    cl ent.close()
  }

  overr de def get(keys: Seq[Str ng]): Future[KeyValueResult[Str ng, Array[Byte]]] =
    cl ent.getResult(keys).transform {
      case Return(gr) =>
        val found = gr.h s.map {
          case (key, v) =>
            val bytes = Buf.ByteArray.Owned.extract(v.value)
            key -> bytes
        }
        Future.value(KeyValueResult(found, gr.m sses, gr.fa lures))

      case Throw(t) =>
        Future.value(KeyValueResult(fa led = keys.map(_ -> t).toMap))
    }

  overr de def getW hC cksum(keys: Seq[Str ng]): Future[CsKeyValueResult[Str ng, Array[Byte]]] =
    cl ent.getsResult(keys).transform {
      case Return(gr) =>
        try {
          val h s = gr.h s map {
            case (key, v) =>
              val bytes = Buf.ByteArray.Owned.extract(v.value)
              key -> (Return(bytes), BufferC cksum(
                v.casUn que.get
              )) // TODO. what to do  f m ss ng?
          }
          Future.value(KeyValueResult(h s, gr.m sses, gr.fa lures))
        } catch {
          case t: Throwable =>
            Future.value(KeyValueResult(fa led = keys.map(_ -> t).toMap))
        }
      case Throw(t) =>
        Future.value(KeyValueResult(fa led = keys.map(_ -> t).toMap))
    }

  pr vate val jb2sb: java.lang.Boolean => Boolean = _.booleanValue
  pr vate val jl2sl: java.lang.Long => Long = _.longValue

  overr de def add(key: Str ng, value: Array[Byte], ttl: Durat on): Future[Boolean] =
    cl ent.add(key, NoFlags, ttl.fromNow, Buf.ByteArray.Owned(value)) map jb2sb

  overr de def c ckAndSet(
    key: Str ng,
    value: Array[Byte],
    c cksum: C cksum,
    ttl: Durat on
  ): Future[Boolean] = {
    c cksum match {
      case BufferC cksum(cs) =>
        cl ent.c ckAndSet(key, NoFlags, ttl.fromNow, Buf.ByteArray.Owned(value), cs) map {
          res: CasResult =>
            res.replaced
        }
      case _ =>
        Future.except on(new  llegalArgu ntExcept on("unrecogn zed c cksum: " + c cksum))
    }
  }

  overr de def set(key: Str ng, value: Array[Byte], ttl: Durat on): Future[Un ] =
    cl ent.set(key, NoFlags, ttl.fromNow, Buf.ByteArray.Owned(value))

  overr de def replace(key: Str ng, value: Array[Byte], ttl: Durat on): Future[Boolean] =
    cl ent.replace(key, NoFlags, ttl.fromNow, Buf.ByteArray.Owned(value)) map jb2sb

  overr de def delete(key: Str ng): Future[Boolean] =
    cl ent.delete(key) map jb2sb

  def  ncr(key: Str ng, delta: Long = 1): Future[Opt on[Long]] =
    cl ent. ncr(key, delta) map { _ map jl2sl }

  def decr(key: Str ng, delta: Long = 1): Future[Opt on[Long]] =
    cl ent.decr(key, delta) map { _ map jl2sl }

  // NOTE: T   s t  only reason that hashNa   s passed as a param to F nagle mcac .
  overr de lazy val toStr ng = "F nagle mcac (%s)".format(hashNa )
}
