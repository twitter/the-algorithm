package com.tw ter.s mclusters_v2.scald ng.t et_s m lar y

 mport com.tw ter.ads.ent  es.db.thr ftscala.PromotedT et
 mport com.tw ter.dataproducts.est mat on.Reservo rSampler
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.scald ng.{DateRange, Execut on, TypedTsv}
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.{Expl c Locat on, Proc3Atla, ProcAtla}
 mport com.tw ter.s mclusters_v2.common.{S mClustersEmbedd ng, T  stamp, T et d, User d}
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.ExternalDataS ces
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  T etTopKT etsW hScore,
  T etW hScore,
  T etsW hScore
}
 mport com.tw ter.t  l neserv ce.thr ftscala.{Contextual zedFavor eEvent, Favor eEventUn on}
 mport com.tw ter.wtf.scald ng.cl ent_event_process ng.thr ftscala.{
   nteract onDeta ls,
   nteract onType,
  T et mpress onDeta ls
}
 mport com.tw ter.wtf.scald ng.jobs.cl ent_event_process ng.User nteract onScalaDataset
 mport java.ut l.Random
 mport scala.collect on.mutable.ArrayBuffer
 mport scala.ut l.control.Breaks._
 mport twadoop_conf g.conf gurat on.log_categor es.group.t  l ne.T  l neServ ceFavor esScalaDataset

object T etPa rLabelCollect onUt l {

  case class FeaturedT et(
    t et: T et d,
    t  stamp: T  stamp, //engage nt or  mpress on t  
    author: Opt on[User d],
    embedd ng: Opt on[S mClustersEmbedd ng])
      extends Ordered[FeaturedT et] {

     mport scala.math.Ordered.order ngToOrdered

    def compare(that: FeaturedT et):  nt =
      (t .t et, t .t  stamp, t .author) compare (that.t et, that.t  stamp, that.author)
  }

  val MaxFavPerUser:  nt = 100

  /**
   * Get all fav events w h n t  g ven dateRange and w re all users' out-degree <= maxOutDegree
   * from T  l neServ ceFavor esScalaDataset
   *
   * @param dateRange         date of  nterest
   * @param maxOutgo ngDegree max #degrees for t  users of  nterests
   *
   * @return F ltered fav events, TypedP pe of (user d, t et d, t  stamp) tuples
   */
  def getFavEvents(
    dateRange: DateRange,
    maxOutgo ngDegree:  nt
  ): TypedP pe[(User d, T et d, T  stamp)] = {
    val fullT  l neFavData: TypedP pe[Contextual zedFavor eEvent] =
      DAL
        .read(T  l neServ ceFavor esScalaDataset, dateRange)
        .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
        .toTypedP pe

    val userT etTuples = fullT  l neFavData
      .flatMap { cfe: Contextual zedFavor eEvent =>
        cfe.event match {
          case Favor eEventUn on.Favor e(fav) =>
            So ((fav.user d, (fav.t et d, fav.eventT  Ms)))
          case _ =>
            None
        }
      }
    //Get users w h t  out-degree <= maxOutDegree f rst
    val usersW hVal dOutDegree = userT etTuples
      .groupBy(_._1)
      .w hReducers(1000)
      .s ze
      .f lter(_._2 <= maxOutgo ngDegree)

    // Keep only usersW hVal dOutDegree  n t  graph
    userT etTuples
      .jo n(usersW hVal dOutDegree).map {
        case (user d, ((t et d, eventT  ), _)) => (user d, t et d, eventT  )
      }.forceToD sk
  }

  /**
   * Get  mpress on events w re users stay at t  t ets for more than one m nute
   *
   * @param dateRange t   range of  nterest
   *
   * @return
   */
  def get mpress onEvents(dateRange: DateRange): TypedP pe[(User d, T et d, T  stamp)] = {
    DAL
      .read(User nteract onScalaDataset, dateRange)
      .w hRemoteReadPol cy(Expl c Locat on(Proc3Atla))
      .toTypedP pe
      .flatMap {
        case user nteract on
             f user nteract on. nteract onType ==  nteract onType.T et mpress ons =>
          user nteract on. nteract onDeta ls match {
            case  nteract onDeta ls.T et mpress onDeta ls(
                  T et mpress onDeta ls(t et d, _, d llT   nSecOpt))
                 f d llT   nSecOpt.ex sts(_ >= 1) =>
              So (user nteract on.user d, t et d, user nteract on.t  Stamp)
            case _ =>
              None
          }
        case _ => None
      }
      .forceToD sk
  }

