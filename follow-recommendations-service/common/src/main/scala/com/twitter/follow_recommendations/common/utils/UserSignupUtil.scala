package com.tw ter.follow_recom ndat ons.common.ut ls

 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  

object UserS gnupUt l {
  def s gnupT  (hasCl entContext: HasCl entContext): Opt on[T  ] =
    hasCl entContext.cl entContext.user d.flatMap(Snowflake d.t  From dOpt)

  def userS gnupAge(hasCl entContext: HasCl entContext): Opt on[Durat on] =
    s gnupT  (hasCl entContext).map(T  .now - _)
}
