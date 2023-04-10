// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

#pragma once

#include <string>
#include <unordered_map>
#include <vector>

namespace onnxruntime {

// data types for execution provider options

using ProviderOptions = std::unordered_map<std::string, std::string>;
using ProviderOptionsVector = std::vector<ProviderOptions>;
using ProviderOptionsMap = std::unordered_map<std::string, ProviderOptions>;

}  // namespace onnxruntime
