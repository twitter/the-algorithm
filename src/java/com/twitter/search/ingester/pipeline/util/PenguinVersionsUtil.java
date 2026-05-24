package com.twitter.search.ingester.pipeline.util;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import com.twitter.common_internal.text.version.PenguinVersion;
import com.twitter.decider.Decider;

public final class PenguinVersionsUtil {

  private PenguinVersionsUtil() { /* prevent instantiation */ }

  /**
   * Utility method for updating penguinVersions lists via decider availability. We must have
   * at least one version available.
   * @param penguinVersions
   * @param decider
   * @return
   */
  public static List<PenguinVersion> filterPenguinVersionsWithDeciders(
      List<PenguinVersion> penguinVersions,
      Decider decider) {
    List<PenguinVersion> updatedPenguinVersions = new ArrayList<>();
    for (PenguinVersion penguinVersion : penguinVersions) {
      if (isPenguinVersionAvailable(penguinVersion, decider)) {
        updatedPenguinVersions.add(penguinVersion);
      }
    }
    Preconditions.checkArgument(penguinVersions.size() > 0,
        "At least one penguin version must be specified.");

    return updatedPenguinVersions;
  }

  /**
   * Checks penguinVersion decider for availability.
   * @param penguinVersion
   * @param decider
   * @return
   */
  public static boolean isPenguinVersionAvailable(PenguinVersion penguinVersion, Decider decider) {
    return decider.isAvailable(
        String.format("enable_penguin_version_%d", penguinVersion.getByteValue()));
  }
}
