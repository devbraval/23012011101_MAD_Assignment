package com.example.a23012011101_mad_assignment1.model

data class Medicine(
    val id: Long,
    val theId: String,
    var tableName: String,
    var type: String,
    var preBP: String? = null,
    var preOxygen: String? = null,
    var prePulse: String? = null,
    var totalTablets: Int = 0,
    var perDay: Int = 1,
    var emptyStomach: Boolean = false,
    var remainingTablets: Int = 0,
    var doseTimes: List<Long> = listOf()
) {
    fun nextRingTime(): Long {
        val now = System.currentTimeMillis()
        // next time today or tomorrow
        val nextToday = doseTimes.filter { it >= now }.minOrNull()
        return nextToday ?: doseTimes.minOrNull()?.plus(24L * 60 * 60 * 1000) ?: -1L
    }
}
