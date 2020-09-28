package nl.omererdem.madlevel4task2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gameTable")
class Game(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "answer_user")
    var answerUser: Int,

    @ColumnInfo(name = "answer_pc")
    var answerPc: Int,

    @ColumnInfo(name = "created_on")
    var createdOn: Int,

    @ColumnInfo(name = "result")
    var result: Boolean
)