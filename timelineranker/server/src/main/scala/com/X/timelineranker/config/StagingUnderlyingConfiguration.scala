package com.X.timelineranker.config

import com.X.finagle.stats.StatsReceiver

class StagingUnderlyingClientConfiguration(flags: TimelineRankerFlags, statsReceiver: StatsReceiver)
    extends DefaultUnderlyingClientConfiguration(flags, statsReceiver)
