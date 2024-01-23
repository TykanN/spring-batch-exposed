package org.springframework.batch.item.exposed


import org.jetbrains.exposed.dao.flushCache
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.springframework.batch.item.database.AbstractPagingItemReader
import java.util.concurrent.CopyOnWriteArrayList

open class ExposedPagingItemReader(
	pageSize: Int,
	private val transacted: Boolean = false,
	protected val queryFunction: () -> Query,
) : AbstractPagingItemReader<ResultRow>() {
	protected open var tx: Transaction? = null
	
	init {
		this.pageSize = pageSize
	}
	
	override fun doReadPage() {
		tx = getTxOrNull()
		val query = createQuery()
			.limit(pageSize, offset = (page * pageSize).toLong())
		
		initResults()
		fetchQuery(query)
	}
	
	fun getTxOrNull(): Transaction? {
		if (transacted) {
			val tx = TransactionManager.manager.newTransaction()
			tx.flushCache()
			return tx
		}
		return null
	}
	
	protected open fun createQuery(): Query {
		return queryFunction.invoke()
	}
	
	fun initResults() {
		if (results.isNullOrEmpty()) {
			results = CopyOnWriteArrayList()
		} else {
			results.clear()
		}
	}
	
	fun fetchQuery(query: Query) {
		results.addAll(query.map { it })
		if (transacted) {
			tx?.commit()
		}
	}
	
	override fun doClose() {
		if (transacted) {
			tx?.close()
		}
		super.doClose()
	}
}
