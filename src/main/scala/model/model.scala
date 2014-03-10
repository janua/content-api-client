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

trait ContentImplicitWrites {
  implicit val assetRead = Json.writes[Asset]
  implicit val elementRead = Json.writes[Element]
  implicit val referenceRead = Json.writes[Reference]
  implicit val folderRead = Json.writes[Folder]
  implicit val bestBetRead = Json.writes[BestBet]
  implicit val refinementRead = Json.writes[Refinement]
  implicit val refinementGroupRead = Json.writes[RefinementGroup]
  implicit val mediaEncodingRead = Json.writes[MediaEncoding]
  implicit val mediaAssetRead = Json.writes[MediaAsset]
  implicit val editionRead = Json.writes[Edition]
  implicit val sectionRead = Json.writes[Section]
  implicit val tagRead = Json.writes[Tag]
  implicit val factBoxRead = Json.writes[Factbox]
  implicit val contentRead = Json.writes[Content]
  implicit val contentApiResponseRead = Json.writes[ContentApiResponse]
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
  lazy val tag: Option[Tag] = response.get("tag").flatMap(_.asOpt[Map[String, JsValue]]).map(Tag)
  lazy val edition: Option[Edition] = response.get("edition").flatMap(_.asOpt[Map[String, JsValue]]).map(Edition)
  lazy val section: Option[Section] = response.get("section").flatMap(_.asOpt[Map[String, JsValue]]).map(Section)
  lazy val content: Option[Content] = response.get("content").flatMap(_.asOpt[Map[String, JsValue]].map(Content))
  lazy val results: Option[List[Content]] = response.get("results").flatMap(_.asOpt[List[Map[String, JsValue]]]).map(_.map(Content))
  lazy val relatedContent: Option[List[Content]] = response.get("relatedContent").flatMap(_.asOpt[List[Map[String, JsValue]]]).map(_.map(Content))
  lazy val editorsPicks: Option[List[Content]] = response.get("editorsPicks").flatMap(_.asOpt[List[Map[String, JsValue]]]).map(_.map(Content))
  lazy val mostViewed: Option[List[Content]] = response.get("mostViewed").flatMap(_.asOpt[List[Map[String, JsValue]]]).map(_.map(Content))
  lazy val storyPackage: Option[List[Content]] = response.get("storyPackage").flatMap(_.asOpt[List[Map[String, JsValue]]]).map(_.map(Content))
  lazy val leadContent: Option[List[Content]] = response.get("leadContent").flatMap(_.asOpt[List[Map[String, JsValue]]]).map(_.map(Content))
}

case class Content(jsonFields: Map[String, JsValue]) extends ContentImplicitReads {
                    lazy val id: String = jsonFields.apply("id").as[String]
                    lazy val sectionId: Option[String] = jsonFields.get("sectionId").flatMap(_.asOpt[String])
                    lazy val sectionName: Option[String] = jsonFields.get("sectionName").flatMap(_.asOpt[String])
                    lazy val webPublicationDate: String = jsonFields.apply("webPublicationDate").as[String]
                    lazy val webTitle: String = jsonFields.apply("webTitle").as[String]
                    lazy val webUrl: String = jsonFields.apply("webUrl").as[String]
                    lazy val apiUrl: String = jsonFields.apply("apiUrl").as[String]
                    lazy val fields: Option[Map[String, String]] = jsonFields.get("fields").flatMap(_.asOpt[Map[String, String]])
                    lazy val tags: Option[List[Tag]] = jsonFields.get("tags").flatMap(_.asOpt[List[Map[String, JsValue]]]).map(_.map(Tag))
                    lazy val factboxes: Option[List[Factbox]] = jsonFields.get("factboxes").flatMap(_.asOpt[List[Factbox]])
                    lazy val mediaAssets: Option[List[MediaAsset]] = jsonFields.get("mediaAssets").flatMap(_.asOpt[List[MediaAsset]])
                    lazy val elements: Option[List[Element]] = jsonFields.get("elements").flatMap(_.asOpt[List[Map[String, JsValue]]]).map(_.map(Element))
                    lazy val snippets: Option[Map[String, String]] = jsonFields.get("snippets").flatMap(_.asOpt[Map[String, String]])
                    lazy val references: Option[List[Reference]] = jsonFields.get("references").flatMap(_.asOpt[List[Reference]])
}


