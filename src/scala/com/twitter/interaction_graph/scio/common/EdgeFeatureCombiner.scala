package com.tw ter. nteract on_graph.sc o.common

 mport com.spot fy.sc o.Sc o tr cs
 mport com.tw ter. nteract on_graph.thr ftscala.Edge
 mport com.tw ter. nteract on_graph.thr ftscala.EdgeFeature
 mport com.tw ter. nteract on_graph.thr ftscala.FeatureNa 
 mport com.tw ter. nteract on_graph.thr ftscala.T  Ser esStat st cs

object EdgeFeatureComb ner {
  def apply(src d: Long, dest d: Long): EdgeFeatureComb ner = new EdgeFeatureComb ner(
     nstanceEdge = Edge(src d, dest d),
    featureMap = Map(
      FeatureNa .NumRet ets -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .NumFavor es -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .Num nt ons -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .NumT etCl cks -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .NumL nkCl cks -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .NumProf leV ews -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .NumFollows -> new BooleanOrEdgeComb ner,
      FeatureNa .NumUnfollows -> new BooleanOrEdgeComb ner,
      FeatureNa .NumMutualFollows -> new BooleanOrEdgeComb ner,
      FeatureNa .NumBlocks -> new BooleanOrEdgeComb ner,
      FeatureNa .NumMutes -> new BooleanOrEdgeComb ner,
      FeatureNa .NumReportAsAbuses -> new BooleanOrEdgeComb ner,
      FeatureNa .NumReportAsSpams -> new BooleanOrEdgeComb ner,
      FeatureNa .NumT etQuotes -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .AddressBookEma l -> new BooleanOrEdgeComb ner,
      FeatureNa .AddressBookPhone -> new BooleanOrEdgeComb ner,
      FeatureNa .AddressBook nBoth -> new BooleanOrEdgeComb ner,
      FeatureNa .AddressBookMutualEdgeEma l -> new BooleanOrEdgeComb ner,
      FeatureNa .AddressBookMutualEdgePhone -> new BooleanOrEdgeComb ner,
      FeatureNa .AddressBookMutualEdge nBoth -> new BooleanOrEdgeComb ner,
      FeatureNa .TotalD llT   -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .Num nspectedStatuses -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .NumPhotoTags -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .NumPushOpens -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .NumNtabCl cks -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .NumRt nt ons -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .NumRtRepl es -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .NumRtRet ets -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .NumRtFavor es -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .NumRtL nkCl cks -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .NumRtT etCl cks -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .NumRtT etQuotes -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .NumShares -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .NumEma lOpen -> new   ghtedAdd  veEdgeComb ner,
      FeatureNa .NumEma lCl ck -> new   ghtedAdd  veEdgeComb ner,
    )
  )
}

/**
 * T  class can take  n a number of  nput Edge thr ft objects, (all of wh ch are assu d to
 * conta n  nformat on about a s ngle edge) and bu lds a comb ned Edge protobuf object, wh ch has
 * t  un on of all t   nput.
 * <p>
 * T re are two modes of aggregat on: one of t m just adds t  values  n assum ng that t se are
 * from t  sa  day, and t  ot r adds t m  n a t  -decayed manner us ng t  passed  n   ghts.
 * <p>
 * T   nput objects features must be d sjo nt. Also, re mber that t  edge  s d rected!
 */
class EdgeFeatureComb ner( nstanceEdge: Edge, featureMap: Map[FeatureNa , EFeatureComb ner]) {

  /**
   * Adds features w hout any decay. To be used for t  sa  day.
   *
   * @param edge edge to be added  nto t  comb ner
   */
  def addFeature(edge: Edge): EdgeFeatureComb ner = {

    val newEdge =
       f (edge.  ght. sDef ned)  nstanceEdge.copy(  ght = edge.  ght) else  nstanceEdge
    val newFeatures = featureMap.map {
      case (featureNa , comb ner) =>
        edge.features.f nd(_.na .equals(featureNa )) match {
          case So (feature) =>
            val updatedComb ner =
               f (comb ner. sSet) comb ner.updateFeature(feature) else comb ner.setFeature(feature)
            (featureNa , updatedComb ner)
          case _ => (featureNa , comb ner)
        }
    }

    new EdgeFeatureComb ner(newEdge, newFeatures)

  }

