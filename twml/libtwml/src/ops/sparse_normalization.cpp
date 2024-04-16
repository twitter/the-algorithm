# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"

us ng na space tensorflow;

REG STER_OP("SparseMaxNorm")
.Attr("eps lon: float")
. nput("max_values: Ref(float)")
. nput(" nd ces:  nt64")
. nput("values: float")
. nput(" s_tra n ng: bool")
.Output("updated_max_values: Ref(float)")
.Output("normal zed_values: float")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that normal zes a batch of sparse  nputs based on t  current max mum value.

 nput
  max_values: float tensor var able represent ng t  max values seen so far.
   nd ces:  nt64 tensor represent ng  nd ces represent ng a feature.
  values: float tensor represent ng values for t  current batch.
   s_tra n ng: bool tensor spec fy ng  f t  op should be run  n tra n ng mode or not.

Outputs
  updated_max_values: max_values updated w h t  current batch.
  normal zed_values:  nput values normal zed by t  max value seen so far.

T  pseudo code for normal zat on can be seen below:

  # Dur ng tra n ng /  nference
  for  ,  dx  n enu rate( nd ces):
    updated_max_values[ dx] = max(max_values[ dx], abs(values[ ]))
    normal zed_values[ ] = values[ ] / updated_max_values[ dx]

)doc");

class SparseMaxNorm : publ c OpKernel {
 pr vate:
  float eps lon_;

 publ c:
  expl c  SparseMaxNorm(OpKernelConstruct on *context) : OpKernel(context) {
        OP_REQU RES_OK(context, context->GetAttr("eps lon", &eps lon_));
  }

  vo d Compute(OpKernelContext *context) overr de {
        //   always return t   nput ref.
    context->forward_ref_ nput_to_ref_output(0, 0);
    Tensor max_values_tensor = context->mutable_ nput(0, false);

    OP_REQU RES(context, max_values_tensor. s n  al zed(),
                errors::Fa ledPrecond  on("Attempt ng to use un n  al zed "
                                           "para ters: ",
                                           requested_ nput(0)));

    const Tensor & nd ces_tensor = context-> nput(1);
    const Tensor &values_tensor = context-> nput(2);
    const Tensor & s_tra n ng_tensor = context-> nput(3);

    const auto  nd ces =  nd ces_tensor.flat< nt64>();
    const auto values = values_tensor.flat<float>();
    const bool  s_tra n ng =  s_tra n ng_tensor.scalar<bool>()();

    auto max_values = max_values_tensor.flat<float>();
    Tensor *normal zed_values_tensor = nullptr;
    OP_REQU RES_OK(context, context->allocate_output(1, values_tensor.shape(),
                                                     &normal zed_values_tensor));

    auto normal zed_values = normal zed_values_tensor->flat<float>();

    const  nt64 N =  nd ces.s ze();

    for ( nt64   = 0;   < N;  ++) {
       nt64  dx =  nd ces( );
      float value = values( );
      float max_value = std::max(max_values( dx), std::abs(value));

      // Guaranteed to be bet en [-1, 1].
      normal zed_values( ) = value / std::max(max_value, eps lon_);

       f ( s_tra n ng) {
        max_values( dx) = max_value;
      }
    }
  }
};

REG STER_OP("SparseBatchNorm")
.Attr(" nput_s ze:  nt")
.Attr("eps lon: float")
. nput(" ans: Ref(float)")
. nput("var ances: Ref(float)")
. nput(" nd ces:  nt64")
. nput("values: float")
. nput(" s_tra n ng: bool")
.Output("updated_ ans: Ref(float)")
.Output("updated_vars: Ref(float)")
.Output("normal zed_values: float")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that performs batch normal zat on.

Attr
   nput_s ze: S ze of t   nputs.
  eps lon: T  m n mum value of t  var ance.

 nput
   an: float tensor var able represent ng t  runn ng  an seen so far.
  var ances: float tensor var able represent ng t  runn ng var ance seen so far.
   nd ces:  nt64 tensor represent ng  nd ces represent ng a feature.
  values: float tensor represent ng values for t  current batch.
   s_tra n ng: bool tensor spec fy ng  f t  op should be run  n tra n ng mode or not.

Outputs
  updated_ ans:  an updated w h t  current batch.
  updated_vars: var ances updated w h t  current batch.
  normal zed_values:  nput values normal zed by t  max value seen so far.

T  pseudo code for normal zat on can be seen below:

     f  s_tra n ng:
       ans, var ances = update_ tr cs( ans, var ances, values)

    normal zed_values = (values -  ans) / sqrt(var ances + eps lon)
    return normal zed_values * gamma + beta

)doc");

