package com.tw ter.s mclusters_v2.scald ng.embedd ng.common

 mport com.tw ter.scald ng.{Args, DateRange, Execut on, TypedP pe, Un que D}
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.scald ng.common.matr x.{SparseMatr x, SparseRowMatr x}
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l._
 mport com.tw ter.s mclusters_v2.thr ftscala._
 mport java.ut l.T  Zone

/**
 * T   s t  base job for comput ng S mClusters Embedd ng for any Noun Type on Tw ter, such as
 * Users, T ets, Top cs, Ent  es, Channels, etc.
 *
 * T  most stra ghtforward way to understand t  S mClusters Embedd ngs for a Noun  s that    s
 * a   ghted sum of S mClusters  nterested n vectors from users who are  nterested  n t  Noun.
 * So for a noun type,   only need to def ne `prepareNounToUserMatr x` to pass  n a matr x wh ch
 * represents how much each user  s  nterested  n t  noun.
 */
tra  S mClustersEmbedd ngBaseJob[NounType] {

  def numClustersPerNoun:  nt

  def numNounsPerClusters:  nt

  def thresholdForEmbedd ngScores: Double

  def numReducersOpt: Opt on[ nt] = None

  def prepareNounToUserMatr x(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): SparseMatr x[NounType, User d, Double]

  def prepareUserToClusterMatr x(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): SparseRowMatr x[User d, Cluster d, Double]

  def wr eNounToClusters ndex(
    output: TypedP pe[(NounType, Seq[(Cluster d, Double)])]
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ]

  def wr eClusterToNouns ndex(
    output: TypedP pe[(Cluster d, Seq[(NounType, Double)])]
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ]

  def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    val embedd ngMatr x: SparseRowMatr x[NounType, Cluster d, Double] =
      prepareNounToUserMatr x.rowL2Normal ze
        .mult plySk nnySparseRowMatr x(
          prepareUserToClusterMatr x.colL2Normal ze,
          numReducersOpt
        )
        .f lter((_, _, v) => v > thresholdForEmbedd ngScores)

    Execut on
      .z p(
        wr eNounToClusters ndex(
          embedd ngMatr x.sortW hTakePerRow(numClustersPerNoun)(Order ng.by(-_._2))
        ),
        wr eClusterToNouns ndex(
          embedd ngMatr x.sortW hTakePerCol(numNounsPerClusters)(
            Order ng.by(-_._2)
          )
        )
      )
      .un 
  }

}

object S mClustersEmbedd ngJob {

  /**
   * Mult ply t  [user, cluster] and [user, T] matr ces, and return t  cross product.
   */
  def computeEmbedd ngs[T](
    s mClustersS ce: TypedP pe[(User d, ClustersUser s nterested n)],
    normal zed nputMatr x: TypedP pe[(User d, (T, Double))],
    scoreExtractors: Seq[UserTo nterested nClusterScores => (Double, ScoreType.ScoreType)],
    modelVers on: ModelVers on,
    toS mClustersEmbedd ng d: (T, ScoreType.ScoreType) => S mClustersEmbedd ng d,
    numReducers: Opt on[ nt] = None
  ): TypedP pe[(S mClustersEmbedd ng d, (Cluster d, Double))] = {
    val userS mClustersMatr x =
      getUserS mClustersMatr x(s mClustersS ce, scoreExtractors, modelVers on)
    mult plyMatr ces(
      normal zed nputMatr x,
      userS mClustersMatr x,
      toS mClustersEmbedd ng d,
      numReducers)
  }

  def getL2Norm[T](
     nputMatr x: TypedP pe[(T, (User d, Double))],
    numReducers: Opt on[ nt] = None
  )(
     mpl c  order ng: Order ng[T]
  ): TypedP pe[(T, Double)] = {
    val l2Norm =  nputMatr x
      .mapValues {
        case (_, score) => score * score
      }
      .sumByKey
      .mapValues(math.sqrt)

    numReducers match {
      case So (reducers) => l2Norm.w hReducers(reducers)
      case _ => l2Norm
    }
  }

  def getNormal zedTranspose nputMatr x[T](
     nputMatr x: TypedP pe[(T, (User d, Double))],
    numReducers: Opt on[ nt] = None
  )(
     mpl c  order ng: Order ng[T]
  ): TypedP pe[(User d, (T, Double))] = {
    val  nputW hNorm =  nputMatr x.jo n(getL2Norm( nputMatr x, numReducers))

    (numReducers match {
      case So (reducers) =>  nputW hNorm.w hReducers(reducers)
      case _ =>  nputW hNorm
    }).map {
      case ( nput d, ((user d, favScore), norm)) =>
        (user d, ( nput d, favScore / norm))
    }
  }

