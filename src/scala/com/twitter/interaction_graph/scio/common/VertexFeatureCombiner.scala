package com.tw ter. nteract on_graph.sc o.common

 mport com.tw ter. nteract on_graph.thr ftscala.FeatureNa 
 mport com.tw ter. nteract on_graph.thr ftscala.T  Ser esStat st cs
 mport com.tw ter. nteract on_graph.thr ftscala.Vertex
 mport com.tw ter. nteract on_graph.thr ftscala.VertexFeature

object VertexFeatureComb ner {
  def apply(user d: Long): VertexFeatureComb ner = new VertexFeatureComb ner(
     nstanceVertex = Vertex(user d),
    featureMap = Map(
      (FeatureNa .NumRet ets, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumRet ets, false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumFavor es, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumFavor es, false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .Num nt ons, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .Num nt ons, false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumT etCl cks, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumT etCl cks, false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumL nkCl cks, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumL nkCl cks, false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumProf leV ews, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumProf leV ews, false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumFollows, true) -> new Replace ntVertexComb ner,
      (FeatureNa .NumFollows, false) -> new Replace ntVertexComb ner,
      (FeatureNa .NumUnfollows, true) -> new Replace ntVertexComb ner,
      (FeatureNa .NumUnfollows, false) -> new Replace ntVertexComb ner,
      (FeatureNa .NumMutualFollows, true) -> new Replace ntVertexComb ner,
      (FeatureNa .NumBlocks, true) -> new Replace ntVertexComb ner,
      (FeatureNa .NumBlocks, false) -> new Replace ntVertexComb ner,
      (FeatureNa .NumMutes, true) -> new Replace ntVertexComb ner,
      (FeatureNa .NumMutes, false) -> new Replace ntVertexComb ner,
      (FeatureNa .NumReportAsAbuses, true) -> new Replace ntVertexComb ner,
      (FeatureNa .NumReportAsAbuses, false) -> new Replace ntVertexComb ner,
      (FeatureNa .NumReportAsSpams, true) -> new Replace ntVertexComb ner,
      (FeatureNa .NumReportAsSpams, false) -> new Replace ntVertexComb ner,
      (FeatureNa .NumT etQuotes, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumT etQuotes, false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumMutualFollows, false) -> new Replace ntVertexComb ner,
      (FeatureNa .AddressBookEma l, true) -> new Replace ntVertexComb ner,
      (FeatureNa .AddressBookEma l, false) -> new Replace ntVertexComb ner,
      (FeatureNa .AddressBookPhone, true) -> new Replace ntVertexComb ner,
      (FeatureNa .AddressBookPhone, false) -> new Replace ntVertexComb ner,
      (FeatureNa .AddressBook nBoth, true) -> new Replace ntVertexComb ner,
      (FeatureNa .AddressBook nBoth, false) -> new Replace ntVertexComb ner,
      (FeatureNa .AddressBookMutualEdgeEma l, true) -> new Replace ntVertexComb ner,
      (FeatureNa .AddressBookMutualEdgeEma l, false) -> new Replace ntVertexComb ner,
      (FeatureNa .AddressBookMutualEdgePhone, true) -> new Replace ntVertexComb ner,
      (FeatureNa .AddressBookMutualEdgePhone, false) -> new Replace ntVertexComb ner,
      (FeatureNa .AddressBookMutualEdge nBoth, true) -> new Replace ntVertexComb ner,
      (FeatureNa .AddressBookMutualEdge nBoth, false) -> new Replace ntVertexComb ner,
      (FeatureNa .TotalD llT  , true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .TotalD llT  , false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .Num nspectedStatuses, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .Num nspectedStatuses, false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumPhotoTags, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumPhotoTags, false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumPushOpens, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumPushOpens, false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumNtabCl cks, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumNtabCl cks, false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumRtFavor es, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumRtFavor es, false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumRtT etQuotes, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumRtT etQuotes, false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumRtT etCl cks, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumRtT etCl cks, false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumRtRet ets, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumRtRet ets, false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumRtRepl es, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumRtRepl es, false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumRtL nkCl cks, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumRtL nkCl cks, false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumRt nt ons, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumRt nt ons, false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumShares, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumShares, false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumEma lOpen, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumEma lOpen, false) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumEma lCl ck, true) -> new   ghtedAdd  veVertexComb ner,
      (FeatureNa .NumEma lCl ck, false) -> new   ghtedAdd  veVertexComb ner,
    )
  )
}

/**
 * T  class can take  n a number of  nput Vertex thr ft objects (all of wh ch are assu d to
 * conta n  nformat on about a s ngle vertex) and bu lds a comb ned Vertex protobuf object, wh ch
 * has t  un on of all t   nput. Note that   do a   ghted add  on for a t  -decayed value.
 * <p>
 * T   nput objects features must be d sjo nt. Also, re mber that t  Vertex  s d rected!
 */
