package com.tw ter. nteract on_graph.sc o.common

 mport com.spot fy.sc o.Sc o tr cs
 mport com.tw ter. nteract on_graph.thr ftscala.Edge
 mport com.tw ter. nteract on_graph.thr ftscala.FeatureNa 
 mport com.tw ter. nteract on_graph.thr ftscala.T  Ser esStat st cs
 mport com.tw ter.t  l nes.real_graph.v1.thr ftscala.RealGraphEdgeFeatures
 mport com.tw ter.t  l nes.real_graph.v1.thr ftscala.{
  RealGraphEdgeFeature => RealGraphEdgeFeatureV1
}

object Convers onUt l {
  def toRealGraphEdgeFeatureV1(tss: T  Ser esStat st cs): RealGraphEdgeFeatureV1 = {
    RealGraphEdgeFeatureV1(
       an = So (tss. an),
      ewma = So (tss.ewma),
      m2ForVar ance = So (tss.m2ForVar ance),
      daysS nceLast = tss.numDaysS nceLast.map(_.toShort),
      nonZeroDays = So (tss.numNonZeroDays.toShort),
      elapsedDays = So (tss.numElapsedDays.toShort),
       sM ss ng = So (false)
    )
  }

  /**
   * C cks  f t  converted `RealGraphEdgeFeatures` has negat ve edges features.
   *   p pel ne  ncludes ot r negat ve  nteract ons that aren't  n t  UserSess on thr ft
   * so  'll just f lter t m away for now (for par y).
   */
  def hasNegat veFeatures(rgef: RealGraphEdgeFeatures): Boolean = {
    rgef.numMutes.nonEmpty ||
    rgef.numBlocks.nonEmpty ||
    rgef.numReportAsAbuses.nonEmpty ||
    rgef.numReportAsSpams.nonEmpty
  }

  /**
   * C cks  f t  converted `RealGraphEdgeFeatures` has so  of t  key  nteract on features present.
   * T   s adapted from t  l ne's code  re:
   */
  def hasT  l nesRequ redFeatures(rgef: RealGraphEdgeFeatures): Boolean = {
    rgef.ret etsFeature.nonEmpty ||
    rgef.favsFeature.nonEmpty ||
    rgef. nt onsFeature.nonEmpty ||
    rgef.t etCl cksFeature.nonEmpty ||
    rgef.l nkCl cksFeature.nonEmpty ||
    rgef.prof leV ewsFeature.nonEmpty ||
    rgef.d llT  Feature.nonEmpty ||
    rgef. nspectedStatusesFeature.nonEmpty ||
    rgef.photoTagsFeature.nonEmpty ||
    rgef.numT etQuotes.nonEmpty ||
    rgef.followFeature.nonEmpty ||
    rgef.mutualFollowFeature.nonEmpty ||
    rgef.addressBookEma lFeature.nonEmpty ||
    rgef.addressBookPhoneFeature.nonEmpty
  }

  /**
   * Convert an Edge  nto a RealGraphEdgeFeature.
   *   return t  converted RealGraphEdgeFeature w n f lterFn  s true.
   * T   s to allow us to f lter early on dur ng t  convers on  f requ red, rat r than map over t  whole
   * collect on of records aga n to f lter.
   *
   * @param f lterFn true  f and only  f   want to keep t  converted feature
   */
  def toRealGraphEdgeFeatures(
    f lterFn: RealGraphEdgeFeatures => Boolean
  )(
    e: Edge
  ): Opt on[RealGraphEdgeFeatures] = {
    val baseFeature = RealGraphEdgeFeatures(dest d = e.dest nat on d)
    val aggregatedFeature = e.features.foldLeft(baseFeature) {
      case (aggregatedFeature, edgeFeature) =>
        val f = So (toRealGraphEdgeFeatureV1(edgeFeature.tss))
        Sc o tr cs.counter("toRealGraphEdgeFeatures", edgeFeature.na .na ). nc()
        edgeFeature.na  match {
          case FeatureNa .NumRet ets => aggregatedFeature.copy(ret etsFeature = f)
          case FeatureNa .NumFavor es => aggregatedFeature.copy(favsFeature = f)
          case FeatureNa .Num nt ons => aggregatedFeature.copy( nt onsFeature = f)
          case FeatureNa .NumT etCl cks => aggregatedFeature.copy(t etCl cksFeature = f)
          case FeatureNa .NumL nkCl cks => aggregatedFeature.copy(l nkCl cksFeature = f)
          case FeatureNa .NumProf leV ews => aggregatedFeature.copy(prof leV ewsFeature = f)
          case FeatureNa .TotalD llT   => aggregatedFeature.copy(d llT  Feature = f)
          case FeatureNa .Num nspectedStatuses =>
            aggregatedFeature.copy( nspectedStatusesFeature = f)
          case FeatureNa .NumPhotoTags => aggregatedFeature.copy(photoTagsFeature = f)
          case FeatureNa .NumFollows => aggregatedFeature.copy(followFeature = f)
          case FeatureNa .NumMutualFollows => aggregatedFeature.copy(mutualFollowFeature = f)
          case FeatureNa .AddressBookEma l => aggregatedFeature.copy(addressBookEma lFeature = f)
          case FeatureNa .AddressBookPhone => aggregatedFeature.copy(addressBookPhoneFeature = f)
          case FeatureNa .AddressBook nBoth => aggregatedFeature.copy(addressBook nBothFeature = f)
          case FeatureNa .AddressBookMutualEdgeEma l =>
            aggregatedFeature.copy(addressBookMutualEdgeEma lFeature = f)
          case FeatureNa .AddressBookMutualEdgePhone =>
            aggregatedFeature.copy(addressBookMutualEdgePhoneFeature = f)
          case FeatureNa .AddressBookMutualEdge nBoth =>
            aggregatedFeature.copy(addressBookMutualEdge nBothFeature = f)
          case FeatureNa .NumT etQuotes => aggregatedFeature.copy(numT etQuotes = f)
          case FeatureNa .NumBlocks => aggregatedFeature.copy(numBlocks = f)
          case FeatureNa .NumMutes => aggregatedFeature.copy(numMutes = f)
          case FeatureNa .NumReportAsSpams => aggregatedFeature.copy(numReportAsSpams = f)
          case FeatureNa .NumReportAsAbuses => aggregatedFeature.copy(numReportAsAbuses = f)
          case _ => aggregatedFeature
        }
    }
     f (f lterFn(aggregatedFeature))
      So (aggregatedFeature.copy(  ght = e.  ght.orElse(So (0.0))))
    else None
  }
}
