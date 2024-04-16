package com.tw ter.t  l neranker.repos ory

 mport com.tw ter.t  l neranker.conf g.Runt  Conf gurat on
 mport com.tw ter.t  l neranker.para ters.Conf gBu lder

object Rout ngT  l neRepos oryBu lder {
  def apply(
    conf g: Runt  Conf gurat on,
    conf gBu lder: Conf gBu lder
  ): Rout ngT  l neRepos ory = {

    val reverseChronT  l neRepos ory =
      new ReverseChronHo T  l neRepos oryBu lder(conf g, conf gBu lder).apply
    val rankedT  l neRepos ory = new RankedHo T  l neRepos ory

    new Rout ngT  l neRepos ory(reverseChronT  l neRepos ory, rankedT  l neRepos ory)
  }
}
