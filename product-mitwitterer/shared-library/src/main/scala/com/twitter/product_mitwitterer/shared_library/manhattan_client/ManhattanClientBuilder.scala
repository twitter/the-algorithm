package com.twitter.product_mitwitterer.shared_library.manhattan_client

import com.twitter.finagle.mtls.authentication.EmptyServiceIdentifier
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.ssl.OpportunisticTls
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.manhattan.v1.{thriftscala => mh}
import com.twitter.storage.client.manhattan.kv.Etwitterperiments
import com.twitter.storage.client.manhattan.kv.Etwitterperiments.Etwitterperiment
import com.twitter.storage.client.manhattan.kv.Guarantee
import com.twitter.storage.client.manhattan.kv.ManhattanKVClient
import com.twitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.twitter.storage.client.manhattan.kv.ManhattanKVEndpoint
import com.twitter.storage.client.manhattan.kv.ManhattanKVEndpointBuilder
import com.twitter.storage.client.manhattan.kv.NoMtlsParams
import com.twitter.storehaus_internal.manhattan.ManhattanCluster
import com.twitter.util.Duration

object ManhattanClientBuilder {

  /**
   * Build a ManhattanKVClient/Endpoint [[ManhattanKVEndpoint]] / [[ManhattanKVClient]]
   *
   * @param cluster Manhattan cluster
   * @param appId Manhattan appid
   * @param numTries Matwitter number of times to try
   * @param matwitterTimeout Matwitter request timeout
   * @param matwitterItemsPerRequest Matwitter items per request
   * @param guarantee Consistency guarantee
   * @param serviceIdentifier Service ID used to S2S Auth
   * @param statsReceiver Stats
   * @param etwitterperiments MH client etwitterperiments to include
   * @return ManhattanKVEndpoint
   */
  def buildManhattanEndpoint(
    cluster: ManhattanCluster,
    appId: String,
    numTries: Int,
    matwitterTimeout: Duration,
    guarantee: Guarantee,
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver,
    matwitterItemsPerRequest: Int = 100,
    etwitterperiments: Seq[Etwitterperiment] = Seq(Etwitterperiments.ApertureLoadBalancer)
  ): ManhattanKVEndpoint = {
    val client = buildManhattanClient(
      cluster,
      appId,
      serviceIdentifier,
      etwitterperiments
    )

    ManhattanKVEndpointBuilder(client)
      .defaultGuarantee(guarantee)
      .defaultMatwitterTimeout(matwitterTimeout)
      .matwitterRetryCount(numTries)
      .matwitterItemsPerRequest(matwitterItemsPerRequest)
      .statsReceiver(statsReceiver)
      .build()
  }

  /**
   *  Build a ManhattanKVClient
   *
   * @param cluster Manhattan cluster
   * @param appId   Manhattan appid
   * @param serviceIdentifier Service ID used to S2S Auth
   * @param etwitterperiments MH client etwitterperiments to include
   *
   * @return ManhattanKVClient
   */
  def buildManhattanClient(
    cluster: ManhattanCluster,
    appId: String,
    serviceIdentifier: ServiceIdentifier,
    etwitterperiments: Seq[Etwitterperiment] = Seq(Etwitterperiments.ApertureLoadBalancer)
  ): ManhattanKVClient = {
    val mtlsParams = serviceIdentifier match {
      case EmptyServiceIdentifier => NoMtlsParams
      case serviceIdentifier =>
        ManhattanKVClientMtlsParams(
          serviceIdentifier = serviceIdentifier,
          opportunisticTls = OpportunisticTls.Required)
    }

    val label = s"manhattan/${cluster.prefitwitter}"

    new ManhattanKVClient(
      appId = appId,
      dest = cluster.wilyName,
      mtlsParams = mtlsParams,
      label = label,
      etwitterperiments = etwitterperiments
    )
  }

  def buildManhattanV1FinagleClient(
    cluster: ManhattanCluster,
    serviceIdentifier: ServiceIdentifier,
    etwitterperiments: Seq[Etwitterperiment] = Seq(Etwitterperiments.ApertureLoadBalancer)
  ): mh.ManhattanCoordinator.MethodPerEndpoint = {
    val mtlsParams = serviceIdentifier match {
      case EmptyServiceIdentifier => NoMtlsParams
      case serviceIdentifier =>
        ManhattanKVClientMtlsParams(
          serviceIdentifier = serviceIdentifier,
          opportunisticTls = OpportunisticTls.Required)
    }

    val label = s"manhattan/${cluster.prefitwitter}"

    Etwitterperiments
      .clientWithEtwitterperiments(etwitterperiments, mtlsParams)
      .build[mh.ManhattanCoordinator.MethodPerEndpoint](cluster.wilyName, label)
  }
}
