package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work

 mport com.tw ter.ml.ap ._
 mport com.tw ter.ml.ap .constant.SharedFeatures
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.AggregateFeature
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.Aggregat on tr c
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.Aggregat on tr cCommon
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.Aggregat on tr cCommon._
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.transforms.OneToSo Transform
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Try
 mport java.lang.{Boolean => JBoolean}
 mport java.lang.{Double => JDouble}
 mport java.lang.{Long => JLong}
 mport java.ut l.{Set => JSet}
 mport scala.annotat on.ta lrec
 mport scala.language.ex stent als
 mport scala.collect on.JavaConverters._
 mport scala.ut l.match ng.Regex

/**
 * A case class conta ned precomputed data useful to qu ckly
 * process operat ons over an aggregate.
 *
 * @param query T  underly ng feature be ng aggregated
 * @param  tr c T  aggregat on  tr c
 * @param outputFeatures T  output features that aggregat on w ll produce
 * @param outputFeature ds T  precomputed has s of t  above outputFeatures
 */
case class PrecomputedAggregateDescr ptor[T](
  query: AggregateFeature[T],
   tr c: Aggregat on tr c[T, _],
  outputFeatures: L st[Feature[_]],
  outputFeature ds: L st[JLong])

object TypedAggregateGroup {

  /**
   * Recurs ve funct on that generates all comb nat ons of value
   * ass gn nts for a collect on of sparse b nary features.
   *
   * @param sparseB nary dValues l st of sparse b nary feature  ds and poss ble values t y can take
   * @return A set of maps, w re each map represents one poss ble ass gn nt of values to  ds
   */
  def sparseB naryPermutat ons(
    sparseB nary dValues: L st[(Long, Set[Str ng])]
  ): Set[Map[Long, Str ng]] = sparseB nary dValues match {
    case ( d, values) +: rest =>
      ta lRecSparseB naryPermutat ons(
        ex st ngPermutat ons = values.map(value => Map( d -> value)),
        rema n ng dValues = rest
      )
    case N l => Set.empty
  }

  @ta lrec pr vate[t ] def ta lRecSparseB naryPermutat ons(
    ex st ngPermutat ons: Set[Map[Long, Str ng]],
    rema n ng dValues: L st[(Long, Set[Str ng])]
  ): Set[Map[Long, Str ng]] = rema n ng dValues match {
    case N l => ex st ngPermutat ons
    case ( d, values) +: rest =>
      ta lRecSparseB naryPermutat ons(
        ex st ngPermutat ons.flatMap { ex st ng dValueMap =>
          values.map(value => ex st ng dValueMap ++ Map( d -> value))
        },
        rest
      )
  }

  val SparseFeatureSuff x = ". mber"
  def sparseFeature(sparseB naryFeature: Feature[_]): Feature[Str ng] =
    new Feature.Text(
      sparseB naryFeature.getDenseFeatureNa  + SparseFeatureSuff x,
      Aggregat on tr cCommon.der vePersonalDataTypes(So (sparseB naryFeature)))

  /* Throws except on  f obj not an  nstance of U */
  pr vate[t ] def val date[U](obj: Any): U = {
    requ re(obj. s nstanceOf[U])
    obj.as nstanceOf[U]
  }

  pr vate[t ] def getFeatureOpt[U](dataRecord: DataRecord, feature: Feature[U]): Opt on[U] =
    Opt on(SR chDataRecord(dataRecord).getFeatureValue(feature)).map(val date[U](_))

  /**
   * Get a mapp ng from feature  ds
   * ( nclud ng  nd v dual sparse ele nts of a sparse feature) to values
   * from t  g ven data record, for a g ven feature type.
   *
   * @param dataRecord Data record to get features from
   * @param keysToAggregate key features to get  d-value mapp ngs for
   * @param featureType Feature type to get  d-value maps for
   */
  def getKeyFeature dValues[U](
    dataRecord: DataRecord,
    keysToAggregate: Set[Feature[_]],
    featureType: FeatureType
  ): Set[(Long, Opt on[U])] = {
    val featuresOfT Type: Set[Feature[U]] = keysToAggregate
      .f lter(_.getFeatureType == featureType)
      .map(val date[Feature[U]])

    featuresOfT Type
      .map { feature: Feature[U] =>
        val feature d: Long = getDenseFeature d(feature)
        val featureOpt: Opt on[U] = getFeatureOpt(dataRecord, feature)
        (feature d, featureOpt)
      }
  }

