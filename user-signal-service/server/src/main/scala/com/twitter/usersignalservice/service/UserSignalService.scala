package com.tw ter.users gnalserv ce
package serv ce

 mport com.google. nject. nject
 mport com.google. nject.S ngleton
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.storehaus.St chOfReadableStore
 mport com.tw ter.users gnalserv ce.conf g.S gnalFetc rConf g
 mport com.tw ter.users gnalserv ce.handler.UserS gnalHandler
 mport com.tw ter.users gnalserv ce.thr ftscala.BatchS gnalRequest
 mport com.tw ter.users gnalserv ce.thr ftscala.BatchS gnalResponse
 mport com.tw ter.ut l.T  r

@S ngleton
class UserS gnalServ ce @ nject() (
  s gnalFetc rConf g: S gnalFetc rConf g,
  t  r: T  r,
  stats: StatsRece ver) {

  pr vate val userS gnalHandler =
    new UserS gnalHandler(s gnalFetc rConf g, t  r, stats)

  val userS gnalServ ceHandlerStoreSt ch: BatchS gnalRequest => com.tw ter.st ch.St ch[
    BatchS gnalResponse
  ] = St chOfReadableStore(userS gnalHandler.toReadableStore)
}
