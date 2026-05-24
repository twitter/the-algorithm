package com.twitter.search.earlybird;

import org.apache.zookeeper.KeeperException;

import com.twitter.common.zookeeper.ServerSet;
import com.twitter.common.zookeeper.ZooKeeperClient;

/**
 * Represents a server that can add and remove itself from a server set.
 */
public interface ServerSetMember {
  /**
   * Makes this server join its server set.
   *
   * @throws ServerSet.UpdateException
   * @param requestSource
   */
  void joinServerSet(String requestSource) throws ServerSet.UpdateException;

  /**
   * Makes this server leave its server set.
   *
   * @throws ServerSet.UpdateException
   * @param requestSource
   */
  void leaveServerSet(String requestSource) throws ServerSet.UpdateException;

  /**
   * Gets and returns the current number of members in this server's server set.
   *
   * @return number of members currently in this host's server set.
   * @throws InterruptedException
   * @throws ZooKeeperClient.ZooKeeperConnectionException
   * @throws KeeperException
   */
  int getNumberOfServerSetMembers() throws InterruptedException,
      ZooKeeperClient.ZooKeeperConnectionException, KeeperException;

  /**
   * Checks if this earlybird is in the server set.
   *
   * @return true if it is, false otherwise.
   */
  boolean isInServerSet();

  /**
   * Should only be called for Archive Earlybirds.
   *
   * Join ServerSet for ServiceProxy with a named admin port and with a zookeeper path that Service
   * Proxy can translate to a domain name label that is less than 64 characters (due to the size
   * limit for domain name labels described here: https://tools.ietf.org/html/rfc1035)
   * This will allow us to access Earlybirds that are not on mesos via ServiceProxy.
   */
  void joinServerSetForServiceProxy();
}
