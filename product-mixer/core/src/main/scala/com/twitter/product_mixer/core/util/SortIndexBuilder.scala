package com.tw ter.product_m xer.core.ut l

 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.ut l.T  

object Sort ndexBu lder {

  /** t  [[T  ]] from a [[Snowflake d]] */
  def  dToT  ( d: Long): T   =
    T  .fromM ll seconds(Snowflake d.un xT  M ll sOrFloorFrom d( d))

  /** t  f rst [[Snowflake d]] poss ble for a g ven [[T  ]]  */
  def t  To d(t  : T  ): Long = Snowflake d.f rst dFor(t  )

  /** t  f rst [[Snowflake d]] poss ble for a g ven un x epoch m ll s  */
  def t  To d(t  M ll s: Long): Long = Snowflake d.f rst dFor(t  M ll s)
}
