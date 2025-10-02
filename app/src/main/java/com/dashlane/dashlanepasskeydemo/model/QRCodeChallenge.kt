package com.dashlane.dashlanepasskeydemo.model

data class QRCodeChallenge(
    val sessionId: String,
    val type: String,
    val username: String?,
    val rpID: String,
    val origin: String,
    val challenge: String,
    val options: Options
) {
    data class Options(
        val challenge: String,
        val allowCredentials: List<AllowCredential>?,
        val timeout: Long,
        val userVerification: String,
        val rpId: String
    )
    
    data class AllowCredential(
        val id: String,
        val type: String,
        val transports: List<String>
    )
}
