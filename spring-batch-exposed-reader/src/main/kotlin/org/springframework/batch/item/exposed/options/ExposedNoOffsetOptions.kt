package org.springframework.batch.item.exposed.options

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder

abstract class ExposedNoOffsetOptions<T>(
	val order: Pair<Column<T>, SortOrder>,
) {
	
	abstract fun initKeys(query: Query, page: Int)
	protected abstract fun initFirstId(query: Query)
	protected abstract fun initLastId(query: Query)
	abstract fun createQuery(query: Query, page: Int): Query
	abstract fun resetCurrentId(item: ResultRow)
	protected fun getFieldValue(item: ResultRow): T {
		val (column) = order
		return item[column] ?: throw IllegalArgumentException("Not Found or Not Access Field")
	}
	
	protected fun isGroupByQuery(query: Query): Boolean {
		return isGroupByQuery(query.toString())
	}
	
	private fun isGroupByQuery(sql: String): Boolean {
		return sql.contains("group by")
	}
}

