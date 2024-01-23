package org.springframework.batch.item.exposed.options

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greater
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.less
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.andIfNotNull
import org.jetbrains.exposed.sql.max
import org.jetbrains.exposed.sql.min


class ExposedNoOffsetIdLongOptions<T : Comparable<T>>(
	order: Pair<Column<T>, SortOrder>,
) : ExposedNoOffsetOptions<T>(order) {
	override fun initKeys(query: Query, page: Int) {
		if (page == 0) {
			initFirstId(query)
			initLastId(query)
		}
	}
	
	private var currentId: T? = null
	private var lastId: T? = null
	
	override fun initFirstId(query: Query) {
		val clone = query.copy()
		val isGroupByQuery = isGroupByQuery(clone)
		
		currentId = if (isGroupByQuery) {
			clone
				.adjustSlice { slice(order.first) }
				.orderBy(order)
				.firstOrNull()?.get(order.first)
		} else {
			val exp = if (order.second == SortOrder.ASC) order.first.min() else order.first.max()
			clone
				.adjustSlice { slice(exp) }
				.firstOrNull()?.get(exp)
		}
	}
	
	override fun initLastId(query: Query) {
		val clone = query.copy()
		val isGroupByQuery = isGroupByQuery(clone)
		
		lastId = if (isGroupByQuery) {
			clone
				.adjustSlice { slice(order.first) }
				.orderBy(order.first to if (order.second == SortOrder.ASC) SortOrder.DESC else SortOrder.ASC)
				.firstOrNull()?.get(order.first)
		} else {
			val exp = if (order.second == SortOrder.ASC) order.first.max() else order.first.min()
			clone
				.adjustSlice { slice(exp) }
				.firstOrNull()?.get(exp)
		}
	}
	
	override fun createQuery(query: Query, page: Int): Query {
		return if (currentId == null) {
			query
				.orderBy(order)
		} else {
			val isFirstPage = page == 0
			val isOrderAsc = order.second == SortOrder.ASC
			val startCondition = when {
				isFirstPage && isOrderAsc -> order.first greaterEq currentId!!
				isFirstPage && !isOrderAsc -> order.first lessEq currentId!!
				!isFirstPage && isOrderAsc -> order.first greater currentId!!
				else -> order.first less currentId!!
			}
			
			val endCondition = if (isOrderAsc) (order.first lessEq lastId!!) else (order.first greaterEq lastId!!)
			
			query
				.adjustWhere { startCondition and endCondition andIfNotNull this }
				.orderBy(order)
		}
	}
	
	override fun resetCurrentId(item: ResultRow) {
		currentId = getFieldValue(item)
	}
}
