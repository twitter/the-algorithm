package com.twitter.search.earlybird;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicLong;

import javatwitter.annotation.concurrent.GuardedBy;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.apache.zookeeper.KeeperEtwitterception;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.zookeeper.ServerSet;
import com.twitter.common.zookeeper.ZooKeeperClient;
import com.twitter.common_internal.zookeeper.TwitterServerSet;
import com.twitter.search.common.config.Config;
import com.twitter.search.common.database.DatabaseConfig;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchLongGauge;
import com.twitter.search.common.metrics.SearchStatsReceiver;
import com.twitter.search.common.util.zookeeper.ZooKeeperProtwittery;
import com.twitter.search.earlybird.common.config.EarlybirdConfig;
import com.twitter.search.earlybird.common.config.EarlybirdProperty;
import com.twitter.search.earlybird.config.TierConfig;
import com.twitter.search.earlybird.etwitterception.AlreadyInServerSetUpdateEtwitterception;
import com.twitter.search.earlybird.etwitterception.NotInServerSetUpdateEtwitterception;
import com.twitter.search.earlybird.partition.PartitionConfig;

public class EarlybirdServerSetManager implements ServerSetMember {
  private static final Logger LOG = LoggerFactory.getLogger(EarlybirdServerSetManager.class);

  // How many times this earlybird joined/left its partition's server set
  @VisibleForTesting
  protected final SearchCounter leaveServerSetCounter;
  @VisibleForTesting
  protected final SearchCounter joinServerSetCounter;
  private final ZooKeeperProtwittery discoveryZKClient;
  private final SearchLongGauge inServerSetGauge;
  private final PartitionConfig partitionConfig;
  private final int port;
  private final String serverSetNamePrefitwitter;

  @VisibleForTesting
  protected final SearchLongGauge connectedToZooKeeper;

  private final Object endpointStatusLock = new Object();
  @GuardedBy("endpointStatusLock")
  private ServerSet.EndpointStatus endpointStatus = null;

  private boolean inServerSetForServiceProtwittery = false;

  public EarlybirdServerSetManager(
      SearchStatsReceiver searchStatsReceiver,
      ZooKeeperProtwittery discoveryZKClient,
      final PartitionConfig partitionConfig,
      int port,
      String serverSetNamePrefitwitter) {
    this.discoveryZKClient = discoveryZKClient;
    this.partitionConfig = partitionConfig;
    this.port = port;
    this.serverSetNamePrefitwitter = serverSetNamePrefitwitter;

    // Etwitterport serverset related stats
    Preconditions.checkNotNull(searchStatsReceiver);
    this.joinServerSetCounter = searchStatsReceiver.getCounter(
        serverSetNamePrefitwitter + "join_server_set_count");
    this.leaveServerSetCounter = searchStatsReceiver.getCounter(
        serverSetNamePrefitwitter + "leave_server_set_count");

    // Create a new stat based on the partition number for hosts-in-partition aggregation.
    // The value of the stat is dependent on whether the server is in the serverset so that the
    // aggregate stat reflects the number serving traffic instead of the live process count.
    AtomicLong sharedInServerSetStatus = new AtomicLong();
    this.inServerSetGauge = searchStatsReceiver.getLongGauge(
        serverSetNamePrefitwitter + "is_in_server_set", sharedInServerSetStatus);
    this.connectedToZooKeeper = searchStatsReceiver.getLongGauge(
        serverSetNamePrefitwitter + "connected_to_zookeeper");

    searchStatsReceiver.getLongGauge(
        serverSetNamePrefitwitter + "member_of_partition_" + partitionConfig.getIndetwitteringHashPartitionID(),
        sharedInServerSetStatus);

    this.discoveryZKClient.registerEtwitterpirationHandler(() -> connectedToZooKeeper.set(0));

    this.discoveryZKClient.register(event -> {
      if (event.getType() == Watcher.Event.EventType.None
          && event.getState() == Watcher.Event.KeeperState.SyncConnected) {
        connectedToZooKeeper.set(1);
      }
    });
  }

  /**
   * Join ServerSet and update endpointStatus.
   * This will allow Earlybird consumers, e.g. Blender, to detect when an
   * Earlybird goes online and offline.
   * @param username
   */
  @Override
  public void joinServerSet(String username) throws ServerSet.UpdateEtwitterception {
    joinServerSetCounter.increment();

    synchronized (endpointStatusLock) {
      LOG.info("Joining {} ServerSet (instructed by: {}) ...", serverSetNamePrefitwitter, username);
      if (endpointStatus != null) {
        LOG.warn("Already in ServerSet. Nothing done.");
        throw new AlreadyInServerSetUpdateEtwitterception("Already in ServerSet. Nothing done.");
      }

      try {
        TwitterServerSet.Service service = getServerSetService();

        ServerSet serverSet = discoveryZKClient.createServerSet(service);
        endpointStatus = serverSet.join(
            new InetSocketAddress(InetAddress.getLocalHost().getHostName(), port),
            Maps.newHashMap(),
            partitionConfig.getHostPositionWithinHashPartition());

        inServerSetGauge.set(1);

        String path = service.getPath();
        EarlybirdStatus.recordEarlybirdEvent("Joined " + serverSetNamePrefitwitter + " ServerSet " + path
                                             + " (instructed by: " + username + ")");
        LOG.info("Successfully joined {} ServerSet {} (instructed by: {})",
                 serverSetNamePrefitwitter, path, username);
      } catch (Etwitterception e) {
        endpointStatus = null;
        String message = "Failed to join " + serverSetNamePrefitwitter + " ServerSet of partition "
            + partitionConfig.getIndetwitteringHashPartitionID();
        LOG.error(message, e);
        throw new ServerSet.UpdateEtwitterception(message, e);
      }
    }
  }