  // TypedAggregateGroup may transform t  aggregate keys for  nternal use. T   thod generates
  // denseFeature ds for t  transfor d feature.
  def getDenseFeature d(feature: Feature[_]): Long =
     f (feature.getFeatureType != FeatureType.SPARSE_B NARY) {
      feature.getDenseFeature d
    } else {
      sparseFeature(feature).getDenseFeature d
    }

  /**
   * Return denseFeature ds for t   nput features after apply ng t  custom transformat on that
   * TypedAggregateGroup appl es to  s keysToAggregate.
   *
   * @param keysToAggregate key features to get  d for
   */
  def getKeyFeature ds(keysToAggregate: Set[Feature[_]]): Set[Long] =
    keysToAggregate.map(getDenseFeature d)

  def c ck fAllKeysEx st[U](feature dValueMap: Map[Long, Opt on[U]]): Boolean =
    feature dValueMap.forall { case (_, valueOpt) => valueOpt. sDef ned }

  def l ftOpt ons[U](feature dValueMap: Map[Long, Opt on[U]]): Map[Long, U] =
    feature dValueMap
      .flatMap {
        case ( d, valueOpt) =>
          valueOpt.map { value => ( d, value) }
      }

  val t  stampFeature: Feature[JLong] = SharedFeatures.T MESTAMP

  /**
   * Bu lds all val d aggregat on keys (for t  output store) from
   * a datarecord and a spec l st ng t  keys to aggregate. T re
   * can be mult ple aggregat on keys generated from a s ngle data
   * record w n group ng by sparse b nary features, for wh ch mult ple
   * values can be set w h n t  data record.
   *
   * @param dataRecord Data record to read values for key features from
   * @return A set of Aggregat onKeys encod ng t  values of all keys
   */
  def bu ldAggregat onKeys(
    dataRecord: DataRecord,
    keysToAggregate: Set[Feature[_]]
  ): Set[Aggregat onKey] = {
    val d screteAggregat onKeys = getKeyFeature dValues[Long](
      dataRecord,
      keysToAggregate,
      FeatureType.D SCRETE
    ).toMap

    val textAggregat onKeys = getKeyFeature dValues[Str ng](
      dataRecord,
      keysToAggregate,
      FeatureType.STR NG
    ).toMap

    val sparseB nary dValues = getKeyFeature dValues[JSet[Str ng]](
      dataRecord,
      keysToAggregate,
      FeatureType.SPARSE_B NARY
    ).map {
      case ( d, values) =>
        (
           d,
          values
            .map(_.asScala.toSet)
            .getOrElse(Set.empty[Str ng])
        )
    }.toL st

     f (c ck fAllKeysEx st(d screteAggregat onKeys) &&
      c ck fAllKeysEx st(textAggregat onKeys)) {
       f (sparseB nary dValues.nonEmpty) {
        sparseB naryPermutat ons(sparseB nary dValues).map { sparseB naryTextKeys =>
          Aggregat onKey(
            d screteFeaturesBy d = l ftOpt ons(d screteAggregat onKeys),
            textFeaturesBy d = l ftOpt ons(textAggregat onKeys) ++ sparseB naryTextKeys
          )
        }
      } else {
        Set(
          Aggregat onKey(
            d screteFeaturesBy d = l ftOpt ons(d screteAggregat onKeys),
            textFeaturesBy d = l ftOpt ons(textAggregat onKeys)
          )
        )
      }
    } else Set.empty[Aggregat onKey]
  }

}

