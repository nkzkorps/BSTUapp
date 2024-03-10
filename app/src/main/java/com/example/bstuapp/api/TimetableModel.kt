package com.example.bstuapp.api


data class TimetableModel(
    val success: Boolean,
    val result: TimetableResult,
    val entity_key: String
)

data class TimetableResult(
    val name: String,
    val weeks: List<Days>,
)

data class Days(
    val days: List<Day>,
    val is_denominator: Boolean,
    val start: String,
    val end: String
)

data class Day(
    val date: String,
    val day_short_name: String,
    val day_num: Int,
    val day_week_num: Int,
    val month_num: Int,
    val is_current_day: Boolean,
    val events: List<Event?>
)

data class Event(
    val type: String,
    val data: TtData
)

data class TtData(
    val id: Int,
    val subject: TtSubject,
    val groups: List<TtGroup>,
    val teachers: List<TtTeacher>,
    val audiences: List<Audience>,
    val types: List<Type>,
    val order: Int?,
    val start: String,
    val end: String,
    var entity_type: String
)

data class TtGroup(
    val id: Int,
    val old_id: Int,
    val name: String
)

data class TtTeacher(
    val id: Int,
    val old_id: Int,
    val name: String,
    val short_name: String,
    val position: String
)

data class Audience(
    val id: Int,
    val old_id: Int,
    val name: String
)

data class Type(
    val id: Int,
    val name: String,
    val short_name: String
)

data class TtSubject(
    val name: String,
    val shortName: String
)