  /**
   * G ven an events dataset, return a f ltered events l m ed to a g ven set of t ets
   *
   * @param events user fav events, a TypedP pe of (user d, t et d, t  stamp) tuples
   * @param t ets t ets of  nterest
   *
   * @return F ltered fav events on t  g ven t ets of  nterest only, TypedP pe of (user d, t et d, t  stamp) tuples
   */
  def getF lteredEvents(
    events: TypedP pe[(User d, T et d, T  stamp)],
    t ets: TypedP pe[T et d]
  ): TypedP pe[(User d, T et d, T  stamp)] = {
    events
      .map {
        case (user d, t et d, eventT  ) => (t et d, (user d, eventT  ))
      }
      .jo n(t ets.asKeys)
      .w hReducers(1000)
      .map {
        case (t et d, ((user d, eventT  ), _)) => (user d, t et d, eventT  )
      }
  }

  /** Get (t et d, author user d) of a g ven dateRange
   *
   * @param dateRange t   range of  nterest
   *
   * @return TypedP pe of (t et d, user d)
   */
  def getT etAuthorPa rs(dateRange: DateRange): TypedP pe[(T et d, User d)] = {
    ExternalDataS ces
      .flatT etsS ce(dateRange)
      .collect {
        // Exclude ret ets and quoted t ets
        case record  f record.shareS ceT et d. sEmpty && record.quotedT etT et d. sEmpty =>
          (record.t et d, record.user d)
      }
  }

  /** G ven a set of t ets, get all non-promoted t ets from t  g ven set
   *
   * @param promotedT ets TypedP pe of promoted t ets
   * @param t ets         t ets of  nterest
   *
   * @return TypedP pe of t et d
   */
  def getNonPromotedT ets(
    promotedT ets: TypedP pe[PromotedT et],
    t ets: TypedP pe[T et d]
  ): TypedP pe[T et d] = {
    promotedT ets
      .collect {
        case promotedT et  f promotedT et.t et d. sDef ned => promotedT et.t et d.get
      }
      .asKeys
      .r ghtJo n(t ets.asKeys)
      .w hReducers(1000)
      .f lterNot(jo ned => jo ned._2._1. sDef ned) //f lter out those  n promotedT ets
      .keys
  }

  /**
   * G ven a fav events dataset, return all d st nct ordered t et pa rs, labelled by w t r t y are co-engaged or not
   * Note   d st ngu sh bet en (t1, t2) and (t2, t1) because o.w    ntroduce b as to tra n ng samples
   *
   * @param events      user fav events, a TypedP pe of (user d, featuredT et) tuples
   * @param t  fra    two t ets w ll be cons dered co-engaged  f t y are fav-ed w h n coengage ntT  fra 
   * @param  sCoengaged  f pa rs are co-engaged
   *
   * @return labelled t et pa rs, TypedP pe of (user d, featuredT et1, featuredT et2,  sCoengaged) tuples
   */
  def getT etPa rs(
    events: TypedP pe[(User d, FeaturedT et)],
    t  fra : Long,
     sCoengaged: Boolean
  ): TypedP pe[(User d, FeaturedT et, FeaturedT et, Boolean)] = {
    events
      .map {
        case (user d, featuredT et) => (user d, Seq(featuredT et))
      }
      .sumByKey
      .flatMap {
        case (user d, featuredT ets)  f featuredT ets.s ze > 1 =>
          val sortedFeaturedT et = featuredT ets.sortBy(_.t  stamp)
          // Get all d st nct ordered pa rs that happen w h n coengage ntT  fra 
          val d st nctPa rs = ArrayBuffer[(User d, FeaturedT et, FeaturedT et, Boolean)]()
          breakable {
            for (  <- sortedFeaturedT et. nd ces) {
              for (j <-   + 1 unt l sortedFeaturedT et.s ze) {
                val featuredT et1 = sortedFeaturedT et( )
                val featuredT et2 = sortedFeaturedT et(j)
                 f (math.abs(featuredT et1.t  stamp - featuredT et2.t  stamp) <= t  fra )
                  d st nctPa rs ++= Seq(
                    (user d, featuredT et1, featuredT et2,  sCoengaged),
                    (user d, featuredT et2, featuredT et1,  sCoengaged))
                else
                  break
              }
            }
          }
          d st nctPa rs
        case _ => N l
      }
  }

