package com.twitter.product_mitwitterer.core.service

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.ProductPipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.PipelineStepIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineResult
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.service.Etwitterecutor.Contetwittert
import com.twitter.product_mitwitterer.shared_library.observer.Observer
import com.twitter.product_mitwitterer.shared_library.observer.Observer.Observer
import com.twitter.product_mitwitterer.shared_library.observer.ResultsStatsObserver.ResultsStatsObserver
import com.twitter.util.Duration
import com.twitter.util.Return
import com.twitter.util.Throw
import com.twitter.util.Try

private[core] object EtwitterecutorObserver {

  /** Make a [[EtwitterecutorObserver]] with stats for the [[ComponentIdentifier]] and relative to the parent in the [[Contetwittert.componentStack]] */
  def etwitterecutorObserver[T](
    contetwittert: Contetwittert,
    currentComponentIdentifier: ComponentIdentifier,
    statsReceiver: StatsReceiver
  ): EtwitterecutorObserver[T] = new EtwitterecutorObserver[T](
    Etwitterecutor.broadcastStatsReceiver(contetwittert, currentComponentIdentifier, statsReceiver))

  /** Make a [[EtwitterecutorObserverWithSize]] with stats for the [[ComponentIdentifier]] and relative to the parent in the [[Contetwittert.componentStack]] */
  def etwitterecutorObserverWithSize(
    contetwittert: Contetwittert,
    currentComponentIdentifier: ComponentIdentifier,
    statsReceiver: StatsReceiver
  ): EtwitterecutorObserverWithSize = new EtwitterecutorObserverWithSize(
    Etwitterecutor.broadcastStatsReceiver(contetwittert, currentComponentIdentifier, statsReceiver))

  /** Make a [[PipelineEtwitterecutorObserver]] with stats for the [[ComponentIdentifier]] and relative to the parent in the [[Contetwittert.componentStack]] */
  def pipelineEtwitterecutorObserver[T <: PipelineResult[_]](
    contetwittert: Contetwittert,
    currentComponentIdentifier: ComponentIdentifier,
    statsReceiver: StatsReceiver
  ): PipelineEtwitterecutorObserver[T] = new PipelineEtwitterecutorObserver[T](
    Etwitterecutor.broadcastStatsReceiver(contetwittert, currentComponentIdentifier, statsReceiver))

  /**
   * Make a [[PipelineEtwitterecutorObserver]] specifically for a [[com.twitter.product_mitwitterer.core.pipeline.product.ProductPipeline]]
   * with no relative stats
   */
  def productPipelineEtwitterecutorObserver[T <: PipelineResult[_]](
    currentComponentIdentifier: ProductPipelineIdentifier,
    statsReceiver: StatsReceiver
  ): PipelineEtwitterecutorObserver[T] =
    new PipelineEtwitterecutorObserver[T](statsReceiver.scope(currentComponentIdentifier.toScopes: _*))

  /**
   * Make a [[PipelineEtwitterecutorObserver]] with only stats relative to the parent pipeline
   * for [[com.twitter.product_mitwitterer.core.pipeline.PipelineBuilder.Step]]s
   */
  def stepEtwitterecutorObserver(
    contetwittert: Contetwittert,
    currentComponentIdentifier: PipelineStepIdentifier,
    statsReceiver: StatsReceiver
  ): EtwitterecutorObserver[Unit] = {
    new EtwitterecutorObserver[Unit](
      statsReceiver.scope(
        Etwitterecutor.buildScopes(contetwittert, currentComponentIdentifier).relativeScope: _*))
  }
}

/**
 * An [[Observer]] which is called as a side effect. Unlike the other observers which wrap a computation,
 * this [[Observer]] etwitterpects the caller to provide the latency value and wire it in
 */
private[core] sealed class EtwitterecutorObserver[T](
  override val statsReceiver: StatsReceiver)
    etwittertends {

  /**
   * always empty because we etwitterpect an already scoped [[com.twitter.finagle.stats.BroadcastStatsReceiver]] to be passed in
   * @note uses early definitions [[https://docs.scala-lang.org/tutorials/FAQ/initialization-order.html]] to avoid null values for `scopes` in [[Observer]]
   */
  override val scopes: Seq[String] = Seq.empty
} with Observer[T] {

  /**
   * Serialize the provided [[Throwable]], prefitwittering [[PipelineFailure]]s with their
   * [[com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailureCategory.categoryName]] and
   * [[com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailureCategory.failureName]]
   */
  override def serializeThrowable(throwable: Throwable): Seq[String] = {
    throwable match {
      case PipelineFailure(category, _, None, _) =>
        Seq(category.categoryName, category.failureName)
      case PipelineFailure(category, _, Some(underlying), _) =>
        Seq(category.categoryName, category.failureName) ++ serializeThrowable(underlying)
      case throwable: Throwable => super.serializeThrowable(throwable)
    }
  }

  /** record success, failure, and latency stats based on `t` and `latency` */
  def apply(t: Try[T], latency: Duration): Unit = observe(t, latency)
}

/**
 * Same as [[EtwitterecutorObserver]] but records a size stat for [[PipelineResult]]s and
 * records a failure counter for the cause of the failure under `failures/$pipelineFailureCategory/$componentType/$componentName`.
 *
 * @etwitterample if `GateIdentifier("MyGate")` is at the top of the [[PipelineFailure.componentStack]] then
 *          the resulting metric `failures/ClientFailure/Gate/MyGate` will be incremented.
 */
private[core] final class PipelineEtwitterecutorObserver[T <: PipelineResult[_]](
  override val statsReceiver: StatsReceiver)
    etwittertends EtwitterecutorObserver[T](statsReceiver)
    with ResultsStatsObserver[T] {
  override val size: T => Int = _.resultSize()

  override def apply(t: Try[T], latency: Duration): Unit = {
    super.apply(t, latency)
    t match {
      case Return(result) => observeResults(result)
      case Throw(PipelineFailure(category, _, _, Some(componentIdentifierStack))) =>
        statsReceiver
          .counter(
            Seq(
              Observer.Failures,
              category.categoryName,
              category.failureName) ++ componentIdentifierStack.peek.toScopes: _*).incr()
      case _ =>
    }
  }
}

/** Same as [[EtwitterecutorObserver]] but records a size stat */
private[core] final class EtwitterecutorObserverWithSize(
  override val statsReceiver: StatsReceiver)
    etwittertends EtwitterecutorObserver[Int](statsReceiver)
    with ResultsStatsObserver[Int] {
  override val size: Int => Int = identity

  override def apply(t: Try[Int], latency: Duration): Unit = {
    super.apply(t, latency)
    t match {
      case Return(result) => observeResults(result)
      case _ =>
    }
  }
}
