package com.twitter.cr_mitwitterer.candidate_generation

import com.twitter.cr_mitwitterer.model.CandidateGenerationInfo
import com.twitter.cr_mitwitterer.model.TweetWithCandidateGenerationInfo
import com.twitter.cr_mitwitterer.model.TweetWithScore
import com.twitter.cr_mitwitterer.param.GlobalParams
import com.twitter.cr_mitwitterer.param.InterestedInParams
import com.twitter.cr_mitwitterer.param.SimClustersANNParams
import com.twitter.cr_mitwitterer.similarity_engine.EngineQuery
import com.twitter.cr_mitwitterer.similarity_engine.SimClustersANNSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.StandardSimilarityEngine
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.base.CandidateSource
import com.twitter.frigate.common.util.StatsUtil
import com.twitter.simclusters_v2.common.ModelVersions
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.timelines.configapi
import com.twitter.util.Future
import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import javatwitter.inject.Named
import com.twitter.cr_mitwitterer.model.ModuleNames

/**
 * This store looks for similar tweets for a given UserId that generates UserInterestedIn
 * from SimClustersANN. It will be a standalone CandidateGeneration class moving forward.
 *
 * After the abstraction improvement (apply SimilarityEngine trait)
 * these CG will be subjected to change.
 */
@Singleton
case class SimClustersInterestedInCandidateGeneration @Inject() (
  @Named(ModuleNames.SimClustersANNSimilarityEngine)
  simClustersANNSimilarityEngine: StandardSimilarityEngine[
    SimClustersANNSimilarityEngine.Query,
    TweetWithScore
  ],
  statsReceiver: StatsReceiver)
    etwittertends CandidateSource[
      SimClustersInterestedInCandidateGeneration.Query,
      Seq[TweetWithCandidateGenerationInfo]
    ] {

  override def name: String = this.getClass.getSimpleName
  private val stats = statsReceiver.scope(name)
  private val fetchCandidatesStat = stats.scope("fetchCandidates")

  override def get(
    query: SimClustersInterestedInCandidateGeneration.Query
  ): Future[Option[Seq[Seq[TweetWithCandidateGenerationInfo]]]] = {

    query.internalId match {
      case _: InternalId.UserId =>
        StatsUtil.trackOptionItemsStats(fetchCandidatesStat) {
          // UserInterestedIn Queries
          val userInterestedInCandidateResultFut =
            if (query.enableUserInterestedIn && query.enableProdSimClustersANNSimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.interestedInSimClustersANNQuery,
                query.simClustersInterestedInMinScore)
            else
              Future.None

          val userInterestedInEtwitterperimentalSANNCandidateResultFut =
            if (query.enableUserInterestedIn && query.enableEtwitterperimentalSimClustersANNSimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.interestedInEtwitterperimentalSimClustersANNQuery,
                query.simClustersInterestedInMinScore)
            else
              Future.None

          val userInterestedInSANN1CandidateResultFut =
            if (query.enableUserInterestedIn && query.enableSimClustersANN1SimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.interestedInSimClustersANN1Query,
                query.simClustersInterestedInMinScore)
            else
              Future.None

          val userInterestedInSANN2CandidateResultFut =
            if (query.enableUserInterestedIn && query.enableSimClustersANN2SimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.interestedInSimClustersANN2Query,
                query.simClustersInterestedInMinScore)
            else
              Future.None

          val userInterestedInSANN3CandidateResultFut =
            if (query.enableUserInterestedIn && query.enableSimClustersANN3SimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.interestedInSimClustersANN3Query,
                query.simClustersInterestedInMinScore)
            else
              Future.None

          val userInterestedInSANN5CandidateResultFut =
            if (query.enableUserInterestedIn && query.enableSimClustersANN5SimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.interestedInSimClustersANN5Query,
                query.simClustersInterestedInMinScore)
            else
              Future.None

          val userInterestedInSANN4CandidateResultFut =
            if (query.enableUserInterestedIn && query.enableSimClustersANN4SimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.interestedInSimClustersANN4Query,
                query.simClustersInterestedInMinScore)
            else
              Future.None
          // UserNetwittertInterestedIn Queries
          val userNetwittertInterestedInCandidateResultFut =
            if (query.enableUserNetwittertInterestedIn && query.enableProdSimClustersANNSimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.netwittertInterestedInSimClustersANNQuery,
                query.simClustersInterestedInMinScore)
            else
              Future.None

          val userNetwittertInterestedInEtwitterperimentalSANNCandidateResultFut =
            if (query.enableUserNetwittertInterestedIn && query.enableEtwitterperimentalSimClustersANNSimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.netwittertInterestedInEtwitterperimentalSimClustersANNQuery,
                query.simClustersInterestedInMinScore)
            else
              Future.None

          val userNetwittertInterestedInSANN1CandidateResultFut =
            if (query.enableUserNetwittertInterestedIn && query.enableSimClustersANN1SimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.netwittertInterestedInSimClustersANN1Query,
                query.simClustersInterestedInMinScore)
            else
              Future.None

          val userNetwittertInterestedInSANN2CandidateResultFut =
            if (query.enableUserNetwittertInterestedIn && query.enableSimClustersANN2SimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.netwittertInterestedInSimClustersANN2Query,
                query.simClustersInterestedInMinScore)
            else
              Future.None

          val userNetwittertInterestedInSANN3CandidateResultFut =
            if (query.enableUserNetwittertInterestedIn && query.enableSimClustersANN3SimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.netwittertInterestedInSimClustersANN3Query,
                query.simClustersInterestedInMinScore)
            else
              Future.None

          val userNetwittertInterestedInSANN5CandidateResultFut =
            if (query.enableUserNetwittertInterestedIn && query.enableSimClustersANN5SimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.netwittertInterestedInSimClustersANN5Query,
                query.simClustersInterestedInMinScore)
            else
              Future.None

          val userNetwittertInterestedInSANN4CandidateResultFut =
            if (query.enableUserNetwittertInterestedIn && query.enableSimClustersANN4SimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.netwittertInterestedInSimClustersANN4Query,
                query.simClustersInterestedInMinScore)
            else
              Future.None

          // AddressBookInterestedIn Queries
          val userAddressBookInterestedInCandidateResultFut =
            if (query.enableAddressBookNetwittertInterestedIn && query.enableProdSimClustersANNSimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.addressbookInterestedInSimClustersANNQuery,
                query.simClustersInterestedInMinScore)
            else
              Future.None

          val userAddressBookEtwitterperimentalSANNCandidateResultFut =
            if (query.enableAddressBookNetwittertInterestedIn && query.enableEtwitterperimentalSimClustersANNSimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.addressbookInterestedInEtwitterperimentalSimClustersANNQuery,
                query.simClustersInterestedInMinScore)
            else
              Future.None

          val userAddressBookSANN1CandidateResultFut =
            if (query.enableAddressBookNetwittertInterestedIn && query.enableSimClustersANN1SimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.addressbookInterestedInSimClustersANN1Query,
                query.simClustersInterestedInMinScore)
            else
              Future.None

          val userAddressBookSANN2CandidateResultFut =
            if (query.enableAddressBookNetwittertInterestedIn && query.enableSimClustersANN2SimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.addressbookInterestedInSimClustersANN2Query,
                query.simClustersInterestedInMinScore)
            else
              Future.None

          val userAddressBookSANN3CandidateResultFut =
            if (query.enableAddressBookNetwittertInterestedIn && query.enableSimClustersANN3SimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.addressbookInterestedInSimClustersANN3Query,
                query.simClustersInterestedInMinScore)
            else
              Future.None

          val userAddressBookSANN5CandidateResultFut =
            if (query.enableAddressBookNetwittertInterestedIn && query.enableSimClustersANN5SimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.addressbookInterestedInSimClustersANN5Query,
                query.simClustersInterestedInMinScore)
            else
              Future.None

          val userAddressBookSANN4CandidateResultFut =
            if (query.enableAddressBookNetwittertInterestedIn && query.enableSimClustersANN4SimilarityEngine)
              getInterestedInCandidateResult(
                simClustersANNSimilarityEngine,
                query.addressbookInterestedInSimClustersANN4Query,
                query.simClustersInterestedInMinScore)
            else
              Future.None

          Future
            .collect(
              Seq(
                userInterestedInCandidateResultFut,
                userNetwittertInterestedInCandidateResultFut,
                userAddressBookInterestedInCandidateResultFut,
                userInterestedInEtwitterperimentalSANNCandidateResultFut,
                userNetwittertInterestedInEtwitterperimentalSANNCandidateResultFut,
                userAddressBookEtwitterperimentalSANNCandidateResultFut,
                userInterestedInSANN1CandidateResultFut,
                userNetwittertInterestedInSANN1CandidateResultFut,
                userAddressBookSANN1CandidateResultFut,
                userInterestedInSANN2CandidateResultFut,
                userNetwittertInterestedInSANN2CandidateResultFut,
                userAddressBookSANN2CandidateResultFut,
                userInterestedInSANN3CandidateResultFut,
                userNetwittertInterestedInSANN3CandidateResultFut,
                userAddressBookSANN3CandidateResultFut,
                userInterestedInSANN5CandidateResultFut,
                userNetwittertInterestedInSANN5CandidateResultFut,
                userAddressBookSANN5CandidateResultFut,
                userInterestedInSANN4CandidateResultFut,
                userNetwittertInterestedInSANN4CandidateResultFut,
                userAddressBookSANN4CandidateResultFut
              )
            ).map { candidateResults =>
              Some(
                candidateResults.map(candidateResult => candidateResult.getOrElse(Seq.empty))
              )
            }
        }
      case _ =>
        stats.counter("sourceId_is_not_userId_cnt").incr()
        Future.None
    }
  }

  private def simClustersCandidateMinScoreFilter(
    simClustersAnnCandidates: Seq[TweetWithScore],
    simClustersInterestedInMinScore: Double,
    simClustersANNConfigId: String
  ): Seq[TweetWithScore] = {
    val filteredCandidates = simClustersAnnCandidates
      .filter { candidate =>
        candidate.score > simClustersInterestedInMinScore
      }

    stats.stat(simClustersANNConfigId, "simClustersAnnCandidates_size").add(filteredCandidates.size)
    stats.counter(simClustersANNConfigId, "simClustersAnnRequests").incr()
    if (filteredCandidates.isEmpty)
      stats.counter(simClustersANNConfigId, "emptyFilteredSimClustersAnnCandidates").incr()

    filteredCandidates.map { candidate =>
      TweetWithScore(candidate.tweetId, candidate.score)
    }
  }

  private def getInterestedInCandidateResult(
    simClustersANNSimilarityEngine: StandardSimilarityEngine[
      SimClustersANNSimilarityEngine.Query,
      TweetWithScore
    ],
    simClustersANNQuery: EngineQuery[SimClustersANNSimilarityEngine.Query],
    simClustersInterestedInMinScore: Double,
  ): Future[Option[Seq[TweetWithCandidateGenerationInfo]]] = {
    val interestedInCandidatesFut =
      simClustersANNSimilarityEngine.getCandidates(simClustersANNQuery)

    val interestedInCandidateResultFut = interestedInCandidatesFut.map { interestedInCandidates =>
      stats.stat("candidateSize").add(interestedInCandidates.size)

      val embeddingCandidatesStat = stats.scope(
        simClustersANNQuery.storeQuery.simClustersANNQuery.sourceEmbeddingId.embeddingType.name)

      embeddingCandidatesStat.stat("candidateSize").add(interestedInCandidates.size)
      if (interestedInCandidates.isEmpty) {
        embeddingCandidatesStat.counter("empty_results").incr()
      }
      embeddingCandidatesStat.counter("requests").incr()

      val filteredTweets = simClustersCandidateMinScoreFilter(
        interestedInCandidates.toSeq.flatten,
        simClustersInterestedInMinScore,
        simClustersANNQuery.storeQuery.simClustersANNConfigId)

      val interestedInTweetsWithCGInfo = filteredTweets.map { tweetWithScore =>
        TweetWithCandidateGenerationInfo(
          tweetWithScore.tweetId,
          CandidateGenerationInfo(
            None,
            SimClustersANNSimilarityEngine
              .toSimilarityEngineInfo(simClustersANNQuery, tweetWithScore.score),
            Seq.empty // SANN is an atomic SE, and hence it has no contributing SEs
          )
        )
      }

      val interestedInResults = if (interestedInTweetsWithCGInfo.nonEmpty) {
        Some(interestedInTweetsWithCGInfo)
      } else None
      interestedInResults
    }
    interestedInCandidateResultFut
  }
}

