업데이트 날짜 2022.06.08
Blog : https://medium.com/p/a72b6075e57e
## Paging
Android developers : [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview?hl=ko)

## 페이징 장점
1. 데이터를 미리 호출, 캐싱하여 메모리 리소스를 효율적으로 사용 가능하다.
2. 요청 중복 제거기능이 있다.
3. RecyclerView Adapter와 자동으로 연결하여 스크롤 시 데이터를 호출할 수 있다.
4. Coroutine, Flow, RxJava 지원이 최고 수준

## 구현 방법
### 의존성 추가
```
  def paging_version = "3.1.1"
  implementation "androidx.paging:paging-runtime:$paging_version"
```
### PagingSouce 정의
PagingSource<사용하고자 하는 키, 리스트 value>
```kt
class SearchBookSource(
    private val searchBookService: SearchBookService,
    private val query: String
) :PagingSource<Int, BookListItem>() {
    // 키를 다시 가져올때 사용합니다. (리스트 데이터 변경 후 스크롤 유지 등)
    override fun getRefreshKey(state: PagingState<Int, BookListItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookListItem> {
        return try {
            // 최초 실행시 page는 null입니다.
            val page = params.key ?: 1
            // 실질적으로 데이터를 호출합니다.
            val response = searchBookService.searchBookRequest(query, page)
            LoadResult.Page(
                // 호추한 데이터를 반환하여 UI에 데이터를 보냅니다.
                data = response.books,
                // 이전 페이지
                prevKey = if (page == 1) null else page -1,
                // 다음 페이지를 구현할때 api의 조건에 따라 다음 페이지가 없다면 null을 넣으면 됩니다.
                nextKey = if (response.total.toInt() == 0) null else response.page.toInt() + 1
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}
```

### Pagingdata 스트림 설정
```kt
fun getSearchBooksByPaging(query: String): Flow<PagingData<BookListItem>> {
        return Pager(
            // 화면에 보여줄 데이터의 개수 입니다.
            config = PagingConfig(pageSize = 10),
            // 이전에 구현한 Source를 연결 해서 Paging 할 수있도록 합니다.
            pagingSourceFactory = {SearchBookSource(searchBookService, query)}
            // flow를 사용하여 구현했습니다
        ).flow // liveData또한 지원하기 떄문에 .liveData를 이용하여 liveData형태로 반환 가능합니다.
    }
```

### Recycler View 구현
기존의 diffUtil을 이용한 ListAdapter,RecyclerView 구현과 비슷 하다.
```kt
class BookListAdapter(private val itemClickListener: (isbn13: String, bitmap: Bitmap) -> Unit) :
    PagingDataAdapter<BookListItem, BookListAdapter.BookListHolder>(diffUtil) {
    inner class BookListHolder(
        private val binding: ItemBookListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun binding(bookListItem: BookListItem) {
            ...
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<BookListItem>() {
           ...
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListHolder =
        BookListHolder(
            ...
        )

    override fun onBindViewHolder(holder: BookListHolder, position: Int) {
        getItem(position)?.let { holder.binding(it) }
    }
}
```

### 데이터 및 Adapter 연결
```kt 
// ViewModel
private fun searchBooks(query: String) {
    viewModelScope.launch {
        searchBooksUseCase.paging(query).cachedIn(viewModelScope).collectLatest {
            _searchBooks.value = it
        }
    }
}

//Fragment
vm.bookListLiveData.observe(viewLifecycleOwner) { pagingData ->
    viewLifecycleOwner.lifecycleScope.launch {
        bookListAdapter.submitData(pagingData)
    }
}
```

## 추가 정보
- NestedScrollView 와 혼용해서 사용 할 수 없다.(NestedScrollView가 데이터를 미리 다 호출 해버려서 그런듯)
- RecyclerView 에서 item에 접근하기위해서는 `getItem(position)`을 사용 한다.
- 테스트 코드 작성시에는 `PagingData.from(list)` 방식으로 데이터를 만들어 넣을 수 있다.
-`pagingAdapter.refresh()` 함수로 리플레쉬 할 수 있
