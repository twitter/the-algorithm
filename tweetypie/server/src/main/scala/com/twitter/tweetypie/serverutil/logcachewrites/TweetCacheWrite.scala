package com.tw ter.t etyp e.serverut l.logcac wr es

 mport com.tw ter.servo.cac .Cac d
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.t etyp e.T et d
 mport com.tw ter.t etyp e.core.Ser al zer
 mport com.tw ter.t etyp e.thr ftscala.Cac dT et
 mport com.tw ter.ut l.T  
 mport java.ut l.Base64

/**
 * A record of a t et cac  wr e. T   s used for debugg ng. T se log
 *  ssages are scr bed to test_t etyp e_t et_cac _wr es.
 */
case class T etCac Wr e(
  t et d: T et d,
  t  stamp: T  ,
  act on: Str ng,
  value: Opt on[Cac d[Cac dT et]]) {

  /**
   * Convert to a tab-separated str ng su able for wr  ng to a log  ssage.
   *
   * F elds are:
   *  - T et  d
   *  - T  stamp:
   *       f t  t et  d  s a snowflake  d, t   s an offset s nce t et creat on.
   *       f    s not a snowflake  d, t n t   s a Un x epoch t    n
   *      m ll seconds. (T   dea  s that for most t ets, t  encod ng w ll make
   *        eas er to see t   nterval bet en events and w t r   occured soon
   *      after t et creat on.)
   *  - Cac  act on ("set", "add", "replace", "cas", "delete")
   *  - Base64-encoded Cac d[Cac dT et] struct
   */
  def toLog ssage: Str ng = {
    val bu lder = new java.lang.Str ngBu lder
    val t  stampOffset =
       f (Snowflake d. sSnowflake d(t et d)) {
        Snowflake d(t et d).un xT  M ll s.asLong
      } else {
        0
      }
    bu lder
      .append(t et d)
      .append('\t')
      .append(t  stamp. nM ll seconds - t  stampOffset)
      .append('\t')
      .append(act on)
      .append('\t')
    value.foreach { ct =>
      // W n logg ng,   end up ser al z ng t  value tw ce, once for t 
      // cac  wr e and once for t  logg ng. T   s subopt mal, but t 
      // assumpt on  s that   only do t  for a small fract on of cac 
      // wr es, so   should be ok. T  reason that t   s necessary  s
      // because   want to do t  f lter ng on t  deser al zed value, so
      // t  ser al zed value  s not ava lable at t  level that   are
      // do ng t  f lter ng.
      val thr ftBytes = Ser al zer.Cac dT et.Cac dCompact.to(ct).get
      bu lder.append(Base64.getEncoder.encodeToStr ng(thr ftBytes))
    }
    bu lder.toStr ng
  }
}

object T etCac Wr e {
  case class ParseExcept on(msg: Str ng, cause: Except on) extends Runt  Except on(cause) {
    overr de def get ssage: Str ng = s"Fa led to parse as T etCac Wr e: $msg"
  }

  /**
   * Parse a T etCac Wr e object from t  result of T etCac Wr e.toLog ssage
   */
  def fromLog ssage(msg: Str ng): T etCac Wr e =
    try {
      val (t et dStr, t  stampStr, act on, cac dT etStr) =
        msg.spl ('\t') match {
          case Array(f1, f2, f3) => (f1, f2, f3, None)
          case Array(f1, f2, f3, f4) => (f1, f2, f3, So (f4))
        }
      val t et d = t et dStr.toLong
      val t  stamp = {
        val offset =
           f (Snowflake d. sSnowflake d(t et d)) {
            Snowflake d(t et d).un xT  M ll s.asLong
          } else {
            0
          }
        T  .fromM ll seconds(t  stampStr.toLong + offset)
      }
      val value = cac dT etStr.map { str =>
        val thr ftBytes = Base64.getDecoder.decode(str)
        Ser al zer.Cac dT et.Cac dCompact.from(thr ftBytes).get
      }

      T etCac Wr e(t et dStr.toLong, t  stamp, act on, value)
    } catch {
      case e: Except on => throw ParseExcept on(msg, e)
    }
}
