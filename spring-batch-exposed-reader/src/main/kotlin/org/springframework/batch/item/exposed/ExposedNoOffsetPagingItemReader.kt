package org.springframework.batch.item.exposed

import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import org.springframework.batch.item.exposed.options.ExposedNoOffsetOptions

open class ExposedNoOffsetPagingItemReader(
	private val options: ExposedNoOffsetOptions<*>,
	pageSize: Int,
	transacted: Boolean = false,
	queryFunction: () -> Query,
) : ExposedPagingItemReader(pageSize, transacted, queryFunction) {
	
	
	override fun doReadPage() {
		tx = getTxOrNull()
		val query = createQuery()
			.limit(pageSize)
		initResults()
		fetchQuery(query)
		resetCurrentIdIfNotLastPage()
	}
	
	override fun createQuery(): Query {
		val query = queryFunction.invoke()
		options.initKeys(query, page) // 제일 첫번째 페이징시 시작해야할 ID 찾기
		return options.createQuery(query, page)
	}
	
	private fun resetCurrentIdIfNotLastPage() {
		if (isNotEmptyResults()) {
			options.resetCurrentId(getLastItem())
		}
	}
	
	// 조회결과가 Empty이면 results에 null이 담긴다
	private fun isNotEmptyResults(): Boolean {
		return results.isNotEmpty() && results[0] != null
	}
	
	private fun getLastItem(): ResultRow {
		return results.last()
	}
}
