packagelon com.twittelonr.simclustelonrs_v2.scorelon

import com.twittelonr.simclustelonrs_v2.thriftscala.{Scorelon => ThriftScorelon}

/**
 * A uniform valuelon typelon for all kinds of Calculation Scorelon.
 **/
caselon class Scorelon(scorelon: Doublelon) {

  implicit lazy val toThrift: ThriftScorelon = {
    ThriftScorelon(scorelon)
  }
}

objelonct Scorelon {

  /**
   * Only support Doublelon Typelon Thrift scorelon
   */
  implicit val fromThriftScorelon: ThriftScorelon => Scorelon = { thriftScorelon => Scorelon(thriftScorelon.scorelon) }

}