class VertexFeatureComb ner(
   nstanceVertex: Vertex,
  featureMap: Map[(FeatureNa , Boolean), VFeatureComb ner]) {

  /**
   * Adds features w hout any decay. To be used for t  sa  day.
   *
   * @param vertex vertex to be added  nto t  comb ner
   */
  def addFeature(vertex: Vertex): VertexFeatureComb ner = {
    val newVertex =  nstanceVertex.copy(  ght = vertex.  ght)
    val newFeatures = featureMap.map {
      case ((featureNa , outgo ng), comb ner) =>
        vertex.features.f nd(f => f.na .equals(featureNa ) && f.outgo ng.equals(outgo ng)) match {
          case So (feature) =>
            val updatedComb ner =
               f (comb ner. sSet) comb ner.updateFeature(feature) else comb ner.setFeature(feature)
            ((featureNa , outgo ng), updatedComb ner)
          case _ => ((featureNa , outgo ng), comb ner)
        }
    }

    new VertexFeatureComb ner(newVertex, newFeatures)
  }

  /**
   * Adds features w h decays. Used for comb n ng mult ple days.
   *
   * @param vertex vertex to be added  nto t  comb ner
   * @param alpha  para ters for t  decay calculat on
   * @param day    number of days from today
   */
  def addFeature(vertex: Vertex, alpha: Double, day:  nt): VertexFeatureComb ner = {

    val newVertex =  nstanceVertex.copy(  ght = vertex.  ght)
    val newFeatures = featureMap.map {
      case ((featureNa , outgo ng), comb ner) =>
        vertex.features.f nd(f => f.na .equals(featureNa ) && f.outgo ng.equals(outgo ng)) match {
          case So (feature) =>
            val updatedComb ner =
               f (comb ner. sSet) comb ner.updateFeature(feature, alpha, day)
              else comb ner.setFeature(feature, alpha, day)
            ((featureNa , outgo ng), updatedComb ner)
          case _ => ((featureNa , outgo ng), comb ner)
        }
    }

    new VertexFeatureComb ner(newVertex, newFeatures)
  }

  /**
   * Generate t  f nal comb ned Vertex  nstance
   *
   * @param totalDays total number of days to be comb ned toget r
   */
  def getComb nedVertex(totalDays:  nt): Vertex = {
    val moreFeatures = featureMap.values.flatMap {
      case comb ner => comb ner.getF nalFeature(totalDays)
    }
     nstanceVertex.copy(features = moreFeatures.toSeq)
  }

}

/**
 * T  port on conta ns t  actual comb nat on log c. For now,   only  mple nt a s mple
 * add  ve comb ner, but  n future  'd l ke to have th ngs l ke t  -  ghted (exponent al
 * decay, maybe) values.
 */
tra  VFeatureComb ner {
  val start ngDay:  nt
  val end ngDay:  nt
  val t  Ser esStat st cs: Opt on[T  Ser esStat st cs]
  val vertexFeature: Opt on[VertexFeature]

  def updateTss(feature: VertexFeature, alpha: Double): VFeatureComb ner
  def addToTss(feature: VertexFeature): VFeatureComb ner
  def updateFeature(feature: VertexFeature, alpha: Double, day:  nt): VFeatureComb ner
  def updateFeature(feature: VertexFeature): VFeatureComb ner
  def  sSet: Boolean
  def dropFeature: Boolean
  def setFeature(feature: VertexFeature, alpha: Double, day:  nt): VFeatureComb ner
  def setFeature(feature: VertexFeature): VFeatureComb ner
  def getF nalFeature(totalDays:  nt): Opt on[VertexFeature]
}