  /**
   * Get co-engaged t et pa rs
   *
   * @param favEvents             user fav events, TypedP pe of (user d, t et d, t  stamp)
   * @param t ets                t ets to be cons dered
   * @param coengage ntT  fra  t   w ndow for two t ets to be cons dered as co-engaged
   *
   * @return TypedP pe of co-engaged t et pa rs
   */
  def getCoengagedPa rs(
    favEvents: TypedP pe[(User d, T et d, T  stamp)],
    t ets: TypedP pe[T et d],
    coengage ntT  fra : Long
  ): TypedP pe[(User d, FeaturedT et, FeaturedT et, Boolean)] = {
    val userFeaturedT etPa rs =
      getF lteredEvents(favEvents, t ets)
        .map {
          case (user, t et, t  stamp) => (user, FeaturedT et(t et, t  stamp, None, None))
        }

    getT etPa rs(userFeaturedT etPa rs, coengage ntT  fra ,  sCoengaged = true)
  }

  /**
   * Get co- mpressed t et pa rs
   *
   * @param  mpress onEvents t et  mpress on events, TypedP pe of (user d, t et d, t  stamp)
   * @param t ets           set of t ets cons dered to be part of co- mpressed t et pa rs
   * @param t  fra         t   w ndow for two t ets to be cons dered as co- mpressed
   *
   * @return TypedP pe of co- mpressed t et pa rs
   */
  def getCo mpressedPa rs(
     mpress onEvents: TypedP pe[(User d, T et d, T  stamp)],
    t ets: TypedP pe[T et d],
    t  fra : Long
  ): TypedP pe[(User d, FeaturedT et, FeaturedT et, Boolean)] = {
    val userFeaturedT etPa rs = getF lteredEvents( mpress onEvents, t ets)
      .map {
        case (user, t et, t  stamp) => (user, FeaturedT et(t et, t  stamp, None, None))
      }

    getT etPa rs(userFeaturedT etPa rs, t  fra ,  sCoengaged = false)
  }

  /**
   * Consol date co-engaged pa rs and co- mpressed pa rs, and compute all t  labelled t et pa rs
   * G ven a pa r:
   * label = 1  f co-engaged (w t r or not  's co- mpressed)
   * label = 0  f co- mpressed and not co-engaged
   *
   * @param coengagedPa rs   co-engaged t et pa rs, TypedP pe of (user, queryFeaturedT et, cand dateFeaturedT et, label)
   * @param co mpressedPa rs co- mpressed t et pa rs, TypedP pe of (user, queryFeaturedT et, cand dateFeaturedT et, label)
   *
   * @return labelled t et pa rs, TypedP pe of (queryFeaturedT et, cand dateFeaturedT et, label) tuples
   */
  def computeLabelledT etPa rs(
    coengagedPa rs: TypedP pe[(User d, FeaturedT et, FeaturedT et, Boolean)],
    co mpressedPa rs: TypedP pe[(User d, FeaturedT et, FeaturedT et, Boolean)]
  ): TypedP pe[(FeaturedT et, FeaturedT et, Boolean)] = {
    (coengagedPa rs ++ co mpressedPa rs)
      .groupBy {
        case (user d, queryFeaturedT et, cand dateFeaturedT et, _) =>
          (user d, queryFeaturedT et.t et, cand dateFeaturedT et.t et)
      }
      // consol date all t  labelled pa rs  nto one w h t  max label
      // (label order: co-engage nt = true > co- mpress on = false)
      .maxBy {
        case (_, _, _, label) => label
      }
      .values
      .map { case (_, queryT et, cand dateT et, label) => (queryT et, cand dateT et, label) }
  }

