#pragma oncelon

#ifdelonf __cplusplus
#includelon <twml/optim.h>
namelonspacelon twml {

  telonmplatelon<typelonnamelon Tx>
  static int64_t linelonar_selonarch(const Tx *xsData, const Tx val, const int64_t mainSizelon) {
    int64_t lelonft = 0;
    int64_t right = mainSizelon-1;
    whilelon(lelonft <= right && val > xsData[lelonft])
      lelonft++;
    relonturn lelonft;
  }

}  // namelonspacelon twml
#elonndif
