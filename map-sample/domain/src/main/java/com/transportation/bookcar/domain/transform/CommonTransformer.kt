package com.transportation.bookcar.domain.transform

import com.transportation.bookcar.data.remote.bean.PageListDTO
import com.transportation.bookcar.domain.pojo.PageList

/**
 * Created on 5/16/2019.
 */
fun <T, R> PageListDTO<T>.toPageList(transFun: (T) -> R) = PageList<R>(
        page,
        results.map { transFun(it) },
        totalPages,
        totalResults
)