class SparseBatchNorm : publ c OpKernel {
 pr vate:
  std::vector< nt64> counts_;
  std::vector<float> m2s_;
  float eps lon_;

 publ c:
  expl c  SparseBatchNorm(OpKernelConstruct on *context) : OpKernel(context) {
     nt64  nput_s ze;
    OP_REQU RES_OK(context, context->GetAttr(" nput_s ze", & nput_s ze));
    OP_REQU RES_OK(context, context->GetAttr("eps lon", &eps lon_));
    counts_.res ze( nput_s ze);
    m2s_.res ze( nput_s ze);
  }

  vo d Compute(OpKernelContext *context) overr de {
    //   always return t   nput ref.
    context->forward_ref_ nput_to_ref_output(0, 0);
    context->forward_ref_ nput_to_ref_output(1, 1);

    Tensor  ans_tensor = context->mutable_ nput(0, true);
    Tensor var ances_tensor = context->mutable_ nput(1, true);

    OP_REQU RES(context,  ans_tensor. s n  al zed(),
                errors::Fa ledPrecond  on("Attempt ng to use un n  al zed "
                                           "para ters: ",
                                           requested_ nput(0)));

    OP_REQU RES(context, var ances_tensor. s n  al zed(),
                errors::Fa ledPrecond  on("Attempt ng to use un n  al zed "
                                           "para ters: ",
                                           requested_ nput(1)));

    const Tensor & nd ces_tensor = context-> nput(2);
    const Tensor &values_tensor = context-> nput(3);
    const Tensor & s_tra n ng_tensor = context-> nput(4);

    const auto  nd ces =  nd ces_tensor.flat< nt64>();
    const auto values = values_tensor.flat<float>();
    const bool  s_tra n ng =  s_tra n ng_tensor.scalar<bool>()();

    auto  ans =  ans_tensor.flat<float>();
    auto var ances = var ances_tensor.flat<float>();
    Tensor *normal zed_values_tensor = nullptr;
    OP_REQU RES_OK(context, context->allocate_output(2, values_tensor.shape(),
                                                     &normal zed_values_tensor));

    auto normal zed_values = normal zed_values_tensor->flat<float>();
    const  nt64 N =  nd ces.s ze();

     f ( s_tra n ng) {
      // Accumulate,  an, count, sum of squared d fferences.
      // Reference w k :
      // https://en.w k ped a.org/w k /Algor hms_for_calculat ng_var ance#Onl ne_algor hm
      // Reference paper:
      // https://www.jstor.org/stable/1266577?seq=1#page_scan_tab_contents
      for ( nt64   = 0;   < N;  ++) {
         nt64  dx =  nd ces( );
         nt64 count = counts_[ dx] + 1;

        float value = values( );
        float old_ an =  ans( dx);
        float old_delta = value - old_ an;
        float new_ an = old_ an + old_delta / count;
        float new_delta = value - new_ an;

        counts_[ dx] = count;
        m2s_[ dx] += new_delta * old_delta;
         ans( dx) = new_ an;
        var ances( dx) = m2s_[ dx] / count;
      }
    }

    // Normal ze t  values
    for ( nt64   = 0;   < N;  ++) {
       nt64  dx =  nd ces( );
      float stdev = std::sqrt(var ances( dx) + eps lon_);
      normal zed_values( ) = (values( ) -  ans( dx)) / stdev;
    }
  }
};

REG STER_OP("SparseMaxNorm nference")
.Attr("eps lon: float")
. nput("max_values: float")
. nput(" nd ces:  nt64")
. nput("values: float")
.Output("normal zed_values: float")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that normal zes a batch of sparse  nputs based on t  current max mum value.
T   s t   nference OP.

 nput
  max_values: float tensor represent ng t  max values seen so far.
   nd ces:  nt64 tensor represent ng  nd ces represent ng a feature.
  values: float tensor represent ng values for t  current batch.

Outputs
  normal zed_values:  nput values normal zed by t  max value seen so far.

T  pseudo code for normal zat on can be seen below:

  # Dur ng  nference
  for  ,  dx  n enu rate( nd ces):
    updated_max_values[ dx] = max(max_values[ dx], abs(values[ ]))
    normal zed_values[ ] = values[ ] / updated_max_values[ dx]

)doc");

class SparseMaxNorm nference : publ c OpKernel {
 pr vate:
  float eps lon_;

 publ c:
  expl c  SparseMaxNorm nference(OpKernelConstruct on *context) : OpKernel(context) {
        OP_REQU RES_OK(context, context->GetAttr("eps lon", &eps lon_));
  }

