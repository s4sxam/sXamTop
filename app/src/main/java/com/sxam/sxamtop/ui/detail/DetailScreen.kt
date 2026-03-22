// Remove `object NewsDetailStore` entirely (#1)
@Composable
fun DetailScreen(
    onBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel() // Use Hilt
) {
    val item by viewModel.newsItem.collectAsState()
    // ... rest of UI uses `item`
}