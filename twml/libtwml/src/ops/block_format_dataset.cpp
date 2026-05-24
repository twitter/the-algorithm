#include "block_format_reader.h"

#include "tensorflow/core/framework/dataset.h"
#include "tensorflow/core/framework/partial_tensor_shape.h"
#include "tensorflow/core/framework/tensor.h"
#include "tensorflow/core/lib/io/random_inputstream.h"

#if !defined(DISABLE_ZLIB)
#include "tensorflow/core/lib/io/zlib_inputstream.h"
#endif

#include <twml.h>

#include <cstdio>
#include <algorithm>
#include <iterator>

using namespace tensorflow;


inline std::string stripPath(std::string const &file_name) {
  const auto pos = file_name.find_last_of("/");
  if (pos == std::string::npos) return file_name;
  return file_name.substr(pos + 1);
}

inline std::string getExtension(std::string const &file_name) {
  const auto stripped_file_name = stripPath(file_name);
  const auto pos = stripPath(stripped_file_name).find_last_of(".");
  if (pos == std::string::npos) return "";
  return stripped_file_name.substr(pos + 1);
}

REGISTER_OP("BlockFormatDatasetV2")
.Input("filenames: string")
.Input("compression_type: string")
.Input("buffer_size: int64")
.Output("handle: variant")
.SetIsStateful()
.SetShapeFn(shape_inference::ScalarShape)
.Doc(R"doc(

Creates a dataset for streaming BlockFormat data in compressed (e.g. gzip), uncompressed formats.
This op also has the ability stream a dataset containing files from multiple formats mentioned above.

filenames: A scalar or vector containing the name(s) of the file(s) to be read.
compression_type: A scalar string denoting the compression type. Can be 'none', 'zlib', 'auto'.
buffer_size: A scalar denoting the buffer size to use during decompression.

Outputs
  handle: A handle to the dataset. This handle is later used to create an iterator to stream the data from the dataset.

)doc");


class BlockFormatDatasetV2 : public DatasetOpKernel {
 public:
  using DatasetOpKernel::DatasetOpKernel;

  void MakeDataset(OpKernelContext* ctx, DatasetBase **output) override {
    const Tensor* filenames_tensor;
    OP_REQUIRES_OK(ctx, ctx->input("filenames", &filenames_tensor));
    OP_REQUIRES(
        ctx, filenames_tensor->dims() <= 1,
        errors::InvalidArgument("`filenames` must be a scalar or a vector."));

    const auto filenames_flat = filenames_tensor->flat<string>();
    const int64 num_files = filenames_tensor->NumElements();
    std::vector<string> filenames;
    filenames.reserve(num_files);
    std::copy(filenames_flat.data(),
              filenames_flat.data() + num_files,
              std::back_inserter(filenames));

    string compression_type;
    OP_REQUIRES_OK(
        ctx, tensorflow::data::ParseScalarArgument<string>(
            ctx, "compression_type", &compression_type));

    int64 buffer_size = -1;
    OP_REQUIRES_OK(
        ctx, tensorflow::data::ParseScalarArgument<int64>(
            ctx, "buffer_size", &buffer_size));

    OP_REQUIRES(ctx, buffer_size >= 0,
                errors::InvalidArgument(
                    "`buffer_size` must be >= 0 (0 == no buffering)"));

    OP_REQUIRES(ctx,
                compression_type == "auto" ||
                compression_type == "gz" ||
                compression_type == "",
                errors::InvalidArgument("Unknown extension: ", compression_type));

    *output = new Dataset(ctx, std::move(filenames), compression_type, buffer_size);
  }

 private:
  class Dataset : public DatasetBase {
   public:
    Dataset(OpKernelContext* ctx,
            std::vector<string> filenames,
            std::string compression_type,
            int64 buffer_size)
        : DatasetBase(DatasetContext(ctx)),
          compression_type_(compression_type),
          buffer_size_(buffer_size),
          filenames_(std::move(filenames))
    {}

    const DataTypeVector& output_dtypes() const override {
      static DataTypeVector* dtypes = new DataTypeVector({DT_STRING});
      return *dtypes;
    }

    const std::vector<PartialTensorShape>& output_shapes() const override {
      static std::vector<PartialTensorShape>* shapes =
          new std::vector<PartialTensorShape>({{}});
      return *shapes;
    }

    string DebugString() const override { return "BlockFormatDatasetV2::Dataset"; }

