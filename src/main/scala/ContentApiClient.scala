package contentapiclient

import scala.concurrent.{ExecutionContext, Future}
import dispatch.{url, Http}
import com.ning.http.client.Response
import model.{ContentApiResponse, ContentApiParser}


trait ContentApiClient {

  import ExecutionContext.Implicits.global

  private val apiUrl: String = "http://content.guardianapis.com"

  private def getUrl(id: String): String = s"$apiUrl/$id"

  private def parseResponse(r: Response): Option[ContentApiResponse] =
    ContentApiParser.parseResponse(r)

  def getResponse(id: String): Future[Response] = Http(url(getUrl(id)))

  def getResponse(query: ContentApiQuery): Future[Response] =
    Http(query.params.foldLeft(url(getUrl(query.id))){case (req, (k, v)) => req.addQueryParameter(k, v)})

  def getSearchResponse(query: ContentApiQuery): Future[Response] =
    Http(query.params.foldLeft(url(getUrl(s"search"))){case (req, (k, v)) => req.addQueryParameter(k, v)})

  def getContent(id: String): Future[Option[ContentApiResponse]] =
    getResponse(id).map(parseResponse)

  def getContent(query: ContentApiQuery):  Future[Option[ContentApiResponse]] =
    getResponse(query).map(parseResponse)

  def search(id: String): Future[Option[ContentApiResponse]] =
    getSearchResponse(ContentApiQuery(id).withQueryString("q", id)).map(parseResponse)
}

object ContentApiClient extends ContentApiClient