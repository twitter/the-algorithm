/** Copyr ght 2010 Tw ter,  nc. */
package com.tw ter.t etyp e
package tflock

 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.ut l.Future

tra  T et ndexer {

  /**
   * Called at t et-creat on t  , t   thod should set up all relevant  nd ces on t  t et.
   */
  def create ndex(t et: T et): Future[Un ] = Future.Un 

  /**
   * Called at t et-undelete t   (wh ch  sn't yet handled), t   thod should
   * restore all relevant  nd ces on t  t et.
   */
  def undelete ndex(t et: T et): Future[Un ] = Future.Un 

  /**
   * Called at t et-delete t  , t   thod should arch ve all relevant  nd ces on t  t et.
   */
  def delete ndex(t et: T et,  sBounceDelete: Boolean): Future[Un ] = Future.Un 

  /**
   * T   thod should arch ve or unarch ve t  ret et edge  n TFlock Ret etsGraph.
   */
  def setRet etV s b l y(ret et d: T et d, v s ble: Boolean): Future[Un ] = Future.Un 
}
