package com.twittew.tweetypie.utiw

impowt com.twittew.eschewbiwd.thwiftscawa.tweetentityannotation
i-impowt com.twittew.tweetypie.thwiftscawa.eschewbiwdentityannotations
i-impowt com.twittew.tweetypie.thwiftscawa.tweet

o-object communityannotation {

  v-vaw gwoupid: w-wong = 8
  v-vaw domainid: wong = 31

  d-def appwy(communityid: w-wong): tweetentityannotation =
    tweetentityannotation(gwoupid, ( ͡o ω ͡o ) domainid, entityid = communityid)

  def unappwy(annotation: t-tweetentityannotation): option[wong] =
    annotation m-match {
      case tweetentityannotation(`gwoupid`, rawr x3 `domainid`, nyaa~~ e-entityid) => some(entityid)
      case _ => nyone
    }

  // w-wetuwns nyone instead of some(seq()) w-when thewe a-awe nyon-community annotations pwesent
  def additionawfiewdstocommunityids(additionawfiewds: tweet): option[seq[wong]] = {
    a-additionawfiewds.eschewbiwdentityannotations
      .map {
        case eschewbiwdentityannotations(entityannotations) =>
          entityannotations.fwatmap(communityannotation.unappwy)
      }.fiwtew(_.nonempty)
  }
}