  /**
   * Get a balanced-class sampl ng of t et pa rs.
   * For each query t et,   make sure t  numbers of pos  ves and negat ves are equal.
   *
   * @param labelledPa rs      labelled t et pa rs, TypedP pe of (queryFeaturedT et, cand dateFeaturedT et, label) tuples
   * @param maxSamplesPerClass max number of samples per class
   *
   * @return sampled labelled pa rs after balanced-class sampl ng
   */
  def getQueryT etBalancedClassPa rs(
    labelledPa rs: TypedP pe[(FeaturedT et, FeaturedT et, Boolean)],
    maxSamplesPerClass:  nt
  ): TypedP pe[(FeaturedT et, FeaturedT et, Boolean)] = {
    val queryT etToSampleCount = labelledPa rs
      .map {
        case (queryT et, _, label) =>
           f (label) (queryT et.t et, (1, 0)) else (queryT et.t et, (0, 1))
      }
      .sumByKey
      .map {
        case (queryT et, (posCount, negCount)) =>
          (queryT et, Math.m n(Math.m n(posCount, negCount), maxSamplesPerClass))
      }

    labelledPa rs
      .groupBy { case (queryT et, _, _) => queryT et.t et }
      .jo n(queryT etToSampleCount)
      .values
      .map {
        case ((queryT et, cand dateT et, label), samplePerClass) =>
          ((queryT et.t et, label, samplePerClass), (queryT et, cand dateT et, label))
      }
      .group
      .mapGroup {
        case ((_, _, samplePerClass),  er) =>
          val random = new Random(123L)
          val sampler =
            new Reservo rSampler[(FeaturedT et, FeaturedT et, Boolean)](samplePerClass, random)
           er.foreach { pa r => sampler.sample em(pa r) }
          sampler.sample.to erator
      }
      .values
  }

  /**
   * G ven a user fav dataset, computes t  s m lar y scores (based on engagers) bet en every t et pa rs
   *
   * @param events                user fav events, a TypedP pe of (user d, t et d, t  stamp) tuples
   * @param m n nDegree           m n number of engage nt count for t  t ets
   * @param coengage ntT  fra  two t ets w ll be cons dered co-engaged  f t y are fav-ed w h n coengage ntT  fra 
   *
   * @return t et s m lar y based on engagers, a TypedP pe of (t et1, t et2, s m lar y_score) tuples
   **/
  def getScoredCoengagedT etPa rs(
    events: TypedP pe[(User d, T et d, T  stamp)],
    m n nDegree:  nt,
    coengage ntT  fra : Long
  )(
  ): TypedP pe[(T et d, T etW hScore)] = {

    // compute t et norms (based on engagers)
    // only keep t ets whose  ndegree >= m n nDegree
    val t etNorms = events
      .map { case (_, t et d, _) => (t et d, 1.0) }
      .sumByKey //t  number of engagers per t et d
      .f lter(_._2 >= m n nDegree)
      .mapValues(math.sqrt)

    val edgesW h  ght = events
      .map {
        case (user d, t et d, eventT  ) => (t et d, (user d, eventT  ))
      }
      .jo n(t etNorms)
      .map {
        case (t et d, ((user d, eventT  ), norm)) =>
          (user d, Seq((t et d, eventT  , 1 / norm)))
      }

    // get cos ne s m lar y
    val t etPa rsW h  ght = edgesW h  ght.sumByKey
      .flatMap {
        case (_, t ets)  f t ets.s ze > 1 =>
          allUn quePa rs(t ets).flatMap {
            case ((t et d1, eventT  1,   ght1), (t et d2, eventT  2,   ght2)) =>
              // cons der only co-engage nt happened w h n t  g ven t  fra 
               f ((eventT  1 - eventT  2).abs <= coengage ntT  fra ) {
                 f (t et d1 > t et d2) // each worker generate allUn quePa rs  n d fferent orders,  nce should standard ze t  pa rs
                  So (((t et d2, t et d1),   ght1 *   ght2))
                else
                  So (((t et d1, t et d2),   ght1 *   ght2))
              } else {
                None
              }
            case _ =>
              None
          }
        case _ => N l
      }
    t etPa rsW h  ght.sumByKey
      .flatMap {
        case ((t et d1, t et d2),   ght) =>
          Seq(
            (t et d1, T etW hScore(t et d2,   ght)),
            (t et d2, T etW hScore(t et d1,   ght))
          )
        case _ => N l
      }
  }

