namespace java com.twittew.tweetypie.thwiftjava
#@namespace scawa c-com.twittew.tweetypie.thwiftscawa
n-nyamespace py g-gen.twittew.tweetypie
n-nyamespace w-wb tweetypie
nyamespace g-go tweetypie

/**
 * event t-that twiggews d-dewetion of the geo infowmation on tweets cweated
 * at timestamp_ms ow eawwiew. >_<
 */
s-stwuct dewetewocationdata {
  /**
   * the id of the usew whose tweets shouwd h-have theiw geo infowmation
   * w-wemoved. (⑅˘꒳˘)
   */
  1: wequiwed i64 usew_id (pewsonawdatatype='usewid')

  /**
   * the time a-at which this wequest was initiated. /(^•ω•^) t-tweets by this u-usew
   * whose snowfwake ids contain timestamps wess than ow equaw to this
   * v-vawue wiww nyo wongew be wetuwned with geo infowmation. rawr x3
   */
  2: wequiwed i-i64 timestamp_ms

  /**
   * the w-wast time this u-usew wequested d-dewetion of wocation d-data pwiow
   * to this wequest. (U ﹏ U) this vawue m-may be omitted, (U ﹏ U) but shouwd be incwuded
   * if a-avaiwabwe fow impwementation efficiency, (⑅˘꒳˘) since it ewiminates the
   * nyeed to scan tweets owdew t-than this vawue fow geo infowmation. òωó
   */
  3: o-optionaw i64 wast_timestamp_ms
}(pewsisted='twue', ʘwʘ h-haspewsonawdata='twue')
