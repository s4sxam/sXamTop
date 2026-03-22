// ...
data class NewsApiArticle(
    @SerializedName("title") val title: String = "",
    @SerializedName("description") val description: String? = "",
    @SerializedName("source") val source: NewsApiSource = NewsApiSource(),
    @SerializedName("publishedAt") val publishedAt: String = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("urlToImage") val urlToImage: String? = null // #43 Add Image
)
// ... update RssItem to include `enclosure` or `thumbnail` string if available