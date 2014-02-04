package model

case class ContentApiResponse(
                               status: String,
                               userTier: String,
                               total: Option[Int],
                               startIndex: Option[Int],
                               pageSize: Option[Int],
                               currentPage: Option[Int],
                               pages: Option[Int],
                               orderBy: Option[String],
                               tag: Option[Tag],
                               edition: Option[Edition],
                               section: Option[Section],
                               content: Option[Content],
                               results: Option[List[Content]],
                               relatedContent: Option[List[Content]],
                               editorsPicks: Option[List[Content]],
                               mostViewed: Option[List[Content]],
                               storyPackage: Option[List[Content]],
                               leadContent: Option[List[Content]]
                               )

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
