package model

import play.api.libs.json.Json
import com.ning.http.client.Response

trait ContentApiParser {

  implicit val assetRead = Json.reads[Asset]
  implicit val elementRead = Json.reads[Element]
  implicit val referenceRead = Json.reads[Reference]
  implicit val folderRead = Json.reads[Folder]
  implicit val bestBetRead = Json.reads[BestBet]
  implicit val refinementRead = Json.reads[Refinement]
  implicit val refinementGroupRead = Json.reads[RefinementGroup]
  implicit val mediaEncodingRead = Json.reads[MediaEncoding]
  implicit val mediaAssetRead = Json.reads[MediaAsset]
  implicit val editionRead = Json.reads[Edition]
  implicit val sectionRead = Json.reads[Section]
  implicit val tagRead = Json.reads[Tag]
  implicit val factBoxRead = Json.reads[Factbox]
  implicit val contentRead = Json.reads[Content]
  implicit val contentApiResponseRead = Json.reads[ContentApiResponse]

  def parseResponse(r: String): Option[ContentApiResponse] =
    (Json.parse(r) \ "response").asOpt[ContentApiResponse]

  def parseResponse(r: Response): Option[ContentApiResponse] =
    parseResponse(r.getResponseBody)
}

object ContentApiParser extends ContentApiParser
