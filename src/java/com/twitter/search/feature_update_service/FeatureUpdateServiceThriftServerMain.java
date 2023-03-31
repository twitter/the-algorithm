package com.twitter.search.feature_update_service;

final class FeatureUpdateServiceThriftServerMain {
  private FeatureUpdateServiceThriftServerMain() {
    // Private constructor to satisfy checkstyle error:
    // "Utility classes should not have a public or default constructor)."
  }

  public static void main(String[] args) {
    new FeatureUpdateServiceThriftServer(args).main(args);
  }
}
