package com.transportation.bookcar.domain.transform

import com.transportation.bookcar.data.remote.bean.MovieDTO
import com.transportation.bookcar.domain.pojo.Movie

/**
 * Created by Dao Thanh HA on 11/20/2018.
 */
fun MovieDTO.toMovie() = Movie(id, overview, title, posterPath)

