package com.tw ter.servo.ut l

 mport com.google.common.base.Charsets
 mport com.google.common.pr m  ves.{ nts, Longs}
 mport com.tw ter.scrooge.{B naryThr ftStructSer al zer, Thr ftStructCodec, Thr ftStruct}
 mport com.tw ter.ut l.{Future, Return, Try, Throw}
 mport java.n o.{ByteBuffer, CharBuffer}
 mport java.n o.charset.{Charset, CharsetEncoder, CharsetDecoder}

/**
 * Transfor r  s a (poss bly part al) b d rect onal convers on
 * bet en values of two types.    s part cularly useful for
 * ser al z ng values for storage and read ng t m back out (see
 * com.tw ter.servo.cac .Ser al zer).
 *
 *  n so   mple ntat ons, t  convers on may lose data (for example
 * w n used for storage  n a cac ).  n general, any data that passes
 * through a convers on should be preserved  f t  data  s converted
 * back. T re  s code to make   easy to c ck that y  Transfor r
 *  nstance has t  property  n
 * com.tw ter.servo.ut l.Transfor rLawSpec.
 *
 * Transfor rs should take care not to mutate t  r  nputs w n
 * convert ng  n e  r d rect on,  n order to ensure that concurrent
 * transformat ons of t  sa   nput y eld t  sa  result.
 *
 * Transfor r forms a category w h `andT n` and ` dent y`.
 */
tra  Transfor r[A, B] { self =>
  def to(a: A): Try[B]

  def from(b: B): Try[A]

  @deprecated("Use Future.const(transfor r.to(x))", "2.0.1")
  def asyncTo(a: A): Future[B] = Future.const(to(a))

  @deprecated("Use Future.const(transfor r.from(x))", "2.0.1")
  def asyncFrom(b: B): Future[A] = Future.const(from(b))

  /**
   * Compose t  transfor r w h anot r. As long as both
   * transfor rs follow t  stated laws, t  composed transfor r
   * w ll follow t m.
   */
  def andT n[C](t: Transfor r[B, C]): Transfor r[A, C] =
    new Transfor r[A, C] {
      overr de def to(a: A) = self.to(a) andT n t.to
      overr de def from(c: C) = t.from(c) andT n self.from
    }

  /**
   * Reverse t  d rect on of t  transfor r.
   *
   * Law: t.fl p.fl p == t
   */
  lazy val fl p: Transfor r[B, A] =
    new Transfor r[B, A] {
      overr de lazy val fl p = self
      overr de def to(b: B) = self.from(b)
      overr de def from(a: A) = self.to(a)
    }
}

object Transfor r {

  /**
   * Create a new Transfor r from t  suppl ed funct ons, catch ng
   * except ons and convert ng t m to fa lures.
   */
  def apply[A, B](tTo: A => B, tFrom: B => A): Transfor r[A, B] =
    new Transfor r[A, B] {
      overr de def to(a: A): Try[B] = Try { tTo(a) }
      overr de def from(b: B): Try[A] = Try { tFrom(b) }
    }

  def  dent y[A]: Transfor r[A, A] = pure[A, A](a => a, a => a)

  /**
   * L ft a pa r of (total) convers on funct ons to a Transfor r. T 
   * caller  s respons ble for ensur ng that t  result ng transfor r
   * follows t  laws for Transfor rs.
   */
  def pure[A, B](pureTo: A => B, pureFrom: B => A): Transfor r[A, B] =
    new Transfor r[A, B] {
      overr de def to(a: A): Try[B] = Return(pureTo(a))
      overr de def from(b: B): Try[A] = Return(pureFrom(b))
    }

  /**
   * L ft a transfor r to a transfor r on opt onal values.
   *
   * None bypasses t  underly ng convers on (as   must, s nce t re
   *  s no value to transform).
   */
  def opt onal[A, B](underly ng: Transfor r[A, B]): Transfor r[Opt on[A], Opt on[B]] =
    new Transfor r[Opt on[A], Opt on[B]] {
      overr de def to(optA: Opt on[A]) = optA match {
        case None => Return.None
        case So (a) => underly ng.to(a) map { So (_) }
      }

      overr de def from(optB: Opt on[B]) = optB match {
        case None => Return.None
        case So (b) => underly ng.from(b) map { So (_) }
      }
    }

  //////////////////////////////////////////////////
  // Transfor rs for access ng/generat ng f elds of a Map.
  //
  // T se transfor rs are useful for ser al z ng/deser al z ng to
  // storage that stores Maps, for example Hamsa.

  /**
   * Thrown by `requ redF eld` w n t  f eld  s not present.
   */
  case class M ss ngRequ redF eld[K](k: K) extends Runt  Except on

