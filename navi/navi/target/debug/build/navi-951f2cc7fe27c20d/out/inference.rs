#[derive(Clone, PartialEq, ::prost::Message)]
pub struct ServerLiveRequest {}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct ServerLiveResponse {
    /// True if the inference server is live, false if not live.
    #[prost(bool, tag = "1")]
    pub live: bool,
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct ServerReadyRequest {}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct ServerReadyResponse {
    /// True if the inference server is ready, false if not ready.
    #[prost(bool, tag = "1")]
    pub ready: bool,
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct ModelReadyRequest {
    /// The name of the model to check for readiness.
    #[prost(string, tag = "1")]
    pub name: ::prost::alloc::string::String,
    /// The version of the model to check for readiness. If not given the
    /// server will choose a version based on the model and internal policy.
    #[prost(string, tag = "2")]
    pub version: ::prost::alloc::string::String,
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct ModelReadyResponse {
    /// True if the model is ready, false if not ready.
    #[prost(bool, tag = "1")]
    pub ready: bool,
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct ServerMetadataRequest {}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct ServerMetadataResponse {
    /// The server name.
    #[prost(string, tag = "1")]
    pub name: ::prost::alloc::string::String,
    /// The server version.
    #[prost(string, tag = "2")]
    pub version: ::prost::alloc::string::String,
    /// The extensions supported by the server.
    #[prost(string, repeated, tag = "3")]
    pub extensions: ::prost::alloc::vec::Vec<::prost::alloc::string::String>,
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct ModelMetadataRequest {
    /// The name of the model.
    #[prost(string, tag = "1")]
    pub name: ::prost::alloc::string::String,
    /// The version of the model to check for readiness. If not given the
    /// server will choose a version based on the model and internal policy.
    #[prost(string, tag = "2")]
    pub version: ::prost::alloc::string::String,
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct ModelMetadataResponse {
    /// The model name.
    #[prost(string, tag = "1")]
    pub name: ::prost::alloc::string::String,
    /// The versions of the model available on the server.
    #[prost(string, repeated, tag = "2")]
    pub versions: ::prost::alloc::vec::Vec<::prost::alloc::string::String>,
    /// The model's platform. See Platforms.
    #[prost(string, tag = "3")]
    pub platform: ::prost::alloc::string::String,
    /// The model's inputs.
    #[prost(message, repeated, tag = "4")]
    pub inputs: ::prost::alloc::vec::Vec<model_metadata_response::TensorMetadata>,
    /// The model's outputs.
    #[prost(message, repeated, tag = "5")]
    pub outputs: ::prost::alloc::vec::Vec<model_metadata_response::TensorMetadata>,
}
/// Nested message and enum types in `ModelMetadataResponse`.
pub mod model_metadata_response {
    /// Metadata for a tensor.
    #[derive(Clone, PartialEq, ::prost::Message)]
    pub struct TensorMetadata {
        /// The tensor name.
        #[prost(string, tag = "1")]
        pub name: ::prost::alloc::string::String,
        /// The tensor data type.
        #[prost(string, tag = "2")]
        pub datatype: ::prost::alloc::string::String,
        /// The tensor shape. A variable-size dimension is represented
        /// by a -1 value.
        #[prost(int64, repeated, tag = "3")]
        pub shape: ::prost::alloc::vec::Vec<i64>,
    }
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct ModelInferRequest {
    /// The name of the model to use for inferencing.
    #[prost(string, tag = "1")]
    pub model_name: ::prost::alloc::string::String,
    /// The version of the model to use for inference. If not given the
    /// server will choose a version based on the model and internal policy.
    #[prost(string, tag = "2")]
    pub model_version: ::prost::alloc::string::String,
    /// Optional identifier for the request. If specified will be
    /// returned in the response.
    #[prost(string, tag = "3")]
    pub id: ::prost::alloc::string::String,
    /// Optional inference parameters.
    #[prost(map = "string, message", tag = "4")]
    pub parameters: ::std::collections::HashMap<::prost::alloc::string::String, InferParameter>,
    /// The input tensors for the inference.
    #[prost(message, repeated, tag = "5")]
    pub inputs: ::prost::alloc::vec::Vec<model_infer_request::InferInputTensor>,
    /// The requested output tensors for the inference. Optional, if not
    /// specified all outputs produced by the model will be returned.
    #[prost(message, repeated, tag = "6")]
    pub outputs: ::prost::alloc::vec::Vec<model_infer_request::InferRequestedOutputTensor>,
    /// The data contained in an input tensor can be represented in "raw"
    /// bytes form or in the repeated type that matches the tensor's data
    /// type. To use the raw representation 'raw_input_contents' must be
    /// initialized with data for each tensor in the same order as
    /// 'inputs'. For each tensor, the size of this content must match
    /// what is expected by the tensor's shape and data type. The raw
    /// data must be the flattened, one-dimensional, row-major order of
    /// the tensor elements without any stride or padding between the
    /// elements. Note that the FP16 and BF16 data types must be represented as
    /// raw content as there is no specific data type for a 16-bit float type.
    ///
    /// If this field is specified then InferInputTensor::contents must
    /// not be specified for any input tensor.
    #[prost(bytes = "vec", repeated, tag = "7")]
    pub raw_input_contents: ::prost::alloc::vec::Vec<::prost::alloc::vec::Vec<u8>>,
}
/// Nested message and enum types in `ModelInferRequest`.
pub mod model_infer_request {
    /// An input tensor for an inference request.
    #[derive(Clone, PartialEq, ::prost::Message)]
    pub struct InferInputTensor {
        /// The tensor name.
        #[prost(string, tag = "1")]
        pub name: ::prost::alloc::string::String,
        /// The tensor data type.
        #[prost(string, tag = "2")]
        pub datatype: ::prost::alloc::string::String,
        /// The tensor shape.
        #[prost(int64, repeated, tag = "3")]
        pub shape: ::prost::alloc::vec::Vec<i64>,
        /// Optional inference input tensor parameters.
        #[prost(map = "string, message", tag = "4")]
        pub parameters:
            ::std::collections::HashMap<::prost::alloc::string::String, super::InferParameter>,
        /// The tensor contents using a data-type format. This field must
        /// not be specified if "raw" tensor contents are being used for
        /// the inference request.
        #[prost(message, optional, tag = "5")]
        pub contents: ::core::option::Option<super::InferTensorContents>,
    }
    /// An output tensor requested for an inference request.
    #[derive(Clone, PartialEq, ::prost::Message)]
    pub struct InferRequestedOutputTensor {
        /// The tensor name.
        #[prost(string, tag = "1")]
        pub name: ::prost::alloc::string::String,
        /// Optional requested output tensor parameters.
        #[prost(map = "string, message", tag = "2")]
        pub parameters:
            ::std::collections::HashMap<::prost::alloc::string::String, super::InferParameter>,
    }
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct ModelInferResponse {
    /// The name of the model used for inference.
    #[prost(string, tag = "1")]
    pub model_name: ::prost::alloc::string::String,
    /// The version of the model used for inference.
    #[prost(string, tag = "2")]
    pub model_version: ::prost::alloc::string::String,
    /// The id of the inference request if one was specified.
    #[prost(string, tag = "3")]
    pub id: ::prost::alloc::string::String,
    /// Optional inference response parameters.
    #[prost(map = "string, message", tag = "4")]
    pub parameters: ::std::collections::HashMap<::prost::alloc::string::String, InferParameter>,
    /// The output tensors holding inference results.
    #[prost(message, repeated, tag = "5")]
    pub outputs: ::prost::alloc::vec::Vec<model_infer_response::InferOutputTensor>,
    /// The data contained in an output tensor can be represented in
    /// "raw" bytes form or in the repeated type that matches the
    /// tensor's data type. To use the raw representation 'raw_output_contents'
    /// must be initialized with data for each tensor in the same order as
    /// 'outputs'. For each tensor, the size of this content must match
    /// what is expected by the tensor's shape and data type. The raw
    /// data must be the flattened, one-dimensional, row-major order of
    /// the tensor elements without any stride or padding between the
    /// elements. Note that the FP16 and BF16 data types must be represented as
    /// raw content as there is no specific data type for a 16-bit float type.
    ///
    /// If this field is specified then InferOutputTensor::contents must
    /// not be specified for any output tensor.
    #[prost(bytes = "vec", repeated, tag = "6")]
    pub raw_output_contents: ::prost::alloc::vec::Vec<::prost::alloc::vec::Vec<u8>>,
}
/// Nested message and enum types in `ModelInferResponse`.
pub mod model_infer_response {
    /// An output tensor returned for an inference request.
    #[derive(Clone, PartialEq, ::prost::Message)]
    pub struct InferOutputTensor {
        /// The tensor name.
        #[prost(string, tag = "1")]
        pub name: ::prost::alloc::string::String,
        /// The tensor data type.
        #[prost(string, tag = "2")]
        pub datatype: ::prost::alloc::string::String,
        /// The tensor shape.
        #[prost(int64, repeated, tag = "3")]
        pub shape: ::prost::alloc::vec::Vec<i64>,
        /// Optional output tensor parameters.
        #[prost(map = "string, message", tag = "4")]
        pub parameters:
            ::std::collections::HashMap<::prost::alloc::string::String, super::InferParameter>,
        /// The tensor contents using a data-type format. This field must
        /// not be specified if "raw" tensor contents are being used for
        /// the inference response.
        #[prost(message, optional, tag = "5")]
        pub contents: ::core::option::Option<super::InferTensorContents>,
    }
}
/// An inference parameter value. The Parameters message describes a
/// “name”/”value” pair, where the “name” is the name of the parameter
/// and the “value” is a boolean, integer, or string corresponding to
/// the parameter.
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct InferParameter {
    /// The parameter value can be a string, an int64, a boolean
    /// or a message specific to a predefined parameter.
    #[prost(oneof = "infer_parameter::ParameterChoice", tags = "1, 2, 3")]
    pub parameter_choice: ::core::option::Option<infer_parameter::ParameterChoice>,
}
/// Nested message and enum types in `InferParameter`.
pub mod infer_parameter {
    /// The parameter value can be a string, an int64, a boolean
    /// or a message specific to a predefined parameter.
    #[derive(Clone, PartialEq, ::prost::Oneof)]
    pub enum ParameterChoice {
        /// A boolean parameter value.
        #[prost(bool, tag = "1")]
        BoolParam(bool),
        /// An int64 parameter value.
        #[prost(int64, tag = "2")]
        Int64Param(i64),
        /// A string parameter value.
        #[prost(string, tag = "3")]
        StringParam(::prost::alloc::string::String),
    }
}
/// The data contained in a tensor represented by the repeated type
/// that matches the tensor's data type. Protobuf oneof is not used
/// because oneofs cannot contain repeated fields.
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct InferTensorContents {
    /// Representation for BOOL data type. The size must match what is
    /// expected by the tensor's shape. The contents must be the flattened,
    /// one-dimensional, row-major order of the tensor elements.
    #[prost(bool, repeated, tag = "1")]
    pub bool_contents: ::prost::alloc::vec::Vec<bool>,
    /// Representation for INT8, INT16, and INT32 data types. The size
    /// must match what is expected by the tensor's shape. The contents
    /// must be the flattened, one-dimensional, row-major order of the
    /// tensor elements.
    #[prost(int32, repeated, tag = "2")]
    pub int_contents: ::prost::alloc::vec::Vec<i32>,
    /// Representation for INT64 data types. The size must match what
    /// is expected by the tensor's shape. The contents must be the
    /// flattened, one-dimensional, row-major order of the tensor elements.
    #[prost(int64, repeated, tag = "3")]
    pub int64_contents: ::prost::alloc::vec::Vec<i64>,
    /// Representation for UINT8, UINT16, and UINT32 data types. The size
    /// must match what is expected by the tensor's shape. The contents
    /// must be the flattened, one-dimensional, row-major order of the
    /// tensor elements.
    #[prost(uint32, repeated, tag = "4")]
    pub uint_contents: ::prost::alloc::vec::Vec<u32>,
    /// Representation for UINT64 data types. The size must match what
    /// is expected by the tensor's shape. The contents must be the
    /// flattened, one-dimensional, row-major order of the tensor elements.
    #[prost(uint64, repeated, tag = "5")]
    pub uint64_contents: ::prost::alloc::vec::Vec<u64>,
    /// Representation for FP32 data type. The size must match what is
    /// expected by the tensor's shape. The contents must be the flattened,
    /// one-dimensional, row-major order of the tensor elements.
    #[prost(float, repeated, tag = "6")]
    pub fp32_contents: ::prost::alloc::vec::Vec<f32>,
    /// Representation for FP64 data type. The size must match what is
    /// expected by the tensor's shape. The contents must be the flattened,
    /// one-dimensional, row-major order of the tensor elements.
    #[prost(double, repeated, tag = "7")]
    pub fp64_contents: ::prost::alloc::vec::Vec<f64>,
    /// Representation for BYTES data type. The size must match what is
    /// expected by the tensor's shape. The contents must be the flattened,
    /// one-dimensional, row-major order of the tensor elements.
    #[prost(bytes = "vec", repeated, tag = "8")]
    pub bytes_contents: ::prost::alloc::vec::Vec<::prost::alloc::vec::Vec<u8>>,
}
#[doc = r" Generated client implementations."]
pub mod grpc_inference_service_client {
    #![allow(unused_variables, dead_code, missing_docs, clippy::let_unit_value)]
    use tonic::codegen::*;
    #[doc = " Inference Server GRPC endpoints."]
    #[derive(Debug, Clone)]
    pub struct GrpcInferenceServiceClient<T> {
        inner: tonic::client::Grpc<T>,
    }
    impl GrpcInferenceServiceClient<tonic::transport::Channel> {
        #[doc = r" Attempt to create a new client by connecting to a given endpoint."]
        pub async fn connect<D>(dst: D) -> Result<Self, tonic::transport::Error>
        where
            D: std::convert::TryInto<tonic::transport::Endpoint>,
            D::Error: Into<StdError>,
        {
            let conn = tonic::transport::Endpoint::new(dst)?.connect().await?;
            Ok(Self::new(conn))
        }
    }
    impl<T> GrpcInferenceServiceClient<T>
    where
        T: tonic::client::GrpcService<tonic::body::BoxBody>,
        T::ResponseBody: Body + Send + 'static,
        T::Error: Into<StdError>,
        <T::ResponseBody as Body>::Error: Into<StdError> + Send,
    {
        pub fn new(inner: T) -> Self {
            let inner = tonic::client::Grpc::new(inner);
            Self { inner }
        }
        pub fn with_interceptor<F>(
            inner: T,
            interceptor: F,
        ) -> GrpcInferenceServiceClient<InterceptedService<T, F>>
        where
            F: tonic::service::Interceptor,
            T: tonic::codegen::Service<
                http::Request<tonic::body::BoxBody>,
                Response = http::Response<
                    <T as tonic::client::GrpcService<tonic::body::BoxBody>>::ResponseBody,
                >,
            >,
            <T as tonic::codegen::Service<http::Request<tonic::body::BoxBody>>>::Error:
                Into<StdError> + Send + Sync,
        {
            GrpcInferenceServiceClient::new(InterceptedService::new(inner, interceptor))
        }
        #[doc = r" Compress requests with `gzip`."]
        #[doc = r""]
        #[doc = r" This requires the server to support it otherwise it might respond with an"]
        #[doc = r" error."]
        pub fn send_gzip(mut self) -> Self {
            self.inner = self.inner.send_gzip();
            self
        }
        #[doc = r" Enable decompressing responses with `gzip`."]
        pub fn accept_gzip(mut self) -> Self {
            self.inner = self.inner.accept_gzip();
            self
        }
        #[doc = " The ServerLive API indicates if the inference server is able to receive "]
        #[doc = " and respond to metadata and inference requests."]
        pub async fn server_live(
            &mut self,
            request: impl tonic::IntoRequest<super::ServerLiveRequest>,
        ) -> Result<tonic::Response<super::ServerLiveResponse>, tonic::Status> {
            self.inner.ready().await.map_err(|e| {
                tonic::Status::new(
                    tonic::Code::Unknown,
                    format!("Service was not ready: {}", e.into()),
                )
            })?;
            let codec = tonic::codec::ProstCodec::default();
            let path =
                http::uri::PathAndQuery::from_static("/inference.GRPCInferenceService/ServerLive");
            self.inner.unary(request.into_request(), path, codec).await
        }
        #[doc = " The ServerReady API indicates if the server is ready for inferencing."]
        pub async fn server_ready(
            &mut self,
            request: impl tonic::IntoRequest<super::ServerReadyRequest>,
        ) -> Result<tonic::Response<super::ServerReadyResponse>, tonic::Status> {
            self.inner.ready().await.map_err(|e| {
                tonic::Status::new(
                    tonic::Code::Unknown,
                    format!("Service was not ready: {}", e.into()),
                )
            })?;
            let codec = tonic::codec::ProstCodec::default();
            let path =
                http::uri::PathAndQuery::from_static("/inference.GRPCInferenceService/ServerReady");
            self.inner.unary(request.into_request(), path, codec).await
        }
        #[doc = " The ModelReady API indicates if a specific model is ready for inferencing."]
        pub async fn model_ready(
            &mut self,
            request: impl tonic::IntoRequest<super::ModelReadyRequest>,
        ) -> Result<tonic::Response<super::ModelReadyResponse>, tonic::Status> {
            self.inner.ready().await.map_err(|e| {
                tonic::Status::new(
                    tonic::Code::Unknown,
                    format!("Service was not ready: {}", e.into()),
                )
            })?;
            let codec = tonic::codec::ProstCodec::default();
            let path =
                http::uri::PathAndQuery::from_static("/inference.GRPCInferenceService/ModelReady");
            self.inner.unary(request.into_request(), path, codec).await
        }
        #[doc = " The ServerMetadata API provides information about the server. Errors are "]
        #[doc = " indicated by the google.rpc.Status returned for the request. The OK code "]
        #[doc = " indicates success and other codes indicate failure."]
        pub async fn server_metadata(
            &mut self,
            request: impl tonic::IntoRequest<super::ServerMetadataRequest>,
        ) -> Result<tonic::Response<super::ServerMetadataResponse>, tonic::Status> {
            self.inner.ready().await.map_err(|e| {
                tonic::Status::new(
                    tonic::Code::Unknown,
                    format!("Service was not ready: {}", e.into()),
                )
            })?;
            let codec = tonic::codec::ProstCodec::default();
            let path = http::uri::PathAndQuery::from_static(
                "/inference.GRPCInferenceService/ServerMetadata",
            );
            self.inner.unary(request.into_request(), path, codec).await
        }
        #[doc = " The per-model metadata API provides information about a model. Errors are "]
        #[doc = " indicated by the google.rpc.Status returned for the request. The OK code "]
        #[doc = " indicates success and other codes indicate failure."]
        pub async fn model_metadata(
            &mut self,
            request: impl tonic::IntoRequest<super::ModelMetadataRequest>,
        ) -> Result<tonic::Response<super::ModelMetadataResponse>, tonic::Status> {
            self.inner.ready().await.map_err(|e| {
                tonic::Status::new(
                    tonic::Code::Unknown,
                    format!("Service was not ready: {}", e.into()),
                )
            })?;
            let codec = tonic::codec::ProstCodec::default();
            let path = http::uri::PathAndQuery::from_static(
                "/inference.GRPCInferenceService/ModelMetadata",
            );
            self.inner.unary(request.into_request(), path, codec).await
        }
        #[doc = " The ModelInfer API performs inference using the specified model. Errors are"]
        #[doc = " indicated by the google.rpc.Status returned for the request. The OK code "]
        #[doc = " indicates success and other codes indicate failure."]
        pub async fn model_infer(
            &mut self,
            request: impl tonic::IntoRequest<super::ModelInferRequest>,
        ) -> Result<tonic::Response<super::ModelInferResponse>, tonic::Status> {
            self.inner.ready().await.map_err(|e| {
                tonic::Status::new(
                    tonic::Code::Unknown,
                    format!("Service was not ready: {}", e.into()),
                )
            })?;
            let codec = tonic::codec::ProstCodec::default();
            let path =
                http::uri::PathAndQuery::from_static("/inference.GRPCInferenceService/ModelInfer");
            self.inner.unary(request.into_request(), path, codec).await
        }
    }
}
#[doc = r" Generated server implementations."]
pub mod grpc_inference_service_server {
    #![allow(unused_variables, dead_code, missing_docs, clippy::let_unit_value)]
    use tonic::codegen::*;
    #[doc = "Generated trait containing gRPC methods that should be implemented for use with GrpcInferenceServiceServer."]
    #[async_trait]
    pub trait GrpcInferenceService: Send + Sync + 'static {
        #[doc = " The ServerLive API indicates if the inference server is able to receive "]
        #[doc = " and respond to metadata and inference requests."]
        async fn server_live(
            &self,
            request: tonic::Request<super::ServerLiveRequest>,
        ) -> Result<tonic::Response<super::ServerLiveResponse>, tonic::Status>;
        #[doc = " The ServerReady API indicates if the server is ready for inferencing."]
        async fn server_ready(
            &self,
            request: tonic::Request<super::ServerReadyRequest>,
        ) -> Result<tonic::Response<super::ServerReadyResponse>, tonic::Status>;
        #[doc = " The ModelReady API indicates if a specific model is ready for inferencing."]
        async fn model_ready(
            &self,
            request: tonic::Request<super::ModelReadyRequest>,
        ) -> Result<tonic::Response<super::ModelReadyResponse>, tonic::Status>;
        #[doc = " The ServerMetadata API provides information about the server. Errors are "]
        #[doc = " indicated by the google.rpc.Status returned for the request. The OK code "]
        #[doc = " indicates success and other codes indicate failure."]
        async fn server_metadata(
            &self,
            request: tonic::Request<super::ServerMetadataRequest>,
        ) -> Result<tonic::Response<super::ServerMetadataResponse>, tonic::Status>;
        #[doc = " The per-model metadata API provides information about a model. Errors are "]
        #[doc = " indicated by the google.rpc.Status returned for the request. The OK code "]
        #[doc = " indicates success and other codes indicate failure."]
        async fn model_metadata(
            &self,
            request: tonic::Request<super::ModelMetadataRequest>,
        ) -> Result<tonic::Response<super::ModelMetadataResponse>, tonic::Status>;
        #[doc = " The ModelInfer API performs inference using the specified model. Errors are"]
        #[doc = " indicated by the google.rpc.Status returned for the request. The OK code "]
        #[doc = " indicates success and other codes indicate failure."]
        async fn model_infer(
            &self,
            request: tonic::Request<super::ModelInferRequest>,
        ) -> Result<tonic::Response<super::ModelInferResponse>, tonic::Status>;
    }
    #[doc = " Inference Server GRPC endpoints."]
    #[derive(Debug)]
    pub struct GrpcInferenceServiceServer<T: GrpcInferenceService> {
        inner: _Inner<T>,
        accept_compression_encodings: EnabledCompressionEncodings,
        send_compression_encodings: EnabledCompressionEncodings,
    }
    struct _Inner<T>(Arc<T>);
    impl<T: GrpcInferenceService> GrpcInferenceServiceServer<T> {
        pub fn new(inner: T) -> Self {
            let inner = Arc::new(inner);
            let inner = _Inner(inner);
            Self {
                inner,
                accept_compression_encodings: Default::default(),
                send_compression_encodings: Default::default(),
            }
        }
        pub fn with_interceptor<F>(inner: T, interceptor: F) -> InterceptedService<Self, F>
        where
            F: tonic::service::Interceptor,
        {
            InterceptedService::new(Self::new(inner), interceptor)
        }
        #[doc = r" Enable decompressing requests with `gzip`."]
        pub fn accept_gzip(mut self) -> Self {
            self.accept_compression_encodings.enable_gzip();
            self
        }
        #[doc = r" Compress responses with `gzip`, if the client supports it."]
        pub fn send_gzip(mut self) -> Self {
            self.send_compression_encodings.enable_gzip();
            self
        }
    }
    impl<T, B> tonic::codegen::Service<http::Request<B>> for GrpcInferenceServiceServer<T>
    where
        T: GrpcInferenceService,
        B: Body + Send + 'static,
        B::Error: Into<StdError> + Send + 'static,
    {
        type Response = http::Response<tonic::body::BoxBody>;
        type Error = Never;
        type Future = BoxFuture<Self::Response, Self::Error>;
        fn poll_ready(&mut self, _cx: &mut Context<'_>) -> Poll<Result<(), Self::Error>> {
            Poll::Ready(Ok(()))
        }
        fn call(&mut self, req: http::Request<B>) -> Self::Future {
            let inner = self.inner.clone();
            match req.uri().path() {
                "/inference.GRPCInferenceService/ServerLive" => {
                    #[allow(non_camel_case_types)]
                    struct ServerLiveSvc<T: GrpcInferenceService>(pub Arc<T>);
                    impl<T: GrpcInferenceService>
                        tonic::server::UnaryService<super::ServerLiveRequest> for ServerLiveSvc<T>
                    {
                        type Response = super::ServerLiveResponse;
                        type Future = BoxFuture<tonic::Response<Self::Response>, tonic::Status>;
                        fn call(
                            &mut self,
                            request: tonic::Request<super::ServerLiveRequest>,
                        ) -> Self::Future {
                            let inner = self.0.clone();
                            let fut = async move { (*inner).server_live(request).await };
                            Box::pin(fut)
                        }
                    }
                    let accept_compression_encodings = self.accept_compression_encodings;
                    let send_compression_encodings = self.send_compression_encodings;
                    let inner = self.inner.clone();
                    let fut = async move {
                        let inner = inner.0;
                        let method = ServerLiveSvc(inner);
                        let codec = tonic::codec::ProstCodec::default();
                        let mut grpc = tonic::server::Grpc::new(codec).apply_compression_config(
                            accept_compression_encodings,
                            send_compression_encodings,
                        );
                        let res = grpc.unary(method, req).await;
                        Ok(res)
                    };
                    Box::pin(fut)
                }
                "/inference.GRPCInferenceService/ServerReady" => {
                    #[allow(non_camel_case_types)]
                    struct ServerReadySvc<T: GrpcInferenceService>(pub Arc<T>);
                    impl<T: GrpcInferenceService>
                        tonic::server::UnaryService<super::ServerReadyRequest>
                        for ServerReadySvc<T>
                    {
                        type Response = super::ServerReadyResponse;
                        type Future = BoxFuture<tonic::Response<Self::Response>, tonic::Status>;
                        fn call(
                            &mut self,
                            request: tonic::Request<super::ServerReadyRequest>,
                        ) -> Self::Future {
                            let inner = self.0.clone();
                            let fut = async move { (*inner).server_ready(request).await };
                            Box::pin(fut)
                        }
                    }
                    let accept_compression_encodings = self.accept_compression_encodings;
                    let send_compression_encodings = self.send_compression_encodings;
                    let inner = self.inner.clone();
                    let fut = async move {
                        let inner = inner.0;
                        let method = ServerReadySvc(inner);
                        let codec = tonic::codec::ProstCodec::default();
                        let mut grpc = tonic::server::Grpc::new(codec).apply_compression_config(
                            accept_compression_encodings,
                            send_compression_encodings,
                        );
                        let res = grpc.unary(method, req).await;
                        Ok(res)
                    };
                    Box::pin(fut)
                }
                "/inference.GRPCInferenceService/ModelReady" => {
                    #[allow(non_camel_case_types)]
                    struct ModelReadySvc<T: GrpcInferenceService>(pub Arc<T>);
                    impl<T: GrpcInferenceService>
                        tonic::server::UnaryService<super::ModelReadyRequest> for ModelReadySvc<T>
                    {
                        type Response = super::ModelReadyResponse;
                        type Future = BoxFuture<tonic::Response<Self::Response>, tonic::Status>;
                        fn call(
                            &mut self,
                            request: tonic::Request<super::ModelReadyRequest>,
                        ) -> Self::Future {
                            let inner = self.0.clone();
                            let fut = async move { (*inner).model_ready(request).await };
                            Box::pin(fut)
                        }
                    }
                    let accept_compression_encodings = self.accept_compression_encodings;
                    let send_compression_encodings = self.send_compression_encodings;
                    let inner = self.inner.clone();
                    let fut = async move {
                        let inner = inner.0;
                        let method = ModelReadySvc(inner);
                        let codec = tonic::codec::ProstCodec::default();
                        let mut grpc = tonic::server::Grpc::new(codec).apply_compression_config(
                            accept_compression_encodings,
                            send_compression_encodings,
                        );
                        let res = grpc.unary(method, req).await;
                        Ok(res)
                    };
                    Box::pin(fut)
                }
                "/inference.GRPCInferenceService/ServerMetadata" => {
                    #[allow(non_camel_case_types)]
                    struct ServerMetadataSvc<T: GrpcInferenceService>(pub Arc<T>);
                    impl<T: GrpcInferenceService>
                        tonic::server::UnaryService<super::ServerMetadataRequest>
                        for ServerMetadataSvc<T>
                    {
                        type Response = super::ServerMetadataResponse;
                        type Future = BoxFuture<tonic::Response<Self::Response>, tonic::Status>;
                        fn call(
                            &mut self,
                            request: tonic::Request<super::ServerMetadataRequest>,
                        ) -> Self::Future {
                            let inner = self.0.clone();
                            let fut = async move { (*inner).server_metadata(request).await };
                            Box::pin(fut)
                        }
                    }
                    let accept_compression_encodings = self.accept_compression_encodings;
                    let send_compression_encodings = self.send_compression_encodings;
                    let inner = self.inner.clone();
                    let fut = async move {
                        let inner = inner.0;
                        let method = ServerMetadataSvc(inner);
                        let codec = tonic::codec::ProstCodec::default();
                        let mut grpc = tonic::server::Grpc::new(codec).apply_compression_config(
                            accept_compression_encodings,
                            send_compression_encodings,
                        );
                        let res = grpc.unary(method, req).await;
                        Ok(res)
                    };
                    Box::pin(fut)
                }
                "/inference.GRPCInferenceService/ModelMetadata" => {
                    #[allow(non_camel_case_types)]
                    struct ModelMetadataSvc<T: GrpcInferenceService>(pub Arc<T>);
                    impl<T: GrpcInferenceService>
                        tonic::server::UnaryService<super::ModelMetadataRequest>
                        for ModelMetadataSvc<T>
                    {
                        type Response = super::ModelMetadataResponse;
                        type Future = BoxFuture<tonic::Response<Self::Response>, tonic::Status>;
                        fn call(
                            &mut self,
                            request: tonic::Request<super::ModelMetadataRequest>,
                        ) -> Self::Future {
                            let inner = self.0.clone();
                            let fut = async move { (*inner).model_metadata(request).await };
                            Box::pin(fut)
                        }
                    }
                    let accept_compression_encodings = self.accept_compression_encodings;
                    let send_compression_encodings = self.send_compression_encodings;
                    let inner = self.inner.clone();
                    let fut = async move {
                        let inner = inner.0;
                        let method = ModelMetadataSvc(inner);
                        let codec = tonic::codec::ProstCodec::default();
                        let mut grpc = tonic::server::Grpc::new(codec).apply_compression_config(
                            accept_compression_encodings,
                            send_compression_encodings,
                        );
                        let res = grpc.unary(method, req).await;
                        Ok(res)
                    };
                    Box::pin(fut)
                }
                "/inference.GRPCInferenceService/ModelInfer" => {
                    #[allow(non_camel_case_types)]
                    struct ModelInferSvc<T: GrpcInferenceService>(pub Arc<T>);
                    impl<T: GrpcInferenceService>
                        tonic::server::UnaryService<super::ModelInferRequest> for ModelInferSvc<T>
                    {
                        type Response = super::ModelInferResponse;
                        type Future = BoxFuture<tonic::Response<Self::Response>, tonic::Status>;
                        fn call(
                            &mut self,
                            request: tonic::Request<super::ModelInferRequest>,
                        ) -> Self::Future {
                            let inner = self.0.clone();
                            let fut = async move { (*inner).model_infer(request).await };
                            Box::pin(fut)
                        }
                    }
                    let accept_compression_encodings = self.accept_compression_encodings;
                    let send_compression_encodings = self.send_compression_encodings;
                    let inner = self.inner.clone();
                    let fut = async move {
                        let inner = inner.0;
                        let method = ModelInferSvc(inner);
                        let codec = tonic::codec::ProstCodec::default();
                        let mut grpc = tonic::server::Grpc::new(codec).apply_compression_config(
                            accept_compression_encodings,
                            send_compression_encodings,
                        );
                        let res = grpc.unary(method, req).await;
                        Ok(res)
                    };
                    Box::pin(fut)
                }
                _ => Box::pin(async move {
                    Ok(http::Response::builder()
                        .status(200)
                        .header("grpc-status", "12")
                        .header("content-type", "application/grpc")
                        .body(empty_body())
                        .unwrap())
                }),
            }
        }
    }
    impl<T: GrpcInferenceService> Clone for GrpcInferenceServiceServer<T> {
        fn clone(&self) -> Self {
            let inner = self.inner.clone();
            Self {
                inner,
                accept_compression_encodings: self.accept_compression_encodings,
                send_compression_encodings: self.send_compression_encodings,
            }
        }
    }
    impl<T: GrpcInferenceService> Clone for _Inner<T> {
        fn clone(&self) -> Self {
            Self(self.0.clone())
        }
    }
    impl<T: std::fmt::Debug> std::fmt::Debug for _Inner<T> {
        fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
            write!(f, "{:?}", self.0)
        }
    }
    impl<T: GrpcInferenceService> tonic::transport::NamedService for GrpcInferenceServiceServer<T> {
        const NAME: &'static str = "inference.GRPCInferenceService";
    }
}