object SimClustersInterestedInCandidateGeneration {

  case class Query(
    internalId: InternalId,
    enableUserInterestedIn: Boolean,
    enableUserNetwittertInterestedIn: Boolean,
    enableAddressBookNetwittertInterestedIn: Boolean,
    enableProdSimClustersANNSimilarityEngine: Boolean,
    enableEtwitterperimentalSimClustersANNSimilarityEngine: Boolean,
    enableSimClustersANN1SimilarityEngine: Boolean,
    enableSimClustersANN2SimilarityEngine: Boolean,
    enableSimClustersANN3SimilarityEngine: Boolean,
    enableSimClustersANN5SimilarityEngine: Boolean,
    enableSimClustersANN4SimilarityEngine: Boolean,
    simClustersInterestedInMinScore: Double,
    simClustersNetwittertInterestedInMinScore: Double,
    simClustersAddressBookInterestedInMinScore: Double,
    interestedInSimClustersANNQuery: EngineQuery[SimClustersANNSimilarityEngine.Query],
    netwittertInterestedInSimClustersANNQuery: EngineQuery[SimClustersANNSimilarityEngine.Query],
    addressbookInterestedInSimClustersANNQuery: EngineQuery[SimClustersANNSimilarityEngine.Query],
    interestedInEtwitterperimentalSimClustersANNQuery: EngineQuery[SimClustersANNSimilarityEngine.Query],
    netwittertInterestedInEtwitterperimentalSimClustersANNQuery: EngineQuery[
      SimClustersANNSimilarityEngine.Query
    ],
    addressbookInterestedInEtwitterperimentalSimClustersANNQuery: EngineQuery[
      SimClustersANNSimilarityEngine.Query
    ],
    interestedInSimClustersANN1Query: EngineQuery[SimClustersANNSimilarityEngine.Query],
    netwittertInterestedInSimClustersANN1Query: EngineQuery[SimClustersANNSimilarityEngine.Query],
    addressbookInterestedInSimClustersANN1Query: EngineQuery[SimClustersANNSimilarityEngine.Query],
    interestedInSimClustersANN2Query: EngineQuery[SimClustersANNSimilarityEngine.Query],
    netwittertInterestedInSimClustersANN2Query: EngineQuery[SimClustersANNSimilarityEngine.Query],
    addressbookInterestedInSimClustersANN2Query: EngineQuery[SimClustersANNSimilarityEngine.Query],
    interestedInSimClustersANN3Query: EngineQuery[SimClustersANNSimilarityEngine.Query],
    netwittertInterestedInSimClustersANN3Query: EngineQuery[SimClustersANNSimilarityEngine.Query],
    addressbookInterestedInSimClustersANN3Query: EngineQuery[SimClustersANNSimilarityEngine.Query],
    interestedInSimClustersANN5Query: EngineQuery[SimClustersANNSimilarityEngine.Query],
    netwittertInterestedInSimClustersANN5Query: EngineQuery[SimClustersANNSimilarityEngine.Query],
    addressbookInterestedInSimClustersANN5Query: EngineQuery[SimClustersANNSimilarityEngine.Query],
    interestedInSimClustersANN4Query: EngineQuery[SimClustersANNSimilarityEngine.Query],
    netwittertInterestedInSimClustersANN4Query: EngineQuery[SimClustersANNSimilarityEngine.Query],
    addressbookInterestedInSimClustersANN4Query: EngineQuery[SimClustersANNSimilarityEngine.Query],
  )

