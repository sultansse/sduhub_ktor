package com.softwareit.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


fun Route.addNewsRoutes() {
    get("/news") {

        runBlocking {
            val targetUrl =
                "https://tengrinews.kz/tag/%D1%83%D0%BD%D0%B8%D0%B2%D0%B5%D1%80%D1%81%D0%B8%D1%82%D0%B5%D1%82/"
            val document = scrapeWebsite(targetUrl)
            val items = extractElements(document)

            call.respondText("News title: ${items.first().title}, News announce: ${items.first().announce}")
        }
    }

    get("/news/{id}") {
        val id = call.parameters["id"]
        call.respondText("News with id $id")
    }
}

suspend fun scrapeWebsite(url: String): Document {
    return Jsoup.connect(url).get()
}

fun extractElements(document: Document): List<ContentItem> {
    val contentItems = mutableListOf<ContentItem>()
    val mainContentDiv = document.selectFirst("div.content_main")
    val itemElements = mainContentDiv?.getElementsByClass("content_main_item")

    itemElements?.forEach { itemElement ->
        val title = itemElement.getElementsByClass("content_main_item_title").firstOrNull()?.text() ?: ""
        val announce = itemElement.getElementsByClass("content_main_item_announce").firstOrNull()?.text() ?: ""
        contentItems.add(ContentItem(title, announce))
    }

    return contentItems
}

data class ContentItem(val title: String, val announce: String)

