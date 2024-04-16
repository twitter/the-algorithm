package com.tw ter.un f ed_user_act ons.adapter.common

 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.ut l.T  

object AdapterUt ls {
  def currentT  stampMs: Long = T  .now. nM ll seconds
  def getT  stampMsFromT et d(t et d: Long): Long = Snowflake d.un xT  M ll sFrom d(t et d)

  // For now just make sure both language code and country code are  n upper cases for cons stency
  // For language code, t re are m xed lo r and upper cases
  // For country code, t re are m xed lo r and upper cases
  def normal zeLanguageCode( nputLanguageCode: Str ng): Str ng =  nputLanguageCode.toUpperCase
  def normal zeCountryCode( nputCountryCode: Str ng): Str ng =  nputCountryCode.toUpperCase
}
