package com.twitter.cr_mixer.param

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

  object SourceEmbedding extends Enumeration {
    protected case class EmbeddingType(embeddingType: SimClustersEmbeddingType) extends super.Val
    import scala.language.implicitConversions
    implicit def valueToEmbeddingtype(x: Value): EmbeddingType = x.asInstanceOf[EmbeddingType]

    val UserInterestedIn: Value = EmbeddingType(SimClustersEmbeddingType.FilteredUserInterestedIn)
    val UnfilteredUserInterestedIn: Value = EmbeddingType(
      SimClustersEmbeddingType.UnfilteredUserInterestedIn)
    val FromProducerEmbedding: Value = EmbeddingType(
      SimClustersEmbeddingType.FilteredUserInterestedInFromPE)
    val LogFavBasedUserInterestedInFromAPE: Value = EmbeddingType(
      SimClustersEmbeddingType.LogFavBasedUserInterestedInFromAPE)
    val FollowBasedUserInterestedInFromAPE: Value = EmbeddingType(
      SimClustersEmbeddingType.FollowBasedUserInterestedInFromAPE)
    val UserNextInterestedIn: Value = EmbeddingType(SimClustersEmbeddingType.UserNextInterestedIn)
    // AddressBook based InterestedIn
    val LogFavBasedUserInterestedAverageAddressBookFromIIAPE: Value = EmbeddingType(
      SimClustersEmbeddingType.LogFavBasedUserInterestedAverageAddressBookFromIIAPE)
    val LogFavBasedUserInterestedMaxpoolingAddressBookFromIIAPE: Value = EmbeddingType(
      SimClustersEmbeddingType.LogFavBasedUserInterestedMaxpoolingAddressBookFromIIAPE)
    val LogFavBasedUserInterestedBooktypeMaxpoolingAddressBookFromIIAPE: Value = EmbeddingType(
      SimClustersEmbeddingType.LogFavBasedUserInterestedBooktypeMaxpoolingAddressBookFromIIAPE)
    val LogFavBasedUserInterestedLargestDimMaxpoolingAddressBookFromIIAPE: Value = EmbeddingType(
      SimClustersEmbeddingType.LogFavBasedUserInterestedLargestDimMaxpoolingAddressBookFromIIAPE)
    val LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE: Value = EmbeddingType(
      SimClustersEmbeddingType.LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE)
    val LogFavBasedUserInterestedConnectedMaxpoolingAddressBookFromIIAPE: Value = EmbeddingType(
      SimClustersEmbeddingType.LogFavBasedUserInterestedConnectedMaxpoolingAddressBookFromIIAPE)
  }

  object EnableSourceParam
      extends FSParam[Boolean](
        name = "twistly_interestedin_enable_source",
        default = true
      )

  object InterestedInEmbeddingIdParam
      extends FSEnumParam[SourceEmbedding.type](
        name = "twistly_interestedin_embedding_id",
        default = SourceEmbedding.UnfilteredUserInterestedIn,
        enum = SourceEmbedding
      )

  object MinScoreParam
      extends FSBoundedParam[Double](
        name = "twistly_interestedin_min_score",
        default = 0.072,
        min = 0.0,
        max = 1.0
      )

  object EnableSourceSequentialModelParam
      extends FSParam[Boolean](
        name = "twistly_interestedin_sequential_model_enable_source",
        default = false
      )

  object NextInterestedInEmbeddingIdParam
      extends FSEnumParam[SourceEmbedding.type](
        name = "twistly_interestedin_sequential_model_embedding_id",
        default = SourceEmbedding.UserNextInterestedIn,
        enum = SourceEmbedding
      )

  object MinScoreSequentialModelParam
      extends FSBoundedParam[Double](
        name = "twistly_interestedin_sequential_model_min_score",
        default = 0.0,
        min = 0.0,
        max = 1.0
      )

  object EnableSourceAddressBookParam
      extends FSParam[Boolean](
        name = "twistly_interestedin_addressbook_enable_source",
        default = false
      )

  object AddressBookInterestedInEmbeddingIdParam
      extends FSEnumParam[SourceEmbedding.type](
        name = "twistly_interestedin_addressbook_embedding_id",
        default = SourceEmbedding.LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE,
        enum = SourceEmbedding
      )

  object MinScoreAddressBookParam
      extends FSBoundedParam[Double](
        name = "twistly_interestedin_addressbook_min_score",
        default = 0.0,
        min = 0.0,
        max = 1.0
      )

  // Prod SimClusters ANN param
  // This is used to enable/disable querying of production SANN service. Useful when experimenting
  // with replacements to it.
  object EnableProdSimClustersANNParam
      extends FSParam[Boolean](
        name = "twistly_interestedin_enable_prod_simclusters_ann",
        default = true
      )

  // Experimental SimClusters ANN params
  object EnableExperimentalSimClustersANNParam
      extends FSParam[Boolean](
        name = "twistly_interestedin_enable_experimental_simclusters_ann",
        default = false
      )

  // SimClusters ANN 1 cluster params
  object EnableSimClustersANN1Param
      extends FSParam[Boolean](
        name = "twistly_interestedin_enable_simclusters_ann_1",
        default = false
      )

  // SimClusters ANN 2 cluster params
  object EnableSimClustersANN2Param
      extends FSParam[Boolean](
        name = "twistly_interestedin_enable_simclusters_ann_2",
        default = false
      )

  // SimClusters ANN 3 cluster params
  object EnableSimClustersANN3Param
      extends FSParam[Boolean](
        name = "twistly_interestedin_enable_simclusters_ann_3",
        default = false
      )

  // SimClusters ANN 5 cluster params
  object EnableSimClustersANN5Param
      extends FSParam[Boolean](
        name = "twistly_interestedin_enable_simclusters_ann_5",
        default = false
      )

  // SimClusters ANN 4 cluster params
  object EnableSimClustersANN4Param
      extends FSParam[Boolean](
        name = "twistly_interestedin_enable_simclusters_ann_4",
        default = false
      )
  val AllParams: Seq[Param[_] with FSName] = Seq(
    EnableSourceParam,
    EnableSourceSequentialModelParam,
    EnableSourceAddressBookParam,
    EnableProdSimClustersANNParam,
    EnableExperimentalSimClustersANNParam,
    EnableSimClustersANN1Param,
    EnableSimClustersANN2Param,
    EnableSimClustersANN3Param,
    EnableSimClustersANN5Param,
    EnableSimClustersANN4Param,
    MinScoreParam,
    MinScoreSequentialModelParam,
    MinScoreAddressBookParam,
    InterestedInEmbeddingIdParam,
    NextInterestedInEmbeddingIdParam,
    AddressBookInterestedInEmbeddingIdParam,
  )

  lazy val config: BaseConfig = {

    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      EnableSourceParam,
      EnableSourceSequentialModelParam,
      EnableSourceAddressBookParam,
      EnableProdSimClustersANNParam,
      EnableExperimentalSimClustersANNParam,
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
      NextInterestedInEmbeddingIdParam,
      AddressBookInterestedInEmbeddingIdParam
    )

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(doubleOverrides: _*)
      .set(enumOverrides: _*)
      .build()
  }
}
