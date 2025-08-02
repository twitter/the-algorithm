package com.twitter.cr_mitwitterer.param

import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.logging.Logger
import com.twitter.simclusters_v2.thriftscala.{EmbeddingType => SimClustersEmbeddingType}
import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.Param

object InterestedInParams {

  object SourceEmbedding etwittertends Enumeration {
    protected case class EmbeddingType(embeddingType: SimClustersEmbeddingType) etwittertends super.Val
    import scala.language.implicitConversions
    implicit def valueToEmbeddingtype(twitter: Value): EmbeddingType = twitter.asInstanceOf[EmbeddingType]

    val UserInterestedIn: Value = EmbeddingType(SimClustersEmbeddingType.FilteredUserInterestedIn)
    val UnfilteredUserInterestedIn: Value = EmbeddingType(
      SimClustersEmbeddingType.UnfilteredUserInterestedIn)
    val FromProducerEmbedding: Value = EmbeddingType(
      SimClustersEmbeddingType.FilteredUserInterestedInFromPE)
    val LogFavBasedUserInterestedInFromAPE: Value = EmbeddingType(
      SimClustersEmbeddingType.LogFavBasedUserInterestedInFromAPE)
    val FollowBasedUserInterestedInFromAPE: Value = EmbeddingType(
      SimClustersEmbeddingType.FollowBasedUserInterestedInFromAPE)
    val UserNetwittertInterestedIn: Value = EmbeddingType(SimClustersEmbeddingType.UserNetwittertInterestedIn)
    // AddressBook based InterestedIn
    val LogFavBasedUserInterestedAverageAddressBookFromIIAPE: Value = EmbeddingType(
      SimClustersEmbeddingType.LogFavBasedUserInterestedAverageAddressBookFromIIAPE)
    val LogFavBasedUserInterestedMatwitterpoolingAddressBookFromIIAPE: Value = EmbeddingType(
      SimClustersEmbeddingType.LogFavBasedUserInterestedMatwitterpoolingAddressBookFromIIAPE)
    val LogFavBasedUserInterestedBooktypeMatwitterpoolingAddressBookFromIIAPE: Value = EmbeddingType(
      SimClustersEmbeddingType.LogFavBasedUserInterestedBooktypeMatwitterpoolingAddressBookFromIIAPE)
    val LogFavBasedUserInterestedLargestDimMatwitterpoolingAddressBookFromIIAPE: Value = EmbeddingType(
      SimClustersEmbeddingType.LogFavBasedUserInterestedLargestDimMatwitterpoolingAddressBookFromIIAPE)
    val LogFavBasedUserInterestedLouvainMatwitterpoolingAddressBookFromIIAPE: Value = EmbeddingType(
      SimClustersEmbeddingType.LogFavBasedUserInterestedLouvainMatwitterpoolingAddressBookFromIIAPE)
    val LogFavBasedUserInterestedConnectedMatwitterpoolingAddressBookFromIIAPE: Value = EmbeddingType(
      SimClustersEmbeddingType.LogFavBasedUserInterestedConnectedMatwitterpoolingAddressBookFromIIAPE)
  }

  object EnableSourceParam
      etwittertends FSParam[Boolean](
        name = "twistly_interestedin_enable_source",
        default = true
      )

  object InterestedInEmbeddingIdParam
      etwittertends FSEnumParam[SourceEmbedding.type](
        name = "twistly_interestedin_embedding_id",
        default = SourceEmbedding.UnfilteredUserInterestedIn,
        enum = SourceEmbedding
      )

  object MinScoreParam
      etwittertends FSBoundedParam[Double](
        name = "twistly_interestedin_min_score",
        default = 0.072,
        min = 0.0,
        matwitter = 1.0
      )

  object EnableSourceSequentialModelParam
      etwittertends FSParam[Boolean](
        name = "twistly_interestedin_sequential_model_enable_source",
        default = false
      )

  object NetwittertInterestedInEmbeddingIdParam
      etwittertends FSEnumParam[SourceEmbedding.type](
        name = "twistly_interestedin_sequential_model_embedding_id",
        default = SourceEmbedding.UserNetwittertInterestedIn,
        enum = SourceEmbedding
      )

