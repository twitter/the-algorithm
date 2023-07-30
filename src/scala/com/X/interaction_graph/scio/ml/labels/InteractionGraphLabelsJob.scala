package com.X.interaction_graph.scio.ml.labels

import com.google.api.services.bigquery.model.TimePartitioning
import com.spotify.scio.ScioContext
import com.spotify.scio.values.SCollection
import com.X.beam.io.dal.DAL
import com.X.beam.io.fs.multiformat.DiskFormat
import com.X.beam.io.fs.multiformat.PathLayout
import com.X.beam.io.fs.multiformat.WriteOptions
import com.X.beam.job.ServiceIdentifierOptions
import com.X.cde.scio.dal_read.SourceUtil
import com.X.conversions.DurationOps._
import com.X.dal.client.dataset.TimePartitionedDALDataset
import com.X.interaction_graph.scio.agg_client_event_logs.InteractionGraphAggClientEventLogsEdgeDailyScalaDataset
import com.X.interaction_graph.scio.agg_direct_interactions.InteractionGraphAggDirectInteractionsEdgeDailyScalaDataset
import com.X.interaction_graph.scio.agg_notifications.InteractionGraphAggNotificationsEdgeDailyScalaDataset
import com.X.interaction_graph.thriftscala.Edge
import com.X.interaction_graph.thriftscala.EdgeLabel
import com.X.scio_internal.job.ScioBeamJob
import com.X.socialgraph.event.thriftscala.FollowEvent
import com.X.socialgraph.hadoop.SocialgraphFollowEventsScalaDataset
import com.X.statebird.v2.thriftscala.Environment
import com.X.tcdc.bqblaster.beam.syntax._
import com.X.tcdc.bqblaster.core.avro.TypedProjection
import com.X.tcdc.bqblaster.core.transform.RootTransform
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO
import org.joda.time.Interval

object InteractionGraphLabelsJob extends ScioBeamJob[InteractionGraphLabelsOption] {

  override protected def configurePipeline(
    scioContext: ScioContext,
    pipelineOptions: InteractionGraphLabelsOption
  ): Unit = {
    @transient
    implicit lazy val sc: ScioContext = scioContext
    implicit lazy val dateInterval: Interval = pipelineOptions.interval

    val bqTableName: String = pipelineOptions.getBqTableName
    val dalEnvironment: String = pipelineOptions
      .as(classOf[ServiceIdentifierOptions])
      .getEnvironment()
    val dalWriteEnvironment = if (pipelineOptions.getDALWriteEnvironment != null) {
      pipelineOptions.getDALWriteEnvironment
    } else {
      dalEnvironment
    }

    def readPartition[T: Manifest](dataset: TimePartitionedDALDataset[T]): SCollection[T] = {
      SourceUtil.readDALDataset[T](
        dataset = dataset,
        interval = dateInterval,
        dalEnvironment = dalEnvironment
      )
    }

    val follows = readPartition[FollowEvent](SocialgraphFollowEventsScalaDataset)
      .flatMap(LabelUtil.fromFollowEvent)

    val directInteractions =
      readPartition[Edge](InteractionGraphAggDirectInteractionsEdgeDailyScalaDataset)
        .flatMap(LabelUtil.fromInteractionGraphEdge)

    val clientEvents =
      readPartition[Edge](InteractionGraphAggClientEventLogsEdgeDailyScalaDataset)
        .flatMap(LabelUtil.fromInteractionGraphEdge)

    val pushEvents =
      readPartition[Edge](InteractionGraphAggNotificationsEdgeDailyScalaDataset)
        .flatMap(LabelUtil.fromInteractionGraphEdge)


    val labels = groupLabels(
      follows ++
        directInteractions ++
        clientEvents ++
        pushEvents)

    labels.saveAsCustomOutput(
      "Write Edge Labels",
      DAL.write[EdgeLabel](
        InteractionGraphLabelsDailyScalaDataset,
        PathLayout.DailyPath(pipelineOptions.getOutputPath),
        dateInterval,
        DiskFormat.Parquet,
        Environment.valueOf(dalWriteEnvironment),
        writeOption = WriteOptions(numOfShards = Some(pipelineOptions.getNumberOfShards))
      )
    )

    // save to BQ
    if (pipelineOptions.getBqTableName != null) {
      val ingestionTime = pipelineOptions.getDate().value.getStart.toDate
      val bqFieldsTransform = RootTransform
        .Builder()
        .withPrependedFields("dateHour" -> TypedProjection.fromConstant(ingestionTime))
      val timePartitioning = new TimePartitioning()
        .setType("DAY").setField("dateHour").setExpirationMs(90.days.inMilliseconds)
      val bqWriter = BigQueryIO
        .write[EdgeLabel]
        .to(bqTableName)
        .withExtendedErrorInfo()
        .withTimePartitioning(timePartitioning)
        .withLoadJobProjectId("twttr-recos-ml-prod")
        .withThriftSupport(bqFieldsTransform.build(), AvroConverter.Legacy)
        .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
        .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)
      labels
        .saveAsCustomOutput(
          s"Save Recommendations to BQ $bqTableName",
          bqWriter
        )
    }

  }

  def groupLabels(labels: SCollection[EdgeLabel]): SCollection[EdgeLabel] = {
    labels
      .map { e: EdgeLabel => ((e.sourceId, e.destinationId), e.labels.toSet) }
      .sumByKey
      .map { case ((srcId, destId), labels) => EdgeLabel(srcId, destId, labels) }
  }
}
