package com.itami.calorie_tracker.reports_feature.data.remote

import javax.inject.Inject

class ReportsApiServiceImpl @Inject constructor(

): ReportsApiService {

    companion object {

        private const val REPORTS = "/api/v1/reports"

    }

}