/**
 * Spec f es one or more related aggregate(s) to compute  n t  summ ngb rd job.
 *
 * @param  nputS ce S ce to compute t  aggregate over
 * @param preTransforms Sequence of [[com.tw ter.ml.ap .R ch Transform]] that transform
 * data records pre-aggregat on (e.g. d scret zat on, renam ng)
 * @param sampl ngTransformOpt Opt onal [[OneToSo Transform]] that transform data
 * record to opt onal data record (e.g. for sampl ng) before aggregat on
 * @param aggregatePref x Pref x to use for nam ng resultant aggregate features
 * @param keysToAggregate Features to group by w n comput ng t  aggregates
 * (e.g. USER_ D, AUTHOR_ D)
 * @param featuresToAggregate Features to aggregate (e.g. blender_score or  s_photo)
 * @param labels Labels to cross t  features w h to make pa r features,  f any.
 * use Label.All  f   don't want to cross w h a label.
 * @param  tr cs Aggregat on  tr cs to compute (e.g. count,  an)
 * @param halfL ves Half l ves to use for t  aggregat ons, to be crossed w h t  above.
 * use Durat on.Top for "forever" aggregat ons over an  nf n e t   w ndow (no decay).
 * @param outputStore Store to output t  aggregate to
 * @param  ncludeAnyFeature Aggregate label counts for any feature value
 * @param  ncludeAnyLabel Aggregate feature counts for any label value (e.g. all  mpress ons)
 *
 * T  overall conf g for t  summ ngb rd job cons sts of a l st of "AggregateGroup"
 * case class objects, wh ch get translated  nto strongly typed "TypedAggregateGroup"
 * case class objects. A s ngle TypedAggregateGroup always groups  nput data records from
 * '' nputS ce'' by a s ngle set of aggregat on keys (''featuresToAggregate'').
 * W h n t se groups,   perform a compre ns ve cross of:
 *
 * ''featuresToAggregate'' x ''labels'' x '' tr cs'' x ''halfL ves''
 *
 * All t  resultant aggregate features are ass gned a human-readable feature na 
 * beg nn ng w h ''aggregatePref x'', and are wr ten to DataRecords that get
 * aggregated and wr ten to t  store spec f ed by ''outputStore''.
 *
 *  llustrat ve example. Suppose   def ne   spec as follows:
 *
 * TypedAggregateGroup(
 *    nputS ce         = "t  l nes_recap_da ly",
 *   aggregatePref x     = "user_author_aggregate",
 *   keysToAggregate     = Set(USER_ D, AUTHOR_ D),
 *   featuresToAggregate = Set(RecapFeatures.TEXT_SCORE, RecapFeatures.BLENDER_SCORE),
 *   labels              = Set(RecapFeatures. S_FAVOR TED, RecapFeatures. S_REPL ED),
 *    tr cs             = Set(Count tr c,  an tr c),
 *   halfL ves           = Set(7.Days, 30.Days),
 *   outputStore         = "user_author_aggregate_store"
 * )
 *
 * T  w ll process data records from t  s ce na d "t  l nes_recap_da ly"
 * (see AggregateS ce.scala for more deta ls on how to add y  own s ce)
 *   w ll produce a total of 2x2x2x2 = 16 aggregat on features, na d l ke:
 *
 * user_author_aggregate.pa r.recap.engage nt. s_favor ed.recap.searchfeature.blender_score.count.7days
 * user_author_aggregate.pa r.recap.engage nt. s_favor ed.recap.searchfeature.blender_score.count.30days
 * user_author_aggregate.pa r.recap.engage nt. s_favor ed.recap.searchfeature.blender_score. an.7days
 *
 * ... (and so on)
 *
 * and all t  result features w ll be stored  n DataRecords, sum d up, and wr ten
 * to t  output store def ned by t  na  "user_author_aggregate_store".
 * (see AggregateStore.scala for deta ls on how to add y  own store).
 *
 *  f   do not want a full cross, spl  up y  conf g  nto mult ple TypedAggregateGroup
 * objects. Spl t ng  s strongly adv sed to avo d blow ng up and creat ng  nval d
 * or unnecessary comb nat ons of aggregate features (note that so  comb nat ons
 * are useless or  nval d e.g. comput ng t   an of a b nary feature). Spl t ng
 * also does not cost anyth ng  n terms of real-t   performance, because all
 * Aggregate objects  n t  master spec that share t  sa  ''keysToAggregate'', t 
 * sa  '' nputS ce'' and t  sa  ''outputStore'' are grouped by t  summ ngb rd
 * job log c and stored  nto a s ngle DataRecord  n t  output store. Overlapp ng
 * aggregates w ll also automat cally be dedupl cated so don't worry about overlaps.
 */
