# nclude "block_format_reader.h"

# nclude "tensorflow/core/fra work/dataset.h"
# nclude "tensorflow/core/fra work/part al_tensor_shape.h"
# nclude "tensorflow/core/fra work/tensor.h"
# nclude "tensorflow/core/l b/ o/random_ nputstream.h"

# f !def ned(D SABLE_ZL B)
# nclude "tensorflow/core/l b/ o/zl b_ nputstream.h"
#end f

# nclude <twml.h>

# nclude <cstd o>
# nclude <algor hm>
# nclude < erator>

us ng na space tensorflow;


 nl ne std::str ng str pPath(std::str ng const &f le_na ) {
  const auto pos = f le_na .f nd_last_of("/");
   f (pos == std::str ng::npos) return f le_na ;
  return f le_na .substr(pos + 1);
}

 nl ne std::str ng getExtens on(std::str ng const &f le_na ) {
  const auto str pped_f le_na  = str pPath(f le_na );
  const auto pos = str pPath(str pped_f le_na ).f nd_last_of(".");
   f (pos == std::str ng::npos) return "";
  return str pped_f le_na .substr(pos + 1);
}

REG STER_OP("BlockFormatDatasetV2")
. nput("f lena s: str ng")
. nput("compress on_type: str ng")
. nput("buffer_s ze:  nt64")
.Output("handle: var ant")
.Set sStateful()
.SetShapeFn(shape_ nference::ScalarShape)
.Doc(R"doc(

Creates a dataset for stream ng BlockFormat data  n compressed (e.g. gz p), uncompressed formats.
T  op also has t  ab l y stream a dataset conta n ng f les from mult ple formats  nt oned above.

f lena s: A scalar or vector conta n ng t  na (s) of t  f le(s) to be read.
compress on_type: A scalar str ng denot ng t  compress on type. Can be 'none', 'zl b', 'auto'.
buffer_s ze: A scalar denot ng t  buffer s ze to use dur ng decompress on.

Outputs
  handle: A handle to t  dataset. T  handle  s later used to create an  erator to stream t  data from t  dataset.

)doc");


class BlockFormatDatasetV2 : publ c DatasetOpKernel {
 publ c:
  us ng DatasetOpKernel::DatasetOpKernel;

  vo d MakeDataset(OpKernelContext* ctx, DatasetBase **output) overr de {
    const Tensor* f lena s_tensor;
    OP_REQU RES_OK(ctx, ctx-> nput("f lena s", &f lena s_tensor));
    OP_REQU RES(
        ctx, f lena s_tensor->d ms() <= 1,
        errors:: nval dArgu nt("`f lena s` must be a scalar or a vector."));

    const auto f lena s_flat = f lena s_tensor->flat<str ng>();
    const  nt64 num_f les = f lena s_tensor->NumEle nts();
    std::vector<str ng> f lena s;
    f lena s.reserve(num_f les);
    std::copy(f lena s_flat.data(),
              f lena s_flat.data() + num_f les,
              std::back_ nserter(f lena s));

    str ng compress on_type;
    OP_REQU RES_OK(
        ctx, tensorflow::data::ParseScalarArgu nt<str ng>(
            ctx, "compress on_type", &compress on_type));

     nt64 buffer_s ze = -1;
    OP_REQU RES_OK(
        ctx, tensorflow::data::ParseScalarArgu nt< nt64>(
            ctx, "buffer_s ze", &buffer_s ze));

    OP_REQU RES(ctx, buffer_s ze >= 0,
                errors:: nval dArgu nt(
                    "`buffer_s ze` must be >= 0 (0 == no buffer ng)"));

    OP_REQU RES(ctx,
                compress on_type == "auto" ||
                compress on_type == "gz" ||
                compress on_type == "",
                errors:: nval dArgu nt("Unknown extens on: ", compress on_type));

    *output = new Dataset(ctx, std::move(f lena s), compress on_type, buffer_s ze);
  }

 pr vate:
  class Dataset : publ c DatasetBase {
   publ c:
    Dataset(OpKernelContext* ctx,
            std::vector<str ng> f lena s,
            std::str ng compress on_type,
             nt64 buffer_s ze)
        : DatasetBase(DatasetContext(ctx)),
          compress on_type_(compress on_type),
          buffer_s ze_(buffer_s ze),
          f lena s_(std::move(f lena s))
    {}

    const DataTypeVector& output_dtypes() const overr de {
      stat c DataTypeVector* dtypes = new DataTypeVector({DT_STR NG});
      return *dtypes;
    }

    const std::vector<Part alTensorShape>& output_shapes() const overr de {
      stat c std::vector<Part alTensorShape>* shapes =
          new std::vector<Part alTensorShape>({{}});
      return *shapes;
    }

    str ng DebugStr ng() const overr de { return "BlockFormatDatasetV2::Dataset"; }

