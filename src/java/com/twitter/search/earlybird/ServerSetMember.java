package com.twitter.search.earlybird;

import org.apache.zookeeper.KeeperEtwitterception;

import com.twitter.common.zookeeper.ServerSet;
import com.twitter.common.zookeeper.ZooKeeperClient;

/**
 * Represents a server that can add and remove itself from a server set.
 */
public interface ServerSetMember {
  /**
   * Makes this server join its server set.
   *
   * @throws ServerSet.UpdateEtwitterception
   * @param requestSource
   */
  void joinServerSet(String requestSource) throws ServerSet.UpdateEtwitterception;

  /**
   * Makes this server leave its server set.
   *
   * @throws ServerSet.UpdateEtwitterception
   * @param requestSource
   */
  void leaveServerSet(String requestSource) throws ServerSet.UpdateEtwitterception;

  /**
   * Gets and returns the current number of members in this server's server set.
   *
   * @return number of members currently in this host's server set.
   * @throws InterruptedEtwitterception
   * @throws ZooKeeperClient.ZooKeeperConnectionEtwitterception
   * @throws KeeperEtwitterception
   */
  int getNumberOfServerSetMembers() throws InterruptedEtwitterception,
      ZooKeeperClient.ZooKeeperConnectionEtwitterception, KeeperEtwitterception;

  /**
   * Checks if this earlybird is in the server set.
   *
   * @return true if it is, false otherwise.
   */
  boolean isInServerSet();

  /**
   * Should only be called for Archive Earlybirds.
   *
   * Join ServerSet for ServiceProtwittery with a named admin port and with a zookeeper path that Service
   * Protwittery can translate to a domain name label that is less than 64 characters (due to the size
   * limit for domain name labels described here: https://tools.ietf.org/html/rfc1035)
   * This will allow us to access Earlybirds that are not on mesos via ServiceProtwittery.
   */
  void joinServerSetForServiceProtwittery();
}
