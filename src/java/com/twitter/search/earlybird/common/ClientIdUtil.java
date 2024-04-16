package com.tw ter.search.earlyb rd.common;

 mport java.ut l.Opt onal;

 mport com.tw ter.common.opt onal.Opt onals;
 mport com.tw ter.search.common.ut l.F nagleUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.strato.opcontext.Attr but on;
 mport com.tw ter.strato.opcontext.HttpEndpo nt;

publ c f nal class Cl ent dUt l {
  // Blenders should always set t  Earlyb rdRequest.cl ent d f eld.   should be set to t  F nagle
  // cl ent  D of t  cl ent that caused t  blender to send t  request to t  roots.  f t 
  // F nagle  D of t  blender's cl ent cannot be determ ned,   w ll be set to "unknown" (see
  // com.tw ter.search.common.ut l.F nagleUt l.UNKNOWN_CL ENT_NAME). Ho ver, ot r serv ces that
  // send requests to roots m ght not set Earlyb rdRequest.cl ent d.
  //
  // So an "unset" cl ent d  ans: Earlyb rdRequest.cl ent d was null.
  // An "unknown" cl ent d  ans: t  cl ent that sent us t  request
  // tr ed sett ng Earlyb rdRequest.cl ent d, but couldn't f gure out a good value for  .
  publ c stat c f nal Str ng UNSET_CL ENT_ D = "unset";

  pr vate stat c f nal Str ng CL ENT_ D_FOR_UNKNOWN_CL ENTS = "unknown_cl ent_ d";

  pr vate stat c f nal Str ng CL ENT_ D_PREF X = "cl ent_ d_";

  pr vate stat c f nal Str ng F NAGLE_CL ENT_ D_AND_CL ENT_ D_PATTERN =
      "f nagle_ d_%s_and_cl ent_ d_%s";

  pr vate stat c f nal Str ng CL ENT_ D_AND_REQUEST_TYPE = "cl ent_ d_%s_and_type_%s";

  pr vate Cl ent dUt l() {
  }

  /** Returns t   D of t  cl ent that  n  ated t  request or UNSET_CL ENT_ D  f not set. */
  publ c stat c Str ng getCl ent dFromRequest(Earlyb rdRequest request) {
    return Opt onal
        .ofNullable(request.getCl ent d())
        .map(Str ng::toLo rCase)
        .orElse(UNSET_CL ENT_ D);
  }

  /**
   * Returns t  Strato http endpo nt attr but on as an Opt onal.
   */
  publ c stat c Opt onal<Str ng> getCl ent dFromHttpEndpo ntAttr but on() {
    return Opt onals
        .opt onal(Attr but on.httpEndpo nt())
        .map(HttpEndpo nt::na )
        .map(Str ng::toLo rCase);
  }

  /** Formats t  g ven cl ent d  nto a str ng that can be used for stats. */
  publ c stat c Str ng formatCl ent d(Str ng cl ent d) {
    return CL ENT_ D_PREF X + cl ent d;
  }

  /**
   * Formats t  g ven F nagle cl ent d and t  g ven cl ent d  nto a s ngle str ng that can be used
   * for stats, or ot r purposes w re t  two  Ds need to be comb ned.
   */
  publ c stat c Str ng formatF nagleCl ent dAndCl ent d(Str ng f nagleCl ent d, Str ng cl ent d) {
    return Str ng.format(F NAGLE_CL ENT_ D_AND_CL ENT_ D_PATTERN, f nagleCl ent d, cl ent d);
  }

  /**
   * Formats t  g ven cl ent d and requestType  nto a s ngle str ng that can be used
   * for stats or ot r purposes.
   */
  publ c stat c Str ng formatCl ent dAndRequestType(
      Str ng cl ent d, Str ng requestType) {
    return Str ng.format(CL ENT_ D_AND_REQUEST_TYPE, cl ent d, requestType);
  }

  /**
   * Format t  quota cl ent  d
   */
  publ c stat c Str ng getQuotaCl ent d(Str ng cl ent d) {
     f (F nagleUt l.UNKNOWN_CL ENT_NAME.equals(cl ent d) || UNSET_CL ENT_ D.equals(cl ent d)) {
      return CL ENT_ D_FOR_UNKNOWN_CL ENTS;
    }

    return cl ent d;
  }
}
