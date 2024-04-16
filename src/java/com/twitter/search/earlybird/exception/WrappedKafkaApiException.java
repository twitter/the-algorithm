package com.tw ter.search.earlyb rd.except on;

 mport org.apac .kafka.common.errors.Ap Except on;

/**
 * Kafka's Ap Except on class doesn't reta n  s stack trace (see  s s ce code).
 * As a result a kafka except on that propagates up t  call cha n can't po nt to w re exactly
 * d d t  except on happen  n   code. As a solut on, use t  class w n call ng kafka AP 
 *  thods.
 */
publ c class WrappedKafkaAp Except on extends Runt  Except on {
  publ c WrappedKafkaAp Except on(Ap Except on cause) {
    super(cause);
  }

  publ c WrappedKafkaAp Except on(Str ng  ssage, Ap Except on cause) {
    super( ssage, cause);
  }
}
