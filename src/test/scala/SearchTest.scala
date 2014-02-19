package test

import org.scalatest.{ShouldMatchers, FlatSpec}
import java.io.File
import scala.io.Source
import model.{Element, ContentApiParser, ContentApiResponse}

class SearchTest extends FlatSpec with ShouldMatchers {

  val baseDir = new File(System.getProperty("user.dir"), "testdata")
  def get(fileName: String): String = Source.fromFile(new File(baseDir, fileName), "UTF-8").getLines().mkString
  def getSearchResponse: String = get("search-response.json")

  val searchResponse: String = getSearchResponse
  val contentApiResponse: ContentApiResponse = ContentApiParser.parseResponse(searchResponse).get

  "ContentApiParser" should "parse the search response" in {
    contentApiResponse.status should be ("ok")
    contentApiResponse.userTier.get should be ("free")
    contentApiResponse.total.get should be (407805)
    contentApiResponse.startIndex.get should be (1)
    contentApiResponse.pageSize.get should be (10)
    contentApiResponse.currentPage.get should be (1)
    contentApiResponse.pages.get should be (40781)
    contentApiResponse.orderBy.get should be ("newest")
    contentApiResponse.results.get.length should be (10)
  }

}