case class   ghtedAdd  veVertexComb ner(
  overr de val vertexFeature: Opt on[VertexFeature] = None,
  overr de val start ngDay:  nt =  nteger.MAX_VALUE,
  overr de val end ngDay:  nt =  nteger.M N_VALUE,
  overr de val t  Ser esStat st cs: Opt on[T  Ser esStat st cs] = None)
    extends VFeatureComb ner {
  overr de def updateTss(
    feature: VertexFeature,
    alpha: Double
  ):   ghtedAdd  veVertexComb ner = copy(t  Ser esStat st cs = t  Ser esStat st cs.map(tss =>
     nteract onGraphUt ls.updateT  Ser esStat st cs(tss, feature.tss. an, alpha)))

  overr de def addToTss(feature: VertexFeature):   ghtedAdd  veVertexComb ner =
    copy(t  Ser esStat st cs = t  Ser esStat st cs.map(tss =>
       nteract onGraphUt ls.addToT  Ser esStat st cs(tss, feature.tss. an)))

  overr de def updateFeature(feature: VertexFeature, alpha: Double, day:  nt): VFeatureComb ner = {
    updateTss(feature, alpha).copy(
      vertexFeature,
      start ngDay = start ngDay,
      end ngDay = Math.max(end ngDay, day)
    )
  }

  overr de def updateFeature(feature: VertexFeature): VFeatureComb ner =
    addToTss(feature)

  overr de def setFeature(feature: VertexFeature, alpha: Double, day:  nt): VFeatureComb ner = {
    val newStart ngDay = Math.m n(start ngDay, day)
    val newEnd ngDay = Math.max(end ngDay, day)

    val numDaysS nceLast =
       f (feature.tss.numDaysS nceLast.ex sts(_ > 0))
        feature.tss.numDaysS nceLast
      else So (feature.tss.numElapsedDays - feature.tss.numNonZeroDays + 1)

    val tss = feature.tss.copy(numDaysS nceLast = numDaysS nceLast)

    val newFeature = VertexFeature(
      na  = feature.na ,
      outgo ng = feature.outgo ng,
      tss = tss
    )

      ghtedAdd  veVertexComb ner(
      So (newFeature),
      newStart ngDay,
      newEnd ngDay,
      So (tss)
    )
  }

  def getF nalFeature(totalDays:  nt): Opt on[VertexFeature] = {
     f (vertexFeature. sEmpty || dropFeature) return None

    val newTss =  f (totalDays > 0) {
      val elapsed =
        t  Ser esStat st cs.map(tss => tss.numElapsedDays + totalDays - 1 - start ngDay)
      val latest =
         f (end ngDay > 0) So (totalDays - end ngDay)
        else t  Ser esStat st cs.map(tss => tss.numDaysS nceLast.get + totalDays - 1)

      t  Ser esStat st cs.map(tss =>
        tss.copy(
          numElapsedDays = elapsed.get,
          numDaysS nceLast = latest
        ))
    } else t  Ser esStat st cs

    vertexFeature.map(vf => vf.copy(tss = newTss.get))
  }

  overr de def setFeature(feature: VertexFeature): VFeatureComb ner = setFeature(feature, 1.0, 0)
  overr de def  sSet: Boolean = vertexFeature. sDef ned
  overr de def dropFeature: Boolean =
    t  Ser esStat st cs.ex sts(tss =>
      tss.numDaysS nceLast.ex sts(_ >  nteract onGraphUt ls.MAX_DAYS_RETENT ON) &&
        tss.ewma <  nteract onGraphUt ls.M N_FEATURE_VALUE)
}

/**
 * T  comb ner always replaces t  old value w h t  current.  gnores t  -decays.
 */
case class Replace ntVertexComb ner(
  overr de val vertexFeature: Opt on[VertexFeature] = None,
  overr de val start ngDay:  nt =  nteger.MAX_VALUE,
  overr de val end ngDay:  nt =  nteger.M N_VALUE,
  overr de val t  Ser esStat st cs: Opt on[T  Ser esStat st cs] = None)
    extends VFeatureComb ner {
  overr de def updateTss(
    feature: VertexFeature,
    alpha: Double
  ): Replace ntVertexComb ner = setFeature(feature, 1.0, 0)

  overr de def addToTss(feature: VertexFeature): Replace ntVertexComb ner =
    setFeature(feature, 1.0, 0)

  overr de def updateFeature(
    feature: VertexFeature,
    alpha: Double,
    day:  nt
  ): Replace ntVertexComb ner = updateTss(feature, alpha).copy(
    vertexFeature,
    start ngDay = start ngDay,
    end ngDay = Math.max(end ngDay, day)
  )

  overr de def updateFeature(feature: VertexFeature): Replace ntVertexComb ner =
    addToTss(feature)

  overr de def setFeature(
    feature: VertexFeature,
    alpha: Double,
    day:  nt
  ): Replace ntVertexComb ner = {
    val newStart ngDay = Math.m n(start ngDay, day)
    val newEnd ngDay = Math.max(end ngDay, day)

    val numDaysS nceLast =
       f (feature.tss.numDaysS nceLast.ex sts(_ > 0))
        feature.tss.numDaysS nceLast
      else So (feature.tss.numElapsedDays - feature.tss.numNonZeroDays + 1)

    val tss = feature.tss.copy(numDaysS nceLast = numDaysS nceLast)

    val newFeature = VertexFeature(
      na  = feature.na ,
      outgo ng = feature.outgo ng,
      tss = tss
    )

    Replace ntVertexComb ner(
      So (newFeature),
      newStart ngDay,
      newEnd ngDay,
      So (tss)
    )
  }

  overr de def getF nalFeature(totalDays:  nt): Opt on[VertexFeature] = {
     f (vertexFeature. sEmpty || dropFeature) return None
     f (t  Ser esStat st cs.ex sts(tss => tss.ewma < 1.0)) return None
    val newTss =  f (totalDays > 0) {
      val latest =
         f (end ngDay > 0) totalDays - end ngDay
        else t  Ser esStat st cs.get.numDaysS nceLast.get + totalDays - 1

      t  Ser esStat st cs.map(tss =>
        tss.copy(
          numElapsedDays = 1,
          numDaysS nceLast = So (latest)
        ))
    } else t  Ser esStat st cs

    vertexFeature.map(vf => vf.copy(tss = newTss.get))
  }

  overr de def setFeature(feature: VertexFeature): VFeatureComb ner = setFeature(feature, 1.0, 0)
  overr de def  sSet: Boolean = vertexFeature. sDef ned
  overr de def dropFeature: Boolean =
    t  Ser esStat st cs.ex sts(tss =>
      tss.numDaysS nceLast.ex sts(_ >  nteract onGraphUt ls.MAX_DAYS_RETENT ON) &&
        tss.ewma <  nteract onGraphUt ls.M N_FEATURE_VALUE)
}