  /**
   * Matr x mult pl cat on w h t  ab l y to tune t  reducer s ze for better performance
   */
  @Deprecated
  def legacyMult plyMatr ces[T](
    normal zedTranspose nputMatr x: TypedP pe[(User d, (T, Double))],
    userS mClustersMatr x: TypedP pe[(User d, Seq[(Cluster d, Double)])],
    numReducers:  nt // Matr x mult pl cat on  s expens ve. Use t  to tune performance
  )(
     mpl c  order ng: Order ng[T]
  ): TypedP pe[((Cluster d, T), Double)] = {
    normal zedTranspose nputMatr x
      .jo n(userS mClustersMatr x)
      .w hReducers(numReducers)
      .flatMap {
        case (_, (( nput d, score), clustersW hScores)) =>
          clustersW hScores.map {
            case (cluster d, clusterScore) =>
              ((cluster d,  nput d), score * clusterScore)
          }
      }
      .sumByKey
      .w hReducers(numReducers + 1) // +1 to d st ngu sh t  step from above  n Dr. Scald ng
  }

  def mult plyMatr ces[T](
    normal zedTranspose nputMatr x: TypedP pe[(User d, (T, Double))],
    userS mClustersMatr x: TypedP pe[(User d, Seq[((Cluster d, ScoreType.ScoreType), Double)])],
    toS mClustersEmbedd ng d: (T, ScoreType.ScoreType) => S mClustersEmbedd ng d,
    numReducers: Opt on[ nt] = None
  ): TypedP pe[(S mClustersEmbedd ng d, (Cluster d, Double))] = {
    val  nputJo nedW hS mClusters = numReducers match {
      case So (reducers) =>
        normal zedTranspose nputMatr x
          .jo n(userS mClustersMatr x)
          .w hReducers(reducers)
      case _ =>
        normal zedTranspose nputMatr x.jo n(userS mClustersMatr x)
    }

    val matr xMult pl cat onResult =  nputJo nedW hS mClusters.flatMap {
      case (_, (( nput d,  nputScore), clustersW hScores)) =>
        clustersW hScores.map {
          case ((cluster d, scoreType), clusterScore) =>
            ((cluster d, toS mClustersEmbedd ng d( nput d, scoreType)),  nputScore * clusterScore)
        }
    }.sumByKey

    (numReducers match {
      case So (reducers) =>
        matr xMult pl cat onResult.w hReducers(reducers + 1)
      case _ => matr xMult pl cat onResult
    }).map {
      case ((cluster d, embedd ng d), score) =>
        (embedd ng d, (cluster d, score))
    }
  }

  def getUserS mClustersMatr x(
    s mClustersS ce: TypedP pe[(User d, ClustersUser s nterested n)],
    scoreExtractors: Seq[UserTo nterested nClusterScores => (Double, ScoreType.ScoreType)],
    modelVers on: ModelVers on
  ): TypedP pe[(User d, Seq[((Cluster d, ScoreType.ScoreType), Double)])] = {
    s mClustersS ce.map {
      case (user d, clusters)
           f ModelVers ons.toModelVers on(clusters.knownForModelVers on) == modelVers on =>
        user d -> clusters.cluster dToScores.flatMap {
          case (cluster d, clusterScores) =>
            scoreExtractors.map { scoreExtractor =>
              scoreExtractor(clusterScores) match {
                case (score, scoreType) => ((cluster d, scoreType), score)
              }
            }
        }.toSeq
      case (user d, _) => user d -> N l
    }
  }

  def toReverse ndexS mClusterEmbedd ng(
    embedd ngs: TypedP pe[(S mClustersEmbedd ng d, (Cluster d, Embedd ngScore))],
    topK:  nt
  ): TypedP pe[(S mClustersEmbedd ng d,  nternal dEmbedd ng)] = {
    embedd ngs
      .map {
        case (embedd ng d, (cluster d, score)) =>
          (
            S mClustersEmbedd ng d(
              embedd ng d.embedd ngType,
              embedd ng d.modelVers on,
               nternal d.Cluster d(cluster d)),
            (embedd ng d. nternal d, score))
      }
      .group
      .sortedReverseTake(topK)(Order ng.by(_._2))
      .mapValues { top nternal dsW hScore =>
        val  nternal dsW hScore = top nternal dsW hScore.map {
          case ( nternal d, score) =>  nternal dW hScore( nternal d, score)
        }
         nternal dEmbedd ng( nternal dsW hScore)
      }
  }
}