  /**
   * Adds features w h decays. Used for comb n ng mult ple days.
   *
   * @param edge  edge to be added  nto t  comb ner
   * @param alpha para ters for t  decay calculat on
   * @param day   number of days from today
   */
  def addFeature(edge: Edge, alpha: Double, day:  nt): EdgeFeatureComb ner = {

    val newEdge =  f (edge.  ght. sDef ned) edge.copy(  ght = edge.  ght) else edge
    val newFeatures = featureMap.map {
      case (featureNa , comb ner) =>
        edge.features.f nd(_.na .equals(featureNa )) match {
          case So (feature) =>
            val updatedComb ner =
               f (comb ner. sSet) comb ner.updateFeature(feature, alpha, day)
              else comb ner.setFeature(feature, alpha, day)
            Sc o tr cs.counter("EdgeFeatureComb ner.addFeature", feature.na .na ). nc()
            (featureNa , updatedComb ner)
          case _ => (featureNa , comb ner)
        }
    }
    new EdgeFeatureComb ner(newEdge, newFeatures)
  }

  /**
   * Generate t  f nal comb ned Edge  nstance
   *   return a determ n st cally sorted l st of edge features
   *
   * @param totalDays total number of days to be comb ned toget r
   */
  def getComb nedEdge(totalDays:  nt): Edge = {
    val moreFeatures = featureMap.values
      .flatMap { comb ner =>
        comb ner.getF nalFeature(totalDays)
      }.toL st.sortBy(_.na .value)
     nstanceEdge.copy(
      features = moreFeatures
    )
  }

}

/**
 * T  port on conta ns t  actual comb nat on log c. For now,   only  mple nt a s mple
 * add  ve comb ner, but  n future  'd l ke to have th ngs l ke t  -  ghted (exponent al
 * decay, maybe) values.
 */

tra  EFeatureComb ner {
  val edgeFeature: Opt on[EdgeFeature]
  val start ngDay:  nt
  val end ngDay:  nt
  val t  Ser esStat st cs: Opt on[T  Ser esStat st cs]

  def updateTSS(feature: EdgeFeature, alpha: Double): Opt on[T  Ser esStat st cs]

  def addToTSS(feature: EdgeFeature): Opt on[T  Ser esStat st cs]

  def updateFeature(feature: EdgeFeature): EFeatureComb ner

  def updateFeature(feature: EdgeFeature, alpha: Double, day:  nt): EFeatureComb ner

  def  sSet: Boolean

  def dropFeature: Boolean

  def setFeature(feature: EdgeFeature, alpha: Double, day:  nt): EFeatureComb ner

  def setFeature(feature: EdgeFeature): EFeatureComb ner

  def getF nalFeature(totalDays:  nt): Opt on[EdgeFeature]

}

case class   ghtedAdd  veEdgeComb ner(
  overr de val edgeFeature: Opt on[EdgeFeature] = None,
  overr de val start ngDay:  nt =  nteger.MAX_VALUE,
  overr de val end ngDay:  nt =  nteger.M N_VALUE,
  overr de val t  Ser esStat st cs: Opt on[T  Ser esStat st cs] = None)
    extends EFeatureComb ner {

  overr de def updateTSS(
    feature: EdgeFeature,
    alpha: Double
  ): Opt on[T  Ser esStat st cs] = {
    t  Ser esStat st cs.map(tss =>
       nteract onGraphUt ls.updateT  Ser esStat st cs(tss, feature.tss. an, alpha))
  }

  overr de def addToTSS(feature: EdgeFeature): Opt on[T  Ser esStat st cs] = {
    t  Ser esStat st cs.map(tss =>
       nteract onGraphUt ls.addToT  Ser esStat st cs(tss, feature.tss. an))
  }

  overr de def updateFeature(feature: EdgeFeature):   ghtedAdd  veEdgeComb ner = {
      ghtedAdd  veEdgeComb ner(
      edgeFeature,
      start ngDay,
      end ngDay,
      addToTSS(feature)
    )
  }

  def setFeature(feature: EdgeFeature, alpha: Double, day:  nt):   ghtedAdd  veEdgeComb ner = {
    val newStart ngDay = Math.m n(start ngDay, day)
    val newEnd ngDay = Math.max(end ngDay, day)

    val numDaysS nceLast =
       f (feature.tss.numDaysS nceLast.ex sts(_ > 0))
        feature.tss.numDaysS nceLast
      else So (feature.tss.numElapsedDays - feature.tss.numNonZeroDays + 1)

    val tss = feature.tss.copy(
      numDaysS nceLast = numDaysS nceLast,
      ewma = alpha * feature.tss.ewma
    )

    val newFeature = EdgeFeature(
      na  = feature.na ,
      tss = tss
    )

      ghtedAdd  veEdgeComb ner(
      So (newFeature),
      newStart ngDay,
      newEnd ngDay,
      So (tss)
    )
  }

  def getF nalFeature(totalDays:  nt): Opt on[EdgeFeature] = {
     f (edgeFeature. sEmpty || dropFeature) return None

    val newTss =  f (totalDays > 0) {
      val elapsed =
        t  Ser esStat st cs.map(tss => tss.numElapsedDays + totalDays - 1 - start ngDay)

      val latest =
         f (end ngDay > 0) So (totalDays - end ngDay)
        else
          t  Ser esStat st cs.flatMap(tss =>
            tss.numDaysS nceLast.map(numDaysS nceLast => numDaysS nceLast + totalDays - 1))

      t  Ser esStat st cs.map(tss =>
        tss.copy(
          numElapsedDays = elapsed.get,
          numDaysS nceLast = latest
        ))
    } else t  Ser esStat st cs

    edgeFeature.map(ef => ef.copy(tss = newTss.get))
  }

  overr de def updateFeature(
    feature: EdgeFeature,
    alpha: Double,
    day:  nt
  ):   ghtedAdd  veEdgeComb ner = copy(
    end ngDay = Math.max(end ngDay, day),
    t  Ser esStat st cs = updateTSS(feature, alpha)
  )

  overr de def dropFeature: Boolean = t  Ser esStat st cs.ex sts(tss =>
    tss.numDaysS nceLast.ex sts(_ >  nteract onGraphUt ls.MAX_DAYS_RETENT ON) ||
      tss.ewma <  nteract onGraphUt ls.M N_FEATURE_VALUE)

  overr de def  sSet = edgeFeature. sDef ned

  overr de def setFeature(feature: EdgeFeature):   ghtedAdd  veEdgeComb ner =
    setFeature(feature, 1.0, 0)

}

