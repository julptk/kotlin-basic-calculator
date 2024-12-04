package com.example.calculator

import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import java.io.IOException

class ImageScraper {
    private val client = OkHttpClient()

    fun fetchImageUrls(url: String): List<String> {
        val imageUrls = mutableListOf<String>()

        try {
            // Fetch HTML content using OkHttp
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val htmlContent = response.body?.string() ?: ""

            // Parse HTML with JSoup
            val document = Jsoup.parse(htmlContent)
            val images = document.select("img[src]") // Select all <img> tags with src attribute

            for (img in images) {
                val imageUrl = img.absUrl("src") // Get absolute URL of the image
                if (imageUrl.isNotEmpty()) {
                    imageUrls.add(imageUrl)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return imageUrls
    }
}