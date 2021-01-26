package com.dextra.alelo.samples.authentication.model.response

import io.swagger.annotations.ApiModelProperty

data class InvoiceResponse(

    @ApiModelProperty(required = true)
    var accountId: String,

    @ApiModelProperty(required = true)
    var paymentType: String,

    @ApiModelProperty(required = true)
    var discount: Float? = null,

    @ApiModelProperty(required = true)
    var invoiceTotal: Float

)