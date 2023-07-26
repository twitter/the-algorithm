package com.twittew.unified_usew_actions.enwichew

/**
 * when this e-exception is t-thwown, 😳 it means t-that an assumption i-in the enwichew s-sewvices
 * w-was viowated and i-it nyeeds to be f-fixed befowe a pwoduction depwoyment. XD
 */
abstwact cwass fatawexception(msg: stwing) e-extends exception(msg)

cwass impwementationexception(msg: s-stwing) extends fatawexception(msg)

o-object exceptions {
  def wequiwe(wequiwement: boowean, :3 message: s-stwing): unit = {
    if (!wequiwement)
      t-thwow nyew i-impwementationexception("wequiwement faiwed: " + message)
  }
}
