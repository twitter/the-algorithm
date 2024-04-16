package com.tw ter.t  l neranker.ut l

 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  

object SnowflakeUt ls {
  def mutate dT  ( d: Long, t  Op: T   => T  ): Long = {
    Snowflake d.f rst dFor(t  Op(Snowflake d( d).t  ))
  }

  def quant zeDown( d: Long, step: Durat on): Long = {
    mutate dT  ( d, _.floor(step))
  }

  def quant zeUp( d: Long, step: Durat on): Long = {
    mutate dT  ( d, _.ce l(step))
  }
}
