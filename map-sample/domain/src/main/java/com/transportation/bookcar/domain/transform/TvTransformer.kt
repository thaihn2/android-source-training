package com.transportation.bookcar.domain.transform

import com.transportation.bookcar.data.remote.bean.TvDTO
import com.transportation.bookcar.domain.pojo.Tv

/**
 * Created by Dao Thanh HA on 11/20/2018.
 */
fun TvDTO.toTv() = Tv(id, overview, name, posterPath)

