package com.tw ter.servo.database

 mport com.tw ter.ut l.T  
 mport java.sql.{ResultSet, T  stamp}

/**
 * A base tra  for transform ng JDBC ResultSets.
 * Des gned to be used w h t  Accessors tra .
 */
tra   mpl c Bu lder[T] extends Accessors {
  def apply( mpl c  row: ResultSet): T
}

object Accessors {

  /**
   *  lper to make   comp le t   error w n try ng to call getOpt on on types not supported
   *  nstead of a runt   except on
   */
  object SafeMan fest {
     mpl c  val booleanSafeMan fest = new SafeMan fest( mpl c ly[Man fest[Boolean]])
     mpl c  val doubleSafeMan fest = new SafeMan fest( mpl c ly[Man fest[Double]])
     mpl c  val  ntSafeMan fest = new SafeMan fest[ nt]( mpl c ly[Man fest[ nt]])
     mpl c  val longSafeMan fest = new SafeMan fest[Long]( mpl c ly[Man fest[Long]])
     mpl c  val str ngSafeMan fest = new SafeMan fest[Str ng]( mpl c ly[Man fest[Str ng]])
     mpl c  val t  stampSafeMan fest =
      new SafeMan fest[T  stamp]( mpl c ly[Man fest[T  stamp]])
  }

  @deprecated("safe man fests no longer supported, use type-spec f c accessors  nstead", "1.1.1")
  case class SafeMan fest[T](mf: Man fest[T])
}

/**
 * m x n to get ResultSet accessors for standard types
 */
tra  Accessors {
   mport Accessors._

  /**
   * @return None w n t  column  s null for t  current row of t  result set passed  n
   *         So [T] ot rw se
   * @throws UnsupportedOperat onExcept on  f t  return type expected  s not supported, currently
   *        only Boolean,  nt, Long, Str ng and T  stamp are supported
   */
  @deprecated("use type-spec f c accessors  nstead", "1.1.1")
  def getOpt on[T](column: Str ng)( mpl c  row: ResultSet, sf: SafeMan fest[T]): Opt on[T] = {
    val res = {
       f (classOf[Boolean] == sf.mf.erasure) {
        row.getBoolean(column)
      } else  f (classOf[Double] == sf.mf.erasure) {
        row.getDouble(column)
      } else  f (classOf[ nt] == sf.mf.erasure) {
        row.get nt(column)
      } else  f (classOf[Long] == sf.mf.erasure) {
        row.getLong(column)
      } else  f (classOf[Str ng] == sf.mf.erasure) {
        row.getStr ng(column)
      } else  f (classOf[T  stamp] == sf.mf.erasure) {
        row.getT  stamp(column)
      } else {
        throw new UnsupportedOperat onExcept on("type not supported: " + sf.mf.erasure)
      }
    }
     f (row.wasNull()) {
      None
    } else {
      So (res.as nstanceOf[T])
    }
  }

  /**
   * @param get t   thod to apply to t  ResultSet
   * @param row t   mpl c  ResultSet on wh ch to apply get
   * @return None w n t  column  s null for t  current row of t  result set passed  n
   *         So [T] ot rw se
   */
  def getOpt on[T](get: ResultSet => T)( mpl c  row: ResultSet): Opt on[T] = {
    val result = get(row)
     f (row.wasNull()) {
      None
    } else {
      So (result)
    }
  }

  def booleanOpt on(column: Str ng)( mpl c  row: ResultSet): Opt on[Boolean] =
    getOpt on((_: ResultSet).getBoolean(column))

  def boolean(column: Str ng, default: Boolean = false)( mpl c  row: ResultSet): Boolean =
    booleanOpt on(column).getOrElse(default)

  def doubleOpt on(column: Str ng)( mpl c  row: ResultSet): Opt on[Double] =
    getOpt on((_: ResultSet).getDouble(column))

  def double(column: Str ng, default: Double = 0.0)( mpl c  row: ResultSet): Double =
    doubleOpt on(column).getOrElse(default)

  def  ntOpt on(column: Str ng)( mpl c  row: ResultSet): Opt on[ nt] =
    getOpt on((_: ResultSet).get nt(column))

  def  nt(column: Str ng, default:  nt = 0)( mpl c  row: ResultSet):  nt =
     ntOpt on(column).getOrElse(default)

  def longOpt on(column: Str ng)( mpl c  row: ResultSet): Opt on[Long] =
    getOpt on((_: ResultSet).getLong(column))

  def long(column: Str ng, default: Long = 0)( mpl c  row: ResultSet): Long =
    longOpt on(column).getOrElse(default)

  def str ngOpt on(column: Str ng)( mpl c  row: ResultSet): Opt on[Str ng] =
    getOpt on((_: ResultSet).getStr ng(column))

  def str ng(column: Str ng, default: Str ng = "")( mpl c  row: ResultSet): Str ng =
    str ngOpt on(column).getOrElse(default)

  def t  stampOpt on(column: Str ng)( mpl c  row: ResultSet): Opt on[T  stamp] =
    getOpt on((_: ResultSet).getT  stamp(column))

  def t  stamp(
    column: Str ng,
    default: T  stamp = new T  stamp(0)
  )(
     mpl c  row: ResultSet
  ): T  stamp =
    t  stampOpt on(column).getOrElse(default)

  def datet  Opt on(column: Str ng)( mpl c  row: ResultSet): Opt on[Long] =
    t  stampOpt on(column) map { _.getT   }

  def datet  (column: Str ng, default: Long = 0L)( mpl c  row: ResultSet): Long =
    datet  Opt on(column).getOrElse(default)

  def t  Opt on(column: Str ng)( mpl c  row: ResultSet): Opt on[T  ] =
    datet  Opt on(column) map { T  .fromM ll seconds(_) }

  def t  (column: Str ng, default: T   = T  .epoch)( mpl c  row: ResultSet): T   =
    t  Opt on(column).getOrElse(default)

  def bytesOpt on(column: Str ng)( mpl c  row: ResultSet): Opt on[Array[Byte]] =
    getOpt on((_: ResultSet).getBytes(column))

  def bytes(
    column: Str ng,
    default: Array[Byte] = Array.empty[Byte]
  )(
     mpl c  row: ResultSet
  ): Array[Byte] =
    bytesOpt on(column).getOrElse(default)

}
