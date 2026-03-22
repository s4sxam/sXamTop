@HiltViewModel // #8 DI
class SearchViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    val query = MutableStateFlow("")

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val results = query
        .debounce(300)
        .flatMapLatest { q -> 
            if (q.isBlank()) flowOf(emptyList()) else repository.searchCachedNews(q) // #2 Queries DB instead of network
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}