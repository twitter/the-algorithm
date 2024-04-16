na space java com.tw ter.t etyp e.geo.thr ftjava
#@na space scala com.tw ter.t etyp e.geo.thr ftscala
#@na space strato com.tw ter.t etyp e.geo
na space py gen.tw ter.t etyp e.geo
na space rb T etyP e

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                                                      //
//   T  f le conta ns type def n  ons to support t  Geo f eld added to T et flex ble sc ma ONLY.                  //
//      s unl kely to be re-usable so treat   t m as pr vate outs de t  subpackage def ned  re.                    //
//                                                                                                                      //
//    n respect to back storage, cons der   has l m ed capac y, prov s oned to address part cular use cases.         //
//   T re  s no free res ces outs de  s current usage plus a future project on (see Storage Capac y below).        //
//   For example:                                                                                                       //
//    1- Add ng extra f elds to T etLocat on nfo w ll l kely requ re extra storage.                                    //
//    2-  ncrease on front-load QPS (read or wr e) may requ re extra shard ng to not  mpact delay percent les.         //
//   Fa lure to observe t se may  mpact T etyp e wr e-path and read-path.                                            //
//                                                                                                                      //
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Flags how a _Place_  s publ s d  nto a t et (a.k.a. geotagg ng).
 */
enum GeoTagPlaceS ce {
  /**
   * T et  s tagged to a place but    s  mposs ble to determ ne  s s ce.
   * E.g.: created from non-TOO cl ents or legacy TOO cl ents
   */
  UNKNOWN     = 0
  /**
   * T et  s tagged to a Place by reverse geocod ng  s coord nates.
   */
  COORD NATES = 1
  /**
   * T et  s tagged to a Place by t  cl ent appl cat on on user's behalf.
   * N.B.: COORD NATES  s not AUTO because t  AP  request doesn't publ sh a Place
   */
  AUTO        = 2
  EXPL C T    = 3

  // free to use, added for backwards compat b l y on cl ent code.
  RESERVED_4  = 4
  RESERVED_5  = 5
  RESERVED_6  = 6
  RESERVED_7  = 7
}

/**
 *  nformat on about T et's Locat on(s).
 * Des gned to enable custom consumpt on exper ences of t  T et's locat on(s).
 * E.g.: T et's perspect val v ew of a Locat on ent y
 *
 * To guarantee user's r ghts of pr vacy:
 *
 * - Only  nclude user's publ s d locat on data or unpubl s d locat on data that
 *    s EXPL C TLY set as publ cly ava lable by t  user.
 *
 * - Never  nclude user's unpubl s d (aka shared) locat on data that
 *    s NOT EXPL C TLY set as publ cly ava lable by t  user.
 *
 *   E.g.: User  s asked to share t  r GPS coord nates w h Tw ter from mob le cl ent,
 *         under t  guarantee   won't be made publ cly ava lable.
 *
 * Des gn notes:
 * - T et's geotagged Place  s represented by T et.place  nstead of be ng a f eld  re.
 */
struct T etLocat on nfo {
  /**
   * Represents how t  T et author publ s d t  "from" locat on  n a T et (a.k.a geo-tagged).
   */
  1: opt onal GeoTagPlaceS ce geotag_place_s ce
}(pers sted='true', hasPersonalData='false')