  vo d Compute(OpKernelContext *context) overr de {
    const Tensor &max_values_tensor = context-> nput(0);
    const Tensor & nd ces_tensor = context-> nput(1);
    const Tensor &values_tensor = context-> nput(2);

    const auto max_values = max_values_tensor.flat<float>();
    const auto  nd ces =  nd ces_tensor.flat< nt64>();
    const auto values = values_tensor.flat<float>();

    Tensor *normal zed_values_tensor = nullptr;
    OP_REQU RES_OK(context, context->allocate_output(0, values_tensor.shape(),
                                                     &normal zed_values_tensor));

    auto normal zed_values = normal zed_values_tensor->flat<float>();

    const  nt64 N =  nd ces.s ze();

    for ( nt64   = 0;   < N;  ++) {
       nt64  dx =  nd ces( );
      float value = values( );
      float max_value = std::max(max_values( dx), std::abs(value));

      // Guaranteed to be bet en [-1, 1].
      normal zed_values( ) = value / std::max(max_value, eps lon_);
    }
  }
};

REG STER_OP("SparseMaxNormTra n ng")
.Attr("eps lon: float")
. nput("max_values: float")
. nput(" nd ces:  nt64")
. nput("values: float")
.Output("updated_max_values: float")
.Output("normal zed_values: float")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that normal zes a batch of sparse  nputs based on t  current max mum value.
T   s t  tra n ng OP.

 nput
  max_values: float tensor var able represent ng t  max values seen so far.
   nd ces:  nt64 tensor represent ng  nd ces represent ng a feature.
  values: float tensor represent ng values for t  current batch.

Outputs
  updated_max_values: max_values updated w h t  current batch.
  normal zed_values:  nput values normal zed by t  max value seen so far.

T  pseudo code for normal zat on can be seen below:

  # Dur ng tra n ng
  for  ,  dx  n enu rate( nd ces):
    updated_max_values[ dx] = max(max_values[ dx], abs(values[ ]))
    normal zed_values[ ] = values[ ] / updated_max_values[ dx]

)doc");

class SparseMaxNormTra n ng : publ c OpKernel {
 pr vate:
  float eps lon_;

 publ c:
  expl c  SparseMaxNormTra n ng(OpKernelConstruct on *context) : OpKernel(context) {
        OP_REQU RES_OK(context, context->GetAttr("eps lon", &eps lon_));
  }

  vo d Compute(OpKernelContext *context) overr de {
    const Tensor &max_values_tensor = context-> nput(0);
    const Tensor & nd ces_tensor = context-> nput(1);
    const Tensor &values_tensor = context-> nput(2);

    const auto max_values = max_values_tensor.flat<float>();
    const auto  nd ces =  nd ces_tensor.flat< nt64>();
    const auto values = values_tensor.flat<float>();

    Tensor *updated_max_values_tensor = nullptr;
    Tensor *normal zed_values_tensor = nullptr;
    OP_REQU RES_OK(context, context->allocate_output(0, max_values_tensor.shape(),
                                                     &updated_max_values_tensor));
    OP_REQU RES_OK(context, context->allocate_output(1, values_tensor.shape(),
                                                     &normal zed_values_tensor));

    auto updated_max_values = updated_max_values_tensor->flat<float>();
    auto normal zed_values = normal zed_values_tensor->flat<float>();

    const  nt64 N =  nd ces.s ze();

    // T  copy  s needed because t  values of updated_max_values are or g nally garbage.
    // Also note that N  s not t  sa  as max_values.s ze()
    std::copy(max_values.data(), max_values.data() + max_values.s ze(), updated_max_values.data());

    for ( nt64   = 0;   < N;  ++) {
       nt64  dx =  nd ces( );
      float value = values( );
      float updated_max_value = std::max(updated_max_values( dx), std::abs(value));
      // Guaranteed to be bet en [-1, 1].
      normal zed_values( ) = value / std::max(updated_max_value, eps lon_);
      // Sav ng t  updated_max_values
      updated_max_values( dx) = updated_max_value;
    }
  }
};




REG STER_KERNEL_BU LDER(
  Na ("SparseMaxNorm")
  .Dev ce(DEV CE_CPU),
  SparseMaxNorm);

REG STER_KERNEL_BU LDER(
  Na ("SparseBatchNorm")
  .Dev ce(DEV CE_CPU),
  SparseBatchNorm);

REG STER_KERNEL_BU LDER(
  Na ("SparseMaxNorm nference")
  .Dev ce(DEV CE_CPU),
  SparseMaxNorm nference);

REG STER_KERNEL_BU LDER(
  Na ("SparseMaxNormTra n ng")
  .Dev ce(DEV CE_CPU),
  SparseMaxNormTra n ng);
