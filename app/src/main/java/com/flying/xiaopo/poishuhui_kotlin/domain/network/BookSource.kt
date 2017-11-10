package com.flying.xiaopo.poishuhui_kotlin.domain.network

import com.flying.xiaopo.poishuhui_kotlin.Level
import com.flying.xiaopo.poishuhui_kotlin.domain.model.Cover
import com.flying.xiaopo.poishuhui_kotlin.getHtml
import com.flying.xiaopo.poishuhui_kotlin.log
import org.jsoup.Jsoup
import java.util.*

/**
 * @author wupanjie
 */
class BookSource : Source<ArrayList<Cover>> {
    var baseURL = "http://ishuhui.com"
    override fun obtain(url: String): ArrayList<Cover> {
        val list = ArrayList<Cover>()
        baseURL = url
        val html = getHtml(url)
        val doc = Jsoup.parse(html)

        val elements = doc.select("ul.chinaMangaContentList").select("li")
        log(Level.I, elements.toString())
        for (element in elements) {
            val coverUrl = if (element.select("img").attr("src").contains("http")) {
                element.select("img").attr("src")
            } else {
                baseURL + element.select("img").attr("src")
            }
            log(Level.I, coverUrl)
            val title = element.select("p").text()
            val link = baseURL + element.select("div.chinaMangaContentImg").select("a").attr("href")

            val cover = Cover(coverUrl, title, link)
            list.add(cover)
        }

        return list
    }

}