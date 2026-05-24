package com.twitter.home_mixer.param

object HomeMixerFlagName {
  final val ScribeClientEventsFlag = "scribe.client_events"
  final val ScribeServedCandidatesFlag = "scribe.served_candidates"
  final val ScribeScoredCandidatesFlag = "scribe.scored_candidates"
  final val ScribeFeaturesFlag = "scribe.features"
  final val DataRecordMetadataStoreConfigsYmlFlag = "data.record.metadata.store.configs.yml"
  final val DarkTrafficFilterDeciderKey = "thrift.dark.traffic.filter.decider_key"
  final val TargetScoringLatency = "target.scoring.latency"
}
