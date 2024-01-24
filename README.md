[![](https://jitpack.io/v/tykann/spring-batch-exposed.svg)](https://jitpack.io/#tykann/spring-batch-exposed)

# Spring Batch Exposed ItemReader

Kotlin Exposed ItemReader For Spring Batch

## Usage

### Requires

* Kotlin (above Java 17)
* Spring Batch

### Get Started

Add to `build.gradle.kts`  
`x.y.z` should be replace into the latest version.
```gradle
repositories {
    ...
    maven { url = uri("https://jitpack.io") }
}
...
dependencies {
    ...
    implementation("com.github.tykann.spring-batch-exposed:spring-batch-exposed-reader:x.y.z")
}
```

Type of item is always `ResultRow` of Exposed.

### ExposedPagingItemReader

Common paging reader using offset.

```kotlin
@Bean(READER_NAME)
@StepScope
fun reader(): ExposedPagingItemReader {
	return ExposedPagingItemReader(CHUNK_SIZE) {
		Store
			.join(Book, JoinType.INNER, Book.storeId, Store.id)
			.selectAll()
			.orderBy(Store.id to SortOrder.ASC)
	}
}
```

### ExposedNoOffsetPagingItemReader

**No offset** query is required due to the performance problem of the paging query.

```kotlin
@Bean(READER_NAME)
@StepScope
fun reader(): ExposedNoOffsetPagingItemReader {
	val options = ExposedNoOffsetIdLongOptions(Store.id to SortOrder.ASC)
	return ExposedNoOffsetPagingItemReader(options, CHUNK_SIZE) {
		Store
			.join(Book, JoinType.INNER, Book.storeId, Store.id)
			.selectAll()
	}
}
```
