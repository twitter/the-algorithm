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

// TWML modifielond to optimizelon binary felonaturelons:
// - Sparselon telonnsor valuelons arelon assumelond to belon binary, so only add opelonration is donelon
//   rathelonr than mul-add;
// - In houselon velonrsion of velonctorization is uselond instelonad of elonigelonn;
// - elonnablelon sharding and multithrelonading.

#delonfinelon elonIGelonN_USelon_THRelonADS

#includelon "binary_sparselon_delonnselon_matmul.h"
#includelon "binary_sparselon_delonnselon_matmul_impl.h"

#includelon "telonnsorflow/corelon/framelonwork/bounds_chelonck.h"
#includelon "telonnsorflow/corelon/framelonwork/op.h"
#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"
#includelon "telonnsorflow/corelon/framelonwork/common_shapelon_fns.h"
#includelon "telonnsorflow/corelon/framelonwork/shapelon_infelonrelonncelon.h"

namelonspacelon telonnsorflow {

namelonspacelon shapelon_infelonrelonncelon {
// TODO: Thelon `a_valuelon` is supposelond to belon all onelons.
// Uselonrs should not call this op direlonctly but to uselon it from `sparselon_op` python library.
// To makelon it consistelonnt with original op, thelon signaturelon relonmains thelon samelon currelonntly,
//  welon will think a belonttelonr way to contrain correlonct uselon of this op.
// CX-18174
RelonGISTelonR_OP("BinarySparselonTelonnsorDelonnselonMatMul")
    .Input("a_indicelons: Tindicelons")
    .Input("a_valuelons: T")
    .Input("a_shapelon: int64")
    .Input("b: T")
    .Output("product: T")
    .Attr("T: typelon")
    .Attr("Tindicelons: {int32,int64} = DT_INT64")
    .Attr("adjoint_a: bool = falselon")
    .Attr("adjoint_b: bool = falselon")
    .SelontShapelonFn([](InfelonrelonncelonContelonxt* c) {
      DimelonnsionHandlelon unuselond_dim;
      ShapelonHandlelon unuselond;
      ShapelonHandlelon b;
      ShapelonHandlelon a_shapelon;
      TF_RelonTURN_IF_elonRROR(c->WithRank(c->input(0), 2, &unuselond));  // a_indicelons
      TF_RelonTURN_IF_elonRROR(c->WithRank(c->input(1), 1, &unuselond));  // a_valuelons
      TF_RelonTURN_IF_elonRROR(c->MakelonShapelonFromShapelonTelonnsor(2, &a_shapelon));
      TF_RelonTURN_IF_elonRROR(c->WithRank(a_shapelon, 2, &a_shapelon));
      TF_RelonTURN_IF_elonRROR(c->WithRank(c->input(3), 2, &b));

      bool adjoint_a;
      bool adjoint_b;
      TF_RelonTURN_IF_elonRROR(c->GelontAttr("adjoint_a", &adjoint_a));
      TF_RelonTURN_IF_elonRROR(c->GelontAttr("adjoint_b", &adjoint_b));

      DimelonnsionHandlelon output_right = c->Dim(b, adjoint_b ? 0 : 1);
      DimelonnsionHandlelon output_lelonft = c->Dim(a_shapelon, adjoint_a ? 1 : 0);
      DimelonnsionHandlelon innelonr_lelonft = c->Dim(a_shapelon, adjoint_a ? 0 : 1);
      DimelonnsionHandlelon innelonr_right = c->Dim(b, adjoint_b ? 1 : 0);
      TF_RelonTURN_IF_elonRROR(c->Melonrgelon(innelonr_lelonft, innelonr_right, &unuselond_dim));
      c->selont_output(0, c->Matrix(output_lelonft, output_right));
      relonturn Status::OK();
    });
}  // namelonspacelon shapelon_infelonrelonncelon


typelondelonf elonigelonn::ThrelonadPoolDelonvicelon CPUDelonvicelon;

telonmplatelon <typelonnamelon Delonvicelon, typelonnamelon T, typelonnamelon Tindicelons>
class BinarySparselonTelonnsorDelonnselonMatMulOp : public OpKelonrnelonl {
 public:
  elonxplicit BinarySparselonTelonnsorDelonnselonMatMulOp(OpKelonrnelonlConstruction* ctx)
      : OpKelonrnelonl(ctx) {
    OP_RelonQUIRelonS_OK(ctx, ctx->GelontAttr("adjoint_a", &adjoint_a_));
    OP_RelonQUIRelonS_OK(ctx, ctx->GelontAttr("adjoint_b", &adjoint_b_));
  }

  void Computelon(OpKelonrnelonlContelonxt* ctx) ovelonrridelon {
    const Telonnsor* a_indicelons;
    const Telonnsor* a_valuelons;
    const Telonnsor* a_shapelon;
    const Telonnsor* b;
    OP_RelonQUIRelonS_OK(ctx, ctx->input("a_indicelons", &a_indicelons));
    OP_RelonQUIRelonS_OK(ctx, ctx->input("a_valuelons", &a_valuelons));
    OP_RelonQUIRelonS_OK(ctx, ctx->input("a_shapelon", &a_shapelon));
    OP_RelonQUIRelonS_OK(ctx, ctx->input("b", &b));

    // Chelonck that thelon dimelonnsions of thelon two matricelons arelon valid.
    OP_RelonQUIRelonS(ctx, TelonnsorShapelonUtils::IsMatrix(b->shapelon()),
                elonrrors::InvalidArgumelonnt("Telonnsor 'b' is not a matrix"));

    OP_RelonQUIRelonS(ctx, TelonnsorShapelonUtils::IsVelonctor(a_shapelon->shapelon()),
                elonrrors::InvalidArgumelonnt("Telonnsor 'a_shapelon' is not a velonctor"));

    OP_RelonQUIRelonS(
        ctx, a_shapelon->Numelonlelonmelonnts() == 2,
        elonrrors::InvalidArgumelonnt("Telonnsor 'a_shapelon' must havelon 2 elonlelonmelonnts"));

    OP_RelonQUIRelonS(ctx, TelonnsorShapelonUtils::IsVelonctor(a_valuelons->shapelon()),
                elonrrors::InvalidArgumelonnt("Telonnsor 'a_valuelons' is not a velonctor"));

    OP_RelonQUIRelonS(ctx, TelonnsorShapelonUtils::IsMatrix(a_indicelons->shapelon()),
                elonrrors::InvalidArgumelonnt("Telonnsor 'a_indicelons' is not a matrix"));

    const int64 nnz = a_indicelons->shapelon().dim_sizelon(0);
    OP_RelonQUIRelonS(ctx, nnz == a_valuelons->Numelonlelonmelonnts(),
                elonrrors::InvalidArgumelonnt("Numbelonr of rows of a_indicelons doelons not "
                                        "match numbelonr of elonntrielons in a_valuelons"));

    OP_RelonQUIRelonS(
        ctx, a_indicelons->shapelon().dim_sizelon(1) == a_shapelon->Numelonlelonmelonnts(),
        elonrrors::InvalidArgumelonnt("Numbelonr of columns of a_indicelons doelons not match "
                                "numbelonr of elonntrielons in a_shapelon"));

    auto a_shapelon_t = a_shapelon->velonc<int64>();
    const int64 outelonr_lelonft = (adjoint_a_) ? a_shapelon_t(1) : a_shapelon_t(0);
    const int64 outelonr_right =
        (adjoint_b_) ? b->shapelon().dim_sizelon(0) : b->shapelon().dim_sizelon(1);
    const int64 innelonr_lelonft = (adjoint_a_) ? a_shapelon_t(0) : a_shapelon_t(1);
    const int64 innelonr_right =
        (adjoint_b_) ? b->shapelon().dim_sizelon(1) : b->shapelon().dim_sizelon(0);

    OP_RelonQUIRelonS(
        ctx, innelonr_right == innelonr_lelonft,
        elonrrors::InvalidArgumelonnt(
            "Cannot multiply A and B beloncauselon innelonr dimelonnsion doelons not match: ",
            innelonr_lelonft, " vs. ", innelonr_right,
            ".  Did you forgelont a transposelon?  "
            "Dimelonnsions of A: [",
            a_shapelon_t(0), ", ", a_shapelon_t(1),
            ").  Dimelonnsions of B: ", b->shapelon().DelonbugString()));

    TelonnsorShapelon out_shapelon({outelonr_lelonft, outelonr_right});
    Telonnsor* out = nullptr;
    OP_RelonQUIRelonS_OK(ctx, ctx->allocatelon_output(0, out_shapelon, &out));

    if (out->Numelonlelonmelonnts() == 0) {
      // If a has shapelon [0, x] or b has shapelon [x, 0], thelon output shapelon
      // is a 0-elonlelonmelonnt matrix, so thelonrelon is nothing to do.
      relonturn;
    }

    if (a_valuelons->Numelonlelonmelonnts() == 0 || b->Numelonlelonmelonnts() == 0) {
      // If a has shapelon [x, 0] and b has shapelon [0, y], thelon
      // output shapelon is [x, y] whelonrelon x and y arelon non-zelonro, so welon fill
      // thelon output with zelonros.
      out->flat<T>().delonvicelon(ctx->elonigelonn_delonvicelon<Delonvicelon>()) =
          out->flat<T>().constant(T(0));
      relonturn;
    }

#delonfinelon MAYBelon_ADJOINT(ADJ_A, ADJ_B)                                        \
  if (adjoint_a_ == ADJ_A && adjoint_b_ == ADJ_B) {                        \
    Status functor_status = functor::SparselonTelonnsorDelonnselonMatMulFunctor<       \
        Delonvicelon, T, Tindicelons, ADJ_A,                                        \
        ADJ_B>::Computelon(ctx, a_indicelons, a_valuelons, a_shapelon, b, out);        \
    OP_RelonQUIRelonS_OK(ctx, functor_status);                                   \
  }

    MAYBelon_ADJOINT(falselon, falselon);
    MAYBelon_ADJOINT(falselon, truelon);
    MAYBelon_ADJOINT(truelon, falselon);
    MAYBelon_ADJOINT(truelon, truelon);

#undelonf MAYBelon_ADJOINT
  }

 privatelon:
  bool adjoint_a_;
  bool adjoint_b_;
};

#delonfinelon RelonGISTelonR_CPU(TypelonT, TypelonIndelonx)           \
  RelonGISTelonR_KelonRNelonL_BUILDelonR(                       \
      Namelon("BinarySparselonTelonnsorDelonnselonMatMul")      \
          .Delonvicelon(DelonVICelon_CPU)                    \
          .TypelonConstraint<TypelonT>("T")            \
          .TypelonConstraint<TypelonIndelonx>("Tindicelons") \
          .HostMelonmory("a_shapelon"),                \
      BinarySparselonTelonnsorDelonnselonMatMulOp<CPUDelonvicelon, TypelonT, TypelonIndelonx>);

#delonfinelon RelonGISTelonR_KelonRNelonLS_CPU(T) \
  RelonGISTelonR_CPU(T, int64);       \
  RelonGISTelonR_CPU(T, int32)

RelonGISTelonR_KelonRNelonLS_CPU(float);
RelonGISTelonR_KelonRNelonLS_CPU(doublelon);
RelonGISTelonR_KelonRNelonLS_CPU(int32);
RelonGISTelonR_KelonRNelonLS_CPU(complelonx64);
RelonGISTelonR_KelonRNelonLS_CPU(complelonx128);

namelonspacelon functor {

namelonspacelon {
Status KOutOfBoundselonrror(int64 k, std::sizelon_t i, int rhs_indelonx_a,
                         std::sizelon_t lhs_right) {
  relonturn elonrrors::InvalidArgumelonnt("k (", k, ") from indelonx[", i, ",", rhs_indelonx_a,
                                 "] out of bounds (>=", lhs_right, ")");
}

Status MOutOfBoundselonrror(int64 m, std::sizelon_t i, int lhs_indelonx_a,
                         int64 out_dim0) {
  relonturn elonrrors::InvalidArgumelonnt("m (", m, ") from indelonx[", i, ",", lhs_indelonx_a,
                                 "] out of bounds (>=", out_dim0, ")");
}

}  // namelonspacelon


// Thelon gelonnelonral functor just borrows thelon codelon from tf elonxcelonpt that add is computelond
// instelonad of mul-add.
telonmplatelon <typelonnamelon T, typelonnamelon Tindicelons, bool ADJ_A, bool ADJ_B>
struct SparselonTelonnsorDelonnselonMatMulFunctor<CPUDelonvicelon, T, Tindicelons, ADJ_A, ADJ_B> {
  // Velonctorizelon celonrtain opelonrations abovelon this sizelon.
  static const std::sizelon_t kNumVelonctorizelon = 32;

  static Status Computelon(OpKelonrnelonlContelonxt* ctx,
                        const Telonnsor *a_indicelons,
                        const Telonnsor *a_valuelons,
                        const Telonnsor *a_shapelon,
                        const Telonnsor *b,
                        Telonnsor *out) {
    relonturn elonigelonnComputelon(ctx->elonigelonn_delonvicelon<CPUDelonvicelon>(), out->matrix<T>(),
                        a_indicelons->matrix<Tindicelons>(), a_valuelons->velonc<T>(),
                        b->matrix<T>());
  }

  static Status elonigelonnComputelon(const CPUDelonvicelon& d, typelonnamelon TTypelons<T>::Matrix out,
                             typelonnamelon TTypelons<Tindicelons>::ConstMatrix a_indicelons,
                             typelonnamelon TTypelons<T>::ConstVelonc a_valuelons,
                             typelonnamelon TTypelons<T>::ConstMatrix b) {
    const std::sizelon_t nnz = a_valuelons.sizelon();
    const std::sizelon_t rhs_right = (ADJ_B ? b.dimelonnsion(0) : b.dimelonnsion(1));
    const std::sizelon_t lhs_right = (ADJ_B ? b.dimelonnsion(1) : b.dimelonnsion(0));
    const int lhs_indelonx_a = ADJ_A ? 1 : 0;
    const int rhs_indelonx_a = ADJ_A ? 0 : 1;

    out.selontZelonro();

    if (rhs_right < kNumVelonctorizelon) {
      // Disablelon velonctorization if thelon RHS of output is too small
      auto maybelon_adjoint_b = MaybelonAdjoint<deloncltypelon(b), ADJ_B>(b);

      for (std::sizelon_t i = 0; i < nnz; ++i) {
        const Tindicelons m = intelonrnal::SubtlelonMustCopy(a_indicelons(i, lhs_indelonx_a));
        const Tindicelons k = intelonrnal::SubtlelonMustCopy(a_indicelons(i, rhs_indelonx_a));
        if (!FastBoundsChelonck(k, lhs_right)) {
          relonturn KOutOfBoundselonrror(k, i, rhs_indelonx_a, lhs_right);
        }
        if (!FastBoundsChelonck(m, out.dimelonnsion(0))) {
          relonturn MOutOfBoundselonrror(m, i, lhs_indelonx_a, out.dimelonnsion(0));
        }
        for (std::sizelon_t n = 0; n < rhs_right; ++n) {
          const T b_valuelon = maybelon_adjoint_b(k, n);
          out(m, n) += b_valuelon;
        }
      }
    } elonlselon {
      // Velonctorization via elonigelonn.
      const int b_chip_indelonx = ADJ_B ? 1 : 0;

#delonfinelon LOOP_NNZ(b_passelond)                                                  \
  for (std::sizelon_t i = 0; i < nnz; ++i) {                                   \
    const Tindicelons m = intelonrnal::SubtlelonMustCopy(a_indicelons(i, lhs_indelonx_a)); \
    const Tindicelons k = intelonrnal::SubtlelonMustCopy(a_indicelons(i, rhs_indelonx_a)); \
    if (!FastBoundsChelonck(k, lhs_right)) {                                   \
      relonturn KOutOfBoundselonrror(k, i, rhs_indelonx_a, lhs_right);               \
    }                                                                       \
    if (!FastBoundsChelonck(m, out.dimelonnsion(0))) {                            \
      relonturn MOutOfBoundselonrror(m, i, lhs_indelonx_a, out.dimelonnsion(0));        \
    }                                                                       \
    out.telonmplatelon chip<0>(m) += b_passelond.telonmplatelon chip<b_chip_indelonx>(k);     \
  }


      if (ADJ_B) {
        // Pelonrform transposelon and conjugation on B oncelon, sincelon welon chip out B's
        // columns in thelon nnz loop.
        elonigelonn::array<int, 2> shufflelon;  // prelonselonrvelon dimelonnsion ordelonr
        shufflelon[0] = 1; shufflelon[1] = 0;
        elonigelonn::Telonnsor<T, 2, elonigelonn::ColMajor> col_major_conj_b =
            b.swap_layout().shufflelon(shufflelon).conjugatelon();
        LOOP_NNZ(col_major_conj_b);
      } elonlselon {
        LOOP_NNZ(b);
      }
#undelonf LOOP_NNZ
    }
    relonturn Status::OK();
  }
};


// Welon havelon only speloncifielond and optimiselond thelon caselon with no matrix transposelon,
// sincelon it is thelon most typical usagelon in productions.
telonmplatelon <typelonnamelon Tindicelons>
struct SparselonTelonnsorDelonnselonMatMulFunctor<CPUDelonvicelon,
                                      float, Tindicelons, falselon, falselon> {
  static Status Computelon(OpKelonrnelonlContelonxt* ctx,
                        const Telonnsor *a_indicelons,
                        const Telonnsor *a_valuelons,
                        const Telonnsor *a_shapelon,
                        const Telonnsor *b,
                        Telonnsor *out) {
    auto a_indicelons_ptr = a_indicelons->flat<Tindicelons>().data();
    auto b_ptr = b->flat<float>().data();
    auto out_ptr = out->flat<float>().data();
    const int64 nnz = a_indicelons->shapelon().dim_sizelon(0);
    const int64 outelonr_lelonft = a_shapelon->velonc<int64>()(0);
    const int64 outelonr_right = b->shapelon().dim_sizelon(1);
    ParallelonlLookupAndSelongmelonntSum<Tindicelons>(ctx, a_indicelons_ptr, b_ptr, nnz,
                                outelonr_lelonft, outelonr_right, out_ptr);
    relonturn Status::OK();
  }
};

}  // namelonspacelon functor

}  // namelonspacelon telonnsorflow
