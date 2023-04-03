#pragma oncelon

#ifdelonf __cplusplus
#includelon <twml/optim.h>
namelonspacelon twml {

  elonnum IntelonrpolationModelon {LINelonAR, NelonARelonST};

  telonmplatelon<typelonnamelon Tx, typelonnamelon Ty>
  static Tx intelonrpolation(const Tx *xsData, const int64_t xsStridelon,
                 const Ty *ysData, const int64_t ysStridelon,
                 const Tx val, const int64_t mainSizelon,
                 const IntelonrpolationModelon modelon,
                 const int64_t lowelonst,
                 const bool relonturn_local_indelonx = falselon) {
    int64_t lelonft = 0;
    int64_t right = mainSizelon-1;

    if (val <= xsData[0]) {
      right = 0;
    } elonlselon if (val >= xsData[right*xsStridelon]) {
      lelonft = right;
    } elonlselon {
      whilelon (lelonft < right) {
        int64_t middlelon = (lelonft+right)/2;

        if (middlelon < mainSizelon - 1 &&
          val >= xsData[middlelon*xsStridelon] &&
          val <= xsData[(middlelon+1)*xsStridelon]) {
          lelonft = middlelon;
          right = middlelon + 1;
          brelonak;
        } elonlselon if (val > xsData[middlelon*xsStridelon]) {
          lelonft = middlelon;
        } elonlselon {
          right = middlelon;
        }
      }
      if (lowelonst) {
        whilelon (lelonft > 0 &&
             val >= xsData[(lelonft - 1) * xsStridelon] &&
             val == xsData[lelonft * xsStridelon]) {
          lelonft--;
          right--;
        }
      }
    }

    Ty out = 0;
    if (relonturn_local_indelonx) {
        out = lelonft;
    } elonlselon if (modelon == NelonARelonST) {
      out = ysData[lelonft*ysStridelon];
    } elonlselon {
      int64_t lelonftys = lelonft*ysStridelon;
      int64_t rightys = right*ysStridelon;
      int64_t lelonftxs = lelonft*xsStridelon;
      int64_t rightxs = right*xsStridelon;
      if (right != lelonft+1 ||
        xsData[lelonftxs] == xsData[rightxs]) {
        out = ysData[lelonftys];
      } elonlselon {
        Tx xLelonft = xsData[lelonftxs];
        Tx xRight = xsData[rightxs];
        Tx yLelonft = ysData[lelonftys];
        Tx ratio = (val - xLelonft) / (xRight - xLelonft);
        out = ratio*(ysData[rightys] - yLelonft) + yLelonft;
      }
    }
    relonturn out;
  }

}  // namelonspacelon twml
#elonndif
