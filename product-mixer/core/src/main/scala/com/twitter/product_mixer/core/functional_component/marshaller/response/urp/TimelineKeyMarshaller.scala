package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urp

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Follo dTop cs T  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Follo dTop csOt rT  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.For ExploreM xerT  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.NoteworthyAccountsT  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Not nterestedTop cs T  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.NuxFor CategoryUserRecom ndat onsT  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.NuxGeoCategoryUserRecom ndat onsT  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.NuxPymkCategoryUserRecom ndat onsT  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.NuxS ngle nterestCategoryUserRecom ndat onsT  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.NuxUserRecom ndat onsT  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Shopp ngHo T  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.T  l neKey
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Top csLand ngT  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Top csP ckerT  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Trend ngExploreM xerT  l ne
 mport com.tw ter.strato.graphql.t  l nes.{thr ftscala => graphql}
 mport javax. nject.S ngleton

@S ngleton
class T  l neKeyMarshaller {

  def apply(t  l neKey: T  l neKey): graphql.T  l neKey = t  l neKey match {
    case Top csLand ngT  l ne(top c d) =>
      graphql.T  l neKey.Top cT  l ne(graphql.Top c d(top c d))
    case NoteworthyAccountsT  l ne(top c d) =>
      graphql.T  l neKey.NoteworthyAccountsT  l ne(graphql.Top c d(top c d))
    case Top csP ckerT  l ne(top c d) =>
      graphql.T  l neKey.Top csP ckerT  l ne(graphql.Top c d(top c d))
    case Follo dTop cs T  l ne() =>
      graphql.T  l neKey.Follo dTop cs T  l ne(graphql.Vo d())
    case Not nterestedTop cs T  l ne() =>
      graphql.T  l neKey.Not nterestedTop cs T  l ne(graphql.Vo d())
    case Follo dTop csOt rT  l ne(user d) =>
      graphql.T  l neKey.Follo dTop csOt rT  l ne(user d)
    case NuxUserRecom ndat onsT  l ne() =>
      graphql.T  l neKey.NuxUserRecom ndat onsT  l ne(graphql.Vo d())
    case NuxFor CategoryUserRecom ndat onsT  l ne() =>
      graphql.T  l neKey.NuxFor CategoryUserRecom ndat onsT  l ne(graphql.Vo d())
    case NuxPymkCategoryUserRecom ndat onsT  l ne() =>
      graphql.T  l neKey.NuxPymkCategoryUserRecom ndat onsT  l ne(graphql.Vo d())
    case NuxGeoCategoryUserRecom ndat onsT  l ne() =>
      graphql.T  l neKey.NuxGeoCategoryUserRecom ndat onsT  l ne(graphql.Vo d())
    case NuxS ngle nterestCategoryUserRecom ndat onsT  l ne(top c d) =>
      graphql.T  l neKey.NuxS ngle nterestCategoryUserRecom ndat onsT  l ne(
        graphql.Top c d(top c d))
    case Shopp ngHo T  l ne() => graphql.T  l neKey.Shopp ngHo (graphql.Vo d())
    case For ExploreM xerT  l ne() =>
      graphql.T  l neKey.For ExploreM xerT  l ne(graphql.Vo d())
    case Trend ngExploreM xerT  l ne() =>
      graphql.T  l neKey.Trend ngExploreM xerT  l ne(graphql.Vo d())
  }
}
