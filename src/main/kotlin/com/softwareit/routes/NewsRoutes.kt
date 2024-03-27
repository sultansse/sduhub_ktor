package com.softwareit.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


fun Route.addNewsRoutes() {
    get("/news") {

        val targetUrl =
            "https://tengrinews.kz/tag/университет/"
        val document = scrapeWebsite(targetUrl)
        val items: List<NewsItem> = extractNews(document)

        call.respond(HttpStatusCode.OK, items)
    }

    get("/news/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()

        if (id != null) {
            val items = extractNews(scrapeWebsite("https://tengrinews.kz/tag/университет/"))

            if (id >= 0 && id < items.size) {
                val specificItem = items[id]
                call.respond(HttpStatusCode.OK, specificItem)
            } else {
                call.respond(HttpStatusCode.NotFound, "News not found")
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "Invalid id")
        }
    }
}

fun scrapeWebsite(url: String): Document {
    return Jsoup.connect(url).get()
}

fun extractNews(document: Document): List<NewsItem> {
    val newsItems = mutableListOf<NewsItem>()
    val mainContentDiv = document.selectFirst("div.content_main")
    val itemElements = mainContentDiv?.getElementsByClass("content_main_item")

    itemElements?.forEach { itemElement ->
        val title = itemElement.getElementsByClass("content_main_item_title").firstOrNull()?.text() ?: ""
        val announce = itemElement.getElementsByClass("content_main_item_announce").firstOrNull()?.text() ?: ""
        val imageUrl = itemElement.getElementsByClass("content_main_item_img").firstOrNull()?.attr("src") ?: ""
        val date = itemElement.getElementsByClass("content_main_item_meta").firstOrNull()?.getElementsByTag("span")
            ?.firstOrNull()?.text()?.trim() ?: ""
        newsItems.add(NewsItem(imageUrl, title, announce, date))
    }

    return newsItems
}

@Serializable
data class NewsItem(
    val imageUrl: String,
    val title: String,
    val announce: String,
    val date: String,
)

