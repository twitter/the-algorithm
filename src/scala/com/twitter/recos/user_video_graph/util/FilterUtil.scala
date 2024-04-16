package com.tw ter.recos.user_v deo_graph.ut l

 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  

object F lterUt l {
  def t etAgeF lter(t et d: T et d, maxAge: Durat on): Boolean = {
    Snowflake d
      .t  From dOpt(t et d)
      .map { t etT   => t etT   > T  .now - maxAge }.getOrElse(false)
    //  f t re's no snowflake t  stamp,   have no  dea w n t  t et happened.
  }
}
