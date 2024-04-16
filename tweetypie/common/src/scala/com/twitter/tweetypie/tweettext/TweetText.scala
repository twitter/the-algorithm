package com.tw ter.t etyp e.t ettext

 mport java.text.Normal zer

object T etText {

  /** T  or g nal max mum t et length, tak ng  nto account normal zat on */
  pr vate[t etyp e] val Or g nalMaxD splayLength = 140

  /** Max mum number of v s ble code po nts allo d  n a t et w n t et length  s counted by code
   * po nts, tak ng  nto account normal zat on. See also [[MaxV s ble  ghtedEmoj Length]].
   */
  pr vate[t etyp e] val MaxV s ble  ghtedLength = 280

  /** Max mum number of v s ble code po nts allo d  n a t et w n t et length  s counted by
   * emoj , tak ng  nto account normal zat on. See also [[MaxV s ble  ghtedLength]].
   * 140  s t  max number of Emoj s, v s ble, fully-  ghted per Tw ter's cramm ng rules
   * 10  s t  max number of Code Po nts per Emoj 
   */
  pr vate[t etyp e] val MaxV s ble  ghtedEmoj Length = 140 * 10

  /** Max mum number of bytes w n truncat ng t et text for a ret et.  Or g nally was t 
   * max UTF-8 length w n t ets  re at most 140 characters.
   * See also [[Or g nalMaxD splayLength]].
   */
  pr vate[t etyp e] val Or g nalMaxUtf8Length = 600

  /** Max mum number of bytes for t et text us ng utf-8 encod ng.
   */
  pr vate[t etyp e] val MaxUtf8Length = 5708

  /** Max mum number of  nt ons allo d  n t et text.  T   s enforced at t et creat on t   */
  pr vate[t etyp e] val Max nt ons = 50

  /** Max mum number of urls allo d  n t et text.  T   s enforced at t et creat on t   */
  pr vate[t etyp e] val MaxUrls = 10

  /** Max mum number of hashtags allo d  n t et text.  T   s enforced at t et creat on t   */
  pr vate[t etyp e] val MaxHashtags = 50

  /** Max mum number of cashtags allo d  n t et text.  T   s enforced at t et creat on t   */
  pr vate[t etyp e] val MaxCashtags = 50

  /** Max mum length of a hashtag (not  nclud ng t  '#') */
  pr vate[t etyp e] val MaxHashtagLength = 100

  /**
   * Normal zes t  text accord ng to t  un code NFC spec.
   */
  def nfcNormal ze(text: Str ng): Str ng = Normal zer.normal ze(text, Normal zer.Form.NFC)

  /**
   * Return t  number of "characters"  n t  text. See
   * [[Offset.D splayUn ]].
   */
  def d splayLength(text: Str ng):  nt = Offset.D splayUn .length(text).to nt

  /**
   * Return t  number of Un code code po nts  n t  Str ng.
   */
  def codePo ntLength(text: Str ng):  nt = Offset.CodePo nt.length(text).to nt
}
