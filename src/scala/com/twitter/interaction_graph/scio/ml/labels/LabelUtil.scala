package com.tw ter. nteract on_graph.sc o.ml.labels

 mport com.spot fy.sc o.Sc o tr cs
 mport com.tw ter. nteract on_graph.thr ftscala.EdgeFeature
 mport com.tw ter. nteract on_graph.thr ftscala.EdgeLabel
 mport com.tw ter. nteract on_graph.thr ftscala.FeatureNa 
 mport com.tw ter. nteract on_graph.thr ftscala.{Edge => TEdge}
 mport com.tw ter.soc algraph.event.thr ftscala.FollowEvent

object LabelUt l {

  val LabelExpl c  = Set(
    FeatureNa .NumFollows,
    FeatureNa .NumFavor es,
    FeatureNa .NumRet ets,
    FeatureNa .Num nt ons,
    FeatureNa .NumT etQuotes,
    FeatureNa .NumPhotoTags,
    FeatureNa .NumRtFavor es,
    FeatureNa .NumRtRepl es,
    FeatureNa .NumRtT etQuotes,
    FeatureNa .NumRtRet ets,
    FeatureNa .NumRt nt ons,
    FeatureNa .NumShares,
    FeatureNa .NumRepl es,
  )

  val Label mpl c  = Set(
    FeatureNa .NumT etCl cks,
    FeatureNa .NumProf leV ews,
    FeatureNa .NumL nkCl cks,
    FeatureNa .NumPushOpens,
    FeatureNa .NumNtabCl cks,
    FeatureNa .NumRtT etCl cks,
    FeatureNa .NumRtL nkCl cks,
    FeatureNa .NumEma lOpen,
    FeatureNa .NumEma lCl ck,
  )

  val LabelSet = (LabelExpl c  ++ Label mpl c ).map(_.value)

  def fromFollowEvent(f: FollowEvent): Opt on[EdgeLabel] = {
    for {
      src d <- f.s ce d
      dest d <- f.target d
    } y eld EdgeLabel(src d, dest d, labels = Set(FeatureNa .NumFollows))
  }

  def from nteract onGraphEdge(e: TEdge): Opt on[EdgeLabel] = {
    val labels = e.features.collect {
      case EdgeFeature(featureNa : FeatureNa , _)  f LabelSet.conta ns(featureNa .value) =>
        Sc o tr cs.counter("from nteract onGraphEdge", featureNa .toStr ng). nc()
        featureNa 
    }.toSet
     f (labels.nonEmpty) {
      So (EdgeLabel(e.s ce d, e.dest nat on d, labels))
    } else None
  }

  def toTEdge(e: EdgeLabel): EdgeLabel = {
    EdgeLabel(e.s ce d, e.dest nat on d, labels = e.labels)
  }
}
