package com.tw ter.un f ed_user_act ons.enr c r

/**
 * W n t  except on  s thrown,    ans that an assumpt on  n t  enr c r serv ces
 * was v olated and   needs to be f xed before a product on deploy nt.
 */
abstract class FatalExcept on(msg: Str ng) extends Except on(msg)

class  mple ntat onExcept on(msg: Str ng) extends FatalExcept on(msg)

object Except ons {
  def requ re(requ re nt: Boolean,  ssage: Str ng): Un  = {
     f (!requ re nt)
      throw new  mple ntat onExcept on("requ re nt fa led: " +  ssage)
  }
}
