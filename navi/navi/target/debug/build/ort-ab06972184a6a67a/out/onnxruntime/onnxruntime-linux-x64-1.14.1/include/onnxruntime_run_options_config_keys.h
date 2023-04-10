// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

#pragma once

/*
 * This file defines RunOptions Config Keys and format of the Config Values.
 *
 * The Naming Convention for a RunOptions Config Key,
 * "[Area][.[SubArea1].[SubArea2]...].[Keyname]"
 * Such as "ep.cuda.use_arena"
 * The Config Key cannot be empty
 * The maximum length of the Config Key is 128
 *
 * The string format of a RunOptions Config Value is defined individually for each Config.
 * The maximum length of the Config Value is 1024
 */

// Key for enabling shrinkages of user listed device memory arenas.
// Expects a list of semi-colon separated key value pairs separated by colon in the following format:
// "device_0:device_id_0;device_1:device_id_1"
// No white-spaces allowed in the provided list string.
// Currently, the only supported devices are : "cpu", "gpu" (case sensitive).
// If "cpu" is included in the list, DisableCpuMemArena() API must not be called (i.e.) arena for cpu should be enabled.
// Example usage: "cpu:0;gpu:0" (or) "gpu:0"
// By default, the value for this key is empty (i.e.) no memory arenas are shrunk
static const char* const kOrtRunOptionsConfigEnableMemoryArenaShrinkage = "memory.enable_memory_arena_shrinkage";

// Set to '1' to not synchronize execution providers with CPU at the end of session run.
// Per default it will be set to '0'
// Taking CUDA EP as an example, it omit triggering cudaStreamSynchronize on the compute stream.
static const char* const kOrtRunOptionsConfigDisableSynchronizeExecutionProviders = "disable_synchronize_execution_providers";
