package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.snowflake. d.Snowflake d

object CreatedAtRepa rer {
  // no createdAt value should be less than t 
  val jan_01_2006 = 1136073600000L

  // no non-snowflake createdAt value should be greater than t 
  val jan_01_2011 = 1293840000000L

  // allow createdAt t  stamp to be up to t  amount off from t  snowflake  d
  // before apply ng t  correct on.
  val var anceThreshold:  d a d = 10.m nutes. nM ll seconds
}

/**
 * Detects t ets w h bad createdAt t  stamps and attempts to f x,  f poss ble
 * us ng t  snowflake  d.  pre-snowflake t ets are left unmod f ed.
 */
class CreatedAtRepa rer(scr be: FutureEffect[Str ng]) extends Mutat on[T et] {
   mport CreatedAtRepa rer._

  def apply(t et: T et): Opt on[T et] = {
    assert(t et.coreData.nonEmpty, "t et core data  s m ss ng")
    val createdAtM ll s = getCreatedAt(t et) * 1000

     f (Snowflake d. sSnowflake d(t et. d)) {
      val snowflakeM ll s = Snowflake d(t et. d).un xT  M ll s.asLong
      val d ff = (snowflakeM ll s - createdAtM ll s).abs

       f (d ff >= var anceThreshold) {
        scr be(t et. d + "\t" + createdAtM ll s)
        val snowflakeSeconds = snowflakeM ll s / 1000
        So (T etLenses.createdAt.set(t et, snowflakeSeconds))
      } else {
        None
      }
    } else {
      // not a snowflake  d, hard to repa r, so just log  
       f (createdAtM ll s < jan_01_2006 || createdAtM ll s > jan_01_2011) {
        scr be(t et. d + "\t" + createdAtM ll s)
      }
      None
    }
  }
}
