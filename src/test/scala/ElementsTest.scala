package test

import org.scalatest.{FlatSpec, ShouldMatchers}
import model.{Element, ContentApiResponse, ContentApiParser}
import java.io.File
import scala.io.Source

class ElementsTest extends FlatSpec with ShouldMatchers {

  val baseDir = new File(System.getProperty("user.dir"), "testdata")
  def get(fileName: String): String = Source.fromFile(new File(baseDir, fileName), "UTF-8").getLines().mkString
  def getItemResponse: String = get("elements-response.json")

  val itemResponse: String = getItemResponse
  val contentApiResponse: ContentApiResponse = ContentApiParser.parseResponse(itemResponse).get

  "ContentApiParser" should "parse the elements" in {
    val elements: List[Element] = contentApiResponse.results.get.apply(0).elements.get
    elements.length should be (2)

    val firstElement = elements(0)
    firstElement.id should be ("gu-image-430247659")
    firstElement.relation should be ("main")
    firstElement.`type` should be ("image")
    firstElement.assets.length should be (15)

    val secondElement = elements(1)
    secondElement.id should be ("gu-image-430247660")
    secondElement.relation should be ("thumbnail")
    secondElement.`type` should be ("image")
    secondElement.assets.length should be (2)
  }

}
