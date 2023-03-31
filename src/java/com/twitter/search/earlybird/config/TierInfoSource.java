package com.twitter.search.earlybird.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import com.twitter.search.common.util.zookeeper.ZooKeeperProxy;

public class TierInfoSource {
  private final ZooKeeperProxy zkClient;

  @Inject
  public TierInfoSource(ZooKeeperProxy sZooKeeperClient) {
    this.zkClient = sZooKeeperClient;
  }

  public List<TierInfo> getTierInformation() {
    return getTierInfoWithPrefix("tier");
  }

  public String getConfigFileType() {
    return TierConfig.getConfigFileName();
  }

  private List<TierInfo> getTierInfoWithPrefix(String tierPrefix) {
    Set<String> tierNames = TierConfig.getTierNames();
    List<TierInfo> tierInfos = new ArrayList<>();
    for (String name : tierNames) {
      if (name.startsWith(tierPrefix)) {
        TierInfo tierInfo = TierConfig.getTierInfo(name);
        tierInfos.add(tierInfo);
      }
    }
    return tierInfos;
  }

}
