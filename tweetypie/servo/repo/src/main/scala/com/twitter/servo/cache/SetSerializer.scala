package com.tw ter.servo.cac 

/**
 * A Ser al zer of `Set[T]`s.
 *
 * @param  emSer al zer a Ser al zer for t   nd v dual ele nts.
 * @param  emS zeEst mate est mated s ze  n bytes of  nd v dual ele nts
 */
class SetSer al zer[T]( emSer al zer: Ser al zer[T],  emS zeEst mate:  nt = 8)
    extends  erableSer al zer[T, Set[T]](() => Set.newBu lder[T],  emSer al zer,  emS zeEst mate)
