package com.twitter.search.earlybird.config;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;

import com.twitter.common.util.Clock;
import com.twitter.search.common.config.Config;
import com.twitter.search.common.config.ConfigFile;
import com.twitter.search.common.config.ConfigurationException;
import com.twitter.search.common.metrics.SearchLongGauge;
import com.twitter.search.common.util.date.DateUtil;

/**
 * This class provides APIs to access the tier configurations for a cluster.
 * Each tier has tier name, number of partitions, tier start time and end time.
 */
public final class TierConfig {
  private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(TierConfig.class);

  private static final String DEFAULT_CONFIG_DIR = "common/config";
  public static final String DEFAULT_TIER_FILE = "earlybird-tiers.yml";

  public static final Date DEFAULT_TIER_START_DATE = DateUtil.toDate(2006, 3, 21);
  // It's convenient for DEFAULT_TIER_END_DATE to be before ~2100, because then the output of
  // FieldTermCounter.getHourValue(DEFAULT_TIER_END_END_DATE) can still fit into an integer.
  public static final Date DEFAULT_TIER_END_DATE = DateUtil.toDate(2099, 1, 1);

  public static final String DEFAULT_TIER_NAME = "all";
  public static final boolean DEFAULT_ENABLED = true;
  public static final TierInfo.RequestReadType DEFAULT_READ_TYPE = TierInfo.RequestReadType.LIGHT;

  private static ConfigFile tierConfigFile = null;
  private static ConfigSource tierConfigSource = null;

  public enum ConfigSource {
    LOCAL,
    ZOOKEEPER
  }

  private TierConfig() { }

  private static synchronized void init() {
    if (tierConfigFile == null) {
      tierConfigFile = new ConfigFile(DEFAULT_CONFIG_DIR, DEFAULT_TIER_FILE);
      tierConfigSource = ConfigSource.LOCAL;
      SearchLongGauge.export("tier_config_source_" + tierConfigSource.name()).set(1);
      LOG.info("Tier config file " + DEFAULT_TIER_FILE + " is successfully loaded from bundle.");
    }
  }

  public static ConfigFile getConfigFile() {
    init();
    return tierConfigFile;
  }

  public static String getConfigFileName() {
    return getConfigFile().getConfigFileName();
  }

  /**
   * Return all the tier names specified in the config file.
   */
  public static Set<String> getTierNames() {
    return Config.getConfig().getMapCopy(getConfigFileName()).keySet();
  }

  /**
   * Sets the value of the given tier config property to the given value.
   */
  public static void setForTests(String property, Object value) {
    Config.getConfig().setForTests(DEFAULT_TIER_FILE, property, value);
  }

  /**
   * Returns the config info for the specified tier.
   */
  public static TierInfo getTierInfo(String tierName) {
    return getTierInfo(tierName, null /* use current environment */);
  }

  /**
   * Returns the config info for the specified tier and environment.
   */
  public static TierInfo getTierInfo(String tierName, @Nullable String environment) {
    String tierConfigFileType = getConfigFileName();
    Map<String, Object> tierInfo;
    try {
      tierInfo = (Map<String, Object>) Config.getConfig()
          .getFromEnvironment(environment, tierConfigFileType, tierName);
    } catch (ConfigurationException e) {
      throw new RuntimeException(e);
    }
    if (tierInfo == null) {
      LOG.error("Cannot find tier config for "
          + tierName + "in config file: " + tierConfigFileType);
      throw new RuntimeException("Configuration error: " + tierConfigFileType);
    }

    Long partitions = (Long) tierInfo.get("number_of_partitions");
    if (partitions == null) {
      LOG.error("No number of partition is specified for tier "
          + tierName + " in tier config file " + tierConfigFileType);
      throw new RuntimeException("Configuration error: " + tierConfigFileType);
    }

    Long numTimeslices = (Long) tierInfo.get("serving_timeslices");
    if (numTimeslices == null) {
      LOG.info("No max timeslices is specified for tier "
          + tierName + " in tier config file " + tierConfigFileType
          + ", not setting a cap on number of serving timeslices");
      // NOTE: we use max int32 here because it will ultimately be cast to an int, but the config
      // map expects Longs for all integral types.  Using Long.MAX_VALUE leads to max serving
      // timeslices being set to -1 when it is truncated to an int.
      numTimeslices = (long) Integer.MAX_VALUE;
    }

    Date tierStartDate = (Date) tierInfo.get("data_range_start_date_inclusive");
    if (tierStartDate == null) {
      tierStartDate = DEFAULT_TIER_START_DATE;
    }
    Date tierEndDate = (Date) tierInfo.get("data_range_end_date_exclusive");
    if (tierEndDate == null) {
      tierEndDate = DEFAULT_TIER_END_DATE;
    }

    Boolean tierEnabled = (Boolean) tierInfo.get("tier_enabled");
    if (tierEnabled == null) {
      tierEnabled = DEFAULT_ENABLED;
    }

    TierInfo.RequestReadType readType =
      getRequestReadType((String) tierInfo.get("tier_read_type"), DEFAULT_READ_TYPE);
    TierInfo.RequestReadType readTypeOverride =
      getRequestReadType((String) tierInfo.get("tier_read_type_override"), readType);

    return new TierInfo(
        tierName,
        tierStartDate,
        tierEndDate,
        partitions.intValue(),
        numTimeslices.intValue(),
        tierEnabled,
        (String) tierInfo.get("serving_range_since_id_exclusive"),
        (String) tierInfo.get("serving_range_max_id_inclusive"),
        (Date) tierInfo.get("serving_range_start_date_inclusive_override"),
        (Date) tierInfo.get("serving_range_end_date_exclusive_override"),
        readType,
        readTypeOverride,
        Clock.SYSTEM_CLOCK);
  }

  public static synchronized void clear() {
    tierConfigFile = null;
    tierConfigSource = null;
  }

  protected static synchronized ConfigSource getTierConfigSource() {
    return tierConfigSource;
  }

  private static TierInfo.RequestReadType getRequestReadType(
      String readTypeEnumName, TierInfo.RequestReadType defaultReadType) {
    TierInfo.RequestReadType readType = defaultReadType;
    if (readTypeEnumName != null) {
      readType = TierInfo.RequestReadType.valueOf(readTypeEnumName.trim().toUpperCase());
      Preconditions.checkState(readType != null);
    }
    return readType;
  }
}