   protected:
    Status AsGraphDef nternal(Ser al zat onContext* ctx,
                              DatasetGraphDefBu lder* b,
                              Node** output) const overr de {
      Node* f lena s = nullptr;
      Node* compress on_type = nullptr;
      Node* buffer_s ze = nullptr;
      TF_RETURN_ F_ERROR(b->AddVector(f lena s_, &f lena s));
      TF_RETURN_ F_ERROR(b->AddScalar(compress on_type_, &compress on_type));
      TF_RETURN_ F_ERROR(
          b->AddScalar(buffer_s ze_, &buffer_s ze));
      TF_RETURN_ F_ERROR(b->AddDataset(
          t , {f lena s, compress on_type, buffer_s ze}, output));
      return Status::OK();
    }

   pr vate:
    std::un que_ptr< eratorBase> Make erator nternal(
        const str ng& pref x) const overr de {
      return std::un que_ptr< eratorBase>(
          new  erator({t , str ngs::StrCat(pref x, "::BlockFormat")}));
    }

    class  erator : publ c Dataset erator<Dataset> {
     publ c:
      expl c   erator(const Params &params)
          : Dataset erator<Dataset>(params) {}

      Status GetNext nternal( eratorContext* ctx,
                             std::vector<Tensor>* out_tensors,
                             bool* end_of_sequence) overr de {
        mutex_lock l(mu_);
        do {
          //   are currently process ng a f le, so try to read t  next record.
           f (reader_) {
            Tensor result_tensor(cpu_allocator(), DT_STR NG, {});
            Status s = reader_->ReadNext(&result_tensor.scalar<str ng>()());
             f (s.ok()) {
              out_tensors->emplace_back(std::move(result_tensor));
              *end_of_sequence = false;
              return Status::OK();
            } else  f (!errors:: sOutOfRange(s)) {
              return s;
            }

            //   have reac d t  end of t  current f le, so maybe
            // move on to next f le.
            reader_.reset();
            ++current_f le_ ndex_;
          }

          //  erat on ends w n t re are no more f les to process.
           f (current_f le_ ndex_ == dataset()->f lena s_.s ze()) {
            *end_of_sequence = true;
            return Status::OK();
          }

          // Actually move on to next f le.
          const str ng& next_f lena  =
              dataset()->f lena s_[current_f le_ ndex_];

          auto compress on_type = dataset()->compress on_type_;
           nt64 buffer_s ze = dataset()->buffer_s ze_;

           f (compress on_type == "auto") {
            compress on_type = getExtens on(next_f lena );
          }

           f (compress on_type != "gz" && compress on_type != "") {
            return errors:: nval dArgu nt("Unknown extens on: ", compress on_type);
          }

          tensorflow::Env* env = tensorflow::Env::Default();
          TF_CHECK_OK(env->NewRandomAccessF le(next_f lena , &f le_));

          // RandomAccess nputstream defaults t  second param to "false".
          // T  second para ter "false"  s t  key  ssue.
          // "false" assu s t  ownersh p of t  f le  s elsew re.
          // But mak ng that "true" causes segfaults down t  l ne.
          // So keep t  ownersh p of "f le_"  n t  class and clean up properly.
          f le_stream_.reset(new tensorflow:: o::RandomAccess nputStream(f le_.get(), false));

           f (compress on_type == "gz") {
            // unpack_stream does not take ownersh p of f le_stream_
# f !def ned(D SABLE_ZL B)
            unpack_stream_.reset(new tensorflow:: o::Zl b nputStream(
                                   f le_stream_.get(),
                                   buffer_s ze,
                                   buffer_s ze,
                                   tensorflow:: o::Zl bCompress onOpt ons::GZ P()));
            reader_.reset(new BlockFormatReader(unpack_stream_.get()));
#else
            return errors:: nval dArgu nt("l btwml comp led w hout zl b support");
#end f
          } else {
            unpack_stream_.reset(nullptr);
            reader_.reset(new BlockFormatReader(f le_stream_.get()));
          }
        } wh le (true);
      }

     pr vate:
      mutex mu_;
      u nt64_t current_f le_ ndex_ GUARDED_BY(mu_) = 0;
      std::un que_ptr<tensorflow::RandomAccessF le> f le_;
      std::un que_ptr<tensorflow:: o:: nputStream nterface> f le_stream_;
      std::un que_ptr<tensorflow:: o:: nputStream nterface> unpack_stream_;
      std::un que_ptr<BlockFormatReader> reader_ GUARDED_BY(mu_);
    };

    const std::str ng compress on_type_;
    const  nt64 buffer_s ze_;
    const std::vector<str ng> f lena s_;
  };
};

REG STER_KERNEL_BU LDER(
  Na ("BlockFormatDatasetV2")
  .Dev ce(DEV CE_CPU),
  BlockFormatDatasetV2);