  /**
   * Takes this Earlybird out of its registered ServerSet.
   *
   * @throws ServerSet.UpdateEtwitterception if there was a problem leaving the ServerSet,
   * or if this Earlybird is already not in a ServerSet.
   * @param username
   */
  @Override
  public void leaveServerSet(String username) throws ServerSet.UpdateEtwitterception {
    leaveServerSetCounter.increment();
    synchronized (endpointStatusLock) {
      LOG.info("Leaving {} ServerSet (instructed by: {}) ...", serverSetNamePrefitwitter, username);
      if (endpointStatus == null) {
        String message = "Not in a ServerSet. Nothing done.";
        LOG.warn(message);
        throw new NotInServerSetUpdateEtwitterception(message);
      }

      endpointStatus.leave();
      endpointStatus = null;
      inServerSetGauge.set(0);
      EarlybirdStatus.recordEarlybirdEvent("Left " + serverSetNamePrefitwitter
                                           + " ServerSet (instructed by: " + username + ")");
      LOG.info("Successfully left {} ServerSet. (instructed by: {})",
               serverSetNamePrefitwitter, username);
    }
  }

  @Override
  public int getNumberOfServerSetMembers()
      throws InterruptedEtwitterception, ZooKeeperClient.ZooKeeperConnectionEtwitterception, KeeperEtwitterception {
    String path = getServerSetService().getPath();
    return discoveryZKClient.getNumberOfServerSetMembers(path);
  }

  /**
   * Determines if this earlybird is in the server set.
   */
  @Override
  public boolean isInServerSet() {
    synchronized (endpointStatusLock) {
      return endpointStatus != null;
    }
  }

  /**
   * Returns the server set that this earlybird should join.
   */
  public String getServerSetIdentifier() {
    TwitterServerSet.Service service = getServerSetService();
    return String.format("/cluster/local/%s/%s/%s",
                         service.getRole(),
                         service.getEnv(),
                         service.getName());
  }

  private TwitterServerSet.Service getServerSetService() {
    // If the tier name is 'all' then it treat it as an untiered EB cluster
    // and do not add the tier component into the ZK path it registers under.
    String tierZKPathComponent = "";
    if (!TierConfig.DEFAULT_TIER_NAME.equalsIgnoreCase(partitionConfig.getTierName())) {
      tierZKPathComponent = "/" + partitionConfig.getTierName();
    }
    if (EarlybirdConfig.isAurora()) {
      // ROLE, EARYLBIRD_NAME, and ENV properties are required on Aurora, thus will be set here
      return new TwitterServerSet.Service(
          EarlybirdProperty.ROLE.get(),
          EarlybirdProperty.ENV.get(),
          getServerSetPath(EarlybirdProperty.EARLYBIRD_NAME.get() + tierZKPathComponent));
    } else {
      return new TwitterServerSet.Service(
          DatabaseConfig.getZooKeeperRole(),
          Config.getEnvironment(),
          getServerSetPath("earlybird" + tierZKPathComponent));
    }
  }

  private String getServerSetPath(String earlybirdName) {
    return String.format("%s%s/hash_partition_%d", serverSetNamePrefitwitter, earlybirdName,
        partitionConfig.getIndetwitteringHashPartitionID());
  }

  /**
   * Join ServerSet for ServiceProtwittery with a named admin port and with a zookeeper path that Service
   * Protwittery can translate to a domain name label that is less than 64 characters (due to the size
   * limit for domain name labels described here: https://tools.ietf.org/html/rfc1035)
   * This will allow us to access Earlybirds that are not on mesos via ServiceProtwittery.
   */
  @Override
  public void joinServerSetForServiceProtwittery() {
    // This additional Zookeeper server set is only necessary for Archive Earlybirds which are
    // running on bare metal hardware, so ensure that this method is never called for services
    // on Aurora.
    Preconditions.checkArgument(!EarlybirdConfig.isAurora(),
        "Attempting to join server set for ServiceProtwittery on Earlybird running on Aurora");

    LOG.info("Attempting to join ServerSet for ServiceProtwittery");
    try {
      TwitterServerSet.Service service = getServerSetForServiceProtwitteryOnArchive();

      ServerSet serverSet = discoveryZKClient.createServerSet(service);
      String hostName = InetAddress.getLocalHost().getHostName();
      int adminPort = EarlybirdConfig.getAdminPort();
      serverSet.join(
          new InetSocketAddress(hostName, port),
          ImmutableMap.of("admin", new InetSocketAddress(hostName, adminPort)),
          partitionConfig.getHostPositionWithinHashPartition());

      String path = service.getPath();
      LOG.info("Successfully joined ServerSet for ServiceProtwittery {}", path);
      inServerSetForServiceProtwittery = true;
    } catch (Etwitterception e) {
      String message = "Failed to join ServerSet for ServiceProtwittery of partition "
          + partitionConfig.getIndetwitteringHashPartitionID();
      LOG.warn(message, e);
    }
  }

  @VisibleForTesting
  protected TwitterServerSet.Service getServerSetForServiceProtwitteryOnArchive() {
    String serverSetPath = String.format("protwittery/%s/p_%d",
        partitionConfig.getTierName(),
        partitionConfig.getIndetwitteringHashPartitionID());
    return new TwitterServerSet.Service(
        DatabaseConfig.getZooKeeperRole(),
        Config.getEnvironment(),
        serverSetPath);
  }

  @VisibleForTesting
  protected boolean isInServerSetForServiceProtwittery() {
    return inServerSetForServiceProtwittery;
  }
}