/**
 * T  comb ner resets t  value to 0  f t  latest event be ng comb ned = 0.  gnores t   decays.
 */
case class BooleanOrEdgeComb ner(
  overr de val edgeFeature: Opt on[EdgeFeature] = None,
  overr de val start ngDay:  nt =  nteger.MAX_VALUE,
  overr de val end ngDay:  nt =  nteger.M N_VALUE,
  overr de val t  Ser esStat st cs: Opt on[T  Ser esStat st cs] = None)
    extends EFeatureComb ner {

  overr de def updateTSS(
    feature: EdgeFeature,
    alpha: Double
  ): Opt on[T  Ser esStat st cs] = {
    val value = t  Ser esStat st cs.map(tss => Math.floor(tss.ewma))
    val newValue =  f (value.ex sts(_ == 1.0) || feature.tss. an > 0.0) 1.0 else 0.0
    t  Ser esStat st cs.map(tss =>
      tss.copy(
         an = newValue,
        ewma = newValue,
        numNonZeroDays = tss.numNonZeroDays + 1
      ))
  }

  overr de def addToTSS(feature: EdgeFeature): Opt on[T  Ser esStat st cs] = {
    val value = t  Ser esStat st cs.map(tss => Math.floor(tss.ewma))
    val newValue =  f (value.ex sts(_ == 1.0) || feature.tss. an > 0.0) 1.0 else 0.0
    t  Ser esStat st cs.map(tss => tss.copy( an = newValue, ewma = newValue))
  }

  overr de def updateFeature(feature: EdgeFeature): BooleanOrEdgeComb ner = BooleanOrEdgeComb ner(
    edgeFeature,
    start ngDay,
    end ngDay,
    addToTSS(feature)
  )

  def setFeature(feature: EdgeFeature, alpha: Double, day:  nt): BooleanOrEdgeComb ner = {
    val newStart ngDay = Math.m n(start ngDay, day)
    val newEnd ngDay = Math.max(end ngDay, day)

    val numDaysS nceLast =
       f (feature.tss.numDaysS nceLast.ex sts(_ > 0))
        feature.tss.numDaysS nceLast.get
      else feature.tss.numElapsedDays - feature.tss.numNonZeroDays + 1

    val tss = feature.tss.copy(
      numDaysS nceLast = So (numDaysS nceLast),
      ewma = alpha * feature.tss.ewma
    )

    val newFeature = EdgeFeature(
      na  = feature.na ,
      tss = tss
    )

    BooleanOrEdgeComb ner(
      So (newFeature),
      newStart ngDay,
      newEnd ngDay,
      So (tss)
    )
  }

  overr de def getF nalFeature(totalDays:  nt): Opt on[EdgeFeature] =
     f (t  Ser esStat st cs.ex sts(tss => tss.ewma < 1.0)) None
    else {
       f (edgeFeature. sEmpty || dropFeature) return None
      edgeFeature.map(ef =>
        ef.copy(
          tss = t  Ser esStat st cs.get
        ))
    }

  overr de def updateFeature(
    feature: EdgeFeature,
    alpha: Double,
    day:  nt
  ): BooleanOrEdgeComb ner = copy(
    end ngDay = Math.max(end ngDay, day),
    t  Ser esStat st cs = updateTSS(feature, alpha)
  )

  overr de def dropFeature: Boolean = false //   w ll keep roll ng up status-based features

  overr de def  sSet = edgeFeature. sDef ned

  overr de def setFeature(feature: EdgeFeature): BooleanOrEdgeComb ner = setFeature(feature, 1.0, 0)
}
