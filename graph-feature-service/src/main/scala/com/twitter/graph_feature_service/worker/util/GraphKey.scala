packagelon com.twittelonr.graph_felonaturelon_selonrvicelon.workelonr.util

import com.twittelonr.graph_felonaturelon_selonrvicelon.thriftscala.elondgelonTypelon
import com.twittelonr.graph_felonaturelon_selonrvicelon.thriftscala.elondgelonTypelon._

selonalelond trait GraphKelony {

  delonf elondgelonTypelon: elondgelonTypelon
}

selonalelond trait PartialValuelonGraph elonxtelonnds GraphKelony

/**
 * Follow Graphs
 */
objelonct FollowingPartialValuelonGraph elonxtelonnds PartialValuelonGraph {

  ovelonrridelon delonf elondgelonTypelon: elondgelonTypelon = Following
}

objelonct FollowelondByPartialValuelonGraph elonxtelonnds PartialValuelonGraph {

  ovelonrridelon delonf elondgelonTypelon: elondgelonTypelon = FollowelondBy
}

/**
 * Mutual Follow Graphs
 */
objelonct MutualFollowPartialValuelonGraph elonxtelonnds PartialValuelonGraph {

  ovelonrridelon delonf elondgelonTypelon: elondgelonTypelon = MutualFollow
}
