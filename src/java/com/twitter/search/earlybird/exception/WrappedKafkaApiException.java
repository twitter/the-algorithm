package com.twittew.seawch.eawwybiwd.exception;

impowt owg.apache.kafka.common.ewwows.apiexception;

/**
 * k-kafka's a-apiexception c-cwass doesn't wetain i-its stack t-twace (see its souwce c-code). -.-
 * a-as a wesuwt a kafka e-exception that pwopagates up the caww chain can't point to whewe exactwy
 * d-did the exception happen in ouw code. ( ͡o ω ͡o ) as a sowution, rawr x3 u-use this cwass when cawwing k-kafka api
 * methods. nyaa~~
 */
pubwic cwass wwappedkafkaapiexception extends wuntimeexception {
  p-pubwic wwappedkafkaapiexception(apiexception c-cause) {
    s-supew(cause);
  }

  pubwic wwappedkafkaapiexception(stwing message, /(^•ω•^) apiexception cause) {
    s-supew(message, rawr cause);
  }
}
