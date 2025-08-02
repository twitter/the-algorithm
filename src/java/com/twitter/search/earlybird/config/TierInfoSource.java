package com.twitter.search.earlybird.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javatwitter.inject.Inject;

import com.twitter.search.common.util.zookeeper.ZooKeeperProtwittery;

public class TierInfoSource {
  private final ZooKeeperProtwittery zkClient;

  @Inject
  public TierInfoSource(ZooKeeperProtwittery sZooKeeperClient) {
    this.zkClient = sZooKeeperClient;
  }

  public List<TierInfo> getTierInformation() {
    return getTierInfoWithPrefitwitter("tier");
  }

  public String getConfigFileType() {
    return TierConfig.getConfigFileName();
  }

  private List<TierInfo> getTierInfoWithPrefitwitter(String tierPrefitwitter) {
    Set<String> tierNames = TierConfig.getTierNames();
    List<TierInfo> tierInfos = new ArrayList<>();
    for (String name : tierNames) {
      if (name.startsWith(tierPrefitwitter)) {
        TierInfo tierInfo = TierConfig.getTierInfo(name);
        tierInfos.add(tierInfo);
      }
    }
    return tierInfos;
  }

}
