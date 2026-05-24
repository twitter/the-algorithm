package com.twitter.timelineranker.config

import com.twitter.finagle.stats.StatsReceiver

class StagingUnderlyingClientConfiguration(flags: TimelineRankerFlags, statsReceiver: StatsReceiver)
    extends DefaultUnderlyingClientConfiguration(flags, statsReceiver)