case class Tag(jsonFields: Map[String, JsValue]) extends ContentImplicitReads {
                lazy val id: String = jsonFields.apply("id").as[String]
                lazy val `type`: String = jsonFields.apply("type").as[String]
                lazy val sectionId: Option[String] = jsonFields.get("sectionId").flatMap(_.asOpt[String])
                lazy val sectionName: Option[String] = jsonFields.get("sectionName").flatMap(_.asOpt[String])
                lazy val webTitle: String = jsonFields.apply("webTitle").as[String]
                lazy val webUrl: String = jsonFields.apply("webUrl").as[String]
                lazy val apiUrl: String = jsonFields.apply("apiUrl").as[String]
                lazy val references: Option[List[Reference]] = jsonFields.get("references").flatMap(_.asOpt[List[Reference]])
                lazy val bio: Option[String] = jsonFields.get("bio").flatMap(_.asOpt[String])
                lazy val bylineImageUrl: Option[String] = jsonFields.get("bylineImageUrl").flatMap(_.asOpt[String])
                lazy val bylineLargeImageUrl: Option[String] = jsonFields.get("bylineLargeImageUrl").flatMap(_.asOpt[String])

  def tagType = `type`
}

case class Edition(jsonFields: Map[String, JsValue]) extends ContentImplicitReads {
                    lazy val id: String = jsonFields.apply("id").as[String]
                    lazy val webTitle: String = jsonFields.apply("webTitle").as[String]
                    lazy val webUrl: String = jsonFields.apply("webUrl").as[String]
                    lazy val apiUrl: String = jsonFields.apply("apiUrl").as[String]
                    lazy val code: String = jsonFields.apply("code").as[String]
}

case class Section(jsonFields: Map[String, JsValue]) extends ContentImplicitReads {
                    lazy val id: String = jsonFields.apply("id").as[String]
                    lazy val webTitle: String = jsonFields.apply("webTitle").as[String]
                    lazy val webUrl: String = jsonFields.apply("webUrl").as[String]
                    lazy val apiUrl: String = jsonFields.apply("apiUrl").as[String]
                    lazy val editions: List[Edition] = jsonFields.apply("editions").as[List[Map[String, JsValue]]].map(Edition)
}

case class Folder(jsonFields: Map[String, JsValue]) extends ContentImplicitReads {
                   lazy val id: String = jsonFields.apply("id").as[String]
                   lazy val webTitle: String = jsonFields.apply("webTitle").as[String]
                   lazy val apiUrl: String = jsonFields.apply("apiUrl").as[String]
                   lazy val sectionId: Option[String] = jsonFields.get("sectionId").flatMap(_.asOpt[String])
                   lazy val sectionName: Option[String] = jsonFields.get("sectionName").flatMap(_.asOpt[String])
}


case class Factbox(jsonFields: Map[String, JsValue]) extends ContentImplicitReads {
                    lazy val `type`: String = jsonFields.apply("type").as[String]
                    lazy val heading: Option[String] = jsonFields.get("heading").flatMap(_.asOpt[String])
                    lazy val picture: Option[String] = jsonFields.get("picture").flatMap(_.asOpt[String])
                    lazy val fields: Option[Map[String, String]] = jsonFields.get("fields").flatMap(_.asOpt[Map[String, String]])

  def factboxType = `type`
}

case class MediaAsset(jsonFields: Map[String, JsValue]) extends ContentImplicitReads  {
                       lazy val `type`: String = jsonFields.apply("type").as[String]
                       lazy val rel: String = jsonFields.apply("rel").as[String]
                       lazy val index: Int = jsonFields.apply("index").as[Int]
                       lazy val file: Option[String] = jsonFields.apply("file").asOpt[String]
                       lazy val fields: Option[Map[String, String]] = jsonFields.apply("fields").asOpt[Map[String, String]]
                       lazy val encodings: List[MediaEncoding] = jsonFields.apply("encodings").asOpt[List[MediaEncoding]].getOrElse(Nil)

  def mediaAssetType = `type`
}