  def fromParams(
    internalId: InternalId,
    params: configapi.Params,
  ): Query = {
    // SimClusters common configs
    val simClustersModelVersion =
      ModelVersions.Enum.enumToSimClustersModelVersionMap(params(GlobalParams.ModelVersionParam))
    val simClustersANNConfigId = params(SimClustersANNParams.SimClustersANNConfigId)
    val etwitterperimentalSimClustersANNConfigId = params(
      SimClustersANNParams.EtwitterperimentalSimClustersANNConfigId)
    val simClustersANN1ConfigId = params(SimClustersANNParams.SimClustersANN1ConfigId)
    val simClustersANN2ConfigId = params(SimClustersANNParams.SimClustersANN2ConfigId)
    val simClustersANN3ConfigId = params(SimClustersANNParams.SimClustersANN3ConfigId)
    val simClustersANN5ConfigId = params(SimClustersANNParams.SimClustersANN5ConfigId)
    val simClustersANN4ConfigId = params(SimClustersANNParams.SimClustersANN4ConfigId)

    val simClustersInterestedInMinScore = params(InterestedInParams.MinScoreParam)
    val simClustersNetwittertInterestedInMinScore = params(
      InterestedInParams.MinScoreSequentialModelParam)
    val simClustersAddressBookInterestedInMinScore = params(
      InterestedInParams.MinScoreAddressBookParam)

    // InterestedIn embeddings parameters
    val interestedInEmbedding = params(InterestedInParams.InterestedInEmbeddingIdParam)
    val netwittertInterestedInEmbedding = params(InterestedInParams.NetwittertInterestedInEmbeddingIdParam)
    val addressbookInterestedInEmbedding = params(
      InterestedInParams.AddressBookInterestedInEmbeddingIdParam)

    // Prod SimClustersANN Query
    val interestedInSimClustersANNQuery =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        interestedInEmbedding.embeddingType,
        simClustersModelVersion,
        simClustersANNConfigId,
        params)

