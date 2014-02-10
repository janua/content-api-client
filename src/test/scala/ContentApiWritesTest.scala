package test

import org.scalatest.{FlatSpec, ShouldMatchers}
import model.{ContentApiParser, ContentApiResponse, ContentImplicitWrites}
import java.io.File
import scala.io.Source
import play.api.libs.json.Json

class ContentApiWritesTest extends FlatSpec with ShouldMatchers with ContentImplicitWrites {

  val baseDir = new File(System.getProperty("user.dir"), "testdata")
  def get(fileName: String): String = Source.fromFile(new File(baseDir, fileName), "UTF-8").getLines().mkString
  def getItemResponse: String = get("item-response.json")

  val itemResponse: String = getItemResponse
  val contentApiResponse: ContentApiResponse = ContentApiParser.parseResponse(itemResponse).get

  "Content class" should "be equal to the JSON it came from" in {
    Json.parse(itemResponse) should be (Json.toJson(contentApiResponse))
  }
}
