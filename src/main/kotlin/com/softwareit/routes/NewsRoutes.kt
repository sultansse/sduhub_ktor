package com.softwareit.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

fun Route.addNewsRoutes() {
    get("/v1/news") {
        val targetUrl = "https://tengrinews.kz/tag/университет/"
        val document = scrapeWebsite(targetUrl)
        val items: List<NewsItem> = extractNews(document)

        call.respond(HttpStatusCode.OK, items)
    }

    get("/v1/news/{id}") {
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

    itemElements?.forEachIndexed { index, itemElement ->
        val title = itemElement.getElementsByClass("content_main_item_title").firstOrNull()?.text() ?: ""
        val announce = itemElement.getElementsByClass("content_main_item_announce").firstOrNull()?.text() ?: ""
        val imageUrl = "https://tengrinews.kz/${
            itemElement.getElementsByClass("content_main_item_img").firstOrNull()?.attr("src") ?: ""
        }"
        val date = itemElement.getElementsByClass("content_main_item_meta").firstOrNull()?.getElementsByTag("span")
            ?.firstOrNull()?.text()?.trim() ?: ""
        val id = index.toString() // Generating ID based on item index
        val link =
            itemElement.getElementsByClass("content_main_item_title").firstOrNull()?.getElementsByTag("a")?.attr("href")
                ?: ""
        newsItems.add(NewsItem(id, imageUrl, title, announce, date, link))
    }

    return newsItems
}

@Serializable
data class NewsItem(
    val id: String,
    val imageUrl: String,
    val title: String,
    val announce: String,
    val date: String,
    val link: String,
)