case class MediaEncoding(jsonFields: Map[String, JsValue]) extends ContentImplicitReads  {
                          lazy val format: String = jsonFields.apply("format").as[String]
                          lazy val file: String = jsonFields.apply("file").as[String]
}

case class RefinementGroup(jsonFields: Map[String, JsValue]) extends ContentImplicitReads  {
                            lazy val `type`: String = jsonFields.apply("type").as[String]
                            lazy val refinements: List[Refinement] = jsonFields.apply("refinements").as[List[Refinement]]
                            
  def refinementType = `type`
}

case class Refinement(jsonFields: Map[String, JsValue]) extends ContentImplicitReads  {
                       lazy val count: Int = jsonFields.apply("count").as[Int]
                       lazy val refinedUrl: String = jsonFields.apply("refinedUrl").as[String]
                       lazy val displayName: String = jsonFields.apply("displayName").as[String]
                       lazy val id: String = jsonFields.apply("id").as[String]
                       lazy val apiUrl: String = jsonFields.apply("apiUrl").as[String]
}

case class BestBet(jsonFields: Map[String, JsValue]) extends ContentImplicitReads  {
                    lazy val webTitle: String = jsonFields.apply("webTitle").as[String]
                    lazy val webUrl: String = jsonFields.apply("webUrl").as[String]
                    lazy val trailText: Option[String] = jsonFields.apply("trailText").as[Option[String]]
}

case class Reference(jsonFields: Map[String, JsValue]) extends ContentImplicitReads  {
                      lazy val `type`: String = jsonFields.apply("type").as[String]
                      lazy val id: String = jsonFields.apply("id").as[String]
}

case class Element(jsonFields: Map[String, JsValue]) extends ContentImplicitReads  {
                    lazy val id: String = jsonFields.apply("id").as[String]
                    lazy val relation: String = jsonFields.apply("relation").as[String]
                    lazy val `type`: String = jsonFields.apply("type").as[String]
                    lazy val galleryIndex: Option[Int] = jsonFields.get("galleryIndex").flatMap(_.asOpt[Int])
                    lazy val assets: List[Asset] = jsonFields.get("assets").flatMap(_.asOpt[List[Map[String, JsValue]]]).map(_.map(Asset)).getOrElse(Nil)

  def elementType = `type`
  lazy val largestAsset: Option[Asset] = assets.sortBy(_.width.getOrElse(0)).headOption
}

case class Asset(jsonFields: Map[String, JsValue]) extends ContentImplicitReads  {
                  lazy val `type`: String = jsonFields.apply("type").as[String]
                  lazy val mimeType: Option[String] = jsonFields.get("mimeType").flatMap(_.asOpt[String])
                  lazy val file: Option[String] = jsonFields.get("file").flatMap(_.asOpt[String])
                  lazy val typeData: Map[String, String] = jsonFields.get("typeData").flatMap(_.asOpt[Map[String, String]]).getOrElse(Map.empty)
  def assetType = `type`

  lazy val secureFile: Option[String] = typeData.get("secureFile")
  lazy val source: Option[String] = typeData.get("source")
  lazy val photographer: Option[String] = typeData.get("photographer")
  lazy val altText: Option[String] = typeData.get("altText")
  lazy val credit: Option[String] = typeData.get("credit")
  lazy val caption: Option[String] = typeData.get("caption")
  lazy val mediaId: Option[String] = typeData.get("mediaId")
  lazy val picdarUrn: Option[String] = typeData.get("picdarUrn")

  lazy val height: Option[Int] = typeData.get("height").map(_.toInt)
  lazy val width: Option[Int] = typeData.get("width").map(_.toInt)

  lazy val durationMinutes: Option[Int] = typeData.get("durationMinutes").map(_.toInt)
  lazy val durationSeconds: Option[Int] = typeData.get("durationSeconds").map(_.toInt)
  lazy val duration: Option[Int] = for {dm <- durationMinutes;ds <- durationSeconds} yield dm * 60 + ds
  lazy val explicit: Option[String] = typeData.get("explicit")

  //Belong to Video?
  lazy val blocksAds: Boolean = typeData.get("blocksAds").exists(_ == "true")
}
