package mx.mauriciogs.storage.payments.data.datasource.local

import mx.mauriciogs.storage.common.Query.DELETE_FROM
import mx.mauriciogs.storage.common.Query.FROM
import mx.mauriciogs.storage.common.Query.SELECT
import mx.mauriciogs.storage.common.Query.SELECT_FROM
import mx.mauriciogs.storage.common.Query.WHERE

object ConstantsDao {

    /**
     * PaymentsDao
     */
    private const val TABLE_PAYMENTS = "user_payments"

    const val GET_ALL_PAYMENTS = SELECT_FROM + TABLE_PAYMENTS

}
