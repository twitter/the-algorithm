package com.tw ter.t etyp e
package conf g

 mport com.tw ter. o.Buf
 mport com.tw ter.f nagle.{Serv ce, S mpleF lter}
 mport com.tw ter.f nagle. mcac d.protocol._

class  mcac Except onLogg ngF lter extends S mpleF lter[Command, Response] {
  // Us ng a custom logger na  so that   can target logg ng rules spec f cally
  // for  mcac  excpet on logg ng.
  val logger: Logger = Logger(getClass)

  def apply(command: Command, serv ce: Serv ce[Command, Response]): Future[Response] = {
    serv ce(command).respond {
      case Return(Error(e)) =>
        log(command, e)
      case Return(ValuesAndErrors(_, errors))  f errors.nonEmpty =>
        errors.foreach {
          case (Buf.Utf8(keyStr), e) =>
            log(command.na , keyStr, e)
        }
      case Throw(e) =>
        log(command, e)

      case _ =>
    }
  }

  pr vate def log(command: Command, e: Throwable): Un  = {
    log(command.na , getKey(command), e)
  }

  pr vate def log(commandNa : Str ng, keyStr: Str ng, e: Throwable): Un  = {
    logger.debug(
      s"CACHE_EXCEPT ON command: ${commandNa } key: ${keyStr} except on: ${e.getClass.getNa }",
      e,
    )
  }

  pr vate def getKey(command: Command): Str ng = command match {
    case Get(keys) => toKeyStr(keys)
    case Gets(keys) => toKeyStr(keys)

    case Set(Buf.Utf8(key), _, _, _) => key
    case Add(Buf.Utf8(key), _, _, _) => key
    case Cas(Buf.Utf8(key), _, _, _, _) => key
    case Delete(Buf.Utf8(key)) => key
    case Replace(Buf.Utf8(key), _, _, _) => key
    case Append(Buf.Utf8(key), _, _, _) => key
    case Prepend(Buf.Utf8(key), _, _, _) => key

    case  ncr(Buf.Utf8(key), _) => key
    case Decr(Buf.Utf8(key), _) => key
    case Stats(keys) => toKeyStr(keys)
    case Qu () => "qu "
    case Upsert(Buf.Utf8(key), _, _, _, _) => key
    case Getv(keys) => toKeyStr(keys)
  }

  pr vate def toKeyStr(keys: Seq[Buf]): Str ng =
    keys.map { case Buf.Utf8(key) => key }.mkStr ng(",")
}
