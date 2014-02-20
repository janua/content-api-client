package contentapiclient

import org.joda.time.DateTime
import model.ContentApiResponse
import scala.concurrent.Future

case class ContentApiQuery(id: Option[String], queryParams: Map[String, String] = Map.empty) {

  def params: Seq[(String, String)] = queryParams.toSeq

  def withQueryString(key: String, value: String) = copy(queryParams = queryParams + (key -> value))

  def withApiKey(apiKey: String) = withQueryString("api-key", apiKey)
  def withUserTier(userTier: String) = withQueryString("user-tier", userTier)
  def withPageSize(pageSize: String) = withQueryString("page-size", pageSize)

  //Filters
  def filterSection(section: String) = withQueryString("section", section)
  def filterIds(ids: String): ContentApiQuery = withQueryString("ids", ids)
  def filterIds(ids: Seq[String]): ContentApiQuery = filterIds(ids.mkString(","))

  def filterTag(tag: String) = withQueryString("tag", tag)
  def filterFolder(folder: String) = withQueryString("folder", folder)


  def withOrderBy(orderBy: String) = withQueryString("order-by", orderBy)
  def withFromDate(fromDate: DateTime) = withQueryString("from-date", fromDate.toString)
  def withToDate(toDate: DateTime) = withQueryString("to-date", toDate.toString)
  def withDateId(dateId: String) = withQueryString("date-id", dateId)
  def withUseDate(useDate: String) = withQueryString("use-date", useDate)

  //Show
  def withShowFields(showFields: String) = withQueryString("show-fields", showFields)
  def withShowSnippets(showSnippets: String) = withQueryString("show-snippets", showSnippets)
  def withShowTags(showTags: String) = withQueryString("show-tags", showTags)
  def withShowFactboxes(showFactboxes: String) = withQueryString("show-factboxes", showFactboxes)
  //Following Deprecated ??
  def withShowMedia(showMedia: String) = withQueryString("show-media", showMedia)
  def withShowElements(showElements: String) = withQueryString("show-elements", showElements)
  def withShowRelated(showRelated: String) = withQueryString("show-related", showRelated)
  def withShowEditorsPicks(showEditorsPicks: Boolean) = withQueryString("show-editors-picks", showEditorsPicks.toString)
  def withEdition(edition: String) = withQueryString("edition", edition)
  def withShowMostViewed(mostViewed: Boolean) = withQueryString("show-most-viewed", mostViewed.toString)
  def withShowStoryPackage(storyPackage: Boolean) = withQueryString("show-story-package", storyPackage.toString)
  def withShowBestBets(bestBets: Boolean) = withQueryString("show-best-bets", bestBets.toString)
  def withSnippetPre(snippetPre: String) = withQueryString("snippet-pre", snippetPre)
  def withSnippetPost(snippetPost: String) = withQueryString("snippet-post", snippetPost)
  def withShowInlineElements(inlineElements: String) = withQueryString("show-inline-elements", inlineElements)
  def withShowExpired(showExpired: Boolean) = withQueryString("show-expired", showExpired.toString)

  def withShowRefinements(showRefinements: String) = withQueryString("show-refinements", showRefinements)
  def withRefinementSize(refinementSize: Int) = withQueryString("refinements-size", refinementSize.toString)

  //Reference
  def withReference(reference: String) = withQueryString("reference", reference)
  def withReferenceType(referenceType: String) = withQueryString("reference-type", referenceType)
  def withShowReferences(references: String) = withQueryString("show-references", references)

  def withSearchQuery(query: String) = withQueryString("q", query)

  def get: Future[Option[ContentApiResponse]] =
    queryParams.get("q")
      .map{_ => ContentApiClient.search(this)}
      .getOrElse(ContentApiClient.getContent(this))

  def getId: String = id.getOrElse(if (queryParams.contains("q")) "search" else "")
}

object ContentApiQuery {
  def apply(id: String): ContentApiQuery = ContentApiQuery(Some(id))
  def apply(): ContentApiQuery = ContentApiQuery(None)
}
