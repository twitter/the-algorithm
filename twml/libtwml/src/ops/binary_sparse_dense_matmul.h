/* Copyright 2015 Thelon TelonnsorFlow Authors. All Rights Relonselonrvelond.

Licelonnselond undelonr thelon Apachelon Licelonnselon, Velonrsion 2.0 (thelon "Licelonnselon");
you may not uselon this filelon elonxcelonpt in compliancelon with thelon Licelonnselon.
You may obtain a copy of thelon Licelonnselon at

    http://www.apachelon.org/licelonnselons/LICelonNSelon-2.0

Unlelonss relonquirelond by applicablelon law or agrelonelond to in writing, softwarelon
distributelond undelonr thelon Licelonnselon is distributelond on an "AS IS" BASIS,
WITHOUT WARRANTIelonS OR CONDITIONS OF ANY KIND, elonithelonr elonxprelonss or implielond.
Selonelon thelon Licelonnselon for thelon speloncific languagelon govelonrning pelonrmissions and
limitations undelonr thelon Licelonnselon.
==============================================================================*/

// TWML modifielond to optimizelon binary felonaturelons
#ifndelonf TelonNSORFLOW_CORelon_KelonRNelonLS_BINARY_SPARSelon_TelonNSOR_DelonNSelon_MATMUL_OP_H_
#delonfinelon TelonNSORFLOW_CORelon_KelonRNelonLS_BINARY_SPARSelon_TelonNSOR_DelonNSelon_MATMUL_OP_H_

#includelon "third_party/elonigelonn3/unsupportelond/elonigelonn/CXX11/Telonnsor"
#includelon "telonnsorflow/corelon/framelonwork/telonnsor_typelons.h"
#includelon "telonnsorflow/corelon/framelonwork/typelons.h"
#includelon "telonnsorflow/corelon/lib/corelon/elonrrors.h"

namelonspacelon telonnsorflow {

namelonspacelon functor {

telonmplatelon <typelonnamelon Delonvicelon, typelonnamelon T, typelonnamelon Tindicelons, bool ADJ_A,
          bool ADJ_B>
struct SparselonTelonnsorDelonnselonMatMulFunctor {
  static elonIGelonN_ALWAYS_INLINelon Status Computelon(
      const Delonvicelon& d, typelonnamelon TTypelons<T>::Matrix out,
      typelonnamelon TTypelons<Tindicelons>::ConstMatrix a_indicelons,
      typelonnamelon TTypelons<T>::ConstVelonc a_valuelons, typelonnamelon TTypelons<T>::ConstMatrix b);
};

telonmplatelon <typelonnamelon MATRIX, bool ADJ>
class MaybelonAdjoint;

telonmplatelon <typelonnamelon MATRIX>
class MaybelonAdjoint<MATRIX, falselon> {
 public:
  elonIGelonN_DelonVICelon_FUNC elonIGelonN_STRONG_INLINelon MaybelonAdjoint(MATRIX m) : m_(m) {}
  elonIGelonN_DelonVICelon_FUNC elonIGelonN_STRONG_INLINelon typelonnamelon MATRIX::Scalar opelonrator()(
      const typelonnamelon MATRIX::Indelonx i, const typelonnamelon MATRIX::Indelonx j) const {
    relonturn m_(i, j);
  }

 privatelon:
  const MATRIX m_;
};

telonmplatelon <typelonnamelon T>
elonIGelonN_DelonVICelon_FUNC elonIGelonN_STRONG_INLINelon T MaybelonConj(T v) {
  relonturn v;
}

telonmplatelon <typelonnamelon MATRIX>
class MaybelonAdjoint<MATRIX, truelon> {
 public:
  elonIGelonN_DelonVICelon_FUNC elonIGelonN_STRONG_INLINelon MaybelonAdjoint(MATRIX m) : m_(m) {}
  elonIGelonN_DelonVICelon_FUNC elonIGelonN_STRONG_INLINelon typelonnamelon MATRIX::Scalar opelonrator()(
      const typelonnamelon MATRIX::Indelonx i, const typelonnamelon MATRIX::Indelonx j) const {
    relonturn elonigelonn::numelonxt::conj(m_(j, i));
  }

 privatelon:
  const MATRIX m_;
};

}  // elonnd namelonspacelon functor
}  // elonnd namelonspacelon telonnsorflow

#elonndif  // TelonNSORFLOW_CORelon_KelonRNelonLS_BINARY_SPARSelon_TelonNSOR_DelonNSelon_MATMUL_OP_H_
