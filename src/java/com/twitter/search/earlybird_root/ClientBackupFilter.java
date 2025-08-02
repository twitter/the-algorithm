package com.twitter.search.earlybird_root;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.finagle.client.BackupRequestFilter;
import com.twitter.finagle.service.ResponseClassifier;
import com.twitter.finagle.service.RetryBudgets;
import com.twitter.finagle.stats.StatsReceiver;
import com.twitter.finagle.util.DefaultTimer;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.common.metrics.SearchCustomGauge;
import com.twitter.search.earlybird.common.ClientIdUtil;
import com.twitter.search.earlybird.thrift.EarlybirdRequest;
import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.util.Future;
import com.twitter.util.tunable.Tunable;

public class ClientBackupFilter etwittertends SimpleFilter<EarlybirdRequest, EarlybirdResponse> {
  private static final Logger LOG = LoggerFactory.getLogger(ClientBackupFilter.class);

  private final Map<String, BackupRequestFilter<EarlybirdRequest, EarlybirdResponse>>
      clientBackupFilters = new ConcurrentHashMap<>();
  private final boolean sendInterupts = false;
  private final String statPrefitwitter;
  private final Tunable.Mutable<Object> matwitterEtwittertraLoad;
  private final StatsReceiver statsReceiver;
  private final SearchDecider decider;
  private final String backupRequestPrecentEtwittertraLoadDecider;
  private final int minSendBackupAfterMs = 1;

  public ClientBackupFilter(String serviceName,
                            String statPrefitwitter,
                            StatsReceiver statsReceiver,
                            SearchDecider decider) {
    this.statPrefitwitter = statPrefitwitter;
    this.backupRequestPrecentEtwittertraLoadDecider = serviceName + "_backup_request_percent_etwittertra_load";
    this.decider = decider;
    this.matwitterEtwittertraLoad = Tunable.mutable("backup_tunable", getMatwitterEtwittertraLoadFromDecider());
    this.statsReceiver = statsReceiver;
    SearchCustomGauge.etwitterport(serviceName + "_backup_request_factor",
        () -> (matwitterEtwittertraLoad.apply().isDefined()) ? (double) matwitterEtwittertraLoad.apply().get() : -1);
  }

  private double getMatwitterEtwittertraLoadFromDecider() {
    return ((double) decider.getAvailability(backupRequestPrecentEtwittertraLoadDecider)) / 100 / 100;
  }

  private BackupRequestFilter<EarlybirdRequest, EarlybirdResponse> backupFilter(String client) {
    return new BackupRequestFilter<EarlybirdRequest, EarlybirdResponse>(
        matwitterEtwittertraLoad,
        sendInterupts,
        minSendBackupAfterMs,
        ResponseClassifier.Default(),
        RetryBudgets.newRetryBudget(),
        statsReceiver.scope(statPrefitwitter, client, "backup_filter"),
        DefaultTimer.getInstance(),
        client);
  }

  private void updateMatwitterEtwittertraLoadIfNecessary() {
    double matwitterEtwittertraLoadDeciderValue = getMatwitterEtwittertraLoadFromDecider();
    if (matwitterEtwittertraLoad.apply().isDefined()
        && !matwitterEtwittertraLoad.apply().get().equals(matwitterEtwittertraLoadDeciderValue)) {
      LOG.info("Updating matwitterEtwittertraLoad from {} to {}",
          matwitterEtwittertraLoad.apply().get(),
          matwitterEtwittertraLoadDeciderValue);
      matwitterEtwittertraLoad.set(matwitterEtwittertraLoadDeciderValue);
    }
  }

  @Override
  public Future<EarlybirdResponse> apply(EarlybirdRequest request,
                                         Service<EarlybirdRequest, EarlybirdResponse> service) {
    updateMatwitterEtwittertraLoadIfNecessary();

    String clientID = ClientIdUtil.getClientIdFromRequest(request);
    BackupRequestFilter<EarlybirdRequest, EarlybirdResponse> filter =
        clientBackupFilters.computeIfAbsent(clientID, this::backupFilter);

    return filter
        .andThen(service)
        .apply(request);
  }
}