  object MinScoreSequentialModelParam
      etwittertends FSBoundedParam[Double](
        name = "twistly_interestedin_sequential_model_min_score",
        default = 0.0,
        min = 0.0,
        matwitter = 1.0
      )

  object EnableSourceAddressBookParam
      etwittertends FSParam[Boolean](
        name = "twistly_interestedin_addressbook_enable_source",
        default = false
      )

  object AddressBookInterestedInEmbeddingIdParam
      etwittertends FSEnumParam[SourceEmbedding.type](
        name = "twistly_interestedin_addressbook_embedding_id",
        default = SourceEmbedding.LogFavBasedUserInterestedLouvainMatwitterpoolingAddressBookFromIIAPE,
        enum = SourceEmbedding
      )

  object MinScoreAddressBookParam
      etwittertends FSBoundedParam[Double](
        name = "twistly_interestedin_addressbook_min_score",
        default = 0.0,
        min = 0.0,
        matwitter = 1.0
      )

  // Prod SimClusters ANN param
  // This is used to enable/disable querying of production SANN service. Useful when etwitterperimenting
  // with replacements to it.
  object EnableProdSimClustersANNParam
      etwittertends FSParam[Boolean](
        name = "twistly_interestedin_enable_prod_simclusters_ann",
        default = true
      )

  // Etwitterperimental SimClusters ANN params
  object EnableEtwitterperimentalSimClustersANNParam
      etwittertends FSParam[Boolean](
        name = "twistly_interestedin_enable_etwitterperimental_simclusters_ann",
        default = false
      )

  // SimClusters ANN 1 cluster params
  object EnableSimClustersANN1Param
      etwittertends FSParam[Boolean](
        name = "twistly_interestedin_enable_simclusters_ann_1",
        default = false
      )

  // SimClusters ANN 2 cluster params
  object EnableSimClustersANN2Param
      etwittertends FSParam[Boolean](
        name = "twistly_interestedin_enable_simclusters_ann_2",
        default = false
      )

  // SimClusters ANN 3 cluster params
  object EnableSimClustersANN3Param
      etwittertends FSParam[Boolean](
        name = "twistly_interestedin_enable_simclusters_ann_3",
        default = false
      )

  // SimClusters ANN 5 cluster params
  object EnableSimClustersANN5Param
      etwittertends FSParam[Boolean](
        name = "twistly_interestedin_enable_simclusters_ann_5",
        default = false
      )

  // SimClusters ANN 4 cluster params
  object EnableSimClustersANN4Param
      etwittertends FSParam[Boolean](
        name = "twistly_interestedin_enable_simclusters_ann_4",
        default = false
      )
  val AllParams: Seq[Param[_] with FSName] = Seq(
    EnableSourceParam,
    EnableSourceSequentialModelParam,
    EnableSourceAddressBookParam,
    EnableProdSimClustersANNParam,
    EnableEtwitterperimentalSimClustersANNParam,
    EnableSimClustersANN1Param,
    EnableSimClustersANN2Param,
    EnableSimClustersANN3Param,
    EnableSimClustersANN5Param,
    EnableSimClustersANN4Param,
    MinScoreParam,
    MinScoreSequentialModelParam,
    MinScoreAddressBookParam,
    InterestedInEmbeddingIdParam,
    NetwittertInterestedInEmbeddingIdParam,
    AddressBookInterestedInEmbeddingIdParam,
  )

  lazy val config: BaseConfig = {

    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableSourceParam,
      EnableSourceSequentialModelParam,
      EnableSourceAddressBookParam,
      EnableProdSimClustersANNParam,
      EnableEtwitterperimentalSimClustersANNParam,
      EnableSimClustersANN1Param,
      EnableSimClustersANN2Param,
      EnableSimClustersANN3Param,
      EnableSimClustersANN5Param,
      EnableSimClustersANN4Param
    )

    val doubleOverrides = FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides(
      MinScoreParam,
      MinScoreSequentialModelParam,
      MinScoreAddressBookParam)

    val enumOverrides = FeatureSwitchOverrideUtil.getEnumFSOverrides(
      NullStatsReceiver,
      Logger(getClass),
      InterestedInEmbeddingIdParam,
      NetwittertInterestedInEmbeddingIdParam,
      AddressBookInterestedInEmbeddingIdParam
    )

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(doubleOverrides: _*)
      .set(enumOverrides: _*)
      .build()
  }
}
