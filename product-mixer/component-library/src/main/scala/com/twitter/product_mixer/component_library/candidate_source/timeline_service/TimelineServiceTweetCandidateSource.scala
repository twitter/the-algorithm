package com.tw ter.product_m xer.component_l brary.cand date_s ce.t  l ne_serv ce

 mport com.tw ter.product_m xer.component_l brary.model.cursor.NextCursorFeature
 mport com.tw ter.product_m xer.component_l brary.model.cursor.Prev ousCursorFeature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ceW hExtractedFeatures
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand datesW hS ceFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.t  l neserv ce.T  l neServ ce
 mport com.tw ter.t  l neserv ce.{thr ftscala => t}
 mport javax. nject. nject
 mport javax. nject.S ngleton

case object T  l neServ ceResponseWasTruncatedFeature
    extends FeatureW hDefaultOnFa lure[P pel neQuery, Boolean] {
  overr de val defaultValue: Boolean = false
}

@S ngleton
class T  l neServ ceT etCand dateS ce @ nject() (
  t  l neServ ce: T  l neServ ce)
    extends Cand dateS ceW hExtractedFeatures[t.T  l neQuery, t.T et] {

  overr de val  dent f er: Cand dateS ce dent f er =
    Cand dateS ce dent f er("T  l neServ ceT et")

  overr de def apply(request: t.T  l neQuery): St ch[Cand datesW hS ceFeatures[t.T et]] = {
    t  l neServ ce
      .getT  l ne(request).map { t  l ne =>
        val cand dates = t  l ne.entr es.collect {
          case t.T  l neEntry.T et(t et) => t et
        }

        val cand dateS ceFeatures =
          FeatureMapBu lder()
            .add(T  l neServ ceResponseWasTruncatedFeature, t  l ne.wasTruncated.getOrElse(false))
            .add(Prev ousCursorFeature, t  l ne.responseCursor.flatMap(_.top).getOrElse(""))
            .add(NextCursorFeature, t  l ne.responseCursor.flatMap(_.bottom).getOrElse(""))
            .bu ld()

        Cand datesW hS ceFeatures(cand dates = cand dates, features = cand dateS ceFeatures)
      }
  }

}
