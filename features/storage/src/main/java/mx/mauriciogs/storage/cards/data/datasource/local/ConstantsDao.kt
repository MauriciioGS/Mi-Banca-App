package mx.mauriciogs.storage.cards.data.datasource.local

import mx.mauriciogs.storage.common.Query.DELETE_FROM
import mx.mauriciogs.storage.common.Query.FROM
import mx.mauriciogs.storage.common.Query.SELECT
import mx.mauriciogs.storage.common.Query.SELECT_FROM
import mx.mauriciogs.storage.common.Query.WHERE

object ConstantsDao {

    /**
     * CardsDao
     */
    private const val TABLE_CARDS = "user_cards"

    const val GET_ALL_CARDS = SELECT_FROM + TABLE_CARDS

    private const val COLUM_CARD_NUMBER = "cardNumber"
    private const val CARD_NUMBER_CONDITION = "$COLUM_CARD_NUMBER = :cardNumber"
    const val GET_CARD = SELECT_FROM + TABLE_CARDS + WHERE + CARD_NUMBER_CONDITION

    private const val COLUM_ID = "id"
    private const val CARD_DELETE_CONDITION = "$COLUM_ID = :cardId"
    const val DELETE_CARD = DELETE_FROM + TABLE_CARDS + WHERE + CARD_DELETE_CONDITION
}
