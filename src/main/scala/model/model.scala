package model

import play.api.libs.json.{Json, JsValue}

trait ContentImplicitReads {
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
}

case class ContentApiResponse(response: Map[String, JsValue]) extends ContentImplicitReads {
  lazy val status: String = response.apply("status").as[String]
  lazy val message: Option[String] = response.get("message").flatMap(_.asOpt[String])
  lazy val userTier: Option[String] = response.get("userTier").flatMap(_.asOpt[String])
  lazy val total: Option[Int] = response.get("total").flatMap(_.asOpt[Int])
  lazy val startIndex: Option[Int] = response.get("startIndex").flatMap(_.asOpt[Int])
  lazy val pageSize: Option[Int] = response.get("pageSize").flatMap(_.asOpt[Int])
  lazy val currentPage: Option[Int] = response.get("currentPage").flatMap(_.asOpt[Int])
  lazy val pages: Option[Int] = response.get("pages").flatMap(_.asOpt[Int])
  lazy val orderBy: Option[String] = response.get("orderBy").flatMap(_.asOpt[String])
  lazy val tag: Option[Tag] = response.get("tag").flatMap(_.asOpt[Tag])
  lazy val edition: Option[Edition] = response.get("edition").flatMap(_.asOpt[Edition])
  lazy val section: Option[Section] = response.get("section").flatMap(_.asOpt[Section])
  lazy val content: Option[Content] = response.get("content").flatMap(_.asOpt[Content])
  lazy val results: Option[List[Content]] = response.get("results").flatMap(_.asOpt[List[Content]])
  lazy val relatedContent: Option[List[Content]] = response.get("relatedContent").flatMap(_.asOpt[List[Content]])
  lazy val editorsPicks: Option[List[Content]] = response.get("editorsPicks").flatMap(_.asOpt[List[Content]])
  lazy val mostViewed: Option[List[Content]] = response.get("mostViewed").flatMap(_.asOpt[List[Content]])
  lazy val storyPackage: Option[List[Content]] = response.get("storyPackage").flatMap(_.asOpt[List[Content]])
  lazy val leadContent: Option[List[Content]] = response.get("leadContent").flatMap(_.asOpt[List[Content]])
}

case class Content(
                    id: String,
                    sectionId: Option[String],
                    sectionName: Option[String],
                    webPublicationDate: String,
                    webTitle: String,
                    webUrl: String,
                    apiUrl: String,
                    fields: Option[Map[String, String]],
                    tags: Option[List[Tag]],
                    factboxes: Option[List[Factbox]],
                    mediaAssets: Option[List[MediaAsset]],
                    elements: Option[List[Element]],
                    snippets: Option[Map[String, String]],
                    references: Option[List[Reference]],
                    isExpired: Option[Boolean]
                    )


case class Tag(
                id: String,
                `type`: String,
                sectionId: Option[String] = None,
                sectionName: Option[String] = None,
                webTitle: String,
                webUrl: String,
                apiUrl: String,
                references: Option[List[Reference]] = None,
                bio: Option[String] = None,
                bylineImageUrl: Option[String] = None,
                bylineLargeImageUrl: Option[String] = None
                ) {
  def tagType = `type`
}

case class Edition(
                    id: String,
                    webTitle: String,
                    webUrl: String,
                    apiUrl: String,
                    code: String
                    )

case class Section(
                    id: String,
                    webTitle: String,
                    webUrl: String,
                    apiUrl: String,
                    editions: List[Edition]
                    )

case class Folder(
                   id: String,
                   webTitle: String,
                   apiUrl: String,
                   sectionId: Option[String] = None,
                   sectionName: Option[String] = None
                   )


case class Factbox(
                    `type`: String,
                    heading: Option[String],
                    picture: Option[String],
                    fields: Option[Map[String, String]]
                    ) {
  def factboxType = `type`
}

case class MediaAsset(
                       `type`: String,
                       rel: String,
                       index: Int,
                       file: Option[String],
                       fields: Option[Map[String, String]],
                       encodings: List[MediaEncoding] = Nil
                       ) {
  def mediaAssetType = `type`
}

case class MediaEncoding(
                          format: String,
                          file: String
                          )

case class RefinementGroup(
                            `type`: String,
                            refinements: List[Refinement]
                            ) {
  def refinementType = `type`
}

case class Refinement(
                       count: Int,
                       refinedUrl: String,
                       displayName: String,
                       id: String,
                       apiUrl: String
                       )

case class BestBet(
                    webTitle: String,
                    webUrl: String,
                    trailText: Option[String]
                    )

case class Reference(
                      `type`: String,
                      id: String
                      )

case class Element(
                    id: String,
                    relation: String,
                    `type`: String,
                    galleryIndex: Option[Int] = None,
                    assets: List[Asset]
                    ) {
  def elementType = `type`
}

case class Asset(
                  `type`: String,
                  mimeType: Option[String],
                  file: Option[String],
                  typeData: Map[String, String]
                  ) {
  def assetType = `type`
}