    val netwittertInterestedInSimClustersANNQuery =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        netwittertInterestedInEmbedding.embeddingType,
        simClustersModelVersion,
        simClustersANNConfigId,
        params)

    val addressbookInterestedInSimClustersANNQuery =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        addressbookInterestedInEmbedding.embeddingType,
        simClustersModelVersion,
        simClustersANNConfigId,
        params)

    // Etwitterperimental SANN cluster Query
    val interestedInEtwitterperimentalSimClustersANNQuery =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        interestedInEmbedding.embeddingType,
        simClustersModelVersion,
        etwitterperimentalSimClustersANNConfigId,
        params)

    val netwittertInterestedInEtwitterperimentalSimClustersANNQuery =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        netwittertInterestedInEmbedding.embeddingType,
        simClustersModelVersion,
        etwitterperimentalSimClustersANNConfigId,
        params)

    val addressbookInterestedInEtwitterperimentalSimClustersANNQuery =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        addressbookInterestedInEmbedding.embeddingType,
        simClustersModelVersion,
        etwitterperimentalSimClustersANNConfigId,
        params)

    // SimClusters ANN cluster 1 Query
    val interestedInSimClustersANN1Query =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        interestedInEmbedding.embeddingType,
        simClustersModelVersion,
        simClustersANN1ConfigId,
        params)

    val netwittertInterestedInSimClustersANN1Query =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        netwittertInterestedInEmbedding.embeddingType,
        simClustersModelVersion,
        simClustersANN1ConfigId,
        params)

    val addressbookInterestedInSimClustersANN1Query =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        addressbookInterestedInEmbedding.embeddingType,
        simClustersModelVersion,
        simClustersANN1ConfigId,
        params)

    // SimClusters ANN cluster 2 Query
    val interestedInSimClustersANN2Query =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        interestedInEmbedding.embeddingType,
        simClustersModelVersion,
        simClustersANN2ConfigId,
        params)

    val netwittertInterestedInSimClustersANN2Query =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        netwittertInterestedInEmbedding.embeddingType,
        simClustersModelVersion,
        simClustersANN2ConfigId,
        params)

    val addressbookInterestedInSimClustersANN2Query =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        addressbookInterestedInEmbedding.embeddingType,
        simClustersModelVersion,
        simClustersANN2ConfigId,
        params)

    // SimClusters ANN cluster 3 Query
    val interestedInSimClustersANN3Query =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        interestedInEmbedding.embeddingType,
        simClustersModelVersion,
        simClustersANN3ConfigId,
        params)

    val netwittertInterestedInSimClustersANN3Query =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        netwittertInterestedInEmbedding.embeddingType,
        simClustersModelVersion,
        simClustersANN3ConfigId,
        params)

    val addressbookInterestedInSimClustersANN3Query =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        addressbookInterestedInEmbedding.embeddingType,
        simClustersModelVersion,
        simClustersANN3ConfigId,
        params)

    // SimClusters ANN cluster 5 Query
    val interestedInSimClustersANN5Query =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        interestedInEmbedding.embeddingType,
        simClustersModelVersion,
        simClustersANN5ConfigId,
        params)
    // SimClusters ANN cluster 4 Query
    val interestedInSimClustersANN4Query =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        interestedInEmbedding.embeddingType,
        simClustersModelVersion,
        simClustersANN4ConfigId,
        params)

    val netwittertInterestedInSimClustersANN5Query =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        netwittertInterestedInEmbedding.embeddingType,
        simClustersModelVersion,
        simClustersANN5ConfigId,
        params)

    val netwittertInterestedInSimClustersANN4Query =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        netwittertInterestedInEmbedding.embeddingType,
        simClustersModelVersion,
        simClustersANN4ConfigId,
        params)

    val addressbookInterestedInSimClustersANN5Query =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        addressbookInterestedInEmbedding.embeddingType,
        simClustersModelVersion,
        simClustersANN5ConfigId,
        params)

    val addressbookInterestedInSimClustersANN4Query =
      SimClustersANNSimilarityEngine.fromParams(
        internalId,
        addressbookInterestedInEmbedding.embeddingType,
        simClustersModelVersion,
        simClustersANN4ConfigId,
        params)

    Query(
      internalId = internalId,
      enableUserInterestedIn = params(InterestedInParams.EnableSourceParam),
      enableUserNetwittertInterestedIn = params(InterestedInParams.EnableSourceSequentialModelParam),
      enableAddressBookNetwittertInterestedIn = params(InterestedInParams.EnableSourceAddressBookParam),
      enableProdSimClustersANNSimilarityEngine =
        params(InterestedInParams.EnableProdSimClustersANNParam),
      enableEtwitterperimentalSimClustersANNSimilarityEngine =
        params(InterestedInParams.EnableEtwitterperimentalSimClustersANNParam),
      enableSimClustersANN1SimilarityEngine = params(InterestedInParams.EnableSimClustersANN1Param),
      enableSimClustersANN2SimilarityEngine = params(InterestedInParams.EnableSimClustersANN2Param),
      enableSimClustersANN3SimilarityEngine = params(InterestedInParams.EnableSimClustersANN3Param),
      enableSimClustersANN5SimilarityEngine = params(InterestedInParams.EnableSimClustersANN5Param),
      enableSimClustersANN4SimilarityEngine = params(InterestedInParams.EnableSimClustersANN4Param),
      simClustersInterestedInMinScore = simClustersInterestedInMinScore,
      simClustersNetwittertInterestedInMinScore = simClustersNetwittertInterestedInMinScore,
      simClustersAddressBookInterestedInMinScore = simClustersAddressBookInterestedInMinScore,
      interestedInSimClustersANNQuery = interestedInSimClustersANNQuery,
      netwittertInterestedInSimClustersANNQuery = netwittertInterestedInSimClustersANNQuery,
      addressbookInterestedInSimClustersANNQuery = addressbookInterestedInSimClustersANNQuery,
      interestedInEtwitterperimentalSimClustersANNQuery = interestedInEtwitterperimentalSimClustersANNQuery,
      netwittertInterestedInEtwitterperimentalSimClustersANNQuery =
        netwittertInterestedInEtwitterperimentalSimClustersANNQuery,
      addressbookInterestedInEtwitterperimentalSimClustersANNQuery =
        addressbookInterestedInEtwitterperimentalSimClustersANNQuery,
      interestedInSimClustersANN1Query = interestedInSimClustersANN1Query,
      netwittertInterestedInSimClustersANN1Query = netwittertInterestedInSimClustersANN1Query,
      addressbookInterestedInSimClustersANN1Query = addressbookInterestedInSimClustersANN1Query,
      interestedInSimClustersANN2Query = interestedInSimClustersANN2Query,
      netwittertInterestedInSimClustersANN2Query = netwittertInterestedInSimClustersANN2Query,
      addressbookInterestedInSimClustersANN2Query = addressbookInterestedInSimClustersANN2Query,
      interestedInSimClustersANN3Query = interestedInSimClustersANN3Query,
      netwittertInterestedInSimClustersANN3Query = netwittertInterestedInSimClustersANN3Query,
      addressbookInterestedInSimClustersANN3Query = addressbookInterestedInSimClustersANN3Query,
      interestedInSimClustersANN5Query = interestedInSimClustersANN5Query,
      netwittertInterestedInSimClustersANN5Query = netwittertInterestedInSimClustersANN5Query,
      addressbookInterestedInSimClustersANN5Query = addressbookInterestedInSimClustersANN5Query,
      interestedInSimClustersANN4Query = interestedInSimClustersANN4Query,
      netwittertInterestedInSimClustersANN4Query = netwittertInterestedInSimClustersANN4Query,
      addressbookInterestedInSimClustersANN4Query = addressbookInterestedInSimClustersANN4Query,
    )
  }
}