case class TypedAggregateGroup[T](
   nputS ce: AggregateS ce,
  aggregatePref x: Str ng,
  keysToAggregate: Set[Feature[_]],
  featuresToAggregate: Set[Feature[T]],
  labels: Set[_ <: Feature[JBoolean]],
   tr cs: Set[Aggregat on tr c[T, _]],
  halfL ves: Set[Durat on],
  outputStore: AggregateStore,
  preTransforms: Seq[OneToSo Transform] = Seq.empty,
   ncludeAnyFeature: Boolean = true,
   ncludeAnyLabel: Boolean = true,
  aggExclus onRegex: Seq[Str ng] = Seq.empty) {
   mport TypedAggregateGroup._

  val comp ledRegexes = aggExclus onRegex.map(new Regex(_))

  // true  f should drop, false  f should keep
  def f lterOutAggregateFeature(
    feature: PrecomputedAggregateDescr ptor[_],
    regexes: Seq[Regex]
  ): Boolean = {
     f (regexes.nonEmpty)
      feature.outputFeatures.ex sts { feature =>
        regexes.ex sts { re => re.f ndF rstMatch n(feature.getDenseFeatureNa ).nonEmpty }
      }
    else false
  }

  def bu ldAggregat onKeys(
    dataRecord: DataRecord
  ): Set[Aggregat onKey] = {
    TypedAggregateGroup.bu ldAggregat onKeys(dataRecord, keysToAggregate)
  }

  /**
   * T  val precomputes descr ptors for all  nd v dual aggregates  n t  group
   * (of type ''AggregateFeature''). Also precompute has s of all aggregat on
   * "output" features generated by t se operators for faster
   * run-t   performance (t  turns out to be a pr mary CPU bottleneck).
   * Ex: for t   an operator, "sum" and "count" are output features
   */
  val  nd v dualAggregateDescr ptors: Set[PrecomputedAggregateDescr ptor[T]] = {
    /*
     * By default,  n add  onal to all feature-label crosses, also
     * compute  n aggregates over each feature and label w hout cross ng
     */
    val labelOpt ons = labels.map(Opt on(_)) ++
      ( f ( ncludeAnyLabel) Set(None) else Set.empty)
    val featureOpt ons = featuresToAggregate.map(Opt on(_)) ++
      ( f ( ncludeAnyFeature) Set(None) else Set.empty)
    for {
      feature <- featureOpt ons
      label <- labelOpt ons
       tr c <-  tr cs
      halfL fe <- halfL ves
    } y eld {
      val query = AggregateFeature[T](aggregatePref x, feature, label, halfL fe)

      val aggregateOutputFeatures =  tr c.getOutputFeatures(query)
      val aggregateOutputFeature ds =  tr c.getOutputFeature ds(query)
      PrecomputedAggregateDescr ptor(
        query,
         tr c,
        aggregateOutputFeatures,
        aggregateOutputFeature ds
      )
    }
  }.f lterNot(f lterOutAggregateFeature(_, comp ledRegexes))

  /* Precomputes a map from all generated aggregate feature  ds to t  r half l ves. */
  val cont nuousFeature dsToHalfL ves: Map[Long, Durat on] =
     nd v dualAggregateDescr ptors.flatMap { descr ptor =>
      descr ptor.outputFeatures
        .flatMap { feature =>
           f (feature.getFeatureType() == FeatureType.CONT NUOUS) {
            Try(feature.as nstanceOf[Feature[JDouble]]).toOpt on
              .map(feature => (feature.getFeature d(), descr ptor.query.halfL fe))
          } else None
        }
    }.toMap

  /*
   * Sparse b nary keys beco   nd v dual str ng keys  n t  output.
   * e.g. group by "words. n.t et", output key: "words. n.t et. mber"
   */
  val allOutputKeys: Set[Feature[_]] = keysToAggregate.map { key =>
     f (key.getFeatureType == FeatureType.SPARSE_B NARY) sparseFeature(key)
    else key
  }

  val allOutputFeatures: Set[Feature[_]] =  nd v dualAggregateDescr ptors.flatMap {
    case PrecomputedAggregateDescr ptor(
          query,
           tr c,
          outputFeatures,
          outputFeature ds
        ) =>
      outputFeatures
  }

  val aggregateContext: FeatureContext = new FeatureContext(allOutputFeatures.toL st.asJava)

  /**
   * Adds all aggregates  n t  group found  n t  two  nput data records
   *  nto a result, mutat ng t  result. Uses a wh le loop for an
   * approx mately 10% ga n  n speed over a for compre ns on.
   *
   * WARN NG: mutates ''result''
   *
   * @param result T  output data record to mutate
   * @param left T  left data record to add
   * @param r ght T  r ght data record to add
   */
  def mutatePlus(result: DataRecord, left: DataRecord, r ght: DataRecord): Un  = {
    val feature erator =  nd v dualAggregateDescr ptors. erator
    wh le (feature erator.hasNext) {
      val descr ptor = feature erator.next
      descr ptor. tr c.mutatePlus(
        result,
        left,
        r ght,
        descr ptor.query,
        So (descr ptor.outputFeature ds)
      )
    }
  }

  /**
   * Apply preTransforms sequent ally.  f any transform results  n a dropped (None)
   * DataRecord, t n ent re tranform sequence w ll result  n a dropped DataRecord.
   * Note that preTransforms are order-dependent.
   */
  pr vate[t ] def sequent allyTransform(dataRecord: DataRecord): Opt on[DataRecord] = {
    val recordOpt = Opt on(new DataRecord(dataRecord))
    preTransforms.foldLeft(recordOpt) {
      case (So (prev ousRecord), preTransform) =>
        preTransform(prev ousRecord)
      case _ => Opt on.empty[DataRecord]
    }
  }

  /**
   * G ven a data record, apply transforms and fetch t   ncre ntal contr but ons to
   * each conf gured aggregate from t  data record, and store t se  n an output data record.
   *
   * @param dataRecord  nput data record to aggregate.
   * @return A set of tuples (Aggregat onKey, DataRecord) whose f rst entry  s an
   * Aggregat onKey  nd cat ng what keys  're group ng by, and whose second entry
   *  s an output data record w h  ncre ntal contr but ons to t  aggregate value(s)
   */
  def computeAggregateKVPa rs(dataRecord: DataRecord): Set[(Aggregat onKey, DataRecord)] = {
    sequent allyTransform(dataRecord)
      .flatMap { dataRecord =>
        val aggregat onKeys = bu ldAggregat onKeys(dataRecord)
        val  ncre nt = new DataRecord

        val  sNonEmpty ncre nt =  nd v dualAggregateDescr ptors
          .map { descr ptor =>
            descr ptor. tr c.set ncre nt(
              output =  ncre nt,
               nput = dataRecord,
              query = descr ptor.query,
              t  stampFeature =  nputS ce.t  stampFeature,
              aggregateOutputs = So (descr ptor.outputFeature ds)
            )
          }
          .ex sts( dent y)

         f ( sNonEmpty ncre nt) {
          SR chDataRecord( ncre nt).setFeatureValue(
            t  stampFeature,
            getT  stamp(dataRecord,  nputS ce.t  stampFeature)
          )
          So (aggregat onKeys.map(key => (key,  ncre nt)))
        } else {
          None
        }
      }
      .getOrElse(Set.empty[(Aggregat onKey, DataRecord)])
  }

  def outputFeaturesToRena dOutputFeatures(pref x: Str ng): Map[Feature[_], Feature[_]] = {
    requ re(pref x.nonEmpty)

    allOutputFeatures.map { feature =>
       f (feature. sSetFeatureNa ) {
        val rena dFeatureNa  = pref x + feature.getDenseFeatureNa 
        val personalDataTypes =
           f (feature.getPersonalDataTypes. sPresent) feature.getPersonalDataTypes.get()
          else null

        val rena dFeature = feature.getFeatureType match {
          case FeatureType.B NARY =>
            new Feature.B nary(rena dFeatureNa , personalDataTypes)
          case FeatureType.D SCRETE =>
            new Feature.D screte(rena dFeatureNa , personalDataTypes)
          case FeatureType.STR NG =>
            new Feature.Text(rena dFeatureNa , personalDataTypes)
          case FeatureType.CONT NUOUS =>
            new Feature.Cont nuous(rena dFeatureNa , personalDataTypes)
          case FeatureType.SPARSE_B NARY =>
            new Feature.SparseB nary(rena dFeatureNa , personalDataTypes)
          case FeatureType.SPARSE_CONT NUOUS =>
            new Feature.SparseCont nuous(rena dFeatureNa , personalDataTypes)
        }
        feature -> rena dFeature
      } else {
        feature -> feature
      }
    }.toMap
  }
}
