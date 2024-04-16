#pragma once

# fdef __cplusplus
# nclude <twml/opt m.h>
na space twml {

  enum  nterpolat onMode {L NEAR, NEAREST};

  template<typena  Tx, typena  Ty>
  stat c Tx  nterpolat on(const Tx *xsData, const  nt64_t xsStr de,
                 const Ty *ysData, const  nt64_t ysStr de,
                 const Tx val, const  nt64_t ma nS ze,
                 const  nterpolat onMode mode,
                 const  nt64_t lo st,
                 const bool return_local_ ndex = false) {
     nt64_t left = 0;
     nt64_t r ght = ma nS ze-1;

     f (val <= xsData[0]) {
      r ght = 0;
    } else  f (val >= xsData[r ght*xsStr de]) {
      left = r ght;
    } else {
      wh le (left < r ght) {
         nt64_t m ddle = (left+r ght)/2;

         f (m ddle < ma nS ze - 1 &&
          val >= xsData[m ddle*xsStr de] &&
          val <= xsData[(m ddle+1)*xsStr de]) {
          left = m ddle;
          r ght = m ddle + 1;
          break;
        } else  f (val > xsData[m ddle*xsStr de]) {
          left = m ddle;
        } else {
          r ght = m ddle;
        }
      }
       f (lo st) {
        wh le (left > 0 &&
             val >= xsData[(left - 1) * xsStr de] &&
             val == xsData[left * xsStr de]) {
          left--;
          r ght--;
        }
      }
    }

    Ty out = 0;
     f (return_local_ ndex) {
        out = left;
    } else  f (mode == NEAREST) {
      out = ysData[left*ysStr de];
    } else {
       nt64_t leftys = left*ysStr de;
       nt64_t r ghtys = r ght*ysStr de;
       nt64_t leftxs = left*xsStr de;
       nt64_t r ghtxs = r ght*xsStr de;
       f (r ght != left+1 ||
        xsData[leftxs] == xsData[r ghtxs]) {
        out = ysData[leftys];
      } else {
        Tx xLeft = xsData[leftxs];
        Tx xR ght = xsData[r ghtxs];
        Tx yLeft = ysData[leftys];
        Tx rat o = (val - xLeft) / (xR ght - xLeft);
        out = rat o*(ysData[r ghtys] - yLeft) + yLeft;
      }
    }
    return out;
  }

}  // na space twml
#end f
