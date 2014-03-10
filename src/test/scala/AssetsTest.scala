package test

import org.scalatest.{FlatSpec, ShouldMatchers}
import model.{Asset, ContentApiResponse, ContentApiParser}
import java.io.File
import scala.io.Source

class AssetsTest extends FlatSpec with ShouldMatchers {

  val baseDir = new File(System.getProperty("user.dir"), "testdata")
  def get(fileName: String): String = Source.fromFile(new File(baseDir, fileName), "UTF-8").getLines().mkString
  def getItemResponse: String = get("elements-response.json")

  val itemResponse: String = getItemResponse
  val contentApiResponse: ContentApiResponse = ContentApiParser.parseResponse(itemResponse).get

  def getAsset(itemIndex: Int, elementIndex: Int, assetIndex: Int): Asset = contentApiResponse.results.get.apply(itemIndex).elements.get.apply(elementIndex).assets.apply(assetIndex)
  val imageAsset: Asset = getAsset(0, 0, 0)
  val audioAsset: Asset = getAsset(9, 1, 0)

  "ContentApiParser" should "parse image assets" in {
    imageAsset.assetType should be ("image")
    imageAsset.mimeType should be (Some("image/jpeg"))
    imageAsset.file should be (Some("http://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2014/2/19/1392831807426/The-Courtauld-Gallerys-ha-001.jpg"))
    imageAsset.typeData.keys.toList.length should be (6)

    imageAsset.duration should be (None)
    imageAsset.durationMinutes should be (None)
    imageAsset.durationSeconds should be (None)
  }

  it should "parse audio assets" in {
    audioAsset.assetType should be ("audio")
    audioAsset.mimeType should be (Some("audio/mpeg"))
    audioAsset.file should be (Some("http://static.guim.co.uk/audio/kip/culture/series/guardian-australia-culture-podcast/1392773496555/4786/140219perth2.mp3"))
    audioAsset.durationMinutes should be (Some(25))
    audioAsset.durationSeconds should be (Some(36))
    audioAsset.explicit should be (Some("false"))
    audioAsset.source should be (Some("Guardian"))
  }

}
