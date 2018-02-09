package com.teamwork.sample.maicon.util.extension

import com.squareup.sqlbrite.BriteDatabase


fun BriteDatabase.deleteAll(tableName: String): Int {
    return this.delete(tableName, null)
}