  /**
   * Get t  wr e exec for per-query stats
   *
   * @param t etPa rs  nput dataset
   * @param outputPath output path for t  per-query stats
   * @param  dent f er  dent f er for t  t etPa rs dataset
   *
   * @return execut on of t  t  wr  ng exec
   */
  def getPerQueryStatsExec(
    t etPa rs: TypedP pe[(FeaturedT et, FeaturedT et, Boolean)],
    outputPath: Str ng,
     dent f er: Str ng
  ): Execut on[Un ] = {
    val queryT etsToCounts = t etPa rs
      .map {
        case (queryT et, _, label) =>
           f (label) (queryT et.t et, (1, 0)) else (queryT et.t et, (0, 1))
      }
      .sumByKey
      .map { case (queryT et, (posCount, negCount)) => (queryT et, posCount, negCount) }

    Execut on
      .z p(
        queryT etsToCounts.wr eExecut on(
          TypedTsv[(T et d,  nt,  nt)](s"${outputPath}_$ dent f er")),
        Ut l.pr ntSummaryOfNu r cColumn(
          queryT etsToCounts
            .map { case (_, posCount, _) => posCount },
          So (s"Per-query Pos  ve Count ($ dent f er)")),
        Ut l.pr ntSummaryOfNu r cColumn(
          queryT etsToCounts
            .map { case (_, _, negCount) => negCount },
          So (s"Per-query Negat ve Count ($ dent f er)"))
      ).un 
  }

  /**
   * Get t  top K s m lar t ets key-val dataset
   *
   * @param allT etPa rs all t et pa rs w h t  r s m lar y scores
   * @param k             t  max mum number of top results for each user
   *
   * @return key-val top K results for each t et
   */
  def getKeyValTopKS m larT ets(
    allT etPa rs: TypedP pe[(T et d, T etW hScore)],
    k:  nt
  )(
  ): TypedP pe[(T et d, T etsW hScore)] = {
    allT etPa rs.group
      .sortedReverseTake(k)(Order ng.by(_.score))
      .map { case (t et d, t etW hScoreSeq) => (t et d, T etsW hScore(t etW hScoreSeq)) }
  }

  /**
   * Get t  top K s m lar t ets dataset.
   *
   * @param allT etPa rs all t et pa rs w h t  r s m lar y scores
   * @param k             t  max mum number of top results for each user
   *
   * @return top K results for each t et
   */
  def getTopKS m larT ets(
    allT etPa rs: TypedP pe[(T et d, T etW hScore)],
    k:  nt
  )(
  ): TypedP pe[T etTopKT etsW hScore] = {
    allT etPa rs.group
      .sortedReverseTake(k)(Order ng.by(_.score))
      .map {
        case (t et d, t etW hScoreSeq) =>
          T etTopKT etsW hScore(t et d, T etsW hScore(t etW hScoreSeq))
      }
  }

  /**
   * G ven a  nput sequence, output all un que pa rs  n t  sequence.
   */
  def allUn quePa rs[T]( nput: Seq[T]): Stream[(T, T)] = {
     nput match {
      case N l => Stream.empty
      case seq =>
        seq.ta l.toStream.map(a => (seq. ad, a)) #::: allUn quePa rs(seq.ta l)
    }
  }
}
