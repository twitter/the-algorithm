package com.tw ter.t etyp e.storage

object Response {
  case class T etResponse(
    t et d: Long,
    overallResponse: T etResponseCode,
    add  onalF eldResponses: Opt on[Map[Short, F eldResponse]] = None)

  sealed tra  T etResponseCode

  object T etResponseCode {
    object Success extends T etResponseCode
    object Part al extends T etResponseCode
    object Fa lure extends T etResponseCode
    object OverCapac y extends T etResponseCode
    object Deleted extends T etResponseCode
  }

  case class F eldResponse(code: F eldResponseCode,  ssage: Opt on[Str ng] = None)

  sealed tra  F eldResponseCode

  object F eldResponseCode {
    object Success extends F eldResponseCode
    object  nval dRequest extends F eldResponseCode
    object ValueNotFound extends F eldResponseCode
    object T  out extends F eldResponseCode
    object Error extends F eldResponseCode
  }
}
