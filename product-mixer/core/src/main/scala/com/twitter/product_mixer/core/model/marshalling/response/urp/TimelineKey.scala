package com.tw ter.product_m xer.core.model.marshall ng.response.urp

sealed tra  T  l neKey

case class Top csLand ngT  l ne(top c d: Opt on[Str ng]) extends T  l neKey

case class NoteworthyAccountsT  l ne(top c d: Opt on[Str ng]) extends T  l neKey

case class Top csP ckerT  l ne(top c d: Opt on[Str ng]) extends T  l neKey

case class Not nterestedTop cs T  l ne() extends T  l neKey

case class Follo dTop cs T  l ne() extends T  l neKey

case class Follo dTop csOt rT  l ne(user d: Long) extends T  l neKey

case class NuxUserRecom ndat onsT  l ne() extends T  l neKey

case class NuxFor CategoryUserRecom ndat onsT  l ne() extends T  l neKey

case class NuxPymkCategoryUserRecom ndat onsT  l ne() extends T  l neKey

case class NuxGeoCategoryUserRecom ndat onsT  l ne() extends T  l neKey

case class NuxS ngle nterestCategoryUserRecom ndat onsT  l ne(top c d: Opt on[Str ng])
    extends T  l neKey

case class Shopp ngHo T  l ne() extends T  l neKey

case class For ExploreM xerT  l ne() extends T  l neKey

case class Trend ngExploreM xerT  l ne() extends T  l neKey
