package test

import io.Source
import java.io.File
import org.scalatest.{FlatSpec, ShouldMatchers}
import model.{Edition, ContentApiResponse, ContentApiParser}

class ContentApiParserTest extends FlatSpec with ShouldMatchers {

  val baseDir = new File(System.getProperty("user.dir"), "testdata")
  def get(fileName: String): String = Source.fromFile(new File(baseDir, fileName), "UTF-8").getLines().mkString
  def getItemResponse: String = get("item-response.json")

  val itemResponse: String = getItemResponse
  val contentApiResponse: ContentApiResponse = ContentApiParser.parseResponse(itemResponse).get

  "ContentApiParser" should "parse top level" in {
    contentApiResponse.status should be ("ok")
    contentApiResponse.pages should be (Some(2618))
    contentApiResponse.userTier should be (Some("free"))
    contentApiResponse.startIndex should be (Some(1))
    contentApiResponse.pageSize should be (Some(10))
    contentApiResponse.currentPage should be (Some(1))
    contentApiResponse.total should be (Some(26176))
    contentApiResponse.orderBy should be (Some("newest"))
  }

  it should "parse the edition" in {
    val edition: Edition = contentApiResponse.edition.get
    edition.id should be ("uk/culture")
    edition.webTitle should be ("Culture")
    edition.webUrl should be ("http://www.theguardian.com/uk/culture")
    edition.apiUrl should be ("http://content.guardianapis.com/uk/culture")
    edition.code should be ("uk")
  }

  it should "parse the section" in {
    val section = contentApiResponse.section.get
    section.id should be ("culture")
    section.webTitle should be ("Culture")
    section.webUrl should be ("http://www.theguardian.com/culture")
    section.apiUrl should be ("http://content.guardianapis.com/culture")
    section.editions.length should be (4)
  }

  it should "parse an edition in a section" in {
    val edition = contentApiResponse.section.get.editions(3)
    edition.id should be ("au/culture")
    edition.webTitle should be ("Culture")
    edition.webUrl should be ("http://www.theguardian.com/au/culture")
    edition.apiUrl should be ("http://content.guardianapis.com/au/culture")
    edition.code should be ("au")
  }

}