   protected:
    Status AsGraphDefInternal(SerializationContext* ctx,
                              DatasetGraphDefBuilder* b,
                              Node** output) const override {
      Node* filenames = nullptr;
      Node* compression_type = nullptr;
      Node* buffer_size = nullptr;
      TF_RETURN_IF_ERROR(b->AddVector(filenames_, &filenames));
      TF_RETURN_IF_ERROR(b->AddScalar(compression_type_, &compression_type));
      TF_RETURN_IF_ERROR(
          b->AddScalar(buffer_size_, &buffer_size));
      TF_RETURN_IF_ERROR(b->AddDataset(
          this, {filenames, compression_type, buffer_size}, output));
      return Status::OK();
    }

   private:
    std::unique_ptr<IteratorBase> MakeIteratorInternal(
        const string& prefix) const override {
      return std::unique_ptr<IteratorBase>(
          new Iterator({this, strings::StrCat(prefix, "::BlockFormat")}));
    }

    class Iterator : public DatasetIterator<Dataset> {
     public:
      explicit Iterator(const Params &params)
          : DatasetIterator<Dataset>(params) {}

      Status GetNextInternal(IteratorContext* ctx,
                             std::vector<Tensor>* out_tensors,
                             bool* end_of_sequence) override {
        mutex_lock l(mu_);
        do {
          // We are currently processing a file, so try to read the next record.
          if (reader_) {
            Tensor result_tensor(cpu_allocator(), DT_STRING, {});
            Status s = reader_->ReadNext(&result_tensor.scalar<string>()());
            if (s.ok()) {
              out_tensors->emplace_back(std::move(result_tensor));
              *end_of_sequence = false;
              return Status::OK();
            } else if (!errors::IsOutOfRange(s)) {
              return s;
            }

            // We have reached the end of the current file, so maybe
            // move on to next file.
            reader_.reset();
            ++current_file_index_;
          }

          // Iteration ends when there are no more files to process.
          if (current_file_index_ == dataset()->filenames_.size()) {
            *end_of_sequence = true;
            return Status::OK();
          }

          // Actually move on to next file.
          const string& next_filename =
              dataset()->filenames_[current_file_index_];

          auto compression_type = dataset()->compression_type_;
          int64 buffer_size = dataset()->buffer_size_;

          if (compression_type == "auto") {
            compression_type = getExtension(next_filename);
          }

          if (compression_type != "gz" && compression_type != "") {
            return errors::InvalidArgument("Unknown extension: ", compression_type);
          }

          tensorflow::Env* env = tensorflow::Env::Default();
          TF_CHECK_OK(env->NewRandomAccessFile(next_filename, &file_));

          // RandomAccessInputstream defaults the second param to "false".
          // The second parameter "false" is the key issue.
          // "false" assumes the ownership of the file is elsewhere.
          // But making that "true" causes segfaults down the line.
          // So keep the ownership of "file_" in this class and clean up properly.
          file_stream_.reset(new tensorflow::io::RandomAccessInputStream(file_.get(), false));

          if (compression_type == "gz") {
            // unpack_stream does not take ownership of file_stream_
#if !defined(DISABLE_ZLIB)
            unpack_stream_.reset(new tensorflow::io::ZlibInputStream(
                                   file_stream_.get(),
                                   buffer_size,
                                   buffer_size,
                                   tensorflow::io::ZlibCompressionOptions::GZIP()));
            reader_.reset(new BlockFormatReader(unpack_stream_.get()));
#else
            return errors::InvalidArgument("libtwml compiled without zlib support");
#endif
          } else {
            unpack_stream_.reset(nullptr);
            reader_.reset(new BlockFormatReader(file_stream_.get()));
          }
        } while (true);
      }

     private:
      mutex mu_;
      uint64_t current_file_index_ GUARDED_BY(mu_) = 0;
      std::unique_ptr<tensorflow::RandomAccessFile> file_;
      std::unique_ptr<tensorflow::io::InputStreamInterface> file_stream_;
      std::unique_ptr<tensorflow::io::InputStreamInterface> unpack_stream_;
      std::unique_ptr<BlockFormatReader> reader_ GUARDED_BY(mu_);
    };

    const std::string compression_type_;
    const int64 buffer_size_;
    const std::vector<string> filenames_;
  };
};

REGISTER_KERNEL_BUILDER(
  Name("BlockFormatDatasetV2")
  .Device(DEVICE_CPU),
  BlockFormatDatasetV2);