  /**
   * Get a value from t  map, y eld ng M ss ngRequ redF eld w n t 
   * value  s not present  n t  map.
   *
   * T   nverse transform y elds a Map conta n ng only t  one value.
   */
  def requ redF eld[K, V](k: K): Transfor r[Map[K, V], V] =
    new Transfor r[Map[K, V], V] {
      overr de def to(m: Map[K, V]) =
        m get k match {
          case So (v) => Return(v)
          case None => Throw(M ss ngRequ redF eld(k))
        }

      overr de def from(v: V) = Return(Map(k -> v))
    }

  /**
   * Attempt to get a f eld from a Map, y eld ng None  f t  value  s
   * not present.
   *
   * T   nverse transform w ll put t  value  n a Map  f    s So ,
   * and om     f    s None.
   */
  def opt onalF eld[K, V](k: K): Transfor r[Map[K, V], Opt on[V]] =
    pure[Map[K, V], Opt on[V]](_.get(k), _.map { k -> _ }.toMap)

  /**
   * Transforms an Opt on[T] to a T, us ng a default value for None.
   *
   * Note that t  default value w ll be converted back to None by
   * .from (.from w ll never return So (default)).
   */
  def default[T](value: T): Transfor r[Opt on[T], T] =
    pure[Opt on[T], T](_ getOrElse value, t =>  f (t == value) None else So (t))

  /**
   * Transforms `Long`s to b g-end an byte arrays.
   */
  lazy val LongToB gEnd an: Transfor r[Long, Array[Byte]] =
    new Transfor r[Long, Array[Byte]] {
      def to(a: Long) = Try(Longs.toByteArray(a))
      def from(b: Array[Byte]) = Try(Longs.fromByteArray(b))
    }

  /**
   * Transforms ` nt`s to b g-end an byte arrays.
   */
  lazy val  ntToB gEnd an: Transfor r[ nt, Array[Byte]] =
    new Transfor r[ nt, Array[Byte]] {
      def to(a:  nt) = Try( nts.toByteArray(a))
      def from(b: Array[Byte]) = Try( nts.fromByteArray(b))
    }

  /**
   * Transforms UTF8-encoded str ngs to byte arrays.
   */
  lazy val Utf8ToBytes: Transfor r[Str ng, Array[Byte]] =
    str ngToBytes(Charsets.UTF_8)

  /**
   * Transforms str ngs, encoded  n a g ven character set, to byte arrays.
   */
  pr vate[ut l] def str ngToBytes(charset: Charset): Transfor r[Str ng, Array[Byte]] =
    new Transfor r[Str ng, Array[Byte]] {
      pr vate[t ] val charsetEncoder = new ThreadLocal[CharsetEncoder]() {
        protected overr de def  n  alValue() = charset.newEncoder
      }

      pr vate[t ] val charsetDecoder = new ThreadLocal[CharsetDecoder]() {
        protected overr de def  n  alValue() = charset.newDecoder
      }

      overr de def to(str: Str ng): Try[Array[Byte]] = Try {
        //   can't just use `Str ng.getBytes("UTF-8")`  re because   w ll
        // s lently replace UTF-16 surrogate characters, wh ch w ll cause
        // CharsetEncoder to throw except ons.
        val bytes = charsetEncoder.get.encode(CharBuffer.wrap(str))
        bytes.array.sl ce(bytes.pos  on, bytes.l m )
      }

      overr de def from(bytes: Array[Byte]): Try[Str ng] = Try {
        charsetDecoder.get.decode(ByteBuffer.wrap(bytes)).toStr ng
      }
    }

  /**
   * Transforms a Thr ftStruct to a byte-array us ng Thr ft's TB naryProtocol.
   */
  def thr ftStructToBytes[T <: Thr ftStruct](c: Thr ftStructCodec[T]): Transfor r[T, Array[Byte]] =
    new Transfor r[T, Array[Byte]] {
      pr vate[t ] val ser = B naryThr ftStructSer al zer(c)
      def to(a: T) = Try(ser.toBytes(a))
      def from(b: Array[Byte]) = Try(ser.fromBytes(b))
    }
}

/**
 * transforms an Opt on[T] to a T, us ng a default value for None
 */
@deprecated("Use Transfor r.default", "2.0.1")
class Opt onToTypeTransfor r[T](default: T) extends Transfor r[Opt on[T], T] {
  overr de def to(b: Opt on[T]): Try[T] = Return(b.getOrElse(default))

  overr de def from(a: T): Try[Opt on[T]] = a match {
    case `default` => Return.None
    case _ => Return(So (a))
  }
}
