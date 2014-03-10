package test

import org.scalatest.{FlatSpec, ShouldMatchers}
import model._
import java.io.File
import scala.io.Source
import model.Asset
import model.ContentApiResponse
import scala.Some

class AssetsTest extends FlatSpec with ShouldMatchers {

  val baseDir = new File(System.getProperty("user.dir"), "testdata")
  def get(fileName: String): String = Source.fromFile(new File(baseDir, fileName), "UTF-8").getLines().mkString
  def getItemResponse: String = get("elements-response.json")

  val itemResponse: String = getItemResponse
  val contentApiResponse: ContentApiResponse = ContentApiParser.parseResponse(itemResponse).get

  def getAsset(itemIndex: Int, elementIndex: Int, assetIndex: Int): Asset = contentApiResponse.results.get.apply(itemIndex).elements.get.apply(elementIndex).assets.apply(assetIndex)
  val imageAsset: Asset = getAsset(0, 0, 0)
  val audioAsset: Asset = getAsset(9, 1, 0)
  val videoAsset: Asset = getAsset(10, 6, 1)

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

  it should "parse video assets" in {
    videoAsset.assetType should be ("video")
    videoAsset.mimeType should be (Some("video/3gpp:small"))
    videoAsset.file should be (Some("http://cdn.theguardian.tv/3gp/small/2014/03/10/140210juliarobinson3_FromGAus_3gpSml16x9.3gp"))
    videoAsset.typeData.keys.toList.length should be (7)

    videoAsset.durationMinutes should be (Some(2))
    videoAsset.durationSeconds should be (Some(34))
    videoAsset.duration should be (Some(154))

    videoAsset.blocksAds should be (false)
  }

  it should "use match correctly against types" in {
    val matchResult = imageAsset match {
      case Audio(audio) => "audio"
      case Video(video) => "video"
      case Image(image) => "image"
      case _ => "NoMatch"
    }
    matchResult should be ("image")

    Audio.unapply(audioAsset) shouldBe an [Option[Audio]]
    Audio.unapply(imageAsset) should be (None)
    Audio.unapply(videoAsset) should be (None)

    Video.unapply(audioAsset) should be (None)
    Video.unapply(imageAsset) should be (None)
    Video.unapply(videoAsset) shouldBe an [Option[Video]]

    Image.unapply(audioAsset) should be (None)
    Image.unapply(imageAsset) shouldBe an [Option[Image]]
    Image.unapply(videoAsset) should be (None)
  }

}
