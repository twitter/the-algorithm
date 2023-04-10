/// Specifies one or more fully independent input Examples.
/// See examples at:
///     <https://github.com/tensorflow/tensorflow/blob/master/tensorflow/core/example/example.proto>
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct ExampleList {
    #[prost(message, repeated, tag = "1")]
    pub examples: ::prost::alloc::vec::Vec<super::Example>,
}
/// Specifies one or more independent input Examples, with a common context
/// Example.
///
/// The common use case for context is to cleanly and optimally specify some
/// features that are common across multiple examples.
///
/// See example below with a search query as the context and multiple restaurants
/// to perform some inference on.
///
/// context: {
///   features: {
///     feature: {
///       key  : "query"
///       value: {
///         bytes_list: {
///           value: [ "pizza" ]
///         }
///       }
///     }
///   }
/// }
/// examples: {
///   features: {
///     feature: {
///       key  : "cuisine"
///       value: {
///         bytes_list: {
///           value: [ "Pizzeria" ]
///         }
///       }
///     }
///   }
/// }
/// examples: {
///   features: {
///     feature: {
///       key  : "cuisine"
///       value: {
///         bytes_list: {
///           value: [ "Taqueria" ]
///         }
///       }
///     }
///   }
/// }
///
/// Implementations of ExampleListWithContext merge the context Example into each
/// of the Examples. Note that feature keys must not be duplicated between the
/// Examples and context Example, or the behavior is undefined.
///
/// See also:
///     tensorflow/core/example/example.proto
///     <https://developers.google.com/protocol-buffers/docs/proto3#maps>
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct ExampleListWithContext {
    #[prost(message, repeated, tag = "1")]
    pub examples: ::prost::alloc::vec::Vec<super::Example>,
    #[prost(message, optional, tag = "2")]
    pub context: ::core::option::Option<super::Example>,
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct Input {
    #[prost(oneof = "input::Kind", tags = "1, 2")]
    pub kind: ::core::option::Option<input::Kind>,
}
/// Nested message and enum types in `Input`.
pub mod input {
    #[derive(Clone, PartialEq, ::prost::Oneof)]
    pub enum Kind {
        #[prost(message, tag = "1")]
        ExampleList(super::ExampleList),
        #[prost(message, tag = "2")]
        ExampleListWithContext(super::ExampleListWithContext),
    }
}
/// Metadata for an inference request such as the model name and version.
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct ModelSpec {
    /// Required servable name.
    #[prost(string, tag = "1")]
    pub name: ::prost::alloc::string::String,
    /// A named signature to evaluate. If unspecified, the default signature will
    /// be used.
    #[prost(string, tag = "3")]
    pub signature_name: ::prost::alloc::string::String,
    /// Optional choice of which version of the model to use.
    ///
    /// Recommended to be left unset in the common case. Should be specified only
    /// when there is a strong version consistency requirement.
    ///
    /// When left unspecified, the system will serve the best available version.
    /// This is typically the latest version, though during version transitions,
    /// notably when serving on a fleet of instances, may be either the previous or
    /// new version.
    #[prost(oneof = "model_spec::VersionChoice", tags = "2, 4")]
    pub version_choice: ::core::option::Option<model_spec::VersionChoice>,
}
/// Nested message and enum types in `ModelSpec`.
pub mod model_spec {
    /// Optional choice of which version of the model to use.
    ///
    /// Recommended to be left unset in the common case. Should be specified only
    /// when there is a strong version consistency requirement.
    ///
    /// When left unspecified, the system will serve the best available version.
    /// This is typically the latest version, though during version transitions,
    /// notably when serving on a fleet of instances, may be either the previous or
    /// new version.
    #[derive(Clone, PartialEq, ::prost::Oneof)]
    pub enum VersionChoice {
        /// Use this specific version number.
        #[prost(message, tag = "2")]
        Version(i64),
        /// Use the version associated with the given label.
        #[prost(string, tag = "4")]
        VersionLabel(::prost::alloc::string::String),
    }
}
/// A single class.
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct Class {
    /// Label or name of the class.
    #[prost(string, tag = "1")]
    pub label: ::prost::alloc::string::String,
    /// Score for this class (e.g., the probability the item belongs to this
    /// class). As per the proto3 default-value semantics, if the score is missing,
    /// it should be treated as 0.
    #[prost(float, tag = "2")]
    pub score: f32,
}
/// List of classes for a single item (tensorflow.Example).
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct Classifications {
    #[prost(message, repeated, tag = "1")]
    pub classes: ::prost::alloc::vec::Vec<Class>,
}
/// Contains one result per input example, in the same order as the input in
/// ClassificationRequest.
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct ClassificationResult {
    #[prost(message, repeated, tag = "1")]
    pub classifications: ::prost::alloc::vec::Vec<Classifications>,
}
// RPC Interfaces

#[derive(Clone, PartialEq, ::prost::Message)]
pub struct ClassificationRequest {
    /// Model Specification. If version is not specified, will use the latest
    /// (numerical) version.
    #[prost(message, optional, tag = "1")]
    pub model_spec: ::core::option::Option<ModelSpec>,
    /// Input data.
    #[prost(message, optional, tag = "2")]
    pub input: ::core::option::Option<Input>,
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct ClassificationResponse {
    /// Effective Model Specification used for classification.
    #[prost(message, optional, tag = "2")]
    pub model_spec: ::core::option::Option<ModelSpec>,
    /// Result of the classification.
    #[prost(message, optional, tag = "1")]
    pub result: ::core::option::Option<ClassificationResult>,
}
/// Message returned for "signature_def" field.
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct SignatureDefMap {
    #[prost(map = "string, message", tag = "1")]
    pub signature_def:
        ::std::collections::HashMap<::prost::alloc::string::String, super::SignatureDef>,
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct GetModelMetadataRequest {
    /// Model Specification indicating which model we are querying for metadata.
    /// If version is not specified, will use the latest (numerical) version.
    #[prost(message, optional, tag = "1")]
    pub model_spec: ::core::option::Option<ModelSpec>,
    /// Metadata fields to get. Currently supported: "signature_def".
    #[prost(string, repeated, tag = "2")]
    pub metadata_field: ::prost::alloc::vec::Vec<::prost::alloc::string::String>,
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct GetModelMetadataResponse {
    /// Model Specification indicating which model this metadata belongs to.
    #[prost(message, optional, tag = "1")]
    pub model_spec: ::core::option::Option<ModelSpec>,
    /// Map of metadata field name to metadata field. The options for metadata
    /// field name are listed in GetModelMetadataRequest. Currently supported:
    /// "signature_def".
    #[prost(map = "string, message", tag = "2")]
    pub metadata: ::std::collections::HashMap<::prost::alloc::string::String, ::prost_types::Any>,
}
/// Regression result for a single item (tensorflow.Example).
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct Regression {
    #[prost(float, tag = "1")]
    pub value: f32,
}
/// Contains one result per input example, in the same order as the input in
/// RegressionRequest.
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct RegressionResult {
    #[prost(message, repeated, tag = "1")]
    pub regressions: ::prost::alloc::vec::Vec<Regression>,
}
// RPC interfaces.

#[derive(Clone, PartialEq, ::prost::Message)]
pub struct RegressionRequest {
    /// Model Specification. If version is not specified, will use the latest
    /// (numerical) version.
    #[prost(message, optional, tag = "1")]
    pub model_spec: ::core::option::Option<ModelSpec>,
    /// Input data.
    #[prost(message, optional, tag = "2")]
    pub input: ::core::option::Option<Input>,
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct RegressionResponse {
    /// Effective Model Specification used for regression.
    #[prost(message, optional, tag = "2")]
    pub model_spec: ::core::option::Option<ModelSpec>,
    #[prost(message, optional, tag = "1")]
    pub result: ::core::option::Option<RegressionResult>,
}
/// Inference request such as classification, regression, etc...
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct InferenceTask {
    /// Model Specification. If version is not specified, will use the latest
    /// (numerical) version.
    /// All ModelSpecs in a MultiInferenceRequest must access the same model name.
    #[prost(message, optional, tag = "1")]
    pub model_spec: ::core::option::Option<ModelSpec>,
    /// Signature's method_name. Should be one of the method names defined in
    /// third_party/tensorflow/python/saved_model/signature_constants.py.
    /// e.g. "tensorflow/serving/classify".
    #[prost(string, tag = "2")]
    pub method_name: ::prost::alloc::string::String,
}
/// Inference result, matches the type of request or is an error.
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct InferenceResult {
    #[prost(message, optional, tag = "1")]
    pub model_spec: ::core::option::Option<ModelSpec>,
    #[prost(oneof = "inference_result::Result", tags = "2, 3")]
    pub result: ::core::option::Option<inference_result::Result>,
}
/// Nested message and enum types in `InferenceResult`.
pub mod inference_result {
    #[derive(Clone, PartialEq, ::prost::Oneof)]
    pub enum Result {
        #[prost(message, tag = "2")]
        ClassificationResult(super::ClassificationResult),
        #[prost(message, tag = "3")]
        RegressionResult(super::RegressionResult),
    }
}
/// Inference request containing one or more requests.
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct MultiInferenceRequest {
    /// Inference tasks.
    #[prost(message, repeated, tag = "1")]
    pub tasks: ::prost::alloc::vec::Vec<InferenceTask>,
    /// Input data.
    #[prost(message, optional, tag = "2")]
    pub input: ::core::option::Option<Input>,
}
/// Inference request containing one or more responses.
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct MultiInferenceResponse {
    /// List of results; one for each InferenceTask in the request, returned in the
    /// same order as the request.
    #[prost(message, repeated, tag = "1")]
    pub results: ::prost::alloc::vec::Vec<InferenceResult>,
}
/// PredictRequest specifies which TensorFlow model to run, as well as
/// how inputs are mapped to tensors and how outputs are filtered before
/// returning to user.
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct PredictRequest {
    /// Model Specification. If version is not specified, will use the latest
    /// (numerical) version.
    #[prost(message, optional, tag = "1")]
    pub model_spec: ::core::option::Option<ModelSpec>,
    /// Input tensors.
    /// Names of input tensor are alias names. The mapping from aliases to real
    /// input tensor names is stored in the SavedModel export as a prediction
    /// SignatureDef under the 'inputs' field.
    #[prost(map = "string, message", tag = "2")]
    pub inputs: ::std::collections::HashMap<::prost::alloc::string::String, super::TensorProto>,
    /// Output filter.
    /// Names specified are alias names. The mapping from aliases to real output
    /// tensor names is stored in the SavedModel export as a prediction
    /// SignatureDef under the 'outputs' field.
    /// Only tensors specified here will be run/fetched and returned, with the
    /// exception that when none is specified, all tensors specified in the
    /// named signature will be run/fetched and returned.
    #[prost(string, repeated, tag = "3")]
    pub output_filter: ::prost::alloc::vec::Vec<::prost::alloc::string::String>,
}
/// Response for PredictRequest on successful run.
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct PredictResponse {
    /// Effective Model Specification used to process PredictRequest.
    #[prost(message, optional, tag = "2")]
    pub model_spec: ::core::option::Option<ModelSpec>,
    /// Output tensors.
    #[prost(map = "string, message", tag = "1")]
    pub outputs: ::std::collections::HashMap<::prost::alloc::string::String, super::TensorProto>,
}
#[doc = r" Generated client implementations."]
pub mod prediction_service_client {
    #![allow(unused_variables, dead_code, missing_docs, clippy::let_unit_value)]
    use tonic::codegen::*;
    #[doc = " open source marker; do not remove"]
    #[doc = " PredictionService provides access to machine-learned models loaded by"]
    #[doc = " model_servers."]
    #[derive(Debug, Clone)]
    pub struct PredictionServiceClient<T> {
        inner: tonic::client::Grpc<T>,
    }
    impl PredictionServiceClient<tonic::transport::Channel> {
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
    impl<T> PredictionServiceClient<T>
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
        ) -> PredictionServiceClient<InterceptedService<T, F>>
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
            PredictionServiceClient::new(InterceptedService::new(inner, interceptor))
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
        #[doc = " Classify."]
        pub async fn classify(
            &mut self,
            request: impl tonic::IntoRequest<super::ClassificationRequest>,
        ) -> Result<tonic::Response<super::ClassificationResponse>, tonic::Status> {
            self.inner.ready().await.map_err(|e| {
                tonic::Status::new(
                    tonic::Code::Unknown,
                    format!("Service was not ready: {}", e.into()),
                )
            })?;
            let codec = tonic::codec::ProstCodec::default();
            let path = http::uri::PathAndQuery::from_static(
                "/tensorflow.serving.PredictionService/Classify",
            );
            self.inner.unary(request.into_request(), path, codec).await
        }
        #[doc = " Regress."]
        pub async fn regress(
            &mut self,
            request: impl tonic::IntoRequest<super::RegressionRequest>,
        ) -> Result<tonic::Response<super::RegressionResponse>, tonic::Status> {
            self.inner.ready().await.map_err(|e| {
                tonic::Status::new(
                    tonic::Code::Unknown,
                    format!("Service was not ready: {}", e.into()),
                )
            })?;
            let codec = tonic::codec::ProstCodec::default();
            let path = http::uri::PathAndQuery::from_static(
                "/tensorflow.serving.PredictionService/Regress",
            );
            self.inner.unary(request.into_request(), path, codec).await
        }
        #[doc = " Predict -- provides access to loaded TensorFlow model."]
        pub async fn predict(
            &mut self,
            request: impl tonic::IntoRequest<super::PredictRequest>,
        ) -> Result<tonic::Response<super::PredictResponse>, tonic::Status> {
            self.inner.ready().await.map_err(|e| {
                tonic::Status::new(
                    tonic::Code::Unknown,
                    format!("Service was not ready: {}", e.into()),
                )
            })?;
            let codec = tonic::codec::ProstCodec::default();
            let path = http::uri::PathAndQuery::from_static(
                "/tensorflow.serving.PredictionService/Predict",
            );
            self.inner.unary(request.into_request(), path, codec).await
        }
        #[doc = " MultiInference API for multi-headed models."]
        pub async fn multi_inference(
            &mut self,
            request: impl tonic::IntoRequest<super::MultiInferenceRequest>,
        ) -> Result<tonic::Response<super::MultiInferenceResponse>, tonic::Status> {
            self.inner.ready().await.map_err(|e| {
                tonic::Status::new(
                    tonic::Code::Unknown,
                    format!("Service was not ready: {}", e.into()),
                )
            })?;
            let codec = tonic::codec::ProstCodec::default();
            let path = http::uri::PathAndQuery::from_static(
                "/tensorflow.serving.PredictionService/MultiInference",
            );
            self.inner.unary(request.into_request(), path, codec).await
        }
        #[doc = " GetModelMetadata - provides access to metadata for loaded models."]
        pub async fn get_model_metadata(
            &mut self,
            request: impl tonic::IntoRequest<super::GetModelMetadataRequest>,
        ) -> Result<tonic::Response<super::GetModelMetadataResponse>, tonic::Status> {
            self.inner.ready().await.map_err(|e| {
                tonic::Status::new(
                    tonic::Code::Unknown,
                    format!("Service was not ready: {}", e.into()),
                )
            })?;
            let codec = tonic::codec::ProstCodec::default();
            let path = http::uri::PathAndQuery::from_static(
                "/tensorflow.serving.PredictionService/GetModelMetadata",
            );
            self.inner.unary(request.into_request(), path, codec).await
        }
    }
}
#[doc = r" Generated server implementations."]
pub mod prediction_service_server {
    #![allow(unused_variables, dead_code, missing_docs, clippy::let_unit_value)]
    use tonic::codegen::*;
    #[doc = "Generated trait containing gRPC methods that should be implemented for use with PredictionServiceServer."]
    #[async_trait]
    pub trait PredictionService: Send + Sync + 'static {
        #[doc = " Classify."]
        async fn classify(
            &self,
            request: tonic::Request<super::ClassificationRequest>,
        ) -> Result<tonic::Response<super::ClassificationResponse>, tonic::Status>;
        #[doc = " Regress."]
        async fn regress(
            &self,
            request: tonic::Request<super::RegressionRequest>,
        ) -> Result<tonic::Response<super::RegressionResponse>, tonic::Status>;
        #[doc = " Predict -- provides access to loaded TensorFlow model."]
        async fn predict(
            &self,
            request: tonic::Request<super::PredictRequest>,
        ) -> Result<tonic::Response<super::PredictResponse>, tonic::Status>;
        #[doc = " MultiInference API for multi-headed models."]
        async fn multi_inference(
            &self,
            request: tonic::Request<super::MultiInferenceRequest>,
        ) -> Result<tonic::Response<super::MultiInferenceResponse>, tonic::Status>;
        #[doc = " GetModelMetadata - provides access to metadata for loaded models."]
        async fn get_model_metadata(
            &self,
            request: tonic::Request<super::GetModelMetadataRequest>,
        ) -> Result<tonic::Response<super::GetModelMetadataResponse>, tonic::Status>;
    }
    #[doc = " open source marker; do not remove"]
    #[doc = " PredictionService provides access to machine-learned models loaded by"]
    #[doc = " model_servers."]
    #[derive(Debug)]
    pub struct PredictionServiceServer<T: PredictionService> {
        inner: _Inner<T>,
        accept_compression_encodings: EnabledCompressionEncodings,
        send_compression_encodings: EnabledCompressionEncodings,
    }
    struct _Inner<T>(Arc<T>);
    impl<T: PredictionService> PredictionServiceServer<T> {
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
    impl<T, B> tonic::codegen::Service<http::Request<B>> for PredictionServiceServer<T>
    where
        T: PredictionService,
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
                "/tensorflow.serving.PredictionService/Classify" => {
                    #[allow(non_camel_case_types)]
                    struct ClassifySvc<T: PredictionService>(pub Arc<T>);
                    impl<T: PredictionService>
                        tonic::server::UnaryService<super::ClassificationRequest>
                        for ClassifySvc<T>
                    {
                        type Response = super::ClassificationResponse;
                        type Future = BoxFuture<tonic::Response<Self::Response>, tonic::Status>;
                        fn call(
                            &mut self,
                            request: tonic::Request<super::ClassificationRequest>,
                        ) -> Self::Future {
                            let inner = self.0.clone();
                            let fut = async move { (*inner).classify(request).await };
                            Box::pin(fut)
                        }
                    }
                    let accept_compression_encodings = self.accept_compression_encodings;
                    let send_compression_encodings = self.send_compression_encodings;
                    let inner = self.inner.clone();
                    let fut = async move {
                        let inner = inner.0;
                        let method = ClassifySvc(inner);
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
                "/tensorflow.serving.PredictionService/Regress" => {
                    #[allow(non_camel_case_types)]
                    struct RegressSvc<T: PredictionService>(pub Arc<T>);
                    impl<T: PredictionService> tonic::server::UnaryService<super::RegressionRequest> for RegressSvc<T> {
                        type Response = super::RegressionResponse;
                        type Future = BoxFuture<tonic::Response<Self::Response>, tonic::Status>;
                        fn call(
                            &mut self,
                            request: tonic::Request<super::RegressionRequest>,
                        ) -> Self::Future {
                            let inner = self.0.clone();
                            let fut = async move { (*inner).regress(request).await };
                            Box::pin(fut)
                        }
                    }
                    let accept_compression_encodings = self.accept_compression_encodings;
                    let send_compression_encodings = self.send_compression_encodings;
                    let inner = self.inner.clone();
                    let fut = async move {
                        let inner = inner.0;
                        let method = RegressSvc(inner);
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
                "/tensorflow.serving.PredictionService/Predict" => {
                    #[allow(non_camel_case_types)]
                    struct PredictSvc<T: PredictionService>(pub Arc<T>);
                    impl<T: PredictionService> tonic::server::UnaryService<super::PredictRequest> for PredictSvc<T> {
                        type Response = super::PredictResponse;
                        type Future = BoxFuture<tonic::Response<Self::Response>, tonic::Status>;
                        fn call(
                            &mut self,
                            request: tonic::Request<super::PredictRequest>,
                        ) -> Self::Future {
                            let inner = self.0.clone();
                            let fut = async move { (*inner).predict(request).await };
                            Box::pin(fut)
                        }
                    }
                    let accept_compression_encodings = self.accept_compression_encodings;
                    let send_compression_encodings = self.send_compression_encodings;
                    let inner = self.inner.clone();
                    let fut = async move {
                        let inner = inner.0;
                        let method = PredictSvc(inner);
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
                "/tensorflow.serving.PredictionService/MultiInference" => {
                    #[allow(non_camel_case_types)]
                    struct MultiInferenceSvc<T: PredictionService>(pub Arc<T>);
                    impl<T: PredictionService>
                        tonic::server::UnaryService<super::MultiInferenceRequest>
                        for MultiInferenceSvc<T>
                    {
                        type Response = super::MultiInferenceResponse;
                        type Future = BoxFuture<tonic::Response<Self::Response>, tonic::Status>;
                        fn call(
                            &mut self,
                            request: tonic::Request<super::MultiInferenceRequest>,
                        ) -> Self::Future {
                            let inner = self.0.clone();
                            let fut = async move { (*inner).multi_inference(request).await };
                            Box::pin(fut)
                        }
                    }
                    let accept_compression_encodings = self.accept_compression_encodings;
                    let send_compression_encodings = self.send_compression_encodings;
                    let inner = self.inner.clone();
                    let fut = async move {
                        let inner = inner.0;
                        let method = MultiInferenceSvc(inner);
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
                "/tensorflow.serving.PredictionService/GetModelMetadata" => {
                    #[allow(non_camel_case_types)]
                    struct GetModelMetadataSvc<T: PredictionService>(pub Arc<T>);
                    impl<T: PredictionService>
                        tonic::server::UnaryService<super::GetModelMetadataRequest>
                        for GetModelMetadataSvc<T>
                    {
                        type Response = super::GetModelMetadataResponse;
                        type Future = BoxFuture<tonic::Response<Self::Response>, tonic::Status>;
                        fn call(
                            &mut self,
                            request: tonic::Request<super::GetModelMetadataRequest>,
                        ) -> Self::Future {
                            let inner = self.0.clone();
                            let fut = async move { (*inner).get_model_metadata(request).await };
                            Box::pin(fut)
                        }
                    }
                    let accept_compression_encodings = self.accept_compression_encodings;
                    let send_compression_encodings = self.send_compression_encodings;
                    let inner = self.inner.clone();
                    let fut = async move {
                        let inner = inner.0;
                        let method = GetModelMetadataSvc(inner);
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
    impl<T: PredictionService> Clone for PredictionServiceServer<T> {
        fn clone(&self) -> Self {
            let inner = self.inner.clone();
            Self {
                inner,
                accept_compression_encodings: self.accept_compression_encodings,
                send_compression_encodings: self.send_compression_encodings,
            }
        }
    }
    impl<T: PredictionService> Clone for _Inner<T> {
        fn clone(&self) -> Self {
            Self(self.0.clone())
        }
    }
    impl<T: std::fmt::Debug> std::fmt::Debug for _Inner<T> {
        fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
            write!(f, "{:?}", self.0)
        }
    }
    impl<T: PredictionService> tonic::transport::NamedService for PredictionServiceServer<T> {
        const NAME: &'static str = "tensorflow.serving.PredictionService";
    }
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct LogCollectorConfig {
    /// Identifies the type of the LogCollector we will use to collect these logs.
    #[prost(string, tag = "1")]
    pub r#type: ::prost::alloc::string::String,
    /// The prefix to use for the filenames of the logs.
    #[prost(string, tag = "2")]
    pub filename_prefix: ::prost::alloc::string::String,
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct SamplingConfig {
    /// Requests will be logged uniformly at random with this probability. Valid
    /// range: [0, 1.0].
    #[prost(double, tag = "1")]
    pub sampling_rate: f64,
}
/// Configuration for logging query/responses.
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct LoggingConfig {
    #[prost(message, optional, tag = "1")]
    pub log_collector_config: ::core::option::Option<LogCollectorConfig>,
    #[prost(message, optional, tag = "2")]
    pub sampling_config: ::core::option::Option<SamplingConfig>,
}
/// Metadata logged along with the request logs.
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct LogMetadata {
    #[prost(message, optional, tag = "1")]
    pub model_spec: ::core::option::Option<ModelSpec>,
    #[prost(message, optional, tag = "2")]
    pub sampling_config: ::core::option::Option<SamplingConfig>,
    /// List of tags used to load the relevant MetaGraphDef from SavedModel.
    ///
    /// TODO: Add more metadata as mentioned in the bug.
    #[prost(string, repeated, tag = "3")]
    pub saved_model_tags: ::prost::alloc::vec::Vec<::prost::alloc::string::String>,
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct SessionRunRequest {
    /// Model Specification. If version is not specified, will use the latest
    /// (numerical) version.
    #[prost(message, optional, tag = "1")]
    pub model_spec: ::core::option::Option<ModelSpec>,
    /// Tensors to be fed in the step. Each feed is a named tensor.
    #[prost(message, repeated, tag = "2")]
    pub feed: ::prost::alloc::vec::Vec<super::NamedTensorProto>,
    /// Fetches. A list of tensor names. The caller expects a tensor to
    /// be returned for each fetch\[i\] (see RunResponse.tensor). The
    /// order of specified fetches does not change the execution order.
    #[prost(string, repeated, tag = "3")]
    pub fetch: ::prost::alloc::vec::Vec<::prost::alloc::string::String>,
    /// Target Nodes. A list of node names. The named nodes will be run
    /// to but their outputs will not be fetched.
    #[prost(string, repeated, tag = "4")]
    pub target: ::prost::alloc::vec::Vec<::prost::alloc::string::String>,
    /// If true, treat names in feed/fetch/target as alias names than actual tensor
    /// names (that appear in the TF graph). Alias names are resolved to actual
    /// names using `SignatureDef` in SavedModel associated with the model.
    #[prost(bool, tag = "6")]
    pub tensor_name_is_alias: bool,
    /// Options for the run call. **Currently ignored.**
    #[prost(message, optional, tag = "5")]
    pub options: ::core::option::Option<super::RunOptions>,
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct SessionRunResponse {
    /// Effective Model Specification used for session run.
    #[prost(message, optional, tag = "3")]
    pub model_spec: ::core::option::Option<ModelSpec>,
    /// NOTE: The order of the returned tensors may or may not match
    /// the fetch order specified in RunRequest.
    #[prost(message, repeated, tag = "1")]
    pub tensor: ::prost::alloc::vec::Vec<super::NamedTensorProto>,
    /// Returned metadata if requested in the options.
    #[prost(message, optional, tag = "2")]
    pub metadata: ::core::option::Option<super::RunMetadata>,
}
#[doc = r" Generated client implementations."]
pub mod session_service_client {
    #![allow(unused_variables, dead_code, missing_docs, clippy::let_unit_value)]
    use tonic::codegen::*;
    #[doc = " SessionService defines a service with which a client can interact to execute"]
    #[doc = " Tensorflow model inference. The SessionService::SessionRun method is similar"]
    #[doc = " to MasterService::RunStep of Tensorflow, except that all sessions are ready"]
    #[doc = " to run, and you request a specific model/session with ModelSpec."]
    #[derive(Debug, Clone)]
    pub struct SessionServiceClient<T> {
        inner: tonic::client::Grpc<T>,
    }
    impl SessionServiceClient<tonic::transport::Channel> {
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
    impl<T> SessionServiceClient<T>
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
        ) -> SessionServiceClient<InterceptedService<T, F>>
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
            SessionServiceClient::new(InterceptedService::new(inner, interceptor))
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
        #[doc = " Runs inference of a given model."]
        pub async fn session_run(
            &mut self,
            request: impl tonic::IntoRequest<super::SessionRunRequest>,
        ) -> Result<tonic::Response<super::SessionRunResponse>, tonic::Status> {
            self.inner.ready().await.map_err(|e| {
                tonic::Status::new(
                    tonic::Code::Unknown,
                    format!("Service was not ready: {}", e.into()),
                )
            })?;
            let codec = tonic::codec::ProstCodec::default();
            let path = http::uri::PathAndQuery::from_static(
                "/tensorflow.serving.SessionService/SessionRun",
            );
            self.inner.unary(request.into_request(), path, codec).await
        }
    }
}
#[doc = r" Generated server implementations."]
pub mod session_service_server {
    #![allow(unused_variables, dead_code, missing_docs, clippy::let_unit_value)]
    use tonic::codegen::*;
    #[doc = "Generated trait containing gRPC methods that should be implemented for use with SessionServiceServer."]
    #[async_trait]
    pub trait SessionService: Send + Sync + 'static {
        #[doc = " Runs inference of a given model."]
        async fn session_run(
            &self,
            request: tonic::Request<super::SessionRunRequest>,
        ) -> Result<tonic::Response<super::SessionRunResponse>, tonic::Status>;
    }
    #[doc = " SessionService defines a service with which a client can interact to execute"]
    #[doc = " Tensorflow model inference. The SessionService::SessionRun method is similar"]
    #[doc = " to MasterService::RunStep of Tensorflow, except that all sessions are ready"]
    #[doc = " to run, and you request a specific model/session with ModelSpec."]
    #[derive(Debug)]
    pub struct SessionServiceServer<T: SessionService> {
        inner: _Inner<T>,
        accept_compression_encodings: EnabledCompressionEncodings,
        send_compression_encodings: EnabledCompressionEncodings,
    }
    struct _Inner<T>(Arc<T>);
    impl<T: SessionService> SessionServiceServer<T> {
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
    impl<T, B> tonic::codegen::Service<http::Request<B>> for SessionServiceServer<T>
    where
        T: SessionService,
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
                "/tensorflow.serving.SessionService/SessionRun" => {
                    #[allow(non_camel_case_types)]
                    struct SessionRunSvc<T: SessionService>(pub Arc<T>);
                    impl<T: SessionService> tonic::server::UnaryService<super::SessionRunRequest> for SessionRunSvc<T> {
                        type Response = super::SessionRunResponse;
                        type Future = BoxFuture<tonic::Response<Self::Response>, tonic::Status>;
                        fn call(
                            &mut self,
                            request: tonic::Request<super::SessionRunRequest>,
                        ) -> Self::Future {
                            let inner = self.0.clone();
                            let fut = async move { (*inner).session_run(request).await };
                            Box::pin(fut)
                        }
                    }
                    let accept_compression_encodings = self.accept_compression_encodings;
                    let send_compression_encodings = self.send_compression_encodings;
                    let inner = self.inner.clone();
                    let fut = async move {
                        let inner = inner.0;
                        let method = SessionRunSvc(inner);
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
    impl<T: SessionService> Clone for SessionServiceServer<T> {
        fn clone(&self) -> Self {
            let inner = self.inner.clone();
            Self {
                inner,
                accept_compression_encodings: self.accept_compression_encodings,
                send_compression_encodings: self.send_compression_encodings,
            }
        }
    }
    impl<T: SessionService> Clone for _Inner<T> {
        fn clone(&self) -> Self {
            Self(self.0.clone())
        }
    }
    impl<T: std::fmt::Debug> std::fmt::Debug for _Inner<T> {
        fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
            write!(f, "{:?}", self.0)
        }
    }
    impl<T: SessionService> tonic::transport::NamedService for SessionServiceServer<T> {
        const NAME: &'static str = "tensorflow.serving.SessionService";
    }
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct ClassifyLog {
    #[prost(message, optional, tag = "1")]
    pub request: ::core::option::Option<ClassificationRequest>,
    #[prost(message, optional, tag = "2")]
    pub response: ::core::option::Option<ClassificationResponse>,
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct RegressLog {
    #[prost(message, optional, tag = "1")]
    pub request: ::core::option::Option<RegressionRequest>,
    #[prost(message, optional, tag = "2")]
    pub response: ::core::option::Option<RegressionResponse>,
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct PredictLog {
    #[prost(message, optional, tag = "1")]
    pub request: ::core::option::Option<PredictRequest>,
    #[prost(message, optional, tag = "2")]
    pub response: ::core::option::Option<PredictResponse>,
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct MultiInferenceLog {
    #[prost(message, optional, tag = "1")]
    pub request: ::core::option::Option<MultiInferenceRequest>,
    #[prost(message, optional, tag = "2")]
    pub response: ::core::option::Option<MultiInferenceResponse>,
}
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct SessionRunLog {
    #[prost(message, optional, tag = "1")]
    pub request: ::core::option::Option<SessionRunRequest>,
    #[prost(message, optional, tag = "2")]
    pub response: ::core::option::Option<SessionRunResponse>,
}
/// Logged model inference request.
#[derive(Clone, PartialEq, ::prost::Message)]
pub struct PredictionLog {
    #[prost(message, optional, tag = "1")]
    pub log_metadata: ::core::option::Option<LogMetadata>,
    #[prost(oneof = "prediction_log::LogType", tags = "2, 3, 6, 4, 5")]
    pub log_type: ::core::option::Option<prediction_log::LogType>,
}
/// Nested message and enum types in `PredictionLog`.
pub mod prediction_log {
    #[derive(Clone, PartialEq, ::prost::Oneof)]
    pub enum LogType {
        #[prost(message, tag = "2")]
        ClassifyLog(super::ClassifyLog),
        #[prost(message, tag = "3")]
        RegressLog(super::RegressLog),
        #[prost(message, tag = "6")]
        PredictLog(super::PredictLog),
        #[prost(message, tag = "4")]
        MultiInferenceLog(super::MultiInferenceLog),
        #[prost(message, tag = "5")]
        SessionRunLog(super::SessionRunLog),
    }
}
