package com.example.assignment_detect_language_api.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/*data class Response(
    val id: Int,
    val title: String,
    val description: String
)*/

@Entity(tableName = "responses")
class Response constructor() {
    @field:PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @field:ColumnInfo(name = "title")
    var title: String? = null

    @field:ColumnInfo(name = "description")
    var description: String? = null

    @Ignore
    constructor(title: String, description: String) : this() {
        this.title = title
        this.description = description
    }

}