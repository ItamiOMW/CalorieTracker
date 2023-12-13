package com.itami.calorie_tracker.core.domain.paginator

interface Paginator {

    suspend fun loadNextPage()

    fun reset()

}