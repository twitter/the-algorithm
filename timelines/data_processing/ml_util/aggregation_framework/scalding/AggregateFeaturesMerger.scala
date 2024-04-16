package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.scald ng

 mport com.tw ter.ml.ap ._
 mport com.tw ter.ml.ap .constant.SharedFeatures._
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.scald ng.Stat
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work._
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.sampl ng.Sampl ngUt ls

tra  AggregateFeatures rgerBase {
   mport Ut ls._

  def sampl ngRateOpt: Opt on[Double]
  def numReducers:  nt = 2000
  def numReducers rge:  nt = 20000

  def aggregat onConf g: Aggregat onConf g
  def storeReg ster: StoreReg ster
  def store rger: Store rger

  def getAggregateP pe(storeNa : Str ng): DataSetP pe
  def applyMaxS zeByTypeOpt(aggregateType: AggregateType.Value): Opt on[ nt] = Opt on.empty[ nt]

  def usersAct veS ceP pe: TypedP pe[Long]
  def numRecords: Stat
  def numF lteredRecords: Stat

  /*
   * T   thod should only be called w h a storeNa  that corresponds
   * to a user aggregate store.
   */
  def extractUserFeaturesMap(storeNa : Str ng): TypedP pe[(Long, KeyedRecord)] = {
    val aggregateKey = storeReg ster.storeNa ToTypeMap(storeNa )
    sampl ngRateOpt
      .map(rate => Sampl ngUt ls.userBasedSample(getAggregateP pe(storeNa ), rate))
      .getOrElse(getAggregateP pe(storeNa )) // must return store w h only user aggregates
      .records
      .map { r: DataRecord =>
        val record = SR chDataRecord(r)
        val user d = record.getFeatureValue(USER_ D).longValue
        record.clearFeature(USER_ D)
        (user d, KeyedRecord(aggregateKey, r))
      }
  }

  /*
   * W n t  secondaryKey be ng used  s a Str ng, t n t  shouldHash funct on should be set to true.
   * Refactor such that t  shouldHash para ter  s removed and t  behav or
   *  s defaulted to true.
   *
   * T   thod should only be called w h a storeNa  that conta ns records w h t 
   * des red secondaryKey.   prov de secondaryKeyF lterP peOpt aga nst wh ch secondary
   * keys can be f ltered to  lp prune t  f nal  rged MH dataset.
   */
  def extractSecondaryTuples[T](
    storeNa : Str ng,
    secondaryKey: Feature[T],
    shouldHash: Boolean = false,
    maxS zeOpt: Opt on[ nt] = None,
    secondaryKeyF lterP peOpt: Opt on[TypedP pe[Long]] = None
  ): TypedP pe[(Long, KeyedRecordMap)] = {
    val aggregateKey = storeReg ster.storeNa ToTypeMap(storeNa )

    val extractedRecordsBySecondaryKey =
      sampl ngRateOpt
        .map(rate => Sampl ngUt ls.userBasedSample(getAggregateP pe(storeNa ), rate))
        .getOrElse(getAggregateP pe(storeNa ))
        .records
        .map { r: DataRecord =>
          val record = SR chDataRecord(r)
          val user d = keyFromLong(r, USER_ D)
          val secondary d = extractSecondary(r, secondaryKey, shouldHash)
          record.clearFeature(USER_ D)
          record.clearFeature(secondaryKey)

          numRecords. nc()
          (user d, secondary d -> r)
        }

    val grouped =
      (secondaryKeyF lterP peOpt match {
        case So (secondaryKeyF lterP pe: TypedP pe[Long]) =>
          extractedRecordsBySecondaryKey
            .map {
              //  n t  step,   swap `user d` w h `secondary d` to jo n on t  `secondary d`
              //    s  mportant to swap t m back after t  jo n, ot rw se t  job w ll fa l.
              case (user d, (secondary d, r)) =>
                (secondary d, (user d, r))
            }
            .jo n(secondaryKeyF lterP pe.groupBy( dent y))
            .map {
              case (secondary d, ((user d, r), _)) =>
                numF lteredRecords. nc()
                (user d, secondary d -> r)
            }
        case _ => extractedRecordsBySecondaryKey
      }).group
        .w hReducers(numReducers)

    maxS zeOpt match {
      case So (maxS ze) =>
        grouped
          .take(maxS ze)
          .mapValueStream(records er =>  erator(KeyedRecordMap(aggregateKey, records er.toMap)))
          .toTypedP pe
      case None =>
        grouped
          .mapValueStream(records er =>  erator(KeyedRecordMap(aggregateKey, records er.toMap)))
          .toTypedP pe
    }
  }

  def userP pes: Seq[TypedP pe[(Long, KeyedRecord)]] =
    storeReg ster.allStores.flatMap { storeConf g =>
      val StoreConf g(storeNa s, aggregateType, _) = storeConf g
      requ re(store rger. sVal dTo rge(storeNa s))

       f (aggregateType == AggregateType.User) {
        storeNa s.map(extractUserFeaturesMap)
      } else None
    }.toSeq

  pr vate def getSecondaryKeyF lterP peOpt(
    aggregateType: AggregateType.Value
  ): Opt on[TypedP pe[Long]] = {
     f (aggregateType == AggregateType.UserAuthor) {
      So (usersAct veS ceP pe)
    } else None
  }

  def userSecondaryKeyP pes: Seq[TypedP pe[(Long, KeyedRecordMap)]] = {
    storeReg ster.allStores.flatMap { storeConf g =>
      val StoreConf g(storeNa s, aggregateType, shouldHash) = storeConf g
      requ re(store rger. sVal dTo rge(storeNa s))

       f (aggregateType != AggregateType.User) {
        storeNa s.flatMap { storeNa  =>
          storeConf g.secondaryKeyFeatureOpt
            .map { secondaryFeature =>
              extractSecondaryTuples(
                storeNa ,
                secondaryFeature,
                shouldHash,
                applyMaxS zeByTypeOpt(aggregateType),
                getSecondaryKeyF lterP peOpt(aggregateType)
              )
            }
        }
      } else None
    }.toSeq
  }

  def jo nedAggregates: TypedP pe[(Long,  rgedRecordsDescr ptor)] = {
    (userP pes ++ userSecondaryKeyP pes)
      .reduce(_ ++ _)
      .group
      .w hReducers(numReducers rge)
      .mapGroup {
        case (u d, keyedRecordsAndMaps) =>
          /*
           * For every user, part  on t  r records by aggregate type.
           * AggregateType.User should only conta n KeyedRecord w reas
           * ot r aggregate types (w h secondary keys) conta n KeyedRecordMap.
           */
          val (userRecords, userSecondaryKeyRecords) = keyedRecordsAndMaps.toL st
            .map { record =>
              record match {
                case record: KeyedRecord => (record.aggregateType, record)
                case record: KeyedRecordMap => (record.aggregateType, record)
              }
            }
            .groupBy(_._1)
            .mapValues(_.map(_._2))
            .part  on(_._1 == AggregateType.User)

          val userAggregateRecordMap: Map[AggregateType.Value, Opt on[KeyedRecord]] =
            userRecords
              .as nstanceOf[Map[AggregateType.Value, L st[KeyedRecord]]]
              .map {
                case (aggregateType, keyedRecords) =>
                  val  rgedKeyedRecordOpt =  rgeKeyedRecordOpts(keyedRecords.map(So (_)): _*)
                  (aggregateType,  rgedKeyedRecordOpt)
              }

          val userSecondaryKeyAggregateRecordOpt: Map[AggregateType.Value, Opt on[KeyedRecordMap]] =
            userSecondaryKeyRecords
              .as nstanceOf[Map[AggregateType.Value, L st[KeyedRecordMap]]]
              .map {
                case (aggregateType, keyedRecordMaps) =>
                  val keyedRecordMapOpt =
                    keyedRecordMaps.foldLeft(Opt on.empty[KeyedRecordMap]) {
                      ( rgedRecOpt, nextRec) =>
                        applyMaxS zeByTypeOpt(aggregateType)
                          .map { maxS ze =>
                             rgeKeyedRecordMapOpts( rgedRecOpt, So (nextRec), maxS ze)
                          }.getOrElse {
                             rgeKeyedRecordMapOpts( rgedRecOpt, So (nextRec))
                          }
                    }
                  (aggregateType, keyedRecordMapOpt)
              }

           erator(
             rgedRecordsDescr ptor(
              user d = u d,
              keyedRecords = userAggregateRecordMap,
              keyedRecordMaps = userSecondaryKeyAggregateRecordOpt
            )
          )
      }.toTypedP pe
  }
}
