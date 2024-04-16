package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em. con_label

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em. con_label. conLabelCand dateUrt emBu lder. conLabelCl entEvent nfoEle nt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.LabelCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.r chtext.BaseR chTextBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. con.Hor zon con
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. con_label. conLabel em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chTextEnt y
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object  conLabelCand dateUrt emBu lder {
  val  conLabelCl entEvent nfoEle nt: Str ng = " conlabel"
}

case class  conLabelCand dateUrt emBu lder[-Query <: P pel neQuery, Cand date <: LabelCand date](
  r chTextBu lder: BaseR chTextBu lder[Query, Cand date],
   con: Opt on[Hor zon con] = None,
  ent  es: Opt on[L st[R chTextEnt y]] = None,
  cl entEvent nfoBu lder: Opt on[BaseCl entEvent nfoBu lder[Query, Cand date]] = None,
  feedbackAct on nfoBu lder: Opt on[BaseFeedbackAct on nfoBu lder[Query, Cand date]] = None)
    extends Cand dateUrtEntryBu lder[Query, Cand date,  conLabel em] {

  overr de def apply(
    query: Query,
    labelCand date: Cand date,
    cand dateFeatures: FeatureMap
  ):  conLabel em =
     conLabel em(
       d = labelCand date. d.toStr ng,
      sort ndex = None, // Sort  ndexes are automat cally set  n t  doma n marshaller phase
      cl entEvent nfo = cl entEvent nfoBu lder.flatMap(
        _.apply(query, labelCand date, cand dateFeatures, So ( conLabelCl entEvent nfoEle nt))),
      feedbackAct on nfo =
        feedbackAct on nfoBu lder.flatMap(_.apply(query, labelCand date, cand dateFeatures)),
      text = r chTextBu lder(query, labelCand date, cand dateFeatures),
       con =  con,
    )
}
