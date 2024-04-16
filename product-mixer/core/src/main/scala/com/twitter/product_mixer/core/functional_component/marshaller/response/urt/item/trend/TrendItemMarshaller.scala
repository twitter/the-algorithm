package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.trend

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.UrlMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.promoted.Promoted tadataMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.trend.Trend em
 mport com.tw ter.t  l nes.render.thr ftscala.GroupedTrend
 mport com.tw ter.t  l nes.render.thr ftscala.Trend tadata
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}

@S ngleton
class Trend emMarshaller @ nject() (
  promoted tadataMarshaller: Promoted tadataMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(trend em: Trend em): urt.T  l ne emContent =
    urt.T  l ne emContent.Trend(
      urt.Trend(
        na  = trend em.trendNa ,
        url = urlMarshaller(trend em.url),
        promoted tadata = trend em.promoted tadata.map(promoted tadataMarshaller(_)),
        descr pt on = trend em.descr pt on,
        trend tadata = So (
          Trend tadata(
             taDescr pt on = trend em. taDescr pt on,
            url = So (urlMarshaller(trend em.url)),
            doma nContext = trend em.doma nContext
          )),
        groupedTrends = trend em.groupedTrends.map { trends =>
          trends.map { trend =>
            GroupedTrend(na  = trend.trendNa , url = urlMarshaller(trend.url))
          }
        }
      )
    )
}
