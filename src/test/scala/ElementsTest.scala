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

  it should "parse Assets" in {
    val asset = contentApiResponse.results.get.apply(0).elements.get(0).assets(0)

    asset.assetType should be ("image")
    asset.mimeType should be (Some("image/jpeg"))
    asset.file should be (Some("http://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2014/2/19/1392831807426/The-Courtauld-Gallerys-ha-001.jpg"))
    asset.typeData.keys.toSeq.length should be (6)

    //typeData
    asset.source.get should be ("Courtauld Gallery, London")
    asset.altText.get should be ("The Courtauld Gallery's handbag")
    asset.height.get should be ("54")
    asset.credit.get should be ("Courtauld Gallery, London")
    asset.caption.get should be ("The Courtauld Gallery's handbag made of brass inlaid with silver and gold, from Mosul, northern Iraq, dated 1300-1330. Photograph: Courtauld Gallery, London")
    asset.width.get should be ("54")
  }

}
