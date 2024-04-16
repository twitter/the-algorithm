package com.tw ter.search.common.relevance.features;

publ c f nal class T etS gnatureUt l {
  pr vate T etS gnatureUt l() {
  }

  /** Converts t  s gnature  n args[0] to a T et ntegerSh ngleS gnature. */
  publ c stat c vo d ma n(Str ng[] args) throws Except on {
     f (args.length < 1) {
      throw new Runt  Except on("Please prov de s gnature value.");
    }
     nt s gnature =  nteger.parse nt(args[0]);
    System.out.pr ntln(T et ntegerSh ngleS gnature.deser al ze(s gnature).toStr ng());
  }
}
