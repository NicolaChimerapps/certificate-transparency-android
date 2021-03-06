/*
 * Copyright 2019 Babylon Partners Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.babylon.certificatetransparency.internal.loglist.model.v2

import com.babylon.certificatetransparency.internal.loglist.deserializer.HttpUrlDeserializer
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import okhttp3.HttpUrl

/**
 * @property description Description of the CT log. A human-readable description that can be used to identify this log.
 * @property key The public key of the CT log. The log's public key as a DER-encoded ASN.1 SubjectPublicKeyInfo structure, then encoded as
 * base64 (https://tools.ietf.org/html/rfc5280#section-4.1.2.7).
 * @property logId The SHA-256 hash of the CT log's public key, base64-encoded. This is the LogID found in SCTs issued by this log
 * (https://tools.ietf.org/html/rfc6962#section-3.2).
 * @property maximumMergeDelay The Maximum Merge Delay, in seconds. The CT log should not take longer than this to incorporate a certificate
 * (https://tools.ietf.org/html/rfc6962#section-3).
 * @property url The base URL of the CT log's HTTP API. The API endpoints are defined in https://tools.ietf.org/html/rfc6962#section-4.
 * @property dns The domain name of the CT log's DNS API. The API endpoints are defined in
 * https://github.com/google/certificate-transparency-rfcs/blob/master/dns/draft-ct-over-dns.md.
 * @property temporalInterval The log will only accept certificates that expire (have a NotAfter date) between these dates.
 * @property logType The purpose of this log, e.g. test.
 * @property state The state of the log from the log list distributor's perspective.
 */
internal data class Log(
    @SerializedName("description") val description: String?,
    @SerializedName("key") val key: String,
    @SerializedName("log_id") val logId: String,
    @SerializedName("mmd") val maximumMergeDelay: Int,
    @SerializedName("url") @JsonAdapter(HttpUrlDeserializer::class) val url: HttpUrl,
    @SerializedName("dns") val dns: Hostname?,
    @SerializedName("temporal_interval") val temporalInterval: TemporalInterval?,
    @SerializedName("log_type") val logType: LogType?,
    @SerializedName("state") val state: State?
) {
    init {
        require(description == null || description.isNotEmpty())
        @Suppress("MagicNumber")
        require(logId.length == 44)
        require(maximumMergeDelay >= 1)
    }
}
