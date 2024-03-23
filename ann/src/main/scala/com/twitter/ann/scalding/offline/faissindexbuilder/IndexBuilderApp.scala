package com.ExTwitter.ann.scalding.offline.faissindexbuilder

import com.ExTwitter.ann.common.Distance
import com.ExTwitter.ann.common.Metric
import com.ExTwitter.cortex.ml.embeddings.common._
import com.ExTwitter.ml.featurestore.lib.UserId
import com.ExTwitter.scalding.Args
import com.ExTwitter.scalding.DateOps
import com.ExTwitter.scalding.DateParser
import com.ExTwitter.scalding.DateRange
import com.ExTwitter.scalding.Execution
import com.ExTwitter.scalding_internal.job.ExTwitterExecutionApp
import com.ExTwitter.search.common.file.FileUtils
import com.ExTwitter.util.logging.Logging
import java.util.Calendar
import java.util.TimeZone

trait IndexBuilderExecutable extends Logging {
  // This method is used to cast the entityKind and the metric to have parameters.
  def indexBuilderExecution[T <: UserId, D <: Distance[D]](
    args: Args
  ): Execution[Unit] = {
    // parse the arguments for this job
    val uncastEntityKind = EntityKind.getEntityKind(args("entity_kind"))
    val uncastMetric = Metric.fromString(args("metric"))
    val entityKind = uncastEntityKind.asInstanceOf[EntityKind[T]]
    val metric = uncastMetric.asInstanceOf[Metric[D]]
    val uncastDateRange = args.list("embedding_date_range")
    val embeddingDateRange = if (uncastDateRange.nonEmpty) {
      Some(DateRange.parse(uncastDateRange)(DateOps.UTC, DateParser.default))
    } else {
      None
    }
    val embeddingFormat =
      entityKind.parser.getEmbeddingFormat(args, "input", providedDateRange = embeddingDateRange)
    val numDimensions = args.int("num_dimensions")
    val embeddingLimit = args.optional("embedding_limit").map(_.toInt)
    val outputDirectory = FileUtils.getFileHandle(args("output_dir"))
    val factoryString = args.optional("factory_string").get
    val sampleRate = args.float("training_sample_rate", 0.05f)

    logger.debug(s"Job args: ${args.toString}")

    val finalOutputDirectory = embeddingDateRange
      .map { range =>
        val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        cal.setTime(range.end)
        outputDirectory
          .getChild(s"${cal.get(Calendar.YEAR)}")
          .getChild(f"${cal.get(Calendar.MONTH) + 1}%02d")
          .getChild(f"${cal.get(Calendar.DAY_OF_MONTH)}%02d")
      }.getOrElse(outputDirectory)

    logger.info(s"Final output directory is ${finalOutputDirectory.getPath}")

    IndexBuilder
      .run(
        embeddingFormat,
        embeddingLimit,
        sampleRate,
        factoryString,
        metric,
        finalOutputDirectory,
        numDimensions
      ).onComplete { _ =>
        Unit
      }
  }
}

object IndexBuilderApp extends ExTwitterExecutionApp with IndexBuilderExecutable {
  override def job: Execution[Unit] = Execution.getArgs.flatMap { args: Args =>
    indexBuilderExecution(args)
  }
}
