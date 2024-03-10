package com.example.bstuapp.api

data class ApiResponse(
    val success: Boolean,
    val result: Result,
    val errors: Errors
)

data class Errors(
    val credentials: String
)

data class Result(
    val user_info: UserInfo
)

data class UserInfo(
    val id: Int,
    val email: String,
    val name: String,
    val surname: String,
    val patronymic: String,
    val default_account_key: String,
    val default_account_id: Int,
    val api_token: String,
    val accounts: List<Account>
)

data class Account(
    val id: Int,
    val key: String,
    val name: String,
    val permissions: List<String>,
    val data: UserData
)

data class UserData(
    val uuid: String,
    val phone: String?,
    val status: Int,
    val snils: String,
    val student_record_book_code: String,
    val birth_day: String,
    val citizenship: String?,
    val gender: Int,
    val plan: Plan,
    val edu_type: EduType,
    val edu_form: EduForm,
    val edu_level: EduLevel,
    val payment_contract_number: String?,
    val group: Group,
    val is_headman: Boolean,
    val course: Int
)

data class Plan(
    val id: Int,
    val fgos: String,
    val link: String,
    val name: String,
    val spec_code: String,
    val year_date_start: String
)

data class EduType(
    val name: String,
    val is_free: Boolean
)

data class EduForm(
    val id: Int,
    val name: String
)

data class EduLevel(
    val id: Int,
    val name: String
)

data class Group(
    val id: Int,
    val old_id: Int,
    val name: String,
    val kafedra: Kafedra,
    val institute: Institute
)

data class Kafedra(
    val id: Int,
    val name: String,
    val short_name: String
)

data class Institute(
    val id: Int,
    val name: String,
    val short_name: String
)
