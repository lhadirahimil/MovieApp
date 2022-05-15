package com.hadirahimi.movieapppowerfull.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomMasterTable.TABLE_NAME
import com.hadirahimi.movieapppowerfull.utils.Constants

@Entity(tableName = Constants.MOVIE_TABLE)
data class MovieEntity(
    @PrimaryKey
    var id : Int = 0,
    var poster : String = "",
    var title : String = "",
    var rate : String = "",
    var country : String = "",
    var year : String = ""
)